package com.WadhuWarProject.WadhuWar.adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.MainActivity;
import com.WadhuWarProject.WadhuWar.activity.MyPhotosActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.SliderListImage;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPhotosAdapter extends RecyclerView.Adapter<MyPhotosAdapter.ViewHolder>{
    List<SliderListImage> images = new ArrayList<>();
    Context context;
    UserData user;
    InsertResponse deleteResp;
    ProgressDialog progressBar;
    static boolean isNetworkAvailable;

    int size;
    public MyPhotosAdapter(Context context, List<SliderListImage> images) {
        this.context = context;
        this.images = images;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_myphotos, parent, false);
        user = SharedPrefManager.getInstance(context).getUser();

        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        System.out.println("img123===========" + images.get(position).getImgs());
        Glide.with(context).load(images.get(position).getImgs()).into(holder.profile_image);

        if(position==0){
            holder.ll1.setVisibility(View.VISIBLE);
        }else{
            holder.ll1.setVisibility(View.GONE);
        }

        holder.btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println("img size========="  + images.size());

                if(images.size()==1){

//                    Toast.makeText(context,"Atleast 1 image required!", Toast.LENGTH_SHORT).show();


                    /*dialog apply*/



                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.img_dlt_box);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.getWindow().setGravity(Gravity.CENTER);

                    LinearLayout add_profile_btn =  dialog.findViewById(R.id.add_profile_btn);

                    dialog.show();


                    // Hide after some seconds
                    final Handler handler  = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    };


                    handler.postDelayed(runnable, 2000);


                    /*dialog apply*/


                }else{
                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Are you sure want to delete photo?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteImage(position,holder);
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                }



            }
        });

    }


    public  void  deleteImage(int pos, ViewHolder holder){

        System.out.println("images list========" + images.get(0).getImgs());
        progressBar = ProgressDialog.show(context, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.deleteImage(String.valueOf(user.getUser_id()), String.valueOf(pos));
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                deleteResp = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {

//                    System.out.println("del resp==========" + new Gson().toJson(deleteResp));

                    if(deleteResp.getResid().equals("200")){
                        images.remove(pos);

                        notifyItemRemoved(pos);
                        holder.itemView.setVisibility(View.GONE);


                        if(images.size()>0){
                            notifyItemRangeChanged(pos,images.size());
                        }
                        Toast.makeText(context,"Image Deleted Successfully.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,deleteResp.getResMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 image dlt******" + t.toString());
                Toast.makeText(context,deleteResp.getResMsg(),Toast.LENGTH_SHORT).show();
                if(!isNetworkAvailable(context)){

                    Toast.makeText(context, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Please Try Again!", Toast.LENGTH_SHORT).show();
                }
                progressBar.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {

        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_image;
        LinearLayout ll1;
        ImageButton btn_close;

        public ViewHolder(View itemView) {
            super(itemView);

            ll1 =  itemView.findViewById(R.id.ll1);
            profile_image =  itemView.findViewById(R.id.profile_image);
            btn_close =  itemView.findViewById(R.id.btn_close);


        }
    }
    public static boolean isNetworkAvailable(Context context) {
        isNetworkAvailable = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isNetworkAvailable = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }//isNetworkAvailable()




}
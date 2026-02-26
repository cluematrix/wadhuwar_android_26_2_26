package com.WadhuWarProject.WadhuWar.adapter;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.PremiunMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PremiumMatchesAdapter extends RecyclerView.Adapter<PremiumMatchesAdapter.ViewHolder>{
    ArrayList<PremiunMatchesList.PremiunMatchesListData> premiunMatchesListData = new ArrayList<>();    Context context;
    UserData user;
    static boolean isNetworkAvailable;
    InsertResponse connect_response;

    int size;
    public PremiumMatchesAdapter(Context context,  ArrayList<PremiunMatchesList.PremiunMatchesListData> premiunMatchesListData) {
        this.context = context;
        this.premiunMatchesListData = premiunMatchesListData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_matches, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        user = SharedPrefManager.getInstance(context).getUser();
        holder.connect_btn_pb.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);


        if(premiunMatchesListData!=null) {
            holder.name.setText(premiunMatchesListData.get(position).getName());

            if (!premiunMatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getHeight().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(String.format("%syrs, %s,%s,",
                        premiunMatchesListData.get(position).getAge(),
                        premiunMatchesListData.get(position).getHeight(),
                        premiunMatchesListData.get(position).getMothertounge()));

            } else if (!premiunMatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getHeight().contentEquals("Not Specified") &&
                    premiunMatchesListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        premiunMatchesListData.get(position).getAge() + " yrs, " +
                                premiunMatchesListData.get(position).getHeight());

            } else if (premiunMatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getHeight().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        premiunMatchesListData.get(position).getHeight() + " yrs, " +
                                premiunMatchesListData.get(position).getMothertounge());

            } else if (!premiunMatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                    premiunMatchesListData.get(position).getHeight().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        premiunMatchesListData.get(position).getAge() + " yrs, " +
                                premiunMatchesListData.get(position).getMothertounge());

            } else if (!premiunMatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                    premiunMatchesListData.get(position).getHeight().contentEquals("Not Specified") &&
                    premiunMatchesListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        premiunMatchesListData.get(position).getAge());

            } else if (premiunMatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getHeight().contentEquals("Not Specified") &&
                    premiunMatchesListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        premiunMatchesListData.get(position).getHeight());

            } else if (premiunMatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                    premiunMatchesListData.get(position).getHeight().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        premiunMatchesListData.get(position).getMothertounge());

            } else {
                holder.age_height_mothertoung.setVisibility(View.GONE);

            }


            if (!premiunMatchesListData.get(position).getCaste().contentEquals("Not Specified")) {
                holder.caste.setVisibility(View.VISIBLE);

                if( !premiunMatchesListData.get(position).getSubcaste().contentEquals("Not Specified")){
                    holder.caste.setText(premiunMatchesListData.get(position).getCaste()+" ("+premiunMatchesListData.get(position).getSubcaste()+")");
                }else{
                    holder.caste.setText(premiunMatchesListData.get(position).getCaste());

                }

            }
            else {
                holder.caste.setVisibility(View.GONE);

            }

            if (!premiunMatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setVisibility(View.VISIBLE);

                holder.address.setText(String.format("%s, %s", premiunMatchesListData.get(position).getDistrict(),
                        premiunMatchesListData.get(position).getState()));

            } else if (!premiunMatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    premiunMatchesListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setVisibility(View.VISIBLE);

                holder.address.setText(premiunMatchesListData.get(position).getDistrict());
            } else if (premiunMatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    !premiunMatchesListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setVisibility(View.VISIBLE);

                holder.address.setText(premiunMatchesListData.get(position).getState());
            } else {
                holder.address.setVisibility(View.VISIBLE);
            }



            if (premiunMatchesListData.get(position).getImage() != null) {
                if (premiunMatchesListData.get(position).getImage().equals("")) {
                    holder.img.setVisibility(View.GONE);
                    holder.default_Avtar_LL.setVisibility(View.VISIBLE);


                    if(premiunMatchesListData.get(position).getGender().equals("Female")){
                        holder.img2.setImageResource(R.drawable.female_avtar);
                    }else {
                        holder.img2.setImageResource(R.drawable.male_avtar);
                    }

                } else {

                    holder.default_Avtar_LL.setVisibility(View.GONE);
                    holder.img.setVisibility(View.VISIBLE);
                    Glide.with(context.getApplicationContext()).load(premiunMatchesListData.get(position).getImage()).into(holder.img);

                }
            } else {

                holder.img.setVisibility(View.GONE);
                holder.default_Avtar_LL.setVisibility(View.VISIBLE);

                if(premiunMatchesListData.get(position).getGender().equals("Female")){
                    holder.img2.setImageResource(R.drawable.female_avtar);
                }else {
                    holder.img2.setImageResource(R.drawable.male_avtar);
                }
            }





            holder.topFL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Intent i = new Intent(context, DetailMatchesActivity.class);
//                    i.putExtra("userid", premiunMatchesListData.get(position).getId());
//                    context.startActivity(i);
                    Intent i = new Intent(context, DetailMatchesActivity.class);
                    i.putExtra("userid", premiunMatchesListData.get(position).getId());
                    i.putExtra("profile_list", new Gson().toJson(premiunMatchesListData)); // Pass list as JSON
                    i.putExtra("position", position);
                    context.startActivity(i);

                }
            });
        }



        /*------if connect show contact bottom --------*/
        if(premiunMatchesListData.get(position).getIs_connected().equals("1")){

            holder.connect_now_txt.setText("Connected");
        }
        else  if (premiunMatchesListData.get(position).getIs_connected().equals("0") || premiunMatchesListData.get(position).getIs_connected().equals("3")) {

            holder.connect_now_txt.setText("Connect Now");


            holder.connect_now_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connectApi(premiunMatchesListData.get(position).getId(),String.valueOf(user.getUser_id()),view,holder);

                }
            });

        }else if(premiunMatchesListData.get(position).getIs_connected().equals("2")){

            holder.connect_now_txt.setText("Request Sent");

        }
        /*------end if connect show contact bottom --------*/


    }




    @Override
    public int getItemCount() {

        if(premiunMatchesListData.size()>5){
            size=5;
        }else {
            size=premiunMatchesListData.size();
        }
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,age_height_mothertoung,caste,address,connect_now_txt;
        ImageView img,img2;
        LinearLayout mathcesLL,connect_now_btn,default_Avtar_LL;
        ProgressBar connect_btn_pb;
        FrameLayout topFL;

        public ViewHolder(View itemView) {
            super(itemView);
            default_Avtar_LL =  itemView.findViewById(R.id.default_Avtar_LL);

            topFL =  itemView.findViewById(R.id.topFL);
            mathcesLL =  itemView.findViewById(R.id.mathcesLL);
            img =  itemView.findViewById(R.id.img);
            name =  itemView.findViewById(R.id.name);
            age_height_mothertoung =  itemView.findViewById(R.id.age_height_mothertoung);
            caste =  itemView.findViewById(R.id.caste);
            address =  itemView.findViewById(R.id.address);
            connect_now_btn =  itemView.findViewById(R.id.connect_now_btn);
            connect_btn_pb =  itemView.findViewById(R.id.connect_btn_pb);
            connect_now_txt =  itemView.findViewById(R.id.connect_now_txt);
            img2 =  itemView.findViewById(R.id.img2);

        }
    }


    public  void  connectApi(String user_id,String login_user_id,View view, ViewHolder holder){
        holder.connect_now_txt.setText("");
        holder.connect_btn_pb.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.connectNow(user_id,login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                connect_response = response.body();

                holder.connect_btn_pb.setVisibility(View.GONE);

//                System.out.println("connect_response==========" + new Gson().toJson(connect_response));

                if (response.isSuccessful()) {


                    if(connect_response.getResid().equals("200")){
                        holder.connect_now_txt.setText("Request Sent");

                        Snackbar snackbar = Snackbar
                                .make(view, "Request send successfully.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else{
                        Toast.makeText(context, connect_response.getResMsg(),Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 my connect_response******" + t.toString());
                holder.connect_btn_pb.setVisibility(View.GONE);

                if(!isNetworkAvailable(context)){
//                    Toast.makeText(context, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(context, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
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


    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }


}
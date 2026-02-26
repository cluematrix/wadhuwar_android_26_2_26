package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.NewMatchesList;
import com.WadhuWarProject.WadhuWar.model.PremiunMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
//import com.github.florent37.shapeofview.shapes.BubbleView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllNewMatchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<NewMatchesList.NewmatchesListData> newmatchesListData ;

    InsertResponse connect_response;
    UserData user;

    static boolean isNetworkAvailable;

    Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public SeeAllNewMatchesAdapter(Context context ) {
        this.context = context;
        this.newmatchesListData = new ArrayList<>();
    }

    public ArrayList<NewMatchesList.NewmatchesListData> getNewmatchesListData() {
        return newmatchesListData;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new SeeAllNewMatchesAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_mathces_fragment, parent, false);
        viewHolder = new SeeAllNewMatchesAdapter.ProductViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;

                user = SharedPrefManager.getInstance(context).getUser();
                holder.connect_btn_pb.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);


                if(newmatchesListData!=null && newmatchesListData.size()!=0) {


                    /*========connect block=====*/
                    System.out.println("is connected check ========== " + newmatchesListData.get(position).getIs_connected());

                    if (newmatchesListData.get(position).getIs_connected().equals("1")) {

                        holder.like_profile_txt.setText("");
                        holder.connect_btn_txt.setText("Connected");


                    } else  if (newmatchesListData.get(position).getIs_connected().equals("0") || newmatchesListData.get(position).getIs_connected().equals("3")) {
                        holder.like_profile_txt.setText("Like this Profile?");
                        holder.connect_btn_txt.setText("Connect Now");


                            holder.connecy_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    connectApi(newmatchesListData.get(position).getId(),String.valueOf(user.getUser_id()),view,holder);

                                }
                            });





                    }else if(newmatchesListData.get(position).getIs_connected().equals("2")){
                        holder.like_profile_txt.setText("");
                        holder.connect_btn_txt.setText("Request Sent");
                    }
                    /*======end connect block=======*/



                    holder.name.setText(newmatchesListData.get(position).getName());


                    if(!newmatchesListData.get(position).getImg_count().equals("0")){
                        holder.imgLL.setVisibility(View.VISIBLE);
                        holder.img_txt.setText(String.valueOf(newmatchesListData.get(position).getImg_count()));
                    }else{
                        holder.imgLL.setVisibility(View.GONE);
                    }

                    if (!newmatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                            !newmatchesListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(String.format("%syrs, %s,", newmatchesListData.get(position).getAge(), newmatchesListData.get(position).getHeight()));

                    } else if (!newmatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                            newmatchesListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(newmatchesListData.get(position).getAge()+" yrs");

                    } else if (newmatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                            !newmatchesListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(newmatchesListData.get(position).getHeight());

                    } else {
                        holder.age_height.setText(" ");

                    }

                    if (!newmatchesListData.get(position).getOccupation().contentEquals("Not Specified")) {
                        holder.job.setText(newmatchesListData.get(position).getOccupation());
                        holder.job_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.job.setText("");
                        holder.job_dot.setVisibility(View.GONE);

                    }


                    if (!newmatchesListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !newmatchesListData.get(position).getCaste().contentEquals("Not Specified")) {

                        if( !newmatchesListData.get(position).getSubcaste().contentEquals("Not Specified")){
                            holder.lang.setText(String.format("%s,%s", newmatchesListData.get(position).getMothertounge(), newmatchesListData.get(position).getCaste()+
                                    "("+ newmatchesListData.get(position).getSubcaste() +")"));
                        }else{
                            holder.lang.setText(String.format("%s,%s", newmatchesListData.get(position).getMothertounge(), newmatchesListData.get(position).getCaste()));
                        }

                    } else if (!newmatchesListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            newmatchesListData.get(position).getCaste().contentEquals("Not Specified")) {
                        holder.lang.setText(newmatchesListData.get(position).getMothertounge());

                    } else if (newmatchesListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !newmatchesListData.get(position).getCaste().contentEquals("Not Specified")) {


                        if( !newmatchesListData.get(position).getSubcaste().contentEquals("Not Specified")){
                            holder.lang.setText(newmatchesListData.get(position).getCaste()+"("+ newmatchesListData.get(position).getSubcaste() +")");
                        }else{
                            holder.lang.setText(newmatchesListData.get(position).getCaste());
                        }



                    } else {
                        holder.lang.setText(" ");

                    }


                    if (!newmatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            !newmatchesListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(String.format("%s, %s", newmatchesListData.get(position).getDistrict(), newmatchesListData.get(position).getState()));
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else if (!newmatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            newmatchesListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(newmatchesListData.get(position).getDistrict());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else if (newmatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            !newmatchesListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(newmatchesListData.get(position).getState());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.address.setText(" ");
                        holder.address_dot.setVisibility(View.GONE);

                    }



                    if (newmatchesListData.get(position).getImage() != null) {
                        if (newmatchesListData.get(position).getImage().equals("")) {
                            holder.img.setVisibility(View.GONE);
                            holder.default_Avtar_LL.setVisibility(View.VISIBLE);


                            if(newmatchesListData.get(position).getGender().equals("Female")){
                                holder.img2.setImageResource(R.drawable.female_avtar);
                            }else {
                                holder.img2.setImageResource(R.drawable.male_avtar);
                            }

                        } else {

                            holder.default_Avtar_LL.setVisibility(View.GONE);
                            holder.img.setVisibility(View.VISIBLE);
                            Glide.with(context.getApplicationContext()).load(newmatchesListData.get(position).getImage()).into(holder.img);

                        }
                    } else {

                        holder.img.setVisibility(View.GONE);
                        holder.default_Avtar_LL.setVisibility(View.VISIBLE);

                        if(newmatchesListData.get(position).getGender().equals("Female")){
                            holder.img2.setImageResource(R.drawable.female_avtar);
                        }else {
                            holder.img2.setImageResource(R.drawable.male_avtar);
                        }
                    }



                    if (newmatchesListData.get(position).getChkonline().equals("1")) {
                        holder.onlineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.onlineLL.setVisibility(View.GONE);
                    }

                    if (newmatchesListData.get(position).getPremium().equals("1")) {
                        holder.premiumLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.premiumLL.setVisibility(View.GONE);
                    }

                    if (newmatchesListData.get(position).getJust_joined().equals("1")) {
                        holder.just_joineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.just_joineLL.setVisibility(View.GONE);
                    }

                    if (newmatchesListData.get(position).getAccount_verify().equals("1")) {
                        holder.verifiedLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.verifiedLL.setVisibility(View.GONE);
                    }

                    holder.LL1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(context, DetailMatchesActivity.class);
                            i.putExtra("userid", newmatchesListData.get(position).getId());
                            i.putExtra("profile_list", new Gson().toJson(newmatchesListData)); // Pass list as JSON
                            i.putExtra("position", position);
                            context.startActivity(i);
                        }
                    });
                }

                else{
//                    holder.itemView.setVisibility(View.GONE);
                }
                break;

            case LOADING:
//                Do nothing

                final LoadingVH holder2 = (LoadingVH) holder1;

                holder2.loadmore_progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#AB0A0A0A"),android.graphics.PorterDuff.Mode.MULTIPLY);

                break;
        }

    }


    @Override
    public int getItemCount () {


        if (newmatchesListData != null) {
            if (newmatchesListData.size() == 0) {
                return 1;
            } else {
                return newmatchesListData.size();
            }
        } else {
            return 0;
        }


    }

    @Override
    public int getItemViewType(int position) {
        return (position == newmatchesListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

      /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(NewMatchesList.NewmatchesListData mc) {
        newmatchesListData.add(mc);
        notifyItemInserted(newmatchesListData.size() - 1);
    }


    public void addAll(ArrayList<NewMatchesList.NewmatchesListData> mcList) {
        for (NewMatchesList.NewmatchesListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(NewMatchesList.NewmatchesListData p) {
        int position = newmatchesListData.indexOf(p);
        if (position > -1) {
            newmatchesListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;

        add(new NewMatchesList.NewmatchesListData());
    }



    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = newmatchesListData.size() - 1;
        NewMatchesList.NewmatchesListData item = getItem(position);

        if (item != null) {
            newmatchesListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public NewMatchesList.NewmatchesListData getItem(int position) {
        return newmatchesListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */



    protected  class ProductViewHolder extends RecyclerView.ViewHolder {

        FrameLayout LL,LL1;
        ProgressBar connect_btn_pb;
        LinearLayout connecy_btn;

        TextView name,online,you_,age_height,job,lang,address,img_txt,like_profile_txt,connect_btn_txt;
        LinearLayout onlineLL,just_joineLL,verifiedLL,premiumLL,imgLL,default_Avtar_LL;
        ImageView img,img2;
        CardView card_view;
        FrameLayout matches_fl;
        CardView bubble_v;
        ImageView job_dot,address_dot;




        //            CardView card;
        public ProductViewHolder(View itemView) {
            super(itemView);

            connecy_btn =  itemView.findViewById(R.id.connecy_btn);
            LL1 =  itemView.findViewById(R.id.LL1);
            LL =  itemView.findViewById(R.id.LL);
            connect_btn_pb =  itemView.findViewById(R.id.connect_btn_pb);

            connect_btn_txt =  itemView.findViewById(R.id.connect_btn_txt);
            like_profile_txt =  itemView.findViewById(R.id.like_profile_txt);
            card_view =  itemView.findViewById(R.id.card_view);
            name =  itemView.findViewById(R.id.name);
            online =  itemView.findViewById(R.id.online);
            you_ =  itemView.findViewById(R.id.you_);
            age_height =  itemView.findViewById(R.id.age_height);
            job =  itemView.findViewById(R.id.job);
            lang =  itemView.findViewById(R.id.lang);
            address =  itemView.findViewById(R.id.address);
            connecy_btn =  itemView.findViewById(R.id.connecy_btn);
            img =  itemView.findViewById(R.id.img);
            onlineLL =  itemView.findViewById(R.id.onlineLL);
            matches_fl =  itemView.findViewById(R.id.matches_fl);
            bubble_v =  itemView.findViewById(R.id.bubble_v);
            just_joineLL =  itemView.findViewById(R.id.just_joineLL);
            verifiedLL =  itemView.findViewById(R.id.verifiedLL);
            premiumLL =  itemView.findViewById(R.id.premiumLL);
            imgLL =  itemView.findViewById(R.id.imgLL);
            img_txt =  itemView.findViewById(R.id.img_txt);
            img2 =  itemView.findViewById(R.id.img2);
            default_Avtar_LL =  itemView.findViewById(R.id.default_Avtar_LL);
            job_dot =  itemView.findViewById(R.id.job_dot);
            address_dot =  itemView.findViewById(R.id.address_dot);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar loadmore_progress;
        public LoadingVH(View itemView) {
            super(itemView);


            loadmore_progress = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
        }
    }

    public  void  connectApi(String user_id,String login_user_id,View view, ProductViewHolder holder){
        holder.connect_btn_txt.setText("");
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
                        holder.connect_btn_txt.setText("Request Sent");

                        holder.connecy_btn.setClickable(false);

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
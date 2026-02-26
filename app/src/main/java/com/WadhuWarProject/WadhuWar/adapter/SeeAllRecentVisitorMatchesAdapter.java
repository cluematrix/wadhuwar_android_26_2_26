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
import com.WadhuWarProject.WadhuWar.model.RecentVisitorMatchesList;
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

public class
SeeAllRecentVisitorMatchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    InsertResponse connect_response;
    UserData user;
    static boolean isNetworkAvailable;


    ArrayList<RecentVisitorMatchesList.RecentVisitorsListmatchesListData> recentVisitorsListmatchesListData;
    Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public SeeAllRecentVisitorMatchesAdapter(Context context) {
        this.context = context;
        this.recentVisitorsListmatchesListData  = new ArrayList<>();
    }

    public ArrayList<RecentVisitorMatchesList.RecentVisitorsListmatchesListData> getRecentVisitorsListmatchesListData() {
        return recentVisitorsListmatchesListData;
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
                viewHolder = new SeeAllRecentVisitorMatchesAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_mathces_fragment, parent, false);
        viewHolder = new SeeAllRecentVisitorMatchesAdapter.ProductViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;

                user = SharedPrefManager.getInstance(context).getUser();
                holder.connect_btn_pb.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

                if(recentVisitorsListmatchesListData!=null && recentVisitorsListmatchesListData.size()!=0) {

                    /*========connect block=====*/
                    System.out.println("is connected check ========== " + recentVisitorsListmatchesListData.get(position).getIs_connected());

                    if (recentVisitorsListmatchesListData.get(position).getIs_connected().equals("1")) {

                        holder.like_profile_txt.setText("");
                        holder.connect_btn_txt.setText("Connected");

                    } else  if (recentVisitorsListmatchesListData.get(position).getIs_connected().equals("0") || recentVisitorsListmatchesListData.get(position).getIs_connected().equals("3")) {
                        holder.like_profile_txt.setText("Like this Profile?");
                        holder.connect_btn_txt.setText("Connect Now");

                        holder.connecy_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                connectApi(recentVisitorsListmatchesListData.get(position).getId(),String.valueOf(user.getUser_id()),view,holder);

                            }
                        });



                    }else if(recentVisitorsListmatchesListData.get(position).getIs_connected().equals("2")){
                        holder.like_profile_txt.setText("");
                        holder.connect_btn_txt.setText("Request Sent");
                    }
                    /*======end connect block=======*/



                    if(!recentVisitorsListmatchesListData.get(position).getImg_count().equals("0")){
                        holder.imgLL.setVisibility(View.VISIBLE);
                        holder.img_txt.setText(String.valueOf(recentVisitorsListmatchesListData.get(position).getImg_count()));
                    }else{
                        holder.imgLL.setVisibility(View.GONE);
                    }


                    holder.name.setText(recentVisitorsListmatchesListData.get(position).getName());


                    if (!recentVisitorsListmatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                            !recentVisitorsListmatchesListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(String.format("%syrs, %s,", recentVisitorsListmatchesListData.get(position).getAge(), recentVisitorsListmatchesListData.get(position).getHeight()));

                    } else if (!recentVisitorsListmatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                            recentVisitorsListmatchesListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(recentVisitorsListmatchesListData.get(position).getAge()+" yrs");

                    } else if (recentVisitorsListmatchesListData.get(position).getAge().contentEquals("Not Specified") &&
                            !recentVisitorsListmatchesListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(recentVisitorsListmatchesListData.get(position).getHeight());

                    } else {
                        holder.age_height.setText(" ");

                    }

                    if (!recentVisitorsListmatchesListData.get(position).getOccupation().contentEquals("Not Specified")) {
                        holder.job.setText(recentVisitorsListmatchesListData.get(position).getOccupation());
                        holder.job_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.job.setText("");
                        holder.job_dot.setVisibility(View.GONE);

                    }


                    if (!recentVisitorsListmatchesListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !recentVisitorsListmatchesListData.get(position).getCaste().contentEquals("Not Specified")) {


                        if( !recentVisitorsListmatchesListData.get(position).getSubcaste().contentEquals("Not Specified")){
                            holder.lang.setText(String.format("%s,%s", recentVisitorsListmatchesListData.get(position).getMothertounge(), recentVisitorsListmatchesListData.get(position).getCaste()+
                                    "("+ recentVisitorsListmatchesListData.get(position).getSubcaste() +")"));
                        }else{
                            holder.lang.setText(String.format("%s,%s", recentVisitorsListmatchesListData.get(position).getMothertounge(), recentVisitorsListmatchesListData.get(position).getCaste()));
                        }

                    } else if (!recentVisitorsListmatchesListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            recentVisitorsListmatchesListData.get(position).getCaste().contentEquals("Not Specified")) {
                        holder.lang.setText(recentVisitorsListmatchesListData.get(position).getMothertounge());

                    } else if (recentVisitorsListmatchesListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !recentVisitorsListmatchesListData.get(position).getCaste().contentEquals("Not Specified")) {

                        if( !recentVisitorsListmatchesListData.get(position).getSubcaste().contentEquals("Not Specified")){
                            holder.lang.setText(recentVisitorsListmatchesListData.get(position).getCaste()+"("+ recentVisitorsListmatchesListData.get(position).getSubcaste() +")");
                        }else{
                            holder.lang.setText(recentVisitorsListmatchesListData.get(position).getCaste());
                        }
                    } else {
                        holder.lang.setText(" ");

                    }

                    System.out.println("premiunMatchesListData.get(position).getDistrict()---" + recentVisitorsListmatchesListData.get(position).getDistrict());
                    System.out.println("premiunMatchesListData.get(position).getState()---" + recentVisitorsListmatchesListData.get(position).getState());

                    if (!recentVisitorsListmatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            !recentVisitorsListmatchesListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(String.format("%s, %s", recentVisitorsListmatchesListData.get(position).getDistrict(), recentVisitorsListmatchesListData.get(position).getState()));
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else if (!recentVisitorsListmatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            recentVisitorsListmatchesListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(recentVisitorsListmatchesListData.get(position).getDistrict());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else if (recentVisitorsListmatchesListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            !recentVisitorsListmatchesListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(recentVisitorsListmatchesListData.get(position).getState());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.address.setText(" ");
                        holder.address_dot.setVisibility(View.GONE);

                    }


                    if (recentVisitorsListmatchesListData.get(position).getImage() != null) {
                        if (recentVisitorsListmatchesListData.get(position).getImage().equals("")) {
                            holder.img.setVisibility(View.GONE);
                            holder.default_Avtar_LL.setVisibility(View.VISIBLE);


                            if(recentVisitorsListmatchesListData.get(position).getGender().equals("Female")){
                                holder.img2.setImageResource(R.drawable.female_avtar);
                            }else {
                                holder.img2.setImageResource(R.drawable.male_avtar);
                            }

                        } else {

                            holder.default_Avtar_LL.setVisibility(View.GONE);
                            holder.img.setVisibility(View.VISIBLE);
                            Glide.with(context.getApplicationContext()).load(recentVisitorsListmatchesListData.get(position).getImage()).into(holder.img);

                        }
                    } else {

                        holder.img.setVisibility(View.GONE);
                        holder.default_Avtar_LL.setVisibility(View.VISIBLE);

                        if(recentVisitorsListmatchesListData.get(position).getGender().equals("Female")){
                            holder.img2.setImageResource(R.drawable.female_avtar);
                        }else {
                            holder.img2.setImageResource(R.drawable.male_avtar);
                        }
                    }



                    if (recentVisitorsListmatchesListData.get(position).getChkonline().equals("1")) {
                        holder.onlineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.onlineLL.setVisibility(View.GONE);
                    }

                    if (recentVisitorsListmatchesListData.get(position).getPremium().equals("1")) {
                        holder.premiumLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.premiumLL.setVisibility(View.GONE);
                    }

                    if (recentVisitorsListmatchesListData.get(position).getJust_joined().equals("1")) {
                        holder.just_joineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.just_joineLL.setVisibility(View.GONE);
                    }

                    if (recentVisitorsListmatchesListData.get(position).getAccount_verify().equals("1")) {
                        holder.verifiedLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.verifiedLL.setVisibility(View.GONE);
                    }

                    holder.LL1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(context, DetailMatchesActivity.class);
                            i.putExtra("userid", recentVisitorsListmatchesListData.get(position).getId());
                            i.putExtra("profile_list", new Gson().toJson(recentVisitorsListmatchesListData)); // Pass list as JSON
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


        if (recentVisitorsListmatchesListData != null) {
            if (recentVisitorsListmatchesListData.size() == 0) {
                return 1;
            } else {
                return recentVisitorsListmatchesListData.size();
            }
        } else {
            return 0;
        }


    }

    @Override
    public int getItemViewType(int position) {
        return (position == recentVisitorsListmatchesListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }






      /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(RecentVisitorMatchesList.RecentVisitorsListmatchesListData mc) {
        recentVisitorsListmatchesListData.add(mc);
        notifyItemInserted(recentVisitorsListmatchesListData.size() - 1);
    }


    public void addAll(ArrayList<RecentVisitorMatchesList.RecentVisitorsListmatchesListData> mcList) {
        for (RecentVisitorMatchesList.RecentVisitorsListmatchesListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(RecentVisitorMatchesList.RecentVisitorsListmatchesListData p) {
        int position = recentVisitorsListmatchesListData.indexOf(p);
        if (position > -1) {
            recentVisitorsListmatchesListData.remove(position);
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

        add(new RecentVisitorMatchesList.RecentVisitorsListmatchesListData());
    }



    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = recentVisitorsListmatchesListData.size() - 1;
        RecentVisitorMatchesList.RecentVisitorsListmatchesListData item = getItem(position);

        if (item != null) {
            recentVisitorsListmatchesListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public RecentVisitorMatchesList.RecentVisitorsListmatchesListData getItem(int position) {
        return recentVisitorsListmatchesListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */



    protected  class ProductViewHolder extends RecyclerView.ViewHolder {

        FrameLayout LL,LL1;
        ProgressBar connect_btn_pb;

        TextView name,online,you_,age_height,job,lang,address,img_txt,like_profile_txt,connect_btn_txt;
        LinearLayout connecy_btn,onlineLL,premiumLL,just_joineLL,verifiedLL,imgLL,default_Avtar_LL;
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
            premiumLL =  itemView.findViewById(R.id.premiumLL);
            just_joineLL =  itemView.findViewById(R.id.just_joineLL);
            verifiedLL =  itemView.findViewById(R.id.verifiedLL);
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
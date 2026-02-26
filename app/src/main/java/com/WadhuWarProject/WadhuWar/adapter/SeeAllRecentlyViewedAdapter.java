package com.WadhuWarProject.WadhuWar.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.ReasonForReportActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.CommonResponse;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.PremiunMatchesList;
import com.WadhuWarProject.WadhuWar.model.TabRecentlyViewedList;
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

public class SeeAllRecentlyViewedAdapter   extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    InsertResponse connect_response;
    UserData user;
    static boolean isNetworkAvailable;
    String people_userid;
    ArrayList<TabRecentlyViewedList.TabRecentlyViewedListData> tabRecentlyViewedListData ;
    Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public SeeAllRecentlyViewedAdapter(Context context ){
        this.context = context;
        this.tabRecentlyViewedListData = new ArrayList<>();
    }

    public ArrayList<TabRecentlyViewedList.TabRecentlyViewedListData> getTabRecentlyViewedListData() {
        return tabRecentlyViewedListData;
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
                viewHolder = new SeeAllRecentlyViewedAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_mathces_fragment, parent, false);
        viewHolder = new SeeAllRecentlyViewedAdapter.ProductViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;

                holder.bubble_v.setVisibility(View.GONE);

                user = SharedPrefManager.getInstance(context).getUser();
                holder.connect_btn_pb.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                people_userid=tabRecentlyViewedListData.get(position).getId();

                if(tabRecentlyViewedListData!=null && tabRecentlyViewedListData.size()!=0) {

                    /*========connect block=====*/
//                    System.out.println("is connected check ========== " + tabRecentlyViewedListData.get(position).getIs_connected());

                    if (tabRecentlyViewedListData.get(position).getIs_connected().equals("1")) {

                        holder.like_profile_txt.setText("");
                        holder.connect_btn_txt.setText("Connected");


                    } else  if (tabRecentlyViewedListData.get(position).getIs_connected().equals("0") || tabRecentlyViewedListData.get(position).getIs_connected().equals("3")) {
                        holder.like_profile_txt.setText("Like this Profile?");
                        holder.connect_btn_txt.setText("Connect Now");

                        holder.connecy_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                connectApi(tabRecentlyViewedListData.get(position).getId(),String.valueOf(user.getUser_id()),view,holder);
                            }
                        });

                    }else if(tabRecentlyViewedListData.get(position).getIs_connected().equals("2")){
                        holder.like_profile_txt.setText("");
                        holder.connect_btn_txt.setText("Request Sent");
                    }
                    /*======end connect block=======*/



                    if(!tabRecentlyViewedListData.get(position).getImg_count().equals("0")){
                        holder.imgLL.setVisibility(View.VISIBLE);
                        holder.img_txt.setText(String.valueOf(tabRecentlyViewedListData.get(position).getImg_count()));
                    }else{
                        holder.imgLL.setVisibility(View.GONE);
                    }



                    holder.name.setText(tabRecentlyViewedListData.get(position).getName());


                    if (!tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                            !tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(String.format("%syrs, %s,", tabRecentlyViewedListData.get(position).getAge(), tabRecentlyViewedListData.get(position).getHeight()));

                    } else if (!tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                            tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(tabRecentlyViewedListData.get(position).getAge()+" yrs");

                    } else if (tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                            !tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(tabRecentlyViewedListData.get(position).getHeight());

                    } else {
                        holder.age_height.setText(" ");

                    }

                    if (!tabRecentlyViewedListData.get(position).getOccupation().contentEquals("Not Specified")) {
                        holder.job.setText(tabRecentlyViewedListData.get(position).getOccupation());
                        holder.job_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.job.setText("");
                        holder.job_dot.setVisibility(View.GONE);

                    }


                    if (!tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !tabRecentlyViewedListData.get(position).getCaste().contentEquals("Not Specified")) {

                        if( !tabRecentlyViewedListData.get(position).getSubcaste().contentEquals("Not Specified")){
                            holder.lang.setText(String.format("%s,%s", tabRecentlyViewedListData.get(position).getMothertounge(), tabRecentlyViewedListData.get(position).getCaste()+
                                    "("+ tabRecentlyViewedListData.get(position).getSubcaste() +")"));
                        }else{
                            holder.lang.setText(String.format("%s,%s", tabRecentlyViewedListData.get(position).getMothertounge(), tabRecentlyViewedListData.get(position).getCaste()));
                        }



                    } else if (!tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            tabRecentlyViewedListData.get(position).getCaste().contentEquals("Not Specified")) {
                        holder.lang.setText(tabRecentlyViewedListData.get(position).getMothertounge());

                    } else if (tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !tabRecentlyViewedListData.get(position).getCaste().contentEquals("Not Specified")) {

                        if( !tabRecentlyViewedListData.get(position).getSubcaste().contentEquals("Not Specified")){
                            holder.lang.setText(tabRecentlyViewedListData.get(position).getCaste()+"("+ tabRecentlyViewedListData.get(position).getSubcaste() +")");
                        }else{
                            holder.lang.setText(tabRecentlyViewedListData.get(position).getCaste());
                        }
                    } else {
                        holder.lang.setText(" ");

                    }


                    if (!tabRecentlyViewedListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            !tabRecentlyViewedListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(String.format("%s, %s", tabRecentlyViewedListData.get(position).getDistrict(), tabRecentlyViewedListData.get(position).getState()));
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else if (!tabRecentlyViewedListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            tabRecentlyViewedListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(tabRecentlyViewedListData.get(position).getDistrict());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else if (tabRecentlyViewedListData.get(position).getDistrict().contentEquals("Not Specified") &&
                            !tabRecentlyViewedListData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(tabRecentlyViewedListData.get(position).getState());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.address.setText(" ");
                        holder.address_dot.setVisibility(View.GONE);

                    }


                    if (tabRecentlyViewedListData.get(position).getImage() != null) {
                        if (tabRecentlyViewedListData.get(position).getImage().equals("")) {
                            holder.img.setVisibility(View.GONE);
                            holder.default_Avtar_LL.setVisibility(View.VISIBLE);


                            if(tabRecentlyViewedListData.get(position).getGender().equals("Female")){
                                holder.img2.setImageResource(R.drawable.female_avtar);
                            }else {
                                holder.img2.setImageResource(R.drawable.male_avtar);
                            }

                        } else {

                            holder.default_Avtar_LL.setVisibility(View.GONE);
                            holder.img.setVisibility(View.VISIBLE);
                            Glide.with(context.getApplicationContext()).load(tabRecentlyViewedListData.get(position).getImage()).into(holder.img);

                        }
                    } else {

                        holder.img.setVisibility(View.GONE);
                        holder.default_Avtar_LL.setVisibility(View.VISIBLE);

                        if(tabRecentlyViewedListData.get(position).getGender().equals("Female")){
                            holder.img2.setImageResource(R.drawable.female_avtar);
                        }else {
                            holder.img2.setImageResource(R.drawable.male_avtar);
                        }
                    }


                    if (tabRecentlyViewedListData.get(position).getChkonline().equals("1")) {
                        holder.onlineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.onlineLL.setVisibility(View.GONE);
                    }

                    if (tabRecentlyViewedListData.get(position).getPremium().equals("1")) {
                        holder.premiumLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.premiumLL.setVisibility(View.GONE);
                    }

                    if (tabRecentlyViewedListData.get(position).getJust_joined().equals("1")) {
                        holder.just_joineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.just_joineLL.setVisibility(View.GONE);
                    }
                    if (tabRecentlyViewedListData.get(position).getAccount_verify().equals("1")) {
                        holder.verifiedLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.verifiedLL.setVisibility(View.GONE);
                    }

                    holder.LL1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(context, DetailMatchesActivity.class);
                            i.putExtra("userid", tabRecentlyViewedListData.get(position).getId());
                            i.putExtra("profile_list", new Gson().toJson(tabRecentlyViewedListData)); // Pass list as JSON
                            i.putExtra("position", position);
                            context.startActivity(i);
                        }
                    });

                    holder.btn_block_report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            PopupMenu popup = new PopupMenu(context, view);
                            MenuInflater inflater = popup.getMenuInflater();
                            inflater.inflate(R.menu.profile_block_reject, popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    if (item.getItemId() == R.id.block_profile) {

                                        showConfirmationDialogBoxBlock();
                                    }

                                    if (item.getItemId() == R.id.report_profile) {
                                        Intent i = new Intent(context, ReasonForReportActivity.class);
                                        i.putExtra("userid", people_userid);
                                        i.putExtra("Fname", tabRecentlyViewedListData.get(position).getName());
                                        i.putExtra("isCallFromComment", false);
                                        context.startActivity(i);
                                    }
                                    return false;
                                }
                            });
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


        if (tabRecentlyViewedListData != null) {
            if (tabRecentlyViewedListData.size() == 0) {
                return 1;
            } else {
                return tabRecentlyViewedListData.size();
            }
        } else {
            return 0;
        }


    }

    @Override
    public int getItemViewType(int position) {
        return (position == tabRecentlyViewedListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(TabRecentlyViewedList.TabRecentlyViewedListData mc) {
        tabRecentlyViewedListData.add(mc);
        notifyItemInserted(tabRecentlyViewedListData.size() - 1);
    }


    public void addAll(ArrayList<TabRecentlyViewedList.TabRecentlyViewedListData> mcList) {
        for (TabRecentlyViewedList.TabRecentlyViewedListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(TabRecentlyViewedList.TabRecentlyViewedListData p) {
        int position = tabRecentlyViewedListData.indexOf(p);
        if (position > -1) {
            tabRecentlyViewedListData.remove(position);
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

        add(new TabRecentlyViewedList.TabRecentlyViewedListData());
    }



    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = tabRecentlyViewedListData.size() - 1;
        TabRecentlyViewedList.TabRecentlyViewedListData item = getItem(position);

        if (item != null) {
            tabRecentlyViewedListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TabRecentlyViewedList.TabRecentlyViewedListData getItem(int position) {
        return tabRecentlyViewedListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */



    protected  class ProductViewHolder extends RecyclerView.ViewHolder {

        FrameLayout LL,LL1;
        ProgressBar connect_btn_pb;


        TextView name,online,you_,age_height,job,lang,address,img_txt,like_profile_txt,connect_btn_txt;
        LinearLayout connecy_btn,onlineLL,premiumLL,just_joineLL,verifiedLL,btn_block_report,imgLL,default_Avtar_LL;
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
            btn_block_report =  itemView.findViewById(R.id.btn_block_report);


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



    private void showConfirmationDialogBoxBlock() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the message show for the Alert time
        builder.setMessage("Blocked Member will not able to view your Profile or contact you on Teli Samaj Wadhuwar");
        builder.setTitle("Are you sure you want to Block this profile?");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
            blockUser(dialog);
        });

        builder.setNegativeButton("CANCEL", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void blockUser(DialogInterface dialog) {
        Api apiService = RetrofitClient.getApiService();
        Call<CommonResponse> commonResponseCall = apiService.blockAccount(people_userid, String.valueOf(user.getUser_id()));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (commonResponse.getResid() == 200) {
                        // finish();
                        Toast.makeText(context, "Block this user", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                System.out.println("err UpdateResponse******" + t.toString());
                if (!isNetworkAvailable(context)) {
//                    Toast.makeText(context, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(context, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
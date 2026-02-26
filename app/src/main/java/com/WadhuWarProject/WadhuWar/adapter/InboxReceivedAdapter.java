package com.WadhuWarProject.WadhuWar.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.AdvertiseDetailActivity;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesForSearchActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.RoundedImageView;
import com.WadhuWarProject.WadhuWar.model.AdvertiseList;
import com.WadhuWarProject.WadhuWar.model.Image;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MemberList;
import com.WadhuWarProject.WadhuWar.model.NewmatchesTabList;
import com.WadhuWarProject.WadhuWar.model.ShowTabList;
import com.bumptech.glide.Glide;
//import com.github.florent37.shapeofview.shapes.BubbleView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxReceivedAdapter extends   RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<MemberList.MemberListData> memberListData= new ArrayList<>();
    Context context;
    String login_user_id;
    InsertResponse rejectResponse;
    InsertResponse acceptResponse;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public InboxReceivedAdapter(Context context, String login_user_id ) {
        this.context = context;
        this.login_user_id = login_user_id;
        this.memberListData =  new ArrayList<>();


    }

    public   ArrayList<MemberList.MemberListData> getMemberListData() {
        return memberListData;
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
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_inbox_received, parent, false);
        viewHolder = new ProductViewHolder(v1);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;


                holder.progressBar_decl.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                holder.progressBar_accept.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

                if(memberListData!=null && memberListData.size()!=0) {

                    holder.name.setText(memberListData.get(position).getName());
                    holder.date.setText(memberListData.get(position).getDate());

                    if(memberListData.get(position).getImage()!=null) {
                        if (!memberListData.get(position).getImage().equals("")) {
                            Glide.with(context.getApplicationContext()).load(memberListData.get(position).getImage()).into(holder.img_profile);
                        }else{

                            if(memberListData.get(position).getGender().equals("Female")){
                                holder.img_profile.setImageResource(R.drawable.female_avtar);
                            }else {
                                holder.img_profile.setImageResource(R.drawable.male_avtar);
                            }

                        }
                    }else{
                        if(memberListData.get(position).getGender().equals("Female")){
                            holder.img_profile.setImageResource(R.drawable.female_avtar);
                        }else {
                            holder.img_profile.setImageResource(R.drawable.male_avtar);
                        }
                    }


                    if(!memberListData.get(position).getAge().equals("Not Specified") &&
                            !memberListData.get(position).getHeight().equals("Not Specified")  &&
                            !memberListData.get(position).getCaste().equals("Not Specified") ){

                        holder.t1.setText(memberListData.get(position).getAge()+" yrs, " + memberListData.get(position).getHeight() +
                                ", "+ memberListData.get(position).getCaste());

                    }else if(memberListData.get(position).getAge().equals("Not Specified") &&
                            !memberListData.get(position).getHeight().equals("Not Specified")  &&
                            !memberListData.get(position).getCaste().equals("Not Specified") ){

                        holder.t1.setText( memberListData.get(position).getHeight() +
                                ", "+ memberListData.get(position).getCaste());
                    }else if(!memberListData.get(position).getAge().equals("Not Specified") &&
                            memberListData.get(position).getHeight().equals("Not Specified")  &&
                            !memberListData.get(position).getCaste().equals("Not Specified") ){
                        holder.t1.setText(memberListData.get(position).getAge()+" yrs, "  +
                                ", "+ memberListData.get(position).getCaste());

                    }else if(!memberListData.get(position).getAge().equals("Not Specified") &&
                            !memberListData.get(position).getHeight().equals("Not Specified")  &&
                            memberListData.get(position).getCaste().equals("Not Specified")){
                        holder.t1.setText(memberListData.get(position).getAge()+" yrs, " + memberListData.get(position).getHeight() );

                    }else if(memberListData.get(position).getAge().equals("Not Specified") &&
                            memberListData.get(position).getHeight().equals("Not Specified")  &&
                            !memberListData.get(position).getCaste().equals("Not Specified")){
                        holder.t1.setText( memberListData.get(position).getCaste());

                    }else if(!memberListData.get(position).getAge().equals("Not Specified") &&
                            memberListData.get(position).getHeight().equals("Not Specified")  &&
                            memberListData.get(position).getCaste().equals("Not Specified") ){

                        holder.t1.setText(memberListData.get(position).getAge()+" yrs" );

                    }else if(memberListData.get(position).getAge().equals("Not Specified") &&
                            !memberListData.get(position).getHeight().equals("Not Specified")  &&
                            memberListData.get(position).getCaste().equals("Not Specified") ){

                        holder.t1.setText(memberListData.get(position).getHeight());

                    }else if(memberListData.get(position).getAge().equals("Not Specified") &&
                            memberListData.get(position).getHeight().equals("Not Specified")  &&
                            memberListData.get(position).getCaste().equals("Not Specified") ){
                        holder.t1.setVisibility(View.GONE);
                    }


                    /*note - add education */
                    if(!memberListData.get(position).getDistrict().equals("Not Specified") &&
                            !memberListData.get(position).getState().equals("Not Specified") ){
                        holder.t2.setText(memberListData.get(position).getDistrict()+", "+memberListData.get(position).getState() );

                    }else if(!memberListData.get(position).getDistrict().equals("Not Specified") &&
                            memberListData.get(position).getState().equals("Not Specified") ){
                        holder.t2.setText(memberListData.get(position).getDistrict());

                    }else if(memberListData.get(position).getDistrict().equals("Not Specified") &&
                            !memberListData.get(position).getState().equals("Not Specified") ){
                        holder.t2.setText(memberListData.get(position).getState() );

                    }else if(memberListData.get(position).getDistrict().equals("Not Specified") &&
                            memberListData.get(position).getState().equals("Not Specified") ){
                        holder.t2.setVisibility(View.GONE);
                    }


                    if(!memberListData.get(position).getOccupation().equals("Not Specified")){
                        holder.t3.setText(memberListData.get(position).getOccupation());
                    }else{
                        holder.t3.setVisibility(View.GONE);
                    }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(context, DetailMatchesForSearchActivity.class);
                            i.putExtra("userid",memberListData.get(position).getId());
                            context.startActivity(i);
                        }
                    });

                    holder.reject_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new AlertDialog.Builder(context)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setMessage("Are you sure want to Decline?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            rejectProfile(memberListData.get(position).getId(),login_user_id,position,holder);
                                        }

                                    })
                                    .setNegativeButton("No", null)
                                    .show();

                        }
                    });
                    holder.accept_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            acceptProfile(memberListData.get(position).getId(),login_user_id,position,holder);

                        }
                    });


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


        if (memberListData != null) {
            if (memberListData.size() == 0) {
                return 1;
            } else {
                return memberListData.size();
            }
        } else {
            return 0;
        }


    }



    @Override
    public int getItemViewType(int position) {
        return (position == memberListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



       /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(MemberList.MemberListData mc) {
        memberListData.add(mc);
        notifyItemInserted(memberListData.size() - 1);
    }


    public void addAll(ArrayList<MemberList.MemberListData> mcList) {
        for (MemberList.MemberListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(MemberList.MemberListData p) {
        int position = memberListData.indexOf(p);
        if (position > -1) {
            memberListData.remove(position);
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

        add(new MemberList.MemberListData());
    }




    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = memberListData.size() - 1;
        MemberList.MemberListData item = getItem(position);

        if (item != null) {
            memberListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public MemberList.MemberListData getItem(int position) {
        return memberListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    protected  class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2,t3,name,date;
        RoundedImageView img_profile;
        CardView cv;
        LinearLayout reject_btn;
        FrameLayout accept_btn;
        ImageView reject_img;
        RelativeLayout pb_accept,pb_decline;
        ProgressBar progressBar_decl,progressBar_accept;

        public ProductViewHolder(View itemView) {
            super(itemView);


            progressBar_accept =  itemView.findViewById(R.id.progressBar_accept);
            progressBar_decl =  itemView.findViewById(R.id.progressBar_decl);
            pb_decline =  itemView.findViewById(R.id.pb_decline);
            pb_accept =  itemView.findViewById(R.id.pb_accept);
            reject_img =  itemView.findViewById(R.id.reject_img);
            accept_btn =  itemView.findViewById(R.id.accept_btn);
            reject_btn =  itemView.findViewById(R.id.reject_btn);
            name =  itemView.findViewById(R.id.name);
            t1 =  itemView.findViewById(R.id.t1);
            t2 =  itemView.findViewById(R.id.t2);
            t3 =  itemView.findViewById(R.id.t3);
            img_profile =  itemView.findViewById(R.id.img_profile);
            cv =  itemView.findViewById(R.id.cv);
            date =  itemView.findViewById(R.id.date);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar loadmore_progress;
        public LoadingVH(View itemView) {
            super(itemView);


            loadmore_progress = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
        }
    }



    public  void  acceptProfile(String member_id, String login_user_id,int pos, ProductViewHolder holder){

        holder.pb_accept.setVisibility(View.VISIBLE);
        holder.accept_btn.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.requestAccepted(member_id,login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                acceptResponse = response.body();
                holder.pb_accept.setVisibility(View.GONE);
                holder.accept_btn.setVisibility(View.VISIBLE);

//                System.out.println("res acceptResponse******" + new Gson().toJson(acceptResponse));

                if (response.isSuccessful()) {

                    if(acceptResponse.getResid().equals("200")){
                        memberListData.remove(pos);
                        notifyDataSetChanged();
                        notifyItemRemoved(pos);
                        holder.itemView.setVisibility(View.GONE);

                        if(memberListData.size()>0){
                            notifyItemRangeChanged(pos,memberListData.size());
                        }

                        Toast.makeText(context,"Request Accept.",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context,acceptResponse.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                holder.pb_accept.setVisibility(View.GONE);
                holder.accept_btn.setVisibility(View.VISIBLE);
                System.out.println("msg1 error accept******" + t.toString());

            }
        });


    }


    public  void  rejectProfile(String member_id, String login_user_id,int pos, ProductViewHolder holder){

        holder.pb_decline.setVisibility(View.VISIBLE);
        holder.reject_img.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.requestRejected(member_id,login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                rejectResponse = response.body();
                holder.pb_decline.setVisibility(View.GONE);
                holder.reject_img.setVisibility(View.VISIBLE);

//                System.out.println("res reject******" + new Gson().toJson(rejectResponse));

                if (response.isSuccessful()) {

                    if(rejectResponse.getResid().equals("200")){
                        memberListData.remove(pos);
                        notifyDataSetChanged();

                        notifyItemRemoved(pos);
                        holder.itemView.setVisibility(View.GONE);


                        if(memberListData.size()>0){
                            notifyItemRangeChanged(pos,memberListData.size());
                        }

                        Toast.makeText(context,"Request Decline.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,rejectResponse.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                holder.pb_decline.setVisibility(View.GONE);
                holder.reject_img.setVisibility(View.VISIBLE);
                System.out.println("msg1 error reject******" + t.toString());

            }
        });


    }


}
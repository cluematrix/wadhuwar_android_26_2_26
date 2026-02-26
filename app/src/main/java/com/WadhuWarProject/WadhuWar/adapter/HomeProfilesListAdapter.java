package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.ProfileHomeActivity;
import com.WadhuWarProject.WadhuWar.extras.BlurTransformation;
import com.WadhuWarProject.WadhuWar.model.ProfileSlider;
import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
import com.bumptech.glide.Glide;
//import com.github.florent37.shapeofview.shapes.BubbleView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeProfilesListAdapter extends   RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<ProfileSlider.ProfileSliderData> profileSliderData ;
    Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public HomeProfilesListAdapter(Context context) {
        this.context = context;
        this.profileSliderData = new ArrayList<>();
    }


    public   ArrayList<ProfileSlider.ProfileSliderData> getProfileSliderData() {
        return profileSliderData;
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
                viewHolder = new HomeProfilesListAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_mathces_fragment, parent, false);
        viewHolder = new HomeProfilesListAdapter.ProductViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;

                System.out.println("1111111111111=============" + profileSliderData.size());

                if(profileSliderData!=null && profileSliderData.size()!=0) {
                    holder.itemView.setVisibility(View.VISIBLE);
                    /*========connect block=====*/
//                    System.out.println("is connected check ========== " + profileSliderData.get(position).getIs_connected());

                    if (profileSliderData.get(position).getIs_connected().equals("1")) {

                        holder.like_profile_txt.setText("");
                        holder.connect_btn_txt.setText("Connected");


                    } else  if (profileSliderData.get(position).getIs_connected().equals("0") || profileSliderData.get(position).getIs_connected().equals("3")) {
                        holder.like_profile_txt.setText("Like this Profile?");
                        holder.connect_btn_txt.setText("Connect Now");


                    }else if(profileSliderData.get(position).getIs_connected().equals("2")){
                        holder.like_profile_txt.setText("");
                        holder.connect_btn_txt.setText("Request Sent");
                    }
                    /*======end connect block=======*/


                    holder.name.setText(profileSliderData.get(position).getName());

                    if(!profileSliderData.get(position).getImg_count().equals("0")){
                        holder.imgLL.setVisibility(View.VISIBLE);
                        holder.img_txt.setText(String.valueOf(profileSliderData.get(position).getImg_count()));
                    }else{
                        holder.imgLL.setVisibility(View.GONE);
                    }

                    if (!profileSliderData.get(position).getAge().contentEquals("Not Specified") &&
                            !profileSliderData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(String.format("%syrs, %s,", profileSliderData.get(position).getAge(), profileSliderData.get(position).getHeight()));

                    } else if (!profileSliderData.get(position).getAge().contentEquals("Not Specified") &&
                            profileSliderData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(profileSliderData.get(position).getAge()+" yrs");

                    } else if (profileSliderData.get(position).getAge().contentEquals("Not Specified") &&
                            !profileSliderData.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(profileSliderData.get(position).getHeight());

                    } else {
                        holder.age_height.setText(" ");

                    }
                    if(profileSliderData.get(position).getOccupation()!=null) {
                        if (!profileSliderData.get(position).getOccupation().contentEquals("Not Specified")) {
                            holder.job_dot.setVisibility(View.VISIBLE);

                            holder.job.setText(profileSliderData.get(position).getOccupation());

                        } else {

                            holder.job.setText("");
                            holder.job_dot.setVisibility(View.GONE);

                        }
                    }
                    if (!profileSliderData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !profileSliderData.get(position).getCaste().contentEquals("Not Specified")) {

                        if( !profileSliderData.get(position).getSubcaste().contentEquals("Not Specified")){
                            holder.lang.setText(String.format("%s,%s", profileSliderData.get(position).getMothertounge(), profileSliderData.get(position).getCaste()+
                                    "("+ profileSliderData.get(position).getSubcaste() +")"));
                        }else{
                            holder.lang.setText(String.format("%s,%s", profileSliderData.get(position).getMothertounge(), profileSliderData.get(position).getCaste()));
                        }
                    } else if (!profileSliderData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            profileSliderData.get(position).getCaste().contentEquals("Not Specified")) {
                        holder.lang.setText(profileSliderData.get(position).getMothertounge());

                    } else if (profileSliderData.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !profileSliderData.get(position).getCaste().contentEquals("Not Specified")) {

                        if( !profileSliderData.get(position).getSubcaste().contentEquals("Not Specified")){
                            holder.lang.setText(profileSliderData.get(position).getCaste()+"("+ profileSliderData.get(position).getSubcaste() +")");
                        }else{
                            holder.lang.setText(profileSliderData.get(position).getCaste());
                        }


                    } else {
                        holder.lang.setText(" ");

                    }
                    System.out.println("premiunMatchesListData.get(position).getDistrict()---" + profileSliderData.get(position).getDistrict());
                    System.out.println("premiunMatchesListData.get(position).getState()---" + profileSliderData.get(position).getState());

                    if (!profileSliderData.get(position).getDistrict().contentEquals("Not Specified") &&
                            !profileSliderData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(String.format("%s, %s", profileSliderData.get(position).getDistrict(), profileSliderData.get(position).getState()));
                        holder.address_dot.setVisibility(View.VISIBLE);
                    } else if (!profileSliderData.get(position).getDistrict().contentEquals("Not Specified") &&
                            profileSliderData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(profileSliderData.get(position).getDistrict());
                        holder.address_dot.setVisibility(View.VISIBLE);
                    } else if (profileSliderData.get(position).getDistrict().contentEquals("Not Specified") &&
                            !profileSliderData.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(profileSliderData.get(position).getState());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.address.setText(" ");
                        holder.address_dot.setVisibility(View.GONE);

                    }



                    if (profileSliderData.get(position).getImage() != null) {
                        if (profileSliderData.get(position).getImage().equals("")) {
                            holder.img.setVisibility(View.GONE);
                            holder.default_Avtar_LL.setVisibility(View.VISIBLE);


                            if(profileSliderData.get(position).getGender().equals("Female")){
                                holder.img2.setImageResource(R.drawable.female_avtar);
                            }else {
                                holder.img2.setImageResource(R.drawable.male_avtar);
                            }

                        } else {

                            holder.default_Avtar_LL.setVisibility(View.GONE);
                            holder.img.setVisibility(View.VISIBLE);
                            Glide.with(context.getApplicationContext()).load(profileSliderData.get(position).getImage()).into(holder.img);

                        }
                    } else {

                        holder.img.setVisibility(View.GONE);
                        holder.default_Avtar_LL.setVisibility(View.VISIBLE);

                        if(profileSliderData.get(position).getGender().equals("Female")){
                            holder.img2.setImageResource(R.drawable.female_avtar);
                        }else {
                            holder.img2.setImageResource(R.drawable.male_avtar);
                        }
                    }


                    if (profileSliderData.get(position).getChkonline().equals("1")) {
                        holder.onlineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.onlineLL.setVisibility(View.GONE);
                    }

                    if (profileSliderData.get(position).getPremium().equals("1")) {
                        holder.premiumLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.premiumLL.setVisibility(View.GONE);
                    }

                    if (profileSliderData.get(position).getJust_joined().equals("1")) {
                        holder.just_joineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.just_joineLL.setVisibility(View.GONE);
                    }

                    if (profileSliderData.get(position).getAccount_verify().equals("1")) {
                        holder.verifiedLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.verifiedLL.setVisibility(View.GONE);
                    }


                    if (profileSliderData.get(position).getIsDelete().equals("2")){
                        Glide.with(context.getApplicationContext()).
                                load(profileSliderData.get(position).getImage())
                                .transform(new BlurTransformation(context, 25))
                                .into(holder.img);

                    }
                    holder.matches_fl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (profileSliderData.get(position).getIsDelete().equals("2")){
                                Toast.makeText(context,"This Profile is hidden",Toast.LENGTH_SHORT).show();
                            }else {
                                Intent i = new Intent(context, DetailMatchesActivity.class);
                                i.putExtra("userid", profileSliderData.get(position).getId());
                                i.putExtra("profile_list", new Gson().toJson(profileSliderData)); // Pass list as JSON
                                i.putExtra("position", position);
                                context.startActivity(i);
                            }
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


        if (profileSliderData != null) {
            if (profileSliderData.size() == 0) {
                return 1;
            } else {
                return profileSliderData.size();
            }
        } else {
            return 0;
        }


    }



    @Override
    public int getItemViewType(int position) {
        return (position == profileSliderData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



       /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(ProfileSlider.ProfileSliderData mc) {
        profileSliderData.add(mc);
        notifyItemInserted(profileSliderData.size() - 1);
    }


    public void addAll(ArrayList<ProfileSlider.ProfileSliderData> mcList) {
        for (ProfileSlider.ProfileSliderData mc : mcList) {
            add(mc);

        }
    }





    public void remove(ProfileSlider.ProfileSliderData p) {
        int position = profileSliderData.indexOf(p);
        if (position > -1) {
            profileSliderData.remove(position);
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

        add(new ProfileSlider.ProfileSliderData());
    }




    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = profileSliderData.size() - 1;
        ProfileSlider.ProfileSliderData item = getItem(position);

        if (item != null) {
            profileSliderData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ProfileSlider.ProfileSliderData getItem(int position) {
        return profileSliderData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */





    protected  class ProductViewHolder extends RecyclerView.ViewHolder {



        TextView name,online,you_,age_height,job,lang,address,img_txt,like_profile_txt,connect_btn_txt;
        LinearLayout connecy_btn,onlineLL,just_joineLL,verifiedLL,premiumLL,imgLL,default_Avtar_LL;
        ImageView img,verified_icon,img2;
        CardView card_view;
        FrameLayout matches_fl;
        CardView bubble_v;

        FrameLayout connect_now_fl;

        ImageView job_dot,address_dot;

        public ProductViewHolder(View itemView) {
            super(itemView);

            default_Avtar_LL =  itemView.findViewById(R.id.default_Avtar_LL);

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
            img_txt =  itemView.findViewById(R.id.img_txt);
            imgLL =  itemView.findViewById(R.id.imgLL);
            verified_icon =  itemView.findViewById(R.id.verified_icon);
            connect_now_fl =  itemView.findViewById(R.id.connect_now_fl);
            img2 =  itemView.findViewById(R.id.img2);
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







}


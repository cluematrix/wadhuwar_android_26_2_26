package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.ProfileHomeActivity;
import com.WadhuWarProject.WadhuWar.extras.BlurTransformation;
import com.WadhuWarProject.WadhuWar.model.ProfileSlider;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeProfilesAdapter  extends RecyclerView.Adapter{
    ArrayList<ProfileSlider.ProfileSliderData> profileSliderData = new ArrayList<>();
    Context context;

//    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;

    public HomeProfilesAdapter(Context context, ArrayList<ProfileSlider.ProfileSliderData> profileSliderData) {
        this.context = context;
        this.profileSliderData = profileSliderData;
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_footer, parent, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder1;
            footerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent i = new Intent(context, ProfileHomeActivity.class);
                   context.startActivity(i);
                }
            });
        } else if (holder1 instanceof ItemViewHolder) {
            ItemViewHolder holder = (ItemViewHolder) holder1;
//            position = position -1;

            if (profileSliderData != null && profileSliderData.size() != 0) {
                if (profileSliderData.get(position).getImage() != null) {
                    if (profileSliderData.get(position).getImage().equals("")) {
                        holder.img.setVisibility(View.GONE);
                        holder.default_Avtar_LL.setVisibility(View.VISIBLE);


                        if (profileSliderData.get(position).getGender().equals("Female")) {
                            holder.img2.setImageResource(R.drawable.female_avtar);
                        } else {
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

                    if (profileSliderData.get(position).getGender().equals("Female")) {
                        holder.img2.setImageResource(R.drawable.female_avtar);
                    } else {
                        holder.img2.setImageResource(R.drawable.male_avtar);
                    }
                }



                if(profileSliderData.get(position).getOccupation().equals("Not Specified")){
                    holder.occupation.setText("");

                }else{
                    holder.occupation.setText(profileSliderData.get(position).getOccupation());

                }

                holder.name.setText(profileSliderData.get(position).getName());


                int finalPosition = position;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
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

                try {
                    if (profileSliderData.get(position).getIsDelete().equals("2")) {
                        Glide.with(context.getApplicationContext()).
                                load(profileSliderData.get(position).getImage())
                                .transform(new BlurTransformation(context, 25))
                                .into(holder.img);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
       if (position == profileSliderData.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }



    @Override
    public int getItemCount() {
        return profileSliderData.size() + 1;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        ProgressBar loadmore_progress;
        public HeaderViewHolder(View view) {
            super(view);
            loadmore_progress =  view.findViewById(R.id.loadmore_progress);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar loadmore_progress;

        public FooterViewHolder(View view) {
            super(view);
            loadmore_progress = view.findViewById(R.id.loadmore_progress);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name,occupation;
        com.WadhuWarProject.WadhuWar.extras.ProportionalImageView img;
        ImageView img2;
        LinearLayout default_Avtar_LL;


        public ItemViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            occupation = (TextView) itemView.findViewById(R.id.occupation);
            name = (TextView) itemView.findViewById(R.id.name);
            img2 =  itemView.findViewById(R.id.img2);
            default_Avtar_LL =  itemView.findViewById(R.id.default_Avtar_LL);        }
    }
}


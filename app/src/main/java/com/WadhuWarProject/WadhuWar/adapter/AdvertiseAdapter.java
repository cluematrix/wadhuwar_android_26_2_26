package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.AdvertiseDetailActivity;
import com.WadhuWarProject.WadhuWar.model.AdvertiseList;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdvertiseAdapter extends RecyclerView.Adapter<AdvertiseAdapter.ViewHolder>{
    ArrayList<AdvertiseList.AdvertiseListData> advertiseListData = new ArrayList<>();
    Context context;

    int size;
    public AdvertiseAdapter(Context context, ArrayList<AdvertiseList.AdvertiseListData> advertiseListData) {
        this.context = context;
        this.advertiseListData = advertiseListData;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_home_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AdvertiseList.AdvertiseListData item = advertiseListData.get(position);
        holder.name.setText(item.getName());

        String type = item.getType();

        if ("1".equals(type)) {
            // ✅ IMAGE
            holder.iv_play_pause.setVisibility(View.GONE);
            Log.d("image11", "onBindViewHolder: " + item.getImg());
            if (!item.getImg().isEmpty()) {
                Glide.with(context)
                        .load(item.getImg())
                        .into(holder.img);
            }

        }
        else if ("2".equals(type)) {
            // ✅ YOUTUBE VIDEO
            holder.iv_play_pause.setVisibility(View.VISIBLE);

            Uri uri = Uri.parse(item.getVideolink());
            String videoID = uri.getQueryParameter("v");

            if (videoID != null) {
                String thumbUrl = "https://img.youtube.com/vi/" + videoID + "/0.jpg";
                Glide.with(context)
                        .load(thumbUrl)
                        .into(holder.img);
            }

        }
        else {
            // ✅ MP4 VIDEO
            holder.iv_play_pause.setVisibility(View.VISIBLE);

            String videoUrl = item.getVideo();

            Glide.with(context)
                    .load(videoUrl)
                    .thumbnail(0.1f)   // fetch first frame
                    .into(holder.img);
        }

        holder.item_success_story_RL.setOnClickListener(v -> {
            Intent i = new Intent(context, AdvertiseDetailActivity.class);
            i.putExtra("id", item.getId());
            context.startActivity(i);
        });
    }


    @Override
    public int getItemCount() {
        if(advertiseListData.size()>5){
            size=5;
        }else {
            size=advertiseListData.size();
        }
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img,iv_play_pause;
        LinearLayout item_success_story_RL;

        public ViewHolder(View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.img);
            name =  itemView.findViewById(R.id.name);
            item_success_story_RL =  itemView.findViewById(R.id.item_success_story_RL);
            iv_play_pause =  itemView.findViewById(R.id.iv_play_pause);
        }
    }
}
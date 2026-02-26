package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.SuccessStoriesDetailActivity;
import com.WadhuWarProject.WadhuWar.model.BlogList;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SuccessStoryAdapter extends RecyclerView.Adapter<SuccessStoryAdapter.ViewHolder>{
    ArrayList<BlogList.BlogListData> blogListData = new ArrayList<>();
    Context context;

    int size;
    public SuccessStoryAdapter(Context context, ArrayList<BlogList.BlogListData> blogListData) {
        this.context = context;
        this.blogListData = blogListData;
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


        holder.name.setText(blogListData.get(position).getName());

        /*1 for image , 2 for video*/
        if(blogListData.get(position).getType().equals("1")){
            holder.iv_play_pause.setVisibility(View.GONE);

            if(!blogListData.get(position).getImg().equals(""))
                Glide.with(context.getApplicationContext()).load(blogListData.get(position).getImg()).into(holder.img);

        }else {
            holder.iv_play_pause.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(blogListData.get(position).getVideolink());
            String videoID = uri.getQueryParameter("v");
            String url = "http://img.youtube.com/vi/" + videoID +"/0.jpg";
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .into(holder.img);
        }


        holder.item_success_story_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i = new Intent(context, SuccessStoriesDetailActivity.class);
                i.putExtra("id" , blogListData.get(position).getId());
                context.startActivity(i);

            }
        });




    }




    @Override
    public int getItemCount() {

        if(blogListData.size()>5){
            size=5;
        }else {
            size=blogListData.size();
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
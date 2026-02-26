package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.AdvertiseDetailActivity;
import com.WadhuWarProject.WadhuWar.activity.NewsDetailActivity;
import com.WadhuWarProject.WadhuWar.model.AdvertiseList;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewAllAdvertiseAdapter extends   RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<AdvertiseList.AdvertiseListData> advertiseListData = new ArrayList<>();
    Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public ViewAllAdvertiseAdapter(Context context) {
        this.context = context;
        this.advertiseListData = new ArrayList<>();
    }



    public   ArrayList<AdvertiseList.AdvertiseListData> getAdvertiseListData() {
        return advertiseListData;
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
        View v1 = inflater.inflate(R.layout.item_home_list_view_all, parent, false);
        viewHolder = new ProductViewHolder(v1);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;

                if(advertiseListData!=null && advertiseListData.size()!=0) {

                    System.out.println("size adv============" + advertiseListData.size());
                    holder.name.setText(advertiseListData.get(position).getName());
                    /*1 for image , 2 for video*/
                    if (advertiseListData.get(position).getType().equals("1")) {
                        holder.iv_play_pause.setVisibility(View.GONE);

                        if (!advertiseListData.get(position).getImg().equals(""))
                            Glide.with(context.getApplicationContext()).load(advertiseListData.get(position).getImg()).into(holder.img);

                    } else if(advertiseListData.get(position).getType().equals("2")){
                        holder.iv_play_pause.setVisibility(View.VISIBLE);
                        Uri uri = Uri.parse(advertiseListData.get(position).getVideolink());
                        String videoID = uri.getQueryParameter("v");
                        String url = "http://img.youtube.com/vi/" + videoID + "/0.jpg";
                        Glide.with(context.getApplicationContext())
                                .load(url)
                                .into(holder.img);
                    } else {
                        // ✅ MP4 VIDEO
                        holder.iv_play_pause.setVisibility(View.VISIBLE);

                        String videoUrl = advertiseListData.get(position).getVideo();
                        Glide.with(context)
                                .load(videoUrl)
                                .thumbnail(0.1f)   // fetch first frame
                                .into(holder.img);

                    }

                    holder.item_success_story_RL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent i = new Intent(context, AdvertiseDetailActivity.class);
                            i.putExtra("id", advertiseListData.get(position).getId());
                            context.startActivity(i);


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


        if (advertiseListData != null) {
            if (advertiseListData.size() == 0) {
                return 1;
            } else {
                return advertiseListData.size();
            }
        } else {
            return 0;
        }


    }



    @Override
    public int getItemViewType(int position) {
        return (position == advertiseListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



       /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(AdvertiseList.AdvertiseListData mc) {
        advertiseListData.add(mc);
        notifyItemInserted(advertiseListData.size() - 1);
    }


    public void addAll(ArrayList<AdvertiseList.AdvertiseListData> mcList) {
        for (AdvertiseList.AdvertiseListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(AdvertiseList.AdvertiseListData p) {
        int position = advertiseListData.indexOf(p);
        if (position > -1) {
            advertiseListData.remove(position);
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

        add(new AdvertiseList.AdvertiseListData());
    }




    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = advertiseListData.size() - 1;
        AdvertiseList.AdvertiseListData item = getItem(position);

        if (item != null) {
            advertiseListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public AdvertiseList.AdvertiseListData getItem(int position) {
        return advertiseListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */





    protected  class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        ImageView img,iv_play_pause;
        LinearLayout item_success_story_RL;
        public ProductViewHolder(View itemView) {
            super(itemView);

            img =  itemView.findViewById(R.id.img);
            name =  itemView.findViewById(R.id.name);
            item_success_story_RL =  itemView.findViewById(R.id.item_success_story_RL);
            iv_play_pause =  itemView.findViewById(R.id.iv_play_pause);

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
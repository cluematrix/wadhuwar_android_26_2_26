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
import com.WadhuWarProject.WadhuWar.activity.NewsDetailActivity;
import com.WadhuWarProject.WadhuWar.activity.SuccessStoriesDetailActivity;
import com.WadhuWarProject.WadhuWar.model.BlogList;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewAllNewsAdapter extends   RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<NewsList.NewsListData> newsListData = new ArrayList<>();
    Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public ViewAllNewsAdapter(Context context) {
        this.context = context;
        this.newsListData  = new ArrayList<>();
    }


    public   ArrayList<NewsList.NewsListData> getNewsListData() {
        return newsListData;
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

                if(newsListData!=null && newsListData.size()!=0) {
                    holder.name.setText(newsListData.get(position).getName());
                    /*1 for image , 2 for video*/
                    if (newsListData.get(position).getType().equals("1")) {
                        holder.iv_play_pause.setVisibility(View.GONE);

                        if (!newsListData.get(position).getImg().equals(""))
                            Glide.with(context.getApplicationContext()).load(newsListData.get(position).getImg()).into(holder.img);

                    } else {
                        holder.iv_play_pause.setVisibility(View.VISIBLE);
                        Uri uri = Uri.parse(newsListData.get(position).getVideolink());
                        String videoID = uri.getQueryParameter("v");
                        String url = "http://img.youtube.com/vi/" + videoID + "/0.jpg";
                        Glide.with(context.getApplicationContext())
                                .load(url)
                                .into(holder.img);
                    }
                    holder.item_success_story_RL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(context, NewsDetailActivity.class);
                            i.putExtra("id", newsListData.get(position).getId());
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


        if (newsListData != null) {
            if (newsListData.size() == 0) {
                return 1;
            } else {
                return newsListData.size();
            }
        } else {
            return 0;
        }


    }



    @Override
    public int getItemViewType(int position) {
        return (position == newsListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



       /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(NewsList.NewsListData mc) {
        newsListData.add(mc);
        notifyItemInserted(newsListData.size() - 1);
    }


    public void addAll(ArrayList<NewsList.NewsListData> mcList) {
        for (NewsList.NewsListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(NewsList.NewsListData p) {
        int position = newsListData.indexOf(p);
        if (position > -1) {
            newsListData.remove(position);
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

        add(new NewsList.NewsListData());
    }




    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = newsListData.size() - 1;
        NewsList.NewsListData item = getItem(position);

        if (item != null) {
            newsListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public NewsList.NewsListData getItem(int position) {
        return newsListData.get(position);
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
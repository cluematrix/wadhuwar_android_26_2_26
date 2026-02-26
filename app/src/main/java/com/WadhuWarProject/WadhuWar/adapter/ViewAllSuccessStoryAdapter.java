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
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.SuccessStoriesDetailActivity;
import com.WadhuWarProject.WadhuWar.model.BlogList;
import com.WadhuWarProject.WadhuWar.model.ProfileSlider;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAllSuccessStoryAdapter  extends   RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<BlogList.BlogListData> blogListData = new ArrayList<>();
    Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public ViewAllSuccessStoryAdapter(Context context) {
        this.context = context;
        this.blogListData = new ArrayList<>();
    }

    public   ArrayList<BlogList.BlogListData> getBlogListData() {
        return blogListData;
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
                viewHolder = new ViewAllSuccessStoryAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_home_list_view_all, parent, false);
        viewHolder = new ViewAllSuccessStoryAdapter.ProductViewHolder(v1);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;

                if(blogListData!=null & blogListData.size()!=0) {

                    holder.name.setText(blogListData.get(position).getName());

                    /*1 for image , 2 for video*/
                    if (blogListData.get(position).getType().equals("1")) {
                        holder.iv_play_pause.setVisibility(View.GONE);

                        if (!blogListData.get(position).getImg().equals(""))
                            Glide.with(context.getApplicationContext()).load(blogListData.get(position).getImg()).into(holder.img);

                    } else {
                        holder.iv_play_pause.setVisibility(View.VISIBLE);
                        Uri uri = Uri.parse(blogListData.get(position).getVideolink());
                        String videoID = uri.getQueryParameter("v");
                        String url = "http://img.youtube.com/vi/" + videoID + "/0.jpg";
                        Glide.with(context.getApplicationContext())
                                .load(url)
                                .into(holder.img);
                    }

                    holder.item_success_story_RL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            holder.item_success_story_RL.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent i = new Intent(context, SuccessStoriesDetailActivity.class);
                                    i.putExtra("id", blogListData.get(position).getId());
                                    context.startActivity(i);

                                }
                            });

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


        if (blogListData != null) {
            if (blogListData.size() == 0) {
                return 1;
            } else {
                return blogListData.size();
            }
        } else {
            return 0;
        }


    }



    @Override
    public int getItemViewType(int position) {
        return (position == blogListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



       /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(BlogList.BlogListData mc) {
        blogListData.add(mc);
        notifyItemInserted(blogListData.size() - 1);
    }


    public void addAll(ArrayList<BlogList.BlogListData> mcList) {
        for (BlogList.BlogListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(BlogList.BlogListData p) {
        int position = blogListData.indexOf(p);
        if (position > -1) {
            blogListData.remove(position);
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

        add(new BlogList.BlogListData());
    }




    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = blogListData.size() - 1;
        BlogList.BlogListData item = getItem(position);

        if (item != null) {
            blogListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public BlogList.BlogListData getItem(int position) {
        return blogListData.get(position);
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
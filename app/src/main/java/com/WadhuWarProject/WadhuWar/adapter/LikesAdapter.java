package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.NewsDetailActivity;
import com.WadhuWarProject.WadhuWar.extras.RoundedImageView;
import com.WadhuWarProject.WadhuWar.model.Likes;
import com.WadhuWarProject.WadhuWar.model.NewmatchesTabList;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.bumptech.glide.Glide;
//import com.github.florent37.shapeofview.shapes.BubbleView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LikesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<Likes.LikesData> likesData ;
    Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public LikesAdapter(Context context) {
        this.context = context;
        this.likesData = new ArrayList<>();
    }


    public ArrayList<Likes.LikesData> getLikesData() {
        return likesData;
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
        View v1 = inflater.inflate(R.layout.item_like, parent, false);
        viewHolder = new ProductViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;


                if(likesData!=null && likesData.size()!=0) {
                    holder.name.setText(likesData.get(position).getName());

                    if (likesData.get(position).getImage() != null) {
                        if (!likesData.get(position).getImage().equals("")) {
//                            Glide.with(context).load(likesData.get(position).getImage()).into(holder.profile);

                            Glide.with(context.getApplicationContext()).load(likesData.get(position).getImage()).into(holder.profile);

                        } else {

                            if (likesData.get(position).getGender().equals("Female")) {
//                                Glide.with(context).load(R.drawable.female_avtar).into(holder.profile);
                                Glide.with(context.getApplicationContext()).load(R.drawable.female_avtar).into(holder.profile);


                            } else {
//                                Glide.with(context).load(R.drawable.male_avtar).into(holder.profile);
                                Glide.with(context.getApplicationContext()).load(R.drawable.male_avtar).into(holder.profile);

                            }
                        }
                    } else {
                        if (likesData.get(position).getGender().equals("Female")) {
//                            Glide.with(context).load(R.drawable.female_avtar).into(holder.profile);
                            Glide.with(context.getApplicationContext()).load(R.drawable.female_avtar).into(holder.profile);

                        } else {
//                            Glide.with(context).load(R.drawable.male_avtar).into(holder.profile);
                            Glide.with(context.getApplicationContext()).load(R.drawable.male_avtar).into(holder.profile);

                        }
                    }


                    /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(context, DetailMatchesActivity.class);
                            i.putExtra("userid", likesData.get(position).getM_id());
                            context.startActivity(i);

                        }
                    });*/


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


        if (likesData != null) {
            if (likesData.size() == 0) {
                return 1;
            } else {
                return likesData.size();
            }
        } else {
            return 0;
        }


    }


    @Override
    public int getItemViewType(int position) {
        return (position == likesData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }
     /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Likes.LikesData mc) {
        likesData.add(mc);
        notifyItemInserted(likesData.size() - 1);
    }


    public void addAll(ArrayList<Likes.LikesData> mcList) {
        for (Likes.LikesData mc : mcList) {
            add(mc);

        }
    }





    public void remove(Likes.LikesData p) {
        int position = likesData.indexOf(p);
        if (position > -1) {
            likesData.remove(position);
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

        add(new Likes.LikesData());
    }



    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = likesData.size() - 1;
        Likes.LikesData item = getItem(position);

        if (item != null) {
            likesData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Likes.LikesData getItem(int position) {
        return likesData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */




    protected  class ProductViewHolder extends RecyclerView.ViewHolder {



        TextView name;
        CircleImageView profile;

        public ProductViewHolder(View itemView) {
            super(itemView);


            profile =  itemView.findViewById(R.id.profile);
            name =  itemView.findViewById(R.id.name);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar loadmore_progress;
        public LoadingVH(View itemView) {
            super(itemView);


            loadmore_progress = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CircleImageView profile;

        public ViewHolder(View itemView) {
            super(itemView);

            profile =  itemView.findViewById(R.id.profile);
            name =  itemView.findViewById(R.id.name);

        }
    }



}
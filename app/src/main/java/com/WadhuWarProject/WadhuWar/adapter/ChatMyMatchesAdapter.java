package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.ChatActivity;
import com.WadhuWarProject.WadhuWar.extras.BlurTransformation;
import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMyMatchesAdapter extends   RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<TabMyMatchesList.TabMyMatchesListData> tabMyMatchesListData = new ArrayList<>();
    Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public   ArrayList<TabMyMatchesList.TabMyMatchesListData> tabMyMatchesListData() {
        return tabMyMatchesListData;
    }

    public ChatMyMatchesAdapter(Context context ) {
        this.context = context;
        this.tabMyMatchesListData = new ArrayList<>();
    }
   /* @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_mathces_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

*/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress_chat, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_chat_my_matches, parent, false);
        viewHolder = new ProductViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;

                System.out.println("1111111111111=============" + tabMyMatchesListData.size());

                if(tabMyMatchesListData!=null && tabMyMatchesListData.size()!=0) {
                    holder.itemView.setVisibility(View.VISIBLE);

                    holder.name.setText(tabMyMatchesListData.get(position).getName());

                    /*Picasso.with(context).load(tabMyMatchesListData.get(position).getImage())
                            .placeholder(R.drawable.ic_male_avatar).fit().into(holder.img);*/


                    if(tabMyMatchesListData.get(position).getImage()!=null) {
                        if (!tabMyMatchesListData.get(position).getImage().equals("")) {

                            Glide.with(context.getApplicationContext()).load(tabMyMatchesListData.get(position).getImage()).into(holder.img);
                        }else{

                            if(tabMyMatchesListData.get(position).getGender().equals("Female")){
                                holder.img.setImageResource(R.drawable.female_avtar);
                            }else {
                                holder.img.setImageResource(R.drawable.male_avtar);
                            }

                        }
                    }else{
                        if(tabMyMatchesListData.get(position).getGender().equals("Female")){
                            holder.img.setImageResource(R.drawable.female_avtar);
                        }else {
                            holder.img.setImageResource(R.drawable.male_avtar);
                        }
                    }

                    if (tabMyMatchesListData.get(position).getChkonline().equals("1")) {
                        holder.online_iv.setVisibility(View.VISIBLE);
                    } else {
                        holder.online_iv.setVisibility(View.GONE);
                    }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Toast.makeText(context, "clicked on profile", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(context, ChatActivity.class);
                            i.putExtra("receiver_id",tabMyMatchesListData.get(position).getId());
                            i.putExtra("receiver_name",tabMyMatchesListData.get(position).getName());
                            i.putExtra("receiver_image",tabMyMatchesListData.get(position).getImage());
//                            i.putExtra("hideProfile",tabMyMatchesListData.get(position).getIsDelete());
                            context.startActivity(i);
                        }
                    });

                    if (tabMyMatchesListData.get(position).getIsDelete().equals("2")){
                        Glide.with(context.getApplicationContext()).
                                load(tabMyMatchesListData.get(position).getImage())
                                .transform(new BlurTransformation(context, 25))
                                .into(holder.img);
                    }
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


        if (tabMyMatchesListData != null) {
            if (tabMyMatchesListData.size() == 0) {
                return 1;
            } else {
                return tabMyMatchesListData.size();
            }
        } else {
            return 0;
        }


    }



    @Override
    public int getItemViewType(int position) {
        return (position == tabMyMatchesListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


       /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(TabMyMatchesList.TabMyMatchesListData mc) {
        tabMyMatchesListData.add(mc);
        notifyItemInserted(tabMyMatchesListData.size() - 1);
    }


    public void addAll(ArrayList<TabMyMatchesList.TabMyMatchesListData> mcList) {
        for (TabMyMatchesList.TabMyMatchesListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(TabMyMatchesList.TabMyMatchesListData p) {
        int position = tabMyMatchesListData.indexOf(p);
        if (position > -1) {
            tabMyMatchesListData.remove(position);
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

        add(new TabMyMatchesList.TabMyMatchesListData());
    }




    public void removeLoadingFooter() {

        isLoadingAdded = false;

        System.out.println("0000000000--------------");
        int position = tabMyMatchesListData.size() - 1;
        TabMyMatchesList.TabMyMatchesListData item = getItem(position);

        if (item != null) {
            System.out.println("1111111 0000000000--------------");
            tabMyMatchesListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TabMyMatchesList.TabMyMatchesListData getItem(int position) {
        return tabMyMatchesListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    protected  class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;
        FrameLayout online_iv;

        public ProductViewHolder(View itemView) {
            super(itemView);

            name =  itemView.findViewById(R.id.namemyMatches);
            img =  itemView.findViewById(R.id.imgmyMatches);
            online_iv =  itemView.findViewById(R.id.online_iv);
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
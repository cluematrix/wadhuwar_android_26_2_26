package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.MelavaDetailActivity;
import com.WadhuWarProject.WadhuWar.activity.NewsDetailActivity;
import com.WadhuWarProject.WadhuWar.model.MelawaList;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MelavaAdapter extends RecyclerView.Adapter<MelavaAdapter.ViewHolder>{
    ArrayList<MelawaList.MelawaListData> melawaListData = new ArrayList<>();
    MelawaList melawaList;
    Context context;

    int size;
    public MelavaAdapter(Context context,  ArrayList<MelawaList.MelawaListData> melawaListData) {
        this.context = context;
        this.melawaListData = melawaListData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_melava, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if(melawaListData!=null){
            holder.title.setText(melawaListData.get(position).getName());
            holder.amount.setText("\u20B9"+melawaListData.get(position).getAmount());

        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, MelavaDetailActivity.class);
                i.putExtra("id" , melawaListData.get(position).getId());
                i.putExtra("position" , String.valueOf(position));

                i.putExtra("title" , melawaListData.get(position).getName());
                i.putExtra("amount" , melawaListData.get(position).getAmount());
                i.putExtra("date" , melawaListData.get(position).getEvent_date());
                i.putExtra("time" , melawaListData.get(position).getEvent_time());




                context.startActivity(i);

            }
        });




    }




    @Override
    public int getItemCount() {


        return melawaListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,amount,discount_amount,discount_per;
        LinearLayout item_success_story_RL;

        public ViewHolder(View itemView) {
            super(itemView);

            title =  itemView.findViewById(R.id.title);
            amount =  itemView.findViewById(R.id.amount);


        }
    }



}
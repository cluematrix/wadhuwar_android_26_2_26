package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.model.TabShortList;
import com.bumptech.glide.Glide;
//import com.github.florent37.shapeofview.shapes.BubbleView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TabShortlistedAdapter extends RecyclerView.Adapter<TabShortlistedAdapter.ViewHolder>{
    ArrayList<TabShortList.MatchesTabShortListData> matchesTabShortListData = new ArrayList<>();
    Context context;

    int size;
    public TabShortlistedAdapter(Context context,   ArrayList<TabShortList.MatchesTabShortListData> matchesTabShortListData) {
        this.context = context;
        this.matchesTabShortListData = matchesTabShortListData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_mathces_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(matchesTabShortListData!=null) {

            if(!matchesTabShortListData.get(position).getImg_count().equals("0")){
                holder.imgLL.setVisibility(View.VISIBLE);
                holder.img_txt.setText(String.valueOf(matchesTabShortListData.get(position).getImg_count()));
            }else{
                holder.imgLL.setVisibility(View.GONE);
            }



            holder.name.setText(matchesTabShortListData.get(position).getName());


            if (!matchesTabShortListData.get(position).getAge().contentEquals("Not Specified") &&
                    !matchesTabShortListData.get(position).getHeight().contentEquals("Not Specified")) {
                holder.age_height.setText(String.format("%syrs, %s,", matchesTabShortListData.get(position).getAge(), matchesTabShortListData.get(position).getHeight()));

            } else if (!matchesTabShortListData.get(position).getAge().contentEquals("Not Specified") &&
                    matchesTabShortListData.get(position).getHeight().contentEquals("Not Specified")) {
                holder.age_height.setText(matchesTabShortListData.get(position).getAge()+" yrs");

            } else if (matchesTabShortListData.get(position).getAge().contentEquals("Not Specified") &&
                    !matchesTabShortListData.get(position).getHeight().contentEquals("Not Specified")) {
                holder.age_height.setText(matchesTabShortListData.get(position).getHeight());

            } else {
                holder.age_height.setText(" ");

            }

            if (!matchesTabShortListData.get(position).getOccupation().contentEquals("Not Specified")) {
                holder.job.setText(matchesTabShortListData.get(position).getOccupation());

            } else {
                holder.job.setText("");

            }


            if (!matchesTabShortListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                    !matchesTabShortListData.get(position).getCaste().contentEquals("Not Specified")) {
                holder.lang.setText(String.format("%s,%s", matchesTabShortListData.get(position).getMothertounge(), matchesTabShortListData.get(position).getCaste()));

            } else if (!matchesTabShortListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                    matchesTabShortListData.get(position).getCaste().contentEquals("Not Specified")) {
                holder.lang.setText(matchesTabShortListData.get(position).getMothertounge());

            } else if (matchesTabShortListData.get(position).getMothertounge().contentEquals("Not Specified") &&
                    !matchesTabShortListData.get(position).getCaste().contentEquals("Not Specified")) {
                holder.lang.setText(matchesTabShortListData.get(position).getCaste());

            } else {
                holder.lang.setText(" ");

            }

            System.out.println("premiunMatchesListData.get(position).getDistrict()---" + matchesTabShortListData.get(position).getDistrict());
            System.out.println("premiunMatchesListData.get(position).getState()---" + matchesTabShortListData.get(position).getState());

            if (!matchesTabShortListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    !matchesTabShortListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setText(String.format("%s, %s", matchesTabShortListData.get(position).getDistrict(), matchesTabShortListData.get(position).getState()));

            } else if (!matchesTabShortListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    matchesTabShortListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setText(matchesTabShortListData.get(position).getDistrict());

            } else if (matchesTabShortListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    !matchesTabShortListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setText(matchesTabShortListData.get(position).getState());

            } else {
                holder.address.setText(" ");

            }


                if (matchesTabShortListData.get(position).getImage().equals("")) {
                    Glide.with(context.getApplicationContext()).load(R.drawable.default_avtar).into(holder.img);


                } else {
                    Glide.with(context.getApplicationContext()).load(matchesTabShortListData.get(position).getImage()).into(holder.img);

                }
            } else {
                Glide.with(context.getApplicationContext()).load(R.drawable.default_avtar).into(holder.img);

            }


            if (matchesTabShortListData.get(position).getChkonline().equals("1")) {
                holder.onlineLL.setVisibility(View.VISIBLE);
            } else {
                holder.onlineLL.setVisibility(View.GONE);
            }

            if (matchesTabShortListData.get(position).getPremium().equals("1")) {
                holder.premiumLL.setVisibility(View.VISIBLE);
            } else {
                holder.premiumLL.setVisibility(View.GONE);
            }

            if (matchesTabShortListData.get(position).getJust_joined().equals("1")) {
                holder.just_joineLL.setVisibility(View.VISIBLE);
            } else {
                holder.just_joineLL.setVisibility(View.GONE);
            }

        if (matchesTabShortListData.get(position).getAccount_verify().equals("1")) {
            holder.verifiedLL.setVisibility(View.VISIBLE);
        } else {
            holder.verifiedLL.setVisibility(View.GONE);
        }

            holder.matches_fl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context, DetailMatchesActivity.class);
                    i.putExtra("userid", matchesTabShortListData.get(position).getId());
                    context.startActivity(i);

                }
            });



    }




    @Override
    public int getItemCount() {


        return matchesTabShortListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,online,you_,age_height,job,lang,address,img_txt;
        LinearLayout connecy_btn,onlineLL,just_joineLL,verifiedLL,premiumLL,imgLL;
        ImageView img;
        CardView card_view;
        FrameLayout matches_fl;
        CardView bubble_v;

        public ViewHolder(View itemView) {
            super(itemView);

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
            imgLL =  itemView.findViewById(R.id.imgLL);
            img_txt =  itemView.findViewById(R.id.img_txt);

        }
    }



}
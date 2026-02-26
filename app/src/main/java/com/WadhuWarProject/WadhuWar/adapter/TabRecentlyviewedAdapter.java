package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.extras.BlurTransformation;
import com.WadhuWarProject.WadhuWar.extras.DialogUtil;
import com.WadhuWarProject.WadhuWar.model.TabRecentlyViewedList;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TabRecentlyviewedAdapter extends RecyclerView.Adapter<TabRecentlyviewedAdapter.ViewHolder> {
    ArrayList<TabRecentlyViewedList.TabRecentlyViewedListData> tabRecentlyViewedListData = new ArrayList<>();
    Context context;

    int size;

    public TabRecentlyviewedAdapter(Context context, ArrayList<TabRecentlyViewedList.TabRecentlyViewedListData> tabRecentlyViewedListData) {
        this.context = context;
        this.tabRecentlyViewedListData = tabRecentlyViewedListData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_matches, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (tabRecentlyViewedListData != null && tabRecentlyViewedListData.size() != 0) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.name.setText(tabRecentlyViewedListData.get(position).getName());

            if (!tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(String.format("%syrs, %s,%s,",
                        tabRecentlyViewedListData.get(position).getAge(),
                        tabRecentlyViewedListData.get(position).getHeight(),
                        tabRecentlyViewedListData.get(position).getMothertounge()));

            } else if (!tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified") &&
                    tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        tabRecentlyViewedListData.get(position).getAge() + " yrs, " +
                                tabRecentlyViewedListData.get(position).getHeight());

            } else if (tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        tabRecentlyViewedListData.get(position).getHeight() + " yrs, " +
                                tabRecentlyViewedListData.get(position).getMothertounge());

            } else if (!tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                    tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        tabRecentlyViewedListData.get(position).getAge() + " yrs, " +
                                tabRecentlyViewedListData.get(position).getMothertounge());

            } else if (!tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                    tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified") &&
                    tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        tabRecentlyViewedListData.get(position).getAge());

            } else if (tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified") &&
                    tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        tabRecentlyViewedListData.get(position).getHeight());

            } else if (tabRecentlyViewedListData.get(position).getAge().contentEquals("Not Specified") &&
                    tabRecentlyViewedListData.get(position).getHeight().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getMothertounge().contentEquals("Not Specified")) {
                holder.age_height_mothertoung.setVisibility(View.VISIBLE);

                holder.age_height_mothertoung.setText(
                        tabRecentlyViewedListData.get(position).getMothertounge());

            } else {
                holder.age_height_mothertoung.setVisibility(View.GONE);

            }


            if (!tabRecentlyViewedListData.get(position).getCaste().contentEquals("Not Specified")) {
                holder.caste.setVisibility(View.VISIBLE);

                holder.caste.setText(tabRecentlyViewedListData.get(position).getCaste());

            } else {
                holder.caste.setVisibility(View.GONE);

            }

            if (!tabRecentlyViewedListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setVisibility(View.VISIBLE);

                holder.address.setText(String.format("%s, %s", tabRecentlyViewedListData.get(position).getDistrict(),
                        tabRecentlyViewedListData.get(position).getState()));

            } else if (!tabRecentlyViewedListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    tabRecentlyViewedListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setVisibility(View.VISIBLE);

                holder.address.setText(tabRecentlyViewedListData.get(position).getDistrict());
            } else if (tabRecentlyViewedListData.get(position).getDistrict().contentEquals("Not Specified") &&
                    !tabRecentlyViewedListData.get(position).getState().contentEquals("Not Specified")) {
                holder.address.setVisibility(View.VISIBLE);

                holder.address.setText(tabRecentlyViewedListData.get(position).getState());
            } else {
                holder.address.setVisibility(View.VISIBLE);
            }


            if (tabRecentlyViewedListData.get(position).getImage() != null) {
                if (tabRecentlyViewedListData.get(position).getImage().equals("")) {
                    holder.img.setVisibility(View.GONE);
                    holder.default_Avtar_LL.setVisibility(View.VISIBLE);


                    if (tabRecentlyViewedListData.get(position).getGender().equals("Female")) {
                        holder.img2.setImageResource(R.drawable.female_avtar);
                    } else {
                        holder.img2.setImageResource(R.drawable.male_avtar);
                    }

                } else {

                    holder.default_Avtar_LL.setVisibility(View.GONE);
                    holder.img.setVisibility(View.VISIBLE);
                    Glide.with(context.getApplicationContext()).load(tabRecentlyViewedListData.get(position).getImage()).into(holder.img);

                }
            } else {

                holder.img.setVisibility(View.GONE);
                holder.default_Avtar_LL.setVisibility(View.VISIBLE);

                if (tabRecentlyViewedListData.get(position).getGender().equals("Female")) {
                    holder.img2.setImageResource(R.drawable.female_avtar);
                } else {
                    holder.img2.setImageResource(R.drawable.male_avtar);
                }
            }

            if (tabRecentlyViewedListData.get(position).getIsDelete().equals("2")) {
                Glide.with(context.getApplicationContext()).
                        load(tabRecentlyViewedListData.get(position).getImage())
                        .transform(new BlurTransformation(context, 25))
                        .into(holder.img);

            }

            holder.mathcesLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SharedPrefManager.getInstance(context).getString().equals("0")) {

                        if (tabRecentlyViewedListData.get(position).getIsDelete().equals("2")) {
                            Toast.makeText(context, "Profile is Hidden", Toast.LENGTH_SHORT).show();

                        } else {
                            Intent i = new Intent(context, DetailMatchesActivity.class);
                            i.putExtra("userid", tabRecentlyViewedListData.get(position).getId());
                            i.putExtra("profile_list", new Gson().toJson(tabRecentlyViewedListData)); // Pass list as JSON
                            i.putExtra("position", position);
                            context.startActivity(i);
                        }
                    } else {
                        DialogUtil.showAlertDialog(context);
                    }
                }
            });
        } else {
//            holder.itemView.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {

        if (tabRecentlyViewedListData.size() > 5) {
            size = 5;
        } else {
            size = tabRecentlyViewedListData.size();
        }
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, age_height_mothertoung, caste, address;
        ImageView img, img2;
        LinearLayout mathcesLL, default_Avtar_LL;

        public ViewHolder(View itemView) {
            super(itemView);

            mathcesLL = itemView.findViewById(R.id.mathcesLL);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            age_height_mothertoung = itemView.findViewById(R.id.age_height_mothertoung);
            caste = itemView.findViewById(R.id.caste);
            address = itemView.findViewById(R.id.address);
            img2 = itemView.findViewById(R.id.img2);
            default_Avtar_LL = itemView.findViewById(R.id.default_Avtar_LL);

        }
    }


}
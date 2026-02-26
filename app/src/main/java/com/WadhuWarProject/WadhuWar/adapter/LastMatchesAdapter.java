package com.WadhuWarProject.WadhuWar.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.model.GetOnlineDate;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class LastMatchesAdapter extends RecyclerView.Adapter<LastMatchesAdapter.ViewHolder> {

    Context context;

    private UserData user;
    private SharedPrefManager sharedPrefManager;

    List<GetOnlineDate.LastFourProfiles> list; // change according to response model

    public LastMatchesAdapter(Context context, List<GetOnlineDate.LastFourProfiles> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_last_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("AdapterBind", "Name: " + list.get(position).getName());
        holder.tvName.setText(list.get(position).getName());
//        holder.age_height.setText(list.get(position).getAge()+ " " + list.get(position).getHeight() );
        String image =list.get(position).getImage();
        if (image != null && !image.isEmpty()) {
            holder.img.setVisibility(View.VISIBLE);
            Glide.with(context).load(image).into((ImageView) holder.img);
        } else {
            holder.img.setVisibility(View.GONE);
        }

        String age = list.get(position).getAge();
        String height = list.get(position).getHeight();
        if (age != null && height != null && !"Not Specified".equals(age) && !"Not Specified".equals(height)) {
            holder.age_height.setText(String.format("%syrs, %s", age, height));
        } else if (age != null && !"Not Specified".equals(age)) {
            holder.age_height.setText(age + " yrs");
        } else if (height != null && !"Not Specified".equals(height)) {
            holder.age_height.setText(height);
        } else {
            holder.age_height.setText("");
        }

        String district = list.get(position).getDistrict();
        String state = list.get(position).getState();
        if (district != null && state != null && !"Not Specified".equals(district) && !"Not Specified".equals(state)) {
            holder.address.setText(String.format("%s, %s", district, state));
        } else if (district != null && !"Not Specified".equals(district)) {
            holder.address.setText(district);
        } else if (state != null && !"Not Specified".equals(state)) {
            holder.address.setText(state);
        } else {
            holder.address.setText("");
        }

//        holder.connecy_btn.setOnClickListener(view -> connectApi(list.get(position).getId(), String.valueOf(user.getUser_id()), view, holder));
//        holder.connecy_btn.setOnClickListener(view -> connectApi(list.get(position).getId(), String.valueOf(user.getUser_id()), view, holder));
        holder.matches_fl.setOnClickListener(view -> {
//            if ("0".equals(sharedPrefManager.getString())) {
//                if ("2".equals(list.get(position).getPremium())) {
//                    Toast.makeText(context, "This Profile is hidden", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent i = new Intent(context, DetailMatchesActivity.class);
//                    i.putExtra("userid", list.get(position).getId());
//                    i.putExtra("profile_list", new Gson().toJson(list));
//                    i.putExtra("position", position);
//                    context.startActivity(i);
//                }
//            } else {
//                DialogUtil.showAlertDialog(context);
//            }

                    Intent i = new Intent(context, DetailMatchesActivity.class);
                    i.putExtra("userid", list.get(position).getId());
                    i.putExtra("profile_list", new Gson().toJson(list));
                    i.putExtra("position", position);
                    context.startActivity(i);

        });


    }

    private void connectApi(String id, String s, View view, ViewHolder holder) {
        Toast.makeText(context, "user id  " + user.getUser_id(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, age_height ,lang ,address;
        LinearLayout connecy_btn;
        FrameLayout matches_fl;
        View img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            age_height = itemView.findViewById(R.id.age_height);
            address = itemView.findViewById(R.id.address);
            lang = itemView.findViewById(R.id.lang);
            img = itemView.findViewById(R.id.img);
            connecy_btn = itemView.findViewById(R.id.connecy_btn);
            matches_fl = itemView.findViewById(R.id.matches_fl);

        }
    }
}


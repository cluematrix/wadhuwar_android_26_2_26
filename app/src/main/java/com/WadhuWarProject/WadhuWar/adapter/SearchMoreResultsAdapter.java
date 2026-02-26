package com.WadhuWarProject.WadhuWar.adapter;

import android.app.AlertDialog;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesForSearchActivity;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.DialogUtil;
import com.WadhuWarProject.WadhuWar.listner.OnRemoveClickListener;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.SaveHistory;
import com.WadhuWarProject.WadhuWar.model.SearchDataMore;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMoreResultsAdapter extends RecyclerView.Adapter<SearchMoreResultsAdapter.ProductViewHolder> {

    private ArrayList<SearchDataMore.SearchDataList> searchDataLists = new ArrayList<>();
    private Context context;
    private FetchProfile user;
    private OnRemoveClickListener removeClickListener;

    public SearchMoreResultsAdapter(Context context, OnRemoveClickListener listener) {
        this.context = context;
        this.removeClickListener = listener;
        this.user = SharedPrefManager.getInstance(context).getProfileData();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mathces_fragment, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (position >= 0 && position < searchDataLists.size()) {
            SearchDataMore.SearchDataList item = searchDataLists.get(position);

            // Set data to views
            holder.name.setText(item.getFirstName());
            holder.more.setVisibility(View.GONE);
            holder.cros.setVisibility(View.VISIBLE);

            // Age and Height
            if (!item.getAge().equals("Not Specified") && !item.getHeight().equals("Not Specified")) {
                holder.age_height.setText(item.getAge() + "yrs, " + item.getHeight());
            } else if (!item.getAge().equals("Not Specified")) {
                holder.age_height.setText(item.getAge() + " yrs");
            } else if (!item.getHeight().equals("Not Specified")) {
                holder.age_height.setText(item.getHeight());
            } else {
                holder.age_height.setText(" ");
            }

            // Job
            if (!item.getOccupation().equals("Not Specified")) {
                holder.job.setText(item.getOccupation());
                holder.job_dot.setVisibility(View.VISIBLE);
            } else {
                holder.job.setText("");
                holder.job_dot.setVisibility(View.GONE);
            }

            // Language and Caste
            if (!item.getMothertounge().equals("Not Specified") && !item.getCaste().equals("Not Specified")) {
                holder.lang.setText(item.getMothertounge() + "," + item.getCaste());
            } else if (!item.getMothertounge().equals("Not Specified")) {
                holder.lang.setText(item.getMothertounge());
            } else if (!item.getCaste().equals("Not Specified")) {
                holder.lang.setText(item.getCaste());
            } else {
                holder.lang.setText(" ");
            }

            // Address
            if (!item.getDistrict().equals("Not Specified") && !item.getState().equals("Not Specified")) {
                holder.address.setText(item.getDistrict() + ", " + item.getState());
                holder.address_dot.setVisibility(View.VISIBLE);
            } else if (!item.getDistrict().equals("Not Specified")) {
                holder.address.setText(item.getDistrict());
                holder.address_dot.setVisibility(View.VISIBLE);
            } else if (!item.getState().equals("Not Specified")) {
                holder.address.setText(item.getState());
                holder.address_dot.setVisibility(View.VISIBLE);
            } else {
                holder.address.setText(" ");
                holder.address_dot.setVisibility(View.GONE);
            }

            // Profile Image
            if (item.getImage() != null && !item.getImage().isEmpty()) {
                holder.img.setVisibility(View.VISIBLE);
                holder.default_Avtar_LL.setVisibility(View.GONE);
                Glide.with(context).load(item.getImage()).into(holder.img);
            } else {
                holder.img.setVisibility(View.GONE);
                holder.default_Avtar_LL.setVisibility(View.VISIBLE);
                if (item.getGender().equals("Female")) {
                    holder.img2.setImageResource(R.drawable.female_avtar);
                } else {
                    holder.img2.setImageResource(R.drawable.male_avtar);
                }
            }

            // Badges
            holder.premiumLL.setVisibility(item.getPremium().equals("1") ? View.VISIBLE : View.GONE);
            holder.just_joineLL.setVisibility(item.getJustJoined().equals("1") ? View.VISIBLE : View.GONE);
            holder.verifiedLL.setVisibility(item.getAccountVerify().equals("1") ? View.VISIBLE : View.GONE);

            // Remove button click
            holder.cros.setOnClickListener(v -> {
                if (removeClickListener != null) {
                    removeClickListener.onRemoveClick(holder.getAdapterPosition(), item);
                }
            });

            // Item click
            holder.matches_fl.setOnClickListener(v -> {
                if (SharedPrefManager.getInstance(context).getString().equals("0")) {
                    if (user.getPremium().equals("1")) {
                        int login_user = SharedPrefManager.getInstance(context).getUser().getUser_id();
                        callSaveHistoryApi(String.valueOf(login_user), item.getId());
                    } else {
                        showPremiumDialog(context);
                    }
                } else {
                    DialogUtil.showAlertDialog(context);
                }
            });
        }
    }

    private void callSaveHistoryApi(String loginUserId, String viewUserId) {
        Api apiService = RetrofitClient.getApiService();
        Call<SaveHistory> call = apiService.getSaveHistory(loginUserId, viewUserId);
        call.enqueue(new Callback<SaveHistory>() {
            @Override
            public void onResponse(Call<SaveHistory> call, Response<SaveHistory> response) {
                Intent i = new Intent(context, DetailMatchesForSearchActivity.class);
                i.putExtra("userid", viewUserId);
                context.startActivity(i);
            }

            @Override
            public void onFailure(Call<SaveHistory> call, Throwable t) {
                Log.e("SaveHistoryError", t.getMessage());
                Intent i = new Intent(context, DetailMatchesForSearchActivity.class);
                i.putExtra("userid", viewUserId);
                context.startActivity(i);
            }
        });
    }

    private void showPremiumDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Premium Required");
        builder.setMessage("To view this profile, you need to upgrade to Premium.");
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Get Premium", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(context, PremiumActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return searchDataLists.size();
    }

    public ArrayList<SearchDataMore.SearchDataList> getSearchDataList() {
        return searchDataLists;
    }

    // Add this method to remove item
    public void removeItem(int position) {
        if (position >= 0 && position < searchDataLists.size()) {
            searchDataLists.remove(position);
            notifyItemRemoved(position);
            // Update positions for remaining items
            notifyItemRangeChanged(position, getItemCount() - position);
        }
    }

    // Add all data at once
    public void addAll(List<SearchDataMore.SearchDataList> list) {
        int oldSize = searchDataLists.size();
        searchDataLists.clear();
        searchDataLists.addAll(list);
        notifyDataSetChanged();
    }

    // Clear all data
    public void clear() {
        searchDataLists.clear();
        notifyDataSetChanged();
    }

    public SearchDataMore.SearchDataList getItem(int position) {
        return searchDataLists.get(position);
    }

    // ViewHolder class
    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, online, you_, age_height, job, lang, address;
        LinearLayout connecy_btn, onlineLL, premiumLL, just_joineLL, verifiedLL, default_Avtar_LL, btn_block_report;
        ImageView img, img2;
        CardView card_view;
        FrameLayout matches_fl;
        CardView bubble_v;
        ImageView job_dot, address_dot, cros, more;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            card_view = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.name);
            online = itemView.findViewById(R.id.online);
            you_ = itemView.findViewById(R.id.you_);
            age_height = itemView.findViewById(R.id.age_height);
            job = itemView.findViewById(R.id.job);
            lang = itemView.findViewById(R.id.lang);
            address = itemView.findViewById(R.id.address);
            connecy_btn = itemView.findViewById(R.id.connecy_btn);
            img = itemView.findViewById(R.id.img);
            onlineLL = itemView.findViewById(R.id.onlineLL);
            matches_fl = itemView.findViewById(R.id.matches_fl);
            bubble_v = itemView.findViewById(R.id.bubble_v);
            premiumLL = itemView.findViewById(R.id.premiumLL);
            just_joineLL = itemView.findViewById(R.id.just_joineLL);
            verifiedLL = itemView.findViewById(R.id.verifiedLL);
            default_Avtar_LL = itemView.findViewById(R.id.default_Avtar_LL);
            img2 = itemView.findViewById(R.id.img2);
            job_dot = itemView.findViewById(R.id.job_dot);
            address_dot = itemView.findViewById(R.id.address_dot);
            btn_block_report = itemView.findViewById(R.id.btn_block_report);
            cros = itemView.findViewById(R.id.cros);
            more = itemView.findViewById(R.id.more);
        }
    }
}
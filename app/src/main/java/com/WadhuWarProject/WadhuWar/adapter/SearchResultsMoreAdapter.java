package com.WadhuWarProject.WadhuWar.adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.WadhuWarProject.WadhuWar.extras.BlurTransformation;
import com.WadhuWarProject.WadhuWar.extras.DialogUtil;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.SaveHistory;
import com.WadhuWarProject.WadhuWar.model.SearchData;
import com.WadhuWarProject.WadhuWar.model.SearchDataMore;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<SearchDataMore.SearchDataList> searchDataLists ;
    ArrayList<SearchDataMore.SearchDataList> searchDataListsMore ;
    Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    FetchProfile user;
//    SharedPrefManager sharedPrefManager;

    public SearchResultsMoreAdapter(Context context){
        this.context = context;
        this.searchDataListsMore =  new ArrayList<SearchDataMore.SearchDataList>();
        user = SharedPrefManager.getInstance(context).getProfileData();

    }

    public ArrayList<SearchDataMore.SearchDataList> getSearchDataList() {
        return searchDataListsMore;
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
        View v1 = inflater.inflate(R.layout.item_mathces_fragment, parent, false);
        viewHolder = new ProductViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, @SuppressLint("RecyclerView") final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;


                if(searchDataListsMore!=null && searchDataListsMore.size()!=0) {

                    holder.name.setText(searchDataListsMore.get(position).getName());


                    if (!searchDataListsMore.get(position).getAge().contentEquals("Not Specified") &&
                            !searchDataListsMore.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(String.format("%syrs, %s,", searchDataListsMore.get(position).getAge(), searchDataListsMore.get(position).getHeight()));

                    } else if (!searchDataListsMore.get(position).getAge().contentEquals("Not Specified") &&
                            searchDataListsMore.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(searchDataListsMore.get(position).getAge()+" yrs");

                    } else if (searchDataListsMore.get(position).getAge().contentEquals("Not Specified") &&
                            !searchDataListsMore.get(position).getHeight().contentEquals("Not Specified")) {
                        holder.age_height.setText(searchDataListsMore.get(position).getHeight());

                    } else {
                        holder.age_height.setText(" ");
                    }

                    if (!searchDataListsMore.get(position).getOccupation().contentEquals("Not Specified")) {
                        holder.job.setText(searchDataListsMore.get(position).getOccupation());
                        holder.job_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.job.setText("");
                        holder.job_dot.setVisibility(View.GONE);

                    }


                    if (!searchDataListsMore.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !searchDataListsMore.get(position).getCaste().contentEquals("Not Specified")) {
                        holder.lang.setText(String.format("%s,%s", searchDataListsMore.get(position).getMothertounge(), searchDataListsMore.get(position).getCaste()));

                    } else if (!searchDataListsMore.get(position).getMothertounge().contentEquals("Not Specified") &&
                            searchDataListsMore.get(position).getCaste().contentEquals("Not Specified")) {
                        holder.lang.setText(searchDataListsMore.get(position).getMothertounge());

                    } else if (searchDataListsMore.get(position).getMothertounge().contentEquals("Not Specified") &&
                            !searchDataListsMore.get(position).getCaste().contentEquals("Not Specified")) {
                        holder.lang.setText(searchDataListsMore.get(position).getCaste());

                    } else {
                        holder.lang.setText(" ");

                    }

                    System.out.println("premiunMatchesListData.get(position).getDistrict()---" + searchDataListsMore.get(position).getDistrict());
                    System.out.println("premiunMatchesListData.get(position).getState()---" + searchDataListsMore.get(position).getState());

                    if (!searchDataListsMore.get(position).getDistrict().contentEquals("Not Specified") &&
                            !searchDataListsMore.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(String.format("%s, %s", searchDataListsMore.get(position).getDistrict(), searchDataListsMore.get(position).getState()));
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else if (!searchDataListsMore.get(position).getDistrict().contentEquals("Not Specified") &&
                            searchDataListsMore.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(searchDataListsMore.get(position).getDistrict());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else if (searchDataListsMore.get(position).getDistrict().contentEquals("Not Specified") &&
                            !searchDataListsMore.get(position).getState().contentEquals("Not Specified")) {
                        holder.address.setText(searchDataListsMore.get(position).getState());
                        holder.address_dot.setVisibility(View.VISIBLE);

                    } else {
                        holder.address.setText(" ");
                        holder.address_dot.setVisibility(View.GONE);

                    }


           /* if (searchDataLists.get(position).getImage() != null) {
                if (searchDataLists.get(position).getImage().equals("")) {
                    Glide.with(context).load(R.drawable.default_avtar).into(holder.img);


                } else {
                    Glide.with(context).load(searchDataLists.get(position).getImage()).into(holder.img);

                }
            } else {
                Glide.with(context).load(R.drawable.default_avtar).into(holder.img);

            }
*/

                    if (searchDataListsMore.get(position).getImage() != null) {
                        if (searchDataListsMore.get(position).getImage().equals("")) {
                            holder.img.setVisibility(View.GONE);
                            holder.default_Avtar_LL.setVisibility(View.VISIBLE);


                            if(searchDataListsMore.get(position).getGender().equals("Female")){
                                holder.img2.setImageResource(R.drawable.female_avtar);
                            }else {
                                holder.img2.setImageResource(R.drawable.male_avtar);
                            }

                        } else {

                            holder.default_Avtar_LL.setVisibility(View.GONE);
                            holder.img.setVisibility(View.VISIBLE);
                            Glide.with(context.getApplicationContext()).load(searchDataListsMore.get(position).getImage()).into(holder.img);

                        }
                    } else {

                        holder.img.setVisibility(View.GONE);
                        holder.default_Avtar_LL.setVisibility(View.VISIBLE);

                        if(searchDataListsMore.get(position).getGender().equals("Female")){
                            holder.img2.setImageResource(R.drawable.female_avtar);
                        }else {
                            holder.img2.setImageResource(R.drawable.male_avtar);
                        }
                    }




                    if (searchDataListsMore.get(position).getChkonline().equals("1")) {
                        holder.onlineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.onlineLL.setVisibility(View.GONE);
                    }

                    if (searchDataListsMore.get(position).getPremium().equals("1")) {
                        holder.premiumLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.premiumLL.setVisibility(View.GONE);
                    }

                    if (searchDataListsMore.get(position).getJustJoined().equals("1")) {
                        holder.just_joineLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.just_joineLL.setVisibility(View.GONE);
                    }

                    if (searchDataListsMore.get(position).getAccountVerify().equals("1")) {
                        holder.verifiedLL.setVisibility(View.VISIBLE);
                    } else {
                        holder.verifiedLL.setVisibility(View.GONE);
                    }

                    if (searchDataListsMore.get(position).getIsDelete().equals("2")) {
                        Glide.with(context.getApplicationContext()).
                                load(searchDataListsMore.get(position).getImage())
                                .transform(new BlurTransformation(context, 25))
                                .into(holder.img);

                    }

//                    holder.matches_fl.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                            Toast.makeText(context, "premium : " + user.getPremium(), Toast.LENGTH_SHORT).show();
//                            if (SharedPrefManager.getInstance(context).getString().equals("0")) {
//                                if(user.getPremium().equals("1")) {
//
//                                    if (searchDataLists.get(position).getIsDelete().equals("2")) {
//                                        Toast.makeText(context, "Profile is Hidden", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Intent i = new Intent(context, DetailMatchesForSearchActivity.class);
//                                        i.putExtra("userid", searchDataLists.get(position).getId());
//                                        context.startActivity(i);
//
//                                    }
//                                } else{
////                                    Toast.makeText(context, "For see this Profile You have to get premioun  !", Toast.LENGTH_SHORT).show();
//                                    showPremiumDialog(context);
//                                }
//                            }else {
//                                DialogUtil.showAlertDialog(context);
//                            }
//                        }
//                    });

                    holder.matches_fl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (SharedPrefManager.getInstance(context).getString().equals("0")) {

                                if (user.getPremium().equals("1")) {

                                    if (searchDataListsMore.get(position).getIsDelete().equals("2")) {
                                        Toast.makeText(context, "Profile is Hidden", Toast.LENGTH_SHORT).show();

                                    } else {
                                        int login_user =  SharedPrefManager.getInstance(context).getUser().getUser_id();
                                        // 🔹 CALL SAVE HISTORY API
                                        callSaveHistoryApi(String.valueOf(login_user), searchDataListsMore.get(position).getId(), position);
                                    }

                                } else {
                                    showPremiumDialog(context);
                                }

                            } else {
                                DialogUtil.showAlertDialog(context);
                            }
                        }
                    });

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

    private void callSaveHistoryApi(String loginUserId, String viewUserId, int position) {
        Api apiService = RetrofitClient.getApiService();
        Call<SaveHistory> call = apiService.getSaveHistory(loginUserId, viewUserId);
        call.enqueue(new Callback<SaveHistory>() {
            @Override
            public void onResponse(Call<SaveHistory> call, Response<SaveHistory> response) {

                if (response.isSuccessful() && response.body() != null) {

                    SaveHistory saveHistory = response.body();

//                    if (saveHistory.getResid().equals("200")) {

                        // ✅ AFTER SUCCESS → OPEN DETAILS SCREEN
                        Intent i = new Intent(context, DetailMatchesForSearchActivity.class);
                        i.putExtra("userid", viewUserId);
                        context.startActivity(i);

//                    } else {
//                        Toast.makeText(context, saveHistory.getResMsg(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }



            @Override
            public void onFailure(Call<SaveHistory> call, Throwable t) {
                Log.e("SaveHistoryError", t.getMessage());

                // Even if API fails, you can still open profile (optional)
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

        // ❌ Cancel
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // ⭐ Get Premium
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
    public int getItemCount () {


        if (searchDataListsMore != null) {
            if (searchDataListsMore.size() == 0) {
                return 1;
            } else {
                return searchDataListsMore.size();
            }
        } else {
            return 0;
        }


    }


   /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(SearchDataMore.SearchDataList mc) {
        searchDataListsMore.add(mc);
        notifyItemInserted(searchDataListsMore.size() - 1);
    }


    public void addAll(ArrayList<SearchDataMore.SearchDataList> mcList) {
        for (SearchDataMore.SearchDataList mc : mcList) {
            add(mc);

        }
    }





    public void remove(SearchDataMore.SearchDataList p) {
        int position = searchDataListsMore.indexOf(p);
        if (position > -1) {
            searchDataListsMore.remove(position);
            notifyItemRemoved(position);
        }
    }

//    public void clear() {
//        isLoadingAdded = false;
//        while (getItemCount() > 0) {
//            remove(getItem(0));
//        }
//    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;

        add(new SearchDataMore.SearchDataList());
    }



//    public void removeLoadingFooter() {
//        isLoadingAdded = false;
//
//        int position = searchDataListsMore.size() - 1;
//        SearchData.SearchDataList item = getItem(position);
//
//        if (item != null) {
//            searchDataListsMore.remove(position);
//            notifyItemRemoved(position);
//        }
//    }

    public SearchDataMore.SearchDataList getItem(int position) {
        return searchDataListsMore.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */




    protected  class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView name,online,you_,age_height,job,lang,address;
        LinearLayout connecy_btn,onlineLL,premiumLL,just_joineLL,verifiedLL,default_Avtar_LL;
        ImageView img,img2;
        CardView card_view;
        FrameLayout matches_fl;
        CardView bubble_v;
        ImageView job_dot,address_dot;

        //            CardView card;
        public ProductViewHolder(View itemView) {
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
            premiumLL =  itemView.findViewById(R.id.premiumLL);
            just_joineLL =  itemView.findViewById(R.id.just_joineLL);
            verifiedLL =  itemView.findViewById(R.id.verifiedLL);
            default_Avtar_LL =  itemView.findViewById(R.id.default_Avtar_LL);
            img2 =  itemView.findViewById(R.id.img2);
            job_dot =  itemView.findViewById(R.id.job_dot);
            address_dot =  itemView.findViewById(R.id.address_dot);


        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar loadmore_progress;
        public LoadingVH(View itemView) {
            super(itemView);


            loadmore_progress = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
        }
    }



    @Override
    public int getItemViewType(int position) {
        return (position == searchDataListsMore.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }





}
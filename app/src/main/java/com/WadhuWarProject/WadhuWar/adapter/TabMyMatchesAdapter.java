package com.WadhuWarProject.WadhuWar.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.AccomodationDetailActivity;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.EducationCareerActivity;
import com.WadhuWarProject.WadhuWar.activity.MyProfileActivity;
import com.WadhuWarProject.WadhuWar.activity.ReasonForReportActivity;
import com.WadhuWarProject.WadhuWar.activity.ReligiousBackgroundActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.BlurTransformation;
import com.WadhuWarProject.WadhuWar.extras.DialogUtil;
import com.WadhuWarProject.WadhuWar.model.CommonResponse;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// todo:: this file last updated on 17 Apr 2025 by sagar . its final version of the code working as expected
public class TabMyMatchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private InsertResponse connect_response;
    private UserData user;
    private static boolean isNetworkAvailable;
    private ArrayList<TabMyMatchesList.TabMyMatchesListData> tabMyMatchesListData = new ArrayList<>();
    private ArrayList<FetchProfile> fetchProfiles = new ArrayList<>();
    private Context context;
    private String people_userid;
    private SharedPrefManager sharedPrefManager;
    private static final int ITEM = 0;
    private static final int WARNING = 1;
    private static final int LOADING = 2;
    private boolean isLoadingAdded = false;
    private static final int ITEMS_PER_WARNING = 3; // Kept for reference but not used
    private List<String> allWarnings = new ArrayList<>();
    private List<Integer> warningPositions = new ArrayList<>();
    private Random random = new Random();
    public ImageView img;

    public TabMyMatchesAdapter(Context context, ArrayList<FetchProfile> profileList,
                               ArrayList<TabMyMatchesList.TabMyMatchesListData> tabMyMatchesListData) {
        this.context = context;
        this.sharedPrefManager = SharedPrefManager.getInstance(context);
        this.tabMyMatchesListData = (tabMyMatchesListData != null) ? new ArrayList<>() : new ArrayList<>();
        if (tabMyMatchesListData != null) {
            for (TabMyMatchesList.TabMyMatchesListData item : tabMyMatchesListData) {
                if (item != null) {
                    this.tabMyMatchesListData.add(item);
                }
            }
        }

        this.fetchProfiles = (profileList != null) ? new ArrayList<>(profileList) : new ArrayList<>();
        Log.d("1234", "Constructor - tabMyMatchesListData size: " + this.tabMyMatchesListData.size());
        Log.d("1234", "Constructor - fetchProfiles size: " + this.fetchProfiles.size());
        updateWarnings();
        setRandomWarningPositions();
    }

    private void setRandomWarningPositions() {
        warningPositions.clear();
        if (tabMyMatchesListData.isEmpty() || allWarnings.isEmpty()) {
            Log.d("WarningPosition", "No warnings or data, clearing warning positions");
            return;
        }

        int totalSlots = tabMyMatchesListData.size() + allWarnings.size();
        Set<Integer> positions = new HashSet<>();
        while (positions.size() < allWarnings.size()) {
            int pos = random.nextInt(totalSlots);
            positions.add(pos);
        }

        warningPositions = new ArrayList<>(positions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            warningPositions.sort(Integer::compareTo);
        }
        Log.d("WarningPosition", "Set warning positions: " + warningPositions);
    }

    private void validateWarningPositions() {
        if (tabMyMatchesListData.isEmpty() || allWarnings.isEmpty()) {
            warningPositions.clear();
            Log.d("WarningPosition", "Cleared warning positions due to empty data or warnings");
            return;
        }

        int totalSlots = tabMyMatchesListData.size() + allWarnings.size();
// Check if current positions are valid
        boolean valid = false; // No duplicates
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            valid = warningPositions.size() == allWarnings.size() &&
                    warningPositions.stream().allMatch(pos -> pos >= 0 && pos < totalSlots) &&
                    new HashSet<>(warningPositions).size() == warningPositions.size();
        }

        if (!valid) {
            setRandomWarningPositions();
        } else {
            Log.d("WarningPosition", "Retained warning positions: " + warningPositions);
        }
    }

    public void setFetchProfiles(List<FetchProfile> profiles) {
        if (profiles == null) {
            Log.w("AdapterUpdate", "setFetchProfiles received null profiles");
            this.fetchProfiles.clear();
        } else {
            this.fetchProfiles.clear();
            this.fetchProfiles.addAll(profiles);
        }
        Log.d("AdapterUpdate", "setFetchProfiles - new size: " + fetchProfiles.size());
        updateWarnings();
        validateWarningPositions();
        notifyDataSetChanged();
    }

    private void updateWarnings() {
        allWarnings.clear();
        if (fetchProfiles == null || fetchProfiles.isEmpty() || fetchProfiles.get(0) == null) {
            Log.w("WarningsUpdate", "No valid profile data to generate warnings");
            return;
        }

        FetchProfile profile = fetchProfiles.get(0);
        Map<String, Boolean> fieldStatus = new LinkedHashMap<>();
        fieldStatus.put("Add your Sampraday Name", profile.getSampraday_name() == null || profile.getSampraday_name().isEmpty() || profile.getSampraday_name().equals("Not Specified"));
        fieldStatus.put("Add your Rashi Name", profile.getRashi_name() == null || profile.getRashi_name().isEmpty() || profile.getRashi_name().equals("Not Specified"));
        fieldStatus.put("Add your Current Address", profile.getCurrent_address() == null || profile.getCurrent_address().isEmpty() || profile.getCurrent_address().equals("Not Specified"));
        fieldStatus.put("Add your Current District", profile.getCurr_ditrict_name() == null || profile.getCurr_ditrict_name().isEmpty() || profile.getCurr_ditrict_name().equals("Not Specified"));
        fieldStatus.put("Add your Occupation Name", profile.getMain_occupation_name() == null || profile.getMain_occupation_name().isEmpty() || profile.getMain_occupation_name().equals("Not Specified"));
        fieldStatus.put("Add your Post Name", profile.getPost_name() == null || profile.getPost_name().isEmpty() || profile.getPost_name().equals("Not Specified"));
        fieldStatus.put("Add your Office Address", profile.getOfc_address() == null || profile.getOfc_address().isEmpty() || profile.getOfc_address().equals("Not Specified"));
        fieldStatus.put("Add your Office Location", profile.getOfc_loc() == null || profile.getOfc_loc().isEmpty() || profile.getOfc_loc().equals("Not Specified"));
        fieldStatus.put("Add your Office District", profile.getOfc_ditrict_name() == null || profile.getOfc_ditrict_name().isEmpty() || profile.getOfc_ditrict_name().equals("Not Specified"));
        fieldStatus.put("Add your Current Yearly Salary", profile.getYearly_salary() == null || profile.getYearly_salary().isEmpty() || profile.getYearly_salary().equals("Select Salary"));
        fieldStatus.put("Add your Verification Document", profile.getVerification_doc() == null || profile.getVerification_doc().isEmpty() || profile.getVerification_doc().equals("Select Document"));

        for (Map.Entry<String, Boolean> entry : fieldStatus.entrySet()) {
            if (entry.getValue()) {
                allWarnings.add(entry.getKey());
            }
        }


        Log.d("WarningsUpdate", "Warnings count: " + allWarnings.size() + ", List: " + allWarnings);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == WARNING) {
            View view = inflater.inflate(R.layout.item_warning, parent, false);
            return new WarningViewHolder(view);
        } else if (viewType == LOADING) {
            View view = inflater.inflate(R.layout.item_progress, parent, false);
            return new LoadingViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_mathces_fragment, parent, false);
            return new ProductViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Log.d("BindPosition", "Position: " + position + ", ViewType: " + (viewType == ITEM ? "ITEM" : viewType == WARNING ? "WARNING" : "LOADING"));

        if (holder instanceof WarningViewHolder) {
            int warningIndex = getWarningIndex(position);
            bindItemViewHolderWar((WarningViewHolder) holder, warningIndex);
        } else if (holder instanceof ProductViewHolder) {
            int actualPosition = getActualPosition(position);
            if (actualPosition >= 0 && actualPosition < tabMyMatchesListData.size() && tabMyMatchesListData.get(actualPosition) != null) {
                bindItemViewHolder((ProductViewHolder) holder, actualPosition);
                Log.d("BindItem", "Binding item at position: " + position + ", actualPosition: " + actualPosition + ", name: " + tabMyMatchesListData.get(actualPosition).getName());
            } else {
                Log.e("BindError", "Invalid actualPosition: " + actualPosition + " or null item for position: " + position + ", size: " + tabMyMatchesListData.size());
                holder.itemView.setVisibility(View.GONE);
            }
        } else if (holder instanceof LoadingViewHolder) {
            Log.d("BindLoading", "Binding loading footer at position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        int totalItems = tabMyMatchesListData.size();
        int warningCount = allWarnings.size();
        int totalCount = totalItems + warningCount + (isLoadingAdded ? 1 : 0);
        Log.d("ItemCount", "Total items: " + totalItems + ", Warnings: " + warningCount + ", Loading: " + isLoadingAdded + ", Total count: " + totalCount);
        return totalCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadingAdded && position == getItemCount() - 1) {
            Log.d("ViewType", "Position: " + position + " -> LOADING");
            return LOADING;
        }
        if (!tabMyMatchesListData.isEmpty() && !allWarnings.isEmpty() && warningPositions.contains(position)) {
            Log.d("ViewType", "Position: " + position + " -> WARNING");
            return WARNING;
        }
        Log.d("ViewType", "Position: " + position + " -> ITEM");
        return ITEM;
    }

    private int getActualPosition(int position) {
        if (warningPositions.isEmpty()) {
            return position;
        }

        int warningsBefore = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            warningsBefore = (int) warningPositions.stream().filter(p -> p < position).count();
        }
        int actualPosition = position - warningsBefore;
        if (actualPosition >= tabMyMatchesListData.size()) {
            Log.w("ActualPosition", "Position: " + position + " adjusted to " + (tabMyMatchesListData.size() - 1));
            actualPosition = tabMyMatchesListData.size() - 1;
        }
        if (actualPosition < 0) {
            actualPosition = 0;
        }
        Log.d("ActualPosition", "Position: " + position + ", WarningsBefore: " + warningsBefore + ", ActualPosition: " + actualPosition);
        return actualPosition;
    }

    private int getWarningIndex(int position) {
        int warningIndex = warningPositions.indexOf(position);
        if (warningIndex < 0 || warningIndex >= allWarnings.size()) {
            Log.w("WarningIndex", "Invalid warning index for position: " + position + ", using 0");
            warningIndex = 0;
        }
        Log.d("WarningIndex", "Position: " + position + ", WarningIndex: " + warningIndex);
        return warningIndex;
    }

    // Helper Methods
    public ArrayList<TabMyMatchesList.TabMyMatchesListData> tabMyMatchesListData() {
        return tabMyMatchesListData;
    }

    public ArrayList<FetchProfile> fetchProfiles() {
        Log.d("FetchProfile", "fetchProfiles size: " + fetchProfiles.size());
        return fetchProfiles;
    }

    public void add(TabMyMatchesList.TabMyMatchesListData mc) {
        if (mc != null) {
            tabMyMatchesListData.add(mc);
            validateWarningPositions();
            notifyItemInserted(tabMyMatchesListData.size() - 1);
        }
    }

    public void addAll(ArrayList<TabMyMatchesList.TabMyMatchesListData> mcList, ArrayList<FetchProfile> profiles) {
        if (mcList == null || profiles == null) {
            Log.w("AdapterUpdate", "addAll received null mcList or profiles");
            return;
        }
        int oldSize = tabMyMatchesListData.size();
        for (TabMyMatchesList.TabMyMatchesListData item : mcList) {
            if (item != null) {
                tabMyMatchesListData.add(item);
            }
        }
        fetchProfiles.addAll(profiles);
        Log.d("dataProfiles123", fetchProfiles.toString());
        Log.d("AdapterUpdate", "addAll - old size: " + oldSize + ", new size: " + tabMyMatchesListData.size());
        updateWarnings();
        validateWarningPositions();
        notifyDataSetChanged();
    }

    public void remove(TabMyMatchesList.TabMyMatchesListData p) {
        int position = tabMyMatchesListData.indexOf(p);
        if (position > -1) {
            tabMyMatchesListData.remove(position);
            validateWarningPositions();
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        tabMyMatchesListData.clear();
        fetchProfiles.clear();
        allWarnings.clear();
        warningPositions.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return tabMyMatchesListData.isEmpty() && !isLoadingAdded && warningPositions.isEmpty();
    }

    public void addLoadingFooter() {
        if (!isLoadingAdded && tabMyMatchesListData.size() > 0) {
            isLoadingAdded = true;
            notifyItemInserted(getItemCount());
            Log.d("Adapter", "Added loading footer");
        }
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;
            notifyItemRemoved(getItemCount());
            Log.d("Adapter", "Removed loading footer");
        }
    }

    public TabMyMatchesList.TabMyMatchesListData getItem(int position) {
        if (position >= 0 && position < tabMyMatchesListData.size()) {
            return tabMyMatchesListData.get(position);
        }
        return null;
    }

    // View Holders
    static class WarningViewHolder extends RecyclerView.ViewHolder {
        TextView warningText;
        TextView tvClickHere;
        ImageView img1;

        public WarningViewHolder(@NonNull View itemView) {
            super(itemView);
            warningText = itemView.findViewById(R.id.warning_text);
            tvClickHere = itemView.findViewById(R.id.tv_click_here);
            img1 = itemView.findViewById(R.id.img);

        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ProductViewHolder extends RecyclerView.ViewHolder {
        FrameLayout LL, LL1, matches_fl;
        ProgressBar connect_btn_pb;
        TextView name, online, you_, age_height, job, lang, address, img_txt, like_profile_txt, connect_btn_txt;
        LinearLayout connecy_btn, onlineLL, just_joineLL, verifiedLL, premiumLL, btn_block_report, imgLL, default_Avtar_LL;
        ImageView img, img2, job_dot, address_dot;
        CardView card_view, bubble_v;

        public ProductViewHolder(View itemView) {
            super(itemView);
            connecy_btn = itemView.findViewById(R.id.connecy_btn);
            LL1 = itemView.findViewById(R.id.LL1);
            LL = itemView.findViewById(R.id.LL);
            connect_btn_pb = itemView.findViewById(R.id.connect_btn_pb);
            connect_btn_txt = itemView.findViewById(R.id.connect_btn_txt);
            like_profile_txt = itemView.findViewById(R.id.like_profile_txt);
            card_view = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.name);
            online = itemView.findViewById(R.id.online);
            you_ = itemView.findViewById(R.id.you_);
            age_height = itemView.findViewById(R.id.age_height);
            job = itemView.findViewById(R.id.job);
            lang = itemView.findViewById(R.id.lang);
            address = itemView.findViewById(R.id.address);
            img = itemView.findViewById(R.id.img);
            onlineLL = itemView.findViewById(R.id.onlineLL);
            matches_fl = itemView.findViewById(R.id.matches_fl);
            bubble_v = itemView.findViewById(R.id.bubble_v);
            just_joineLL = itemView.findViewById(R.id.just_joineLL);
            verifiedLL = itemView.findViewById(R.id.verifiedLL);
            premiumLL = itemView.findViewById(R.id.premiumLL);
            imgLL = itemView.findViewById(R.id.imgLL);
            img_txt = itemView.findViewById(R.id.img_txt);
            default_Avtar_LL = itemView.findViewById(R.id.default_Avtar_LL);
            img2 = itemView.findViewById(R.id.img2);
            job_dot = itemView.findViewById(R.id.job_dot);
            address_dot = itemView.findViewById(R.id.address_dot);
            btn_block_report = itemView.findViewById(R.id.btn_block_report);
        }
    }

    // Binding Methods
    public void bindItemViewHolderWar(WarningViewHolder holder, int warningIndex) {
        if (holder.warningText == null || holder.tvClickHere == null) {
            Log.e("BindError", "warningText or tvClickHere is null in WarningViewHolder at position: " + holder.getAdapterPosition());
            holder.itemView.setVisibility(View.GONE);
            return;
        }

        if (allWarnings.isEmpty() || warningIndex < 0 || warningIndex >= allWarnings.size()) {
            Log.w("BindWarning", "No valid warning at index: " + warningIndex);
            holder.warningText.setText("");
            holder.itemView.setVisibility(View.GONE);
            return;
        }

        String warningText = allWarnings.get(warningIndex);
        holder.warningText.setText(warningText);
        holder.itemView.setVisibility(View.VISIBLE);
        Log.d("BindWarning", "Set warning: " + warningText + " at position: " + holder.getAdapterPosition());

        // Set different images based on warning text
        switch (warningText) {
            case "Add your Sampraday Name":
                holder.img1.setImageResource(R.drawable.sampr_icon); // Replace with your drawable
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Rashi Name":
                holder.img1.setImageResource(R.drawable.rashi_icon); // Replace with your drawable
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Current Address":
                holder.img1.setImageResource(R.drawable.ofc_add_icon); // Replace with your drawable
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Current District":
                holder.img1.setImageResource(R.drawable.ofc_add_icon); // Replace with your drawable
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Verification Document":
                holder.img1.setImageResource(R.drawable.verification_icon); // Replace with your drawable
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Occupation Name":
                holder.img1.setImageResource(R.drawable.occ_icon); // Existing drawable from your example
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Post Name":
                holder.img1.setImageResource(R.drawable.job_post); // Existing drawable from your example
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Office Address":
                holder.img1.setImageResource(R.drawable.ofc_add_icon); // Existing drawable from your example
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Office Location":
                holder.img1.setImageResource(R.drawable.ofc_add_icon); // Replace with your drawable
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Office District":
                holder.img1.setImageResource(R.drawable.ofc_add_icon); // Replace with your drawable
                holder.img1.setVisibility(View.VISIBLE);
                break;
            case "Add your Current Yearly Salary":
                holder.img1.setImageResource(R.drawable.salary_icon); // Replace with your drawable
                holder.img1.setVisibility(View.VISIBLE);
                break;
            default:
                holder.img1.setVisibility(View.GONE); // Hide image for unspecified warnings
                // Or set a default: holder.img1.setImageResource(R.drawable.default_icon);
                break;
        }


        holder.tvClickHere.setOnClickListener(v -> {
            Intent intent;
            switch (warningText) {
                case "Add your Sampraday Name":
                case "Add your Rashi Name":
                    intent = new Intent(context, ReligiousBackgroundActivity.class);
                    break;
                case "Add your Current Address":
                case "Add your Current District":
                    intent = new Intent(context, AccomodationDetailActivity.class);
                    break;

                case "Add your Verification Document":
                    intent = new Intent(context, MyProfileActivity.class);
                    intent.putExtra("showVerificationDocCertificateDialog",true);
//                    showVerificationDocCertificateDialog();
                    break;

                case "Add your Occupation Name":
                case "Add your Post Name":
                case "Add your Office Address":
                case "Add your Office Location":
                case "Add your Office District":
                case "Add your Current Yearly Salary":
                    intent = new Intent(context, EducationCareerActivity.class);
                    break;
                default:
                    intent = new Intent(context, MyProfileActivity.class);
                    break;
            }
            context.startActivity(intent);
        });
    }

    private void bindItemViewHolder(ProductViewHolder holder, int actualPosition) {
        if (sharedPrefManager == null || (user = sharedPrefManager.getUser()) == null) {
            Log.e("BindError", "SharedPrefManager or User is null");
            holder.itemView.setVisibility(View.GONE);
            return;
        }
        if (holder.connect_btn_pb == null || holder.name == null || holder.img == null || holder.connect_btn_txt == null) {
            Log.e("BindError", "One or more views in ProductViewHolder are null at position: " + actualPosition);
            holder.itemView.setVisibility(View.GONE);
            return;
        }
        if (tabMyMatchesListData == null || actualPosition < 0 || actualPosition >= tabMyMatchesListData.size() || tabMyMatchesListData.get(actualPosition) == null) {
            Log.e("BindError", "Invalid data at actualPosition: " + actualPosition + ", size: " + (tabMyMatchesListData == null ? "null" : tabMyMatchesListData.size()));
            holder.itemView.setVisibility(View.GONE);
            return;
        }

        TabMyMatchesList.TabMyMatchesListData item = tabMyMatchesListData.get(actualPosition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            holder.connect_btn_pb.getIndeterminateDrawable().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.RED, BlendModeCompat.MULTIPLY));
        }
        holder.itemView.setVisibility(View.VISIBLE);
        people_userid = item.getId();

        String isConnected = item.getIs_connected();
        if ("1".equals(isConnected)) {
            holder.like_profile_txt.setText("");
            holder.connect_btn_txt.setText("Connected");
        } else if ("0".equals(isConnected) || "3".equals(isConnected)) {
            holder.like_profile_txt.setText("Like this Profile?");
            holder.connect_btn_txt.setText("Connect Now");
            holder.connecy_btn.setOnClickListener(view -> connectApi(item.getId(), String.valueOf(user.getUser_id()), view, holder));
        } else if ("2".equals(isConnected)) {
            holder.like_profile_txt.setText("");
            holder.connect_btn_txt.setText("Request Sent");
        } else {
            holder.like_profile_txt.setText("Like this Profile?");
            holder.connect_btn_txt.setText("Connect Now");
            holder.connecy_btn.setOnClickListener(view -> connectApi(item.getId(), String.valueOf(user.getUser_id()), view, holder));
        }

        String imgCount = item.getImg_count();
        if (imgCount != null && !"0".equals(imgCount)) {
            holder.imgLL.setVisibility(View.VISIBLE);
            holder.img_txt.setText(imgCount);
        } else {
            holder.imgLL.setVisibility(View.GONE);
        }

        holder.name.setText(item.getName() != null ? item.getName() : "Unknown");

        String age = item.getAge();
        String height = item.getHeight();
        if (age != null && height != null && !"Not Specified".equals(age) && !"Not Specified".equals(height)) {
            holder.age_height.setText(String.format("%syrs, %s", age, height));
        } else if (age != null && !"Not Specified".equals(age)) {
            holder.age_height.setText(age + " yrs");
        } else if (height != null && !"Not Specified".equals(height)) {
            holder.age_height.setText(height);
        } else {
            holder.age_height.setText("");
        }

        String occupation = item.getOccupation();
        if (occupation != null && !"Not Specified".equals(occupation)) {
            holder.job.setText(occupation);
            holder.job_dot.setVisibility(View.VISIBLE);
        } else {
            holder.job.setText("");
            holder.job_dot.setVisibility(View.GONE);
        }

        String motherTongue = item.getMothertounge();
        String caste = item.getCaste();
        if (motherTongue != null && caste != null && !"Not Specified".equals(motherTongue) && !"Not Specified".equals(caste)) {
            holder.lang.setText(String.format("%s, %s", motherTongue, caste));
        } else if (motherTongue != null && !"Not Specified".equals(motherTongue)) {
            holder.lang.setText(motherTongue);
        } else if (caste != null && !"Not Specified".equals(caste)) {
            holder.lang.setText(caste);
        } else {
            holder.lang.setText("");
        }

        String district = item.getDistrict();
        String state = item.getState();
        if (district != null && state != null && !"Not Specified".equals(district) && !"Not Specified".equals(state)) {
            holder.address.setText(String.format("%s, %s", district, state));
            holder.address_dot.setVisibility(View.VISIBLE);
        } else if (district != null && !"Not Specified".equals(district)) {
            holder.address.setText(district);
            holder.address_dot.setVisibility(View.VISIBLE);
        } else if (state != null && !"Not Specified".equals(state)) {
            holder.address.setText(state);
            holder.address_dot.setVisibility(View.VISIBLE);
        } else {
            holder.address.setText("");
            holder.address_dot.setVisibility(View.GONE);
        }

        String image = item.getImage();
        if (image != null && !image.isEmpty()) {
            holder.default_Avtar_LL.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            Glide.with(context).load(image).into(holder.img);
        } else {
            holder.img.setVisibility(View.GONE);
            holder.default_Avtar_LL.setVisibility(View.VISIBLE);
            holder.img2.setImageResource(item.getGender() != null && "Female".equals(item.getGender()) ? R.drawable.female_avtar : R.drawable.male_avtar);
        }

        holder.onlineLL.setVisibility("1".equals(item.getChkonline()) ? View.VISIBLE : View.GONE);
        holder.premiumLL.setVisibility("1".equals(item.getPremium()) ? View.VISIBLE : View.GONE);
        holder.just_joineLL.setVisibility("1".equals(item.getJust_joined()) ? View.VISIBLE : View.GONE);
        holder.verifiedLL.setVisibility("1".equals(item.getAccount_verify()) ? View.VISIBLE : View.GONE);

        holder.LL1.setOnClickListener(view -> {
            if ("0".equals(sharedPrefManager.getString())) {
                if ("2".equals(item.getIsDelete())) {
                    Toast.makeText(context, "This Profile is hidden", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(context, DetailMatchesActivity.class);
                    i.putExtra("userid", item.getId());
//                    Toast.makeText(context, " : "  + item.getId(), Toast.LENGTH_SHORT).show();
                    i.putExtra("profile_list", new Gson().toJson(tabMyMatchesListData));
                    i.putExtra("position", actualPosition);
                    context.startActivity(i);
                }
            } else {
                DialogUtil.showAlertDialog(context);
            }
        });

        if ("2".equals(item.getIsDelete()) && image != null && !image.isEmpty()) {
            Glide.with(context).load(image).transform(new BlurTransformation(context, 25)).into(holder.img);
        }

        holder.btn_block_report.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.profile_block_reject, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.block_profile) {
                    showConfirmationDialogBoxBlock();
                } else if (menuItem.getItemId() == R.id.report_profile) {
                    Intent i = new Intent(context, ReasonForReportActivity.class);
                    i.putExtra("userid", people_userid);
                    i.putExtra("Fname", item.getName() != null ? item.getName() : "Unknown");
                    i.putExtra("isCallFromComment", false);
                    context.startActivity(i);
                }
                return true;
            });
            popup.show();
        });
    }

    // Network and API Methods
    public void connectApi(String user_id, String login_user_id, View view, ProductViewHolder holder) {
        if (holder.connect_btn_txt == null || holder.connect_btn_pb == null) {
            Log.e("ConnectApiError", "connect_btn_txt or connect_btn_pb is null");
            return;
        }
        holder.connect_btn_txt.setText("");
        holder.connect_btn_pb.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.connectNow(user_id, login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                connect_response = response.body();
                holder.connect_btn_pb.setVisibility(View.GONE);
                if (response.isSuccessful() && connect_response != null) {
                    if (connect_response.getResid().equals("200")) {
                        holder.connect_btn_txt.setText("Request Sent");
                        holder.connecy_btn.setClickable(false);
                        Snackbar.make(view, "Request sent successfully.", Snackbar.LENGTH_LONG).show();
                    } else {
                        holder.connect_btn_txt.setText("Connect Now");
                        Toast.makeText(context, connect_response.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    holder.connect_btn_txt.setText("Connect Now");
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                holder.connect_btn_pb.setVisibility(View.GONE);
                holder.connect_btn_txt.setText("Connect Now");
            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        isNetworkAvailable = true;
                        return true;
                    }
                }
            }
        }
        isNetworkAvailable = false;
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void showConfirmationDialogBoxBlock() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Blocked Member will not be able to view your Profile or contact you on Buddhist Samaj Wadhuwar")
                .setTitle("Are you sure you want to Block this profile?")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> blockUser(dialog))
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    private void blockUser(DialogInterface dialog) {
        Api apiService = RetrofitClient.getApiService();
        Call<CommonResponse> commonResponseCall = apiService.blockAccount(people_userid, String.valueOf(user.getUser_id()));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if (response.isSuccessful() && commonResponse != null) {
                    if (commonResponse.getResid() == 200) {
                        Toast.makeText(context, "Blocked this user", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}


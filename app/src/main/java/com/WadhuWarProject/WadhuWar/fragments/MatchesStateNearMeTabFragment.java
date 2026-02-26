package com.WadhuWarProject.WadhuWar.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.adapter.SearchMoreResultsAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.RemoveHistory;
import com.WadhuWarProject.WadhuWar.model.SearchDataMore;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesStateNearMeTabFragment extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {

    static boolean isNetworkAvailable;
    UserData user;
    FetchProfile fetchProfile;
    SearchMoreResultsAdapter searchResultsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private NetworkStateReceiver networkStateReceiver;

    RelativeLayout progress_bar_main, internetOffRL;
    ProgressBar simpleProgressBar;
    TextView try_again_txt, couldnt_reach_internet_txt;

    RecyclerView rv;
    LinearLayout emptyLL;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;

    LinearLayout swip_topLL;
    CardView just_joind_cv, new_upgrade_cv;
    TextView count_new;
    LinearLayout new_txtLL, upgrade_now_btn, upgrade_new_btn;
    ImageView close_btn, close_btn_new;

    String user_id;

    public static MatchesStateNearMeTabFragment newInstance() {
        return new MatchesStateNearMeTabFragment();
    }

    public MatchesStateNearMeTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }
        setHasOptionsMenu(true);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_matches_near_me_tab, container, false);

        // Initialize views
        close_btn_new = view.findViewById(R.id.close_btn_new);
        upgrade_new_btn = view.findViewById(R.id.upgrade_new_btn);
        new_upgrade_cv = view.findViewById(R.id.new_upgrade_cv);
        close_btn = view.findViewById(R.id.close_btn);
        upgrade_now_btn = view.findViewById(R.id.upgrade_now_btn);
        just_joind_cv = view.findViewById(R.id.just_joind_cv);
        count_new = view.findViewById(R.id.count_new);
        new_txtLL = view.findViewById(R.id.new_txtLL);
        swip_topLL = view.findViewById(R.id.swip_topLL);
        emptyLL = view.findViewById(R.id.emptyLL);
        progress_bar_main = view.findViewById(R.id.progress_bar_main);
        rv = view.findViewById(R.id.rv);
        nestedScroll = view.findViewById(R.id.nestedScroll);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        internetOffRL = view.findViewById(R.id.internetOffRL);
        simpleProgressBar = view.findViewById(R.id.simpleProgressBar);
        try_again_txt = view.findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = view.findViewById(R.id.couldnt_reach_internet_txt);

        // Setup RecyclerView
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);

        // Get user data
        user = SharedPrefManager.getInstance(getActivity()).getUser();
        user_id = String.valueOf(user.getUser_id());

        // Initialize adapter with remove callback
        searchResultsAdapter = new SearchMoreResultsAdapter(getActivity(), (position, item) -> {
            callDeleteApi(position, item.getId());
        });

        rv.setAdapter(searchResultsAdapter);

        // Scroll to top button
        nestedScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollChanged() {
                if (nestedScroll.getScrollY() > 1000) {
                    swip_topLL.setVisibility(View.VISIBLE);
                    swip_topLL.setOnClickListener(v -> nestedScroll.smoothScrollTo(0, 0));
                } else {
                    swip_topLL.setVisibility(View.GONE);
                }
            }
        });

        // Button click listeners
        close_btn.setOnClickListener(v -> just_joind_cv.setVisibility(View.GONE));
        upgrade_now_btn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), PremiumActivity.class);
            startActivity(i);
        });

        close_btn_new.setOnClickListener(v -> new_upgrade_cv.setVisibility(View.GONE));
        upgrade_new_btn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), PremiumActivity.class);
            startActivity(i);
        });

        // Load data
        fetchProfileData();
        submitData();

        // Network state receiver
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        return view;
    }

    private void callDeleteApi(int position, String deleteUserId) {
//        Toast.makeText(getActivity(), "Removing...", Toast.LENGTH_SHORT).show();

        Api apiService = RetrofitClient.getApiService();
        Call<RemoveHistory> call = apiService.getRemoveHistory(user_id, deleteUserId);

        call.enqueue(new Callback<RemoveHistory>() {
            @Override
            public void onResponse(Call<RemoveHistory> call, Response<RemoveHistory> response) {
                // First, try to remove item from UI immediately for better UX
                searchResultsAdapter.removeItem(position);

                // Then check API response
                if (response.isSuccessful() && response.body() != null) {
                    RemoveHistory removeHistory = response.body();

                    // Log the actual response
                    Log.d("DeleteResponse", "Resid: " + removeHistory.getResid());
                    Log.d("DeleteResponse", "ResMsg: " + removeHistory.getResMsg());

                    // Check if response is success (handle both string and integer)
                    String residStr = String.valueOf(removeHistory.getResid());
                    if ("200".equals(residStr)) {
//                        Toast.makeText(getActivity(), "Profile removed", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(getActivity(),
//                                "Server error: " + removeHistory.getResMsg(),
//                                Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toast.makeText(getActivity(),
//                            "API Error: " + response.code(),
//                            Toast.LENGTH_SHORT).show();
                }
                // Update empty view
                if (searchResultsAdapter.getItemCount() == 0) {
                    emptyLL.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RemoveHistory> call, Throwable t) {
//                Toast.makeText(getActivity(),
//                        "Network error: " + t.getMessage(),
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void networkAvailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchProfileData();
            submitData();
        });

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            // internetOffRL.setVisibility(View.GONE);
            // couldnt_reach_internet_txt.setVisibility(View.GONE);
        }, 3000);
    }

    @Override
    public void networkUnavailable() {
        try_again_txt.setOnClickListener(v -> {
            try_again_txt.setVisibility(View.GONE);
            simpleProgressBar.setVisibility(View.VISIBLE);
            if (!isNetworkAvailable()) {
                simpleProgressBar.postDelayed(() -> {
                    couldnt_reach_internet_txt.postDelayed(() -> {
                        try_again_txt.setVisibility(View.VISIBLE);
                        simpleProgressBar.setVisibility(View.GONE);
                        couldnt_reach_internet_txt.postDelayed(() -> couldnt_reach_internet_txt.setVisibility(View.GONE), 2000);
                    }, 2000);
                }, 2000);
            } else {
                networkUnavailable();
            }
        });

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    public void submitData() {
        progress_bar_main.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<SearchDataMore> call = apiService.getSaveHistory(user_id);

        call.enqueue(new Callback<SearchDataMore>() {
            @Override
            public void onResponse(Call<SearchDataMore> call, Response<SearchDataMore> response) {
                progress_bar_main.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    SearchDataMore searchData = response.body();

                    if ("200".equals(searchData.getResid())) {
                        ArrayList<SearchDataMore.SearchDataList> list = searchData.getSearchDataList();

                        if (list != null && !list.isEmpty()) {
                            // Clear and add all data
                            searchResultsAdapter.clear();
                            searchResultsAdapter.addAll(list);

                            rv.setVisibility(View.VISIBLE);
                            emptyLL.setVisibility(View.GONE);
                        } else {
                            // No data
                            searchResultsAdapter.clear();
                            emptyLL.setVisibility(View.VISIBLE);
                            rv.setVisibility(View.GONE);
                        }
                    } else {
                        // API error
                        emptyLL.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.GONE);
                    }
                } else {
                    // Response error
                    emptyLL.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SearchDataMore> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Network error. Please try again.", Toast.LENGTH_SHORT).show();
                emptyLL.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
            }
        });
    }

    public void fetchProfileData() {
        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {
            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                swipeRefreshLayout.setRefreshing(false);
                fetchProfile = response.body();
                if (response.isSuccessful() && fetchProfile != null) {
                    if (fetchProfile.getResid().equals("200")) {
                        // Check if user created before 1 year
                        String create_at = fetchProfile.getCreatedAt();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date createdDate = sdf.parse(create_at);
                            Calendar createdCal = Calendar.getInstance();
                            createdCal.setTime(createdDate);

                            Calendar today = Calendar.getInstance();

                            int yearDiff = today.get(Calendar.YEAR) - createdCal.get(Calendar.YEAR);
                            if (yearDiff > 1 || (yearDiff == 1 && today.get(Calendar.DAY_OF_YEAR) > createdCal.get(Calendar.DAY_OF_YEAR))) {
                                just_joind_cv.setVisibility(View.GONE);
                            } else {
                                just_joind_cv.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Check premium status
                        if (fetchProfile.getPremium().equals("1")) {
                            just_joind_cv.setVisibility(View.GONE);
                            new_upgrade_cv.setVisibility(View.VISIBLE);
                        } else {
                            new_upgrade_cv.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                if (!isNetworkAvailable()) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        isNetworkAvailable = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isNetworkAvailable = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (networkStateReceiver != null) {
            getActivity().unregisterReceiver(networkStateReceiver);
        }
    }
}
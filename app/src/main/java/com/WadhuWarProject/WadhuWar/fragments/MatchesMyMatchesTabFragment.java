package com.WadhuWarProject.WadhuWar.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.BasicInfoActivity;
import com.WadhuWarProject.WadhuWar.activity.EducationCareerActivity;
import com.WadhuWarProject.WadhuWar.activity.LifestyleActivity;
import com.WadhuWarProject.WadhuWar.activity.PartnerPreferencesActivity;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.activity.ReligiousBackgroundActivity;
import com.WadhuWarProject.WadhuWar.adapter.LastMatchesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.TabMyMatchesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.GetOnlineDate;
import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchesMyMatchesTabFragment extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {


    TextView change_peref_criteria_btn,new_macthesText;
    static boolean isNetworkAvailable;
    UserData user;
    FetchProfile fetchProfile;
    ProgressDialog progressBar;
    LinearLayout indicatorLayout;
    ImageView[] dots;

    TabMyMatchesAdapter tabMyMatchesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private NetworkStateReceiver networkStateReceiver;

    String gender;
    RelativeLayout progress_bar_main;

    ArrayList<TabMyMatchesList.TabMyMatchesListData> tabMyMatchesListData = new ArrayList<>();
    ArrayList<FetchProfile> fetchprofile = new ArrayList<>();
    TabMyMatchesList tabMyMatchesList;
    View overlayView;

    RecyclerView rv;
    LinearLayout emptyLL;

    RecyclerView rvLastMatches ,rv_last_matches;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;

    LinearLayout swip_topLL, showUnfilledDetailsLayout;


    CardView just_joind_cv, new_upgrade_cv;
    TextView count_new;
    LinearLayout new_txtLL, upgrade_now_btn, upgrade_new_btn;
    ImageView close_btn, close_btn_new,cancle;
    FetchProfile fetchProfileLoginUser;
    TextView edit_txt;

    Calendar createdCalendar;
    SimpleDateFormat sdf;
//    FetchProfile profileData = SharedPrefManager.getInstance(getActivity()).getProfileData();
SharedPrefManager prefManager;
    FetchProfile profileData;

    private ProgressDialog progressDialog;

    public static MatchesMyMatchesTabFragment newInstance() {
        MatchesMyMatchesTabFragment fragment = new MatchesMyMatchesTabFragment();
        return fragment;
    }


    public MatchesMyMatchesTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        setHasOptionsMenu(true);


    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        prefManager = SharedPrefManager.getInstance(context);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_matches_mymatches_tab, container, false);

        change_peref_criteria_btn = view.findViewById(R.id.change_peref_criteria_btn);
        close_btn_new = view.findViewById(R.id.close_btn_new);
        upgrade_new_btn = view.findViewById(R.id.upgrade_new_btn);
        new_upgrade_cv = view.findViewById(R.id.new_upgrade_cv);
        cancle = view.findViewById(R.id.cancle);
        rv_last_matches = view.findViewById(R.id.rv_last_matches);

        edit_txt = view.findViewById(R.id.edit_txt);
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
        showUnfilledDetailsLayout = view.findViewById(R.id.showUnfilledItems);
        overlayView = view.findViewById(R.id.overlayView);
        new_macthesText = view.findViewById(R.id.new_macthesText);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        // for scroll only one item at a time
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv);
//        PagerSnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(rv_last_matches);
//        rv_last_matches.setOnFlingListener(null);
//
//// attach indicator
//        SpringDotsIndicator dotsIndicator = view.findViewById(R.id.dot2);
//        dotsIndicator.attachToRecyclerView(rv_last_matches, snapHelper);
        indicatorLayout = view.findViewById(R.id.indicatorLayout);




//        user = SharedPrefManager.getInstance(getActivity()).getUser();

        profileData = prefManager.getProfileData();
        user = prefManager.getUser();


//     todo :: added by sagar for progress bar on 16 april 2025
        initializeProgressDialog();


        /*--------*/
        List<FetchProfile> profileList = fetchLoginUSerData();
        tabMyMatchesAdapter = new TabMyMatchesAdapter(getActivity(), fetchprofile, tabMyMatchesListData);
        rv.setItemAnimator(null);
        rv.setAdapter(tabMyMatchesAdapter);
        rv.setFocusable(false);
        /*--------*/

        nestedScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollChanged() {

                if (nestedScroll.getScrollY() > 1000) {

                    swip_topLL.setVisibility(View.VISIBLE);

                    swip_topLL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            nestedScroll.smoothScrollTo(0, 0);
                        }
                    });

                } else {
                    swip_topLL.setVisibility(View.GONE);

                }
            }
        });
        edit_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PartnerPreferencesActivity.class);
                startActivity(i);
            }
        });
        cancle.setOnClickListener(v -> {
            rv_last_matches.setVisibility(View.GONE); // Hide recyclerView
            cancle.setVisibility(View.GONE);
            overlayView.setVisibility(View.GONE);
            new_macthesText.setVisibility(View.GONE);
            indicatorLayout.setVisibility(View.GONE);

        });

        change_peref_criteria_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), PartnerPreferencesActivity.class);
                startActivity(i);
            }
        });

        showUnfilledDetailsLayout.setOnClickListener(v -> {
            fetchProfileData();

        });


        rvLastMatches = view.findViewById(R.id.rv_last_matches);
        String lastDate = prefManager.getLastApiCallDate();
//        prefManager.saveLastApiCallDate(todayDate);


//        String lastDate = SharedPrefManager.getInstance(getActivity()).getLastApiCallDate();
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (!todayDate.equals(lastDate)) {
//        if (todayDate.equals(lastDate)) {
            cancle.setVisibility(View.VISIBLE);
            new_macthesText.setVisibility(View.VISIBLE);
            LastMatches(profileData);
            SharedPrefManager.getInstance(getActivity()).saveLastApiCallDate(todayDate);
        } else {
        }



        fetchLoginUSerData();
//        loadFirstPage();
        fetchProfileDataChecking();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/
        return view;
    }

    private void LastMatches(FetchProfile profileData) {
        overlayView.setVisibility(View.VISIBLE);
        rvLastMatches.setVisibility(View.VISIBLE);
        indicatorLayout.setVisibility(View.VISIBLE);

//        String userId = "1";
        String userId = String.valueOf(user.getUser_id());
//        String gender = "Male";
        String gender = profileData.getGender();
//        String todayDate = "2025-11-25";
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ProgressDialog progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Please Wait...");
        progressBar.setCancelable(false);
        progressBar.show();
//        Toast.makeText(getContext(), "user id " +userId , Toast.LENGTH_SHORT).show();
        Api apiService = RetrofitClient.getApiService();
        Call<GetOnlineDate> userResponse =
                apiService.getOnlineDate(userId, todayDate, gender);
//        Toast.makeText(getActivity(), "data" +userId + todayDate + gender, Toast.LENGTH_SHORT).show();

        userResponse.enqueue(new Callback<GetOnlineDate>() {
            @Override
            public void onResponse(Call<GetOnlineDate> call, Response<GetOnlineDate> response) {

                if (getContext() == null || getActivity() == null) return;

//                Toast.makeText(getContext(), "API call", Toast.LENGTH_SHORT).show();

                if (progressBar != null && progressBar.isShowing()) {
                    progressBar.dismiss();
                }

                if (!response.isSuccessful()) {
                    Log.e("API_ERROR", "Response failed: " + response.message());
//                    Toast.makeText(getContext(), "API failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }

                GetOnlineDate body = response.body();

                if (body == null) {
                    Log.e("API_ERROR", "Response body is null");
//                    Toast.makeText(getContext(), "No data received", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ✅ Log FULL response safely
                Log.d("API_FULL_RESPONSE11", new Gson().toJson(body));

                List<GetOnlineDate.LastFourProfiles> list = body.getLastFourProfiles();

                if (list == null || list.isEmpty()) {
                    Log.w("API_EMPTY", "LastFourProfiles list is empty");
//                    Toast.makeText(getContext(), "No profiles found", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ---------------- RecyclerView Setup ----------------

                LinearLayoutManager layoutManager =
                        new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.HORIZONTAL, false) {
                            @Override
                            public int scrollHorizontallyBy(int dx,
                                                            RecyclerView.Recycler recycler,
                                                            RecyclerView.State state) {
                                return super.scrollHorizontallyBy(dx / 1, recycler, state);
                            }
                        };

                rvLastMatches.setLayoutManager(layoutManager);

                PagerSnapHelper snapHelper = new PagerSnapHelper();
                rvLastMatches.setOnFlingListener(null); // IMPORTANT
                snapHelper.attachToRecyclerView(rvLastMatches);

                // Indicators
                setupIndicators(list.size());
                updateIndicators(0);

                rvLastMatches.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View snappedView = snapHelper.findSnapView(layoutManager);
                            if (snappedView != null) {
                                int pos = layoutManager.getPosition(snappedView);
                                updateIndicators(pos);
                            }
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        float midpoint = recyclerView.getWidth() / 2f;
                        float d1 = 0.9f * midpoint;
                        float s0 = 0.7f;
                        float s1 = 1f;

                        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                            View child = recyclerView.getChildAt(i);
                            if (child == null) continue;

                            float childMid = (child.getLeft() + child.getRight()) / 2f;
                            float d = Math.min(d1, Math.abs(midpoint - childMid));
                            float scale = s1 + (s0 - s1) * (d / d1);
                            child.setScaleX(scale);
                            child.setScaleY(scale);
                        }
                    }
                });

                LastMatchesAdapter adapter =
                        new LastMatchesAdapter(getActivity(), list);
                rvLastMatches.setAdapter(adapter);
            }

            private void updateIndicators(int position) {
                for (int i = 0; i < dots.length; i++) {
                    if (i == position) {
                        dots[i].setImageResource(R.drawable.indicator_active);
                    } else {
                        dots[i].setImageResource(R.drawable.indicator_inactive);
                    }
                }
            }


            private void setupIndicators(int count) {
                dots = new ImageView[count];
                indicatorLayout.removeAllViews();

                for (int i = 0; i < count; i++) {
                    dots[i] = new ImageView(getActivity());
                    dots[i].setImageResource(R.drawable.indicator_inactive);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
                    params.setMargins(6, 0, 6, 0);

                    indicatorLayout.addView(dots[i], params);
                }
            }


            @Override
            public void onFailure(Call<GetOnlineDate> call, Throwable t) {
                progressBar.dismiss();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }


    //    todo :: added by sagar for progress bar on 16 april 2025
    private void initializeProgressDialog() {
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        Window window = progressDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
//            window.setBackgroundDrawableResource(android.R.color.transparent);
            layoutParams.gravity = Gravity.CENTER; // Set to bottom
            window.setAttributes(layoutParams);
        }
    }

    public void fetchProfileDataChecking() {

        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));
        Api apiService = RetrofitClient.getApiService();

        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {
            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("fetchProfilefetchProfile-------" + new Gson().toJson(fetchProfile));

                if (response.isSuccessful()) {
                    fetchProfile = response.body();
                    fetchprofile.add(fetchProfile);
                    loadFirstPage();

                    if (fetchProfile.getIs_active() != null) {
                        if (fetchProfile.getIs_active().equals("1")) {
                            if (fetchProfile.getReligion_name().equalsIgnoreCase("Not Specified") || fetchProfile.getMain_occupation_name().equalsIgnoreCase("Not Specified") || fetchProfile.getMain_education_name().equalsIgnoreCase("Not Specified") || fetchProfile.getMarital_status_name().equalsIgnoreCase("Not Specified") || fetchProfile.getYearly_salary().equalsIgnoreCase("Not Specified")) {
                                showUnfilledDetailsLayout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            showUnfilledDetailsLayout.setVisibility(View.GONE);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

//    public void fetchProfileData() {
//
//        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));
//        Api apiService = RetrofitClient.getApiService();
//        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
//        userResponse.enqueue(new Callback<FetchProfile>() {
//
//            @Override
//            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
//
//                swipeRefreshLayout.setRefreshing(false);
//
//                fetchProfile = response.body();
//
//                System.out.println("fetchProfilefetchProfile-------" + new Gson().toJson(fetchProfile));
//
//                if (response.isSuccessful()) {
//                    openCustomDialog(fetchProfile);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FetchProfile> call, Throwable t) {
//                System.out.println("msg1 my profile******" + t.toString());
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//    }


    public void fetchProfileData() {
        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {
            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    fetchProfile = response.body();

                    ArrayList<FetchProfile> profileList = new ArrayList<>();
                    profileList.add(fetchProfile);
                    tabMyMatchesAdapter.setFetchProfiles(profileList); // Update adapter
                    Log.d("fetchProfileData", "Fetched Profile Data: " + new Gson().toJson(fetchProfile));
                    openCustomDialog(fetchProfile);
                } else {
                    Log.e("fetchProfileData", "Error: Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                Log.e("fetchProfileData", "API Call Failed", t);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void openCustomDialog(FetchProfile fetchProfile) {
// Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.custom_dialog_fields, null);

// Find views within the custom layout
        MaterialTextView religionEdit = dialogView.findViewById(R.id.religionEdit);
        MaterialTextView genderEdit = dialogView.findViewById(R.id.genderEdit);
        MaterialTextView occupationEdit = dialogView.findViewById(R.id.occupationEdit);
        MaterialTextView yearlySalaryEdit = dialogView.findViewById(R.id.yearlySalaryEdit);
        MaterialTextView maritalEdit = dialogView.findViewById(R.id.maritalEdit);
        MaterialTextView qualificationEdit = dialogView.findViewById(R.id.qualificationEdit);

        LinearLayout religionLayout = dialogView.findViewById(R.id.religionLayout);
        LinearLayout genderLayout = dialogView.findViewById(R.id.genderLayout);
        LinearLayout occupationLayout = dialogView.findViewById(R.id.occupationLayout);
        LinearLayout yearlySalaryLayout = dialogView.findViewById(R.id.yearlySalaryLayout);
        LinearLayout maritalLayout = dialogView.findViewById(R.id.maritalStatusLayout);
        LinearLayout qualificationLayout = dialogView.findViewById(R.id.highestQualificationLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        builder.setTitle("Please Fill Details");

// Add action buttons
// builder.setPositiveButton("OK", null); // You can add actions here if needed
        builder.setNegativeButton("Cancel", null);

// Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        religionEdit.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), ReligiousBackgroundActivity.class);
            startActivity(i);
            dialog.dismiss();
        });

        genderEdit.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), BasicInfoActivity.class);
            startActivity(i);
            dialog.dismiss();
        });

        occupationEdit.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), EducationCareerActivity.class);
            startActivity(i);
            dialog.dismiss();
        });
        yearlySalaryEdit.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), EducationCareerActivity.class);
            startActivity(i);
            dialog.dismiss();
        });
        maritalEdit.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), LifestyleActivity.class);
            startActivity(i);
            dialog.dismiss();
        });
        qualificationEdit.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), EducationCareerActivity.class);
            startActivity(i);
            dialog.dismiss();
        });

        if (fetchProfile.getReligion_name().isEmpty() ||
                fetchProfile.getReligion_name().equalsIgnoreCase("Not Specified")) {
            religionLayout.setVisibility(View.VISIBLE);
        } else {
            religionLayout.setVisibility(View.GONE);
        }

        if (fetchProfile.getGender().isEmpty()) {
            genderLayout.setVisibility(View.VISIBLE);
        } else {
            genderLayout.setVisibility(View.GONE);
        }

        if (fetchProfile.getMain_occupation_name().isEmpty() ||
                fetchProfile.getMain_occupation_name().equalsIgnoreCase("Not Specified")) {
            occupationLayout.setVisibility(View.VISIBLE);
        } else {
            occupationLayout.setVisibility(View.GONE);
        }

        if (fetchProfile.getYearly_salary().isEmpty() ||
                fetchProfile.getYearly_salary().equalsIgnoreCase("Not Specified")) {
            yearlySalaryLayout.setVisibility(View.VISIBLE);
        } else {
            yearlySalaryLayout.setVisibility(View.GONE);
        }

        if (fetchProfile.getMarital_status_name().isEmpty() ||
                fetchProfile.getMarital_status_name().equalsIgnoreCase("Not Specified")) {
            maritalLayout.setVisibility(View.VISIBLE);
        } else {
            maritalLayout.setVisibility(View.GONE);
        }

        if (fetchProfile.getMain_education_name().isEmpty() ||
                fetchProfile.getMain_education_name().equalsIgnoreCase("Not Specified")) {
            qualificationLayout.setVisibility(View.VISIBLE);
        } else {
            qualificationLayout.setVisibility(View.GONE);
        }


// Build the dialog

    }


    protected void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        System.out.println("currentPage 11>>>>>>>>>>>" + currentPage);
        loadNextPage(currentPage);
    }

    public int getTotalPageCount() {
        return TOTAL_PAGES;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public boolean isLoading() {
        return isLoading;
    }


    @Override
    public void networkAvailable() {


//        fetchLoginUSerData();
//        loadFirstPage();


        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {

                        int visibleItemCount = linearLayoutManager.getChildCount();
                        int totalItemCount = linearLayoutManager.getItemCount();
                        int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();


                        if (!isLoading() && !isLastPage()) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && pastVisiblesItems >= 0) {

                                loadMoreItems();

                            }
                        }
                    }
                }
            }
        });


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                fetchLoginUSerData();
////                fetchProfileDataChecking();
////
////                currentPage = PAGE_START;
////                isLastPage = false;
////                tabMyMatchesAdapter.tabMyMatchesListData().clear();
////                if (tabMyMatchesListData != null) tabMyMatchesListData.clear();
////                tabMyMatchesAdapter.notifyDataSetChanged();
////                loadFirstPage();
////                swipeRefreshLayout.setRefreshing(false);
////
//
////                TODO :: CHANGES by sagar  on 16 april 2025
//                // Reset pagination and states
//                currentPage = PAGE_START;
//                isLastPage = false;
//                isLoading = false;
//
//                // Clear existing data
//                tabMyMatchesAdapter.clear();
//                tabMyMatchesListData.clear();
//                fetchprofile.clear();
//
//                // Notify adapter of data change
//                tabMyMatchesAdapter.notifyDataSetChanged();
//
//                // Fetch fresh data
//                fetchProfileDataChecking();
//
//                // Stop refreshing animation
//                swipeRefreshLayout.setRefreshing(false);
//
//
//            }
//        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            private boolean isRefreshing = false;

            @Override
            public void onRefresh() {
                if (isRefreshing) {
                    // Avoid multiple triggers of refresh
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }

                isRefreshing = true;

                // Reset pagination and states
                currentPage = PAGE_START;
                isLastPage = false;
                isLoading = false;

                // Clear existing data
                tabMyMatchesAdapter.clear();
                tabMyMatchesListData.clear();
                fetchprofile.clear();

                // Notify adapter of data change
                tabMyMatchesAdapter.notifyDataSetChanged();

                // Fetch fresh data
                fetchProfileDataChecking();

                // Simulate data fetching (if async task is used)
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    // Stop refreshing animation after data is fetched
                    swipeRefreshLayout.setRefreshing(false);
                    isRefreshing = false; // Allow future refresh
                }, 1000); // Adjust delay as per the data fetch time
            }
        });






    }


    public List<FetchProfile> fetchLoginUSerData() {
        System.out.println("user_id12345=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfileLoginUser = response.body();


                // new for user create befor 1 year or not show box
                String create_at = fetchProfileLoginUser.getCreatedAt();
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

                //


                if (fetchProfileLoginUser.getPremium() != null) {
                    if (fetchProfileLoginUser.getPremium().equals("1")) {
                        just_joind_cv.setVisibility(View.GONE);

                        new_upgrade_cv.setVisibility(View.VISIBLE);

                        upgrade_new_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(), PremiumActivity.class);
                                startActivity(i);
                            }
                        });
                        close_btn_new.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new_upgrade_cv.setVisibility(View.GONE);
                            }
                        });

                    } else {
//                        just_joind_cv.setVisibility(View.VISIBLE);

                        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(), PremiumActivity.class);
                                startActivity(i);
                            }
                        });
                        close_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                just_joind_cv.setVisibility(View.GONE);
                            }
                        });
                    }

                }


            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());


            }
        });

        return null;
    }


    @Override
    public void networkUnavailable() {

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                Toast.makeText(getActivity(), "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    public void loadFirstPage() {

        progress_bar_main.setVisibility(View.VISIBLE);

        if (tabMyMatchesListData != null) tabMyMatchesListData.clear();

        new_txtLL.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        emptyLL.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<TabMyMatchesList> userResponse = apiService.matchesTabMyMatches("2", String.valueOf(user.getUser_id()), "10", "1");
        userResponse.enqueue(new Callback<TabMyMatchesList>() {

            @Override
            public void onResponse(Call<TabMyMatchesList> call, Response<TabMyMatchesList> response) {

                swipeRefreshLayout.setRefreshing(false);
                tabMyMatchesList = response.body();
                progress_bar_main.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);

                System.out.println("mymatchesTabList-------------");
                System.out.println("gender------------" + gender);
                if (response.isSuccessful()) {
                    System.out.println("tabMyMatchesList.getResid() 123===========" + tabMyMatchesList.getResid());

                    if (tabMyMatchesList.getResid().equals("200")) {
//                        System.out.println("resp 222222.......>>>" + new Gson().toJson(response.body()));
                        new_txtLL.setVisibility(View.VISIBLE);
                        count_new.setText("(" + tabMyMatchesList.getCount() + ")");

                        emptyLL.setVisibility(View.GONE);
                        TOTAL_PAGES = Integer.parseInt(tabMyMatchesList.getTotal_pages());

                        tabMyMatchesListData = tabMyMatchesList.getTabMyMatchesList();
                        if (tabMyMatchesListData != null) {

                            if (!tabMyMatchesListData.isEmpty()) {

//                                ArrayList<TabMyMatchesList.TabMyMatchesListData> list = new ArrayList<>();
//                                for (int i = 0; i < 2; i++) {list.add(tabMyMatchesListData.get(i));}
//                                tabMyMatchesAdapter.addAll(list, fetchprofile);

                                tabMyMatchesAdapter.addAll(tabMyMatchesListData, fetchprofile);
                                Log.d("dataProfiles", fetchprofile.toString());

                                System.out.println("size check===========" + tabMyMatchesListData.size());

                                if (tabMyMatchesListData.size() < 10) {
                                    isLastPage = true;
                                    isLoading = false;
                                } else {
                                    if (currentPage <= TOTAL_PAGES)
                                        tabMyMatchesAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }
                    } else {
                        System.out.println("no data----------- 22");

                        /*Toast toast = Toast.makeText(getActivity(), tabMyMatchesList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();*/
                        emptyLL.setVisibility(View.VISIBLE);
                        new_txtLL.setVisibility(View.GONE);
                        rv.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(Call<TabMyMatchesList> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error TabMyMatchesList******" + t.toString());

            }
        });
    }

    public void loadNextPage(int pos) {

        emptyLL.setVisibility(View.GONE);

//        progress_bar_main.setVisibility(View.VISIBLE);
//        progressDialog.show();

        Api apiService = RetrofitClient.getApiService();
        Call<TabMyMatchesList> userResponse = apiService.matchesTabMyMatches("2", String.valueOf(user.getUser_id()), "10", String.valueOf(pos));
        userResponse.enqueue(new Callback<TabMyMatchesList>() {

            @Override
            public void onResponse(Call<TabMyMatchesList> call, Response<TabMyMatchesList> response) {
                tabMyMatchesList = response.body();
                progress_bar_main.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
//                progressDialog.dismiss();

                System.out.println("mymatchesTabList-------------");
                System.out.println("gender------------" + gender);
                if (response.isSuccessful()) {
                    System.out.println("tabMyMatchesList.getResid() 123===========" + tabMyMatchesList.getResid());

                    if (tabMyMatchesList.getResid().equals("200")) {
//                        System.out.println("resp 222222.......>>>" + new Gson().toJson(response.body()));

                        emptyLL.setVisibility(View.GONE);

                        tabMyMatchesAdapter.removeLoadingFooter();
                        isLoading = false;

                        tabMyMatchesListData = tabMyMatchesList.getTabMyMatchesList();
                        if (tabMyMatchesListData != null) {

                            if (!tabMyMatchesListData.isEmpty()) {

                                tabMyMatchesAdapter.addAll(tabMyMatchesListData, fetchprofile);
                                Log.d("dataProfiles", fetchprofile.toString());

                                if (tabMyMatchesListData.size() < 10) {
                                    isLastPage = true;
                                    isLoading = false;
                                } else {
                                    if (currentPage != TOTAL_PAGES)
                                        tabMyMatchesAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }
                    } else {
                        System.out.println("no data----------- 22");
                        tabMyMatchesAdapter.removeLoadingFooter();


                        /*Toast toast = Toast.makeText(getActivity(), tabMyMatchesList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();*/
//                        emptyLL.setVisibility(View.VISIBLE);
                    }
                }
            }


            @Override
            public void onFailure(Call<TabMyMatchesList> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);
//                progressDialog.dismiss();
                System.out.println("msg1 error TabMyMatchesList******" + t.toString());
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
    }//isNetworkAvailable()
}









//package com.WadhuWarProject.WadhuWar.fragments;
//
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.cardview.widget.CardView;
//import androidx.core.widget.NestedScrollView;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.PagerSnapHelper;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.WadhuWarProject.WadhuWar.R;
//import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
//import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
//import com.WadhuWarProject.WadhuWar.activity.EducationCareerActivity;
//import com.WadhuWarProject.WadhuWar.activity.LifestyleActivity;
//import com.WadhuWarProject.WadhuWar.activity.PartnerPreferencesActivity;
//import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
//import com.WadhuWarProject.WadhuWar.activity.ReligiousBackgroundActivity;
//import com.WadhuWarProject.WadhuWar.adapter.TabMyMatchesAdapter;
//import com.WadhuWarProject.WadhuWar.api.Api;
//import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
//import com.WadhuWarProject.WadhuWar.model.FetchProfile;
//import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
//import com.WadhuWarProject.WadhuWar.model.UserData;
//import com.google.android.material.textview.MaterialTextView;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class MatchesMyMatchesTabFragment extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {
//
//
//    TextView change_peref_criteria_btn;
//    static boolean isNetworkAvailable;
//    UserData user;
//    FetchProfile fetchProfile;
//    ProgressDialog progressBar;
//
//    TabMyMatchesAdapter tabMyMatchesAdapter;
//    SwipeRefreshLayout swipeRefreshLayout;
//    private NetworkStateReceiver networkStateReceiver;
//
//    String gender;
//    RelativeLayout progress_bar_main;
//
//
//    ArrayList<TabMyMatchesList.TabMyMatchesListData> tabMyMatchesListData = new ArrayList<>();
//    TabMyMatchesList tabMyMatchesList;
//
//
//    RecyclerView rv;
//    LinearLayout emptyLL;
//
//
//    private static final int PAGE_START = 1;
//    private boolean isLoading = false;
//    private boolean isLastPage = false;
//    private int TOTAL_PAGES;
//    private int currentPage = PAGE_START;
//    NestedScrollView nestedScroll;
//    LinearLayoutManager linearLayoutManager;
//
//    LinearLayout swip_topLL,showUnfilledDetailsLayout;
//
//
//    CardView just_joind_cv, new_upgrade_cv;
//    TextView count_new;
//    LinearLayout new_txtLL, upgrade_now_btn, upgrade_new_btn;
//    ImageView close_btn, close_btn_new;
//    FetchProfile fetchProfileLoginUser;
//    TextView edit_txt;
//
//    public static MatchesMyMatchesTabFragment newInstance() {
//        MatchesMyMatchesTabFragment fragment = new MatchesMyMatchesTabFragment();
//        return fragment;
//    }
//
//
//    public MatchesMyMatchesTabFragment() {
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.fragment_matches_mymatches_tab, container, false);
//
//        change_peref_criteria_btn = view.findViewById(R.id.change_peref_criteria_btn);
//        close_btn_new = view.findViewById(R.id.close_btn_new);
//        upgrade_new_btn = view.findViewById(R.id.upgrade_new_btn);
//        new_upgrade_cv = view.findViewById(R.id.new_upgrade_cv);
//
//        edit_txt = view.findViewById(R.id.edit_txt);
//        close_btn = view.findViewById(R.id.close_btn);
//        upgrade_now_btn = view.findViewById(R.id.upgrade_now_btn);
//        just_joind_cv = view.findViewById(R.id.just_joind_cv);
//        count_new = view.findViewById(R.id.count_new);
//        new_txtLL = view.findViewById(R.id.new_txtLL);
//        swip_topLL = view.findViewById(R.id.swip_topLL);
//        emptyLL = view.findViewById(R.id.emptyLL);
//        progress_bar_main = view.findViewById(R.id.progress_bar_main);
//        rv = view.findViewById(R.id.rv);
//        nestedScroll = view.findViewById(R.id.nestedScroll);
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//        showUnfilledDetailsLayout = view.findViewById(R.id.showUnfilledItems);
//
//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rv.setLayoutManager(linearLayoutManager);
//
//        user = SharedPrefManager.getInstance(getActivity()).getUser();
//
//
//
//
//        /*--------*/
//        tabMyMatchesAdapter = new TabMyMatchesAdapter(getActivity());
//        rv.setItemAnimator(null);
//        rv.setAdapter(tabMyMatchesAdapter);
//        rv.setFocusable(false);
//        /*--------*/
//
//
//        nestedScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @SuppressLint("RestrictedApi")
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onScrollChanged() {
//
//
//                if (nestedScroll.getScrollY() > 1000) {
//
//                    swip_topLL.setVisibility(View.VISIBLE);
//
//                    swip_topLL.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            nestedScroll.smoothScrollTo(0, 0);
//
//                        }
//                    });
//
//
//                } else {
//                    swip_topLL.setVisibility(View.GONE);
//
//                }
//
//            }
//        });
//        edit_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), PartnerPreferencesActivity.class);
//                startActivity(i);
//            }
//        });
//
//        showUnfilledDetailsLayout.setOnClickListener(v -> {
//            fetchProfileData();
//        });
//
//        change_peref_criteria_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getActivity(), PartnerPreferencesActivity.class);
//                startActivity(i);
//            }
//        });
//
//
//        fetchLoginUSerData();
//        loadFirstPage();
//        fetchProfileDataChecking();
//
//
//        /*if net off*/
//        networkStateReceiver = new NetworkStateReceiver();
//        networkStateReceiver.addListener(this);
//        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        /*end code if net off*/
//        return view;
//
//    }
//
//
//    public void fetchProfileData() {
//
//        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));
//        Api apiService = RetrofitClient.getApiService();
//        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
//        userResponse.enqueue(new Callback<FetchProfile>() {
//
//            @Override
//            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
//
//                swipeRefreshLayout.setRefreshing(false);
//
//                fetchProfile = response.body();
//
//                System.out.println("fetchProfilefetchProfile-------" + new Gson().toJson(fetchProfile));
//
//                if (response.isSuccessful()) {
//                    openCustomDialog(fetchProfile);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FetchProfile> call, Throwable t) {
//                System.out.println("msg1 my profile******" + t.toString());
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//    }
//
//
//
//    protected void loadMoreItems() {
//        isLoading = true;
//        currentPage += 1;
//        System.out.println("currentPage 11>>>>>>>>>>>" + currentPage);
//        loadNextPage(currentPage);
//    }
//
//    public int getTotalPageCount() {
//        return TOTAL_PAGES;
//    }
//
//    public boolean isLastPage() {
//        return isLastPage;
//    }
//
//    public boolean isLoading() {
//        return isLoading;
//    }
//
//
//    @Override
//    public void networkAvailable() {
//
//
////        fetchLoginUSerData();
////        loadFirstPage();
//
//
//        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                if (v.getChildAt(v.getChildCount() - 1) != null) {
//                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
//                            scrollY > oldScrollY) {
//
//
//                        int visibleItemCount = linearLayoutManager.getChildCount();
//                        int totalItemCount = linearLayoutManager.getItemCount();
//                        int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
//
//
//                        if (!isLoading() && !isLastPage()) {
//                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount
//                                    && pastVisiblesItems >= 0) {
//
//                                loadMoreItems();
//
//                            }
//                        }
//                    }
//                }
//            }
//        });
//
//
//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                fetchLoginUSerData();
//
//
//                currentPage = PAGE_START;
//                isLastPage = false;
//                tabMyMatchesAdapter.tabMyMatchesListData().clear();
//                if (tabMyMatchesListData != null)
//                    tabMyMatchesListData.clear();
//                tabMyMatchesAdapter.notifyDataSetChanged();
//                loadFirstPage();
////                swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });
//
//
//    }
//    private void openCustomDialog(FetchProfile fetchProfile) {
//        // Inflate the custom layout
//        LayoutInflater inflater = LayoutInflater.from(requireContext());
//        View dialogView = inflater.inflate(R.layout.custom_dialog_fields, null);
//
//        // Find views within the custom layout
//        MaterialTextView religionEdit = dialogView.findViewById(R.id.religionEdit);
//        MaterialTextView occupationEdit = dialogView.findViewById(R.id.occupationEdit);
//        MaterialTextView yearlySalaryEdit = dialogView.findViewById(R.id.yearlySalaryEdit);
//        MaterialTextView maritalEdit = dialogView.findViewById(R.id.maritalEdit);
//        MaterialTextView qualificationEdit = dialogView.findViewById(R.id.qualificationEdit);
//
//        LinearLayout religionLayout = dialogView.findViewById(R.id.religionLayout);
//        LinearLayout occupationLayout = dialogView.findViewById(R.id.occupationLayout);
//        LinearLayout yearlySalaryLayout = dialogView.findViewById(R.id.yearlySalaryLayout);
//        LinearLayout maritalLayout = dialogView.findViewById(R.id.maritalStatusLayout);
//        LinearLayout qualificationLayout = dialogView.findViewById(R.id.highestQualificationLayout);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setView(dialogView);
//        builder.setTitle("Please Fill Details");
//
//        // Add action buttons
//        // builder.setPositiveButton("OK", null); // You can add actions here if needed
//        builder.setNegativeButton("Cancel", null);
//
//        // Show the dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        religionEdit.setOnClickListener(v -> {
//            Intent i = new Intent(requireContext(), ReligiousBackgroundActivity.class);
//            startActivity(i);
//            dialog.dismiss();
//        });
//        occupationEdit.setOnClickListener(v -> {
//            Intent i = new Intent(requireContext(), EducationCareerActivity.class);
//            startActivity(i);
//            dialog.dismiss();
//        });
//        yearlySalaryEdit.setOnClickListener(v -> {
//            Intent i = new Intent(requireContext(), EducationCareerActivity.class);
//            startActivity(i);
//            dialog.dismiss();
//        });
//        maritalEdit.setOnClickListener(v -> {
//            Intent i = new Intent(requireContext(), LifestyleActivity.class);
//            startActivity(i);
//            dialog.dismiss();
//        });
//        qualificationEdit.setOnClickListener(v -> {
//            Intent i = new Intent(requireContext(), EducationCareerActivity.class);
//            startActivity(i);
//            dialog.dismiss();
//        });
//        if (fetchProfile.getReligion_name().isEmpty() || fetchProfile.getReligion_name().equalsIgnoreCase("Not Specified")) {
//            religionLayout.setVisibility(View.VISIBLE);
//        } else {
//            religionLayout.setVisibility(View.GONE);
//        }
//
//        if (fetchProfile.getMain_occupation_name().isEmpty() || fetchProfile.getMain_occupation_name().equalsIgnoreCase("Not Specified")) {
//            occupationLayout.setVisibility(View.VISIBLE);
//        } else {
//            occupationLayout.setVisibility(View.GONE);
//        }
//
//        if (fetchProfile.getYearly_salary().isEmpty() || fetchProfile.getYearly_salary().equalsIgnoreCase("Not Specified")) {
//            yearlySalaryLayout.setVisibility(View.VISIBLE);
//        } else {
//            yearlySalaryLayout.setVisibility(View.GONE);
//        }
//
//        if (fetchProfile.getMarital_status_name().isEmpty() || fetchProfile.getMarital_status_name().equalsIgnoreCase("Not Specified")) {
//            maritalLayout.setVisibility(View.VISIBLE);
//        } else {
//            maritalLayout.setVisibility(View.GONE);
//        }
//
//        if (fetchProfile.getMain_education_name().isEmpty() || fetchProfile.getMain_education_name().equalsIgnoreCase("Not Specified")) {
//            qualificationLayout.setVisibility(View.VISIBLE);
//        } else {
//            qualificationLayout.setVisibility(View.GONE);
//        }
//
//
//        // Build the dialog
//
//    }
//
//
//    public void fetchLoginUSerData() {
//
//
//        System.out.println("user_id12345=====" + String.valueOf(user.getUser_id()));
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
//        userResponse.enqueue(new Callback<FetchProfile>() {
//
//            @Override
//            public void onResponse(@NonNull Call<FetchProfile> call, @NonNull Response<FetchProfile> response) {
//                fetchProfileLoginUser = response.body();
//
//
//                if (fetchProfileLoginUser.getPremium() != null) {
//                    if (fetchProfileLoginUser.getPremium().equals("1")) {
//                        just_joind_cv.setVisibility(View.GONE);
//
//                        new_upgrade_cv.setVisibility(View.VISIBLE);
//
//                        upgrade_new_btn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent i = new Intent(getActivity(), PremiumActivity.class);
//                                startActivity(i);
//                            }
//                        });
//                        close_btn_new.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                new_upgrade_cv.setVisibility(View.GONE);
//                            }
//                        });
//
//                    } else {
//                        just_joind_cv.setVisibility(View.VISIBLE);
//
//                        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent i = new Intent(getActivity(), PremiumActivity.class);
//                                startActivity(i);
//                            }
//                        });
//                        close_btn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                just_joind_cv.setVisibility(View.GONE);
//                            }
//                        });
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<FetchProfile> call, @NonNull Throwable t) {
//                System.out.println("msg1 my profile******" + t);
//
//
//            }
//        });
//
//    }
//
//    public void fetchProfileDataChecking() {
//
//        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));
//        Api apiService = RetrofitClient.getApiService();
//
//        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
//        userResponse.enqueue(new Callback<FetchProfile>() {
//            @Override
//            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
//
//                swipeRefreshLayout.setRefreshing(false);
//
//                fetchProfile = response.body();
//
//                System.out.println("fetchProfilefetchProfile-------" + new Gson().toJson(fetchProfile));
//                if (response.isSuccessful()) {
//                    boolean isReligionNotSpecified = fetchProfile.getReligion_name() != null && fetchProfile.getReligion_name().equalsIgnoreCase("Not Specified");
//                    boolean isOccupationNotSpecified = fetchProfile.getMain_occupation_name() != null && fetchProfile.getMain_occupation_name().equalsIgnoreCase("Not Specified");
//                    boolean isEducationNotSpecified = fetchProfile.getMain_education_name() != null && fetchProfile.getMain_education_name().equalsIgnoreCase("Not Specified");
//                    boolean isMaritalStatusNotSpecified = fetchProfile.getMarital_status_name() != null && fetchProfile.getMarital_status_name().equalsIgnoreCase("Not Specified");
//                    boolean isYearlySalaryNotSpecified = fetchProfile.getYearly_salary() != null && fetchProfile.getYearly_salary().equalsIgnoreCase("Not Specified");
//
//                    if (isReligionNotSpecified || isOccupationNotSpecified || isEducationNotSpecified || isMaritalStatusNotSpecified || isYearlySalaryNotSpecified) {
//                        showUnfilledDetailsLayout.setVisibility(View.VISIBLE);
//                    } else {
//                        showUnfilledDetailsLayout.setVisibility(View.GONE);
//                    }
//                }
//
////                if (response.isSuccessful()) {
////                    if (fetchProfile.getReligion_name()!=null&&fetchProfile.getReligion_name().equalsIgnoreCase("Not Specified") ||
////                            fetchProfile.getMain_occupation_name().equalsIgnoreCase("Not Specified")||
////                            fetchProfile.getMain_education_name().equalsIgnoreCase("Not Specified") ||
////                            fetchProfile.getMarital_status_name().equalsIgnoreCase("Not Specified") ||
////                            fetchProfile.getYearly_salary().equalsIgnoreCase("Not Specified")) {
////                        showUnfilledDetailsLayout.setVisibility(View.VISIBLE);
////
////                    }else {
////                        showUnfilledDetailsLayout.setVisibility(View.GONE);
////                    }
////                }
//            }
//
//            @Override
//            public void onFailure(Call<FetchProfile> call, Throwable t) {
//                System.out.println("msg1 my profile******" + t.toString());
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }
//
//
//    @Override
//    public void networkUnavailable() {
//
//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Toast.makeText(getActivity(), "Please check internet connection!", Toast.LENGTH_SHORT).show();
//
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//    }
//
//    public boolean isNetworkAvailable() {
//
//        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return networkInfo != null;
//    }
//
//
//    public void loadFirstPage() {
//
//        progress_bar_main.setVisibility(View.VISIBLE);
//
//
//        if (tabMyMatchesListData != null)
//            tabMyMatchesListData.clear();
//
//        new_txtLL.setVisibility(View.GONE);
//        rv.setVisibility(View.GONE);
//        emptyLL.setVisibility(View.GONE);
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<TabMyMatchesList> userResponse = apiService.matchesTabMyMatches("2", String.valueOf(user.getUser_id()), "10", "1");
//        userResponse.enqueue(new Callback<TabMyMatchesList>() {
//
//            @Override
//            public void onResponse(@NonNull Call<TabMyMatchesList> call, @NonNull Response<TabMyMatchesList> response) {
//
//                swipeRefreshLayout.setRefreshing(false);
//                tabMyMatchesList = response.body();
//                progress_bar_main.setVisibility(View.GONE);
//                rv.setVisibility(View.VISIBLE);
//
//                System.out.println("mymatchesTabList-------------");
//                System.out.println("gender------------" + gender);
//                if (response.isSuccessful()) {
//                    System.out.println("tabMyMatchesList.getResid() 123===========" + tabMyMatchesList.getResid());
//                    if (tabMyMatchesList != null) {
//                        if (tabMyMatchesList.getResid().equals("200")) {
////                        System.out.println("resp 222222.......>>>" + new Gson().toJson(response.body()));
//                            new_txtLL.setVisibility(View.VISIBLE);
//                            count_new.setText("(" + tabMyMatchesList.getCount() + ")");
//
//                            emptyLL.setVisibility(View.GONE);
//
//                            TOTAL_PAGES = Integer.parseInt(tabMyMatchesList.getTotal_pages());
//
//
//                            tabMyMatchesListData = tabMyMatchesList.getTabMyMatchesList();
//                            if (tabMyMatchesListData != null) {
//
//                                if (!tabMyMatchesListData.isEmpty()) {
//                                    emptyLL.setVisibility(View.GONE);
//                                    tabMyMatchesAdapter.addAll(tabMyMatchesListData);
//
//
//                                    System.out.println("size check===========" + tabMyMatchesListData.size());
//
//                                    if (tabMyMatchesListData.size() < 10) {
//                                        isLastPage = true;
//                                        isLoading = false;
//                                    } else {
//                                        if (currentPage <= TOTAL_PAGES)
//                                            tabMyMatchesAdapter.addLoadingFooter();
//                                        else isLastPage = true;
//                                    }
//
//                                }
//
//
//                            }
//                        } else {
//                            System.out.println("no data----------- 22");
//
//                        /*Toast toast = Toast.makeText(getActivity(), tabMyMatchesList.getResMsg(), Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
//                        toast.show();*/
//                            emptyLL.setVisibility(View.VISIBLE);
//                            new_txtLL.setVisibility(View.GONE);
//                            rv.setVisibility(View.GONE);
//                        }
//                    }else {
//                        emptyLL.setVisibility(View.VISIBLE);
//                        new_txtLL.setVisibility(View.GONE);
//                        rv.setVisibility(View.GONE);
//                    }
//                }else {
//                    emptyLL.setVisibility(View.VISIBLE);
//                    new_txtLL.setVisibility(View.GONE);
//                    rv.setVisibility(View.GONE);
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<TabMyMatchesList> call, @NonNull Throwable t) {
//                progress_bar_main.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
//                System.out.println("msg1 error TabMyMatchesList******" + t);
//
//            }
//        });
//    }
//
//
//    public void loadNextPage(int pos) {
//
//        emptyLL.setVisibility(View.GONE);
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<TabMyMatchesList> userResponse = apiService.matchesTabMyMatches("2", String.valueOf(user.getUser_id()), "10", String.valueOf(pos));
//        userResponse.enqueue(new Callback<TabMyMatchesList>() {
//
//            @Override
//            public void onResponse(@NonNull Call<TabMyMatchesList> call, @NonNull Response<TabMyMatchesList> response) {
//                tabMyMatchesList = response.body();
//                progress_bar_main.setVisibility(View.GONE);
//                rv.setVisibility(View.VISIBLE);
//
//                System.out.println("mymatchesTabList-------------");
//                System.out.println("gender------------" + gender);
//                if (response.isSuccessful()) {
//                    System.out.println("tabMyMatchesList.getResid() 123===========" + tabMyMatchesList.getResid());
//
//                    if (tabMyMatchesList.getResid().equals("200")) {
////                        System.out.println("resp 222222.......>>>" + new Gson().toJson(response.body()));
//
//                        emptyLL.setVisibility(View.GONE);
//
//                        tabMyMatchesAdapter.removeLoadingFooter();
//                        isLoading = false;
//
//
//                        tabMyMatchesListData = tabMyMatchesList.getTabMyMatchesList();
//                        if (tabMyMatchesListData != null) {
//
//
//                            if (!tabMyMatchesListData.isEmpty()) {
//
//                                tabMyMatchesAdapter.addAll(tabMyMatchesListData);
//
//                                if (tabMyMatchesListData.size() < 10) {
//                                    isLastPage = true;
//                                    isLoading = false;
//                                } else {
//                                    if (currentPage != TOTAL_PAGES)
//                                        tabMyMatchesAdapter.addLoadingFooter();
//                                    else isLastPage = true;
//                                }
//                            }
//
//
//                        }
//                    } else {
//                        System.out.println("no data----------- 22");
//
//                        /*Toast toast = Toast.makeText(getActivity(), tabMyMatchesList.getResMsg(), Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
//                        toast.show();*/
//                        emptyLL.setVisibility(View.VISIBLE);
//
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<TabMyMatchesList> call, @NonNull Throwable t) {
//                progress_bar_main.setVisibility(View.GONE);
//
//                System.out.println("msg1 error TabMyMatchesList******" + t);
//
//            }
//        });
//    }
//
//
//    public static boolean isNetworkAvailable(Context context) {
//        isNetworkAvailable = false;
//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity == null) {
//            return false;
//        } else {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        isNetworkAvailable = true;
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }//isNetworkAvailable()
//
//}
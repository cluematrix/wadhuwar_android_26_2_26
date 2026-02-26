package com.WadhuWarProject.WadhuWar.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.HomeProfilesListAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.ProfileSlider;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileHomeActivity extends AppCompatActivity   implements  NetworkStateReceiver.NetworkStateReceiverListener{
    Toolbar toolbar;

    RecyclerView rv;
    RelativeLayout progress_bar;
    UserData user;

    private NetworkStateReceiver networkStateReceiver;

    HomeProfilesListAdapter homeProfilesListAdapter;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    static boolean isNetworkAvailable;
    SwipeRefreshLayout swipeRefreshLayout;
    String user_id;
    ArrayList<ProfileSlider.ProfileSliderData> sliderListImagesProfilesList = new ArrayList<>();
    ProfileSlider.ProfileSliderData profileSliderData;
    ProfileSlider profileSlider;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;
    LinearLayout swip_topLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightStatusBars(true);

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        setContentView(R.layout.activity_profile_home);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile_home);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        progress_bar = findViewById(R.id.progress_bar);
        nestedScroll =  findViewById(R.id.nestedScroll);
        swipeRefreshLayout =  findViewById(R.id.swipeRefreshLayout);

        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        swip_topLL = findViewById(R.id.swip_topLL);


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        user = SharedPrefManager.getInstance(ProfileHomeActivity.this).getUser();

        linearLayoutManager = new LinearLayoutManager(ProfileHomeActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        homeProfilesListAdapter = new HomeProfilesListAdapter(ProfileHomeActivity.this);
        rv.setItemAnimator(null);
        rv.setAdapter(homeProfilesListAdapter);
        rv.setFocusable(false);

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

                            nestedScroll.smoothScrollTo(0,0);

                        }
                    });


                } else {
                    swip_topLL.setVisibility(View.GONE);

                }

            }
        });
        loadProfileBannerData();


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }

    protected void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        System.out.println("currentPage 11>>>>>>>>>>>" +  currentPage);
        loadProfileBannerDataNext(currentPage);
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
//        loadProfileBannerData();


           nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if(v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {



                        int visibleItemCount = linearLayoutManager.getChildCount();
                        int totalItemCount = linearLayoutManager.getItemCount();
                        int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();




                        if (!isLoading() && !isLastPage()) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount
                                    && pastVisiblesItems >= 0) {

                                loadMoreItems();

                            }
                        }
                    }
                }
            }
        });


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                currentPage = PAGE_START;
                isLastPage = false;
                homeProfilesListAdapter.getProfileSliderData().clear();
                if(sliderListImagesProfilesList!=null)
                    sliderListImagesProfilesList.clear();
                homeProfilesListAdapter.notifyDataSetChanged();
                loadProfileBannerData();
//                swipeRefreshLayout.setRefreshing(false);

            }
        });
        final Handler handler = new Handler();

        Runnable delayrunnable = new Runnable() {

            @Override
            public void run() {

                internetOffRL.setVisibility(View.GONE);
                couldnt_reach_internet_txt.setVisibility(View.GONE);

            }
        };
        handler.postDelayed(delayrunnable, 3000);


    }

    public  void  loadProfileBannerData(){


        homeProfilesListAdapter.getProfileSliderData().clear();

        System.out.println("4444444444");

        if (sliderListImagesProfilesList!=null )
            sliderListImagesProfilesList.clear();

        progress_bar.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<ProfileSlider> userResponse = apiService.profileSlider("18",String.valueOf(user.getUser_id()),"10","1");
        userResponse.enqueue(new Callback<ProfileSlider>() {

            @Override
            public void onResponse(Call<ProfileSlider> call, Response<ProfileSlider> response) {
                swipeRefreshLayout.setRefreshing(false);
                profileSlider = response.body();

                progress_bar.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    if(profileSlider.getResid().equals("200")) {

                        getSupportActionBar().setTitle("Profiles (" +profileSlider.getCount() +")");

//                        System.out.println("profile slider data===========" + new Gson().toJson(profileSlider));


                        TOTAL_PAGES = Integer.parseInt(profileSlider.getTotal_pages());
                        sliderListImagesProfilesList = profileSlider.getProfileSlider();



                        if (sliderListImagesProfilesList != null) {

                            if(!sliderListImagesProfilesList.isEmpty()) {
                                homeProfilesListAdapter.addAll(sliderListImagesProfilesList);
                                if(sliderListImagesProfilesList.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) homeProfilesListAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }

                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileSlider> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error profile slider******" + t.toString());

            }
        });
    }

    public  void  loadProfileBannerDataNext(int c){



        Api apiService = RetrofitClient.getApiService();
        Call<ProfileSlider> userResponse = apiService.profileSlider("18",String.valueOf(user.getUser_id()),"10",String.valueOf(c));
        userResponse.enqueue(new Callback<ProfileSlider>() {

            @Override
            public void onResponse(Call<ProfileSlider> call, Response<ProfileSlider> response) {
                profileSlider = response.body();

                progress_bar.setVisibility(View.GONE);


                if (response.isSuccessful()) {

                    if(profileSlider.getResid().equals("200")) {


                        if (sliderListImagesProfilesList != null) {

                            homeProfilesListAdapter.removeLoadingFooter();
                            isLoading = false;

                            sliderListImagesProfilesList = profileSlider.getProfileSlider();
                            if (sliderListImagesProfilesList != null) {



                                if(!sliderListImagesProfilesList.isEmpty()) {

                                    homeProfilesListAdapter.addAll(sliderListImagesProfilesList);

                                    if(sliderListImagesProfilesList.size()<10){
                                        isLastPage = true;
                                        isLoading= false;
                                    }else{
                                        if (currentPage != TOTAL_PAGES) homeProfilesListAdapter.addLoadingFooter();
                                        else isLastPage = true;
                                    }
                                }
                            }

                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileSlider> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);

                System.out.println("msg1 error profile slider******" + t.toString());

            }
        });
    }


    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(ProfileHomeActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try_again_txt.setVisibility(View.GONE);
                simpleProgressBar.setVisibility(View.VISIBLE);
                if(!isNetworkAvailable()){
                    simpleProgressBar.postDelayed(new Runnable() {
                        public void run() {
                            couldnt_reach_internet_txt.postDelayed(new Runnable() {
                                public void run() {
                                    couldnt_reach_internet_txt.setVisibility(View.VISIBLE);
                                    try_again_txt.setVisibility(View.VISIBLE);
                                    simpleProgressBar.setVisibility(View.GONE);
                                    couldnt_reach_internet_txt.postDelayed(new Runnable() {
                                        public void run() {
                                            couldnt_reach_internet_txt.setVisibility(View.GONE);


                                        }
                                    }, 2000);
                                }
                            }, 2000);

                        }
                    }, 2000);
                }
                else{
                    networkUnavailable();
                }

            }
        });

    }

    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
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
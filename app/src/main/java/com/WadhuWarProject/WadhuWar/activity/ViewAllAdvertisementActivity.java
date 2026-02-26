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
import com.WadhuWarProject.WadhuWar.adapter.ViewAllAdvertiseAdapter;
import com.WadhuWarProject.WadhuWar.adapter.ViewAllNewsAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.AdvertiseList;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllAdvertisementActivity extends AppCompatActivity   implements  NetworkStateReceiver.NetworkStateReceiverListener{
    Toolbar toolbar;

    RecyclerView advertisement_rv;
    RelativeLayout progress_bar_advertisement;

    private NetworkStateReceiver networkStateReceiver;


    ViewAllAdvertiseAdapter viewAllAdvertiseAdapter;
    ArrayList<AdvertiseList.AdvertiseListData> advertiseListData = new ArrayList<>();
    AdvertiseList advertiseList;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    static boolean isNetworkAvailable;
    SwipeRefreshLayout swipeRefreshLayout;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;
    LinearLayout swip_topLL;
    UserData user;

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
        setContentView(R.layout.activity_view_all_advertisement);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_all_advertisement);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        nestedScroll = findViewById(R.id.nestedScroll);
        swip_topLL = findViewById(R.id.swip_topLL);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        advertisement_rv = findViewById(R.id.advertisement_rv);
        progress_bar_advertisement = findViewById(R.id.progress_bar_advertisement);

        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        user = SharedPrefManager.getInstance(this).getUser();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Advertisement");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        linearLayoutManager = new LinearLayoutManager(ViewAllAdvertisementActivity.this, LinearLayoutManager.VERTICAL, false);
        advertisement_rv.setLayoutManager(linearLayoutManager);

        viewAllAdvertiseAdapter = new ViewAllAdvertiseAdapter(ViewAllAdvertisementActivity.this);
        advertisement_rv.setItemAnimator(null);
        advertisement_rv.setAdapter(viewAllAdvertiseAdapter);
        advertisement_rv.setFocusable(false);

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

        getAdvertisementData();


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
        getAdvertisementDataNext(currentPage);
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
//        getAdvertisementData();


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
                viewAllAdvertiseAdapter.getAdvertiseListData().clear();
                if(advertiseListData!=null)
                    advertiseListData.clear();
                viewAllAdvertiseAdapter.notifyDataSetChanged();
                getAdvertisementData();
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




    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(ViewAllAdvertisementActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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

    public  void  getAdvertisementData(){

        viewAllAdvertiseAdapter.getAdvertiseListData().clear();


        System.out.println("333333333333333333");

        if (advertiseListData!=null )
            advertiseListData.clear();

        progress_bar_advertisement.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<AdvertiseList> userResponse = apiService.advertiseList("04","10","1",user.getUser_id()+"");
        userResponse.enqueue(new Callback<AdvertiseList>() {

            @Override
            public void onResponse(Call<AdvertiseList> call, Response<AdvertiseList> response) {

                swipeRefreshLayout.setRefreshing(false);
                advertiseList = response.body();

                progress_bar_advertisement.setVisibility(View.GONE);
                advertisement_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

//                    getSupportActionBar().setTitle("News ("+advertiseList.getCount() +")");
                    getSupportActionBar().setTitle("Advertisement ("+advertiseList.getCount() +")");


                    if(advertiseList.getResid().equals("200")) {

                        TOTAL_PAGES = Integer.parseInt(advertiseList.getTotal_pages());


                        advertiseListData = advertiseList.getAdvertiseList();

                        if (advertiseListData != null) {


                            if(!advertiseListData.isEmpty()) {
                                viewAllAdvertiseAdapter.addAll(advertiseListData);
                                if(advertiseListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) viewAllAdvertiseAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }


                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<AdvertiseList> call, Throwable t) {
                progress_bar_advertisement.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error new******" + t.toString());

            }
        });
    }


    public  void  getAdvertisementDataNext(int c){



        Api apiService = RetrofitClient.getApiService();
        Call<AdvertiseList> userResponse = apiService.advertiseList("04","10",String.valueOf(c),user.getUser_id()+"");
        userResponse.enqueue(new Callback<AdvertiseList>() {

            @Override
            public void onResponse(Call<AdvertiseList> call, Response<AdvertiseList> response) {
                advertiseList = response.body();

                progress_bar_advertisement.setVisibility(View.GONE);

                if (response.isSuccessful()) {



                    if(advertiseList.getResid().equals("200")) {

                        TOTAL_PAGES = Integer.parseInt(advertiseList.getTotal_pages());

                        viewAllAdvertiseAdapter.removeLoadingFooter();
                        isLoading = false;
                        advertiseListData = advertiseList.getAdvertiseList();

                        if (advertiseListData != null) {


                            if(!advertiseListData.isEmpty()) {

                                viewAllAdvertiseAdapter.addAll(advertiseListData);

                                if(advertiseListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage != TOTAL_PAGES) viewAllAdvertiseAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }


                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<AdvertiseList> call, Throwable t) {
                progress_bar_advertisement.setVisibility(View.GONE);

                System.out.println("msg1 error new******" + t.toString());

            }
        });
    }



}
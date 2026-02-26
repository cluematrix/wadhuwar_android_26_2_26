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
import com.WadhuWarProject.WadhuWar.adapter.ViewAllNewsAdapter;
import com.WadhuWarProject.WadhuWar.adapter.ViewAllSuccessStoryAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.NewsList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllNewsActivity extends AppCompatActivity   implements  NetworkStateReceiver.NetworkStateReceiverListener{
    Toolbar toolbar;

    RecyclerView news_rv;
    RelativeLayout progress_bar_news;

    private NetworkStateReceiver networkStateReceiver;

    ViewAllNewsAdapter viewAllNewsAdapter;

    ArrayList<NewsList.NewsListData> newsListData = new ArrayList<>();
    NewsList newsList;
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
        setContentView(R.layout.activity_view_all_news);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_all_news);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        nestedScroll = findViewById(R.id.nestedScroll);
        swip_topLL = findViewById(R.id.swip_topLL);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        news_rv = findViewById(R.id.news_rv);
        progress_bar_news = findViewById(R.id.progress_bar_news);

        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("News");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linearLayoutManager = new LinearLayoutManager(ViewAllNewsActivity.this, LinearLayoutManager.VERTICAL, false);
        news_rv.setLayoutManager(linearLayoutManager);

        viewAllNewsAdapter = new ViewAllNewsAdapter(ViewAllNewsActivity.this);
        news_rv.setItemAnimator(null);

        news_rv.setAdapter(viewAllNewsAdapter);
        news_rv.setFocusable(false);

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

        getNewsListData();



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
        getNewsListDataNext(currentPage);
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
//        getNewsListData();


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
                viewAllNewsAdapter.getNewsListData().clear();
                if(newsListData!=null)
                    newsListData.clear();
                viewAllNewsAdapter.notifyDataSetChanged();
                getNewsListData();
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

                Toast.makeText(ViewAllNewsActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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

    public  void  getNewsListData(){

        viewAllNewsAdapter.getNewsListData().clear();


        System.out.println("333333333333333333");

        if (newsListData!=null )
            newsListData.clear();

        progress_bar_news.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<NewsList> userResponse = apiService.newsList("03","10","1");
        userResponse.enqueue(new Callback<NewsList>() {

            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {

                swipeRefreshLayout.setRefreshing(false);

                newsList = response.body();

                progress_bar_news.setVisibility(View.GONE);
                news_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));


                    getSupportActionBar().setTitle("News ("+newsList.getCount() +")");


                    if(newsList.getResid().equals("200")) {

                        TOTAL_PAGES = Integer.parseInt(newsList.getTotal_pages());
                        newsListData = newsList.getNewsList();

                        if (newsListData != null) {



                            if(!newsListData.isEmpty()) {
                                viewAllNewsAdapter.addAll(newsListData);
                                if(newsListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) viewAllNewsAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }


                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                progress_bar_news.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error new******" + t.toString());

            }
        });
    }


    public  void  getNewsListDataNext(int c){



        Api apiService = RetrofitClient.getApiService();
        Call<NewsList> userResponse = apiService.newsList("03","10",String.valueOf(c));
        userResponse.enqueue(new Callback<NewsList>() {

            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                newsList = response.body();

                progress_bar_news.setVisibility(View.GONE);


                if (response.isSuccessful()) {

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));


                    if(newsList.getResid().equals("200")) {

                        viewAllNewsAdapter.removeLoadingFooter();
                        isLoading = false;
                        newsListData = newsList.getNewsList();

                        if (newsListData != null) {



                            if(!newsListData.isEmpty()) {

                                viewAllNewsAdapter.addAll(newsListData);

                                if(newsListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage != TOTAL_PAGES) viewAllNewsAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }


                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                progress_bar_news.setVisibility(View.GONE);

                System.out.println("msg1 error new******" + t.toString());

            }
        });
    }



}
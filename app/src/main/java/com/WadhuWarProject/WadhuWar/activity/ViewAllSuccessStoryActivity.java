package com.WadhuWarProject.WadhuWar.activity;

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

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.adapter.HomeProfilesListAdapter;
import com.WadhuWarProject.WadhuWar.adapter.ViewAllSuccessStoryAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.BlogList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllSuccessStoryActivity extends AppCompatActivity   implements  NetworkStateReceiver.NetworkStateReceiverListener{
    Toolbar toolbar;

    RecyclerView success_story_rv;
    RelativeLayout progress_bar_banner_succes_story;

    private NetworkStateReceiver networkStateReceiver;

    ArrayList<BlogList.BlogListData> blogListData = new ArrayList<>();
    BlogList blogList;

    ViewAllSuccessStoryAdapter viewAllSuccessStoryAdapter;
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
        setContentView(R.layout.activity_view_all_success_story);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_all_success_story);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        nestedScroll = findViewById(R.id.nestedScroll);
        swip_topLL = findViewById(R.id.swip_topLL);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        success_story_rv = findViewById(R.id.success_story_rv);
        progress_bar_banner_succes_story = findViewById(R.id.progress_bar_banner_succes_story);
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        linearLayoutManager = new LinearLayoutManager(ViewAllSuccessStoryActivity.this, LinearLayoutManager.VERTICAL, false);
        success_story_rv.setLayoutManager(linearLayoutManager);

        viewAllSuccessStoryAdapter = new ViewAllSuccessStoryAdapter(ViewAllSuccessStoryActivity.this);
        success_story_rv.setItemAnimator(null);
        success_story_rv.setAdapter(viewAllSuccessStoryAdapter);
        success_story_rv.setFocusable(false);

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


        getSuccessStoryListData();
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
        getSuccessStoryListDataNext(currentPage);
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
//        getSuccessStoryListData();


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
                viewAllSuccessStoryAdapter.getBlogListData().clear();
                if(blogListData!=null)
                    blogListData.clear();
                viewAllSuccessStoryAdapter.notifyDataSetChanged();
                getSuccessStoryListData();
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

                Toast.makeText(ViewAllSuccessStoryActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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

    public  void  getSuccessStoryListData(){


        viewAllSuccessStoryAdapter.getBlogListData().clear();

        System.out.println("222222222222222");

        if (blogListData!=null )
            blogListData.clear();

        progress_bar_banner_succes_story.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<BlogList> userResponse = apiService.blogList("02","10","1");
        userResponse.enqueue(new Callback<BlogList>() {

            @Override
            public void onResponse(Call<BlogList> call, Response<BlogList> response) {

                swipeRefreshLayout.setRefreshing(false);
                blogList = response.body();

                progress_bar_banner_succes_story.setVisibility(View.GONE);
                success_story_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    if(blogList.getResid().equals("200")) {

                        getSupportActionBar().setTitle("Success Stories ("+blogList.getCount() +")");


                        TOTAL_PAGES = Integer.parseInt(blogList.getTotal_pages());
                        blogListData = blogList.getBlogList();

                        if (blogListData != null) {
                            System.out.println("success story list  size.......>>>" + blogListData.size());


                            if(!blogListData.isEmpty()) {
                                viewAllSuccessStoryAdapter.addAll(blogListData);
                                if(blogListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) viewAllSuccessStoryAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }

                        }
                    }


                }

            }

            @Override
            public void onFailure(Call<BlogList> call, Throwable t) {
                progress_bar_banner_succes_story.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error success story******" + t.toString());

            }
        });
    }


    public  void  getSuccessStoryListDataNext(int c){

        Api apiService = RetrofitClient.getApiService();
        Call<BlogList> userResponse = apiService.blogList("02","10",String.valueOf(c));
        userResponse.enqueue(new Callback<BlogList>() {

            @Override
            public void onResponse(Call<BlogList> call, Response<BlogList> response) {
                blogList = response.body();

                progress_bar_banner_succes_story.setVisibility(View.GONE);

                isLoading = false;
                isLastPage = true;
                if (response.isSuccessful()) {

                    if (blogList!=null){
                        if(blogList.getResid().equals("200")) {

                            viewAllSuccessStoryAdapter.removeLoadingFooter();
                            isLoading = false;
                            isLastPage = true;

                            blogListData = blogList.getBlogList();

                            if (blogListData != null) {
                                System.out.println("success story list  size.......>>>" + blogListData.size());



                                if(!blogListData.isEmpty()) {

                                    viewAllSuccessStoryAdapter.addAll(blogListData);

                                    if(blogListData.size()<10){
                                        isLastPage = true;
                                        isLoading= false;
                                    }else{
                                        if (currentPage != TOTAL_PAGES) viewAllSuccessStoryAdapter.addLoadingFooter();
                                        else isLastPage = true;
                                    }
                                }

                            }
                        }

                    }else {
                        if (currentPage != TOTAL_PAGES) viewAllSuccessStoryAdapter.addLoadingFooter();
                        else isLastPage = true;
                    }


                }

            }

            @Override
            public void onFailure(Call<BlogList> call, Throwable t) {
                progress_bar_banner_succes_story.setVisibility(View.GONE);
                isLoading = false;
                isLastPage = true;
                System.out.println("msg1 error success story******" + t.toString());

            }
        });
    }


}
package com.WadhuWarProject.WadhuWar.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.WadhuWarProject.WadhuWar.adapter.LikesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.Likes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeShowActivity extends AppCompatActivity   implements  NetworkStateReceiver.NetworkStateReceiverListener{
    Toolbar toolbar;

    RecyclerView like_rv;
    RelativeLayout progress_bar_news;

    private NetworkStateReceiver networkStateReceiver;

    LikesAdapter likesAdapter;

    ArrayList<Likes.LikesData> likesData = new ArrayList<>();
    Likes likes;
    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    static boolean isNetworkAvailable;
    SwipeRefreshLayout swipeRefreshLayout;
    String user_id;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;
    LinearLayout swip_topLL;

    TextView toolbar_title;
    LinearLayout emptyLL;

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
        setContentView(R.layout.activity_like_show);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_like_show);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        like_rv = findViewById(R.id.like_rv);
        progress_bar_news = findViewById(R.id.progress_bar_news);

        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        swip_topLL = findViewById(R.id.swip_topLL);
        nestedScroll =  findViewById(R.id.nestedScroll);
        toolbar_title =  findViewById(R.id.toolbar_title);
        emptyLL =  findViewById(R.id.emptyLL);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.black_arrow_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            user_id =(String) b.get("user_id");

        }

        System.out.println("user_id==========="+ user_id);

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

        getLikeList();

        linearLayoutManager = new LinearLayoutManager(LikeShowActivity.this, LinearLayoutManager.VERTICAL, false);
        like_rv.setLayoutManager(linearLayoutManager);

        likesAdapter = new LikesAdapter(LikeShowActivity.this);
        like_rv.setItemAnimator(null);
        like_rv.setAdapter(likesAdapter);
        like_rv.setFocusable(false);


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

    }

    protected void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        System.out.println("currentPage 11>>>>>>>>>>>" +  currentPage);
        getLikeListNext(currentPage);
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

//        getLikeList();


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
                likesAdapter.getLikesData().clear();
                if(likesData!=null)
                    likesData.clear();
                likesAdapter.notifyDataSetChanged();
                getLikeList();
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

                Toast.makeText(LikeShowActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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


    public  void getLikeList(){


        if(likesAdapter!=null)
            likesAdapter.getLikesData().clear();


        System.out.println("333333333333333333");

        if (likesData!=null )
            likesData.clear();

        progress_bar_news.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<Likes> userResponse = apiService.getLikemembes(user_id,"20","1");
        userResponse.enqueue(new Callback<Likes>() {

            @Override
            public void onResponse(Call<Likes> call, Response<Likes> response) {
                likes = response.body();
                swipeRefreshLayout.setRefreshing(false);
                progress_bar_news.setVisibility(View.GONE);
                like_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {


                    if(likes.getResid().equals("200")) {
//                        System.out.println("like repso.......>>>" + new Gson().toJson(response.body()));

                        emptyLL.setVisibility(View.GONE);

                        toolbar_title.setText("People Who reacted (" +likes.getCount()+")");

                        TOTAL_PAGES = Integer.parseInt(likes.getTotal_pages());

                        likesData = likes.getLikes();
                        if (likesData != null) {

                            if (!likesData.isEmpty()) {
                                likesAdapter.addAll(likesData);
                                if (likesData.size() < 20) {
                                    isLastPage = true;
                                    isLoading = false;
                                } else {
                                    if (currentPage <= TOTAL_PAGES) likesAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }
                    }else{
                        emptyLL.setVisibility(View.VISIBLE);
                    }


                }

            }

            @Override
            public void onFailure(Call<Likes> call, Throwable t) {
                progress_bar_news.setVisibility(View.GONE);
                emptyLL.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 Likes******" + t.toString());
                if(!isNetworkAvailable(LikeShowActivity.this)){
//                    Toast.makeText(LikeShowActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(LikeShowActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public  void  getLikeListNext(int c){



        Api apiService = RetrofitClient.getApiService();
        Call<Likes> userResponse = apiService.getLikemembes(user_id,"20",String.valueOf(c));
        userResponse.enqueue(new Callback<Likes>() {

            @Override
            public void onResponse(Call<Likes> call, Response<Likes> response) {
                likes = response.body();

                progress_bar_news.setVisibility(View.GONE);

                if (response.isSuccessful()) {

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));

                    likesAdapter.removeLoadingFooter();
                    isLoading = false;
                    likesData = likes.getLikes();

                    if(likesData!=null) {
                        if(!likesData.isEmpty()) {

                            likesAdapter.addAll(likesData);

                            if(likesData.size()<20){
                                isLastPage = true;
                                isLoading= false;
                            }else{
                                if (currentPage != TOTAL_PAGES) likesAdapter.addLoadingFooter();
                                else isLastPage = true;
                            }
                        }
                    }


                }

            }

            @Override
            public void onFailure(Call<Likes> call, Throwable t) {
                progress_bar_news.setVisibility(View.GONE);

                System.out.println("msg1 Likes******" + t.toString());
                if(!isNetworkAvailable(LikeShowActivity.this)){
//                    Toast.makeText(LikeShowActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(LikeShowActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



}
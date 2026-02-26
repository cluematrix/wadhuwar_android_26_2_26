package com.WadhuWarProject.WadhuWar.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import com.WadhuWarProject.WadhuWar.adapter.SeeAllPremiumMatchesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.SeeAllRecentVisitorMatchesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.SeeAllRecentlyViewedAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.RecentVisitorMatchesList;
import com.WadhuWarProject.WadhuWar.model.TabRecentlyViewedList;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentlyViewedActivity extends AppCompatActivity  implements  NetworkStateReceiver.NetworkStateReceiverListener{

    LinearLayout upgrade_now_btn;
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    FetchProfile fetchProfile;
    UserData user;

    String gender;
    ImageView close_btn;
    RecyclerView rv;
    RelativeLayout pb;
    FetchProfile fetchProfileLoginUser;

    ArrayList<TabRecentlyViewedList.TabRecentlyViewedListData> tabRecentlyViewedListData = new ArrayList<>();
    TabRecentlyViewedList tabRecentlyViewedList;

    SeeAllRecentlyViewedAdapter seeAllRecentlyViewedAdapter;
    String premium_value;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;


    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    LinearLayoutManager linearLayoutManager;
    LinearLayout emptyLL;
    LinearLayout upgrade_new_btn;
    ImageView close_btn_new;
    LinearLayout swip_topLL;
    NestedScrollView nestedScroll;
    CardView just_joind_cv,new_upgrade_cv;

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
        setContentView(R.layout.activity_recently_viewed);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recently_viewed);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);

        nestedScroll  = findViewById(R.id.nestedScroll);
        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        swip_topLL = findViewById(R.id.swip_topLL);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        emptyLL =  findViewById(R.id.emptyLL);
        close_btn_new =  findViewById(R.id.close_btn_new);
        upgrade_new_btn =  findViewById(R.id.upgrade_new_btn);
        close_btn =  findViewById(R.id.close_btn);
        upgrade_now_btn =  findViewById(R.id.upgrade_now_btn);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        pb = findViewById(R.id.pb);
        just_joind_cv = findViewById(R.id.just_joind_cv);
        new_upgrade_cv = findViewById(R.id.new_upgrade_cv);
        close_btn = findViewById(R.id.close_btn);
        upgrade_now_btn = findViewById(R.id.upgrade_now_btn);


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




        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                just_joind_cv.setVisibility(View.GONE);
            }
        });

        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RecentlyViewedActivity.this,PremiumActivity.class);
                startActivity(i);
            }
        });

        user = SharedPrefManager.getInstance(RecentlyViewedActivity.this).getUser();


        linearLayoutManager = new LinearLayoutManager(RecentlyViewedActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        seeAllRecentlyViewedAdapter = new SeeAllRecentlyViewedAdapter(RecentlyViewedActivity.this);
        rv.setItemAnimator(null);
        rv.setAdapter(seeAllRecentlyViewedAdapter);
        rv.setFocusable(false);
//        rv.setLayoutManager(new LinearLayoutManager(RecentlyViewedActivity.this, LinearLayoutManager.VERTICAL, false));

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

        fetchLoginProfileData();
        recentlyviewed();

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
        recentlyviewedNext(currentPage);
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

//        fetchLoginProfileData();
//        recentlyviewed();

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

                fetchLoginProfileData();
                currentPage = PAGE_START;
                isLastPage = false;
                seeAllRecentlyViewedAdapter.getTabRecentlyViewedListData().clear();
                if(tabRecentlyViewedListData!=null)
                    tabRecentlyViewedListData.clear();
                seeAllRecentlyViewedAdapter.notifyDataSetChanged();
                recentlyviewed();
//                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public  void fetchLoginProfileData(){

        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfileLoginUser = response.body();

                if (response.isSuccessful()) {

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

                    if(fetchProfileLoginUser.getPremium().equals("1")){
                        just_joind_cv.setVisibility(View.GONE);

                        new_upgrade_cv.setVisibility(View.VISIBLE);

                        upgrade_new_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(RecentlyViewedActivity.this, PremiumActivity.class);
                                startActivity(i);
                            }
                        });
                        close_btn_new.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new_upgrade_cv.setVisibility(View.GONE);
                            }
                        });

                    }
                    else{
//                        just_joind_cv.setVisibility(View.VISIBLE);

                        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(RecentlyViewedActivity.this, PremiumActivity.class);
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

                if(!isNetworkAvailable(RecentlyViewedActivity.this)){
//                    Toast.makeText(RecentlyViewedActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(RecentlyViewedActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(RecentlyViewedActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public  void  recentlyviewed(){


        seeAllRecentlyViewedAdapter.getTabRecentlyViewedListData().clear();

        System.out.println("000000000000000000000000");

        if (tabRecentlyViewedListData!=null )
            tabRecentlyViewedListData.clear();

        pb.setVisibility(View.VISIBLE);


        Api apiService = RetrofitClient.getApiService();
        Call<TabRecentlyViewedList> userResponse = apiService.matchesTabRecent("4",String.valueOf(user.getUser_id()),"10","1");
        userResponse.enqueue(new Callback<TabRecentlyViewedList>() {

            @Override
            public void onResponse(Call<TabRecentlyViewedList> call, Response<TabRecentlyViewedList> response) {

                swipeRefreshLayout.setRefreshing(false);

                tabRecentlyViewedList = response.body();

                pb.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    if(tabRecentlyViewedList.getResid().equals("200")) {

                        emptyLL.setVisibility(View.GONE);

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));
                        TOTAL_PAGES = Integer.parseInt(tabRecentlyViewedList.getTotal_pages());

                        tabRecentlyViewedListData = tabRecentlyViewedList.getTabRecentlyViewedList();

                        if (tabRecentlyViewedListData != null) {
                            getSupportActionBar().setTitle("Recently Viewed \n" + "(" + tabRecentlyViewedList.getCount() + ")");

                            if(!tabRecentlyViewedListData.isEmpty()) {
                                seeAllRecentlyViewedAdapter.addAll(tabRecentlyViewedListData);
                                if(tabRecentlyViewedListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) seeAllRecentlyViewedAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }

                            }

                        } else {

                        }

                    } else {
                        emptyLL.setVisibility(View.VISIBLE);

                    }
                }
            }


            @Override
            public void onFailure(Call<TabRecentlyViewedList> call, Throwable t) {
                pb.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error TabRecentlyViewedList******" + t.toString());
                if(!isNetworkAvailable(RecentlyViewedActivity.this)){
//                    Toast.makeText(RecentlyViewedActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(RecentlyViewedActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public  void  recentlyviewedNext(int c) {


        Api apiService = RetrofitClient.getApiService();
        Call<TabRecentlyViewedList> userResponse = apiService.matchesTabRecent("4", String.valueOf(user.getUser_id()), "10", String.valueOf(c));
        userResponse.enqueue(new Callback<TabRecentlyViewedList>() {

            @Override
            public void onResponse(Call<TabRecentlyViewedList> call, Response<TabRecentlyViewedList> response) {
                tabRecentlyViewedList = response.body();

                pb.setVisibility(View.GONE);


                if (response.isSuccessful()) {
                    if (tabRecentlyViewedList.getResid().equals("200")) {



//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));
                        seeAllRecentlyViewedAdapter.removeLoadingFooter();
                        isLoading = false;
                        tabRecentlyViewedListData = tabRecentlyViewedList.getTabRecentlyViewedList();

                        if (tabRecentlyViewedListData != null) {

                            if(!tabRecentlyViewedListData.isEmpty()) {

                                seeAllRecentlyViewedAdapter.addAll(tabRecentlyViewedListData);

                                if(tabRecentlyViewedListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage != TOTAL_PAGES) seeAllRecentlyViewedAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }

                        } else {

                        }

                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<TabRecentlyViewedList> call, Throwable t) {
                pb.setVisibility(View.GONE);

                System.out.println("msg1 error TabRecentlyViewedList******" + t.toString());
                if (!isNetworkAvailable(RecentlyViewedActivity.this)) {
//                    Toast.makeText(RecentlyViewedActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(RecentlyViewedActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
    }//isNetworkAvailable()

}
package com.WadhuWarProject.WadhuWar.activity;

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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
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

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.SeeAllPremiumMatchesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.PremiunMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PremiumMatchesActivity extends AppCompatActivity  implements  NetworkStateReceiver.NetworkStateReceiverListener{


    ImageView close_btn;

    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;

    RecyclerView premium_rv;
    RelativeLayout progress_bar_premium;
    LinearLayout upgrade_now_btn;

    ArrayList<PremiunMatchesList.PremiunMatchesListData> premiunMatchesListData = new ArrayList<>();
    PremiunMatchesList premiunMatchesList;
    SeeAllPremiumMatchesAdapter seeAllPremiumMatchesAdapter;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    UserData user;
    String premium_value;
    CardView just_joind_cv,new_upgrade_cv;
    FetchProfile fetchProfileLoginUser;
    LinearLayout new_txtLL,upgrade_new_btn;
    ImageView close_btn_new;
    LinearLayout swip_topLL;
    NestedScrollView nestedScroll;


    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    LinearLayoutManager linearLayoutManager;
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
        setContentView(R.layout.activity_premium_matches);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
//
//        setContentView(R.layout.activity_premium_matches);


        emptyLL =  findViewById(R.id.emptyLL);
        close_btn_new =  findViewById(R.id.close_btn_new);
        upgrade_new_btn =  findViewById(R.id.upgrade_new_btn);
        close_btn =  findViewById(R.id.close_btn);
        upgrade_now_btn =  findViewById(R.id.upgrade_now_btn);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        premium_rv = findViewById(R.id.premium_rv);
        progress_bar_premium = findViewById(R.id.progress_bar_premium);
        just_joind_cv =  findViewById(R.id.just_joind_cv);
        new_upgrade_cv =  findViewById(R.id.new_upgrade_cv);
        close_btn = findViewById(R.id.close_btn);
        upgrade_now_btn = findViewById(R.id.upgrade_now_btn);
        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        swip_topLL = findViewById(R.id.swip_topLL);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        nestedScroll =  findViewById(R.id.nestedScroll);


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

        linearLayoutManager = new LinearLayoutManager(PremiumMatchesActivity.this, LinearLayoutManager.VERTICAL, false);
        premium_rv.setLayoutManager(linearLayoutManager);

        seeAllPremiumMatchesAdapter = new SeeAllPremiumMatchesAdapter(PremiumMatchesActivity.this);
        premium_rv.setItemAnimator(null);
        premium_rv.setAdapter(seeAllPremiumMatchesAdapter);
        premium_rv.setFocusable(false);


//        premium_rv.setLayoutManager(new LinearLayoutManager(PremiumMatchesActivity.this, LinearLayoutManager.VERTICAL, false));
        user = SharedPrefManager.getInstance(PremiumMatchesActivity.this).getUser();

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            premium_value =(String) b.get("premium_value");
        }
        System.out.println("premium activity premium_value---------" + premium_value);



        if(premium_value.equals("1")){
            just_joind_cv.setVisibility(View.GONE);

            new_upgrade_cv.setVisibility(View.VISIBLE);

            upgrade_new_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PremiumMatchesActivity.this, PremiumActivity.class);
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
            just_joind_cv.setVisibility(View.VISIBLE);

            upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PremiumMatchesActivity.this, PremiumActivity.class);
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

        fetchPremiumListData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


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
        fetchPremiumListDataNext(currentPage);
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

//        fetchPremiumListData();

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
                seeAllPremiumMatchesAdapter.getPremiunMatchesListData().clear();
                if(premiunMatchesListData!=null)
                    premiunMatchesListData.clear();
                seeAllPremiumMatchesAdapter.notifyDataSetChanged();
                fetchPremiumListData();


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

                Toast.makeText(PremiumMatchesActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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


    public  void  fetchPremiumListData(){

        seeAllPremiumMatchesAdapter.getPremiunMatchesListData().clear();

        System.out.println("10101010");

        if (premiunMatchesListData!=null )
            premiunMatchesListData.clear();

        emptyLL.setVisibility(View.GONE);
        progress_bar_premium.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<PremiunMatchesList> userResponse = apiService.premiumMatches(String.valueOf(user.getUser_id()),"10","1");
        userResponse.enqueue(new Callback<PremiunMatchesList>() {

            @Override
            public void onResponse(Call<PremiunMatchesList> call, Response<PremiunMatchesList> response) {

                swipeRefreshLayout.setRefreshing(false);
                premiunMatchesList = response.body();

                progress_bar_premium.setVisibility(View.GONE);
                premium_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));



                    if(premiunMatchesList.getResid().equals("200")) {


                        emptyLL.setVisibility(View.GONE);

                        premiunMatchesListData = premiunMatchesList.getPremiumMatchesList();

                        TOTAL_PAGES = Integer.parseInt(premiunMatchesList.getTotal_pages());


                        getSupportActionBar().setTitle("Premium Matches\n" + "(" + premiunMatchesList.getCount() + ")");

                        if (premiunMatchesListData != null) {

                            if(!premiunMatchesListData.isEmpty()) {
                                seeAllPremiumMatchesAdapter.addAll(premiunMatchesListData);
                                if(premiunMatchesListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) seeAllPremiumMatchesAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }

                            }

                        } else {

                        }
                    }else{
                        emptyLL.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onFailure(Call<PremiunMatchesList> call, Throwable t) {
                progress_bar_premium.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error premium list******" + t.toString());
                if(!isNetworkAvailable(PremiumMatchesActivity.this)){
//                    Toast.makeText(PremiumMatchesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(PremiumMatchesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public  void  fetchPremiumListDataNext(int c){

        System.out.println("10101010");


        Api apiService = RetrofitClient.getApiService();
        Call<PremiunMatchesList> userResponse = apiService.premiumMatches(String.valueOf(user.getUser_id()),"10",String.valueOf(c));
        userResponse.enqueue(new Callback<PremiunMatchesList>() {

            @Override
            public void onResponse(Call<PremiunMatchesList> call, Response<PremiunMatchesList> response) {
                premiunMatchesList = response.body();

                progress_bar_premium.setVisibility(View.GONE);

                if (response.isSuccessful()) {

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));

                    if(premiunMatchesList.getResid().equals("200")) {

                        seeAllPremiumMatchesAdapter.removeLoadingFooter();
                        isLoading = false;
                        premiunMatchesListData = premiunMatchesList.getPremiumMatchesList();



                        if (premiunMatchesListData != null) {



                            if(!premiunMatchesListData.isEmpty()) {

                                seeAllPremiumMatchesAdapter.addAll(premiunMatchesListData);

                                if(premiunMatchesListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage != TOTAL_PAGES) seeAllPremiumMatchesAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }



                        } else {

                        }
                    }else {
                        Toast toast= Toast.makeText(PremiumMatchesActivity.this, premiunMatchesList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }

                }

            }

            @Override
            public void onFailure(Call<PremiunMatchesList> call, Throwable t) {
                progress_bar_premium.setVisibility(View.GONE);

                System.out.println("msg1 error premium list******" + t.toString());
                if(!isNetworkAvailable(PremiumMatchesActivity.this)){
//                    Toast.makeText(PremiumMatchesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(PremiumMatchesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
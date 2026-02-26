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
import com.WadhuWarProject.WadhuWar.adapter.SeeAllNewMatchesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.SeeAllPremiumMatchesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.NewMatchesList;
import com.WadhuWarProject.WadhuWar.model.PremiunMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMatchesActivity extends AppCompatActivity  implements  NetworkStateReceiver.NetworkStateReceiverListener{

    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    ImageView close_btn;

    LinearLayout upgrade_now_btn;
    RecyclerView new_mathces_rv;
    RelativeLayout progress_bar_new_matches;

    ArrayList<NewMatchesList.NewmatchesListData> newmatchesListData = new ArrayList<>();
    NewMatchesList newMatchesList;
    SeeAllNewMatchesAdapter seeAllNewMatchesAdapter;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    String premium_value;

    UserData user;

    CardView just_joind_cv,new_upgrade_cv;
    LinearLayout new_txtLL,upgrade_new_btn;
    ImageView close_btn_new;
    NestedScrollView nestedScroll;
    LinearLayout swip_topLL;

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
        setContentView(R.layout.activity_new_matches);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_matches);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        emptyLL =  findViewById(R.id.emptyLL);
        swip_topLL = findViewById(R.id.swip_topLL);
        nestedScroll =  findViewById(R.id.nestedScroll);
        close_btn_new =  findViewById(R.id.close_btn_new);
        upgrade_new_btn =  findViewById(R.id.upgrade_new_btn);
        close_btn =  findViewById(R.id.close_btn);
        just_joind_cv =  findViewById(R.id.just_joind_cv);
        new_upgrade_cv =  findViewById(R.id.new_upgrade_cv);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        new_mathces_rv = findViewById(R.id.new_mathces_rv);
        progress_bar_new_matches = findViewById(R.id.progress_bar_new_matches);
        close_btn = findViewById(R.id.close_btn);
        just_joind_cv = findViewById(R.id.just_joind_cv);
        upgrade_now_btn = findViewById(R.id.upgrade_now_btn);

        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                just_joind_cv.setVisibility(View.GONE);
            }
        });

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
            premium_value =(String) b.get("premium_value");
        }
        System.out.println("premium activity premium_value---------" + premium_value);


        if(premium_value.equals("1")){
            just_joind_cv.setVisibility(View.GONE);

            new_upgrade_cv.setVisibility(View.VISIBLE);

            upgrade_new_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(NewMatchesActivity.this, PremiumActivity.class);
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
//            just_joind_cv.setVisibility(View.VISIBLE);

            upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(NewMatchesActivity.this, PremiumActivity.class);
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






        linearLayoutManager = new LinearLayoutManager(NewMatchesActivity.this, LinearLayoutManager.VERTICAL, false);
        new_mathces_rv.setLayoutManager(linearLayoutManager);

        seeAllNewMatchesAdapter = new SeeAllNewMatchesAdapter(NewMatchesActivity.this);
        new_mathces_rv.setItemAnimator(null);
        new_mathces_rv.setAdapter(seeAllNewMatchesAdapter);
        new_mathces_rv.setFocusable(false);


//        new_mathces_rv.setLayoutManager(new LinearLayoutManager(NewMatchesActivity.this, LinearLayoutManager.VERTICAL, false));

        user = SharedPrefManager.getInstance(NewMatchesActivity.this).getUser();


        fetchNewMatchesListData();

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
        fetchNewMatchesListDataNext(currentPage);
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



//        fetchNewMatchesListData();

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
                seeAllNewMatchesAdapter.getNewmatchesListData().clear();
                if(newmatchesListData!=null)
                    newmatchesListData.clear();
                seeAllNewMatchesAdapter.notifyDataSetChanged();
                fetchNewMatchesListData();


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

                Toast.makeText(NewMatchesActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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

    public  void  fetchNewMatchesListData(){

        seeAllNewMatchesAdapter.getNewmatchesListData().clear();
        emptyLL.setVisibility(View.GONE);


        System.out.println("10101010");

        if (newmatchesListData!=null )
            newmatchesListData.clear();

        progress_bar_new_matches.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<NewMatchesList> userResponse = apiService.newMatches(String.valueOf(user.getUser_id()),"10","1");
        userResponse.enqueue(new Callback<NewMatchesList>() {

            @Override
            public void onResponse(Call<NewMatchesList> call, Response<NewMatchesList> response) {

                swipeRefreshLayout.setRefreshing(false);
                newMatchesList = response.body();

                progress_bar_new_matches.setVisibility(View.GONE);
                new_mathces_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {


                    if(newMatchesList.getResid().equals("200")) {
                        emptyLL.setVisibility(View.GONE);

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));

                        newmatchesListData = newMatchesList.getNewmatchesList();
                        TOTAL_PAGES = Integer.parseInt(newMatchesList.getTotal_pages());

                        getSupportActionBar().setTitle("New Matches\n" + "(" + newMatchesList.getCount() + ")");
                        if (newmatchesListData != null) {


                            if (!newmatchesListData.isEmpty()) {
                                seeAllNewMatchesAdapter.addAll(newmatchesListData);
                                if (newmatchesListData.size() < 10) {
                                    isLastPage = true;
                                    isLoading = false;
                                } else {
                                    if (currentPage <= TOTAL_PAGES)
                                        seeAllNewMatchesAdapter.addLoadingFooter();
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
            public void onFailure(Call<NewMatchesList> call, Throwable t) {
                progress_bar_new_matches.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error NewMatchesList******" + t.toString());
                if(!isNetworkAvailable(NewMatchesActivity.this)){
//                    Toast.makeText(NewMatchesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(NewMatchesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public  void  fetchNewMatchesListDataNext(int c){

        System.out.println("10101010");

        Api apiService = RetrofitClient.getApiService();
        Call<NewMatchesList> userResponse = apiService.newMatches(String.valueOf(user.getUser_id()),"10",String.valueOf(c));
        userResponse.enqueue(new Callback<NewMatchesList>() {

            @Override
            public void onResponse(Call<NewMatchesList> call, Response<NewMatchesList> response) {
                newMatchesList = response.body();

                progress_bar_new_matches.setVisibility(View.GONE);

                if (response.isSuccessful()) {

//                    System.out.println("resp success story 11.......>>>" + new Gson().toJson(response.body()));

                    seeAllNewMatchesAdapter.removeLoadingFooter();
                    isLoading = false;
                    newmatchesListData = newMatchesList.getNewmatchesList();

                    if(newmatchesListData!=null) {


                        if(!newmatchesListData.isEmpty()) {

                            seeAllNewMatchesAdapter.addAll(newmatchesListData);

                            if(newmatchesListData.size()<10){
                                isLastPage = true;
                                isLoading= false;
                            }else{
                                if (currentPage != TOTAL_PAGES) seeAllNewMatchesAdapter.addLoadingFooter();
                                else isLastPage = true;
                            }
                        }


                    }else{

                    }


                }

            }

            @Override
            public void onFailure(Call<NewMatchesList> call, Throwable t) {
                progress_bar_new_matches.setVisibility(View.GONE);

                System.out.println("msg1 error NewMatchesList******" + t.toString());
                if(!isNetworkAvailable(NewMatchesActivity.this)){
//                    Toast.makeText(NewMatchesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(NewMatchesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
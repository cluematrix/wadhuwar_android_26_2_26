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
import android.os.Handler;
import android.util.Log;
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
import com.WadhuWarProject.WadhuWar.adapter.SearchResultsAdapter;
import com.WadhuWarProject.WadhuWar.adapter.SearchResultsMoreAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.SearchData;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity  implements  NetworkStateReceiver.NetworkStateReceiverListener{

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

    ArrayList<SearchData.SearchDataList> searchDataLists =  new ArrayList<>();
    SearchData searchData;

    SearchResultsAdapter searchResultsAdapter;
    SearchResultsMoreAdapter searchResultsMoreAdapter;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    ProgressDialog progressBar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;

    String user_id,_name,gender_str,marital_id,agefrom_list_str,ageTo_list_str, height_str = "", height_to_str = "", salary_str = "",caste_id,religion_id,subcaste_id,state_id,district_id,education_id,
            dietray_id,occupation_id,sampraday_id,manglik_str,handicap_str , education_type_id,occupation_type_id,
            mother_tongue_pckg_list;


    LinearLayout swip_topLL;
    CardView just_joind_cv,new_upgrade_cv;
    TextView count_new;
    LinearLayout new_txtLL,upgrade_new_btn;
    ImageView close_btn_new;

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
        setContentView(R.layout.activity_search_results);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_results);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        close_btn_new =  findViewById(R.id.close_btn_new);
        upgrade_new_btn =  findViewById(R.id.upgrade_new_btn);
        close_btn =  findViewById(R.id.close_btn);
        upgrade_now_btn =  findViewById(R.id.upgrade_now_btn);
        just_joind_cv =  findViewById(R.id.just_joind_cv);
        new_upgrade_cv =  findViewById(R.id.new_upgrade_cv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        pb = findViewById(R.id.pb);
        just_joind_cv = findViewById(R.id.just_joind_cv);
        close_btn = findViewById(R.id.close_btn);
        upgrade_now_btn = findViewById(R.id.upgrade_now_btn);
        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        swip_topLL = findViewById(R.id.swip_topLL);
        swipeRefreshLayout =  findViewById(R.id.swipeRefreshLayout);
        nestedScroll =  findViewById(R.id.nestedScroll);


        rv.setHasFixedSize(true);



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

                Intent i = new Intent(SearchResultsActivity.this,PremiumActivity.class);
                startActivity(i);
            }
        });

        user = SharedPrefManager.getInstance(SearchResultsActivity.this).getUser();


        if(getIntent().getExtras() != null) {


            user_id = getIntent().getStringExtra("user_id");
            _name = getIntent().getStringExtra("_name");
            gender_str = getIntent().getStringExtra("gender_str");
            marital_id = getIntent().getStringExtra("marital_id");
            agefrom_list_str = getIntent().getStringExtra("agefrom_list_str");
            ageTo_list_str = getIntent().getStringExtra("ageTo_list_str");
            religion_id = getIntent().getStringExtra("religion_id");
            caste_id = getIntent().getStringExtra("caste_id");
            subcaste_id = getIntent().getStringExtra("subcaste_id");
            state_id = getIntent().getStringExtra("state_id");
            district_id = getIntent().getStringExtra("district_id");
            education_id = getIntent().getStringExtra("education_id");
            occupation_id = getIntent().getStringExtra("occupation_id");
            dietray_id = getIntent().getStringExtra("dietray_id");
            sampraday_id = getIntent().getStringExtra("sampraday_id");
            manglik_str = getIntent().getStringExtra("manglik_str");
            handicap_str = getIntent().getStringExtra("handicap_str");
            education_type_id = getIntent().getStringExtra("education_type_id");
            occupation_type_id = getIntent().getStringExtra("occupation_type_id");
            height_str = getIntent().getStringExtra("height_str");
            height_to_str = getIntent().getStringExtra("height_to_str");
            salary_str = getIntent().getStringExtra("salary_str");
            mother_tongue_pckg_list = getIntent().getStringExtra("mother_tongue_pckg_list");



        }





        linearLayoutManager = new LinearLayoutManager(SearchResultsActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        /*--------*/
        searchResultsAdapter = new SearchResultsAdapter(SearchResultsActivity.this);
        rv.setItemAnimator(null);
        rv.setAdapter(searchResultsAdapter);
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

                            nestedScroll.smoothScrollTo(0,0);

                        }
                    });


                } else {
                    swip_topLL.setVisibility(View.GONE);

                }

            }
        });

        fetchProfileData();

        submitData();

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
        submitDataNext(currentPage);
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

//        fetchProfileData();
//
//        submitData();


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

                fetchProfileData();

                currentPage = PAGE_START;
                isLastPage = false;
                searchResultsAdapter.getSearchDataList().clear();
                if(searchDataLists!=null)
                    searchDataLists.clear();
                searchResultsAdapter.notifyDataSetChanged();
                submitData();
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
    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }


    public  void submitData(){
        searchResultsAdapter.getSearchDataList().clear();



        pb.setVisibility(View.VISIBLE);
        System.out.println("user_id===============" + user_id);
        System.out.println("_name===============" + _name);
        System.out.println("gender_str===============" + gender_str);
        System.out.println("marital_id===============" + marital_id);
        System.out.println("agefrom_list_str===============" + agefrom_list_str);
        System.out.println("ageTo_list_str===============" + ageTo_list_str);
        System.out.println("religion_id===============" + religion_id);
        System.out.println("caste_id===============" + caste_id);
        System.out.println("state_id===============" + state_id);
        System.out.println("district_id===============" + district_id);
        System.out.println("sampraday_id===============" + sampraday_id);
        System.out.println("manglik_str===============" + manglik_str);
        System.out.println("handicap_str===============" + handicap_str);



        Api apiService = RetrofitClient.getApiService();
        Call<SearchData> userResponse = apiService.searchProfile(gender_str.trim(), user_id,agefrom_list_str,ageTo_list_str,
                religion_id,caste_id,subcaste_id,education_id,occupation_id,state_id,district_id,marital_id,_name,
                dietray_id,sampraday_id,manglik_str,handicap_str,"10","1",education_type_id,occupation_type_id,
                height_str, height_to_str, salary_str,mother_tongue_pckg_list);
        userResponse.enqueue(new Callback<SearchData>() {

            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                searchData = response.body();

                if (response.isSuccessful()) {
                    pb.setVisibility(View.GONE);
                    String fullJson = new Gson().toJson(searchData);
                    Log.d("API_FULL_RESPONSE", fullJson);

                    getSupportActionBar().setTitle("Search Result (" +searchData.getCount() + ")");

                    rv.setVisibility(View.VISIBLE);
//                    System.out.println("search resp ========" + new Gson().toJson(searchData));
                    String success = searchData.getResid();

                    if (success.equals("200")) {
                        TOTAL_PAGES = Integer.parseInt(searchData.getTotal_pages());
                        searchDataLists = searchData.getSearchData();


                        System.out.println("TOTAL_PAGES-------------" + TOTAL_PAGES);


                        if (searchDataLists != null) {

                            if(!searchDataLists.isEmpty()) {
                                searchResultsAdapter.addAll(searchDataLists);
                                if(searchDataLists.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) searchResultsAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }


                    }else {

                    }
                }

            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                pb.setVisibility(View.GONE);


                System.out.println("err basic info******" + t.toString());

                if(!isNetworkAvailable(SearchResultsActivity.this)){
                    Toast.makeText(SearchResultsActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SearchResultsActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }


    public void submitDataNext(int c) {
        Api apiService = RetrofitClient.getApiService();
        Call<SearchData> userResponse = apiService.searchProfile(gender_str.trim(), user_id, agefrom_list_str, ageTo_list_str,
                religion_id, caste_id, subcaste_id, education_id, occupation_id, state_id, district_id, marital_id, _name,
                dietray_id, sampraday_id, manglik_str, handicap_str, "10", String.valueOf(c), education_type_id, occupation_type_id,
                height_str, height_to_str, salary_str, mother_tongue_pckg_list);
        userResponse.enqueue(new Callback<SearchData>() {

            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                searchData = response.body();
                if (response.isSuccessful() && searchData != null) {
                    String success = searchData.getResid();

                    if (success.equals("200")) {
                        searchResultsAdapter.removeLoadingFooter();
                        isLoading = false;
                        searchDataLists = searchData.getSearchData();

                        System.out.println("TOTAL_PAGES-------------" + TOTAL_PAGES);

                        if (searchDataLists != null && !searchDataLists.isEmpty()) {
                            // Calculate the position to scroll to (before adding new items)
                            int scrollToPosition = searchResultsAdapter.getItemCount();
                            // Add new items to the adapter
                            searchResultsAdapter.addAll(searchDataLists);
                            // Scroll to the first item of the newly loaded page
                            linearLayoutManager.scrollToPosition(scrollToPosition);

                            if (searchDataLists.size() < 10) {
                                isLastPage = true;
                                isLoading = false;
                            } else {
                                if (currentPage != TOTAL_PAGES) searchResultsAdapter.addLoadingFooter();
                                else isLastPage = true;
                            }
                        }
                    } else {
                        // Handle non-200 response (e.g., show empty state or error message)
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                System.out.println("err basic info******" + t.toString());

                if (!isNetworkAvailable(SearchResultsActivity.this)) {
                    Toast.makeText(SearchResultsActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchResultsActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    public  void fetchProfileData(){

        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {

                swipeRefreshLayout.setRefreshing(false);
                fetchProfile = response.body();
                if (response.isSuccessful()) {


                    if (fetchProfile.getResid().equals("200")) {
                        SharedPrefManager.getInstance(SearchResultsActivity.this).saveProfileData(fetchProfile);


                        // new for user create befor 1 year or not show box
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
                        //

                        if(fetchProfile.getPremium().equals("1")){
                            just_joind_cv.setVisibility(View.GONE);

                            new_upgrade_cv.setVisibility(View.VISIBLE);

                            upgrade_new_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(SearchResultsActivity.this, PremiumActivity.class);
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
//                            just_joind_cv.setVisibility(View.VISIBLE);

                            upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(SearchResultsActivity.this, PremiumActivity.class);
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

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
                if(!isNetworkAvailable(SearchResultsActivity.this)){
//                    Toast.makeText(SearchResultsActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(SearchResultsActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }






    @Override
    public void networkUnavailable() {
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
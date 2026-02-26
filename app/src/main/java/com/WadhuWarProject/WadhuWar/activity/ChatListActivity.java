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
import android.view.LayoutInflater;
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
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.ChatListAdapter;
import com.WadhuWarProject.WadhuWar.adapter.ChatMyMatchesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.ViewAllNewsAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.listner.PaginationListener;
import com.WadhuWarProject.WadhuWar.listner.PaginationScrollListener;
import com.WadhuWarProject.WadhuWar.model.Chat;
import com.WadhuWarProject.WadhuWar.model.ChatList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    ChatListAdapter chatListAdapter;
    ChatMyMatchesAdapter chatMyMatchesAdapter;
    ArrayList<TabMyMatchesList.TabMyMatchesListData> myMatchesListData = new ArrayList<>();
    TabMyMatchesList myMatchesList;
    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    TextView recenttxt, mymatchestxt;
    UserData user;
    private NetworkStateReceiver networkStateReceiver;
    RecyclerView rv, rvMymatches;
    RelativeLayout progress_bar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private static final int PAGE_START11 = 1;
    private boolean isLoading11 = false;
    private boolean isLastPage11 = false;
    private int TOTAL_PAGES11;
    private int currentPage11 = PAGE_START11;


    NestedScrollView nestedScroll;
    ArrayList<ChatList.ChatListData> chatListData = new ArrayList<>();
    ChatList chatList;
    LinearLayout swip_topLL;
    LinearLayoutManager linearLayoutManager, linearLayoutManagerMyMacthes;

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
        setContentView(R.layout.activity_chat_list);

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
//        setContentView(R.layout.activity_chat_list);
        nestedScroll = findViewById(R.id.nestedScroll);
        rvMymatches = findViewById(R.id.rvMymatches);
        swip_topLL = findViewById(R.id.swip_topLL);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        internetOffRL = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        rv = findViewById(R.id.rv);
        progress_bar = findViewById(R.id.progress_bar);
        recenttxt = findViewById(R.id.recentchatstxt);
        mymatchestxt = findViewById(R.id.mymatchesChatstxt);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Chat");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });
        user = SharedPrefManager.getInstance(ChatListActivity.this).getUser();
        linearLayoutManager = new LinearLayoutManager(ChatListActivity.this, LinearLayoutManager.VERTICAL, false);

        rv.setLayoutManager(linearLayoutManager);

        chatListAdapter = new ChatListAdapter(ChatListActivity.this);
        rv.setItemAnimator(null);


        rv.setAdapter(chatListAdapter);
        rv.setFocusable(false);

        linearLayoutManagerMyMacthes = new LinearLayoutManager(ChatListActivity.this, LinearLayoutManager.HORIZONTAL, false);

        rvMymatches.setLayoutManager(linearLayoutManagerMyMacthes);
        chatMyMatchesAdapter = new ChatMyMatchesAdapter(ChatListActivity.this);
        rvMymatches.setItemAnimator(null);
        rvMymatches.setAdapter(chatMyMatchesAdapter);
        rvMymatches.setFocusable(false);


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


        fetchChatList();
        checkMyMatches();


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


    }


    protected void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        System.out.println("currentPage 11>>>>>>>>>>>" + currentPage);
        fetchChatListNext(currentPage);
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
//        fetchChatList();
//        checkMyMatches();


        rvMymatches.addOnScrollListener(new PaginationListener(linearLayoutManagerMyMacthes) {
            @Override
            protected void loadMoreItems() {
                isLoading11 = true;
                currentPage11++;
                System.out.println("new page=========" + currentPage11);
                loadNextPage11(currentPage11);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage11;
            }

            @Override
            public boolean isLoading() {
                return isLoading11;
            }
        });


        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (v.getChildAt(v.getChildCount() - 1) != null) {
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

                currentPage11 = PAGE_START11;
                isLastPage11 = false;


                chatListAdapter.getNewsListData().clear();
                chatMyMatchesAdapter.tabMyMatchesListData().clear();

                if (chatListData != null)
                    chatListData.clear();
                chatListAdapter.notifyDataSetChanged();
                chatMyMatchesAdapter.notifyDataSetChanged();

                fetchChatList();
                checkMyMatches();

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


    public void checkMyMatches() {

        if (myMatchesListData != null)
            myMatchesListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<TabMyMatchesList> userResponse = apiService.matchesTabMyMatches("2", String.valueOf(user.getUser_id()), "10", "1");
        userResponse.enqueue(new Callback<TabMyMatchesList>() {

            @Override
            public void onResponse(Call<TabMyMatchesList> call, Response<TabMyMatchesList> response) {
                myMatchesList = response.body();
                mymatchestxt.setVisibility(View.VISIBLE);


                if (response.isSuccessful()) {

                    if (myMatchesList.getResid().equals("200")) {
                        TOTAL_PAGES11 = Integer.parseInt(myMatchesList.getTotal_pages());


                        rvMymatches.setVisibility(View.VISIBLE);
                        myMatchesListData = myMatchesList.getTabMyMatchesList();
                        if (myMatchesListData != null) {

                            if (!myMatchesListData.isEmpty()) {
                                chatMyMatchesAdapter.addAll(myMatchesListData);

                                System.out.println("myMatchesListData szie 1 =============" + myMatchesListData.size());

                                if (myMatchesListData.size() < 10) {
                                    isLastPage11 = true;
                                    isLoading11 = false;
                                } else {
                                    if (currentPage11 <= TOTAL_PAGES11)
                                        chatMyMatchesAdapter.addLoadingFooter();
                                    else isLastPage11 = true;
                                }

                            }


                        }
                    } else {
                        System.out.println("no data----------- 22");

                        /*Toast toast = Toast.makeText(getActivity(), tabMyMatchesList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();*/
                        rvMymatches.setVisibility(View.GONE);

                    }
                }


            }

            @Override
            public void onFailure(Call<TabMyMatchesList> call, Throwable t) {

                System.out.println("msg1 error TabMyMatchesList******" + t.toString());

            }
        });
    }

    public void loadNextPage11(int pos) {
        System.out.println("222222==============");

        Api apiService = RetrofitClient.getApiService();
        Call<TabMyMatchesList> userResponse = apiService.matchesTabMyMatches("2", String.valueOf(user.getUser_id()), "10", String.valueOf(pos));
        userResponse.enqueue(new Callback<TabMyMatchesList>() {

            @Override
            public void onResponse(Call<TabMyMatchesList> call, Response<TabMyMatchesList> response) {
                myMatchesList = response.body();
//                rvMymatches.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    System.out.println("tabMyMatchesList.getResid() 123===========" + myMatchesList.getResid());

                    if (myMatchesList.getResid().equals("200")) {
//                        System.out.println("chat resp 222222.......>>>" + new Gson().toJson(response.body()));
                        chatMyMatchesAdapter.removeLoadingFooter();
                        isLoading11 = false;

                        myMatchesListData = myMatchesList.getTabMyMatchesList();
                        if (myMatchesListData != null) {


                            if (!myMatchesListData.isEmpty()) {

                                chatMyMatchesAdapter.addAll(myMatchesListData);

                                System.out.println("size check 22 ===========" + myMatchesListData.size());

                                if (myMatchesListData.size() < 10) {
                                    isLastPage11 = true;
                                    isLoading11 = false;
                                } else {
                                    if (currentPage11 != TOTAL_PAGES11)
                                        chatMyMatchesAdapter.addLoadingFooter();
                                    else isLastPage11 = true;
                                }
                            }


                        }
                    } else {
                        System.out.println("no data----------- 22");

                        chatMyMatchesAdapter.removeLoadingFooter();
                        /*Toast toast = Toast.makeText(getActivity(), tabMyMatchesList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();*/


                    }
                }


            }

            @Override
            public void onFailure(Call<TabMyMatchesList> call, Throwable t) {
                System.out.println("msg1 error TabMyMatchesList******" + t.toString());

            }
        });
    }


    public void fetchChatList() {

        chatListAdapter.getNewsListData().clear();


        System.out.println("333333333333333333");

        if (chatListData != null)
            chatListData.clear();

        progress_bar.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<ChatList> userResponse = apiService.getChatList(String.valueOf(user.getUser_id()), "1", "20");
        userResponse.enqueue(new Callback<ChatList>() {

            @Override
            public void onResponse(Call<ChatList> call, Response<ChatList> response) {
                swipeRefreshLayout.setRefreshing(false);
                chatList = response.body();

                progress_bar.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);

                recenttxt.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

//                    System.out.println("caht list resp.......>>>" + new Gson().toJson(response.body()));

                    if (chatList.getResid().equals("200")) {

                        TOTAL_PAGES = Integer.parseInt(chatList.getTotal_pages());
                        chatListData = chatList.getChatList();

                        System.out.println("chatListData size 11.......>>>" + chatListData.size());
//                        System.out.println("chatListData size 22.......>>>" + new Gson().toJson(chatListData));

                        if (chatListData != null) {

                            if (!chatListData.isEmpty()) {
                                chatListAdapter.addAll(chatListData);
                                if (chatListData.size() < 10) {
                                    isLastPage = true;
                                    isLoading = false;
                                } else {
                                    if (currentPage <= TOTAL_PAGES)
                                        chatListAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }


                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ChatList> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error chat list******" + t.toString());

            }
        });
    }

    public void fetchChatListNext(int c) {
        Api apiService = RetrofitClient.getApiService();
        Call<ChatList> userResponse = apiService.getChatList(String.valueOf(user.getUser_id()), String.valueOf(c), "20");
        userResponse.enqueue(new Callback<ChatList>() {

            @Override
            public void onResponse(Call<ChatList> call, Response<ChatList> response) {
                chatList = response.body();

                progress_bar.setVisibility(View.GONE);


                if (response.isSuccessful()) {

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));


                    if (chatList.getResid().equals("200")) {

                        chatListAdapter.removeLoadingFooter();
                        isLoading = false;
                        chatListData = chatList.getChatList();

                        if (chatListData != null) {


                            if (!chatListData.isEmpty()) {

                                chatListAdapter.addAll(chatListData);

                                if (chatListData.size() < 20) {
                                    isLastPage = true;
                                    isLoading = false;
                                } else {
                                    if (currentPage != TOTAL_PAGES)
                                        chatListAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }


                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ChatList> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);

                System.out.println("msg1 error ChatList******" + t.toString());

            }
        });
    }


    @Override
    public void networkUnavailable() {

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(ChatListActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try_again_txt.setVisibility(View.GONE);
                simpleProgressBar.setVisibility(View.VISIBLE);
                if (!isNetworkAvailable()) {
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
                } else {
                    networkUnavailable();
                }

            }
        });
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }


}
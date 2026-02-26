package com.WadhuWarProject.WadhuWar.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.MainActivity;
import com.WadhuWarProject.WadhuWar.adapter.ChatListAdapter;
import com.WadhuWarProject.WadhuWar.adapter.ChatMyMatchesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.listner.PaginationListener;
import com.WadhuWarProject.WadhuWar.model.ChatList;
import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InboxChatMessageFragment
        extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {


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

    public static InboxChatMessageFragment newInstance() {
        InboxChatMessageFragment fragment = new InboxChatMessageFragment();
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_inbox_chat_message, container, false);
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }

        nestedScroll = view.findViewById(R.id.nestedScroll);
        rvMymatches = view.findViewById(R.id.rvMymatches);
        swip_topLL = view.findViewById(R.id.swip_topLL);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        internetOffRL = (RelativeLayout) view.findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) view.findViewById(R.id.simpleProgressBar);
        try_again_txt = (TextView) view.findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) view.findViewById(R.id.couldnt_reach_internet_txt);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rv = view.findViewById(R.id.rv);
        progress_bar = view.findViewById(R.id.progress_bar);
        recenttxt = view.findViewById(R.id.recentchatstxt);
        mymatchestxt = view.findViewById(R.id.mymatchesChatstxt);


        user = SharedPrefManager.getInstance(getContext()).getUser();
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rv.setLayoutManager(linearLayoutManager);

        chatListAdapter = new ChatListAdapter(getContext());
        rv.setItemAnimator(null);


        rv.setAdapter(chatListAdapter);
        rv.setFocusable(false);

        linearLayoutManagerMyMacthes = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rvMymatches.setLayoutManager(linearLayoutManagerMyMacthes);
        chatMyMatchesAdapter = new ChatMyMatchesAdapter(getContext());
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
        getContext().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


        return view;

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

                MainActivity.getMsgCount(getContext(), user);
                fetchChatList();
                checkMyMatches();


//                swipeRefreshLayout.setRefreshing(false);
            }
        });

        MainActivity.getMsgCount(getContext(), user);
        final Handler handler = new Handler();

        Runnable delayrunnable = new Runnable() {

            @Override
            public void run() {
                MainActivity.getMsgCount(getContext(), user);
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

                Toast.makeText(getContext(), "Please check internet connection!", Toast.LENGTH_SHORT).show();

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

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

}
package com.WadhuWarProject.WadhuWar.fragments;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.PartnerPreferencesActivity;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.adapter.TabNearMeMatchesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.TabNearMeMatchesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.TabNearMeMatchesList;
import com.WadhuWarProject.WadhuWar.model.TabNearMeMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.gms.common.config.GservicesValue;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchesNearMeTabFragment extends Fragment  implements  NetworkStateReceiver.NetworkStateReceiverListener{


    static boolean isNetworkAvailable;
    UserData user;
    FetchProfile fetchProfile;
    ProgressDialog progressBar;

    TabNearMeMatchesAdapter tabNearMeMatchesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private NetworkStateReceiver networkStateReceiver;

    String gender;
    RelativeLayout progress_bar_main;

    ArrayList<TabNearMeMatchesList.MatchesTabNearListData> matchesTabNearListData = new ArrayList<>();
    TabNearMeMatchesList tabNearMeMatchesList;


    RecyclerView rv;
    LinearLayout emptyLL;


    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;

    LinearLayout swip_topLL;


    CardView just_joind_cv,new_upgrade_cv;
    TextView count_new;
    LinearLayout new_txtLL,upgrade_now_btn,upgrade_new_btn;
    ImageView close_btn,close_btn_new;
    FetchProfile fetchProfileLoginUser;
    TextView edit_txt;

    public static MatchesNearMeTabFragment newInstance() {
        MatchesNearMeTabFragment fragment = new MatchesNearMeTabFragment();
        return fragment;
    }


    public MatchesNearMeTabFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }
    }



    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_matches_near_me_tab, container, false);

        close_btn_new =  view.findViewById(R.id.close_btn_new);
        upgrade_new_btn =  view.findViewById(R.id.upgrade_new_btn);
        new_upgrade_cv =  view.findViewById(R.id.new_upgrade_cv);

        edit_txt =  view.findViewById(R.id.edit_txt);
        close_btn =  view.findViewById(R.id.close_btn);
        upgrade_now_btn =  view.findViewById(R.id.upgrade_now_btn);
        just_joind_cv =  view.findViewById(R.id.just_joind_cv);
        count_new =  view.findViewById(R.id.count_new);
        new_txtLL =  view.findViewById(R.id.new_txtLL);
        swip_topLL =  view.findViewById(R.id.swip_topLL);
        emptyLL =  view.findViewById(R.id.emptyLL);
        progress_bar_main =  view.findViewById(R.id.progress_bar_main);
        rv =  view.findViewById(R.id.rv);
        nestedScroll =  view.findViewById(R.id.nestedScroll);
        swipeRefreshLayout =  view.findViewById(R.id.swipeRefreshLayout);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        user = SharedPrefManager.getInstance(getActivity()).getUser();


        /*--------*/
        tabNearMeMatchesAdapter = new TabNearMeMatchesAdapter(getActivity());
        rv.setItemAnimator(null);
        rv.setAdapter(tabNearMeMatchesAdapter);
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
        edit_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PartnerPreferencesActivity.class);
                startActivity(i);
            }
        });


        fetchLoginUSerData();

        loadFirstPage();


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/
        return view;

    }




    protected void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        System.out.println("currentPage 11>>>>>>>>>>>" +  currentPage);
        loadNextPage(currentPage);
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
//        fetchLoginUSerData();
//
//        loadFirstPage();


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

//        if (swipeRefreshLayout.isRefreshing()) {
//            System.out.println("refresh already---------");
////            swipeRefreshLayout.setRefreshing(false);
//        }else{
//            System.out.println("not refresh already---------");
//
//        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                fetchLoginUSerData();


                currentPage = PAGE_START;
                isLastPage = false;

                System.out.println("size check================"+tabNearMeMatchesAdapter.matchesTabNearListData().size());

//                if(tabNearMeMatchesAdapter.matchesTabNearListData().size()!=0)
                    tabNearMeMatchesAdapter.matchesTabNearListData().clear();

                if(matchesTabNearListData!=null)
                    matchesTabNearListData.clear();
//                tabNearMeMatchesAdapter.notifyDataSetChanged();
                loadFirstPage();
//                swipeRefreshLayout.setRefreshing(false);

            }
        });





    }



    public  void fetchLoginUSerData(){


        System.out.println("user_id12345=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfileLoginUser = response.body();

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
                            Intent i = new Intent(getActivity(), PremiumActivity.class);
                            startActivity(i);
                        }
                    });
                    close_btn_new.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new_upgrade_cv.setVisibility(View.GONE);
                        }
                    });

                }else{
//                    just_joind_cv.setVisibility(View.VISIBLE);

                    upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), PremiumActivity.class);
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

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());


            }
        });

    }



    @Override
    public void networkUnavailable() {

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(getActivity(),"Please check internet connection!",Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }


    public void loadFirstPage(){



        progress_bar_main.setVisibility(View.VISIBLE);


        if (matchesTabNearListData!=null )
            matchesTabNearListData.clear();

        new_txtLL.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        emptyLL.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<TabNearMeMatchesList> userResponse = apiService.matchesTabNear("5",String.valueOf(user.getUser_id()),"10","1");
        userResponse.enqueue(new Callback<TabNearMeMatchesList>() {

            @Override
            public void onResponse(Call<TabNearMeMatchesList> call, Response<TabNearMeMatchesList> response) {

                swipeRefreshLayout.setRefreshing(false);
                tabNearMeMatchesList = response.body();
                progress_bar_main.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);


                System.out.println("near resp==========" + new Gson().toJson(response.body()));
                System.out.println("mymatchesTabList-------------");
                System.out.println("gender------------"+ gender);
                if (response.isSuccessful()) {
                    System.out.println("tabNearMeMatchesList.getResid() 123===========" + tabNearMeMatchesList.getResid());

                    if (tabNearMeMatchesList.getResid().equals("200")) {
//                        System.out.println("resp 222222.......>>>" + new Gson().toJson(response.body()));

                        new_txtLL.setVisibility(View.VISIBLE);

                        count_new.setText("("+ tabNearMeMatchesList.getCount()+")");

                        emptyLL.setVisibility(View.GONE);

                        TOTAL_PAGES = Integer.parseInt(tabNearMeMatchesList.getTotal_pages());


                        matchesTabNearListData = tabNearMeMatchesList.getMatchesTabNearList();

                        System.out.println("api size near-=========" +  matchesTabNearListData.size());
                        if (matchesTabNearListData != null) {

                            if(!matchesTabNearListData.isEmpty()) {
                                tabNearMeMatchesAdapter.addAll(matchesTabNearListData);
                                if(matchesTabNearListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) tabNearMeMatchesAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }

                            }



                        }
                    }
                    else  {
                        System.out.println("no data----------- 22");

                        /*Toast toast = Toast.makeText(getActivity(), tabNearMeMatchesList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();*/
                        emptyLL.setVisibility(View.VISIBLE);
                        new_txtLL.setVisibility(View.GONE);
                        rv.setVisibility(View.GONE);

                    }
                }




            }

            @Override
            public void onFailure(Call<TabNearMeMatchesList> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error TabNearMeMatchesList******" + t.toString());

            }
        });
    }



    public void loadNextPage(int pos){

        emptyLL.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<TabNearMeMatchesList> userResponse = apiService.matchesTabNear("5",String.valueOf(user.getUser_id()),"10", String.valueOf(pos));
        userResponse.enqueue(new Callback<TabNearMeMatchesList>() {

            @Override
            public void onResponse(Call<TabNearMeMatchesList> call, Response<TabNearMeMatchesList> response) {
                tabNearMeMatchesList = response.body();
                progress_bar_main.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);

                System.out.println("mymatchesTabList-------------");
                System.out.println("gender------------"+ gender);
                if (response.isSuccessful()) {
                    System.out.println("tabNearMeMatchesList.getResid() 123===========" + tabNearMeMatchesList.getResid());

                    if (tabNearMeMatchesList.getResid().equals("200")) {
//                        System.out.println("resp 222222.......>>>" + new Gson().toJson(response.body()));
                        emptyLL.setVisibility(View.GONE);

                        tabNearMeMatchesAdapter.removeLoadingFooter();
                        isLoading = false;

                        matchesTabNearListData = tabNearMeMatchesList.getMatchesTabNearList();
                        if (matchesTabNearListData != null) {

                            if(!matchesTabNearListData.isEmpty()) {

                                tabNearMeMatchesAdapter.addAll(matchesTabNearListData);

                                if(matchesTabNearListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage != TOTAL_PAGES) tabNearMeMatchesAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }
                    }
                    else  {
                        System.out.println("no data----------- 22");

                        /*Toast toast = Toast.makeText(getActivity(), tabNearMeMatchesList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();*/
                        emptyLL.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<TabNearMeMatchesList> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);

                System.out.println("msg1 error TabNearMeMatchesList******" + t.toString());

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


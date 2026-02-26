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
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.adapter.TabNewMatchesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.NewmatchesTabList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllMatchesTabFragment extends Fragment  implements
        NetworkStateReceiver.NetworkStateReceiverListener{

    static boolean isNetworkAvailable;
    UserData user;
    FetchProfile fetchProfile;
    ProgressDialog progressBar;
    TabNewMatchesAdapter newMatchesAdapterScroll;

    SwipeRefreshLayout swipeRefreshLayout;
    private NetworkStateReceiver networkStateReceiver;
    String gender;
    RelativeLayout progress_bar_main;

    ArrayList<NewmatchesTabList.NewmatchesTabListData> newmatchesListData = new ArrayList<>();
    NewmatchesTabList newMatchesList;



    RecyclerView rv;
    LinearLayout emptyLL;
    LinearLayout swip_topLL;


    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;

    CardView just_joind_cv,new_upgrade_cv;
    TextView count_new;
    LinearLayout new_txtLL,upgrade_now_btn,upgrade_new_btn;
    ImageView close_btn,close_btn_new;
    FetchProfile fetchProfileLoginUser;



    public static AllMatchesTabFragment newInstance() {
        AllMatchesTabFragment fragment = new AllMatchesTabFragment();
        return fragment;
    }


    public AllMatchesTabFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }
        setHasOptionsMenu(true);
    }



    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_all_matches_tab, container, false);


        close_btn_new =  view.findViewById(R.id.close_btn_new);
        upgrade_new_btn =  view.findViewById(R.id.upgrade_new_btn);
        close_btn =  view.findViewById(R.id.close_btn);
        upgrade_now_btn =  view.findViewById(R.id.upgrade_now_btn);
        just_joind_cv =  view.findViewById(R.id.just_joind_cv);
        new_upgrade_cv =  view.findViewById(R.id.new_upgrade_cv);
        count_new =  view.findViewById(R.id.count_new);
        new_txtLL =  view.findViewById(R.id.new_txtLL);
        emptyLL =  view.findViewById(R.id.emptyLL);
        swip_topLL = view.findViewById(R.id.swip_topLL);

        progress_bar_main =  view.findViewById(R.id.progress_bar_main);
        rv =  view.findViewById(R.id.rv);

        nestedScroll =  view.findViewById(R.id.nestedScroll);


        swipeRefreshLayout =  view.findViewById(R.id.swipeRefreshLayout);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);



        user = SharedPrefManager.getInstance(getActivity()).getUser();

        /*--------*/
        newMatchesAdapterScroll = new TabNewMatchesAdapter(getActivity());
        rv.setItemAnimator(null);
        rv.setAdapter(newMatchesAdapterScroll);
        rv.setFocusable(false);
        /*--------*/


        fetchLoginUserData();

        loadFirstPage();


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
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


//        fetchLoginUserData();
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onRefresh() {

                fetchLoginUserData();

                currentPage = PAGE_START;
                isLastPage = false;
                newMatchesAdapterScroll.getTabNewMathcesListData().clear();
                if(newmatchesListData!=null)
                    newmatchesListData.clear();
                newMatchesAdapterScroll.notifyDataSetChanged();
                loadFirstPage();
//                swipeRefreshLayout.setRefreshing(false);

            }
        });



    }

    public  void fetchLoginUserData(){


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

                if(fetchProfileLoginUser.getPremium()!=null) {
                    if (fetchProfileLoginUser.getPremium().equals("1")) {
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

                    } else {
//                        just_joind_cv.setVisibility(View.VISIBLE);

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


            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());


            }
        });

    }


    public void loadFirstPage(){

        newMatchesAdapterScroll.getTabNewMathcesListData().clear();
        progress_bar_main.setVisibility(View.VISIBLE);

        if (newmatchesListData!=null )
            newmatchesListData.clear();

        new_txtLL.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        emptyLL.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<NewmatchesTabList> userResponse = apiService.MatchesTabAllList("3",String.valueOf(user.getUser_id()),"10","1");
        userResponse.enqueue(new Callback<NewmatchesTabList>() {

            @Override
            public void onResponse(Call<NewmatchesTabList> call, Response<NewmatchesTabList> response) {

                swipeRefreshLayout.setRefreshing(false);

                newMatchesList = response.body();
                progress_bar_main.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);


                System.out.println("newmatchesTabLis-------------");
                System.out.println("gender------------"+ gender);


                if (response.isSuccessful()) {
                    if(newMatchesList.getResid().equals("200")) {

                        new_txtLL.setVisibility(View.VISIBLE);
                        count_new.setText("("+ newMatchesList.getCount()+")");


                        emptyLL.setVisibility(View.GONE);

//                        System.out.println("resp 123123.......>>>" + new Gson().toJson(response.body()));

                        TOTAL_PAGES = Integer.parseInt(newMatchesList.getTotal_pages());
                        newmatchesListData = newMatchesList.getNewmatchesList();


                        System.out.println("TOTAL_PAGES-------------" + TOTAL_PAGES);


                        if (newmatchesListData != null) {

                            if(!newmatchesListData.isEmpty()) {
                                newMatchesAdapterScroll.addAll(newmatchesListData);
                                if(newmatchesListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) newMatchesAdapterScroll.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }

                    }else {

                        emptyLL.setVisibility(View.VISIBLE);
                        new_txtLL.setVisibility(View.GONE);
                        rv.setVisibility(View.GONE);

                    }

                }

            }

            @Override
            public void onFailure(Call<NewmatchesTabList> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error NewmatchesTabList******" + t.toString());

            }
        });
    }


    public void loadNextPage(int pos){

        emptyLL.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<NewmatchesTabList> userResponse = apiService.MatchesTabAllList("3",String.valueOf(user.getUser_id()),"10", String.valueOf(pos));
        userResponse.enqueue(new Callback<NewmatchesTabList>() {

            @Override
            public void onResponse(Call<NewmatchesTabList> call, Response<NewmatchesTabList> response) {
                newMatchesList = response.body();


                System.out.println("newmatchesTabLis-------------");
                System.out.println("gender------------"+ gender);
                if (response.isSuccessful()) {
                    if(newMatchesList.getResid().equals("200")) {


//                        System.out.println("resp 123123.......>>>" + new Gson().toJson(response.body()));
                        System.out.println("resp 123123.......>>>" + pos);

                        newMatchesAdapterScroll.removeLoadingFooter();
                        isLoading = false;

                        newmatchesListData = newMatchesList.getNewmatchesList();
                        if (newmatchesListData != null) {



                            if(!newmatchesListData.isEmpty()) {

                                newMatchesAdapterScroll.addAll(newmatchesListData);

                                if(newmatchesListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage != TOTAL_PAGES) newMatchesAdapterScroll.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }

                    }else {

                    }

                }

            }

            @Override
            public void onFailure(Call<NewmatchesTabList> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);

                System.out.println("msg1 error NewmatchesTabList******" + t.toString());

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


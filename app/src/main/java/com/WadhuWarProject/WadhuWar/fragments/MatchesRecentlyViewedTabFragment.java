package com.WadhuWarProject.WadhuWar.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.RecentlyViewedActivity;
import com.WadhuWarProject.WadhuWar.adapter.TabRecentlyviewedAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.TabRecentlyViewedList;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchesRecentlyViewedTabFragment extends Fragment implements  NetworkStateReceiver.NetworkStateReceiverListener{


    static boolean isNetworkAvailable;
    UserData user;
    FetchProfile fetchProfile;
    ProgressDialog progressBar;

    TextView see_all_recently;
    TabRecentlyviewedAdapter recentlyviewedAdapter;

    SwipeRefreshLayout swipeRefreshLayout;
    private NetworkStateReceiver networkStateReceiver;

    String gender;
    RelativeLayout progress_bar_recently;


    ArrayList<TabRecentlyViewedList.TabRecentlyViewedListData> tabRecentlyViewedListData = new ArrayList<>();
    TabRecentlyViewedList tabRecentlyViewedList;

    RecyclerView recentlyviewed_rv;
    LinearLayout recentlyviewedLL,emptyLL;
    TextView count_view;

    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;

    public static MatchesRecentlyViewedTabFragment newInstance() {
        MatchesRecentlyViewedTabFragment fragment = new MatchesRecentlyViewedTabFragment();
        return fragment;
    }


    public MatchesRecentlyViewedTabFragment() {

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
        final View view =inflater.inflate(R.layout.fragment_matches_recently_viewed_tab, container, false);


        count_view =  view.findViewById(R.id.count_view);
        emptyLL =  view.findViewById(R.id.emptyLL);

        recentlyviewed_rv =  view.findViewById(R.id.recentlyviewed_rv);
        progress_bar_recently =  view.findViewById(R.id.progress_bar_recently);
        see_all_recently =  view.findViewById(R.id.see_all_recently);
        recentlyviewedLL =  view.findViewById(R.id.recentlyviewedLL);
        nestedScroll =  view.findViewById(R.id.nestedScroll);


        swipeRefreshLayout =  view.findViewById(R.id.swipeRefreshLayout);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recentlyviewed_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        user = SharedPrefManager.getInstance(getActivity()).getUser();



        see_all_recently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), RecentlyViewedActivity.class);
                startActivity(i);
            }
        });



        recentlyviewed(4);


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/
        return view;

    }





    @Override
    public void networkAvailable() {
//        recentlyviewed(3);



        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recentlyviewed(4);

//                swipeRefreshLayout.setRefreshing(false);

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




    public void recentlyviewed(int pos){


        progress_bar_recently.setVisibility(View.VISIBLE);


        if (tabRecentlyViewedListData!=null )
            tabRecentlyViewedListData.clear();


        recentlyviewedLL.setVisibility(View.GONE);
        emptyLL.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<TabRecentlyViewedList> userResponse = apiService.matchesTabRecent(String.valueOf(pos),String.valueOf(user.getUser_id()),"5","1");
        userResponse.enqueue(new Callback<TabRecentlyViewedList>() {

            @Override
            public void onResponse(Call<TabRecentlyViewedList> call, Response<TabRecentlyViewedList> response) {

                swipeRefreshLayout.setRefreshing(false);
                tabRecentlyViewedList = response.body();
                progress_bar_recently.setVisibility(View.GONE);
                recentlyviewedLL.setVisibility(View.VISIBLE);
                System.out.println("recentlyviewed-------------");
                System.out.println("gender------------"+ gender);

                if (response.isSuccessful()) {

                    if(tabRecentlyViewedList.getResid().equals("200")){
                        emptyLL.setVisibility(View.GONE);

                        count_view.setText("("+tabRecentlyViewedList.getCount()+")");

//                    System.out.println("resp 33333 recentlyviewed.......>>>" + new Gson().toJson(response.body()));
                        System.out.println("resp 333333 recentlyviewed.......>>>" + pos);

                        tabRecentlyViewedListData = tabRecentlyViewedList.getTabRecentlyViewedList();
                        if(tabRecentlyViewedListData!=null) {

                            for (int i = 0; i < tabRecentlyViewedListData.size(); i++) {
//                            System.out.println("success story list .......>>>" + blogListData.get(i).getName());
                            }


                            recentlyviewedAdapter = new TabRecentlyviewedAdapter(getActivity(), tabRecentlyViewedListData);

                            recentlyviewed_rv.setHasFixedSize(true);

                            recentlyviewed_rv.setAdapter(recentlyviewedAdapter);
                            recentlyviewedAdapter.notifyDataSetChanged();

                        }

                    }else {

                        emptyLL.setVisibility(View.VISIBLE);

                    }


                }

            }

            @Override
            public void onFailure(Call<TabRecentlyViewedList> call, Throwable t) {
                progress_bar_recently.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error TabRecentlyViewedList******" + t.toString());

            }
        });
    }


}


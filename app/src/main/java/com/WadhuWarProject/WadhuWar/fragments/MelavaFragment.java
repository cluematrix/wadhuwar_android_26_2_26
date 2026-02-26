package com.WadhuWarProject.WadhuWar.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.MelavaDetailActivity;
import com.WadhuWarProject.WadhuWar.activity.ViewAllNewsActivity;
import com.WadhuWarProject.WadhuWar.adapter.MelavaAdapter;
import com.WadhuWarProject.WadhuWar.adapter.ViewAllNewsAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.MelawaList;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MelavaFragment extends Fragment implements  NetworkStateReceiver.NetworkStateReceiverListener{
    static boolean isNetworkAvailable;
    Toolbar toolbar;

    RecyclerView melava_rv;
    RelativeLayout progress_bar;

    private NetworkStateReceiver networkStateReceiver;

    MelavaAdapter melavaAdapter;
    ArrayList<MelawaList.MelawaListData> melawaListData = new ArrayList<>();
    MelawaList melawaList;
    UserData user;


    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout empty_melavaLL;

    public MelavaFragment() {

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
        final View view =inflater.inflate(R.layout.fragment_melava, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        melava_rv = view.findViewById(R.id.melava_rv);
        progress_bar = view.findViewById(R.id.progress_bar);
        empty_melavaLL = view.findViewById(R.id.empty_melavaLL);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        toolbar.setTitle("Melava");

        user = SharedPrefManager.getInstance(getActivity()).getUser();

        getMelavaData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

        return view;

    }


    @Override
    public void networkAvailable() {

//        getMelavaData();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMelavaData();

//                swipeRefreshLayout.setRefreshing(false);
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


    public  void  getMelavaData(){

        System.out.println("333333333333333333");

        if (melawaListData!=null )
            melawaListData.clear();

        progress_bar.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<MelawaList> userResponse = apiService.getMelawaList(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<MelawaList>() {

            @Override
            public void onResponse(Call<MelawaList> call, Response<MelawaList> response) {

                swipeRefreshLayout.setRefreshing(false);
                melawaList = response.body();

                progress_bar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                   /* System.out.println("resp melava.......>>>" + new Gson().toJson(response.body()));*/

                    if(melawaList.getResid().equals("200")) {
                        melava_rv.setVisibility(View.VISIBLE);

                        empty_melavaLL.setVisibility(View.GONE);

                        melawaListData = melawaList.getMelawaList();
                        if (melawaListData != null) {

                            melavaAdapter = new MelavaAdapter(getActivity(), melawaListData);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            melava_rv.setLayoutManager(linearLayoutManager);

                            melava_rv.setAdapter(melavaAdapter);
                            melavaAdapter.notifyDataSetChanged();

                        }
                    }else{
                        empty_melavaLL.setVisibility(View.VISIBLE);

                    }

                }

            }

            @Override
            public void onFailure(Call<MelawaList> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error melava******" + t.toString());

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



}


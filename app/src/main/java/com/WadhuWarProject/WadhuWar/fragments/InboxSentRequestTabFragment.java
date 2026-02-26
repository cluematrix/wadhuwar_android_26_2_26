package com.WadhuWarProject.WadhuWar.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.InboxSendAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.MemberList;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InboxSentRequestTabFragment extends Fragment  implements  NetworkStateReceiver.NetworkStateReceiverListener{


    static boolean isNetworkAvailable;
    UserData user;
    InboxSendAdapter inboxSendAdapter ;


    SwipeRefreshLayout swipeRefreshLayout;
    private NetworkStateReceiver networkStateReceiver;

    String gender;
    RelativeLayout progress_bar_main;


    ArrayList<MemberList.MemberListData> sendMemberListData;
    MemberList sendMemberList;

    FetchProfile fetchProfileLoginUser;

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


    TextView count_txt;


    public static InboxSentRequestTabFragment newInstance() {
        InboxSentRequestTabFragment fragment = new InboxSentRequestTabFragment();
        return fragment;
    }


    public InboxSentRequestTabFragment() {

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

        final View view =inflater.inflate(R.layout.fragment_inbox_sent_request_tab, container, false);


        count_txt =  view.findViewById(R.id.count_txt);
        emptyLL =  view.findViewById(R.id.emptyLL);
        swip_topLL = view.findViewById(R.id.swip_topLL);

        progress_bar_main =  view.findViewById(R.id.progress_bar_main);
        rv =  view.findViewById(R.id.rv);

        nestedScroll =  view.findViewById(R.id.nestedScroll);


        swipeRefreshLayout =  view.findViewById(R.id.swipeRefreshLayout);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);



        user = SharedPrefManager.getInstance(getActivity()).getUser();

        fetchLoginUSerData();

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



        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
      /*  inboxSendAdapter = new InboxSendAdapter(getActivity(),String.valueOf(user.getUser_id()));
        rv.setItemAnimator(null);
        rv.setAdapter(inboxSendAdapter);
        rv.setFocusable(false);*/

        return view;

    }


    protected void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        System.out.println("currentPage 11>>>>>>>>>>>" +  currentPage);
        inboxSendtabListNext(currentPage);
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



//        inboxAccepttabList();
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                currentPage = PAGE_START;
                isLastPage = false;
                inboxSendAdapter.getMemberListData().clear();
                if(sendMemberListData!=null)
                    sendMemberListData.clear();
                inboxSendAdapter.notifyDataSetChanged();
                fetchLoginUSerData();

//                swipeRefreshLayout.setRefreshing(false);

            }
        });


    }


    public void inboxSendtabList(){

        inboxSendAdapter.getMemberListData().clear();

        if(sendMemberListData!=null)
            sendMemberListData.clear();

        progress_bar_main.setVisibility(View.VISIBLE);

        rv.setVisibility(View.GONE);
        emptyLL.setVisibility(View.GONE);

        Api apiService = RetrofitClient.getApiService();
        Call<MemberList> userResponse = apiService.sendConnection("3",String.valueOf(user.getUser_id()),"10","1");
        userResponse.enqueue(new Callback<MemberList>() {

            @Override
            public void onResponse(Call<MemberList> call, Response<MemberList> response) {
                sendMemberList = response.body();
                progress_bar_main.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);

                System.out.println("MemberList-------------");

                if (response.isSuccessful()) {
                    if(sendMemberList.getResid().equals("200")) {
                        emptyLL.setVisibility(View.GONE);


                        count_txt.setVisibility(View.VISIBLE);
                        count_txt.setText("All Invitations ("+sendMemberList.getCount()+")");


//                        System.out.println("resp 123123.......>>>" + new Gson().toJson(response.body()));

                        TOTAL_PAGES = Integer.parseInt(sendMemberList.getTotal_pages());

                        sendMemberListData = sendMemberList.getMemberList();
                        if (sendMemberListData != null) {


                            if(!sendMemberListData.isEmpty()) {


                                System.out.println("1111 check=============>>" +  sendMemberListData.size());



                                inboxSendAdapter.addAll(sendMemberListData);
                                System.out.println("2222 check=============>>" +  sendMemberListData.size());

//                                inboxSendAdapter.addAll(sendMemberListData);
                                if(sendMemberListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage <= TOTAL_PAGES) inboxSendAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }

                        }

                    }else {
                       /* Toast toast= Toast.makeText(getActivity(), sendMemberList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();*/
                        emptyLL.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.GONE);

                    }

                }

            }

            @Override
            public void onFailure(Call<MemberList> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);

                System.out.println("msg1 error NewmatchesTabList******" + t.toString());

            }
        });
    }
    public ArrayList<MemberList.MemberListData>  removeDuplicates(ArrayList<MemberList.MemberListData> list){
        Set<MemberList.MemberListData> set = new TreeSet(new Comparator<MemberList.MemberListData>() {

            @Override
            public int compare(MemberList.MemberListData o1, MemberList.MemberListData o2) {
                if(o1.getId().equalsIgnoreCase(o2.getId())){
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(list);

        final ArrayList newList = new ArrayList(set);
        return newList;
    }


    public void inboxSendtabListNext(int c){


        Api apiService = RetrofitClient.getApiService();
        Call<MemberList> userResponse = apiService.sendConnection("3",String.valueOf(user.getUser_id()),"10",String.valueOf(c));
        userResponse.enqueue(new Callback<MemberList>() {

            @Override
            public void onResponse(Call<MemberList> call, Response<MemberList> response) {
                sendMemberList = response.body();
                progress_bar_main.setVisibility(View.GONE);

                System.out.println("MemberList-------------");

                if (response.isSuccessful()) {
                    if(sendMemberList.getResid().equals("200")) {
                        emptyLL.setVisibility(View.GONE);


                        inboxSendAdapter.removeLoadingFooter();
                        isLoading = false;

                        TOTAL_PAGES = Integer.parseInt(sendMemberList.getTotal_pages());

                        sendMemberListData = sendMemberList.getMemberList();
                        if (sendMemberListData != null) {


                            if(!sendMemberListData.isEmpty()) {

                                inboxSendAdapter.addAll(sendMemberListData);

                                if(sendMemberListData.size()<10){
                                    isLastPage = true;
                                    isLoading= false;
                                }else{
                                    if (currentPage != TOTAL_PAGES) inboxSendAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }

                        }

                    }else {
                       /* Toast toast= Toast.makeText(getActivity(), sendMemberList.getResMsg(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();*/

                    }

                }

            }

            @Override
            public void onFailure(Call<MemberList> call, Throwable t) {
                progress_bar_main.setVisibility(View.GONE);

                System.out.println("msg1 error NewmatchesTabList******" + t.toString());

            }
        });
    }


    public  void fetchLoginUSerData(){


        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {

                swipeRefreshLayout.setRefreshing(false);
                fetchProfileLoginUser = response.body();


                inboxSendAdapter = new InboxSendAdapter(getActivity(),String.valueOf(user.getUser_id()),fetchProfileLoginUser.getPremium());
                rv.setItemAnimator(null);
                rv.setAdapter(inboxSendAdapter);
                rv.setFocusable(false);


                inboxSendtabList();



            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
                if(!isNetworkAvailable(getActivity())){
//                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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



package com.WadhuWarProject.WadhuWar.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;

import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.HeightWrappingViewPager;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.ShowTabList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InboxFragment extends Fragment implements  NetworkStateReceiver.NetworkStateReceiverListener{
    private static InboxFragment instance = null;
    TabLayout tabLayout ;
    HeightWrappingViewPager mViewPager;
    InboxFragment.PagerAdapter pagerAdapter;
    private NetworkStateReceiver networkStateReceiver;

    UserData user;
    FetchProfile fetchProfileLoginUser;

    ArrayList<ShowTabList.ShowTabListData> showTabListData ;
    ShowTabList showTabList;

    public static InboxFragment getInstance() {
        return instance;
    }


    public InboxFragment() {

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
        final View view =inflater.inflate(R.layout.fragment_inbox, container, false);



        mViewPager =  (HeightWrappingViewPager)view.findViewById(R.id.pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        user = SharedPrefManager.getInstance(getActivity()).getUser();
        showTabListData = new ArrayList<>();

        mViewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        fetchLoginUSerData();

        if (mViewPager != null) {
            getShowTabData(String.valueOf(user.getUser_id()));

        }

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

        return view;

    }


    @Override
    public void networkAvailable() {

//        fetchLoginUSerData();
//
//        if (mViewPager != null) {
//            getShowTabData(String.valueOf(user.getUser_id()));
//
//        }
    }

    public  void fetchLoginUSerData(){


        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfileLoginUser = response.body();


            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());


            }
        });

    }

    public void getShowTabData(String login_userid){


        if (showTabListData!=null )
            showTabListData.clear();

//        progress_bar_tab.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<ShowTabList> userResponse = apiService.showConnectTabList(login_userid);
        userResponse.enqueue(new Callback<ShowTabList>() {

            @Override
            public void onResponse(Call<ShowTabList> call, Response<ShowTabList> response) {
                showTabList = response.body();
//                progress_bar_tab.setVisibility(View.GONE);


                if (response.isSuccessful()) {

                    if(showTabList!=null) {



                        for (int i = 0; i < showTabList.getShowTabList().size(); i++) {
                            showTabListData.add( new ShowTabList.ShowTabListData(
                                    showTabList.getShowTabList().get(i).getName(),showTabList.getShowTabList().get(i).getId()));

                        }

                        initViewPagerAndTabs(showTabListData);

                        TabLayout.Tab tab = tabLayout.getTabAt(0); // Count Starts From 0
                        tab.select();


                        if((pagerAdapter.getItem(mViewPager.getCurrentItem())!=null)) {
                            Fragment currentFragment = pagerAdapter.getItem(mViewPager.getCurrentItem());
                            System.out.println("currentFragment>>>>>>>>>>" + currentFragment);
                        }

                    }

                    for (int i = 0; i < showTabListData.size(); i++) {
                        System.out.println("showTabListData .......>>>" + showTabListData.get(i).getName());

                    }



                }

            }

            @Override
            public void onFailure(Call<ShowTabList> call, Throwable t) {
//                progress_bar_tab.setVisibility(View.GONE);

                System.out.println("msg1 error ShowTabList******" + t.toString());

            }
        });
    }


    @Override
    public void networkUnavailable() {

    }


    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }


    private void initViewPagerAndTabs(ArrayList<ShowTabList.ShowTabListData> showTabListData) {

        String chat_message = "Chat";
        String receive = showTabListData.get(0).getName();
        String connected = showTabListData.get(1).getName();
        String sendreq = showTabListData.get(2).getName();

        pagerAdapter = new InboxFragment.PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new InboxChatMessageFragment().newInstance(), chat_message);
        pagerAdapter.addFragment(new InboxReceivedRequestTabFragment().newInstance(), receive);
        pagerAdapter.addFragment(new InboxConnectedTabFragment().newInstance(), connected);
        pagerAdapter.addFragment(new InboxSentRequestTabFragment().newInstance(), sendreq);

        mViewPager.setAdapter(pagerAdapter);




        Fragment currentFragment = pagerAdapter.getItem(mViewPager.getCurrentItem());
        System.out.println("currentFragment 11>>>>>>>>>>" + currentFragment);

        tabLayout.setupWithViewPager(mViewPager);



        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, tabLayout, false);
            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            TextView img_Search = (TextView) relativeLayout.findViewById(R.id.img_Search);
            CardView cv = (CardView) relativeLayout.findViewById(R.id.cv);
            cv.setVisibility(View.VISIBLE);
            tabTextView.setText(tab.getText());

            img_Search.setVisibility(View.GONE);
            tab.setCustomView(relativeLayout);
            tab.select();
        }
    }

    static class PagerAdapter extends FragmentPagerAdapter {
        private String mSearchTerm;

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }



        @Override
        public int getItemPosition(Object object) {


            return POSITION_NONE;
        }


        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

    }




    public void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        getActivity().unregisterReceiver(networkStateReceiver);
    }




}


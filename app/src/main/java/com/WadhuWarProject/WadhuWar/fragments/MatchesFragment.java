package com.WadhuWarProject.WadhuWar.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.EBookActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.HeightWrappingViewPager;
import com.WadhuWarProject.WadhuWar.model.MatchesTabList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchesFragment extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {
    private static MatchesFragment instance = null;
    SwipeRefreshLayout refreshLayout;
    TabLayout tabLayout;
    HeightWrappingViewPager mViewPager;
    ProgressDialog progressBar;
    LinearLayout ebook_layout;

    MatchesNewTabFragment connectionTabFragment;
    MatchesFragment.PagerAdapter pagerAdapter;
    private NetworkStateReceiver networkStateReceiver;

    UserData user;

    ArrayList<MatchesTabList.MatchesTabListData> matchesTabListData;
    MatchesTabList matchesTabList;

    public static MatchesFragment getInstance() {
        return instance;
    }


    public MatchesFragment() {

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

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_matches, container, false);

        ebook_layout = view.findViewById(R.id.ebook_layout);
        mViewPager = (HeightWrappingViewPager) view.findViewById(R.id.pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        user = SharedPrefManager.getInstance(getActivity()).getUser();
        matchesTabListData = new ArrayList<>();

        mViewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
       /* if (mViewPager != null) {
//            initViewPagerAndTabs();
            getMatchesTabData(String.valueOf(user.getUser_id()));

        }*/


        ebook_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EBookActivity.class);
                startActivity(i);
            }
        });


        if (mViewPager != null) {
            getMatchesTabData(String.valueOf(user.getUser_id()));

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
        /*if (mViewPager != null) {
            getMatchesTabData(String.valueOf(user.getUser_id()));

        }*/
    }

    @Override
    public void networkUnavailable() {


    }


    public void getMatchesTabData(String login_userid) {


        if (matchesTabListData != null)
            matchesTabListData.clear();

//        progress_bar_tab.setVisibility(View.VISIBLE);

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait...");


        Api apiService = RetrofitClient.getApiService();
        Call<MatchesTabList> userResponse = apiService.matchesTabList(login_userid);
        userResponse.enqueue(new Callback<MatchesTabList>() {

            @Override
            public void onResponse(Call<MatchesTabList> call, Response<MatchesTabList> response) {
                matchesTabList = response.body();
//                progress_bar_tab.setVisibility(View.GONE);


                progressBar.dismiss();
                if (response.isSuccessful()) {

                    if (matchesTabList != null) {


                        for (int i = 0; i < matchesTabList.getMatchesTabList().size(); i++) {
                            matchesTabListData.add(new MatchesTabList.MatchesTabListData(
                                    matchesTabList.getMatchesTabList().get(i).getName(), matchesTabList.getMatchesTabList().get(i).getId()));

                        }

                        System.out.println("matchesTabListData==============" + matchesTabListData.size());
                        initViewPagerAndTabs(matchesTabListData);

                        TabLayout.Tab tab = tabLayout.getTabAt(2); // Count Starts From 0
                        tab.select();

                        if ((pagerAdapter.getItem(mViewPager.getCurrentItem()) != null)) {
                            Fragment currentFragment = pagerAdapter.getItem(mViewPager.getCurrentItem());
                            System.out.println("currentFragment>>>>>>>>>>" + currentFragment);
                        }

//                        matchesTopAdapter.notifyDataSetChanged();

                    }

                    for (int i = 0; i < matchesTabListData.size(); i++) {
                        System.out.println("success story list .......>>>" + matchesTabListData.get(i).getName());

                    }

                }

            }

            @Override
            public void onFailure(Call<MatchesTabList> call, Throwable t) {
                System.out.println("msg1 error MatchesTabList******" + t.toString());
                progressBar.dismiss();
            }
        });
    }


    private void initViewPagerAndTabs(ArrayList<MatchesTabList.MatchesTabListData> matchesTabListData) {

        String newn = matchesTabListData.get(0).getName();
        String mymatches = matchesTabListData.get(1).getName();
        String allmatches = matchesTabListData.get(2).getName();
        String recentlyviewed = matchesTabListData.get(3).getName();
        String nearme = matchesTabListData.get(4).getName();
        String more_matches = matchesTabListData.get(5).getName();

        pagerAdapter = new MatchesFragment.PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new MatchesTabSearchFragment().newInstance(), "Search");  // todo :: on 16 may 2026 by sagar
//        pagerAdapter.addFragment(new MatchesTabSearchFragmentNew().newInstance(), "Search");
        pagerAdapter.addFragment(new MatchesNewTabFragment().newInstance(), newn);
        pagerAdapter.addFragment(new MatchesMyMatchesTabFragment().newInstance(), mymatches);
        pagerAdapter.addFragment(new AllMatchesTabFragment().newInstance(), allmatches);
        pagerAdapter.addFragment(new MatchesRecentlyViewedTabFragment().newInstance(), recentlyviewed);
        pagerAdapter.addFragment(new MatchesNearMeTabFragment().newInstance(), nearme);
        pagerAdapter.addFragment(new MatchesStateNearMeTabFragment().newInstance(), more_matches);



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

            if (tab.getText().equals("Search")) {
                img_Search.setVisibility(View.VISIBLE);
                cv.setVisibility(View.GONE);
            } else {
                img_Search.setVisibility(View.GONE);
                cv.setVisibility(View.VISIBLE);
                tabTextView.setText(tab.getText());
            }

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


}



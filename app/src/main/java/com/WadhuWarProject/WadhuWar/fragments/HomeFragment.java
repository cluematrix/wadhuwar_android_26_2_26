package com.WadhuWarProject.WadhuWar.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.ViewAllAdvertisementActivity;
import com.WadhuWarProject.WadhuWar.activity.ViewAllNewsActivity;
import com.WadhuWarProject.WadhuWar.activity.ViewAllSuccessStoryActivity;
import com.WadhuWarProject.WadhuWar.adapter.AdvertiseAdapter;
import com.WadhuWarProject.WadhuWar.adapter.BannerAdapter;
import com.WadhuWarProject.WadhuWar.adapter.HomeProfilesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.NewsAdapter;
import com.WadhuWarProject.WadhuWar.adapter.SuccessStoryAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.AdvertiseList;
import com.WadhuWarProject.WadhuWar.model.BlogList;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.WadhuWarProject.WadhuWar.model.ProfileSlider;
import com.WadhuWarProject.WadhuWar.model.SliderList;
import com.WadhuWarProject.WadhuWar.model.SliderListImage;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HomeFragment extends Fragment implements  NetworkStateReceiver.NetworkStateReceiverListener{
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    HomeProfilesAdapter homeProfilesAdapter;
    RecyclerView success_story_rv,news_rv,advertisement_rv,profiles_rv;

    RelativeLayout progress_bar_banner,progress_bar_profiles,progress_bar_banner_succes_story,progress_bar_news,progress_bar_advertisement;
    List<View> banners=new ArrayList<>();
    List<View> bannersProfiles =new ArrayList<>();
    //    ViewPager2 bannerSlider,banner_slider_profiles;
    List<SliderListImage> sliderListImages = new ArrayList<>();
    ArrayList<ProfileSlider.ProfileSliderData> sliderListImagesProfilesList = new ArrayList<>();
    ProfileSlider.ProfileSliderData profileSliderData;
    ProfileSlider profileSlider;
    SliderList sliderList;
    SuccessStoryAdapter successStoryAdapter;
    NewsAdapter newsAdapter;
    AdvertiseAdapter advertiseAdapter;
    TextView view_all_succes_story,view_all_news,view_all_advertise;

    ArrayList<BlogList.BlogListData> blogListData = new ArrayList<>();
    BlogList blogList;

    ArrayList<NewsList.NewsListData> newsListData = new ArrayList<>();
    NewsList newsList;

    ArrayList<AdvertiseList.AdvertiseListData> advertiseListData = new ArrayList<>();
    AdvertiseList advertiseList;
    SwipeRefreshLayout swipeRefreshLayout;

    private ViewPager2 bannerSlider;
    private TabLayout dotIndicator;
    private Handler autoSlideHandler;
    private Runnable autoSlideRunnable;

    CardView success_story_cv,new_cv,advertise_cv;

    UserData user;
    ImageView ic_call, ic_web;


    public HomeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }

//        /*if net off*/
//        networkStateReceiver = new NetworkStateReceiver();
//        networkStateReceiver.addListener(this);
//        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        /*end code if net off*/

    }


    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_home, container, false);

        advertise_cv =  view.findViewById(R.id.advertise_cv);
        ic_call =  view.findViewById(R.id.ivCall);
        ic_web =  view.findViewById(R.id.ivWebsite);


        new_cv =  view.findViewById(R.id.new_cv);
        success_story_cv =  view.findViewById(R.id.success_story_cv);
        bannerSlider =  view.findViewById(R.id.banner_slider1);
        progress_bar_banner =  view.findViewById(R.id.progress_bar_banner);
        progress_bar_profiles =  view.findViewById(R.id.progress_bar_profiles);
        progress_bar_banner_succes_story =  view.findViewById(R.id.progress_bar_banner_succes_story);
        progress_bar_advertisement =  view.findViewById(R.id.progress_bar_advertisement);
        progress_bar_news =  view.findViewById(R.id.progress_bar_news);
        success_story_rv =  view.findViewById(R.id.success_story_rv);
        advertisement_rv =  view.findViewById(R.id.advertisement_rv);
        news_rv =  view.findViewById(R.id.news_rv);
        view_all_succes_story =  view.findViewById(R.id.view_all_succes_story);
        view_all_news =  view.findViewById(R.id.view_all_news);
        view_all_advertise =  view.findViewById(R.id.view_all_advertise);
        profiles_rv =  view.findViewById(R.id.profiles_rv);

        profiles_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        success_story_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        news_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        advertisement_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        bannerSlider = view.findViewById(R.id.banner_slider1);
        dotIndicator = view.findViewById(R.id.dotIndicator);

        user = SharedPrefManager.getInstance(getActivity()).getUser();

        System.out.println("user_id=====11" + String.valueOf(user.getUser_id()));

        loadDataBanner();
        loadProfileBannerData();
        getBlogListData();
        getNewsListData();
        getAdvertisementData();


        ic_call.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:9420944984")); // wadhuwar number
//            intent.setData(Uri.parse("tel:9404279040"));     // rts
            startActivity(intent);
        });
//String basepath=RetrofitClient.getApiService().toString();
//        Toast.makeText(requireContext(), "base" + basepath, Toast.LENGTH_SHORT).show();
        ic_web.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(basepath+"/UserAdvertisement/logout"));
            String cleanBaseUrl = RetrofitClient.basePath.replace("Api_con/", "");
            String logoutUrl = cleanBaseUrl + "UserAdvertisement/logout";
            intent.setData(Uri.parse(logoutUrl));

//            intent.setData(Uri.parse("https://buddishtwadhuwar.com/bdtest/UserAdvertisement/logout"));
            startActivity(intent);
        });


        view_all_succes_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ViewAllSuccessStoryActivity.class);
                startActivity(i);
            }
        });

        view_all_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ViewAllNewsActivity.class);
                startActivity(i);
            }
        });

        view_all_advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ViewAllAdvertisementActivity.class);
                startActivity(i);
            }
        });

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


        return view;

    }

    @Override
    public void networkAvailable() {
//        loadDataBanner();
//        loadProfileBannerData();
//        getBlogListData();
//        getNewsListData();
//        getAdvertisementData();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadProfileBannerData();
                loadDataBanner();
                getBlogListData();
                getNewsListData();
                getAdvertisementData();

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

    public  void  loadProfileBannerData(){

        System.out.println("4444444444");

        if (sliderListImagesProfilesList!=null )
            sliderListImagesProfilesList.clear();

        progress_bar_profiles.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<ProfileSlider> userResponse = apiService.profileSlider("18",String.valueOf(user.getUser_id()),"5","1");
        userResponse.enqueue(new Callback<ProfileSlider>() {

            @Override
            public void onResponse(Call<ProfileSlider> call, Response<ProfileSlider> response) {

                swipeRefreshLayout.setRefreshing(false);
                profileSlider = response.body();

                progress_bar_profiles.setVisibility(View.GONE);
                profiles_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    if(profileSlider.getResid().equals("200")) {

//                        System.out.println("profile slider data===========" + new Gson().toJson(profileSlider));
                        sliderListImagesProfilesList = profileSlider.getProfileSlider();
                        if (sliderListImagesProfilesList != null) {

                            homeProfilesAdapter = new HomeProfilesAdapter(getActivity(), sliderListImagesProfilesList);

                            profiles_rv.setHasFixedSize(true);
                            profiles_rv.setAdapter(homeProfilesAdapter);
                            homeProfilesAdapter.notifyDataSetChanged();

                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileSlider> call, Throwable t) {
                progress_bar_profiles.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error profile slider******" + t.toString());

            }
        });
    }


    public  void  getAdvertisementData(){

        System.out.println("333333333333333333");

        if (advertiseListData!=null )
            advertiseListData.clear();

        progress_bar_advertisement.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<AdvertiseList> userResponse = apiService.advertiseList("04","5","1",user.getUser_id()+"");
        userResponse.enqueue(new Callback<AdvertiseList>() {

            @Override
            public void onResponse(Call<AdvertiseList> call, Response<AdvertiseList> response) {
                advertiseList = response.body();

                progress_bar_advertisement.setVisibility(View.GONE);
                advertisement_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    Gson gson = new Gson();
                    String fullResponse = gson.toJson(response.body());

                    Log.d("API_FULL_RESPONSE", fullResponse);
                    if(advertiseList.getResid().equals("200")) {
                        advertise_cv.setVisibility(View.VISIBLE);
                        advertiseListData = advertiseList.getAdvertiseList();
                        if (advertiseListData != null) {

                            for (int i = 0; i < advertiseListData.size(); i++) {
//                            System.out.println("success story list .......>>>" + advertiseListData.get(i).getName());
                            }

                            advertiseAdapter = new AdvertiseAdapter(getActivity(), advertiseListData);

                            advertisement_rv.setHasFixedSize(true);
                            advertisement_rv.setAdapter(advertiseAdapter);
                            advertiseAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<AdvertiseList> call, Throwable t) {
                progress_bar_advertisement.setVisibility(View.GONE);

                System.out.println("msg1 error new******" + t.toString());
            }
        });
    }

    public  void  getNewsListData(){

        System.out.println("333333333333333333");

        if (newsListData!=null )
            newsListData.clear();

        progress_bar_news.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<NewsList> userResponse = apiService.newsList("03","5","1");
        userResponse.enqueue(new Callback<NewsList>() {

            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                newsList = response.body();

                progress_bar_news.setVisibility(View.GONE);
                news_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    if(newsList.getResid().equals("200")) {
                        new_cv.setVisibility(View.VISIBLE);

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));

                        newsListData = newsList.getNewsList();
                        if (newsListData != null) {

                            newsAdapter = new NewsAdapter(getActivity(), newsListData);
                            success_story_rv.setHasFixedSize(true);
                            news_rv.setAdapter(newsAdapter);
                            newsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                progress_bar_news.setVisibility(View.GONE);

                System.out.println("msg1 error new******" + t.toString());

            }
        });
    }

    public  void  getBlogListData(){

        System.out.println("222222222222222");

        if (blogListData!=null )
            blogListData.clear();

        progress_bar_banner_succes_story.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<BlogList> userResponse = apiService.blogList("02","5","1");
        userResponse.enqueue(new Callback<BlogList>() {

            @Override
            public void onResponse(Call<BlogList> call, Response<BlogList> response) {
                blogList = response.body();

                progress_bar_banner_succes_story.setVisibility(View.GONE);
                success_story_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));

                    if(blogList.getResid().equals("200")){
                        success_story_cv.setVisibility(View.VISIBLE);

                        blogListData = blogList.getBlogList();
                        if(blogListData!=null) {

                            successStoryAdapter = new SuccessStoryAdapter(getActivity(), blogListData);

                            success_story_rv.setHasFixedSize(true);
//                        success_story_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            success_story_rv.setAdapter(successStoryAdapter);
                            successStoryAdapter.notifyDataSetChanged();

                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<BlogList> call, Throwable t) {
                progress_bar_banner_succes_story.setVisibility(View.GONE);

                System.out.println("msg1 error success story******" + t.toString());

            }
        });
    }

    public  void  loadDataBanner(){

        System.out.println("111111111111111");

        if (sliderListImages!=null )
            sliderListImages.clear();

        if (banners!=null )
            banners.clear();


        progress_bar_banner.setVisibility(View.VISIBLE);
        Api apiService = RetrofitClient.getApiService();
        Call<SliderList> userResponse = apiService.sliderList("01");
        userResponse.enqueue(new Callback<SliderList>() {

            @Override
            public void onResponse(Call<SliderList> call, Response<SliderList> response) {
                sliderList = response.body();

                progress_bar_banner.setVisibility(View.GONE);
                bannerSlider.setVisibility(View.VISIBLE);


                progress_bar_banner.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    sliderList = response.body();
                    sliderListImages = sliderList.getSliderList();

                    // Filter to ensure only two images are displayed
                    if (sliderListImages.size() > 2) {
                        sliderListImages = sliderListImages.subList(0, 2); // Take the first two images
                    }

                    // Set up the ViewPager
                }

                if (response.isSuccessful()) {

//                    System.out.println("resp banner.......>>>" + new Gson().toJson(response.body()));

                    sliderListImages = sliderList.getSliderList();
                    if(sliderListImages!=null) {
//                        for (int i = 0; i < sliderListImages.size(); i++) {
//                            System.out.println("banner list .......>>>" + sliderListImages.get(i).getImgs());
//                        }

                        /*-------*/
                        for (int i = 0; i < sliderListImages.size(); i++)
                        {

//                            banners.add(new RemoteBanner(sliderListImages.get(i).getImgs()));
//                            bannerSlider.setBanners(banners);
//                            bannerSlider.setIndicatorSize(15);


                            // Dynamic Banner List
                            List<String> banners = new ArrayList<>();
                            banners.add("https://www.shutterstock.com/shutterstock/photos/1489086422/display_1500/stock-vector-colorful-abstract-banner-template-with-dummy-text-for-web-design-landing-page-and-print-material-1489086422.jpg");
                            banners.add("https://static.vecteezy.com/system/resources/previews/003/085/046/non_2x/colorful-abstract-banner-template-with-dummy-text-hand-drawn-doodle-vector.jpg");
                            banners.add("https://www.shutterstock.com/image-vector/colorful-abstract-banner-template-dummy-260nw-1538384741.jpg");

                            // Setting up the ViewPager with Adapter
                            BannerAdapter bannerAdapter = new BannerAdapter(requireContext(), sliderListImages);
//                    bannerViewPager.setAdapter(bannerAdapter);
                            bannerSlider.setAdapter(bannerAdapter);

                            // Connecting the dots indicator with TabLayoutMediator
                            new TabLayoutMediator(dotIndicator, bannerSlider, (tab, position) -> {
                                // Nothing to do here
                            }).attach();
                            // Auto-Sliding Logic
                            setupAutoSlide(bannerAdapter.getItemCount());

                        }
                    }
                    /*-------*/
                }
            }

            @Override
            public void onFailure(Call<SliderList> call, Throwable t) {
                progress_bar_banner.setVisibility(View.GONE);

                System.out.println("msg1 banner frag******" + t.toString());

            }
        });
    }

    private void setupAutoSlide(int itemCount) {
        autoSlideHandler = new Handler(Looper.getMainLooper());
        autoSlideRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = bannerSlider.getCurrentItem();
                int nextItem = (currentItem + 1) % itemCount; // Loop back to the first item
                bannerSlider.setCurrentItem(nextItem, true);
                autoSlideHandler.postDelayed(this, 3000); // 3-second delay
            }
        };
        autoSlideHandler.postDelayed(autoSlideRunnable, 3000);


        // Pausing auto-slide on user interaction
        bannerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                autoSlideHandler.removeCallbacks(autoSlideRunnable); // Pause auto-slide
                autoSlideHandler.postDelayed(autoSlideRunnable, 3000); // Resume after delay
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







//package com.WadhuWarProject.WadhuWar.fragments;
//
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.WadhuWarProject.WadhuWar.R;
//import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
//import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
//import com.WadhuWarProject.WadhuWar.activity.ViewAllAdvertisementActivity;
//import com.WadhuWarProject.WadhuWar.activity.ViewAllNewsActivity;
//import com.WadhuWarProject.WadhuWar.activity.ViewAllSuccessStoryActivity;
//import com.WadhuWarProject.WadhuWar.adapter.AdvertiseAdapter;
//import com.WadhuWarProject.WadhuWar.adapter.BannerAdapter;
//import com.WadhuWarProject.WadhuWar.adapter.HomeProfilesAdapter;
//import com.WadhuWarProject.WadhuWar.adapter.NewsAdapter;
//import com.WadhuWarProject.WadhuWar.adapter.SuccessStoryAdapter;
//import com.WadhuWarProject.WadhuWar.api.Api;
//import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
//import com.WadhuWarProject.WadhuWar.model.AdvertiseList;
//import com.WadhuWarProject.WadhuWar.model.BlogList;
//import com.WadhuWarProject.WadhuWar.model.NewsList;
//import com.WadhuWarProject.WadhuWar.model.ProfileSlider;
//import com.WadhuWarProject.WadhuWar.model.SliderList;
//import com.WadhuWarProject.WadhuWar.model.SliderListImage;
//import com.WadhuWarProject.WadhuWar.model.UserData;
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.tabs.TabLayoutMediator;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//public class HomeFragment extends Fragment implements  NetworkStateReceiver.NetworkStateReceiverListener{
//    static boolean isNetworkAvailable;
//    private NetworkStateReceiver networkStateReceiver;
//    HomeProfilesAdapter homeProfilesAdapter;
//    RecyclerView success_story_rv,news_rv,advertisement_rv,profiles_rv;
//
//    RelativeLayout progress_bar_banner,progress_bar_profiles,progress_bar_banner_succes_story,progress_bar_news,progress_bar_advertisement;
//    List<View> banners=new ArrayList<>();
//    List<View> bannersProfiles =new ArrayList<>();
////    ViewPager bannerSlider,banner_slider_profiles;
//    List<SliderListImage> sliderListImages = new ArrayList<>();
//    ArrayList<ProfileSlider.ProfileSliderData> sliderListImagesProfilesList = new ArrayList<>();
//    ProfileSlider.ProfileSliderData profileSliderData;
//    ProfileSlider profileSlider;
//    SliderList sliderList;
//    SuccessStoryAdapter successStoryAdapter;
//    NewsAdapter newsAdapter;
//    AdvertiseAdapter advertiseAdapter;
//    TextView view_all_succes_story,view_all_news,view_all_advertise;
//
//    ArrayList<BlogList.BlogListData> blogListData = new ArrayList<>();
//    BlogList blogList;
//
//    ArrayList<NewsList.NewsListData> newsListData = new ArrayList<>();
//    NewsList newsList;
//
//    ArrayList<AdvertiseList.AdvertiseListData> advertiseListData = new ArrayList<>();
//    AdvertiseList advertiseList;
//    SwipeRefreshLayout swipeRefreshLayout;
//
//
//
//    private ViewPager2 bannerSlider;
//    private TabLayout dotIndicator;
//    private Handler autoSlideHandler;
//    private Runnable autoSlideRunnable;
//
//    CardView success_story_cv,new_cv,advertise_cv;
//
//    UserData user;
//
//    public HomeFragment() {
//
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//
//
////        /*if net off*/
////        networkStateReceiver = new NetworkStateReceiver();
////        networkStateReceiver.addListener(this);
////        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
////        /*end code if net off*/
//
//    }
//
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        final View view =inflater.inflate(R.layout.fragment_home, container, false);
//
//        advertise_cv =  view.findViewById(R.id.advertise_cv);
//        new_cv =  view.findViewById(R.id.new_cv);
//        success_story_cv =  view.findViewById(R.id.success_story_cv);
////        bannerSlider =  view.findViewById(R.id.banner_slider1);
//        progress_bar_banner =  view.findViewById(R.id.progress_bar_banner);
//        progress_bar_profiles =  view.findViewById(R.id.progress_bar_profiles);
//        progress_bar_banner_succes_story =  view.findViewById(R.id.progress_bar_banner_succes_story);
//        progress_bar_advertisement =  view.findViewById(R.id.progress_bar_advertisement);
//        progress_bar_news =  view.findViewById(R.id.progress_bar_news);
//        success_story_rv =  view.findViewById(R.id.success_story_rv);
//        advertisement_rv =  view.findViewById(R.id.advertisement_rv);
//        news_rv =  view.findViewById(R.id.news_rv);
//        view_all_succes_story =  view.findViewById(R.id.view_all_succes_story);
//        view_all_news =  view.findViewById(R.id.view_all_news);
//        view_all_advertise =  view.findViewById(R.id.view_all_advertise);
//        profiles_rv =  view.findViewById(R.id.profiles_rv);
//
//        profiles_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        success_story_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        news_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        advertisement_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
//
//
//        bannerSlider = view.findViewById(R.id.banner_slider1);
//        dotIndicator = view.findViewById(R.id.dotIndicator);
//
//
//        user = SharedPrefManager.getInstance(getActivity()).getUser();
//
//        System.out.println("user_id=====11" + String.valueOf(user.getUser_id()));
//
//        loadDataBanner();
//        loadProfileBannerData();
//        getBlogListData();
//        getNewsListData();
//        getAdvertisementData();
//
//
//        view_all_succes_story.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), ViewAllSuccessStoryActivity.class);
//                startActivity(i);
//            }
//        });
//
//        view_all_news.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), ViewAllNewsActivity.class);
//                startActivity(i);
//            }
//        });
//        view_all_advertise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), ViewAllAdvertisementActivity.class);
//                startActivity(i);
//            }
//        });
//
//        /*if net off*/
//        networkStateReceiver = new NetworkStateReceiver();
//        networkStateReceiver.addListener(this);
//        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        /*end code if net off*/
//
//
//        return view;
//
//    }
//
//
//    @Override
//    public void networkAvailable() {
////        loadDataBanner();
////        loadProfileBannerData();
////        getBlogListData();
////        getNewsListData();
////        getAdvertisementData();
//
//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                loadProfileBannerData();
//                loadDataBanner();
//                getBlogListData();
//                getNewsListData();
//                getAdvertisementData();
//
////                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//
//    }
//
//
//    @Override
//    public void networkUnavailable() {
//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Toast.makeText(getActivity(),"Please check internet connection!",Toast.LENGTH_SHORT).show();
//
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }
//
//
//    public  void  loadProfileBannerData(){
//
//        System.out.println("4444444444");
//
//        if (sliderListImagesProfilesList!=null )
//            sliderListImagesProfilesList.clear();
//
//        progress_bar_profiles.setVisibility(View.VISIBLE);
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<ProfileSlider> userResponse = apiService.profileSlider("18",String.valueOf(user.getUser_id()),"5","1");
//        userResponse.enqueue(new Callback<ProfileSlider>() {
//
//            @Override
//            public void onResponse(Call<ProfileSlider> call, Response<ProfileSlider> response) {
//
//                swipeRefreshLayout.setRefreshing(false);
//                profileSlider = response.body();
//
//                progress_bar_profiles.setVisibility(View.GONE);
//                profiles_rv.setVisibility(View.VISIBLE);
//
//                if (response.isSuccessful()) {
//
//                    if(profileSlider.getResid().equals("200")) {
//
////                        System.out.println("profile slider data===========" + new Gson().toJson(profileSlider));
//                        sliderListImagesProfilesList = profileSlider.getProfileSlider();
//                        if (sliderListImagesProfilesList != null) {
//
//                            homeProfilesAdapter = new HomeProfilesAdapter(getActivity(), sliderListImagesProfilesList);
//
//                            profiles_rv.setHasFixedSize(true);
//                            profiles_rv.setAdapter(homeProfilesAdapter);
//                            homeProfilesAdapter.notifyDataSetChanged();
//
//                        }
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ProfileSlider> call, Throwable t) {
//                progress_bar_profiles.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
//                System.out.println("msg1 error profile slider******" + t.toString());
//
//            }
//        });
//    }
//
//
//
//    public  void  getAdvertisementData(){
//
//        System.out.println("333333333333333333");
//
//        if (advertiseListData!=null )
//            advertiseListData.clear();
//
//        progress_bar_advertisement.setVisibility(View.VISIBLE);
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<AdvertiseList> userResponse = apiService.advertiseList("04","5","1");
//        userResponse.enqueue(new Callback<AdvertiseList>() {
//
//            @Override
//            public void onResponse(Call<AdvertiseList> call, Response<AdvertiseList> response) {
//                advertiseList = response.body();
//
//                progress_bar_advertisement.setVisibility(View.GONE);
//                advertisement_rv.setVisibility(View.VISIBLE);
//
//                if (response.isSuccessful()) {
//
//
//                    if(advertiseList.getResid().equals("200")) {
//                        advertise_cv.setVisibility(View.VISIBLE);
//                        advertiseListData = advertiseList.getAdvertiseList();
//                        if (advertiseListData != null) {
//
//                            for (int i = 0; i < advertiseListData.size(); i++) {
////                            System.out.println("success story list .......>>>" + advertiseListData.get(i).getName());
//                            }
//
//
//                            advertiseAdapter = new AdvertiseAdapter(getActivity(), advertiseListData);
//
//                            advertisement_rv.setHasFixedSize(true);
//                            advertisement_rv.setAdapter(advertiseAdapter);
//                            advertiseAdapter.notifyDataSetChanged();
//
//
//                        }
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<AdvertiseList> call, Throwable t) {
//                progress_bar_advertisement.setVisibility(View.GONE);
//
//                System.out.println("msg1 error new******" + t.toString());
//
//            }
//        });
//    }
//
//
//    public  void  getNewsListData(){
//
//        System.out.println("333333333333333333");
//
//        if (newsListData!=null )
//            newsListData.clear();
//
//        progress_bar_news.setVisibility(View.VISIBLE);
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<NewsList> userResponse = apiService.newsList("03","5","1");
//        userResponse.enqueue(new Callback<NewsList>() {
//
//            @Override
//            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
//                newsList = response.body();
//
//                progress_bar_news.setVisibility(View.GONE);
//                news_rv.setVisibility(View.VISIBLE);
//
//                if (response.isSuccessful()) {
//
//                    if(newsList.getResid().equals("200")) {
//                        new_cv.setVisibility(View.VISIBLE);
//
////                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));
//
//                        newsListData = newsList.getNewsList();
//                        if (newsListData != null) {
//
//
//                            newsAdapter = new NewsAdapter(getActivity(), newsListData);
//
//                            success_story_rv.setHasFixedSize(true);
//                            news_rv.setAdapter(newsAdapter);
//                            newsAdapter.notifyDataSetChanged();
//
//
//                        }
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<NewsList> call, Throwable t) {
//                progress_bar_news.setVisibility(View.GONE);
//
//                System.out.println("msg1 error new******" + t.toString());
//
//            }
//        });
//    }
//
//    public  void  getBlogListData(){
//
//        System.out.println("222222222222222");
//
//        if (blogListData!=null )
//            blogListData.clear();
//
//        progress_bar_banner_succes_story.setVisibility(View.VISIBLE);
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<BlogList> userResponse = apiService.blogList("02","5","1");
//        userResponse.enqueue(new Callback<BlogList>() {
//
//            @Override
//            public void onResponse(Call<BlogList> call, Response<BlogList> response) {
//                blogList = response.body();
//
//                progress_bar_banner_succes_story.setVisibility(View.GONE);
//                success_story_rv.setVisibility(View.VISIBLE);
//
//                if (response.isSuccessful()) {
//
////                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));
//
//                    if(blogList.getResid().equals("200")){
//                        success_story_cv.setVisibility(View.VISIBLE);
//
//                        blogListData = blogList.getBlogList();
//                        if(blogListData!=null) {
//
//                            successStoryAdapter = new SuccessStoryAdapter(getActivity(), blogListData);
//
//                            success_story_rv.setHasFixedSize(true);
////                        success_story_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            success_story_rv.setAdapter(successStoryAdapter);
//                            successStoryAdapter.notifyDataSetChanged();
//
//                        }
//                    }
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<BlogList> call, Throwable t) {
//                progress_bar_banner_succes_story.setVisibility(View.GONE);
//
//                System.out.println("msg1 error success story******" + t.toString());
//
//            }
//        });
//    }
//
//
//    public  void  loadDataBanner(){
//
//        System.out.println("111111111111111");
//
//        if (sliderListImages!=null )
//            sliderListImages.clear();
//
//        if (banners!=null )
//            banners.clear();
//
//
//        progress_bar_banner.setVisibility(View.VISIBLE);
//        Api apiService = RetrofitClient.getApiService();
//        Call<SliderList> userResponse = apiService.sliderLits("01");
//        userResponse.enqueue(new Callback<SliderList>() {
//
//            @Override
//            public void onResponse(Call<SliderList> call, Response<SliderList> response) {
//                sliderList = response.body();
//
//                progress_bar_banner.setVisibility(View.GONE);
//                bannerSlider.setVisibility(View.VISIBLE);
//
//                if (response.isSuccessful()) {
//
//
//
//
////                    System.out.println("resp banner.......>>>" + new Gson().toJson(response.body()));
//
//                    sliderListImages = sliderList.getSliderList();
//                    if(sliderListImages!=null) {
////                        for (int i = 0; i < sliderListImages.size(); i++) {
////                            System.out.println("banner list .......>>>" + sliderListImages.get(i).getImgs());
////                        }
//
//                        /*-------*/
//                        for (int i = 0; i < sliderListImages.size(); i++)
//                        {
//
////                            banners.add(new RemoteBanner(sliderListImages.get(i).getImgs()));
////                            bannerSlider.setBanners(banners);
////                            bannerSlider.setIndicatorSize(15);
//
//
//                            // Dynamic Banner List
//                            List<String> banners = new ArrayList<>();
//                            banners.add("https://www.shutterstock.com/shutterstock/photos/1489086422/display_1500/stock-vector-colorful-abstract-banner-template-with-dummy-text-for-web-design-landing-page-and-print-material-1489086422.jpg");
//                            banners.add("https://static.vecteezy.com/system/resources/previews/003/085/046/non_2x/colorful-abstract-banner-template-with-dummy-text-hand-drawn-doodle-vector.jpg");
//                            banners.add("https://www.shutterstock.com/image-vector/colorful-abstract-banner-template-dummy-260nw-1538384741.jpg");
//
//                            // Setting up the ViewPager with Adapter
//                            BannerAdapter bannerAdapter = new BannerAdapter(requireContext(), sliderListImages);
////                    bannerViewPager.setAdapter(bannerAdapter);
//                            bannerSlider.setAdapter(bannerAdapter);
//
//                            // Connecting the dots indicator with TabLayoutMediator
//                            new TabLayoutMediator(dotIndicator, bannerSlider, (tab, position) -> {
//                                // Nothing to do here
//                            }).attach();
//                            // Auto-Sliding Logic
//                            setupAutoSlide(bannerAdapter.getItemCount());
//
//
//                        }
//
//                        /*-------*/
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SliderList> call, Throwable t) {
//                progress_bar_banner.setVisibility(View.GONE);
//
//                System.out.println("msg1 banner frag******" + t.toString());
//
//            }
//        });
//    }
//
//private void setupAutoSlide(int itemCount) {
//    autoSlideHandler = new Handler(Looper.getMainLooper());
//    autoSlideRunnable = new Runnable() {
//        @Override
//        public void run() {
//            int currentItem = bannerSlider.getCurrentItem();
//            int nextItem = (currentItem + 1) % itemCount; // Loop back to the first item
//            bannerSlider.setCurrentItem(nextItem, true);
//            autoSlideHandler.postDelayed(this, 3000); // 3-second delay
//        }
//    };
//    autoSlideHandler.postDelayed(autoSlideRunnable, 3000);
//
//
//    // Pausing auto-slide on user interaction
//    bannerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//        @Override
//        public void onPageSelected(int position) {
//            super.onPageSelected(position);
//            autoSlideHandler.removeCallbacks(autoSlideRunnable); // Pause auto-slide
//            autoSlideHandler.postDelayed(autoSlideRunnable, 3000); // Resume after delay
//        }
//    });
//}
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (autoSlideHandler != null) {
//            autoSlideHandler.removeCallbacks(autoSlideRunnable); // Clean up to avoid memory leaks
//        }
//    }
//
//
//    public static boolean isNetworkAvailable(Context context) {
//        isNetworkAvailable = false;
//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity == null) {
//            return false;
//        } else {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        isNetworkAvailable = true;
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }//isNetworkAvailable()
//}






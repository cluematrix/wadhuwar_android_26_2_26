package com.WadhuWarProject.WadhuWar.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.WadhuWarProject.WadhuWar.BuildConfig;
import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.AccountSettingActivity;
import com.WadhuWarProject.WadhuWar.activity.AccountTransactionActivity;
import com.WadhuWarProject.WadhuWar.activity.AdvisorActivity;
import com.WadhuWarProject.WadhuWar.activity.AdvisorPlanActivity;
import com.WadhuWarProject.WadhuWar.activity.AstroDetailsActivity;
import com.WadhuWarProject.WadhuWar.activity.ChatListActivity;
import com.WadhuWarProject.WadhuWar.activity.ContactFiltersActivity;
import com.WadhuWarProject.WadhuWar.activity.HelpSupportActivity;
import com.WadhuWarProject.WadhuWar.activity.MainActivity;
import com.WadhuWarProject.WadhuWar.activity.MyPhotosActivity;
import com.WadhuWarProject.WadhuWar.activity.MyProfileActivity;
import com.WadhuWarProject.WadhuWar.activity.NewMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.NotificationsActivity;
import com.WadhuWarProject.WadhuWar.activity.PartnerPreferencesActivity;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.activity.PremiumMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.RecentVisitorMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.TermsConditionActivity;
import com.WadhuWarProject.WadhuWar.activity.ViewSelfProfileActivity;
import com.WadhuWarProject.WadhuWar.adapter.NewMatchesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.PremiumMatchesAdapter;
import com.WadhuWarProject.WadhuWar.adapter.RecentVisitorMatchesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.NewMatchesList;
import com.WadhuWarProject.WadhuWar.model.PremiunMatchesList;
import com.WadhuWarProject.WadhuWar.model.ProfilePercent;
import com.WadhuWarProject.WadhuWar.model.RecentVisitorMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.squareup.picasso.BuildConfig;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.WadhuWarProject.WadhuWar.fragments.MatchesRecentlyViewedTabFragment.isNetworkAvailable;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class MyProfileFragment extends Fragment implements  NetworkStateReceiver.NetworkStateReceiverListener{
    private static final int WRITE_PERMISSION = 0x01;

    InsertResponse notificationMsg;
    Button count_msg;
    TextView profile_complete_percent_txt;
    static boolean isNetworkAvailable;

    String isHidden;

    TextView edit_profile_txt,acc_verified,terms_condition,view_profile_txt;
    RelativeLayout partner_preferences,contact_filter,account_Setting,account_transaction,rating,notification_setting,help_support,notification,rate_app;
    UserData user;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView backLL;
    CircleImageView profile_image;
    FetchProfile fetchProfile;
    TextView name,id_key,account_type,copyright_txt,version_name;
    ImageView verified_img;
    FrameLayout profile_imgFM;
    ProfilePercent profilePercent;
    InsertResponse response_check;
    RelativeLayout progress_bar_premium,progress_bar_recent_visitor,progress_bar_new_matches;

    RecyclerView premium_rv,new_mathces_rv,recent_visitor_rv;

    ArrayList<PremiunMatchesList.PremiunMatchesListData> premiunMatchesListData = new ArrayList<>();
    PremiunMatchesList premiunMatchesList;
    ArrayList<NewMatchesList.NewmatchesListData> newmatchesListData = new ArrayList<>();
    NewMatchesList newMatchesList;
    ArrayList<RecentVisitorMatchesList.RecentVisitorsListmatchesListData> recentVisitorsListmatchesListData = new ArrayList<>();
    RecentVisitorMatchesList recentVisitorMatchesList;

    PremiumMatchesAdapter premiumMatchesAdapter;
    NewMatchesAdapter newMatchesAdapter;
    RecentVisitorMatchesAdapter recentVisitorMatchesAdapter;

    LinearLayout upgrade_now_btn,upgradedLL;

    TextView premium_txt,new_matches_txt,recent_visitor_txt,see_all_premium,see_all_new_mathces,see_all_recent_visitor,
            profile_view_count;

    CardView premiumCV,newmatchesCV,recentvisitorCV;


    CardView share_cv,advisor_cv;
    Button btnGetAdvisor;
    private Uri imageUri;
    String fileUri;

    RelativeLayout chat_btn;
    ProgressDialog progressBar;

    ProgressBar progress_bar_profile;

    public MyProfileFragment() {

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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == WRITE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("LOG_TAG", "Write Permission Failed");
                Toast.makeText(getActivity(), "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }

    private void requestWritePermission(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =inflater.inflate(R.layout.fragment_my_profile, container, false);

        count_msg = view.findViewById(R.id.count_msg);
        progress_bar_profile = view.findViewById(R.id.progress_bar_profile);
        profile_complete_percent_txt = view.findViewById(R.id.profile_complete_percent_txt);
        backLL = view.findViewById(R.id.backLL);
        chat_btn = view.findViewById(R.id.chat_btn);
        advisor_cv = view.findViewById(R.id.advisor_cv);
        btnGetAdvisor = view.findViewById(R.id.btnGetAdvisor);
        view_profile_txt = view.findViewById(R.id.view_profile_txt);
        share_cv = view.findViewById(R.id.share_cv);
        profile_view_count = view.findViewById(R.id.profile_view_count);
        recentvisitorCV = view.findViewById(R.id.recentvisitorCV);
        newmatchesCV = view.findViewById(R.id.newmatchesCV);
        premiumCV = view.findViewById(R.id.premiumCV);
        account_transaction = view.findViewById(R.id.account_transaction);
        version_name = view.findViewById(R.id.version_name);
        copyright_txt = view.findViewById(R.id.copyright_txt);
        profile_imgFM = view.findViewById(R.id.profile_imgFM);
        edit_profile_txt = view.findViewById(R.id.edit_profile_txt);
        acc_verified = view.findViewById(R.id.acc_verified);
        partner_preferences = view.findViewById(R.id.partner_preferences);
        contact_filter = view.findViewById(R.id.contact_filter);
        account_Setting = view.findViewById(R.id.account_Setting);
        help_support = view.findViewById(R.id.help_support);
        notification_setting = view.findViewById(R.id.notification_setting);
        rating = view.findViewById(R.id.rating);
        notification = view.findViewById(R.id.notification);
        rate_app = view.findViewById(R.id.rate_app);
        terms_condition = view.findViewById(R.id.terms_condition);
        profile_image = view.findViewById(R.id.profile_image);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        name = view.findViewById(R.id.name);
        id_key = view.findViewById(R.id.id_key);
        account_type = view.findViewById(R.id.account_type);
        verified_img = view.findViewById(R.id.verified_img);
        premium_rv = view.findViewById(R.id.premium_rv);
        new_mathces_rv = view.findViewById(R.id.new_mathces_rv);
        recent_visitor_rv = view.findViewById(R.id.recent_visitor_rv);
        progress_bar_premium = view.findViewById(R.id.progress_bar_premium);
        progress_bar_recent_visitor = view.findViewById(R.id.progress_bar_recent_visitor);
        progress_bar_new_matches = view.findViewById(R.id.progress_bar_new_matches);
        premium_txt = view.findViewById(R.id.premium_txt);
        new_matches_txt = view.findViewById(R.id.new_matches_txt);
        recent_visitor_txt = view.findViewById(R.id.recent_visitor_txt);
        see_all_premium = view.findViewById(R.id.see_all_premium);
        see_all_new_mathces = view.findViewById(R.id.see_all_new_mathces);
        see_all_recent_visitor = view.findViewById(R.id.see_all_recent_visitor);
        upgrade_now_btn = view.findViewById(R.id.upgrade_now_btn);
        upgradedLL = view.findViewById(R.id.upgradedLL);


        user = SharedPrefManager.getInstance(getActivity()).getUser();

        getMsgCount();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                getMsgCount();
                handler.postDelayed(this, 60000);

            }
        }, 60000);

        requestWritePermission();



        checkProfilePercent();
        fetchProfileData();
        fetchPremiumListData();
        fetchNewMatchesData();
        fetchRecentVisitorData();
        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


        advisor_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AdvisorActivity.class);
                startActivity(i);
            }
        });
        btnGetAdvisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AdvisorActivity.class);
                startActivity(i);
            }
        });


        view_profile_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ViewSelfProfileActivity.class);
                startActivity(i);
            }
        });


        terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), TermsConditionActivity.class);
                startActivity(i);


            }
        });

        edit_profile_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(i);
            }
        });



        profile_imgFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), MyPhotosActivity.class);
                startActivity(i);
            }
        });




        partner_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), PartnerPreferencesActivity.class);
                startActivity(i);
            }
        });

        contact_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), ContactFiltersActivity.class);
                startActivity(i);
            }
        });


        account_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AccountTransactionActivity.class);
                startActivity(i);
            }
        });

        account_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AccountSettingActivity.class);
                startActivity(i);
            }
        });

        help_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), HelpSupportActivity.class);
                startActivity(i);
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayStoreForRating();
            }
        });

        notification_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotificationSettings();
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), NotificationsActivity.class);
                startActivity(i);
            }
        });


        rate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });





        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), PremiumActivity.class);
                startActivity(i);
            }
        });


        upgradedLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), PremiumActivity.class);
                startActivity(i);
            }
        });


        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), ChatListActivity.class);
                startActivity(i);
            }
        });

        premium_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        new_mathces_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recent_visitor_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        copyright_txt.setText("Copyright " + year);

//        String versionName = BuildConfig.VERSION_NAME;
//        version_name.setText("Version " + versionName);
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String versionName = pInfo.versionName;    // Gets "1.9" from build.gradle
            int versionCode = pInfo.versionCode;       // Gets 9 from build.gradle
            String versionText = "Version " + versionCode + " (" + versionName + ")";
            version_name.setText(versionText);
        } catch (PackageManager.NameNotFoundException e) {
            version_name.setText("Version: Unknown");
            e.printStackTrace();
        }
        return view;

    }

    private void openNotificationSettings() {
        Intent intent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For Android 8.0 (Oreo) and above
            intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, requireActivity().getPackageName());
        } else {
            // For older Android versions
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:" + requireActivity().getPackageName()));
        }

        startActivity(intent);
    }

    private void openPlayStoreForRating() {
        String packageName = requireActivity().getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    @Override
    public void onResume() {
        fetchProfileData();
        super.onResume();
    }

    private void getMsgCount() {

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.notificationCount(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {

                notificationMsg = response.body();

                System.out.println("notificationMsg.getCount()=========" + notificationMsg.getCount());

                if (response.isSuccessful()) {

                    if(notificationMsg.getResid().equals("200")){
                        if(!notificationMsg.getCount().equals("0")){
                            count_msg.setVisibility(View.VISIBLE);
                            count_msg.setText(notificationMsg.getCount());
                        }else{
                            count_msg.setVisibility(View.GONE);
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
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
    public void networkAvailable() {

//        checkProfilePercent();
//        fetchProfileData();
//        fetchPremiumListData();
//        fetchNewMatchesData();
//        fetchRecentVisitorData();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchProfileData();
                fetchPremiumListData();
                fetchNewMatchesData();
                fetchRecentVisitorData();
                getMsgCount();

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

    public  void  fetchRecentVisitorData(){

        System.out.println("333333333333333333");

        if (recentVisitorsListmatchesListData!=null )
            recentVisitorsListmatchesListData.clear();

        progress_bar_recent_visitor.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<RecentVisitorMatchesList> userResponse = apiService.recentVisitors(String.valueOf(user.getUser_id()),"5","1");
        userResponse.enqueue(new Callback<RecentVisitorMatchesList>() {

            @Override
            public void onResponse(Call<RecentVisitorMatchesList> call, Response<RecentVisitorMatchesList> response) {
                recentVisitorMatchesList = response.body();

                progress_bar_recent_visitor.setVisibility(View.GONE);
                recent_visitor_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    if(recentVisitorMatchesList.getResid().equals("200")) {
                        recentvisitorCV.setVisibility(View.VISIBLE);

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));

                        recentVisitorsListmatchesListData = recentVisitorMatchesList.getRecentVisitorsList();
                        if (recentVisitorsListmatchesListData != null) {

                            for (int i = 0; i < recentVisitorsListmatchesListData.size(); i++) {
//                            System.out.println("success story list .......>>>" + newsListData.get(i).getName());
                            }

                            recent_visitor_txt.setText("Recent Visitors (" + recentVisitorMatchesList.getCount() + ")");

                            recentVisitorMatchesAdapter = new RecentVisitorMatchesAdapter(getActivity(), recentVisitorsListmatchesListData);

                            recent_visitor_rv.setHasFixedSize(true);
                            recent_visitor_rv.setAdapter(recentVisitorMatchesAdapter);
                            recentVisitorMatchesAdapter.notifyDataSetChanged();

                        }
                    }else{
                        recentvisitorCV.setVisibility(View.GONE);
                    }


                }

            }

            @Override
            public void onFailure(Call<RecentVisitorMatchesList> call, Throwable t) {
                progress_bar_recent_visitor.setVisibility(View.GONE);

                System.out.println("msg1 error RecentVisitorMatchesList******" + t.toString());
                if(!isNetworkAvailable(getActivity())){
//                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public  void  fetchNewMatchesData(){

        System.out.println("333333333333333333");

        if (newmatchesListData!=null )
            newmatchesListData.clear();

        progress_bar_new_matches.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<NewMatchesList> userResponse = apiService.newMatches(String.valueOf(user.getUser_id()),"5","1");
        userResponse.enqueue(new Callback<NewMatchesList>() {

            @Override
            public void onResponse(Call<NewMatchesList> call, Response<NewMatchesList> response) {
                newMatchesList = response.body();

                progress_bar_new_matches.setVisibility(View.GONE);
                new_mathces_rv.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {


                    if(newMatchesList.getResid().equals("200")) {
                        newmatchesCV.setVisibility(View.VISIBLE);

//                    System.out.println("resp success story.......>>>" + new Gson().toJson(response.body()));

                        newmatchesListData = newMatchesList.getNewmatchesList();
                        if (newmatchesListData != null) {

                            for (int i = 0; i < newmatchesListData.size(); i++) {
//                            System.out.println("success story list .......>>>" + newsListData.get(i).getName());
                            }

                            new_matches_txt.setText("New Matches (" + newMatchesList.getCount() + ")");

                            newMatchesAdapter = new NewMatchesAdapter(getActivity(), newmatchesListData);


                            new_mathces_rv.setHasFixedSize(true);
                            new_mathces_rv.setAdapter(newMatchesAdapter);
                            newMatchesAdapter.notifyDataSetChanged();

                        }
                    }else{
                        newmatchesCV.setVisibility(View.GONE);
                    }


                }

            }

            @Override
            public void onFailure(Call<NewMatchesList> call, Throwable t) {
                progress_bar_new_matches.setVisibility(View.GONE);

                System.out.println("msg1 error NewMatchesList******" + t.toString());
                if(!isNetworkAvailable(getActivity())){
//                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public  void  fetchPremiumListData(){

        System.out.println("333333333333333333");

        if (premiunMatchesListData!=null )
            premiunMatchesListData.clear();

        progress_bar_premium.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<PremiunMatchesList> userResponse = apiService.premiumMatches(String.valueOf(user.getUser_id()),"5","1");
        userResponse.enqueue(new Callback<PremiunMatchesList>() {

            @Override
            public void onResponse(Call<PremiunMatchesList> call, Response<PremiunMatchesList> response) {
                premiunMatchesList = response.body();

                progress_bar_premium.setVisibility(View.GONE);
                premium_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

//                    System.out.println("premium.......>>>" + new Gson().toJson(response.body()));

                    if(premiunMatchesList.getResid().equals("200")) {
                        premiumCV.setVisibility(View.VISIBLE);


                        premiunMatchesListData = premiunMatchesList.getPremiumMatchesList();
                        if (premiunMatchesListData != null) {

                            System.out.println("premiunMatchesListData size .......>>>" + premiunMatchesListData.size());
                            if (premiunMatchesListData.size() == 0) {
                            }

                            for (int i = 0; i < premiunMatchesListData.size(); i++) {
//                            System.out.println("success story list .......>>>" + newsListData.get(i).getName());
                            }

                            premium_txt.setText("Premium Matches (" + premiunMatchesList.getCount() + ")");


                            premiumMatchesAdapter = new PremiumMatchesAdapter(getActivity(), premiunMatchesListData);

                            premium_rv.setHasFixedSize(true);
                            premium_rv.setAdapter(premiumMatchesAdapter);
                            premiumMatchesAdapter.notifyDataSetChanged();


                        }

                    }else{
                        premiumCV.setVisibility(View.GONE);
                    }


                }

            }

            @Override
            public void onFailure(Call<PremiunMatchesList> call, Throwable t) {
                progress_bar_premium.setVisibility(View.GONE);

                System.out.println("msg1 error premium list******" + t.toString());
                if(!isNetworkAvailable(getActivity())){
//                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



    public  void fetchProfileData(){

        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {

                swipeRefreshLayout.setRefreshing(false);

                fetchProfile = response.body();
                if (response.isSuccessful()) {


                    if (fetchProfile.getResid().equals("200")) {
                        isHidden = fetchProfile.getIsDelete();
                        SharedPrefManager.getInstance(getActivity()).saveString(isHidden);
//                        share_cv.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                    String link = "https://wadhuwar.com/Matrimonial/index.php?id=" + user.getUser_id();
//
//                                String string =
//                                        "*Cast / Subcaste* : " + fetchProfile.getCaste_name() + " / " + fetchProfile.getSubcaste_name() +
//                                                "\n\n" +
//                                                "*Age / Height* : " + fetchProfile.getAge() + " / " + fetchProfile.getHeight() +
//                                                "\n\n" +
//                                                "*Education* : " + fetchProfile.getEducation_name() +
//                                                "\n\n" +
//                                                "*Profession / Income* : " + fetchProfile.getOccupation_name() + " / " + fetchProfile.getYearly_salary() +
//                                                "\n\n" +
//                                                "*Permanent Location / State* : " + fetchProfile.getPer_address() + " / " + fetchProfile.getPer_state_name() +
//                                                "\n\n" +
//                                                "*Current / Work Location* : " + fetchProfile.getCurrent_address() + " / " + fetchProfile.getOfc_address();
//
//                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                                    sharingIntent.setType("text/plain");
//                                    sharingIntent.putExtra(Intent.EXTRA_TEXT,
//                                            link +
//                                                    "*" + fetchProfile.getFname() + "*\n" +
//                                                    "*(" + fetchProfile.getAcc_id() + ")*\n\n" +
//                                                    "*Bride Groom Introduction*\n" +
//                                                    "\n" +
//                                                   /* "*I invite you by sharing the marriage bride and groom introduction link | I use Trusted Wadhu War Parichay App |*\n" +
//                                                    "\n" +*/
//                                                    string +
//                                                    "\n" +
//                                                    "\n" +
//                                                    "*Download Free Register App Use my referral code and see your favorite bride groom (DRWAWM00" + fetchProfile.getUser_id() + ")*\n" +
//                                                    "\n" +
//                                                    "*Search the link \uD83D\uDC49\uD83C\uDFFB*\n" +
//                                                    "*https://wadhuwar.com/Matrimonial* \n" +
//                                                    "\n" +
//                                                    "★★★★★★★★★★★★\n" +
//                                                    "*100% Reliable Bridesmaid Introducing*");
//
//                                    startActivity(Intent.createChooser(sharingIntent, "Share Image"));
//                                /*----------------*/
//
//                               /* // use intent to share image
//                                Intent share = new Intent(Intent.ACTION_SEND);
//                                share.setType("image/*");
//                                share.putExtra(Intent.EXTRA_STREAM, uri);
//
//                                share.putExtra(Intent.EXTRA_TEXT,
//                                        "*"+fetchProfile.getFname()+"*\n" +
//                                                "*("+fetchProfile.getAcc_id()+")*\n\n"+
//                                                "*वधु वर परीचय*\n" +
//                                                "\n" +
//                                                "*विवाह वधु वर परीचय लिंक आपको शेयर कर निमंत्रित करता हू| विश्वसनीय वधु वर परीचय एप्प का मै उपयोग करता हू|*\n" +
//                                                "\n" +
//                                                "*स्वजाती विवाह वधु वर परीचय के लिए फ्री  रजिस्टर अप्प डाउनलोड कर मेरा रेफर कोड उपयोग करे और मनपसंद वधु वर देखे (WAWM00"+fetchProfile.getUser_id()+ ")*\n" +
//                                                "\n" +
//                                                "*लिंक को सर्च करे \uD83D\uDC49\uD83C\uDFFB*\n" +
//                                                "*https://wadhuwar.com/Matrimonial* \n" +
//                                                "\n" +
//                                                "★★★★★★★★★★★★\n" +
//                                                "*१००%विश्वसनीय वधु वर परीचय*");
//
//
//                                progressBar.dismiss();
//
//                                startActivity(Intent.createChooser(share, "Share Image"));
//
//
//
//
//
//                                progressBar = ProgressDialog.show(getActivity(), "", "Please Wait...");
//
//
//                                imageUri = Uri.parse(fetchProfile.getImages().get(0).getImgs());
//
//                                System.out.println("image name of user=========" +imageUri );
//
//
//                                Picasso.with(getActivity()).load(fetchProfile.getImages().get(0).getImgs()).into(new Target() {
//                                    @Override
//                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                                        try {
//                                            File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
//                                            if (!mydir.exists()) {
//                                                mydir.mkdirs();
//                                            }
//
//                                            System.out.println("img url==========" + fetchProfile.getImages().get(0).getImgs());
//
//                                            fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
//
//                                            System.out.println("fileUri img url==========" + fileUri);
//
//
//                                            FileOutputStream outputStream = new FileOutputStream(fileUri);
//
//                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                                            outputStream.flush();
//                                            outputStream.close();
//                                        } catch(IOException e) {
//                                            e.printStackTrace();
//                                        }
//
//                                        System.out.println("22 fileUri img url==========" + fileUri);
//                                        Uri uri= Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap,null,null));
//
//                                        System.out.println("url check///////" +uri );
//
//                                        // use intent to share image
//                                        Intent share = new Intent(Intent.ACTION_SEND);
//                                        share.setType("image/*");
//                                        share.putExtra(Intent.EXTRA_STREAM, uri);
//
//
//
//
//                                        share.putExtra(Intent.EXTRA_TEXT,
//                                                "*"+fetchProfile.getFname()+"*\n" +
//                                                        "*("+fetchProfile.getAcc_id()+")*\n\n"+
//                                                "*वधु वर परीचय*\n" +
//                                                        "\n" +
//                                                        "*विवाह वधु वर परीचय लिंक आपको शेयर कर निमंत्रित करता हू| विश्वसनीय वधु वर परीचय एप्प का मै उपयोग करता हू|*\n" +
//                                                        "\n" +
//                                                        "*स्वजाती विवाह वधु वर परीचय के लिए फ्री  रजिस्टर अप्प डाउनलोड कर मेरा रेफर कोड उपयोग करे और मनपसंद वधु वर देखे (WAWM00"+fetchProfile.getUser_id()+ ")*\n" +
//                                                        "\n" +
//                                                        "*लिंक को सर्च करे \uD83D\uDC49\uD83C\uDFFB*\n" +
//                                                        "*https://wadhuwar.com/Matrimonial* \n" +
//                                                        "\n" +
//                                                        "★★★★★★★★★★★★\n" +
//                                                        "*१००%विश्वसनीय वधु वर परीचय*");
//
//
//                                        progressBar.dismiss();
//
//                                        startActivity(Intent.createChooser(share, "Share Image"));
//                                    }
//                                    @Override
//                                    public void onBitmapFailed(Drawable errorDrawable) {
//                                    }
//                                    @Override
//                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//                                    }
//                                });*/
//
//
//
//
//                                /*----------------*/
////
////                                Intent sendIntent = new Intent();
////                                sendIntent.setAction(Intent.ACTION_SEND);
////
////                                sendIntent.putExtra(Intent.EXTRA_TEXT,
////                                        "*वधु वर परीचय*\n" +
////                                                "\n" +
////                                                "*विवाह वधु वर परीचय लिंक आपको शेयर कर निमंत्रित करता हू| विश्वसनीय वधु वर परीचय एप्प का मै उपयोग करता हू|*\n" +
////                                                "\n" +
////                                                "*स्वजाती विवाह वधु वर परीचय के लिए फ्री  रजिस्टर अप्प डाउनलोड कर मेरा रेफर कोड उपयोग करे और मनपसंद वधु वर देखे (WAWM00"+fetchProfile.getUser_id()+ ")*\n" +
////                                                "\n" +
////                                                "*लिंक को सर्च करे \uD83D\uDC49\uD83C\uDFFB*\n" +
////                                                "*https://wadhuwar.com/Matrimonial* \n" +
////                                                "\n" +
////                                                "★★★★★★★★★★★★\n" +
////                                                "*१००%विश्वसनीय वधु वर परीचय*");
////
////
////
////                                sendIntent.setType("text/plain");
////                                startActivity(sendIntent);
//                            }
//                        });

                        share_cv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String link = "https://wadhuwar.com/Matrimonial/index.php?id=" + user.getUser_id();

                                String string =
                                        "*Cast / Subcaste* : " + fetchProfile.getCaste_name() + " / " + fetchProfile.getSubcaste_name() +
                                                "\n\n" +
                                                "*Age / Height* : " + fetchProfile.getAge() + " / " + fetchProfile.getHeight() +
                                                "\n\n" +
                                                "*Education* : " + fetchProfile.getEducation_name() +
                                                "\n\n" +
                                                "*Profession / Income* : " + fetchProfile.getOccupation_name() + " / " + fetchProfile.getYearly_salary() +
                                                "\n\n" +
                                                "*Permanent Location / State* : " + fetchProfile.getPer_address() + " / " + fetchProfile.getPer_state_name() +
                                                "\n\n" +
                                                "*Current / Work Location* : " + fetchProfile.getCurrent_address() + " / " + fetchProfile.getOfc_address();

                                String shareText =
                                        link +
                                                "\n*" + fetchProfile.getFname() + "*\n" +
                                                "*(" + fetchProfile.getAcc_id() + ")*\n\n" +
                                                "*Bride Groom Introduction*\n" +
                                                "\n" +
                                                string +
                                                "\n" +
                                                "\n" +
                                                "*Download Free Register App Use my referral code and see your favorite bride groom (WAWM00" + fetchProfile.getUser_id() + ")*\n" +
                                                "\n" +
                                                "*Search the link \uD83D\uDC49\uD83C\uDFFB*\n" +
                                                "*https://wadhuwar.com/Matrimonial/index.php?id=*\n" +
                                                "\n" +
                                                "★★★★★★★★★★★★\n" +
                                                "*100% Reliable Bridesmaid Introducing*";

                                // Show progress dialog
                                ProgressDialog progressBar = ProgressDialog.show(getActivity(), "", "Please Wait...");

                                // Load image using Picasso
                                String imageUrl = fetchProfile.getImages().get(0).getImgs(); // Ensure this is a valid URL
                                Log.d("ImageURL", "Image URL: " + imageUrl);
                                Picasso.get().load(imageUrl).into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        try {
                                            // Save image to external files directory
                                            File mydir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "shared_profile");
                                            if (!mydir.exists() && !mydir.mkdirs()) {
                                                Log.e("DirectoryError", "Failed to create directory!");
                                                progressBar.dismiss();
                                                return;
                                            }

                                            File imageFile = new File(mydir, "shared_" + System.currentTimeMillis() + ".jpg");
                                            FileOutputStream outputStream = new FileOutputStream(imageFile);
//                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                            Bitmap watermarkedBitmap = addWatermark(bitmap, "Wadhuwar.com     ");

                                            watermarkedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                            outputStream.flush();
                                            outputStream.close();

                                            // Get URI for the image file using FileProvider
                                            Uri uri = FileProvider.getUriForFile(
                                                    getActivity(),
                                                    getActivity().getPackageName() + ".fileprovider",  // Dynamic authority
                                                    imageFile
                                            );

                                            // Create Intent to share both image and text
                                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                            shareIntent.setType("image/*"); // Use image/* to allow sharing image with text
                                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri); // Add image URI
                                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText); // Add text
                                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                            progressBar.dismiss();
                                            startActivity(Intent.createChooser(shareIntent, "Share Profile"));

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            progressBar.dismiss();
                                            // Fallback to sharing text only
                                            Intent textOnlyIntent = new Intent(Intent.ACTION_SEND);
                                            textOnlyIntent.setType("text/plain");
                                            textOnlyIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                                            startActivity(Intent.createChooser(textOnlyIntent, "Share Profile"));
                                        }
                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                        Log.e("ImageLoadError", "Failed to load image: " + e.getMessage());
                                        progressBar.dismiss();
                                        // Fallback to sharing text only
                                        Intent textOnlyIntent = new Intent(Intent.ACTION_SEND);
                                        textOnlyIntent.setType("text/plain");
                                        textOnlyIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                                        startActivity(Intent.createChooser(textOnlyIntent, "Share Profile"));
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                        // Optional: Keep progress dialog visible while loading
                                    }
                                });
                            }

                            private Bitmap addWatermark(Bitmap originalBitmap, String watermarkText) {

                                Bitmap result = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                                Canvas canvas = new Canvas(result);

                                // ================= TEXT WATERMARK =================
                                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                textPaint.setColor(Color.parseColor("#80000000"));
                                textPaint.setTextSize(28);
                                textPaint.setFakeBoldText(true);

// Measure text
                                Rect textBounds = new Rect();
                                textPaint.getTextBounds(watermarkText, 0, watermarkText.length(), textBounds);

// Center coordinates
                                float centerX = result.getWidth() / 2f;
                                float centerY = result.getHeight() / 2f;

// Rotate around center
                                canvas.save();
                                canvas.rotate(34, centerX, centerY);

// Draw text exactly centered
                                canvas.drawText(
                                        watermarkText,
                                        centerX - textBounds.width() / 2f,
                                        centerY + textBounds.height() / 2f,
                                        textPaint
                                );

// Restore canvas
                                canvas.restore();


                                // ================= LOGO WATERMARK =================
//                Bitmap logoBitmap = BitmapFactory.decodeResource(
//                        getResources(),
//                        R.drawable.logo // 🔴 CHANGE THIS to your logo drawable
//                );
//
//                if (logoBitmap != null) {
//
//                    // Scale logo (10–15% of image width looks perfect)
//                    int logoWidth = result.getWidth() / 6;
//                    int logoHeight = (logoWidth * logoBitmap.getHeight()) / logoBitmap.getWidth();
//
//                    Bitmap scaledLogo = Bitmap.createScaledBitmap(
//                            logoBitmap,
//                            logoWidth,
//                            logoHeight,
//                            true
//                    );
//
//                    // Position: TOP-LEFT with margin
//                    int margin = 24;
//                    float xLogo = margin;
//                    float yLogo = margin;
//
//                    canvas.drawBitmap(scaledLogo, xLogo, yLogo, null);
//                }

                                return result;
                            }

                        });

                        profile_view_count.setText(fetchProfile.getView_count() +"/" + fetchProfile.getCount());


                        if(fetchProfile.getPremium().equals("1")){
                            upgradedLL.setVisibility(View.VISIBLE);
                            upgrade_now_btn.setVisibility(View.GONE);

                        }else{
                            upgrade_now_btn.setVisibility(View.VISIBLE);
                            upgradedLL.setVisibility(View.GONE);

                        }



                        System.out.println("fetchProfile.getPremium()=======" +  fetchProfile.getPremium());

                        see_all_premium.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent i = new Intent(getActivity(), PremiumMatchesActivity.class);
                                i.putExtra("premium_value",fetchProfile.getPremium());
                                startActivity(i);
                            }
                        });


                        see_all_new_mathces.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent i = new Intent(getActivity(), NewMatchesActivity.class);
                                i.putExtra("premium_value",fetchProfile.getPremium());

                                startActivity(i);
                            }
                        });



                        see_all_recent_visitor.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent i = new Intent(getActivity(), RecentVisitorMatchesActivity.class);
                                i.putExtra("premium_value",fetchProfile.getPremium());

                                startActivity(i);
                            }
                        });



                        if (fetchProfile.getImages() != null) {
                            if (fetchProfile.getImages().size() != 0) {
                                Glide.with(getActivity().getApplicationContext()).load(fetchProfile.getImages().get(0).getImgs()).into(profile_image);

                                Glide.with(getActivity().getApplicationContext()).load(fetchProfile.getImages().get(0).getImgs())
                                        .apply(bitmapTransform(new BlurTransformation(150)))
                                        .into(backLL);

                            } else {

                                if(fetchProfile.getGender().equals("Female")){
                                    profile_image.setImageResource(R.drawable.female_avtar);
                                    backLL.setBackgroundResource(R.color.black);
                                }else {
                                    profile_image.setImageResource(R.drawable.male_avtar);
                                    backLL.setBackgroundResource(R.color.black);
                                }

                            }
                        } else {
                            if(fetchProfile.getGender().equals("Female")){
                                profile_image.setImageResource(R.drawable.female_avtar);
                                backLL.setBackgroundResource(R.color.black);
                            }else {
                                profile_image.setImageResource(R.drawable.male_avtar);
                                backLL.setBackgroundResource(R.color.black);
                            }
                        }






                        name.setText(String.format("%s %s %s", fetchProfile.getFname(), fetchProfile.getMname(), fetchProfile.getLname()));
                        id_key.setText(fetchProfile.getAcc_id());
                        account_type.setText(fetchProfile.getAcc_type());

                        if (fetchProfile.getAcc_type().equals("1")) {
                            verified_img.setVisibility(View.VISIBLE);

                        } else {
                            verified_img.setVisibility(View.GONE);
                        }

                        if (fetchProfile.getAccount_verify().equals("1")) {
                            acc_verified.setVisibility(View.VISIBLE);
                        } else {
                            acc_verified.setVisibility(View.GONE);
                        }

//                    verified_img

                    }
                }

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


    public void checkProfilePercent(){

        System.out.println("profile percent api=========="  + user.getUser_id());
        Api apiService = RetrofitClient.getApiService();
        Call<ProfilePercent> userResponse = apiService.profilecompelte(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<ProfilePercent>() {

            @Override
            public void onResponse(Call<ProfilePercent> call, Response<ProfilePercent> response) {
                profilePercent = response.body();

                int percent = Integer.parseInt(profilePercent.getPercent());

                profile_complete_percent_txt.setText("Your profile scrore " +percent +"%");
               progress_bar_profile.setProgress(percent);


            }

            @Override
            public void onFailure(Call<ProfilePercent> call, Throwable t) {
                System.out.println("msg1 my ProfilePercent******" + t.toString());
//                progressBar.dismiss();

//                if(!isNetworkAvailable(ChatActivity.this)){
//                    Toast.makeText(ChatActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(ChatActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                }

            }
        });








    }



}


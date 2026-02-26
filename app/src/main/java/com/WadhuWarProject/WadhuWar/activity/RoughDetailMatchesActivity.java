//package com.WadhuWarProject.WadhuWar.activity;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.cardview.widget.CardView;
//import androidx.constraintlayout.solver.GoalRow;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.core.widget.NestedScrollView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.Color;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver;
//import android.view.Window;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupMenu;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.WadhuWarProject.WadhuWar.R;
//import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
//import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
//import com.WadhuWarProject.WadhuWar.api.Api;
//import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
//import com.WadhuWarProject.WadhuWar.model.FetchProfile;
//import com.WadhuWarProject.WadhuWar.model.InsertResponse;
//import com.WadhuWarProject.WadhuWar.model.UserData;
//import com.github.florent37.shapeofview.shapes.DiagonalView;
//import com.google.android.material.appbar.AppBarLayout;
//import com.google.android.material.snackbar.Snackbar;
//import com.squareup.picasso.Picasso;
//
//import java.io.Serializable;
//import java.text.NumberFormat;
//import java.text.ParseException;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DetailMachesActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener, PopupMenu.OnMenuItemClickListener {
//    Toolbar toolbar;
//    UserData user;
//
//    CardView top_cv;
//    int pref_count;
//    CircleImageView his_her_profile;
//    static boolean isNetworkAvailable;
//    private NetworkStateReceiver networkStateReceiver;
//    SwipeRefreshLayout swipeRefreshLayout;
//    AppBarLayout toolbar_appbar;
//
//    RelativeLayout age_pref_ll,marital_status_prefRL,height_RL,religion_caste_prefRL,mother_tongue_prefRL,state_livein_prefRL,diet_prefRL,
//            annual_income_prefRL,gotra_RL,countryRL,cityRL,educationRL,occupationRL,color_complex_pref_RL,caste_pref_RL,sub_caste_pref_RL,lifestyle_pref_RL,
//            sampraday_pref_RL;
//
//    TextView name,age_height,lang_caste,job,address,about_txt,acc_id,profile_created_for,about_desc,not_see_txt,
//            age_height_color,birthday_date,marital_status,address_lifestyle,job_lifestyle,diet,lang_status,caste_status,
//            state_Status,conversation_txt,bg_religion_lang,bg_caste,family,family_lbl,education,other_education,occupation,post_name,income,hobbies,
//            age_from_to_pref,marital_status_pref,religion_caste_pref,mother_tongue_pref,state_livein_pref,diet_pref,
//            annual_income_pref,height_pref,gotra,country,city,education_pref,occupation_pref,her_him_pref,match_count,
//            background_txt,education_career_txt,hobbies_txt,you_,img_count,job_top,address_top,color_complex_pref,caste_pref,sub_caste_pref,lifestyle_pref,
//            sampraday_pref,contact_no,whatsapp_no,email,work_location,current_address,permanant_address,gender,gothra,weight,
//            color_complexion,handicap,lifestyle,blood_group,pref_marital_status,pref_age,pref_education,swip_name_top;
//
//    ImageView user_img,connect_now_btn,about_lock,arrow1,arrow2,lifestyle_lock,lang_status_img,caste_status_img,state_Status_img,age_from_to,
//            height_pref_img,marital_status_pref_img,religion_caste_pref_img,mother_tongue_pref_img,diet_pref_img,
//            annual_income_pref_img,gotra_img,country_img,city_img,education_img,occupation_img,login_user_profile_image,state_livein_pref_img,
//            color_complex_pref_img,caste_pref_img,sub_caste_pref_img,lifestyle_pref_img,sampraday_pref_img,birthday_lock,marital_status_lock,diet_lock,
//            bg_religion_lang_lock,other_education_lock,occupation_lock,post_name_lock,hobbies_lock,contact_no_lock,whatsapp_no_lock,email_lock;
//
//    LinearLayout onlineLL,upgrade_now,state_Status_LL,caste_status_LL,lang_status_LL,bg_religion_lang_LL,bg_caste_LL,educationLL,other_educationLL,
//            occupationLL,post_nameLL,incomeLL,hobbiesLL,imgLL,bottom_like_connectLL,verified_LL,verified_txtLL,premiumLL,option_LL,option_LL1,go_premium_LL,
//            contact_detailLL,seeAllAddressLL,addressLL,seeAllfamilyLL,seeAllPrefLL,view_all_detail,lifestyle_appearance_LL,basic_block_LL,education_careerLL,
//            conversation_background_familyLL,partner_prefLL,partner_pref_compareLL,connect_now_topLL,contact_connect_now_btn_LL,contact_connect_now_btn,
//            contact_go_premium_now_btn_LL,contact_go_premium_now_btn,background_go_premium_now_btn_LL,background_go_premium_now_btn,
//            education_go_premium_now_btn_LL,education_go_premium_now_btn,hobbies_go_premium_now_btn_LL,hobbies_go_premium_now_btn,swip_topLL;
//
//    CardView conversation_cv,family_cv,background_cv,education_career_cv,hobbies_cv;
//    String premium_userid;
//
//    View age_pref_v,height_pref_v,marital_status_pref_v,religion_caste_pref_v,mother_tongue_pref_v,state_livein_pref_v,
//            diet_pref_v,annual_income_v,gotra_v,county_v,city_v,education_v,occupation_v,color_complex_pref_v,caste_pref_v,sub_caste_pref_v,lifestyle_pref_v,
//            sampraday_pref_v;
//
//    FetchProfile fetchProfile,fetchProfile1;
//    FetchProfile fetchProfileLoginUser;
//
//    static RelativeLayout internetOffRL;
//    static ProgressBar simpleProgressBar;
//    static TextView try_again_txt,couldnt_reach_internet_txt;
//
//    NestedScrollView sv_feature;
//    ImageView back_btn;
//    ProgressDialog progressBar;
//    Window window;
//    FrameLayout mainFL;
//    DiagonalView justjoinL;
//    InsertResponse addShortList;
//    InsertResponse removeShortList;
//    Animation animBounce;
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_maches);
//
//        swip_topLL =  findViewById(R.id.swip_topLL);
//        swip_name_top =  findViewById(R.id.swip_name_top);
//        hobbies_go_premium_now_btn =  findViewById(R.id.hobbies_go_premium_now_btn);
//        hobbies_go_premium_now_btn_LL =  findViewById(R.id.hobbies_go_premium_now_btn_LL);
//        education_go_premium_now_btn =  findViewById(R.id.education_go_premium_now_btn);
//        education_go_premium_now_btn_LL =  findViewById(R.id.education_go_premium_now_btn_LL);
//        background_go_premium_now_btn =  findViewById(R.id.background_go_premium_now_btn);
//        background_go_premium_now_btn_LL =  findViewById(R.id.background_go_premium_now_btn_LL);
//        contact_go_premium_now_btn =  findViewById(R.id.contact_go_premium_now_btn);
//        contact_go_premium_now_btn_LL =  findViewById(R.id.contact_go_premium_now_btn_LL);
//        contact_connect_now_btn =  findViewById(R.id.contact_connect_now_btn);
//        contact_connect_now_btn_LL =  findViewById(R.id.contact_connect_now_btn_LL);
//        email_lock =  findViewById(R.id.email_lock);
//        whatsapp_no_lock =  findViewById(R.id.whatsapp_no_lock);
//        contact_no_lock =  findViewById(R.id.contact_no_lock);
//        connect_now_topLL =  findViewById(R.id.connect_now_topLL);
//        partner_pref_compareLL =  findViewById(R.id.partner_pref_compareLL);
//        partner_prefLL =  findViewById(R.id.partner_prefLL);
//        education_careerLL =  findViewById(R.id.education_careerLL);
//        conversation_background_familyLL =  findViewById(R.id.conversation_background_familyLL);
//        basic_block_LL =  findViewById(R.id.basic_block_LL);
//        lifestyle_appearance_LL =  findViewById(R.id.lifestyle_appearance_LL);
//        view_all_detail =  findViewById(R.id.view_all_detail);
//        seeAllPrefLL =  findViewById(R.id.seeAllPrefLL);
//        pref_education =  findViewById(R.id.pref_education);
//        pref_marital_status =  findViewById(R.id.pref_marital_status);
//        pref_age =  findViewById(R.id.pref_age);
//        seeAllfamilyLL =  findViewById(R.id.seeAllfamilyLL);
//        handicap =  findViewById(R.id.handicap);
//        color_complexion =  findViewById(R.id.color_complexion);
//        blood_group =  findViewById(R.id.blood_group);
//        lifestyle =  findViewById(R.id.lifestyle);
//        weight =  findViewById(R.id.weight);
//        gothra =  findViewById(R.id.gothra);
//        gender =  findViewById(R.id.gender);
//        addressLL =  findViewById(R.id.addressLL);
//        permanant_address =  findViewById(R.id.permanant_address);
//        current_address =  findViewById(R.id.current_address);
//        work_location =  findViewById(R.id.work_location);
//        seeAllAddressLL =  findViewById(R.id.seeAllAddressLL);
//        contact_detailLL =  findViewById(R.id.contact_detailLL);
//        email =  findViewById(R.id.email);
//        whatsapp_no =  findViewById(R.id.whatsapp_no);
//        contact_no =  findViewById(R.id.contact_no);
//        go_premium_LL =  findViewById(R.id.go_premium_LL);
//        hobbies_lock =  findViewById(R.id.hobbies_lock);
//        post_name_lock =  findViewById(R.id.post_name_lock);
//        occupation_lock =  findViewById(R.id.occupation_lock);
//        other_education_lock =  findViewById(R.id.other_education_lock);
//        bg_religion_lang_lock =  findViewById(R.id.bg_religion_lang_lock);
//        diet_lock =  findViewById(R.id.diet_lock);
//        marital_status_lock =  findViewById(R.id.marital_status_lock);
//        birthday_lock =  findViewById(R.id.birthday_lock);
//        sampraday_pref_v =  findViewById(R.id.sampraday_pref_v);
//        sampraday_pref_img =  findViewById(R.id.sampraday_pref_img);
//        sampraday_pref =  findViewById(R.id.sampraday_pref);
//        sampraday_pref_RL =  findViewById(R.id.sampraday_pref_RL);
//        lifestyle_pref_v =  findViewById(R.id.lifestyle_pref_v);
//        lifestyle_pref_img =  findViewById(R.id.lifestyle_pref_img);
//        lifestyle_pref =  findViewById(R.id.lifestyle_pref);
//        lifestyle_pref_RL =  findViewById(R.id.lifestyle_pref_RL);
//        sub_caste_pref_RL =  findViewById(R.id.sub_caste_pref_RL);
//        sub_caste_pref =  findViewById(R.id.sub_caste_pref);
//        sub_caste_pref_img =  findViewById(R.id.sub_caste_pref_img);
//        sub_caste_pref_v =  findViewById(R.id.sub_caste_pref_v);
//        caste_pref_v =  findViewById(R.id.caste_pref_v);
//        caste_pref_img =  findViewById(R.id.caste_pref_img);
//        caste_pref =  findViewById(R.id.caste_pref);
//        caste_pref_RL =  findViewById(R.id.caste_pref_RL);
//        color_complex_pref_v =  findViewById(R.id.color_complex_pref_v);
//        color_complex_pref_img =  findViewById(R.id.color_complex_pref_img);
//        color_complex_pref =  findViewById(R.id.color_complex_pref);
//        color_complex_pref_RL =  findViewById(R.id.color_complex_pref_RL);
//        option_LL =  findViewById(R.id.option_LL);
//        option_LL1 =  findViewById(R.id.option_LL1);
//        justjoinL =  findViewById(R.id.justjoinL);
//        premiumLL =  findViewById(R.id.premiumLL);
//        verified_txtLL =  findViewById(R.id.verified_txtLL);
//        verified_LL =  findViewById(R.id.verified_LL);
//        bottom_like_connectLL =  findViewById(R.id.bottom_like_connectLL);
//        sv_feature =  findViewById(R.id.sv_feature);
//        hobbies_txt =  findViewById(R.id.hobbies_txt);
//        toolbar =  findViewById(R.id.toolbar);
//        top_cv =  findViewById(R.id.top_cv);
//        user_img =  findViewById(R.id.user_img);
//        onlineLL =  findViewById(R.id.onlineLL);
//        name =  findViewById(R.id.name);
//        age_height =  findViewById(R.id.age_height);
//        lang_caste =  findViewById(R.id.lang_caste);
//        job =  findViewById(R.id.job);
//        job_top =  findViewById(R.id.job_top);
//        address =  findViewById(R.id.address);
//        address_top =  findViewById(R.id.address_top);
//        connect_now_btn =  findViewById(R.id.connect_now_btn);
//        about_txt =  findViewById(R.id.about_txt);
//        about_lock =  findViewById(R.id.about_lock);
//        acc_id =  findViewById(R.id.acc_id);
//        profile_created_for =  findViewById(R.id.profile_created_for);
//        about_desc =  findViewById(R.id.about_desc);
//        arrow1=  findViewById(R.id.arrow1);
//        arrow2=  findViewById(R.id.arrow2);
//        upgrade_now=  findViewById(R.id.upgrade_now);
//        not_see_txt=  findViewById(R.id.not_see_txt);
//        age_height_color=  findViewById(R.id.age_height_color);
//        birthday_date=  findViewById(R.id.birthday_date);
//        marital_status =  findViewById(R.id.marital_status);
//        address_lifestyle =  findViewById(R.id.address_lifestyle);
//        job_lifestyle =  findViewById(R.id.job_lifestyle);
//        diet =  findViewById(R.id.diet);
//        lang_status =  findViewById(R.id.lang_status);
//        caste_status =  findViewById(R.id.caste_status);
//        state_Status =  findViewById(R.id.state_Status);
//        conversation_txt =  findViewById(R.id.conversation_txt);
//        conversation_cv =  findViewById(R.id.conversation_cv);
//        lang_status_img =  findViewById(R.id.lang_status_img);
//        caste_status_img =  findViewById(R.id.caste_status_img);
//        state_Status_img =  findViewById(R.id.state_Status_img);
//        state_Status_LL =  findViewById(R.id.state_Status_LL);
//        caste_status_LL =  findViewById(R.id.caste_status_LL);
//        lang_status_LL =  findViewById(R.id.lang_status_LL);
//        bg_religion_lang =  findViewById(R.id.bg_religion_lang);
//        bg_caste =  findViewById(R.id.bg_caste);
//        bg_religion_lang_LL =  findViewById(R.id.bg_religion_lang_LL);
//        bg_caste_LL =  findViewById(R.id.bg_caste_LL);
//        family =  findViewById(R.id.family);
//        family_lbl =  findViewById(R.id.family_lbl);
//        family_cv =  findViewById(R.id.family_cv);
//        education =  findViewById(R.id.education);
//        other_education =  findViewById(R.id.other_education);
//        occupation =  findViewById(R.id.occupation);
//        post_name =  findViewById(R.id.post_name);
//        income =  findViewById(R.id.income);
//        educationLL =  findViewById(R.id.educationLL);
//        other_educationLL =  findViewById(R.id.other_educationLL);
//        occupationLL =  findViewById(R.id.occupationLL);
//        post_nameLL =  findViewById(R.id.post_nameLL);
//        incomeLL =  findViewById(R.id.incomeLL);
//        age_from_to_pref =  findViewById(R.id.age_from_to_pref);
//        marital_status_pref =  findViewById(R.id.marital_status_pref);
//        religion_caste_pref =  findViewById(R.id.religion_caste_pref);
//        mother_tongue_pref =  findViewById(R.id.mother_tongue_pref);
//        state_livein_pref =  findViewById(R.id.state_livein_pref);
//        diet_pref =  findViewById(R.id.diet_pref);
//        annual_income_pref =  findViewById(R.id.annual_income_pref);
//        age_from_to =  findViewById(R.id.age_from_to);
//        marital_status_prefRL =  findViewById(R.id.marital_status_prefRL);
//        height_RL =  findViewById(R.id.height_RL);
//        height_pref =  findViewById(R.id.height_pref);
//        height_pref_img =  findViewById(R.id.height_pref_img);
//        marital_status_prefRL =  findViewById(R.id.marital_status_prefRL);
//        marital_status_pref =  findViewById(R.id.marital_status_pref);
//        marital_status_pref_img =  findViewById(R.id.marital_status_pref_img);
//        religion_caste_prefRL =  findViewById(R.id.religion_caste_prefRL);
//        religion_caste_pref_img =  findViewById(R.id.religion_caste_pref_img);
//        mother_tongue_prefRL =  findViewById(R.id.mother_tongue_prefRL);
//        mother_tongue_pref =  findViewById(R.id.mother_tongue_pref);
//        mother_tongue_pref_img =  findViewById(R.id.mother_tongue_pref_img);
//        state_livein_prefRL =  findViewById(R.id.state_livein_prefRL);
//        diet_prefRL =  findViewById(R.id.diet_prefRL);
//        diet_pref =  findViewById(R.id.diet_pref);
//        diet_pref_img =  findViewById(R.id.diet_pref_img);
//        annual_income_prefRL =  findViewById(R.id.annual_income_prefRL);
//        annual_income_pref =  findViewById(R.id.annual_income_pref);
//        annual_income_pref_img =  findViewById(R.id.annual_income_pref_img);
//        hobbiesLL =  findViewById(R.id.hobbiesLL);
//        hobbies =  findViewById(R.id.hobbies);
//        age_pref_ll =  findViewById(R.id.age_pref_ll);
//        age_pref_v =  findViewById(R.id.age_pref_v);
//        height_pref_v =  findViewById(R.id.height_pref_v);
//        marital_status_pref_v =  findViewById(R.id.marital_status_pref_v);
//        religion_caste_pref_v =  findViewById(R.id.religion_caste_pref_v);
//        mother_tongue_pref_v =  findViewById(R.id.mother_tongue_pref_v);
//        state_livein_pref_v =  findViewById(R.id.state_livein_pref_v);
//        diet_pref_v =  findViewById(R.id.diet_pref_v);
//        annual_income_v =  findViewById(R.id.annual_income_v);
//        gotra_v =  findViewById(R.id.gotra_v);
//        gotra =  findViewById(R.id.gotra);
//        gotra_RL =  findViewById(R.id.gotra_RL);
//        gotra_img =  findViewById(R.id.gotra_img);
//        country_img =  findViewById(R.id.country_img);
//        country =  findViewById(R.id.country);
//        countryRL =  findViewById(R.id.countryRL);
//        county_v =  findViewById(R.id.county_v);
//        city_v =  findViewById(R.id.city_v);
//        city_img =  findViewById(R.id.city_img);
//        city =  findViewById(R.id.city);
//        cityRL =  findViewById(R.id.cityRL);
//        educationRL =  findViewById(R.id.educationRL);
//        education_pref =  findViewById(R.id.education_pref);
//        education_img =  findViewById(R.id.education_img);
//        education_v =  findViewById(R.id.education_v);
//        occupation_v =  findViewById(R.id.occupation_v);
//        occupation_img =  findViewById(R.id.occupation_img);
//        occupation_pref =  findViewById(R.id.occupation_pref);
//        occupationRL =  findViewById(R.id.occupationRL);
//        her_him_pref =  findViewById(R.id.her_him_pref);
//        his_her_profile =  findViewById(R.id.his_her_profile);
//        login_user_profile_image =  findViewById(R.id.login_user_profile_image);
//        match_count =  findViewById(R.id.match_count);
//        state_livein_pref_img =  findViewById(R.id.state_livein_pref_img);
//        background_cv =  findViewById(R.id.background_cv);
//        background_txt =  findViewById(R.id.background_txt);
//        education_career_txt =  findViewById(R.id.education_career_txt);
//        education_career_cv =  findViewById(R.id.education_career_cv);
//        hobbies_cv =  findViewById(R.id.hobbies_cv);
//        you_ =  findViewById(R.id.you_);
//        img_count =  findViewById(R.id.img_count);
//        imgLL =  findViewById(R.id.imgLL);
//        back_btn =  findViewById(R.id.back_btn);
//
//        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
//        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
//
//        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
//        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
//
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
////        getSupportActionBar().setTitle("");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onBackPressed();
//
//            }
//        });
//
//
//
//
//
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        Intent i = getIntent();
//        if(i.getStringExtra("userid")!=null){
//            premium_userid = i.getStringExtra("userid");
//        }
//
//        user = SharedPrefManager.getInstance(DetailMachesActivity.this).getUser();
//
//
//        /*if net off*/
//        networkStateReceiver = new NetworkStateReceiver();
//        networkStateReceiver.addListener(this);
//        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        /*end code if net off*/
//
//
//
//
//
//        /*toolbae code*/
//       /* toolbar_appbar = (AppBarLayout) findViewById(R.id.toolbar_appbar);
//
//
//        toolbar_appbar.setVisibility(View.INVISIBLE);
//        toolbar.setVisibility(View.INVISIBLE);
//         getSupportActionBar().setShowHideAnimationEnabled(false);
//
//        window = getWindow();
//
//        sv_feature.smoothScrollTo(0,0);
//        sv_feature.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @SuppressLint("RestrictedApi")
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onScrollChanged() {
//
//                if(sv_feature.getScrollY() > 1100){
//
//                    toolbar_appbar.setVisibility(View.VISIBLE);
//                    toolbar.setVisibility(View.VISIBLE);
//
//
//                   getSupportActionBar().show();
//
////                    window.setStatusBarColor(Color.parseColor("#EC5252"));
//                    getSupportActionBar().setShowHideAnimationEnabled(false);
//
//
//                }
//                else{
//                    toolbar_appbar.setVisibility(View.GONE);
//                    toolbar.setVisibility(View.GONE);
//
//                   getSupportActionBar().setShowHideAnimationEnabled(false);
//
//                }
//
//
//
//            }
//        });*/
//        /*end toolbae code*/
//
//
//
//
//    }
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    public void networkAvailable() {
//
//        fetchLoginProfileData();
//
//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                fetchLoginProfileData();
//
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//
//        final Handler handler = new Handler();
//
//        Runnable delayrunnable = new Runnable() {
//
//            @Override
//            public void run() {
//
//                internetOffRL.setVisibility(View.GONE);
//                couldnt_reach_internet_txt.setVisibility(View.GONE);
//
//            }
//        };
//        handler.postDelayed(delayrunnable, 3000);
//
//
//
//
//    }
//
//    @Override
//    public void networkUnavailable() {
//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Toast.makeText(DetailMachesActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();
//
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//        internetOffRL.setVisibility(View.VISIBLE);
//
//        try_again_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try_again_txt.setVisibility(View.GONE);
//                simpleProgressBar.setVisibility(View.VISIBLE);
//                if(!isNetworkAvailable()){
//                    simpleProgressBar.postDelayed(new Runnable() {
//                        public void run() {
//                            couldnt_reach_internet_txt.postDelayed(new Runnable() {
//                                public void run() {
//                                    couldnt_reach_internet_txt.setVisibility(View.VISIBLE);
//                                    try_again_txt.setVisibility(View.VISIBLE);
//                                    simpleProgressBar.setVisibility(View.GONE);
//                                    couldnt_reach_internet_txt.postDelayed(new Runnable() {
//                                        public void run() {
//                                            couldnt_reach_internet_txt.setVisibility(View.GONE);
//
//
//                                        }
//                                    }, 2000);
//                                }
//                            }, 2000);
//
//                        }
//                    }, 2000);
//                }
//                else{
//                    networkUnavailable();
//                }
//
//            }
//        });
//    }
//
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.add_to_shortlist:
//
//                Api apiService = RetrofitClient.getApiService();
//                Call<InsertResponse> userResponse = apiService.shortListProfile(String.valueOf(user.getUser_id()),fetchProfile1.getUser_id());
//                userResponse.enqueue(new Callback<InsertResponse>() {
//
//                    @Override
//                    public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
//                        addShortList = response.body();
//
//                        if (response.isSuccessful()) {
//                            if(addShortList.getResid().equals("200")){
//                                Toast.makeText(DetailMachesActivity.this,addShortList.getResMsg(),Toast.LENGTH_SHORT).show();
//
//                                fetchProfileDataforShortlistUpdate();
//                            }else{
//                                Toast.makeText(DetailMachesActivity.this,addShortList.getResMsg(),Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<InsertResponse> call, Throwable t) {
//                        System.out.println("msg1 my add shortlist******" + t.toString());
//                        progressBar.dismiss();
//
//                        if(!isNetworkAvailable(DetailMachesActivity.this)){
//                            Toast.makeText(DetailMachesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                        }else{
//                            Toast.makeText(DetailMachesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//                });
//
//
//
//                return true;
//
//            case R.id.remove_from_shortlist:
//
//                Api apiService1 = RetrofitClient.getApiService();
//                Call<InsertResponse> userResponse1 = apiService1.removeShortListProfile(String.valueOf(user.getUser_id()),fetchProfile1.getUser_id());
//                userResponse1.enqueue(new Callback<InsertResponse>() {
//
//                    @Override
//                    public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
//                        removeShortList = response.body();
//
//                        if (response.isSuccessful()) {
//                            if(removeShortList.getResid().equals("200")){
//                                Toast.makeText(DetailMachesActivity.this,removeShortList.getResMsg(),Toast.LENGTH_SHORT).show();
//
//                                fetchProfileDataforShortlistUpdate();
//                            }else{
//                                Toast.makeText(DetailMachesActivity.this,removeShortList.getResMsg(),Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<InsertResponse> call, Throwable t) {
//                        System.out.println("msg1 my add removeShortList******" + t.toString());
//                        progressBar.dismiss();
//
//                        if(!isNetworkAvailable(DetailMachesActivity.this)){
//                            Toast.makeText(DetailMachesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                        }else{
//                            Toast.makeText(DetailMachesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//                });
//                return true;
//
//            default:
//                return false;
//        }
//    }
//
//
//
//
//    public void showAllDetail(FetchProfile fetchProfileofUsers){
//        go_premium_LL.setVisibility(View.GONE);
//
//        /*contact*/
//
//        if(fetchProfileofUsers.getIs_connected().equals("1")){
//            contact_no_lock.setVisibility(View.GONE);
//            contact_no.setText(fetchProfileofUsers.getMobile_no());
//
//            whatsapp_no_lock.setVisibility(View.GONE);
//            whatsapp_no.setText(fetchProfileofUsers.getWtsapp_no());
//
//            email_lock.setVisibility(View.GONE);
//            email.setText(fetchProfileofUsers.getEmail());
//
//            contact_connect_now_btn_LL.setVisibility(View.GONE);
//
//        }else {
//            contact_no_lock.setVisibility(View.VISIBLE);
//            contact_no.setText("******");
//
//            whatsapp_no_lock.setVisibility(View.VISIBLE);
//            whatsapp_no.setText("******");
//
//            email_lock.setVisibility(View.VISIBLE);
//            email.setText("******");
//
//
//
//            contact_connect_now_btn_LL.setVisibility(View.VISIBLE);
//            contact_connect_now_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(DetailMachesActivity.this,"Connect Now Click ",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//
//
//
//
//
//
//        /*end contact*/
//
//        /*address*/
//
//        permanant_address.setText(fetchProfileofUsers.getPer_address());
//        current_address.setText(fetchProfileofUsers.getCurrent_address());
//        work_location.setText(fetchProfileofUsers.getOfc_loc());
//        seeAllAddressLL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(DetailMachesActivity.this,AddressInDetailActivity.class);
//                i.putExtra("userdata",fetchProfileofUsers);
//                startActivity(i);
//            }
//        });
//        /*address*/
//
//        /*basic detail*/
//        gender.setText(fetchProfileofUsers.getGender());
//        gothra.setText("Gothra- "+fetchProfileofUsers.getGotra());
//        weight.setText("Weight- "+fetchProfileofUsers.getWeight());
//        lifestyle.setText("Addiction- "+fetchProfileofUsers.getLifestyle_name());
//        blood_group.setText(fetchProfileofUsers.getBloodgroup_name());
//        color_complexion.setText(fetchProfileofUsers.getColor_complex_name());
//        if(fetchProfileofUsers.getHandicap().equals("No")){
//            handicap.setText(fetchProfileofUsers.getHandicap());
//
//        }else {
//            if(!fetchProfileofUsers.getHandicap_name().equals("Not Specified") || !fetchProfileofUsers.getHandicap_name().trim().equals("") )
//                handicap.setText(fetchProfileofUsers.getHandicap() + "  (" + fetchProfileofUsers.getHandicap_name() +")");
//            else
//                handicap.setText(fetchProfileofUsers.getHandicap());
//
//        }
//        /*basic detail*/
//
//        /*family*/
//        seeAllfamilyLL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(DetailMachesActivity.this,FamilyInDetailActivity.class);
//                i.putExtra("userdata",fetchProfileofUsers);
//                startActivity(i);
//            }
//        });
//        /*family*/
//
//        /*partner pref*/
//        pref_marital_status.setText(fetchProfileofUsers.getPref_marital_name());
//        if(!fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && !fetchProfileofUsers.getPref_ageto().equals("Not Specified") ){
//            if(!fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") && !fetchProfileofUsers.getPref_ageto().equals("Doesn't Matter") ){
//                pref_age.setText(fetchProfileofUsers.getPref_agefrom() + " to " +  fetchProfileofUsers.getPref_ageto());
//            }else if(fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") && !fetchProfileofUsers.getPref_ageto().equals("Doesn't Matter")){
//                pref_age.setText("Upto " +  fetchProfileofUsers.getPref_ageto());
//
//            }else if(!fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") ){
//                pref_age.setText("from " +  fetchProfileofUsers.getPref_agefrom());
//
//            }
//
//
//        }else{
//            if(!fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && fetchProfileofUsers.getPref_ageto().equals("Not Specified") ){
//                pref_age.setText(fetchProfileofUsers.getPref_agefrom());
//
//            }
//
//            if(fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && !fetchProfileofUsers.getPref_ageto().equals("Not Specified") ){
//                pref_age.setText(fetchProfileofUsers.getPref_ageto());
//
//            }
//        }
//
//        pref_education.setText(fetchProfileofUsers.getEducation_name());
//
//        seeAllPrefLL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(DetailMachesActivity.this,PartnerPrefInDetailActivity.class);
//                i.putExtra("userdata",fetchProfileofUsers);
//                startActivity(i);
//            }
//        });
//        /*partner pref*/
//
//
//
//    }
//
//
//    public  void fetchProfileData(){
//
//        System.out.println("login userid=====" +user.getUser_id());
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<FetchProfile> userResponse = apiService.peopleDetails(premium_userid,String.valueOf(user.getUser_id()));
//        userResponse.enqueue(new Callback<FetchProfile>() {
//
//            @Override
//            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
//                fetchProfile = response.body();
//
//                if(fetchProfile!=null){
//
//                    if (response.isSuccessful()) {
//
//
//
//
//                        /*=============*/
//
//
//                        if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
//                            view_all_detail.setVisibility(View.VISIBLE);
//
//
//                            view_all_detail.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    view_all_detail.setVisibility(View.GONE);
//                                    contact_detailLL.setVisibility(View.VISIBLE);
//                                    go_premium_LL.setVisibility(View.GONE);
//                                    lifestyle_appearance_LL.setVisibility(View.VISIBLE);
//                                    basic_block_LL.setVisibility(View.VISIBLE);
//                                    conversation_background_familyLL.setVisibility(View.VISIBLE);
//                                    seeAllfamilyLL.setVisibility(View.VISIBLE);
//                                    education_careerLL.setVisibility(View.VISIBLE);
//                                    partner_prefLL.setVisibility(View.VISIBLE);
//                                    addressLL.setVisibility(View.VISIBLE);
//                                    partner_pref_compareLL.setVisibility(View.VISIBLE);
//
//                                }
//                            });
//                        } else {
//
//                            view_all_detail.setVisibility(View.GONE);
//                            contact_detailLL.setVisibility(View.GONE);
//                            go_premium_LL.setVisibility(View.VISIBLE);
//                            lifestyle_appearance_LL.setVisibility(View.VISIBLE);
//                            basic_block_LL.setVisibility(View.GONE);
//                            conversation_background_familyLL.setVisibility(View.VISIBLE);
//                            seeAllfamilyLL.setVisibility(View.GONE);
//                            education_careerLL.setVisibility(View.VISIBLE);
//                            partner_prefLL.setVisibility(View.GONE);
//                            addressLL.setVisibility(View.GONE);
//                            partner_pref_compareLL.setVisibility(View.VISIBLE);
//
//                        }
//
//                        if (fetchProfile.getIs_connected().equals("1")) {
//                            connect_now_topLL.setVisibility(View.GONE);
//                            bottom_like_connectLL.setVisibility(View.GONE);
//
//
//                        } else {
//                            connect_now_topLL.setVisibility(View.VISIBLE);
//
//                            sv_feature.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                                @SuppressLint("RestrictedApi")
//                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                                @Override
//                                public void onScrollChanged() {
//
//                                    if (sv_feature.getScrollY() > 1500) {
//
//                                        bottom_like_connectLL.setVisibility(View.VISIBLE);
//                                   /* getSupportActionBar().show();
//                                    getSupportActionBar().setShowHideAnimationEnabled(false);*/
//
//
//                                    } else {
//                                        bottom_like_connectLL.setVisibility(View.GONE);
//
//                                        /*getSupportActionBar().setShowHideAnimationEnabled(false);*/
//
//                                    }
//
//
//                                }
//                            });
//
//                        }
//
//
//                        sv_feature.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                            @SuppressLint("RestrictedApi")
//                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                            @Override
//                            public void onScrollChanged() {
//
//                           /* animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
//                                    R.anim.bounce);
//                            swip_topLL.startAnimation(animBounce);*/
//                                if (sv_feature.getScrollY() > 1600) {
//
//                                    swip_topLL.setVisibility(View.VISIBLE);
//                                    swip_name_top.setText(fetchProfile.getFname());
//
//
//                                } else {
//                                    swip_topLL.setVisibility(View.GONE);
//
//                                }
//
//                            }
//                        });
//
//                        /*=============*/
//
//
//                        System.out.println("user id=====" + fetchProfile.getUser_id());
//
//                        if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
//                            showAllDetail(fetchProfile);
//                        }
//
//
//                        if (fetchProfile.getImages() != null) {
//                            if (fetchProfile.getImages().size() != 0) {
//                                Glide.with(DetailMachesActivity.this).load(fetchProfile.getImages().get(0).getImgs()).into(user_img);
//                            } else {
//                                Glide.with(DetailMachesActivity.this).load(R.drawable.default_avtar_2).into(user_img);
//
//                            }
//                        } else {
//                            Glide.with(DetailMachesActivity.this).load(R.drawable.default_avtar_2).into(user_img);
//                        }
//
//                        name.setText(fetchProfile.getFname());
//
//                        if (fetchProfile.getPremium().equals("1")) {
//                            premiumLL.setVisibility(View.VISIBLE);
//                        } else {
//                            premiumLL.setVisibility(View.GONE);
//
//                        }
//
//                        if (fetchProfile.getAcc_verify().equals("1")) {
//                            verified_LL.setVisibility(View.VISIBLE);
//                        } else {
//                            verified_LL.setVisibility(View.GONE);
//                        }
//
//                        if (fetchProfile.getJust_joined().equals("1")) {
//                            justjoinL.setVisibility(View.VISIBLE);
//                        } else {
//                            justjoinL.setVisibility(View.GONE);
//                        }
//
//
//                        verified_LL.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                verified_txtLL.setVisibility(View.VISIBLE);
//                            }
//                        });
//
//                        getSupportActionBar().setTitle(fetchProfile.getFname());
//                        toolbar.setTitleTextColor(Color.WHITE);
//
//                        if (!fetchProfile.getAge().contentEquals("Not Specified") &&
//                                !fetchProfile.getHeight().contentEquals("Not Specified")) {
//                            age_height.setText(String.format("%s yrs,%s", fetchProfile.getAge(), fetchProfile.getHeight()));
//
//                        } else if (!fetchProfile.getAge().contentEquals("Not Specified") &&
//                                fetchProfile.getHeight().contentEquals("Not Specified")) {
//                            age_height.setText(fetchProfile.getAge() + " yrs");
//
//                        } else if (fetchProfile.getAge().contentEquals("Not Specified") &&
//                                !fetchProfile.getHeight().contentEquals("Not Specified")) {
//                            age_height.setText(fetchProfile.getHeight());
//
//                        } else {
//                            age_height.setText(" ");
//
//                        }
//
//
//                        if (!fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
//                                !fetchProfile.getCaste_name().contentEquals("Not Specified")) {
//                            lang_caste.setText(String.format("%s,%s", fetchProfile.getMothertounge_name(), fetchProfile.getCaste_name()));
//
//                        } else if (!fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
//                                fetchProfile.getCaste_name().contentEquals("Not Specified")) {
//                            lang_caste.setText(fetchProfile.getMothertounge_name());
//
//                        } else if (fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
//                                !fetchProfile.getCaste_name().contentEquals("Not Specified")) {
//                            lang_caste.setText(fetchProfile.getCaste_name());
//
//                        } else {
//                            lang_caste.setText("");
//
//                        }
//
//
//                        System.out.println("fetchProfile.getOccupation_name()-------" + fetchProfile.getOccupation_name());
//
//                        if (!fetchProfile.getOccupation_name().contentEquals("Not Specified")) {
//                            job_top.setText(fetchProfile.getOccupation_name());
//
//                        } else {
//                            job_top.setText("");
//
//                        }
//
//                        if (!fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
//                                !fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
//                            address_top.setText(String.format("%s,%s", fetchProfile.getCurr_ditrict_name(), fetchProfile.getCurr_state_name()));
//
//                        } else if (!fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
//                                fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
//                            address_top.setText(fetchProfile.getCurr_ditrict_name());
//
//                        } else if (fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
//                                !fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
//                            address_top.setText(fetchProfile.getCurr_state_name());
//
//                        } else {
//                            address_top.setText("");
//
//                        }
//
//
//                        if (fetchProfile.getChkonline().equals("1")) {
//                            onlineLL.setVisibility(View.VISIBLE);
//                        } else {
//                            onlineLL.setVisibility(View.GONE);
//                        }
//
//                        /*top layout*/
//
//                        /*about layout*/
//                        if (fetchProfileLoginUser.getPremium().equals("0") || !fetchProfile.getIs_connected().equals("1")) {
//                            about_lock.setVisibility(View.VISIBLE);
//                            about_txt.setText("About " + fetchProfile.getFname());
//
//                        } else {
//                            about_lock.setVisibility(View.GONE);
//                            about_txt.setText(fetchProfile.getFname() + " " + fetchProfile.getMname() + " " + fetchProfile.getLname());
//
//                        }
//
//                        acc_id.setText(fetchProfile.getAcc_id());
//
//                        if (fetchProfile.getProfile_name().equals("Not Specified")) {
//                            profile_created_for.setVisibility(View.GONE);
//                        } else {
//                            profile_created_for.setVisibility(View.VISIBLE);
//
//                            profile_created_for.setText("| Profile Created for " + fetchProfile.getProfile_name());
//
//                        }
//
//
//                        about_desc.setText("Hello, here are a few lines to help you know me better.\n" + fetchProfile.getAbout_me());
//
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
//                        about_desc.setLayoutParams(params);
//
//                        arrow1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                arrow1.setVisibility(View.GONE);
//                                arrow2.setVisibility(View.VISIBLE);
//
//                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                about_desc.setLayoutParams(params);
//
//
//                            }
//                        });
//
//                        arrow2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                arrow1.setVisibility(View.VISIBLE);
//                                arrow2.setVisibility(View.GONE);
//                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
//                                about_desc.setLayoutParams(params);
//                            }
//                        });
//                        /*about layout*/
//
//                        /*view full profile layout*/
//                        upgrade_now.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                Intent i = new Intent(DetailMachesActivity.this, PremiumActivity.class);
//                                startActivity(i);
//                            }
//                        });
//
//                        not_see_txt.setText(String.format("You will not see information that %s has chosen to hide.", fetchProfile.getFname()));
//                        /*end view full profile layout*/
//
//
//                        /*lifestyle appearance*/
//                        age_height_color.setText(String.format("%s yrs, %s", fetchProfile.getAge(), fetchProfile.getHeight()));
//
//                        if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
//                            birthday_date.setText("Born on " + fetchProfile.getDob());
//                            birthday_lock.setVisibility(View.GONE);
//
//                            marital_status.setText(fetchProfile.getMarital_status_name());
//                            marital_status_lock.setVisibility(View.GONE);
//
//                            diet.setText(fetchProfile.getDietry_name());
//                            diet_lock.setVisibility(View.GONE);
//
//                            contact_go_premium_now_btn_LL.setVisibility(View.GONE);
//
//
//                        } else {
//                            birthday_date.setText("Born on **/**/****");
//                            birthday_lock.setVisibility(View.VISIBLE);
//
//                            marital_status.setText("Marital status ******");
//                            marital_status_lock.setVisibility(View.VISIBLE);
//
//                            diet.setText("Diet ******");
//                            diet_lock.setVisibility(View.VISIBLE);
//
//
//                            contact_go_premium_now_btn_LL.setVisibility(View.VISIBLE);
//                            contact_go_premium_now_btn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent i = new Intent(DetailMachesActivity.this, PremiumActivity.class);
//                                    startActivity(i);
//                                }
//                            });
//
//
//                        }
//
//                        address_lifestyle.setText(String.format("Lives in %s, %s, %s", fetchProfile.getCurr_ditrict_name(), fetchProfile.getCurr_state_name(), fetchProfile.getCurr_country_name()));
//
//                        job_lifestyle.setText(fetchProfile.getOccupation_name());
//                        /*end lifestyle appearance*/
//
//                        /*conversation Starters*/
//                        if ((!fetchProfileLoginUser.getMothertounge_name().equals(fetchProfile.getMothertounge_name())) &&
//                                (!fetchProfileLoginUser.getCaste_name().equals(fetchProfile.getCaste_name())) &&
//                                (!fetchProfileLoginUser.getCurr_state_name().equals(fetchProfile.getCurr_state_name()))) {
//                            conversation_cv.setVisibility(View.GONE);
//                            conversation_txt.setVisibility(View.GONE);
//                        } else {
//                            conversation_cv.setVisibility(View.VISIBLE);
//                            conversation_txt.setVisibility(View.VISIBLE);
//                        }
//
//
//                        if (fetchProfileLoginUser.getMothertounge_name().equals(fetchProfile.getMothertounge_name())) {
//                            lang_status_LL.setVisibility(View.VISIBLE);
//                            lang_status.setText("You both are from the " + fetchProfile.getMothertounge_name() + " community");
//
//                        } else {
//                            lang_status_LL.setVisibility(View.GONE);
//                        }
//
//
//                        if (fetchProfileLoginUser.getCaste_name().equals(fetchProfile.getCaste_name())) {
//                            caste_status_LL.setVisibility(View.VISIBLE);
//                            caste_status.setText("You both are from the " + fetchProfile.getCaste_name() + " caste");
//
//
//                        } else {
//                            caste_status_LL.setVisibility(View.GONE);
//
//                        }
//
//
//                        if (fetchProfile.getGender().equals("Male")) {
//                            if (fetchProfileLoginUser.getCurr_state_name().equals(fetchProfile.getCurr_state_name())) {
//                                state_Status_LL.setVisibility(View.VISIBLE);
//                                state_Status.setText("He lives in " + fetchProfile.getCurr_state_name() + " too");
//
//                            } else {
//                                state_Status_LL.setVisibility(View.GONE);
//
//                            }
//                        } else if (fetchProfile.getGender().equals("Female")) {
//                            if (fetchProfileLoginUser.getCurr_state_name().equals(fetchProfile.getCurr_state_name())) {
//                                state_Status_LL.setVisibility(View.VISIBLE);
//                                state_Status.setText("She lives in " + fetchProfile.getCurr_state_name() + " too");
//
//                            } else {
//                                state_Status_LL.setVisibility(View.GONE);
//
//                            }
//                        } else {
//                            if (fetchProfileLoginUser.getCurr_state_name().equals(fetchProfile.getCurr_state_name())) {
//                                state_Status_LL.setVisibility(View.VISIBLE);
//                                state_Status.setText("Lives in " + fetchProfile.getCurr_state_name() + " too");
//
//                            } else {
//                                state_Status_LL.setVisibility(View.VISIBLE);
//
//
//                            }
//                        }
//                        /*end conversation Starters*/
//
//                        /*background*/
//
//                        if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
//
//
//                            bg_religion_lang_lock.setVisibility(View.GONE);
//                            background_go_premium_now_btn_LL.setVisibility(View.GONE);
//
//
//                            if (fetchProfile.getReligion_name().equals("Not Specified") && fetchProfile.getMothertounge_name().equals("Not Specified")) {
//                                bg_religion_lang_LL.setVisibility(View.GONE);
//                                background_cv.setVisibility(View.GONE);
//                                background_txt.setVisibility(View.GONE);
//                            } else {
//                                bg_religion_lang_LL.setVisibility(View.VISIBLE);
//                                background_cv.setVisibility(View.VISIBLE);
//                                background_txt.setVisibility(View.VISIBLE);
//
//                                if (fetchProfile.getReligion_name().equals("Not Specified")) {
//                                    bg_religion_lang.setText("");
//
//                                } else {
//                                    bg_religion_lang.setText(fetchProfile.getReligion_name());
//
//                                }
//
//                                if (fetchProfile.getMothertounge_name().equals("Not Specified")) {
//                                    bg_religion_lang.setText("");
//
//                                } else {
//                                    bg_religion_lang.setText(fetchProfile.getMothertounge_name());
//
//                                }
//
//
//                                if (!fetchProfile.getReligion_name().equals("Not Specified") && !fetchProfile.getMothertounge_name().equals("Not Specified")) {
//                                    bg_religion_lang.setText(fetchProfile.getReligion_name() + ", " + fetchProfile.getMothertounge_name());
//
//                                }
//
//                            }
//
//                        } else {
//                            bg_religion_lang.setText("Religion, Language ******");
//                            bg_religion_lang_lock.setVisibility(View.VISIBLE);
//
//                            background_go_premium_now_btn_LL.setVisibility(View.VISIBLE);
//                            background_go_premium_now_btn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent i = new Intent(DetailMachesActivity.this, PremiumActivity.class);
//                                    startActivity(i);
//                                }
//                            });
//
//                        }
//
//
//                        if (fetchProfile.getCaste_name().equals("Not Specified")) {
//                            bg_caste_LL.setVisibility(View.GONE);
//                        } else {
//                            bg_caste_LL.setVisibility(View.VISIBLE);
//
//                            if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
//                                if (!fetchProfile.getSubcaste_name().equals("Not Specified")) {
//                                    bg_caste.setText(fetchProfile.getCaste_name() + "  (" + fetchProfile.getSubcaste_name() + ")");
//                                } else {
//                                    bg_caste.setText(fetchProfile.getCaste_name());
//
//                                }
//
//                            } else {
//                                bg_caste.setText(fetchProfile.getCaste_name());
//
//                            }
//
//                        }
//
//                        /*end background*/
//
//
//                        /*family*/
//
//                        if (!fetchProfile.getFamily_class().equals("Not Specified") && !fetchProfile.getPer_ditrict_name().equals("Not Specified") &&
//                                !fetchProfile.getMother_occ().equals("Not Specified") && !fetchProfile.getBro_count().equals("Not Specified") &&
//                                !fetchProfile.getSis_count().equals("Not Specified")) {
//
//                            family_cv.setVisibility(View.VISIBLE);
//                            family_lbl.setVisibility(View.VISIBLE);
//
//                            if (fetchProfile.getGender().equals("Male")) {
//
//                                String familt_str = "Ours is " + fetchProfile.getFamily_class() + " family, originally from " +
//                                        fetchProfile.getPer_ditrict_name() + ". His mother is a " + fetchProfile.getMother_occ() +
//                                        ". He has " + fetchProfile.getBro_count() + " Brother and " + fetchProfile.getSis_count() + " sister.";
//
//                                family.setText(familt_str);
//
//                            } else if (fetchProfile.getGender().equals("Female")) {
//                                String familt_str = "Ours is " + fetchProfile.getFamily_class() + " family, originally from " +
//                                        fetchProfile.getPer_ditrict_name() + ". Her mother is a " + fetchProfile.getMother_occ() +
//                                        ". She has " + fetchProfile.getBro_count() + " Brother and " + fetchProfile.getSis_count() + " sister.";
//
//                                family.setText(familt_str);
//                            }
//
//                        } else {
//                            family_cv.setVisibility(View.GONE);
//                            family_lbl.setVisibility(View.GONE);
//                        }
//
//
//                        /*end family*/
//
//                        /*education career*/
//
//                        if (!fetchProfile.getEducation_name().equals("Not Specified") ||
//                                !fetchProfile.getOther_education().equals("Not Specified") ||
//                                !fetchProfile.getOccupation_name().equals("Not Specified") ||
//                                !fetchProfile.getPost_name().equals("Not Specified") ||
//                                !fetchProfile.getYearly_salary().equals("Not Specified")) {
//
//
//                            education_career_txt.setVisibility(View.VISIBLE);
//                            education_career_cv.setVisibility(View.VISIBLE);
//
//                            if (!fetchProfile.getEducation_name().equals("Not Specified")) {
//                                educationLL.setVisibility(View.VISIBLE);
//                                education.setVisibility(View.VISIBLE);
//                                education.setText(fetchProfile.getEducation_name());
//                            } else {
//                                educationLL.setVisibility(View.GONE);
//                                education.setVisibility(View.GONE);
//                            }
//
//                            if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
//
//                                education_go_premium_now_btn_LL.setVisibility(View.GONE);
//
//                                other_education_lock.setVisibility(View.GONE);
//                                occupation_lock.setVisibility(View.GONE);
//                                post_name_lock.setVisibility(View.GONE);
//
//                                if (!fetchProfile.getOther_education().equals("Not Specified")) {
//                                    other_educationLL.setVisibility(View.VISIBLE);
//                                    other_education.setVisibility(View.VISIBLE);
//                                    other_education.setText(fetchProfile.getOther_education());
//
//                                } else {
//                                    other_educationLL.setVisibility(View.GONE);
//                                    other_education.setVisibility(View.GONE);
//                                }
//
//
//                                if (!fetchProfile.getOccupation_name().equals("Not Specified")) {
//                                    occupationLL.setVisibility(View.VISIBLE);
//                                    occupation.setVisibility(View.VISIBLE);
//                                    occupation.setText(fetchProfile.getOccupation_name());
//                                } else {
//                                    occupationLL.setVisibility(View.GONE);
//                                    occupation.setVisibility(View.GONE);
//                                }
//
//
//                                if (!fetchProfile.getPost_name().equals("Not Specified")) {
//                                    post_nameLL.setVisibility(View.VISIBLE);
//                                    post_name.setText(fetchProfile.getPost_name());
//                                } else {
//                                    post_nameLL.setVisibility(View.GONE);
//                                    post_name.setVisibility(View.GONE);
//                                }
//
//                            } else {
//
//                                other_education.setText("Other education ******");
//                                other_education_lock.setVisibility(View.VISIBLE);
//
//
//                                occupation.setText("Occupation ******");
//                                occupation_lock.setVisibility(View.VISIBLE);
//
//                                post_name.setText("Post Name ******");
//                                post_name_lock.setVisibility(View.VISIBLE);
//
//
//                                education_go_premium_now_btn_LL.setVisibility(View.VISIBLE);
//                                education_go_premium_now_btn.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent i = new Intent(DetailMachesActivity.this, PremiumActivity.class);
//                                        startActivity(i);
//                                    }
//                                });
//
//
//                            }
//
//
//                            if (!fetchProfile.getYearly_salary().equals("Not Specified")) {
//                                incomeLL.setVisibility(View.VISIBLE);
//                                income.setVisibility(View.VISIBLE);
//                                income.setText(fetchProfile.getYearly_salary());
//                            } else {
//                                incomeLL.setVisibility(View.GONE);
//                                income.setVisibility(View.GONE);
//                            }
//
//                        } else {
//                            education_career_txt.setVisibility(View.GONE);
//                            education_career_cv.setVisibility(View.GONE);
//                        }
//
//
//                        if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
//                            hobbies_lock.setVisibility(View.GONE);
//                            hobbies_go_premium_now_btn_LL.setVisibility(View.GONE);
//
//                            if (!fetchProfile.getHobbies().equals("Not Specified")) {
//                                hobbiesLL.setVisibility(View.VISIBLE);
//                                hobbies.setVisibility(View.VISIBLE);
//                                hobbies_cv.setVisibility(View.VISIBLE);
//                                hobbies_txt.setVisibility(View.VISIBLE);
//                                hobbies.setText(fetchProfile.getHobbies());
//                            } else {
//                                hobbiesLL.setVisibility(View.GONE);
//                                hobbies.setVisibility(View.GONE);
//                                hobbies_cv.setVisibility(View.GONE);
//                                hobbies_txt.setVisibility(View.GONE);
//                            }
//
//                        } else {
//                            hobbies.setText("Hobbies ******");
//                            hobbies_lock.setVisibility(View.VISIBLE);
//
//
//                            hobbies_go_premium_now_btn_LL.setVisibility(View.VISIBLE);
//                            hobbies_go_premium_now_btn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent i = new Intent(DetailMachesActivity.this, PremiumActivity.class);
//                                    startActivity(i);
//                                }
//                            });
//
//                        }
//
//
//
//
//
//                        /*education career*/
//
//                        /*pref comparision*/
//
//                        System.out.println("age ---------" + fetchProfile.getAge());
//                        System.out.println("my pref age from ---------" + fetchProfileLoginUser.getPref_agefrom());
//                        System.out.println("my pref age to ---------" + fetchProfileLoginUser.getPref_ageto());
//
//
//                        String part_age = fetchProfile.getAge();
//                        String my_pref_age_from = fetchProfileLoginUser.getPref_agefrom();
//                        String my_pref_age_to = fetchProfileLoginUser.getPref_ageto();
//
//
//                        if ((!part_age.equals("Not Specified") && !my_pref_age_from.equals("Not Specified")) && !my_pref_age_to.equals("Not Specified")) {
//
//
//                            if (!my_pref_age_from.equals("Doesn't Matter") && !my_pref_age_to.equals("Doesn't Matter")) {
//
//                                int _part_age = Integer.parseInt(part_age);
//                                int _my_pref_age_from = Integer.parseInt(my_pref_age_from);
//                                int _my_pref_age_to = Integer.parseInt(my_pref_age_to);
//
//
//                                if (_part_age <= _my_pref_age_to && _part_age >= _my_pref_age_from) {
//                                    pref_count++;
//                                    age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    age_from_to.setImageResource(R.drawable.cross_circle);
//                                }
//                            } else if (!my_pref_age_from.equals("Doesn't Matter") && my_pref_age_to.equals("Doesn't Matter")) {
//
//                                int _part_age = Integer.parseInt(part_age);
//                                int _my_pref_age_from = Integer.parseInt(my_pref_age_from);
//
//
//                                if (_part_age >= _my_pref_age_from) {
//                                    pref_count++;
//
//                                    age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    age_from_to.setImageResource(R.drawable.cross_circle);
//                                }
//                            } else if (my_pref_age_from.equals("Doesn't Matter") && !my_pref_age_to.equals("Doesn't Matter")) {
//
//                                int _part_age = Integer.parseInt(part_age);
//                                int _my_pref_age_to = Integer.parseInt(my_pref_age_to);
//
//
//                                if (_part_age <= _my_pref_age_to) {
//                                    pref_count++;
//
//                                    age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    age_from_to.setImageResource(R.drawable.cross_circle);
//                                }
//                            } else if (my_pref_age_from.equals("Doesn't Matter") && my_pref_age_to.equals("Doesn't Matter")) {
//
//                                pref_count++;
//
//                                age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
//
//                            }
//
//                            age_from_to_pref.setText(part_age);
//                        } else if (part_age.equals("Not Specified")) {
//                            age_pref_ll.setVisibility(View.GONE);
//                            age_pref_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getHeight().equals("Not Specified") && !fetchProfileLoginUser.getPref_height().equals("Not Specified")) {
//
//                            height_RL.setVisibility(View.VISIBLE);
//                            height_pref_v.setVisibility(View.VISIBLE);
//
//                            if (fetchProfileLoginUser.getPref_height().equals("Doesn't Matter")) {
//                                pref_count++;
//                                height_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//
//
//                                if (fetchProfile.getHeight().equals(fetchProfileLoginUser.getPref_height())) {
//                                    pref_count++;
//                                    height_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    height_pref_img.setImageResource(R.drawable.cross_circle);
//                                }
//                            }
//
//                            height_pref.setText(fetchProfile.getHeight());
//
//                        } else {
//                            height_RL.setVisibility(View.GONE);
//                            height_pref_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getMarital_status_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_marital_name().equals("Not Specified")) {
//
//
//                            marital_status_prefRL.setVisibility(View.VISIBLE);
//                            marital_status_pref_v.setVisibility(View.VISIBLE);
//
//
//                            if (fetchProfileLoginUser.getPref_marital_name().equals("Doesn't Matter")) {
//                                pref_count++;
//                                marital_status_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//                                if (fetchProfile.getMarital_status_name().equals(fetchProfileLoginUser.getPref_marital_name())) {
//                                    pref_count++;
//                                    marital_status_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    marital_status_pref_img.setImageResource(R.drawable.cross_circle);
//                                }
//                            }
//
//                            marital_status_pref.setText(fetchProfile.getMarital_status_name());
//
//                        } else {
//                            marital_status_prefRL.setVisibility(View.GONE);
//                            marital_status_pref_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getReligion_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_religion_name().equals("Not Specified")) {
//
//
//                            religion_caste_pref_v.setVisibility(View.VISIBLE);
//                            religion_caste_prefRL.setVisibility(View.VISIBLE);
//
//                            if (fetchProfileLoginUser.getPref_religion_name().equals("Doesn't Matter")) {
//                                pref_count++;
//
//                                religion_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//
//                                if (fetchProfile.getReligion_name().equals(fetchProfileLoginUser.getPref_religion_name())) {
//                                    pref_count++;
//
//                                    religion_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    religion_caste_pref_img.setImageResource(R.drawable.cross_circle);
//                                }
//
//                            }
//
//
//                            religion_caste_pref.setText(fetchProfile.getReligion_name());
//
//                        } else {
//                            religion_caste_prefRL.setVisibility(View.GONE);
//                            religion_caste_pref_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getDietry_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_diet_name().equals("Not Specified")) {
//
//
//                            diet_prefRL.setVisibility(View.VISIBLE);
//                            diet_pref_v.setVisibility(View.VISIBLE);
//
//
//                            if (fetchProfileLoginUser.getPref_diet_name().equals("Doesn't Matter")) {
//                                pref_count++;
//
//                                diet_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//                                if (fetchProfile.getDietry_name().equals(fetchProfileLoginUser.getPref_diet_name())) {
//                                    pref_count++;
//
//                                    diet_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    diet_pref_img.setImageResource(R.drawable.cross_circle);
//                                }
//                            }
//
//
//                            diet_pref.setText(fetchProfile.getDietry_name());
//
//                        } else {
//                            diet_prefRL.setVisibility(View.GONE);
//                            diet_pref_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getEducation_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_edu_name().equals("Not Specified")) {
//
//
//                            education_v.setVisibility(View.VISIBLE);
//                            educationRL.setVisibility(View.VISIBLE);
//
//                            if (fetchProfileLoginUser.getPref_edu_name().equals("Doesn't Matter")) {
//
//                                pref_count++;
//                                education_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//
//                                if (fetchProfile.getEducation_name().equals(fetchProfileLoginUser.getPref_edu_name())) {
//
//                                    pref_count++;
//                                    education_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    education_img.setImageResource(R.drawable.cross_circle);
//                                }
//                            }
//
//                            education_pref.setText(fetchProfile.getEducation_name());
//
//                        } else {
//                            educationRL.setVisibility(View.GONE);
//                            education_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getColor_complex_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_complex_name().equals("Not Specified")) {
//
//
//                            color_complex_pref_v.setVisibility(View.VISIBLE);
//                            color_complex_pref_RL.setVisibility(View.VISIBLE);
//
//                            if (fetchProfileLoginUser.getPref_complex_name().equals("Doesn't Matter")) {
//
//                                pref_count++;
//                                color_complex_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//
//                                if (fetchProfile.getColor_complex_name().equals(fetchProfileLoginUser.getPref_complex_name())) {
//
//                                    pref_count++;
//                                    color_complex_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    color_complex_pref_img.setImageResource(R.drawable.cross_circle);
//                                }
//                            }
//
//                            color_complex_pref.setText(fetchProfile.getColor_complex_name());
//
//                        } else {
//                            color_complex_pref_RL.setVisibility(View.GONE);
//                            color_complex_pref_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getCaste_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_caste_name().equals("Not Specified")) {
//
//
//                            caste_pref_v.setVisibility(View.VISIBLE);
//                            caste_pref_RL.setVisibility(View.VISIBLE);
//
//                            if (fetchProfileLoginUser.getPref_caste_name().equals("Doesn't Matter")) {
//
//                                pref_count++;
//                                caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//
//                                if (fetchProfile.getCaste_name().equals(fetchProfileLoginUser.getPref_caste_name())) {
//
//                                    pref_count++;
//                                    caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    caste_pref_img.setImageResource(R.drawable.cross_circle);
//                                }
//                            }
//
//                            caste_pref.setText(fetchProfile.getCaste_name());
//
//                        } else {
//                            caste_pref_RL.setVisibility(View.GONE);
//                            caste_pref_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getSubcaste_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_subcaste_name().equals("Not Specified")) {
//
//
//                            sub_caste_pref_v.setVisibility(View.VISIBLE);
//                            sub_caste_pref_RL.setVisibility(View.VISIBLE);
//
//                            if (fetchProfileLoginUser.getPref_subcaste_name().equals("Doesn't Matter")) {
//
//                                pref_count++;
//                                sub_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//
//                                if (fetchProfile.getSubcaste_name().equals(fetchProfileLoginUser.getPref_subcaste_name())) {
//
//                                    pref_count++;
//                                    sub_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    sub_caste_pref_img.setImageResource(R.drawable.cross_circle);
//                                }
//                            }
//
//                            sub_caste_pref.setText(fetchProfile.getSubcaste_name());
//
//                        } else {
//                            sub_caste_pref_RL.setVisibility(View.GONE);
//                            sub_caste_pref_v.setVisibility(View.GONE);
//                        }
//
//
//                        if (!fetchProfile.getLifestyle_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_lifestyle_name().equals("Not Specified")) {
//
//
//                            lifestyle_pref_v.setVisibility(View.VISIBLE);
//                            lifestyle_pref_RL.setVisibility(View.VISIBLE);
//
//                            if (fetchProfileLoginUser.getPref_lifestyle_name().equals("Doesn't Matter")) {
//
//                                pref_count++;
//                                lifestyle_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//
//                                if (fetchProfile.getLifestyle_name().equals(fetchProfileLoginUser.getPref_lifestyle_name())) {
//
//                                    pref_count++;
//                                    lifestyle_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                                } else {
//                                    lifestyle_pref_img.setImageResource(R.drawable.cross_circle);
//                                }
//                            }
//
//                            lifestyle_pref.setText(fetchProfile.getLifestyle_name());
//
//                        } else {
//                            lifestyle_pref_RL.setVisibility(View.GONE);
//                            lifestyle_pref_v.setVisibility(View.GONE);
//                        }
//
//
//
//
//
//
//                        /*-----------*/
//
//
//
//
//
//
//
//
//                        /*end pref comparision*/
//
//
//                        if (fetchProfile.getGender().equals("Male")) {
//                            her_him_pref.setText("His Prefrences");
//                        }
//                        if (fetchProfile.getGender().equals("Female")) {
//                            her_him_pref.setText("Her Prefrences");
//
//                        }
//                        System.out.println("match_count------" + match_count);
//
//                        if (pref_count != 0)
//                            match_count.setText("You Match " + pref_count);
//
//
//                        if (fetchProfile.getImages() != null) {
//                            if (fetchProfile.getImages().size() != 0) {
//                                Glide.with(DetailMachesActivity.this).load(fetchProfile.getImages().get(0).getImgs()).into(his_her_profile);
//                            } else {
//                                Glide.with(DetailMachesActivity.this).load(R.drawable.default_avtar_2).into(his_her_profile);
//
//                            }
//                        } else {
//                            Glide.with(DetailMachesActivity.this).load(R.drawable.default_avtar_2).into(his_her_profile);
//                        }
//
//
//                        if (fetchProfileLoginUser.getImages() != null) {
//                            if (fetchProfileLoginUser.getImages().size() != 0) {
//                                Glide.with(DetailMachesActivity.this).load(fetchProfileLoginUser.getImages().get(0).getImgs()).into(login_user_profile_image);
//                            } else {
//                                Glide.with(DetailMachesActivity.this).load(R.drawable.default_avtar_2).into(login_user_profile_image);
//
//                            }
//                        } else {
//                            Glide.with(DetailMachesActivity.this).load(R.drawable.default_avtar_2).into(login_user_profile_image);
//                        }
//
//
//                        if (fetchProfile.getGender().equals("Male")) {
//                            you_.setText("You & Him");
//                        }
//                        if (fetchProfile.getGender().equals("Female")) {
//                            you_.setText("You & She");
//                        }
//
//                        if (fetchProfile.getImages().size() != 0) {
//
//                            top_cv.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    if (verified_txtLL.getVisibility() == View.VISIBLE) {
//                                        verified_txtLL.setVisibility(View.GONE);
//
//                                    } else {
//                                        Intent i = new Intent(DetailMachesActivity.this, ImagesActivity.class);
//                                        if (fetchProfile.getImages().size() != 0)
//                                            i.putExtra("mylist", (Serializable) fetchProfile.getImages());
//
//                                        startActivity(i);
//                                    }
//
//                                }
//                            });
//
//
//                            imgLL.setVisibility(View.VISIBLE);
//                            img_count.setText(String.valueOf(fetchProfile.getImages().size()));
//
//                            imgLL.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent i = new Intent(DetailMachesActivity.this, ImagesActivity.class);
//                                    if (fetchProfile.getImages().size() != 0)
//                                        i.putExtra("mylist", (Serializable) fetchProfile.getImages());
//
//                                    startActivity(i);
//                                }
//                            });
//
//
//                        } else {
//                            imgLL.setVisibility(View.GONE);
//                        }
//
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<FetchProfile> call, Throwable t) {
//                System.out.println("msg1 my profile******" + t.toString());
//
//                if(!isNetworkAvailable(DetailMachesActivity.this)){
//                    Toast.makeText(DetailMachesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(DetailMachesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });
//
//    }
//
//
//    public  void fetchLoginProfileData(){
//
//        progressBar = ProgressDialog.show(DetailMachesActivity.this, "", "Please Wait...");
//
//
//        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
//        userResponse.enqueue(new Callback<FetchProfile>() {
//
//            @Override
//            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
//                fetchProfileLoginUser = response.body();
//                progressBar.dismiss();
//
//                if (response.isSuccessful()) {
//
//                    fetchProfileData();
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<FetchProfile> call, Throwable t) {
//                System.out.println("msg1 my profile******" + t.toString());
//                progressBar.dismiss();
//
//                if(!isNetworkAvailable(DetailMachesActivity.this)){
//                    Toast.makeText(DetailMachesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(DetailMachesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });
//
//    }
//
//
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
//
//
//    public boolean isNetworkAvailable(){
//
//        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
//        return networkInfo !=null;
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//
//    public  void fetchProfileDataforShortlistUpdate(){
//
//        System.out.println("login userid 111=====" +user.getUser_id());
//        System.out.println("premium_userid 111=====" +premium_userid);
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<FetchProfile> userResponse = apiService.peopleDetails(premium_userid,String.valueOf(user.getUser_id()));
//        userResponse.enqueue(new Callback<FetchProfile>() {
//
//            @Override
//            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
//                fetchProfile1 = response.body();
//
//
//                if (response.isSuccessful()) {
//
//                    System.out.println("user id=====" + fetchProfile1.getUser_id());
//
//
//                    if (!fetchProfile1.getIs_shortlist().equals("1")) {
//                        option_LL.setVisibility(View.VISIBLE);
//                        option_LL1.setVisibility(View.GONE);
//                    } else {
//                        option_LL.setVisibility(View.GONE);
//                        option_LL1.setVisibility(View.VISIBLE);
//                    }
//
//                    option_LL.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            PopupMenu popup = new PopupMenu(DetailMachesActivity.this, view);
//                            popup.setOnMenuItemClickListener(DetailMachesActivity.this);
//
//                            MenuInflater inflater = popup.getMenuInflater();
//                            inflater.inflate(R.menu.option_menu, popup.getMenu());
//
//                            popup.show();
//                        }
//                    });
//
//
//                    option_LL1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            PopupMenu popup = new PopupMenu(DetailMachesActivity.this, view);
//                            popup.setOnMenuItemClickListener(DetailMachesActivity.this);
//
//                            MenuInflater inflater = popup.getMenuInflater();
//                            inflater.inflate(R.menu.option_menu_1, popup.getMenu());
//
//                            popup.show();
//                        }
//                    });
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FetchProfile> call, Throwable t) {
//                System.out.println("msg1 my shortlist check******" + t.toString());
//
//                if(!isNetworkAvailable(DetailMachesActivity.this)){
//                    Toast.makeText(DetailMachesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(DetailMachesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });
//
//    }
//
//
//
//}
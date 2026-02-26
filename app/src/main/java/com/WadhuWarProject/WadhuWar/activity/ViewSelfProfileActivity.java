


package com.WadhuWarProject.WadhuWar.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.RoundedImageView;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.LikeProfileResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
//import com.github.florent37.shapeofview.shapes.DiagonalView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.like.LikeButton;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSelfProfileActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener, PopupMenu.OnMenuItemClickListener {
    Toolbar toolbar;
    UserData user;

    InsertResponse connect_response;

    CardView top_cv;
    int pref_count;
    CircleImageView his_her_profile;
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar connect_btn_pb;
    AppBarLayout toolbar_appbar;
    ImageView close_btn;
    RoundedImageView img_box;
    TextView contact_no_b, whatapp_no, chat, mail;
    LinearLayout upgrade_now_btn;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;

    RelativeLayout age_pref_ll, marital_status_prefRL, height_RL, religion_caste_prefRL, mother_tongue_prefRL, state_livein_prefRL, diet_prefRL,
            annual_income_prefRL, gotra_RL, countryRL, cityRL, educationRL, occupationRL, color_complex_pref_RL, caste_pref_RL, sub_caste_pref_RL, lifestyle_pref_RL,
            sampraday_pref_RL, likeRL;

    TextView name, age_height, lang_caste, job, address, about_txt, acc_id, profile_created_for, about_desc, not_see_txt,
            age_height_color, birthday_date,birthday_time, marital_status,college_name,manglik, address_lifestyle, job_lifestyle, diet, lang_status, caste_status,
            state_Status, conversation_txt, bg_religion_lang, bg_caste, family, family_lbl, education, other_education, occupation, post_name, income, hobbies,
            age_from_to_pref, marital_status_pref, religion_caste_pref, mother_tongue_pref, state_livein_pref, diet_pref,
            annual_income_pref, height_pref, gotra, country, city, education_pref, occupation_pref, her_him_pref, match_count,
            background_txt, education_career_txt, hobbies_txt, you_, img_count, job_top, address_top, color_complex_pref, caste_pref, sub_caste_pref, lifestyle_pref,
            sampraday_pref, contact_no, whatsapp_no, email, work_location, current_address, permanant_address, gender, gothra, weight,
            color_complexion, handicap, lifestyle, blood_group, pref_marital_status, pref_age, pref_education, swip_name_top, connect_now_txt, like_profile_txt,
            connect_btn_bottom_txt, contact_connect_txt, arrow1, arrow2, sampraday_txt, rashi_txt, pref_education_type;

    ImageView user_img, connect_now_btn, about_lock,birthday_time_lock, lifestyle_lock, lang_status_img, caste_status_img, state_Status_img, age_from_to,
            height_pref_img, marital_status_pref_img, religion_caste_pref_img, mother_tongue_pref_img, diet_pref_img,
            annual_income_pref_img, gotra_img, country_img, city_img, education_img, occupation_img, login_user_profile_image, state_livein_pref_img,
            color_complex_pref_img, caste_pref_img, sub_caste_pref_img, lifestyle_pref_img, sampraday_pref_img, birthday_lock, marital_status_lock, diet_lock,
            bg_religion_lang_lock, other_education_lock, occupation_lock, post_name_lock, hobbies_lock, contact_no_lock, whatsapp_no_lock, email_lock;

    LinearLayout btn_block_report, onlineLL, upgrade_now, state_Status_LL, caste_status_LL, lang_status_LL, bg_religion_lang_LL, bg_caste_LL, educationLL, other_educationLL,
            occupationLL, post_nameLL, incomeLL, hobbiesLL, imgLL, bottom_like_connectLL, verified_LL, verified_txtLL, premiumLL, option_LL, option_LL1, go_premium_LL,
            contact_detailLL, seeAllAddressLL, addressLL, seeAllfamilyLL, seeAllPrefLL, view_all_detail, lifestyle_appearance_LL, basic_block_LL, education_careerLL,
            conversation_background_familyLL, partner_prefLL, partner_pref_compareLL, connect_now_topLL, contact_connect_now_btn_LL, contact_connect_now_btn,
            contact_go_premium_now_btn_LL, contact_go_premium_now_btn, background_go_premium_now_btn_LL, background_go_premium_now_btn,
            education_go_premium_now_btn_LL, education_go_premium_now_btn, hobbies_go_premium_now_btn_LL, hobbies_go_premium_now_btn, swip_topLL, connect_btn_LL,
            connect_btn_bottomLL, upgrade_txt, chat_btn, whatapp_btn, call_btn, bottom_contactLL, chat_btn1, whatapp_btn1, call_btn1, contact_topLL, you_LL, converLL;

    CardView conversation_cv, family_cv, background_cv, education_career_cv, hobbies_cv;
    String people_userid;

    View age_pref_v, height_pref_v, marital_status_pref_v, religion_caste_pref_v, mother_tongue_pref_v, state_livein_pref_v,
            diet_pref_v, annual_income_v, gotra_v, county_v, city_v, education_v, occupation_v, color_complex_pref_v, caste_pref_v, sub_caste_pref_v, lifestyle_pref_v,
            sampraday_pref_v;

    FetchProfile fetchProfile, fetchProfile1;
    FetchProfile fetchProfileLoginUser;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;

    NestedScrollView sv_feature;
    ImageView back_btn;
    ProgressDialog progressBar;
    Window window;
    FrameLayout mainFL;
    CardView justjoinL;
    InsertResponse addShortList;
    InsertResponse removeShortList;
    Animation animBounce;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    ImageView img2;

    LinearLayout default_Avtar_LL;

    InsertResponse viewCountRespo;
    ProgressBar p1;

    LikeButton likeButton;
    LikeProfileResponse likeProfileResponse;
    TextView like_count;

    @SuppressLint("RestrictedApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightStatusBars(true);

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        setContentView(R.layout.activity_view_self_profile);

        View root1 = findViewById(R.id.featurefragmentCor);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @SuppressLint("RestrictedApi")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_self_profile);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        btn_block_report = findViewById(R.id.btn_block_report);
        pref_education_type = findViewById(R.id.pref_education_type);
        rashi_txt = findViewById(R.id.rashi_txt);
        sampraday_txt = findViewById(R.id.sampraday_txt);
        likeRL = findViewById(R.id.likeRL);
        you_LL = findViewById(R.id.you_LL);
        like_count = findViewById(R.id.like_count);
        likeButton = findViewById(R.id.likeButton);
        p1 = findViewById(R.id.p1);
        img2 = findViewById(R.id.img2);
        default_Avtar_LL = findViewById(R.id.default_Avtar_LL);
        contact_connect_txt = findViewById(R.id.contact_connect_txt);
        contact_topLL = findViewById(R.id.contact_topLL);
        chat_btn1 = findViewById(R.id.chat_btn1);
        call_btn1 = findViewById(R.id.call_btn1);
        whatapp_btn1 = findViewById(R.id.whatapp_btn1);
        bottom_contactLL = findViewById(R.id.bottom_contactLL);
        call_btn = findViewById(R.id.call_btn);
        whatapp_btn = findViewById(R.id.whatapp_btn);
        chat_btn = findViewById(R.id.chat_btn);
        upgrade_txt = findViewById(R.id.upgrade_txt);
        connect_btn_bottom_txt = findViewById(R.id.connect_btn_bottom_txt);
        connect_btn_bottomLL = findViewById(R.id.connect_btn_bottomLL);
        connect_btn_pb = findViewById(R.id.connect_btn_pb);
        connect_btn_LL = findViewById(R.id.connect_btn_LL);
        like_profile_txt = findViewById(R.id.like_profile_txt);
        connect_now_txt = findViewById(R.id.connect_now_txt);
        swip_topLL = findViewById(R.id.swip_topLL);
        swip_name_top = findViewById(R.id.swip_name_top);
        hobbies_go_premium_now_btn = findViewById(R.id.hobbies_go_premium_now_btn);
        hobbies_go_premium_now_btn_LL = findViewById(R.id.hobbies_go_premium_now_btn_LL);
        education_go_premium_now_btn = findViewById(R.id.education_go_premium_now_btn);
        education_go_premium_now_btn_LL = findViewById(R.id.education_go_premium_now_btn_LL);
        background_go_premium_now_btn = findViewById(R.id.background_go_premium_now_btn);
        background_go_premium_now_btn_LL = findViewById(R.id.background_go_premium_now_btn_LL);
        contact_go_premium_now_btn = findViewById(R.id.contact_go_premium_now_btn);
        contact_go_premium_now_btn_LL = findViewById(R.id.contact_go_premium_now_btn_LL);
        contact_connect_now_btn = findViewById(R.id.contact_connect_now_btn);
        contact_connect_now_btn_LL = findViewById(R.id.contact_connect_now_btn_LL);
        email_lock = findViewById(R.id.email_lock);
        whatsapp_no_lock = findViewById(R.id.whatsapp_no_lock);
        contact_no_lock = findViewById(R.id.contact_no_lock);
        connect_now_topLL = findViewById(R.id.connect_now_topLL);
        partner_pref_compareLL = findViewById(R.id.partner_pref_compareLL);
        partner_prefLL = findViewById(R.id.partner_prefLL);
        education_careerLL = findViewById(R.id.education_careerLL);
        conversation_background_familyLL = findViewById(R.id.conversation_background_familyLL);
        basic_block_LL = findViewById(R.id.basic_block_LL);
        lifestyle_appearance_LL = findViewById(R.id.lifestyle_appearance_LL);
        view_all_detail = findViewById(R.id.view_all_detail);
        seeAllPrefLL = findViewById(R.id.seeAllPrefLL);
        pref_education = findViewById(R.id.pref_education);
        pref_marital_status = findViewById(R.id.pref_marital_status);
        pref_age = findViewById(R.id.pref_age);
        seeAllfamilyLL = findViewById(R.id.seeAllfamilyLL);
        handicap = findViewById(R.id.handicap);
        color_complexion = findViewById(R.id.color_complexion);
        blood_group = findViewById(R.id.blood_group);
        lifestyle = findViewById(R.id.lifestyle);
        weight = findViewById(R.id.weight);
        gothra = findViewById(R.id.gothra);
        gender = findViewById(R.id.gender);
        addressLL = findViewById(R.id.addressLL);
        permanant_address = findViewById(R.id.permanant_address);
        current_address = findViewById(R.id.current_address);
        work_location = findViewById(R.id.work_location);
        seeAllAddressLL = findViewById(R.id.seeAllAddressLL);
        contact_detailLL = findViewById(R.id.contact_detailLL);
        email = findViewById(R.id.email);
        whatsapp_no = findViewById(R.id.whatsapp_no);
        contact_no = findViewById(R.id.contact_no);
        go_premium_LL = findViewById(R.id.go_premium_LL);
        hobbies_lock = findViewById(R.id.hobbies_lock);
        post_name_lock = findViewById(R.id.post_name_lock);
        occupation_lock = findViewById(R.id.occupation_lock);
        other_education_lock = findViewById(R.id.other_education_lock);
        bg_religion_lang_lock = findViewById(R.id.bg_religion_lang_lock);
        diet_lock = findViewById(R.id.diet_lock);
        marital_status_lock = findViewById(R.id.marital_status_lock);
        birthday_lock = findViewById(R.id.birthday_lock);
        sampraday_pref_v = findViewById(R.id.sampraday_pref_v);
        sampraday_pref_img = findViewById(R.id.sampraday_pref_img);
        sampraday_pref = findViewById(R.id.sampraday_pref);
        sampraday_pref_RL = findViewById(R.id.sampraday_pref_RL);
        lifestyle_pref_v = findViewById(R.id.lifestyle_pref_v);
        lifestyle_pref_img = findViewById(R.id.lifestyle_pref_img);
        lifestyle_pref = findViewById(R.id.lifestyle_pref);
        lifestyle_pref_RL = findViewById(R.id.lifestyle_pref_RL);
        sub_caste_pref_RL = findViewById(R.id.sub_caste_pref_RL);
        sub_caste_pref = findViewById(R.id.sub_caste_pref);
        sub_caste_pref_img = findViewById(R.id.sub_caste_pref_img);
        sub_caste_pref_v = findViewById(R.id.sub_caste_pref_v);
        caste_pref_v = findViewById(R.id.caste_pref_v);
        caste_pref_img = findViewById(R.id.caste_pref_img);
        caste_pref = findViewById(R.id.caste_pref);
        caste_pref_RL = findViewById(R.id.caste_pref_RL);
        color_complex_pref_v = findViewById(R.id.color_complex_pref_v);
        color_complex_pref_img = findViewById(R.id.color_complex_pref_img);
        color_complex_pref = findViewById(R.id.color_complex_pref);
        color_complex_pref_RL = findViewById(R.id.color_complex_pref_RL);
        option_LL = findViewById(R.id.option_LL);
        option_LL1 = findViewById(R.id.option_LL1);
        justjoinL = findViewById(R.id.justjoinL);
        premiumLL = findViewById(R.id.premiumLL);
        verified_txtLL = findViewById(R.id.verified_txtLL);
        verified_LL = findViewById(R.id.verified_LL);
        bottom_like_connectLL = findViewById(R.id.bottom_like_connectLL);
        sv_feature = findViewById(R.id.sv_feature);
        hobbies_txt = findViewById(R.id.hobbies_txt);
        toolbar = findViewById(R.id.toolbar);
        top_cv = findViewById(R.id.top_cv);
        user_img = findViewById(R.id.user_img);
        onlineLL = findViewById(R.id.onlineLL);
        name = findViewById(R.id.name);
        age_height = findViewById(R.id.age_height);
        lang_caste = findViewById(R.id.lang_caste);
        job = findViewById(R.id.job);
        job_top = findViewById(R.id.job_top);
        address = findViewById(R.id.address);
        address_top = findViewById(R.id.address_top);
        connect_now_btn = findViewById(R.id.connect_now_btn);
        about_txt = findViewById(R.id.about_txt);
        about_lock = findViewById(R.id.about_lock);
        acc_id = findViewById(R.id.acc_id);
        profile_created_for = findViewById(R.id.profile_created_for);
        about_desc = findViewById(R.id.about_desc);
        arrow1 = findViewById(R.id.arrow1);
        arrow2 = findViewById(R.id.arrow2);
        upgrade_now = findViewById(R.id.upgrade_now);
        not_see_txt = findViewById(R.id.not_see_txt);
        age_height_color = findViewById(R.id.age_height_color);
        birthday_date = findViewById(R.id.birthday_date);
        birthday_time = findViewById(R.id.birthday_time);
        birthday_time_lock = findViewById(R.id.birthday_time_lock);
        marital_status = findViewById(R.id.marital_status);
        address_lifestyle = findViewById(R.id.address_lifestyle);
        job_lifestyle = findViewById(R.id.job_lifestyle);
        diet = findViewById(R.id.diet);
        lang_status = findViewById(R.id.lang_status);
        caste_status = findViewById(R.id.caste_status);
        state_Status = findViewById(R.id.state_Status);
        conversation_txt = findViewById(R.id.conversation_txt);
        conversation_cv = findViewById(R.id.conversation_cv);
        lang_status_img = findViewById(R.id.lang_status_img);
        caste_status_img = findViewById(R.id.caste_status_img);
        state_Status_img = findViewById(R.id.state_Status_img);
        state_Status_LL = findViewById(R.id.state_Status_LL);
        caste_status_LL = findViewById(R.id.caste_status_LL);
        lang_status_LL = findViewById(R.id.lang_status_LL);
        converLL = findViewById(R.id.converLL);
        bg_religion_lang = findViewById(R.id.bg_religion_lang);
        bg_caste = findViewById(R.id.bg_caste);
        bg_religion_lang_LL = findViewById(R.id.bg_religion_lang_LL);
        bg_caste_LL = findViewById(R.id.bg_caste_LL);
        family = findViewById(R.id.family);
        family_lbl = findViewById(R.id.family_lbl);
        family_cv = findViewById(R.id.family_cv);
        education = findViewById(R.id.education);
        college_name = findViewById(R.id.college_name);
        manglik = findViewById(R.id.manglik);
        other_education = findViewById(R.id.other_education);
        occupation = findViewById(R.id.occupation);
        post_name = findViewById(R.id.post_name);
        income = findViewById(R.id.income);
        educationLL = findViewById(R.id.educationLL);
        other_educationLL = findViewById(R.id.other_educationLL);
        occupationLL = findViewById(R.id.occupationLL);
        post_nameLL = findViewById(R.id.post_nameLL);
        incomeLL = findViewById(R.id.incomeLL);
        age_from_to_pref = findViewById(R.id.age_from_to_pref);
        marital_status_pref = findViewById(R.id.marital_status_pref);
        religion_caste_pref = findViewById(R.id.religion_caste_pref);
        mother_tongue_pref = findViewById(R.id.mother_tongue_pref);
        state_livein_pref = findViewById(R.id.state_livein_pref);
        diet_pref = findViewById(R.id.diet_pref);
        annual_income_pref = findViewById(R.id.annual_income_pref);
        age_from_to = findViewById(R.id.age_from_to);
        marital_status_prefRL = findViewById(R.id.marital_status_prefRL);
        height_RL = findViewById(R.id.height_RL);
        height_pref = findViewById(R.id.height_pref);
        height_pref_img = findViewById(R.id.height_pref_img);
        marital_status_prefRL = findViewById(R.id.marital_status_prefRL);
        marital_status_pref = findViewById(R.id.marital_status_pref);
        marital_status_pref_img = findViewById(R.id.marital_status_pref_img);
        religion_caste_prefRL = findViewById(R.id.religion_caste_prefRL);
        religion_caste_pref_img = findViewById(R.id.religion_caste_pref_img);
        mother_tongue_prefRL = findViewById(R.id.mother_tongue_prefRL);
        mother_tongue_pref = findViewById(R.id.mother_tongue_pref);
        mother_tongue_pref_img = findViewById(R.id.mother_tongue_pref_img);
        state_livein_prefRL = findViewById(R.id.state_livein_prefRL);
        diet_prefRL = findViewById(R.id.diet_prefRL);
        diet_pref = findViewById(R.id.diet_pref);
        diet_pref_img = findViewById(R.id.diet_pref_img);
        annual_income_prefRL = findViewById(R.id.annual_income_prefRL);
        annual_income_pref = findViewById(R.id.annual_income_pref);
        annual_income_pref_img = findViewById(R.id.annual_income_pref_img);
        hobbiesLL = findViewById(R.id.hobbiesLL);
        hobbies = findViewById(R.id.hobbies);
        age_pref_ll = findViewById(R.id.age_pref_ll);
        age_pref_v = findViewById(R.id.age_pref_v);
        height_pref_v = findViewById(R.id.height_pref_v);
        marital_status_pref_v = findViewById(R.id.marital_status_pref_v);
        religion_caste_pref_v = findViewById(R.id.religion_caste_pref_v);
        mother_tongue_pref_v = findViewById(R.id.mother_tongue_pref_v);
        state_livein_pref_v = findViewById(R.id.state_livein_pref_v);
        diet_pref_v = findViewById(R.id.diet_pref_v);
        annual_income_v = findViewById(R.id.annual_income_v);
        gotra_v = findViewById(R.id.gotra_v);
        gotra = findViewById(R.id.gotra);
        gotra_RL = findViewById(R.id.gotra_RL);
        gotra_img = findViewById(R.id.gotra_img);
        country_img = findViewById(R.id.country_img);
        country = findViewById(R.id.country);
        countryRL = findViewById(R.id.countryRL);
        county_v = findViewById(R.id.county_v);
        city_v = findViewById(R.id.city_v);
        city_img = findViewById(R.id.city_img);
        city = findViewById(R.id.city);
        cityRL = findViewById(R.id.cityRL);
        educationRL = findViewById(R.id.educationRL);
        education_pref = findViewById(R.id.education_pref);
        education_img = findViewById(R.id.education_img);
        education_v = findViewById(R.id.education_v);
        occupation_v = findViewById(R.id.occupation_v);
        occupation_img = findViewById(R.id.occupation_img);
        occupation_pref = findViewById(R.id.occupation_pref);
        occupationRL = findViewById(R.id.occupationRL);
        her_him_pref = findViewById(R.id.her_him_pref);
        his_her_profile = findViewById(R.id.his_her_profile);
        login_user_profile_image = findViewById(R.id.login_user_profile_image);
        match_count = findViewById(R.id.match_count);
        state_livein_pref_img = findViewById(R.id.state_livein_pref_img);
        background_cv = findViewById(R.id.background_cv);
        background_txt = findViewById(R.id.background_txt);
        education_career_txt = findViewById(R.id.education_career_txt);
        education_career_cv = findViewById(R.id.education_career_cv);
        hobbies_cv = findViewById(R.id.hobbies_cv);
        you_ = findViewById(R.id.you_);
        img_count = findViewById(R.id.img_count);
        imgLL = findViewById(R.id.imgLL);
        back_btn = findViewById(R.id.back_btn);

        internetOffRL = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        try_again_txt = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        //visibility gone for self block and report
        btn_block_report.setVisibility(View.GONE);


        connect_btn_pb.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        if (i.getStringExtra("userid") != null) {
            people_userid = i.getStringExtra("userid");
        }

        user = SharedPrefManager.getInstance(ViewSelfProfileActivity.this).getUser();

        onlineLL.setVisibility(View.GONE);
        you_LL.setVisibility(View.GONE);

        fetchLoginProfileData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


    }

    @SuppressLint("RestrictedApi")
    @Override
    public void networkAvailable() {

//        fetchLoginProfileData();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                fetchLoginProfileData();

//                swipeRefreshLayout.setRefreshing(false);
            }
        });


        final Handler handler = new Handler();

        Runnable delayrunnable = new Runnable() {

            @Override
            public void run() {

                internetOffRL.setVisibility(View.GONE);
                couldnt_reach_internet_txt.setVisibility(View.GONE);

            }
        };
        handler.postDelayed(delayrunnable, 3000);


    }

    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(ViewSelfProfileActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try_again_txt.setVisibility(View.GONE);
                simpleProgressBar.setVisibility(View.VISIBLE);
                if (!isNetworkAvailable()) {
                    simpleProgressBar.postDelayed(new Runnable() {
                        public void run() {
                            couldnt_reach_internet_txt.postDelayed(new Runnable() {
                                public void run() {
                                    couldnt_reach_internet_txt.setVisibility(View.VISIBLE);
                                    try_again_txt.setVisibility(View.VISIBLE);
                                    simpleProgressBar.setVisibility(View.GONE);
                                    couldnt_reach_internet_txt.postDelayed(new Runnable() {
                                        public void run() {
                                            couldnt_reach_internet_txt.setVisibility(View.GONE);


                                        }
                                    }, 2000);
                                }
                            }, 2000);

                        }
                    }, 2000);
                } else {
                    networkUnavailable();
                }

            }
        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Api apiService = RetrofitClient.getApiService();

        if (item.getItemId() == R.id.add_to_shortlist) {
            Call<InsertResponse> userResponse = apiService.shortListProfile(String.valueOf(user.getUser_id()), fetchProfile1.getUser_id());
            userResponse.enqueue(new Callback<InsertResponse>() {
                @Override
                public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                    addShortList = response.body();

                    if (response.isSuccessful()) {
                        if (addShortList.getResid().equals("200")) {
                            Toast.makeText(ViewSelfProfileActivity.this, addShortList.getResMsg(), Toast.LENGTH_SHORT).show();

                            fetchProfileDataforShortlistUpdate();
                        } else {
                            Toast.makeText(ViewSelfProfileActivity.this, addShortList.getResMsg(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                @Override
                public void onFailure(Call<InsertResponse> call, Throwable t) {
                    System.out.println("msg1 my add shortlist******" + t.toString());
                    progressBar.dismiss();

                    if (!isNetworkAvailable(ViewSelfProfileActivity.this)) {
//                        Toast.makeText(ViewSelfProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                    } else {
//                        Toast.makeText(ViewSelfProfileActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return true;

        } else if (item.getItemId() == R.id.remove_from_shortlist) {
            Call<InsertResponse> userResponse1 = apiService.removeShortListProfile(String.valueOf(user.getUser_id()), fetchProfile1.getUser_id());
            userResponse1.enqueue(new Callback<InsertResponse>() {
                @Override
                public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                    removeShortList = response.body();

                    if (response.isSuccessful()) {
                        if (removeShortList.getResid().equals("200")) {
                            Toast.makeText(ViewSelfProfileActivity.this, removeShortList.getResMsg(), Toast.LENGTH_SHORT).show();

                            fetchProfileDataforShortlistUpdate();
                        } else {
                            Toast.makeText(ViewSelfProfileActivity.this, removeShortList.getResMsg(), Toast.LENGTH_SHORT).show();

                        }

                    }

                }

                @Override
                public void onFailure(Call<InsertResponse> call, Throwable t) {
                    System.out.println("msg1 my add removeShortList******" + t.toString());
                    progressBar.dismiss();

                    if (!isNetworkAvailable(ViewSelfProfileActivity.this)) {
//                        Toast.makeText(ViewSelfProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                    } else {
//                        Toast.makeText(ViewSelfProfileActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                    }

                }
            });
            return true;
        } else {
            return false;
        }
    }


 //   tanmay 27-12-2024

//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.add_to_shortlist:
//
//                Api apiService = RetrofitClient.getApiService();
//                Call<InsertResponse> userResponse = apiService.shortListProfile(String.valueOf(user.getUser_id()), fetchProfile1.getUser_id());
//                userResponse.enqueue(new Callback<InsertResponse>() {
//
//                    @Override
//                    public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
//                        addShortList = response.body();
//
//                        if (response.isSuccessful()) {
//                            if (addShortList.getResid().equals("200")) {
//                                Toast.makeText(ViewSelfProfileActivity.this, addShortList.getResMsg(), Toast.LENGTH_SHORT).show();
//
//                                fetchProfileDataforShortlistUpdate();
//                            } else {
//                                Toast.makeText(ViewSelfProfileActivity.this, addShortList.getResMsg(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<InsertResponse> call, Throwable t) {
//                        System.out.println("msg1 my add shortlist******" + t.toString());
//                        progressBar.dismiss();
//
//                        if (!isNetworkAvailable(ViewSelfProfileActivity.this)) {
//                            Toast.makeText(ViewSelfProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(ViewSelfProfileActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//                });
//
//
//                return true;
//
//            case R.id.remove_from_shortlist:
//
//                Api apiService1 = RetrofitClient.getApiService();
//                Call<InsertResponse> userResponse1 = apiService1.removeShortListProfile(String.valueOf(user.getUser_id()), fetchProfile1.getUser_id());
//                userResponse1.enqueue(new Callback<InsertResponse>() {
//
//                    @Override
//                    public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
//                        removeShortList = response.body();
//
//                        if (response.isSuccessful()) {
//                            if (removeShortList.getResid().equals("200")) {
//                                Toast.makeText(ViewSelfProfileActivity.this, removeShortList.getResMsg(), Toast.LENGTH_SHORT).show();
//
//                                fetchProfileDataforShortlistUpdate();
//                            } else {
//                                Toast.makeText(ViewSelfProfileActivity.this, removeShortList.getResMsg(), Toast.LENGTH_SHORT).show();
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
//                        if (!isNetworkAvailable(ViewSelfProfileActivity.this)) {
//                            Toast.makeText(ViewSelfProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(ViewSelfProfileActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
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


    public void showAllDetail(FetchProfile fetchProfileofUsers) {
        go_premium_LL.setVisibility(View.GONE);


        /*address*/

        permanant_address.setText(fetchProfileofUsers.getPer_address());
        current_address.setText(fetchProfileofUsers.getCurrent_address());
        work_location.setText(fetchProfileofUsers.getOfc_loc());
        seeAllAddressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ViewSelfProfileActivity.this, AddressInDetailActivity.class);
                i.putExtra("userdata", fetchProfileofUsers);
                startActivity(i);
            }
        });
        /*address*/

        /*basic detail*/
        gender.setText(fetchProfileofUsers.getGender());
        gothra.setText("Gothra- " + fetchProfileofUsers.getGotra());
        weight.setText("Weight- " + fetchProfileofUsers.getWeight());
        lifestyle.setText("Addiction- " + fetchProfileofUsers.getLifestyle_name());
        blood_group.setText(fetchProfileofUsers.getBloodgroup_name());
        color_complexion.setText(fetchProfileofUsers.getColor_complex_name());
        if (fetchProfileofUsers.getHandicap().equals("No")) {
            handicap.setText(fetchProfileofUsers.getHandicap());

        } else {
            if (!fetchProfileofUsers.getHandicap_name().equals("Not Specified") || !fetchProfileofUsers.getHandicap_name().trim().equals(""))
                handicap.setText(fetchProfileofUsers.getHandicap() + "  (" + fetchProfileofUsers.getHandicap_name() + ")");
            else
                handicap.setText(fetchProfileofUsers.getHandicap());

        }
        /*basic detail*/

        /*family*/
        seeAllfamilyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewSelfProfileActivity.this, ViewFamilyInDetailActivity.class);
                i.putExtra("userdata", fetchProfileofUsers);
                startActivity(i);
            }
        });
        /*family*/

        /*partner pref*/
        pref_marital_status.setText(fetchProfileofUsers.getPref_marital_name());

        if (!fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && !fetchProfileofUsers.getPref_ageto().equals("Not Specified")) {
            if (!fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") && !fetchProfileofUsers.getPref_ageto().equals("Doesn't Matter")) {
                pref_age.setText(fetchProfileofUsers.getPref_agefrom() + " to " + fetchProfileofUsers.getPref_ageto());
            } else if (fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") && !fetchProfileofUsers.getPref_ageto().equals("Doesn't Matter")) {
                pref_age.setText("Upto " + fetchProfileofUsers.getPref_ageto());

            } else if (!fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter")) {
                pref_age.setText("from " + fetchProfileofUsers.getPref_agefrom());

            }


        } else {
            if (!fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && fetchProfileofUsers.getPref_ageto().equals("Not Specified")) {
                pref_age.setText(fetchProfileofUsers.getPref_agefrom());

            }

            if (fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && !fetchProfileofUsers.getPref_ageto().equals("Not Specified")) {
                pref_age.setText(fetchProfileofUsers.getPref_ageto());

            }


        }
        if (fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") && fetchProfileofUsers.getPref_ageto().equals("Doesn't Matter")) {
            pref_age.setText("Doesn't Matter");

        }

        if (fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && fetchProfileofUsers.getPref_ageto().equals("Not Specified")) {
            pref_age.setText("Not Specified");

        }
        pref_education_type.setText(fetchProfileofUsers.getPrefmain_edu_name());
        pref_education.setText(fetchProfileofUsers.getPref_edu_name());

        seeAllPrefLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewSelfProfileActivity.this, PartnerPrefInDetailActivity.class);
                i.putExtra("userdata", fetchProfileofUsers);
                startActivity(i);
            }
        });
        /*partner pref*/


    }


    public void fetchProfileData() {

        System.out.println("login userid=====" + user.getUser_id());
        System.out.println("people_userid=====" + people_userid);

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.peopleDetails(String.valueOf(user.getUser_id()), String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfile = response.body();

                if (fetchProfile != null) {
                    if (fetchProfile.getResid().equals("200")) {


                        lifestyle_appearance_LL.setVisibility(View.VISIBLE);
                        basic_block_LL.setVisibility(View.VISIBLE);
                        partner_prefLL.setVisibility(View.VISIBLE);
                        addressLL.setVisibility(View.VISIBLE);


                        likeRL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(ViewSelfProfileActivity.this, LikeShowActivity.class);
                                i.putExtra("user_id", fetchProfile.getUser_id());
                                startActivity(i);
                            }
                        });


                        if (fetchProfile.getImages().size() != 0) {

                            top_cv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (verified_txtLL.getVisibility() == View.VISIBLE) {
                                        verified_txtLL.setVisibility(View.GONE);

                                    } else {
                                        Intent i = new Intent(ViewSelfProfileActivity.this, ImagesActivity.class);
                                        if (fetchProfile.getImages().size() != 0)
                                            i.putExtra("mylist", (Serializable) fetchProfile.getImages());
                                        i.putExtra("isCallFromSelfProfile", true);
                                        startActivity(i);
                                    }

                                }
                            });


                            imgLL.setVisibility(View.VISIBLE);
                            img_count.setText(String.valueOf(fetchProfile.getImages().size()));

                            imgLL.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(ViewSelfProfileActivity.this, ImagesActivity.class);
                                    if (fetchProfile.getImages().size() != 0)
                                        i.putExtra("mylist", (Serializable) fetchProfile.getImages());
                                    i.putExtra("isCallFromSelfProfile", true);
                                    startActivity(i);
                                }
                            });


                        } else {
                            imgLL.setVisibility(View.GONE);
                        }

                        acc_id.setText(fetchProfile.getAcc_id());

                        if (fetchProfile.getImages() != null) {
                            if (fetchProfile.getImages().size() == 0) {
                                user_img.setVisibility(View.GONE);
                                default_Avtar_LL.setVisibility(View.VISIBLE);


                                if (fetchProfile.getGender().equals("Female")) {
                                    img2.setImageResource(R.drawable.female_avtar);
                                } else {
                                    img2.setImageResource(R.drawable.male_avtar);
                                }

                            } else {

                                default_Avtar_LL.setVisibility(View.GONE);
                                user_img.setVisibility(View.VISIBLE);
                                Glide.with(getApplicationContext()).load(fetchProfile.getImages().get(0).getImgs()).into(user_img);

                            }
                        } else {

                            user_img.setVisibility(View.GONE);
                            default_Avtar_LL.setVisibility(View.VISIBLE);


                            if (fetchProfile.getGender().equals("Female")) {
                                img2.setImageResource(R.drawable.female_avtar);
                            } else {
                                img2.setImageResource(R.drawable.male_avtar);
                            }
                        }


                        name.setText(fetchProfile.getFname());

                        if (!fetchProfile.getAge().contentEquals("Not Specified") &&
                                !fetchProfile.getHeight().contentEquals("Not Specified")) {
                            age_height.setText(String.format("%s yrs,%s", fetchProfile.getAge(), fetchProfile.getHeight()));

                        } else if (!fetchProfile.getAge().contentEquals("Not Specified") &&
                                fetchProfile.getHeight().contentEquals("Not Specified")) {
                            age_height.setText(fetchProfile.getAge() + " yrs");

                        } else if (fetchProfile.getAge().contentEquals("Not Specified") &&
                                !fetchProfile.getHeight().contentEquals("Not Specified")) {
                            age_height.setText(fetchProfile.getHeight());

                        } else {
                            age_height.setText(" ");

                        }


                        if (!fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
                                !fetchProfile.getCaste_name().contentEquals("Not Specified")) {
                            lang_caste.setText(String.format("%s,%s", fetchProfile.getMothertounge_name(), fetchProfile.getCaste_name()));

                        } else if (!fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
                                fetchProfile.getCaste_name().contentEquals("Not Specified")) {
                            lang_caste.setText(fetchProfile.getMothertounge_name());

                        } else if (fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
                                !fetchProfile.getCaste_name().contentEquals("Not Specified")) {
                            lang_caste.setText(fetchProfile.getCaste_name());

                        } else {
                            lang_caste.setText("");

                        }


                        System.out.println("fetchProfile.getOccupation_name()-------" + fetchProfile.getOccupation_name());

                        if (!fetchProfile.getOccupation_name().contentEquals("Not Specified")) {
                            job_top.setText(fetchProfile.getOccupation_name());

                        } else {
                            job_top.setText("");

                        }

                        if (!fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
                                !fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
                            address_top.setText(String.format("%s,%s", fetchProfile.getCurr_ditrict_name(), fetchProfile.getCurr_state_name()));

                        } else if (!fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
                                fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
                            address_top.setText(fetchProfile.getCurr_ditrict_name());

                        } else if (fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
                                !fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
                            address_top.setText(fetchProfile.getCurr_state_name());

                        } else {
                            address_top.setText("");

                        }

                        like_count.setText(fetchProfile.getLikecount());
                        about_txt.setText(fetchProfile.getFname() + " " + fetchProfile.getMname() + " " + fetchProfile.getLname());
                        about_lock.setVisibility(View.GONE);
                        if (fetchProfile.getProfile_name().equals("Not Specified")) {
                            profile_created_for.setVisibility(View.GONE);
                        } else {
                            profile_created_for.setVisibility(View.VISIBLE);

                            profile_created_for.setText("| Profile Created for " + fetchProfile.getProfile_name());

                        }
                        about_desc.setText("Hello, here are a few lines to help you know me better.\n" + fetchProfile.getAbout_me());

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                        about_desc.setLayoutParams(params);

                        arrow1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                arrow1.setVisibility(View.GONE);
                                arrow2.setVisibility(View.VISIBLE);

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                about_desc.setLayoutParams(params);


                            }
                        });

                        arrow2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                arrow1.setVisibility(View.VISIBLE);
                                arrow2.setVisibility(View.GONE);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                                about_desc.setLayoutParams(params);
                            }
                        });


                        other_education_lock.setVisibility(View.GONE);
                        occupation_lock.setVisibility(View.GONE);
                        post_name_lock.setVisibility(View.GONE);


                        education.setText(fetchProfile.getMain_education_name() + " (" + fetchProfile.getEducation_name() + ")");
                        college_name.setText(fetchProfile.getCollege_name());
                        manglik.setText(fetchProfile.getManglik());
                        other_education.setText(fetchProfile.getOther_education());
                        occupation.setText(fetchProfile.getMain_occupation_name() + " (" + fetchProfile.getOccupation_name() + ")");
                        post_name.setText(fetchProfile.getPost_name());
                        income.setText(fetchProfile.getYearly_salary());

                        hobbies_lock.setVisibility(View.GONE);
                        hobbies.setText(fetchProfile.getHobbies());

                        conversation_txt.setVisibility(View.GONE);
                        conversation_cv.setVisibility(View.GONE);


                        bg_religion_lang_lock.setVisibility(View.GONE);
                        background_go_premium_now_btn_LL.setVisibility(View.GONE);
                        if (fetchProfile.getReligion_name().equals("Not Specified") && fetchProfile.getMothertounge_name().equals("Not Specified")) {
                            bg_religion_lang_LL.setVisibility(View.GONE);
                            background_cv.setVisibility(View.GONE);
                            background_txt.setVisibility(View.GONE);
                        } else {
                            bg_religion_lang_LL.setVisibility(View.VISIBLE);
                            background_cv.setVisibility(View.VISIBLE);
                            background_txt.setVisibility(View.VISIBLE);

                            if (fetchProfile.getReligion_name().equals("Not Specified")) {
                                bg_religion_lang.setText("");

                            } else {
                                bg_religion_lang.setText(fetchProfile.getReligion_name());

                            }

                            if (fetchProfile.getMothertounge_name().equals("Not Specified")) {
                                bg_religion_lang.setText("");

                            } else {
                                bg_religion_lang.setText(fetchProfile.getMothertounge_name());

                            }


                            if (!fetchProfile.getReligion_name().equals("Not Specified") && !fetchProfile.getMothertounge_name().equals("Not Specified")) {
                                bg_religion_lang.setText(fetchProfile.getReligion_name() + ", " + fetchProfile.getMothertounge_name());

                            }

                        }

                        /*caste*/
                        if (fetchProfile.getCaste_name().equals("Not Specified")) {
                            bg_caste_LL.setVisibility(View.GONE);
                        } else {
                            bg_caste_LL.setVisibility(View.VISIBLE);

                            if (!fetchProfile.getSubcaste_name().equals("Not Specified")) {
                                bg_caste.setText(fetchProfile.getCaste_name() + "  (" + fetchProfile.getSubcaste_name() + ")");
                            } else {
                                bg_caste.setText(fetchProfile.getCaste_name());

                            }

                        }
                        /*caste*/

                        /*sampraday & rashi*/
                        sampraday_txt.setText("Sampraday: " + fetchProfile.getSampraday_name());

                        rashi_txt.setText("Rashi: " + fetchProfile.getRashi_name());
                        /*sampraday & rashi*/

                        /*family*/

                        if (!fetchProfile.getFamily_class().equals("Not Specified") && !fetchProfile.getPer_ditrict_name().equals("Not Specified") &&
                                !fetchProfile.getFather_occ_name().equals("Not Specified") && !fetchProfile.getMother_occ().equals("Not Specified") &&
                                !fetchProfile.getBro_count().equals("Not Specified") && !fetchProfile.getSis_count().equals("Not Specified")) {

                            family_cv.setVisibility(View.VISIBLE);
                            family_lbl.setVisibility(View.VISIBLE);

                            if (fetchProfile.getGender().equals("Male")) {

                                String familt_str = "Ours is " + fetchProfile.getFamily_class() + " family, originally from " +
                                        fetchProfile.getPer_ditrict_name() + ". His father is " + fetchProfile.getFather_occ_name() + " and mother is a " + fetchProfile.getMother_occ() +
                                        ". He has " + fetchProfile.getBro_count() + " Brother and " + fetchProfile.getSis_count() + " sister.";

                                family.setText(familt_str);

                            } else if (fetchProfile.getGender().equals("Female")) {
                                String familt_str = "Ours is " + fetchProfile.getFamily_class() + " family, originally from " +
                                        fetchProfile.getPer_ditrict_name() + ". Hes father is " + fetchProfile.getFather_occ_name() + " and mother is a " + fetchProfile.getMother_occ() +
                                        ". She has " + fetchProfile.getBro_count() + " Brother and " + fetchProfile.getSis_count() + " sister.";

                                family.setText(familt_str);
                            }

                        } else if (!fetchProfile.getFamily_class().equals("Not Specified") && !fetchProfile.getPer_ditrict_name().equals("Not Specified") &&
                                !fetchProfile.getFather_occ_name().equals("Not Specified") && !fetchProfile.getMother_occ().equals("Not Specified")) {

                            String familt_str = "Ours is " + fetchProfile.getFamily_class() + " family, originally from " +
                                    fetchProfile.getPer_ditrict_name() + ". My father is " + fetchProfile.getFather_occ_name() + " and mother is a " + fetchProfile.getMother_occ() + ".";

                            family.setText(familt_str);

                        } else {
                            family_cv.setVisibility(View.GONE);
                            family_lbl.setVisibility(View.GONE);
                        }


                        /*end family*/
                        seeAllfamilyLL.setVisibility(View.VISIBLE);


                        contact_detailLL.setVisibility(View.VISIBLE);
                        contact_no_lock.setVisibility(View.GONE);
                        contact_no.setText(fetchProfile.getMobile_no());

                        whatsapp_no_lock.setVisibility(View.GONE);
                        whatsapp_no.setText(fetchProfile.getWtsapp_no());

                        email_lock.setVisibility(View.GONE);
                        email.setText(fetchProfile.getEmail());

                        age_height_color.setText(String.format("%s yrs, %s", fetchProfile.getAge(), fetchProfile.getHeight()));

//                        birthday_date.setText("Born on " + fetchProfile.getDob());
                        if (fetchProfile.getBirth_time() != null && !fetchProfile.getBirth_time().equals("Not Specified")) {
                            birthday_date.setText("Born on " + fetchProfile.getDob());

                        } else {
                            birthday_date.setText("Born on " + fetchProfile.getDob());

                        }
                        birthday_lock.setVisibility(View.GONE);


                      if (fetchProfile.getBirth_time() != null && !fetchProfile.getBirth_time().equals("Not Specified")) {
                            birthday_time.setText("Born time " + fetchProfile.getBirth_time() );

                        } else {
                            birthday_time.setText("Born time " + fetchProfile.getBirth_time());

                        }
                        birthday_time_lock.setVisibility(View.GONE);



                        marital_status.setText(fetchProfile.getMarital_status_name());

                        marital_status_lock.setVisibility(View.GONE);

                        address_lifestyle.setText(fetchProfile.getCurr_ditrict_name());
                        job_lifestyle.setText(fetchProfile.getOccupation_name());

                        diet.setText(fetchProfile.getDietry_name());
                        diet_lock.setVisibility(View.GONE);

                        contact_go_premium_now_btn_LL.setVisibility(View.GONE);

                        showAllDetail(fetchProfile);
                    }
                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());

                if (!isNetworkAvailable(ViewSelfProfileActivity.this)) {
//                    Toast.makeText(ViewSelfProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(ViewSelfProfileActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void fetchLoginProfileData() {

        progressBar = ProgressDialog.show(ViewSelfProfileActivity.this, "", "Please Wait...");


        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {

                swipeRefreshLayout.setRefreshing(false);
                fetchProfileLoginUser = response.body();
                progressBar.dismiss();
                System.out.println("fetchProfile-------------" + new Gson().toJson(fetchProfileLoginUser));

                if (response.isSuccessful()) {

                    fetchProfileData();
                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                progressBar.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                if (!isNetworkAvailable(ViewSelfProfileActivity.this)) {
//                    Toast.makeText(ViewSelfProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(ViewSelfProfileActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

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


    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void fetchProfileDataforShortlistUpdate() {

        System.out.println("login userid 111=====" + user.getUser_id());
        System.out.println("people_userid 111=====" + people_userid);

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.peopleDetails(people_userid, String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfile1 = response.body();


                if (response.isSuccessful()) {

                    System.out.println("user id=====" + fetchProfile1.getUser_id());


                    if (!fetchProfile1.getIs_shortlist().equals("1")) {
                        option_LL.setVisibility(View.VISIBLE);
                        option_LL1.setVisibility(View.GONE);
                    } else {
                        option_LL.setVisibility(View.GONE);
                        option_LL1.setVisibility(View.VISIBLE);
                    }

                    option_LL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupMenu popup = new PopupMenu(ViewSelfProfileActivity.this, view);
                            popup.setOnMenuItemClickListener(ViewSelfProfileActivity.this);

                            MenuInflater inflater = popup.getMenuInflater();
                            inflater.inflate(R.menu.option_menu, popup.getMenu());

                            popup.show();
                        }
                    });


                    option_LL1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupMenu popup = new PopupMenu(ViewSelfProfileActivity.this, view);
                            popup.setOnMenuItemClickListener(ViewSelfProfileActivity.this);

                            MenuInflater inflater = popup.getMenuInflater();
                            inflater.inflate(R.menu.option_menu_1, popup.getMenu());

                            popup.show();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my shortlist check******" + t.toString());

                if (!isNetworkAvailable(ViewSelfProfileActivity.this)) {
//                    Toast.makeText(ViewSelfProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(ViewSelfProfileActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


}
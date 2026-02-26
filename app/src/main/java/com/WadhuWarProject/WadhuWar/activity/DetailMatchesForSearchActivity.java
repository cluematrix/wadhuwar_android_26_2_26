package com.WadhuWarProject.WadhuWar.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
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
import com.WadhuWarProject.WadhuWar.model.CommonResponse;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.LikeProfileResponse;
import com.WadhuWarProject.WadhuWar.model.SendProfileViewNoti;
import com.WadhuWarProject.WadhuWar.model.TabMyMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
//import com.github.florent37.shapeofview.shapes.DiagonalView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailMatchesForSearchActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener, PopupMenu.OnMenuItemClickListener {


    int a11_height, a12_height, a21_height, a22_height, a23_height;

    Toolbar toolbar;
    UserData user;
    double before_below, after_Above;
    InsertResponse connect_response;
    int before_to_pref_login_user_final_inch, after_to_pref_login_user_final_inch, profile_separate_height, profile_separate_height2;
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
    LinearLayout upgrade_now_btn, btn_block_report;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;

    RelativeLayout age_pref_ll, marital_status_prefRL, height_RL, height_to_RL, religion_caste_prefRL, mother_tongue_prefRL, state_livein_prefRL, diet_prefRL,
            annual_income_prefRL, gotra_RL, countryRL, cityRL, educationRL, occupationRL, color_complex_pref_RL, caste_pref_RL, sub_caste_pref_RL, lifestyle_pref_RL,
            sampraday_pref_RL, education_type_pref_RL, occupation_type_pref_RL;

    TextView name, age_height, lang_caste, job, address, about_txt, acc_id, profile_created_for, about_desc, not_see_txt,
            age_height_color, birthday_date, marital_status, address_lifestyle, job_lifestyle, diet, lang_status, caste_status,
            state_Status, conversation_txt, bg_religion_lang, bg_caste, family, family_lbl, education, other_education, occupation, post_name, income,
            collgeName, hobbies,
            age_from_to_pref, marital_status_pref, religion_caste_pref, mother_tongue_pref, state_livein_pref, diet_pref,
            annual_income_pref, height_pref, height_to_pref, gotra, country, city, education_pref, occupation_pref, her_him_pref, match_count,
            background_txt, education_career_txt, hobbies_txt, you_, img_count, job_top, address_top, color_complex_pref, caste_pref, sub_caste_pref, lifestyle_pref,
            sampraday_pref, contact_no, whatsapp_no, email, work_location, current_address, permanant_address, gender, gothra, manglik, weight,
            color_complexion, handicap, lifestyle, blood_group, pref_marital_status, pref_age, pref_education_type, pref_education, swip_name_top, connect_now_txt, like_profile_txt,
            connect_btn_bottom_txt, contact_connect_txt, arrow1, arrow2, sampraday_txt,
            rashi_txt, education_type_pref, occupation_type_pref;

    ImageView user_img, connect_now_btn, about_lock, lifestyle_lock, lang_status_img, caste_status_img, state_Status_img, age_from_to,
            height_pref_img, height_to_pref_img, marital_status_pref_img, religion_caste_pref_img, mother_tongue_pref_img, diet_pref_img,
            annual_income_pref_img, gotra_img, country_img, city_img, education_img, occupation_img, login_user_profile_image, state_livein_pref_img,
            color_complex_pref_img, caste_pref_img, sub_caste_pref_img, lifestyle_pref_img, sampraday_pref_img, birthday_lock, marital_status_lock, diet_lock,
            bg_religion_lang_lock, other_education_lock, occupation_lock, post_name_lock,
            hobbies_lock, contact_no_lock, whatsapp_no_lock, email_lock, education_type_pref_img, occupation_type_pref_img;

    LinearLayout onlineLL, upgrade_now, state_Status_LL, caste_status_LL, lang_status_LL, bg_religion_lang_LL, bg_caste_LL, educationLL, other_educationLL,
            occupationLL, post_nameLL, incomeLL, collegeNameLL, hobbiesLL, imgLL, bottom_like_connectLL, verified_LL, verified_txtLL, premiumLL, option_LL, option_LL1, go_premium_LL,
            contact_detailLL, seeAllAddressLL, addressLL, seeAllfamilyLL, seeAllPrefLL, view_all_detail, lifestyle_appearance_LL, basic_block_LL, education_careerLL,
            conversation_background_familyLL, partner_prefLL, partner_pref_compareLL, connect_now_topLL, contact_connect_now_btn_LL, contact_connect_now_btn,
            contact_go_premium_now_btn_LL, contact_go_premium_now_btn, background_go_premium_now_btn_LL, background_go_premium_now_btn,
            education_go_premium_now_btn_LL, education_go_premium_now_btn, hobbies_go_premium_now_btn_LL, hobbies_go_premium_now_btn, swip_topLL, connect_btn_LL,
            connect_btn_bottomLL, upgrade_txt, chat_btn, whatapp_btn, call_btn, bottom_contactLL, chat_btn1, whatapp_btn1, call_btn1, contact_topLL,
            sampradayLL, rashiLL;

    CardView conversation_cv, family_cv, background_cv, education_career_cv, hobbies_cv;
    String people_userid;

    View age_pref_v, height_pref_v, height_to_pref_v, marital_status_pref_v, religion_caste_pref_v, mother_tongue_pref_v, state_livein_pref_v,
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
    ImageView verifiedL;
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
    TextView like_count, sub_caste_top;
    LinearLayout sub_caste_LL;
    InsertResponse acceptResponse;

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
        setContentView(R.layout.activity_detail_maches_for_search);

        View root1 = findViewById(R.id.featurefragmentCor);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
//        setContentView(R.layout.activity_detail_maches_for_search);

        btn_block_report = findViewById(R.id.btn_block_report);
        rashiLL = findViewById(R.id.rashiLL);
        sampradayLL = findViewById(R.id.sampradayLL);
        rashi_txt = findViewById(R.id.rashi_txt);
        sampraday_txt = findViewById(R.id.sampraday_txt);
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
        pref_education_type = findViewById(R.id.pref_education_type);
        pref_marital_status = findViewById(R.id.pref_marital_status);
        pref_age = findViewById(R.id.pref_age);
        seeAllfamilyLL = findViewById(R.id.seeAllfamilyLL);
        handicap = findViewById(R.id.handicap);
        color_complexion = findViewById(R.id.color_complexion);
        blood_group = findViewById(R.id.blood_group);
        lifestyle = findViewById(R.id.lifestyle);
        weight = findViewById(R.id.weight);
        gothra = findViewById(R.id.gothra);
        manglik = findViewById(R.id.manglik);
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
        verifiedL = findViewById(R.id.verifiedL);
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
        bg_religion_lang = findViewById(R.id.bg_religion_lang);
        bg_caste = findViewById(R.id.bg_caste);
        bg_religion_lang_LL = findViewById(R.id.bg_religion_lang_LL);
        bg_caste_LL = findViewById(R.id.bg_caste_LL);
        family = findViewById(R.id.family);
        family_lbl = findViewById(R.id.family_lbl);
        family_cv = findViewById(R.id.family_cv);
        education = findViewById(R.id.education);
        other_education = findViewById(R.id.other_education);
        occupation = findViewById(R.id.occupation);
        post_name = findViewById(R.id.post_name);
        collgeName = findViewById(R.id.college_name);
        income = findViewById(R.id.income);
        educationLL = findViewById(R.id.educationLL);
        education_type_pref_RL = findViewById(R.id.education_type_pref_RL);
        education_type_pref = findViewById(R.id.education_type_pref);
        other_educationLL = findViewById(R.id.other_educationLL);
        occupation_type_pref_RL = findViewById(R.id.occupation_type_pref_RL);
        occupation_type_pref = findViewById(R.id.occupation_type_pref);
        occupationLL = findViewById(R.id.occupationLL);
        post_nameLL = findViewById(R.id.post_nameLL);
        collegeNameLL = findViewById(R.id.collegeNameLL);
        incomeLL = findViewById(R.id.incomeLL);
        age_from_to_pref = findViewById(R.id.age_from_to_pref);
        marital_status_pref = findViewById(R.id.marital_status_pref);
        religion_caste_pref = findViewById(R.id.religion_caste_pref);
        state_livein_pref = findViewById(R.id.state_livein_pref);
        annual_income_pref = findViewById(R.id.annual_income_pref);
        age_from_to = findViewById(R.id.age_from_to);
        marital_status_prefRL = findViewById(R.id.marital_status_prefRL);
        height_RL = findViewById(R.id.height_RL);
        height_to_RL = findViewById(R.id.height_to_RL);
        height_pref = findViewById(R.id.height_pref);
        height_to_pref = findViewById(R.id.height_to_pref);
        height_pref_img = findViewById(R.id.height_pref_img);
        height_to_pref_img = findViewById(R.id.height_to_pref_img);
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
        height_to_pref_v = findViewById(R.id.height_to_pref_v);
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
        education_type_pref_img = findViewById(R.id.education_type_pref_img);
        occupation_type_pref_img = findViewById(R.id.occupation_type_pref_img);
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

        user = SharedPrefManager.getInstance(DetailMatchesForSearchActivity.this).getUser();
        System.out.println("222222check test-------------->>>>>>>>>>");

        fetchLoginProfileData();//my data


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


        btn_block_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(DetailMatchesForSearchActivity.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.profile_block_reject, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.block_profile) {
                            showConfirmationDialogBoxBlock();
                        }

                        if (item.getItemId() == R.id.report_profile) {
                            Intent i = new Intent(DetailMatchesForSearchActivity.this, ReasonForReportActivity.class);
                            i.putExtra("userid", people_userid);
                            i.putExtra("Fname", fetchProfile.getFname());
                            i.putExtra("Mname", fetchProfile.getMname());
                            i.putExtra("Lname", fetchProfile.getLname());
                            i.putExtra("isCallFromComment", false);
                            startActivity(i);
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void showConfirmationDialogBoxBlock() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailMatchesForSearchActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Blocked Member will not able to view your Profile or contact you on Wadhuwar");
        builder.setTitle("Are you sure you want to Block this profile?");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
            blockUser();
        });

        builder.setNegativeButton("CANCEL", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void networkAvailable() {

        System.out.println("11111check test-------------->>>>>>>>>>");


//        fetchLoginProfileData();//my data

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                view_all_detail.setVisibility(View.GONE);
                contact_detailLL.setVisibility(View.GONE);
                go_premium_LL.setVisibility(View.GONE);
                lifestyle_appearance_LL.setVisibility(View.GONE);
                basic_block_LL.setVisibility(View.GONE);
                seeAllfamilyLL.setVisibility(View.GONE);
//                                education_careerLL.setVisibility(View.VISIBLE);
//                                conversation_background_familyLL.setVisibility(View.VISIBLE);

                partner_prefLL.setVisibility(View.GONE);
                addressLL.setVisibility(View.GONE);
                partner_pref_compareLL.setVisibility(View.GONE);


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

//                Toast.makeText(DetailMatchesForSearchActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

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
        int itemId = item.getItemId();

        if (itemId == R.id.add_to_shortlist) {
            addToShortlist();
            return true;
        }
        else if (itemId == R.id.remove_from_shortlist) {
            removeFromShortlist();
            return true;
        }
        else {
            return false;
        }
    }

    private void addToShortlist() {
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> call = apiService.shortListProfile(
                String.valueOf(user.getUser_id()),
                fetchProfile1.getUser_id()
        );

        call.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    InsertResponse result = response.body();
                    String message = result.getResMsg();
                    Toast.makeText(DetailMatchesForSearchActivity.this, message, Toast.LENGTH_SHORT).show();

                    if ("200".equals(result.getResid())) {
                        fetchProfileDataforShortlistUpdate();
                    }
                } else {
                    Toast.makeText(DetailMatchesForSearchActivity.this, "Failed to add to shortlist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                handleNetworkError(t);
            }
        });
    }

    private void removeFromShortlist() {
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> call = apiService.removeShortListProfile(
                String.valueOf(user.getUser_id()),
                fetchProfile1.getUser_id()
        );

        call.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    InsertResponse result = response.body();
                    String message = result.getResMsg();
                    Toast.makeText(DetailMatchesForSearchActivity.this, message, Toast.LENGTH_SHORT).show();

                    if ("200".equals(result.getResid())) {
                        fetchProfileDataforShortlistUpdate();
                    }
                } else {
                    Toast.makeText(DetailMatchesForSearchActivity.this, "Failed to remove from shortlist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                handleNetworkError(t);
            }
        });
    }

    private void handleNetworkError(Throwable t) {
        Log.e("ShortlistError", "API call failed: " + t.getMessage());
        progressBar.dismiss();

        if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
            Toast.makeText(DetailMatchesForSearchActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailMatchesForSearchActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
        }
    }
    public void showAllDetail(FetchProfile fetchProfileofUsers) {

        go_premium_LL.setVisibility(View.GONE);

        /*contact*/

        contact_no_lock.setVisibility(View.GONE);
        contact_no.setText(fetchProfileofUsers.getMobile_no());

        whatsapp_no_lock.setVisibility(View.GONE);
        whatsapp_no.setText(fetchProfileofUsers.getWtsapp_no());

        email_lock.setVisibility(View.GONE);
        email.setText(fetchProfileofUsers.getEmail());

        contact_connect_now_btn_LL.setVisibility(View.GONE);

     /*   if(fetchProfileofUsers.getIs_connected().equals("1")){
            contact_no_lock.setVisibility(View.GONE);
            contact_no.setText(fetchProfileofUsers.getMobile_no());

            whatsapp_no_lock.setVisibility(View.GONE);
            whatsapp_no.setText(fetchProfileofUsers.getWtsapp_no());

            email_lock.setVisibility(View.GONE);
            email.setText(fetchProfileofUsers.getEmail());

            contact_connect_now_btn_LL.setVisibility(View.GONE);

        }
        else {
            contact_no_lock.setVisibility(View.VISIBLE);
            contact_no.setText("******");

            whatsapp_no_lock.setVisibility(View.VISIBLE);
            whatsapp_no.setText("******");

            email_lock.setVisibility(View.VISIBLE);
            email.setText("******");

            contact_connect_now_btn_LL.setVisibility(View.VISIBLE);
            contact_connect_now_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Toast.makeText(DetailMatchesActivity.this,"Connect Now Click ",Toast.LENGTH_SHORT).show();


                    connectApi(fetchProfileofUsers.getUser_id(),String.valueOf(user.getUser_id()),view);


                }
            });

        }*/


        /*end contact*/

        /*address*/

        permanant_address.setText(fetchProfileofUsers.getPer_address());
        current_address.setText(fetchProfileofUsers.getCurrent_address());
        work_location.setText(fetchProfileofUsers.getOfc_loc());
        seeAllAddressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetailMatchesForSearchActivity.this, AddressInDetailActivity.class);
                i.putExtra("userdata", fetchProfileofUsers);
                startActivity(i);
            }
        });
        /*address*/

        /*basic detail*/
        gender.setText(fetchProfileofUsers.getGender());
        gothra.setText("Gothra- " + fetchProfileofUsers.getGotra());
        manglik.setText("Manglik- " + fetchProfileofUsers.getManglik());
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
                Intent i = new Intent(DetailMatchesForSearchActivity.this, FamilyInDetailActivity.class);
                i.putExtra("userdata", fetchProfileofUsers);
                startActivity(i);
            }
        });
        /*family*/

        /*partner pref*/
        pref_marital_status.setText(fetchProfileofUsers.getPref_marital_name());
        System.out.println("fetchProfileofUsers.getPref_marital_name()--------" + fetchProfileofUsers.getPref_marital_name());
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
        if (fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && fetchProfileofUsers.getPref_ageto().equals("Not Specified")) {
            pref_age.setText("Not Specified");
        }

        if (fetchProfileofUsers.getPrefmain_edu_name().equals("Not Specified")) {
            pref_education_type.setText("Not Specified");
        } else {
            pref_education_type.setText(fetchProfileofUsers.getPrefmain_edu_name());
        }

        if (fetchProfileofUsers.getPref_edu_name().equals("Not Specified")) {
            pref_education.setText("Not Specified");
        } else {
            pref_education.setText(fetchProfileofUsers.getPref_edu_name());
        }


        //remove hardcoded education type
        // pref_education.setText(fetchProfileofUsers.getPref_edu_name() + " (M.C.A.)");

        seeAllPrefLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailMatchesForSearchActivity.this, PartnerPrefInDetailActivity.class);
                i.putExtra("userdata", fetchProfileofUsers);
                startActivity(i);
            }
        });
        /*partner pref*/

    }

    public void fetchProfileData() {

        System.out.println("login userid=====" + user.getUser_id());
        System.out.println(" people_userid=====" + people_userid);

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.peopleDetails(people_userid, String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfile = response.body();


                if (fetchProfile != null) {

                    if (response.isSuccessful()) {

                        System.out.println("check dectiviate or not=========>>>" + fetchProfile.getResid());
                        System.out.println("check getAccount_verify or not=========>>>" + fetchProfile.getAccount_verify());

                        sentViewNotification(people_userid,user.getUser_id());

                        if (fetchProfile.getResid().equals("200")) {


                            if (fetchProfile.getAlready_seen().equals("0")) {
                                view_all_detail.setBackgroundResource(R.drawable.curve_blue_btn_bg);
                            } else {
                                view_all_detail.setBackgroundResource(R.drawable.curve_green_btn_bg);
                            }

                            like_count.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(DetailMatchesForSearchActivity.this, LikeShowActivity.class);
                                    i.putExtra("user_id", fetchProfile.getUser_id());
                                    startActivity(i);
                                }
                            });

                            System.out.println("like or unlike--------------" + fetchProfile.getIslike());

                            if (fetchProfile.getIslike().equals("1")) {
                                likeButton.setLiked(true);
                            } else {
                                likeButton.setLiked(false);
                            }


                            like_count.setText(fetchProfile.getLikecount() + " likes");

                            /*-----like unlike functionality------*/
                            likeButton.setOnLikeListener(new OnLikeListener() {
                                @Override
                                public void liked(LikeButton likeButton) {

                                    likeUnlike(fetchProfile.getUser_id(), fetchProfileLoginUser.getUser_id());

                                }

                                @Override
                                public void unLiked(LikeButton likeButton) {
                                    likeUnlike(fetchProfile.getUser_id(), fetchProfileLoginUser.getUser_id());

                                }
                            });

                            /*-----like unlike functionality------*/



                            /*--------------*/
                            chat_btn1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (fetchProfileLoginUser.getPremium().equals("1")) {
                                        Intent i = new Intent(DetailMatchesForSearchActivity.this, ChatActivity.class);
                                        i.putExtra("receiver_id", fetchProfile.getUser_id());
                                        i.putExtra("receiver_name", fetchProfile.getFname());
                                        startActivity(i);
                                    } else if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("2")) {
                                        toast("Please send request or member not connected !");
                                    } else if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("4")) {
                                        toast("Please send request or member not connected !");
                                    } else {
                                        openBottomSheet(fetchProfile);
                                    }
                                }
                            });

                            whatapp_btn1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("1")) {

                                        String whatapp_no = fetchProfile.getWtsapp_no();

                                        String phoneNumber = "91" + whatapp_no; //without '+'

                                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=");

                                        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

                                        startActivity(sendIntent);

                                    } else if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("2")) {
                                        toast("Please send request or member not connected !");
                                    } else if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("4")) {
                                        toast("Please send request or member not connected !");
                                    } else {
                                        openBottomSheet(fetchProfile);
                                    }
                                }
                            });

                            call_btn1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("1")) {
                                        Intent it = new Intent(Intent.ACTION_CALL);
                                        it.setData(Uri.parse("tel:" + fetchProfile.getMobile_no()));
                                        if (ContextCompat.checkSelfPermission(DetailMatchesForSearchActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                            ActivityCompat.requestPermissions((Activity) DetailMatchesForSearchActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                        } else {
                                            //You already have permission
                                            try {
                                                startActivity(it);
                                            } catch (SecurityException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("2")) {
                                        toast("Please send request or member not connected !");
                                    } else if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("4")) {
                                        toast("Please send request or member not connected !");
                                    } else {
                                        openBottomSheet(fetchProfile);
                                    }
                                }
                            });
                            /*--------------*/



                            /*------if connect show contact bottom --------*/
                            if (fetchProfile.getIs_connected().equals("1")) {

                                bottom_like_connectLL.setVisibility(View.GONE);

                                connect_now_topLL.setVisibility(View.GONE);

                                bottom_contactLL.setVisibility(View.VISIBLE);


                                sv_feature.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                                    @SuppressLint("RestrictedApi")
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onScrollChanged() {

                                        if (sv_feature.getScrollY() > 1500) {

                                            bottom_contactLL.setVisibility(View.VISIBLE);


                                        } else {

                                            bottom_contactLL.setVisibility(View.GONE);

                                        }


                                    }
                                });


                                if (fetchProfileLoginUser.getPremium().equals("1")) {
                                    upgrade_txt.setVisibility(View.GONE);

                                    chat_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            Intent i = new Intent(DetailMatchesForSearchActivity.this, ChatActivity.class);
                                            i.putExtra("receiver_id", fetchProfile.getUser_id());
                                            i.putExtra("receiver_name", fetchProfile.getFname());
                                            startActivity(i);

                                        }
                                    });
                                    whatapp_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String whatapp_no = fetchProfile.getWtsapp_no();

                                            String phoneNumber = "91" + whatapp_no; //without '+'

                                            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=");

                                            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

                                            startActivity(sendIntent);
                                        }
                                    });

                                    call_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent it = new Intent(Intent.ACTION_CALL);
                                            it.setData(Uri.parse("tel:" + fetchProfile.getMobile_no()));


                                            if (ContextCompat.checkSelfPermission(DetailMatchesForSearchActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                                ActivityCompat.requestPermissions((Activity) DetailMatchesForSearchActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                            } else {
                                                //You already have permission
                                                try {
                                                    startActivity(it);
                                                } catch (SecurityException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });


                                    chat_btn1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Intent i = new Intent(DetailMatchesForSearchActivity.this, ChatActivity.class);
                                            i.putExtra("receiver_id", fetchProfile.getUser_id());
                                            i.putExtra("receiver_name", fetchProfile.getFname());
                                            startActivity(i);
                                        }
                                    });


                                    whatapp_btn1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String whatapp_no = fetchProfile.getWtsapp_no();

                                            String phoneNumber = "91" + whatapp_no; //without '+'

                                            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=");

                                            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

                                            startActivity(sendIntent);
                                        }
                                    });

                                    call_btn1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent it = new Intent(Intent.ACTION_CALL);
                                            it.setData(Uri.parse("tel:" + fetchProfile.getMobile_no()));


                                            if (ContextCompat.checkSelfPermission(DetailMatchesForSearchActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                                ActivityCompat.requestPermissions((Activity) DetailMatchesForSearchActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                            } else {
                                                //You already have permission
                                                try {
                                                    startActivity(it);
                                                } catch (SecurityException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });

                                } else {
                                    upgrade_txt.setVisibility(View.VISIBLE);
                                    upgrade_txt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(DetailMatchesForSearchActivity.this, PremiumActivity.class);
                                            startActivity(i);
                                        }
                                    });
                                    chat_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            openBottomSheet(fetchProfile);
                                        }
                                    });
                                    whatapp_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            openBottomSheet(fetchProfile);
                                        }
                                    });
                                    call_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            openBottomSheet(fetchProfile);
                                        }
                                    });


                                    chat_btn1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            openBottomSheet(fetchProfile);
                                        }
                                    });
                                    whatapp_btn1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            openBottomSheet(fetchProfile);
                                        }
                                    });

                                    call_btn1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            openBottomSheet(fetchProfile);
                                        }
                                    });


                                }

                            } else if (fetchProfile.getIs_connected().equals("0") || fetchProfile.getIs_connected().equals("3")) {
                                connect_now_topLL.setVisibility(View.VISIBLE);
                                like_profile_txt.setText("Like this Profile?");
                                connect_now_txt.setText("Connect Now");
                                connect_btn_bottom_txt.setText("Connect Now");

                                contact_topLL.setVisibility(View.GONE);
                                //  bottom_contactLL.setVisibility(View.GONE);

                                connect_btn_LL.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        connectApi(fetchProfile.getUser_id(), String.valueOf(user.getUser_id()), view);

                                    }
                                });

                                connect_btn_bottomLL.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        connectApi11(fetchProfile.getUser_id(), String.valueOf(user.getUser_id()), view);

                                    }
                                });


                                sv_feature.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                                    @SuppressLint("RestrictedApi")
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onScrollChanged() {

                                        if (sv_feature.getScrollY() > 1500) {

                                            bottom_like_connectLL.setVisibility(View.VISIBLE);

                                        } else {
                                            bottom_like_connectLL.setVisibility(View.GONE);


                                        }


                                    }
                                });

                            } else if (fetchProfile.getIs_connected().equals("2")) {
                                connect_now_topLL.setVisibility(View.VISIBLE);
                                like_profile_txt.setText("");
                                connect_now_txt.setText("Request Sent");
                                contact_connect_txt.setText("Request Sent");
                                bottom_contactLL.setVisibility(View.VISIBLE);
                                // bottom_contactLL.setVisibility(View.GONE);
                                connect_btn_LL.setClickable(false);
                                connect_btn_bottomLL.setClickable(false);
                            } else if (fetchProfile.getIs_connected().equals("4")) {
                                connect_now_topLL.setVisibility(View.VISIBLE);
                                like_profile_txt.setText("");
                                connect_now_txt.setText("Received\nRequest");
                                contact_connect_txt.setText("Received Request");
                                bottom_contactLL.setVisibility(View.VISIBLE);
                                // bottom_contactLL.setVisibility(View.GONE);

                                connect_btn_LL.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        System.out.println("membr id=======" + fetchProfile.getUser_id());
                                        System.out.println("11membr id=======" + String.valueOf(user.getUser_id()));
                                        acceptProfile(fetchProfile.getUser_id(), String.valueOf(user.getUser_id()));

                                    }
                                });

                            }
                            /*------end if connect show contact bottom --------*/


//                    if (fetchProfileLoginUser.getPremium().equals("1") && fetchProfile.getIs_connected().equals("1")) {

                            if (fetchProfileLoginUser.getPremium().equals("1")) {

//                                education_careerLL.setVisibility(View.GONE);
                                view_all_detail.setVisibility(View.VISIBLE);
                                partner_pref_compareLL.setVisibility(View.VISIBLE);
                                view_all_detail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (fetchProfileLoginUser.getView_count().equals(fetchProfileLoginUser.getCount())) {
                                            /*dialog apply*/

                                            final Dialog dialog = new Dialog(DetailMatchesForSearchActivity.this);
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.dialog_view_contact_exist);
                                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                            LinearLayout upgrade_now_btn = dialog.findViewById(R.id.upgrade_now_btn);
                                            TextView view_count_desc = dialog.findViewById(R.id.view_count_desc);
                                            LinearLayout close_btn = dialog.findViewById(R.id.close_btn);

                                            upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                    Intent i = new Intent(DetailMatchesForSearchActivity.this, PremiumActivity.class);
                                                    startActivity(i);
                                                }
                                            });

                                            view_count_desc.setText("You View " + fetchProfileLoginUser.getCount() + " out of " + fetchProfileLoginUser.getView_count() + " profile. Upgrade Plan for view more contacts !!!!");

                                            close_btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    dialog.dismiss();
                                                }
                                            });


                                            dialog.show();

                                            /*dialog apply*/

                                        }

                                        partner_pref_compareLL.setVisibility(View.GONE);

                                        System.out.println("login user id============>>>" + fetchProfileLoginUser.getUser_id());
                                        System.out.println("people user id============>>>" + fetchProfile.getUser_id());

                                        p1.setVisibility(View.VISIBLE);
                                        Api apiService = RetrofitClient.getApiService();
                                        Call<InsertResponse> userResponse = apiService.getViewcount(fetchProfileLoginUser.getUser_id(), fetchProfile.getUser_id());
                                        userResponse.enqueue(new Callback<InsertResponse>() {

                                            @Override
                                            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                                                viewCountRespo = response.body();
                                                if (response.isSuccessful()) {

                                                    p1.setVisibility(View.GONE);

//                                            System.out.println("check view count repso============" + new Gson().toJson(viewCountRespo) );
                                                    if (viewCountRespo.getResid().equals("200")) {


                                                        System.out.println("01010101==========11");


                                                        about_txt.setText(fetchProfile.getFname() + " " + fetchProfile.getMname() + " " + fetchProfile.getLname());

                                                        view_all_detail.setVisibility(View.GONE);
                                                        education_careerLL.setVisibility(View.VISIBLE);
                                                        contact_detailLL.setVisibility(View.VISIBLE);
                                                        go_premium_LL.setVisibility(View.GONE);
                                                        lifestyle_appearance_LL.setVisibility(View.VISIBLE);
                                                        basic_block_LL.setVisibility(View.VISIBLE);
                                                        seeAllfamilyLL.setVisibility(View.VISIBLE);

//                                                education_careerLL.setVisibility(View.VISIBLE);

//                                conversation_background_familyLL.setVisibility(View.VISIBLE);

                                                        partner_prefLL.setVisibility(View.VISIBLE);
                                                        addressLL.setVisibility(View.VISIBLE);
                                                        partner_pref_compareLL.setVisibility(View.VISIBLE);

                                                    } else {
                                                        System.out.println("333333333333==========11");
                                                        Toast.makeText(DetailMatchesForSearchActivity.this, viewCountRespo.getResMsg(), Toast.LENGTH_SHORT);
                                                    }

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<InsertResponse> call, Throwable t) {
                                                p1.setVisibility(View.GONE);
                                                System.out.println("err profile created for******" + t.toString());

                                                if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
//                                                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                                                } else {
//                                                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        });


                                    }
                                });
                            } else {
                                view_all_detail.setVisibility(View.GONE);
                                contact_detailLL.setVisibility(View.VISIBLE);
                                go_premium_LL.setVisibility(View.VISIBLE);
                                lifestyle_appearance_LL.setVisibility(View.VISIBLE);
                                basic_block_LL.setVisibility(View.GONE);
                                conversation_background_familyLL.setVisibility(View.VISIBLE);
                                seeAllfamilyLL.setVisibility(View.GONE);
                                education_careerLL.setVisibility(View.VISIBLE);
                                partner_prefLL.setVisibility(View.GONE);
                                addressLL.setVisibility(View.GONE);
                                partner_pref_compareLL.setVisibility(View.VISIBLE);

                            }


                            sv_feature.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                                @SuppressLint("RestrictedApi")
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onScrollChanged() {


                                    if (sv_feature.getScrollY() > 1600) {

                                        swip_topLL.setVisibility(View.VISIBLE);
                                        swip_name_top.setText(fetchProfile.getFname());

                                        swip_topLL.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                sv_feature.smoothScrollTo(0, 0);

                                            }
                                        });


                                    } else {
                                        swip_topLL.setVisibility(View.GONE);

                                    }

                                }
                            });


                            System.out.println("user id=====" + fetchProfile.getUser_id());

//                    if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
                            if (fetchProfileLoginUser.getPremium().equals("1")) {
                                showAllDetail(fetchProfile);
                            }


                   /* if (fetchProfile.getImages() != null) {
                        if (fetchProfile.getImages().size() != 0) {
                            Glide.with(DetailMatchesActivity.this).load(fetchProfile.getImages().get(0).getImgs()).into(user_img);
                        } else {
                            Glide.with(DetailMatchesActivity.this).load(R.drawable.default_avtar_2).into(user_img);

                        }
                    } else {
                        Glide.with(DetailMatchesActivity.this).load(R.drawable.default_avtar_2).into(user_img);
                    }*/
                            if (fetchProfile.getImages().size() != 0) {

                                top_cv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (verified_txtLL.getVisibility() == View.VISIBLE) {
                                            verified_txtLL.setVisibility(View.GONE);

                                        } else {
                                            Intent i = new Intent(DetailMatchesForSearchActivity.this, ImagesActivity.class);
                                            if (fetchProfile.getImages().size() != 0)
                                                i.putExtra("mylist", (Serializable) fetchProfile.getImages());
                                            i.putExtra("userid", people_userid);
                                            i.putExtra("Fname", fetchProfile.getFname());
                                            i.putExtra("Mname", fetchProfile.getMname());
                                            i.putExtra("Lname", fetchProfile.getLname());
                                            i.putExtra("isCallFromSelfProfile", false);
                                            startActivity(i);
                                        }

                                    }
                                });


                                imgLL.setVisibility(View.VISIBLE);
                                img_count.setText(String.valueOf(fetchProfile.getImages().size()));

                                imgLL.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(DetailMatchesForSearchActivity.this, ImagesActivity.class);
                                        if (fetchProfile.getImages().size() != 0)
                                            i.putExtra("mylist", (Serializable) fetchProfile.getImages());
                                        i.putExtra("Fname", fetchProfile.getFname());
                                        i.putExtra("Mname", fetchProfile.getMname());
                                        i.putExtra("Lname", fetchProfile.getLname());
                                        i.putExtra("isCallFromSelfProfile", false);
                                        startActivity(i);
                                    }
                                });


                            } else {
                                imgLL.setVisibility(View.GONE);
                            }


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

                            if (fetchProfile.getPremium().equals("1")) {
                                premiumLL.setVisibility(View.VISIBLE);
                            } else {
                                premiumLL.setVisibility(View.GONE);

                            }

                            if (fetchProfile.getAcc_verify().equals("1")) {
                                verified_LL.setVisibility(View.VISIBLE);
                            } else {
                                verified_LL.setVisibility(View.GONE);
                            }

                            if (fetchProfile.getJust_joined().equals("1")) {
                                justjoinL.setVisibility(View.VISIBLE);
                            } else {
                                justjoinL.setVisibility(View.GONE);
                            }


                            verified_LL.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    verified_txtLL.setVisibility(View.VISIBLE);
                                }
                            });


                            if (fetchProfile.getAccount_verify().equals("1")) {
                                verifiedL.setVisibility(View.VISIBLE);
                            } else {
                                verifiedL.setVisibility(View.GONE);
                            }

                            getSupportActionBar().setTitle(fetchProfile.getFname());
                            toolbar.setTitleTextColor(Color.WHITE);

                            if (!fetchProfile.getAge().contentEquals("Not Specified") &&
                                    !fetchProfile.getHeight().contentEquals("Not Specified")) {
                                age_height.setText(String.format("%s yrs, %s", fetchProfile.getAge(), fetchProfile.getHeight()));

                            } else if (!fetchProfile.getAge().contentEquals("Not Specified") &&
                                    fetchProfile.getHeight().contentEquals("Not Specified")) {
                                age_height.setText(fetchProfile.getAge() + " yrs");

                            } else if (fetchProfile.getAge().contentEquals("Not Specified") &&
                                    !fetchProfile.getHeight().contentEquals("Not Specified")) {
                                age_height.setText(fetchProfile.getHeight());

                            } else {
                                age_height.setText(" ");

                            }


                   /* if (!fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
                            !fetchProfile.getCaste_name().contentEquals("Not Specified")) {
                        lang_caste.setText(String.format("%s, %s", fetchProfile.getMothertounge_name(), fetchProfile.getCaste_name()));

                    } else if (!fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
                            fetchProfile.getCaste_name().contentEquals("Not Specified")) {
                        lang_caste.setText(fetchProfile.getMothertounge_name());

                    } else if (fetchProfile.getMothertounge_name().contentEquals("Not Specified") &&
                            !fetchProfile.getCaste_name().contentEquals("Not Specified")) {
                        lang_caste.setText(fetchProfile.getCaste_name());

                    } else {
                        lang_caste.setText("");

                    }*/


                            if (!fetchProfile.getCaste_name().contentEquals("Not Specified") &&
                                    !fetchProfile.getSubcaste_name().contentEquals("Not Specified")) {
                                lang_caste.setText(String.format("%s, %s", fetchProfile.getCaste_name(), fetchProfile.getSubcaste_name()));

                            } else if (!fetchProfile.getCaste_name().contentEquals("Not Specified") &&
                                    fetchProfile.getSubcaste_name().contentEquals("Not Specified")) {
                                lang_caste.setText(fetchProfile.getCaste_name());

                            } else if (fetchProfile.getCaste_name().contentEquals("Not Specified") &&
                                    !fetchProfile.getSubcaste_name().contentEquals("Not Specified")) {
                                lang_caste.setText(fetchProfile.getSubcaste_name());

                            } else {
                                lang_caste.setText("");

                            }


/*
                    if (!fetchProfile.getSubcaste_name().contentEquals("Not Specified")){
                        sub_caste_LL.setVisibility(View.VISIBLE);
                        sub_caste_top.setText("("+fetchProfile.getSubcaste_name()+")");
                    }else{
                        sub_caste_LL.setVisibility(View.GONE);

                    }*/


                            System.out.println("fetchProfile.getOccupation_name()-------" + fetchProfile.getOccupation_name());

                            if (!fetchProfile.getOccupation_name().contentEquals("Not Specified")) {
                                job_top.setText(fetchProfile.getOccupation_name());

                            } else {
                                job_top.setText("");

                            }

                            if (!fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
                                    !fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
                                address_top.setText(String.format("%s, %s", fetchProfile.getCurr_ditrict_name(), fetchProfile.getCurr_state_name()));

                            } else if (!fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
                                    fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
                                address_top.setText(fetchProfile.getCurr_ditrict_name());

                            } else if (fetchProfile.getCurr_ditrict_name().contentEquals("Not Specified") &&
                                    !fetchProfile.getCurr_state_name().contentEquals("Not Specified")) {
                                address_top.setText(fetchProfile.getCurr_state_name());

                            } else {
                                address_top.setText("");

                            }


                            if (fetchProfile.getChkonline().equals("1")) {
                                onlineLL.setVisibility(View.VISIBLE);
                            } else {
                                onlineLL.setVisibility(View.GONE);
                            }

                            /*top layout*/

                            /*about layout*/


//                    if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
                            if (fetchProfileLoginUser.getPremium().equals("1")) {

                                about_lock.setVisibility(View.GONE);
                                about_txt.setText(fetchProfile.getFname());

//                        about_txt.setText(fetchProfile.getFname() + " " + fetchProfile.getMname() + " " + fetchProfile.getLname());

                            } else {

                                about_lock.setVisibility(View.VISIBLE);
                                about_txt.setText("About " + fetchProfile.getFname());

                            }

                            acc_id.setText(fetchProfile.getAcc_id());

                            if (fetchProfile.getProfile_name().equals("Not Specified")) {
                                profile_created_for.setVisibility(View.GONE);
                            } else {
                                profile_created_for.setVisibility(View.VISIBLE);

                                profile_created_for.setText("| Profile Created by " + fetchProfile.getProfile_name());

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
                            /*about layout*/

                            /*view full profile layout*/
                            upgrade_now.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent i = new Intent(DetailMatchesForSearchActivity.this, PremiumActivity.class);
                                    startActivity(i);
                                }
                            });

                            not_see_txt.setText(String.format("You will not see information that %s has chosen to hide.", fetchProfile.getFname()));
                            /*end view full profile layout*/


                            /*lifestyle appearance*/
                            age_height_color.setText(String.format("%s yrs, %s", fetchProfile.getAge(), fetchProfile.getHeight()));

//                    if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
                            if (fetchProfileLoginUser.getPremium().equals("1")) {

                                if (fetchProfile.getBirth_time() != null && !fetchProfile.getBirth_time().equals("Not Specified")) {
                                    birthday_date.setText("Born on " + fetchProfile.getDob() + " / " + fetchProfile.getBirth_time());

                                } else {
                                    birthday_date.setText("Born on " + fetchProfile.getDob());

                                }

                                birthday_lock.setVisibility(View.GONE);

                                marital_status.setText(fetchProfile.getMarital_status_name());
                                marital_status_lock.setVisibility(View.GONE);

                                diet.setText(fetchProfile.getDietry_name());
                                diet_lock.setVisibility(View.GONE);

                                contact_go_premium_now_btn_LL.setVisibility(View.GONE);


                            } else {
                                birthday_date.setText("Born on **/**/****");
                                birthday_lock.setVisibility(View.VISIBLE);

                                marital_status.setText("Marital status ******");
                                marital_status_lock.setVisibility(View.VISIBLE);

                                diet.setText("Diet ******");
                                diet_lock.setVisibility(View.VISIBLE);


                                contact_go_premium_now_btn_LL.setVisibility(View.VISIBLE);
                                contact_go_premium_now_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(DetailMatchesForSearchActivity.this, PremiumActivity.class);
                                        startActivity(i);
                                    }
                                });


                            }

                            address_lifestyle.setText(String.format("Lives in %s, %s, %s", fetchProfile.getCurr_ditrict_name(), fetchProfile.getCurr_state_name(), fetchProfile.getCurr_country_name()));

                            job_lifestyle.setText(fetchProfile.getOccupation_name());
                            /*end lifestyle appearance*/

                            /*conversation Starters*/
                            if ((!fetchProfileLoginUser.getMothertounge_name().equals(fetchProfile.getMothertounge_name())) &&
                                    (!fetchProfileLoginUser.getCaste_name().equals(fetchProfile.getCaste_name())) &&
                                    (!fetchProfileLoginUser.getCurr_state_name().equals(fetchProfile.getCurr_state_name()))) {
                                conversation_cv.setVisibility(View.GONE);
                                conversation_txt.setVisibility(View.GONE);
                            } else {
                                conversation_cv.setVisibility(View.VISIBLE);
                                conversation_txt.setVisibility(View.VISIBLE);
                            }


                            if (fetchProfileLoginUser.getMothertounge_name().equals(fetchProfile.getMothertounge_name())) {
                                lang_status_LL.setVisibility(View.VISIBLE);
                                lang_status.setText("You both are from the " + fetchProfile.getMothertounge_name() + " community");

                            } else {
                                lang_status_LL.setVisibility(View.GONE);
                            }


                            if (fetchProfileLoginUser.getCaste_name().equals(fetchProfile.getCaste_name())) {
                                caste_status_LL.setVisibility(View.VISIBLE);
                                caste_status.setText("You both are from the " + fetchProfile.getCaste_name() + " caste");


                            } else {
                                caste_status_LL.setVisibility(View.GONE);

                            }


                            if (fetchProfile.getGender().equals("Male")) {
                                if (fetchProfileLoginUser.getCurr_state_name().equals(fetchProfile.getCurr_state_name())) {
                                    state_Status_LL.setVisibility(View.VISIBLE);
                                    state_Status.setText("He lives in " + fetchProfile.getCurr_state_name() + " too");

                                } else {
                                    state_Status_LL.setVisibility(View.GONE);

                                }
                            } else if (fetchProfile.getGender().equals("Female")) {
                                if (fetchProfileLoginUser.getCurr_state_name().equals(fetchProfile.getCurr_state_name())) {
                                    state_Status_LL.setVisibility(View.VISIBLE);
                                    state_Status.setText("She lives in " + fetchProfile.getCurr_state_name() + " too");

                                } else {
                                    state_Status_LL.setVisibility(View.GONE);

                                }
                            } else {
                                if (fetchProfileLoginUser.getCurr_state_name().equals(fetchProfile.getCurr_state_name())) {
                                    state_Status_LL.setVisibility(View.VISIBLE);
                                    state_Status.setText("Lives in " + fetchProfile.getCurr_state_name() + " too");

                                } else {
                                    state_Status_LL.setVisibility(View.VISIBLE);


                                }
                            }
                            /*end conversation Starters*/

                            /*background*/

//                    if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
                            if (fetchProfileLoginUser.getPremium().equals("1")) {


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

                            } else {
                                bg_religion_lang.setText("Religion, Language ******");
                                bg_religion_lang_lock.setVisibility(View.VISIBLE);

                                background_go_premium_now_btn_LL.setVisibility(View.VISIBLE);
                                background_go_premium_now_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(DetailMatchesForSearchActivity.this, PremiumActivity.class);
                                        startActivity(i);
                                    }
                                });

                            }


                            if (fetchProfile.getCaste_name().equals("Not Specified")) {
                                bg_caste_LL.setVisibility(View.GONE);
                            } else {
                                bg_caste_LL.setVisibility(View.VISIBLE);

                                if (!fetchProfile.getSubcaste_name().equals("Not Specified")) {
                                    bg_caste.setText(fetchProfile.getCaste_name() + "  (" + fetchProfile.getSubcaste_name() + ")");
                                } else {
                                    bg_caste.setText(fetchProfile.getCaste_name());

                                }


                       /* if (fetchProfileLoginUser.getPremium().equals("1") ) {
                            if (!fetchProfile.getSubcaste_name().equals("Not Specified")) {
                                bg_caste.setText(fetchProfile.getCaste_name() + "  (" + fetchProfile.getSubcaste_name() + ")");
                            } else {
                                bg_caste.setText(fetchProfile.getCaste_name());

                            }

                        } else {
                            bg_caste.setText(fetchProfile.getCaste_name());

                        }*/

                            }


                            /*--smpraday and rashi--*/
                            if (fetchProfile.getSampraday_name() != null) {
                                if (fetchProfile.getSampraday_name().equals("Not Specified")) {
                                    sampradayLL.setVisibility(View.GONE);
                                } else {
                                    sampradayLL.setVisibility(View.VISIBLE);
                                    sampraday_txt.setText("Sampraday: " + fetchProfile.getSampraday_name());
                                }
                            }

                            if (fetchProfile.getRashi_name() != null) {
                                if (fetchProfile.getRashi_name().equals("Not Specified")) {
                                    rashiLL.setVisibility(View.GONE);
                                } else {
                                    rashiLL.setVisibility(View.VISIBLE);
                                    rashi_txt.setText("Rashi: " + fetchProfile.getRashi_name());
                                }
                            }

                            /*--smpraday and rashi--*/

                            /*end background*/


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
                                if (fetchProfile.getGender().equals("Male")) {

                                    String familt_str = "Ours is " + fetchProfile.getFamily_class() + " family, originally from " +
                                            fetchProfile.getPer_ditrict_name() + ". His father is " + fetchProfile.getFather_occ_name() + " and mother is a " + fetchProfile.getMother_occ() + ".";

                                    family.setText(familt_str);

                                } else if (fetchProfile.getGender().equals("Female")) {
                                    String familt_str = "Ours is " + fetchProfile.getFamily_class() + " family, originally from " +
                                            fetchProfile.getPer_ditrict_name() + ". His father is " + fetchProfile.getFather_occ_name() + " and mother is a " + fetchProfile.getMother_occ() + ".";

                                    family.setText(familt_str);
                                }
                            } else {
                                family_cv.setVisibility(View.GONE);
                                family_lbl.setVisibility(View.GONE);
                            }


                            /*end family*/

                            /*education career*/

                            if (!fetchProfile.getEducation_name().equals("Not Specified") ||
                                    !fetchProfile.getOther_education().equals("Not Specified") ||
                                    !fetchProfile.getOccupation_name().equals("Not Specified") ||
                                    !fetchProfile.getPost_name().equals("Not Specified") ||
                                    !fetchProfile.getYearly_salary().equals("Not Specified")) {


                                education_career_txt.setVisibility(View.VISIBLE);
                                education_career_cv.setVisibility(View.VISIBLE);

                                if (!fetchProfile.getEducation_name().equals("Not Specified")) {
                                    educationLL.setVisibility(View.VISIBLE);
                                    education.setVisibility(View.VISIBLE);
                                    education.setText(fetchProfile.getEducation_name());
                                    //remove hard coded type and name of education
                                } else {
                                   /* educationLL.setVisibility(View.GONE);
                                    education.setVisibility(View.GONE);*/
                                }

                                if (!fetchProfile.getCollege_name().equals("Not Specified")) {
                                    System.out.println("getCollege_name check============" + fetchProfile.getCollege_name());

                                    collegeNameLL.setVisibility(View.VISIBLE);
                                    collgeName.setVisibility(View.VISIBLE);
                                    collgeName.setText(fetchProfile.getCollege_name());
                                } else {
                                  /*  collegeNameLL.setVisibility(View.GONE);
                                    collgeName.setVisibility(View.GONE);*/
                                }


//                        if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
                                if (fetchProfileLoginUser.getPremium().equals("1")) {

                                    education_go_premium_now_btn_LL.setVisibility(View.GONE);
                                    other_education_lock.setVisibility(View.GONE);
                                    occupation_lock.setVisibility(View.GONE);
                                    post_name_lock.setVisibility(View.GONE);

                                    if (!fetchProfile.getOther_education().equals("Not Specified")) {
                                        other_educationLL.setVisibility(View.VISIBLE);
                                        other_education.setVisibility(View.VISIBLE);
                                        other_education.setText(fetchProfile.getOther_education());

                                    } else {
                                        /*other_educationLL.setVisibility(View.GONE);
                                        other_education.setVisibility(View.GONE);*/
                                    }


                                    if (!fetchProfile.getOccupation_name().equals("Not Specified")) {
                                        occupationLL.setVisibility(View.VISIBLE);
                                        occupation.setVisibility(View.VISIBLE);
                                        occupation.setText(fetchProfile.getOccupation_name());
                                    } else {
                             /*occupationLL.setVisibility(View.GONE);
                                        occupation.setVisibility(View.GONE);*/
                                    }


                                    if (!fetchProfile.getPost_name().equals("Not Specified")) {
                                        post_nameLL.setVisibility(View.VISIBLE);

                                        post_name.setText(fetchProfile.getPost_name());
                                    } else {
                                        //  post_nameLL.setVisibility(View.GONE);
                                    }

                                } else {

                                    other_education.setText("Other education ******");
                                    other_education_lock.setVisibility(View.VISIBLE);


                                    occupation.setText("Occupation ******");
                                    occupation_lock.setVisibility(View.VISIBLE);

                                    post_name.setText("Post Name ******");
                                    post_name_lock.setVisibility(View.VISIBLE);


                                    education_go_premium_now_btn_LL.setVisibility(View.VISIBLE);
                                    education_go_premium_now_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(DetailMatchesForSearchActivity.this, PremiumActivity.class);
                                            startActivity(i);
                                        }
                                    });


                                }

                                System.out.println("annual salary check============" + fetchProfile.getFname());
                                System.out.println("annual salary check============" + fetchProfile.getYearly_salary());

                                if (!fetchProfile.getYearly_salary().equals("Not Specified")) {
                                    System.out.println("11annual salary check============" + fetchProfile.getYearly_salary());

                                    incomeLL.setVisibility(View.VISIBLE);
                                    income.setVisibility(View.VISIBLE);
                                    income.setText(fetchProfile.getYearly_salary());
                                } else {
                                 /*   incomeLL.setVisibility(View.GONE);
                                    income.setVisibility(View.GONE);*/
                                }

                            } else {
                                education_career_txt.setVisibility(View.GONE);
                                education_career_cv.setVisibility(View.GONE);
                            }


//                    if (fetchProfileLoginUser.getPremium().equals("1") || fetchProfile.getIs_connected().equals("1")) {
                            if (fetchProfileLoginUser.getPremium().equals("1")) {
                                hobbies_lock.setVisibility(View.GONE);
                                hobbies_go_premium_now_btn_LL.setVisibility(View.GONE);

                                if (!fetchProfile.getHobbies().equals("Not Specified")) {
                                    hobbiesLL.setVisibility(View.VISIBLE);
                                    hobbies.setVisibility(View.VISIBLE);
                                    hobbies_cv.setVisibility(View.VISIBLE);
                                    hobbies_txt.setVisibility(View.VISIBLE);
                                    hobbies.setText(fetchProfile.getHobbies());
                                } else {
                                    hobbiesLL.setVisibility(View.GONE);
                                    hobbies.setVisibility(View.GONE);
                                    hobbies_cv.setVisibility(View.GONE);
                                    hobbies_txt.setVisibility(View.GONE);
                                }

                            } else {
                                hobbies.setText("Hobbies ******");
                                hobbies_lock.setVisibility(View.VISIBLE);


                                hobbies_go_premium_now_btn_LL.setVisibility(View.VISIBLE);
                                hobbies_go_premium_now_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(DetailMatchesForSearchActivity.this, PremiumActivity.class);
                                        startActivity(i);
                                    }
                                });

                            }





                            /*education career*/

                            /*pref comparision*/

                            System.out.println("age ---------" + fetchProfile.getAge());
                            System.out.println("my pref age from ---------" + fetchProfileLoginUser.getPref_agefrom());
                            System.out.println("my pref age to ---------" + fetchProfileLoginUser.getPref_ageto());


                            String part_age = fetchProfile.getAge();
                            String my_pref_age_from = fetchProfileLoginUser.getPref_agefrom();
                            String my_pref_age_to = fetchProfileLoginUser.getPref_ageto();


                            if ((!part_age.equals("Not Specified") && !my_pref_age_from.equals("Not Specified")) && !my_pref_age_to.equals("Not Specified")) {


                                if (!my_pref_age_from.equals("Doesn't Matter") && !my_pref_age_to.equals("Doesn't Matter")) {

                                    int _part_age = Integer.parseInt(part_age);
                                    int _my_pref_age_from = Integer.parseInt(my_pref_age_from);
                                    int _my_pref_age_to = Integer.parseInt(my_pref_age_to);


                                    if (_part_age <= _my_pref_age_to && _part_age >= _my_pref_age_from) {
                                        pref_count++;
                                        age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        age_from_to.setImageResource(R.drawable.cross_circle);
                                    }
                                } else if (!my_pref_age_from.equals("Doesn't Matter") && my_pref_age_to.equals("Doesn't Matter")) {

                                    int _part_age = Integer.parseInt(part_age);
                                    int _my_pref_age_from = Integer.parseInt(my_pref_age_from);


                                    if (_part_age >= _my_pref_age_from) {
                                        pref_count++;

                                        age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        age_from_to.setImageResource(R.drawable.cross_circle);
                                    }
                                } else if (my_pref_age_from.equals("Doesn't Matter") && !my_pref_age_to.equals("Doesn't Matter")) {

                                    int _part_age = Integer.parseInt(part_age);
                                    int _my_pref_age_to = Integer.parseInt(my_pref_age_to);


                                    if (_part_age <= _my_pref_age_to) {
                                        pref_count++;

                                        age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        age_from_to.setImageResource(R.drawable.cross_circle);
                                    }
                                } else if (my_pref_age_from.equals("Doesn't Matter") && my_pref_age_to.equals("Doesn't Matter")) {

                                    pref_count++;

                                    age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);

                                }

                                if (!fetchProfile.getPref_agefrom().equalsIgnoreCase("Not Specified") &&
                                        !fetchProfile.getPref_ageto().equalsIgnoreCase("Not Specified")) {
                                    age_from_to_pref.setText(fetchProfile.getPref_agefrom() + " to " + fetchProfile.getPref_ageto());
                                }

                            } else if (part_age.equals("Not Specified")) {
                              /*  age_pref_ll.setVisibility(View.GONE);
                                age_pref_v.setVisibility(View.GONE);*/
                                age_from_to_pref.setText("Not Specified");
                            } else if (!part_age.equals("Not Specified") &&
                                    !my_pref_age_from.equals("Not Specified")
                                    && my_pref_age_to.equals("Not Specified")) {

                                if (!my_pref_age_from.equals("Doesn't Matter")) {

                                    int _part_age = Integer.parseInt(part_age);
                                    int _my_pref_age_from = Integer.parseInt(my_pref_age_from);

                                    if (_part_age >= _my_pref_age_from) {
                                        pref_count++;

                                        age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        age_from_to.setImageResource(R.drawable.cross_circle);
                                    }


                                } else {
                                    pref_count++;

                                    age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                }
                                age_from_to_pref.setText("Not Specified");
                            } else if (!part_age.equals("Not Specified") && my_pref_age_from.equals("Not Specified") && !my_pref_age_to.equals("Not Specified")) {


                                if (!my_pref_age_to.equals("Doesn't Matter")) {
                                    int _part_age = Integer.parseInt(part_age);
                                    int _my_pref_age_to = Integer.parseInt(my_pref_age_to);

                                    if (_part_age <= _my_pref_age_to) {
                                        pref_count++;

                                        age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        age_from_to.setImageResource(R.drawable.cross_circle);
                                    }


                                } else {
                                    pref_count++;
                                    age_from_to.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                }
                                age_from_to_pref.setText("Not Specified");
                            } else if (my_pref_age_from.equals("Not Specified") && my_pref_age_to.equals("Not Specified")) {
//                                age_pref_ll.setVisibility(View.GONE);
//                                age_pref_v.setVisibility(View.GONE);
                                age_from_to.setImageResource(R.drawable.cross_circle);
                                age_from_to_pref.setText("Not Specified");

                            }


                            if (!fetchProfile.getPref_height_from().equals("Not Specified")) {

                                height_RL.setVisibility(View.VISIBLE);
                                height_pref_v.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getPref_height_from().equals("Doesn't Matter")) {
                                    pref_count++;
                                    height_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    System.out.println("11 height check-------------" + fetchProfile.getPref_height_from());
                                    System.out.println("22 height check-------------" + fetchProfileLoginUser.getPref_height_from());

                                    /*-------------*/

                                    //for below 4ft && above 7ft
                                    if (fetchProfile.getHeight().equals(fetchProfileLoginUser.getHeight())) {

                                        pref_count++;
                                        height_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    }
                                    //end for below 4ft && above 7ft
                                    //check to present or not
                                    else if (fetchProfileLoginUser.getPref_height_from().contains("to")) {

                                        String[] parts = fetchProfileLoginUser.getPref_height_from().split(" to ");

                                        String beforeFirstTo = parts[0];
                                        if (beforeFirstTo.contains(" ")) { //5ft 5inch
                                            String[] parts1 = beforeFirstTo.split(" ");
                                            String beforeSpace = parts1[0]; //5ft

                                            String[] split_ft = beforeSpace.split("ft");
                                            String firstSubString = split_ft[0];
                                            a11_height = Integer.parseInt(firstSubString);

                                            System.out.println("a11_height========>>>" + a11_height);


                                        }

                                        String afterFirstTo = parts[1];
                                        if (afterFirstTo.contains(" ")) { //5ft 5inch
                                            String[] parts1 = afterFirstTo.split(" ");
                                            String beforeSpace = parts1[0]; //5ft


                                            String[] split_ft = beforeSpace.split("ft");
                                            String firstSubString = split_ft[0];
                                            a12_height = Integer.parseInt(firstSubString);

                                            System.out.println("a12_height========>>>" + a12_height);

                                        }



                                        /*check profile height condition*/

                                        System.out.println("fetchProfile.getHeight()========>>>" + fetchProfile.getPref_height_from());

                                        String profile_height = fetchProfile.getPref_height_from();

                                        if (profile_height.equalsIgnoreCase("Above 7ft")) {
                                            if (profile_height.contains(" ")) {
                                                String[] parts1 = profile_height.split(" ");
                                                String beforeSpace = parts1[1]; //7ft


                                                String[] split_ft = beforeSpace.split("ft");
                                                String firstSubString = split_ft[0];
                                                a21_height = Integer.parseInt(firstSubString);

                                                System.out.println("a21_height========>>>" + a21_height);
                                                if (a21_height >= a11_height && a21_height <= a12_height) {
                                                    pref_count++;
                                                    height_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                                } else {
                                                    height_pref_img.setImageResource(R.drawable.cross_circle);

                                                }
                                            }

                                        } else if (profile_height.equalsIgnoreCase("Below 4ft")) {

                                            height_pref_img.setImageResource(R.drawable.cross_circle);


                                        } else {

                                            String[] split_ft = profile_height.split("ft");
                                            String firstSubString = split_ft[0];
                                            a23_height = Integer.parseInt(firstSubString);

                                            System.out.println("a21_height========>>>" + a21_height);

                                            if (a23_height >= a11_height && a23_height <= a12_height) {
                                                pref_count++;
                                                height_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                            } else {
                                                height_pref_img.setImageResource(R.drawable.cross_circle);

                                            }
                                        }






                                        /*end check profile height condition*/


                                    }
                                    //if to not present
                                    else {
                                        height_pref_img.setImageResource(R.drawable.cross_circle);
                                    }



                                    /*-------------*/

//
//                            if (fetchProfile.getHeight().equals(fetchProfileLoginUser.getPref_height())) {
//
//
//
//                                pref_count++;
//                                height_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//                                height_pref_img.setImageResource(R.drawable.cross_circle);
//                            }
                                }

                                height_pref.setText(fetchProfile.getPref_height_from()
                                        + " to " + fetchProfile.getPref_height_from());

                            } else {
                                height_pref_img.setImageResource(R.drawable.cross_circle);
                                height_pref.setText("Not Specified");
//                                height_RL.setVisibility(View.GONE);
//                                height_pref_v.setVisibility(View.GONE);
                            }


                            /*  *//*height  to start *//*
                            if ( !fetchProfileLoginUser.getHeight().equals("Not Specified")) {

                                height_to_RL.setVisibility(View.VISIBLE);
                                height_to_pref_v.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getHeight().equals("Doesn't Matter")) {
                                    pref_count++;
                                    height_to_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    System.out.println("11 height_to check-------------" + fetchProfile.getHeight());
                                    System.out.println("22 height_to check-------------" + fetchProfileLoginUser.getHeight());

                                    *//*-------------*//*

                                    //for below 4ft && above 7ft
                                    if (fetchProfile.getHeight().equals(fetchProfileLoginUser.getHeight())) {

                                        pref_count++;
                                        height_to_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    }
                                    //end for below 4ft && above 7ft
                                    //check to present or not
                                    else if (fetchProfileLoginUser.getHeight().contains("to")) {

                                        String[] parts = fetchProfileLoginUser.getHeight().split(" to ");

                                        String beforeFirstTo = parts[0];
                                        if (beforeFirstTo.contains(" ")) { //5ft 5inch
                                            String[] parts1 = beforeFirstTo.split(" ");
                                            String beforeSpace = parts1[0]; //5ft

                                            String[] split_ft = beforeSpace.split("ft");
                                            String firstSubString = split_ft[0];
                                            a11_height = Integer.parseInt(firstSubString);

                                            System.out.println("a11_height========>>>" + a11_height);


                                        }

                                        String afterFirstTo = parts[1];
                                        if (afterFirstTo.contains(" ")) { //5ft 5inch
                                            String[] parts1 = afterFirstTo.split(" ");
                                            String beforeSpace = parts1[0]; //5ft


                                            String[] split_ft = beforeSpace.split("ft");
                                            String firstSubString = split_ft[0];
                                            a12_height = Integer.parseInt(firstSubString);

                                            System.out.println("a12_height========>>>" + a12_height);

                                        }



                                        *//*check profile height condition*//*

                                        System.out.println("fetchProfile.getHeight()========>>>" + fetchProfile.getHeight());

                                        String profile_height = fetchProfile.getHeight();

                                        if (profile_height.equalsIgnoreCase("Above 7ft")) {
                                            if (profile_height.contains(" ")) {
                                                String[] parts1 = profile_height.split(" ");
                                                String beforeSpace = parts1[1]; //7ft


                                                String[] split_ft = beforeSpace.split("ft");
                                                String firstSubString = split_ft[0];
                                                a21_height = Integer.parseInt(firstSubString);

                                                System.out.println("a21_height========>>>" + a21_height);
                                                if (a21_height >= a11_height && a21_height <= a12_height) {
                                                    pref_count++;
                                                    height_to_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                                } else {
                                                    height_to_pref_img.setImageResource(R.drawable.cross_circle);

                                                }

                                            }

                                        } else if (profile_height.equalsIgnoreCase("Below 4ft")) {

                                            height_to_pref_img.setImageResource(R.drawable.cross_circle);


                                        } else {

                                            String[] split_ft = profile_height.split("ft");
                                            String firstSubString = split_ft[0];
                                            a23_height = Integer.parseInt(firstSubString);

                                            System.out.println("a21_height========>>>" + a21_height);

                                            if (a23_height >= a11_height && a23_height <= a12_height) {
                                                pref_count++;
                                                height_to_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                            } else {
                                                height_to_pref_img.setImageResource(R.drawable.cross_circle);

                                            }
                                        }






                                        *//*end check profile height_to condition*//*


                                    }
                                    //if to not present
                                    else {
                                        height_to_pref_img.setImageResource(R.drawable.cross_circle);
                                    }



                                    *//*-------------*//*

//
//                            if (fetchProfile.getHeight().equals(fetchProfileLoginUser.getPref_height())) {
//
//
//
//                                pref_count++;
//                                height_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
//                            } else {
//                                height_pref_img.setImageResource(R.drawable.cross_circle);
//                            }
                                }

                                height_to_pref.setText(fetchProfile.getHeight());

                            } else {
                                height_to_RL.setVisibility(View.GONE);
                                height_to_pref_v.setVisibility(View.GONE);
                            }
                            *//*height  to end */


                            if (!fetchProfile.getMarital_status_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_marital_name().equals("Not Specified")) {


                                marital_status_prefRL.setVisibility(View.VISIBLE);
                                marital_status_pref_v.setVisibility(View.VISIBLE);


                                if (fetchProfileLoginUser.getPref_marital_name().equals("Doesn't Matter")) {
                                    pref_count++;
                                    marital_status_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {
                                    if (fetchProfile.getMarital_status_name().equals(fetchProfileLoginUser.getPref_marital_name())) {
                                        pref_count++;
                                        marital_status_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        marital_status_pref_img.setImageResource(R.drawable.cross_circle);
                                    }
                                }

                                marital_status_pref.setText(fetchProfile.getPref_marital_name());

                            } else {
                                marital_status_pref_img.setImageResource(R.drawable.cross_circle);
                                marital_status_pref.setText(fetchProfile.getMarital_status_name());
//                                marital_status_prefRL.setVisibility(View.GONE);
//                                marital_status_pref_v.setVisibility(View.GONE);
                            }


                            if (!fetchProfile.getReligion_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_religion_name().equals("Not Specified")) {


                                religion_caste_pref_v.setVisibility(View.VISIBLE);
                                religion_caste_prefRL.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getPref_religion_name().equals("Doesn't Matter")) {
                                    pref_count++;

                                    religion_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getReligion_name().equals(fetchProfileLoginUser.getPref_religion_name())) {
                                        pref_count++;

                                        religion_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        religion_caste_pref_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }


                                religion_caste_pref.setText(fetchProfile.getPref_religion_name());

                            } else {
                                religion_caste_pref_img.setImageResource(R.drawable.cross_circle);
                                religion_caste_pref.setText(fetchProfile.getReligion_name());
//                                religion_caste_prefRL.setVisibility(View.GONE);
//                                religion_caste_pref_v.setVisibility(View.GONE);
                            }

                            /*-----------------*/
                            if (!fetchProfile.getMain_education_name().equals("Not Specified")
                                    && !fetchProfileLoginUser.getPrefmain_edu_name().equals("Not Specified")) {

                                if (fetchProfileLoginUser.getPrefmain_edu_name().equals("Doesn't Matter")) {
                                    pref_count++;

                                    education_type_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getMain_education_name().equals(fetchProfileLoginUser.getPrefmain_edu_name())) {
                                        pref_count++;
                                        education_type_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        education_type_pref_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }


                                education_type_pref.setText(fetchProfile.getPrefmain_edu_name());

                            } else {
                                education_type_pref_img.setImageResource(R.drawable.cross_circle);
                                education_type_pref.setText(fetchProfile.getMain_education_name());
                            }

                            if (!fetchProfile.getOccupation_name().equals("Not Specified")
                                    && !fetchProfileLoginUser.getPref_occ_name().equals("Not Specified")) {

                                if (fetchProfileLoginUser.getPref_occ_name()
                                        .equals("Doesn't Matter")) {
                                    pref_count++;

                                    occupation_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getOccupation_name()
                                            .equals(fetchProfileLoginUser.getPref_occ_name())) {
                                        pref_count++;
                                        occupation_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        occupation_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }


                                occupation_pref.setText(fetchProfile.getPref_occ_name());

                            } else {
                                occupation_img.setImageResource(R.drawable.cross_circle);
                                occupation_pref.setText(fetchProfile.getOccupation_name());
                            }

                            if (!fetchProfile.getMain_occupation_name().equals("Not Specified")
                                    && !fetchProfileLoginUser.getPrefmain_occ_name().equals("Not Specified")) {

                                if (fetchProfileLoginUser.getPrefmain_occ_name()
                                        .equals("Doesn't Matter")) {
                                    pref_count++;

                                    occupation_type_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getMain_occupation_name().equals(fetchProfileLoginUser.getPrefmain_occ_name())) {
                                        pref_count++;
                                        occupation_type_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        occupation_type_pref_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }


                                occupation_type_pref.setText(fetchProfile.getPrefmain_occ_name());

                            } else {
                                occupation_type_pref_img.setImageResource(R.drawable.cross_circle);
                                occupation_type_pref.setText(fetchProfile.getMain_occupation_name());

                            }


                            if (fetchProfile.getPref_mothertongue_name() != null) {
                                if (!fetchProfile.getMothertounge_name().equals("Not Specified")
                                        && !fetchProfileLoginUser.getPref_mothertongue_name().equals("Not Specified")) {

                                    if (fetchProfileLoginUser.getPref_mothertongue_name()
                                            .equals("Doesn't Matter")) {
                                        pref_count++;

                                        mother_tongue_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {

                                        if (fetchProfile.getMothertounge_name().equals(fetchProfileLoginUser.getPref_mothertongue_name())) {
                                            pref_count++;
                                            mother_tongue_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                        } else {
                                            mother_tongue_pref_img.setImageResource(R.drawable.cross_circle);
                                        }
                                    }

                                    mother_tongue_pref.setText(fetchProfile.getPref_mothertongue_name());

                                } else {
                                    mother_tongue_pref_img.setImageResource(R.drawable.cross_circle);
                                    mother_tongue_pref.setText(fetchProfile.getMothertounge_name());
                                }
                            } else {
                                mother_tongue_pref_img.setImageResource(R.drawable.cross_circle);

                            }

                            if (!fetchProfile.getSampraday_name().equals("Not Specified")
                                    && !fetchProfileLoginUser.getPref_sampraday_name().equals("Not Specified")) {

                                if (fetchProfileLoginUser.getPref_sampraday_name()
                                        .equals("Doesn't Matter")) {
                                    pref_count++;

                                    sampraday_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getSampraday_name()
                                            .equals(fetchProfileLoginUser.getPref_sampraday_name())) {
                                        pref_count++;
                                        sampraday_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        sampraday_pref_img.setImageResource(R.drawable.cross_circle);
                                    }
                                }

                                sampraday_pref.setText(fetchProfile.getPref_sampraday_name());

                            } else if (!fetchProfileLoginUser.getPref_sampraday_name().equals("Not Specified")) {
                                sampraday_pref.setText(fetchProfile.getPref_sampraday_name());
                                if (fetchProfile.getSampraday_name()
                                        .equals(fetchProfileLoginUser.getPref_sampraday_name())) {
                                    pref_count++;
                                    sampraday_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {
                                    sampraday_pref_img.setImageResource(R.drawable.cross_circle);
                                }
                            } else {
                                sampraday_pref_img.setImageResource(R.drawable.cross_circle);
                                sampraday_pref.setText(fetchProfile.getSampraday_name());
                            }

                            if (!fetchProfile.getYearly_salary().equals("Not Specified")
                                    && !fetchProfileLoginUser.getPref_yearsalary().equals("Not Specified")) {

                                if (fetchProfileLoginUser.getPref_yearsalary()
                                        .equals("Doesn't Matter")) {
                                    pref_count++;

                                    annual_income_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getYearly_salary()
                                            .equals(fetchProfileLoginUser.getPref_yearsalary())) {
                                        pref_count++;
                                        annual_income_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        annual_income_pref_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }


                                annual_income_pref.setText(fetchProfile.getPref_yearsalary());

                            } else if (!fetchProfileLoginUser.getPref_yearsalary().equals("Not Specified")) {
                                annual_income_pref.setText(fetchProfile.getPref_yearsalary());
                                if (fetchProfile.getYearly_salary()
                                        .equals(fetchProfileLoginUser.getPref_yearsalary())) {
                                    pref_count++;
                                    annual_income_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {
                                    annual_income_pref_img.setImageResource(R.drawable.cross_circle);
                                }
                            } else {
                                annual_income_pref_img.setImageResource(R.drawable.cross_circle);
                                annual_income_pref.setText(fetchProfile.getYearly_salary());
                            }


                            if (!fetchProfile.getReligion_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_religion_name().equals("Not Specified")) {


                                religion_caste_pref_v.setVisibility(View.VISIBLE);
                                religion_caste_prefRL.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getPref_religion_name().equals("Doesn't Matter")) {
                                    pref_count++;

                                    religion_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getReligion_name().equals(fetchProfileLoginUser.getPref_religion_name())) {
                                        pref_count++;

                                        religion_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        religion_caste_pref_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }


                                religion_caste_pref.setText(fetchProfile.getPref_religion_name());

                            } else {
                                religion_caste_pref_img.setImageResource(R.drawable.cross_circle);
                                religion_caste_pref.setText(fetchProfile.getReligion_name());
                            }


                            if (!fetchProfile.getCurr_country_name().
                                    equals("Not Specified") && !fetchProfileLoginUser.getPref_country_name().equals("Not Specified")) {


                                if (fetchProfileLoginUser.getPref_country_name()
                                        .equals("Doesn't Matter")) {
                                    pref_count++;

                                    country_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getCurr_country_name()
                                            .equals(fetchProfileLoginUser.getPref_country_name())) {
                                        pref_count++;

                                        country_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        country_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }

                                country.setText(fetchProfile.getPref_country_name());

                            } else {
                                country_img.setImageResource(R.drawable.cross_circle);
                                country.setText(fetchProfile.getCurr_country_name());
                            }


                            if (!fetchProfile.getCurr_state_name().
                                    equals("Not Specified") && !fetchProfileLoginUser.getPref_state_name().equals("Not Specified")) {


                                if (fetchProfileLoginUser.getPref_state_name()
                                        .equals("Doesn't Matter")) {
                                    pref_count++;

                                    state_livein_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getCurr_state_name()
                                            .equals(fetchProfileLoginUser.getPref_state_name())) {
                                        pref_count++;

                                        state_livein_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        state_livein_pref_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }


                                state_livein_pref.setText(fetchProfile.getPref_state_name());

                            } else {
                                state_livein_pref_img.setImageResource(R.drawable.cross_circle);
                                state_livein_pref.setText(fetchProfile.getCurr_state_name());
                            }


                            if (!fetchProfile.getCurr_ditrict_name().
                                    equals("Not Specified") && !fetchProfileLoginUser.getPref_dist_name().equals("Not Specified")) {


                                if (fetchProfileLoginUser.getPref_dist_name()
                                        .equals("Doesn't Matter")) {
                                    pref_count++;

                                    city_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getCurr_ditrict_name()
                                            .equals(fetchProfileLoginUser.getPref_dist_name())) {
                                        pref_count++;

                                        city_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        city_img.setImageResource(R.drawable.cross_circle);
                                    }

                                }
                                city.setText(fetchProfile.getPref_dist_name());

                            } else {
                                city_img.setImageResource(R.drawable.cross_circle);

                                city.setText(fetchProfile.getCurr_ditrict_name());
                            }

                            /*-----------------*/


                            if (!fetchProfile.getDietry_name().equals("Not Specified")
                                    && !fetchProfileLoginUser.getPref_diet_name().equals("Not Specified")) {


                                diet_prefRL.setVisibility(View.VISIBLE);
                                diet_pref_v.setVisibility(View.VISIBLE);


                                if (fetchProfileLoginUser.getPref_diet_name().equals("Doesn't Matter")) {
                                    pref_count++;

                                    diet_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {
                                    if (fetchProfile.getDietry_name().equals(fetchProfileLoginUser.getPref_diet_name())) {
                                        pref_count++;

                                        diet_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        diet_pref_img.setImageResource(R.drawable.cross_circle);
                                    }
                                }


                                diet_pref.setText(fetchProfile.getPref_diet_name());

                            } else if (!fetchProfileLoginUser.getPref_diet_name().equals("Not Specified")) {
                                diet_pref.setText(fetchProfile.getPref_diet_name());
                                if (fetchProfile.getDietry_name().equals(fetchProfileLoginUser.getPref_diet_name())) {
                                    pref_count++;

                                    diet_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {
                                    diet_pref_img.setImageResource(R.drawable.cross_circle);
                                }
                            } else {
                                diet_pref_img.setImageResource(R.drawable.cross_circle);
                                diet_pref.setText(fetchProfile.getDietry_name());

//                                diet_prefRL.setVisibility(View.GONE);
//                                diet_pref_v.setVisibility(View.GONE);
                            }


                            if (!fetchProfile.getEducation_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_edu_name().equals("Not Specified")) {


                                education_v.setVisibility(View.VISIBLE);
                                educationRL.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getPref_edu_name().equals("Doesn't Matter")) {

                                    pref_count++;
                                    education_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {


                                    String all_vals = fetchProfileLoginUser.getPref_edu_name();
                                    List<String> list = Arrays.asList(all_vals.split(","));
                                    for (int x = 0; x < list.size(); x++) {
                                        System.out.println("pref education name------------" + list.get(x));
                                    }

                                    if (list.contains(fetchProfile.getEducation_name())) {
                                        System.out.println("pref education name------------" + "yes");
                                        pref_count++;
                                        education_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        System.out.println("pref education name------------" + "no");
                                        education_img.setImageResource(R.drawable.cross_circle);

                                    }


                                    if (fetchProfile.getEducation_name().equals(fetchProfileLoginUser.getPref_edu_name())) {

                                        pref_count++;
                                        education_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        education_img.setImageResource(R.drawable.cross_circle);
                                    }
                                }

                                education_pref.setText(fetchProfile.getPref_edu_name());

                            } else {
                                education_img.setImageResource(R.drawable.cross_circle);
                                education_pref.setText(fetchProfile.getEducation_name());

//                                educationRL.setVisibility(View.GONE);
//                                education_v.setVisibility(View.GONE);
                            }


                            if (!fetchProfile.getColor_complex_name().equals("Not Specified")
                                    && !fetchProfileLoginUser.getPref_complex_name().equals("Not Specified")) {


                                color_complex_pref_v.setVisibility(View.VISIBLE);
                                color_complex_pref_RL.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getPref_complex_name().equals("Doesn't Matter")) {

                                    pref_count++;
                                    color_complex_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getColor_complex_name().equals(fetchProfileLoginUser.getPref_complex_name())) {

                                        pref_count++;
                                        color_complex_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        color_complex_pref_img.setImageResource(R.drawable.cross_circle);
                                    }
                                }

                                color_complex_pref.setText(fetchProfile.getPref_complex_name());

                            } else if (!fetchProfileLoginUser.getPref_complex_name().equals("Not Specified")) {
                                color_complex_pref.setText(fetchProfile.getPref_complex_name());
                                if (fetchProfile.getColor_complex_name().equals(fetchProfileLoginUser.getPref_complex_name())) {

                                    pref_count++;
                                    color_complex_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {
                                    color_complex_pref_img.setImageResource(R.drawable.cross_circle);
                                }
                            } else {
                                color_complex_pref_img.setImageResource(R.drawable.cross_circle);
                                color_complex_pref.setText(fetchProfile.getColor_complex_name());

//                                color_complex_pref_RL.setVisibility(View.GONE);
//                                color_complex_pref_v.setVisibility(View.GONE);
                            }


                            if (!fetchProfile.getCaste_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_caste_name().equals("Not Specified")) {


                                caste_pref_v.setVisibility(View.VISIBLE);
                                caste_pref_RL.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getPref_caste_name().equals("Doesn't Matter")) {

                                    pref_count++;
                                    caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getCaste_name().equals(fetchProfileLoginUser.getPref_caste_name())) {

                                        pref_count++;
                                        caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        caste_pref_img.setImageResource(R.drawable.cross_circle);
                                    }
                                }

                                caste_pref.setText(fetchProfile.getPref_caste_name());

                            } else {
                                caste_pref_img.setImageResource(R.drawable.cross_circle);
                                caste_pref.setText(fetchProfile.getCaste_name());

//                                caste_pref_RL.setVisibility(View.GONE);
//                                caste_pref_v.setVisibility(View.GONE);
                            }


                            if (!fetchProfile.getSubcaste_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_subcaste_name().equals("Not Specified")) {

                                sub_caste_pref_v.setVisibility(View.VISIBLE);
                                sub_caste_pref_RL.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getPref_subcaste_name().equals("Doesn't Matter")) {

                                    pref_count++;
                                    sub_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getSubcaste_name().equals(fetchProfileLoginUser.getPref_subcaste_name())) {

                                        pref_count++;
                                        sub_caste_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        sub_caste_pref_img.setImageResource(R.drawable.cross_circle);
                                    }
                                }

                                sub_caste_pref.setText(fetchProfile.getPref_subcaste_name());

                            } else {
                                sub_caste_pref_img.setImageResource(R.drawable.cross_circle);
                                sub_caste_pref.setText(fetchProfile.getSubcaste_name());
//                                sub_caste_pref_RL.setVisibility(View.GONE);
//                                sub_caste_pref_v.setVisibility(View.GONE);
                            }


                            if (!fetchProfile.getLifestyle_name().equals("Not Specified") && !fetchProfileLoginUser.getPref_lifestyle_name().equals("Not Specified")) {


                                lifestyle_pref_v.setVisibility(View.VISIBLE);
                                lifestyle_pref_RL.setVisibility(View.VISIBLE);

                                if (fetchProfileLoginUser.getPref_lifestyle_name().equals("Doesn't Matter")) {

                                    pref_count++;
                                    lifestyle_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                } else {

                                    if (fetchProfile.getLifestyle_name().equals(fetchProfileLoginUser.getPref_lifestyle_name())) {

                                        pref_count++;
                                        lifestyle_pref_img.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                    } else {
                                        lifestyle_pref_img.setImageResource(R.drawable.cross_circle);
                                    }
                                }

                                lifestyle_pref.setText(fetchProfile.getPref_lifestyle_name());

                            } else {
                                lifestyle_pref_img.setImageResource(R.drawable.cross_circle);
                                lifestyle_pref.setText(fetchProfile.getLifestyle_name());

//                                lifestyle_pref_RL.setVisibility(View.GONE);
//                                lifestyle_pref_v.setVisibility(View.GONE);
                            }

                            /*-----------*/



                            /*end pref comparision*/


                            if (fetchProfile.getGender().equals("Male")) {
//                        her_him_pref.setText("His Prefrences");
                                her_him_pref.setText("You and Him");

                                if (pref_count != 0)
                                    match_count.setText("You Match " + pref_count + " of him Preferences");
                            }
                            if (fetchProfile.getGender().equals("Female")) {
//                        her_him_pref.setText("Her Prefrences");
                                her_him_pref.setText("You and Her");

                                if (pref_count != 0)
                                    match_count.setText("You Match " + pref_count + " of her Preferences");

                            }
                            System.out.println("match_count------" + match_count);

                    /*if (pref_count != 0)
                        match_count.setText("You Match " + pref_count );*/


                            if (fetchProfile.getImages() != null) {
                                if (fetchProfile.getImages().size() != 0) {
                                    Glide.with(getApplicationContext()).load(fetchProfile.getImages().get(0).getImgs()).into(his_her_profile);
                                } else {

                                    if (fetchProfile.getGender().equals("Female")) {
                                        his_her_profile.setImageResource(R.drawable.female_avtar);
                                    } else {
                                        his_her_profile.setImageResource(R.drawable.male_avtar);
                                    }

//                            Glide.with(DetailMatchesActivity.this).load(R.drawable.default_avtar_2).into(his_her_profile);

                                }
                            } else {

                                if (fetchProfile.getGender().equals("Female")) {
                                    his_her_profile.setImageResource(R.drawable.female_avtar);
                                } else {
                                    his_her_profile.setImageResource(R.drawable.male_avtar);
                                }
//                        Glide.with(DetailMatchesActivity.this).load(R.drawable.default_avtar_2).into(his_her_profile);
                            }


                            if (fetchProfileLoginUser.getImages() != null) {
                                if (fetchProfileLoginUser.getImages().size() != 0) {
                                    Glide.with(getApplicationContext()).load(fetchProfileLoginUser.getImages().get(0).getImgs()).into(login_user_profile_image);
                                } else {
//                            Glide.with(DetailMatchesActivity.this).load(R.drawable.default_avtar_2).into(login_user_profile_image);

                                    if (fetchProfileLoginUser.getGender().equals("Female")) {
                                        login_user_profile_image.setImageResource(R.drawable.female_avtar);
                                    } else {
                                        login_user_profile_image.setImageResource(R.drawable.male_avtar);
                                    }


                                }
                            } else {
//                        Glide.with(DetailMatchesActivity.this).load(R.drawable.default_avtar_2).into(login_user_profile_image);

                                if (fetchProfileLoginUser.getGender().equals("Female")) {
                                    login_user_profile_image.setImageResource(R.drawable.female_avtar);
                                } else {
                                    login_user_profile_image.setImageResource(R.drawable.male_avtar);
                                }
                            }


                            if (fetchProfileLoginUser.getGender().equals("Male")) {
                                you_.setText("You & Him");
                            }
                            if (fetchProfile.getGender().equals("Female")) {
                                you_.setText("You & She");
                            }


                        } else if (fetchProfile.getResid().equals("203")) {

                            showBox("This account is deactivate!");
                        } else if (fetchProfile.getResid().equals("204")) {

                            showBox("This account is deleted!");
                        } else {
                            showBox("Profile not found!");
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());

                if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    private void sentViewNotification(String user_id, int login_user_id) {
//        progressBar = ProgressDialog.show(AccomodationDetailActivity.this, "", "Please wait...");
        Api apiService = RetrofitClient.getApiService();
        Call<SendProfileViewNoti> userResponse = apiService.sendProfileViewNotification(user_id, String.valueOf(login_user_id));
        userResponse.enqueue(new Callback<SendProfileViewNoti>() {
            @Override
            public void onResponse(Call<SendProfileViewNoti> call, Response<SendProfileViewNoti> response) {

            }

            @Override
            public void onFailure(Call<SendProfileViewNoti> call, Throwable t) {
                progressBar.dismiss();
            }
        });
    }

    public void showBox(String msg) {

        /*dialog apply*/

        final Dialog dialog = new Dialog(DetailMatchesForSearchActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.deactivate_profile_detail);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView call_btn = dialog.findViewById(R.id.call_btn);
        TextView msg_name = dialog.findViewById(R.id.msg_name);

        msg_name.setText(msg);

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.show();




        /*dialog apply*/
    }

    public void likeUnlike(String user_id, String login_user_id) {

        Api apiService = RetrofitClient.getApiService();
        Call<LikeProfileResponse> userResponse = apiService.getlLikeunlike(login_user_id, user_id);
        userResponse.enqueue(new Callback<LikeProfileResponse>() {

            @Override
            public void onResponse(Call<LikeProfileResponse> call, Response<LikeProfileResponse> response) {
                likeProfileResponse = response.body();

//                connect_btn_pb.setVisibility(View.GONE);

//                System.out.println("like respo==========" + new Gson().toJson(likeProfileResponse));

                if (response.isSuccessful()) {


                    if (likeProfileResponse.getResid().equals("200")) {

                        if (likeProfileResponse.getLike().equals("1")) {
                            likeButton.setLiked(true);
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(android.R.id.content), "Profile Like Successfully.", Snackbar.LENGTH_LONG);
                            snackbar.show();

                            like_count.setText(likeProfileResponse.getLikecount() + " likes");

                        } else {
                            likeButton.setLiked(false);
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(android.R.id.content), "Unlike Successfully.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            like_count.setText(likeProfileResponse.getLikecount() + " likes");
                        }

                    } else {
                        Toast.makeText(DetailMatchesForSearchActivity.this, connect_response.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LikeProfileResponse> call, Throwable t) {
                System.out.println("msg1 like unlike******" + t.toString());
                connect_btn_pb.setVisibility(View.GONE);

                if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void connectApi(String user_id, String login_user_id, View view) {
        connect_now_txt.setText("");
        connect_btn_pb.setVisibility(View.VISIBLE);

        System.out.println("people id============" + user_id);
        System.out.println("login_user_id============" + login_user_id);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.connectNow(user_id, login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                connect_response = response.body();

                connect_btn_pb.setVisibility(View.GONE);

                /*System.out.println("connect_response==========" + new Gson().toJson(connect_response));*/

                if (response.isSuccessful()) {


                    if (connect_response.getResid().equals("200")) {
                        bottom_contactLL.setVisibility(View.VISIBLE);
                        connect_now_txt.setText("Request Sent");
                        connect_btn_bottom_txt.setText("Request Sent");
                        contact_connect_txt.setText("Request Sent");

                        connect_btn_LL.setClickable(false);


                        Snackbar snackbar = Snackbar
                                .make(view, "Request send successfully.", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } else {
                        Toast.makeText(DetailMatchesForSearchActivity.this, connect_response.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 my connect_response******" + t.toString());
                connect_btn_pb.setVisibility(View.GONE);

                if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void connectApi11(String user_id, String login_user_id, View view) {
        connect_now_txt.setText("");
        connect_btn_pb.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.connectNow(user_id, login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                connect_response = response.body();

//                connect_btn_pb.setVisibility(View.GONE);

                /*System.out.println("connect_response==========" + new Gson().toJson(connect_response));*/

                if (response.isSuccessful()) {
                    connect_btn_pb.setVisibility(View.GONE);

                    if (connect_response.getResid().equals("200")) {
                        bottom_contactLL.setVisibility(View.VISIBLE);
                        connect_now_txt.setText("Request Sent");
                        connect_btn_bottom_txt.setText("Request Sent");
                        contact_connect_txt.setText("Request Sent");
                        connect_btn_LL.setClickable(false);
                        Snackbar snackbar = Snackbar
                                .make(view, "Request send successfully.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        Toast.makeText(DetailMatchesForSearchActivity.this, connect_response.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 my connect_response******" + t.toString());
                connect_btn_pb.setVisibility(View.GONE);

                if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void fetchLoginProfileData() {
        System.out.println("check test-------------->>>>>>>>>>");
        progressBar = ProgressDialog.show(DetailMatchesForSearchActivity.this, "", "Please Wait...");

        System.out.println("user_id=====>>>check" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                swipeRefreshLayout.setRefreshing(false);

                progressBar.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    fetchProfileLoginUser = response.body();
                    fetchProfileData();
                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                progressBar.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
                            PopupMenu popup = new PopupMenu(DetailMatchesForSearchActivity.this, view);
                            popup.setOnMenuItemClickListener(DetailMatchesForSearchActivity.this);

                            MenuInflater inflater = popup.getMenuInflater();
                            inflater.inflate(R.menu.option_menu, popup.getMenu());

                            popup.show();
                        }
                    });


                    option_LL1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupMenu popup = new PopupMenu(DetailMatchesForSearchActivity.this, view);
                            popup.setOnMenuItemClickListener(DetailMatchesForSearchActivity.this);

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

                if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void openBottomSheet(FetchProfile fetchProfile) {


        mBottomSheetDialog = new BottomSheetDialog(DetailMatchesForSearchActivity.this);
        sheetView = getLayoutInflater().inflate(R.layout.inbox_bottom_sheet_content, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        close_btn = sheetView.findViewById(R.id.close_btn);
        img_box = sheetView.findViewById(R.id.img_box);
        contact_no_b = sheetView.findViewById(R.id.contact_no);
        whatapp_no = sheetView.findViewById(R.id.whatapp_no);
        chat = sheetView.findViewById(R.id.chat);
        mail = sheetView.findViewById(R.id.mail);
        upgrade_now_btn = sheetView.findViewById(R.id.upgrade_now_btn);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });


        System.out.println("bottom img=========" + fetchProfile.getImages().get(0));
        Glide.with(getApplicationContext()).load(String.valueOf(fetchProfile.getImages().get(0).getImgs())).into(img_box);
        contact_no_b.setText("+91-**********");
        whatapp_no.setText("Chat via Whatsapp");
        chat.setText("Message via Shaadi Chat");
        mail.setText("***************@gmail.com");

        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailMatchesForSearchActivity.this, PremiumActivity.class);
                startActivity(i);
                mBottomSheetDialog.dismiss();
            }
        });


    }

    public void acceptProfile(String member_id, String login_user_id) {

        connect_btn_pb.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.requestAccepted(member_id, login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                acceptResponse = response.body();
                connect_btn_pb.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (acceptResponse.getResid().equals("200")) {
                        fetchProfileData();

                        Toast.makeText(DetailMatchesForSearchActivity.this, "Request Accept.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(DetailMatchesForSearchActivity.this, acceptResponse.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                connect_btn_pb.setVisibility(View.GONE);

                System.out.println("msg1 error accept******" + t.toString());

            }
        });


    }

    //    @Override
//    protected void onDestroy() {
//        unregisterReceiver(networkStateReceiver);
//        networkStateReceiver.removeListener(this);
//
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onPause() {
//        /***/
//        unregisterReceiver(networkStateReceiver);
//
//        super.onPause();
//    }
    private void blockUser() {
        Api apiService = RetrofitClient.getApiService();
        Call<CommonResponse> commonResponseCall = apiService.blockAccount(people_userid, String.valueOf(user.getUser_id()));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                if (response.isSuccessful()) {
                    if (commonResponse.getResid() == 200) {
                        finish();
                        Toast.makeText(DetailMatchesForSearchActivity.this, "Block this user", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailMatchesForSearchActivity.this, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                System.out.println("err UpdateResponse******" + t.toString());
                if (!isNetworkAvailable(DetailMatchesForSearchActivity.this)) {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(DetailMatchesForSearchActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void toast(String message) {
        Toast toast = new Toast(this);
        View view = LayoutInflater.from(this).inflate(R.layout.item_toast_red, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_dia);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}

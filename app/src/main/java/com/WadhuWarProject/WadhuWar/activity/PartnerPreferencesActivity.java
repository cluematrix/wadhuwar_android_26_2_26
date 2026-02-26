package com.WadhuWarProject.WadhuWar.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.AgeList;
import com.WadhuWarProject.WadhuWar.model.CasteList;
import com.WadhuWarProject.WadhuWar.model.ColorcomplexList;
import com.WadhuWarProject.WadhuWar.model.CountryList;
import com.WadhuWarProject.WadhuWar.model.DietaryList;
import com.WadhuWarProject.WadhuWar.model.DistrictList;
import com.WadhuWarProject.WadhuWar.model.EducationList;
import com.WadhuWarProject.WadhuWar.model.EducationType;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.HeightList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.LifeSettingList;
import com.WadhuWarProject.WadhuWar.model.MaritalSettingList;
import com.WadhuWarProject.WadhuWar.model.MothertoungeList;
import com.WadhuWarProject.WadhuWar.model.OccupationList;
import com.WadhuWarProject.WadhuWar.model.OccupationType;
import com.WadhuWarProject.WadhuWar.model.ReligionList;
import com.WadhuWarProject.WadhuWar.model.SalaryList;
import com.WadhuWarProject.WadhuWar.model.SampradayList;
import com.WadhuWarProject.WadhuWar.model.StateList;
import com.WadhuWarProject.WadhuWar.model.SubCasteList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartnerPreferencesActivity extends AppCompatActivity implements

        NetworkStateReceiver.NetworkStateReceiverListener {

    LinearLayout educationFlexBothLL;
    String incomeYearly;
    LinearLayout education_flexLL;
    RecyclerView education_type_old;
    String status_education_type = "0";
    String status_education = "0";

    LinearLayout occupationFlexBothLL;
    LinearLayout occupation_flexLL;
    LinearLayout marital_status_flexLL;
    RecyclerView occupation_type_old;
    String status_occupation_type = "0";
    String status_occupation = "0";
    boolean isViewMore = false;

    String districtId;

    UserData user;

    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;

    EditText expectation;
    TextView step1_view_more, step2_view_more, step3_view_more, step4_view_more, step5_view_more;
    CardView step2_cv, step3_cv, step4_cv, step5_cv, step6_cv;
    LinearLayout update_btn;
    List<String> selectedEducationList = new ArrayList<>();
    List<String> selectedEducationIdList = new ArrayList<>();
    //    List<String> education_type_array = new ArrayList<>();
    String getEducation_type_id = "";
    String education_type_str = "";

    FetchProfile fetchPartnerPref;
    Toolbar toolbar;
    Spinner marital_status_spinner;
    Spinner colorcomplex_spinner;
    String getPref_education_name;
    String getPref_education_id;
    Spinner height_spinner;
    Spinner height_spinner_to;
    Spinner religion_spinner;
    Spinner caste_spinner;
    Spinner sub_caste_spinner;
    Spinner highest_education_spinner;
    Spinner lifestyle_spinner;
    Spinner dietary_spinner;
    Spinner agefrom_spinner;
    Spinner ageto_spinner;
    Spinner sampraday_spinner;
    Spinner education_type_spinner;
    Spinner occupation_type_spinner;
    Spinner occupation_spinner;
    Spinner salary_spinner;
    Spinner current_state_spinner;
    Spinner current_district_spinner;
    Spinner current_country_spinner;
    Spinner mother_tounge_spinner;
    Spinner current_district_job_spinner;
    Spinner current_country_job_spinner;
    Spinner current_state_job_spinner;

    TextView marital_status_spinner1;
    String maritial_st;

    ArrayList<String> selectedSalaryList = new ArrayList<>();
    ArrayList<String> selectedDietaryList = new ArrayList<>();
    ArrayList<String> selectedLifestyleList = new ArrayList<>();
    ArrayList<String> selectedColorComplexList = new ArrayList<>();

    ArrayList<EducationType.EducationTypeList> educationTypeLists = new ArrayList<>();
    EducationType educationType;

    ArrayList<OccupationType.OccupationTypeData> occupationTypeLists = new ArrayList<>();
    OccupationType occupationType;


    ArrayList<OccupationList.OccupationListData> occupationListData = new ArrayList<>();
    OccupationList occupationList;
    ArrayList<MaritalSettingList.MaritalSettingListData> maritalSettingLists = new ArrayList<>();
    MaritalSettingList maritalSettingList;
    ArrayList<ColorcomplexList.ColorcomplexListData> colorcomplexLists = new ArrayList<>();
    ColorcomplexList colorcomplexList;
    ArrayList<HeightList.HeightListData> heightListDataFrom = new ArrayList<>();
    ArrayList<HeightList.HeightListData> heightListDataTo = new ArrayList<>();
    HeightList heightList;
    ArrayList<ReligionList.ReligionListData> religionListData = new ArrayList<>();
    ReligionList religionList;
    ArrayList<CasteList.CasteListData> casteListData = new ArrayList<>();
    CasteList casteList;
    ArrayList<SubCasteList> subCasteLists = new ArrayList<>();
    ArrayList<DietaryList.DietaryListData> dietaryLists = new ArrayList<>();
    ArrayList<String> sub_caste_array = new ArrayList<String>();
    ArrayList<String> sub_caste_array_id = new ArrayList<String>();
    List<String> selectedCasteList = new ArrayList<>();
    List<String> caste_array = new ArrayList<>();
    ArrayList<String> dietary_array = new ArrayList<String>();
    ArrayList<String> dietary_array_id = new ArrayList<String>();

    ArrayList<String> lifestyle_array = new ArrayList<String>();
    ArrayList<String> lifestyle_array_id = new ArrayList<String>();
    List<String> selectedDistrictList = new ArrayList<>();


    List<String> selectedOccupationList = new ArrayList<>();
    List<String> occupation_array = new ArrayList<>();
    List<Integer> occupation_array_ids = new ArrayList<>();

    List<String> selectedCasteIdList = new ArrayList<>(); // caste IDs

    ArrayList<String> color_complexion_array = new ArrayList<String>();
    ArrayList<String> color_complexion_array_id = new ArrayList<String>();

    ArrayList<String> marital_status_array = new ArrayList<String>();
    ArrayList<String> marital_status_array_id = new ArrayList<String>();

    ArrayList<String> district_array = new ArrayList<String>();
    ArrayList<String> district_array_id = new ArrayList<String>();

    ArrayList<String> district_job_array = new ArrayList<String>();
    ArrayList<String> district_job_array_id = new ArrayList<String>();

    private Map<String, String> district_job_id_map = new HashMap<>();
    List<String> districtIds = new ArrayList<>(district_job_id_map.values());
// Use districtIds as needed

    ArrayList<String> mothertounge_array = new ArrayList<String>();
    ArrayList<String> mothertounge_array_id = new ArrayList<String>();

    ArrayList<EducationList.EducationListData> educationListData = new ArrayList<>();
    EducationList educationList;
    ArrayList<LifeSettingList.LifeSettingListData> lifeSettingLists = new ArrayList<>();
    ArrayList<OccupationList.OccupationListData> OccupationList = new ArrayList<>();
    LifeSettingList lifeSettingList;
    DietaryList dietaryList;
    ArrayList<AgeList.AgeListData> ageListDataTo = new ArrayList<>();
    List<String> savedMaritalList = new ArrayList<>();

    AgeList ageListTo;
    ArrayList<AgeList.AgeListData> ageListDataFrom = new ArrayList<>();
    AgeList ageListFrom;
    ArrayList<SampradayList.SampradayListData> sampradayListData = new ArrayList<>();
    SampradayList sampradayList;

    InsertResponse edit_response;

    LinkedHashSet<String> listToSet_education, listToSet_education_type;
    LinkedHashSet<String> listToSet_occupation, listToSet_occupation_type;
    LinkedHashSet<String> listToSet_sub_caste;
    LinkedHashSet<String> listToSet_dietary;
    LinkedHashSet<String> listToSet_lifestyle;
    LinkedHashSet<String> listToSet_color_complexion;
    LinkedHashSet<String> listToSet_marital_status;
    LinkedHashSet<String> listToSet_district;
    LinkedHashSet<String> listToSet_district_job;
    LinkedHashSet<String> listToSet_mother_tongue;

    List<String> marital_list = new ArrayList<String>();
    List<String> colorcomplex_list = new ArrayList<String>();
    List<String> height_list_from = new ArrayList<String>();
    List<String> height_list_to = new ArrayList<String>();
    List<String> religion_list = new ArrayList<String>();
    List<String> caste_list = new ArrayList<String>();
    List<String> sub_caste_list = new ArrayList<String>();
    List<String> education_type_list = new ArrayList<String>();
    List<String> education_list = new ArrayList<String>();
    List<String> state_list = new ArrayList<String>();
    List<String> district_list = new ArrayList<String>();
    List<String> country_list = new ArrayList<String>();
    List<String> selectedDietaryIdList = new ArrayList<>();
    List<String> selectedSubCasteList = new ArrayList<>();
//    List<String> sub_caste_array = new ArrayList<>();
//    String sub_caste_str = "";

    List<String> state_job_list = new ArrayList<String>();
    List<String> selectedStateList = new ArrayList<String>();
    List<String> district_job_list = new ArrayList<String>();
    List<String> country_job_list = new ArrayList<String>();

    List<String> occupation_type_list = new ArrayList<String>();

    List<String> occupation_list = new ArrayList<String>();
    List<String> lifestyle_list = new ArrayList<String>();
    List<String> dietray_list = new ArrayList<String>();
    List<String> ageTo_list = new ArrayList<String>();
    List<String> ageFrom_list = new ArrayList<String>();
    List<String> sampraday_list = new ArrayList<String>();
    List<String> salary_list = new ArrayList<String>();
    List<String> mother_tongue_list = new ArrayList<String>();

    List<String> al = new ArrayList<String>();
    FlexboxLayout education_pckg_flex,occupation_pckg_flex,subcaste_flex,dietary_flex,lifestyle_flex,color_complexion_flex,marital_status_flex,district_flex,district_job_flex,mother_tongue_flex;

    LinearLayout education_type_flexLL,occupation_type_flexLL,subcaste_flexLL,dietary_flexLL,lifestyle_flexLL,color_complexion_flexLL,district_flexLL,district_job_flexLL,mother_tongue_flexLL;

    String subcaste_pckg_list,prefOccupation, dietary_str,dietary_pckg_list, lifestyle_pckg_list, color_complexion_pckg_list,
            marital_status_pckg_list, district_pckg_list,district_job_pckg_list, education_pckg_list, education_type_pckg_list,
            occupation_pckg_list, occupation_type_pckg_list, mother_tongue_pckg_list;
    String[] namesList;
    String[] selectedIds ;
    String marital_str,prefEduType,maritial_status, maritial_id,colorcomplex_str, height_str, height_to_str, religion_list_str, caste_list_str, sub_caste_str, education_list_str,
            lifestyle_str, dietray_str, ageTo_list_str, agefrom_list_str, sampraday_list_str, education_type_list_str, occupation_list_str, occupation_type_list_str;
    String maritalstatus_id,getDistrictId,getStateId,getSubCastId,getColorstatus_id, getLifestyle_id, getDietary_id ,getMaritalstatus_id, colorcomplex_id, height_id, religion_id, caste_id, subcaste_id, dietray_id,
            age_id, sampraday_id, education_id, lifestyle_id, education_type_id,
            occupation_id, occupation_type_id, salary_str, state_list_str = "", district_str = "",
            district_id, state_id, country_id, country_list_str, mother_tongue_str, mother_tongue_id
            , state_job_list_str = "", district_job_str = "", district_job_id, state_job_id, country_job_id, country_job_list_str;
    TextView education_type_txtv,occupation_type_txtv,marital_status_txtv,subcaste_txtv,dietary_txtv,lifestyle_txtv,color_complexion_txtv,district_txtv,mother_tounge_txtv;
    ArrayAdapter<String> adapter_marital;
    ArrayAdapter<String> colorcomplex_adapter;
    ArrayAdapter<String> height_adapter;
    ArrayAdapter<String> adapter_religion;
    ArrayAdapter<String> caste_adapter;
    ArrayAdapter<String> sub_caste_adapter;
    ArrayAdapter<String> adapter_education;
    ArrayAdapter<String> adapter_occupation;
    ArrayAdapter<String> lifestyle_adapter;
    ArrayAdapter<String> adapter_dietray;
    ArrayAdapter<String> adapter_ageTo;
    ArrayAdapter<String> adapter_ageFrom;
    ArrayAdapter<String> adapter_sampraday;
    ArrayAdapter<String> adapter_education_type;
    ArrayAdapter<String> adapter_occupation_type;
    ArrayAdapter<String> salary_adapter;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> district_adapter;
    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> adapter_state_job;
    ArrayAdapter<String> district_adapter_job;
    ArrayAdapter<String> adapter_country_job;


    ArrayAdapter<String> mother_tongue_adapter;
    String _expectation;

    ProgressDialog progressBar;
    InsertResponse insertResponse;

    String user_id;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> marital_list_id;   // ids from API


    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;


    TextView textView2, textView1, textView3;
    TextView textView2_type, textView1_type, textView3_type;
    ArrayList<String> education_pck_array = new ArrayList<String>();
    ArrayList<String> occupation_pck_array = new ArrayList<String>();
    ArrayList<String> marital_status_pck_array = new ArrayList<String>();
    ArrayList<String> education_pck_array_education_name_id = new ArrayList<String>();
    ArrayList<String> occupation_pck_array_occupation_name_id = new ArrayList<String>();
    ArrayList<String> education_pck_array_new = new ArrayList<String>();
    ArrayList<String> education_pck_array_id = new ArrayList<String>();
    ArrayList<String> occupation_pck_array_id = new ArrayList<String>();
    String package_id;
    String occupation_package_id;
    LinkedHashSet<String> listToSet;
    LinkedHashSet<String> occupation_listToSet;
    String pckg_list;

    FlexboxLayout education_type_flex;
    FlexboxLayout occupation_type_flex;
    ArrayList<String> education_type_array = new ArrayList<String>();
    ArrayList<String> occupation_type_array = new ArrayList<String>();
    ArrayList<String> education_type_array_unique = new ArrayList<String>();
    ArrayList<String> occupation_type_array_unique = new ArrayList<String>();
    ArrayList<String> education_type_array_id = new ArrayList<String>();
    ArrayList<String> occupation_type_array_id = new ArrayList<String>();
    ArrayList<SalaryList.SalaryListData> salaryListData = new ArrayList<>();
    SalaryList salaryList;
    ArrayList<StateList> stateListsData = new ArrayList<>();
    ArrayList<StateList> stateJobListsData = new ArrayList<>();
    StateList stateList,stateJobList;
    ArrayList<DistrictList.DistrictListData> districtListData = new ArrayList<>();
    ArrayList<DistrictList.DistrictListData> districtJobListData = new ArrayList<>();
    DistrictList districtList,districtJobList;
    ArrayList<String> selectedMotherTongueList = new ArrayList<>();
    ArrayList<String> motherTongueArray = new ArrayList<>();


    ArrayList<CountryList.CountryListData> countryListData = new ArrayList<>();
    ArrayList<CountryList.CountryListData> countryJobListData = new ArrayList<>();
    CountryList countryList,countryJobList;
    ArrayList<MothertoungeList.MothertoungeListData> mothertoungeListData = new ArrayList<>();
    MothertoungeList mothertoungeList;


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
        setContentView(R.layout.activity_partner_preferences);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
//        setContentView(R.layout.activity_partner_preferences);

        step1_view_more = findViewById(R.id.step1_view_more);
        step2_view_more = findViewById(R.id.step2_view_more);
        step3_view_more = findViewById(R.id.step3_view_more);
        step4_view_more = findViewById(R.id.step4_view_more);
        step5_view_more = findViewById(R.id.step5_view_more);
        step2_cv = findViewById(R.id.step2_cv);
        step3_cv = findViewById(R.id.step3_cv);
        step4_cv = findViewById(R.id.step4_cv);
        step5_cv = findViewById(R.id.step5_cv);
        step6_cv = findViewById(R.id.step6_cv);

        occupationFlexBothLL = findViewById(R.id.occupationFlexBothLL);
        occupation_pckg_flex = findViewById(R.id.occupation_pckg_flex);
        occupation_flexLL = findViewById(R.id.occupation_flexLL);
        occupation_type_flex = findViewById(R.id.occupation_type_flex);
        subcaste_flexLL = findViewById(R.id.sub_caste_flexLL);
        subcaste_flex = findViewById(R.id.sub_caste_flex);
        dietary_flexLL = findViewById(R.id.dietary_flexLL);
        dietary_flex = findViewById(R.id.dietary_flex);
        lifestyle_flexLL = findViewById(R.id.lifestyle_flexLL);
        lifestyle_flex = findViewById(R.id.lifestyle_flex);
        color_complexion_flexLL = findViewById(R.id.color_complexion_flexLL);
        color_complexion_flex = findViewById(R.id.color_complexion_flex);
        occupation_spinner = findViewById(R.id.occupation_spinner);
        education_flexLL = findViewById(R.id.education_flexLL);
        educationFlexBothLL = findViewById(R.id.educationFlexBothLL);
        education_type_flexLL = findViewById(R.id.education_type_flexLL);
        occupation_type_flexLL = findViewById(R.id.occupation_type_flexLL);
        education_type_flex = findViewById(R.id.education_type_flex);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        education_type_spinner = findViewById(R.id.education_type_spinner);
        occupation_type_spinner = findViewById(R.id.occupation_type_spinner);
        update_btn = findViewById(R.id.update_btn);
        marital_status_spinner = findViewById(R.id.marital_status_spinner);
        marital_status_spinner1 = findViewById(R.id.marital_status_spinner1);
        marital_status_flexLL = findViewById(R.id.marital_status_flexLL);
        marital_status_flex = findViewById(R.id.marital_status_flex);
        colorcomplex_spinner = findViewById(R.id.colorcomplex_spinner);
        height_spinner = findViewById(R.id.height_spinner);
        height_spinner_to = findViewById(R.id.height_spinner_to);
        religion_spinner = findViewById(R.id.religion_spinner);
        caste_spinner = findViewById(R.id.caste_spinner);
        sub_caste_spinner = findViewById(R.id.sub_caste_spinner);
        highest_education_spinner = findViewById(R.id.highest_education_spinner);
        lifestyle_spinner = findViewById(R.id.lifestyle_spinner);
        dietary_spinner = findViewById(R.id.dietary_spinner);
        agefrom_spinner = findViewById(R.id.agefrom_spinner);
        ageto_spinner = findViewById(R.id.ageto_spinner);
        sampraday_spinner = findViewById(R.id.sampraday_spinner);
        expectation = findViewById(R.id.expectation);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        internetOffRL = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        education_pckg_flex = (FlexboxLayout) findViewById(R.id.education_pckg_flex);
        occupation_pckg_flex = (FlexboxLayout) findViewById(R.id.occupation_pckg_flex);
        salary_spinner = findViewById(R.id.salary_spinner);
        current_country_spinner = findViewById(R.id.current_country_spinner);
        current_state_spinner = findViewById(R.id.current_state_spinner);
        current_district_job_spinner = findViewById(R.id.current_district_job_spinner);
        current_country_job_spinner = findViewById(R.id.current_country_job_spinner);
        current_state_job_spinner = findViewById(R.id.current_state_job_spinner);
        current_district_spinner = findViewById(R.id.current_district_spinner);
        mother_tounge_spinner = findViewById(R.id.mother_tounge_spinner);
        district_flex = findViewById(R.id.district_flex);
        district_flexLL = findViewById(R.id.district_flexLL);
        district_job_flex = findViewById(R.id.district_job_flex);
        district_job_flexLL = findViewById(R.id.district_job_flexLL);

        mother_tongue_flex = findViewById(R.id.mother_tongue_flex);
        mother_tongue_flexLL = findViewById(R.id.mother_tongue_flexLL);

        user = SharedPrefManager.getInstance(PartnerPreferencesActivity.this).getUser();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("My Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i = new Intent(PartnerPreferencesFormActivity.this,AboutMeAndMembershipActivity.class);
//                startActivity(i);

                _expectation = expectation.getText().toString();
//                if (allDone()) {

                if (marital_str != null && !marital_str.contentEquals("")) {
                    for (int i = 0; i < maritalSettingLists.size(); i++) {
                        if (marital_str.equals(maritalSettingLists.get(i).getName())) {
                            maritalstatus_id = String.valueOf(maritalSettingLists.get(i).getId());
                        }
                    }
                } else {
                    maritalstatus_id = "Doesn't Matter";
                }

                if (maritalstatus_id == null) {
                    maritalstatus_id = "Doesn't Matter";
                }
                System.out.println("colorcomplex_str=========" + colorcomplex_str);

                if (colorcomplex_str != null && !colorcomplex_str.isEmpty() && !colorcomplex_str.equals("Doesn't Matter")) {

                    // User selected a proper value (not empty & not "Doesn't Matter")
                    for (int i = 0; i < colorcomplexLists.size(); i++) {
                        if (colorcomplex_str.equals(colorcomplexLists.get(i).getName())) {
                            colorcomplex_id = String.valueOf(colorcomplexLists.get(i).getId());
                            break; // stop loop once found
                        }
                    }

                } else if ("Doesn't Matter".equals(colorcomplex_str)) {

                    // User selected "Doesn't Matter"
                    colorcomplex_id = "Doesn't Matter";
                    Toast.makeText(PartnerPreferencesActivity.this, "desent matter", Toast.LENGTH_SHORT).show();


                } else {

                    // User didn't select anything (null or empty)
                    if (getColorstatus_id == null || getColorstatus_id.isEmpty()) {
                        colorcomplex_id = "";
//                        Toast.makeText(PartnerPreferencesActivity.this, "empty", Toast.LENGTH_SHORT).show();
                    } else {
                        colorcomplex_id = getColorstatus_id;
                        color_complexion_pckg_list = getColorstatus_id;
//                        Toast.makeText(PartnerPreferencesActivity.this, "getColorstatus_id" +getColorstatus_id, Toast.LENGTH_SHORT).show();

                    }
                }


                if (religion_list_str != null && !religion_list_str.contentEquals("") && !religion_list_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < religionListData.size(); i++) {
                        if (religion_list_str.equals(religionListData.get(i).getName())) {
                            religion_id = String.valueOf(religionListData.get(i).getId());

                        }
                    }
                } else if (religion_list_str != null && religion_list_str.equals("Doesn't Matter")) {
                    religion_id = "Doesn't Matter";

                } else {
                    religion_id = "";
                }

                if (caste_list_str != null && !caste_list_str.contentEquals("") && !caste_list_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < casteListData.size(); i++) {
                        if (caste_list_str.equals(casteListData.get(i).getName())) {
                            caste_id = String.valueOf(casteListData.get(i).getId());

                        }
                    }
                } else if (caste_list_str != null && caste_list_str.equals("Doesn't Matter")) {
                    caste_id = "Doesn't Matter";

                } else {
                    caste_id = "";
                }

                if (country_list_str != null && !country_list_str.contentEquals("") && !country_list_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < countryListData.size(); i++) {
                        if (country_list_str.equals(countryListData.get(i).getName())) {
                            country_id = String.valueOf(countryListData.get(i).getId());

                        }
                    }
                } else if (country_list_str != null && country_list_str.equals("Doesn't Matter")) {
                    country_id = "Doesn't Matter";

                } else {
                    country_id = "";
                }


                if (state_list_str != null && !state_list_str.contentEquals("") && !state_list_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < stateListsData.size(); i++) {
                        if (state_list_str.equals(stateListsData.get(i).getSc_name())) {
                            state_id = String.valueOf(stateListsData.get(i).getSc_id());

                        }
                    }
                } else if (state_list_str != null && state_list_str.equals("Doesn't Matter")) {
                    state_id = "Doesn't Matter";

                } else {
                    state_id = "";
                }

                if (district_str != null && !district_str.contentEquals("") && !district_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < districtListData.size(); i++) {
                        if (district_str.equals(districtListData.get(i).getName())) {
                            district_id = String.valueOf(districtListData.get(i).getId());
                        }
                    }
                } else if (district_str != null && district_str.equals("Doesn't Matter")) {
                    district_id = "Doesn't Matter";
                } else {
                    district_id = "";
                }

                /*subcaste list */
                sub_caste_array_id.clear();
                for (int j = 0; j < sub_caste_array.size(); j++) {
                    System.out.println("111 character name----------" + sub_caste_array.get(j));


                    for (int i = 0; i < subCasteLists.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                        if (sub_caste_array.get(j).equals(subCasteLists.get(i).getSc_name())) {


                            subcaste_id = String.valueOf(subCasteLists.get(i).getSc_id());

                            sub_caste_array_id.add(subcaste_id);


                            System.out.println("packages id 111----------" + subCasteLists);
                            System.out.println("222 character name----------" + subCasteLists.get(i).getSc_name());


                        }
                    }


                }

                for (int i = 0; i < sub_caste_array_id.size(); i++) {
                    System.out.println("packages id 000----------" + sub_caste_array_id.get(i));

                }


                listToSet_sub_caste = new LinkedHashSet<String>(sub_caste_array_id);

                StringBuilder subcaste_sbString = new StringBuilder("");

                for (String language : listToSet_sub_caste) {

                    subcaste_sbString.append(language).append(",");
                }

                subcaste_pckg_list = subcaste_sbString.toString();
                if (subcaste_pckg_list.length() > 0)
                    subcaste_pckg_list = subcaste_pckg_list.substring(0, subcaste_pckg_list.length() - 1);


                System.out.println("subcaste>>>>>>>>>>>>>>>" + subcaste_pckg_list);
                /*end subcaste list*/


                /*dietary list */
                dietary_array_id.clear();
                for (int j = 0; j < dietary_array.size(); j++) {
                    System.out.println("111 character name----------" + dietary_array.get(j));


                    for (int i = 0; i < dietaryLists.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                        if (dietary_array.get(j).equals(dietaryLists.get(i).getName())) {


                            dietray_id = String.valueOf(dietaryLists.get(i).getId());

                            dietary_array_id.add(dietray_id);


                            System.out.println("packages id 111----------" + dietaryLists);
                            System.out.println("222 character name----------" + dietaryLists.get(i).getName());


                        }
                    }


                }

                for (int i = 0; i < dietary_array_id.size(); i++) {
                    System.out.println("packages id 000----------" + dietary_array_id.get(i));

                }


                listToSet_dietary = new LinkedHashSet<String>(dietary_array_id);

                StringBuilder dietary_sbString = new StringBuilder("");

                for (String language : listToSet_dietary) {

                    dietary_sbString.append(language).append(",");
                }

                dietary_pckg_list = dietary_sbString.toString();
                if (dietary_pckg_list.length() > 0)
                    dietary_pckg_list = dietary_pckg_list.substring(0, dietary_pckg_list.length() - 1);


                System.out.println("subcaste>>>>>>>>>>>>>>>" + dietary_pckg_list);
                /*end dietary list*/


                /*lifestyle list */
                lifestyle_array_id.clear();
                for (int j = 0; j < lifestyle_array.size(); j++) {
                    System.out.println("111 character name----------" + lifestyle_array.get(j));


                    for (int i = 0; i < lifeSettingLists.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                        if (lifestyle_array.get(j).equals(lifeSettingLists.get(i).getName())) {


                            lifestyle_id = String.valueOf(lifeSettingLists.get(i).getId());

                            lifestyle_array_id.add(lifestyle_id);


                            System.out.println("packages id 111----------" + lifeSettingLists);
                            System.out.println("222 character name----------" + lifeSettingLists.get(i).getName());


                        }
                    }


                }

                for (int i = 0; i < lifestyle_array_id.size(); i++) {
                    System.out.println("packages id 000----------" + lifestyle_array_id.get(i));

                }


                listToSet_lifestyle = new LinkedHashSet<String>(lifestyle_array_id);

                StringBuilder lifestyle_sbString = new StringBuilder("");

                for (String language : listToSet_lifestyle) {

                    lifestyle_sbString.append(language).append(",");
                }

                lifestyle_pckg_list = lifestyle_sbString.toString();
                if (lifestyle_pckg_list.length() > 0)
                    lifestyle_pckg_list = lifestyle_pckg_list.substring(0, lifestyle_pckg_list.length() - 1);


                System.out.println("subcaste>>>>>>>>>>>>>>>" + lifestyle_pckg_list);
                /*end lifestyle list*/


                /*color complexion list */
                color_complexion_array_id.clear();
                for (int j = 0; j < color_complexion_array.size(); j++) {
                    System.out.println("111 character name----------" + color_complexion_array.get(j));


                    for (int i = 0; i < colorcomplexLists.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                        if (color_complexion_array.get(j).equals(colorcomplexLists.get(i).getName())) {

                            colorcomplex_id = String.valueOf(colorcomplexLists.get(i).getId());

                            color_complexion_array_id.add(colorcomplex_id);

                            System.out.println("packages id 111----------" + colorcomplexLists);
                            System.out.println("222 character name----------" + colorcomplexLists.get(i).getName());


                        }
                    }


                }

                for (int i = 0; i < color_complexion_array_id.size(); i++) {
                    System.out.println("packages id 000----------" + color_complexion_array_id.get(i));

                }


                listToSet_color_complexion = new LinkedHashSet<String>(color_complexion_array_id);

                StringBuilder color_complexion_sbString = new StringBuilder("");

                for (String language : listToSet_color_complexion) {

                    color_complexion_sbString.append(language).append(",");
                }

                color_complexion_pckg_list = color_complexion_sbString.toString();
                if (color_complexion_pckg_list.length() > 0)
                    color_complexion_pckg_list = color_complexion_pckg_list.substring(0, color_complexion_pckg_list.length() - 1);


                System.out.println("subcaste>>>>>>>>>>>>>>>" + color_complexion_pckg_list);
                /*end color complexion list*/


                /* Marital Status list */
                marital_status_array_id.clear();
                for (int j = 0; j < marital_status_array.size(); j++) {
                    System.out.println("111 character name----------" + marital_status_array.get(j));


                    for (int i = 0; i < maritalSettingLists.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                        if (marital_status_array.get(j).equals(maritalSettingLists.get(i).getName())) {


                            maritalstatus_id = String.valueOf(maritalSettingLists.get(i).getId());

                            marital_status_array_id.add(maritalstatus_id);


                            System.out.println("packages id 111----------" + maritalSettingLists);
                            System.out.println("222 character name----------" + maritalSettingLists.get(i).getName());


                        }
                    }


                }

                for (int i = 0; i < marital_status_array_id.size(); i++) {
                    System.out.println("packages id 000----------" + marital_status_array_id.get(i));

                }


                listToSet_marital_status = new LinkedHashSet<String>(marital_status_array_id);

                StringBuilder marital_status_sbString = new StringBuilder("");

                for (String language : listToSet_marital_status) {

                    marital_status_sbString.append(language).append(",");
                }

                marital_status_pckg_list = marital_status_sbString.toString();
                if (marital_status_pckg_list.length() > 0) {
                    marital_status_pckg_list = marital_status_pckg_list.substring(0, marital_status_pckg_list.length() - 1);
                } else {
//                    if (getMaritalstatus_id == null){
                        marital_status_pckg_list = "";
//                    }else {
//                        marital_status_pckg_list=getMaritalstatus_id;
//                    }

                }

                System.out.println("subcaste>>>>>>>>>>>>>>>" + marital_status_pckg_list);
                /*end Marital Status list*/


                /*district list */


                if (district_array.size() != 0) {

                    district_array_id.clear();
                    for (int j = 0; j < district_array.size(); j++) {
                        System.out.println("111 education character name----------" + district_array.get(j));

                        for (int i = 0; i < districtListData.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                            if (district_array.get(j).trim().equals(districtListData.get(i).getName().trim())) {


                                district_id = String.valueOf(districtListData.get(i).getId());

                                district_array_id.add(district_id);


                                System.out.println("packages education  id 111----------" + districtListData);
                                System.out.println("222  education character name----------" + districtListData.get(i).getName());


                            }
                        }

                    }


                    for (int i = 0; i < district_array_id.size(); i++) {
                        System.out.println("packages id 000----------" + district_array_id.get(i));

                    }


                    listToSet_district = new LinkedHashSet<String>(district_array_id);

                    StringBuilder sbString = new StringBuilder("");

                    for (String language : listToSet_district) {

                        sbString.append(language).append(",");
                    }

                    district_pckg_list = sbString.toString();
                    if (district_pckg_list.length() > 0)
                        district_pckg_list = district_pckg_list.substring(0, district_pckg_list.length() - 1);


                    System.out.println("strList>>>>>>>>>>>>>>>" + district_pckg_list);
                } else {
                    district_pckg_list = "";
                }


                if (district_job_array.size() != 0) {

                    district_job_array_id.clear();
                    for (int j = 0; j < district_job_array.size(); j++) {
                        System.out.println("111 education character name----------" + district_job_array.get(j));

                        for (int i = 0; i < districtJobListData.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                            if (district_job_array.get(j).trim().equals(districtJobListData.get(i).getName().trim())) {


                                district_job_id = String.valueOf(districtJobListData.get(i).getId());

                                district_job_array_id.add(district_job_id);


                                System.out.println("packages education  id 111----------" + districtJobListData);
                                System.out.println("222  education character name----------" + districtJobListData.get(i).getName());


                            }
                        }

                    }


                    for (int i = 0; i < district_job_array_id.size(); i++) {
                        System.out.println("packages id 000----------" + district_job_array_id.get(i));

                    }


                    listToSet_district_job = new LinkedHashSet<String>(district_job_array_id);

                    StringBuilder sbString = new StringBuilder("");

                    for (String language : listToSet_district_job) {

                        sbString.append(language).append(",");
                    }

                    district_job_pckg_list = sbString.toString();
                    if (district_job_pckg_list.length() > 0)
                        district_job_pckg_list = district_job_pckg_list.substring(0, district_job_pckg_list.length() - 1);


                    System.out.println("strList>>>>>>>>>>>>>>>" + district_job_pckg_list);
                } else {
                    district_job_pckg_list = "";
                }



                /*end district list*/

                if (sub_caste_str != null && !sub_caste_str.contentEquals("") && !sub_caste_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < subCasteLists.size(); i++) {
                        if (sub_caste_str.equals(subCasteLists.get(i).getSc_name())) {
                            subcaste_id = String.valueOf(subCasteLists.get(i).getSc_id());


                        }
                    }
                } else if (sub_caste_str != null && sub_caste_str.equals("Doesn't Matter")) {
                    subcaste_id = "Doesn't Matter";

                } else {
//                                            Toast.makeText(PartnerPreferencesActivity.this, "1", Toast.LENGTH_SHORT).show();

                    if (getSubCastId == null){
                        subcaste_id = "";
//                        Toast.makeText(PartnerPreferencesActivity.this, "2", Toast.LENGTH_SHORT).show();

//                        subcaste_id="1017";
                    } else {
//                        subcaste_id = "1017";
                        sub_caste_str=getSubCastId;
//                        Toast.makeText(PartnerPreferencesActivity.this, "3 " + subcaste_id,  Toast.LENGTH_SHORT).show();

                    }

                }


                if (lifestyle_str != null && !lifestyle_str.contentEquals("") && !lifestyle_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < lifeSettingLists.size(); i++) {
                        if (lifestyle_str.equals(lifeSettingLists.get(i).getName())) {
                            lifestyle_id = String.valueOf(lifeSettingLists.get(i).getId());

                        }
                    }
                } else if (lifestyle_str != null && lifestyle_str.equals("Doesn't Matter")) {
                    lifestyle_id = "Doesn't Matter";

                } else {
//                    if (getLifestyle_id == null ||
//                            getLifestyle_id.trim().isEmpty() ||
//                            getLifestyle_id.equalsIgnoreCase("null")) {

                        lifestyle_id = "";
//                        Toast.makeText(PartnerPreferencesActivity.this, "empty", Toast.LENGTH_SHORT).show();

//                    } else {

//                        lifestyle_id = getLifestyle_id;
//                        lifestyle_pckg_list = getLifestyle_id;
//                        Toast.makeText(PartnerPreferencesActivity.this, " non empty", Toast.LENGTH_SHORT).show();
//                    }
                }


                if (dietray_str != null && !dietray_str.contentEquals("") && !dietray_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < dietaryLists.size(); i++) {
                        if (dietray_str.equals(dietaryLists.get(i).getName())) {
                            dietray_id = String.valueOf(dietaryLists.get(i).getId());

                        }
                    }
                } else if (dietray_str != null && dietray_str.equals("Doesn't Matter")) {
//                } else if (dietray_str.equals("Doesn't Matter")) {
                    dietray_id = "Doesn't Matter";

                } else {
                    if (getDietary_id == null ){
                        dietray_id = "";
                    } else{
                        dietray_id=getDietary_id;
                        dietary_pckg_list=getDietary_id;
                    }



                }

                if (salary_str != null && !salary_str.contentEquals("") && !salary_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < salaryListData.size(); i++) {
                        if (salary_str.equals(salaryListData.get(i).getName())) {
                            salary_str = String.valueOf(salaryListData.get(i).getName());

                        }
                    }
                } else if (salary_str != null && salary_str.equals("Doesn't Matter")) {
                    salary_str = "Doesn't Matter";

                } else {
                    salary_str = "";

                }

                if (sampraday_list_str != null && !sampraday_list_str.contentEquals("") && !sampraday_list_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < sampradayListData.size(); i++) {
                        if (sampraday_list_str.equals(sampradayListData.get(i).getName())) {
                            sampraday_id = String.valueOf(sampradayListData.get(i).getId());

                        }
                    }
                } else if (sampraday_list_str != null && sampraday_list_str.equals("Doesn't Matter")) {
                    sampraday_id = "Doesn't Matter";

                } else {
                    sampraday_id = "";

                }

                if (height_str == null) {
                    height_str = "";
                } else if (height_str.equals("Select Height From")) {
                    height_str = "";
                }

                if (height_to_str == null) {
                    height_to_str = "";
                } else if (height_to_str.equals("Select Height To")) {
                    height_to_str = "";
                }

                if (agefrom_list_str == null) {
                    agefrom_list_str = "";
                } else if (agefrom_list_str.equals("Select Age")) {
                    agefrom_list_str = "";
                }
                if (ageTo_list_str == null) {
                    ageTo_list_str = "";
                } else if (ageTo_list_str.equals("Select Age")) {
                    ageTo_list_str = "";
                }





                /*education type list */
                education_type_array_id.clear();
                for (int j = 0; j < education_type_array.size(); j++) {
                    System.out.println("111 character name----------" + education_type_array.get(j));


                    for (int i = 0; i < educationTypeLists.size(); i++) {

                        System.out.println("xz list  name>>>>>>>>>>>>>>>" + education_type_array.get(j));
                        System.out.println("yz list  name>>>>>>>>>>>>>>>" + educationTypeLists.get(i).getName());


                        if (education_type_array.get(j).trim().equals(educationTypeLists.get(i).getName().trim())) {


                            education_type_id = String.valueOf(educationTypeLists.get(i).getId());

                            education_type_array_id.add(education_type_id);


                            System.out.println("packages id 111----------" + educationTypeLists);
                            System.out.println("222 character name----------" + educationTypeLists.get(i).getName());


                        }
                    }


                }


                listToSet_education_type = new LinkedHashSet<String>(education_type_array_id);

                StringBuilder education_sbString_type = new StringBuilder("");

                for (String language : listToSet_education_type) {

                    education_sbString_type.append(language).append(",");
                }

                education_type_pckg_list = education_sbString_type.toString();
                if (education_type_pckg_list.length() > 0)
                    education_type_pckg_list = education_type_pckg_list.substring(0, education_type_pckg_list.length() - 1);


                System.out.println("education_type_pckg_list>>>>>>>>>>>>>>>" + education_type_pckg_list);
                /*end education type list*/


                /*education list */


                if (education_pck_array.size() != 0) {

                    education_pck_array_id.clear();
                    for (int j = 0; j < education_pck_array.size(); j++) {
                        System.out.println("111 education character name----------" + education_pck_array.get(j));

                        for (int i = 0; i < educationListData.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                            if (education_pck_array.get(j).trim().equals(educationListData.get(i).getName().trim())) {


                                package_id = String.valueOf(educationListData.get(i).getId());

                                education_pck_array_id.add(package_id);


                                System.out.println("packages education  id 111----------" + educationListData);
                                System.out.println("222  education character name----------" + educationListData.get(i).getName());


                            }
                        }

                    }


                    for (int i = 0; i < education_pck_array_id.size(); i++) {
                        System.out.println("packages id 000----------" + education_pck_array_id.get(i));

                    }


                    listToSet = new LinkedHashSet<String>(education_pck_array_id);

                    StringBuilder sbString = new StringBuilder("");

                    for (String language : listToSet) {

                        sbString.append(language).append(",");
                    }

                    pckg_list = sbString.toString();
                    if (pckg_list.length() > 0)
                        pckg_list = pckg_list.substring(0, pckg_list.length() - 1);


                    System.out.println("strList>>>>>>>>>>>>>>>" + pckg_list);
                } else {
                    pckg_list = "";
                }

                /*end education list*/


                /*occupation type list */
                occupation_type_array_id.clear();
                for (int j = 0; j < occupation_type_array.size(); j++) {
                    System.out.println("111 character name----------" + occupation_type_array.get(j));


                    for (int i = 0; i < occupationTypeLists.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                        if (occupation_type_array.get(j).trim().equals(occupationTypeLists.get(i).getName().trim())) {


                            occupation_type_id = String.valueOf(occupationTypeLists.get(i).getId());

                            occupation_type_array_id.add(occupation_type_id);


                            System.out.println("packages id 111----------" + occupationTypeLists);
                            System.out.println("222 character name----------" + occupationTypeLists.get(i).getName());


                        }
                    }


                }


                listToSet_occupation_type = new LinkedHashSet<String>(occupation_type_array_id);

                StringBuilder occupation_sbString_type = new StringBuilder("");

                for (String language : listToSet_occupation_type) {

                    occupation_sbString_type.append(language).append(",");
                }

                occupation_type_pckg_list = occupation_sbString_type.toString();
                if (occupation_type_pckg_list.length() > 0)
                    occupation_type_pckg_list = occupation_type_pckg_list.substring(0, occupation_type_pckg_list.length() - 1);


                System.out.println("occupation_type_pckg_list>>>>>>>>>>>>>>>" + occupation_type_pckg_list);
                /*end occupation type list*/



                /*occupation list */


                if (occupation_pck_array.size() != 0) {


                    occupation_pck_array_id.clear();
                    for (int j = 0; j < occupation_pck_array.size(); j++) {
                        System.out.println("111 occupation character name----------" + occupation_pck_array.get(j));

                        for (int i = 0; i < occupationListData.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                            if (occupation_pck_array.get(j).trim().equals(occupationListData.get(i).getName().trim())) {


                                occupation_package_id = String.valueOf(occupationListData.get(i).getId());

                                occupation_pck_array_id.add(occupation_package_id);


                                System.out.println("packages occupation  id 111----------" + occupationListData);
                                System.out.println("222  occupation character name----------" + occupationListData.get(i).getName());


                            }
                        }

                    }


                    for (int i = 0; i < occupation_pck_array_id.size(); i++) {
                        System.out.println("packages id 000----------" + occupation_pck_array_id.get(i));

                    }


                    occupation_listToSet = new LinkedHashSet<String>(occupation_pck_array_id);

                    StringBuilder sbString = new StringBuilder("");

                    for (String language : occupation_listToSet) {

                        sbString.append(language).append(",");
                    }

                    occupation_pckg_list = sbString.toString();
                    if (occupation_pckg_list.length() > 0)
                        occupation_pckg_list = occupation_pckg_list.substring(0, occupation_pckg_list.length() - 1);


                    System.out.println("strList>>>>>>>>>>>>>>>" + occupation_pckg_list);
                } else {
                    occupation_pckg_list = "";
                }

                /*end occupation list*/



                /*mother_tongue list */


                if (mothertounge_array.size() != 0) {


                    mothertounge_array_id.clear();
                    for (int j = 0; j < mothertounge_array.size(); j++) {
                        System.out.println("111 mothertounge_array character name----------" + mothertounge_array.get(j));

                        for (int i = 0; i < mothertoungeListData.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                            if (mothertounge_array.get(j).trim().equals(mothertoungeListData.get(i)
                                    .getName().trim())) {


                                mother_tongue_id = String.valueOf(mothertoungeListData.get(i).getId());

                                mothertounge_array_id.add(mother_tongue_id);


                                System.out.println("packages occupation  id 111----------" + mothertoungeListData);
                                System.out.println("222  occupation character name----------" + mothertoungeListData.get(i).getName());


                            }
                        }

                    }


                    for (int i = 0; i < mothertounge_array_id.size(); i++) {
                        System.out.println("packages id 000----------" + mothertounge_array_id.get(i));

                    }


                    listToSet_mother_tongue = new LinkedHashSet<String>(mothertounge_array_id);

                    StringBuilder sbString = new StringBuilder("");

                    for (String language : listToSet_mother_tongue) {

                        sbString.append(language).append(",");
                    }

                    mother_tongue_pckg_list = sbString.toString();
                    if (mother_tongue_pckg_list.length() > 0)
                        mother_tongue_pckg_list = mother_tongue_pckg_list.substring(0, mother_tongue_pckg_list.length() - 1);


                    System.out.println("strList>>>>>>>>>>>>>>>" + mother_tongue_pckg_list);
                } else {
                    mother_tongue_pckg_list = "";
                }

                /*end mother_tongue list*/





                 /*   updateData(maritalstatus_id, colorcomplex_id, height_str, religion_id, caste_id, subcaste_id, agefrom_list_str, ageTo_list_str, education_id, lifestyle_id,
                            dietray_id, _expectation, sampraday_id);*/
//                    System.out.println("pck_array size 55=============" + pck_array.size());


                System.out.println("marital_status_pckg_list--------------" + marital_status_pckg_list);
                System.out.println("color_complexion_pckg_list--------------" + color_complexion_pckg_list);
                System.out.println("pckg_list--------------" + pckg_list);
                System.out.println("lifestyle_pckg_list--------------" + lifestyle_pckg_list);
                System.out.println("dietary_pckg_list--------------" + dietary_pckg_list);
                System.out.println("education_type_pckg_list--------------" + education_type_pckg_list);
                System.out.println("occupation_pckg_list--------------" + occupation_pckg_list);
                System.out.println("occupation_type_pckg_list--------------" + occupation_type_pckg_list);
                System.out.println("district_pckg_list--------------" + district_pckg_list);
                System.out.println("mother_tongue_pckg_list--------------" + mother_tongue_pckg_list);


                updateData(marital_status_pckg_list, color_complexion_pckg_list, height_str, height_to_str, religion_id, caste_id, subcaste_pckg_list, agefrom_list_str, ageTo_list_str,
                        pckg_list, lifestyle_pckg_list, dietary_pckg_list, _expectation, sampraday_id,
                        education_type_pckg_list, occupation_pckg_list, occupation_type_pckg_list,
                        country_id, state_id, district_pckg_list,mother_tongue_pckg_list, salary_str,country_job_id,state_job_id,district_job_pckg_list);


            }
        });

        fetchPartnerPrefData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

        /*view more card start*/
        step1_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2_view_more.setVisibility(View.VISIBLE);
                step2_cv.setVisibility(View.VISIBLE);
            }/*{
                if (isViewMore) {
                    isViewMore = false;
                    step1_view_more.setText("view more");
                    step1_view_more.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24), null);
                    step1_view_more.setPadding(5, 3, 5, 3);
                    step1_view_more.setBackground(getResources().getDrawable(R.drawable.curved_rectangle_pink_border));
                    step2_view_more.setVisibility(View.GONE);
                    step2_cv.setVisibility(View.GONE);
                    step3_cv.setVisibility(View.GONE);
                } else {
                    isViewMore = true;
                    step1_view_more.setText("view less");
                    step1_view_more.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24), null);
                    step1_view_more.setPadding(5, 3, 5, 3);
                    step1_view_more.setBackground(getResources().getDrawable(R.drawable.curved_rectangle_pink_border));
                    step2_view_more.setVisibility(View.VISIBLE);
                    step2_cv.setVisibility(View.VISIBLE);

                }
            }*/
        });

        step2_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step3_view_more.setVisibility(View.VISIBLE);
                step3_cv.setVisibility(View.VISIBLE);
            }/*{
                if (isViewMore) {
                    isViewMore = false;
                    step2_view_more.setText("view less");
                    step2_view_more.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24), null);
                    step1_view_more.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24), null);
                    step1_view_more.setPadding(5, 3, 5, 3);
                    step1_view_more.setBackground(getResources().getDrawable(R.drawable.curved_rectangle_pink_border));
                    step3_cv.setVisibility(View.VISIBLE);
                } else {
                    isViewMore = true;
                    step1_view_more.setText("view more");
                    step1_view_more.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24), null);
                    step2_view_more.setVisibility(View.VISIBLE);
                    step1_view_more.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24), null);
                    step1_view_more.setPadding(5, 3, 5, 3);
                    step1_view_more.setBackground(getResources().getDrawable(R.drawable.curved_rectangle_pink_border));
                    step3_cv.setVisibility(View.GONE);
                }
            }*/
        });
        /*view more card close*/

        step3_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step4_view_more.setVisibility(View.VISIBLE);
                step4_cv.setVisibility(View.VISIBLE);
            }
        });

        step4_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step5_view_more.setVisibility(View.VISIBLE);
                step5_cv.setVisibility(View.VISIBLE);
            }
        });

        step5_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step6_cv.setVisibility(View.VISIBLE);
            }
        });

//         incomeYearly = fetchPartnerPref.getPref_yearsalary();
        selectedSalaryList.clear();

        if (incomeYearly != null && !incomeYearly.isEmpty()) {
            String[] parts = incomeYearly.split(",");

            for (String p : parts) {
                selectedSalaryList.add(p.trim()); // store clean values
            }
        }


        salary_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                openSalaryDialog();
            }
            return true; // block default spinner dropdown
        });

    }

    private void openSalaryDialog() {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);

        // ----------- PRESELECT FROM API DATA -----------
        selectedSalaryList.clear();

        String incomeYearly = fetchPartnerPref.getPref_yearsalary();
        if (incomeYearly != null && !incomeYearly.isEmpty() && !incomeYearly.equals("0")) {
            String[] arr = incomeYearly.split(",");
            for (String p : arr) {
                selectedSalaryList.add(p.trim());
            }
        }

        // ----------- DOESN'T MATTER CHECKBOX -----------
        CheckBox cbDoesntMatter = new CheckBox(this);
        cbDoesntMatter.setText("Doesn't Matter");
        cbDoesntMatter.setTextSize(16);

        if ("0".equals(incomeYearly)) {
            cbDoesntMatter.setChecked(true);
        }

        container.addView(cbDoesntMatter);

        // ----------- CREATE SALARY CHECKBOXES -----------
        for (SalaryList.SalaryListData item : salaryListData) {
            CheckBox cb = new CheckBox(this);
            cb.setText(item.getName());
            cb.setTextSize(16);

            if (selectedSalaryList.contains(item.getName().trim())) {
                cb.setChecked(true);
            }

            // If salary checked → uncheck Doesn't Matter
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    cbDoesntMatter.setChecked(false);
                }
            });

            container.addView(cb);
        }

        // If Doesn't Matter checked → uncheck all others
        cbDoesntMatter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (int i = 0; i < container.getChildCount(); i++) {
                    View v = container.getChildAt(i);
                    if (v instanceof CheckBox && v != cbDoesntMatter) {
                        ((CheckBox) v).setChecked(false);
                    }
                }
            }
        });

        // ----------- CANCEL -----------
        btn_cancel.setOnClickListener(v -> dialog.dismiss());

        // ----------- OK BUTTON -----------
        btnOk.setOnClickListener(v -> {

            selectedSalaryList.clear();

            if (cbDoesntMatter.isChecked()) {
                salary_str = "Doesn't Matter"; // 🔥 backend value
                ((TextView) salary_spinner.getSelectedView()).setText("Doesn't Matter");
                dialog.dismiss();
                return;
            }

            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;
                    if (cb.isChecked()) {
                        selectedSalaryList.add(cb.getText().toString());
                    }
                }
            }

            salary_str = TextUtils.join(", ", selectedSalaryList);
            ((TextView) salary_spinner.getSelectedView()).setText(salary_str);

            dialog.dismiss();
        });

        dialog.show();
    }


    private Boolean allDone() {


        if (expectation == null) {
            return false;
        }

        View selectedView_marital_status_spinner = marital_status_spinner.getSelectedView();
        View selectedView_colorcomplex_spinner = colorcomplex_spinner.getSelectedView();
        View selectedView_height_spinner = height_spinner.getSelectedView();
        View selectedView_height_spinner_to = height_spinner_to.getSelectedView();
        View selectedView_religion_spinner = religion_spinner.getSelectedView();
        View selectedView_caste_spinner = caste_spinner.getSelectedView();
        View selectedView_sub_caste_spinner = sub_caste_spinner.getSelectedView();
        View selectedView_agefrom_spinner = agefrom_spinner.getSelectedView();
        View selectedView_ageto_spinner = ageto_spinner.getSelectedView();
//        View selectedView_highest_education_spinner = highest_education_spinner.getSelectedView();
        View selectedView_lifestyle_spinner = lifestyle_spinner.getSelectedView();
        View selectedView_dietary_spinner = dietary_spinner.getSelectedView();
        View selectedView_sampraday_spinner = sampraday_spinner.getSelectedView();


        System.out.println("selectedView_marital_status_spinner=========" + selectedView_marital_status_spinner);
        System.out.println("selectedView_colorcomplex_spinner=========" + selectedView_colorcomplex_spinner);
        System.out.println("selectedView_height_spinner=========" + selectedView_height_spinner);
        System.out.println("selectedView_religion_spinner=========" + selectedView_religion_spinner);
        System.out.println("selectedView_caste_spinner=========" + selectedView_caste_spinner);
        System.out.println("selectedView_sub_caste_spinner=========" + selectedView_sub_caste_spinner);
        System.out.println("selectedView_agefrom_spinner=========" + selectedView_agefrom_spinner);
        System.out.println("selectedView_ageto_spinner=========" + selectedView_ageto_spinner);
//        System.out.println("selectedView_highest_education_spinner=========" + selectedView_highest_education_spinner);
        System.out.println("selectedView_lifestyle_spinner=========" + selectedView_lifestyle_spinner);
        System.out.println("selectedView_dietary_spinner=========" + selectedView_dietary_spinner);
        System.out.println("selectedView_sampraday_spinner=========" + selectedView_sampraday_spinner);


        if (selectedView_marital_status_spinner == null) {
            return false;
        }

        if (selectedView_colorcomplex_spinner == null) {
            return false;
        }

        if (selectedView_height_spinner == null) {
            return false;
        }

        if (selectedView_height_spinner_to == null) {
            return false;
        }

        if (selectedView_religion_spinner == null) {
            return false;
        }
        if (selectedView_caste_spinner == null) {
            return false;
        }

      /*  if(selectedView_sub_caste_spinner==null){
            return false;
        }*/

        if (selectedView_agefrom_spinner == null) {
            return false;
        }
        if (selectedView_ageto_spinner == null) {
            return false;
        }
//        if (selectedView_highest_education_spinner == null) {
//            return false;
//        }
        if (selectedView_lifestyle_spinner == null) {
            return false;
        }
        if (selectedView_dietary_spinner == null) {
            return false;
        }
        if (selectedView_sampraday_spinner == null) {
            return false;
        }


        return true;
    }


    @Override
    public void networkAvailable() {

//        fetchPartnerPrefData();


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                education_pck_array.clear();
                education_pckg_flex.removeAllViews();
                education_pck_array_education_name_id.clear();
                highest_education_spinner.setAdapter(null);
                education_type_array.clear();
                education_type_flex.removeAllViews();
                education_type_flexLL.setVisibility(View.GONE);

                occupation_pck_array.clear();
                occupation_pckg_flex.removeAllViews();
                occupation_pck_array_occupation_name_id.clear();
                occupation_spinner.setAdapter(null);
                occupation_type_array.clear();
                occupation_type_flex.removeAllViews();
                occupation_type_flexLL.setVisibility(View.GONE);

                sub_caste_array.clear();
                subcaste_flex.removeAllViews();
                subcaste_flexLL.setVisibility(View.GONE);

                dietary_array.clear();
                dietary_flex.removeAllViews();
                dietary_flexLL.setVisibility(View.GONE);

                lifestyle_array.clear();
                lifestyle_flex.removeAllViews();
                lifestyle_flexLL.setVisibility(View.GONE);

                color_complexion_array.clear();
                color_complexion_flex.removeAllViews();
                color_complexion_flexLL.setVisibility(View.GONE);

                marital_status_array.clear();
                marital_status_flex.removeAllViews();
                marital_status_flexLL.setVisibility(View.GONE);

                district_array.clear();
                district_flex.removeAllViews();
                district_flexLL.setVisibility(View.GONE);

                mothertounge_array_id.clear();

                mothertounge_array.clear();
                mother_tongue_flex.removeAllViews();
                mother_tongue_flexLL.setVisibility(View.GONE);

                fetchPartnerPrefData();
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

                Toast.makeText(PartnerPreferencesActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

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

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }


    public void fetchPartnerPrefData() {
        System.out.println("user_id111=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchPartnerPrefrence(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                swipeRefreshLayout.setRefreshing(false);
                fetchPartnerPref = response.body();

                /*System.out.println("education -------" + new Gson().toJson(fetchPartnerPref));*/

                if (response.isSuccessful()) {
                    incomeYearly =fetchPartnerPref.getPref_yearsalary();
                    maritial_status = fetchPartnerPref.getPref_marital_name();
                    maritial_id = fetchPartnerPref.getPref_marital_id();
                    maritial_st = fetchPartnerPref.getPref_marital_name().toString();
                    prefEduType= fetchPartnerPref.getPref_edu_id();
                    prefOccupation= fetchPartnerPref.getPref_occ_name();
                    getDistrictId= fetchPartnerPref.getPref_dist_id();

                    getPref_education_name=fetchPartnerPref.getPref_edu_name();
                    getPref_education_id=fetchPartnerPref.getPref_edu_id();

                    getMaritalstatus_id = fetchPartnerPref.getPref_marital_id();
                    getColorstatus_id = fetchPartnerPref.getPref_complex_id();
                    getLifestyle_id = fetchPartnerPref.getPref_lifestyle_id();
                    getDietary_id = fetchPartnerPref.getPref_diet_id();
                    getSubCastId = fetchPartnerPref.getPref_subcaste_id();
//                    Toast.makeText(PartnerPreferencesActivity.this, "MgetSubCastId" + getSubCastId, Toast.LENGTH_SHORT).show();

                    // Convert API id string to integer list
                    selectedDietaryIdList.clear();

                    String prefDietaryIds = fetchPartnerPref.getPref_diet_id();  // Example: "1,4,6"

                    if (prefDietaryIds != null && !prefDietaryIds.isEmpty()) {

                        String[] arr = prefDietaryIds.split(",");

                        for (String id : arr) {
                            selectedDietaryIdList.add(id.trim());  // Store as String
                        }
                    }

//                    Toast.makeText(PartnerPreferencesActivity.this, "M,C,L,D" + getMaritalstatus_id + getColorstatus_id + getLifestyle_id + getDietary_id, Toast.LENGTH_SHORT).show();

                    selectedIds = maritial_id.split(",");
                    String lifestyle=fetchPartnerPref.getPref_lifestyle_name();
//                    Toast.makeText(PartnerPreferencesActivity.this, "maritial_status" + maritial_id, Toast.LENGTH_SHORT).show();
                    Log.d("all response ", "onResponse: " + response.body());

                    expectation.setText(fetchPartnerPref.getPref_expect());

                    getMaritalStatusList();
                    getColorcomplexList();
                    getHeightListFrom();
                    getHeightListTo();
                    getReligionList();
                    getEducationTypeList();
                    getOccupationTypeList();
                    getLifeStyleList();
                    getDietaryList();
                    getAgeListFrom();
                    getAgeListTo();
                    getSampradayList();
                    getSalaryList();
                    getCountryList();
                    getCountryJobList();
                    getMotherToungeList();
                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 error fetch family******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    public void updateData(String maritalstatus_id, String colorcomplex_id, String height_str, String height_to_str, String religion_id, String caste_id,
                           String subcaste_id, String agefrom_list_str, String ageTo_list_str, String education_id,
                           String lifestyle_id, String dietray_id, String _expectation, String sampraday_id, String education_type_id, String occupation_id, String occupation_type_id,
                           String country_id, String state_id, String district_pckg_list,String mother_tongue_pckg_list, String salary_str,
                           String pref_ofc_country,String pref_ofc_state, String pref_ofc_dist ) {

        progressBar = ProgressDialog.show(PartnerPreferencesActivity.this, "", "Please Wait...");

        System.out.println("maritalstatus_id=======" + maritalstatus_id);
        System.out.println("colorcomplex_id=======" + colorcomplex_id);
        System.out.println("height_str=======" + height_str);
        System.out.println("height_to_str=======" + height_to_str);
        System.out.println("religion_id=======" + religion_id);
        System.out.println("caste_id=======" + caste_id);
        System.out.println("subcaste_id=======" + subcaste_id);
        System.out.println("agefrom_list_str=======" + agefrom_list_str);
        System.out.println("ageTo_list_str=======" + ageTo_list_str);
        System.out.println("education_id=======" + education_id);
        System.out.println("education_type_id=======" + education_type_id);
        System.out.println("lifestyle_id=======" + lifestyle_id);
        System.out.println("dietray_id=======" + dietray_id);
        System.out.println("_expectation=======" + _expectation);
        System.out.println("sampraday_id=======" + sampraday_id);
        System.out.println("occupation_id=======" + occupation_id);
        System.out.println("occupation_type_id=======" + occupation_type_id);
        System.out.println("country_id=======" + country_id);
        System.out.println("state_id=======" + state_id);
        System.out.println("district_pckg_list=======" + district_pckg_list);
        System.out.println("mother_tongue_pckg_list=======" + mother_tongue_pckg_list);
        System.out.println("salary_str=======" + salary_str);

//        if (subcaste_id == null || subcaste_id.isEmpty()) {
//            subcaste_id = getSubCastId;
//        }

//        if (district_pckg_list == null || district_pckg_list.isEmpty()) {
//            district_pckg_list = getDistrictId;
//        }


//        Toast.makeText(this, "sub cast id " +subcaste_id, Toast.LENGTH_SHORT).show();
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editPartnerPrefrence(String.valueOf(user.getUser_id()),
                maritalstatus_id, colorcomplex_id, height_str, height_to_str,
                religion_id, caste_id, subcaste_id, agefrom_list_str, ageTo_list_str, education_id, lifestyle_id,
                dietray_id, _expectation, sampraday_id, education_type_id, occupation_id, occupation_type_id,
                salary_str, country_id, state_id, district_pckg_list,mother_tongue_pckg_list,country_job_id,state_job_id, districtId);

        Log.d("district_job_pckg_list", "district_job_pckg_list: " + country_job_id +state_job_id+district_job_pckg_list);

        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();

                progressBar.dismiss();
                if (response.isSuccessful()) {

                    String success = edit_response.getResid();

//                    System.out.println("part pref res=============" + new Gson().toJson(edit_response));

                    if (success.equals("200")) {
                        Toast.makeText(PartnerPreferencesActivity.this, edit_response.getResMsg(), Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(PartnerPreferencesActivity.this, MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(PartnerPreferencesActivity.this, edit_response.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err partner detail******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {

                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(PartnerPreferencesActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    public void getSampradayList() {

        sampradayListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<SampradayList> userResponse = apiService.sampradayList();
        userResponse.enqueue(new Callback<SampradayList>() {

            @Override
            public void onResponse(Call<SampradayList> call, Response<SampradayList> response) {
                sampradayList = response.body();

                if (response.isSuccessful()) {


                    String success = sampradayList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < sampradayList.getSampradayList().size(); i++) {
                            sampradayListData.add(new SampradayList.SampradayListData(sampradayList.getSampradayList().get(i).getId(), sampradayList.getSampradayList().get(i).getName()));

                        }
                        sampraday_spinner_data(sampradayListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<SampradayList> call, Throwable t) {

                System.out.println("err sampraday******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void sampraday_spinner_data(List<SampradayList.SampradayListData> sampradayListData) {

        sampraday_list.clear();

        sampraday_list.add("Select Sampraday");
        sampraday_list.add("Doesn't Matter");

        if (sampraday_list.size() != 0) {
            for (int i = 0; i < sampradayListData.size(); i++) {

                sampraday_list.add(i + 2, sampradayListData.get(i).getName());

            }
        }
        adapter_sampraday = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, sampraday_list);

        sampraday_spinner.setAdapter(adapter_sampraday);

        int pos;
        for (int i = 0; i < sampraday_list.size(); i++) {
            if (sampraday_list.get(i).contains(fetchPartnerPref.getPref_sampraday_name())) {
                pos = i;
                sampraday_spinner.setSelection(pos);
            }
        }
        sampraday_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                sampraday_list_str = sampraday_spinner.getItemAtPosition(position).toString();
                System.out.println("sampraday_list_str------" + sampraday_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSalaryList() {
        salaryListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<SalaryList> userResponse = apiService.salaryList();
        userResponse.enqueue(new Callback<SalaryList>() {

            @Override
            public void onResponse(Call<SalaryList> call, Response<SalaryList> response) {
                salaryList = response.body();

                if (response.isSuccessful()) {

                    System.out.println("list_salary-----------================");

                    String success = salaryList.getResid();

                    if (success.equals("200")) {
                        for (int i = 0; i < salaryList.getSalaryList().size(); i++) {
                            salaryListData.add(new SalaryList.SalaryListData(salaryList.getSalaryList().get(i).getName()));
                        }
                        salary_spinner_data(salaryListData);
                    } else {

                    }
                } else {
                    System.out.println("err salaryList******" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SalaryList> call, Throwable t) {

                System.out.println("err rel******" + t.toString());
                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void salary_spinner_data(ArrayList<SalaryList.SalaryListData> salaryListData) {

        salary_list.clear();
        System.out.println("list_salary-----------================");

        salary_list.add("Select Income");
        salary_list.add("Doesn't Matter");

        if (salary_list.size() != 0) {
            for (int i = 0; i < salaryListData.size(); i++) {

                salary_list.add(i + 2, salaryListData.get(i).getName());

            }
        }


        salary_adapter = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, salary_list);

        salary_spinner.setAdapter(salary_adapter);

        int pos;
        for (int i = 0; i < salary_list.size(); i++) {
            if (salary_list.get(i).contains(fetchPartnerPref.getPref_yearsalary())) {
                pos = i;
                salary_spinner.setSelection(pos);
            }
        }
//        salary_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                salary_str = salary_spinner.getItemAtPosition(position).toString();
//                System.out.println("salary_str------" + salary_str);
////                for_state_spinner_data(religion_list_str);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    public void getAgeListFrom() {

        ageListDataFrom.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<AgeList> userResponse = apiService.ageList();
        userResponse.enqueue(new Callback<AgeList>() {

            @Override
            public void onResponse(Call<AgeList> call, Response<AgeList> response) {
                ageListFrom = response.body();

                if (response.isSuccessful()) {


                    String success = ageListFrom.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < ageListFrom.getAgeList().size(); i++) {
                            ageListDataFrom.add(new AgeList.AgeListData(ageListFrom.getAgeList().get(i).getName()));

                        }
                        ageFrom_spinner_data(ageListDataFrom);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<AgeList> call, Throwable t) {

                System.out.println("err age******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void ageFrom_spinner_data(List<AgeList.AgeListData> ageListDataFrom) {

        ageFrom_list.clear();

        ageFrom_list.add("Select Age");
        ageFrom_list.add("Doesn't Matter");

        if (ageFrom_list.size() != 0) {
            for (int i = 0; i < ageListDataFrom.size(); i++) {

                ageFrom_list.add(i + 2, ageListDataFrom.get(i).getName());

            }
        }
        adapter_ageFrom = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, ageFrom_list);

        agefrom_spinner.setAdapter(adapter_ageFrom);

        int pos;
        for (int i = 0; i < ageFrom_list.size(); i++) {
            if (ageFrom_list.get(i).contains(fetchPartnerPref.getPref_agefrom())) {
                pos = i;
                agefrom_spinner.setSelection(pos);
            }
        }

        agefrom_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                agefrom_list_str = agefrom_spinner.getItemAtPosition(position).toString();
                System.out.println("agefrom_list_str------" + agefrom_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getAgeListTo() {

        ageListDataTo.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<AgeList> userResponse = apiService.ageList();
        userResponse.enqueue(new Callback<AgeList>() {

            @Override
            public void onResponse(Call<AgeList> call, Response<AgeList> response) {
                ageListTo = response.body();

                if (response.isSuccessful()) {


                    String success = ageListTo.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < ageListTo.getAgeList().size(); i++) {
                            ageListDataTo.add(new AgeList.AgeListData(ageListTo.getAgeList().get(i).getName()));

                        }
                        ageTo_spinner_data(ageListDataTo);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<AgeList> call, Throwable t) {

                System.out.println("err age******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void ageTo_spinner_data(List<AgeList.AgeListData> ageListDataTo) {

        ageTo_list.clear();

        ageTo_list.add("Select Age");
        ageTo_list.add("Doesn't Matter");

        if (ageTo_list.size() != 0) {
            for (int i = 0; i < ageListDataTo.size(); i++) {

                ageTo_list.add(i + 2, ageListDataTo.get(i).getName());

            }
        }
        adapter_ageTo = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, ageTo_list);

        ageto_spinner.setAdapter(adapter_ageTo);

        int pos;
        for (int i = 0; i < ageTo_list.size(); i++) {
            if (ageTo_list.get(i).contains(fetchPartnerPref.getPref_ageto())) {
                pos = i;
                ageto_spinner.setSelection(pos);
            }
        }


        ageto_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                ageTo_list_str = ageto_spinner.getItemAtPosition(position).toString();
                System.out.println("ageTo_list_str------" + ageTo_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getDietaryList() {

        dietaryLists.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<DietaryList> userResponse = apiService.dietary("14");
        userResponse.enqueue(new Callback<DietaryList>() {

            @Override
            public void onResponse(Call<DietaryList> call, Response<DietaryList> response) {
                dietaryList = response.body();

                if (response.isSuccessful()) {


                    String success = dietaryList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < dietaryList.getDietaryList().size(); i++) {
                            dietaryLists.add(new DietaryList.DietaryListData(dietaryList.getDietaryList().get(i).getId(), dietaryList.getDietaryList().get(i).getName()));

                        }
                        dietary_spinner_data(dietaryLists);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<DietaryList> call, Throwable t) {

                System.out.println("err dietary******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

/*
    public void dietary_spinner_data(List<DietaryList.DietaryListData> dietaryListData) {

        dietray_list.clear();

        dietray_list.add("Select Dietary");
        dietray_list.add("Doesn't Matter");

        if (dietray_list.size() != 0) {
            for (int i = 0; i < dietaryListData.size(); i++) {

                dietray_list.add(i + 2, dietaryListData.get(i).getName());

            }
        }
        adapter_dietray = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, dietray_list);

        dietary_spinner.setAdapter(adapter_dietray);
        int pos;
        for (int i = 0; i < dietray_list.size(); i++) {
            if (dietray_list.get(i).contains(fetchPartnerPref.getPref_diet_name())) {
                pos = i;
                dietary_spinner.setSelection(pos);
            }
        }


        dietary_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                dietray_str = dietary_spinner.getItemAtPosition(position).toString();
                System.out.println("dietray_str------" + dietray_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
*/


    public void getLifeStyleList() {

        lifeSettingLists.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<LifeSettingList> userResponse = apiService.lifeSetting("15");
        userResponse.enqueue(new Callback<LifeSettingList>() {

            @Override
            public void onResponse(Call<LifeSettingList> call, Response<LifeSettingList> response) {
                lifeSettingList = response.body();

                if (response.isSuccessful()) {


                    String success = lifeSettingList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < lifeSettingList.getLifeSetting().size(); i++) {
                            lifeSettingLists.add(new LifeSettingList.LifeSettingListData(lifeSettingList.getLifeSetting().get(i).getId(), lifeSettingList.getLifeSetting().get(i).getName()));

                        }
                        lifestyle_spinner_data(lifeSettingLists);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<LifeSettingList> call, Throwable t) {

                System.out.println("err lifestyle******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void lifestyle_spinner_data(List<LifeSettingList.LifeSettingListData> lifeSettingListData) {

        lifestyle_list.clear();

        lifestyle_list.add("Select LifeStyle");
        lifestyle_list.add("Doesn't Matter");

        if (lifestyle_list.size() != 0) {
            for (int i = 0; i < lifeSettingListData.size(); i++) {

                lifestyle_list.add(i + 2, lifeSettingListData.get(i).getName());

            }
        }
        lifestyle_adapter = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, lifestyle_list);

        lifestyle_spinner.setAdapter(lifestyle_adapter);

        int pos;
/*
        for (int i = 0; i < lifestyle_list.size(); i++) {
            if (lifestyle_list.get(i).contains(fetchPartnerPref.getPref_lifestyle_name())) {
                pos = i;
                lifestyle_spinner.setSelection(pos);
            }
        }
*/
        lifestyle_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openLifeStyleDialog();
            }
            return true;
        });


//        lifestyle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                lifestyle_str = lifestyle_spinner.getItemAtPosition(position).toString();
//                System.out.println("lifestyle_str------" + lifestyle_str);
//
//                if (!lifestyle_str.equals("Select LifeStyle")) {
//                    if (lifestyle_flexLL.getVisibility() == View.GONE) {
//                        lifestyle_flexLL.setVisibility(View.VISIBLE);
//                    }
//
//                    System.out.println("00.0.1.===========" + "11111111111111111");
//
//                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(10, 5, 10, 5);
//
//
//                    lifestyle_txtv = new TextView(PartnerPreferencesActivity.this);
//                    lifestyle_txtv.setLayoutParams(params);
//                    lifestyle_txtv.setTextSize(16);
//                    lifestyle_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
//
//                    lifestyle_txtv.setPadding(20, 10, 20, 10);
//                    lifestyle_txtv.setLayoutParams(params);
//                    if (!lifestyle_str.equals(lifestyle_txtv.getText().toString())) {
//
//
//                        lifestyle_txtv.setText(lifestyle_str.trim() + "     x ");
//
//                        lifestyle_array.add(lifestyle_str.trim());
//
//
//                    }
//
//
//                    lifestyle_flex.addView(lifestyle_txtv);
//
//
//                    final String myString = lifestyle_txtv.getText().toString();
//                    int i1 = myString.indexOf("x ");
//                    lifestyle_txtv.setMovementMethod(LinkMovementMethod.getInstance());
//                    lifestyle_txtv.setText(myString, TextView.BufferType.SPANNABLE);
//                    Spannable mySpannable = (Spannable) lifestyle_txtv.getText();
//
//                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
//
//                    ClickableSpan myClickableSpan = new ClickableSpan() {
//                        @Override
//                        public void onClick(View textView) {
//
//                            TextView textView1 = (TextView) textView;
//
//                            String charSequence = textView1.getText().toString().trim();
//
//                            String a = charSequence.substring(0, charSequence.length() - 2);
//
//                            System.out.println("character name----------" + a);
//                            System.out.println("final character name----------" + a.trim());
//
//
//                            lifestyle_array.remove(charSequence);
//
//                            for (int i = 0; i < lifestyle_array.size(); i++) {
//                                System.out.println("array character name----------" + lifestyle_array.get(i));
//
//                                if ((lifestyle_array.get(i).trim()).contains(a.trim())) {
//
//                                    int id = lifestyle_array.indexOf(a.trim());
//                                    System.out.println(" 11 array character name----------" + id);
//
//                                    lifestyle_array.remove(id);
//                                    textView1.setVisibility(View.GONE);
//                                }
//
//                            }
//
//                        }
//                    };
//
//                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void openLifeStyleDialog() {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("Select LifeStyle");

        selectedLifestyleList.clear();

        // 🔥 DO NOT CLEAR lifestyle_array HERE

        // ----------------------------------
        // PRESELECT API VALUES
        // ----------------------------------
        String prefLifeStyle = fetchPartnerPref.getPref_lifestyle_name();

        if (prefLifeStyle != null && !prefLifeStyle.isEmpty()) {

            // Fill selected list ONLY IF array is empty (first time)
            if (lifestyle_array.isEmpty()) {
                String[] arr = prefLifeStyle.split(",");

                for (String s : arr) {
                    lifestyle_array.add(s.trim());
                }
            }

//            lifestyle_spinner_text.setText(prefLifeStyle);

            selectedLifestyleList.addAll(lifestyle_array);
        }

        // ----------------------------------
        // MAKE CHECKBOXES
        // ----------------------------------
        for (LifeSettingList.LifeSettingListData item : lifeSettingLists) {
            CheckBox cb = new CheckBox(this);
            cb.setText(item.getName());
            cb.setTextSize(16);

            if (selectedLifestyleList.contains(item.getName().trim())) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // ----------------------------------
        // OK CLICK – update values
        // ----------------------------------
        btnOk.setOnClickListener(v -> {

            selectedLifestyleList.clear();
            lifestyle_array.clear();
            lifestyle_flex.removeAllViews();

            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);

                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;

                    if (cb.isChecked()) {
                        selectedLifestyleList.add(cb.getText().toString());
                        lifestyle_array.add(cb.getText().toString());
                    }
                }
            }

            lifestyle_str = TextUtils.join(", ", selectedLifestyleList);

            ((TextView) lifestyle_spinner.getSelectedView()).setText(lifestyle_str);

            addLifestyleTags();

            dialog.dismiss();
        });

        dialog.show();
    }

    private void addLifestyleTags() {
        lifestyle_flexLL.setVisibility(View.VISIBLE);
        lifestyle_flex.removeAllViews();

        for (String item : lifestyle_array) {

            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 5, 10, 5);

            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(item + "  x");
            tv.setTextSize(16);
            tv.setPadding(20, 10, 20, 10);
            tv.setBackgroundColor(Color.parseColor("#110A0A0A"));

            // Add click to remove
            tv.setOnClickListener(v -> {
                lifestyle_array.remove(item);
                addLifestyleTags(); // Refresh
            });

            lifestyle_flex.addView(tv);
        }
    }



    public void getReligionList() {

        religionListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<ReligionList> userResponse = apiService.religionList("05");
        userResponse.enqueue(new Callback<ReligionList>() {

            @Override
            public void onResponse(Call<ReligionList> call, Response<ReligionList> response) {
                religionList = response.body();

                if (response.isSuccessful()) {


                    String success = religionList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < religionList.getReligionList().size(); i++) {
                            religionListData.add(new ReligionList.ReligionListData(religionList.getReligionList().get(i).getId(), religionList.getReligionList().get(i).getName()));

                        }
                        religion_spinner_data(religionListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ReligionList> call, Throwable t) {

                System.out.println("err rel******" + t.toString());
                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void religion_spinner_data(List<ReligionList.ReligionListData> religionListData) {

        religion_list.clear();

        religion_list.add("Select Religion");
        religion_list.add("Doesn't Matter");

        if (religion_list.size() != 0) {
            for (int i = 0; i < religionListData.size(); i++) {

                religion_list.add(i + 2, religionListData.get(i).getName());

            }
        }


        adapter_religion = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, religion_list);

        religion_spinner.setAdapter(adapter_religion);

        int pos;
        for (int i = 0; i < religion_list.size(); i++) {
            if (religion_list.get(i).contains(fetchPartnerPref.getPref_religion_name())) {
                pos = i;
                religion_spinner.setSelection(pos);
            }
        }


        religion_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                religion_list_str = religion_spinner.getItemAtPosition(position).toString();
                System.out.println("religion_list_str------" + religion_list_str);

                getCastList(religion_list_str);

//                for_state_spinner_data(religion_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getCastList(String religion_list_str) {


        if (religion_list_str != null) {
            for (int i = 0; i < religionListData.size(); i++) {
                if (religion_list_str.equals(religionListData.get(i).getName())) {
                    religion_id = String.valueOf(religionListData.get(i).getId());

                }
            }
        }

        System.out.println("religion_id--------" + religion_id);

        casteListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<CasteList> userResponse = apiService.casteList("06", religion_id);
        userResponse.enqueue(new Callback<CasteList>() {

            @Override
            public void onResponse(Call<CasteList> call, Response<CasteList> response) {
                casteList = response.body();

                if (response.isSuccessful()) {


                    String success = casteList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < casteList.getCasteList().size(); i++) {
                            casteListData.add(new CasteList.CasteListData(casteList.getCasteList().get(i).getId(), casteList.getCasteList().get(i).getName(), casteList.getCasteList().get(i).getSubCasteList()));
                        }
                        caste_spinner_data(casteListData);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<CasteList> call, Throwable t) {

                System.out.println("err caste******" + t.toString());
                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private List<String> getPrefCasteList() {
        List<String> list = new ArrayList<>();

        String prefCaste = fetchPartnerPref.getPref_caste_name();
        if (prefCaste != null && !prefCaste.trim().isEmpty()) {
            String[] arr = prefCaste.split(",");
            for (String s : arr) {
                list.add(s.trim());
            }
        }
        return list;
    }


    //for search cast tanmay 27-5-2025
    public void caste_spinner_data(List<CasteList.CasteListData> casteListData) {
        caste_list.clear();
        caste_list.add("Select Caste");
        caste_list.add("Doesn't Matter");

        // Clear sub-caste related data
        subCasteLists.clear();
        sub_caste_list.clear();
        subcaste_flex.removeAllViews();

        subCasteLists = new ArrayList<>();
        sub_caste_list = new ArrayList<>();

        sub_caste_list.add("Select Sub-Caste");
        sub_caste_list.add("Doesn't Matter");

        sub_caste_spinner.setAdapter(null);

        if (casteListData != null && !casteListData.isEmpty()) {
            for (int i = 0; i < casteListData.size(); i++) {
                caste_list.add(i + 2, casteListData.get(i).getName());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        caste_adapter = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, caste_list);
        caste_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caste_spinner.setAdapter(caste_adapter);

        // Set default selection if pref_caste_name exists
        if (fetchPartnerPref.getPref_caste_name() != null && !fetchPartnerPref.getPref_caste_name().isEmpty()) {
            for (int i = 0; i < caste_list.size(); i++) {
                if (caste_list.get(i).equals(fetchPartnerPref.getPref_caste_name())) {
                    caste_spinner.setSelection(i);
                    break;
                }
            }
        }

        List<String> prefCasteList = getPrefCasteList();

        if (!prefCasteList.isEmpty()) {
            String displayText = TextUtils.join(", ", prefCasteList);

            caste_spinner.post(() -> {
                View view = caste_spinner.getSelectedView();
                if (view instanceof TextView) {
                    ((TextView) view).setText(displayText);
                }
            });
        }


        // Handle Spinner click to show searchable dialog
//        caste_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableCasteDialog(caste_list);
//            }
//            return true; // Consume the touch event
//        });

        caste_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openMultiCasteDialog();
            }
            return true;
        });

    }

    private void openMultiCasteDialog() {
        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("Select Cast");

        selectedCasteList.clear();
        selectedCasteIdList.clear();

        // PRE-SELECT based on API stored value
        String prefCaste = fetchPartnerPref.getPref_caste_name(); // e.g. "Kunbi, Maratha"

        if (prefCaste != null && !prefCaste.isEmpty()) {
            if (caste_array.isEmpty()) {
                String[] arr = prefCaste.split(",");
                for (String s : arr) caste_array.add(s.trim());
            }
            selectedCasteList.addAll(caste_array);
        }

        // CREATE CHECKBOXES
//        for (CasteList.CasteListData caste : casteListData) {
//
//            if (caste.getName().equals("Select Caste") || caste.getName().equals("Doesn't Matter"))
//                continue;
//
//            CheckBox cb = new CheckBox(this);
//            cb.setText(caste.getName());
//            cb.setTextSize(16);
//
//            if (selectedCasteList.contains(caste.getName()))
//                cb.setChecked(true);
//
//            cb.setTag(caste.getId()); // IMPORTANT → store ID here
//
//            container.addView(cb);
//        }
        // 🔥 Add "Doesn't Matter" manually if API does NOT send it
        boolean dmFound = false;

        for (CasteList.CasteListData c : casteListData) {
            if (c.getName().equalsIgnoreCase("Doesn't Matter")) {
                dmFound = true;
                break;
            }
        }

        if (!dmFound) {
            // Add Doesn't Matter using SAME constructor (no need to change model)
            casteListData.add(0, new CasteList.CasteListData(
                    "0",
                    "Doesn't Matter",
                    new ArrayList<>()   // empty subcaste list
            ));
        }

        for (CasteList.CasteListData caste : casteListData) {

            CheckBox cb = new CheckBox(this);
            cb.setText(caste.getName());
            cb.setTextSize(16);
            cb.setTag(caste.getId());

            // ✔ PRESELECT enabled here
            if (selectedCasteList.contains(caste.getName())) {
                cb.setChecked(true);
            }

            // "Doesn't Matter" special handling
            if (caste.getName().equals("Doesn't Matter")) {
                cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        for (int i = 0; i < container.getChildCount(); i++) {
                            View child = container.getChildAt(i);
                            if (child instanceof CheckBox && child != cb) {
                                ((CheckBox) child).setChecked(false);
                            }
                        }
                    }
                });
            } else {
                cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        for (int i = 0; i < container.getChildCount(); i++) {
                            View child = container.getChildAt(i);
                            if (child instanceof CheckBox) {
                                CheckBox cb2 = (CheckBox) child;
                                if (cb2.getText().toString().equals("Doesn't Matter")) {
                                    cb2.setChecked(false);
                                }
                            }
                        }
                    }
                });
            }

            container.addView(cb);
        }


        btnCancel.setOnClickListener(v -> dialog.dismiss());

//        btnOk.setOnClickListener(v -> {
//
//            selectedCasteList.clear();
//            selectedCasteIdList.clear();
//
//            for (int i = 0; i < container.getChildCount(); i++) {
//                View child = container.getChildAt(i);
//
//                if (child instanceof CheckBox) {
//                    CheckBox cb = (CheckBox) child;
//
//                    if (cb.isChecked()) {
//
//                        selectedCasteList.add(cb.getText().toString());   // store NAME
//                        selectedCasteIdList.add(cb.getTag().toString()); // store ID
//                    }
//                }
//            }
//
//            // Convert to comma-separated string
//            caste_list_str = TextUtils.join(", ", selectedCasteList); // names
//            caste_id = TextUtils.join(",", selectedCasteIdList);  // IDs like "1,4,9"
//
////            Toast.makeText(this, "cast" + caste_id, Toast.LENGTH_SHORT).show();
//
//            Log.d("CASTE", "Selected names = " + caste_list_str);
//            Log.d("CASTE", "Selected IDs   = " + caste_id);
//
//            ((TextView) caste_spinner.getSelectedView()).setText(caste_list_str);
//
//
//            updateSubCasteForMultipleCastes(selectedCasteList);
//
//            dialog.dismiss();
//        });


        btnOk.setOnClickListener(v -> {

            selectedCasteList.clear();
            selectedCasteIdList.clear();

            boolean isDoesntMatterSelected = false;

            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;

                    if (cb.isChecked()) {
                        if (cb.getText().toString().equals("Doesn't Matter")) {
                            isDoesntMatterSelected = true;
                        }
                        selectedCasteList.add(cb.getText().toString());
                        selectedCasteIdList.add(cb.getTag().toString());
                    }
                }
            }

            if (isDoesntMatterSelected) {
                // ONLY send Doesn't Matter
                caste_list_str = "Doesn't Matter";
                caste_id = "0";
            } else {
                caste_list_str = TextUtils.join(", ", selectedCasteList);
                caste_id = TextUtils.join(",", selectedCasteIdList);
            }

            ((TextView) caste_spinner.getSelectedView()).setText(caste_list_str);
            updateSubCasteForMultipleCastes(selectedCasteList);

            dialog.dismiss();
        });



        dialog.show();
    }

    public void updateSubCasteForMultipleCastes(List<String> selectedCastes) {
        // ensure subCasteLists is a List<SubCasteList> declared at class level
        subCasteLists.clear();

        // If nothing selected -> clear UI
        if (selectedCastes == null || selectedCastes.isEmpty()) {
            subCaste_spinner_data(new ArrayList<SubCasteList>());
            return;
        }

        // Use a Set to avoid duplicate sub-caste names (use sc_id if you prefer uniqueness by id)
        Set<String> addedNames = new HashSet<>();

        // casteListData is expected to be List<CasteList.CasteListData> (your parsed API data)
        for (String casteName : selectedCastes) {
            for (CasteList.CasteListData caste : casteListData) {

                // match by name (case-sensitive). Use equalsIgnoreCase(...) if you want case-insensitive match.
                if (casteName.equals(caste.getName())) {

                    ArrayList<SubCasteList> subs = caste.getSubCasteList();
                    if (subs == null) continue;

                    for (SubCasteList sub : subs) {
                        String scName = sub.getSc_name(); // getter from your SubCasteList class
                        if (scName == null) scName = "";

                        if (!addedNames.contains(scName)) {
                            // create new SubCasteList object using your constructor
                            SubCasteList item = new SubCasteList(sub.getC_id(), sub.getSc_id(), sub.getSc_name());

                            subCasteLists.add(item);
                            addedNames.add(scName);
                        }
                    }
                }
            }
        }

        // update UI
        subCaste_spinner_data(subCasteLists);
    }



    // Method to show a searchable dialog for caste selection
    private void showSearchableCasteDialog(List<String> casteList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalCasteList = new ArrayList<>(casteList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                casteList
        );
        listView.setAdapter(dialogAdapter);

        // Filter list based on search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                // Get the selected item from the filtered list
                String selectedCaste = dialogAdapter.getItem(position);
                if (selectedCaste == null) {
                    Log.e("CasteDialog", "Selected caste is null at position: " + position);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list to get the correct position
                int originalPosition = -1;
                for (int i = 0; i < originalCasteList.size(); i++) {
                    if (originalCasteList.get(i).equals(selectedCaste)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("CasteDialog", "Could not find selected caste in original list: " + selectedCaste);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                caste_list_str = selectedCaste;
                Log.d("CasteDialog", "Selected caste: " + caste_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                caste_spinner.setSelection(originalPosition);

                // Call existing method to handle sub-caste updates
                System.out.println("caste_list_str------" + caste_list_str);
                for_caste_spinner_data(caste_list_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("CasteDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting caste: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
    // commented by tanmay 27-5-2025
//    public void caste_spinner_data(List<CasteList.CasteListData> casteListData) {
//
//        caste_list.clear();
//
//        caste_list.add("Select Caste");
//        caste_list.add("Doesn't Matter");
//
//
//        subCasteLists.clear();
//        sub_caste_list.clear();
//        subcaste_flex.removeAllViews();
//
//        subCasteLists = new ArrayList<>();
//        sub_caste_list = new ArrayList<>();
//
//        sub_caste_list.add("Select Sub-Caste");
//        sub_caste_list.add("Doesn't Matter");
//
//        sub_caste_spinner.setAdapter(null);
//
//        if (caste_list.size() != 0) {
//            for (int i = 0; i < casteListData.size(); i++) {
//
//                caste_list.add(i + 2, casteListData.get(i).getName());
//
//            }
//        }
//
//
//        caste_adapter = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, caste_list);
//
//        caste_spinner.setAdapter(caste_adapter);
//
//        int pos;
//        for (int i = 0; i < caste_list.size(); i++) {
//            if (caste_list.get(i).contains(fetchPartnerPref.getPref_caste_name())) {
//                pos = i;
//                caste_spinner.setSelection(pos);
//            }
//        }
//
//
//        caste_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                caste_list_str = caste_spinner.getItemAtPosition(position).toString();
//                System.out.println("caste_list_str------" + caste_list_str);
//
//                for_caste_spinner_data(caste_list_str);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    public void for_caste_spinner_data(String caste_list_str) {
        subCasteLists.clear();
        /*---------*/

        if (caste_list_str != null) {
            for (int i = 0; i < casteListData.size(); i++) {
                if (caste_list_str.equals(casteListData.get(i).getName())) {
                    caste_id = String.valueOf(casteListData.get(i).getId());
                }
            }
        }

        for (int i = 0; i < casteListData.size(); i++) {
            if (casteListData.get(i).getSubCasteList() != null) {

                for (int j = 0; j < casteListData.get(i).getSubCasteList().size(); j++) {
                    if (caste_id != null) {

                        if (caste_id.contentEquals(casteListData.get(i).getSubCasteList().get(j).getC_id())) {
                            subCasteLists.add(new SubCasteList(casteListData.get(i).getSubCasteList().get(j).getC_id(), casteListData.get(i).getSubCasteList().get(j).getSc_id(), casteListData.get(i).getSubCasteList().get(j).getSc_name()));
                        }
                    }
                }
                subCaste_spinner_data(subCasteLists);
            }
        }
    }

/*
    public void subCaste_spinner_data(List<SubCasteList> subCasteLists) {

        sub_caste_list.clear();

        sub_caste_list.add("Select Sub-Caste");
        sub_caste_list.add("Doesn't Matter");

        if (sub_caste_list.size() != 0) {
            for (int i = 0; i < subCasteLists.size(); i++) {

                sub_caste_list.add(i + 2, subCasteLists.get(i).getSc_name());

            }
        }


        sub_caste_adapter = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, sub_caste_list);

        sub_caste_spinner.setAdapter(sub_caste_adapter);

        int pos;
        for (int i = 0; i < sub_caste_list.size(); i++) {
            if (sub_caste_list.get(i).contains(fetchPartnerPref.getPref_subcaste_name())) {
                pos = i;
                sub_caste_spinner.setSelection(pos);
            }
        }

        sub_caste_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                sub_caste_str = sub_caste_spinner.getItemAtPosition(position).toString();

                System.out.println("sub_caste_str------" + sub_caste_str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
*/


    public void getHeightListFrom() {

        heightListDataFrom.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<HeightList> userResponse = apiService.prefheightListFrom();
        userResponse.enqueue(new Callback<HeightList>() {

            @Override
            public void onResponse(Call<HeightList> call, Response<HeightList> response) {
                heightList = response.body();

                if (response.isSuccessful()) {


                    String success = heightList.getResid();

                    if (success.equals("200")) {
                        heightListDataFrom.clear();


                        for (int i = 0; i < heightList.getHeightList().size(); i++) {
                            heightListDataFrom.add(new HeightList.HeightListData(heightList.getHeightList().get(i).getName()));

                        }
                        height_spinner_data_from(heightListDataFrom);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<HeightList> call, Throwable t) {

                System.out.println("err height******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void getHeightListTo() {

        heightListDataTo.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<HeightList> userResponse = apiService.prefheightListTo();
        userResponse.enqueue(new Callback<HeightList>() {

            @Override
            public void onResponse(Call<HeightList> call, Response<HeightList> response) {
                HeightList heightList = response.body();

                if (response.isSuccessful()) {


                    String success = heightList.getResid();

                    if (success.equals("200")) {
                        heightListDataTo.clear();

                        for (int i = 0; i < heightList.getHeightList().size(); i++) {
                            heightListDataTo.add(new HeightList.HeightListData(heightList.getHeightList().get(i).getName()));

                        }
                        height_spinner_data_to(heightListDataTo);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<HeightList> call, Throwable t) {

                System.out.println("err height******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void height_spinner_data_from(List<HeightList.HeightListData> heightListData) {

        height_list_from.clear();

        height_list_from.add("Select Height");
        height_list_from.add("Doesn't Matter");

        if (height_list_from.size() != 0) {
            for (int i = 0; i < heightListData.size(); i++) {

                height_list_from.add(i + 2, heightListData.get(i).getName());

            }
        }
        height_adapter = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, height_list_from);

        height_spinner.setAdapter(height_adapter);

        int pos;
        for (int i = 0; i < height_list_from.size(); i++) {
            if (height_list_from.get(i).contains(fetchPartnerPref.getPref_height_from())) {
                pos = i;
                height_spinner.setSelection(pos);
            }
        }

        height_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                height_str = height_spinner.getItemAtPosition(position).toString();
                System.out.println("height_str------" + height_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void height_spinner_data_to(List<HeightList.HeightListData> heightListData) {

        height_list_to.clear();

        height_list_to.add("Select Height");
        height_list_to.add("Doesn't Matter");

        if (height_list_to.size() != 0) {
            for (int i = 0; i < heightListData.size(); i++) {

                height_list_to.add(i + 2, heightListData.get(i).getName());

            }
        }
        height_adapter = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, height_list_to);

        height_spinner_to.setAdapter(height_adapter);

        int pos;
        for (int i = 0; i < height_list_to.size(); i++) {
            if (height_list_to.get(i).contains(fetchPartnerPref.getPref_height_to())) {
                pos = i;
                height_spinner_to.setSelection(pos);
            }
        }

        height_spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                height_to_str = height_spinner_to.getItemAtPosition(position).toString();
                System.out.println("height_spinner_to------" + height_to_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getColorcomplexList() {

        colorcomplexLists.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<ColorcomplexList> userResponse = apiService.colorcomplexList("11");
        userResponse.enqueue(new Callback<ColorcomplexList>() {

            @Override
            public void onResponse(Call<ColorcomplexList> call, Response<ColorcomplexList> response) {
                colorcomplexList = response.body();

                if (response.isSuccessful()) {


                    String success = colorcomplexList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < colorcomplexList.getColorcomplexList().size(); i++) {
                            colorcomplexLists.add(new ColorcomplexList.ColorcomplexListData(colorcomplexList.getColorcomplexList().get(i).getId(), colorcomplexList.getColorcomplexList().get(i).getName()));
                        }
                        colorcomplex_spinner_data(colorcomplexLists);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ColorcomplexList> call, Throwable t) {

                System.out.println("err colorcomplex******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void colorcomplex_spinner_data(List<ColorcomplexList.ColorcomplexListData> colorcomplexListData) {

        colorcomplex_list.clear();

        colorcomplex_list.add("Select Color-Complex");
        colorcomplex_list.add("Doesn't Matter");

        if (colorcomplex_list.size() != 0) {
            for (int i = 0; i < colorcomplexListData.size(); i++) {

                colorcomplex_list.add(i + 2, colorcomplexListData.get(i).getName());

            }
        }
        colorcomplex_adapter = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, colorcomplex_list);

        colorcomplex_spinner.setAdapter(colorcomplex_adapter);

        int pos;
/*
        for (int i = 0; i < colorcomplex_list.size(); i++) {
            if (colorcomplex_list.get(i).equals(fetchPartnerPref.getPref_complex_name())) {
                pos = i;
                colorcomplex_spinner.setSelection(pos);
            }
        }
*/
        colorcomplex_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openColorComplexDialog();
            }
            return true;
        });

//        colorcomplex_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                colorcomplex_str = colorcomplex_spinner.getItemAtPosition(position).toString();
//                System.out.println("colorcomplex_str------" + colorcomplex_str);
//
//                if (!colorcomplex_str.equals("Select Color-Complex")) {
//                    if (color_complexion_flexLL.getVisibility() == View.GONE) {
//                        color_complexion_flexLL.setVisibility(View.VISIBLE);
//                    }
//
//                    System.out.println("00.0.1.===========" + "11111111111111111");
//
//                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(10, 5, 10, 5);
//
//
//                    color_complexion_txtv = new TextView(PartnerPreferencesActivity.this);
//                    color_complexion_txtv.setLayoutParams(params);
//                    color_complexion_txtv.setTextSize(16);
//                    color_complexion_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
//
//                    color_complexion_txtv.setPadding(20, 10, 20, 10);
//                    color_complexion_txtv.setLayoutParams(params);
//                    if (!colorcomplex_str.equals(color_complexion_txtv.getText().toString())) {
//
//
//                        color_complexion_txtv.setText(colorcomplex_str.trim() + "     x ");
//
//                        color_complexion_array.add(colorcomplex_str.trim());
//
//
//                    }
//
//
//                    color_complexion_flex.addView(color_complexion_txtv);
//
//
//                    final String myString = color_complexion_txtv.getText().toString();
//                    int i1 = myString.indexOf("x ");
//                    color_complexion_txtv.setMovementMethod(LinkMovementMethod.getInstance());
//                    color_complexion_txtv.setText(myString, TextView.BufferType.SPANNABLE);
//                    Spannable mySpannable = (Spannable) color_complexion_txtv.getText();
//
//                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
//
//                    ClickableSpan myClickableSpan = new ClickableSpan() {
//                        @Override
//                        public void onClick(View textView) {
//
//                            TextView textView1 = (TextView) textView;
//
//                            String charSequence = textView1.getText().toString().trim();
//
//                            String a = charSequence.substring(0, charSequence.length() - 2);
//
//                            System.out.println("character name----------" + a);
//                            System.out.println("final character name----------" + a.trim());
//
//
//                            color_complexion_array.remove(charSequence);
//
//                            for (int i = 0; i < color_complexion_array.size(); i++) {
//                                System.out.println("array character name----------" + color_complexion_array.get(i));
//
//                                if ((color_complexion_array.get(i).trim()).contains(a.trim())) {
//
//                                    int id = color_complexion_array.indexOf(a.trim());
//                                    System.out.println(" 11 array character name----------" + id);
//
//                                    color_complexion_array.remove(id);
//                                    textView1.setVisibility(View.GONE);
//                                }
//
//                            }
//
//                        }
//                    };
//
//                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void openColorComplexDialog() {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("Select Color Complexion");

        // ------------------------------
        // PRESELECT PREVIOUS DATA
        // ------------------------------
        selectedColorComplexList.clear();

        String prefColor = fetchPartnerPref.getPref_complex_name();  // e.g "Fair, Wheatish"
        if (prefColor != null && !prefColor.isEmpty()) {
            String[] arr = prefColor.split(",");
            for (String p : arr) selectedColorComplexList.add(p.trim());
        }

        // ------------------------------
        // Add Dynamic Checkboxes
        // ------------------------------
        for (ColorcomplexList.ColorcomplexListData item : colorcomplexLists) {

            CheckBox cb = new CheckBox(this);
            cb.setText(item.getName());
            cb.setTextSize(16);

            if (selectedColorComplexList.contains(item.getName().trim())) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnOk.setOnClickListener(v -> {

            selectedColorComplexList.clear();
            color_complexion_array.clear();
            color_complexion_flex.removeAllViews();

            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);

                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;

                    if (cb.isChecked()) {
                        selectedColorComplexList.add(cb.getText().toString());
                        color_complexion_array.add(cb.getText().toString());
                    }
                }
            }

            colorcomplex_str = TextUtils.join(", ", selectedColorComplexList);

            ((TextView) colorcomplex_spinner.getSelectedView()).setText(colorcomplex_str);

            addColorComplexTags();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void addColorComplexTags() {
        color_complexion_flex.removeAllViews();

        for (String item : color_complexion_array) {

            FlexboxLayout.LayoutParams params =
                    new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 10, 5);

            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setPadding(20, 10, 20, 10);
            tv.setTextSize(16);
            tv.setBackgroundColor(Color.parseColor("#110A0A0A"));
            tv.setText(item + "  x");

            tv.setOnClickListener(v -> {
                color_complexion_array.remove(item);
                tv.setVisibility(View.GONE);
            });

            color_complexion_flex.addView(tv);
        }
    }



    public void getMaritalStatusList() {

        maritalSettingLists.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<MaritalSettingList> userResponse = apiService.maritalSetting("17");
        userResponse.enqueue(new Callback<MaritalSettingList>() {

            @Override
            public void onResponse(Call<MaritalSettingList> call, Response<MaritalSettingList> response) {
                maritalSettingList = response.body();

                if (response.isSuccessful()) {


                    String success = maritalSettingList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < maritalSettingList.getMarital_Setting().size(); i++) {
                            maritalSettingLists.add(new MaritalSettingList.MaritalSettingListData(maritalSettingList.getMarital_Setting().get(i).getId(), maritalSettingList.getMarital_Setting().get(i).getName()));

                        }
                        marital_spinner_data(maritalSettingLists);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<MaritalSettingList> call, Throwable t) {

                System.out.println("err marital******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void marital_spinner_data(List<MaritalSettingList.MaritalSettingListData> maritalSettingListData) {

        marital_list.clear();

//        marital_list.add("Select Marital Status");
        marital_list.add("Doesn't Matter");

        if (marital_list.size() != 0) {
            for (int i = 0; i < maritalSettingListData.size(); i++) {

                marital_list.add(i + 1, maritalSettingListData.get(i).getName());

            }
        }
        adapter_marital = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, marital_list);

        marital_status_spinner.setAdapter(adapter_marital);

        int pos;
        for (int i = 0; i < marital_list.size(); i++) {
            if (marital_list.get(i).contains(fetchPartnerPref.getPref_marital_name())) {
                pos = i;
                marital_status_spinner.setSelection(pos);
            }
        }
        if (maritial_st == null){

        } else{
            marital_status_spinner1.setText(maritial_st);
        }

        if (maritial_st != null && !maritial_st.isEmpty()) {
            savedMaritalList = Arrays.asList(maritial_st.split(","));
        }
        marital_status_spinner1.setOnClickListener(v -> openMaritalDialog());

        marital_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                marital_str = marital_status_spinner.getItemAtPosition(position).toString();
                System.out.println("marital_str------" + marital_str);

                if (!marital_str.equals("Select Marital Status")) {
                    if (marital_status_flexLL.getVisibility() == View.GONE) {
                        marital_status_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    marital_status_txtv = new TextView(PartnerPreferencesActivity.this);
                    marital_status_txtv.setLayoutParams(params);
                    marital_status_txtv.setTextSize(16);
                    marital_status_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));

                    marital_status_txtv.setPadding(20, 10, 20, 10);
                    marital_status_txtv.setLayoutParams(params);
                    if (!marital_str.equals(marital_status_txtv.getText().toString())) {


                        marital_status_txtv.setText(marital_str.trim() + "     x ");

                        marital_status_array.add(marital_str.trim());


                    }


                    marital_status_flex.addView(marital_status_txtv);


                    final String myString = marital_status_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    marital_status_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    marital_status_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) marital_status_txtv.getText();

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {

                            TextView textView1 = (TextView) textView;

                            String charSequence = textView1.getText().toString().trim();

                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());


                            marital_status_array.remove(charSequence);

                            for (int i = 0; i < marital_status_array.size(); i++) {
                                System.out.println("array character name----------" + marital_status_array.get(i));

                                if ((marital_status_array.get(i).trim()).contains(a.trim())) {

                                    int id = marital_status_array.indexOf(a.trim());
                                    System.out.println(" 11 array character name----------" + id);

                                    marital_status_array.remove(id);
                                    textView1.setVisibility(View.GONE);
                                }

                            }

                        }
                    };

                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openMaritalDialog() {

        String[] items = marital_list.toArray(new String[0]);

        boolean[] checkedItems = new boolean[items.length];

        // Pre-check from saved list (from API)
        for (int i = 0; i < items.length; i++) {

            // trim both sides because API may have spaces
            if (savedMaritalList.contains(items[i].trim())) {
                checkedItems[i] = true;

                // Also add to selected array so chips show
                if (!marital_status_array.contains(items[i].trim())) {
                    marital_status_array.add(items[i].trim());
                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Marital Status");

        builder.setMultiChoiceItems(items, checkedItems, (dialog, which, isChecked) -> {
            if (isChecked) {
                marital_status_array.add(items[which]);
            } else {
                marital_status_array.remove(items[which]);
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            updateFlexboxChips();
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void updateFlexboxChips() {

        marital_status_flex.removeAllViews();

        for (String item : marital_status_array) {

            TextView chip = new TextView(this);
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 5, 10, 5);

            chip.setLayoutParams(params);
            chip.setPadding(20, 10, 20, 10);
            chip.setBackgroundColor(Color.parseColor("#110A0A0A"));
            chip.setText(item + "  x");

            chip.setTextSize(16);
            chip.setMovementMethod(LinkMovementMethod.getInstance());

            Spannable span = new SpannableString(chip.getText());
            int start = chip.getText().toString().indexOf("x");

            span.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    marital_status_array.remove(item);
                    updateFlexboxChips();
                }
            }, start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            chip.setText(span);
            marital_status_flex.addView(chip);
        }
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


    //???????????????????
    /*eduction type and education*/
    public void getEducationTypeList() {

        educationTypeLists.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<EducationType> userResponse = apiService.educationType("24");
        userResponse.enqueue(new Callback<EducationType>() {

            @Override
            public void onResponse(Call<EducationType> call, Response<EducationType> response) {
                educationType = response.body();

                if (response.isSuccessful()) {


                    String success = educationType.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < educationType.getEducationTypeList().size(); i++) {
                            educationTypeLists.add(new EducationType.EducationTypeList(educationType.getEducationTypeList().get(i).getId(), educationType.getEducationTypeList().get(i).getName()));

                        }
                        education_type_spinner_data(educationTypeLists);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<EducationType> call, Throwable t) {

                System.out.println("err EducationType******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void education_type_spinner_data(List<EducationType.EducationTypeList> educationTypeLists) {

        education_type_list.clear();
        education_type_list.add("Select Education Type");
        if (education_type_list.size() != 0) {
            for (int i = 0; i < educationTypeLists.size(); i++) {
                education_type_list.add(i + 1, educationTypeLists.get(i).getName().trim());
            }
        }
        adapter_education_type = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, education_type_list);

        education_type_spinner.setAdapter(adapter_education_type);

        /*----------*/
        int pos;


//        if (fetchPartnerPref.getPrefmain_edu_name() != null) {
//            education_type_flexLL.setVisibility(View.VISIBLE);
//
//            if (fetchPartnerPref.getPrefmain_edu_name().contains(",")) {
//
//                String[] namesList_ = fetchPartnerPref.getPrefmain_edu_name().split(",");
//
//                if (namesList_.length == 0) {
//                    educationPkgHandle();
//
//
//                } else if (namesList_.length > 1) {
//                    /*hide education selection if education type is more than 1*/
//
//                    educationPkgHandle();
//                    /*hide education selection if education type is more than 1*/
//                } else {
//
//                    educationPkgHandleVisible();
//
//
//
//                    /*????????*/
//
//                    getEducationList("");
//                    /*????????*/
//
//                }
//
//                for (int i = 0; i < namesList_.length; i++) {
//
//                    System.out.println("namesList_ size-------------" + namesList_[i]);
//
//                    education_type_spinner.setSelection(0);
//
//                    if (!namesList_[i].equals("Select Education Type")) {
//
//                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                        params.setMargins(10, 5, 10, 5);
//
//
//                        textView1_type = new TextView(PartnerPreferencesActivity.this);
//                        textView1_type.setLayoutParams(params);
//                        textView1_type.setTextSize(16);
//                        textView1_type.setBackgroundColor(Color.parseColor("#110A0A0A"));
//                        textView1_type.setPadding(20, 10, 20, 10);
//                        textView1_type.setLayoutParams(params);
//
//                        System.out.println("namesList_[i] 11===========" + namesList_[i]);
//                        System.out.println("textView1_type.getText().toString() 11===========" + textView1_type.getText().toString());
//
//                        if (!namesList_[i].equals(textView1_type.getText().toString())) {
//                            textView1_type.setText(namesList_[i].trim() + "     x ");
//                            education_type_flexLL.setVisibility(View.VISIBLE);
//                            education_type_array.add(namesList_[i].trim());
//
//                        }
//
//                        System.out.println("textView1_type 11===========" + textView1_type.getText().toString());
//
//                        education_type_flex.addView(textView1_type);
//
//
//                        final String myString = textView1_type.getText().toString();
//                        int i1 = myString.indexOf("x ");
//                        textView1_type.setMovementMethod(LinkMovementMethod.getInstance());
//                        textView1_type.setText(myString, TextView.BufferType.SPANNABLE);
//                        Spannable mySpannable = (Spannable) textView1_type.getText();
//
//                        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
//
//                        ClickableSpan myClickableSpan = new ClickableSpan() {
//                            @Override
//                            public void onClick(View textView) {
//
//                                TextView textView1 = (TextView) textView;
//
//                                String charSequence = textView1.getText().toString().trim();
//
//                                String a = charSequence.substring(0, charSequence.length() - 2);
//
//                                System.out.println("character name----------" + a);
//                                System.out.println("final character name----------" + a.trim());
//
//
//                                for (int i = 0; i < education_type_array.size(); i++) {
//                                    System.out.println("array character name----------" + education_type_array.get(i).trim() + "---");
//                                    System.out.println("11 array character name----------" + a.trim() + "---");
//
//                                    if ((education_type_array.get(i).trim()).contains(a.trim())) {
//
//                                        int id = education_type_array.indexOf(a.trim());
//                                        System.out.println(" 11 array character name----------:" + id);
//
//                                        /*----------remove education--------*/
//
//                                        for (int x = 0; x < education_pck_array.size(); x++) {
//                                            System.out.println("chekc name 111------------" + education_pck_array.get(x).toString());
//                                        }
//
//                                        /*----------remove education--------*/
//
//
//                                        education_type_array.remove(id);
//                                        education_type_array.remove(charSequence);
//
//                                        textView.setVisibility(View.GONE);
//                                        System.out.println("education_type_array size 22=============" + education_type_array.size());
//
//                                        for (int k = 0; k < education_type_array.size(); k++) {
//                                            System.out.println("education_type_array size 000=============" + education_type_array.get(k));
//
//                                        }
//
//                                    }
//
//                                }
//                                education_type_array.remove(charSequence);
//
//
//                                hideShowEducation();
//
//
//                            }
//                        };
//
//                        mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                    }
//
//
//                }
//
//            } else {
//
//
////                String[] namesList_ = fetchPartnerPref.getPrefmain_edu_name();
//                String[] namesList_ = {fetchPartnerPref.getPrefmain_edu_name()};
//                for (int i = 0; i < namesList_.length; i++) {
//
//                    System.out.println("namesList_ size-------------" + namesList_[i]);
//
//                    education_type_spinner.setSelection(0);
//
//                    if (!namesList_[i].equals("Select Education Type")) {
//
//                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                        params.setMargins(10, 5, 10, 5);
//
//
//                        textView1_type = new TextView(PartnerPreferencesActivity.this);
//                        textView1_type.setLayoutParams(params);
//                        textView1_type.setTextSize(16);
//                        textView1_type.setBackgroundColor(Color.parseColor("#110A0A0A"));
//                        textView1_type.setPadding(20, 10, 20, 10);
//                        textView1_type.setLayoutParams(params);
//
//                        System.out.println("namesList_[i] 11===========" + namesList_[i]);
//                        System.out.println("textView1_type.getText().toString() 11===========" + textView1_type.getText().toString());
//
//                        if (!namesList_[i].equals(textView1_type.getText().toString())) {
//                            textView1_type.setText(namesList_[i].trim() + "     x ");
//                            education_type_flexLL.setVisibility(View.VISIBLE);
//                            education_type_array.add(namesList_[i].trim());
//
//                        }
//
//                        System.out.println("textView1_type 11===========" + textView1_type.getText().toString());
//
//
//                        education_type_flex.addView(textView1_type);
//
//
//                        final String myString = textView1_type.getText().toString();
//                        int i1 = myString.indexOf("x ");
//                        textView1_type.setMovementMethod(LinkMovementMethod.getInstance());
//                        textView1_type.setText(myString, TextView.BufferType.SPANNABLE);
//                        Spannable mySpannable = (Spannable) textView1_type.getText();
//
//                        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
//
//                        ClickableSpan myClickableSpan = new ClickableSpan() {
//                            @Override
//                            public void onClick(View textView) {
//
//                                TextView textView1 = (TextView) textView;
//
//                                String charSequence = textView1.getText().toString().trim();
//
//                                String a = charSequence.substring(0, charSequence.length() - 2);
//
//                                System.out.println("character name----------" + a);
//                                System.out.println("final character name----------" + a.trim());
//
//
//                                for (int i = 0; i < education_type_array.size(); i++) {
//                                    System.out.println("array character name----------" + education_type_array.get(i).trim() + "---");
//                                    System.out.println("11 array character name----------" + a.trim() + "---");
//
//                                    if ((education_type_array.get(i).trim()).contains(a.trim())) {
//
//                                        int id = education_type_array.indexOf(a.trim());
//                                        System.out.println(" 11 array character name----------:" + id);
//
//                                        /*----------remove education--------*/
//
//                                        for (int x = 0; x < education_pck_array.size(); x++) {
//                                            System.out.println("chekc name 111------------" + education_pck_array.get(x).toString());
//                                        }
//
//
//
//
//                                        /*----------remove education--------*/
//
//
//                                        education_type_array.remove(id);
//                                        education_type_array.remove(charSequence);
//                                        textView.setVisibility(View.GONE);
//                                        System.out.println("education_type_array size 22=============" + education_type_array.size());
//
//                                        for (int k = 0; k < education_type_array.size(); k++) {
//                                            System.out.println("education_type_array size 000=============" + education_type_array.get(k));
//
//                                        }
//                                        System.out.println();
//
//                                    }
//
//                                }
//                                education_type_array.remove(charSequence);
//
//
//                                hideShowEducation();
//
//
//                            }
//                        };
//
//                        mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                    }
//
//
//                }
//
//
//            }
//
//        }

        System.out.println("education_type_array size 33=============" + education_type_array.size());
        /*----------*/
//        education_type_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                openEducationTypeDialog();
//            }
//            return true;
//        });


        education_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                education_type_list_str = education_type_spinner.getItemAtPosition(position).toString();
                System.out.println(" 11 new education_type_list_str------" + education_type_list_str);


//                hideShowEducation();


                if (!education_type_list_str.equals("Select Education Type")) {


                    if (education_type_flexLL.getVisibility() == View.GONE) {
                        education_type_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    education_type_txtv = new TextView(PartnerPreferencesActivity.this);
                    education_type_txtv.setLayoutParams(params);
                    education_type_txtv.setTextSize(16);
                    education_type_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));

                    education_type_txtv.setPadding(20, 10, 20, 10);
                    education_type_txtv.setLayoutParams(params);

                    if (!education_type_list_str.equals(education_type_txtv.getText().toString())) {


                        education_type_txtv.setText(education_type_list_str.trim() + "     x ");

                        education_type_array.add(education_type_list_str.trim());


                    }


                    education_type_flex.addView(education_type_txtv);


                    final String myString = education_type_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    education_type_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    education_type_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) education_type_txtv.getText();

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);


                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {

                            TextView textView1 = (TextView) textView;

                            String charSequence = textView1.getText().toString().trim();

                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());


//                            education_array.remove(charSequence);

                            for (int i = 0; i < education_type_array.size(); i++) {
                                System.out.println("array character education name----------" + education_type_array.get(i).trim());

                                if ((education_type_array.get(i).trim()).contains(a.trim())) {

                                    int id = education_type_array.indexOf(a.trim());
                                    System.out.println("11 array  education type id character name---------->>>" + id);

//                                    education_array.remove(charSequence);
                                    education_type_array.remove(id);
                                    education_type_array.remove(charSequence);
                                    textView1.setVisibility(View.GONE);
                                } else if (a.trim().contains("Doesn't Matter")) {
                                    education_type_array.remove(charSequence);
                                    textView1.setVisibility(View.GONE);
                                }

                            }

                            System.out.println("array education after delete=======" + education_type_array.size());
//                            hideShowEducation("");


                            callNewCheckEducation();




                            /*hide education selection if education type is more than 1*/

                        }
                    };

                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                    callNewCheckEducation();
                }
                System.out.println("check education size------>>" + education_type_array.size());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openEducationTypeDialog() {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        // -------- Title -------
        title.setText("Select Education Type");

        // -------- Preselect Based on Saved IDs -------
        selectedEducationList.clear();
        selectedEducationIdList.clear();

//        String prefEduType = fetchPartnerPref.getPrefmain_edu_id();  // e.g. "2,5,7"

        if (prefEduType != null && !prefEduType.isEmpty()) {
            String[] arr = prefEduType.split(",");
            for (String p : arr) {
                selectedEducationIdList.add(p.trim());
            }
        }

        // -------- Render Checkboxes Dynamically -------
        container.removeAllViews();

        for (EducationType.EducationTypeList item : educationTypeLists) {

            CheckBox cb = new CheckBox(this);
            cb.setText(item.getName());
            cb.setTextSize(16);

            if (selectedEducationIdList.contains(String.valueOf(item.getId()))) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }

        // -------- Cancel --------
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // -------- OK --------
        btnOk.setOnClickListener(v -> {

            selectedEducationList.clear();
            selectedEducationIdList.clear();
            education_type_array.clear();
            education_type_flex.removeAllViews();

            StringBuilder idBuilder = new StringBuilder();

            // Collect selected items
            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);

                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;

                    if (cb.isChecked()) {
                        String selectedName = cb.getText().toString();
                        selectedEducationList.add(selectedName);
                        education_type_array.add(selectedName);

                        // Map name to ID
                        for (EducationType.EducationTypeList item : educationTypeLists) {
                            if (item.getName().equals(selectedName)) {

                                selectedEducationIdList.add(String.valueOf(item.getId()));
                                idBuilder.append(item.getId()).append(",");
                                break;
                            }
                        }
                    }
                }
            }

            // Final ID string
            if (idBuilder.length() > 0) {
                getEducation_type_id = idBuilder.substring(0, idBuilder.length() - 1);
            } else {
                getEducation_type_id = "";
            }

            // Name string
            education_type_str = TextUtils.join(", ", selectedEducationList);

            // Set text in spinner
            ((TextView) education_type_spinner.getSelectedView())
                    .setText(education_type_str);

            // Generate removable tags
            addEducationTypeTags();

            dialog.dismiss();
        });

        dialog.show();
    }

    private void addEducationTypeTags() {

        education_type_flex.removeAllViews();

        for (String item : education_type_array) {

            FlexboxLayout.LayoutParams params =
                    new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 10, 5);

            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setTextSize(16);
            tv.setPadding(20, 10, 20, 10);
            tv.setBackgroundColor(Color.parseColor("#110A0A0A"));

            tv.setText(item + "   x");

            int removeIndexStart = tv.getText().toString().indexOf("x");

            tv.setMovementMethod(LinkMovementMethod.getInstance());
            tv.setText(tv.getText(), TextView.BufferType.SPANNABLE);

            Spannable span = (Spannable) tv.getText();

            span.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    education_type_array.remove(item);
                    addEducationTypeTags();
                }
            }, removeIndexStart, removeIndexStart + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            education_type_flex.addView(tv);
        }
    }



    private void callNewCheckEducation() {

        /*hide education selection if education type is more than 1*/

        if (education_type_array.size() == 0) {

            educationPkgHandle();

        } else if (education_type_array.size() > 1) {
            educationPkgHandle();
        } else {
            educationPkgHandleVisible();

            /*????????*/
            System.out.println("hideShowEducation(education_list_str)======" + education_list_str);
            hideShowEducation(education_type_list_str);

//                    getEducationList("");
            /*????????*/
        }

    }


    private void hideShowEducation(String value_str) {
        System.out.println("???????? check-----------------");
        /*hide education selection if education type is more than 1*/
        if (education_type_array.size() == 0) {
            education_pckg_flex.removeAllViews();
            education_pck_array.clear();
            educationFlexBothLL.setVisibility(View.GONE);
        } else if (education_type_array.size() > 1) {
            education_pckg_flex.removeAllViews();
            education_pck_array.clear();
            educationFlexBothLL.setVisibility(View.GONE);
        } else {
            educationFlexBothLL.setVisibility(View.VISIBLE);
            education_pckg_flex.removeAllViews();
            education_pck_array.clear();
            /*????????*/

            getEducationList(value_str);
            /*????????*/


        }
        /*hide education selection if education type is more than 1*/
    }

    private void educationPkgHandleVisible() {
        educationPkgHandle();
    }


    public void educationPkgHandle() {
        education_pckg_flex.removeAllViews();
        education_pck_array.clear();
        educationFlexBothLL.setVisibility(View.GONE);
    }

    public void getEducationList(String education_type_list_str) {

        System.out.println("xxxyy check 11----------" + education_type_list_str);

        education_type_id = "";
        if (!education_type_list_str.equals("")) {
            System.out.println("check 11----------" + education_type_array.size());

            for (int j = 0; j < education_type_array.size(); j++) {
                System.out.println("111 character name----------" + education_type_array.get(j));


                System.out.println("check 11----------" + educationTypeLists.size());


                for (int i = 0; i < educationTypeLists.size(); i++) {

                    System.out.println("xx check 11----------" + education_type_array.get(j).trim());
                    System.out.println("dd check 11----------" + educationTypeLists.get(i).getName().trim());


                    if (education_type_array.get(j).trim().equals(educationTypeLists.get(i).getName().trim())) {


                        education_type_id = String.valueOf(educationTypeLists.get(i).getId());


                        System.out.println("education_type_id for education----------" + education_type_id);
                        System.out.println("education_type_id for education----------" + educationTypeLists.get(i).getName());

                        break;

                    }
                }


            }

        }
//        else {
//
//                for (int i = 0; i < educationTypeLists.size(); i++) {
//                    if (education_type_list_str.equals(educationTypeLists.get(i).getName())) {
//                        education_type_id = String.valueOf(educationTypeLists.get(i).getId());
//
//
//                    }
//                }
//
//
//        }


        educationListData.clear();
        education_pckg_flex.removeAllViews();
        education_pck_array.clear();

        education_list.clear();
        highest_education_spinner.setAdapter(null);
        ;


        progressBar = ProgressDialog.show(PartnerPreferencesActivity.this, "", "Please Wait...");

        System.out.println("education_type_id-------->>>>>>" + education_type_id);

        Api apiService = RetrofitClient.getApiService();
        Call<EducationList> userResponse = apiService.education("09", education_type_id);
        userResponse.enqueue(new Callback<EducationList>() {

            @Override
            public void onResponse(Call<EducationList> call, Response<EducationList> response) {
                educationList = response.body();
                progressBar.dismiss();


                if (response.isSuccessful()) {


                    String success = educationList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < educationList.getEducationList().size(); i++) {
                            educationListData.add(new EducationList.EducationListData(educationList.getEducationList().get(i).getId(), educationList.getEducationList().get(i).getName()));

                        }
                        education_spinner_data(educationListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<EducationList> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err education******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



    public void education_spinner_data(List<EducationList.EducationListData> educationListData) {
        /*-----*/
        education_pck_array.clear();
        education_pck_array_education_name_id.clear();
        /*-----*/

        education_list.clear();
        education_pckg_flex.removeAllViews();
        education_list.add("Select Education");
        education_list.add("Doesn't Matter");

        if (educationListData != null && !educationListData.isEmpty()) {
            for (int i = 0; i < educationListData.size(); i++) {
                education_list.add(i + 2, educationListData.get(i).getName().trim());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        adapter_education = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, education_list);
        adapter_education.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        highest_education_spinner.setAdapter(adapter_education);

        // Uncomment the following block if you want to enable pre-selection
    /*
    System.out.println("education list-------------" + fetchPartnerPref.getPref_edu_name());
    if (fetchPartnerPref.getPref_edu_name() != null) {
        if (fetchPartnerPref.getPref_edu_name().equals("Doesn't Matter")) {
            highest_education_spinner.setSelection(0);
        } else if (fetchPartnerPref.getPref_edu_name().contains(",")) {
            namesList = fetchPartnerPref.getPref_edu_name().split(",");
            highest_education_spinner.setSelection(0);

            for (int i = 0; i < namesList.length; i++) {
                System.out.println("0000 education namesList size-------------" + namesList[i]);
                for (int j = 0; j < education_list.size(); j++) {
                    if (education_list.get(j).contains(namesList[0])) {
                        pos = j;
                    }
                }

                if (!namesList[i].equals("Select Education")) {
                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);

                    textView1 = new TextView(PartnerPreferencesActivity.this);
                    textView1.setLayoutParams(params);
                    textView1.setTextSize(16);
                    textView1.setBackgroundColor(Color.parseColor("#110A0A0A"));
                    textView1.setPadding(20, 10, 20, 10);
                    textView1.setLayoutParams(params);

                    if (!namesList[i].equals(textView1.getText().toString())) {
                        textView1.setText(namesList[i].trim() + "     x ");
                        education_pck_array.add(namesList[i].trim());

                        for (int x = 0; x < educationListData.size(); x++) {
                            System.out.println("11 aaaa=======>>" + namesList[i]);
                            System.out.println("22 aaaa=======>>" + educationListData.get(x).getName().trim());
                            if (namesList[i].trim().equals(educationListData.get(x).getName().trim())) {
                                education_pck_array_education_name_id.add(educationListData.get(x).getId());
                                break;
                            }
                        }

                        for (int x = 0; x < education_pck_array_education_name_id.size(); x++) {
                            System.out.println("education_pck_array_education_name_id ----------" + education_pck_array_education_name_id.get(x));
                        }
                    }

                    education_pckg_flex.addView(textView1);

                    final String myString = textView1.getText().toString();
                    int i1 = myString.indexOf("x ");
                    textView1.setMovementMethod(LinkMovementMethod.getInstance());
                    textView1.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) textView1.getText();
                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            TextView textView1 = (TextView) textView;
                            String charSequence = textView1.getText().toString().trim();
                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());

                            for (int i = 0; i < education_pck_array.size(); i++) {
                                System.out.println("array character name----------" + education_pck_array.get(i).trim() + "---");
                                System.out.println("11 array character name----------" + a.trim() + "---");

                                if (education_pck_array.get(i).trim().contains(a.trim())) {
                                    int id = education_pck_array.indexOf(a.trim());
                                    System.out.println(" 11 array character name----------:" + id);

                                    education_pck_array.remove(id);
                                    education_pck_array.remove(charSequence);

                                    for (int x = 0; x < educationListData.size(); x++) {
                                        if (a.trim().equals(educationListData.get(x).getName())) {
                                            String id_ofArray = educationListData.get(x).getId();
                                            int id_ = education_pck_array_education_name_id.indexOf(id_ofArray);
                                            education_pck_array_education_name_id.remove(id_);
                                        }
                                    }

                                    textView.setVisibility(View.GONE);
                                    System.out.println("education_pck_array size 22=============" + education_pck_array.size());

                                    for (int k = 0; k < education_pck_array.size(); k++) {
                                        System.out.println("education_pck_array size 000=============" + education_pck_array.get(k));
                                    }
                                }
                            }
                            education_pck_array.remove(charSequence);
                        }
                    };

                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        } else {
            for (int i = 0; i < education_list.size(); i++) {
                if (education_list.get(i).trim().contains(fetchPartnerPref.getPref_edu_name().trim())) {
                    pos = i;
                    highest_education_spinner.setSelection(pos);
                }
            }
        }
    }
    System.out.println("education_pck_array size 33=============" + education_pck_array.size());
    */

        // Handle Spinner click to show searchable dialog
//        highest_education_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                openEducationDialog(educationListData);
//            }
//            return true;
//        });

//        highest_education_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableEducationDialog(education_list, educationListData);
//            }
//            return true; // Consume the touch event
//        });

        highest_education_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openMultiEducationDialog(education_list, educationListData);
            }
            return true;
        });

    }

    private void openMultiEducationDialog(
            List<String> educationList,
            List<EducationList.EducationListData> educationListData) {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("Highest Education");
        container.removeAllViews();

        // ---------- PRESELECT ----------
        ArrayList<String> selectedEdu = new ArrayList<>();
        if (education_list_str != null && !education_list_str.isEmpty()) {
            String[] arr = education_list_str.split(",");
            for (String s : arr) selectedEdu.add(s.trim());
        }

        // ---------- CREATE CHECKBOXES ----------
        for (String edu : educationList) {
            CheckBox cb = new CheckBox(this);
            cb.setText(edu);
            cb.setTextSize(16);

            if (selectedEdu.contains(edu)) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }

        // ---------- CANCEL ----------
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // ---------- OK ----------
        btnOk.setOnClickListener(v -> {

            // CLEAR OLD DATA
            education_pck_array.clear();
            education_pck_array_education_name_id.clear();
            education_pckg_flex.removeAllViews();

            // COLLECT CHECKED
            for (int i = 0; i < container.getChildCount(); i++) {
                CheckBox cb = (CheckBox) container.getChildAt(i);
                if (!cb.isChecked()) continue;

                String eduName = cb.getText().toString().trim();
                education_pck_array.add(eduName);

                // FIND ID
                for (EducationList.EducationListData d : educationListData) {
                    if (eduName.equals(d.getName().trim())) {
                        education_pck_array_education_name_id.add(d.getId());
                        break;
                    }
                }
            }

            // BUILD STRING
            education_list_str = TextUtils.join(", ", education_pck_array);

            // ---------- FLEXBOX (SAME BEHAVIOR AS BEFORE) ----------
            if (!education_pck_array.isEmpty()) {
                education_flexLL.setVisibility(View.VISIBLE);
            }

            for (String edu : education_pck_array) {

                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 5, 10, 5);

                TextView tv = new TextView(this);
                tv.setLayoutParams(params);
                tv.setTextSize(16);
                tv.setPadding(20, 10, 20, 10);
                tv.setBackgroundColor(Color.parseColor("#110A0A0A"));
                tv.setText(edu + "     x ");

                education_pckg_flex.addView(tv);
            }

            // UPDATE SPINNER TEXT (NO BACKEND CHANGE NEEDED)
            ((TextView) highest_education_spinner.getSelectedView())
                    .setText(education_list_str);

            dialog.dismiss();
        });

        dialog.show();
    }


    private void openEducationDialog(List<EducationList.EducationListData> educationListData) {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("Select Education");

        // ------------------------ PRESELECT DATA ------------------------
        selectedEducationList.clear();
        selectedEducationIdList.clear();

        if (getPref_education_name != null) {
            String[] arr = getPref_education_name.split(",");
            for (String a : arr) {
                selectedEducationList.add(a.trim());
            }
        }

        if (getPref_education_id != null) {
            String[] arr = getPref_education_id.split(",");
            for (String a : arr) {
                selectedEducationIdList.add(a.trim());
            }
        }

        // ------------------------ DYNAMIC CHECKBOXES ------------------------
        container.removeAllViews();

        for (EducationList.EducationListData item : educationListData) {

            CheckBox cb = new CheckBox(this);
            cb.setText(item.getName());
            cb.setTextSize(16);

            if (selectedEducationList.contains(item.getName().trim())) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }

        // ------------------------ OK BUTTON ------------------------
        btnOk.setOnClickListener(v -> {
            selectedEducationList.clear();
            selectedEducationIdList.clear();
            education_pck_array.clear();
            education_pck_array_education_name_id.clear();
            education_pckg_flex.removeAllViews();

            for (int i = 0; i < container.getChildCount(); i++) {
                View view = container.getChildAt(i);

                if (view instanceof CheckBox) {
                    CheckBox cb = (CheckBox) view;

                    if (cb.isChecked()) {
                        String name = cb.getText().toString().trim();
                        selectedEducationList.add(name);
                        education_pck_array.add(name);

                        for (EducationList.EducationListData item : educationListData) {
                            if (item.getName().trim().equals(name)) {
                                selectedEducationIdList.add(item.getId());
                                education_pck_array_education_name_id.add(item.getId());
                            }
                        }

                        addEducationFlexItem(name);
                    }
                }
            }

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void addEducationFlexItem(String name) {
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 5, 10, 5);

        TextView tv = new TextView(this);
        tv.setLayoutParams(params);
        tv.setTextSize(16);
        tv.setBackgroundColor(Color.parseColor("#110A0A0A"));
        tv.setPadding(20, 10, 20, 10);

        tv.setText(name + "  x ");

        String removeText = tv.getText().toString();
        int index = removeText.indexOf("x ");

        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(removeText, TextView.BufferType.SPANNABLE);

        Spannable span = (Spannable) tv.getText();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                String base = name.trim();

                education_pck_array.remove(base);

                for (int i = 0; i < education_pck_array_education_name_id.size(); i++) {
                    EducationList.EducationListData d = null;
                }

                tv.setVisibility(View.GONE);
            }
        };

        span.setSpan(clickableSpan, index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        education_pckg_flex.addView(tv);
    }



    // Method to show a searchable dialog for education selection
    private void showSearchableEducationDialog(List<String> educationList, List<EducationList.EducationListData> educationListData) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalEducationList = new ArrayList<>(educationList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                educationList
        );
        listView.setAdapter(dialogAdapter);

        // Filter list based on search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                // Get the selected item from the filtered list
                String selectedEducation = dialogAdapter.getItem(position);
                if (selectedEducation == null) {
                    Log.e("EducationDialog", "Selected education is null at position: " + position);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting education. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list to get the correct position
                int originalPosition = -1;
                for (int i = 0; i < originalEducationList.size(); i++) {
                    if (originalEducationList.get(i).equals(selectedEducation)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("EducationDialog", "Could not find selected education in original list: " + selectedEducation);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting education. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                education_list_str = selectedEducation.trim();
                Log.d("EducationDialog", "Selected education: " + education_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                highest_education_spinner.setSelection(originalPosition);

                // Replicate the existing onItemSelected logic
                System.out.println("education_list_str------" + education_list_str);
                for (int h = 0; h < education_pck_array.size(); h++) {
                    System.out.println("11100.0.1.===========" + education_pck_array.get(h));
                }

                /*new cond*/
                status_education = "0";
                for (int x = 0; x < education_pck_array.size(); x++) {
                    System.out.println("11 xx zz==============" + education_pck_array.get(x));
                    System.out.println("22 xx zz==============" + education_list_str);
                    if (education_pck_array.get(x).equals(education_list_str)) {
                        status_education = "1";
                    }
                }

                if (status_education.equals("0")) {
                    if (education_pck_array.size() != 0) {
                        for (int h = 0; h < education_pck_array.size(); h++) {
                            if (!education_list_str.equals("Select Education") && !education_pck_array.contains(education_list_str.trim())) {
                                if (education_flexLL.getVisibility() == View.GONE) {
                                    education_flexLL.setVisibility(View.VISIBLE);
                                }

                                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(10, 5, 10, 5);

                                textView2 = new TextView(PartnerPreferencesActivity.this);
                                textView2.setLayoutParams(params);
                                textView2.setTextSize(16);
                                textView2.setBackgroundColor(Color.parseColor("#110A0A0A"));
                                textView2.setPadding(20, 10, 20, 10);
                                textView2.setLayoutParams(params);

                                System.out.println("textView2.getText().toString().===========" + textView2.getText().toString());

                                if (!education_list_str.trim().equals(textView2.getText().toString())) {
                                    if (textView1 != null) {
                                        if (!textView2.getText().toString().equals(textView1.getText().toString())) {
                                            textView2.setText(education_list_str.trim() + "     x ");
                                            education_pck_array.add(education_list_str.trim());

                                            for (int x = 0; x < educationListData.size(); x++) {
                                                if (education_list_str.trim().equals(educationListData.get(x).getName().trim())) {
                                                    education_pck_array_education_name_id.add(educationListData.get(x).getId());
                                                }
                                            }
                                        }
                                    } else {
                                        textView2.setText(education_list_str.trim() + "     x ");
                                        education_pck_array.add(education_list_str.trim());

                                        for (int x = 0; x < educationListData.size(); x++) {
                                            if (education_list_str.equals(educationListData.get(x).getName().trim())) {
                                                education_pck_array_education_name_id.add(educationListData.get(x).getId());
                                            }
                                        }
                                    }
                                }

                                education_pckg_flex.addView(textView2);

                                final String myString = textView2.getText().toString();
                                int i1 = myString.indexOf("x ");
                                textView2.setMovementMethod(LinkMovementMethod.getInstance());
                                textView2.setText(myString, TextView.BufferType.SPANNABLE);
                                Spannable mySpannable = (Spannable) textView2.getText();
                                ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                                ClickableSpan myClickableSpan = new ClickableSpan() {
                                    @Override
                                    public void onClick(View textView) {
                                        TextView textView1 = (TextView) textView;
                                        String charSequence = textView1.getText().toString();
                                        String a = charSequence.substring(0, charSequence.length() - 2);

                                        System.out.println("character name----------" + a);
                                        System.out.println("final character name----------" + a.trim());

                                        for (int i = 0; i < education_pck_array.size(); i++) {
                                            System.out.println("array character name---------->>>" + education_pck_array.get(i).trim());
                                            System.out.println("array character name---------->>>" + a.trim());

                                            if (education_pck_array.get(i).trim().contains(a.trim())) {
                                                int id = education_pck_array.indexOf(a.trim());
                                                System.out.println("remove id check----------" + id);

                                                education_pck_array.remove(charSequence);
                                                education_pck_array.remove(id);

                                                for (int x = 0; x < educationListData.size(); x++) {
                                                    if (a.trim().equals(educationListData.get(x).getName())) {
                                                        String id_ofArray = educationListData.get(x).getId();
                                                        int id_ = education_pck_array_education_name_id.indexOf(id_ofArray);
                                                        education_pck_array_education_name_id.remove(id_);
                                                    }
                                                }

                                                textView1.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                };

                                mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    } else {
                        if (!education_list_str.equals("Select Education")) {
                            if (education_flexLL.getVisibility() == View.GONE) {
                                education_flexLL.setVisibility(View.VISIBLE);
                            }

                            System.out.println("00.0.1.===========" + "11111111111111111");

                            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(10, 5, 10, 5);

                            textView3 = new TextView(PartnerPreferencesActivity.this);
                            textView3.setLayoutParams(params);
                            textView3.setTextSize(16);
                            textView3.setBackgroundColor(Color.parseColor("#110A0A0A"));
                            textView3.setPadding(20, 10, 20, 10);
                            textView3.setLayoutParams(params);

                            if (!education_list_str.trim().equals(textView3.getText().toString())) {
                                textView3.setText(education_list_str.trim() + "     x ");
                                education_pck_array.add(education_list_str.trim());

                                for (int x = 0; x < educationListData.size(); x++) {
                                    if (education_list_str.equals(educationListData.get(x).getName().trim())) {
                                        education_pck_array_education_name_id.add(educationListData.get(x).getId());
                                    }
                                }
                            }

                            education_pckg_flex.addView(textView3);

                            final String myString = textView3.getText().toString();
                            int i1 = myString.indexOf("x ");
                            textView3.setMovementMethod(LinkMovementMethod.getInstance());
                            textView3.setText(myString, TextView.BufferType.SPANNABLE);
                            Spannable mySpannable = (Spannable) textView3.getText();
                            ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                            ClickableSpan myClickableSpan = new ClickableSpan() {
                                @Override
                                public void onClick(View textView) {
                                    TextView textView1 = (TextView) textView;
                                    String charSequence = textView1.getText().toString();
                                    String a = charSequence.substring(0, charSequence.length() - 2);

                                    System.out.println("character name----------" + a);
                                    System.out.println("final character name----------" + a.trim());

                                    for (int i = 0; i < education_pck_array.size(); i++) {
                                        System.out.println("array character name----------" + education_pck_array.get(i));
                                        if (education_pck_array.get(i).trim().contains(a.trim())) {
                                            int id = education_pck_array.indexOf(a.trim());
                                            System.out.println(" remove txt index----------" + id);

                                            education_pck_array.remove(charSequence);
                                            education_pck_array.remove(id);

                                            for (int x = 0; x < educationListData.size(); x++) {
                                                if (a.trim().equals(educationListData.get(x).getName())) {
                                                    String id_ofArray = educationListData.get(x).getId();
                                                    int id_ = education_pck_array_education_name_id.indexOf(id_ofArray);
                                                    education_pck_array_education_name_id.remove(id_);
                                                }
                                            }

                                            textView1.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            };

                            mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } else {
                    Toast.makeText(PartnerPreferencesActivity.this, "Already Added!!!", Toast.LENGTH_SHORT).show();
                }
                /*new cond*/

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("EducationDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting education: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }    /*end eduction type and education*/





    //*****************
    /*occupation type and occupation*/
    public void getOccupationTypeList() {

        occupationTypeLists.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationType> userResponse = apiService.occupationType("25");
        userResponse.enqueue(new Callback<OccupationType>() {

            @Override
            public void onResponse(Call<OccupationType> call, Response<OccupationType> response) {
                occupationType = response.body();

                if (response.isSuccessful()) {


                    String success = occupationType.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < occupationType.getOccupationTypeList().size(); i++) {
                            occupationTypeLists.add(new OccupationType.OccupationTypeData(occupationType.getOccupationTypeList().get(i).getId(), occupationType.getOccupationTypeList().get(i).getName()));

                        }
                        occupation_type_spinner_data(occupationTypeLists);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationType> call, Throwable t) {

                System.out.println("err OccupationType******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void occupation_type_spinner_data(List<OccupationType.OccupationTypeData>
                                                     OccupationTypeData) {

        occupation_type_list.clear();
        occupation_type_list.add("Select Occupation Type");
        if (occupation_type_list.size() != 0) {
            for (int i = 0; i < occupationTypeLists.size(); i++) {
                occupation_type_list.add(i + 1, occupationTypeLists.get(i).getName().trim());
            }
        }
        adapter_occupation_type = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, occupation_type_list);

        occupation_type_spinner.setAdapter(adapter_occupation_type);

        /*----------*/
        int pos;

        System.out.println("occupation_type_array size 33=============" + occupation_type_array.size());
        /*----------*/

        occupation_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                occupation_type_list_str = occupation_type_spinner.getItemAtPosition(position).toString();
                System.out.println(" 11 new occupation_type_list_str------" + occupation_type_list_str);


//                hideShowOccupation();


                if (!occupation_type_list_str.equals("Select Occupation Type")) {


                    if (occupation_type_flexLL.getVisibility() == View.GONE) {
                        occupation_type_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    occupation_type_txtv = new TextView(PartnerPreferencesActivity.this);
                    occupation_type_txtv.setLayoutParams(params);
                    occupation_type_txtv.setTextSize(16);
                    occupation_type_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));

                    occupation_type_txtv.setPadding(20, 10, 20, 10);
                    occupation_type_txtv.setLayoutParams(params);

                    if (!occupation_type_list_str.equals(occupation_type_txtv.getText().toString())) {


                        occupation_type_txtv.setText(occupation_type_list_str.trim() + "     x ");

                        occupation_type_array.add(occupation_type_list_str.trim());


                    }


                    occupation_type_flex.addView(occupation_type_txtv);


                    final String myString = occupation_type_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    occupation_type_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    occupation_type_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) occupation_type_txtv.getText();

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);


                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {

                            TextView textView1 = (TextView) textView;

                            String charSequence = textView1.getText().toString().trim();

                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());


//                            occupation_array.remove(charSequence);

                            for (int i = 0; i < occupation_type_array.size(); i++) {
                                System.out.println("array character occupation name----------" + occupation_type_array.get(i).trim());

                                if ((occupation_type_array.get(i).trim()).contains(a.trim())) {

                                    int id = occupation_type_array.indexOf(a.trim());
                                    System.out.println("11 array  occupation type id character name---------->>>" + id);

//                                    occupation_array.remove(charSequence);
                                    occupation_type_array.remove(id);
                                    occupation_type_array.remove(charSequence);
                                    textView1.setVisibility(View.GONE);
                                } else if (a.trim().contains("Doesn't Matter")) {
                                    occupation_type_array.remove(charSequence);
                                    textView1.setVisibility(View.GONE);
                                }

                            }

                            System.out.println("array occupation after delete=======" + occupation_type_array.size());
//                            hideShowoccupation("");


                            callNewCheckOccupation();




                            /*hide occupation selection if occupation type is more than 1*/

                        }
                    };

                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                    callNewCheckOccupation();


                }


                System.out.println("check occupation size------>>" + occupation_type_array.size());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callNewCheckMaritalStatus() {



        /*hide occupation selection if occupation type is more than 1*/

        if (marital_status_array.size() == 0) {

            maritalStatusPkgHandle();

        } else if (marital_status_array.size() > 1) {
            maritalStatusPkgHandle();
        } else {
            maritalStatusPkgHandleVisible();

            /*????????*/
            System.out.println("marital_str(marital_str)======" + marital_str);
            hideShowMaritalStatus(marital_str);

//                    getEducationList("");
            /*????????*/
        }
    }

    private void hideShowMaritalStatus(String value_str) {
        /*hide occupation selection if occupation type is more than 1*/
        if (marital_status_array.size() == 0) {
            marital_status_flex.removeAllViews();
            marital_status_array.clear();
            marital_status_flexLL.setVisibility(View.GONE);
        } else if (marital_status_array.size() > 1) {
            marital_status_flex.removeAllViews();
            marital_status_array.clear();
            marital_status_flexLL.setVisibility(View.GONE);
        } else {
            marital_status_flexLL.setVisibility(View.VISIBLE);
            marital_status_flex.removeAllViews();
            marital_status_array.clear();
            /*????????*/

            //   getMaritalStatusList(value_str);
            /*????????*/


        }
        /*hide occupation selection if occupation type is more than 1*/
    }


    private void callNewCheckOccupation() {



        /*hide occupation selection if occupation type is more than 1*/

        if (occupation_type_array.size() == 0) {

            occupationPkgHandle();

        } else if (occupation_type_array.size() > 1) {
            occupationPkgHandle();
        } else {
            occupationPkgHandleVisible();

            /*????????*/
            System.out.println("occupation_type_list_str(occupation_type_list_str)======" + occupation_type_list_str);
            hideShowOccupation(occupation_type_list_str);

//                    getEducationList("");
            /*????????*/
        }
    }

    private void hideShowOccupation(String value_str) {
        /*hide occupation selection if occupation type is more than 1*/
        if (occupation_type_array.size() == 0) {
            occupation_pckg_flex.removeAllViews();
            occupation_pck_array.clear();
            occupationFlexBothLL.setVisibility(View.GONE);
        } else if (occupation_type_array.size() > 1) {
            occupation_pckg_flex.removeAllViews();
            occupation_pck_array.clear();
            occupationFlexBothLL.setVisibility(View.GONE);
        } else {
            occupationFlexBothLL.setVisibility(View.VISIBLE);
            occupation_pckg_flex.removeAllViews();
            occupation_pck_array.clear();
            /*????????*/

            getOccupationList(value_str);
            /*????????*/


        }
        /*hide occupation selection if occupation type is more than 1*/
    }

    private void occupationPkgHandleVisible() {
        occupationPkgHandle();
    }

    public void occupationPkgHandle() {
        occupation_pckg_flex.removeAllViews();
        occupation_pck_array.clear();
        occupationFlexBothLL.setVisibility(View.GONE);
    }

    private void maritalStatusPkgHandleVisible() {
        maritalStatusPkgHandle();
    }

    public void maritalStatusPkgHandle() {
        marital_status_flex.removeAllViews();
        marital_status_array.clear();
        marital_status_flexLL.setVisibility(View.GONE);
    }

    public void getOccupationList(String occupation_type_list_str) {

        occupation_type_id = "";

        System.out.println("xxxyy occupation check 11----------" + occupation_type_list_str);

        if (!occupation_type_list_str.equals("")) {
            System.out.println("check 11----------" + occupation_type_array.size());

            for (int j = 0; j < occupation_type_array.size(); j++) {
                System.out.println("111 character name----------" + occupation_type_array.get(j));


                System.out.println("check 11----------" + occupationTypeLists.size());


                for (int i = 0; i < occupationTypeLists.size(); i++) {

                    System.out.println("xx check 11----------" + occupation_type_array.get(j));
                    System.out.println("xx check 11----------" + occupationTypeLists.get(i).getName());


                    if (occupation_type_array.get(j).trim().equals(occupationTypeLists.get(i).getName().trim())) {


                        occupation_type_id = String.valueOf(occupationTypeLists.get(i).getId());


                        System.out.println("occupation_type_id for occupation----------" + occupation_type_id);
                        System.out.println("occupation_type_id for occupation----------" + occupationTypeLists.get(i).getName());

                        break;

                    }
                }


            }

        }


        occupationListData.clear();
        occupation_pckg_flex.removeAllViews();
        occupation_pck_array.clear();


        occupation_list.clear();
//        occupation_spinner.setSelection(0);
        occupation_spinner.setAdapter(null);


        progressBar = ProgressDialog.show(PartnerPreferencesActivity.this, "", "Please Wait...");

        System.out.println("occupation_type_id-------->>>>>>" + occupation_type_id);

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService.occupationNew("27", occupation_type_id);
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                occupationList = response.body();
                progressBar.dismiss();

                System.out.println("occ resp===========" + new Gson().toJson(response.body()));

                if (response.isSuccessful()) {


                    String success = occupationList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < occupationList.getOccupationList().size(); i++) {
                            occupationListData.add(new OccupationList.OccupationListData(occupationList.getOccupationList().get(i).getId().toString(), occupationList.getOccupationList().get(i).getName()));

                        }
                        occupation_spinner_data(occupationListData);
                    } else {


                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationList> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err occupation******" + t.toString());

                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void occupation_spinner_data(List<OccupationList.OccupationListData> occupationListData) {
        // Clear existing data
        occupation_pck_array.clear();
        occupation_pck_array_occupation_name_id.clear();
        occupation_pckg_flex.removeAllViews();

        // Initialize spinner data
        occupation_list.clear();
        occupation_list.add("Select Occupation");
        occupation_list.add("Doesn't Matter");

        if (occupationListData != null && !occupationListData.isEmpty()) {
            for (int i = 0; i < occupationListData.size(); i++) {
                occupation_list.add(i + 2, occupationListData.get(i).getName().trim());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        adapter_occupation = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, occupation_list);
        adapter_occupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occupation_spinner.setAdapter(adapter_occupation);

        // Handle Spinner click to show searchable dialog
//        occupation_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableOccupationDialog(occupation_list, occupationListData);
//            }
//            return true; // Consume the touch event
//        });

        occupation_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openMultiOccupationDialog(occupation_list, occupationListData);
            }
            return true;
        });

        
//        occupation_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                openOccupationDialog(occupationListData);
//            }
//            return true;
//        });

    }

    private void openMultiOccupationDialog(
            List<String> occupationList,
            List<OccupationList.OccupationListData> occupationListData) {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("Occupation");
        container.removeAllViews();

        // ---------- PRESELECT ----------
        ArrayList<String> preSelected = new ArrayList<>();
        if (occupation_list_str != null && !occupation_list_str.isEmpty()) {
            String[] arr = occupation_list_str.split(",");
            for (String s : arr) preSelected.add(s.trim());
        }

        // ---------- CREATE CHECKBOXES ----------
        for (String occ : occupationList) {
            CheckBox cb = new CheckBox(this);
            cb.setText(occ);
            cb.setTextSize(16);

            if (preSelected.contains(occ)) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }

        // ---------- CANCEL ----------
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // ---------- OK ----------
        btnOk.setOnClickListener(v -> {

            // CLEAR OLD DATA
            occupation_pck_array.clear();
            occupation_pck_array_occupation_name_id.clear();
            occupation_pckg_flex.removeAllViews();

            // COLLECT CHECKED ITEMS
            for (int i = 0; i < container.getChildCount(); i++) {
                CheckBox cb = (CheckBox) container.getChildAt(i);
                if (!cb.isChecked()) continue;

                String occName = cb.getText().toString().trim();
                occupation_pck_array.add(occName);

                // FIND & STORE ID
                for (OccupationList.OccupationListData data : occupationListData) {
                    if (occName.equals(data.getName().trim())) {
                        occupation_pck_array_occupation_name_id.add(data.getId().toString());
                        break;
                    }
                }
            }

            // BUILD STRING (BACKEND USES THIS)
            occupation_list_str = TextUtils.join(", ", occupation_pck_array);

            // ---------- FLEXBOX UI ----------
            if (!occupation_pck_array.isEmpty()) {
                occupation_flexLL.setVisibility(View.VISIBLE);
            }

            for (String occ : occupation_pck_array) {

                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 5, 10, 5);

                TextView tv = new TextView(this);
                tv.setLayoutParams(params);
                tv.setTextSize(16);
                tv.setPadding(20, 10, 20, 10);
                tv.setBackgroundColor(Color.parseColor("#110A0A0A"));
                tv.setText(occ + "     x ");

                occupation_pckg_flex.addView(tv);
            }

            // UPDATE SPINNER TEXT (NO BACKEND CHANGE)
            ((TextView) occupation_spinner.getSelectedView())
                    .setText(occupation_list_str);

            dialog.dismiss();
        });

        dialog.show();
    }


//    private void openOccupationDialog(List<OccupationList.OccupationListData> occupationListData) {
//
//        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_salary_multi_select);
//
//        Window window = dialog.getWindow();
//        if (window != null) {
//            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        }
//
//        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
//        TextView title = dialog.findViewById(R.id.tv_title);
//        Button btnOk = dialog.findViewById(R.id.btn_ok);
//        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
//
//        title.setText("Select Occupation");
//
//        selectedOccupationList.clear();
//
//        // -----------------------------
//        // PRESELECT FROM API DATA
//        // -----------------------------
//
//        if (prefOccupation != null && !prefOccupation.isEmpty()) {
//
//            if (occupation_array.isEmpty()) {
//                String[] arr = prefOccupation.split(",");
//                for (String s : arr) {
//                    occupation_array.add(s.trim());
//                }
//            }
//
//            selectedOccupationList.addAll(occupation_array);
//        }
//
//        // -----------------------------
//        // CREATE CHECKBOXES
//        // -----------------------------
//        for (OccupationList.OccupationListData item : occupationListData) {
//
//            CheckBox cb = new CheckBox(this);
//            cb.setText(item.getName());
//            cb.setTextSize(16);
//
//            if (selectedOccupationList.contains(item.getName().trim())) {
//                cb.setChecked(true);
//            }
//
//            container.addView(cb);
//        }
//
//        btnCancel.setOnClickListener(v -> dialog.dismiss());
//
//        // -----------------------------
//        // OK CLICK
//        // -----------------------------
//        btnOk.setOnClickListener(v -> {
//
//            selectedOccupationList.clear();
//            occupation_array.clear();
//            occupation_array_ids.clear();
//            occupation_pckg_flex.removeAllViews();
//
//            for (int i = 0; i < container.getChildCount(); i++) {
//                View child = container.getChildAt(i);
//
//                if (child instanceof CheckBox) {
//                    CheckBox cb = (CheckBox) child;
//
//                    if (cb.isChecked()) {
//
//                        String name = cb.getText().toString();
//                        selectedOccupationList.add(name);
//                        occupation_array.add(name);
//
//                        for (OccupationList.OccupationListData data : occupationListData) {
//                            if (data.getName().equals(name)) {
//                                occupation_array_ids.add(data.getId().toString());
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//
//            occupation_list_str = TextUtils.join(", ", selectedOccupationList);
//
//            addOccupationTags();
//
//            dialog.dismiss();
//        });
//
//        dialog.show();
//    }

    private void addOccupationTags() {

        occupation_flexLL.setVisibility(View.VISIBLE);
        occupation_pckg_flex.removeAllViews();

        for (String item : occupation_array) {

            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 5, 10, 5);

            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(item + "  x");
            tv.setTextSize(16);
            tv.setPadding(20, 10, 20, 10);
            tv.setBackgroundColor(Color.parseColor("#110A0A0A"));

            tv.setOnClickListener(v -> {
                occupation_array.remove(item);

                for (OccupationList.OccupationListData data : OccupationList) {
                    if (data.getName().equals(item)) {
                        occupation_array_ids.remove(data.getId());
                        break;
                    }
                }

                addOccupationTags();
            });

            occupation_pckg_flex.addView(tv);
        }
    }



    private void showSearchableOccupationDialog(List<String> occupationList, List<OccupationList.OccupationListData> occupationListData) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back
        List<String> originalOccupationList = new ArrayList<>(occupationList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                occupationList
        );
        listView.setAdapter(dialogAdapter);

        // Filter list based on search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                // Get the selected item from the filtered list
                String selectedOccupation = dialogAdapter.getItem(position);
                if (selectedOccupation == null) {
                    Log.e("OccupationDialog", "Selected occupation is null at position: " + position);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting occupation. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list
                int originalPosition = -1;
                for (int i = 0; i < originalOccupationList.size(); i++) {
                    if (originalOccupationList.get(i).equals(selectedOccupation)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("OccupationDialog", "Could not find selected occupation in original list: " + selectedOccupation);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting occupation. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                occupation_list_str = selectedOccupation.trim();
                Log.d("OccupationDialog", "Selected occupation: " + occupation_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                occupation_spinner.setSelection(originalPosition);

                // Replicate the existing onItemSelected logic
                Log.d("OccupationDialog", "occupation_list_str------" + occupation_list_str);
                for (String item : occupation_pck_array) {
                    Log.d("OccupationDialog", "Current occupation_pck_array item: " + item);
                }

                // Check if occupation is already added
                status_occupation = "0";
                for (String item : occupation_pck_array) {
                    if (item.equals(occupation_list_str)) {
                        status_occupation = "1";
                        break;
                    }
                }

                if (status_occupation.equals("0") && !occupation_list_str.equals("Select Occupation")) {
                    if (occupation_flexLL.getVisibility() == View.GONE) {
                        occupation_flexLL.setVisibility(View.VISIBLE);
                    }

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(10, 5, 10, 5);

                    TextView textView = new TextView(PartnerPreferencesActivity.this);
                    textView.setLayoutParams(params);
                    textView.setTextSize(16);
                    textView.setBackgroundColor(Color.parseColor("#110A0A0A"));
                    textView.setPadding(20, 10, 20, 10);

                    // Avoid duplicates and ensure unique addition
                    if (!occupation_pck_array.contains(occupation_list_str)) {
                        textView.setText(occupation_list_str + "     x ");
                        occupation_pck_array.add(occupation_list_str);

                        // Add corresponding ID
                        for (OccupationList.OccupationListData data : occupationListData) {
                            if (occupation_list_str.equals(data.getName().trim())) {
                                occupation_pck_array_occupation_name_id.add(data.getId().toString());
                                break;
                            }
                        }

                        occupation_pckg_flex.addView(textView);

                        // Set up clickable 'x' for removal
                        String myString = textView.getText().toString();
                        int i1 = myString.indexOf("x ");
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                        textView.setText(myString, TextView.BufferType.SPANNABLE);
                        Spannable mySpannable = (Spannable) textView.getText();

                        ClickableSpan clickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                TextView tv = (TextView) widget;
                                String charSequence = tv.getText().toString();
                                String name = charSequence.substring(0, charSequence.length() - 2).trim();

                                Log.d("OccupationDialog", "Removing: " + name);

                                // Remove from arrays
                                int index = occupation_pck_array.indexOf(name);
                                if (index != -1) {
                                    occupation_pck_array.remove(index);
                                    for (OccupationList.OccupationListData data : occupationListData) {
                                        if (name.equals(data.getName().trim())) {
                                            occupation_pck_array_occupation_name_id.remove(data.getId());
                                            break;
                                        }
                                    }
                                }

                                tv.setVisibility(View.GONE);
                            }
                        };

                        mySpannable.setSpan(clickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } else if (status_occupation.equals("1")) {
                    Toast.makeText(PartnerPreferencesActivity.this, "Already Added!!!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("OccupationDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting occupation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


//    public void occupation_spinner_data(List<OccupationList.OccupationListData> occupationListData) {
//        /*-----*/
//
//
//        occupation_pck_array.clear();
//        occupation_pck_array_occupation_name_id.clear();
//
//
//
//        /*-----*/
//        occupation_list.clear();
//
//        occupation_pckg_flex.removeAllViews();
//        occupation_list.add("Select Occupation");
//        occupation_list.add("Doesn't Matter");
//
//        if (occupation_list.size() != 0) {
//            for (int i = 0; i < occupationListData.size(); i++) {
//
//                occupation_list.add(i + 2, occupationListData.get(i).getName().trim());
//
//            }
//        }
//        adapter_occupation = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, occupation_list);
//
//        occupation_spinner.setAdapter(adapter_occupation);
//
//
//        occupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                occupation_list_str = occupation_spinner.getItemAtPosition(position).toString().trim();
//                System.out.println("occupation_list_str------" + occupation_list_str);
//                for (int h = 0; h < occupation_pck_array.size(); h++) {
//                    System.out.println("11100.0.1.===========" + occupation_pck_array.get(h));
//                }
//
//
//                /*new cond*/
//                status_occupation = "0";
//
//                for (int x = 0; x < occupation_pck_array.size(); x++) {
//
//                    System.out.println("11 xx zz==============" + occupation_pck_array.get(x));
//                    System.out.println("22 xx zz==============" + occupation_list_str);
//
//                    if (occupation_pck_array.get(x).equals(occupation_list_str)) {
//                        status_occupation = "1";
//                    }
//                }
//
//                if (status_occupation.equals("0")) {
//                    if (occupation_pck_array.size() != 0) {
//                        for (int h = 0; h < occupation_pck_array.size(); h++) {
//
//                            if (!occupation_list_str.equals("Select Occupation") && !occupation_pck_array.contains(occupation_list_str.trim())) {
//
//                                if (occupation_flexLL.getVisibility() == View.GONE) {
//                                    occupation_flexLL.setVisibility(View.VISIBLE);
//                                }
//
//
//                                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                                params.setMargins(10, 5, 10, 5);
//
//
//                                textView2 = new TextView(PartnerPreferencesActivity.this);
//                                textView2.setLayoutParams(params);
//                                textView2.setTextSize(16);
//
//                                textView2.setBackgroundColor(Color.parseColor("#110A0A0A"));
//
//                                textView2.setPadding(20, 10, 20, 10);
//                                textView2.setLayoutParams(params);
//
//                                System.out.println("textView2.getText().toString().===========" + textView2.getText().toString());
////                            System.out.println("textView1.getText().toString().===========" + textView1.getText().toString());
//
//
//                                if (!occupation_list_str.trim().equals(textView2.getText().toString())) {
//
//                                    if (textView1 != null) {
//                                        if (!textView2.getText().toString().equals(textView1.getText().toString())) {
//
//                                            textView2.setText(occupation_list_str.trim() + "     x ");
//
//                                            occupation_pck_array.add(occupation_list_str.trim());
//
//                                            for (int x = 0; x < occupationListData.size(); x++) {
//                                                if (occupation_list_str.trim().equals(occupationListData.get(x).getName().trim())) {
//                                                    occupation_pck_array_occupation_name_id.add(occupationListData.get(x).getId());
//
//                                                }
//                                            }
//
//                                        }
//                                    } else {
//                                        textView2.setText(occupation_list_str.trim() + "     x ");
//
//                                        occupation_pck_array.add(occupation_list_str.trim());
//
//                                        for (int x = 0; x < occupationListData.size(); x++) {
//                                            if (occupation_list_str.equals(occupationListData.get(x).getName().trim())) {
//                                                occupation_pck_array_occupation_name_id.add(occupationListData.get(x).getId());
//
//                                            }
//                                        }
//
//                                    }
//
//
//                                }
//
//
//                                occupation_pckg_flex.addView(textView2);
//
//
//                                final String myString = textView2.getText().toString();
//                                int i1 = myString.indexOf("x ");
//                                textView2.setMovementMethod(LinkMovementMethod.getInstance());
//                                textView2.setText(myString, TextView.BufferType.SPANNABLE);
//                                Spannable mySpannable = (Spannable) textView2.getText();
//
//                                ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
//
//                                ClickableSpan myClickableSpan = new ClickableSpan() {
//                                    @Override
//                                    public void onClick(View textView) {
//
//                                        TextView textView1 = (TextView) textView;
//
//                                        String charSequence = textView1.getText().toString();
//
//                                        String a = charSequence.substring(0, charSequence.length() - 2);
//
//                                        System.out.println("character name----------" + a);
//                                        System.out.println("final character name----------" + a.trim());
//
//
////                                    occupation_pck_array.remove(charSequence);
//
//                                        for (int i = 0; i < occupation_pck_array.size(); i++) {
//                                            System.out.println("array character name---------->>>" + occupation_pck_array.get(i).trim());
//                                            System.out.println("array character name---------->>>" + a.trim());
//
//                                            if ((occupation_pck_array.get(i).trim()).contains(a.trim())) {
//
//                                                int id = occupation_pck_array.indexOf(a.trim());
//                                                System.out.println("remove id check----------" + id);
//
//                                                occupation_pck_array.remove(charSequence);
//                                                occupation_pck_array.remove(id);
//
//                                                /*delete occupation id from id array*/
//
//                                                for (int x = 0; x < occupationListData.size(); x++) {
//                                                    if (a.trim().equals(occupationListData.get(x).getName())) {
//                                                        String id_ofArray = occupationListData.get(x).getId();
//
//                                                        int id_ = occupation_pck_array_occupation_name_id.indexOf(id_ofArray);
//                                                        occupation_pck_array_occupation_name_id.remove(id_);
//
//                                                    }
//                                                }
//
//                                                /*delete occupation id from id array*/
//                                                textView1.setVisibility(View.GONE);
//
//                                            }
//
//                                        }
//
//                                    }
//                                };
//
//                                mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                            }
//
//                        }
//                    } else {
//
//                        if (!occupation_list_str.equals("Select Occupation")) {
//                            if (occupation_flexLL.getVisibility() == View.GONE) {
//                                occupation_flexLL.setVisibility(View.VISIBLE);
//                            }
//
//                            System.out.println("00.0.1.===========" + "11111111111111111");
//
//                            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                            params.setMargins(10, 5, 10, 5);
//
//
//                            textView3 = new TextView(PartnerPreferencesActivity.this);
//                            textView3.setLayoutParams(params);
//                            textView3.setTextSize(16);
//                            textView3.setBackgroundColor(Color.parseColor("#110A0A0A"));
//
//                            textView3.setPadding(20, 10, 20, 10);
//                            textView3.setLayoutParams(params);
//                            if (!occupation_list_str.trim().equals(textView3.getText().toString())) {
//
//
//                                textView3.setText(occupation_list_str.trim() + "     x ");
//
//                                occupation_pck_array.add(occupation_list_str.trim());
//
//                                for (int x = 0; x < occupationListData.size(); x++) {
//                                    if (occupation_list_str.equals(occupationListData.get(x).getName().trim())) {
//                                        occupation_pck_array_occupation_name_id.add(occupationListData.get(x).getId());
//
//                                    }
//                                }
//                            }
//
//
//                            occupation_pckg_flex.addView(textView3);
//
//
//                            final String myString = textView3.getText().toString();
//                            int i1 = myString.indexOf("x ");
//                            textView3.setMovementMethod(LinkMovementMethod.getInstance());
//                            textView3.setText(myString, TextView.BufferType.SPANNABLE);
//                            Spannable mySpannable = (Spannable) textView3.getText();
//
//                            ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
//
//                            ClickableSpan myClickableSpan = new ClickableSpan() {
//                                @Override
//                                public void onClick(View textView) {
//
//                                    TextView textView1 = (TextView) textView;
//
//                                    String charSequence = textView1.getText().toString();
//
//                                    String a = charSequence.substring(0, charSequence.length() - 2);
//
//                                    System.out.println("character name----------" + a);
//                                    System.out.println("final character name----------" + a.trim());
//
//
    ////                                occupation_pck_array.remove(charSequence);
//
//                                    for (int i = 0; i < occupation_pck_array.size(); i++) {
//                                        System.out.println("array character name----------" + occupation_pck_array.get(i));
//
//                                        if ((occupation_pck_array.get(i).trim()).contains(a.trim())) {
//
//                                            int id = occupation_pck_array.indexOf(a.trim());
//                                            System.out.println(" remove txt index----------" + id);
//
//                                            occupation_pck_array.remove(charSequence);
//                                            occupation_pck_array.remove(id);
//
//                                            /*delete occupation id from id array*/
//
//                                            for (int x = 0; x < occupationListData.size(); x++) {
//                                                if (a.trim().equals(occupationListData.get(x).getName())) {
//                                                    String id_ofArray = occupationListData.get(x).getId();
//
//                                                    int id_ = occupation_pck_array_occupation_name_id.indexOf(id_ofArray);
//                                                    occupation_pck_array_occupation_name_id.remove(id_);
//
//                                                }
//                                            }
//
//                                            /*delete occupation id from id array*/
//                                            textView1.setVisibility(View.GONE);
//                                        }
//
//                                    }
//
//                                }
//                            };
//
//                            mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                            mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                        }
//
//                    }
//                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Already Added!!!", Toast.LENGTH_SHORT).show();
//                }
//                /*new cond*/
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//    }
    /*end occupation type and occupation*/


    //----subCaste_spinner_data---------
    // new implementation for search bar casr 27-5-2025

    public void subCaste_spinner_data(List<SubCasteList> subCasteLists) {
        sub_caste_list.clear();
        sub_caste_list.add("Select Sub-Caste");
        sub_caste_list.add("Doesn't Matter");

        if (subCasteLists != null && !subCasteLists.isEmpty()) {
            for (int i = 0; i < subCasteLists.size(); i++) {
                sub_caste_list.add(i + 2, subCasteLists.get(i).getSc_name());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        sub_caste_adapter = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, sub_caste_list);
        sub_caste_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_caste_spinner.setAdapter(sub_caste_adapter);

        // Handle Spinner click to show searchable dialog
//        sub_caste_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableSubCasteDialog(sub_caste_list);
//            }
//            return true; // Consume the touch event
//        });
        
        
        sub_caste_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openMultiSubCasteDialog();
            }
            return true;
        });
        
        
    }

    private void openMultiSubCasteDialog() {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText(" Sub-Caste");

        selectedSubCasteList.clear();

        // ---------------- PRESELECT ----------------
        String prefSubCaste = fetchPartnerPref.getPref_subcaste_name();
        if (prefSubCaste != null && !prefSubCaste.isEmpty()) {
            sub_caste_array.clear();
            String[] arr = prefSubCaste.split(",");
            for (String s : arr) {
                sub_caste_array.add(s.trim());
            }
            selectedSubCasteList.addAll(sub_caste_array);
        }

        // ---------------- ADD SPECIAL OPTIONS ----------------
        if (!sub_caste_list.contains("Doesn't Matter")) {
            sub_caste_list.add(0, "Doesn't Matter");
        }

        if (!sub_caste_list.contains("Select All")) {
            sub_caste_list.add(1, "Select All");
        }

        CheckBox dmCheckBox = null;
        CheckBox selectAllCheckBox = null;

        // ---------------- CREATE CHECKBOXES ----------------
        for (String name : sub_caste_list) {

            CheckBox cb = new CheckBox(this);
            cb.setText(name);
            cb.setTextSize(16);

            if (selectedSubCasteList.contains(name)) {
                cb.setChecked(true);
            }

            if (name.equals("Doesn't Matter")) {
                dmCheckBox = cb;
            } else if (name.equals("Select All")) {
                selectAllCheckBox = cb;
            }

            container.addView(cb);
        }

        CheckBox finalDm = dmCheckBox;
        CheckBox finalSelectAll = selectAllCheckBox;

        // ---------------- CHECKBOX LOGIC ----------------
        for (int i = 0; i < container.getChildCount(); i++) {

            View v = container.getChildAt(i);
            if (!(v instanceof CheckBox)) continue;

            CheckBox cb = (CheckBox) v;

            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (!isChecked) return;

                String text = cb.getText().toString();

                // DOESN'T MATTER
                if (text.equals("Doesn't Matter")) {
                    for (int j = 0; j < container.getChildCount(); j++) {
                        CheckBox c = (CheckBox) container.getChildAt(j);
                        if (c != finalDm) c.setChecked(false);
                    }
                }

                // SELECT ALL
                else if (text.equals("Select All")) {
                    if (finalDm != null) finalDm.setChecked(false);

                    for (int j = 0; j < container.getChildCount(); j++) {
                        CheckBox c = (CheckBox) container.getChildAt(j);
                        String t = c.getText().toString();

                        if (!t.equals("Doesn't Matter") && !t.equals("Select All")) {
                            c.setChecked(true);
                        }
                    }
                }

                // NORMAL ITEM
                else {
                    if (finalDm != null) finalDm.setChecked(false);
                    if (finalSelectAll != null) finalSelectAll.setChecked(false);
                }
            });
        }

        // ---------------- BUTTONS ----------------
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnOk.setOnClickListener(v -> {

            selectedSubCasteList.clear();
            sub_caste_array.clear();

            boolean isDM = false;

            for (int i = 0; i < container.getChildCount(); i++) {
                CheckBox cb = (CheckBox) container.getChildAt(i);

                if (cb.isChecked()) {
                    String text = cb.getText().toString();

                    if (text.equals("Doesn't Matter")) {
                        isDM = true;
                        break;
                    }

                    if (!text.equals("Select All")) {
                        selectedSubCasteList.add(text);
                        sub_caste_array.add(text);
                    }
                }
            }

            if (isDM) {
                sub_caste_str = "Doesn't Matter";
                subcaste_id = "Doesn't Matter";
            } else {
                sub_caste_str = TextUtils.join(", ", selectedSubCasteList);
            }

            ((TextView) sub_caste_spinner.getSelectedView()).setText(sub_caste_str);
            dialog.dismiss();
        });

        dialog.show();
    }


    // Method to show a searchable dialog for sub-caste selection
    private void showSearchableSubCasteDialog(List<String> subCasteList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalSubCasteList = new ArrayList<>(subCasteList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                subCasteList
        );
        listView.setAdapter(dialogAdapter);

        // Filter list based on search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                // Get the selected item from the filtered list
                String selectedSubCaste = dialogAdapter.getItem(position);
                if (selectedSubCaste == null) {
                    Log.e("SubCasteDialog", "Selected sub-caste is null at position: " + position);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting sub-caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list to get the correct position
                int originalPosition = -1;
                for (int i = 0; i < originalSubCasteList.size(); i++) {
                    if (originalSubCasteList.get(i).equals(selectedSubCaste)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("SubCasteDialog", "Could not find selected sub-caste in original list: " + selectedSubCaste);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting sub-caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }


//                if (sub_caste_str == null){
//                    sub_caste_str=getSubCastId;
//                    subcaste_id =getSubCastId;
//                    subcaste_pckg_list=getSubCastId;
//                } else {
                sub_caste_str = selectedSubCaste;
//                }
                Log.d("SubCasteDialog", "Selected sub-caste: " + sub_caste_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                sub_caste_spinner.setSelection(originalPosition);

                // Replicate the existing onItemSelected logic
                if (!sub_caste_str.equals("Select Sub-Caste")) {
                    if (subcaste_flexLL.getVisibility() == View.GONE) {
                        subcaste_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);

                    subcaste_txtv = new TextView(PartnerPreferencesActivity.this);
                    subcaste_txtv.setLayoutParams(params);
                    subcaste_txtv.setTextSize(16);
                    subcaste_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
                    subcaste_txtv.setPadding(20, 10, 20, 10);
                    subcaste_txtv.setLayoutParams(params);

                    // Check if the sub-caste is already added to avoid duplicates
                    boolean isDuplicate = false;
                    for (int i = 0; i < sub_caste_array.size(); i++) {
                        if (sub_caste_array.get(i).equals(sub_caste_str.trim())) {
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (!isDuplicate) {
                        subcaste_txtv.setText(sub_caste_str.trim() + "     x ");
                        sub_caste_array.add(sub_caste_str.trim());
                    } else {
                        Toast.makeText(PartnerPreferencesActivity.this, "Sub-caste already added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }

                    subcaste_flex.addView(subcaste_txtv);

                    final String myString = subcaste_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    subcaste_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    subcaste_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) subcaste_txtv.getText();

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            TextView textView1 = (TextView) textView;
                            String charSequence = textView1.getText().toString().trim();
                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());

                            // Remove from array and UI
                            for (int i = 0; i < sub_caste_array.size(); i++) {
                                if (sub_caste_array.get(i).trim().equals(a.trim())) {
                                    sub_caste_array.remove(i);
                                    textView1.setVisibility(View.GONE);
                                    break;
                                }
                            }
                        }
                    };

                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("SubCasteDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting sub-caste: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
    // commented by tanmay on 27-5-2025
//    public void subCaste_spinner_data(List<SubCasteList> subCasteLists) {
//
//        sub_caste_list.clear();
//
//        sub_caste_list.add("Select Sub-Caste");
//        sub_caste_list.add("Doesn't Matter");
//
//        if (sub_caste_list.size() != 0) {
//            for (int i = 0; i < subCasteLists.size(); i++) {
//
//                sub_caste_list.add(i + 2, subCasteLists.get(i).getSc_name());
//
//            }
//        }
//        sub_caste_adapter = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, sub_caste_list);
//
//        sub_caste_spinner.setAdapter(sub_caste_adapter);
//
//
//        sub_caste_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                sub_caste_str = sub_caste_spinner.getItemAtPosition(position).toString();
//                System.out.println("sub_caste_str------" + sub_caste_str);
//
//                if (!sub_caste_str.equals("Select Sub-Caste")) {
//                    if (subcaste_flexLL.getVisibility() == View.GONE) {
//                        subcaste_flexLL.setVisibility(View.VISIBLE);
//                    }
//
//                    System.out.println("00.0.1.===========" + "11111111111111111");
//
//                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(10, 5, 10, 5);
//
//
//                    subcaste_txtv = new TextView(PartnerPreferencesActivity.this);
//                    subcaste_txtv.setLayoutParams(params);
//                    subcaste_txtv.setTextSize(16);
//                    subcaste_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
//
//                    subcaste_txtv.setPadding(20, 10, 20, 10);
//                    subcaste_txtv.setLayoutParams(params);
//                    if (!sub_caste_str.equals(subcaste_txtv.getText().toString())) {
//
//
//                        subcaste_txtv.setText(sub_caste_str.trim() + "     x ");
//
//                        sub_caste_array.add(sub_caste_str.trim());
//
//
//                    }
//
//
//                    subcaste_flex.addView(subcaste_txtv);
//
//
//                    final String myString = subcaste_txtv.getText().toString();
//                    int i1 = myString.indexOf("x ");
//                    subcaste_txtv.setMovementMethod(LinkMovementMethod.getInstance());
//                    subcaste_txtv.setText(myString, TextView.BufferType.SPANNABLE);
//                    Spannable mySpannable = (Spannable) subcaste_txtv.getText();
//
//                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
//
//                    ClickableSpan myClickableSpan = new ClickableSpan() {
//                        @Override
//                        public void onClick(View textView) {
//
//                            TextView textView1 = (TextView) textView;
//
//                            String charSequence = textView1.getText().toString().trim();
//
//                            String a = charSequence.substring(0, charSequence.length() - 2);
//
//                            System.out.println("character name----------" + a);
//                            System.out.println("final character name----------" + a.trim());
//
//
//                            sub_caste_array.remove(charSequence);
//
//                            for (int i = 0; i < sub_caste_array.size(); i++) {
//                                System.out.println("array character name----------" + sub_caste_array.get(i));
//
//                                if ((sub_caste_array.get(i).trim()).contains(a.trim())) {
//
//                                    int id = sub_caste_array.indexOf(a.trim());
//                                    System.out.println(" 11 array character name----------" + id);
//
//                                    sub_caste_array.remove(id);
//                                    textView1.setVisibility(View.GONE);
//                                }
//
//                            }
//
//                        }
//                    };
//
//                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
    //------end subCaste_spinner_data-------


    //----dietary_spinner_data---------
    public void dietary_spinner_data(List<DietaryList.DietaryListData> dietaryListData) {

        dietray_list.clear();

        dietray_list.add("Select Dietary");
        dietray_list.add("Doesn't Matter");

        if (dietray_list.size() != 0) {
            for (int i = 0; i < dietaryListData.size(); i++) {

                dietray_list.add(i + 2, dietaryListData.get(i).getName());

            }
        }
        adapter_dietray = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, dietray_list);

        dietary_spinner.setAdapter(adapter_dietray);
        int pos;
/*
        for (int i = 0; i < dietray_list.size(); i++) {
            if (dietray_list.get(i).contains(fetchPartnerPref.getPref_diet_name())) {
                pos = i;
                dietary_spinner.setSelection(pos);
            }
        }
*/
        dietary_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openDietaryDialog();
            }
            return true;
        });

//        dietary_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                dietray_str = dietary_spinner.getItemAtPosition(position).toString();
//                System.out.println("dietray_str------" + dietray_str);
//
//                if (!dietray_str.equals("Select Dietary")) {
//                    if (dietary_flexLL.getVisibility() == View.GONE) {
//                        dietary_flexLL.setVisibility(View.VISIBLE);
//                    }
//
//                    System.out.println("00.0.1.===========" + "11111111111111111");
//
//                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(10, 5, 10, 5);
//
//
//                    dietary_txtv = new TextView(PartnerPreferencesActivity.this);
//                    dietary_txtv.setLayoutParams(params);
//                    dietary_txtv.setTextSize(16);
//                    dietary_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
//
//                    dietary_txtv.setPadding(20, 10, 20, 10);
//                    dietary_txtv.setLayoutParams(params);
//                    if (!dietray_str.equals(dietary_txtv.getText().toString())) {
//
//
//                        dietary_txtv.setText(dietray_str.trim() + "     x ");
//
//                        dietary_array.add(dietray_str.trim());
//
//
//                    }
//
//
//                    dietary_flex.addView(dietary_txtv);
//
//
//                    final String myString = dietary_txtv.getText().toString();
//                    int i1 = myString.indexOf("x ");
//                    dietary_txtv.setMovementMethod(LinkMovementMethod.getInstance());
//                    dietary_txtv.setText(myString, TextView.BufferType.SPANNABLE);
//                    Spannable mySpannable = (Spannable) dietary_txtv.getText();
//
//                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
//
//                    ClickableSpan myClickableSpan = new ClickableSpan() {
//                        @Override
//                        public void onClick(View textView) {
//
//                            TextView textView1 = (TextView) textView;
//
//                            String charSequence = textView1.getText().toString().trim();
//
//                            String a = charSequence.substring(0, charSequence.length() - 2);
//
//                            System.out.println("character name----------" + a);
//                            System.out.println("final character name----------" + a.trim());
//
//
//                            dietary_array.remove(charSequence);
//
//                            for (int i = 0; i < dietary_array.size(); i++) {
//                                System.out.println("array character name----------" + dietary_array.get(i));
//
//                                if ((dietary_array.get(i).trim()).contains(a.trim())) {
//
//                                    int id = dietary_array.indexOf(a.trim());
//                                    System.out.println(" 11 array character name----------" + id);
//
//                                    dietary_array.remove(id);
//                                    textView1.setVisibility(View.GONE);
//                                }
//
//                            }
//
//                        }
//                    };
//
//                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void openDietaryDialog() {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        // ---------- PRESELECT DATA ----------
        selectedDietaryList.clear();
        title.setText("Select Dietary");

        String prefDietary = fetchPartnerPref.getPref_diet_name();  // Example: "Veg, Non-Veg, Vegan"
        if (prefDietary != null && !prefDietary.isEmpty()) {
            String[] arr = prefDietary.split(",");
            for (String p : arr) {
                selectedDietaryList.add(p.trim());
            }
        }

        // ---------- DYNAMIC CHECKBOX CREATION ----------
        container.removeAllViews();

//        for (DietaryList.DietaryListData item : dietaryLists) {
//            CheckBox cb = new CheckBox(this);
//            cb.setText(item.getName());
//            cb.setTextSize(16);
//
//            if (selectedDietaryList.contains(item.getName().trim())) {
//                cb.setChecked(true);
//            }
//
//            container.addView(cb);
//        }
        for (DietaryList.DietaryListData item : dietaryLists) {

            CheckBox cb = new CheckBox(this);
            cb.setText(item.getName());
            cb.setTextSize(16);

            // 👉 Pre-select using ID match
            if (selectedDietaryIdList.contains(String.valueOf(item.getId()))) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }


        // -------- CANCEL BUTTON --------
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // -------- OK BUTTON --------
        btnOk.setOnClickListener(v -> {

            // Clear previous values
            selectedDietaryList.clear();
            selectedDietaryIdList.clear();
            dietary_array.clear();
            dietary_flex.removeAllViews();

            StringBuilder idBuilder = new StringBuilder();

            // Loop through checkboxes
            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);

                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;

                    if (cb.isChecked()) {

                        String selectedName = cb.getText().toString();
                        selectedDietaryList.add(selectedName);
                        dietary_array.add(selectedName);

                        // Find ID of selected name
                        for (DietaryList.DietaryListData item : dietaryLists) {
                            if (item.getName().trim().equals(selectedName.trim())) {

                                selectedDietaryIdList.add(item.getId());
                                idBuilder.append(item.getId()).append(",");
                                break;
                            }
                        }
                    }
                }
            }

            // Final ID string
            if (idBuilder.length() > 0) {
                getDietary_id = idBuilder.substring(0, idBuilder.length() - 1);  // remove last comma
            } else {
                getDietary_id = "";   // nothing selected
            }

            // Convert selected names to comma string
            dietary_str = TextUtils.join(", ", selectedDietaryList);

            // Update spinner display text
            ((TextView) dietary_spinner.getSelectedView()).setText(dietary_str);

            // Regenerate tags in flexbox
            addDietaryTags();

            dialog.dismiss();
        });

        dialog.show();
    }

    private void addDietaryTags() {

        dietary_flex.removeAllViews();

        for (String item : dietary_array) {

            FlexboxLayout.LayoutParams params =
                    new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(10, 5, 10, 5);

            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setTextSize(16);
            tv.setPadding(20, 10, 20, 10);
            tv.setBackgroundColor(Color.parseColor("#110A0A0A"));

            tv.setText(item + "   x");

            int removeIndexStart = tv.getText().toString().indexOf("x");

            tv.setMovementMethod(LinkMovementMethod.getInstance());
            tv.setText(tv.getText(), TextView.BufferType.SPANNABLE);

            Spannable span = (Spannable) tv.getText();

            span.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    dietary_array.remove(item);
                    addDietaryTags();   // Refresh UI
                }
            }, removeIndexStart, removeIndexStart + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            dietary_flex.addView(tv);
        }
    }


    //-----end dietary_spinner_data--------

    public void getCountryList() {
        countryListData.clear();
        Api apiService = RetrofitClient.getApiService();
        Call<CountryList> userResponse = apiService.countryList("07");
        userResponse.enqueue(new Callback<CountryList>() {
            @Override
            public void onResponse(Call<CountryList> call, Response<CountryList> response) {
                countryList = response.body();
                if (response.isSuccessful()) {
                    String success = countryList.getResid();
                    if (success.equals("200")) {
                        for (int i = 0; i < countryList.getCountryList().size(); i++) {
                            countryListData.add(new CountryList.CountryListData(countryList.getCountryList().get(i).getId(), countryList.getCountryList().get(i).getName(), countryList.getCountryList().get(i).getStateList()));
                        }
                        country_spinner_data(countryListData);
                    } else {
                    }
                }
            }
            @Override
            public void onFailure(Call<CountryList> call, Throwable t) {
                System.out.println("err country******" + t.toString());
                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void country_spinner_data(List<CountryList.CountryListData> countryListData) {
        country_list.clear();
        country_list.add("Select Country");
        country_list.add("Doesn't Matter");
        if (country_list.size() != 0) {
            for (int i = 0; i < countryListData.size(); i++) {
                country_list.add(i + 2, countryListData.get(i).getName());
            }
        }


        adapter_country = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, country_list);

        current_country_spinner.setAdapter(adapter_country);


        int pos;
        for (int i = 0; i < country_list.size(); i++) {
            if (country_list.get(i).contains(fetchPartnerPref.getPref_country_name())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                current_country_spinner.setSelection(pos);
            }
        }


        current_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                country_list_str = current_country_spinner.getItemAtPosition(position).toString();
                System.out.println("city_list_str------" + country_list_str);

                for_state_spinner_data(country_list_str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void for_state_spinner_data(String country_list_str) {
        stateListsData.clear();
        /*---------*/

        if (country_list_str != null) {
            for (int i = 0; i < countryListData.size(); i++) {
                if (country_list_str.equals(countryListData.get(i).getName())) {
                    country_id = String.valueOf(countryListData.get(i).getId());


                }
            }
        }

        for (int i = 0; i < countryListData.size(); i++) {
            if (countryListData.get(i).getStateList() != null) {

                for (int j = 0; j < countryListData.get(i).getStateList().size(); j++) {

                    stateList = countryListData.get(i).getStateList().get(j);

                    if (country_id != null) {

                        if (country_id.contentEquals(stateList.getC_id())) {
                            stateListsData.add(new StateList(stateList.getC_id(), stateList.getSc_id(), stateList.getSc_name()));
                        }

                    }


                }
                state_spinner_data(stateListsData);
            }
        }
    }
    public void state_spinner_data(List<StateList> stateLists) {
        state_list.clear();
        state_list.add("Select State");
        state_list.add("Doesn't Matter");

        if (stateLists != null && !stateLists.isEmpty()) {
            for (int i = 0; i < stateLists.size(); i++) {
                state_list.add(i + 2, stateLists.get(i).getSc_name());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        adapter_state = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, state_list);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_state_spinner.setAdapter(adapter_state);

        // Set default selection if pref_state_name exists
        if (fetchPartnerPref.getPref_state_name() != null && !fetchPartnerPref.getPref_state_name().isEmpty()) {
            for (int i = 0; i < state_list.size(); i++) {
                if (state_list.get(i).equals(fetchPartnerPref.getPref_state_name())) {
                    current_state_spinner.setSelection(i);
                    System.out.println("state i-----------" + i);
                    break;
                }
            }
        }

        // Handle Spinner click to show searchable dialog
//        current_state_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableStateDialog(state_list);
//            }
//            return true; // Consume the touch event
//        });

        current_state_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openStateMultiSelectDialog();
            }
            return true;
        });


    }

    private void openStateMultiSelectDialog() {
        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        TextView tv_title= dialog.findViewById(R.id.tv_title);
        tv_title.setText("Select States");
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        selectedStateList.clear();

        // Create checkboxes for each state
        for (StateList s : stateListsData) {
            CheckBox cb = new CheckBox(this);
            cb.setText(s.getSc_name());

            if (selectedStateList.contains(s.getSc_name())) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

//        btnOk.setOnClickListener(v -> {
//            selectedStateList.clear();
//            for (int i = 0; i < container.getChildCount(); i++) {
//                View child = container.getChildAt(i);
//                if (child instanceof CheckBox) {
//                    CheckBox cb = (CheckBox) child;
//                    if (cb.isChecked()) {
//                        selectedStateList.add(cb.getText().toString());
//
//                    }
//
//                }
//
//            }
//
//            // Show selected states in spinner
//            String selectedStatesStr = TextUtils.join(", ", selectedStateList);
//            ((TextView) current_state_spinner.getSelectedView()).setText(selectedStatesStr);
//
//            // ✅ Load districts for multiple selected states
//            getDistrictListForMultipleStates(selectedStateList);
//
//            dialog.dismiss();
//        });

        btnOk.setOnClickListener(v -> {
            selectedStateList.clear();

            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;
                    if (cb.isChecked()) {
                        selectedStateList.add(cb.getText().toString());
                    }
                }
            }

            // Store in required variable (same as old code)
            state_list_str = TextUtils.join(",", selectedStateList);
            Log.d("StateMultiSelect", "Selected states: " + state_list_str);

            // Update spinner text properly (multi-select safe)
            ArrayAdapter<String> adapter =
                    (ArrayAdapter<String>) current_state_spinner.getAdapter();

            adapter.clear();
            adapter.add(state_list_str);
            adapter.notifyDataSetChanged();

            // Load districts from multiple states
            getDistrictListForMultipleStates(selectedStateList);

            dialog.dismiss();
        });


        dialog.show();
    }

    private void getDistrictListForMultipleStates(List<String> selectedStates) {
        districtListData.clear();

        List<String> stateIds = new ArrayList<>();
        for (StateList s : stateListsData) {
            if (selectedStates.contains(s.getSc_name())) {
                stateIds.add(s.getSc_id());
            }
        }

        for (String stateId : stateIds) {
            Api apiService = RetrofitClient.getApiService();
            Call<DistrictList> call = apiService.districtList("13", stateId);
            call.enqueue(new Callback<DistrictList>() {
                @Override
                public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getResid().equals("200")) {
                        districtListData.addAll(response.body().getDistrictList());
                        district_spinner_data(districtListData); // update UI
                    }
                }

                @Override
                public void onFailure(Call<DistrictList> call, Throwable t) {
                    Log.e("DistrictMulti", t.toString());
                }
            });
        }
    }



    // Method to show a searchable dialog for state selection
    private void showSearchableStateDialog(List<String> stateList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalStateList = new ArrayList<>(stateList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                stateList
        );
        listView.setAdapter(dialogAdapter);

        // Filter list based on search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                // Get the selected item from the filtered list
                String selectedState = dialogAdapter.getItem(position);
                if (selectedState == null) {
                    Log.e("StateDialog", "Selected state is null at position: " + position);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list to get the correct position
                int originalPosition = -1;
                for (int i = 0; i < originalStateList.size(); i++) {
                    if (originalStateList.get(i).equals(selectedState)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("StateDialog", "Could not find selected state in original list: " + selectedState);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                state_list_str = selectedState;
                Log.d("StateDialog", "Selected state: " + state_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                current_state_spinner.setSelection(originalPosition);

                // Call existing method to handle district updates
                System.out.println("state_list_str------" + state_list_str);
                getDistrictList(state_list_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("StateDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting state: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    public void getDistrictList(String state_list_str) {
        if (state_list_str != null) {
            for (int i = 0; i < stateListsData.size(); i++) {
                if (state_list_str.equals(stateListsData.get(i).getSc_name())) {
                    state_id = String.valueOf(stateListsData.get(i).getSc_id());

                }
            }
        }

        System.out.println("state_id--------" + state_id);

        districtListData.clear();

        // prog_district.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<DistrictList> userResponse = apiService.districtList("13", state_id);
        userResponse.enqueue(new Callback<DistrictList>() {

            @Override
            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
                districtList = response.body();

                if (response.isSuccessful()) {


                    //  prog_district.setVisibility(View.GONE);

                    String success = districtList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < districtList.getDistrictList().size(); i++) {
                            districtListData.add(new DistrictList.DistrictListData(districtList.getDistrictList().get(i).getId(), districtList.getDistrictList().get(i).getName(), districtList.getDistrictList().get(i).getState_id()));
                        }
                        district_spinner_data(districtListData);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<DistrictList> call, Throwable t) {
                // prog_district.(View.GONE);
                System.out.println("err district******" + t.toString());
                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void district_spinner_data(List<DistrictList.DistrictListData> districtListData) {
        district_list.clear();
        district_list.add("Select District");
        district_list.add("Doesn't Matter");

        if (districtListData != null && !districtListData.isEmpty()) {
            for (int i = 0; i < districtListData.size(); i++) {
                district_list.add(i + 2, districtListData.get(i).getName().trim());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        district_adapter = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, district_list);
        district_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_district_spinner.setAdapter(district_adapter);

        // Uncomment the following block if you want to enable pre-selection
    /*
    if (fetchPartnerPref.getPref_dist_name() != null && !fetchPartnerPref.getPref_dist_name().isEmpty()) {
        for (int i = 0; i < district_list.size(); i++) {
            if (district_list.get(i).equals(fetchPartnerPref.getPref_dist_name())) {
                current_district_spinner.setSelection(i);
                System.out.println("state i-----------" + i);
                break;
            }
        }
    }
    */

        // Handle Spinner click to show searchable dialog
//        current_district_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableDistrictDialog(district_list);
//            }
//            return true; // Consume the touch event
//        });


        current_district_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openDistrictMultiSelectDialog();
            }
            return true;
        });


    }

    private void openDistrictMultiSelectDialog() {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("Select District");

        selectedDistrictList.clear();

        // ---------------- PRESELECT ----------------
        String prefDistrict = fetchPartnerPref.getPref_dist_name();
        if (prefDistrict != null && !prefDistrict.isEmpty()) {
            district_array.clear();
            String[] arr = prefDistrict.split(",");
            for (String s : arr) {
                district_array.add(s.trim());
            }
            selectedDistrictList.addAll(district_array);
        }

        // ---------------- ADD SPECIAL OPTIONS ----------------
        List<String> districtNames = new ArrayList<>();
        districtNames.add("Doesn't Matter");
        districtNames.add("Select All");

        for (DistrictList.DistrictListData item : districtListData) {
            districtNames.add(item.getName());
        }

        CheckBox dmCheckBox = null;
        CheckBox selectAllCheckBox = null;

        // ---------------- CREATE CHECKBOXES ----------------
        for (String name : districtNames) {

            CheckBox cb = new CheckBox(this);
            cb.setText(name);
            cb.setTextSize(16);

            if (selectedDistrictList.contains(name)) {
                cb.setChecked(true);
            }

            if (name.equals("Doesn't Matter")) {
                dmCheckBox = cb;
            } else if (name.equals("Select All")) {
                selectAllCheckBox = cb;
            }

            container.addView(cb);
        }

        CheckBox finalDm = dmCheckBox;
        CheckBox finalSelectAll = selectAllCheckBox;

        // ---------------- CHECKBOX LOGIC ----------------
        for (int i = 0; i < container.getChildCount(); i++) {

            View v = container.getChildAt(i);
            if (!(v instanceof CheckBox)) continue;

            CheckBox cb = (CheckBox) v;

            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (!isChecked) return;

                String text = cb.getText().toString();

                // DOESN'T MATTER
                if (text.equals("Doesn't Matter")) {
                    for (int j = 0; j < container.getChildCount(); j++) {
                        CheckBox c = (CheckBox) container.getChildAt(j);
                        if (c != finalDm) c.setChecked(false);
                    }
                }

                // SELECT ALL
                else if (text.equals("Select All")) {
                    if (finalDm != null) finalDm.setChecked(false);

                    for (int j = 0; j < container.getChildCount(); j++) {
                        CheckBox c = (CheckBox) container.getChildAt(j);
                        String t = c.getText().toString();

                        if (!t.equals("Doesn't Matter") && !t.equals("Select All")) {
                            c.setChecked(true);
                        }
                    }
                }

                // NORMAL DISTRICT
                else {
                    if (finalDm != null) finalDm.setChecked(false);
                    if (finalSelectAll != null) finalSelectAll.setChecked(false);
                }
            });
        }

        // ---------------- BUTTONS ----------------
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnOk.setOnClickListener(v -> {

            selectedDistrictList.clear();
            district_array.clear();
            district_flex.removeAllViews();

            boolean isDM = false;

            for (int i = 0; i < container.getChildCount(); i++) {
                CheckBox cb = (CheckBox) container.getChildAt(i);

                if (cb.isChecked()) {
                    String text = cb.getText().toString();

                    if (text.equals("Doesn't Matter")) {
                        isDM = true;
                        break;
                    }

                    if (!text.equals("Select All")) {
                        selectedDistrictList.add(text);
                        district_array.add(text);
                    }
                }
            }

            if (isDM) {
                district_str = "Doesn't Matter";
            } else {
                district_str = TextUtils.join(", ", selectedDistrictList);
            }

            ((TextView) current_district_spinner.getSelectedView()).setText(district_str);
            dialog.dismiss();
        });

        dialog.show();
    }


    // Method to show a searchable dialog for district selection
    private void showSearchableDistrictDialog(List<String> districtList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalDistrictList = new ArrayList<>(districtList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                districtList
        );
        listView.setAdapter(dialogAdapter);

        // Filter list based on search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                // Get the selected item from the filtered list
                String selectedDistrict = dialogAdapter.getItem(position);
                if (selectedDistrict == null) {
                    Log.e("DistrictDialog", "Selected district is null at position: " + position);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list to get the correct position
                int originalPosition = -1;
                for (int i = 0; i < originalDistrictList.size(); i++) {
                    if (originalDistrictList.get(i).equals(selectedDistrict)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("DistrictDialog", "Could not find selected district in original list: " + selectedDistrict);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                district_str = selectedDistrict;
                Log.d("DistrictDialog", "Selected district: " + district_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                current_district_spinner.setSelection(originalPosition);

                // Replicate the existing onItemSelected logic
                System.out.println("district_str------" + district_str);

                if (!district_str.equals("Select District")) {
                    if (district_flexLL.getVisibility() == View.GONE) {
                        district_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);

                    district_txtv = new TextView(PartnerPreferencesActivity.this);
                    district_txtv.setLayoutParams(params);
                    district_txtv.setTextSize(16);
                    district_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
                    district_txtv.setPadding(20, 10, 20, 10);
                    district_txtv.setLayoutParams(params);

                    // Check if the district is already added to avoid duplicates
                    boolean isDuplicate = false;
                    for (int i = 0; i < district_array.size(); i++) {
                        if (district_array.get(i).equals(district_str.trim())) {
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (!isDuplicate) {
                        district_txtv.setText(district_str.trim() + "     x ");
                        district_array.add(district_str.trim());
                    } else {
                        Toast.makeText(PartnerPreferencesActivity.this, "District already added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }

                    district_flex.addView(district_txtv);

                    final String myString = district_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    district_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    district_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) district_txtv.getText();

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            TextView textView1 = (TextView) textView;
                            String charSequence = textView1.getText().toString().trim();
                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());

                            // Remove from array and UI
                            for (int i = 0; i < district_array.size(); i++) {
                                if (district_array.get(i).trim().equals(a.trim())) {
                                    district_array.remove(i);
                                    textView1.setVisibility(View.GONE);
                                    break;
                                }
                            }
                        }
                    };

                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("DistrictDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    private void getMotherToungeList() {


        mothertoungeListData.clear();
        mothertounge_array.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<MothertoungeList> userResponse = apiService.motherTounge("08");
        userResponse.enqueue(new Callback<MothertoungeList>() {

            @Override
            public void onResponse(Call<MothertoungeList> call, Response<MothertoungeList> response) {
                mothertoungeList = response.body();

                if (response.isSuccessful()) {


                    String success = mothertoungeList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < mothertoungeList.getMothertoungeList().size(); i++) {
                            mothertoungeListData.add(new MothertoungeList.MothertoungeListData(mothertoungeList.getMothertoungeList().get(i).getId(), mothertoungeList.getMothertoungeList().get(i).getName()));
                        }
                        mother_tongue_spinner_data(mothertoungeListData);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<MothertoungeList> call, Throwable t) {

                System.out.println("err mother tongue******" + t.toString());
                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void mother_tongue_spinner_data(ArrayList<MothertoungeList.MothertoungeListData> mothertoungeListData) {
        mother_tongue_list.clear();
        mother_tongue_list.add("Select Mother Tongue");

        if (mothertoungeListData != null && !mothertoungeListData.isEmpty()) {
            for (int i = 0; i < mothertoungeListData.size(); i++) {
                mother_tongue_list.add(i + 1, mothertoungeListData.get(i).getName().trim());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        mother_tongue_adapter = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, mother_tongue_list);
        mother_tongue_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mother_tounge_spinner.setAdapter(mother_tongue_adapter);

        // Handle Spinner click to show searchable dialog
//        mother_tounge_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableMotherTongueDialog(mother_tongue_list);
//            }
//            return true; // Consume the touch event
//        });

        mother_tounge_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                openMultiMotherTongueDialog();
            }
            return true;
        });


    }

    private void openMultiMotherTongueDialog() {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("Mother Tongue");

        selectedMotherTongueList.clear();

        // -------- PRESELECT (if already selected) --------
        if (mother_tongue_str != null && !mother_tongue_str.isEmpty()) {
            String[] arr = mother_tongue_str.split(",");
            for (String s : arr) {
                selectedMotherTongueList.add(s.trim());
            }
        }

        // -------- ADD SPECIAL OPTIONS --------
        if (!mother_tongue_list.contains("Doesn't Matter")) {
            mother_tongue_list.add(0, "Doesn't Matter");
        }

        if (!mother_tongue_list.contains("Select All")) {
            mother_tongue_list.add(1, "Select All");
        }

        CheckBox dmCheckBox = null;
        CheckBox selectAllCheckBox = null;

        // -------- CREATE CHECKBOXES --------
        for (String name : mother_tongue_list) {

            CheckBox cb = new CheckBox(this);
            cb.setText(name);
            cb.setTextSize(16);

            if (selectedMotherTongueList.contains(name)) {
                cb.setChecked(true);
            }

            if (name.equals("Doesn't Matter")) {
                dmCheckBox = cb;
            } else if (name.equals("Select All")) {
                selectAllCheckBox = cb;
            }

            container.addView(cb);
        }

        CheckBox finalDm = dmCheckBox;
        CheckBox finalSelectAll = selectAllCheckBox;

        // -------- CHECKBOX LOGIC --------
        for (int i = 0; i < container.getChildCount(); i++) {

            View v = container.getChildAt(i);
            if (!(v instanceof CheckBox)) continue;

            CheckBox cb = (CheckBox) v;

            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (!isChecked) return;

                String text = cb.getText().toString();

                // DOESN'T MATTER
                if (text.equals("Doesn't Matter")) {
                    for (int j = 0; j < container.getChildCount(); j++) {
                        CheckBox c = (CheckBox) container.getChildAt(j);
                        if (c != finalDm) c.setChecked(false);
                    }
                }

                // SELECT ALL
                else if (text.equals("Select All")) {
                    if (finalDm != null) finalDm.setChecked(false);

                    for (int j = 0; j < container.getChildCount(); j++) {
                        CheckBox c = (CheckBox) container.getChildAt(j);
                        String t = c.getText().toString();

                        if (!t.equals("Doesn't Matter") && !t.equals("Select All")) {
                            c.setChecked(true);
                        }
                    }
                }

                // NORMAL ITEM
                else {
                    if (finalDm != null) finalDm.setChecked(false);
                    if (finalSelectAll != null) finalSelectAll.setChecked(false);
                }
            });
        }

        // -------- BUTTONS --------
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnOk.setOnClickListener(v -> {

            mothertounge_array.clear();
            mother_tongue_flex.removeAllViews();

            boolean isDM = false;

            for (int i = 0; i < container.getChildCount(); i++) {

                CheckBox cb = (CheckBox) container.getChildAt(i);
                if (!cb.isChecked()) continue;

                String text = cb.getText().toString();

                if (text.equals("Doesn't Matter")) {
                    isDM = true;
                    break;
                }

                if (!text.equals("Select All")) {
                    mothertounge_array.add(text.trim());
                }
            }

            if (isDM) {
                mothertounge_array.clear();
                mothertounge_array.add("Doesn't Matter");
                mother_tongue_str = "Doesn't Matter";
            } else {
                mother_tongue_str = TextUtils.join(", ", mothertounge_array);
            }

            // ---------- SHOW FLEXBOX (SAME AS FIRST CODE) ----------
            if (!mothertounge_array.isEmpty()) {
                mother_tongue_flexLL.setVisibility(View.VISIBLE);
            }

            for (String mt : mothertounge_array) {

                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 5, 10, 5);

                TextView tv = new TextView(PartnerPreferencesActivity.this);
                tv.setLayoutParams(params);
                tv.setTextSize(16);
                tv.setPadding(20, 10, 20, 10);
                tv.setBackgroundColor(Color.parseColor("#110A0A0A"));
                tv.setText(mt + "     x ");

                mother_tongue_flex.addView(tv);
            }

            // ---------- UPDATE SPINNER TEXT (IMPORTANT) ----------
            ((TextView) mother_tounge_spinner.getSelectedView())
                    .setText(mother_tongue_str);

            // 🔥 DONE — backend will now work automatically
            dialog.dismiss();
        });

        dialog.show();
    }


    // Method to show a searchable dialog for mother tongue selection
    private void showSearchableMotherTongueDialog(List<String> motherTongueList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalMotherTongueList = new ArrayList<>(motherTongueList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                motherTongueList
        );
        listView.setAdapter(dialogAdapter);

        // Filter list based on search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                // Get the selected item from the filtered list
                String selectedMotherTongue = dialogAdapter.getItem(position);
                if (selectedMotherTongue == null) {
                    Log.e("MotherTongueDialog", "Selected mother tongue is null at position: " + position);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting mother tongue. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list to get the correct position
                int originalPosition = -1;
                for (int i = 0; i < originalMotherTongueList.size(); i++) {
                    if (originalMotherTongueList.get(i).equals(selectedMotherTongue)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("MotherTongueDialog", "Could not find selected mother tongue in original list: " + selectedMotherTongue);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting mother tongue. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mother_tongue_str = selectedMotherTongue;
                Log.d("MotherTongueDialog", "Selected mother tongue: " + mother_tongue_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                mother_tounge_spinner.setSelection(originalPosition);

                // Replicate the existing onItemSelected logic with a duplicate check
                System.out.println("mother_tongue_str------" + mother_tongue_str);

                // Check for duplicates before adding
                boolean isDuplicate = false;
                for (String item : mothertounge_array) {
                    if (item.trim().equals(mother_tongue_str.trim())) {
                        isDuplicate = true;
                        break;
                    }
                }

                if (isDuplicate) {
                    Toast.makeText(PartnerPreferencesActivity.this, "Already Added!!!", Toast.LENGTH_SHORT).show();
                } else if (!mother_tongue_str.equals("Select Mother Tongue")) {
                    if (mother_tongue_flexLL.getVisibility() == View.GONE) {
                        mother_tongue_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);

                    mother_tounge_txtv = new TextView(PartnerPreferencesActivity.this);
                    mother_tounge_txtv.setLayoutParams(params);
                    mother_tounge_txtv.setTextSize(16);
                    mother_tounge_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
                    mother_tounge_txtv.setPadding(20, 10, 20, 10);
                    mother_tounge_txtv.setLayoutParams(params);

                    if (!mother_tongue_str.equals(mother_tounge_txtv.getText().toString())) {
                        mother_tounge_txtv.setText(mother_tongue_str.trim() + "     x ");
                        mothertounge_array.add(mother_tongue_str.trim());
                    }

                    mother_tongue_flex.addView(mother_tounge_txtv);

                    final String myString = mother_tounge_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    mother_tounge_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    mother_tounge_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) mother_tounge_txtv.getText();
                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            TextView textView1 = (TextView) textView;
                            String charSequence = textView1.getText().toString().trim();
                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());

                            mothertounge_array.remove(charSequence);

                            for (int i = 0; i < mothertounge_array.size(); i++) {
                                System.out.println("array character name----------" + mothertounge_array.get(i));
                                if (mothertounge_array.get(i).trim().contains(a.trim())) {
                                    int id = mothertounge_array.indexOf(a.trim());
                                    System.out.println(" 11 array character name----------" + id);
                                    mothertounge_array.remove(id);
                                    textView1.setVisibility(View.GONE);
                                }
                            }
                        }
                    };

                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("MotherTongueDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting mother tongue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }



    public void getCountryJobList() {
        countryJobListData.clear();
        Api apiService = RetrofitClient.getApiService();
        Call<CountryList> userResponse = apiService.countryList("07");
        userResponse.enqueue(new Callback<CountryList>() {
            @Override
            public void onResponse(Call<CountryList> call, Response<CountryList> response) {
                countryJobList = response.body();
                if (response.isSuccessful()) {
                    String success = countryJobList.getResid();
                    if (success.equals("200")) {
                        for (int i = 0; i < countryJobList.getCountryList().size(); i++) {
                            countryJobListData.add(new CountryList.CountryListData(countryJobList.getCountryList().get(i).getId(), countryJobList.getCountryList().get(i).getName(), countryJobList.getCountryList().get(i).getStateList()));
                        }
                        country_job_spinner_data(countryJobListData);
                    } else {
                    }
                }
            }
            @Override
            public void onFailure(Call<CountryList> call, Throwable t) {
                System.out.println("err country******" + t.toString());
                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void country_job_spinner_data(List<CountryList.CountryListData> countryJobListData) {
        country_job_list.clear();
        country_job_list.add("Select Country");
        country_job_list.add("Doesn't Matter");
        if (country_job_list.size() != 0) {
            for (int i = 0; i < countryJobListData.size(); i++) {
                country_job_list.add(i + 2, countryJobListData.get(i).getName());
            }
        }

        adapter_country_job = new ArrayAdapter<String>(PartnerPreferencesActivity.this, R.layout.simple_spinner_item, country_job_list);

        current_country_job_spinner.setAdapter(adapter_country_job);

        int pos;
        for (int i = 0; i < country_job_list.size(); i++) {
//            if (country_job_list.get(i).contains(fetchPartnerPref.getOfc_country_name())) {
//                pos = i;
//                System.out.println("state i-----------" + pos);
//                current_country_job_spinner.setSelection(pos);
//            }
        }


        current_country_job_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                country_job_list_str = current_country_job_spinner.getItemAtPosition(position).toString();
                System.out.println("city_list_str------" + country_job_list_str);

                for_state_job_spinner_data(country_job_list_str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void for_state_job_spinner_data(String country_job_list_str) {
        stateJobListsData.clear();
        /*---------*/

        if (country_job_list_str != null) {
            for (int i = 0; i < countryJobListData.size(); i++) {
                if (country_job_list_str.equals(countryJobListData.get(i).getName())) {
                    country_job_id = String.valueOf(countryJobListData.get(i).getId());
                }
            }
        }

        for (int i = 0; i < countryJobListData.size(); i++) {
            if (countryJobListData.get(i).getStateList() != null) {

                for (int j = 0; j < countryJobListData.get(i).getStateList().size(); j++) {

                    stateJobList = countryJobListData.get(i).getStateList().get(j);

                    if (country_job_id != null) {

                        if (country_job_id.contentEquals(stateJobList.getC_id())) {
                            stateJobListsData.add(new StateList(stateJobList.getC_id(), stateJobList.getSc_id(), stateJobList.getSc_name()));
                        }

                    }
                }
                state_job_spinner_data(stateJobListsData);
            }
        }
    }
    public void state_job_spinner_data(List<StateList> stateJobLists) {
        state_job_list.clear();
        state_job_list.add("Select State");
        state_job_list.add("Doesn't Matter");

        if (stateJobLists != null && !stateJobLists.isEmpty()) {
            for (int i = 0; i < stateJobLists.size(); i++) {
                state_job_list.add(i + 2, stateJobLists.get(i).getSc_name());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        adapter_state_job = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, state_job_list);
        adapter_state_job.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_state_job_spinner.setAdapter(adapter_state_job);

        // Set default selection if pref_state_name exists
        if (fetchPartnerPref.getPref_state_name() != null && !fetchPartnerPref.getPref_state_name().isEmpty()) {
            for (int i = 0; i < state_list.size(); i++) {
                if (state_list.get(i).equals(fetchPartnerPref.getPref_state_name())) {
                    current_state_spinner.setSelection(i);
                    System.out.println("state i-----------" + i);
                    break;
                }
            }
        }

        // Handle Spinner click to show searchable dialog
        current_state_job_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableStateJobDialog(state_job_list);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for state selection
    private void showSearchableStateJobDialog(List<String> stateJobList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalStateList = new ArrayList<>(stateJobList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                stateJobList
        );
        listView.setAdapter(dialogAdapter);

        // Filter list based on search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle item selection
        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                // Get the selected item from the filtered list
                String selectedState = dialogAdapter.getItem(position);
                if (selectedState == null) {
                    Log.e("StateDialog", "Selected state is null at position: " + position);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list to get the correct position
                int originalPosition = -1;
                for (int i = 0; i < originalStateList.size(); i++) {
                    if (originalStateList.get(i).equals(selectedState)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("StateDialog", "Could not find selected state in original list: " + selectedState);
                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                state_job_list_str = selectedState;
                Log.d("StateDialog", "Selected state: " + state_job_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                current_state_job_spinner.setSelection(originalPosition);

                // Call existing method to handle district updates
                System.out.println("state_list_str------" + state_job_list_str);
                getDistrictJobList(state_job_list_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("StateDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting state: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void getDistrictJobList(String state_job_list_str) {
        if (state_job_list_str != null) {
            for (int i = 0; i < stateJobListsData.size(); i++) {
                if (state_job_list_str.equals(stateJobListsData.get(i).getSc_name())) {
                    state_job_id = String.valueOf(stateJobListsData.get(i).getSc_id());
                }
            }
        }

        System.out.println("state_job_id--------" + state_job_id);

        districtJobListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<DistrictList> userResponse = apiService.districtList("13", state_job_id); // Use state_job_id instead of state_id
        userResponse.enqueue(new Callback<DistrictList>() {
            @Override
            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
                districtJobList = response.body();

                if (response.isSuccessful()) {
                    String success = districtJobList.getResid();

                    if (success.equals("200")) {
                        for (int i = 0; i < districtJobList.getDistrictList().size(); i++) {
                            districtJobListData.add(new DistrictList.DistrictListData(
                                    districtJobList.getDistrictList().get(i).getId(),
                                    districtJobList.getDistrictList().get(i).getName(),
                                    districtJobList.getDistrictList().get(i).getState_id()));
                        }
                        district_job_spinner_data(districtJobListData);
                    } else {
                        // Handle unsuccessful response
                    }
                }
            }

            @Override
            public void onFailure(Call<DistrictList> call, Throwable t) {
                System.out.println("err district******" + t.toString());
                if (!isNetworkAvailable(PartnerPreferencesActivity.this)) {
                    // Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void district_job_spinner_data(List<DistrictList.DistrictListData> districtJobListData) {
        district_job_list.clear();
        district_job_list.add("Select District");
        district_job_list.add("Doesn't Matter");

        if (districtJobListData != null && !districtJobListData.isEmpty()) {
            for (int i = 0; i < districtJobListData.size(); i++) {
                district_job_list.add(i + 2, districtJobListData.get(i).getName().trim());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        district_adapter_job = new ArrayAdapter<>(PartnerPreferencesActivity.this,
                R.layout.simple_spinner_item, district_job_list);
        district_adapter_job.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_district_job_spinner.setAdapter(district_adapter_job);

        // Uncomment the following block if you want to enable pre-selection
    /*
    if (fetchPartnerPref.getPref_dist_name() != null && !fetchPartnerPref.getPref_dist_name().isEmpty()) {
        for (int i = 0; i < district_list.size(); i++) {
            if (district_list.get(i).equals(fetchPartnerPref.getPref_dist_name())) {
                current_district_spinner.setSelection(i);
                System.out.println("state i-----------" + i);
                break;
            }
        }
    }
    */
        // Handle Spinner click to show searchable dialog
        current_district_job_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableDistrictJobDialog(district_job_list);
                openMultiDistrictJobDialog(district_job_list, districtJobListData);


            }
            return true; // Consume the touch event
        });
    }

    private void openMultiDistrictJobDialog(
            List<String> districtJobList,
            List<DistrictList.DistrictListData> districtJobListData) {

        Dialog dialog = new Dialog(PartnerPreferencesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_salary_multi_select);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.checkbox_container);
        TextView title = dialog.findViewById(R.id.tv_title);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        title.setText("District");
        container.removeAllViews();

        // ---------- PRESELECT ----------
        ArrayList<String> preSelected = new ArrayList<>(district_job_array);

        // ---------- CREATE CHECKBOXES ----------
        for (String district : districtJobList) {
            CheckBox cb = new CheckBox(this);
            cb.setText(district);
            cb.setTextSize(16);

            if (preSelected.contains(district)) {
                cb.setChecked(true);
            }

            container.addView(cb);
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // ---------- OK ----------
        btnOk.setOnClickListener(v -> {

            // CLEAR OLD DATA
            district_job_array.clear();
            district_job_id_map.clear();
            district_job_flex.removeAllViews();

            boolean isDoesntMatter = false;

            // COLLECT CHECKED
            for (int i = 0; i < container.getChildCount(); i++) {
                CheckBox cb = (CheckBox) container.getChildAt(i);
                if (!cb.isChecked()) continue;

                String districtName = cb.getText().toString().trim();

                if (districtName.equalsIgnoreCase("Doesn't Matter")) {
                    isDoesntMatter = true;
                    break;
                }

                district_job_array.add(districtName);

                // FIND ID
                String id = "0";
                for (DistrictList.DistrictListData data : districtJobListData) {
                    if (districtName.equalsIgnoreCase(data.getName().trim())) {
                        id = String.valueOf(data.getId());
                        break;
                    }
                }
                district_job_id_map.put(districtName, id);
            }

            // ---------- HANDLE DOESN'T MATTER ----------
            if (isDoesntMatter) {
                district_job_array.clear();
                district_job_id_map.clear();

                district_job_array.add("Doesn't Matter");
                district_job_id_map.put("Doesn't Matter", "0");
                districtId = "0";
            }

            // ---------- FLEXBOX UI ----------
            if (!district_job_array.isEmpty()) {
                district_job_flexLL.setVisibility(View.VISIBLE);
            }

            for (String district : district_job_array) {

                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 5, 10, 5);

                TextView tv = new TextView(this);
                tv.setLayoutParams(params);
                tv.setTextSize(16);
                tv.setPadding(20, 10, 20, 10);
                tv.setBackgroundColor(Color.parseColor("#110A0A0A"));
                tv.setText(district + "     x ");

                district_job_flex.addView(tv);
            }

            // ---------- FALLBACK ----------
            if (district_job_array.isEmpty()) {
                districtId = "0";
            }

            // ---------- UPDATE SPINNER TEXT ----------
            ((TextView) current_district_job_spinner.getSelectedView())
                    .setText(TextUtils.join(", ", district_job_array));

            dialog.dismiss();
        });

        dialog.show();
    }


    // Method to show a searchable dialog for district selection
    private void showSearchableDistrictJobDialog(List<String> districtJobList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerPreferencesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalDistrictList = new ArrayList<>(districtJobList);

        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                PartnerPreferencesActivity.this,
                android.R.layout.simple_list_item_1,
                districtJobList
        );
        listView.setAdapter(dialogAdapter);
        final boolean[] selectionMade = {false};
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectionMade[0] = true; // ✅ User selected something

            String selectedDistrict = dialogAdapter.getItem(position);
            if (selectedDistrict == null) {
                Toast.makeText(PartnerPreferencesActivity.this, "Invalid selection", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return;
            }

            String trimmedDistrict = selectedDistrict.trim();
            String normalizedDistrict = trimmedDistrict.toLowerCase();

            // ✅ Handle "Doesn't Matter" explicitly
            if (trimmedDistrict.equalsIgnoreCase("Doesn't Matter")) {

                districtId = "0"; // ✅ force zero
                Log.d("DistrictDialog", "Doesn't Matter selected. districtId = 0");

                // Optional: avoid adding chip
                Toast.makeText(
                        PartnerPreferencesActivity.this,
                        "Doesn't Matter selected",
                        Toast.LENGTH_SHORT
                ).show();

                dialog.dismiss();
                return;
            }


            // ✅ Skip invalid selections
//                if (normalizedDistrict.isEmpty() ||
//                        normalizedDistrict.equals("select district") ||
//                        normalizedDistrict.equals("doesn't matter")) {
//                    districtId = "0"; // Set fallback ID
//                    Toast.makeText(PartnerPreferencesActivity.this, "No valid district selected.", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    return;
//                }

            // ✅ Get original position in spinner
            int originalPosition = -1;
            for (int i = 0; i < originalDistrictList.size(); i++) {
                if (originalDistrictList.get(i).equals(selectedDistrict)) {
                    originalPosition = i;
                    break;
                }
            }
            if (originalPosition != -1) {
                current_district_job_spinner.setSelection(originalPosition);
            }

            // ✅ Get districtId from list
            districtId = null;
            for (DistrictList.DistrictListData districtData : districtJobListData) {
                String districtName = districtData.getName() != null ? districtData.getName().trim().toLowerCase() : "";
                if (districtName.equals(normalizedDistrict)) {
                    districtId = String.valueOf(districtData.getId());
                    Log.d("DistrictDialog", "Found district ID: " + districtId + " for district: " + trimmedDistrict);
                    break;
                }
            }
            if (districtId == null) {
                districtId = "0"; // Default fallback if ID not found
            }

            // ✅ Prevent duplicate additions
            boolean isDuplicate = district_job_array.contains(trimmedDistrict);
            if (isDuplicate) {
                Toast.makeText(PartnerPreferencesActivity.this, "District already added!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return;
            }

            // ✅ Show layout if hidden
            if (district_job_flexLL.getVisibility() == View.GONE) {
                district_job_flexLL.setVisibility(View.VISIBLE);
            }

            // ✅ Create chip view
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 10, 5);

            TextView district_txtv = new TextView(PartnerPreferencesActivity.this);
            district_txtv.setLayoutParams(params);
            district_txtv.setTextSize(16);
            district_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
            district_txtv.setPadding(20, 10, 20, 10);
            district_txtv.setText(trimmedDistrict + "     x ");

            // ✅ Add to lists/maps
            district_job_array.add(trimmedDistrict);
            district_job_id_map.put(trimmedDistrict, districtId);

            // ✅ Add to Flexbox
            district_job_flex.addView(district_txtv);

            // ✅ Set clickable "x" to remove chip
            final String myString = district_txtv.getText().toString();
            int i1 = myString.indexOf("x ");
            district_txtv.setMovementMethod(LinkMovementMethod.getInstance());
            district_txtv.setText(myString, TextView.BufferType.SPANNABLE);
            Spannable mySpannable = (Spannable) district_txtv.getText();

            ClickableSpan myClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    TextView textView1 = (TextView) textView;
                    String charSequence = textView1.getText().toString().trim();
                    String districtName = charSequence.substring(0, charSequence.length() - 2).trim();

                    Log.d("DistrictDialog", "Removing district: " + districtName);
                    district_job_array.remove(districtName);
                    district_job_id_map.remove(districtName);
                    textView1.setVisibility(View.GONE);
                }
            };

            mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            dialog.dismiss();

//            } catch (Exception e) {
//                Log.e("DistrictDialog", "Error in item selection: " + e.getMessage(), e);
//                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
        dialog.setOnDismissListener(dialogInterface -> {
            if (!selectionMade[0]) {
                districtId = "0";
                Log.d("DistrictDialog", "No district selected. districtId set to 0.");
            }
        });

//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            try {
//                String selectedDistrict = dialogAdapter.getItem(position);
//                if (selectedDistrict == null) {
//                    Log.e("DistrictDialog", "Selected district is null at position: " + position);
//                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                int originalPosition = -1;
//                for (int i = 0; i < originalDistrictList.size(); i++) {
//                    if (originalDistrictList.get(i).equals(selectedDistrict)) {
//                        originalPosition = i;
//                        break;
//                    }
//                }
//
//                if (originalPosition == -1) {
//                    Log.e("DistrictDialog", "Could not find selected district in original list: " + selectedDistrict);
//                    Toast.makeText(PartnerPreferencesActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                district_job_str = selectedDistrict;
//                Log.d("DistrictDialog", "Selected district: " + district_job_str + ", Original position: " + originalPosition);
//
//                // Find the district ID from districtJobListData
////                String districtId = null;
////                for (DistrictList.DistrictListData districtData : districtJobListData) {
////                    if (districtData.getName().trim().equals(district_job_str.trim())) {
////                        districtId = String.valueOf(districtData.getId());
////                        break;
////                    }
////                }
//// Find the district ID from districtJobListData
//                districtId = null;
//                for (DistrictList.DistrictListData districtData : districtJobListData) {
//                    // Normalize strings for comparison
//                    String districtName = districtData.getName() != null ? districtData.getName().trim().toLowerCase() : "";
//                    String selectedDistrictNormalized = district_job_str.trim().toLowerCase();
//                    if (districtName.equals(selectedDistrictNormalized)) {
//                        districtId = String.valueOf(districtData.getId());
//                        Log.d("DistrictDialog", "Found district ID: " + districtId + " for district: " + district_job_str);
//                        break;
//                    }
//                }
//
//
//                if (districtId == null) {
//                    Log.e("DistrictDialog", "Could not find district ID for: " + district_job_str);
//                    Toast.makeText(PartnerPreferencesActivity.this, "Error retrieving district ID.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                current_district_job_spinner.setSelection(originalPosition);
//
//                System.out.println("district_job_str------" + district_job_str);
//
//                if (!district_job_str.equals("Select District")) {
//                    if (district_job_flexLL.getVisibility() == View.GONE) {
//                        district_job_flexLL.setVisibility(View.VISIBLE);
//                    }
//
//                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
//                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
//                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(10, 5, 10, 5);
//
//                    TextView district_txtv = new TextView(PartnerPreferencesActivity.this);
//                    district_txtv.setLayoutParams(params);
//                    district_txtv.setTextSize(16);
//                    district_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));
//                    district_txtv.setPadding(20, 10, 20, 10);
//
//
//                    //new 12-6-2025
//
//                    String trimmedDistrict = district_job_str.trim();
//
//// ✅ Handle special cases: "Select District" or "Doesn't Matter"
//                    if (districtId == null || districtId.trim().isEmpty()
//                            || trimmedDistrict.equalsIgnoreCase("Select District")
//                            || trimmedDistrict.equalsIgnoreCase("Doesn't Matter")) {
//                        districtId = "0"; // Set empty if it's not a real district
//                    }
//
//                    boolean isDuplicate = district_job_array.contains(trimmedDistrict);
//
//                    if (!isDuplicate) {
//                        district_txtv.setText(trimmedDistrict + "     x ");
//                        district_job_array.add(trimmedDistrict);
//                        district_job_id_map.put(trimmedDistrict, districtId); // ✅ Now stores "" if special case
//                    } else {
//                        Toast.makeText(PartnerPreferencesActivity.this, "District already added!", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                        return;
//                    }
//
//
//                    // 12-6-2025
//                    // Check for duplicates in district_job_array
////                    boolean isDuplicate = district_job_array.contains(district_job_str.trim());
////                    if (!isDuplicate) {
////                        district_txtv.setText(district_job_str.trim() + "     x ");
////                        district_job_array.add(district_job_str.trim());
////                        district_job_id_map.put(district_job_str.trim(), districtId); // Store district ID
////                    } else {
////                        Toast.makeText(PartnerPreferencesActivity.this, "District already added!", Toast.LENGTH_SHORT).show();
////                        dialog.dismiss();
////                        return;
////                    }
//
//                    //
//
//                    district_job_flex.addView(district_txtv);
//
//                    final String myString = district_txtv.getText().toString();
//                    int i1 = myString.indexOf("x ");
//                    district_txtv.setMovementMethod(LinkMovementMethod.getInstance());
//                    district_txtv.setText(myString, TextView.BufferType.SPANNABLE);
//                    Spannable mySpannable = (Spannable) district_txtv.getText();
//
//                    ClickableSpan myClickableSpan = new ClickableSpan() {
//                        @Override
//                        public void onClick(View textView) {
//                            TextView textView1 = (TextView) textView;
//                            String charSequence = textView1.getText().toString().trim();
//                            String districtName = charSequence.substring(0, charSequence.length() - 2).trim();
//
//                            System.out.println("character name----------" + districtName);
//                            System.out.println("final character name----------" + districtName);
//
//                            // Remove from district_job_array and district_job_id_map
//                            district_job_array.remove(districtName);
//                            district_job_id_map.remove(districtName);
//                            textView1.setVisibility(View.GONE);
//                        }
//                    };
//
//                    mySpannable.setSpan(myClickableSpan, i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    mySpannable.setSpan(new ForegroundColorSpan(Color.BLACK), i1, i1 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                }
//
//                dialog.dismiss();
//            } catch (Exception e) {
//                Log.e("DistrictDialog", "Error in item selection: " + e.getMessage(), e);
//                Toast.makeText(PartnerPreferencesActivity.this, "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        dialog.show();
    }
}

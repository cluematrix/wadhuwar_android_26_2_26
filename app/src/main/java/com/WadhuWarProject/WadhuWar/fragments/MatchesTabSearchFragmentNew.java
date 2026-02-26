package com.WadhuWarProject.WadhuWar.fragments;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.PartnerPreferencesActivity;
import com.WadhuWarProject.WadhuWar.activity.SearchResultsActivity;
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
import com.WadhuWarProject.WadhuWar.model.SearchData;
import com.WadhuWarProject.WadhuWar.model.StateList;
import com.WadhuWarProject.WadhuWar.model.SubCasteList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchesTabSearchFragmentNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchesTabSearchFragmentNew extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    LinearLayout educationFlexBothLL;
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

    UserData user;

    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;

    EditText expectation;

    TextView step1_view_more, step2_view_more, step3_view_more, step4_view_more, step5_view_more;
    CardView step2_cv, step3_cv, step4_cv, step5_cv, step6_cv;
    LinearLayout update_btn;

    FetchProfile fetchPartnerPref;
    //        Toolbar toolbar;
    Spinner marital_status_spinner, colorcomplex_spinner, height_spinner, height_spinner_to, religion_spinner, caste_spinner, sub_caste_spinner, highest_education_spinner, lifestyle_spinner,
            dietary_spinner, agefrom_spinner, ageto_spinner, sampraday_spinner,
            education_type_spinner, occupation_type_spinner, occupation_spinner, salary_spinner, current_state_spinner,
            current_district_spinner,current_country_spinner,office_country_spinner,mother_tounge_spinner;

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

    ArrayList<String> dietary_array = new ArrayList<String>();
    ArrayList<String> dietary_array_id = new ArrayList<String>();

    ArrayList<String> lifestyle_array = new ArrayList<String>();
    ArrayList<String> lifestyle_array_id = new ArrayList<String>();

    ArrayList<String> color_complexion_array = new ArrayList<String>();
    ArrayList<String> color_complexion_array_id = new ArrayList<String>();

    ArrayList<String> marital_status_array = new ArrayList<String>();
    ArrayList<String> marital_status_array_id = new ArrayList<String>();

    ArrayList<String> district_array = new ArrayList<String>();
    ArrayList<String> district_array_id = new ArrayList<String>();

    ArrayList<String> mothertounge_array = new ArrayList<String>();
    ArrayList<String> mothertounge_array_id = new ArrayList<String>();

    ArrayList<EducationList.EducationListData> educationListData = new ArrayList<>();
    EducationList educationList;
    ArrayList<LifeSettingList.LifeSettingListData> lifeSettingLists = new ArrayList<>();
    LifeSettingList lifeSettingList;
    DietaryList dietaryList;
    ArrayList<AgeList.AgeListData> ageListDataTo = new ArrayList<>();
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
    List<String> office_state_list = new ArrayList<String>();
    List<String> district_list = new ArrayList<String>();
    List<String> country_list = new ArrayList<String>();
    List<String> office_country_list = new ArrayList<String>();
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
    FlexboxLayout education_pckg_flex;
    FlexboxLayout occupation_pckg_flex;
    FlexboxLayout subcaste_flex;
    FlexboxLayout dietary_flex;
    FlexboxLayout lifestyle_flex;
    FlexboxLayout color_complexion_flex;
    FlexboxLayout marital_status_flex;
    FlexboxLayout district_flex;
    FlexboxLayout mother_tongue_flex;

    LinearLayout education_type_flexLL;
    LinearLayout occupation_type_flexLL;
    LinearLayout subcaste_flexLL;
    LinearLayout dietary_flexLL;
    LinearLayout lifestyle_flexLL;
    LinearLayout color_complexion_flexLL;
    LinearLayout district_flexLL;
    LinearLayout mother_tongue_flexLL;

    String subcaste_pckg_list, dietary_pckg_list, lifestyle_pckg_list, color_complexion_pckg_list,
            marital_status_pckg_list, district_pckg_list, education_pckg_list, education_type_pckg_list,
            occupation_pckg_list, occupation_type_pckg_list, mother_tongue_pckg_list;
    String[] namesList;

    String marital_str, colorcomplex_str, height_str, height_to_str, religion_list_str, caste_list_str, sub_caste_str, education_list_str,
            lifestyle_str, dietray_str, ageTo_list_str, agefrom_list_str, sampraday_list_str, education_type_list_str, occupation_list_str, occupation_type_list_str;
    String maritalstatus_id, colorcomplex_id, height_id, religion_id, caste_id, subcaste_id, dietray_id,
            age_id, sampraday_id, education_id, lifestyle_id, education_type_id,
            occupation_id, occupation_type_id, salary_str,office_state_list_str = "" ,state_list_str = "", district_str = "",
            district_id, state_id, country_id, country_list_str,office_country_list_str, mother_tongue_str, mother_tongue_id;
    TextView education_type_txtv;
    TextView occupation_type_txtv;
    TextView marital_status_txtv;
    TextView subcaste_txtv;
    TextView dietary_txtv;
    TextView lifestyle_txtv;
    TextView color_complexion_txtv;
    TextView district_txtv;
    TextView mother_tounge_txtv;
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
    ArrayAdapter<String> office_adapter_country;
    ArrayAdapter<String> mother_tongue_adapter;
    String _expectation;


    ProgressDialog progressBar;
    InsertResponse insertResponse;
    String castId;
    String user_id;
    SwipeRefreshLayout swipeRefreshLayout;

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
    StateList stateList;
    ArrayList<DistrictList.DistrictListData> districtListData = new ArrayList<>();
    DistrictList districtList;
    ArrayList<CountryList.CountryListData> countryListData = new ArrayList<>();
    CountryList countryList;
    ArrayList<CountryList.CountryListData> officeCountryListData = new ArrayList<>();
    CountryList officeCountryList;
    ArrayList<MothertoungeList.MothertoungeListData> mothertoungeListData = new ArrayList<>();
    MothertoungeList mothertoungeList;


    EditText name;
    String _name;
    LinearLayout search_btn;

    ArrayList<SearchData.SearchDataList> searchDataLists = new ArrayList<>();
    SearchData searchData;

    String gender_str = "";

    String marital_id;


    public MatchesTabSearchFragmentNew() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchesTabSearchFragmentNew.
     */
    // TODO: Rename and change types and number of parameters
//    public static MatchesTabSearchFragmentNew newInstance(String param1, String param2) {
//        MatchesTabSearchFragmentNew fragment = new MatchesTabSearchFragmentNew();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static MatchesTabSearchFragmentNew newInstance() {
        MatchesTabSearchFragmentNew fragment = new MatchesTabSearchFragmentNew();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_matches_tab_search_new, container, false);
        final View view = inflater.inflate(R.layout.fragment_matches_tab_search_new, container, false);

        castId = SharedPrefManager.getInstance(getContext()).getCastId();

        step1_view_more = view.findViewById(R.id.step1_view_more);
        step2_view_more = view.findViewById(R.id.step2_view_more);
        step3_view_more = view.findViewById(R.id.step3_view_more);
        step4_view_more = view.findViewById(R.id.step4_view_more);
        step5_view_more = view.findViewById(R.id.step5_view_more);
        step2_cv = view.findViewById(R.id.step2_cv);
        step3_cv = view.findViewById(R.id.step3_cv);
        step4_cv = view.findViewById(R.id.step4_cv);
        step5_cv = view.findViewById(R.id.step5_cv);
        step6_cv = view.findViewById(R.id.step6_cv);

        occupationFlexBothLL = view.findViewById(R.id.occupationFlexBothLL);
        occupation_pckg_flex = view.findViewById(R.id.occupation_pckg_flex);
        occupation_flexLL = view.findViewById(R.id.occupation_flexLL);
        occupation_type_flex = view.findViewById(R.id.occupation_type_flex);
        subcaste_flexLL = view.findViewById(R.id.sub_caste_flexLL);
        subcaste_flex = view.findViewById(R.id.sub_caste_flex);
        dietary_flexLL = view.findViewById(R.id.dietary_flexLL);
        dietary_flex = view.findViewById(R.id.dietary_flex);
        lifestyle_flexLL = view.findViewById(R.id.lifestyle_flexLL);
        lifestyle_flex = view.findViewById(R.id.lifestyle_flex);
        color_complexion_flexLL = view.findViewById(R.id.color_complexion_flexLL);
        color_complexion_flex = view.findViewById(R.id.color_complexion_flex);
        occupation_spinner = view.findViewById(R.id.occupation_spinner);
        education_flexLL = view.findViewById(R.id.education_flexLL);
        educationFlexBothLL = view.findViewById(R.id.educationFlexBothLL);
        education_type_flexLL = view.findViewById(R.id.education_type_flexLL);
        occupation_type_flexLL = view.findViewById(R.id.occupation_type_flexLL);
        education_type_flex = view.findViewById(R.id.education_type_flex);
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        education_type_spinner = view.findViewById(R.id.education_type_spinner);
        occupation_type_spinner = view.findViewById(R.id.occupation_type_spinner);
        update_btn = view.findViewById(R.id.update_btn);
        marital_status_spinner = view.findViewById(R.id.marital_status_spinner);
        marital_status_flexLL = view.findViewById(R.id.marital_status_flexLL);
        marital_status_flex = view.findViewById(R.id.marital_status_flex);
        colorcomplex_spinner = view.findViewById(R.id.colorcomplex_spinner);
        height_spinner = view.findViewById(R.id.height_spinner);
        height_spinner_to = view.findViewById(R.id.height_spinner_to);
        religion_spinner = view.findViewById(R.id.religion_spinner);
        caste_spinner = view.findViewById(R.id.caste_spinner);
        sub_caste_spinner = view.findViewById(R.id.sub_caste_spinner);
        highest_education_spinner = view.findViewById(R.id.highest_education_spinner);
        lifestyle_spinner = view.findViewById(R.id.lifestyle_spinner);
        dietary_spinner = view.findViewById(R.id.dietary_spinner);
        agefrom_spinner = view.findViewById(R.id.agefrom_spinner);
        ageto_spinner = view.findViewById(R.id.ageto_spinner);
        sampraday_spinner = view.findViewById(R.id.sampraday_spinner);
        expectation = view.findViewById(R.id.expectation);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        internetOffRL = (RelativeLayout) view.findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) view.findViewById(R.id.simpleProgressBar);
        try_again_txt = (TextView) view.findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) view.findViewById(R.id.couldnt_reach_internet_txt);
        education_pckg_flex = (FlexboxLayout) view.findViewById(R.id.education_pckg_flex);
        occupation_pckg_flex = (FlexboxLayout) view.findViewById(R.id.occupation_pckg_flex);
        salary_spinner = view.findViewById(R.id.salary_spinner);
        current_country_spinner = view.findViewById(R.id.current_country_spinner);
        office_country_spinner = view.findViewById(R.id.office_country_spinner);
        current_state_spinner = view.findViewById(R.id.current_state_spinner);
        current_district_spinner = view.findViewById(R.id.current_district_spinner);
        mother_tounge_spinner = view.findViewById(R.id.mother_tounge_spinner);
        district_flex = view.findViewById(R.id.district_flex);
        district_flexLL = view.findViewById(R.id.district_flexLL);
        mother_tongue_flex = view.findViewById(R.id.mother_tongue_flex);
        mother_tongue_flexLL = view.findViewById(R.id.mother_tongue_flexLL);

        name = view.findViewById(R.id.name);
        search_btn = view.findViewById(R.id.update_btn);

        user = SharedPrefManager.getInstance(requireContext()).getUser();

//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

//        setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("My Profile");
        }


//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requireActivity().onBackPressed(); // or use Navigation component if using it
//            }
//        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _name = name.getText().toString();
            }
        });



        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _name = name.getText().toString();
                Log.d("_name", "onClick: " + _name);

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

                if (colorcomplex_str != null && !colorcomplex_str.contentEquals("") && !colorcomplex_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < colorcomplexLists.size(); i++) {
                        if (colorcomplex_str.equals(colorcomplexLists.get(i).getName())) {
                            colorcomplex_id = String.valueOf(colorcomplexLists.get(i).getId());
                        }
                    }
                } else if (colorcomplex_str != null && colorcomplex_str.equals("Doesn't Matter")) {
                    colorcomplex_id = "Doesn't Matter";

                } else {
                    colorcomplex_id = "";
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
                    marital_status_pckg_list = "";
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
                    subcaste_id = "";
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
                    lifestyle_id = "";

                }


                if (dietray_str != null && !dietray_str.contentEquals("") && !dietray_str.equals("Doesn't Matter")) {
                    for (int i = 0; i < dietaryLists.size(); i++) {
                        if (dietray_str.equals(dietaryLists.get(i).getName())) {
                            dietray_id = String.valueOf(dietaryLists.get(i).getId());

                        }
                    }
                } else if (dietray_str != null && dietray_str.equals("Doesn't Matter")) {
                    dietray_id = "Doesn't Matter";

                } else {
                    dietray_id = "";

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
                        country_id, state_id, district_pckg_list,mother_tongue_pckg_list, salary_str);

                submitData(String.valueOf(user.getUser_id()), _name, "", "", "", "",
                        "", castId, "", "", "", "", "", "",
                        "", "", "", "",
                        "","");

            }
        });

        fetchPartnerPrefData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
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
        return view;
    }




    public void submitData(String user_id, String _name, String gender_str, String marital_id, String agefrom_list_str,
                           String ageTo_list_str, String religion_id, String caste_id, String subcaste_id,
                           String state_id, String district_id, String education_id, String occupation_id,
                           String dietray_id, String sampraday_id, String manglik_str, String handicap_str,
                           String education_type_id, String occupation_type_id, String mother_tongue_pckg_list) {

//        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait...");

        System.out.println("education_type_id=======" + education_type_id);
        System.out.println("education_id=======" + education_id);

        System.out.println("occupation_type_id=======" + occupation_type_id);
        System.out.println("occupation_id=======" + occupation_id);

        System.out.println("gender_str===============" + gender_str);
        System.out.println("user_id===============" + user_id);
        System.out.println("agefrom_list_str===============" + agefrom_list_str);
        System.out.println("ageTo_list_str===============" + ageTo_list_str);
        System.out.println("religion_id===============" + religion_id);
        System.out.println("caste_id===============" + caste_id);
        System.out.println("subcaste_id===============" + subcaste_id);
        System.out.println("education_id===============" + education_id);
        System.out.println("occupation_id===============" + occupation_id);
        System.out.println("state_id===============" + state_id);
        System.out.println("district_id===============" + district_id);
        System.out.println("marital_id===============" + marital_id);
        System.out.println("_name===============" + _name);
        System.out.println("dietray_id===============" + dietray_id);
        System.out.println("sampraday_id===============" + sampraday_id);
        System.out.println("manglik_str===============" + manglik_str);
        System.out.println("handicap_str===============" + handicap_str);
        System.out.println("height_str===============" + height_str);
        System.out.println("height_to_str===============" + height_to_str);
        System.out.println("salary_str===============" + salary_str);
        System.out.println("mother_tongue_pckg_list===============" + mother_tongue_pckg_list);

        Api apiService = RetrofitClient.getApiService();
        Call<SearchData> userResponse = apiService.searchProfile(gender_str, user_id, agefrom_list_str, ageTo_list_str,
                religion_id, caste_id, subcaste_id, education_id, occupation_id, state_id, district_id, marital_id, _name,
                dietray_id, sampraday_id, manglik_str, handicap_str, "10", "1",
                education_type_id, occupation_type_id, height_str, height_to_str, salary_str, mother_tongue_pckg_list);
        userResponse.enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                System.out.println("res-------------" + new Gson().toJson(response.body()));
                searchData = response.body();
//                progressBar.dismiss();

                if (response.isSuccessful()) {
                    System.out.println("basic info resp ========" + new Gson().toJson(searchData));
                    String success = searchData.getResid();
                    if (success.equals("200")) {
                        Intent i = new Intent(getActivity(), SearchResultsActivity.class);
                        i.putExtra("user_id", user_id);
                        i.putExtra("_name", _name);
                        i.putExtra("gender_str", gender_str);
                        i.putExtra("marital_id", marital_id);
                        i.putExtra("agefrom_list_str", agefrom_list_str);
                        i.putExtra("ageTo_list_str", ageTo_list_str);
                        i.putExtra("religion_id", religion_id);
                        i.putExtra("caste_id", caste_id);
                        i.putExtra("subcaste_id", subcaste_id);
                        i.putExtra("state_id", state_id);
                        i.putExtra("district_id", district_id);
                        i.putExtra("education_type_id", education_type_id);
                        i.putExtra("education_id", education_id);
                        i.putExtra("occupation_id", occupation_id);
                        i.putExtra("occupation_type_id", occupation_type_id);
                        i.putExtra("dietray_id", dietray_id);
                        i.putExtra("sampraday_id", sampraday_id);
                        i.putExtra("manglik_str", manglik_str);
                        i.putExtra("handicap_str", handicap_str);
                        i.putExtra("height_str", height_str);
                        i.putExtra("height_to_str", height_to_str);
                        i.putExtra("salary_str", salary_str);
                        i.putExtra("mother_tongue_pckg_list", mother_tongue_pckg_list);

                        startActivity(i);
                    } else {
                        toastMsg(searchData.getResMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {

                System.out.println("err basic info******" + t.toString());
//                progressBar.dismiss();

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void toastMsg(String msg) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.item_toast_additem);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        TextView dialog_message = dialog.findViewById(R.id.dialog_title);
        dialog_message.setText(msg);

        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                dialog.cancel();
            }
        }, 2000);

    }

    private void registerReceiver(NetworkStateReceiver networkStateReceiver, IntentFilter intentFilter) {
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

//                Toast.makeText(PartnerPreferencesActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

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
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                           String country_id, String state_id, String district_pckg_list, String mother_tongue_pckg_list, String salary_str) {

        progressBar = ProgressDialog.show(requireContext(), "", "Please Wait...");

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
        System.out.println("salary_str=======" + this.salary_str);


//        Api apiService = RetrofitClient.getApiService();
//        Call<InsertResponse> userResponse = apiService.editPartnerPrefrence(String.valueOf(user.getUser_id()),
//                maritalstatus_id, colorcomplex_id, height_str, height_to_str,
//                religion_id, caste_id, subcaste_id, agefrom_list_str, ageTo_list_str, education_id, lifestyle_id,
//                dietray_id, _expectation,sampraday_id, education_type_id, occupation_id, occupation_type_id,
//                this.salary_str, country_id, state_id, district_pckg_list,mother_tongue_pckg_list);
//
//        userResponse.enqueue(new Callback<InsertResponse>() {
//
//            @Override
//            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
//                edit_response = response.body();
//
//                progressBar.dismiss();
//                if (response.isSuccessful()) {
//
//                    String success = edit_response.getResid();
//
////                    System.out.println("part pref res=============" + new Gson().toJson(edit_response));
//
//                    if (success.equals("200")) {
//                        Toast.makeText(requireContext(), edit_response.getResMsg(), Toast.LENGTH_SHORT).show();
//
////                        Intent i = new Intent(requireActivity(), MatchesTabSearchFragmentNew.class);
////                        startActivity(i);
////                        requireActivity().finish(); // Only if you want to close the hosting activity
//                    } else {
//                        Toast.makeText(requireContext(), edit_response.getResMsg(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InsertResponse> call, Throwable t) {
//                progressBar.dismiss();
//
//                System.out.println("err partner detail******" + t.toString());
//
//                if (!isNetworkAvailable(requireContext())) {
//                    // Optional: show a Toast
//                    // Toast.makeText(requireContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(requireContext(), "Please Try Again!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });


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

                if (!isNetworkAvailable(requireContext())) {
                    // Toast.makeText(requireContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(requireContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();
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
        adapter_sampraday = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, sampraday_list);

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

                if (!isNetworkAvailable(requireContext())) {
                    // Toast.makeText(requireContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(requireContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();
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


        salary_adapter = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, salary_list);

        salary_spinner.setAdapter(salary_adapter);

        int pos;
        for (int i = 0; i < salary_list.size(); i++) {
            if (salary_list.get(i).contains(fetchPartnerPref.getPref_yearsalary())) {
                pos = i;
                salary_spinner.setSelection(pos);
            }
        }
        salary_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                salary_str = salary_spinner.getItemAtPosition(position).toString();
                System.out.println("salary_str------" + salary_str);
//                for_state_spinner_data(religion_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

                if (!isNetworkAvailable(requireContext())) {  // Fixed the parentheses here
                    // Toast.makeText(requireContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(requireContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();
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
        adapter_ageFrom = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, ageFrom_list);

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

                if (!isNetworkAvailable(requireContext())) { // Use requireContext() for Fragment
                    // Optional: show a Toast for no network
                    // Toast.makeText(requireContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    // Optional: show a Toast for retry
                    // Toast.makeText(requireContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();
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
        adapter_ageTo = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, ageTo_list);

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

                if (!isNetworkAvailable(requireContext())) {  // Use requireContext() for Fragment
                    // Optional: show a Toast for no network
                    // Toast.makeText(requireContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    // Optional: show a Toast for retry
                    // Toast.makeText(requireContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();
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

                if (!isNetworkAvailable(requireContext())) {  // Use requireContext() for Fragment
                    // Optional: show a Toast for no network
                    // Toast.makeText(requireContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
                    // Optional: show a Toast for retry
                    // Toast.makeText(requireContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();
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
        lifestyle_adapter = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, lifestyle_list);

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


        lifestyle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                lifestyle_str = lifestyle_spinner.getItemAtPosition(position).toString();
                System.out.println("lifestyle_str------" + lifestyle_str);

                if (!lifestyle_str.equals("Select LifeStyle")) {
                    if (lifestyle_flexLL.getVisibility() == View.GONE) {
                        lifestyle_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    lifestyle_txtv = new TextView(requireContext());
                    lifestyle_txtv.setLayoutParams(params);
                    lifestyle_txtv.setTextSize(16);
                    lifestyle_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));

                    lifestyle_txtv.setPadding(20, 10, 20, 10);
                    lifestyle_txtv.setLayoutParams(params);
                    if (!lifestyle_str.equals(lifestyle_txtv.getText().toString())) {


                        lifestyle_txtv.setText(lifestyle_str.trim() + "     x ");

                        lifestyle_array.add(lifestyle_str.trim());


                    }


                    lifestyle_flex.addView(lifestyle_txtv);


                    final String myString = lifestyle_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    lifestyle_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    lifestyle_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) lifestyle_txtv.getText();

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {

                            TextView textView1 = (TextView) textView;

                            String charSequence = textView1.getText().toString().trim();

                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());


                            lifestyle_array.remove(charSequence);

                            for (int i = 0; i < lifestyle_array.size(); i++) {
                                System.out.println("array character name----------" + lifestyle_array.get(i));

                                if ((lifestyle_array.get(i).trim()).contains(a.trim())) {

                                    int id = lifestyle_array.indexOf(a.trim());
                                    System.out.println(" 11 array character name----------" + id);

                                    lifestyle_array.remove(id);
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
                if (!isNetworkAvailable(requireContext())) {
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


        adapter_religion = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, religion_list);

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
                if (!isNetworkAvailable(requireContext())) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
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
        caste_adapter = new ArrayAdapter<>(getContext(),
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

        // Handle Spinner click to show searchable dialog
        caste_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableCasteDialog(caste_list);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for caste selection
    private void showSearchableCasteDialog(List<String> casteList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                getContext(),
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
                    Toast.makeText(getContext(), "Error selecting caste. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Error selecting caste. Please try again.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Error selecting caste: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

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

                if (!isNetworkAvailable(requireContext())) {
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

                if (!isNetworkAvailable(requireContext())) {
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
        height_adapter = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, height_list_from);

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
        height_adapter = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, height_list_to);

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

                if (!isNetworkAvailable(requireContext())) {
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
        colorcomplex_adapter = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, colorcomplex_list);

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

        colorcomplex_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                colorcomplex_str = colorcomplex_spinner.getItemAtPosition(position).toString();
                System.out.println("colorcomplex_str------" + colorcomplex_str);

                if (!colorcomplex_str.equals("Select Color-Complex")) {
                    if (color_complexion_flexLL.getVisibility() == View.GONE) {
                        color_complexion_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    color_complexion_txtv = new TextView(requireContext());
                    color_complexion_txtv.setLayoutParams(params);
                    color_complexion_txtv.setTextSize(16);
                    color_complexion_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));

                    color_complexion_txtv.setPadding(20, 10, 20, 10);
                    color_complexion_txtv.setLayoutParams(params);
                    if (!colorcomplex_str.equals(color_complexion_txtv.getText().toString())) {


                        color_complexion_txtv.setText(colorcomplex_str.trim() + "     x ");

                        color_complexion_array.add(colorcomplex_str.trim());


                    }


                    color_complexion_flex.addView(color_complexion_txtv);


                    final String myString = color_complexion_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    color_complexion_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    color_complexion_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) color_complexion_txtv.getText();

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {

                            TextView textView1 = (TextView) textView;

                            String charSequence = textView1.getText().toString().trim();

                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());


                            color_complexion_array.remove(charSequence);

                            for (int i = 0; i < color_complexion_array.size(); i++) {
                                System.out.println("array character name----------" + color_complexion_array.get(i));

                                if ((color_complexion_array.get(i).trim()).contains(a.trim())) {

                                    int id = color_complexion_array.indexOf(a.trim());
                                    System.out.println(" 11 array character name----------" + id);

                                    color_complexion_array.remove(id);
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

                if (!isNetworkAvailable(requireContext())) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void marital_spinner_data(List<MaritalSettingList.MaritalSettingListData> maritalSettingListData) {

        marital_list.clear();

        marital_list.add("Select Marital Status");
        marital_list.add("Doesn't Matter");

        if (marital_list.size() != 0) {
            for (int i = 0; i < maritalSettingListData.size(); i++) {

                marital_list.add(i + 2, maritalSettingListData.get(i).getName());

            }
        }
        adapter_marital = new ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, marital_list);

        marital_status_spinner.setAdapter(adapter_marital);

        int pos;
        for (int i = 0; i < marital_list.size(); i++) {
            if (marital_list.get(i).contains(fetchPartnerPref.getPref_marital_name())) {
                pos = i;
                marital_status_spinner.setSelection(pos);
            }
        }


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


                    marital_status_txtv = new TextView(requireContext());
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

                if (!isNetworkAvailable(requireContext())) {
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
        adapter_education_type = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, education_type_list);

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


                    education_type_txtv = new TextView(requireContext());
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


//        progressBar = ProgressDialog.show(requireContext(), "", "Please Wait...");

        System.out.println("education_type_id-------->>>>>>" + education_type_id);

        Api apiService = RetrofitClient.getApiService();
        Call<EducationList> userResponse = apiService.education("09", education_type_id);
        userResponse.enqueue(new Callback<EducationList>() {

            @Override
            public void onResponse(Call<EducationList> call, Response<EducationList> response) {
                educationList = response.body();
//                progressBar.dismiss();


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
//                progressBar.dismiss();

                System.out.println("err education******" + t.toString());

                if (!isNetworkAvailable(requireContext())) {
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
        adapter_education = new ArrayAdapter<>(getContext(),
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
        highest_education_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableEducationDialog(education_list, educationListData);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for education selection
    private void showSearchableEducationDialog(List<String> educationList, List<EducationList.EducationListData> educationListData) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                getContext(),
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
                    Toast.makeText(getContext(), "Error selecting education. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Error selecting education. Please try again.", Toast.LENGTH_SHORT).show();
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

                                textView2 = new TextView(getContext());
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

                            textView3 = new TextView(getContext());
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
                    Toast.makeText(getContext(), "Already Added!!!", Toast.LENGTH_SHORT).show();
                }
                /*new cond*/

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("EducationDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(getContext(), "Error selecting education: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }    /*end eduction type and education*/


    /*end eduction type and education*/


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

                if (!isNetworkAvailable(requireContext())) {
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
        adapter_occupation_type = new ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, occupation_type_list);

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


                    occupation_type_txtv = new TextView(requireContext());
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

//        progressBar = ProgressDialog.show(requireContext(), "", "Please Wait...");

        System.out.println("occupation_type_id-------->>>>>>" + occupation_type_id);

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService.occupationNew("27", occupation_type_id);
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                occupationList = response.body();
//                progressBar.dismiss();

                System.out.println("occ resp===========" + new Gson().toJson(response.body()));

                if (response.isSuccessful()) {


                    String success = occupationList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < occupationList.getOccupationList().size(); i++) {
                            occupationListData.add(new OccupationList.OccupationListData(occupationList.getOccupationList().get(i).getId(), occupationList.getOccupationList().get(i).getName()));

                        }
                        occupation_spinner_data(occupationListData);
                    } else {


                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationList> call, Throwable t) {
//                progressBar.dismiss();

                System.out.println("err occupation******" + t.toString());

                if (!isNetworkAvailable(requireContext())) {
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
        adapter_occupation = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_item, occupation_list);
        adapter_occupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occupation_spinner.setAdapter(adapter_occupation);

        // Handle Spinner click to show searchable dialog
        occupation_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableOccupationDialog(occupation_list, occupationListData);
            }
            return true; // Consume the touch event
        });
    }

    private void showSearchableOccupationDialog(List<String> occupationList, List<OccupationList.OccupationListData> occupationListData) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                getContext(),
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
                    Toast.makeText(getContext(), "Error selecting occupation. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Error selecting occupation. Please try again.", Toast.LENGTH_SHORT).show();
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

                    TextView textView = new TextView(getContext());
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
                                occupation_pck_array_occupation_name_id.add(data.getId());
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
                    Toast.makeText(getContext(), "Already Added!!!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("OccupationDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(getContext(), "Error selecting occupation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    /*end occupation type and occupation*/


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
        sub_caste_adapter = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_item, sub_caste_list);
        sub_caste_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_caste_spinner.setAdapter(sub_caste_adapter);

        // Handle Spinner click to show searchable dialog
        sub_caste_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableSubCasteDialog(sub_caste_list);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for sub-caste selection
    private void showSearchableSubCasteDialog(List<String> subCasteList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                getContext(),
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
                    Toast.makeText(getContext(), "Error selecting sub-caste. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Error selecting sub-caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                sub_caste_str = selectedSubCaste;
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

                    subcaste_txtv = new TextView(getContext());
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
                        Toast.makeText(getContext(), "Sub-caste already added!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Error selecting sub-caste: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


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
        adapter_dietray = new ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, dietray_list);

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

        dietary_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                dietray_str = dietary_spinner.getItemAtPosition(position).toString();
                System.out.println("dietray_str------" + dietray_str);

                if (!dietray_str.equals("Select Dietary")) {
                    if (dietary_flexLL.getVisibility() == View.GONE) {
                        dietary_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    dietary_txtv = new TextView(requireContext());
                    dietary_txtv.setLayoutParams(params);
                    dietary_txtv.setTextSize(16);
                    dietary_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));

                    dietary_txtv.setPadding(20, 10, 20, 10);
                    dietary_txtv.setLayoutParams(params);
                    if (!dietray_str.equals(dietary_txtv.getText().toString())) {


                        dietary_txtv.setText(dietray_str.trim() + "     x ");

                        dietary_array.add(dietray_str.trim());


                    }


                    dietary_flex.addView(dietary_txtv);


                    final String myString = dietary_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    dietary_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    dietary_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) dietary_txtv.getText();

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {

                            TextView textView1 = (TextView) textView;

                            String charSequence = textView1.getText().toString().trim();

                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());


                            dietary_array.remove(charSequence);

                            for (int i = 0; i < dietary_array.size(); i++) {
                                System.out.println("array character name----------" + dietary_array.get(i));

                                if ((dietary_array.get(i).trim()).contains(a.trim())) {

                                    int id = dietary_array.indexOf(a.trim());
                                    System.out.println(" 11 array character name----------" + id);

                                    dietary_array.remove(id);
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
//                        office_country_spinner_data(countryListData);
                        country_spinner_data(countryListData);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<CountryList> call, Throwable t) {

                System.out.println("err country******" + t.toString());

                if (!isNetworkAvailable(requireContext())) {
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
        adapter_country = new ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, country_list);
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

    private void getOfficeDistrictList(String office_state_list_str) {
        if (office_state_list_str != null) {
            for (int i = 0; i < stateListsData.size(); i++) {
                if (office_state_list_str.equals(stateListsData.get(i).getSc_name())) {
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
//                        office_district_spinner_data(districtListData);
                    } else {
                    }
                }
            }
            @Override
            public void onFailure(Call<DistrictList> call, Throwable t) {
                // prog_district.(View.GONE);
                System.out.println("err district******" + t.toString());
                if (!isNetworkAvailable(requireContext())) {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(PartnerPreferencesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        adapter_state = new ArrayAdapter<>(getContext(),
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
        current_state_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableStateDialog(state_list);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for state selection
    private void showSearchableStateDialog(List<String> stateList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                getContext(),
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
                    Toast.makeText(getContext(), "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Error selecting state: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                if (!isNetworkAvailable(requireContext())) {
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
        district_adapter = new ArrayAdapter<>(getContext(),
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
        current_district_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableDistrictDialog(district_list);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for district selection
    private void showSearchableDistrictDialog(List<String> districtList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                getContext(),
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
                    Toast.makeText(getContext(), "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
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

                    district_txtv = new TextView(getContext());
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
                        Toast.makeText(getContext(), "District already added!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                if (!isNetworkAvailable(requireContext())) {
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
        mother_tongue_adapter = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_item, mother_tongue_list);
        mother_tongue_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mother_tounge_spinner.setAdapter(mother_tongue_adapter);

        // Handle Spinner click to show searchable dialog
        mother_tounge_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableMotherTongueDialog(mother_tongue_list);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for mother tongue selection
    private void showSearchableMotherTongueDialog(List<String> motherTongueList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                getContext(),
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
                    Toast.makeText(getContext(), "Error selecting mother tongue. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Error selecting mother tongue. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Already Added!!!", Toast.LENGTH_SHORT).show();
                } else if (!mother_tongue_str.equals("Select Mother Tongue")) {
                    if (mother_tongue_flexLL.getVisibility() == View.GONE) {
                        mother_tongue_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);

                    mother_tounge_txtv = new TextView(getContext());
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
                Toast.makeText(getContext(), "Error selecting mother tongue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


}

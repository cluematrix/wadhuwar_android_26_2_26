
package com.WadhuWarProject.WadhuWar.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.activity.SearchResultsActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.AgeList;
import com.WadhuWarProject.WadhuWar.model.CasteList;
import com.WadhuWarProject.WadhuWar.model.CountryList;
import com.WadhuWarProject.WadhuWar.model.DietaryList;
import com.WadhuWarProject.WadhuWar.model.DistrictList;
import com.WadhuWarProject.WadhuWar.model.EducationList;
import com.WadhuWarProject.WadhuWar.model.EducationType;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.HeightList;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesTabSearchFragment extends
        Fragment implements NetworkStateReceiver.NetworkStateReceiverListener {
    TextView textView2, textView1, textView3;
    CardView step1,step2,step3,step4;
    LinearLayout showLL,show1,show2,show3,show4,hide1,hide2,hide3,hide4, hideLL, showHideLL;
    LinearLayout occupationFlexBothLL;
    LinearLayout occupation_flexLL;
    RecyclerView occupation_type_old;
    String status_occupation_type = "0";
    String status_occupation = "0";
    LinearLayout educationFlexBothLL;
    LinearLayout education_flexLL;
    RecyclerView education_type_old;
    String status_education_type = "0";
    String status_education = "0";

    LinkedHashSet<String> listToSet_education, listToSet_education_type;
    LinkedHashSet<String> listToSet_occupation, listToSet_occupation_type;
    List<String> education_type_list = new ArrayList<String>();
    List<String> education_list = new ArrayList<String>();
    FlexboxLayout education_pckg_flex;
    LinearLayout education_type_flexLL;

    String package_id;

    String education_type_pckg_list, education_list_str, education_type_list_str, education_type_id, occupation_pckg_list, occupation_type_pckg_list,
            occupation_list_str, occupation_type_list_str, occupation_id, occupation_type_id, mother_tongue_pckg_list;

    TextView education_type_txtv;
    TextView occupation_type_txtv;
    TextView mother_tounge_txtv;

    ArrayAdapter<String> adapter_occupation;
    ArrayAdapter<String> adapter_occupation_type;
    ArrayAdapter<String> mother_tongue_adapter;
    LinkedHashSet<String> listToSet;
    LinkedHashSet<String> listToSet_mother_tongue;

    ArrayAdapter<String> adapter_education;
    ArrayAdapter<String> adapter_education_type;
    ArrayList<String> education_pck_array = new ArrayList<String>();
    ArrayList<String> education_pck_array_education_name_id = new ArrayList<String>();
    ArrayList<String> education_pck_array_id = new ArrayList<String>();
    FlexboxLayout education_type_flex;
    ArrayList<String> education_type_array = new ArrayList<String>();
    ArrayList<String> education_type_array_unique = new ArrayList<String>();
    ArrayList<String> education_type_array_id = new ArrayList<String>();
    ArrayList<String> occupation_pck_array = new ArrayList<String>();
    ArrayList<String> occupation_pck_array_occupation_name_id = new ArrayList<String>();
    ArrayList<String> occupation_pck_array_id = new ArrayList<String>();
    String occupation_package_id;
    LinkedHashSet<String> occupation_listToSet;
    FlexboxLayout occupation_type_flex;
    ArrayList<String> occupation_type_array = new ArrayList<String>();
    ArrayList<String> occupation_type_array_unique = new ArrayList<String>();
    ArrayList<String> occupation_type_array_id = new ArrayList<String>();
    ArrayList<String> mothertounge_array = new ArrayList<String>();
    ArrayList<String> mothertounge_array_id = new ArrayList<String>();

    LinkedHashSet<String> listToSet_sub_caste, listToSet_district;
    String subcaste_pckg_list, district_pckg_list, education_pckg_list;
    TextView subcaste_txtv, district_txtv, education_txtv, occupation_txtv;
    ArrayList<String> sub_caste_array = new ArrayList<String>();
    ArrayList<String> sub_caste_array_id = new ArrayList<String>();

    ArrayList<String> district_array = new ArrayList<String>();
    ArrayList<String> district_array_id = new ArrayList<String>();

    ArrayList<String> education_array = new ArrayList<String>();
    ArrayList<String> education_array_id = new ArrayList<String>();


    ArrayList<String> occupation_array = new ArrayList<String>();
    ArrayList<String> occupation_array_id = new ArrayList<String>();

    FlexboxLayout subcaste_flex, district_flex, education_flex, occupation_flex, mother_tongue_flex;
    LinearLayout subcaste_flexLL, district_flexLL, occupation_type_flexLL, mother_tongue_flexLL;

    static boolean isNetworkAvailable;
    UserData user;
    FetchProfile fetchProfile;
    private NetworkStateReceiver networkStateReceiver;
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout search_btn;
    String pckg_list;

    Spinner gender_spinner, marital_status_spinner, agefrom_spinner, height_spinner, height_spinner_to, sampraday_spinner, ageto_spinner, dietary_spinner, caste_spinner, sub_caste_spinner, religion_spinner,
            current_state_spinner, current_district_spinner, highest_education_spinner, occupation_spinner, manglik_spinner, handicap_spinner, salary_income_spinner,
            education_type_spinner, occupation_type_spinner, mother_tounge_spinner;

    ArrayList<EducationType.EducationTypeList> educationTypeLists = new ArrayList<>();
    EducationType educationType;

    ArrayList<EducationList.EducationListData> educationListData = new ArrayList<>();
    EducationList educationList;

    ArrayList<OccupationType.OccupationTypeData> occupationTypeLists = new ArrayList<>();
    OccupationType occupationType;


    ArrayList<OccupationList.OccupationListData> occupationListData = new ArrayList<>();
    OccupationList occupationList;

    List<String> occupation_type_list = new ArrayList<String>();

    List<String> occupation_list = new ArrayList<String>();
    FlexboxLayout occupation_pckg_flex;


    ArrayList<MaritalSettingList.MaritalSettingListData> maritalSettingListData = new ArrayList<>();
    MaritalSettingList maritalSettingList;
    ArrayList<AgeList.AgeListData> ageListDataTo = new ArrayList<>();
    AgeList ageListTo;
    ArrayList<AgeList.AgeListData> ageListDataFrom = new ArrayList<>();
    AgeList ageListFrom;
    ArrayList<HeightList.HeightListData> heightListDataFrom = new ArrayList<>();
    ArrayList<HeightList.HeightListData> heightListDataTo = new ArrayList<>();
    HeightList heightList;
    HeightList heightListTo;
    ArrayList<SampradayList.SampradayListData> sampradayListData = new ArrayList<>();
    SampradayList sampradayList;
    ArrayList<DietaryList.DietaryListData> dietaryListData = new ArrayList<>();
    DietaryList dietaryList;
    ArrayList<CasteList.CasteListData> casteListData = new ArrayList<>();
    CasteList casteList;
    ArrayList<SubCasteList> subCasteLists = new ArrayList<>();
    ArrayList<ReligionList.ReligionListData> religionListData = new ArrayList<>();
    ReligionList religionList;
    ArrayList<SalaryList.SalaryListData> salaryListData = new ArrayList<>();
    SalaryList salaryList;
    ArrayList<StateList> stateListsData = new ArrayList<>();
    StateList stateList;
    ArrayList<DistrictList.DistrictListData> districtListsData = new ArrayList<>();
    DistrictList districtList;
    ArrayList<CountryList.CountryListData> countryListData = new ArrayList<>();
    CountryList countryList;
    ArrayList<MothertoungeList.MothertoungeListData> mothertoungeListData = new ArrayList<>();
    MothertoungeList mothertoungeList;

    ArrayList<SearchData.SearchDataList> searchDataLists = new ArrayList<>();
    SearchData searchData;


    List<String> gender1;
    List<String> gender2;
    List<String> gender;
    List<String> marital_list = new ArrayList<String>();
    List<String> ageTo_list = new ArrayList<String>();
    List<String> ageFrom_list = new ArrayList<String>();
    List<String> height_list_from = new ArrayList<String>();
    List<String> height_list_to = new ArrayList<String>();
    List<String> sampraday_list = new ArrayList<String>();
    List<String> dietray_list = new ArrayList<String>();
    List<String> caste_list = new ArrayList<String>();
    List<String> sub_caste_list = new ArrayList<String>();
    List<String> religion_list = new ArrayList<String>();
    List<String> state_list = new ArrayList<String>();
    List<String> district_list = new ArrayList<String>();
    List<String> country_list = new ArrayList<String>();
    List<String> salary_list = new ArrayList<String>();
    List<String> occupational_list = new ArrayList<String>();
    List<String> occupational_type_list = new ArrayList<String>();
    List<String> mother_tongue_list = new ArrayList<String>();

    ProgressDialog progressBar;


    String gender_str = "", marital_str = "", ageTo_list_str = "", agefrom_list_str = "", height_str = "", height_to_str = "", sampraday_list_str = "", dietray_str = "", caste_list_str = "",
            sub_caste_str = "", religion_list_str = "",
            state_list_str = "", district_str = "", manglik_str = "", handicap_str = "", salary_str = "", mother_tongue_str = "";

    String maritalstatus_id, dietray_id, caste_id, subcaste_id, religion_id, district_id, state_id, country_id, education_id,
            marital_id, sampraday_id, mother_tongue_id;


    ProgressBar prog_caste, prog_district;

    ArrayAdapter<String> adapter_marital;
    ArrayAdapter<String> adapter_ageTo;
    ArrayAdapter<String> adapter_ageFrom;
    ArrayAdapter<String> height_adapter;
    ArrayAdapter<String> height_adapterTo;
    ArrayAdapter<String> adapter_sampraday;
    ArrayAdapter<String> adapter_dietray;
    ArrayAdapter<String> caste_adapter;
    ArrayAdapter<String> sub_caste_adapter;


    ArrayAdapter<String> adapter_religion;
    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> district_adapter;

    ArrayAdapter<String> manglik_adapter;
    ArrayAdapter<String> handicap_adapter;
    ArrayAdapter<String> salary_adapter;
    String[] manglik_list = {"Select Manglik or Not", "Yes", "No"};
    String[] handicap_list = {"Select Handicap", "Yes", "No"};

    EditText name;
    String _name;
    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;


    public static MatchesTabSearchFragment newInstance() {
        MatchesTabSearchFragment fragment = new MatchesTabSearchFragment();
        return fragment;
    }


    public MatchesTabSearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }
        setHasOptionsMenu(true);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_matches_tab_search, container, false);

        hideLL = view.findViewById(R.id.hideLL);
        showLL = view.findViewById(R.id.showLL);

        show1 = view.findViewById(R.id.show1);
        show2 = view.findViewById(R.id.show2);
        show3 = view.findViewById(R.id.show3);
        show4= view.findViewById(R.id.show4);

        hide1= view.findViewById(R.id.hide1);
        hide2= view.findViewById(R.id.hide2);
        hide3= view.findViewById(R.id.hide3);
        hide4= view.findViewById(R.id.hide4);

        step1= view.findViewById(R.id.step1);
        step2= view.findViewById(R.id.step2);
        step3= view.findViewById(R.id.step3);
        step4= view.findViewById(R.id.step4);

        showHideLL = view.findViewById(R.id.showHideLL);
        education_flexLL = view.findViewById(R.id.education_flexLL);
        educationFlexBothLL = view.findViewById(R.id.educationFlexBothLL);
        education_type_flexLL = view.findViewById(R.id.education_type_flexLL);
        education_type_flex = view.findViewById(R.id.education_type_flex);
        education_type_spinner = view.findViewById(R.id.education_type_spinner);
        highest_education_spinner = view.findViewById(R.id.highest_education_spinner);
        education_pckg_flex = (FlexboxLayout) view.findViewById(R.id.education_pckg_flex);

        occupationFlexBothLL = view.findViewById(R.id.occupationFlexBothLL);
        occupation_pckg_flex = view.findViewById(R.id.occupation_pckg_flex);
        occupation_flexLL = view.findViewById(R.id.occupation_flexLL);
        occupation_type_flex = view.findViewById(R.id.occupation_type_flex);
        occupation_type_flexLL = view.findViewById(R.id.occupation_type_flexLL);
        occupation_spinner = view.findViewById(R.id.occupation_spinner);
        occupation_type_spinner = view.findViewById(R.id.occupation_type_spinner);
        occupation_pckg_flex = (FlexboxLayout) view.findViewById(R.id.occupation_pckg_flex);
        height_spinner = view.findViewById(R.id.heightfrom_spinner);
        height_spinner_to = view.findViewById(R.id.heightto_spinner);

        education_type_flexLL = view.findViewById(R.id.education_type_flexLL);
        education_type_spinner = view.findViewById(R.id.education_type_spinner);

        occupation_flexLL = view.findViewById(R.id.occupation_flexLL);

        occupation_type_flex = view.findViewById(R.id.occupation_type_flex);

        education_flexLL = view.findViewById(R.id.education_flexLL);

        district_flex = view.findViewById(R.id.district_flex);
        district_flexLL = view.findViewById(R.id.district_flexLL);

        mother_tounge_spinner = view.findViewById(R.id.mother_tounge_spinner);
        mother_tongue_flex = view.findViewById(R.id.mother_tongue_flex);
        mother_tongue_flexLL = view.findViewById(R.id.mother_tongue_flexLL);

        subcaste_flex = view.findViewById(R.id.subcaste_flex);
        subcaste_flexLL = view.findViewById(R.id.subcaste_flexLL);
        prog_district = view.findViewById(R.id.prog_district);
        prog_caste = view.findViewById(R.id.prog_caste);
        gender_spinner = (Spinner) view.findViewById(R.id.gender_spinner);
        marital_status_spinner = (Spinner) view.findViewById(R.id.marital_status_spinner);
        agefrom_spinner = (Spinner) view.findViewById(R.id.agefrom_spinner);
        ageto_spinner = view.findViewById(R.id.ageto_spinner);
        height_spinner = (Spinner) view.findViewById(R.id.heightfrom_spinner);
        height_spinner_to = view.findViewById(R.id.heightto_spinner);
        sampraday_spinner = view.findViewById(R.id.sampraday_spinner);
        dietary_spinner = view.findViewById(R.id.dietary_spinner);
        caste_spinner = view.findViewById(R.id.caste_spinner);
        sub_caste_spinner = view.findViewById(R.id.sub_caste_spinner);
        religion_spinner = view.findViewById(R.id.religion_spinner);
        current_state_spinner = view.findViewById(R.id.current_state_spinner);
        current_district_spinner = view.findViewById(R.id.current_district_spinner);
        highest_education_spinner = view.findViewById(R.id.highest_education_spinner);
        occupation_spinner = view.findViewById(R.id.occupation_spinner);
        occupation_type_spinner = view.findViewById(R.id.occupation_type_spinner);
        manglik_spinner = view.findViewById(R.id.manglik_spinner);
        handicap_spinner = view.findViewById(R.id.handicap_spinner);
        salary_income_spinner = view.findViewById(R.id.salary_income_spinner);
        name = view.findViewById(R.id.name);
        search_btn = view.findViewById(R.id.search_btn);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(false);
        internetOffRL = (RelativeLayout) view.findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) view.findViewById(R.id.simpleProgressBar);

        try_again_txt = (TextView) view.findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) view.findViewById(R.id.couldnt_reach_internet_txt);
        user = SharedPrefManager.getInstance(getActivity()).getUser();

        /*for other*/
        gender = new ArrayList<String>();

        gender.add("Male");
        gender.add("Female");
        gender.add("Others");

        /*for female*/
        gender1 = new ArrayList<String>();

        gender1.add("Male");
        gender1.add("Others");


        /*for male*/
        gender2 = new ArrayList<String>();
        gender2.add("Female");
        gender2.add("Others");


        showLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLL.setVisibility(View.GONE);
                hideLL.setVisibility(View.VISIBLE);
                showHideLL.setVisibility(View.VISIBLE);

            }
        });


        show1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show1.setVisibility(View.GONE);
                hide1.setVisibility(View.VISIBLE);
                step1.setVisibility(View.VISIBLE);
                show2.setVisibility(View.VISIBLE);

            }
        });
        hide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide1.setVisibility(View.GONE);
                hide2.setVisibility(View.GONE);
                hide3.setVisibility(View.GONE);
                hide4.setVisibility(View.GONE);
                show1.setVisibility(View.VISIBLE);
                show2.setVisibility(View.GONE);
                show3.setVisibility(View.GONE);
                show4.setVisibility(View.GONE);
                step1.setVisibility(View.GONE);
                step2.setVisibility(View.GONE);
                step3.setVisibility(View.GONE);
                step4.setVisibility(View.GONE);
            }
        });


        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show2.setVisibility(View.GONE);
                hide2.setVisibility(View.VISIBLE);
                step2.setVisibility(View.VISIBLE);
                show3.setVisibility(View.VISIBLE);

            }
        });
        hide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide2.setVisibility(View.GONE);
                hide3.setVisibility(View.GONE);
                hide4.setVisibility(View.GONE);
                show2.setVisibility(View.VISIBLE);
                step2.setVisibility(View.GONE);
                step3.setVisibility(View.GONE);
                step4.setVisibility(View.GONE);
                show3.setVisibility(View.GONE);
                show4.setVisibility(View.GONE);

            }
        });


        show3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show3.setVisibility(View.GONE);
                hide3.setVisibility(View.VISIBLE);
                step3.setVisibility(View.VISIBLE);
                show4.setVisibility(View.VISIBLE);

            }
        });
        hide3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide3.setVisibility(View.GONE);
                hide4.setVisibility(View.GONE);
                show3.setVisibility(View.VISIBLE);
                step3.setVisibility(View.GONE);
                step4.setVisibility(View.GONE);
                show4.setVisibility(View.GONE);

            }
        });


        show4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show4.setVisibility(View.GONE);
                hide4.setVisibility(View.VISIBLE);
                step4.setVisibility(View.VISIBLE);

            }
        });
        hide4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide4.setVisibility(View.GONE);
                show4.setVisibility(View.VISIBLE);
                step4.setVisibility(View.GONE);

            }
        });










        hideLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLL.setVisibility(View.GONE);
                showLL.setVisibility(View.VISIBLE);
                showHideLL.setVisibility(View.GONE);

            }
        });


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchProfileData();

                _name = name.getText().toString();

                /*if (education_list_str != null && !education_list_str.contentEquals("")) {
                    for (int i = 0; i < educationListData.size(); i++) {
                        if (education_list_str.equals(educationListData.get(i).getName())) {
                            education_id = String.valueOf(educationListData.get(i).getId());
                        }
                    }
                } else {
                    education_id = "";
                }*/


                /*education type list */
                education_type_array_id.clear();
                for (int j = 0; j < education_type_array.size(); j++) {
                    System.out.println("111 character name----------" + education_type_array.get(j));


                    for (int i = 0; i < educationTypeLists.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

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

                System.out.println("state_list_str============-------------" + state_list_str);


                if (state_list_str != null && !state_list_str.contentEquals("")) {
                    if (!state_list_str.equalsIgnoreCase("Select State")) {
                        for (int i = 0; i < stateListsData.size(); i++) {
                            System.out.println("stateListsData============-------------" + new Gson().toJson(stateListsData));

                            if (state_list_str.equals(stateListsData.get(i).getSc_name())) {
                                state_id = String.valueOf(stateListsData.get(i).getSc_id());
                            }
                        }
                    } else {
                        state_id = "";

                    }
                } else {
                    state_id = "";

                }


                /*if (district_str != null && !district_str.contentEquals("")) {
                    for (int i = 0; i < districtListsData.size(); i++) {
                        if (district_str.equals(districtListsData.get(i).getName())) {
                            district_id = String.valueOf(districtListsData.get(i).getId());

                        }
                    }
                } else {
                    district_id = "";

                }*/

                /*district list */
                district_array_id.clear();
                for (int j = 0; j < district_array.size(); j++) {
                    System.out.println("111 character name----------" + district_array.get(j));


                    for (int i = 0; i < districtListsData.size(); i++) {

//                            System.out.println("list  name>>>>>>>>>>>>>>>" + packages.get(i).getName());

                        if (district_array.get(j).equals(districtListsData.get(i).getName())) {


                            district_id = String.valueOf(districtListsData.get(i).getId());

                            district_array_id.add(district_id);


                            System.out.println("packages id 111----------" + districtListsData);
                            System.out.println("222 character name----------" + districtListsData.get(i).getName());


                        }
                    }


                }

                for (int i = 0; i < district_array_id.size(); i++) {
                    System.out.println("packages id 000----------" + district_array_id.get(i));

                }


                listToSet_district = new LinkedHashSet<String>(district_array_id);

                StringBuilder district_sbString = new StringBuilder("");

                for (String language : listToSet_district) {

                    district_sbString.append(language).append(",");
                }

                district_pckg_list = district_sbString.toString();
                if (district_pckg_list.length() > 0)
                    district_pckg_list = district_pckg_list.substring(0, district_pckg_list.length() - 1);


                System.out.println("district>>>>>>>>>>>>>>>" + district_pckg_list);
                /*end district list*/




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

                System.out.println("marital_str---------" + marital_str);
                if (marital_str != null && !marital_str.contentEquals("")) {
                    if (!marital_str.equalsIgnoreCase("Select Marital Status")) {
                        for (int i = 0; i < maritalSettingListData.size(); i++) {
                            if (marital_str.equals(maritalSettingListData.get(i).getName())) {
                                marital_id = String.valueOf(maritalSettingListData.get(i).getId());
                            }
                        }
                    } else {
                        marital_id = "";
                    }
                } else {
                    marital_id = "";

                }

                if (dietray_str != null && !dietray_str.contentEquals("")) {
                    for (int i = 0; i < dietaryListData.size(); i++) {
                        if (dietray_str.equals(dietaryListData.get(i).getName())) {
                            dietray_id = String.valueOf(dietaryListData.get(i).getId());

                        }
                    }
                } else {
                    dietray_id = "";

                }

                if (religion_list_str != null) {
                    for (int i = 0; i < religionListData.size(); i++) {
                        if (religion_list_str.equals(religionListData.get(i).getName())) {
                            religion_id = String.valueOf(religionListData.get(i).getId());
                        }
                    }
                } else {
                    religion_id = "";
                }

                System.out.println("caste_list_str--------------" + caste_list_str);

                if (caste_list_str != null && !caste_list_str.contentEquals("")) {
                    System.out.println("aaaaaaaaaaa---------------");
                    if (!caste_list_str.equalsIgnoreCase("Select Caste")) {
                        System.out.println("bbbbbbbbbb---------------");
                        for (int i = 0; i < casteListData.size(); i++) {
                            System.out.println("caste_list_strcaste_list_str---------------" + caste_list_str);
                            System.out.println("casteListData.get(i).getName()---------------" + casteListData.get(i).getName());
                            if (caste_list_str.contentEquals(casteListData.get(i).getName())) {
                                System.out.println("gggggggggggg---------------");
                                caste_id = String.valueOf(casteListData.get(i).getId());
                            }
                        }
                    }
                }

                System.out.println("caste_idcaste_idcaste_id==============" + caste_id);

                /*if (sub_caste_str != null && !sub_caste_str.contentEquals("")) {
                    for (int i = 0; i < subCasteLists.size(); i++) {
                        if (sub_caste_str.equals(subCasteLists.get(i).getSc_name())) {
                            subcaste_id = String.valueOf(subCasteLists.get(i).getSc_id());

                        }
                    }
                } else {
                    subcaste_id = "";
                }*/

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

                if (sampraday_list_str != null && !sampraday_list_str.contentEquals("")) {
                    for (int i = 0; i < sampradayListData.size(); i++) {
                        if (sampraday_list_str.equals(sampradayListData.get(i).getName())) {
                            sampraday_id = String.valueOf(sampradayListData.get(i).getId());

                        }
                    }
                } else {
                    sampraday_id = "";

                }
                if (manglik_str.equals("Select Manglik or Not")) {
                    manglik_str = "";
                }


                if (handicap_str.equals("Select Handicap")) {
                    handicap_str = "";
                }
                if (agefrom_list_str.equals("Select Age")) {
                    agefrom_list_str = "";
                }
                if (ageTo_list_str.equals("Select Age")) {
                    ageTo_list_str = "";
                }
                if (checkValidation()) {

                    if (!isNetworkAvailable(getActivity())) {

                        Snackbar mSnackbar = Snackbar.make(view, "Please Check Internet Connection!", Snackbar.LENGTH_LONG);
                        View mView = mSnackbar.getView();
                        TextView mTextView = (TextView) mView.findViewById(R.id.snackbar_text);
                        mTextView.setBackgroundColor(Color.RED);
                        mTextView.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        else
                            mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                        mSnackbar.show();


                    } else {


                        /*submitData(String.valueOf(user.getUser_id()), _name, gender_str, marital_id, agefrom_list_str, ageTo_list_str,
                                religion_id, caste_id, subcaste_id, state_id, district_id, education_id, occupation_id, dietray_id,
                                sampraday_id, manglik_str, handicap_str);*/

                        // education_type_pckg_list = education type
                        //pckg_list = education list
                        //occupation_type_pckg_list = occupation type
                        //occupation_pckg_list = occupation list

                        submitData(String.valueOf(user.getUser_id()), _name, gender_str, marital_id, agefrom_list_str, ageTo_list_str,
                                religion_id, caste_id, subcaste_pckg_list, state_id, district_pckg_list, pckg_list, occupation_pckg_list, dietray_id,
                                sampraday_id, manglik_str, handicap_str, education_type_pckg_list,
                                occupation_type_pckg_list,mother_tongue_pckg_list);
                    }

                }
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

        fetchProfileData();


        getMaritalStatusList();
        getAgeListFrom();
        getAgeListTo();
        getHeightListFrom();
        getHeightListTo();
        getSampradayList();
        getDietaryList();
        getReligionList();
        getCountryList();
        getEducationTypeList();
        getOccupationTypeList();
        getManglik();
        getHandicapList();
        getSalaryList();
        getMotherToungeList();

//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                education_pck_array.clear();
//                education_pckg_flex.removeAllViews();
//                education_pck_array_education_name_id.clear();
//                highest_education_spinner.setAdapter(null);
//                education_type_array.clear();
//                education_type_flex.removeAllViews();
//                education_type_flexLL.setVisibility(View.GONE);
//
//                occupation_pck_array.clear();
//                occupation_pckg_flex.removeAllViews();
//                occupation_pck_array_occupation_name_id.clear();
//                occupation_spinner.setAdapter(null);
//                occupation_type_array.clear();
//                occupation_type_flex.removeAllViews();
//                occupation_type_flexLL.setVisibility(View.GONE);
//
//
//                district_array.clear();
//                district_flex.removeAllViews();
//                district_flexLL.setVisibility(View.GONE);
//
//                sub_caste_array.clear();
//                subcaste_flex.removeAllViews();
//                subcaste_flexLL.setVisibility(View.GONE);
//
//                mothertounge_array_id.clear();
//
//                mothertounge_array.clear();
//                mother_tongue_flex.removeAllViews();
//                mother_tongue_flexLL.setVisibility(View.GONE);
//
//
//                if (casteListData != null)
//                    casteListData.clear();
//                if (subCasteLists != null)
//                    subCasteLists.clear();
//                if (stateListsData != null)
//                    stateListsData.clear();
//                if (districtListsData != null)
//                    districtListsData.clear();
//
//                getMaritalStatusList();
//                getAgeListFrom();
//                getAgeListTo();
//                getSampradayList();
//                getDietaryList();
//                getReligionList();
//                getCountryList();
//                getEducationTypeList();
//                getOccupationTypeList();
//                getManglik();
//                getHandicapList();
//                getSalaryList();
//                getMotherToungeList();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

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
//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Toast.makeText(getActivity(), "Please check internet connection!", Toast.LENGTH_SHORT).show();
//
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

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

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }


    public void fetchProfileData() {

        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfile = response.body();
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance(getContext()).saveProfileData(fetchProfile);


                    if (fetchProfile.getResid().equals("200")) {

                        if (fetchProfile.getGender().equals("Male")) {

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gender2);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            gender_spinner.setAdapter(dataAdapter);
                            gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                    gender_str = gender_spinner.getItemAtPosition(position).toString();

                                    System.out.println("gender_str------" + gender_str);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } else if (fetchProfile.getGender().equals("Female")) {

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gender1);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            gender_spinner.setAdapter(dataAdapter);
                            gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                    gender_str = gender_spinner.getItemAtPosition(position).toString();

                                    System.out.println("gender_str------" + gender_str);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } else {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gender);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            gender_spinner.setAdapter(dataAdapter);
                            gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                    gender_str = gender_spinner.getItemAtPosition(position).toString();

                                    System.out.println("gender_str------" + gender_str);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my search profie suer login******" + t.toString());

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void submitData(String user_id, String _name, String gender_str, String marital_id, String agefrom_list_str,
                           String ageTo_list_str, String religion_id, String caste_id, String subcaste_id,
                           String state_id, String district_id, String education_id, String occupation_id,
                           String dietray_id, String sampraday_id, String manglik_str, String handicap_str,
                           String education_type_id, String occupation_type_id, String mother_tongue_pckg_list) {


        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait...");


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
        Call<SearchData> userResponse = apiService.searchProfile(gender_str.trim(), user_id, agefrom_list_str, ageTo_list_str,
                religion_id, caste_id, subcaste_id, education_id, occupation_id, state_id, district_id, marital_id, _name,
                dietray_id, sampraday_id, manglik_str, handicap_str, "10", "1",
                education_type_id, occupation_type_id, height_str, height_to_str, salary_str, mother_tongue_pckg_list);
        userResponse.enqueue(new Callback<SearchData>() {

            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                System.out.println("res-------------" + new Gson().toJson(response.body()));
                searchData = response.body();
                progressBar.dismiss();

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


//                        Toast.makeText(getActivity(),searchData.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {

                System.out.println("err basic info******" + t.toString());
                progressBar.dismiss();

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    private Boolean checkValidation() {

        if (String.valueOf(user.getUser_id()) == null) {
            Toast.makeText(getActivity(), "Please Refresh", Toast.LENGTH_SHORT).show();
            return false;
        }
        View selectedView_gen = gender_spinner.getSelectedView();
        if (selectedView_gen != null && selectedView_gen instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_gen;
            if (selectedTextView.getText().equals("Select Gender")) {
                selectedTextView.setError("Please Select Gender");
                gender_spinner.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }

        }

        return true;
    }


    public void getManglik() {

        manglik_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, manglik_list);

        manglik_spinner.setAdapter(manglik_adapter);

        manglik_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                manglik_str = manglik_spinner.getItemAtPosition(position).toString();
                System.out.println("manglik_list------" + manglik_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void getHandicapList() {

        handicap_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, handicap_list);

        handicap_spinner.setAdapter(handicap_adapter);

        handicap_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                handicap_str = handicap_spinner.getItemAtPosition(position).toString();
                System.out.println("manglik_list------" + handicap_str);


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
                        salary_income_spinner_data(salaryListData);
                    } else {

                    }
                } else {
                    System.out.println("err salaryList******" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SalaryList> call, Throwable t) {

                System.out.println("err rel******" + t.toString());
                if (!isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void salary_income_spinner_data(ArrayList<SalaryList.SalaryListData> salaryListData) {

        salary_list.clear();
        System.out.println("list_salary-----------================");

        salary_list.add("Select Income");
        salary_list.add("Doesn't Matter");

        if (salary_list.size() != 0) {
            for (int i = 0; i < salaryListData.size(); i++) {

                salary_list.add(i + 2, salaryListData.get(i).getName());

            }
        }


        salary_adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, salary_list);

        salary_income_spinner.setAdapter(salary_adapter);

        salary_income_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                salary_str = salary_income_spinner.getItemAtPosition(position).toString();
                System.out.println("salary_str------" + salary_str);
//                for_state_spinner_data(religion_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void country_spinner_data(List<CountryList.CountryListData> countryListData) {

        country_list.clear();


        for (int i = 0; i < countryListData.size(); i++) {

            country_list.add(i, countryListData.get(i).getName());

        }

        for_state_spinner_data();


    }

    public void for_state_spinner_data() {
        stateListsData.clear();
        /*---------*/

        country_id = "1";

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

        if (state_list.size() != 0) {
            for (int i = 0; i < stateLists.size(); i++) {

                state_list.add(i + 1, stateLists.get(i).getSc_name());

            }
        }


        adapter_state = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, state_list);

        current_state_spinner.setAdapter(adapter_state);

        current_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                state_list_str = current_state_spinner.getItemAtPosition(position).toString();

                System.out.println("state_list_str------" + state_list_str);

                getDistrictList(state_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void getDistrictList(String state_list_str) {

        if (state_list_str != null) {
            for (int i = 0; i < stateListsData.size(); i++) {
                if (state_list_str.equals(stateListsData.get(i).getSc_name())) {
                    state_id = stateListsData.get(i).getSc_id().toString();
                    Log.d("TAG", "getDistrictList: "+state_id);

                }
            }
        }

        System.out.println("state_id--------" + state_id);

        districtListsData.clear();

        prog_district.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<DistrictList> userResponse = apiService.districtList("13", state_id);
        userResponse.enqueue(new Callback<DistrictList>() {

            @Override
            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
                districtList = response.body();

                if (response.isSuccessful()) {


                    prog_district.setVisibility(View.GONE);

                    String success = districtList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < districtList.getDistrictList().size(); i++) {
                            districtListsData.add(new DistrictList.DistrictListData(districtList.getDistrictList().get(i).getId(), districtList.getDistrictList().get(i).getName(), districtList.getDistrictList().get(i).getState_id()));
                        }
                        district_spinner_data(districtListsData);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<DistrictList> call, Throwable t) {
                prog_district.setVisibility(View.GONE);
                System.out.println("err district******" + t.toString());
                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void district_spinner_data(List<DistrictList.DistrictListData> districtListData) {

        district_list.clear();

        district_list.add("Select District");

        if (district_list.size() != 0) {
            for (int i = 0; i < districtListData.size(); i++) {

                district_list.add(i + 1, districtListData.get(i).getName().trim());

            }
        }


        district_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, district_list);

        current_district_spinner.setAdapter(district_adapter);


        current_district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                district_str = current_district_spinner.getItemAtPosition(position).toString();
                System.out.println("district_str------" + district_str);


                if (!district_str.equals("Select District")) {
                    if (district_flexLL.getVisibility() == View.GONE) {
                        district_flexLL.setVisibility(View.VISIBLE);
                    } else{

                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    district_txtv = new TextView(getActivity());
                    district_txtv.setLayoutParams(params);
                    district_txtv.setTextSize(16);
                    district_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));

                    district_txtv.setPadding(20, 10, 20, 10);
                    district_txtv.setLayoutParams(params);
                    if (!district_str.equals(district_txtv.getText().toString())) {


                        district_txtv.setText(district_str.trim() + "     x ");

                        district_array.add(district_str.trim());


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


                            district_array.remove(charSequence);

                            for (int i = 0; i < district_array.size(); i++) {
                                System.out.println("array character name----------" + district_array.get(i));

                                if ((district_array.get(i).trim()).contains(a.trim())) {

                                    int id = district_array.indexOf(a.trim());
                                    System.out.println(" 11 array character name----------" + id);

                                    district_array.remove(id);
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
                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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


        adapter_religion = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, religion_list);

        religion_spinner.setAdapter(adapter_religion);

        religion_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                religion_list_str = religion_spinner.getItemAtPosition(position).toString();
                System.out.println("religion_list_str------" + religion_list_str);

                if (!religion_list_str.contentEquals("Select Religion")) {
                    getCastList(religion_list_str);
                } else {
                    //  Toast.makeText(getActivity(), "please select religion", Toast.LENGTH_SHORT).show();
                }


//                for_state_spinner_data(religion_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getCastList(String religion_list_str) {

        prog_caste.setVisibility(View.VISIBLE);

        if (religion_list_str != null) {
            for (int i = 0; i < religionListData.size(); i++) {

                System.out.println("religion_list_str=============================---" + religion_list_str);
                System.out.println("religionListData.get(i).getName()============" + religionListData.get(i).getName());
                if (religion_list_str.contentEquals(religionListData.get(i).getName())) {
                    religion_id = String.valueOf(religionListData.get(i).getId());
                }
            }
        }

        System.out.println("religion_id--------" + religion_id);

        casteListData.clear();
        caste_id = "";


        Api apiService = RetrofitClient.getApiService();
        Call<CasteList> userResponse = apiService.casteList("06", religion_id);
        userResponse.enqueue(new Callback<CasteList>() {
            @Override
            public void onResponse(Call<CasteList> call, Response<CasteList> response) {
                casteList = response.body();
                prog_caste.setVisibility(View.GONE);

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
                if (!isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    /*{

        prog_caste.setVisibility(View.VISIBLE);

        if (religion_list_str != null) {
            for (int i = 0; i < religionListData.size(); i++) {
                System.out.println("religion_list_str===============" + religion_list_str);
                System.out.println("religionListData===============" + religionListData.get(i).getName());
                System.out.println("religionListData_getId===============" + religionListData.get(i).getId());

                if (religion_list_str.trim().equals(religionListData.get(i).getName().trim())) {
                    religion_id = String.valueOf(religionListData.get(i).getId());
                } else {
                    religion_id = "";

                }
            }
            System.out.println("religion_id--------" + religion_id);

        }


        casteListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<CasteList> userResponse = apiService.casteList("06", religion_id);
        userResponse.enqueue(new Callback<CasteList>() {

            @Override
            public void onResponse(Call<CasteList> call, Response<CasteList> response) {
                casteList = response.body();
                prog_caste.setVisibility(View.GONE);

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
                prog_caste.setVisibility(View.GONE);

                System.out.println("err caste******" + t.toString());
                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }*/

    public void caste_spinner_data(List<CasteList.CasteListData> casteListData) {

        caste_list.clear();

        caste_list.add("Select Caste");
        caste_list.add("Doesn't Matter");

        subCasteLists.clear();
        sub_caste_list.clear();
        subcaste_flex.removeAllViews();

        subCasteLists = new ArrayList<>();
        sub_caste_list = new ArrayList<>();

        sub_caste_list.add("Select Sub-Caste");
        sub_caste_list.add("Doesn't Matter");

        sub_caste_spinner.setAdapter(null);

        if (caste_list.size() != 0) {
            for (int i = 0; i < casteListData.size(); i++) {

                caste_list.add(i + 2, casteListData.get(i).getName());

            }
        }


        caste_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, caste_list);

        caste_spinner.setAdapter(caste_adapter);

        caste_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                caste_list_str = caste_spinner.getItemAtPosition(position).toString();
                System.out.println("caste_list_str------" + caste_list_str);

                if (!caste_list_str.contentEquals("Select Caste")) {
                    for_caste_spinner_data(caste_list_str);
                } else {
                    // Toast.makeText(getActivity(), "please select Caste", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void for_caste_spinner_data(String caste_list_str) {
        subCasteLists.clear();
        /*---------*/

        if (caste_list_str != null) {
            for (int i = 0; i < casteListData.size(); i++) {
                System.out.println("caste_list_str=-----------" + caste_list_str);
                System.out.println("casteListData.get(i).getName()=-----------" + casteListData.get(i).getName());

                if (caste_list_str.contentEquals(casteListData.get(i).getName())) {
                    caste_id = String.valueOf(casteListData.get(i).getId());
                    System.out.println("000000000000--------------");
                } else {
                    // caste_id = "";
                }
            }
        }

        System.out.println("caste_idcaste_id============" + caste_id);

        for (int i = 0; i < casteListData.size(); i++) {
            if (casteListData.get(i).getSubCasteList() != null) {

                for (int j = 0; j < casteListData.get(i).getSubCasteList().size(); j++) {


                    if (caste_id != null) {

                        if (caste_id.contentEquals(casteListData.get(i).getSubCasteList().get(j).getC_id())) {
                            subCasteLists.add(new SubCasteList(casteListData.get(i).getSubCasteList().get(j).getC_id(), casteListData.get(i).getSubCasteList().get(j).getSc_id(), casteListData.get(i).getSubCasteList().get(j).getSc_name()));
                        }

                    } else {
                        caste_id = "";
                    }

                    subCaste_spinner_data(subCasteLists);

                }
            }
        }

    }

    public void subCaste_spinner_data(List<SubCasteList> subCasteLists) {

        sub_caste_list.clear();
        subcaste_flex.removeAllViews();

        sub_caste_list.add("Select Sub-Caste");
        sub_caste_list.add("Doesn't Matter");

        System.out.println("subCasteLists------------" + new Gson().toJson(subCasteLists));
        System.out.println("castid------------=========" + caste_id);

        if (sub_caste_list.size() != 0) {
            for (int i = 0; i < subCasteLists.size(); i++) {

                sub_caste_list.add(i + 2, subCasteLists.get(i).getSc_name().trim());

            }
        }


        sub_caste_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, sub_caste_list);

        sub_caste_spinner.setAdapter(sub_caste_adapter);


        sub_caste_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                sub_caste_str = sub_caste_spinner.getItemAtPosition(position).toString();

                System.out.println("sub_caste_str------" + sub_caste_str);


                if (!sub_caste_str.equals("Select Sub-Caste")) {
                    if (subcaste_flexLL.getVisibility() == View.GONE) {
                        subcaste_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    subcaste_txtv = new TextView(getActivity());
                    subcaste_txtv.setLayoutParams(params);
                    subcaste_txtv.setTextSize(16);
                    subcaste_txtv.setBackgroundColor(Color.parseColor("#110A0A0A"));

                    subcaste_txtv.setPadding(20, 10, 20, 10);
                    subcaste_txtv.setLayoutParams(params);
                    if (!sub_caste_str.equals(subcaste_txtv.getText().toString())) {


                        subcaste_txtv.setText(sub_caste_str.trim() + "     x ");

                        sub_caste_array.add(sub_caste_str.trim());


                    }
                    System.out.println("subcaste_txtv-----------" + subcaste_txtv);

                    subcaste_flex.addView(subcaste_txtv);


                    final String myString = subcaste_txtv.getText().toString();
                    int i1 = myString.indexOf("x ");
                    subcaste_txtv.setMovementMethod(LinkMovementMethod.getInstance());
                    subcaste_txtv.setText(myString, TextView.BufferType.SPANNABLE);
                    Spannable mySpannable = (Spannable) subcaste_txtv.getText();

                    sub_caste_array.remove(mySpannable);

                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);

                    ClickableSpan myClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {

                            TextView textView1 = (TextView) textView;

                            String charSequence = textView1.getText().toString().trim();

                            String a = charSequence.substring(0, charSequence.length() - 2);

                            System.out.println("character name----------" + a);
                            System.out.println("final character name----------" + a.trim());


                            sub_caste_array.remove(charSequence);

                            for (int i = 0; i < sub_caste_array.size(); i++) {
                                System.out.println("array character name----------" + sub_caste_array.get(i));

                                if ((sub_caste_array.get(i).trim()).contains(a.trim())) {

                                    int id = sub_caste_array.indexOf(a.trim());
                                    System.out.println(" 11 array character name----------" + id);

                                    sub_caste_array.remove(id);
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


    public void getDietaryList() {

        dietaryListData.clear();

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
                            dietaryListData.add(new DietaryList.DietaryListData(dietaryList.getDietaryList().get(i).getId(), dietaryList.getDietaryList().get(i).getName()));

                        }
                        dietary_spinner_data(dietaryListData);
                    } else {


                    }
                }
            }

            @Override
            public void onFailure(Call<DietaryList> call, Throwable t) {

                System.out.println("err dietary******" + t.toString());

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void dietary_spinner_data(List<DietaryList.DietaryListData> dietaryListData) {

        dietray_list.clear();

        dietray_list.add("Select Dietary");
        dietray_list.add("Doesn't Matter");

        if (dietray_list.size() != 0) {
            for (int i = 0; i < dietaryListData.size(); i++) {

                dietray_list.add(i + 2, dietaryListData.get(i).getName());

            }
        }
        adapter_dietray = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, dietray_list);

        dietary_spinner.setAdapter(adapter_dietray);

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

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_sampraday = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, sampraday_list);

        sampraday_spinner.setAdapter(adapter_sampraday);

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

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_ageFrom = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, ageFrom_list);

        agefrom_spinner.setAdapter(adapter_ageFrom);

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

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_ageTo = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, ageTo_list);

        ageto_spinner.setAdapter(adapter_ageTo);

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

                if (!isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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

                if (!isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void height_spinner_data_from(List<HeightList.HeightListData> heightListData) {

        height_list_from.clear();

        height_list_from.add("Select Height From");
        height_list_from.add("Doesn't Matter");

        if (height_list_from.size() != 0) {
            for (int i = 0; i < heightListData.size(); i++) {

                height_list_from.add(i + 2, heightListData.get(i).getName());

            }
        }
        height_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, height_list_from);

        height_spinner.setAdapter(height_adapter);

        height_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                height_str = height_spinner.getItemAtPosition(position).toString();

                if (height_str.equalsIgnoreCase("Select Height From")) {
                    height_str = "";
                } else {
                    height_str = height_spinner.getItemAtPosition(position).toString();
                }

                System.out.println("height_str------" + height_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void height_spinner_data_to(List<HeightList.HeightListData> heightListData) {

        height_list_to.clear();

        height_list_to.add("Select Height To");
        height_list_to.add("Doesn't Matter");

        if (height_list_to.size() != 0) {
            for (int i = 0; i < heightListData.size(); i++) {

                height_list_to.add(i + 2, heightListData.get(i).getName());

            }
        }
        height_adapterTo = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, height_list_to);

        height_spinner_to.setAdapter(height_adapterTo);

        height_spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                height_to_str = height_spinner_to.getItemAtPosition(position).toString();

                if (height_to_str.equalsIgnoreCase("Select Height To")) {
                    height_to_str = "";
                } else {
                    height_to_str = height_spinner_to.getItemAtPosition(position).toString();
                }

                System.out.println("height_to_str------" + height_to_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void getMaritalStatusList() {

        maritalSettingListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<MaritalSettingList> userResponse = apiService.maritalSetting("17");
        userResponse.enqueue(new Callback<MaritalSettingList>() {

            @Override
            public void onResponse(Call<MaritalSettingList> call, Response<MaritalSettingList> response) {

//                swipeRefreshLayout.setRefreshing(false);

                maritalSettingList = response.body();

                if (response.isSuccessful()) {


                    String success = maritalSettingList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < maritalSettingList.getMarital_Setting().size(); i++) {
                            maritalSettingListData.add(new MaritalSettingList.MaritalSettingListData(maritalSettingList.getMarital_Setting().get(i).getId(), maritalSettingList.getMarital_Setting().get(i).getName()));

                        }
                        marital_spinner_data(maritalSettingListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<MaritalSettingList> call, Throwable t) {

                System.out.println("err marital******" + t.toString());
//                swipeRefreshLayout.setRefreshing(false);

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_marital = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, marital_list);

        marital_status_spinner.setAdapter(adapter_marital);

        marital_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                marital_str = marital_status_spinner.getItemAtPosition(position).toString();


                System.out.println("marital_str------" + marital_str);

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

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_education_type = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, education_type_list);

        education_type_spinner.setAdapter(adapter_education_type);

        /*----------*/
        int pos;


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
                        System.out.println("visibility===========");

                        education_type_flexLL.setVisibility(View.VISIBLE);
                    }


                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 5, 10, 5);


                    education_type_txtv = new TextView(getActivity());
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
//        highest_education_spinner.setSelection(0)
        highest_education_spinner.setAdapter(null);


        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait...");

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

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

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

        if (education_list.size() != 0) {
            for (int i = 0; i < educationListData.size(); i++) {

                education_list.add(i + 2, educationListData.get(i).getName().trim());

            }
        }
        adapter_education = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, education_list);

        highest_education_spinner.setAdapter(adapter_education);

        /*-----checkkkkkkkkkkkk-----*/
//        System.out.println("education list-------------" + fetchPartnerPref.getPref_edu_name());
//        int pos;
//        System.out.println("education_pck_array size 11=============" + education_pck_array.size());
//
//
//        if (fetchPartnerPref.getPref_edu_name() != null) {
//
//            if (fetchPartnerPref.getPref_edu_name().equals("Doesn't Matter")) {
//                highest_education_spinner.setSelection(0);
//
//            } else if (fetchPartnerPref.getPref_edu_name().contains(",")) {
//
//                namesList = fetchPartnerPref.getPref_edu_name().split(",");
//
//                highest_education_spinner.setSelection(0);
//
//                for (int i = 0; i < namesList.length; i++) {
//
//                    System.out.println("0000 education namesList size-------------" + namesList[i]);
//
//                    for (int j = 0; j < education_list.size(); j++) {
//                        if (education_list.get(j).contains(namesList[0])) {
//                            pos = j;
////                            highest_education_spinner.setSelection(pos);
//                        }
//                    }
//
//                    if (!namesList[i].equals("Select Education")) {
//
//                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
//                        params.setMargins(10, 5, 10, 5);
//
//
//                        textView1 = new TextView(getActivity());
//                        textView1.setLayoutParams(params);
//                        textView1.setTextSize(16);
//                        textView1.setBackgroundColor(Color.parseColor("#110A0A0A"));
//                        textView1.setPadding(20, 10, 20, 10);
//                        textView1.setLayoutParams(params);
//
//
//                        if (!namesList[i].equals(textView1.getText().toString())) {
//                            textView1.setText(namesList[i].trim() + "     x ");
//                            education_pck_array.add(namesList[i].trim());
//
//
//                            for (int x = 0; x < educationListData.size(); x++) {
//
//                                System.out.println("11 aaaa=======>>" + namesList[i]);
//                                System.out.println("22 aaaa=======>>" + educationListData.get(x).getName().trim());
//
//
//                                if (namesList[i].trim().equals(educationListData.get(x).getName().trim())) {
//                                    education_pck_array_education_name_id.add(educationListData.get(x).getId());
//                                    break;
//
//                                }
//                            }
//
//
//                            for (int x = 0; x < education_pck_array_education_name_id.size(); x++) {
//                                System.out.println("education_pck_array_education_name_id ----------" + education_pck_array_education_name_id.get(x));
//                            }
//
//
//                        }
//
//                        education_pckg_flex.addView(textView1);
//
//
//                        final String myString = textView1.getText().toString();
//                        int i1 = myString.indexOf("x ");
//                        textView1.setMovementMethod(LinkMovementMethod.getInstance());
//                        textView1.setText(myString, TextView.BufferType.SPANNABLE);
//                        Spannable mySpannable = (Spannable) textView1.getText();
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
////                                education_pck_array.remove(charSequence);
//
//
//                                for (int i = 0; i < education_pck_array.size(); i++) {
//                                    System.out.println("array character name----------" + education_pck_array.get(i).trim() + "---");
//                                    System.out.println("11 array character name----------" + a.trim() + "---");
//
//                                    if ((education_pck_array.get(i).trim()).contains(a.trim())) {
//
//                                        int id = education_pck_array.indexOf(a.trim());
//                                        System.out.println(" 11 array character name----------:" + id);
//
//
//                                        education_pck_array.remove(id);
//                                        education_pck_array.remove(charSequence);
//
//
//
//
//                                        /*delete education id from id array*/
//
//                                        for (int x = 0; x < educationListData.size(); x++) {
//                                            if (a.trim().equals(educationListData.get(x).getName())) {
//                                                String id_ofArray = educationListData.get(x).getId();
//
//                                                int id_ = education_pck_array_education_name_id.indexOf(id_ofArray);
//                                                education_pck_array_education_name_id.remove(id_);
//
//                                            }
//                                        }
//
//                                        /*delete education id from id array*/
//
//
//                                        textView.setVisibility(View.GONE);
//                                        System.out.println("education_pck_array size 22=============" + education_pck_array.size());
//
//                                        for (int k = 0; k < education_pck_array.size(); k++) {
//                                            System.out.println("education_pck_array size 000=============" + education_pck_array.get(k));
//
//                                        }
//
//                                    }
//
//                                }
//                                education_pck_array.remove(charSequence);
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
//                for (int i = 0; i < education_list.size(); i++) {
//                    if (education_list.get(i).trim().contains(fetchPartnerPref.getPref_edu_name().trim())) {
//                        pos = i;
//                        highest_education_spinner.setSelection(pos);
//
////                        getEducationList(fetchPartnerPref.getPrefmain_edu_name().trim());
//                    }
//                }
//            }
//
//
//        }
//
//
//        System.out.println("education_pck_array size 33=============" + education_pck_array.size());
        /*----------*/


        highest_education_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                education_list_str = highest_education_spinner.getItemAtPosition(position).toString().trim();
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


                                textView2 = new TextView(getActivity());
                                textView2.setLayoutParams(params);
                                textView2.setTextSize(16);

                                textView2.setBackgroundColor(Color.parseColor("#110A0A0A"));

                                textView2.setPadding(20, 10, 20, 10);
                                textView2.setLayoutParams(params);

                                System.out.println("textView2.getText().toString().===========" + textView2.getText().toString());
//                            System.out.println("textView1.getText().toString().===========" + textView1.getText().toString());


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


//                                    education_pck_array.remove(charSequence);

                                        for (int i = 0; i < education_pck_array.size(); i++) {
                                            System.out.println("array character name---------->>>" + education_pck_array.get(i).trim());
                                            System.out.println("array character name---------->>>" + a.trim());

                                            if ((education_pck_array.get(i).trim()).contains(a.trim())) {

                                                int id = education_pck_array.indexOf(a.trim());
                                                System.out.println("remove id check----------" + id);

                                                education_pck_array.remove(charSequence);
                                                education_pck_array.remove(id);

                                                /*delete education id from id array*/

                                                for (int x = 0; x < educationListData.size(); x++) {
                                                    if (a.trim().equals(educationListData.get(x).getName())) {
                                                        String id_ofArray = educationListData.get(x).getId();

                                                        int id_ = education_pck_array_education_name_id.indexOf(id_ofArray);
                                                        education_pck_array_education_name_id.remove(id_);

                                                    }
                                                }

                                                /*delete education id from id array*/
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


                            textView3 = new TextView(getActivity());
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


//                                education_pck_array.remove(charSequence);

                                    for (int i = 0; i < education_pck_array.size(); i++) {
                                        System.out.println("array character name----------" + education_pck_array.get(i));

                                        if ((education_pck_array.get(i).trim()).contains(a.trim())) {

                                            int id = education_pck_array.indexOf(a.trim());
                                            System.out.println(" remove txt index----------" + id);

                                            education_pck_array.remove(charSequence);
                                            education_pck_array.remove(id);

                                            /*delete education id from id array*/

                                            for (int x = 0; x < educationListData.size(); x++) {
                                                if (a.trim().equals(educationListData.get(x).getName())) {
                                                    String id_ofArray = educationListData.get(x).getId();

                                                    int id_ = education_pck_array_education_name_id.indexOf(id_ofArray);
                                                    education_pck_array_education_name_id.remove(id_);

                                                }
                                            }

                                            /*delete education id from id array*/
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
                    Toast.makeText(getActivity(), "Already Added!!!", Toast.LENGTH_SHORT).show();
                }
                /*new cond*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
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

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void occupation_type_spinner_data(List<OccupationType.OccupationTypeData> OccupationTypeData) {

        occupation_type_list.clear();
        occupation_type_list.add("Select Occupation Type");
        if (occupation_type_list.size() != 0) {
            for (int i = 0; i < occupationTypeLists.size(); i++) {
                occupation_type_list.add(i + 1, occupationTypeLists.get(i).getName().trim());
            }
        }
        adapter_occupation_type = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, occupation_type_list);

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


                    occupation_type_txtv = new TextView(getActivity());
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

    public void getOccupationList(String occupation_type_list_str) {

        System.out.println("xxxyy check 11----------" + occupation_type_list_str);

        if (occupation_type_list_str.equals("")) {
            System.out.println("check 11----------" + occupation_type_array.size());

            for (int j = 0; j < occupation_type_array.size(); j++) {
                System.out.println("111 character name----------" + occupation_type_array.get(j));


                System.out.println("check 11----------" + occupationTypeLists.size());


                for (int i = 0; i < occupationTypeLists.size(); i++) {

                    System.out.println("xx check 11----------" + occupation_type_array.get(j));
                    System.out.println("xx check 11----------" + occupationTypeLists.get(i).getName());


                    if (occupation_type_array.get(j).equals(occupationTypeLists.get(i).getName())) {


                        occupation_type_id = String.valueOf(occupationTypeLists.get(i).getId());


                        System.out.println("occupation_type_id for occupation----------" + occupation_type_id);
                        System.out.println("occupation_type_id for occupation----------" + occupationTypeLists.get(i).getName());

                        break;

                    }
                }


            }

        } else {
            if (occupation_type_list_str != null) {
                for (int i = 0; i < occupationTypeLists.size(); i++) {
                    if (occupation_type_list_str.equals(occupationTypeLists.get(i).getName())) {
                        occupation_type_id = String.valueOf(occupationTypeLists.get(i).getId());


                    }
                }
            }

        }


        occupationListData.clear();
        occupation_pckg_flex.removeAllViews();
        occupation_pck_array.clear();

        occupation_spinner.setAdapter(null);

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait...");

        System.out.println("occupation_type_id-------->>>>>>" + occupation_type_id);

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService.occupationNew("27", occupation_type_id);
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                occupationList = response.body();
                progressBar.dismiss();


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
                progressBar.dismiss();

                System.out.println("err occupation******" + t.toString());

                if (!isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void occupation_spinner_data(List<OccupationList.OccupationListData> occupationListData) {
        /*-----*/


        occupation_pck_array.clear();
        occupation_pck_array_occupation_name_id.clear();



        /*-----*/
        occupation_list.clear();

        occupation_pckg_flex.removeAllViews();
        occupation_list.add("Select Occupation");
        occupation_list.add("Doesn't Matter");

        if (occupation_list.size() != 0) {
            for (int i = 0; i < occupationListData.size(); i++) {

                occupation_list.add(i + 2, occupationListData.get(i).getName().trim());

            }
        }
        adapter_occupation = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, occupation_list);

        occupation_spinner.setAdapter(adapter_occupation);


        occupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                occupation_list_str = occupation_spinner.getItemAtPosition(position).toString().trim();
                System.out.println("occupation_list_str------" + occupation_list_str);
                for (int h = 0; h < occupation_pck_array.size(); h++) {
                    System.out.println("11100.0.1.===========" + occupation_pck_array.get(h));
                }


                /*new cond*/
                status_occupation = "0";

                for (int x = 0; x < occupation_pck_array.size(); x++) {

                    System.out.println("11 xx zz==============" + occupation_pck_array.get(x));
                    System.out.println("22 xx zz==============" + occupation_list_str);

                    if (occupation_pck_array.get(x).equals(occupation_list_str)) {
                        status_occupation = "1";
                    }
                }

                if (status_occupation.equals("0")) {
                    if (occupation_pck_array.size() != 0) {
                        for (int h = 0; h < occupation_pck_array.size(); h++) {

                            if (!occupation_list_str.equals("Select Occupation") && !occupation_pck_array.contains(occupation_list_str.trim())) {

                                if (occupation_flexLL.getVisibility() == View.GONE) {
                                    occupation_flexLL.setVisibility(View.VISIBLE);
                                }


                                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(10, 5, 10, 5);


                                textView2 = new TextView(getActivity());
                                textView2.setLayoutParams(params);
                                textView2.setTextSize(16);

                                textView2.setBackgroundColor(Color.parseColor("#110A0A0A"));

                                textView2.setPadding(20, 10, 20, 10);
                                textView2.setLayoutParams(params);

                                System.out.println("textView2.getText().toString().===========" + textView2.getText().toString());
//                            System.out.println("textView1.getText().toString().===========" + textView1.getText().toString());


                                if (!occupation_list_str.trim().equals(textView2.getText().toString())) {

                                    if (textView1 != null) {
                                        if (!textView2.getText().toString().equals(textView1.getText().toString())) {

                                            textView2.setText(occupation_list_str.trim() + "     x ");

                                            occupation_pck_array.add(occupation_list_str.trim());

                                            for (int x = 0; x < occupationListData.size(); x++) {
                                                if (occupation_list_str.trim().equals(occupationListData.get(x).getName().trim())) {
                                                    occupation_pck_array_occupation_name_id.add(occupationListData.get(x).getId());

                                                }
                                            }

                                        }
                                    } else {
                                        textView2.setText(occupation_list_str.trim() + "     x ");

                                        occupation_pck_array.add(occupation_list_str.trim());

                                        for (int x = 0; x < occupationListData.size(); x++) {
                                            if (occupation_list_str.equals(occupationListData.get(x).getName().trim())) {
                                                occupation_pck_array_occupation_name_id.add(occupationListData.get(x).getId());

                                            }
                                        }

                                    }


                                }


                                occupation_pckg_flex.addView(textView2);


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


//                                    occupation_pck_array.remove(charSequence);

                                        for (int i = 0; i < occupation_pck_array.size(); i++) {
                                            System.out.println("array character name---------->>>" + occupation_pck_array.get(i).trim());
                                            System.out.println("array character name---------->>>" + a.trim());

                                            if ((occupation_pck_array.get(i).trim()).contains(a.trim())) {

                                                int id = occupation_pck_array.indexOf(a.trim());
                                                System.out.println("remove id check----------" + id);

                                                occupation_pck_array.remove(charSequence);
                                                occupation_pck_array.remove(id);

                                                /*delete occupation id from id array*/

                                                for (int x = 0; x < occupationListData.size(); x++) {
                                                    if (a.trim().equals(occupationListData.get(x).getName())) {
                                                        String id_ofArray = occupationListData.get(x).getId();

                                                        int id_ = occupation_pck_array_occupation_name_id.indexOf(id_ofArray);
                                                        occupation_pck_array_occupation_name_id.remove(id_);

                                                    }
                                                }

                                                /*delete occupation id from id array*/
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

                        if (!occupation_list_str.equals("Select Occupation")) {
                            if (occupation_flexLL.getVisibility() == View.GONE) {
                                occupation_flexLL.setVisibility(View.VISIBLE);
                            }

                            System.out.println("00.0.1.===========" + "11111111111111111");

                            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(10, 5, 10, 5);


                            textView3 = new TextView(getActivity());
                            textView3.setLayoutParams(params);
                            textView3.setTextSize(16);
                            textView3.setBackgroundColor(Color.parseColor("#110A0A0A"));

                            textView3.setPadding(20, 10, 20, 10);
                            textView3.setLayoutParams(params);
                            if (!occupation_list_str.trim().equals(textView3.getText().toString())) {


                                textView3.setText(occupation_list_str.trim() + "     x ");

                                occupation_pck_array.add(occupation_list_str.trim());

                                for (int x = 0; x < occupationListData.size(); x++) {
                                    if (occupation_list_str.equals(occupationListData.get(x).getName().trim())) {
                                        occupation_pck_array_occupation_name_id.add(occupationListData.get(x).getId());

                                    }
                                }
                            }


                            occupation_pckg_flex.addView(textView3);


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


//                                occupation_pck_array.remove(charSequence);

                                    for (int i = 0; i < occupation_pck_array.size(); i++) {
                                        System.out.println("array character name----------" + occupation_pck_array.get(i));

                                        if ((occupation_pck_array.get(i).trim()).contains(a.trim())) {

                                            int id = occupation_pck_array.indexOf(a.trim());
                                            System.out.println(" remove txt index----------" + id);

                                            occupation_pck_array.remove(charSequence);
                                            occupation_pck_array.remove(id);

                                            /*delete occupation id from id array*/

                                            for (int x = 0; x < occupationListData.size(); x++) {
                                                if (a.trim().equals(occupationListData.get(x).getName())) {
                                                    String id_ofArray = occupationListData.get(x).getId();

                                                    int id_ = occupation_pck_array_occupation_name_id.indexOf(id_ofArray);
                                                    occupation_pck_array_occupation_name_id.remove(id_);

                                                }
                                            }

                                            /*delete occupation id from id array*/
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
                    Toast.makeText(getActivity(), "Already Added!!!", Toast.LENGTH_SHORT).show();
                }
                /*new cond*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    /*end occupation type and occupation*/

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
                if (!isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void mother_tongue_spinner_data(ArrayList<MothertoungeList.MothertoungeListData> mothertoungeListData) {

        mother_tongue_list.clear();

        mother_tongue_list.add("Select Mother Tongue");

        if (mother_tongue_list.size() != 0) {
            for (int i = 0; i < mothertoungeListData.size(); i++) {

                mother_tongue_list.add(i + 1, mothertoungeListData.get(i).getName().trim());

            }
        }


        mother_tongue_adapter = new ArrayAdapter<String>(getContext(),
                R.layout.simple_spinner_item, mother_tongue_list);

        mother_tounge_spinner.setAdapter(mother_tongue_adapter);


        mother_tounge_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                mother_tongue_str = mother_tounge_spinner.getItemAtPosition(position).toString();
                System.out.println("mother_tongue_str------" + mother_tongue_str);

                if (!mother_tongue_str.equals("Select Mother Tongue")) {
                    if (mother_tongue_flexLL.getVisibility() == View.GONE) {
                        mother_tongue_flexLL.setVisibility(View.VISIBLE);
                    }

                    System.out.println("00.0.1.===========" + "11111111111111111");

                    FlexboxLayout.LayoutParams params = new
                            FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
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

                                if ((mothertounge_array.get(i).trim()).contains(a.trim())) {

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


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


}


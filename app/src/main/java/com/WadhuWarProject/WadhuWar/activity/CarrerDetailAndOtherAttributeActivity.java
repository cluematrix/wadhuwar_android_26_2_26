package com.WadhuWarProject.WadhuWar.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.BloodGroupList;
import com.WadhuWarProject.WadhuWar.model.ColorcomplexList;
import com.WadhuWarProject.WadhuWar.model.CountryList;
import com.WadhuWarProject.WadhuWar.model.DietaryList;
import com.WadhuWarProject.WadhuWar.model.DistrictList;
import com.WadhuWarProject.WadhuWar.model.EducationList;
import com.WadhuWarProject.WadhuWar.model.EducationType;
import com.WadhuWarProject.WadhuWar.model.HeightList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.LifeSettingList;
import com.WadhuWarProject.WadhuWar.model.MaritalSettingList;
import com.WadhuWarProject.WadhuWar.model.OccupationList;
import com.WadhuWarProject.WadhuWar.model.OccupationType;
import com.WadhuWarProject.WadhuWar.model.SalaryList;
import com.WadhuWarProject.WadhuWar.model.StateList;
import com.WadhuWarProject.WadhuWar.model.WeightList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarrerDetailAndOtherAttributeActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;

    RelativeLayout submit_btn;
    TextView a1, a2, a3, a4;
    Spinner occupation_spinner, highest_education_spinner, office_country_spinner, office_state_spinner, office_district_spinner, salary_spinner, manglik_spinner,
            marital_status_spinner, dietary_spinner, lifestyle_spinner, height_spinner, weight_spinner, colorcomplex_spinner,
            bloodgroup_spinner, handicap_spinner, num_childred_spinner, childred_living_spinner, education_type_spinner, occupation_type_spinner;

    ArrayList<OccupationList.OccupationListData> occupationListData = new ArrayList<>();
    OccupationList occupationList;
    ArrayList<OccupationType.OccupationTypeData> occupationTypeData = new ArrayList<>();
    OccupationType occupationType;

    ArrayList<EducationList.EducationListData> educationListData = new ArrayList<>();
    EducationList educationList;
    ArrayList<EducationType.EducationTypeList> educationTypeLists = new ArrayList<>();
    EducationType educationType;
    ArrayList<CountryList.CountryListData> countryListData = new ArrayList<>();
    CountryList countryList;
    ArrayList<StateList> stateListsData = new ArrayList<>();
    StateList stateList;
    ArrayList<DistrictList.DistrictListData> districtListsData = new ArrayList<>();
    DistrictList districtList;
    ArrayList<MaritalSettingList.MaritalSettingListData> maritalSettingListData = new ArrayList<>();
    MaritalSettingList maritalSettingList;
    ArrayList<DietaryList.DietaryListData> dietaryListData = new ArrayList<>();
    DietaryList dietaryList;
    ArrayList<LifeSettingList.LifeSettingListData> lifeSettingListData = new ArrayList<>();
    LifeSettingList lifeSettingList;
    ArrayList<HeightList.HeightListData> heightListData = new ArrayList<>();
    HeightList heightList;
    ArrayList<WeightList.WeightListData> weightListData = new ArrayList<>();
    WeightList weightList;
    ArrayList<ColorcomplexList.ColorcomplexListData> colorcomplexListData = new ArrayList<>();
    ColorcomplexList colorcomplexList;
    ArrayList<BloodGroupList.BloodGroupListData> bloodGroupListData = new ArrayList<>();
    BloodGroupList bloodGroupList;
    ArrayList<SalaryList.SalaryListData> salaryListData = new ArrayList<>();
    SalaryList salaryList;


    List<String> country_list = new ArrayList<String>();
    List<String> state_list = new ArrayList<String>();
    List<String> district_list = new ArrayList<String>();
    List<String> occupational_list = new ArrayList<String>();
    List<String> occupational_type_list = new ArrayList<String>();

    List<String> education_list = new ArrayList<String>();
    List<String> education_type_list = new ArrayList<String>();

    List<String> marital_list = new ArrayList<String>();
    List<String> dietray_list = new ArrayList<String>();
    List<String> lifestyle_list = new ArrayList<String>();
    List<String> height_list = new ArrayList<String>();
    List<String> weight_list = new ArrayList<String>();
    List<String> colorcomplex_list = new ArrayList<String>();
    List<String> bloodgroup_list = new ArrayList<String>();
    List<String> salary_list = new ArrayList<String>();

    String occupation_list_str, occupation_type_list_str, education_list_str, education_type_list_str, country_list_str, state_list_str, district_str, manglik_str, marital_str,
            dietray_str, lifestyle_str, height_str, weight_str, colorcomplex_str, bloodgroup_str, handicap_str, salary_str, num_child_str, child_living_status_str;
    String occupation_id, education_id, country_id, state_id, district_id, dietray_id, lifestyle_id, height_id, weight_id,
            colorcomplex_id, bloodgroup_id, handicap_id, salary_id, marital_id, education_type_id, occupation_type_id;

    ArrayAdapter<String> adapter_dietray;
    ArrayAdapter<String> adapter_marital;
    ArrayAdapter<String> adapter_occupation;
    ArrayAdapter<String> adapter_occupation_type;

    ArrayAdapter<String> adapter_education;
    ArrayAdapter<String> adapter_education_type;
    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> district_adapter;
    ArrayAdapter<String> manglik_adapter;
    ArrayAdapter<String> lifestyle_adapter;
    ArrayAdapter<String> height_adapter;
    ArrayAdapter<String> weight_adapter;
    ArrayAdapter<String> colorcomplex_adapter;
    ArrayAdapter<String> bloodgroup_adapter;
    ArrayAdapter<String> handicap_adapter;
    ArrayAdapter<String> salary_adapter;
    ArrayAdapter<String> num_child_adapter;
    ArrayAdapter<String> child_living_status_adapter;

    String[] manglik_list = {"Select Manglik or Not", "Yes", "No"};
    String[] handicap_list = {"Select Handicap", "Yes", "No"};
    String[] child_living_status_list = {"Select Children Living Status", "Living with me", "Not living with me"};
    String[] num_child_list = {"Select No. of Children", "0", "1", "2", "3", "4", "5 and above"};

    ProgressDialog progressBar;

    TextView birth_time;
    EditText other_education, college_name, post_name, office_address, office_location, hobbies, gotra, disbility_name;
    String _other_education, _college_name, _post_name, _office_address, _office_location, _birth_time, _hobbies, _gotra, _disbility_name, _children_age;
    private int mHour, mMinute;
    private String format = "";
    String user_id;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayout disability_name_LL, other_LL;
    EditText children_age;

    ProgressBar prog_off_district;

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
        setContentView(R.layout.activity_carrer_other_attribute_detail);

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
//        setContentView(R.layout.activity_carrer_other_attribute_detail);


        prog_off_district = findViewById(R.id.prog_off_district);
        submit_btn = findViewById(R.id.submit_btn);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);


        education_type_spinner = findViewById(R.id.education_type_spinner);
        occupation_type_spinner = findViewById(R.id.occupation_type_spinner);

        occupation_spinner = findViewById(R.id.occupation_spinner);
        highest_education_spinner = findViewById(R.id.highest_education_spinner);
        office_country_spinner = findViewById(R.id.office_country_spinner);
        office_state_spinner = findViewById(R.id.office_state_spinner);
        office_district_spinner = findViewById(R.id.office_district_spinner);
        salary_spinner = findViewById(R.id.salary_spinner);
        manglik_spinner = findViewById(R.id.manglik_spinner);
        marital_status_spinner = findViewById(R.id.marital_status_spinner);
        dietary_spinner = findViewById(R.id.dietary_spinner);
        lifestyle_spinner = findViewById(R.id.lifestyle_spinner);
        height_spinner = findViewById(R.id.height_spinner);
        weight_spinner = findViewById(R.id.weight_spinner);
        colorcomplex_spinner = findViewById(R.id.colorcomplex_spinner);
        bloodgroup_spinner = findViewById(R.id.bloodgroup_spinner);
        handicap_spinner = findViewById(R.id.handicap_spinner);
        other_education = findViewById(R.id.other_education);
        college_name = findViewById(R.id.college_name);
        hobbies = findViewById(R.id.hobbies);
        gotra = findViewById(R.id.gotra);
        birth_time = findViewById(R.id.birth_time);
        post_name = findViewById(R.id.post_name);
        office_address = findViewById(R.id.office_address);
        office_location = findViewById(R.id.office_location);
        disability_name_LL = findViewById(R.id.disability_name_LL);
        num_childred_spinner = findViewById(R.id.num_childred_spinner);
        childred_living_spinner = findViewById(R.id.childred_living_spinner);
        other_LL = findViewById(R.id.other_LL);
        children_age = findViewById(R.id.children_age);
        disbility_name = findViewById(R.id.disbility_name);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        a1.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a1.setTextColor(Color.WHITE);
        a2.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a2.setTextColor(Color.WHITE);
        a3.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a3.setTextColor(Color.WHITE);
//        a4.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
//        a4.setTextColor(Color.WHITE);

        Intent i = getIntent();
        user_id = (i.getStringExtra("user_id"));
        //   user_id = "67";
        System.out.println("user_id========" + user_id);

        birth_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CarrerDetailAndOtherAttributeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                showTime(hourOfDay, minute);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _other_education = other_education.getText().toString();
                _college_name = college_name.getText().toString();
                _post_name = post_name.getText().toString();
                _office_address = office_address.getText().toString();
                _office_location = office_location.getText().toString();
                _birth_time = birth_time.getText().toString();
                _hobbies = hobbies.getText().toString();
                _gotra = gotra.getText().toString();
                _disbility_name = disbility_name.getText().toString();
                _children_age = children_age.getText().toString();
                System.out.println("_children_age name----------" + _children_age);

                if (education_type_list_str != null && !education_type_list_str.contentEquals("")) {
                    for (int i = 0; i < educationTypeLists.size(); i++) {
                        if (education_type_list_str.equals(educationTypeLists.get(i).getName())) {
                            education_type_id = String.valueOf(educationTypeLists.get(i).getId());
                        }
                    }
                } else {
                    education_type_id = "";
                }

                if (education_list_str != null && !education_list_str.contentEquals("")) {
                    for (int i = 0; i < educationListData.size(); i++) {
                        if (education_list_str.equals(educationListData.get(i).getName())) {
                            education_id = String.valueOf(educationListData.get(i).getId());
                        }
                    }
                } else {
                    education_id = "";
                }


                if (occupation_type_list_str != null && !occupation_type_list_str.contentEquals("")) {
                    for (int i = 0; i < occupationTypeData.size(); i++) {
                        if (occupation_type_list_str.equals(occupationTypeData.get(i).getName())) {
                            occupation_type_id = String.valueOf(occupationTypeData.get(i).getId());
                        }
                    }
                } else {
                    occupation_type_id = "";

                }
                if (occupation_list_str != null && !occupation_list_str.contentEquals("")) {
                    for (int i = 0; i < occupationListData.size(); i++) {
                        if (occupation_list_str.equals(occupationListData.get(i).getName())) {
                            occupation_id = String.valueOf(occupationListData.get(i).getId());
                        }
                    }
                } else {
                    occupation_id = "";

                }

                if (country_list_str != null && !country_list_str.contentEquals("")) {
                    for (int i = 0; i < countryListData.size(); i++) {
                        if (country_list_str.equals(countryListData.get(i).getName())) {
                            country_id = String.valueOf(countryListData.get(i).getId());
                        }
                    }
                } else {
                    country_id = "";

                }

                if (state_list_str != null && !state_list_str.contentEquals("")) {
                    for (int i = 0; i < stateListsData.size(); i++) {
                        if (state_list_str.equals(stateListsData.get(i).getSc_name())) {
                            state_id = String.valueOf(stateListsData.get(i).getSc_id());

                        }
                    }
                } else {
                    state_id = "";

                }

                if (district_str != null && !district_str.contentEquals("")) {
                    for (int i = 0; i < districtListsData.size(); i++) {
                        if (district_str.equals(districtListsData.get(i).getName())) {
                            district_id = String.valueOf(districtListsData.get(i).getId());

                        }
                    }
                } else {
                    district_id = "";

                }


                if (marital_str != null && !marital_str.contentEquals("")) {
                    for (int i = 0; i < maritalSettingListData.size(); i++) {
                        if (marital_str.equals(maritalSettingListData.get(i).getName())) {
                            marital_id = String.valueOf(maritalSettingListData.get(i).getId());

                        }
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

                if (lifestyle_str != null && !lifestyle_str.contentEquals("")) {
                    for (int i = 0; i < lifeSettingListData.size(); i++) {
                        if (lifestyle_str.equals(lifeSettingListData.get(i).getName())) {
                            lifestyle_id = String.valueOf(lifeSettingListData.get(i).getId());

                        }
                    }
                } else {
                    lifestyle_id = "";

                }

                if (colorcomplex_str != null && !colorcomplex_str.contentEquals("")) {
                    for (int i = 0; i < colorcomplexListData.size(); i++) {
                        if (colorcomplex_str.equals(colorcomplexListData.get(i).getName())) {
                            colorcomplex_id = String.valueOf(colorcomplexListData.get(i).getId());

                        }
                    }
                } else {
                    colorcomplex_id = "";

                }

                if (bloodgroup_str != null && !bloodgroup_str.contentEquals("")) {
                    for (int i = 0; i < bloodGroupListData.size(); i++) {
                        if (bloodgroup_str.equals(bloodGroupListData.get(i).getName())) {
                            bloodgroup_id = String.valueOf(bloodGroupListData.get(i).getId());

                        }
                    }
                } else {
                    bloodgroup_id = "";

                }

                if (salary_str.equals("Select Salary")) {
                    salary_str = "";
                }
                if (manglik_str.equals("Select Manglik or Not")) {
                    manglik_str = "";
                }
                if (height_str.equals("Select Height")) {
                    height_str = "";
                }
                if (weight_str.equals("Select Weight")) {
                    weight_str = "";
                }
                if (handicap_str.equals("Select Handicap")) {
                    handicap_str = "";
                }


                if (num_child_str.equals("Select No. of Children")) {
                    num_child_str = "";
                }

                if (child_living_status_str.equals("Select Children Living Status")) {
                    child_living_status_str = "";
                }


                submitData(education_type_id, education_id, _other_education, _college_name, occupation_id, _post_name, _office_address, _office_location, country_id, state_id, district_id, salary_str,
                        _birth_time, manglik_str, _hobbies, marital_id, num_child_str, _children_age, child_living_status_str, dietray_id, lifestyle_id,
                        height_str, weight_str, _gotra, colorcomplex_id, bloodgroup_id, handicap_str, _disbility_name, occupation_type_id);


            }
        });


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }

    @Override
    public void networkAvailable() {
        getOccupationTypeList();
        getEducationistType();
        getCountryList();
        getManglik();
        getMaritalStatusList();
        getDietaryList();
        getLifeStyleList();
        getHeightList();
        getWeightList();
        getColorcomplexList();
        getBloodGroupList();
        getHandicapList();
        getSalaryList();
        getNumChildrenList();
        getChildLivingStatusList();


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                educationListData.clear();
                occupationListData.clear();
                countryListData.clear();
                stateListsData.clear();
                districtListsData.clear();
                maritalSettingListData.clear();
                dietaryListData.clear();
                lifeSettingListData.clear();
                heightListData.clear();
                weightListData.clear();
                colorcomplexListData.clear();
                bloodGroupListData.clear();

                getOccupationTypeList();
                getEducationistType();
                getCountryList();
                getManglik();
                getMaritalStatusList();
                getDietaryList();
                getLifeStyleList();
                getHeightList();
                getWeightList();
                getColorcomplexList();
                getBloodGroupList();
                getHandicapList();
                getSalaryList();
                getNumChildrenList();
                getChildLivingStatusList();
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

                Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void submitData(String education_type_id, String education_id, String other_education, String college_name, String occupation_id, String _post_name, String _office_address, String office_location,
                           String country_id, String state_id, String district_id, String salary_str, String _birth_time, String manglik_str, String _hobbies,
                           String marital_id, String num_child_str, String _children_age, String child_living_status_str, String dietray_id, String lifestyle_id, String height_str, String weight_str, String _gotra, String colorcomplex_id,
                           String bloodgroup_id, String handicap_str, String disbility_name, String occupation_type_id) {


        System.out.println("education_id --" + education_id);
        System.out.println("other_education --" + other_education);
        System.out.println("college_name --" + college_name);
        System.out.println("occupation_id --" + occupation_id);
        System.out.println("_post_name --" + _post_name);
        System.out.println("_office_address --" + _office_address);
        System.out.println("office_location --" + office_location);
        System.out.println("country_id --" + country_id);
        System.out.println("state_id --" + state_id);
        System.out.println("district_id --" + district_id);
        System.out.println("salary_str --" + salary_str);
        System.out.println("_birth_time --" + _birth_time);
        System.out.println("manglik_str --" + manglik_str);
        System.out.println("_hobbies --" + _hobbies);
        System.out.println("marital_id --" + marital_id);
        System.out.println("dietray_id --" + dietray_id);
        System.out.println("lifestyle_id --" + lifestyle_id);
        System.out.println("height_str --" + height_str);
        System.out.println("weight_str --" + weight_str);
        System.out.println("_gotra --" + _gotra);


        System.out.println("colorcomplex_id --" + colorcomplex_id);
        System.out.println("bloodgroup_id --" + bloodgroup_id);
        System.out.println("handicap_str --" + handicap_str);
        System.out.println("disbility_name --" + disbility_name);


        progressBar = ProgressDialog.show(CarrerDetailAndOtherAttributeActivity.this, "", "Please Wait...");


        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.insertStep3(education_type_id, education_id, _other_education, _college_name, occupation_id, _post_name, _office_address, _office_location,
                country_id, state_id, district_id, salary_str, _birth_time, manglik_str, _hobbies, marital_id, num_child_str, _children_age, child_living_status_str, dietray_id, lifestyle_id, height_str, weight_str,
                _gotra, colorcomplex_id, handicap_str, disbility_name, bloodgroup_id, user_id, occupation_type_id);


        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                insertResponse = response.body();

                progressBar.dismiss();
                if (response.isSuccessful()) {

                    /*System.out.println("carrier resp ========" + new Gson().toJson(insertResponse));*/

                    String success = insertResponse.getResid();


                    if (success.equals("200")) {
                        Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CarrerDetailAndOtherAttributeActivity.this, FamilyDetailActivity.class);
                        i.putExtra("user_id", user_id);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    } else {
                        Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err carrier ******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {

                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        birth_time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

    public void getSalaryList() {

        salaryListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<SalaryList> userResponse = apiService.salaryList();
        userResponse.enqueue(new Callback<SalaryList>() {

            @Override
            public void onResponse(Call<SalaryList> call, Response<SalaryList> response) {
                salaryList = response.body();

                if (response.isSuccessful()) {


                    String success = salaryList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < salaryList.getSalaryList().size(); i++) {
                            salaryListData.add(new SalaryList.SalaryListData(salaryList.getSalaryList().get(i).getName()));
                        }
                        salary_spinner_data(salaryListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<SalaryList> call, Throwable t) {

                System.out.println("err salary******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void salary_spinner_data(List<SalaryList.SalaryListData> salaryListData) {

        salary_list.clear();

        salary_list.add("Select Salary");

        if (salary_list.size() != 0) {
            for (int i = 0; i < salaryListData.size(); i++) {

                salary_list.add(i + 1, salaryListData.get(i).getName());

            }
        }
        salary_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, salary_list);

        salary_spinner.setAdapter(salary_adapter);

        salary_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                salary_str = salary_spinner.getItemAtPosition(position).toString();
                System.out.println("salary_str------" + salary_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getHandicapList() {

        handicap_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, handicap_list);

        handicap_spinner.setAdapter(handicap_adapter);

        handicap_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                handicap_str = handicap_spinner.getItemAtPosition(position).toString();
                System.out.println("manglik_list------" + handicap_str);
                if (handicap_str.equals("Yes")) {
                    disability_name_LL.setVisibility(View.VISIBLE);
                } else {
                    disability_name_LL.setVisibility(View.GONE);
                    disbility_name.setText("");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getNumChildrenList() {

        num_child_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, num_child_list);

        num_childred_spinner.setAdapter(num_child_adapter);

        num_childred_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                num_child_str = num_childred_spinner.getItemAtPosition(position).toString();
                System.out.println("num_child_adapter------" + num_child_adapter);

//                if(num_child_str.equals("Yes")){
//                    disability_name_LL.setVisibility(View.VISIBLE);
//                }else{
//                    disability_name_LL.setVisibility(View.GONE);
//                    disbility_name.setText("");
//
//                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void getChildLivingStatusList() {

        child_living_status_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, child_living_status_list);

        childred_living_spinner.setAdapter(child_living_status_adapter);

        childred_living_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                child_living_status_str = childred_living_spinner.getItemAtPosition(position).toString();
                System.out.println("child_living_status_str------" + child_living_status_str);

//                if(num_child_str.equals("Yes")){
//                    disability_name_LL.setVisibility(View.VISIBLE);
//                }else{
//                    disability_name_LL.setVisibility(View.GONE);
//                    disbility_name.setText("");
//
//                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void getBloodGroupList() {

        bloodGroupListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<BloodGroupList> userResponse = apiService.bloodGroupList("12");
        userResponse.enqueue(new Callback<BloodGroupList>() {

            @Override
            public void onResponse(Call<BloodGroupList> call, Response<BloodGroupList> response) {
                bloodGroupList = response.body();

                if (response.isSuccessful()) {


                    String success = bloodGroupList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < bloodGroupList.getBloodGroupList().size(); i++) {
                            bloodGroupListData.add(new BloodGroupList.BloodGroupListData(bloodGroupList.getBloodGroupList().get(i).getId(), bloodGroupList.getBloodGroupList().get(i).getName()));
                        }
                        bloodgroup_spinner_data(bloodGroupListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<BloodGroupList> call, Throwable t) {

                System.out.println("err colorcomplex******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void bloodgroup_spinner_data(List<BloodGroupList.BloodGroupListData> bloodGroupListData) {

        bloodgroup_list.clear();

        bloodgroup_list.add("Select Blood Group");

        if (bloodgroup_list.size() != 0) {
            for (int i = 0; i < bloodGroupListData.size(); i++) {

                bloodgroup_list.add(i + 1, bloodGroupListData.get(i).getName());

            }
        }
        bloodgroup_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, bloodgroup_list);

        bloodgroup_spinner.setAdapter(bloodgroup_adapter);

        bloodgroup_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                bloodgroup_str = bloodgroup_spinner.getItemAtPosition(position).toString();
                System.out.println("colorcomplex_str------" + bloodgroup_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getColorcomplexList() {

        colorcomplexListData.clear();

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
                            colorcomplexListData.add(new ColorcomplexList.ColorcomplexListData(colorcomplexList.getColorcomplexList().get(i).getId(), colorcomplexList.getColorcomplexList().get(i).getName()));
                        }
                        colorcomplex_spinner_data(colorcomplexListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ColorcomplexList> call, Throwable t) {

                System.out.println("err colorcomplex******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void colorcomplex_spinner_data(List<ColorcomplexList.ColorcomplexListData> colorcomplexListData) {

        colorcomplex_list.clear();

        colorcomplex_list.add("Select Color-Complex");

        if (colorcomplex_list.size() != 0) {
            for (int i = 0; i < colorcomplexListData.size(); i++) {

                colorcomplex_list.add(i + 1, colorcomplexListData.get(i).getName());

            }
        }
        colorcomplex_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, colorcomplex_list);

        colorcomplex_spinner.setAdapter(colorcomplex_adapter);

        colorcomplex_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                colorcomplex_str = colorcomplex_spinner.getItemAtPosition(position).toString();
                System.out.println("colorcomplex_str------" + colorcomplex_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getWeightList() {

        weightListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<WeightList> userResponse = apiService.weightList();
        userResponse.enqueue(new Callback<WeightList>() {

            @Override
            public void onResponse(Call<WeightList> call, Response<WeightList> response) {
                weightList = response.body();

                if (response.isSuccessful()) {


                    String success = weightList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < weightList.getWeightList().size(); i++) {
                            weightListData.add(new WeightList.WeightListData(weightList.getWeightList().get(i).getName()));
                        }
                        weight_spinner_data(weightListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<WeightList> call, Throwable t) {

                System.out.println("err height******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void weight_spinner_data(List<WeightList.WeightListData> weightListData) {

        weight_list.clear();

        weight_list.add("Select Weight");

        if (weight_list.size() != 0) {
            for (int i = 0; i < weightListData.size(); i++) {

                weight_list.add(i + 1, weightListData.get(i).getName());

            }
        }
        weight_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, weight_list);

        weight_spinner.setAdapter(weight_adapter);

        weight_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                weight_str = weight_spinner.getItemAtPosition(position).toString();
                System.out.println("weight_str------" + weight_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getHeightList() {

        heightListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<HeightList> userResponse = apiService.heightList();
        userResponse.enqueue(new Callback<HeightList>() {

            @Override
            public void onResponse(Call<HeightList> call, Response<HeightList> response) {
                heightList = response.body();

                if (response.isSuccessful()) {


                    String success = heightList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < heightList.getHeightList().size(); i++) {
                            heightListData.add(new HeightList.HeightListData(heightList.getHeightList().get(i).getName()));

                        }
                        height_spinner_data(heightListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<HeightList> call, Throwable t) {

                System.out.println("err height******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void height_spinner_data(List<HeightList.HeightListData> heightListData) {

        height_list.clear();

        height_list.add("Select Height");

        if (height_list.size() != 0) {
            for (int i = 0; i < heightListData.size(); i++) {

                height_list.add(i + 1, heightListData.get(i).getName());

            }
        }
        height_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, height_list);

        height_spinner.setAdapter(height_adapter);

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


    public void getLifeStyleList() {

        lifeSettingListData.clear();

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
                            lifeSettingListData.add(new LifeSettingList.LifeSettingListData(lifeSettingList.getLifeSetting().get(i).getId(), lifeSettingList.getLifeSetting().get(i).getName()));

                        }
                        lifestyle_spinner_data(lifeSettingListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<LifeSettingList> call, Throwable t) {

                System.out.println("err lifestyle******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void lifestyle_spinner_data(List<LifeSettingList.LifeSettingListData> lifeSettingListData) {

        lifestyle_list.clear();

        lifestyle_list.add("Select LifeStyle");

        if (lifestyle_list.size() != 0) {
            for (int i = 0; i < lifeSettingListData.size(); i++) {

                lifestyle_list.add(i + 1, lifeSettingListData.get(i).getName());

            }
        }
        lifestyle_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, lifestyle_list);

        lifestyle_spinner.setAdapter(lifestyle_adapter);

        lifestyle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                lifestyle_str = lifestyle_spinner.getItemAtPosition(position).toString();
                System.out.println("dietray_str------" + lifestyle_str);

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

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void dietary_spinner_data(List<DietaryList.DietaryListData> dietaryListData) {

        dietray_list.clear();

        dietray_list.add("Select Dietary");

        if (dietray_list.size() != 0) {
            for (int i = 0; i < dietaryListData.size(); i++) {

                dietray_list.add(i + 1, dietaryListData.get(i).getName());

            }
        }
        adapter_dietray = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, dietray_list);

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


    public void getMaritalStatusList() {

        maritalSettingListData.clear();

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

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void marital_spinner_data(List<MaritalSettingList.MaritalSettingListData> maritalSettingListData) {

        marital_list.clear();

        marital_list.add("Select Marital Status");

        if (marital_list.size() != 0) {
            for (int i = 0; i < maritalSettingListData.size(); i++) {

                marital_list.add(i + 1, maritalSettingListData.get(i).getName());

            }
        }
        adapter_marital = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, marital_list);

        marital_status_spinner.setAdapter(adapter_marital);

        marital_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                marital_str = marital_status_spinner.getItemAtPosition(position).toString();
                System.out.println("marital_str------" + marital_str);


                if (marital_str.equals("Devorce") ||marital_str.equals("Awaiting Divorce") || marital_str.equals("Widow/Widower")) {
                    other_LL.setVisibility(View.VISIBLE);
                } else {
                    other_LL.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getManglik() {

        manglik_adapter = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, manglik_list);

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


    public void getCountryList() {

        countryListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<CountryList> userResponse = apiService.countryList("07");
        userResponse.enqueue(new Callback<CountryList>() {

            @Override
            public void onResponse(Call<CountryList> call, Response<CountryList> response) {

                swipeRefreshLayout.setRefreshing(false);
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
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("err country******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void country_spinner_data(List<CountryList.CountryListData> countryListData) {

        country_list.clear();

        country_list.add("Select Country");

        if (country_list.size() != 0) {
            for (int i = 0; i < countryListData.size(); i++) {

                country_list.add(i + 1, countryListData.get(i).getName());

            }
        }


        adapter_country = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, country_list);

        office_country_spinner.setAdapter(adapter_country);

        office_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                country_list_str = office_country_spinner.getItemAtPosition(position).toString();
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

        if (stateLists != null && !stateLists.isEmpty()) {
            for (int i = 0; i < stateLists.size(); i++) {
                state_list.add(i + 1, stateLists.get(i).getSc_name().trim());
            }
        }

        adapter_state = new ArrayAdapter<>(
                CarrerDetailAndOtherAttributeActivity.this,
                R.layout.simple_spinner_item,
                state_list
        );
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        office_state_spinner.setAdapter(adapter_state);

        // Open searchable dialog on touch
        office_state_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableStateDialog(state_list);
            }
            return true;
        });
    }
    private void showSearchableStateDialog(List<String> stateList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CarrerDetailAndOtherAttributeActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalStateList = new ArrayList<>(stateList);
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                CarrerDetailAndOtherAttributeActivity.this,
                android.R.layout.simple_list_item_1,
                stateList
        );
        listView.setAdapter(dialogAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                String selectedState = dialogAdapter.getItem(position);
                if (selectedState == null) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Error selecting state", Toast.LENGTH_SHORT).show();
                    return;
                }

                int originalPosition = -1;
                for (int i = 0; i < originalStateList.size(); i++) {
                    if (originalStateList.get(i).equals(selectedState)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition != -1) {
                    office_state_spinner.setSelection(originalPosition);
                    state_list_str = selectedState;
                    System.out.println("state_list_str------" + state_list_str);

                    getDistrictList(state_list_str);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        districtListsData.clear();

        prog_off_district.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<DistrictList> userResponse = apiService.districtList("13", state_id);
        userResponse.enqueue(new Callback<DistrictList>() {

            @Override
            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
                districtList = response.body();
                prog_off_district.setVisibility(View.GONE);
                if (response.isSuccessful()) {


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
                prog_off_district.setVisibility(View.GONE);
                System.out.println("err district******" + t.toString());
                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void district_spinner_data(List<DistrictList.DistrictListData> districtListData) {

        district_list.clear();
        district_list.add("Select District");

        if (districtListData != null && !districtListData.isEmpty()) {
            for (int i = 0; i < districtListData.size(); i++) {
                district_list.add(i + 1, districtListData.get(i).getName().trim());
            }
        }

        district_adapter = new ArrayAdapter<>(
                CarrerDetailAndOtherAttributeActivity.this,
                R.layout.simple_spinner_item,
                district_list
        );
        district_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        office_district_spinner.setAdapter(district_adapter);

        // Open searchable dialog on touch
        office_district_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableDistrictDialog(district_list);
            }
            return true;
        });
    }
    private void showSearchableDistrictDialog(List<String> districtList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CarrerDetailAndOtherAttributeActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalDistrictList = new ArrayList<>(districtList);
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                CarrerDetailAndOtherAttributeActivity.this,
                android.R.layout.simple_list_item_1,
                districtList
        );
        listView.setAdapter(dialogAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                String selectedDistrict = dialogAdapter.getItem(position);
                if (selectedDistrict == null) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Error selecting district", Toast.LENGTH_SHORT).show();
                    return;
                }

                int originalPosition = -1;
                for (int i = 0; i < originalDistrictList.size(); i++) {
                    if (originalDistrictList.get(i).equals(selectedDistrict)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition != -1) {
                    office_district_spinner.setSelection(originalPosition);
                    district_str = selectedDistrict;
                    System.out.println("district_str------" + district_str);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    public void getOccupationTypeList() {

        occupationTypeData.clear();

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
                            occupationTypeData.add(new OccupationType.OccupationTypeData(occupationType.getOccupationTypeList().get(i).getId(), occupationType.getOccupationTypeList().get(i).getName()));

                        }
                        occupation_type_spinner_data(occupationTypeData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationType> call, Throwable t) {

                System.out.println("err OccupationType******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void occupation_type_spinner_data(List<OccupationType.OccupationTypeData> occupationTypeData) {

        occupational_type_list.clear();

        occupational_type_list.add("Select Occupation Type");

        if (occupational_type_list.size() != 0) {
            for (int i = 0; i < occupationTypeData.size(); i++) {

                occupational_type_list.add(i + 1, occupationTypeData.get(i).getName());

            }
        }
        adapter_occupation_type = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, occupational_type_list);

        occupation_type_spinner.setAdapter(adapter_occupation_type);


        occupation_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                occupation_type_list_str = occupation_type_spinner.getItemAtPosition(position).toString();
                System.out.println("occupation_type_list_str------" + occupation_type_list_str);

                getOccupationList(occupation_type_list_str);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getOccupationList(String occupation_type_list_str) {
        if (occupation_type_list_str != null) {
            for (int i = 0; i < occupationTypeData.size(); i++) {
                if (occupation_type_list_str.equals(occupationTypeData.get(i).getName())) {
                    occupation_type_id = String.valueOf(occupationTypeData.get(i).getId());


                }
            }
        }
        occupationListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService
                .occupationNew("27", occupation_type_id);
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                occupationList = response.body();

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

                System.out.println("err occupation******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void occupation_spinner_data(List<OccupationList.OccupationListData> occupationListData) {

        occupational_list.clear();
        occupational_list.add("Select Occupation");

        if (occupationListData != null && !occupationListData.isEmpty()) {
            for (int i = 0; i < occupationListData.size(); i++) {
                occupational_list.add(i + 1, occupationListData.get(i).getName().trim());
            }
        }

        adapter_occupation = new ArrayAdapter<>(
                CarrerDetailAndOtherAttributeActivity.this,
                R.layout.simple_spinner_item,
                occupational_list
        );
        adapter_occupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occupation_spinner.setAdapter(adapter_occupation);

        // Show searchable dialog on touch
        occupation_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableOccupationDialog(occupational_list);
            }
            return true;
        });
    }
    private void showSearchableOccupationDialog(List<String> occupationList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CarrerDetailAndOtherAttributeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalList = new ArrayList<>(occupationList);
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                CarrerDetailAndOtherAttributeActivity.this,
                android.R.layout.simple_list_item_1,
                occupationList
        );
        listView.setAdapter(dialogAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogAdapter.getFilter().filter(s);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                String selectedOccupation = dialogAdapter.getItem(position);
                if (selectedOccupation == null) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Error selecting occupation", Toast.LENGTH_SHORT).show();
                    return;
                }

                int originalPosition = -1;
                for (int i = 0; i < originalList.size(); i++) {
                    if (originalList.get(i).equals(selectedOccupation)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition != -1) {
                    occupation_spinner.setSelection(originalPosition);
                    occupation_list_str = selectedOccupation;
                    System.out.println("occupation_list_str------" + occupation_list_str);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    public void getEducationistType() {

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

                System.out.println("err education type******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void education_type_spinner_data(List<EducationType.EducationTypeList> educationTypeLists) {

        education_type_list.clear();

        education_type_list.add("Select Education Type");

        if (education_type_list.size() != 0) {
            for (int i = 0; i < educationTypeLists.size(); i++) {

                education_type_list.add(i + 1, educationTypeLists.get(i).getName());

            }
        }
        adapter_education_type = new ArrayAdapter<String>(CarrerDetailAndOtherAttributeActivity.this, R.layout.simple_spinner_item, education_type_list);

        education_type_spinner.setAdapter(adapter_education_type);


        education_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                education_type_list_str = education_type_spinner.getItemAtPosition(position).toString();
                System.out.println("education_type_list_str------" + education_type_list_str);


                getEducationList(education_type_list_str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getEducationList(String education_type_list_str) {


        if (education_type_list_str != null) {
            for (int i = 0; i < educationTypeLists.size(); i++) {
                if (education_type_list_str.equals(educationTypeLists.get(i).getName())) {
                    education_type_id = String.valueOf(educationTypeLists.get(i).getId());


                }
            }
        }
        educationListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<EducationList> userResponse = apiService.education("09", education_type_id);
        userResponse.enqueue(new Callback<EducationList>() {

            @Override
            public void onResponse(Call<EducationList> call, Response<EducationList> response) {
                educationList = response.body();

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

                System.out.println("err education******" + t.toString());

                if (!isNetworkAvailable(CarrerDetailAndOtherAttributeActivity.this)) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void education_spinner_data(List<EducationList.EducationListData> educationListData) {

        education_list.clear();
        education_list.add("Select Education");

        if (educationListData != null && !educationListData.isEmpty()) {
            for (int i = 0; i < educationListData.size(); i++) {
                education_list.add(i + 1, educationListData.get(i).getName().trim());
            }
        }

        adapter_education = new ArrayAdapter<>(
                CarrerDetailAndOtherAttributeActivity.this,
                R.layout.simple_spinner_item,
                education_list
        );
        adapter_education.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        highest_education_spinner.setAdapter(adapter_education);

        // Show searchable dialog on spinner touch
        highest_education_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableEducationDialog(education_list);
            }
            return true;
        });
    }
    private void showSearchableEducationDialog(List<String> educationList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CarrerDetailAndOtherAttributeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalEducationList = new ArrayList<>(educationList);
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                CarrerDetailAndOtherAttributeActivity.this,
                android.R.layout.simple_list_item_1,
                educationList
        );
        listView.setAdapter(dialogAdapter);

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
            try {
                String selectedEducation = dialogAdapter.getItem(position);
                if (selectedEducation == null) {
                    Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Error selecting education", Toast.LENGTH_SHORT).show();
                    return;
                }

                int originalPosition = -1;
                for (int i = 0; i < originalEducationList.size(); i++) {
                    if (originalEducationList.get(i).equals(selectedEducation)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition != -1) {
                    highest_education_spinner.setSelection(originalPosition);
                    education_list_str = selectedEducation;
                    System.out.println("education_list_str------" + education_list_str);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(CarrerDetailAndOtherAttributeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CarrerDetailAndOtherAttributeActivity.super.onBackPressed();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}
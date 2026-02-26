package com.WadhuWarProject.WadhuWar.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.CountryList;
import com.WadhuWarProject.WadhuWar.model.DistrictList;
import com.WadhuWarProject.WadhuWar.model.EducationList;
import com.WadhuWarProject.WadhuWar.model.EducationType;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.OccupationList;
import com.WadhuWarProject.WadhuWar.model.OccupationType;
import com.WadhuWarProject.WadhuWar.model.SalaryList;
import com.WadhuWarProject.WadhuWar.model.StateList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationCareerActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;

    LinearLayout update_btn;
    Spinner occupation_spinner, highest_education_spinner, office_country_spinner, office_state_spinner, office_district_spinner, salary_spinner, education_type_spinner,
            occupation_type_spinner;

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

    ArrayList<SalaryList.SalaryListData> salaryListData = new ArrayList<>();
    SalaryList salaryList;


    List<String> country_list = new ArrayList<String>();
    List<String> state_list = new ArrayList<String>();
    List<String> district_list = new ArrayList<String>();
    List<String> occupational_type_list = new ArrayList<String>();
    List<String> occupational_list = new ArrayList<String>();
    List<String> education_type_list = new ArrayList<String>();
    List<String> education_list = new ArrayList<String>();
    List<String> salary_list = new ArrayList<String>();

    String occupation_list_str, occupation_type_list_str, education_list_str, education_type_list_str, country_list_str, state_list_str, district_str, salary_str;
    String occupation_id, occupation_type_id, education_id, education_type_id, country_id, state_id, district_id, salary_id;

    ArrayAdapter<String> adapter_occupation;
    ArrayAdapter<String> adapter_occupation_type;
    ArrayAdapter<String> adapter_education;
    ArrayAdapter<String> adapter_education_type;
    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> district_adapter;
    ArrayAdapter<String> salary_adapter;


    ProgressDialog progressBar;

    EditText other_education, college_name, post_name, office_address, office_location;
    String _other_education, _post_name, _college_name, _office_address, _office_location;
    private int mHour, mMinute;
    private String format = "";
    String user_id;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;
    UserData user;
    FetchProfile fetchEducationCareer;
    Toolbar toolbar;

    InsertResponse edit_response;


    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;

    ProgressBar prog_off_district, prog_off_education, prog_off_occupation;

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
        setContentView(R.layout.activity_location_education_career);

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
//        setContentView(R.layout.activity_location_education_career);
        user = SharedPrefManager.getInstance(EducationCareerActivity.this).getUser();


        update_btn = findViewById(R.id.update_btn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        prog_off_occupation = findViewById(R.id.prog_off_occupation);
        prog_off_education = findViewById(R.id.prog_off_education);
        occupation_type_spinner = findViewById(R.id.occupation_type_spinner);
        education_type_spinner = findViewById(R.id.education_type_spinner);
        prog_off_district = findViewById(R.id.prog_off_district);
        occupation_spinner = findViewById(R.id.occupation_spinner);
        highest_education_spinner = findViewById(R.id.highest_education_spinner);
        office_country_spinner = findViewById(R.id.office_country_spinner);
        office_state_spinner = findViewById(R.id.office_state_spinner);
        office_district_spinner = findViewById(R.id.office_district_spinner);
        salary_spinner = findViewById(R.id.salary_spinner);
        other_education = findViewById(R.id.other_education);
        college_name = findViewById(R.id.college_name);
        post_name = findViewById(R.id.post_name);
        office_address = findViewById(R.id.office_address);
        office_location = findViewById(R.id.office_location);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        internetOffRL = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) findViewById(R.id.couldnt_reach_internet_txt);

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
                _other_education = other_education.getText().toString();
                _college_name = college_name.getText().toString();
                _post_name = post_name.getText().toString();
                _office_address = office_address.getText().toString();
                _office_location = office_location.getText().toString();


                if (allDone()) {

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


                    if (salary_str.equals("Select Salary")) {
                        salary_str = "";
                    }

                    updateData(education_type_id, education_id, _other_education, occupation_type_id, occupation_id, _college_name, _post_name, _office_address, _office_location, country_id, state_id, district_id, salary_str);

                }
            }
        });


        fetchEducationCareerData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


    }


    public void updateData(String education_type_id, String education_id, String _other_education, String occupation_type_id, String occupation_id, String _college_name, String _post_name, String _office_address, String _office_location,
                           String country_id, String state_id, String district_id, String salary_str) {

        System.out.println("user_id=========" + user_id);


//        progressBar = ProgressDialog.show(EducationCareerActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editEducationDetails(String.valueOf(user.getUser_id()), education_type_id, education_id, _other_education
                , occupation_type_id, occupation_id, _college_name, _post_name, _office_address, _office_location, country_id, state_id, district_id, salary_str);

        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();
//                progressBar.dismiss();

                if (response.isSuccessful()) {

                    Log.d("success", "onResponse: " + response.body());

                    String success = edit_response.getResid();

                    if (success.equals("200")) {
//                        Toast.makeText(EducationCareerActivity.this, edit_response.getResMsg(), Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(EducationCareerActivity.this, MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    } else {
//                        Toast.makeText(EducationCareerActivity.this, edit_response.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
//                progressBar.dismiss();

                Log.d("fail", "onFailure: " + t.getMessage());

                System.out.println("err accomo******" + t.toString());

                if (!isNetworkAvailable(EducationCareerActivity.this)) {
//                    Toast.makeText(EducationCareerActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(EducationCareerActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    private Boolean allDone() {

        /*EditText father_full_name,father_property,property_location,father_mobile_no,mother_full_name,mother_mobile_no,family_location,mama_full_name,mamekul,
            mama_mobile_no*/

        if (other_education == null) {
            return false;
        }

        if (college_name == null) {
            return false;
        }

        if (post_name == null) {
            return false;
        }
        if (office_address == null) {
            return false;
        }

        if (office_location == null) {
            return false;
        }


        View selectedView_highest_education_spinner = highest_education_spinner.getSelectedView();
        View selectedView_occupation_spinner = occupation_spinner.getSelectedView();
        View selectedView_office_country_spinner = office_country_spinner.getSelectedView();
        View selectedView_office_state_spinner = office_state_spinner.getSelectedView();
        View selectedView_office_district_spinner = office_district_spinner.getSelectedView();
        View selectedView_salary_spinner = salary_spinner.getSelectedView();


        if (selectedView_highest_education_spinner == null) {
            return false;
        }

        if (selectedView_occupation_spinner == null) {
            return false;
        }

        if (selectedView_office_country_spinner == null) {
            return false;
        }

        if (selectedView_office_state_spinner == null) {
            return false;
        }
        if (selectedView_office_district_spinner == null) {
            return false;
        }
        if (selectedView_salary_spinner == null) {
            return false;
        }


        return true;
    }

    //    private Boolean allDone() {
//        // Check EditText fields
//        if (other_education.getText().toString().trim().isEmpty()) {
//            Toast.makeText(this, "Please enter other education details", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (college_name.getText().toString().trim().isEmpty()) {
//            Toast.makeText(this, "Please enter college name", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (post_name.getText().toString().trim().isEmpty()) {
//            Toast.makeText(this, "Please enter post name", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (office_address.getText().toString().trim().isEmpty()) {
//            Toast.makeText(this, "Please enter office address", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (office_location.getText().toString().trim().isEmpty()) {
//            Toast.makeText(this, "Please enter office location", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        // Check spinner selections (use getSelectedItem() instead of getSelectedView())
//        if (highest_education_spinner.getSelectedItem() == null ||
//                highest_education_spinner.getSelectedItem().toString().equals("Select Education")) {
//            Toast.makeText(this, "Please select education", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (occupation_spinner.getSelectedItem() == null ||
//                occupation_spinner.getSelectedItem().toString().equals("Select Occupation")) {
//            Toast.makeText(this, "Please select occupation", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (occupation_type_spinner.getSelectedItem() == null ||
//                occupation_type_spinner.getSelectedItem().toString().equals("Select Occupation Type")) {
//            Toast.makeText(this, "Please select occupation type", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (education_type_spinner.getSelectedItem() == null ||
//                education_type_spinner.getSelectedItem().toString().equals("Select Education Type")) {
//            Toast.makeText(this, "Please select education type", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (office_country_spinner.getSelectedItem() == null ||
//                office_country_spinner.getSelectedItem().toString().equals("Select Country")) {
//            Toast.makeText(this, "Please select country", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (office_state_spinner.getSelectedItem() == null ||
//                office_state_spinner.getSelectedItem().toString().equals("Select State")) {
//            Toast.makeText(this, "Please select state", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (office_district_spinner.getSelectedItem() == null ||
//                office_district_spinner.getSelectedItem().toString().equals("Select District")) {
//            Toast.makeText(this, "Please select district", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (salary_spinner.getSelectedItem() == null ||
//                salary_spinner.getSelectedItem().toString().equals("Select Salary")) {
//            Toast.makeText(this, "Please select salary", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        return true;
//    }
    @Override
    public void networkAvailable() {

//        fetchEducationCareerData();


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                stateListsData.clear();
                districtListsData.clear();

                fetchEducationCareerData();

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

    public void fetchEducationCareerData() {

        System.out.println("user_id111=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchEducationDetails(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {

                swipeRefreshLayout.setRefreshing(false);
                fetchEducationCareer = response.body();


                /*System.out.println("education -------" + new Gson().toJson(fetchEducationCareer));*/

                if (response.isSuccessful()) {
//                    update_btn.setVisibility(View.VISIBLE);


                    other_education.setText(getValidText(fetchEducationCareer.getOther_education()));
                    college_name.setText(getValidText(fetchEducationCareer.getCollege_name()));
                    post_name.setText(getValidText(fetchEducationCareer.getPost_name()));
                    office_address.setText(getValidText(fetchEducationCareer.getOfc_address()));
                    office_location.setText(getValidText(fetchEducationCareer.getOfc_loc()));

                    getOccupationTypeList();
//                    getOccupationList();
                    getEducationListType();
//                    getEducationList();
                    getCountryList();
                    getSalaryList();
                }
            }

            private String getValidText(String value) {
                if (value == null || value.trim().equalsIgnoreCase("Not Specified") || value.trim().equalsIgnoreCase("Not Specif")) {
                    return "";
                }
                return value;
            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 error fetch family******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                Toast.makeText(EducationCareerActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

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

                if (!isNetworkAvailable(EducationCareerActivity.this)) {
//                    Toast.makeText(EducationCareerActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(EducationCareerActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        salary_adapter = new ArrayAdapter<String>(EducationCareerActivity.this, R.layout.simple_spinner_item, salary_list);

        salary_spinner.setAdapter(salary_adapter);


        int pos;
        for (int i = 0; i < salary_list.size(); i++) {
            if (salary_list.get(i).contains(fetchEducationCareer.getYearly_salary())) {
                pos = i;
                salary_spinner.setSelection(pos);
            }
        }

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

                if (!isNetworkAvailable(EducationCareerActivity.this)) {
//                    Toast.makeText(EducationCareerActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(EducationCareerActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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


        adapter_country = new ArrayAdapter<String>(EducationCareerActivity.this, R.layout.simple_spinner_item, country_list);

        office_country_spinner.setAdapter(adapter_country);


        int pos;
        for (int i = 0; i < country_list.size(); i++) {
            if (country_list.get(i).contains(fetchEducationCareer.getOfc_country_name())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                office_country_spinner.setSelection(pos);
            }
        }

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
                state_list.add(i + 1, stateLists.get(i).getSc_name());
            }
        }

        adapter_state = new ArrayAdapter<>(EducationCareerActivity.this,
                R.layout.simple_spinner_item, state_list);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        office_state_spinner.setAdapter(adapter_state);

        // Set default selection
        if (fetchEducationCareer.getOfc_state_name() != null &&
                !fetchEducationCareer.getOfc_state_name().isEmpty()) {
            for (int i = 0; i < state_list.size(); i++) {
                if (state_list.get(i).equals(fetchEducationCareer.getOfc_state_name())) {
                    office_state_spinner.setSelection(i);
                    System.out.println("state i-----------" + i);
                    // Don't trigger district loading here during initialization
                    // It will be handled in for_state_spinner_data()
                    break;
                }
            }
        }

        // Only set the listener after initial selection
        office_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Skip "Select State"
                    state_list_str = office_state_spinner.getItemAtPosition(position).toString();
                    System.out.println("state_list_str------" + state_list_str);
                    getDistrictList(state_list_str);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }    // Method to show a searchable dialog for state selection
    private void showSearchableStateDialog(List<String> stateList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(EducationCareerActivity.this);
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
                EducationCareerActivity.this,
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
                    Toast.makeText(EducationCareerActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(EducationCareerActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                state_list_str = selectedState;
                Log.d("StateDialog", "Selected state: " + state_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                office_state_spinner.setSelection(originalPosition);

                // Call existing method to handle district updates
                System.out.println("state_list_str------" + state_list_str);
                getDistrictList(state_list_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("StateDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(EducationCareerActivity.this, "Error selecting state: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                if (!isNetworkAvailable(EducationCareerActivity.this)) {
//                    Toast.makeText(EducationCareerActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(EducationCareerActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



    public void district_spinner_data(List<DistrictList.DistrictListData> districtListData) {
        district_list.clear();
        district_list.add("Select District");

        if (districtListData != null && !districtListData.isEmpty()) {
            for (int i = 0; i < districtListData.size(); i++) {
                district_list.add(i + 1, districtListData.get(i).getName());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        district_adapter = new ArrayAdapter<>(EducationCareerActivity.this,
                R.layout.simple_spinner_item, district_list);
        district_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        office_district_spinner.setAdapter(district_adapter);

        // Set default selection if fetchEducationCareer.getOfc_ditrict_name() exists
        if (fetchEducationCareer.getOfc_ditrict_name() != null &&
                !fetchEducationCareer.getOfc_ditrict_name().isEmpty() &&
                !fetchEducationCareer.getOfc_ditrict_name().equals("Not Specified")) {

            for (int i = 0; i < district_list.size(); i++) {
                if (district_list.get(i).equals(fetchEducationCareer.getOfc_ditrict_name())) {
                    office_district_spinner.setSelection(i);
                    // CRITICAL: Set district_str when setting initial selection
                    district_str = district_list.get(i);
                    System.out.println("Initial district set: " + district_str + " at position: " + i);
                    break;
                }
            }
        } else {
            district_str = "";
        }

        // Handle Spinner click to show searchable dialog
        office_district_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableDistrictDialog(district_list);
            }
            return true; // Consume the touch event
        });
    }    // Method to show a searchable dialog for district selection
    private void showSearchableDistrictDialog(List<String> districtList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(EducationCareerActivity.this);
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
                EducationCareerActivity.this,
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
                    Toast.makeText(EducationCareerActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If "Select District" is selected, set empty string
                if (selectedDistrict.equals("Select District")) {
                    district_str = "";
                    office_district_spinner.setSelection(0);
                    dialog.dismiss();
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
                    Toast.makeText(EducationCareerActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                district_str = selectedDistrict;
                Log.d("DistrictDialog", "Selected district: " + district_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                office_district_spinner.setSelection(originalPosition);

                // Log the selection as per the original logic
                System.out.println("district_str------" + district_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("DistrictDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(EducationCareerActivity.this, "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

                if (!isNetworkAvailable(EducationCareerActivity.this)) {
//                    Toast.makeText(EducationCareerActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(EducationCareerActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_occupation_type = new ArrayAdapter<String>(EducationCareerActivity.this, R.layout.simple_spinner_item, occupational_type_list);

        occupation_type_spinner.setAdapter(adapter_occupation_type);

        int pos;
        for (int i = 0; i < occupational_type_list.size(); i++) {
            if (occupational_type_list.get(i).contains(fetchEducationCareer.getMain_occupation_name())) {
                pos = i;
                occupation_type_spinner.setSelection(pos);
            }
        }


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

        prog_off_occupation.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService.occupationNew("27", occupation_type_id);
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                occupationList = response.body();
                prog_off_occupation.setVisibility(View.GONE);


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
                prog_off_occupation.setVisibility(View.GONE);

                System.out.println("err occupation******" + t.toString());

                if (!isNetworkAvailable(EducationCareerActivity.this)) {
//                    Toast.makeText(EducationCareerActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(EducationCareerActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void occupation_spinner_data(List<OccupationList.OccupationListData> occupationListData) {

        occupational_list.clear();

        occupational_list.add("Select Occupation");

        if (occupational_list.size() != 0) {
            for (int i = 0; i < occupationListData.size(); i++) {

                occupational_list.add(i + 1, occupationListData.get(i).getName());

            }
        }
        adapter_occupation = new ArrayAdapter<String>(EducationCareerActivity.this, R.layout.simple_spinner_item, occupational_list);

        occupation_spinner.setAdapter(adapter_occupation);

        int pos;
        for (int i = 0; i < occupational_list.size(); i++) {
            if (occupational_list.get(i).contains(fetchEducationCareer.getOccupation_name())) {
                pos = i;
                occupation_spinner.setSelection(pos);
            }
        }


        occupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                occupation_list_str = occupation_spinner.getItemAtPosition(position).toString();
                System.out.println("religion_list_str------" + occupation_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getEducationListType() {

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

                if (!isNetworkAvailable(EducationCareerActivity.this)) {
//                    Toast.makeText(EducationCareerActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(EducationCareerActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_education_type = new ArrayAdapter<String>(EducationCareerActivity.this, R.layout.simple_spinner_item, education_type_list);

        education_type_spinner.setAdapter(adapter_education_type);


        int pos;
        for (int i = 0; i < education_type_list.size(); i++) {
            if (education_type_list.get(i).equals(fetchEducationCareer.getMain_education_name())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                education_type_spinner.setSelection(pos);
            }
        }

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

        System.out.println("education_type_id========" + education_type_id);

        prog_off_education.setVisibility(View.VISIBLE);


        Api apiService = RetrofitClient.getApiService();
        Call<EducationList> userResponse = apiService.education("09", education_type_id);
        userResponse.enqueue(new Callback<EducationList>() {

            @Override
            public void onResponse(Call<EducationList> call, Response<EducationList> response) {

                educationList = response.body();
                prog_off_education.setVisibility(View.GONE);

                if (response.isSuccessful()) {


                    String success = educationList.getResid();

                    System.out.println("resp of education===========" + new Gson().toJson(response.body()));

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

                prog_off_education.setVisibility(View.GONE);
                System.out.println("err education******" + t.toString());

                if (!isNetworkAvailable(EducationCareerActivity.this)) {
//                    Toast.makeText(EducationCareerActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(EducationCareerActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    // for searchble education 27-5-2025
    public void education_spinner_data(List<EducationList.EducationListData> educationListData) {
        education_list.clear();
        education_list.add("Select Education");

        if (educationListData != null && !educationListData.isEmpty()) {
            for (int i = 0; i < educationListData.size(); i++) {
                education_list.add(i + 1, educationListData.get(i).getName());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        adapter_education = new ArrayAdapter<>(EducationCareerActivity.this,
                R.layout.simple_spinner_item, education_list);
        adapter_education.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        highest_education_spinner.setAdapter(adapter_education);

        // Set default selection if fetchEducationCareer.getEducation_name() exists
        if (fetchEducationCareer.getEducation_name() != null && !fetchEducationCareer.getEducation_name().isEmpty()) {
            for (int i = 0; i < education_list.size(); i++) {
                if (education_list.get(i).equals(fetchEducationCareer.getEducation_name())) {
                    highest_education_spinner.setSelection(i);
                    System.out.println("state i-----------" + i);
                    break;
                }
            }
        }

        // Handle Spinner click to show searchable dialog
        highest_education_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableEducationDialog(education_list);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for education selection
    private void showSearchableEducationDialog(List<String> educationList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(EducationCareerActivity.this);
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
                EducationCareerActivity.this,
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
                    Toast.makeText(EducationCareerActivity.this, "Error selecting education. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(EducationCareerActivity.this, "Error selecting education. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                education_list_str = selectedEducation;
                Log.d("EducationDialog", "Selected education: " + education_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                highest_education_spinner.setSelection(originalPosition);

                // Log the selection as per the original logic
                System.out.println("education_list_str------" + education_list_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("EducationDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(EducationCareerActivity.this, "Error selecting education: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

}
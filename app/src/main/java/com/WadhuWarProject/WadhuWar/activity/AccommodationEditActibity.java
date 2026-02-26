package com.WadhuWarProject.WadhuWar.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
import com.WadhuWarProject.WadhuWar.model.FetchAccommodation;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.StateList;
import com.WadhuWarProject.WadhuWar.model.TalukaList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationEditActibity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    EditText current_address, permanent_address, current_village, permant_village,curr_taluka,per_taluka;
    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;

    CheckBox checkBox1;

    RelativeLayout submit_btn;
    TextView a1;

    Spinner current_country_spinner, current_state_spinner, current_district_spinner, current_taluka_spinner,
            permant_taluka_spinner, permant_district_spinner, permant_state_spinner, permant_country_spinner;

    ArrayList<CountryList.CountryListData> countryListData = new ArrayList<>();
    CountryList countryList;
    ArrayList<StateList> stateListsData = new ArrayList<>();
    StateList stateList;

    ArrayList<DistrictList.DistrictListData> districtListsData = new ArrayList<>();
    DistrictList districtList;
    ArrayList<TalukaList.TalukaListData> talukaListData = new ArrayList<>();
    TalukaList talukaList;

    ArrayList<CountryList.CountryListData> countryListData11 = new ArrayList<>();
    CountryList countryList11;
    ArrayList<StateList> stateListsData11 = new ArrayList<>();
    StateList stateList11;
    ArrayList<DistrictList.DistrictListData> districtListsData11 = new ArrayList<>();
    DistrictList districtList11;
    ArrayList<TalukaList.TalukaListData> talukaListData11 = new ArrayList<>();
    TalukaList talukaList11;

    List<String> country_list11 = new ArrayList<String>();
    List<String> state_list11 = new ArrayList<String>();
    List<String> district_list11 = new ArrayList<String>();
    List<String> taluka_list11 = new ArrayList<String>();


    List<String> country_list = new ArrayList<String>();
    List<String> state_list = new ArrayList<String>();
    List<String> district_list = new ArrayList<String>();
    List<String> taluka_list = new ArrayList<String>();

    String country_list_str, state_list_str, district_str, taluka_str;
    String country_list_str11, state_list_str11, district_str11, taluka_str11;

    String country_id, district_id, state_id, taluka_name;
    String country_id11, district_id11, state_id11, taluka_per_name;


    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> adapter_country11;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> adapter_state11;
    ArrayAdapter<String> district_adapter;
    ArrayAdapter<String> district_adapter11;
    ArrayAdapter<String> taluka_adapter;
    ArrayAdapter<String> taluka_adapter11;


    String currentAddress, currentCountry, currentState, currentDistrict, currentTaluka, currentVillage,
            permanentAddress, permanentCountry, permanentState, permanentDistrict, permanentTaluka, permanentVillage;
    String user_id;
    ProgressDialog progressBar;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;

    FetchAccommodation fetchAccommodation;
    UserData user;
    Toolbar toolbar;

    LinearLayout update_btn;
    InsertResponse edit_response;
    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;

    ProgressBar prog_district, prog_taluka, prog_per_district, prog_per_taluka;


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
        setContentView(R.layout.activity_accomodation_edit);

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
//        setContentView(R.layout.activity_accomodation_edit);

        user = SharedPrefManager.getInstance(AccommodationEditActibity.this).getUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        prog_per_district = findViewById(R.id.prog_per_district);
        prog_per_taluka = findViewById(R.id.prog_per_taluka);
        prog_taluka = findViewById(R.id.prog_taluka);
        prog_district = findViewById(R.id.prog_district);
        update_btn = findViewById(R.id.update_btn);
        current_country_spinner = findViewById(R.id.current_country_spinner);
        current_state_spinner = findViewById(R.id.current_state_spinner);
        current_district_spinner = findViewById(R.id.current_district_spinner);
//        current_taluka_spinner = findViewById(R.id.current_taluka_spinner);
        checkBox1 = findViewById(R.id.checkBox1);
//        permant_taluka_spinner = findViewById(R.id.permant_taluka_spinner);
        permant_district_spinner = findViewById(R.id.permant_district_spinner);
        permant_state_spinner = findViewById(R.id.permant_state_spinner);
        permant_country_spinner = findViewById(R.id.permant_country_spinner);
        current_address = findViewById(R.id.current_address);
        permanent_address = findViewById(R.id.permanent_address);
        current_village = findViewById(R.id.current_village);
        permant_village = findViewById(R.id.permant_village);
        curr_taluka = findViewById(R.id.current_taluka);
        per_taluka = findViewById(R.id.permanent_taluka);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        internetOffRL = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


        Intent i = getIntent();
        user_id = (i.getStringExtra("user_id"));

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


        update_btn.setOnClickListener(v -> {

            currentAddress = current_address.getText().toString();
            currentCountry = country_list_str;
            currentState = state_list_str;
            currentDistrict = district_str;
//            currentTaluka = taluka_str;
            currentVillage = current_village.getText().toString();
            permanentAddress = permanent_address.getText().toString();
            permanentCountry = country_list_str11;
            permanentState = state_list_str11;
            permanentDistrict = district_str11;
//            permanentTaluka = taluka_str11;
            permanentVillage = permant_village.getText().toString();
            currentTaluka = curr_taluka.getText().toString();
            permanentTaluka = per_taluka.getText().toString();
            if (allDone()) {

                if (checkValidation()) {

                    if (!isNetworkAvailable(AccommodationEditActibity.this)) {

                        Snackbar mSnackbar = Snackbar.make(v, "Please Check Internet Connection!", Snackbar.LENGTH_LONG);
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


                        if (country_list_str != null && !country_list_str.contentEquals("")) {
                            for (int i1 = 0; i1 < countryListData.size(); i1++) {
                                if (country_list_str.equals(countryListData.get(i1).getName())) {
                                    country_id = String.valueOf(countryListData.get(i1).getId());

                                }
                            }
                        } else {
                            country_id = "";
                        }
                        if (state_list_str != null && !state_list_str.contentEquals("")) {
                            for (int i1 = 0; i1 < stateListsData.size(); i1++) {
                                if (state_list_str.equals(stateListsData.get(i1).getSc_name())) {
                                    state_id = String.valueOf(stateListsData.get(i1).getSc_id());

                                }
                            }
                        } else {
                            state_id = "";
                        }
                        if (district_str != null && !district_str.contentEquals("")) {
                            for (int i1 = 0; i1 < districtListsData.size(); i1++) {
                                if (district_str.equals(districtListsData.get(i1).getName())) {
                                    district_id = String.valueOf(districtListsData.get(i1).getId());

                                }
                            }
                        } else {
                            district_id = "";
                        }
                        if (taluka_str != null && !taluka_str.contentEquals("")) {
                            for (int i1 = 0; i1 < talukaListData.size(); i1++) {
                                if (taluka_str.equals(talukaListData.get(i1).getName())) {
//                                    taluka_id = String.valueOf(talukaListData.get(i1).getId());

                                }
                            }
                        } else {
//                            taluka_id = "";
                        }
                        if (country_list_str11 != null && !country_list_str11.contentEquals("")) {
                            for (int i1 = 0; i1 < countryListData11.size(); i1++) {
                                if (country_list_str11.equals(countryListData11.get(i1).getName())) {
                                    country_id11 = String.valueOf(countryListData11.get(i1).getId());

                                }
                            }
                        } else {
                            country_id11 = "";
                        }
                        if (state_list_str11 != null && !state_list_str11.contentEquals("")) {
                            for (int i1 = 0; i1 < stateListsData11.size(); i1++) {
                                if (state_list_str11.equals(stateListsData11.get(i1).getSc_name())) {
                                    state_id11 = String.valueOf(stateListsData11.get(i1).getSc_id());

                                }
                            }
                        } else {
                            state_id11 = "";
                        }
                        if (district_str11 != null && !district_str11.contentEquals("")) {
                            for (int i1 = 0; i1 < districtListsData11.size(); i1++) {
                                if (district_str11.equals(districtListsData11.get(i1).getName())) {
                                    district_id11 = String.valueOf(districtListsData11.get(i1).getId());

                                }
                            }
                        } else {
                            district_id11 = "";
                        }
                        if (taluka_str11 != null && !taluka_str11.contentEquals("")) {
                            for (int i1 = 0; i1 < talukaListData11.size(); i1++) {
                                if (taluka_str11.equals(talukaListData11.get(i1).getName())) {
//                                    taluka_id11 = String.valueOf(talukaListData11.get(i1).getId());

                                }
                            }
                        } else {
//                            taluka_id11 = "";
                        }

//                        EditText currentTalukaEditText = findViewById(R.id.current_taluka_edittext);
//                        taluka_name = currentTalukaEditText.getText().toString().trim();



                        System.out.println("currentAddress --" + currentAddress);
                        System.out.println("currentCountry --" + country_id);
                        System.out.println("currentState --" + state_id);
                        System.out.println("currentDistrict --" + district_id);
                        System.out.println("currentTaluka --" + taluka_name);
                        System.out.println("currentVillage --" + currentVillage);
                        System.out.println("permanentAddress --" + permanentAddress);
                        System.out.println("permanentCountry --" + country_id11);
                        System.out.println("permanentState --" + state_id11);
                        System.out.println("permanentDistrict --" + district_id11);
                        System.out.println("permanentTaluka --" + taluka_per_name);
                        System.out.println("permanentVillage --" + permanentVillage);


                        updateData(currentAddress, country_id, state_id, district_id, currentTaluka, currentVillage,
                                permanentAddress, country_id11, state_id11, district_id11, permanentTaluka, permanentVillage, user_id);

                    }
                }

            }
        });

        fetchAccommodationData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }


    @Override
    public void networkAvailable() {

//        fetchAccommodationData();


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (countryListData != null)
                    countryListData.clear();

                if (countryListData11 != null)
                    countryListData11.clear();

                stateListsData.clear();
                stateListsData11.clear();
                districtListsData.clear();
                districtListsData11.clear();
                talukaListData.clear();
                talukaListData11.clear();

                fetchAccommodationData();

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

                Toast.makeText(AccommodationEditActibity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

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


    public void fetchAccommodationData() {

        System.out.println("user_id111=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchAccommodation> userResponse = apiService.fetchAccommodation(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchAccommodation>() {

            @Override
            public void onResponse(Call<FetchAccommodation> call, Response<FetchAccommodation> response) {

                swipeRefreshLayout.setRefreshing(false);

                fetchAccommodation = response.body();


                /*System.out.println("fetme -------" + new Gson().toJson(fetchAccommodation));*/

                if (response.isSuccessful()) {

//                    update_btn.setVisibility(View.VISIBLE);

                    current_address.setText(fetchAccommodation.getCurrent_address());
                    current_village.setText(fetchAccommodation.getCurrent_village());
                    permanent_address.setText(fetchAccommodation.getPermnt_address());
                    permant_village.setText(fetchAccommodation.getPermnt_village());
//                    Toast.makeText(AccommodationEditActibity.this, "taluka"+fetchAccommodation.getCurr_taluka_name() , Toast.LENGTH_SHORT).show();
                    curr_taluka.setText(getValidText(fetchAccommodation.getCurr_taluka_name()));
                    per_taluka.setText(getValidText(fetchAccommodation.getPer_taluka_name()));
//                    per_taluka.setText(fetchAccommodation.getPer_taluka_name());
                    getCountryList();
                    getCountryList11();


                }

            }

            private String getValidText(String value) {
                if (value == null || value.trim().equalsIgnoreCase("Not Specified") || value.trim().equalsIgnoreCase("Not Specif") || value.trim().equalsIgnoreCase("NotSpecified")) {
                    return "";
                }
                return value;
            }

            @Override
            public void onFailure(Call<FetchAccommodation> call, Throwable t) {
                System.out.println("msg1 error fetch about me******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    public void updateData(String currentAddress, String country_id, String state_id, String district_id, String currentTaluka, String currentVillage,
                           String permanentAddress, String country_id11, String state_id11, String district_id11, String permanentTaluka, String permanentVillage, String user_id) {

        progressBar = ProgressDialog.show(AccommodationEditActibity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editAccommodation(String.valueOf(user.getUser_id()), currentAddress, country_id, state_id, district_id, currentTaluka,
                currentVillage, permanentAddress, country_id11, state_id11, district_id11, permanentTaluka, permanentVillage);
        userResponse.enqueue(new Callback<InsertResponse>() {


            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {
                    String success = edit_response.getResid();
                    if (success.equals("200")) {
                        Toast.makeText(AccommodationEditActibity.this, edit_response.getResMsg(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AccommodationEditActibity.this, MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(AccommodationEditActibity.this, edit_response.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err accomo******" + t);

                if (!isNetworkAvailable(AccommodationEditActibity.this)) {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
    }// isNetworkAvailable()

    // tanmay 24-12-2024
//    public void onCheckBoxClick(View view) {
//
//        boolean checked = ((CheckBox) view).isChecked();
//        // Check which checkbox was clicked
//        switch (view.getId()) {
//            case R.id.checkBox1:
//                if (checked) {
//
//                    if (country_list_str != null) {
//                        int spinnerPosition = adapter_country.getPosition(country_list_str);
//                        System.out.println("name country_list_str pos-----------" + country_list_str);
//                        System.out.println("country pos-----------" + spinnerPosition);
//
//                        permant_country_spinner.setSelection(spinnerPosition);
//                    }
//
//
//                    for_state_spinner_data11(country_list_str);
//
//                    getDistrictList11(state_list_str11);
//                    getTalukaList11(district_str11);
//
//
//                    String permanent_address_ = current_address.getText().toString();
//                    permanent_address.setText(permanent_address_);
//
//                    String permant_village_ = current_village.getText().toString();
//                    permant_village.setText(permant_village_);
//
//
//                }
//
//                break;
//            // Perform your logic
//        }
//    }

    public void onCheckBoxClick(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        if (view.getId() == R.id.checkBox1) { // Ensure this ID exists in your XML layout
            if (checked) {
                // Handle logic when the checkbox is checked
                if (country_list_str != null) {
                    // Ensure `adapter_country` is initialized
                    int spinnerPosition = adapter_country.getPosition(country_list_str);
                    System.out.println("name country_list_str pos-----------" + country_list_str);
                    System.out.println("country pos-----------" + spinnerPosition);

                    permant_country_spinner.setSelection(spinnerPosition);
                }

                // Call required functions with valid arguments
                for_state_spinner_data11(country_list_str);
                getDistrictList11(state_list_str11);
                getTalukaList11(district_str11);

                // Update the permanent address fields
                String permanent_address_ = current_address.getText().toString();
                permanent_address.setText(permanent_address_);

                String permant_village_ = current_village.getText().toString();
                permant_village.setText(permant_village_);

                String currenrT = curr_taluka.getText().toString();
                per_taluka.setText(currenrT);
            }
        }
    }

    private Boolean allDone() {

        if (current_address == null) {
            return false;
        }

        if (current_village == null) {
            return false;
        }
        if (permanent_address == null) {
            return false;
        }

        if (permant_village == null) {
            return false;
        }


        View selectedView_curr_country11 = current_country_spinner.getSelectedView();
        View selectedView_curr_state11 = current_state_spinner.getSelectedView();
        View selectedView_curr_dist11 = current_district_spinner.getSelectedView();
//        View selectedView_curr_taluka11 = current_taluka_spinner.getSelectedView();
        View selectedView_per_country11 = permant_country_spinner.getSelectedView();
        View selectedView_per_state11 = permant_state_spinner.getSelectedView();
        View selectedView_per_dist11 = permant_district_spinner.getSelectedView();
//        View selectedView_per_taluka11 = permant_taluka_spinner.getSelectedView();


        if (selectedView_curr_country11 == null) {
            return false;
        }

        if (selectedView_curr_state11 == null) {
            return false;
        }

        if (selectedView_curr_dist11 == null) {
            return false;
        }

//        if (selectedView_curr_taluka11 == null) {
//            return false;
//        }
        if (selectedView_per_country11 == null) {
            return false;
        }
        if (selectedView_per_state11 == null) {
            return false;
        }

        if (selectedView_per_dist11 == null) {
            return false;
        }

//        if (selectedView_per_taluka11 == null) {
//            return false;
//        }


        return true;
    }


    private Boolean checkValidation() {

        if (current_address.getText().toString().isEmpty()) {
            current_address.setError("Please enter Current Address");
            current_address.requestFocus();
            return false;
        } else if (current_address.getText().toString().length() < 10) {
            current_address.setError("Address should be atleast 10 character long");
            current_address.requestFocus();
            return false;
        }

        View selectedView_currentcountry = current_country_spinner.getSelectedView();
        if (selectedView_currentcountry != null && selectedView_currentcountry instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_currentcountry;
            if (selectedTextView.getText().equals("Select Country")) {
                selectedTextView.setError("Please Select Country");
                selectedTextView.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }
        }
        View selectedView_currentstate = current_state_spinner.getSelectedView();
        if (selectedView_currentstate != null && selectedView_currentstate instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_currentstate;
            if (selectedTextView.getText().equals("Select State")) {
                selectedTextView.setError("Please Select State");
                selectedTextView.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }
        }
        View selectedView_currentdistrict = current_district_spinner.getSelectedView();
        if (selectedView_currentdistrict != null && selectedView_currentdistrict instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_currentdistrict;
            if (selectedTextView.getText().equals("Select District")) {
                selectedTextView.setError("Please Select District");
                selectedTextView.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }
        }
//        View selectedView_currenttaluka = current_taluka_spinner.getSelectedView();
//        if (selectedView_currenttaluka != null && selectedView_currenttaluka instanceof TextView) {
//            TextView selectedTextView = (TextView) selectedView_currenttaluka;
//            if (selectedTextView.getText().equals("Select Tahsil")) {
//                selectedTextView.setError("Please Select Tahsil");
//                selectedTextView.requestFocus();
//                selectedTextView.setTextColor(Color.RED);
//                return false;
//            }
//
//        }
        if (current_village.getText().toString().isEmpty()) {
            current_village.setError("Please enter Current Village");
            current_village.requestFocus();
            return false;
        }
        return true;
    }

    /*----end current spinner data--*/
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

                if (!isNetworkAvailable(AccommodationEditActibity.this)) {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
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


        adapter_country = new ArrayAdapter<String>(AccommodationEditActibity.this, R.layout.simple_spinner_item, country_list);

        current_country_spinner.setAdapter(adapter_country);


        int pos;
        for (int i = 0; i < country_list.size(); i++) {
            if (country_list.get(i).contains(fetchAccommodation.getCurr_country_name())) {
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

    // for searchable state
    public void state_spinner_data(List<StateList> stateLists) {
        state_list.clear();
        state_list.add("Select State");

        if (stateLists != null && !stateLists.isEmpty()) {
            for (int i = 0; i < stateLists.size(); i++) {
                state_list.add(i + 1, stateLists.get(i).getSc_name());
            }
        }

        adapter_state = new ArrayAdapter<>(AccommodationEditActibity.this,
                R.layout.simple_spinner_item, state_list);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_state_spinner.setAdapter(adapter_state);

        // Find and set state position
        int statePos = -1;
        String savedState = fetchAccommodation.getCurr_state_name();
        if (savedState != null && !savedState.isEmpty()) {
            for (int i = 0; i < state_list.size(); i++) {
                if (state_list.get(i).equals(savedState)) {
                    statePos = i;
                    System.out.println("State found at position: " + statePos);
                    current_state_spinner.setSelection(statePos);
                    state_list_str = state_list.get(i);
                    break;
                }
            }
        }

        // Set up listener AFTER setting initial selection
        current_state_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableStateDialog(state_list);
            }
            return true;
        });

        // IMPORTANT: If we found and set the state, trigger district loading
        if (statePos > 0) {
            // Add a small delay to ensure spinner is ready
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getDistrictList(state_list_str);
                }
            }, 300);
        }
    }
//    public void state_spinner_data(List<StateList> stateLists) {
//        state_list.clear();
//        state_list.add("Select State");
//
//        if (stateLists != null && !stateLists.isEmpty()) {
//            for (int i = 0; i < stateLists.size(); i++) {
//                state_list.add(i + 1, stateLists.get(i).getSc_name());
//            }
//        }
//
//        // Set up the ArrayAdapter for the Spinner
//        adapter_state = new ArrayAdapter<>(AccommodationEditActibity.this,
//                R.layout.simple_spinner_item, state_list);
//        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        current_state_spinner.setAdapter(adapter_state);
//
//        // Set default selection if curr_state_name exists
//        if (fetchAccommodation.getCurr_state_name() != null && !fetchAccommodation.getCurr_state_name().isEmpty()) {
//            for (int i = 0; i < state_list.size(); i++) {
//                if (state_list.get(i).equals(fetchAccommodation.getCurr_state_name())) {
//                    current_state_spinner.setSelection(i);
//                    System.out.println("state i-----------" + i);
//                    break;
//                }
//            }
//        }
//
//        // Handle Spinner click to show searchable dialog
//        current_state_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableStateDialog(state_list);
//            }
//            return true; // Consume the touch event
//        });
//    }
    // Method to show a searchable dialog for state selection
    private void showSearchableStateDialog(List<String> stateList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AccommodationEditActibity.this);
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
                AccommodationEditActibity.this,
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
                    Toast.makeText(AccommodationEditActibity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AccommodationEditActibity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AccommodationEditActibity.this, "Error selecting state: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    // commented for searhable 27-5-2025
//    public void state_spinner_data(List<StateList> stateLists) {
//
//        state_list.clear();
//
//        state_list.add("Select State");
//
//        if (state_list.size() != 0) {
//            for (int i = 0; i < stateLists.size(); i++) {
//
//                state_list.add(i + 1, stateLists.get(i).getSc_name());
//
//            }
//        }
//
//
//        adapter_state = new ArrayAdapter<String>(AccommodationEditActibity.this, R.layout.simple_spinner_item, state_list);
//
//        current_state_spinner.setAdapter(adapter_state);
//
//        int pos;
//        for (int i = 0; i < state_list.size(); i++) {
//            if (state_list.get(i).contains(fetchAccommodation.getCurr_state_name())) {
//                pos = i;
//                System.out.println("state i-----------" + pos);
//                current_state_spinner.setSelection(pos);
//            }
//        }
//
//        current_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                state_list_str = current_state_spinner.getItemAtPosition(position).toString();
//
//                System.out.println("state_list_str------" + state_list_str);
//
//                getDistrictList(state_list_str);
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

        prog_district.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<DistrictList> userResponse = apiService.districtList("13", state_id);
        userResponse.enqueue(new Callback<DistrictList>() {

            @Override
            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
                districtList = response.body();
                prog_district.setVisibility(View.GONE);

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
                prog_district.setVisibility(View.GONE);

                System.out.println("err district******" + t.toString());
                if (!isNetworkAvailable(AccommodationEditActibity.this)) {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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

        district_adapter = new ArrayAdapter<>(AccommodationEditActibity.this,
                R.layout.simple_spinner_item, district_list);
        district_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_district_spinner.setAdapter(district_adapter);

        // Find and set district position
        String savedDistrict = fetchAccommodation.getCurr_district_name();
        if (savedDistrict != null && !savedDistrict.isEmpty()) {
            for (int i = 0; i < district_list.size(); i++) {
                if (district_list.get(i).equals(savedDistrict)) {
                    System.out.println("District found at position: " + i);
                    current_district_spinner.setSelection(i);
                    district_str = district_list.get(i);
                    break;
                }
            }
        }

        // Set up listener AFTER setting initial selection
        current_district_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableDistrictDialog(district_list);
            }
            return true;
        });

        // IMPORTANT: If we found and set the district, trigger taluka loading
        if (district_str != null && !district_str.isEmpty()) {
            // Add a small delay to ensure spinner is ready
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getTalukaList(district_str);
                }
            }, 300);
        }
    }
//    public void district_spinner_data(List<DistrictList.DistrictListData> districtListData) {
//        district_list.clear();
//        district_list.add("Select District");
//
//        if (districtListData != null && !districtListData.isEmpty()) {
//            for (int i = 0; i < districtListData.size(); i++) {
//                district_list.add(i + 1, districtListData.get(i).getName());
//            }
//        }
//
//        // Set up the ArrayAdapter for the Spinner
//        district_adapter = new ArrayAdapter<>(AccommodationEditActibity.this,
//                R.layout.simple_spinner_item, district_list);
//        district_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        current_district_spinner.setAdapter(district_adapter);
//
//        // Set default selection if curr_district_name exists
//        if (fetchAccommodation.getCurr_district_name() != null && !fetchAccommodation.getCurr_district_name().isEmpty()) {
//            for (int i = 0; i < district_list.size(); i++) {
//                if (district_list.get(i).equals(fetchAccommodation.getCurr_district_name())) {
//                    current_district_spinner.setSelection(i);
//                    System.out.println("state i-----------" + i);
//                    break;
//                }
//            }
//        }
//
//        // Handle Spinner click to show searchable dialog
//        current_district_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableDistrictDialog(district_list);
//            }
//            return true; // Consume the touch event
//        });
//    }
    // Method to show a searchable dialog for district selection
    private void showSearchableDistrictDialog(List<String> districtList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AccommodationEditActibity.this);
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
                AccommodationEditActibity.this,
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
                    Toast.makeText(AccommodationEditActibity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AccommodationEditActibity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                district_str = selectedDistrict;
                Log.d("DistrictDialog", "Selected district: " + district_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                current_district_spinner.setSelection(originalPosition);

                // Call existing method to handle taluka updates
                System.out.println("district_str------" + district_str);
                getTalukaList(district_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("DistrictDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(AccommodationEditActibity.this, "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }



    public void getTalukaList(String district_str) {
        if (district_str != null) {
            for (int i = 0; i < districtListsData.size(); i++) {
                if (district_str.equals(districtListsData.get(i).getName())) {
                    district_id = String.valueOf(districtListsData.get(i).getId());

                }
            }
        }

        System.out.println("district_id--------" + district_id);

        talukaListData.clear();

        prog_taluka.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<TalukaList> userResponse = apiService.talukaList("22", district_id);
        userResponse.enqueue(new Callback<TalukaList>() {

            @Override
            public void onResponse(Call<TalukaList> call, Response<TalukaList> response) {
                talukaList = response.body();

                prog_taluka.setVisibility(View.GONE);

                if (response.isSuccessful()) {


                    String success = talukaList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < talukaList.getTalukaList().size(); i++) {
                            talukaListData.add(new TalukaList.TalukaListData(talukaList.getTalukaList().get(i).getId(), talukaList.getTalukaList().get(i).getName(), talukaList.getTalukaList().get(i).getDistrict_id()));
                        }
                        taluka_spinner_data(talukaListData);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<TalukaList> call, Throwable t) {
                prog_taluka.setVisibility(View.GONE);

                System.out.println("err Tahsil******" + t.toString());
                if (!isNetworkAvailable(AccommodationEditActibity.this)) {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void taluka_spinner_data(List<TalukaList.TalukaListData> talukaListData) {

        taluka_list.clear();

        taluka_list.add("Select Tahsil");

        if (taluka_list.size() != 0) {
            for (int i = 0; i < talukaListData.size(); i++) {

                taluka_list.add(i + 1, talukaListData.get(i).getName());

            }
        }


        taluka_adapter = new ArrayAdapter<String>(AccommodationEditActibity.this, R.layout.simple_spinner_item, taluka_list);

//        current_taluka_spinner.setAdapter(taluka_adapter);

        int pos;
        for (int i = 0; i < taluka_list.size(); i++) {
            if (taluka_list.get(i).contains(fetchAccommodation.getCurr_taluka_name())) {
                pos = i;
//                current_taluka_spinner.setSelection(pos);
            }
        }

//        current_taluka_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                taluka_str = current_taluka_spinner.getItemAtPosition(position).toString();
//                System.out.println("taluka_str------" + taluka_str);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }
    /*----end current spinner data--*/


    /*----permanenet spinner data--*/
    public void getCountryList11() {

        countryListData11.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<CountryList> userResponse = apiService.countryList("07");
        userResponse.enqueue(new Callback<CountryList>() {

            @Override
            public void onResponse(Call<CountryList> call, Response<CountryList> response) {
                countryList11 = response.body();

                if (response.isSuccessful()) {
                    String success = countryList11.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < countryList11.getCountryList().size(); i++) {
                            countryListData11.add(new CountryList.CountryListData(countryList11.getCountryList().get(i).getId(), countryList11.getCountryList().get(i).getName(), countryList11.getCountryList().get(i).getStateList()));
                        }

                        country_spinner_data11(countryListData11);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<CountryList> call, Throwable t) {

                System.out.println("err country******" + t.toString());
                if (!isNetworkAvailable(AccommodationEditActibity.this)) {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void country_spinner_data11(List<CountryList.CountryListData> countryListData11) {

        country_list11.clear();

        country_list11.add("Select Country");

        if (country_list11.size() != 0) {
            for (int i = 0; i < countryListData11.size(); i++) {

                country_list11.add(i + 1, countryListData11.get(i).getName());

            }
        }


        adapter_country11 = new ArrayAdapter<String>(AccommodationEditActibity.this, R.layout.simple_spinner_item, country_list11);

        permant_country_spinner.setAdapter(adapter_country11);


        int pos;
        for (int i = 0; i < country_list11.size(); i++) {
            if (country_list11.get(i).contains(fetchAccommodation.getPer_country_name())) {
                pos = i;
                permant_country_spinner.setSelection(pos);
            }
        }

        permant_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                country_list_str11 = permant_country_spinner.getItemAtPosition(position).toString();
                System.out.println("city_list_str------" + country_list_str11);

                for_state_spinner_data11(country_list_str11);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void for_state_spinner_data11(String country_list_str11) {


        stateListsData11.clear();
        if (country_list_str11 != null) {
            for (int i = 0; i < countryListData11.size(); i++) {
                if (country_list_str11.equals(countryListData11.get(i).getName())) {
                    country_id11 = String.valueOf(countryListData11.get(i).getId());


                }
            }
        }

        for (int i = 0; i < countryListData11.size(); i++) {
            if (countryListData11.get(i).getStateList() != null) {

                for (int j = 0; j < countryListData11.get(i).getStateList().size(); j++) {

                    stateList11 = countryListData11.get(i).getStateList().get(j);

                    if (country_id11 != null) {

                        if (country_id11.contentEquals(stateList11.getC_id())) {
                            stateListsData11.add(new StateList(stateList11.getC_id(), stateList11.getSc_id(), stateList11.getSc_name()));
                        }

                    }


                }
                state_spinner_data11(stateListsData11);
            }
        }
    }
    public void state_spinner_data11(List<StateList> stateLists11) {
        state_list11.clear();
        state_list11.add("Select State");

        if (stateLists11 != null && !stateLists11.isEmpty()) {
            for (int i = 0; i < stateLists11.size(); i++) {
                state_list11.add(i + 1, stateLists11.get(i).getSc_name());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        adapter_state11 = new ArrayAdapter<>(AccommodationEditActibity.this,
                R.layout.simple_spinner_item, state_list11);
        adapter_state11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        permant_state_spinner.setAdapter(adapter_state11);

        // Set default selection based on fetchAccommodation.getPer_state_name()
        String stateToSelect = null;
        int statePosition = -1;

        // First priority: If state_list_str is available (from checkbox)
        if (state_list_str != null && !state_list_str.isEmpty()) {
            for (int i = 0; i < state_list11.size(); i++) {
                if (state_list11.get(i).equals(state_list_str)) {
                    statePosition = i;
                    state_list_str11 = state_list_str;
                    stateToSelect = state_list_str;
                    System.out.println("Setting state from current: " + state_list_str);
                    break;
                }
            }
        }

        // Second priority: Use saved state from fetchAccommodation
        if (statePosition == -1 && fetchAccommodation.getPer_state_name() != null &&
                !fetchAccommodation.getPer_state_name().isEmpty()) {
            for (int i = 0; i < state_list11.size(); i++) {
                if (state_list11.get(i).equals(fetchAccommodation.getPer_state_name())) {
                    statePosition = i;
                    state_list_str11 = fetchAccommodation.getPer_state_name();
                    stateToSelect = fetchAccommodation.getPer_state_name();
                    System.out.println("Setting state from saved: " + fetchAccommodation.getPer_state_name());
                    break;
                }
            }
        }

        // Set the spinner selection
        if (statePosition > 0) {
            permant_state_spinner.setSelection(statePosition);

            // IMPORTANT: Trigger district loading after state is set
            if (stateToSelect != null) {
                // Add a small delay to ensure spinner is ready
                String finalStateToSelect = stateToSelect;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Auto-triggering district load for state: " + finalStateToSelect);
                        getDistrictList11(finalStateToSelect);
                    }
                }, 300);
            }
        }

        // Handle Spinner click to show searchable dialog
        permant_state_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableStateDialog11(state_list11);
            }
            return true; // Consume the touch event
        });
    }
//    public void state_spinner_data11(List<StateList> stateLists11) {
//        state_list11.clear();
//        state_list11.add("Select State");
//
//        if (stateLists11 != null && !stateLists11.isEmpty()) {
//            for (int i = 0; i < stateLists11.size(); i++) {
//                state_list11.add(i + 1, stateLists11.get(i).getSc_name());
//            }
//        }
//
//        // Set up the ArrayAdapter for the Spinner
//        adapter_state11 = new ArrayAdapter<>(AccommodationEditActibity.this,
//                R.layout.simple_spinner_item, state_list11);
//        adapter_state11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        permant_state_spinner.setAdapter(adapter_state11);
//
//        // Set default selection based on fetchAccommodation.getPer_state_name()
//        if (fetchAccommodation.getPer_state_name() != null && !fetchAccommodation.getPer_state_name().isEmpty()) {
//            for (int i = 0; i < state_list11.size(); i++) {
//                if (state_list11.get(i).equals(fetchAccommodation.getPer_state_name())) {
//                    permant_state_spinner.setSelection(i);
//                    break;
//                }
//            }
//        }
//
//        // Override with state_list_str if available (higher priority as per original code)
//        if (stateLists11.size() > 0 && state_list_str != null && !state_list_str.isEmpty()) {
//            System.out.println("11111111111111111");
//            for (int i = 0; i < state_list11.size(); i++) {
//                System.out.println("state i name-----------" + state_list11.get(i));
//                if (state_list11.get(i).equals(state_list_str)) {
//                    permant_state_spinner.setSelection(i);
//                    System.out.println("state i-----------" + i);
//                    state_list_str11 = state_list_str;
//                    break;
//                }
//            }
//        }
//
//        // Handle Spinner click to show searchable dialog
//        permant_state_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableStateDialog11(state_list11);
//            }
//            return true; // Consume the touch event
//        });
//    }
    // Method to show a searchable dialog for state selection
    private void showSearchableStateDialog11(List<String> stateList11) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AccommodationEditActibity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalStateList = new ArrayList<>(stateList11);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                AccommodationEditActibity.this,
                android.R.layout.simple_list_item_1,
                stateList11
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
                    Log.e("StateDialog11", "Selected state is null at position: " + position);
                    Toast.makeText(AccommodationEditActibity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Log.e("StateDialog11", "Could not find selected state in original list: " + selectedState);
                    Toast.makeText(AccommodationEditActibity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                state_list_str11 = selectedState;
                Log.d("StateDialog11", "Selected state: " + state_list_str11 + ", Original position: " + originalPosition);

                // Update Spinner selection
                permant_state_spinner.setSelection(originalPosition);

                // Call existing method to handle district updates
                System.out.println("state_list_str------" + state_list_str11);
                getDistrictList11(state_list_str11);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("StateDialog11", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(AccommodationEditActibity.this, "Error selecting state: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }



    public void getDistrictList11(String state_list_str11) {
        if (state_list_str11 != null) {
            for (int i = 0; i < stateListsData11.size(); i++) {
                if (state_list_str11.equals(stateListsData11.get(i).getSc_name())) {
                    state_id11 = String.valueOf(stateListsData11.get(i).getSc_id());

                }
            }
        }

        System.out.println("state_id11--------" + state_id11);

        districtListsData11.clear();
        prog_per_district.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<DistrictList> userResponse = apiService.districtList("13", state_id11);
        userResponse.enqueue(new Callback<DistrictList>() {

            @Override
            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
                districtList11 = response.body();
                prog_per_district.setVisibility(View.GONE);

                if (response.isSuccessful()) {


                    String success = districtList11.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < districtList11.getDistrictList().size(); i++) {
                            districtListsData11.add(new DistrictList.DistrictListData(districtList11.getDistrictList().get(i).getId(), districtList11.getDistrictList().get(i).getName(), districtList11.getDistrictList().get(i).getState_id()));
                        }
                        district_spinner_data11(districtListsData11);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<DistrictList> call, Throwable t) {
                prog_per_district.setVisibility(View.GONE);

                System.out.println("err district******" + t.toString());
                if (!isNetworkAvailable(AccommodationEditActibity.this)) {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void district_spinner_data11(List<DistrictList.DistrictListData> districtListData11) {
        district_list11.clear();
        district_list11.add("Select District");

        if (districtListData11 != null && !districtListData11.isEmpty()) {
            for (int i = 0; i < districtListData11.size(); i++) {
                district_list11.add(i + 1, districtListData11.get(i).getName());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        district_adapter11 = new ArrayAdapter<>(AccommodationEditActibity.this,
                R.layout.simple_spinner_item, district_list11);
        district_adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        permant_district_spinner.setAdapter(district_adapter11);

        // Set default selection
        String districtToSelect = null;
        int districtPosition = -1;

        // First priority: If district_str is available (from checkbox)
        if (district_str != null && !district_str.isEmpty()) {
            for (int i = 0; i < district_list11.size(); i++) {
                if (district_list11.get(i).equals(district_str)) {
                    districtPosition = i;
                    district_str11 = district_str;
                    districtToSelect = district_str;
                    System.out.println("Setting district from current: " + district_str);
                    break;
                }
            }
        }

        // Second priority: Use saved district from fetchAccommodation
        if (districtPosition == -1 && fetchAccommodation.getPer_district_name() != null &&
                !fetchAccommodation.getPer_district_name().isEmpty()) {
            for (int i = 0; i < district_list11.size(); i++) {
                if (district_list11.get(i).equals(fetchAccommodation.getPer_district_name())) {
                    districtPosition = i;
                    district_str11 = fetchAccommodation.getPer_district_name();
                    districtToSelect = fetchAccommodation.getPer_district_name();
                    System.out.println("Setting district from saved: " + fetchAccommodation.getPer_district_name());
                    break;
                }
            }
        }

        // Set the spinner selection
        if (districtPosition > 0) {
            permant_district_spinner.setSelection(districtPosition);

            // IMPORTANT: Trigger taluka loading after district is set
            if (districtToSelect != null) {
                // Add a small delay to ensure spinner is ready
                String finalDistrictToSelect = districtToSelect;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Auto-triggering taluka load for district: " + finalDistrictToSelect);
                        getTalukaList11(finalDistrictToSelect);
                    }
                }, 300);
            }
        }

        // Handle Spinner click to show searchable dialog
        permant_district_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableDistrictDialog11(district_list11);
            }
            return true; // Consume the touch event
        });
    }
//    public void district_spinner_data11(List<DistrictList.DistrictListData> districtListData11) {
//        district_list11.clear();
//        district_list11.add("Select District");
//
//        if (districtListData11 != null && !districtListData11.isEmpty()) {
//            for (int i = 0; i < districtListData11.size(); i++) {
//                district_list11.add(i + 1, districtListData11.get(i).getName());
//            }
//        }
//
//        // Set up the ArrayAdapter for the Spinner
//        district_adapter11 = new ArrayAdapter<>(AccommodationEditActibity.this,
//                R.layout.simple_spinner_item, district_list11);
//        district_adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        permant_district_spinner.setAdapter(district_adapter11);
//
//        // Set default selection based on fetchAccommodation.getPer_district_name()
//        if (fetchAccommodation.getPer_district_name() != null && !fetchAccommodation.getPer_district_name().isEmpty()) {
//            for (int i = 0; i < district_list11.size(); i++) {
//                if (district_list11.get(i).equals(fetchAccommodation.getPer_district_name())) {
//                    permant_district_spinner.setSelection(i);
//                    break;
//                }
//            }
//        }
//
//        // Override with district_str if available (higher priority as per original code)
//        if (district_list11.size() > 0 && district_str != null && !district_str.isEmpty()) {
//            System.out.println("22222222222222222");
//            for (int i = 0; i < district_list11.size(); i++) {
//                System.out.println("district_list11 i name-----------" + district_list11.get(i));
//                if (district_list11.get(i).equals(district_str)) {
//                    permant_district_spinner.setSelection(i);
//                    System.out.println("state i-----------" + i);
//                    district_str11 = district_str;
//                    break;
//                }
//            }
//        }
//
//        // Handle Spinner click to show searchable dialog
//        permant_district_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableDistrictDialog11(district_list11);
//            }
//            return true; // Consume the touch event
//        });
//    }
    // Method to show a searchable dialog for district selection
    private void showSearchableDistrictDialog11(List<String> districtList11) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AccommodationEditActibity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalDistrictList = new ArrayList<>(districtList11);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                AccommodationEditActibity.this,
                android.R.layout.simple_list_item_1,
                districtList11
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
                    Log.e("DistrictDialog11", "Selected district is null at position: " + position);
                    Toast.makeText(AccommodationEditActibity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Log.e("DistrictDialog11", "Could not find selected district in original list: " + selectedDistrict);
                    Toast.makeText(AccommodationEditActibity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                district_str11 = selectedDistrict;
                Log.d("DistrictDialog11", "Selected district: " + district_str11 + ", Original position: " + originalPosition);

                // Update Spinner selection
                permant_district_spinner.setSelection(originalPosition);

                // Call existing method to handle taluka updates
                System.out.println("district_str------" + district_str11);
                getTalukaList11(district_str11);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("DistrictDialog11", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(AccommodationEditActibity.this, "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    public void getTalukaList11(String district_str11) {
        if (district_str11 != null) {
            for (int i = 0; i < districtListsData11.size(); i++) {
                if (district_str11.equals(districtListsData11.get(i).getName())) {
                    district_id11 = String.valueOf(districtListsData11.get(i).getId());

                }
            }
        }

        System.out.println("district_id11--------" + district_id11);

        talukaListData11.clear();
        prog_per_taluka.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<TalukaList> userResponse = apiService.talukaList("22", district_id11);
        userResponse.enqueue(new Callback<TalukaList>() {

            @Override
            public void onResponse(Call<TalukaList> call, Response<TalukaList> response) {
                talukaList11 = response.body();

                if (response.isSuccessful()) {

                    prog_per_taluka.setVisibility(View.GONE);

                    String success = talukaList11.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < talukaList11.getTalukaList().size(); i++) {
                            talukaListData11.add(new TalukaList.TalukaListData(talukaList11.getTalukaList().get(i).getId(), talukaList11.getTalukaList().get(i).getName(), talukaList11.getTalukaList().get(i).getDistrict_id()));
                        }
                        taluka_spinner_data11(talukaListData11);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<TalukaList> call, Throwable t) {
                prog_per_taluka.setVisibility(View.GONE);

                System.out.println("err Tahsil******" + t.toString());
                if (!isNetworkAvailable(AccommodationEditActibity.this)) {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccommodationEditActibity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
                }

            }
        });
    }

    public void taluka_spinner_data11(List<TalukaList.TalukaListData> talukaListData11) {

        taluka_list11.clear();

        taluka_list11.add("Select Tahsil");

        if (taluka_list11.size() != 0) {
            for (int i = 0; i < talukaListData11.size(); i++) {

                taluka_list11.add(i + 1, talukaListData11.get(i).getName());

            }
        }


        taluka_adapter11 = new ArrayAdapter<String>(AccommodationEditActibity.this, R.layout.simple_spinner_item, taluka_list11);


//        permant_taluka_spinner.setAdapter(taluka_adapter11);


        int pos1;
//        for (int i = 0; i < taluka_list11.size(); i++) {
//            if (taluka_list11.get(i).contains(fetchAccommodation.getPer_taluka_name())) {
//                pos1 = i;
//                permant_taluka_spinner.setSelection(pos1);
//            }
//        }


//        permant_taluka_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                taluka_str11 = permant_taluka_spinner.getItemAtPosition(position).toString();
//                System.out.println("taluka_str------" + taluka_str11);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        int pos;
        if (taluka_list11.size() > 0) {
            System.out.println("5555555555555555");
            if (taluka_str != null) {


                for (int i = 0; i < taluka_list11.size(); i++) {
                    System.out.println("district_list11 i name-----------" + taluka_list11.get(i));
                    if (taluka_list11.get(i).contains(taluka_str)) {

                        pos = i;
                        System.out.println("Tahsil i-----------" + pos);
//                        permant_taluka_spinner.setSelection(pos);

                        taluka_str11 = taluka_str;

                    }
                }
            }
        }
    }
    /*----end permanenet spinner data---*/
}
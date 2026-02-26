package com.WadhuWarProject.WadhuWar.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.CasteList;
import com.WadhuWarProject.WadhuWar.model.CountryList;
import com.WadhuWarProject.WadhuWar.model.DistrictList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MothertoungeList;
import com.WadhuWarProject.WadhuWar.model.ReligionList;
import com.WadhuWarProject.WadhuWar.model.SampradayList;
import com.WadhuWarProject.WadhuWar.model.SendMatchingProfiles;
import com.WadhuWarProject.WadhuWar.model.SendNotificationToOtherUser;
import com.WadhuWarProject.WadhuWar.model.StateList;
import com.WadhuWarProject.WadhuWar.model.SubCasteList;
import com.WadhuWarProject.WadhuWar.model.TalukaList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationDetailActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    EditText current_address, permanent_address, current_village, permant_village,curr_taluka,per_taluka;
    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;

    CheckBox checkBox1;

    RelativeLayout submit_btn;
    TextView a1, a2, a3, a4, a5, a6;

    Spinner current_country_spinner, current_state_spinner, religion_spinner, caste_spinner, sub_caste_spinner, current_district_spinner, current_taluka_spinner,
            mother_tounge_spinner, permant_taluka_spinner, permant_district_spinner, permant_state_spinner, permant_country_spinner, sampraday_spinner,rashi_spinner;

    ArrayList<SampradayList.SampradayListData> sampradayListData = new ArrayList<>();
    SampradayList sampradayList;

    ArrayList<CountryList.CountryListData> countryListData = new ArrayList<>();
    CountryList countryList;
    ArrayList<StateList> stateListsData = new ArrayList<>();
    StateList stateList;
    ArrayList<ReligionList.ReligionListData> religionListData = new ArrayList<>();
    ReligionList religionList;
    ArrayList<CasteList.CasteListData> casteListData = new ArrayList<>();
    CasteList casteList;
    ArrayList<SubCasteList> subCasteLists = new ArrayList<>();
    ArrayList<DistrictList.DistrictListData> districtListsData = new ArrayList<>();
    DistrictList districtList;
    ArrayList<TalukaList.TalukaListData> talukaListData = new ArrayList<>();
    TalukaList talukaList;
    ArrayList<MothertoungeList.MothertoungeListData> mothertoungeListData = new ArrayList<>();
    MothertoungeList mothertoungeList;

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
    List<String> religion_list = new ArrayList<String>();
    List<String> caste_list = new ArrayList<String>();
    List<String> sub_caste_list = new ArrayList<String>();
    List<String> district_list = new ArrayList<String>();
    List<String> taluka_list = new ArrayList<String>();
    List<String> mother_tongue_list = new ArrayList<String>();
    List<String> sampraday_list = new ArrayList<String>();
    List<String> rashi_list;

    String country_list_str, state_list_str, religion_list_str, caste_list_str, sub_caste_str, district_str, taluka_str, mother_tongue_str, sampraday_list_str;
    String country_list_str11, state_list_str11, district_str11, taluka_str11, rashi_str;

    String country_id, caste_id, subcaste_id, religion_id, district_id, state_id, mother_tongue_id, taluka_id, sampraday_id;
    String country_id11, district_id11, state_id11, taluka_id11;

    ArrayAdapter<String> adapter_sampraday;

    ArrayAdapter<String> mother_tongue_adapter;
    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> adapter_country11;
    ArrayAdapter<String> caste_adapter;
    ArrayAdapter<String> sub_caste_adapter;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> adapter_state11;
    ArrayAdapter<String> district_adapter;
    ArrayAdapter<String> district_adapter11;
    ArrayAdapter<String> taluka_adapter;
    ArrayAdapter<String> taluka_adapter11;
    ArrayAdapter<String> adapter_religion;


    String currentAddress, currentCountry, currentState, currentDistrict, currentTaluka, currentVillage,
            permanentAddress, permanentCountry, permanentState, permanentDistrict, permanentTaluka, permanentVillage,
            religion, caste, subCaste, motherTounge;
    String user_id,gender;
    ProgressDialog progressBar;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;

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
        setContentView(R.layout.activity_accomodation_detail);

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
//        setContentView(R.layout.activity_accomodation_detail);

        rashi_spinner = findViewById(R.id.rashi_spinner);
        prog_per_taluka = findViewById(R.id.prog_per_taluka);
        prog_per_district = findViewById(R.id.prog_per_district);
        prog_taluka = findViewById(R.id.prog_taluka);
        prog_district = findViewById(R.id.prog_district);
        submit_btn = (RelativeLayout) findViewById(R.id.submit_btn);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);
        a5 = findViewById(R.id.a5);
        a6 = findViewById(R.id.a6);
        sampraday_spinner = findViewById(R.id.sampraday_spinner);
        current_country_spinner = findViewById(R.id.current_country_spinner);
        current_state_spinner = findViewById(R.id.current_state_spinner);
        religion_spinner = findViewById(R.id.religion_spinner);
        caste_spinner = findViewById(R.id.caste_spinner);
        sub_caste_spinner = findViewById(R.id.sub_caste_spinner);
        current_district_spinner = findViewById(R.id.current_district_spinner);
        curr_taluka = findViewById(R.id.current_taluka);
        mother_tounge_spinner = findViewById(R.id.mother_tounge_spinner);
        checkBox1 = findViewById(R.id.checkBox1);
        per_taluka = findViewById(R.id.permanent_taluka);
        permant_district_spinner = findViewById(R.id.permant_district_spinner);
        permant_state_spinner = findViewById(R.id.permant_state_spinner);
        permant_country_spinner = findViewById(R.id.permant_country_spinner);
        current_address = findViewById(R.id.current_address);
        permanent_address = findViewById(R.id.permanent_address);
        current_village = findViewById(R.id.current_village);
        permant_village = findViewById(R.id.permant_village);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);



        a1.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a1.setTextColor(Color.WHITE);
        a2.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a2.setTextColor(Color.WHITE);
//        a3.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
//        a3.setTextColor(Color.WHITE);


        rashi_list = new ArrayList<String>();
        rashi_list.add("Select Rashi");
        rashi_list.add("मेष (Aries)");
        rashi_list.add("वृष/वृषभ (Taurus)");
        rashi_list.add("मिथुन (Gemini)");
        rashi_list.add("कर्क (Cancer)");
        rashi_list.add("सिंह (Leo)");
        rashi_list.add("कन्या (Virgo)");
        rashi_list.add("तुला (Libra)");
        rashi_list.add("वृश्चिक(Scorpious)");
        rashi_list.add("धनु(Sagittarius)");
        rashi_list.add("मकर(Capricornus)");
        rashi_list.add("कुम्भ(Aquarius)");
        rashi_list.add("मीन (Pisces)");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, android.R.layout.simple_spinner_item, rashi_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rashi_spinner.setAdapter(dataAdapter);

        Intent i = getIntent();
        user_id = (i.getStringExtra("user_id"));
        gender = (i.getStringExtra("gender"));
//        user_id = "11043";

        rashi_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                rashi_str = rashi_spinner.getItemAtPosition(position).toString();

                System.out.println("rashi_str------" + rashi_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                currentAddress = current_address.getText().toString();
                currentCountry = country_list_str;
                currentState = state_list_str;
                currentDistrict = district_str;
                currentTaluka = curr_taluka.getText().toString();
                currentVillage = current_village.getText().toString();
                permanentAddress = permanent_address.getText().toString();
                permanentCountry = country_list_str11;
                permanentState = state_list_str11;
                permanentDistrict = district_str11;
                permanentTaluka = per_taluka.getText().toString();
                permanentVillage = permant_village.getText().toString();
                religion = religion_list_str;
                caste = caste_list_str;
                subCaste = sub_caste_str;
                motherTounge = mother_tongue_str;


                if (checkValidation()) {

                    if (!isNetworkAvailable(AccomodationDetailActivity.this)) {

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
//                        if (taluka_str != null && !taluka_str.contentEquals("")) {
//                            for (int i = 0; i < talukaListData.size(); i++) {
//                                if (taluka_str.equals(talukaListData.get(i).getName())) {
//                                    taluka_id = String.valueOf(talukaListData.get(i).getId());
//
//                                }
//                            }
//                        } else {
//                            taluka_id = "";
//                        }
                        if (country_list_str11 != null && !country_list_str11.contentEquals("")) {
                            for (int i = 0; i < countryListData11.size(); i++) {
                                if (country_list_str11.equals(countryListData11.get(i).getName())) {
                                    country_id11 = String.valueOf(countryListData11.get(i).getId());

                                }
                            }
                        } else {
                            country_id11 = "";
                        }
                        if (state_list_str11 != null && !state_list_str11.contentEquals("")) {
                            for (int i = 0; i < stateListsData11.size(); i++) {
                                if (state_list_str11.equals(stateListsData11.get(i).getSc_name())) {
                                    state_id11 = String.valueOf(stateListsData11.get(i).getSc_id());

                                }
                            }
                        } else {
                            state_id11 = "";
                        }
                        if (district_str11 != null && !district_str11.contentEquals("")) {
                            for (int i = 0; i < districtListsData11.size(); i++) {
                                if (district_str11.equals(districtListsData11.get(i).getName())) {
                                    district_id11 = String.valueOf(districtListsData11.get(i).getId());

                                }
                            }
                        } else {
                            district_id11 = "";
                        }
//                        if (taluka_str11 != null && !taluka_str11.contentEquals("")) {
//                            for (int i = 0; i < talukaListData11.size(); i++) {
//                                if (taluka_str11.equals(talukaListData11.get(i).getName())) {
//                                    taluka_id11 = String.valueOf(talukaListData11.get(i).getId());
//
//                                }
//                            }
//                        } else {
//                            taluka_id11 = "";
//                        }

                        if (religion_list_str != null && !religion_list_str.contentEquals("")) {
                            for (int i = 0; i < religionListData.size(); i++) {
                                if (religion_list_str.equals(religionListData.get(i).getName())) {
                                    religion_id = String.valueOf(religionListData.get(i).getId());

                                }
                            }
                        } else {
                            religion_id = "";
                        }
                        if (caste_list_str != null && !caste_list_str.contentEquals("")) {
                            for (int i = 0; i < casteListData.size(); i++) {
                                if (caste_list_str.equals(casteListData.get(i).getName())) {
                                    caste_id = String.valueOf(casteListData.get(i).getId());

                                }
                            }
                        } else {
                            caste_id = "";
                        }
                        if (sub_caste_str != null && !sub_caste_str.contentEquals("")) {
                            for (int i = 0; i < subCasteLists.size(); i++) {
                                if (sub_caste_str.equals(subCasteLists.get(i).getSc_name())) {
                                    subcaste_id = String.valueOf(subCasteLists.get(i).getSc_id());

                                }
                            }
                        } else {
                            subcaste_id = "";
                        }
                        if (mother_tongue_str != null && !mother_tongue_str.contentEquals("")) {
                            for (int i = 0; i < mothertoungeListData.size(); i++) {
                                if (mother_tongue_str.equals(mothertoungeListData.get(i).getName())) {
                                    mother_tongue_id = String.valueOf(mothertoungeListData.get(i).getId());

                                }
                            }
                        } else {
                            mother_tongue_id = "";
                        }

                        if (sampraday_list_str != null && !sampraday_list_str.contentEquals("") ) {
                            for (int i = 0; i < sampradayListData.size(); i++) {
                                if (sampraday_list_str.equals(sampradayListData.get(i).getName())) {
                                    sampraday_id = String.valueOf(sampradayListData.get(i).getId());

                                }
                            }
                        } else {
                            sampraday_id = "";

                        }
                        if (rashi_str != null && !rashi_str.contentEquals("") && !rashi_str.contentEquals("Select Rashi")) {

                        } else {
                            rashi_str = "";

                        }


                        System.out.println("currentAddress --" + currentAddress);
                        System.out.println("currentCountry --" + country_id);
                        System.out.println("currentState --" + state_id);
                        System.out.println("currentDistrict --" + district_id);
                        System.out.println("currentTaluka --" + taluka_id);
                        System.out.println("currentVillage --" + currentVillage);
                        System.out.println("permanentAddress --" + permanentAddress);
                        System.out.println("permanentCountry --" + country_id11);
                        System.out.println("permanentState --" + state_id11);
                        System.out.println("permanentDistrict --" + district_id11);
                        System.out.println("permanentTaluka --" + taluka_id11);
                        System.out.println("permanentVillage --" + permanentVillage);
                        System.out.println("religion --" + religion_id);
                        System.out.println("caste_id --" + caste_id);
                        System.out.println("subcaste_id --" + subcaste_id);
                        System.out.println("motherTounge --" + mother_tongue_id);
                        System.out.println("user_id --" + user_id);
                        System.out.println("sampraday_id --" + sampraday_id);
                        System.out.println("rashi_str --" + rashi_str);


                        submitData(currentAddress, country_id, state_id, district_id, currentTaluka, currentVillage,
                                permanentAddress, country_id11, state_id11, district_id11, permanentTaluka, permanentVillage,
                                religion_id, caste_id, subcaste_id, mother_tongue_id, user_id, sampraday_id,rashi_str);


                    }


                }


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
        getCountryList();
        getCountryList11();

        getReligionList();
        getMotherTongueList();

        getSampradayList();


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
                casteListData.clear();
                subCasteLists.clear();
                sampradayListData.clear();

                getCountryList();
                getCountryList11();

                getReligionList();
                getMotherTongueList();
                getSampradayList();

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

//                Toast.makeText(AccomodationDetailActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void submitData(String currentAddress, String country_id, String state_id, String district_id, String currentTaluka, String currentVillage,
                           String permanentAddress, String country_id11, String state_id11, String district_id11, String permanentTaluka, String permanentVillage,
                           String religion, String caste, String subCaste, String mother_tongue_id, String user_id, String sampraday_id, String rashi_str) {


        progressBar = ProgressDialog.show(AccomodationDetailActivity.this, "", "Please Wait...");


        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.insertStep2(currentAddress, country_id, state_id, district_id, currentTaluka, currentVillage,
                permanentAddress, country_id11, state_id11, district_id11, permanentTaluka, permanentVillage,
                religion, caste, subCaste, mother_tongue_id, user_id,sampraday_id,rashi_str);

        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                insertResponse = response.body();
                progressBar.dismiss();
                String success = insertResponse.getResid();
                Log.d("API_SUCCESS", "onResponse: success:-"+success);
                Log.d("API_SUCCESS", "onResponse: success:-"+response);

                if (response.isSuccessful()) {
                    /*System.out.println("accomodaion resp ========" + new Gson().toJson(insertResponse));*/

//                    String success = insertResponse.getResid();
//                    Log.d("API_SUCCESS", "onResponse: success:-"+success);

                    if (success.equals("200")) {
                        Toast.makeText(AccomodationDetailActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(AccomodationDetailActivity.this,CarrerDetailAndOtherAttributeActivity.class);
                        Intent i = new Intent(AccomodationDetailActivity.this, CarrerDetailAndOtherAttributeActivity.class);
                        i.putExtra("user_id", user_id);


//                        Toast.makeText(AccomodationDetailActivity.this, "u"+user_id+"g"+gender+"c"+caste_id, Toast.LENGTH_SHORT).show();
                        SendNotification(user_id,gender,caste_id);
                        SendMatchingProfile(user_id,caste_id);
                        Log.d("user_id,gender,caste_id", "onResponse: " + user_id+gender+caste_id);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    } else {
                        Toast.makeText(AccomodationDetailActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                        Log.d("API_SUCCESS", "onResponse: success:-"+success);
                    }
                } else{
                    Log.d("API_SUCCESS", "onResponse: success:-"+success);

                    Toast.makeText(AccomodationDetailActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err accomodation******" + t.toString());
                Log.d("API_FAIL","failure"+t);

                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {

                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AccomodationDetailActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    private void SendNotification(String userId, String gender, String casteId) {
//        progressBar = ProgressDialog.show(AccomodationDetailActivity.this, "", "Please wait...");
        Api apiService = RetrofitClient.getApiService();
        Call<SendNotificationToOtherUser> userResponse = apiService.sendNotificationToOtherUser(userId,casteId, gender);
        userResponse.enqueue(new Callback<SendNotificationToOtherUser>() {
            @Override
            public void onResponse(Call<SendNotificationToOtherUser> call, Response<SendNotificationToOtherUser> response) {

            }

            @Override
            public void onFailure(Call<SendNotificationToOtherUser> call, Throwable t) {
                progressBar.dismiss();
            }
        });
    }


    private void SendMatchingProfile(String member_id, String casteId) {
        Api apiService = RetrofitClient.getApiService();
        Call<SendMatchingProfiles> userResponse = apiService.sendMatchingProfiles(member_id, casteId);
        userResponse.enqueue(new Callback<SendMatchingProfiles>() {
            @Override
            public void onResponse(Call<SendMatchingProfiles> call, Response<SendMatchingProfiles> response) {
//                Toast.makeText(AccomodationDetailActivity.this, "okay", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<SendMatchingProfiles> call, Throwable t) {
                progressBar.dismiss();
            }
        });
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


        View selectedView_religion = religion_spinner.getSelectedView();
        if (selectedView_religion != null && selectedView_religion instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_religion;
            if (selectedTextView.getText().equals("Select Religion")) {
                selectedTextView.setError("Please Select Religion");
                selectedTextView.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }

        }


        View selectedView_caste = caste_spinner.getSelectedView();
        if (selectedView_caste != null && selectedView_caste instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_caste;
            if (selectedTextView.getText().equals("Select Caste")) {
                selectedTextView.setError("Please Select Caste");
                selectedTextView.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }

        }

        View selectedView_subcaste = sub_caste_spinner.getSelectedView();
        if (selectedView_subcaste != null && selectedView_subcaste instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_subcaste;
            if (selectedTextView.getText().equals("Select Sub-Caste")) {
                selectedTextView.setError("Please Select Sub-Caste");
                selectedTextView.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }

        }

       /* View selectedView_sampraday = sampraday_spinner.getSelectedView();
        if (selectedView_sampraday != null && selectedView_sampraday instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_sampraday;
            if (selectedTextView.getText().equals("Select Sampraday")) {
                selectedTextView.setError("Please Select Sampraday");
                selectedTextView.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }

        }

        View selectedView_rashi = rashi_spinner.getSelectedView();
        if (selectedView_rashi != null && selectedView_rashi instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_rashi;
            if (selectedTextView.getText().equals("Select Rashi")) {
                selectedTextView.setError("Please Select Rashi");
                selectedTextView.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return false;
            }

        }*/


        return true;
    }


    public void onCheckBoxClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (view.getId() == R.id.checkBox1) {
            if (checked) {
                if (country_list_str != null) {
                    int spinnerPosition = adapter_country.getPosition(country_list_str);
                    System.out.println("name country_list_str pos-----------" + country_list_str);
                    System.out.println("country pos-----------" + spinnerPosition);

                    permant_country_spinner.setSelection(spinnerPosition);
                }

                for_state_spinner_data11(country_list_str);
                getDistrictList11(state_list_str11);
                getTalukaList11(district_str11);

                String permanent_address_ = current_address.getText().toString();
                permanent_address.setText(permanent_address_);

                String permant_village_ = current_village.getText().toString();
                permant_village.setText(permant_village_);

                String currentTaluka1 = curr_taluka.getText().toString();
                per_taluka.setText(currentTaluka1);

//                String perTaluka1 = per_taluka.getText().toString();
//                per_taluka.setText(perTaluka1);


            }
        }
    }


    public void getMotherTongueList() {


        mothertoungeListData.clear();

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
                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



    public void mother_tongue_spinner_data(List<MothertoungeList.MothertoungeListData> mothertoungeListData) {

        mother_tongue_list.clear();
        mother_tongue_list.add("Select Mother Tongue");

        if (mothertoungeListData != null && !mothertoungeListData.isEmpty()) {
            for (int i = 0; i < mothertoungeListData.size(); i++) {
                mother_tongue_list.add(i + 1, mothertoungeListData.get(i).getName().trim());
            }
        }

        mother_tongue_adapter = new ArrayAdapter<>(
                AccomodationDetailActivity.this,
                R.layout.simple_spinner_item,
                mother_tongue_list
        );

        mother_tongue_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mother_tounge_spinner.setAdapter(mother_tongue_adapter);

        // Make the spinner show a searchable dialog on touch
        mother_tounge_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableMotherTongueDialog(mother_tongue_list);
            }
            return true;
        });
    }

    private void showSearchableMotherTongueDialog(List<String> motherTongueList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AccomodationDetailActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalMotherTongueList = new ArrayList<>(motherTongueList);
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                AccomodationDetailActivity.this,
                android.R.layout.simple_list_item_1,
                motherTongueList
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
                String selectedMotherTongue = dialogAdapter.getItem(position);
                if (selectedMotherTongue == null) {
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting mother tongue", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map selected item back to the original list
                int originalPosition = -1;
                for (int i = 0; i < originalMotherTongueList.size(); i++) {
                    if (originalMotherTongueList.get(i).equals(selectedMotherTongue)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition != -1) {
                    mother_tounge_spinner.setSelection(originalPosition);
                    mother_tongue_str = selectedMotherTongue;
                    System.out.println("mother_tongue_str------" + mother_tongue_str);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(AccomodationDetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
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
                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void religion_spinner_data(List<ReligionList.ReligionListData> religionListData) {

        religion_list.clear();

        religion_list.add("Select Religion");

        if (religion_list.size() != 0) {
            for (int i = 0; i < religionListData.size(); i++) {

                religion_list.add(i + 1, religionListData.get(i).getName());

            }
        }


        adapter_religion = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, religion_list);

        religion_spinner.setAdapter(adapter_religion);

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
                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    // for seach cast
    public void caste_spinner_data(List<CasteList.CasteListData> casteListData) {
        caste_list.clear();
        caste_list.add("Select Caste");

        if (casteListData != null && !casteListData.isEmpty()) {
            for (int i = 0; i < casteListData.size(); i++) {
                caste_list.add(i + 1, casteListData.get(i).getName());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        caste_adapter = new ArrayAdapter<>(AccomodationDetailActivity.this,
                R.layout.simple_spinner_item, caste_list);
        caste_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caste_spinner.setAdapter(caste_adapter);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(AccomodationDetailActivity.this);
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
                AccomodationDetailActivity.this,
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting caste. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                caste_list_str = selectedCaste;
                Log.d("CasteDialog", "Selected caste: " + caste_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                caste_spinner.setSelection(originalPosition);

                // Call existing method to handle further logic
                System.out.println("caste_list_str------" + caste_list_str);
                for_caste_spinner_data(caste_list_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("CasteDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(AccomodationDetailActivity.this, "Error selecting caste: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    //commented by tanmay on 27-5-2025
//    public void caste_spinner_data(List<CasteList.CasteListData> casteListData) {
//
//        caste_list.clear();
//
//        caste_list.add("Select Caste");
//
//        if (caste_list.size() != 0) {
//            for (int i = 0; i < casteListData.size(); i++) {
//
//                caste_list.add(i + 1, casteListData.get(i).getName());
//
//            }
//        }
//
//
//        caste_adapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, caste_list);
//
//        caste_spinner.setAdapter(caste_adapter);
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


    // new implemented by tanmay for search 27-5-2025
    public void subCaste_spinner_data(List<SubCasteList> subCasteLists) {
        sub_caste_list.clear();
        sub_caste_list.add("Select Sub-Caste");

        if (subCasteLists != null && !subCasteLists.isEmpty()) {
            for (int i = 0; i < subCasteLists.size(); i++) {
                sub_caste_list.add(i + 1, subCasteLists.get(i).getSc_name());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        sub_caste_adapter = new ArrayAdapter<>(AccomodationDetailActivity.this,
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AccomodationDetailActivity.this);
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
                AccomodationDetailActivity.this,
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting sub-caste. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting sub-caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                sub_caste_str = selectedSubCaste;
                Log.d("SubCasteDialog", "Selected sub-caste: " + sub_caste_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                sub_caste_spinner.setSelection(originalPosition);

                // Log the selection as per the original logic
                System.out.println("sub_caste_str------" + sub_caste_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("SubCasteDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(AccomodationDetailActivity.this, "Error selecting sub-caste: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
//
//        if (sub_caste_list.size() != 0) {
//            for (int i = 0; i < subCasteLists.size(); i++) {
//
//                sub_caste_list.add(i + 1, subCasteLists.get(i).getSc_name());
//
//            }
//        }
//
//
//        sub_caste_adapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, sub_caste_list);
//
//        sub_caste_spinner.setAdapter(sub_caste_adapter);
//
//
//        sub_caste_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                sub_caste_str = sub_caste_spinner.getItemAtPosition(position).toString();
//
//                System.out.println("sub_caste_str------" + sub_caste_str);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }


    /*----end current spinner data--*/
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

                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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


        adapter_country = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, country_list);

        current_country_spinner.setAdapter(adapter_country);

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

        if (stateLists != null && !stateLists.isEmpty()) {
            for (int i = 0; i < stateLists.size(); i++) {
                state_list.add(i + 1, stateLists.get(i).getSc_name());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        adapter_state = new ArrayAdapter<>(AccomodationDetailActivity.this,
                R.layout.simple_spinner_item, state_list);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_state_spinner.setAdapter(adapter_state);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(AccomodationDetailActivity.this);
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
                AccomodationDetailActivity.this,
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AccomodationDetailActivity.this, "Error selecting state: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        district_adapter = new ArrayAdapter<>(AccomodationDetailActivity.this,
                R.layout.simple_spinner_item, district_list);
        district_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_district_spinner.setAdapter(district_adapter);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(AccomodationDetailActivity.this);
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
                AccomodationDetailActivity.this,
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                district_str = selectedDistrict;
                Log.d("DistrictDialog", "Selected district: " + district_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                current_district_spinner.setSelection(originalPosition);

                // Log the selection as per the original logic
                System.out.println("district_str------" + district_str);

                // Uncomment the following line if you want to fetch taluka data
                // getTalukaList(district_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("DistrictDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(AccomodationDetailActivity.this, "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
//    public void getTalukaList(String district_str) {
//        if (district_str != null) {
//            for (int i = 0; i < districtListsData.size(); i++) {
//                if (district_str.equals(districtListsData.get(i).getName())) {
//                    district_id = String.valueOf(districtListsData.get(i).getId());
//
//                }
//            }
//        }
//
//        System.out.println("district_id--------" + district_id);
//
//        talukaListData.clear();
//        prog_taluka.setVisibility(View.VISIBLE);
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<TalukaList> userResponse = apiService.talukaList("22", district_id);
//        userResponse.enqueue(new Callback<TalukaList>() {
//
//            @Override
//            public void onResponse(Call<TalukaList> call, Response<TalukaList> response) {
//                talukaList = response.body();
//                prog_taluka.setVisibility(View.GONE);
//
//                if (response.isSuccessful()) {
//
//
//                    String success = talukaList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for (int i = 0; i < talukaList.getTalukaList().size(); i++) {
//                            talukaListData.add(new TalukaList.TalukaListData(talukaList.getTalukaList().get(i).getId(), talukaList.getTalukaList().get(i).getName(), talukaList.getTalukaList().get(i).getDistrict_id()));
//                        }
////                        taluka_spinner_data(talukaListData);
//                    } else {
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TalukaList> call, Throwable t) {
//                prog_taluka.setVisibility(View.GONE);
//
//                System.out.println("err Tahsil******" + t.toString());
//                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
////                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//                } else {
////                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    public void taluka_spinner_data(List<TalukaList.TalukaListData> talukaListData) {

        taluka_list.clear();

        taluka_list.add("Select Tahsil");

        if (taluka_list.size() != 0) {
            for (int i = 0; i < talukaListData.size(); i++) {

                taluka_list.add(i + 1, talukaListData.get(i).getName());

            }
        }


        taluka_adapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, taluka_list);

//        current_taluka_spinner.setAdapter(taluka_adapter);

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
                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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


        adapter_country11 = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, country_list11);

        permant_country_spinner.setAdapter(adapter_country11);

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

                        if (country_id11.contentEquals(stateList.getC_id())) {
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
        adapter_state11 = new ArrayAdapter<>(AccomodationDetailActivity.this,
                R.layout.simple_spinner_item, state_list11);
        adapter_state11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        permant_state_spinner.setAdapter(adapter_state11);

        // Set default selection if state_list_str exists
        if (stateLists11.size() > 0 && state_list_str != null && !state_list_str.isEmpty()) {
            System.out.println("11111111111111111");
            for (int i = 0; i < state_list11.size(); i++) {
                System.out.println("state i name-----------" + state_list11.get(i));
                if (state_list11.get(i).equals(state_list_str)) {
                    permant_state_spinner.setSelection(i);
                    System.out.println("state i-----------" + i);
                    state_list_str11 = state_list_str;
                    break;
                }
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
    // Method to show a searchable dialog for state selection
    private void showSearchableStateDialog11(List<String> stateList11) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AccomodationDetailActivity.this);
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
                AccomodationDetailActivity.this,
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting state. Please try again.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AccomodationDetailActivity.this, "Error selecting state: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        district_adapter11 = new ArrayAdapter<>(AccomodationDetailActivity.this,
                R.layout.simple_spinner_item, district_list11);
        district_adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        permant_district_spinner.setAdapter(district_adapter11);

        // Set default selection if district_str exists
        if (district_list11.size() > 0 && district_str != null && !district_str.isEmpty()) {
            System.out.println("22222222222222222");
            for (int i = 0; i < district_list11.size(); i++) {
                System.out.println("district_list11 i name-----------" + district_list11.get(i));
                if (district_list11.get(i).equals(district_str)) {
                    permant_district_spinner.setSelection(i);
                    System.out.println("state i-----------" + i);
                    district_str11 = district_str;
                    break;
                }
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
    // Method to show a searchable dialog for district selection
    private void showSearchableDistrictDialog11(List<String> districtList11) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AccomodationDetailActivity.this);
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
                AccomodationDetailActivity.this,
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AccomodationDetailActivity.this, "Error selecting district. Please try again.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AccomodationDetailActivity.this, "Error selecting district: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                prog_per_taluka.setVisibility(View.GONE);

                if (response.isSuccessful()) {


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
                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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


        taluka_adapter11 = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, taluka_list11);


//        permant_taluka_spinner.setAdapter(taluka_adapter11);

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
                        permant_taluka_spinner.setSelection(pos);

                        taluka_str11 = taluka_str;

                    }
                }
            }
        }

    }
    /*----end permanenet spinner data---*/


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
                        AccomodationDetailActivity.super.onBackPressed();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void getSampradayList() {

        sampradayListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<SampradayList> userResponse = apiService.sampradayList();
        userResponse.enqueue(new Callback<SampradayList>() {

            @Override
            public void onResponse(Call<SampradayList> call, Response<SampradayList> response) {
                sampradayList = response.body();

                System.out.println("check 111-------------" +  new Gson().toJson(sampradayList));
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

                if (!isNetworkAvailable(AccomodationDetailActivity.this)) {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(AccomodationDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void sampraday_spinner_data(List<SampradayList.SampradayListData> sampradayListData) {

        sampraday_list.clear();

        sampraday_list.add("Select Sampraday");

        if (sampraday_list.size() != 0) {
            for (int i = 0; i < sampradayListData.size(); i++) {

                sampraday_list.add(i + 1, sampradayListData.get(i).getName());

            }
        }
        adapter_sampraday = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, sampraday_list);

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


}
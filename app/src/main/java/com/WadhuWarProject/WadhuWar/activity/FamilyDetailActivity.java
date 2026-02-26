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
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MotherOccupationList;
import com.WadhuWarProject.WadhuWar.model.OccupationList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyDetailActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private NetworkStateReceiver networkStateReceiver;

    RelativeLayout submit_btn;
    TextView a1, a2, a3, a4, a5,a6;
    static boolean isNetworkAvailable;

    Spinner fatheroccupation_spinner, motheroccupation_spinner, familyclass_spinner, familytype_spinner, mamaoccupation_spinner, sibingoccupation_spinner;


    ArrayList<OccupationList.OccupationListData> occupationListData = new ArrayList<>();
    OccupationList occupationList;
    ArrayList<MotherOccupationList.MotherOccupationData> motherOccupationData = new ArrayList<>();
    MotherOccupationList motherOccupationList;
    ArrayList<OccupationList.OccupationListData> mamaoccupationListData = new ArrayList<>();
    OccupationList mamaoccupationList;
    ArrayList<OccupationList.OccupationListData> siblingoccupationListData = new ArrayList<>();
    OccupationList siblingoccupationList;

    List<String> occupational_list = new ArrayList<String>();
    List<String> motheroccupational_list = new ArrayList<String>();
    List<String> mamaoccupational_list = new ArrayList<String>();
    List<String> siblingoccupational_list = new ArrayList<String>();

    String occupation_list_str, mother_occupation_list_str, familyclass_str, familytype_str, mamaoccupation_list_str, siblingoccupation_list_str;
    String occupation_id, mother_occupation_id, familyclass_id, familytype_id, mamaoccupational_id, siblingoccupational_id;

    ArrayAdapter<String> adapter_occupation;
    ArrayAdapter<String> adapter_occupation_mother;
    ArrayAdapter<String> adapter_familyclass;
    ArrayAdapter<String> adapter_familytype;
    ArrayAdapter<String> adapter_mamaoccupation;
    ArrayAdapter<String> adapter_siblingoccupation;

    String[] familyclass_list = {"Select Family Class", "Low Class", "Medium Class", "High Class"};
    String[] familytype_list = {"Select Family Type", "Single", "Joint"};

    EditText father_full_name, father_property, property_location, property_type_name,father_mobile_no, mother_full_name, mother_mobile_no, family_location, mama_full_name, mamekul,
            mama_mobile_no, count_sibling, name_sibling, count_married_sibling;
    String _father_full_name, _father_property, _property_location,_property_type_name, _father_mobile_no, _mother_full_name, _mother_mobile_no, _family_location, _mama_full_name, _mamekul,
            _mama_mobile_no, _count_sibling, _name_sibling, _count_married_sibling;

    ProgressDialog progressBar;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;

    String user_id;

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
        setContentView(R.layout.activity_family_detail);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_family_detail);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        submit_btn = (RelativeLayout) findViewById(R.id.submit_btn);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);
        a5 = findViewById(R.id.a5);
        a6 = findViewById(R.id.a6);
        fatheroccupation_spinner = findViewById(R.id.fatheroccupation_spinner);
        motheroccupation_spinner = findViewById(R.id.motheroccupation_spinner);
        familyclass_spinner = findViewById(R.id.familyclass_spinner);
        familytype_spinner = findViewById(R.id.familytype_spinner);
        mamaoccupation_spinner = findViewById(R.id.mamaoccupation_spinner);
        sibingoccupation_spinner = findViewById(R.id.sibingoccupation_spinner);
        father_full_name = findViewById(R.id.father_full_name);
        father_property = findViewById(R.id.father_property);
        property_location = findViewById(R.id.property_location);
        property_type_name = findViewById(R.id.property_type_name);
        father_mobile_no = findViewById(R.id.father_mobile_no);
        mother_full_name = findViewById(R.id.mother_full_name);
        mother_mobile_no = findViewById(R.id.mother_mobile_no);
        family_location = findViewById(R.id.family_location);
        mama_full_name = findViewById(R.id.mama_full_name);
        mamekul = findViewById(R.id.mamekul);
        mama_mobile_no = findViewById(R.id.mama_mobile_no);
        count_sibling = findViewById(R.id.count_sibling);
        name_sibling = findViewById(R.id.name_sibling);
        count_married_sibling = findViewById(R.id.count_married_sibling);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        Intent i = getIntent();
        user_id = (i.getStringExtra("user_id"));

        a1.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a1.setTextColor(Color.WHITE);
        a2.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a2.setTextColor(Color.WHITE);
        a3.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a3.setTextColor(Color.WHITE);
        a4.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a4.setTextColor(Color.WHITE);
//        a5.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
//        a5.setTextColor(Color.WHITE);



        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                _father_full_name = father_full_name.getText().toString();
                _father_property = father_property.getText().toString();
                _property_location = property_location.getText().toString();
                _property_type_name = property_type_name.getText().toString();
                _father_mobile_no = father_mobile_no.getText().toString();
                _mother_full_name = mother_full_name.getText().toString();
                _mother_mobile_no = mother_mobile_no.getText().toString();
                _family_location = family_location.getText().toString();
                _mama_full_name = mama_full_name.getText().toString();
                _mamekul = mamekul.getText().toString();
                _mama_mobile_no = mama_mobile_no.getText().toString();
                _count_sibling = count_sibling.getText().toString();
                _name_sibling = name_sibling.getText().toString();
                _count_married_sibling = count_married_sibling.getText().toString();


                if (occupation_list_str != null && !occupation_list_str.contentEquals("")) {
                    for (int i = 0; i < occupationListData.size(); i++) {
                        if (occupation_list_str.equals(occupationListData.get(i).getName())) {
                            occupation_id = String.valueOf(occupationListData.get(i).getId());
                        }
                    }
                } else {
                    occupation_id = "";

                }


                if (mamaoccupation_list_str != null && !mamaoccupation_list_str.contentEquals("")) {
                    for (int i = 0; i < mamaoccupationListData.size(); i++) {
                        if (mamaoccupation_list_str.equals(mamaoccupationListData.get(i).getName())) {
                            mamaoccupational_id = String.valueOf(mamaoccupationListData.get(i).getId());
                        }
                    }
                } else {
                    mamaoccupational_id = "";

                }
                if (siblingoccupation_list_str != null && !siblingoccupation_list_str.contentEquals("")) {
                    for (int i = 0; i < siblingoccupationListData.size(); i++) {
                        if (siblingoccupation_list_str.equals(siblingoccupationListData.get(i).getName())) {
                            siblingoccupational_id = String.valueOf(siblingoccupationListData.get(i).getId());
                        }
                    }
                } else {
                    siblingoccupational_id = "";

                }

                if (familyclass_str.contentEquals("Select Family Class")) {
                    familyclass_str = "";
                }
                if (familytype_str.contentEquals("Select Family Type")) {
                    familytype_str = "";
                }

                submitData(_father_full_name, occupation_id, _father_property, _property_location, _property_type_name,_father_mobile_no, _mother_full_name, mother_occupation_list_str,
                        _mother_mobile_no, _family_location, familyclass_str, familytype_str, _mama_full_name, _mamekul, _mama_mobile_no, mamaoccupational_id,
                        _count_sibling, _name_sibling, _count_married_sibling, siblingoccupational_id, user_id);

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
        getOccupationList();
        getMotherOccupationList();
        getFamilyClassList();
        getFamilyTypeList();
        getMamaOccupationList();
        getSiblingOccupationList();


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getOccupationList();
                getMotherOccupationList();
                getFamilyClassList();
                getFamilyTypeList();
                getMamaOccupationList();
                getSiblingOccupationList();
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

                Toast.makeText(FamilyDetailActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void submitData(String _father_full_name, String occupation_id, String _father_property, String _property_location, String _property_type_name, String _father_mobile_no,
                           String _mother_full_name, String mother_occupation_list_str, String _mother_mobile_no, String _family_location, String familyclass_str,
                           String familytype_str, String _mama_full_name, String _mamekul, String _mama_mobile_no, String mamaoccupational_id, String _count_sibling,
                           String _name_sibling, String _count_married_sibling, String siblingoccupational_id, String user_id) {


        progressBar = ProgressDialog.show(FamilyDetailActivity.this, "", "Please Wait...");


        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.insertStep4(_father_full_name, occupation_id, _father_property, _property_location, _property_type_name, _father_mobile_no,
                _mother_full_name, mother_occupation_list_str, _mother_mobile_no, _family_location, familyclass_str, familytype_str, _mama_full_name, _mamekul,
                _mama_mobile_no, mamaoccupational_id, _count_sibling, _name_sibling, _count_married_sibling, siblingoccupational_id, user_id);


        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                insertResponse = response.body();

                progressBar.dismiss();
                if (response.isSuccessful()) {

                    /*System.out.println("Familt detail resp ========" + new Gson().toJson(insertResponse));*/

                    String success = insertResponse.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(FamilyDetailActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                     //   Intent i = new Intent(FamilyDetailActivity.this, LoginActivity.class);
                        Intent i = new Intent(FamilyDetailActivity.this, PartnerPreferencesFormActivity2.class);
//                        Intent i = new Intent(FamilyDetailActivity.this,PartnerPreferencesFormActivity.class);
                        i.putExtra("user_id", user_id);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    } else {
                        Toast.makeText(FamilyDetailActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err family detail******" + t);

                if (!isNetworkAvailable(FamilyDetailActivity.this)) {

                    Toast.makeText(FamilyDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(FamilyDetailActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    public void getSiblingOccupationList() {

        siblingoccupationListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService.occupation("10");
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                siblingoccupationList = response.body();

                if (response.isSuccessful()) {


                    String success = siblingoccupationList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < siblingoccupationList.getOccupationList().size(); i++) {
                            siblingoccupationListData.add(new OccupationList.OccupationListData(siblingoccupationList.getOccupationList().get(i).getId(), siblingoccupationList.getOccupationList().get(i).getName()));

                        }
                        siblingoccupation_spinner_data(siblingoccupationListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationList> call, Throwable t) {

                System.out.println("err sibling occupation******" + t.toString());

                if (!isNetworkAvailable(FamilyDetailActivity.this)) {
//                    Toast.makeText(FamilyDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(FamilyDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void siblingoccupation_spinner_data(List<OccupationList.OccupationListData> siblingoccupationListData) {

        siblingoccupational_list.clear();

        siblingoccupational_list.add("Select Occupation");

        if (siblingoccupational_list.size() != 0) {
            for (int i = 0; i < siblingoccupationListData.size(); i++) {

                siblingoccupational_list.add(i + 1, siblingoccupationListData.get(i).getName());

            }
        }
        adapter_siblingoccupation = new ArrayAdapter<String>(FamilyDetailActivity.this, R.layout.simple_spinner_item, siblingoccupational_list);

        sibingoccupation_spinner.setAdapter(adapter_siblingoccupation);

        sibingoccupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                siblingoccupation_list_str = sibingoccupation_spinner.getItemAtPosition(position).toString();
                System.out.println("siblingoccupation_list_str------" + siblingoccupation_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getMamaOccupationList() {

        mamaoccupationListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService.occupation("10");
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                mamaoccupationList = response.body();

                if (response.isSuccessful()) {


                    String success = mamaoccupationList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < mamaoccupationList.getOccupationList().size(); i++) {
                            mamaoccupationListData.add(new OccupationList.OccupationListData(mamaoccupationList.getOccupationList().get(i).getId(), mamaoccupationList.getOccupationList().get(i).getName()));

                        }
                        mamaoccupation_spinner_data(mamaoccupationListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationList> call, Throwable t) {

                System.out.println("err mama occupation******" + t.toString());

                if (!isNetworkAvailable(FamilyDetailActivity.this)) {
//                    Toast.makeText(FamilyDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(FamilyDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void mamaoccupation_spinner_data(List<OccupationList.OccupationListData> mamaoccupationListData) {

        mamaoccupational_list.clear();

        mamaoccupational_list.add("Select Occupation");

        if (mamaoccupational_list.size() != 0) {
            for (int i = 0; i < mamaoccupationListData.size(); i++) {

                mamaoccupational_list.add(i + 1, mamaoccupationListData.get(i).getName());

            }
        }
        adapter_mamaoccupation = new ArrayAdapter<String>(FamilyDetailActivity.this, R.layout.simple_spinner_item, mamaoccupational_list);

        mamaoccupation_spinner.setAdapter(adapter_mamaoccupation);

        mamaoccupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                mamaoccupation_list_str = mamaoccupation_spinner.getItemAtPosition(position).toString();
                System.out.println("occupation_list_str------" + mamaoccupation_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getFamilyTypeList() {

        adapter_familytype = new ArrayAdapter<String>(FamilyDetailActivity.this, R.layout.simple_spinner_item, familytype_list);

        familytype_spinner.setAdapter(adapter_familytype);

        familytype_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                familytype_str = familytype_spinner.getItemAtPosition(position).toString();
                System.out.println("familytype_str------" + familytype_str);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getFamilyClassList() {

        adapter_familyclass = new ArrayAdapter<String>(FamilyDetailActivity.this, R.layout.simple_spinner_item, familyclass_list);

        familyclass_spinner.setAdapter(adapter_familyclass);

        familyclass_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                familyclass_str = familyclass_spinner.getItemAtPosition(position).toString();
                System.out.println("familyclass_str------" + familyclass_str);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getMotherOccupationList() {

        motherOccupationData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<MotherOccupationList> userResponse = apiService.motherOccupationList();
        userResponse.enqueue(new Callback<MotherOccupationList>() {

            @Override
            public void onResponse(Call<MotherOccupationList> call, Response<MotherOccupationList> response) {
                motherOccupationList = response.body();

                if (response.isSuccessful()) {


                    String success = motherOccupationList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < motherOccupationList.getMotherOccupation().size(); i++) {
                            motherOccupationData.add(new MotherOccupationList.MotherOccupationData(motherOccupationList.getMotherOccupation().get(i).getName()));

                        }
                        mother_occupation_spinner_data(motherOccupationData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<MotherOccupationList> call, Throwable t) {

                System.out.println("err mother occupation******" + t.toString());

                if (!isNetworkAvailable(FamilyDetailActivity.this)) {
//                    Toast.makeText(FamilyDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(FamilyDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void mother_occupation_spinner_data(List<MotherOccupationList.MotherOccupationData> motherOccupationData) {

        motheroccupational_list.clear();

        motheroccupational_list.add("Select Occupation");

        if (motheroccupational_list.size() != 0) {
            for (int i = 0; i < motherOccupationData.size(); i++) {

                motheroccupational_list.add(i + 1, motherOccupationData.get(i).getName());

            }
        }
        adapter_occupation_mother = new ArrayAdapter<String>(FamilyDetailActivity.this, R.layout.simple_spinner_item, motheroccupational_list);

        motheroccupation_spinner.setAdapter(adapter_occupation_mother);

        motheroccupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                mother_occupation_list_str = motheroccupation_spinner.getItemAtPosition(position).toString();
                System.out.println("mother_occupation_list_str------" + mother_occupation_list_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getOccupationList() {

        occupationListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService.occupation("10");
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                occupationList = response.body();
                swipeRefreshLayout.setRefreshing(false);
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

                System.out.println("err father occupation******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
                if (!isNetworkAvailable(FamilyDetailActivity.this)) {
//                    Toast.makeText(FamilyDetailActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(FamilyDetailActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_occupation = new ArrayAdapter<String>(FamilyDetailActivity.this, R.layout.simple_spinner_item, occupational_list);

        fatheroccupation_spinner.setAdapter(adapter_occupation);

        fatheroccupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                occupation_list_str = fatheroccupation_spinner.getItemAtPosition(position).toString();
                System.out.println("occupation_list_str------" + occupation_list_str);

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


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FamilyDetailActivity.super.onBackPressed();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}
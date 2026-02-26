package com.WadhuWarProject.WadhuWar.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.WadhuWarProject.WadhuWar.model.FetchAccommodation;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MotherOccupationList;
import com.WadhuWarProject.WadhuWar.model.OccupationList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class FamilyActivity extends AppCompatActivity  implements  NetworkStateReceiver.NetworkStateReceiverListener {

    InsertResponse edit_response;
    private NetworkStateReceiver networkStateReceiver;
    UserData user;
    LinearLayout update_btn;
    static boolean isNetworkAvailable;

    Spinner fatheroccupation_spinner,motheroccupation_spinner,familyclass_spinner,familytype_spinner,mamaoccupation_spinner,sibingoccupation_spinner;


    ArrayList<OccupationList.OccupationListData> occupationListData =  new ArrayList<>();
    OccupationList occupationList;
    ArrayList<MotherOccupationList.MotherOccupationData> motherOccupationData =  new ArrayList<>();
    MotherOccupationList motherOccupationList;
    ArrayList<OccupationList.OccupationListData> mamaoccupationListData =  new ArrayList<>();
    OccupationList mamaoccupationList;
    ArrayList<OccupationList.OccupationListData> siblingoccupationListData =  new ArrayList<>();
    OccupationList siblingoccupationList;

    List<String> occupational_list = new ArrayList<String>();
    List<String> motheroccupational_list = new ArrayList<String>();
    List<String> mamaoccupational_list = new ArrayList<String>();
    List<String> siblingoccupational_list = new ArrayList<String>();

    String occupation_list_str,mother_occupation_list_str,familyclass_str,familytype_str,mamaoccupation_list_str,siblingoccupation_list_str;
    String occupation_id,mother_occupation_id,familyclass_id,familytype_id,mamaoccupational_id,siblingoccupational_id;

    ArrayAdapter<String> adapter_occupation;
    ArrayAdapter<String> adapter_occupation_mother;
    ArrayAdapter<String> adapter_familyclass;
    ArrayAdapter<String> adapter_familytype;
    ArrayAdapter<String> adapter_mamaoccupation;
    ArrayAdapter<String> adapter_siblingoccupation;

    List<String> familyclass_list;
    List<String> familytype_list;

    Toolbar toolbar;

    EditText father_full_name,father_property,property_location,property_type_name,mother_oc,father_mobile_no,mother_full_name,mother_mobile_no,family_location,mama_full_name,mamekul,
            mama_mobile_no,count_sibling,name_sibling,count_married_sibling,brother_no,sister_no,sibling_name,married_no;
    String _father_full_name,_father_property,_property_location,_property_type_name,_mother_oc,_father_mobile_no,_mother_full_name,_mother_mobile_no,_family_location,_mama_full_name,_mamekul,
            _mama_mobile_no,_count_sibling,_name_sibling,_count_married_sibling,_brother_no,_sister_no,_sibling_name,_married_no;

    ProgressDialog progressBar;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;

    String user_id;
    FetchProfile fetchFamily;


    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;

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
        setContentView(R.layout.activity_family);

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
//        setContentView(R.layout.activity_family);


        familyclass_list = new ArrayList<String>();
        familyclass_list.add("Select Family Class");
        familyclass_list.add("Low Class");
        familyclass_list.add("Medium Class");
        familyclass_list.add("High Class");

        familytype_list = new ArrayList<String>();
        familytype_list.add("Select Family Type");
        familytype_list.add("Single");
        familytype_list.add("Joint");



        user = SharedPrefManager.getInstance(FamilyActivity.this).getUser();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        married_no =  findViewById(R.id.married_no);
        sibling_name =  findViewById(R.id.sibling_name);
        sister_no =  findViewById(R.id.sister_no);
        brother_no =  findViewById(R.id.brother_no);

        update_btn =  findViewById(R.id.update_btn);
        fatheroccupation_spinner = findViewById(R.id.fatheroccupation_spinner);
        motheroccupation_spinner = findViewById(R.id.motheroccupation_spinner);
        familyclass_spinner = findViewById(R.id.familyclass_spinner);
        familytype_spinner = findViewById(R.id.spinner_family_type);
        mamaoccupation_spinner = findViewById(R.id.mamaoccupation_spinner);
        sibingoccupation_spinner = findViewById(R.id.spinner_sibling_occupation);
        father_full_name = findViewById(R.id.father_full_name);
        father_property = findViewById(R.id.father_property);
        property_location = findViewById(R.id.property_location);
        property_type_name = findViewById(R.id.property_type_name);
        father_mobile_no = findViewById(R.id.father_mobile_no);
        mother_oc = findViewById(R.id.mother_oc);
        mother_full_name = findViewById(R.id.mother_full_name);
        mother_mobile_no = findViewById(R.id.mother_mobile_no);
        family_location = findViewById(R.id.family_location);
        mama_full_name = findViewById(R.id.mama_full_name);
        mamekul = findViewById(R.id.mamekul);
        mama_mobile_no = findViewById(R.id.mama_mobile_no);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


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

                _father_full_name = father_full_name.getText().toString();
                _father_property = father_property.getText().toString();
                _property_location = property_location.getText().toString();
                _property_type_name = property_type_name.getText().toString();
                _father_mobile_no = father_mobile_no.getText().toString();
                _mother_oc = mother_oc.getText().toString();
                _mother_full_name = mother_full_name.getText().toString();
                _mother_mobile_no = mother_mobile_no.getText().toString();
                _family_location = family_location.getText().toString();
                _mama_full_name = mama_full_name.getText().toString();
                _mamekul = mamekul.getText().toString();
                _mama_mobile_no = mama_mobile_no.getText().toString();
                _brother_no = brother_no.getText().toString();
                _sister_no = sister_no.getText().toString();
                _sibling_name = sibling_name.getText().toString();
                _married_no = married_no.getText().toString();


                if(allDone()) {


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

                    updateData(String.valueOf(user.getUser_id()), _father_full_name, occupation_id, _father_property, _property_location, _property_type_name,_father_mobile_no,
                            _mother_full_name, _mother_oc, _mother_mobile_no, _family_location, familyclass_str,
                            familytype_str, _mama_full_name, _mamekul, _mama_mobile_no, mamaoccupational_id,
                            _brother_no, _sister_no, _sibling_name, _married_no, siblingoccupational_id);

                }



            }
        });

        fetchFamilyData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }


    private Boolean allDone(){



        if(father_full_name==null){
            return false;
        }

        if(father_property==null){
            return false;
        }
        if(property_location==null){
            return false;
        }
        if(property_type_name==null){
            return false;
        }

        if(father_mobile_no==null){
            return false;
        }
        if(mother_full_name==null){
            return false;
        }

        if(mother_mobile_no==null){
            return false;
        }
        if(family_location==null){
            return false;
        }
        if(mama_full_name==null){
            return false;
        }
        if(mamekul==null){
            return false;
        }

        if(mama_mobile_no==null){
            return false;
        }

        if(brother_no==null){
            return false;
        }

        if(sister_no==null){
            return false;
        }

        if(married_no==null){
            return false;
        }




        View selectedView_fatheroccupation_spinner = fatheroccupation_spinner.getSelectedView();
        View selectedView_motheroccupation_spinner = motheroccupation_spinner.getSelectedView();
        View selectedView_familyclass_spinner = familyclass_spinner.getSelectedView();
        View selectedView_familytype_spinner = familytype_spinner.getSelectedView();
        View selectedView_mamaoccupation_spinner = mamaoccupation_spinner.getSelectedView();
        View selectedView_sibingoccupation_spinner = sibingoccupation_spinner.getSelectedView();


        if(selectedView_fatheroccupation_spinner==null ){
            return false;
        }

//        if(  selectedView_motheroccupation_spinner==null){
//            return false;
//        }

        if( selectedView_familyclass_spinner==null ){
            return false;
        }

        if(selectedView_familytype_spinner==null){
            return false;
        }
        if(selectedView_mamaoccupation_spinner==null){
            return false;
        }
        if(selectedView_sibingoccupation_spinner==null){
            return false;
        }


        return true;
    }



    @Override
    public void networkAvailable() {


//        fetchFamilyData();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchFamilyData();

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

//                Toast.makeText(FamilyActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try_again_txt.setVisibility(View.GONE);
                simpleProgressBar.setVisibility(View.VISIBLE);
                if(!isNetworkAvailable()){
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
                }
                else{
                    networkUnavailable();
                }

            }
        });
    }
    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }

    public void updateData(String user_id,String _father_full_name,String occupation_id,String _father_property,
                           String _property_location,String _property_type_name,String _father_mobile_no,String  _mother_full_name,String _mother_oc,
                           String _mother_mobile_no,String _family_location,String familyclass_str, String familytype_str,
                           String _mama_full_name,String _mamekul,String _mama_mobile_no,String mamaoccupational_id,
                           String _brother_no,String _sister_no,String _sibling_name,String _count_married_sibling,String siblingoccupational_id){

        System.out.println("user_id=========" + user_id);
        System.out.println("_father_full_name=========" + _father_full_name);
        System.out.println("occupation_id=========" + occupation_id);
        System.out.println("_father_property=========" + _father_property);
        System.out.println("_property_location=========" + _property_location);
        System.out.println("_property_type_name=========" + _property_type_name);
        System.out.println("_father_mobile_no=========" + _father_mobile_no);
        System.out.println("_mother_full_name=========" + _mother_full_name);
        System.out.println("mother_occupation_id=========" + mother_occupation_id);
        System.out.println("_mother_mobile_no=========" + _mother_mobile_no);
        System.out.println("_family_location=========" + _family_location);
        System.out.println("familyclass_str=========" + familyclass_str);
        System.out.println("familytype_str=========" + familytype_str);
        System.out.println("_mama_full_name=========" + _mama_full_name);
        System.out.println("_mamekul=========" + _mamekul);
        System.out.println("_mama_mobile_no=========" + _mama_mobile_no);
        System.out.println("mamaoccupational_id=========" + mamaoccupational_id);
        System.out.println("_brother_no=========" + _brother_no);
        System.out.println("_sister_no=========" + _sister_no);
        System.out.println("_sibling_name=========" + _sibling_name);
        System.out.println("_count_married_sibling=========" + _count_married_sibling);
        System.out.println("siblingoccupational_id=========" + siblingoccupational_id);


        progressBar = ProgressDialog.show(FamilyActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editFamilyDetails(user_id,_father_full_name,occupation_id,_father_property,
                _property_location,_property_type_name,_father_mobile_no, _mother_full_name,_mother_oc, _mother_mobile_no,_family_location,
                familyclass_str, familytype_str,_mama_full_name,_mamekul,_mama_mobile_no,mamaoccupational_id,
                _brother_no,_sister_no,_sibling_name,_count_married_sibling,siblingoccupational_id);


        userResponse.enqueue(new Callback<InsertResponse>() {


            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {

                    String success = edit_response.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(FamilyActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                        Intent i= new Intent(FamilyActivity.this,MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(FamilyActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err accomo******" + t.toString());

                if(!isNetworkAvailable(FamilyActivity.this)){
//                    Toast.makeText(FamilyActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(FamilyActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }



    public  void fetchFamilyData(){

        System.out.println("user_id111=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchFamilyDetails(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                swipeRefreshLayout.setRefreshing(false);
                fetchFamily = response.body();


                /*System.out.println("fetchFamily -------" + new Gson().toJson(fetchFamily));*/

                if (response.isSuccessful()) {

//                    update_btn.setVisibility(View.VISIBLE);



                    father_full_name.setText(getValidText(fetchFamily.getFather_name()));
                    father_property.setText(getValidText(fetchFamily.getFather_property()));
                    property_location.setText(getValidText(fetchFamily.getProperty_loc()));
                    property_type_name.setText(getValidText(fetchFamily.getProperty_type_name()));
                    father_mobile_no.setText(getValidText(fetchFamily.getFather_mobile()));
                    mother_oc.setText(getValidText(fetchFamily.getMother_occ()));
                    mother_full_name.setText(getValidText(fetchFamily.getMother_name()));
                    mother_mobile_no.setText(getValidText(fetchFamily.getMother_mobile()));
                    family_location.setText(getValidText(fetchFamily.getFamily_loc()));
                    mama_full_name.setText(getValidText(fetchFamily.getMama_name()));
                    mamekul.setText(getValidText(fetchFamily.getMamekul()));
                    mama_mobile_no.setText(getValidText(fetchFamily.getMama_mobile()));
                    brother_no.setText(getValidText(fetchFamily.getBro_count()));
                    sister_no.setText(getValidText(fetchFamily.getSis_count()));
                    sibling_name.setText(getValidText(fetchFamily.getSibling_names()));
                    married_no.setText(getValidText(fetchFamily.getMarried_sibling()));


                    getOccupationList();
                    getMotherOccupationList();
                    getFamilyClassList();
                    getFamilyTypeList();
                    getMamaOccupationList();
                    getSiblingOccupationList();
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






    public  void  getSiblingOccupationList(){

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


                        for(int i=0;i<siblingoccupationList.getOccupationList().size();i++){
                            siblingoccupationListData.add(new OccupationList.OccupationListData(siblingoccupationList.getOccupationList().get(i).getId(),siblingoccupationList.getOccupationList().get(i).getName()));

                        }
                        siblingoccupation_spinner_data(siblingoccupationListData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationList> call, Throwable t) {

                System.out.println("err sibling occupation******" + t.toString());

                if(!isNetworkAvailable(FamilyActivity.this)){
//                    Toast.makeText(FamilyActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(FamilyActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void siblingoccupation_spinner_data(List<OccupationList.OccupationListData> siblingoccupationListData) {

        siblingoccupational_list.clear();
        siblingoccupational_list.add("Select Occupation");

        if (siblingoccupationListData != null && !siblingoccupationListData.isEmpty()) {
            for (int i = 0; i < siblingoccupationListData.size(); i++) {
                siblingoccupational_list.add(i + 1, siblingoccupationListData.get(i).getName().trim());
            }
        }

        adapter_siblingoccupation = new ArrayAdapter<>(
                FamilyActivity.this,
                R.layout.simple_spinner_item,
                siblingoccupational_list
        );
        adapter_siblingoccupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sibingoccupation_spinner.setAdapter(adapter_siblingoccupation);

        // Optional: Set previously selected value
        String savedSiblingOcc = fetchFamily.getSibling_occ_name();
        if (savedSiblingOcc != null && !savedSiblingOcc.trim().isEmpty()) {
            for (int i = 0; i < siblingoccupational_list.size(); i++) {
                if (siblingoccupational_list.get(i).equalsIgnoreCase(savedSiblingOcc.trim())) {
                    sibingoccupation_spinner.setSelection(i);
                    break;
                }
            }
        }

        // Show searchable dialog on touch
        sibingoccupation_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableSiblingOccupationDialog(siblingoccupational_list);
            }
            return true;
        });
    }

    private void showSearchableSiblingOccupationDialog(List<String> occupationList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FamilyActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalList = new ArrayList<>(occupationList);
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                FamilyActivity.this,
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
                String selectedItem = dialogAdapter.getItem(position);
                if (selectedItem == null) {
                    Toast.makeText(FamilyActivity.this, "Error selecting occupation", Toast.LENGTH_SHORT).show();
                    return;
                }

                int originalPosition = originalList.indexOf(selectedItem);
                if (originalPosition != -1) {
                    sibingoccupation_spinner.setSelection(originalPosition);
                    siblingoccupation_list_str = selectedItem;
                    System.out.println("siblingoccupation_list_str------" + siblingoccupation_list_str);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(FamilyActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    public  void  getMamaOccupationList(){

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


                        for(int i=0;i<mamaoccupationList.getOccupationList().size();i++){
                            mamaoccupationListData.add(new OccupationList.OccupationListData(mamaoccupationList.getOccupationList().get(i).getId(),mamaoccupationList.getOccupationList().get(i).getName()));

                        }
                        mamaoccupation_spinner_data(mamaoccupationListData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationList> call, Throwable t) {

                System.out.println("err mama occupation******" + t.toString());

                if(!isNetworkAvailable(FamilyActivity.this)){
//                    Toast.makeText(FamilyActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(FamilyActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void mamaoccupation_spinner_data(List<OccupationList.OccupationListData> mamaoccupationListData) {

        mamaoccupational_list.clear();
        mamaoccupational_list.add("Select Occupation");

        if (mamaoccupationListData != null && !mamaoccupationListData.isEmpty()) {
            for (int i = 0; i < mamaoccupationListData.size(); i++) {
                mamaoccupational_list.add(i + 1, mamaoccupationListData.get(i).getName().trim());
            }
        }

        adapter_mamaoccupation = new ArrayAdapter<>(
                FamilyActivity.this,
                R.layout.simple_spinner_item,
                mamaoccupational_list
        );
        adapter_mamaoccupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mamaoccupation_spinner.setAdapter(adapter_mamaoccupation);

        // Pre-select previously saved occupation (if any)
        String savedMamaOcc = fetchFamily.getMama_occ_name();
        if (savedMamaOcc != null && !savedMamaOcc.trim().isEmpty()) {
            for (int i = 0; i < mamaoccupational_list.size(); i++) {
                if (mamaoccupational_list.get(i).equalsIgnoreCase(savedMamaOcc.trim())) {
                    mamaoccupation_spinner.setSelection(i);
                    break;
                }
            }
        }

        // Show searchable dialog on touch
        mamaoccupation_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableMamaOccupationDialog(mamaoccupational_list);
            }
            return true;
        });
    }

    private void showSearchableMamaOccupationDialog(List<String> occupationList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FamilyActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalList = new ArrayList<>(occupationList);
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                FamilyActivity.this,
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
                String selectedItem = dialogAdapter.getItem(position);
                if (selectedItem == null) {
                    Toast.makeText(FamilyActivity.this, "Error selecting occupation", Toast.LENGTH_SHORT).show();
                    return;
                }

                int originalPosition = originalList.indexOf(selectedItem);
                if (originalPosition != -1) {
                    mamaoccupation_spinner.setSelection(originalPosition);
                    mamaoccupation_list_str = selectedItem;
                    System.out.println("mamaoccupation_list_str------" + mamaoccupation_list_str);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(FamilyActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }



    public  void  getFamilyTypeList(){

        adapter_familytype = new ArrayAdapter<String>(FamilyActivity.this, R.layout.simple_spinner_item, familytype_list);

        familytype_spinner.setAdapter(adapter_familytype);

        int pos;
        for(int i=0;i<familytype_list.size();i++) {
            if (familytype_list.get(i).contains(fetchFamily.getFamily_type())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                familytype_spinner.setSelection(pos);
            }
        }

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

    public  void  getFamilyClassList(){

        adapter_familyclass = new ArrayAdapter<String>(FamilyActivity.this, R.layout.simple_spinner_item, familyclass_list);

        familyclass_spinner.setAdapter(adapter_familyclass);

        int pos;
        for(int i=0;i<familyclass_list.size();i++) {
            if (familyclass_list.get(i).contains(fetchFamily.getFamily_class())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                familyclass_spinner.setSelection(pos);
            }
        }

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

    public  void  getMotherOccupationList(){

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


                        for(int i=0;i<motherOccupationList.getMotherOccupation().size();i++){
                            motherOccupationData.add(new MotherOccupationList.MotherOccupationData(motherOccupationList.getMotherOccupation().get(i).getName()));

                        }
                        mother_occupation_spinner_data(motherOccupationData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<MotherOccupationList> call, Throwable t) {

                System.out.println("err mother occupation******" + t.toString());

                if(!isNetworkAvailable(FamilyActivity.this)){
//                    Toast.makeText(FamilyActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(FamilyActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void mother_occupation_spinner_data(List<MotherOccupationList.MotherOccupationData> motherOccupationData){

        motheroccupational_list.clear();

        motheroccupational_list.add("Select Occupation");

        if(motheroccupational_list.size()!=0) {
            for (int i = 0; i <motherOccupationData.size(); i++) {

                motheroccupational_list.add(i+1 , motherOccupationData.get(i).getName());

            }
        }
        adapter_occupation_mother = new ArrayAdapter<String>(FamilyActivity.this, R.layout.simple_spinner_item, motheroccupational_list);

        motheroccupation_spinner.setAdapter(adapter_occupation_mother);

        int pos;
        for(int i=0;i<motheroccupational_list.size();i++) {
            if (motheroccupational_list.get(i).contains(fetchFamily.getMother_occ())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                motheroccupation_spinner.setSelection(pos);
            }
        }

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


    public  void  getOccupationList(){

        occupationListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<OccupationList> userResponse = apiService.occupation("10");
        userResponse.enqueue(new Callback<OccupationList>() {

            @Override
            public void onResponse(Call<OccupationList> call, Response<OccupationList> response) {
                occupationList = response.body();

                if (response.isSuccessful()) {


                    String success = occupationList.getResid();

                    if (success.equals("200")) {


                        for(int i=0;i<occupationList.getOccupationList().size();i++){
                            occupationListData.add(new OccupationList.OccupationListData(occupationList.getOccupationList().get(i).getId(),occupationList.getOccupationList().get(i).getName()));

                        }
                        occupation_spinner_data(occupationListData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<OccupationList> call, Throwable t) {

                System.out.println("err father occupation******" + t.toString());

                if(!isNetworkAvailable(FamilyActivity.this)){
//                    Toast.makeText(FamilyActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(FamilyActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
                FamilyActivity.this,
                R.layout.simple_spinner_item,
                occupational_list
        );
        adapter_occupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fatheroccupation_spinner.setAdapter(adapter_occupation);

        // Optional: Pre-select saved value
        String savedOccupation = fetchFamily.getFather_occ_name();
        if (savedOccupation != null && !savedOccupation.trim().isEmpty()) {
            for (int i = 0; i < occupational_list.size(); i++) {
                if (occupational_list.get(i).equalsIgnoreCase(savedOccupation.trim())) {
                    fatheroccupation_spinner.setSelection(i);
                    break;
                }
            }
        }

        // Make the spinner show a searchable dialog on touch
        fatheroccupation_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableOccupationDialog(occupational_list);
            }
            return true;
        });
    }

    private void showSearchableOccupationDialog(List<String> occupationList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FamilyActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        List<String> originalOccupationList = new ArrayList<>(occupationList);
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                FamilyActivity.this,
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
                    Toast.makeText(FamilyActivity.this, "Error selecting occupation", Toast.LENGTH_SHORT).show();
                    return;
                }

                int originalPosition = originalOccupationList.indexOf(selectedOccupation);
                if (originalPosition != -1) {
                    fatheroccupation_spinner.setSelection(originalPosition);
                    occupation_list_str = selectedOccupation;
                    System.out.println("occupation_list_str------" + occupation_list_str);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(FamilyActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

}
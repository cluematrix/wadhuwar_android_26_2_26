package com.WadhuWarProject.WadhuWar.activity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import android.widget.TimePicker;
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
import com.WadhuWarProject.WadhuWar.model.BloodGroupList;
import com.WadhuWarProject.WadhuWar.model.ColorcomplexList;
import com.WadhuWarProject.WadhuWar.model.CountryList;
import com.WadhuWarProject.WadhuWar.model.DietaryList;
import com.WadhuWarProject.WadhuWar.model.DistrictList;
import com.WadhuWarProject.WadhuWar.model.EducationList;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.HeightList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.LifeSettingList;
import com.WadhuWarProject.WadhuWar.model.MaritalSettingList;
import com.WadhuWarProject.WadhuWar.model.OccupationList;
import com.WadhuWarProject.WadhuWar.model.SalaryList;
import com.WadhuWarProject.WadhuWar.model.StateList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.WadhuWarProject.WadhuWar.model.WeightList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class LifestyleActivity extends AppCompatActivity  implements  NetworkStateReceiver.NetworkStateReceiverListener {
    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;

    LinearLayout update_btn,childLL;
    Spinner marital_status_spinner,dietary_spinner,lifestyle_spinner,colorcomplex_spinner,
            bloodgroup_spinner,num_childred_spinner,childred_living_spinner;


    ArrayList<MaritalSettingList.MaritalSettingListData> maritalSettingListData =  new ArrayList<>();
    MaritalSettingList maritalSettingList;
    ArrayList<DietaryList.DietaryListData> dietaryListData =  new ArrayList<>();
    DietaryList dietaryList;
    ArrayList<LifeSettingList.LifeSettingListData> lifeSettingListData =  new ArrayList<>();
    LifeSettingList lifeSettingList;

    ArrayList<ColorcomplexList.ColorcomplexListData> colorcomplexListData =  new ArrayList<>();
    ColorcomplexList colorcomplexList;
    ArrayList<BloodGroupList.BloodGroupListData> bloodGroupListData =  new ArrayList<>();
    BloodGroupList bloodGroupList;



    List<String> marital_list = new ArrayList<String>();
    List<String> dietray_list = new ArrayList<String>();
    List<String> lifestyle_list = new ArrayList<String>();
    List<String> colorcomplex_list = new ArrayList<String>();
    List<String> bloodgroup_list = new ArrayList<String>();

    String marital_str, dietray_str,lifestyle_str,colorcomplex_str,bloodgroup_str,handicap_str,num_child_str, child_living_status_str;
    String dietray_id,lifestyle_id, colorcomplex_id,bloodgroup_id,marital_id;

    ArrayAdapter<String> adapter_dietray;
    ArrayAdapter<String> adapter_marital;
    ArrayAdapter<String> lifestyle_adapter;
    ArrayAdapter<String> colorcomplex_adapter;
    ArrayAdapter<String> bloodgroup_adapter;
    ArrayAdapter<String> num_child_adapter;
    ArrayAdapter<String> child_living_status_adapter;

    ArrayList<String> child_living_status_list ;
    ArrayList<String> num_child_list ;


    ProgressDialog progressBar;

    TextView birth_time;
    EditText hobbies,children_age;
    String _birth_time,_hobbies,_children_age;
    private int  mHour, mMinute;
    private String format = "";
    String user_id;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;
    UserData user;

    FetchProfile fetchLifestyle;

    InsertResponse edit_response;
    Toolbar toolbar;

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
        setContentView(R.layout.activity_lifestyle);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lifestyle);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        child_living_status_list = new ArrayList<String>();
        child_living_status_list.add("Select Children Living Status");
        child_living_status_list.add("Living with me");
        child_living_status_list.add("Not living with me");


        num_child_list= new ArrayList<String>();
        num_child_list.add("Select No. of Children");
        num_child_list.add("0");
        num_child_list.add("1");
        num_child_list.add("2");
        num_child_list.add("3");
        num_child_list.add("4");
        num_child_list.add("5 and above");



        user = SharedPrefManager.getInstance(LifestyleActivity.this).getUser();

        update_btn =  findViewById(R.id.update_btn);

        childLL =  findViewById(R.id.childLL);
        children_age =  findViewById(R.id.children_age);
        marital_status_spinner =  findViewById(R.id.marital_status_spinner);
        dietary_spinner =  findViewById(R.id.dietary_spinner);
        lifestyle_spinner =  findViewById(R.id.lifestyle_spinner);

        colorcomplex_spinner =  findViewById(R.id.colorcomplex_spinner);
        bloodgroup_spinner =  findViewById(R.id.bloodgroup_spinner);

        num_childred_spinner =  findViewById(R.id.num_childred_spinner);
        childred_living_spinner =  findViewById(R.id.childred_living_spinner);
        hobbies =  findViewById(R.id.hobbies);
        birth_time =  findViewById(R.id.birth_time);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


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
        birth_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(LifestyleActivity.this,
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

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                _birth_time = birth_time.getText().toString();
                _hobbies = hobbies.getText().toString();
                _children_age = children_age.getText().toString();


                if(allDone()) {


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
                    if(num_child_str!=null){
                        if (num_child_str.equals("Select No. of Children")) {
                            num_child_str = "";
                        }
                    }

                    if(child_living_status_str!=null) {
                        if (child_living_status_str.equals("Select Children Living Status")) {
                            child_living_status_str = "";
                        }
                    }


                    updateData(_birth_time, _hobbies, marital_id, num_child_str, _children_age, child_living_status_str, lifestyle_id, colorcomplex_id, bloodgroup_id, dietray_id);


                }
//                user_id,apifor=lifestyle,birth_time,hobbies,marital_status,lifestyle,color_complex,bloodgroup,dietry
            }
        });

        fetchLifestyleData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/




    }


    @Override
    public void networkAvailable() {

//        fetchLifestyleData();


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchLifestyleData();

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

    private Boolean allDone(){



        if(birth_time==null){
            return false;
        }

        if(hobbies==null){
            return false;
        }

        /*if(children_age==null){
            return false;
        }*/




        View selectedView_marital_status_spinner = marital_status_spinner.getSelectedView();
        View selectedView_colorcomplex_spinner = colorcomplex_spinner.getSelectedView();
        View selectedView_bloodgroup_spinner = bloodgroup_spinner.getSelectedView();
        View selectedView_lifestyle_spinner = lifestyle_spinner.getSelectedView();
        View selectedView_dietary_spinner = dietary_spinner.getSelectedView();
        View selectedView_num_childred_spinner = num_childred_spinner.getSelectedView();
        View selectedView_childred_living_spinner = childred_living_spinner.getSelectedView();


        if(selectedView_marital_status_spinner==null ){
            return false;
        }

        if(  selectedView_colorcomplex_spinner==null){
            return false;
        }

        if( selectedView_bloodgroup_spinner==null ){
            return false;
        }

        if(selectedView_lifestyle_spinner==null){
            return false;
        }
        if(selectedView_dietary_spinner==null){
            return false;
        }
       /* if(selectedView_num_childred_spinner==null){
            return false;
        }

        if(selectedView_childred_living_spinner==null){
            return false;
        }
*/

        return true;
    }


    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(LifestyleActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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

    public void updateData(String _birth_time,String _hobbies,String marital_id,String num_child_str, String _children_age,
                           String child_living_status_str,String lifestyle_id,String colorcomplex_id,String bloodgroup_id,String dietray_id){



        progressBar = ProgressDialog.show(LifestyleActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editLifestyle(String.valueOf(user.getUser_id()),"lifestyle",_birth_time,_hobbies,
                marital_id,num_child_str,_children_age,child_living_status_str,lifestyle_id,colorcomplex_id,bloodgroup_id,dietray_id);


        userResponse.enqueue(new Callback<InsertResponse>() {


            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {

                    String success = edit_response.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(LifestyleActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                        Intent i= new Intent(LifestyleActivity.this,MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(LifestyleActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err LifestyleActivity******" + t.toString());

                if(!isNetworkAvailable(LifestyleActivity.this)){
//                    Toast.makeText(LifestyleActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(LifestyleActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    public  void  getNumChildrenList(){




        num_child_adapter = new ArrayAdapter<String>(LifestyleActivity.this, R.layout.simple_spinner_item, num_child_list);

        num_childred_spinner.setAdapter(num_child_adapter);


        if(fetchLifestyle.getNo_of_child()!=null) {
            int pos;
            for (int i = 0; i < num_child_list.size(); i++) {
                if (num_child_list.get(i).contains(fetchLifestyle.getNo_of_child())) {
                    pos = i;
                    num_childred_spinner.setSelection(pos);
                }
            }
        }

        num_childred_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                num_child_str = num_childred_spinner.getItemAtPosition(position).toString();
                System.out.println("num_child_adapter------" + num_child_str);

//




            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public  void  getChildLivingStatusList(){

        child_living_status_adapter = new ArrayAdapter<String>(LifestyleActivity.this, R.layout.simple_spinner_item, child_living_status_list);

        childred_living_spinner.setAdapter(child_living_status_adapter);

        if(fetchLifestyle.getChild_stay()!=null) {
            int pos;
            for (int i = 0; i < child_living_status_list.size(); i++) {
                if (child_living_status_list.get(i).contains(fetchLifestyle.getChild_stay())) {
                    pos = i;
                    childred_living_spinner.setSelection(pos);
                }
            }
        }

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



    public  void fetchLifestyleData(){

        System.out.println("user_id111=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchLifestyle(String.valueOf(user.getUser_id()),"lifestyle");
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchLifestyle = response.body();
                swipeRefreshLayout.setRefreshing(false);

                /*System.out.println("education -------" + new Gson().toJson(fetchLifestyle));*/

                if (response.isSuccessful()) {
//                    update_btn.setVisibility(View.VISIBLE);


                    birth_time.setText(fetchLifestyle.getBirth_time());
                    hobbies.setText(fetchLifestyle.getHobbies());
                    children_age.setText(fetchLifestyle.getAge_of_child());


                    getMaritalStatusList();
                    getDietaryList();
                    getLifeStyleList();
                    getColorcomplexList();
                    getBloodGroupList();



                    getNumChildrenList();
                    getChildLivingStatusList();

                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 error fetch family******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    public  void  getBloodGroupList(){

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


                        for(int i=0;i<bloodGroupList.getBloodGroupList().size();i++){
                            bloodGroupListData.add(new BloodGroupList.BloodGroupListData(bloodGroupList.getBloodGroupList().get(i).getId(),bloodGroupList.getBloodGroupList().get(i).getName()));
                        }
                        bloodgroup_spinner_data(bloodGroupListData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<BloodGroupList> call, Throwable t) {

                System.out.println("err colorcomplex******" + t.toString());

                if(!isNetworkAvailable(LifestyleActivity.this)){
//                    Toast.makeText(LifestyleActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(LifestyleActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void  bloodgroup_spinner_data(List<BloodGroupList.BloodGroupListData> bloodGroupListData){

        bloodgroup_list.clear();

        bloodgroup_list.add("Select Blood Group");

        if(bloodgroup_list.size()!=0) {
            for (int i = 0; i < bloodGroupListData.size(); i++) {

                bloodgroup_list.add(i+1 , bloodGroupListData.get(i).getName());

            }
        }
        bloodgroup_adapter = new ArrayAdapter<String>(LifestyleActivity.this, R.layout.simple_spinner_item, bloodgroup_list);

        bloodgroup_spinner.setAdapter(bloodgroup_adapter);

        int pos;
        for(int i=0;i<bloodgroup_list.size();i++) {
            if (bloodgroup_list.get(i).contains(fetchLifestyle.getBloodgroup_name())) {
                pos = i;
                bloodgroup_spinner.setSelection(pos);
            }
        }


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



    public  void  getColorcomplexList(){

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


                        for(int i=0;i<colorcomplexList.getColorcomplexList().size();i++){
                            colorcomplexListData.add(new ColorcomplexList.ColorcomplexListData(colorcomplexList.getColorcomplexList().get(i).getId(),colorcomplexList.getColorcomplexList().get(i).getName()));
                        }
                        colorcomplex_spinner_data(colorcomplexListData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ColorcomplexList> call, Throwable t) {

                System.out.println("err colorcomplex******" + t.toString());

                if(!isNetworkAvailable(LifestyleActivity.this)){
//                    Toast.makeText(LifestyleActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(LifestyleActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void  colorcomplex_spinner_data(List<ColorcomplexList.ColorcomplexListData> colorcomplexListData){

        colorcomplex_list.clear();

        colorcomplex_list.add("Select Color-Complex");

        if(colorcomplex_list.size()!=0) {
            for (int i = 0; i < colorcomplexListData.size(); i++) {

                colorcomplex_list.add(i+1 , colorcomplexListData.get(i).getName());

            }
        }
        colorcomplex_adapter = new ArrayAdapter<String>(LifestyleActivity.this, R.layout.simple_spinner_item, colorcomplex_list);

        colorcomplex_spinner.setAdapter(colorcomplex_adapter);


        int pos;
        for(int i=0;i<colorcomplex_list.size();i++) {
            if (colorcomplex_list.get(i).trim().equals(fetchLifestyle.getColor_complex_name())) {
                pos = i;
                colorcomplex_spinner.setSelection(pos);
            }
        }
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



    public  void  getLifeStyleList(){

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


                        for(int i=0;i<lifeSettingList.getLifeSetting().size();i++){
                            lifeSettingListData.add(new LifeSettingList.LifeSettingListData(lifeSettingList.getLifeSetting().get(i).getId(),lifeSettingList.getLifeSetting().get(i).getName()));

                        }
                        lifestyle_spinner_data(lifeSettingListData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<LifeSettingList> call, Throwable t) {

                System.out.println("err lifestyle******" + t.toString());

                if(!isNetworkAvailable(LifestyleActivity.this)){
//                    Toast.makeText(LifestyleActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(LifestyleActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void lifestyle_spinner_data(List<LifeSettingList.LifeSettingListData> lifeSettingListData){

        lifestyle_list.clear();

        lifestyle_list.add("Select LifeStyle");

        if(lifestyle_list.size()!=0) {
            for (int i = 0; i < lifeSettingListData.size(); i++) {

                lifestyle_list.add(i+1 , lifeSettingListData.get(i).getName());

            }
        }
        lifestyle_adapter = new ArrayAdapter<String>(LifestyleActivity.this, R.layout.simple_spinner_item, lifestyle_list);

        lifestyle_spinner.setAdapter(lifestyle_adapter);

        int pos;
        for(int i=0;i<lifestyle_list.size();i++) {
            if (lifestyle_list.get(i).contains(fetchLifestyle.getLifestyle_name())) {
                pos = i;
                lifestyle_spinner.setSelection(pos);
            }
        }


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


    public  void  getDietaryList(){

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


                        for(int i=0;i<dietaryList.getDietaryList().size();i++){
                            dietaryListData.add(new DietaryList.DietaryListData(dietaryList.getDietaryList().get(i).getId(),dietaryList.getDietaryList().get(i).getName()));

                        }
                        dietary_spinner_data(dietaryListData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<DietaryList> call, Throwable t) {

                System.out.println("err dietary******" + t.toString());

                if(!isNetworkAvailable(LifestyleActivity.this)){
//                    Toast.makeText(LifestyleActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(LifestyleActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void dietary_spinner_data(List<DietaryList.DietaryListData> dietaryListData){

        dietray_list.clear();

        dietray_list.add("Select Dietary");

        if(dietray_list.size()!=0) {
            for (int i = 0; i < dietaryListData.size(); i++) {

                dietray_list.add(i+1 , dietaryListData.get(i).getName());

            }
        }
        adapter_dietray = new ArrayAdapter<String>(LifestyleActivity.this, R.layout.simple_spinner_item, dietray_list);

        dietary_spinner.setAdapter(adapter_dietray);

        int pos;
        for(int i=0;i<dietray_list.size();i++) {
            if (dietray_list.get(i).contains(fetchLifestyle.getDietry_name())) {
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



    public  void  getMaritalStatusList(){

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


                        for(int i=0;i<maritalSettingList.getMarital_Setting().size();i++){
                            maritalSettingListData.add(new MaritalSettingList.MaritalSettingListData(maritalSettingList.getMarital_Setting().get(i).getId(),maritalSettingList.getMarital_Setting().get(i).getName()));

                        }
                        marital_spinner_data(maritalSettingListData);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<MaritalSettingList> call, Throwable t) {

                System.out.println("err marital******" + t.toString());

                if(!isNetworkAvailable(LifestyleActivity.this)){
//                    Toast.makeText(LifestyleActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(LifestyleActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void marital_spinner_data(List<MaritalSettingList.MaritalSettingListData> maritalSettingListData){

        marital_list.clear();

        marital_list.add("Select Marital Status");

        if(marital_list.size()!=0) {
            for (int i = 0; i <maritalSettingListData.size(); i++) {

                marital_list.add(i+1 , maritalSettingListData.get(i).getName());

            }
        }
        adapter_marital = new ArrayAdapter<String>(LifestyleActivity.this, R.layout.simple_spinner_item, marital_list);

        marital_status_spinner.setAdapter(adapter_marital);

        int pos;
        for(int i=0;i<marital_list.size();i++) {
            if (marital_list.get(i).contains(fetchLifestyle.getMarital_status_name())) {
                pos = i;
                marital_status_spinner.setSelection(pos);

                if(fetchLifestyle.getMarital_status_name().equals("Devorce") ||
                        fetchLifestyle.getMarital_status_name().equals("Awaiting Divorce") ||
                        fetchLifestyle.getMarital_status_name().equals("Widow/Widower")){

                    childLL.setVisibility(View.VISIBLE);

                }else{
                    childLL.setVisibility(View.GONE);

                }

            }
        }

        marital_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                marital_str = marital_status_spinner.getItemAtPosition(position).toString();
                System.out.println("marital_str------" + marital_str);

                if(marital_str.equals("Devorce") ||marital_str.equals("Awaiting Divorce") || marital_str.equals("Widow/Widower") ){
                    childLL.setVisibility(View.VISIBLE);
                    getChildLivingStatusList();
                    getNumChildrenList();
                }else{
                    childLL.setVisibility(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
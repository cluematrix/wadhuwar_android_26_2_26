package com.WadhuWarProject.WadhuWar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchBasicInfo;
import com.WadhuWarProject.WadhuWar.model.HeightList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.ProfileCreatedList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.WadhuWarProject.WadhuWar.model.WeightList;
import com.google.android.material.snackbar.Snackbar;
//import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class BasicInfoActivity extends AppCompatActivity implements  NetworkStateReceiver.NetworkStateReceiverListener {
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    int years_form_date;
    String date;
    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;

    Spinner spinner_profile_created_by,gender_spinner,spinner_disability;
    TextView birth_date,disbility_name;
    Spinner spinner_height,spinner_weight;;
    LinearLayout disability_name_LL,update_btn;
    EditText age,email_id,alt_email_id,mobile,whatapp_no, first_name,middle_name,last_name;

    ArrayList<ProfileCreatedList.ProfileCreatedListData> profileCreatedListData =  new ArrayList<>();
    ProfileCreatedList profileCreatedList;

    FetchBasicInfo fetchBasicInfo;

    List<String> handicap_list ;

    List<String> profileCreatedList_list = new ArrayList<String>();
    List<String> height_list = new ArrayList<String>();
    List<String> weight_list = new ArrayList<String>();
    List<String> gender_list;

    String profileCreatedList_str,handicap_str,height_str,weight_str,gender_str;

    String profileCreatedList_id,height_id,weight_id;

    ArrayAdapter<String> adapter_profilecreatedfor;
    ArrayAdapter<String> handicap_adapter;
    ArrayAdapter<String> height_adapter;
    ArrayAdapter<String> weight_adapter;

    ArrayList<HeightList.HeightListData> heightListData =  new ArrayList<>();
    HeightList heightList;
    ArrayList<WeightList.WeightListData> weightListData =  new ArrayList<>();
    WeightList weightList;

    private int mYear, mMonth, mDay;
    UserData user;
    InsertResponse edit_response;
    ProgressDialog progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    Toolbar toolbar;

    TextView gender_update_txt;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;


    LinearLayout mo_num;

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
        setContentView(R.layout.activity_basic_info);

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
//        setContentView(R.layout.activity_basic_info);

        gender_update_txt = findViewById(R.id.gender_update_txt);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mo_num =  findViewById(R.id.mo_num);
        first_name =  findViewById(R.id.first_name);
        middle_name =  findViewById(R.id.middle_name);
        last_name =  findViewById(R.id.last_name);
        spinner_profile_created_by = findViewById(R.id.spinner_profile_created_by);
        gender_spinner = findViewById(R.id.gender_spinner);
        birth_date = findViewById(R.id.birth_date);
        email_id = findViewById(R.id.email_id);
        alt_email_id = findViewById(R.id.alt_email_id);
        mobile = findViewById(R.id.mobile);
        whatapp_no = findViewById(R.id.whatapp_no);
        spinner_disability = findViewById(R.id.spinner_disability);
        spinner_height = findViewById(R.id.spinner_height);
        spinner_weight = findViewById(R.id.spinner_weight);
        disbility_name = findViewById(R.id.disbility_name);
        disability_name_LL = findViewById(R.id.disability_name_LL);
        update_btn = findViewById(R.id.update_btn);
        age = findViewById(R.id.age);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);



        gender_list = new ArrayList<String>();
        gender_list.add("Select Gender");
        gender_list.add("Male");
        gender_list.add("Female");
        gender_list.add("Others");


        handicap_list = new ArrayList<String>();
        handicap_list.add("Select Disability");
        handicap_list.add("Yes");
        handicap_list.add("No");


        user = SharedPrefManager.getInstance(BasicInfoActivity.this).getUser();


        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


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

        mo_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:"+ "9420944984"));


                if (ContextCompat.checkSelfPermission(BasicInfoActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(BasicInfoActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                } else {
                    //You already have permission
                    try {
                        startActivity(it);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                    }
                }




            }
        });

        /*------current date--------*/

        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        date = format.format(todaysdate);
        System.out.println("current date==========" + date);

        /*------current date--------*/

        birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(BasicInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                birth_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                years_form_date = getDiffYears( date, birth_date.getText().toString());

                                age.setText(String.valueOf(years_form_date));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

//        gender_spinner.setEnabled(false);



        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _first_name= first_name.getText().toString().trim();
                String _middle_name= middle_name.getText().toString().trim();
                String _last_name= last_name.getText().toString().trim();

                String _gender= gender_str;
                String _birthdate = birth_date.getText().toString();
                String _email= email_id.getText().toString().trim();
                String _alternate_email= alt_email_id.getText().toString().trim();
                String _mobile= mobile.getText().toString().trim();
                String _whatapp_mobile= whatapp_no.getText().toString().trim();
                String _disbility_name= disbility_name.getText().toString();
                String _age= age.getText().toString();


                if(allDone()) {


                    if (profileCreatedList_str != null && !profileCreatedList_str.contentEquals("")) {
                        for (int i = 0; i < profileCreatedListData.size(); i++) {
                            if (profileCreatedList_str.equals(profileCreatedListData.get(i).getName())) {
                                profileCreatedList_id = String.valueOf(profileCreatedListData.get(i).getId());
                            }
                        }
                    }else{
                        profileCreatedList_id = "";
                    }
                    if (profileCreatedList_str != null && !profileCreatedList_str.contentEquals("")) {
                        for (int i = 0; i < profileCreatedListData.size(); i++) {
                            if (profileCreatedList_str.equals(profileCreatedListData.get(i).getName())) {
                                profileCreatedList_id = String.valueOf(profileCreatedListData.get(i).getId());
                            }
                        }
                    }else{
                        profileCreatedList_id = "";
                    }

                    if(gender_str.equals("Select Gender")){
                        gender_str ="";
                    }

                    if(height_str.equals("Select Height")){
                        height_str ="";
                    }
//                    if (height_str != null && height_str.equals("Select Height")) {
//                        height_str = "";
//                    }

                    if(weight_str.equals("Select Weight")){
                        weight_str ="";
                    }
                    if(handicap_str.equals("Select Disability")){
                        handicap_str ="";
                    }



//                Intent i = new Intent(getActivity(), AccomodationDetailActivity.class);
//                startActivity(i);

                    if (checkValidation()) {

                        if (!isNetworkAvailable(BasicInfoActivity.this)) {

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

                            updateData(_first_name, _middle_name, _last_name, profileCreatedList_id, _gender, _age, _birthdate, _email, _alternate_email, _mobile, _whatapp_mobile,
                                    handicap_str, _disbility_name, height_str, weight_str);


                        }


                    }
                }



            }
        });

        fetchBasicInformation(String.valueOf(user.getUser_id()));

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }


    public static int getDiffYears(String first, String last) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Calendar a= null;
        Calendar b = null;
        try {

            a = getCalendar(format.parse(first));

            b = getCalendar(format.parse(last));

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        int diff = b.get(YEAR) - a.get(YEAR);
        int diff = a.get(YEAR) - b.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }


    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    @Override
    public void networkAvailable() {

//        fetchBasicInformation(String.valueOf(user.getUser_id()));



        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchBasicInformation(String.valueOf(user.getUser_id()));

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

                Toast.makeText(BasicInfoActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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


    public void updateData(String _first_name,String _middle_name,String _last_name,String profileCreatedList_id,String _gender, String _age,String _birthdate,String _email,String _alternate_email,String _mobile,
                           String _whatapp_mobile,String handicap_str,String _disbility_name,String height_str,String weight_str){

        progressBar = ProgressDialog.show(BasicInfoActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editBasicInfo(_first_name, _middle_name,_last_name,String.valueOf(user.getUser_id()),profileCreatedList_id,
                _gender,_age,_birthdate,_email,_alternate_email,_mobile,_whatapp_mobile,handicap_str,_disbility_name,
                weight_str,height_str);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {

                    String success = edit_response.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(BasicInfoActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                        Intent i= new Intent(BasicInfoActivity.this,MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(BasicInfoActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err height******" + t.toString());

                if(!isNetworkAvailable(BasicInfoActivity.this)){
//                    Toast.makeText(BasicInfoActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(BasicInfoActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    public  void fetchBasicInformation(String user_id){

        Api apiService = RetrofitClient.getApiService();
        Call<FetchBasicInfo> userResponse = apiService.fetchBasicInfo(user_id);
        userResponse.enqueue(new Callback<FetchBasicInfo>() {

            @Override
            public void onResponse(Call<FetchBasicInfo> call, Response<FetchBasicInfo> response) {
                swipeRefreshLayout.setRefreshing(false);
                fetchBasicInfo = response.body();

                if (response.isSuccessful()) {




                    String success = fetchBasicInfo.getResid();

                    if (success.equals("200")) {


//                        update_btn.setVisibility(View.VISIBLE);

                        getProfileCreatedList(fetchBasicInfo);
                        getGenderList(fetchBasicInfo);
                        getHandicapList(fetchBasicInfo);
                        getHeightList(fetchBasicInfo);
                        getWeightList(fetchBasicInfo);


                        if(fetchBasicInfo.getDisable_name()!=null) {
                            if (!fetchBasicInfo.getDisable_name().equals("")) {
                                disability_name_LL.setVisibility(View.VISIBLE);
                                disbility_name.setText(fetchBasicInfo.getDisable_name());
                            } else {
                                disability_name_LL.setVisibility(View.GONE);

                            }

                        }


                        first_name.setText(fetchBasicInfo.getFname());
                        middle_name.setText(fetchBasicInfo.getMname());
                        last_name.setText(fetchBasicInfo.getLname());
//                        age.setText(fetchBasicInfo.getAge());
                        birth_date.setText(fetchBasicInfo.getDob());





                        age.setText(String.valueOf(years_form_date));

                        years_form_date = getDiffYears( date, birth_date.getText().toString());
                        age.setText(String.valueOf(years_form_date));

                        email_id.setText(fetchBasicInfo.getEmail());
                        alt_email_id.setText(fetchBasicInfo.getAlt_email());
                        mobile.setText(fetchBasicInfo.getMobile_no());
                        whatapp_no.setText(fetchBasicInfo.getWtsapp_no());




                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<FetchBasicInfo> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("err height******" + t.toString());

                if(!isNetworkAvailable(BasicInfoActivity.this)){
//                    Toast.makeText(BasicInfoActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(BasicInfoActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public  void  getWeightList(FetchBasicInfo fetchBasicInfo){

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


                        for(int i=0;i<weightList.getWeightList().size();i++){
                            weightListData.add(new WeightList.WeightListData(weightList.getWeightList().get(i).getName()));
                        }
                        weight_spinner_data(weightListData,fetchBasicInfo);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<WeightList> call, Throwable t) {

                System.out.println("err height******" + t.toString());

                if(!isNetworkAvailable(BasicInfoActivity.this)){
//                    Toast.makeText(BasicInfoActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(BasicInfoActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void weight_spinner_data(List<WeightList.WeightListData> weightListData,FetchBasicInfo fetchBasicInfo){

        weight_list.clear();

        weight_list.add("Select Weight");

        if(weight_list.size()!=0) {
            for (int i = 0; i < weightListData.size(); i++) {

                weight_list.add(i+1 , weightListData.get(i).getName());

            }
        }
        weight_adapter = new ArrayAdapter<String>(BasicInfoActivity.this, R.layout.simple_spinner_item, weight_list);

        spinner_weight.setAdapter(weight_adapter);

        int pos;
        for(int i=0;i<weight_list.size();i++) {
            if (weight_list.get(i).contains(fetchBasicInfo.getWeight())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                spinner_weight.setSelection(pos);
            }
        }

        spinner_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                weight_str = spinner_weight.getItemAtPosition(position).toString();
                System.out.println("weight_str------" + weight_str);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    public  void  getHeightList(FetchBasicInfo fetchBasicInfo){

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


                        for(int i=0;i<heightList.getHeightList().size();i++){
                            heightListData.add(new HeightList.HeightListData(heightList.getHeightList().get(i).getName()));
                            height_str = fetchBasicInfo.getHeight(); // ✅ IMPORTANT

                        }
                        height_spinner_data(heightListData,fetchBasicInfo);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<HeightList> call, Throwable t) {

                System.out.println("err height******" + t.toString());

                if(!isNetworkAvailable(BasicInfoActivity.this)){
//                    Toast.makeText(BasicInfoActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(BasicInfoActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void height_spinner_data(List<HeightList.HeightListData> heightListData, FetchBasicInfo fetchBasicInfo) {
        height_list.clear();
        height_list.add("Select Height");

        if (heightListData != null && !heightListData.isEmpty()) {
            for (int i = 0; i < heightListData.size(); i++) {
                height_list.add(i + 1, heightListData.get(i).getName());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        height_adapter = new ArrayAdapter<>(BasicInfoActivity.this,
                R.layout.simple_spinner_item, height_list);
        height_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_height.setAdapter(height_adapter);

        // Set default selection if fetchBasicInfo.getHeight() exists
        if (fetchBasicInfo.getHeight() != null && !fetchBasicInfo.getHeight().isEmpty()) {
            for (int i = 0; i < height_list.size(); i++) {
                if (height_list.get(i).equals(fetchBasicInfo.getHeight())) {
                    spinner_height.setSelection(i);
                    System.out.println("state i-----------" + i);
                    break;
                }
            }
        }

        // Handle Spinner click to show searchable dialog
        spinner_height.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableHeightDialog(height_list);
            }
            return true; // Consume the touch event
        });
    }
    // Method to show a searchable dialog for height selection
    private void showSearchableHeightDialog(List<String> heightList) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(BasicInfoActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Initialize dialog views
        EditText searchEditText = dialogView.findViewById(R.id.search_edit_text);
        ListView listView = dialogView.findViewById(R.id.list_view);
        AlertDialog dialog = builder.create();

        // Create a copy of the original list to map filtered positions back to the original list
        List<String> originalHeightList = new ArrayList<>(heightList);

        // Set up the ListView adapter
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(
                BasicInfoActivity.this,
                android.R.layout.simple_list_item_1,
                heightList
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
                String selectedHeight = dialogAdapter.getItem(position);
                if (selectedHeight == null) {
                    Log.e("HeightDialog", "Selected height is null at position: " + position);
                    Toast.makeText(BasicInfoActivity.this, "Error selecting height. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Map the filtered item back to the original list to get the correct position
                int originalPosition = -1;
                for (int i = 0; i < originalHeightList.size(); i++) {
                    if (originalHeightList.get(i).equals(selectedHeight)) {
                        originalPosition = i;
                        break;
                    }
                }

                if (originalPosition == -1) {
                    Log.e("HeightDialog", "Could not find selected height in original list: " + selectedHeight);
                    Toast.makeText(BasicInfoActivity.this, "Error selecting height. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                height_str = selectedHeight;
                Log.d("HeightDialog", "Selected height: " + height_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                spinner_height.setSelection(originalPosition);

                // Log the selection as per the original logic
                System.out.println("height_str------" + height_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("HeightDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(BasicInfoActivity.this, "Error selecting height: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    public  void  getHandicapList(FetchBasicInfo fetchBasicInfo){

        handicap_adapter = new ArrayAdapter<String>(BasicInfoActivity.this, R.layout.simple_spinner_item, handicap_list);

        spinner_disability.setAdapter(handicap_adapter);
        int pos;
        for(int i=0;i<handicap_list.size();i++) {
            if (handicap_list.get(i).contains(fetchBasicInfo.getAny_disable())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                spinner_disability.setSelection(pos);
            }
        }


        spinner_disability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                handicap_str = spinner_disability.getItemAtPosition(position).toString();
                System.out.println("manglik_list------" + handicap_str);

                if(handicap_str.contentEquals("Yes")){
                    disability_name_LL.setVisibility(View.VISIBLE);
                }else{
                    disability_name_LL.setVisibility(View.GONE);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public  void  getGenderList(FetchBasicInfo fetchBasicInfo){

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BasicInfoActivity.this, R.layout.simple_spinner_item, gender_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(dataAdapter);

        int pos;

        if(fetchBasicInfo.getGender()!=null ){
            if(!fetchBasicInfo.getGender().equals("") ){
                for(int i=0;i<gender_list.size();i++) {
                    if (gender_list.get(i).contains(fetchBasicInfo.getGender())) {
                        pos = i;
                        System.out.println("state i-----------" + pos);
                        gender_spinner.setSelection(pos);

                        String gender_txt_check = gender_spinner.getSelectedItem().toString();
                        if(gender_txt_check.equals("Select Gender")){
                            gender_update_txt.setVisibility(View.GONE);
                        }else{
                            gender_update_txt.setVisibility(View.VISIBLE);
                            gender_spinner.setEnabled(false);
                        }

                    }else{
                        System.out.println("empty gendor=========");
                    }
                }
            }else{
                gender_update_txt.setVisibility(View.GONE);

            }
        }else{
            gender_update_txt.setVisibility(View.GONE);

        }


        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                gender_str = gender_spinner.getItemAtPosition(position).toString();

                System.out.println("gendor------" + gender_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public  void  getProfileCreatedList(FetchBasicInfo fetchBasicInfo){

        profileCreatedListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<ProfileCreatedList> userResponse = apiService.profileCreatedList();
        userResponse.enqueue(new Callback<ProfileCreatedList>() {

            @Override
            public void onResponse(Call<ProfileCreatedList> call, Response<ProfileCreatedList> response) {
                profileCreatedList = response.body();

                if (response.isSuccessful()) {


                    String success = profileCreatedList.getResid();

                    if (success.equals("200")) {


                        for(int i=0;i<profileCreatedList.getProfileCreatedList().size();i++){
                            profileCreatedListData.add(new ProfileCreatedList.ProfileCreatedListData(profileCreatedList.getProfileCreatedList().get(i).getId(),profileCreatedList.getProfileCreatedList().get(i).getName()));

                        }
                        profilecreated_spinner_data(profileCreatedListData,fetchBasicInfo);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileCreatedList> call, Throwable t) {

                System.out.println("err profile created for******" + t.toString());

                if(!isNetworkAvailable(BasicInfoActivity.this)){
//                    Toast.makeText(BasicInfoActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(BasicInfoActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void profilecreated_spinner_data(List<ProfileCreatedList.ProfileCreatedListData> profileCreatedListData,FetchBasicInfo fetchBasicInfo){

        profileCreatedList_list.clear();

        profileCreatedList_list.add("Select Profile For");

        if(profileCreatedList_list.size()!=0) {
            for (int i = 0; i <profileCreatedListData.size(); i++) {

                profileCreatedList_list.add(i+1 , profileCreatedListData.get(i).getName());

            }
        }
        adapter_profilecreatedfor = new ArrayAdapter<String>(BasicInfoActivity.this, R.layout.simple_spinner_item, profileCreatedList_list);

        spinner_profile_created_by.setAdapter(adapter_profilecreatedfor);



        int pos;
        for(int i=0;i<profileCreatedList_list.size();i++) {
            if (profileCreatedList_list.get(i).contains(fetchBasicInfo.getProfilefor_name())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                spinner_profile_created_by.setSelection(pos);
            }
        }

        spinner_profile_created_by.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                profileCreatedList_str = spinner_profile_created_by.getItemAtPosition(position).toString();
                System.out.println("profileCreatedList_str------" + profileCreatedList_str);

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



    private Boolean allDone(){

        if(first_name==null){
            return false;
        }

        if(middle_name==null){
            return false;
        }
        if(last_name==null){
            return false;
        }

        if(age==null){
            return false;
        }
        if(birth_date==null){
            return false;
        }

        if(email_id==null){
            return false;
        }
        if(alt_email_id==null){
            return false;
        }
        if(mobile==null){
            return false;
        }
        if(whatapp_no==null){
            return false;
        }


        View selectedView_profile_created11 = spinner_profile_created_by.getSelectedView();
        View selectedView_gender1 = gender_spinner.getSelectedView();
        View selectedView_disability11 = spinner_disability.getSelectedView();
        View selectedView_height11 = spinner_height.getSelectedView();
        View selectedView_weight11 = spinner_weight.getSelectedView();


        if(selectedView_profile_created11==null ){
            return false;
        }

        if(  selectedView_gender1==null){
            return false;
        }

        if( selectedView_disability11==null ){
            return false;
        }

        if(selectedView_height11==null){
            return false;
        }
        if(selectedView_weight11==null){
            return false;
        }


        return true;
    }



    private Boolean checkValidation(){


        if (first_name.getText().toString().isEmpty()) {
            first_name.setError("First name is required");
            first_name.requestFocus();
            return  false;
        }


        if (middle_name.getText().toString().isEmpty()) {
            middle_name.setError("Middle name is required");
            middle_name.requestFocus();
            return  false;
        }

        if (last_name.getText().toString().isEmpty()) {
            last_name.setError("Last name is required");
            last_name.requestFocus();
            return  false;
        }



        View selectedView_gen = gender_spinner.getSelectedView();
        if (selectedView_gen != null && selectedView_gen instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_gen;
            if (selectedTextView.getText().equals("Select Gender")) {
                selectedTextView.setError("Please Select Gender");
                gender_spinner.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return  false;
            }

        }


       /* if (age.getText().toString().isEmpty()) {
            age.setError("Age is required");
            age.requestFocus();
            return  false;
        }*/

      /*  if (birth_date.getText().toString().isEmpty()) {
            birth_date.setError("Birth date is required");
            birth_date.requestFocus();
            return  false;
        }*/

        if (birth_date.getText().toString().isEmpty()) {
            birth_date.setError("Birth date is required");
            birth_date.requestFocus();
            return  false;
        }else if(years_form_date < 18){
            birth_date.setError("Minimum age should be 18 !");
            Toast.makeText(BasicInfoActivity.this,"Minimum age should be 18 !",Toast.LENGTH_SHORT).show();
            birth_date.requestFocus();
            return  false;
        }
        if (email_id.getText().toString().isEmpty()) {
            email_id.setError("Please enter Email ID");
            email_id.requestFocus();
            return  false;
        } else{

            if (email_id.getText().toString().matches(emailPattern)){

            }
            else
            {
                email_id.setError("Email is not valid!");
                email_id.requestFocus();
                return  false;
            }
        }





      /*  if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please enter mobile number");
            mobile.requestFocus();
            return  false;
        } else if (mobile.getText().toString().length()!=10) {
            mobile.setError("Mobile number should be 10 digit");
            mobile.requestFocus();
            return  false;
        }*/

        if (whatapp_no.getText().toString().isEmpty()) {
            whatapp_no.setError("Please enter mobile number");
            whatapp_no.requestFocus();
            return  false;
        } else if (whatapp_no.getText().toString().length()!=10) {
            whatapp_no.setError("Mobile number should be 10 digit");
            whatapp_no.requestFocus();
            return  false;
        }


        return true;
    }



}
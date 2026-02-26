package com.WadhuWarProject.WadhuWar.activity;

import android.Manifest;
import android.app.AlertDialog;
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
import androidx.core.app.ActivityCompat;
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
import com.WadhuWarProject.WadhuWar.model.FetchAboutMe;
import com.WadhuWarProject.WadhuWar.model.FetchReligion;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MothertoungeList;
import com.WadhuWarProject.WadhuWar.model.ReligionList;
import com.WadhuWarProject.WadhuWar.model.SampradayList;
import com.WadhuWarProject.WadhuWar.model.SubCasteList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class ReligiousBackgroundActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    TextView religion_update_txt,caste_update_txt;
    private NetworkStateReceiver networkStateReceiver;
    UserData user;
    SwipeRefreshLayout swipeRefreshLayout;
    Spinner religion_spinner,mother_tounge_spinner,caste_spinner,sub_caste_spinner,sampraday_spinner,rashi_spinner;
    EditText gotra;
    LinearLayout update_btn;
    static boolean isNetworkAvailable;

    FetchReligion fetchReligion;
    ArrayList<ReligionList.ReligionListData> religionListData =  new ArrayList<>();
    ReligionList religionList;
    ArrayList<CasteList.CasteListData> casteListData =  new ArrayList<>();
    CasteList casteList;
    ArrayList<SubCasteList> subCasteLists =  new ArrayList<>();
    ArrayList<MothertoungeList.MothertoungeListData> mothertoungeListData =  new ArrayList<>();
    MothertoungeList mothertoungeList;
    ArrayList<SampradayList.SampradayListData> sampradayListData = new ArrayList<>();
    SampradayList sampradayList;


    Toolbar toolbar;


    List<String> religion_list = new ArrayList<String>();
    List<String> caste_list = new ArrayList<String>();
    List<String> sub_caste_list = new ArrayList<String>();
    List<String> mother_tongue_list = new ArrayList<String>();
    List<String> sampraday_list = new ArrayList<String>();
    List<String> rashi_list;

    ArrayAdapter<String> caste_adapter;
    ArrayAdapter<String> sub_caste_adapter;
    ArrayAdapter<String> adapter_religion;
    ArrayAdapter<String> mother_tongue_adapter;
    ArrayAdapter<String> adapter_sampraday;

    String religion_list_str,caste_list_str,sub_caste_str,mother_tongue_str,sampraday_list_str,rashi_str;

    String caste_id,religion_id,subcaste_id,mother_tongue_id,sampraday_id;

    ProgressDialog progressBar;
    InsertResponse edit_response;
    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
    LinearLayout mo_num;

    ProgressBar prog_caste;

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
        setContentView(R.layout.activity_religious_background);

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
//        setContentView(R.layout.activity_religious_background);

        rashi_spinner = findViewById(R.id.rashi_spinner);
        sampraday_spinner = findViewById(R.id.sampraday_spinner);
//        caste_update_txt =  findViewById(R.id.caste_update_txt);                                  slash by tanmay
        religion_update_txt =  findViewById(R.id.religion_update_txt);
        mo_num =  findViewById(R.id.mo_num);
        prog_caste =  findViewById(R.id.prog_caste);
        gotra =  findViewById(R.id.gotra);
        sub_caste_spinner =  findViewById(R.id.sub_caste_spinner);
        caste_spinner =  findViewById(R.id.caste_spinner);
        mother_tounge_spinner =  findViewById(R.id.mother_tounge_spinner);
        religion_spinner =  findViewById(R.id.religion_spinner);
        swipeRefreshLayout =  findViewById(R.id.swipeRefreshLayout);
        update_btn =  findViewById(R.id.update_btn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


        user = SharedPrefManager.getInstance(ReligiousBackgroundActivity.this).getUser();


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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ReligiousBackgroundActivity.this, android.R.layout.simple_spinner_item, rashi_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rashi_spinner.setAdapter(dataAdapter);


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


      /*  View selectedView_religion = religion_spinner.getSelectedView();
        View selectedView_mother_tounge = mother_tounge_spinner.getSelectedView();
        View selectedView_caste = caste_spinner.getSelectedView();
        View selectedView_subcaste = sub_caste_spinner.getSelectedView();

        if(selectedView_religion!=null &&  selectedView_mother_tounge!=null &&  selectedView_caste!=null && selectedView_subcaste!=null){
            update_btn.setVisibility(View.VISIBLE);

        }else{
            update_btn.setVisibility(View.GONE);

        }*/

//        religion_spinner.setEnabled(false);
//        caste_spinner.setEnabled(false);


        mo_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:"+ "8698690871"));


                if (ContextCompat.checkSelfPermission(ReligiousBackgroundActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ReligiousBackgroundActivity.this,
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



        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _gotra = gotra.getText().toString();

                if(allDone()) {
                    if (checkValidation()) {

                        if (!isNetworkAvailable(ReligiousBackgroundActivity.this)) {

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



                            System.out.println("Cast id===========" + caste_id);
                            System.out.println("subcaste_id id===========" + subcaste_id);

                            updateData(religion_id, caste_id, subcaste_id, mother_tongue_id, _gotra,sampraday_id,rashi_str);


                        }


                    }
                }


            }
        });

        fetchReligionData();
        getSampradayList();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }



    public void updateData( String religion_id, String caste_id, String subcaste_id, String mother_tongue_id, String _gotra,String sampraday_id,String rashi_str){

        progressBar = ProgressDialog.show(ReligiousBackgroundActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editReligion(String.valueOf(user.getUser_id()),"religious",religion_id,caste_id,subcaste_id,mother_tongue_id,_gotra,sampraday_id,rashi_str);
        userResponse.enqueue(new Callback<InsertResponse>() {


            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {

                    String success = edit_response.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(ReligiousBackgroundActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                        Intent i= new Intent(ReligiousBackgroundActivity.this,MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(ReligiousBackgroundActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err religion edit******" + t.toString());

                if(!isNetworkAvailable(ReligiousBackgroundActivity.this)){
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    private Boolean allDone(){

        View selectedView_religion11 = religion_spinner.getSelectedView();
        View selectedView_mother_tounge11 = mother_tounge_spinner.getSelectedView();
        View selectedView_caste11 = caste_spinner.getSelectedView();
        View selectedView_subcaste11 = sub_caste_spinner.getSelectedView();


        if(selectedView_religion11==null ){
            return false;
        }

        if(  selectedView_mother_tounge11==null){
            return false;
        }

        if( selectedView_caste11==null ){
            return false;
        }

        if(selectedView_subcaste11==null){
            return false;
        }


        return true;
    }


    private Boolean checkValidation(){


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

        View selectedView_mother_tongue = mother_tounge_spinner.getSelectedView();
        if (selectedView_mother_tongue != null && selectedView_mother_tongue instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_mother_tongue;
            if (selectedTextView.getText().equals("Select Mother Tounge")) {
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



        return true;
    }




    @Override
    public void networkAvailable() {
//        fetchReligionData();



        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                casteListData.clear();
                subCasteLists.clear();
//                getReligionList();
//                getMotherTongueList();
                fetchReligionData();
                getSampradayList();

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

                Toast.makeText(ReligiousBackgroundActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

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

    public  void fetchReligionData(){

        System.out.println("user_id111=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchReligion> userResponse = apiService.fetchReligion(String.valueOf(user.getUser_id()),"religious");
        userResponse.enqueue(new Callback<FetchReligion>() {

            @Override
            public void onResponse(Call<FetchReligion> call, Response<FetchReligion> response) {

                swipeRefreshLayout.setRefreshing(false);
                fetchReligion = response.body();


                /*System.out.println("fetme -------" + new Gson().toJson(fetchReligion));*/

                if (response.isSuccessful()) {

//                    update_btn.setVisibility(View.VISIBLE);

                    gotra.setText(fetchReligion.getGothra());

                    System.out.println("getReligion_name=======" + fetchReligion.getReligion_name());
                    System.out.println("getMothertounge_name=======" + fetchReligion.getMothertounge_name());
                    System.out.println("getCaste_name=======" + fetchReligion.getMothertounge_name());


                    getReligionList(fetchReligion);
                    getMotherTongueList(fetchReligion);


                }

            }

            @Override
            public void onFailure(Call<FetchReligion> call, Throwable t) {
                System.out.println("msg1 error fetch about me******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public  void  getReligionList(FetchReligion fetchReligion){

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


                        for(int i=0;i<religionList.getReligionList().size();i++){
                            religionListData.add(new ReligionList.ReligionListData(religionList.getReligionList().get(i).getId(), religionList.getReligionList().get(i).getName()));

                        }
                        religion_spinner_data(religionListData,fetchReligion);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ReligionList> call, Throwable t) {

                System.out.println("err rel******" + t.toString());
                if(!isNetworkAvailable(ReligiousBackgroundActivity.this)){
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void religion_spinner_data(List<ReligionList.ReligionListData> religionListData,FetchReligion fetchReligion){

        religion_list.clear();

        religion_list.add("Select Religion");

        if(religion_list.size()!=0) {
            for (int i = 0; i <religionListData.size(); i++) {

                religion_list.add(i+1 , religionListData.get(i).getName());

            }
        }


        adapter_religion = new ArrayAdapter<String>(ReligiousBackgroundActivity.this, R.layout.simple_spinner_item, religion_list);

        religion_spinner.setAdapter(adapter_religion);

        int pos;
        /*for(int i=0;i<religion_list.size();i++) {
            if (religion_list.get(i).contains(fetchReligion.getReligion_name())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                religion_spinner.setSelection(pos);
            }
        }*/


        /*===========*/
        if(fetchReligion.getReligion_name()!=null ){
            if(!fetchReligion.getReligion_name().equals("") ){
                for(int i=0;i<religion_list.size();i++) {
                    if (religion_list.get(i).contains(fetchReligion.getReligion_name())) {
                        pos = i;
                        System.out.println("state i-----------" + pos);
                        religion_spinner.setSelection(pos);

                        String gender_txt_check = religion_spinner.getSelectedItem().toString();
                        if(gender_txt_check.equals("Select Religion")){
                            religion_update_txt.setVisibility(View.GONE);
                        }else{
//                            religion_update_txt.setVisibility(View.VISIBLE);
                            religion_spinner.setEnabled(false);
                        }

                    }else{
                        System.out.println("empty religion=========");
                    }
                }
            }else{
                religion_update_txt.setVisibility(View.GONE);

            }
        }else{
            religion_update_txt.setVisibility(View.GONE);

        }

        /*===========*/



        religion_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                religion_list_str = religion_spinner.getItemAtPosition(position).toString();
                System.out.println("religion_list_str------" + religion_list_str);

                getCastList(religion_list_str,fetchReligion);

//                for_state_spinner_data(religion_list_str);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public  void  getCastList(String religion_list_str,FetchReligion fetchReligion ){


        if (religion_list_str != null) {
            for (int i = 0; i < religionListData.size(); i++) {
                if (religion_list_str.equals(religionListData.get(i).getName())) {
                    religion_id = String.valueOf(religionListData.get(i).getId());

                }
            }
        }

        System.out.println("religion_id--------" +  religion_id);

        casteListData.clear();
        prog_caste.setVisibility(View.VISIBLE);
        Api apiService = RetrofitClient.getApiService();
        Call<CasteList> userResponse = apiService.casteList("06",religion_id);
        userResponse.enqueue(new Callback<CasteList>() {

            @Override
            public void onResponse(Call<CasteList> call, Response<CasteList> response) {
                casteList = response.body();
                prog_caste.setVisibility(View.GONE);

                if (response.isSuccessful()) {


                    String success = casteList.getResid();

                    if (success.equals("200")) {


                        for(int i=0;i<casteList.getCasteList().size();i++){
                            casteListData.add(new CasteList.CasteListData(casteList.getCasteList().get(i).getId(),casteList.getCasteList().get(i).getName(),casteList.getCasteList().get(i).getSubCasteList()));
                        }
                        caste_spinner_data(casteListData,fetchReligion);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<CasteList> call, Throwable t) {
                prog_caste.setVisibility(View.GONE);

                System.out.println("err caste******" + t.toString());
                if(!isNetworkAvailable(ReligiousBackgroundActivity.this)){
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void caste_spinner_data(List<CasteList.CasteListData> casteListData, FetchReligion fetchReligion) {
        caste_list.clear();
        caste_list.add("Select Caste");

        if (casteListData != null && !casteListData.isEmpty()) {
            for (int i = 0; i < casteListData.size(); i++) {
                caste_list.add(casteListData.get(i).getName());
            }
        }

        caste_adapter = new ArrayAdapter<>(ReligiousBackgroundActivity.this,
                R.layout.simple_spinner_item, caste_list);
        caste_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caste_spinner.setAdapter(caste_adapter);

        // Set default selection and populate sub-caste
        if (fetchReligion != null && fetchReligion.getCaste_name() != null
                && !fetchReligion.getCaste_name().isEmpty()) {
            for (int i = 0; i < caste_list.size(); i++) {
                if (caste_list.get(i).equals(fetchReligion.getCaste_name())) {
                    caste_spinner.setSelection(i);
                    caste_list_str = fetchReligion.getCaste_name();

                    // Now populate sub-caste for this selected caste
                    for_caste_spinner_data(caste_list_str, fetchReligion);

                    String gender_txt_check = caste_spinner.getSelectedItem().toString();
                    if (!gender_txt_check.equals("Select Caste")) {
                        caste_spinner.setEnabled(false);
                    } else {
                        // caste_update_txt.setVisibility(View.GONE);
                    }
                    break;
                }
            }
        } else {
            // caste_update_txt.setVisibility(View.GONE);
        }

        // Handle Spinner click
        caste_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableCasteDialog(caste_list, fetchReligion);
            }
            return true;
        });
    }
    // new implementation for search bar casr 27-5-2025
//    public void caste_spinner_data(List<CasteList.CasteListData> casteListData, FetchReligion fetchReligion) {
//        caste_list.clear();
//        caste_list.add("Select Caste");
//
//        if (casteListData != null && !casteListData.isEmpty()) {
//            for (int i = 0; i < casteListData.size(); i++) {
//                caste_list.add(casteListData.get(i).getName());
//            }
//        }
//
//        // Set up the ArrayAdapter for the Spinner
//        caste_adapter = new ArrayAdapter<>(ReligiousBackgroundActivity.this,
//                R.layout.simple_spinner_item, caste_list);
//        caste_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        caste_spinner.setAdapter(caste_adapter);
//
//        // Set default selection if caste_name exists
//        if (fetchReligion.getCaste_name() != null && !fetchReligion.getCaste_name().isEmpty()) {
//            for (int i = 0; i < caste_list.size(); i++) {
//                if (caste_list.get(i).equals(fetchReligion.getCaste_name())) {
//                    caste_spinner.setSelection(i);
//                    String gender_txt_check = caste_spinner.getSelectedItem().toString();
//                    if (!gender_txt_check.equals("Select Caste")) {
//                        // caste_update_txt.setVisibility(View.VISIBLE); // Uncomment if needed
//                        caste_spinner.setEnabled(false);
//                    } else {
//                        caste_update_txt.setVisibility(View.GONE);
//                    }
//                    break;
//                }
//            }
//        } else {
//            caste_update_txt.setVisibility(View.GONE);
//        }
//
//        // Handle Spinner click to show searchable dialog
//        caste_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableCasteDialog(caste_list, fetchReligion);
//            }
//            return true; // Consume the touch event
//        });
//    }
    // Method to show a searchable dialog for caste selection
    private void showSearchableCasteDialog(List<String> casteList, FetchReligion fetchReligion) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ReligiousBackgroundActivity.this);
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
                ReligiousBackgroundActivity.this,
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
                    Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting caste. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                caste_list_str = selectedCaste;
                Log.d("CasteDialog", "Selected caste: " + caste_list_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                caste_spinner.setSelection(originalPosition);

                // Call existing method to handle backend logic
                for_caste_spinner_data(caste_list_str, fetchReligion);

                // Update UI based on selection
                if (!selectedCaste.equals("Select Caste")) {
                    // caste_update_txt.setVisibility(View.VISIBLE); // Uncomment if needed
                    caste_spinner.setEnabled(false);
                } else {
//                    caste_update_txt.setVisibility(View.GONE);
                }

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("CasteDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting caste: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
//    public void caste_spinner_data(List<CasteList.CasteListData> casteListData,FetchReligion fetchReligion){
//
//        caste_list.clear();
//
//        caste_list.add("Select Caste");
//
//        if(caste_list.size()!=0) {
//            for (int i = 0; i <casteListData.size(); i++) {
//
//                caste_list.add(i+1 , casteListData.get(i).getName());
//
//            }
//        }
//
//
//        caste_adapter = new ArrayAdapter<String>(ReligiousBackgroundActivity.this, R.layout.simple_spinner_item, caste_list);
//
//        caste_spinner.setAdapter(caste_adapter);
//
//        int pos;
//        /*for(int i=0;i<caste_list.size();i++) {
//            if (caste_list.get(i).contains(fetchReligion.getCaste_name())) {
//                pos = i;
//                System.out.println("state i-----------" + pos);
//                caste_spinner.setSelection(pos);
//            }
//        }*/
//
//        /*===========*/
//        if(fetchReligion.getCaste_name()!=null ){
//            if(!fetchReligion.getCaste_name().equals("") ){
//                for(int i=0;i<caste_list.size();i++) {
//                    if (caste_list.get(i).contains(fetchReligion.getCaste_name())) {
//                        pos = i;
//                        System.out.println("state i-----------" + pos);
//                        caste_spinner.setSelection(pos);
//
//                        String gender_txt_check = caste_spinner.getSelectedItem().toString();
//                        if(gender_txt_check.equals("Select Caste")){
//                            caste_update_txt.setVisibility(View.GONE);
//                        }else{
    ////                            caste_update_txt.setVisibility(View.VISIBLE);
//                            caste_spinner.setEnabled(false);
//                        }
//
//                    }else{
//                        System.out.println("empty religion=========");
//                    }
//                }
//            }else{
//                caste_update_txt.setVisibility(View.GONE);
//
//            }
//        }else{
//            caste_update_txt.setVisibility(View.GONE);
//
//        }
//
//        /*===========*/
//
//        caste_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                caste_list_str = caste_spinner.getItemAtPosition(position).toString();
//                System.out.println("caste_list_str------" + caste_list_str);
//
//                for_caste_spinner_data(caste_list_str,fetchReligion);
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


    public void for_caste_spinner_data(String caste_list_str, FetchReligion fetchReligion){
        subCasteLists.clear();

        // Get caste_id from selected caste
        if (caste_list_str != null) {
            for (int i = 0; i < casteListData.size(); i++) {
                if (caste_list_str.equals(casteListData.get(i).getName())) {
                    caste_id = String.valueOf(casteListData.get(i).getId());
                    break;
                }
            }
        }

        // Populate sub-caste list based on caste_id
        if (caste_id != null && !caste_id.isEmpty()) {
            for (int i = 0; i < casteListData.size(); i++) {
                if (String.valueOf(casteListData.get(i).getId()).equals(caste_id)) {
                    if (casteListData.get(i).getSubCasteList() != null) {
                        subCasteLists.addAll(casteListData.get(i).getSubCasteList());
                    }
                    break;
                }
            }
        }

        // Update the sub-caste spinner
        subCaste_spinner_data(subCasteLists, fetchReligion);
    }
//    public void for_caste_spinner_data(String caste_list_str,FetchReligion fetchReligion){
//        subCasteLists.clear();
//        /*---------*/
//
//        if (caste_list_str != null) {
//            for (int i = 0; i < casteListData.size(); i++) {
//                if (caste_list_str.equals(casteListData.get(i).getName())) {
//                    caste_id = String.valueOf(casteListData.get(i).getId());
//
//
//                }
//            }
//        }
//
//        for(int i=0;i<casteListData.size();i++){
//            if( casteListData.get(i).getSubCasteList()!=null) {
//
//                for (int j = 0; j < casteListData.get(i).getSubCasteList().size(); j++) {
//
//
//                    if(caste_id!=null){
//
//                        if(caste_id.contentEquals(casteListData.get(i).getSubCasteList().get(j).getC_id())) {
//                            subCasteLists.add(new SubCasteList(casteListData.get(i).getSubCasteList().get(j).getC_id(), casteListData.get(i).getSubCasteList().get(j).getSc_id(),casteListData.get(i).getSubCasteList().get(j).getSc_name()));
//                        }
//
//                    }
//
//
//                }
//                subCaste_spinner_data(subCasteLists,fetchReligion);
//            }
//        }
//    }

    public void subCaste_spinner_data(List<SubCasteList> subCasteLists, FetchReligion fetchReligion) {
        sub_caste_list.clear();
        sub_caste_list.add("Select Sub-Caste");

        if (subCasteLists != null && !subCasteLists.isEmpty()) {
            for (int i = 0; i < subCasteLists.size(); i++) {
                sub_caste_list.add(subCasteLists.get(i).getSc_name());
            }
        }

        sub_caste_adapter = new ArrayAdapter<>(ReligiousBackgroundActivity.this,
                R.layout.simple_spinner_item, sub_caste_list);
        sub_caste_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_caste_spinner.setAdapter(sub_caste_adapter);

        // Set default selection if subcaste_name exists
        if (fetchReligion != null && fetchReligion.getSubcaste_name() != null
                && !fetchReligion.getSubcaste_name().isEmpty()) {
            // Find the position of the subcaste
            for (int i = 0; i < sub_caste_list.size(); i++) {
                if (sub_caste_list.get(i).equals(fetchReligion.getSubcaste_name())) {
                    sub_caste_spinner.setSelection(i);
                    sub_caste_str = fetchReligion.getSubcaste_name(); // Set the string value
                    break;
                }
            }
        }

        // Handle Spinner click
        sub_caste_spinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchableSubCasteDialog(sub_caste_list, fetchReligion);
            }
            return true;
        });
    }
    //    public void subCaste_spinner_data(List<SubCasteList> subCasteLists, FetchReligion fetchReligion) {
//        sub_caste_list.clear();
//        sub_caste_list.add("Select Sub-Caste");
//
//        if (subCasteLists != null && !subCasteLists.isEmpty()) {
//            for (int i = 0; i < subCasteLists.size(); i++) {
//                sub_caste_list.add(subCasteLists.get(i).getSc_name());
//            }
//        }
//
//        // Set up the ArrayAdapter for the Spinner
//        sub_caste_adapter = new ArrayAdapter<>(ReligiousBackgroundActivity.this,
//                R.layout.simple_spinner_item, sub_caste_list);
//        sub_caste_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sub_caste_spinner.setAdapter(sub_caste_adapter);
//
//        // Set default selection if subcaste_name exists
//        if (fetchReligion.getSubcaste_name() != null && !fetchReligion.getSubcaste_name().isEmpty()) {
//            for (int i = 0; i < sub_caste_list.size(); i++) {
//                if (sub_caste_list.get(i).equals(fetchReligion.getSubcaste_name())) {
//                    sub_caste_spinner.setSelection(i);
//                    break;
//                }
//            }
//        }
//
//        // Handle Spinner click to show searchable dialog
//        sub_caste_spinner.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                showSearchableSubCasteDialog(sub_caste_list, fetchReligion);
//            }
//            return true; // Consume the touch event
//        });
//    }
    // Method to show a searchable dialog for sub-caste selection
    private void showSearchableSubCasteDialog(List<String> subCasteList, FetchReligion fetchReligion) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ReligiousBackgroundActivity.this);
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
                ReligiousBackgroundActivity.this,
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
                    Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting sub-caste. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting sub-caste. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                sub_caste_str = selectedSubCaste;
                Log.d("SubCasteDialog", "Selected sub-caste: " + sub_caste_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                sub_caste_spinner.setSelection(originalPosition);

                // Log the selection (no further backend logic is specified in the original method)
                System.out.println("sub_caste_str------" + sub_caste_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("SubCasteDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting sub-caste: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

//    public void subCaste_spinner_data(List<SubCasteList> subCasteLists,FetchReligion caste_list){
//
//        sub_caste_list.clear();
//
//        sub_caste_list.add("Select Sub-Caste");
//
//        if(sub_caste_list.size()!=0) {
//            for (int i = 0; i <subCasteLists.size(); i++) {
//
//                sub_caste_list.add(i+1 , subCasteLists.get(i).getSc_name());
//
//            }
//        }
//
//
//
//        sub_caste_adapter = new ArrayAdapter<String>(ReligiousBackgroundActivity.this, R.layout.simple_spinner_item, sub_caste_list);
//
//        sub_caste_spinner.setAdapter(sub_caste_adapter);
//
//        int pos;
//        for(int i=0;i<sub_caste_list.size();i++) {
//            if (sub_caste_list.get(i).contains(fetchReligion.getSubcaste_name())) {
//                pos = i;
//                System.out.println("state i-----------" + pos);
//                sub_caste_spinner.setSelection(pos);
//            }
//        }
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


    public  void  getMotherTongueList(FetchReligion fetchReligion ){


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


                        for(int i=0;i<mothertoungeList.getMothertoungeList().size();i++){
                            mothertoungeListData.add(new MothertoungeList.MothertoungeListData(mothertoungeList.getMothertoungeList().get(i).getId(),mothertoungeList.getMothertoungeList().get(i).getName()));
                        }
                        mother_tongue_spinner_data(mothertoungeListData,fetchReligion);
                    }else {

                    }
                }

            }

            @Override
            public void onFailure(Call<MothertoungeList> call, Throwable t) {

                System.out.println("err mother tongue******" + t.toString());
                if(!isNetworkAvailable(ReligiousBackgroundActivity.this)){
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void mother_tongue_spinner_data(List<MothertoungeList.MothertoungeListData> mothertoungeListData, FetchReligion fetchReligion) {
        mother_tongue_list.clear();
        mother_tongue_list.add("Select Mother Tounge");

        if (mothertoungeListData != null && !mothertoungeListData.isEmpty()) {
            for (int i = 0; i < mothertoungeListData.size(); i++) {
                mother_tongue_list.add(i + 1, mothertoungeListData.get(i).getName());
            }
        }

        // Set up the ArrayAdapter for the Spinner
        mother_tongue_adapter = new ArrayAdapter<>(ReligiousBackgroundActivity.this,
                R.layout.simple_spinner_item, mother_tongue_list);
        mother_tongue_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mother_tounge_spinner.setAdapter(mother_tongue_adapter);

        // Set default selection if fetchReligion.getMothertounge_name() exists
        if (fetchReligion.getMothertounge_name() != null && !fetchReligion.getMothertounge_name().isEmpty()) {
            for (int i = 0; i < mother_tongue_list.size(); i++) {
                if (mother_tongue_list.get(i).equals(fetchReligion.getMothertounge_name())) {
                    mother_tounge_spinner.setSelection(i);
                    break;
                }
            }
        }

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
        AlertDialog.Builder builder = new AlertDialog.Builder(ReligiousBackgroundActivity.this);
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
                ReligiousBackgroundActivity.this,
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
                    Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting mother tongue. Please try again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting mother tongue. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mother_tongue_str = selectedMotherTongue;
                Log.d("MotherTongueDialog", "Selected mother tongue: " + mother_tongue_str + ", Original position: " + originalPosition);

                // Update Spinner selection
                mother_tounge_spinner.setSelection(originalPosition);

                // Log the selection as per the original logic
                System.out.println("mother_tongue_str------" + mother_tongue_str);

                dialog.dismiss();
            } catch (Exception e) {
                Log.e("MotherTongueDialog", "Error in item selection: " + e.getMessage(), e);
                Toast.makeText(ReligiousBackgroundActivity.this, "Error selecting mother tongue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

                if (!isNetworkAvailable(ReligiousBackgroundActivity.this)) {
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(ReligiousBackgroundActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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
        adapter_sampraday = new ArrayAdapter<String>(ReligiousBackgroundActivity.this, R.layout.simple_spinner_item, sampraday_list);

        sampraday_spinner.setAdapter(adapter_sampraday);



        if(fetchReligion!=null){
            System.out.println("fetchReligion.getRashi().trim()===========" + fetchReligion.getRashi().trim());

            if(fetchReligion.getRashi().trim()!=null && !fetchReligion.getRashi().trim().equals("")) {
                int pos1;
                for (int i = 0; i < rashi_list.size(); i++) {
                    if (rashi_list.get(i).trim().contains(fetchReligion.getRashi().trim())) {
                        pos1 = i;
                        rashi_spinner.setSelection(pos1);
                    }
                }
            }


            int pos;
            for(int i=0;i<sampraday_list.size();i++) {
                if (sampraday_list.get(i).trim().contains(fetchReligion.getSampraday_name().trim())) {
                    pos = i;
                    sampraday_spinner.setSelection(pos);
                }
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

}
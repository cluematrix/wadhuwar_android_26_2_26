package com.WadhuWarProject.WadhuWar.activity;

public class Rough {

    /*package com.example.rashtriyatelisamaj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rashtriyatelisamaj.R;
import com.example.rashtriyatelisamaj.Receiver.NetworkStateReceiver;
import com.example.rashtriyatelisamaj.api.Api;
import com.example.rashtriyatelisamaj.api.RetrofitClient;
import com.example.rashtriyatelisamaj.model.CasteList;
import com.example.rashtriyatelisamaj.model.CasteListData;
import com.example.rashtriyatelisamaj.model.CountryList;
import com.example.rashtriyatelisamaj.model.CountryListData;
import com.example.rashtriyatelisamaj.model.DistrictList;
import com.example.rashtriyatelisamaj.model.DistrictListData;
import com.example.rashtriyatelisamaj.model.MothertoungeList;
import com.example.rashtriyatelisamaj.model.MothertoungeListData;
import com.example.rashtriyatelisamaj.model.ReligionList;
import com.example.rashtriyatelisamaj.model.ReligionListData;
import com.example.rashtriyatelisamaj.model.StateList;
import com.example.rashtriyatelisamaj.model.SubCasteList;
import com.example.rashtriyatelisamaj.model.TalukaList;
import com.example.rashtriyatelisamaj.model.TalukaListData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationDetailActivity extends AppCompatActivity implements  NetworkStateReceiver.NetworkStateReceiverListener  {

    private NetworkStateReceiver networkStateReceiver;

    CheckBox checkBox1;

    RelativeLayout submit_btn;
    TextView a1;
    Spinner current_country_spinner,current_state_spinner,religion_spinner,caste_spinner,sub_caste_spinner, current_district_spinner,current_taluka_spinner,
            mother_tounge_spinner,permant_taluka_spinner,permant_district_spinner,permant_state_spinner,permant_country_spinner;

    ArrayList<CountryListData> countryListData =  new ArrayList<>();
    CountryList countryList;
    ArrayList<StateList> stateListsData =  new ArrayList<>();
    StateList stateList;
    ArrayList<ReligionListData> religionListData =  new ArrayList<>();
    ReligionList religionList;
    ArrayList<CasteListData> casteListData =  new ArrayList<>();
    CasteList casteList;
    ArrayList<SubCasteList> subCasteLists =  new ArrayList<>();
    ArrayList<DistrictListData> districtListsData =  new ArrayList<>();
    DistrictList districtList;
    ArrayList<TalukaListData> talukaListData =  new ArrayList<>();
    TalukaList talukaList;
    ArrayList<MothertoungeListData> mothertoungeListData =  new ArrayList<>();
    MothertoungeList mothertoungeList;

    List<String> country_list = new ArrayList<String>();
    List<String> state_list = new ArrayList<String>();
    List<String> religion_list = new ArrayList<String>();
    List<String> caste_list = new ArrayList<String>();
    List<String> sub_caste_list = new ArrayList<String>();
    List<String> district_list = new ArrayList<String>();
    List<String> taluka_list = new ArrayList<String>();
    List<String> mother_tongue_list = new ArrayList<String>();

    String country_list_str,state_list_str,religion_list_str,caste_list_str,sub_caste_str,district_str,taluka_str,mother_tongue_str;

    String country_id,caste_id,subcaste_id,religion_id,district_id,state_id,mother_tongue_id;


    ArrayAdapter<String> mother_tongue_adapter;
    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> caste_adapter;
    ArrayAdapter<String> sub_caste_adapter;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> district_adapter;
    ArrayAdapter<String> taluka_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation_detail);

        submit_btn = (RelativeLayout) findViewById(R.id.submit_btn);
        a1 = findViewById(R.id.a1);
        current_country_spinner = findViewById(R.id.current_country_spinner);
        current_state_spinner = findViewById(R.id.current_state_spinner);
        religion_spinner = findViewById(R.id.religion_spinner);
        caste_spinner = findViewById(R.id.caste_spinner);
        sub_caste_spinner = findViewById(R.id.sub_caste_spinner);
        current_district_spinner = findViewById(R.id.current_district_spinner);
        current_taluka_spinner = findViewById(R.id.current_taluka_spinner);
        mother_tounge_spinner = findViewById(R.id.mother_tounge_spinner);
        checkBox1 = findViewById(R.id.checkBox1);
        permant_taluka_spinner = findViewById(R.id.permant_taluka_spinner);
        permant_district_spinner = findViewById(R.id.permant_district_spinner);
        permant_state_spinner = findViewById(R.id.permant_state_spinner);
        permant_country_spinner = findViewById(R.id.permant_country_spinner);

        a1.setBackgroundDrawable( getResources().getDrawable(R.drawable.pink_circle_progress) );

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AccomodationDetailActivity.this,CarrerDetailAndOtherAttributeActivity.class);
                startActivity(i);

            }
        });



//
//        /*if net off*/
//    networkStateReceiver = new NetworkStateReceiver();
//        networkStateReceiver.addListener(this);
//    registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
//    /*end code if net off*/
//
//}
//
//    @Override
//    public void networkAvailable() {
//        getCountryList();
//        getReligionList();
//        getMotherTongueList();
//
//    }
//
//    public void onCheckBoxClick(View view) {
//
//
//        boolean checked = ((CheckBox) view).isChecked();
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkBox1:
//                if (checked){
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
//                    if (state_list_str != null) {
//                        int spinnerPosition = adapter_state.getPosition(state_list_str);
//                        System.out.println("name state_list_str pos-----------" + state_list_str);
//                        System.out.println("state pos-----------" + spinnerPosition);
//
//                        permant_state_spinner.setSelection(spinnerPosition);
//                    }
//
//                    if (district_str != null) {
//                        int spinnerPosition = district_adapter.getPosition(district_str);
//                        System.out.println("name district_str pos-----------" + district_str);
//                        System.out.println("district pos-----------" + spinnerPosition);
//
//                        permant_district_spinner.setSelection(spinnerPosition);
//                    }
//
//
//
//                }
//
//                break;
//            // Perform your logic
//        }
//    }
//
//
//    public  void  getMotherTongueList( ){
//
//
//        mothertoungeListData.clear();
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<MothertoungeList> userResponse = apiService.motherTounge("08");
//        userResponse.enqueue(new Callback<MothertoungeList>() {
//
//            @Override
//            public void onResponse(Call<MothertoungeList> call, Response<MothertoungeList> response) {
//                mothertoungeList = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = mothertoungeList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for(int i=0;i<mothertoungeList.getMothertoungeList().size();i++){
//                            mothertoungeListData.add(new MothertoungeListData(mothertoungeList.getMothertoungeList().get(i).getId(),mothertoungeList.getMothertoungeList().get(i).getName()));
//                        }
//                        mother_tongue_spinner_data(mothertoungeListData);
//                    }else {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MothertoungeList> call, Throwable t) {
//
//                System.out.println("err mother tongue******" + t.toString());
//
////                if(!isNetworkAvailable(RegistrationActivity.this)){
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }else{
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }
//
//            }
//        });
//    }
//    public void mother_tongue_spinner_data(List<MothertoungeListData> mothertoungeListData){
//
//        mother_tongue_list.clear();
//
//        mother_tongue_list.add("Select Mother Tounge");
//
//        if(mothertoungeListData.size()!=0) {
//            for (int i = 0; i <mothertoungeListData.size(); i++) {
//
//                mother_tongue_list.add(i+1 , mothertoungeListData.get(i).getName());
//
//            }
//        }
//
//
//
//        mother_tongue_adapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, mother_tongue_list);
//
//        mother_tounge_spinner.setAdapter(mother_tongue_adapter);
//
//        mother_tounge_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                mother_tongue_str = mother_tounge_spinner.getItemAtPosition(position).toString();
//                System.out.println("mother_tongue_str------" + mother_tongue_str);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//
//    public  void  getReligionList(){
//
//        religionListData.clear();
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<ReligionList> userResponse = apiService.religionList("05");
//        userResponse.enqueue(new Callback<ReligionList>() {
//
//            @Override
//            public void onResponse(Call<ReligionList> call, Response<ReligionList> response) {
//                religionList = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = religionList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for(int i=0;i<religionList.getReligionList().size();i++){
//                            religionListData.add(new ReligionListData(religionList.getReligionList().get(i).getId(), religionList.getReligionList().get(i).getName()));
//
//                        }
//                        religion_spinner_data(religionListData);
//                    }else {
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReligionList> call, Throwable t) {
//
//                System.out.println("err rel******" + t.toString());
//
////                if(!isNetworkAvailable(RegistrationActivity.this)){
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }else{
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }
//
//            }
//        });
//    }
//    public void religion_spinner_data(List<ReligionListData> religionListData){
//
//        religion_list.clear();
//
//        religion_list.add("Select Religion");
//
//        if(religion_list.size()!=0) {
//            for (int i = 0; i <religionListData.size(); i++) {
//
//                religion_list.add(i+1 , religionListData.get(i).getName());
//
//            }
//        }
//
//
//        adapter_country = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, religion_list);
//
//        religion_spinner.setAdapter(adapter_country);
//
//        religion_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                religion_list_str = religion_spinner.getItemAtPosition(position).toString();
//                System.out.println("religion_list_str------" + religion_list_str);
//
//                getCastList(religion_list_str);
//
////                for_state_spinner_data(religion_list_str);
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//
//    public  void  getCastList(String religion_list_str ){
//
//
//        if (religion_list_str != null) {
//            for (int i = 0; i < religionListData.size(); i++) {
//                if (religion_list_str.equals(religionListData.get(i).getName())) {
//                    religion_id = String.valueOf(religionListData.get(i).getId());
//
//                }
//            }
//        }
//
//        System.out.println("religion_id--------" +  religion_id);
//
//        casteListData.clear();
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<CasteList> userResponse = apiService.casteList("06",religion_id);
//        userResponse.enqueue(new Callback<CasteList>() {
//
//            @Override
//            public void onResponse(Call<CasteList> call, Response<CasteList> response) {
//                casteList = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = casteList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for(int i=0;i<casteList.getCasteList().size();i++){
//                            casteListData.add(new CasteListData(casteList.getCasteList().get(i).getId(),casteList.getCasteList().get(i).getName(),casteList.getCasteList().get(i).getSubCasteList()));
//                        }
//                        caste_spinner_data(casteListData);
//                    }else {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CasteList> call, Throwable t) {
//
//                System.out.println("err caste******" + t.toString());
//
////                if(!isNetworkAvailable(RegistrationActivity.this)){
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }else{
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }
//
//            }
//        });
//    }
//    public void caste_spinner_data(List<CasteListData> casteListData){
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
//    public void for_caste_spinner_data(String caste_list_str){
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
//                subCaste_spinner_data(subCasteLists);
//            }
//        }
//    }
//    public void subCaste_spinner_data(List<SubCasteList> subCasteLists){
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
//
//
//    public  void  getCountryList(){
//
//        countryListData.clear();
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<CountryList> userResponse = apiService.countryList("07");
//        userResponse.enqueue(new Callback<CountryList>() {
//
//            @Override
//            public void onResponse(Call<CountryList> call, Response<CountryList> response) {
//                countryList = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = countryList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for(int i=0;i<countryList.getCountryList().size();i++){
//                            countryListData.add(new CountryListData(countryList.getCountryList().get(i).getId(),countryList.getCountryList().get(i).getName(),countryList.getCountryList().get(i).getStateList()));
//
//
////                            stateLists= countryList.getCountryList().get(i).getStateList();
////                            state_spinner_data_list(stateLists);
//
//
//                        }
////                        for(int i=0;i<countryListData.size();i++){
////                            System.out.println("country -----------" + countryList.getCountryList().get(i).getName());
////                        }
//                        country_spinner_data(countryListData);
//                        country_spinner_data11(countryListData);
//                    }else {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CountryList> call, Throwable t) {
//
//                System.out.println("err country******" + t.toString());
//
////                if(!isNetworkAvailable(RegistrationActivity.this)){
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }else{
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }
//
//            }
//        });
//    }
//    public void country_spinner_data(List<CountryListData> countryListData){
//
//        country_list.clear();
//
//        country_list.add("Select Country");
//
//        if(country_list.size()!=0) {
//            for (int i = 0; i <countryListData.size(); i++) {
//
//                country_list.add(i+1 , countryListData.get(i).getName());
//
//            }
//        }
//
//
//        adapter_country = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, country_list);
//
//        current_country_spinner.setAdapter(adapter_country);
//
//        current_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                country_list_str = current_country_spinner.getItemAtPosition(position).toString();
//                System.out.println("city_list_str------" + country_list_str);
//
//                for_state_spinner_data(country_list_str);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//
//
//    }
//    public void for_state_spinner_data(String country_list_str){
//        stateListsData.clear();
//        /*---------*/
//
//        if (country_list_str != null) {
//            for (int i = 0; i < countryListData.size(); i++) {
//                if (country_list_str.equals(countryListData.get(i).getName())) {
//                    country_id = String.valueOf(countryListData.get(i).getId());
//
//
//                }
//            }
//        }
//
//        for(int i=0;i<countryListData.size();i++){
//            if( countryListData.get(i).getStateList()!=null) {
//
//                for (int j = 0; j < countryListData.get(i).getStateList().size(); j++) {
//
//                    stateList = countryListData.get(i).getStateList().get(j);
//
//                    if(country_id!=null){
//
//                        if(country_id.contentEquals(stateList.getC_id())) {
//                            stateListsData.add(new StateList(stateList.getC_id(), stateList.getSc_id(),stateList.getSc_name()));
//                        }
//
//                    }
//
//
//                }
//                state_spinner_data(stateListsData);
//            }
//        }
//    }
//    public void state_spinner_data(List<StateList> stateLists){
//
//        state_list.clear();
//
//        state_list.add("Select State");
//
//        if(state_list.size()!=0) {
//            for (int i = 0; i <stateLists.size(); i++) {
//
//                state_list.add(i+1 , stateLists.get(i).getSc_name());
//
//            }
//        }
//
//
//        adapter_state = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, state_list);
//
//        current_state_spinner.setAdapter(adapter_state);
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
//
//
//    public  void  getDistrictList(String state_list_str ){
//        if (state_list_str != null) {
//            for (int i = 0; i < stateListsData.size(); i++) {
//                if (state_list_str.equals(stateListsData.get(i).getSc_name())) {
//                    state_id = String.valueOf(stateListsData.get(i).getSc_id());
//
//                }
//            }
//        }
//
//        System.out.println("state_id--------" +  state_id);
//
//        districtListsData.clear();
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<DistrictList> userResponse = apiService.districtList("13",state_id);
//        userResponse.enqueue(new Callback<DistrictList>() {
//
//            @Override
//            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
//                districtList = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = districtList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for(int i=0;i<districtList.getDistrictList().size();i++){
//                            districtListsData.add(new DistrictListData(districtList.getDistrictList().get(i).getId(),districtList.getDistrictList().get(i).getName(),districtList.getDistrictList().get(i).getState_id()));
//                        }
//                        district_spinner_data(districtListsData);
//                    }else {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DistrictList> call, Throwable t) {
//
//                System.out.println("err district******" + t.toString());
//
////                if(!isNetworkAvailable(RegistrationActivity.this)){
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }else{
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }
//
//            }
//        });
//    }
//    public void district_spinner_data(List<DistrictListData> districtListData){
//
//        district_list.clear();
//
//        district_list.add("Select District");
//
//        if(district_list.size()!=0) {
//            for (int i = 0; i <districtListData.size(); i++) {
//
//                district_list.add(i+1 , districtListData.get(i).getName());
//
//            }
//        }
//
//
//        district_adapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, district_list);
//
//        current_district_spinner.setAdapter(district_adapter);
//        current_district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                district_str = current_district_spinner.getItemAtPosition(position).toString();
//                System.out.println("district_str------" + district_str);
//
//                getTalukaList(district_str);
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
//
//
//    }
//
//    public  void  getTalukaList(String district_str ){
//        if (district_str != null) {
//            for (int i = 0; i < districtListsData.size(); i++) {
//                if (district_str.equals(districtListsData.get(i).getName())) {
//                    district_id = String.valueOf(districtListsData.get(i).getId());
//
//                }
//            }
//        }
//
//        System.out.println("district_id--------" +  district_id);
//
//        talukaListData.clear();
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<TalukaList> userResponse = apiService.talukaList("22",district_id);
//        userResponse.enqueue(new Callback<TalukaList>() {
//
//            @Override
//            public void onResponse(Call<TalukaList> call, Response<TalukaList> response) {
//                talukaList = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = talukaList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for(int i=0;i<talukaList.getTalukaList().size();i++){
//                            talukaListData.add(new TalukaListData(talukaList.getTalukaList().get(i).getId(),talukaList.getTalukaList().get(i).getName(),talukaList.getTalukaList().get(i).getDistrict_id()));
//                        }
//                        taluka_spinner_data(talukaListData);
//                    }else {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TalukaList> call, Throwable t) {
//
//                System.out.println("err taluka******" + t.toString());
//
////                if(!isNetworkAvailable(RegistrationActivity.this)){
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }else{
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }
//
//            }
//        });
//    }
//    public void taluka_spinner_data(List<TalukaListData> talukaListData){
//
//        taluka_list.clear();
//
//        taluka_list.add("Select Taluka");
//
//        if(taluka_list.size()!=0) {
//            for (int i = 0; i <talukaListData.size(); i++) {
//
//                taluka_list.add(i+1 , talukaListData.get(i).getName());
//
//            }
//        }
//
//
//        taluka_adapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, taluka_list);
//
//        current_taluka_spinner.setAdapter(taluka_adapter);
//
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
//
//
//
//    }
//
//
//
//
//
//
//
//
//
//    /*------*/
//
//    public void country_spinner_data11(List<CountryListData> countryListData){
//
//        country_list.clear();
//
//        country_list.add("Select Country");
//
//        if(country_list.size()!=0) {
//            for (int i = 0; i <countryListData.size(); i++) {
//
//                country_list.add(i+1 , countryListData.get(i).getName());
//
//            }
//        }
//
//
//        adapter_country = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, country_list);
//
//        permant_country_spinner.setAdapter(adapter_country);
//
//        permant_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                country_list_str = permant_country_spinner.getItemAtPosition(position).toString();
//                System.out.println("city_list_str------" + country_list_str);
//
//                for_state_spinner_data11(country_list_str);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//
//
//    }
//    public void for_state_spinner_data11(String country_list_str){
//        stateListsData.clear();
//        /*---------*/
//
//        if (country_list_str != null) {
//            for (int i = 0; i < countryListData.size(); i++) {
//                if (country_list_str.equals(countryListData.get(i).getName())) {
//                    country_id = String.valueOf(countryListData.get(i).getId());
//
//
//                }
//            }
//        }
//
//        for(int i=0;i<countryListData.size();i++){
//            if( countryListData.get(i).getStateList()!=null) {
//
//                for (int j = 0; j < countryListData.get(i).getStateList().size(); j++) {
//
//                    stateList = countryListData.get(i).getStateList().get(j);
//
//                    if(country_id!=null){
//
//                        if(country_id.contentEquals(stateList.getC_id())) {
//                            stateListsData.add(new StateList(stateList.getC_id(), stateList.getSc_id(),stateList.getSc_name()));
//                        }
//
//                    }
//
//
//                }
//                state_spinner_data11(stateListsData);
//            }
//        }
//    }
//    public void state_spinner_data11(List<StateList> stateLists){
//
//        state_list.clear();
//
//        state_list.add("Select State");
//
//        if(state_list.size()!=0) {
//            for (int i = 0; i <stateLists.size(); i++) {
//
//                state_list.add(i+1 , stateLists.get(i).getSc_name());
//
//            }
//        }
//
//
//        adapter_state = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, state_list);
//
//
//
//        permant_state_spinner.setAdapter(adapter_state);
//        permant_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                state_list_str = permant_state_spinner.getItemAtPosition(position).toString();
//
//                System.out.println("state_list_str------" + state_list_str);
//
//                getDistrictList11(state_list_str);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//
//    public  void  getDistrictList11(String state_list_str ){
//        if (state_list_str != null) {
//            for (int i = 0; i < stateListsData.size(); i++) {
//                if (state_list_str.equals(stateListsData.get(i).getSc_name())) {
//                    state_id = String.valueOf(stateListsData.get(i).getSc_id());
//
//                }
//            }
//        }
//
//        System.out.println("state_id--------" +  state_id);
//
//        districtListsData.clear();
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<DistrictList> userResponse = apiService.districtList("13",state_id);
//        userResponse.enqueue(new Callback<DistrictList>() {
//
//            @Override
//            public void onResponse(Call<DistrictList> call, Response<DistrictList> response) {
//                districtList = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = districtList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for(int i=0;i<districtList.getDistrictList().size();i++){
//                            districtListsData.add(new DistrictListData(districtList.getDistrictList().get(i).getId(),districtList.getDistrictList().get(i).getName(),districtList.getDistrictList().get(i).getState_id()));
//                        }
//                        district_spinner_data11(districtListsData);
//                    }else {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DistrictList> call, Throwable t) {
//
//                System.out.println("err district******" + t.toString());
//
////                if(!isNetworkAvailable(RegistrationActivity.this)){
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }else{
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }
//
//            }
//        });
//    }
//    public void district_spinner_data11(List<DistrictListData> districtListData){
//
//        district_list.clear();
//
//        district_list.add("Select District");
//
//        if(district_list.size()!=0) {
//            for (int i = 0; i <districtListData.size(); i++) {
//
//                district_list.add(i+1 , districtListData.get(i).getName());
//
//            }
//        }
//
//
//        district_adapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, district_list);
//
//
//
//        permant_district_spinner.setAdapter(district_adapter);
//        permant_district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                district_str = permant_district_spinner.getItemAtPosition(position).toString();
//                System.out.println("district_str------" + district_str);
//
//                getTalukaList11(district_str);
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
//
//    public  void  getTalukaList11(String district_str ){
//        if (district_str != null) {
//            for (int i = 0; i < districtListsData.size(); i++) {
//                if (district_str.equals(districtListsData.get(i).getName())) {
//                    district_id = String.valueOf(districtListsData.get(i).getId());
//
//                }
//            }
//        }
//
//        System.out.println("district_id--------" +  district_id);
//
//        talukaListData.clear();
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<TalukaList> userResponse = apiService.talukaList("22",district_id);
//        userResponse.enqueue(new Callback<TalukaList>() {
//
//            @Override
//            public void onResponse(Call<TalukaList> call, Response<TalukaList> response) {
//                talukaList = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = talukaList.getResid();
//
//                    if (success.equals("200")) {
//
//
//                        for(int i=0;i<talukaList.getTalukaList().size();i++){
//                            talukaListData.add(new TalukaListData(talukaList.getTalukaList().get(i).getId(),talukaList.getTalukaList().get(i).getName(),talukaList.getTalukaList().get(i).getDistrict_id()));
//                        }
//                        taluka_spinner_data11(talukaListData);
//                    }else {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TalukaList> call, Throwable t) {
//
//                System.out.println("err taluka******" + t.toString());
//
////                if(!isNetworkAvailable(RegistrationActivity.this)){
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }else{
////                    Toast.makeText(RegistrationActivity.this, "रीफ्रेश करा", Toast.LENGTH_SHORT).show();
////
////                }
//
//            }
//        });
//    }
//    public void taluka_spinner_data11(List<TalukaListData> talukaListData){
//
//        taluka_list.clear();
//
//        taluka_list.add("Select Taluka");
//
//        if(taluka_list.size()!=0) {
//            for (int i = 0; i <talukaListData.size(); i++) {
//
//                taluka_list.add(i+1 , talukaListData.get(i).getName());
//
//            }
//        }
//
//
//        taluka_adapter = new ArrayAdapter<String>(AccomodationDetailActivity.this, R.layout.simple_spinner_item, taluka_list);
//
//
//
//        permant_taluka_spinner.setAdapter(taluka_adapter);
//
//        permant_taluka_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
//
//                taluka_str = permant_taluka_spinner.getItemAtPosition(position).toString();
//                System.out.println("taluka_str------" + taluka_str);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//    /*------*/
//
//
//
//
//    @Override
//    public void networkUnavailable() {
//
//    }
//
//
//}

}

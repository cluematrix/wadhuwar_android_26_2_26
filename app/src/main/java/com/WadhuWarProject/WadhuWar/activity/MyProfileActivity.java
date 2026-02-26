package com.WadhuWarProject.WadhuWar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.WadhuWarProject.WadhuWar.extras.UriUtils;
import com.WadhuWarProject.WadhuWar.model.DocumentTypeList;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    LinearLayout basic_info_edit, more_about_edit, religious_back_edit, family_edit, astro_edit, location_edit, lifestyle_edit,
            partner_preferences_edit, disability_nameLL, accommodation_edit, Education_edt, childLL;
    Toolbar toolbar;
    UserData user;
    TextView user_name;
    private NetworkStateReceiver networkStateReceiver;
    ImageView imageView_certificate;
    Button btn_upload;
    ProgressDialog progressBar;
    FrameLayout fl;
    String occupation_img, edu_img, verification_doc_img, encoded_img2, encoded_img3, encoded_img4;
    String id, docId;
    String[] selectionArgs;
    private String imagePath;
    TextView posted_by, gender, age, date_of_birth, email_id, alt_email_id, mobile_no, whatapp_no, disability, body_weight,
            height, about, gothra, sub_caste, caste, mother_tongue, religion, current_address, current_country, current_state,
            current_district, current_taluka, current_village, permanent_address, permanent_country, permanent_state,
            permanent_district, permanent_taluka, permanent_village, father_name, father_occupation, father_property, property_location,
            property_type_name, father_mobile, mother_name, mother_occupation, mother_mobile, family_location, family_class, family_type, mama_name,
            mamekul, mama_mobile, brother_no, sister_no, sibline_name, married_sibling, sibling_occupation,
            manglik, verification_document_name, verification_document_certificate, highest_education,
            other_education, occupation, college_name, post_name, office_address, office_location, office_country, office_state, office_district,
            yr_salary, birth_time, hobbies, marital_status, color_complex, blood_grp, lifestyle, marital_Status_p, color_complexion_p, height_p, height_to,
            religion_p, caste_p, sub_caste_p, age_from_p, age_to_p, education_p, occupation_p, lifestyle_p, dietary_p, sampraday_p, expectaions_p, disability_name, dietary,
            num_childred, childred_living, children_age, rashi_txt, sampraday_txt,
            education_type, education_name, education_type_p, occupation_type_p,
            occupation_type, education_certificate, occupation_certificate, pref_salary, pref_country, pref_state,
            pref_district, pref_ofc_country, pref_ofc_state,
            pref_ofc_district,pref_mother_tongue;

    FetchProfile fetchProfile;
    SwipeRefreshLayout swipeRefreshLayout;
    MultipartBody.Part education_certificate_file, occupation_certificate_file, verification_doc_certificate_file;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;
    LinearLayout verification_documentLL;

    ArrayList<DocumentTypeList.DocumentTypeListData> documentTypeListData = new ArrayList<>();
    DocumentTypeList documentTypeList;
    List<String> documetTypeList = new ArrayList<String>();
    ArrayAdapter<String> adapter_document_type;
    private String documet_type_str;

    static boolean isNetworkAvailable;
    boolean showVerificationDocCertificateDialog = false;


    ImageView profile_image;

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
        setContentView(R.layout.activity_my_profile);

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
//        setContentView(R.layout.activity_my_profile);

        verification_documentLL = findViewById(R.id.verification_documentLL);
        verification_document_certificate = findViewById(R.id.verification_document_certificate);
        education_certificate = findViewById(R.id.education_certificate);
        occupation_certificate = findViewById(R.id.occupation_certificate);
        occupation_p = findViewById(R.id.occupation_p);
        occupation_type_p = findViewById(R.id.occupation_type_p);
        occupation_type = findViewById(R.id.occupation_type);
        education_type_p = findViewById(R.id.education_type_p);
        education_name = findViewById(R.id.education_name);
        education_type = findViewById(R.id.education_type);
        sampraday_txt = findViewById(R.id.sampraday_txt);
        rashi_txt = findViewById(R.id.rashi_txt);
        childLL = findViewById(R.id.childLL);
        children_age = findViewById(R.id.children_age);
        childred_living = findViewById(R.id.childred_living);
        num_childred = findViewById(R.id.num_childred);
        profile_image = findViewById(R.id.profile_image);
        user_name = findViewById(R.id.user_name);
        dietary = findViewById(R.id.dietary);
        accommodation_edit = findViewById(R.id.accommodation_edit);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        basic_info_edit = findViewById(R.id.basic_info_edit);
        more_about_edit = findViewById(R.id.more_about_edit);
        religious_back_edit = findViewById(R.id.religious_back_edit);
        family_edit = findViewById(R.id.family_edit);
        astro_edit = findViewById(R.id.astro_edit);

        lifestyle_edit = findViewById(R.id.lifestyle_edit);
        partner_preferences_edit = findViewById(R.id.partner_preferences_edit);
        posted_by = findViewById(R.id.posted_by);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        date_of_birth = findViewById(R.id.date_of_birth);
        email_id = findViewById(R.id.email_id);
        alt_email_id = findViewById(R.id.alt_email_id);
        mobile_no = findViewById(R.id.mobile_no);
        whatapp_no = findViewById(R.id.whatapp_no);
        disability = findViewById(R.id.disability);
        body_weight = findViewById(R.id.body_weight);
        height = findViewById(R.id.height);
        about = findViewById(R.id.about);
        gothra = findViewById(R.id.gothra);
        sub_caste = findViewById(R.id.sub_caste);
        caste = findViewById(R.id.caste);
        mother_tongue = findViewById(R.id.mother_tongue);
        religion = findViewById(R.id.religion);
        current_address = findViewById(R.id.current_address);
        current_country = findViewById(R.id.current_country);
        current_state = findViewById(R.id.current_state);
        current_district = findViewById(R.id.current_district);
        current_taluka = findViewById(R.id.current_taluka);
        current_village = findViewById(R.id.current_village);
        permanent_address = findViewById(R.id.permanent_address);
        permanent_country = findViewById(R.id.permanent_country);
        permanent_state = findViewById(R.id.permanent_state);
        permanent_district = findViewById(R.id.permanent_district);
        permanent_taluka = findViewById(R.id.permanent_taluka);
        permanent_village = findViewById(R.id.permanent_village);
        father_name = findViewById(R.id.father_name);
        father_occupation = findViewById(R.id.father_occupation);
        father_property = findViewById(R.id.father_property);
        property_location = findViewById(R.id.property_location);
        property_type_name = findViewById(R.id.property_type_name);
        father_mobile = findViewById(R.id.father_mobile);
        mother_name = findViewById(R.id.mother_name);
        mother_occupation = findViewById(R.id.mother_occupation);
        mother_mobile = findViewById(R.id.mother_mobile);
        family_location = findViewById(R.id.family_location);
        family_class = findViewById(R.id.family_class);
        family_type = findViewById(R.id.family_type);
        mama_name = findViewById(R.id.mama_name);
        mamekul = findViewById(R.id.mamekul);
        mama_mobile = findViewById(R.id.mama_mobile);
        brother_no = findViewById(R.id.brother_no);
        sister_no = findViewById(R.id.sister_no);
        sibline_name = findViewById(R.id.sibline_name);
        married_sibling = findViewById(R.id.married_sibling);
        sibling_occupation = findViewById(R.id.sibling_occupation);
        manglik = findViewById(R.id.manglik);
        verification_document_name = findViewById(R.id.verification_document_name);
        highest_education = findViewById(R.id.highest_education);
        other_education = findViewById(R.id.other_education);
        occupation = findViewById(R.id.occupation);
        college_name = findViewById(R.id.college_name);
        post_name = findViewById(R.id.post_name);
        office_address = findViewById(R.id.office_address);
        office_location = findViewById(R.id.office_location);
        office_country = findViewById(R.id.office_country);
        office_state = findViewById(R.id.office_state);
        office_district = findViewById(R.id.office_district);
        yr_salary = findViewById(R.id.yr_salary);
        birth_time = findViewById(R.id.birth_time);
        hobbies = findViewById(R.id.hobbies);
        marital_status = findViewById(R.id.marital_status);
        color_complex = findViewById(R.id.color_complex);
        blood_grp = findViewById(R.id.blood_grp);
        lifestyle = findViewById(R.id.lifestyle);
        dietary = findViewById(R.id.dietary);
        marital_Status_p = findViewById(R.id.marital_Status_p);
        color_complexion_p = findViewById(R.id.color_complexion_p);
        height_p = findViewById(R.id.height_p);
        height_to = findViewById(R.id.height_to);
        religion_p = findViewById(R.id.religion_p);
        caste_p = findViewById(R.id.caste_p);
        sub_caste_p = findViewById(R.id.sub_caste_p);
        age_from_p = findViewById(R.id.age_from_p);
        age_to_p = findViewById(R.id.age_to_p);
        education_p = findViewById(R.id.education_p);
        lifestyle_p = findViewById(R.id.lifestyle_p);
        dietary_p = findViewById(R.id.dietary_p);
        sampraday_p = findViewById(R.id.sampraday_p);
        expectaions_p = findViewById(R.id.expectaions_p);
        disability_nameLL = findViewById(R.id.disability_nameLL);
        disability_name = findViewById(R.id.disability_name);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        Education_edt = findViewById(R.id.Education_edt);
        fl = findViewById(R.id.fl);
        pref_salary = findViewById(R.id.pref_salary);
        pref_country = findViewById(R.id.pref_country);
        pref_state = findViewById(R.id.pref_state);
        pref_district = findViewById(R.id.pref_district);

        pref_ofc_country = findViewById(R.id.pref_ofc_country);
        pref_ofc_state = findViewById(R.id.pref_ofc_state);
        pref_ofc_district = findViewById(R.id.pref_ofc_district);


        pref_mother_tongue = findViewById(R.id.pref_mother_tongue);

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


        basic_info_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, BasicInfoActivity.class);
                startActivity(i);
                finish();
            }
        });


        more_about_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, MoreAboutMyselfPartnerFamilyActivity.class);
                startActivity(i);
                finish();
            }
        });

        religious_back_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, ReligiousBackgroundActivity.class);
                startActivity(i);
                finish();
            }
        });

        accommodation_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, AccommodationEditActibity.class);
                startActivity(i);
                finish();
            }
        });

        family_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, FamilyActivity.class);
                startActivity(i);
                finish();
            }
        });

        astro_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, AstroDetailsActivity.class);
                startActivity(i);
                finish();
            }
        });


        Education_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, EducationCareerActivity.class);
                startActivity(i);
                finish();
            }
        });

        education_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //requestPermission();
                showCertificateDialog("Education_certificate", fetchProfile.getEducation_certificate(), fetchProfile.getUser_id());
                // startActivity(new Intent(MyProfileActivity.this, CertificateActivity.class).putExtra("isCallFromEducationCertificate", true));
            }
        });

        occupation_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //requestPermission();
                showCertificateDialog("occupation_certificate", fetchProfile.getOccupation_certificate(), fetchProfile.getUser_id());
                // startActivity(new Intent(MyProfileActivity.this, CertificateActivity.class).putExtra("isCallFromEducationCertificate", false));
            }
        });


        lifestyle_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, LifestyleActivity.class);
                startActivity(i);
                finish();
            }
        });

        partner_preferences_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, PartnerPreferencesActivity.class);
                startActivity(i);
                finish();
            }
        });
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyProfileActivity.this, MyPhotosActivity.class);
                startActivity(i);
            }
        });

        user = SharedPrefManager.getInstance(MyProfileActivity.this).getUser();

        fetchProfileData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


    }

    private void showVerificationDocCertificateDialog(String verification_doc, String certificate, String user_id) {
        Dialog dialog = new Dialog(MyProfileActivity.this);

        dialog.setContentView(R.layout.verification_doc_certificate_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        imageView_certificate = dialog.findViewById(R.id.imageView_certificate);
        btn_upload = dialog.findViewById(R.id.btn_upload);
        TextView edit_certificate = dialog.findViewById(R.id.edit_certificate);
        Spinner verification_document_for_spinner = dialog.findViewById(R.id.verification_document_for_spinner);

        getdocumentTypeList(verification_document_for_spinner,verification_doc);


        if (!certificate.isEmpty()) {
            edit_certificate.setText("Edit");
            Picasso.get()
                    .load(certificate)
                    .placeholder(R.drawable.no_photo_available)
                    .error(R.drawable.no_photo_available)
                    .into(imageView_certificate);
        } else {
            edit_certificate.setText("Add");
            imageView_certificate.setImageResource(R.drawable.no_photo_available);
        }

        edit_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission4();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("documet_type_str=====----------" + documet_type_str);
                if (documet_type_str != null) {
                    if (!documet_type_str.equalsIgnoreCase("Select Document")) {
                        System.out.println("submitVerificationDoc=============");
                        submitVerificationDoc(user_id, documet_type_str);
                    } else {
                        Toast.makeText(MyProfileActivity.this, "Please select document type", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyProfileActivity.this, "Please select document type", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void getdocumentTypeList(Spinner verification_document_for_spinner, String verification_doc) {

        documentTypeListData.clear();

        Api apiService = RetrofitClient.getApiService();
        Call<DocumentTypeList> userResponse = apiService.documentTypeList();
        userResponse.enqueue(new Callback<DocumentTypeList>() {

            @Override
            public void onResponse(Call<DocumentTypeList> call, Response<DocumentTypeList> response) {
                swipeRefreshLayout.setRefreshing(false);
                documentTypeList = response.body();

                if (response.isSuccessful()) {


                    String success = documentTypeList.getResid();

                    if (success.equals("200")) {


                        for (int i = 0; i < documentTypeList.getDocumentTypeList().size(); i++) {
                            documentTypeListData.add(new DocumentTypeList.DocumentTypeListData(documentTypeList.getDocumentTypeList().get(i).getName()));

                        }



                        document_type_spinner_data(documentTypeListData, verification_document_for_spinner,verification_doc);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<DocumentTypeList> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("err profile created for******" + t.toString());

                if (!isNetworkAvailable()) {
//                    Toast.makeText(MyProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(MyProfileActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void document_type_spinner_data(List<DocumentTypeList.DocumentTypeListData> documentTypeListData,
                                           Spinner verification_document_for_spinner, String verification_doc) {

        int pos;
        for (int i = 0; i < documentTypeListData.size(); i++) {
            if (verification_doc.equalsIgnoreCase(documentTypeListData.get(i).getName().trim())) {
                pos = i;
                verification_document_for_spinner.setSelection(pos);
            }
        }

        documetTypeList.clear();

        documetTypeList.add("Select Document");

        if (documetTypeList.size() != 0) {
            for (int i = 0; i < documentTypeListData.size(); i++) {

                documetTypeList.add(i + 1, documentTypeListData.get(i).getName());

            }
        }
        adapter_document_type = new ArrayAdapter<String>(MyProfileActivity.this, R.layout.simple_spinner_item, documetTypeList);

        verification_document_for_spinner.setAdapter(adapter_document_type);

        verification_document_for_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                documet_type_str = verification_document_for_spinner.getItemAtPosition(position).toString();
                System.out.println("profileCreatedList_str------" + documet_type_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void showCertificateDialog(String isCallFromDoc, String certificate, String user_id) {
        Dialog dialog = new Dialog(MyProfileActivity.this);

        dialog.setContentView(R.layout.certificate_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        imageView_certificate = dialog.findViewById(R.id.imageView_certificate);
        btn_upload = dialog.findViewById(R.id.btn_upload);
        TextView edit_certificate = dialog.findViewById(R.id.edit_certificate);


        if (certificate != null && !certificate.isEmpty()) {
            edit_certificate.setText("Edit");
            Picasso.get()
                    .load(certificate)
                    .placeholder(R.drawable.no_photo_available)
                    .error(R.drawable.no_photo_available)
                    .into(imageView_certificate);
        } else {
            edit_certificate.setText("Add");
            imageView_certificate.setImageResource(R.drawable.no_photo_available);
        }

        if (certificate != null && !certificate.isEmpty()) {
            edit_certificate.setText("Edit");
            Picasso.get()
                    .load(certificate)
                    .placeholder(R.drawable.no_photo_available)
                    .error(R.drawable.no_photo_available)
                    .into(imageView_certificate);
        } else {
            edit_certificate.setText("Add");
            imageView_certificate.setImageResource(R.drawable.no_photo_available);
        }

        edit_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCallFromDoc.equalsIgnoreCase("Education_certificate")) {
                    requestPermission2();
                } else if (isCallFromDoc.equalsIgnoreCase("occupation_certificate")) {
                    requestPermission3();
                }
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCallFromDoc.equalsIgnoreCase("Education_certificate")) {
                    submitEduCertiData(user_id);
                } else if (isCallFromDoc.equalsIgnoreCase("occupation_certificate")) {
                    submitOccCertiData(user_id);
                }
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void submitEduCertiData(String user_id) {

        RequestBody _user_id = RequestBody.create(MediaType.parse("text/plain"), user_id);

        try {
            File file = new File(String.valueOf(edu_img));
            if (file.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                education_certificate_file = MultipartBody.Part.createFormData("education_certificate", file.getName(), requestFile);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        /*===========*/
        progressBar = ProgressDialog.show(MyProfileActivity.this, "", "Please Wait...");

        System.out.println("user_i------d" + user_id);
        System.out.println("education_certificate_file------" + education_certificate_file);
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editEducationCertificate(education_certificate_file, _user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                InsertResponse insertResponse = response.body();

                progressBar.dismiss();

                if (response.isSuccessful()) {

                    /*System.out.println("about resp ========" + new Gson().toJson(insertResponse));*/

                    System.out.println("about resp ========" + insertResponse.getImgnm());
                    System.out.println("about resp ========" + insertResponse.getResMsg());

                    String success = insertResponse.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(MyProfileActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MyProfileActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                progressBar.dismiss();

                System.out.println("err about ******" + t.toString());

                if (!isNetworkAvailable()) {

                    Toast.makeText(MyProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MyProfileActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void submitVerificationDoc(String user_id, String documet_type_str) {

        RequestBody _user_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody _documet_type_str = RequestBody.create(MediaType.parse("text/plain"), documet_type_str);

        try {
            File file = new File(String.valueOf(verification_doc_img));
            if (file.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                verification_doc_certificate_file = MultipartBody.Part.createFormData("document_img", file.getName(), requestFile);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        /*===========*/
        progressBar = ProgressDialog.show(MyProfileActivity.this, "", "Please Wait...");

        System.out.println("user_i------d" + user_id);
        System.out.println("verification_doc_certificate_file------" + verification_doc_certificate_file);
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.EditKycImage(_documet_type_str, verification_doc_certificate_file, _user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                InsertResponse insertResponse = response.body();

                progressBar.dismiss();

                if (response.isSuccessful()) {

                    /*System.out.println("about resp ========" + new Gson().toJson(insertResponse));*/

                    System.out.println("about resp ========" + insertResponse.getImgnm());
                    System.out.println("about resp ========" + insertResponse.getResMsg());

                    String success = insertResponse.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(MyProfileActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MyProfileActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                progressBar.dismiss();

                System.out.println("err about ******" + t.toString());

                if (!isNetworkAvailable()) {

                    Toast.makeText(MyProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MyProfileActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void submitOccCertiData(String user_id) {

        RequestBody _user_id = RequestBody.create(MediaType.parse("text/plain"), user_id);

        try {
            File file = new File(String.valueOf(occupation_img));
            if (file.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                occupation_certificate_file = MultipartBody.Part.createFormData("occupation_certificate", file.getName(), requestFile);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        /*===========*/
        progressBar = ProgressDialog.show(MyProfileActivity.this, "", "Please Wait...");

        System.out.println("user_i------d" + user_id);
        System.out.println("occupation_certificate_file------" + occupation_certificate_file);
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editOccupationCertificate(occupation_certificate_file, _user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                InsertResponse insertResponse = response.body();

                progressBar.dismiss();

                if (response.isSuccessful()) {

                    /*System.out.println("about resp ========" + new Gson().toJson(insertResponse));*/

                    System.out.println("about resp ========" + insertResponse.getImgnm());
                    System.out.println("about resp ========" + insertResponse.getResMsg());

                    String success = insertResponse.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(MyProfileActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MyProfileActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                progressBar.dismiss();

                System.out.println("err about ******" + t.toString());

                if (!isNetworkAvailable()) {

                    Toast.makeText(MyProfileActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MyProfileActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    @Override
    public void networkAvailable() {
//        fetchProfileData();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchProfileData();


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

                Toast.makeText(MyProfileActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

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


    public void fetchProfileData() {

        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {

                swipeRefreshLayout.setRefreshing(false);

                fetchProfile = response.body();

                System.out.println("fetchProfilefetchProfile-------" + new Gson().toJson(fetchProfile));


                if (response.isSuccessful()) {

                    if (fetchProfile.getImages() != null) {
                        if (fetchProfile.getImages().size() != 0) {
                            Glide.with(getApplicationContext()).load(fetchProfile.getImages().get(0).getImgs()).into(profile_image);
                        } else {
                            if (fetchProfile.getGender().equals("Female")) {
                                profile_image.setImageResource(R.drawable.female_avtar);
                            } else {
                                profile_image.setImageResource(R.drawable.male_avtar);
                            }
                        }
                    } else {

                        if (fetchProfile.getGender() != null) {
                            if (fetchProfile.getGender().equals("Female")) {
                                profile_image.setImageResource(R.drawable.female_avtar);
                            } else {
                                profile_image.setImageResource(R.drawable.male_avtar);
                            }
                        }
                    }


                    user_name.setText(fetchProfile.getFname() + " " + fetchProfile.getMname() + " " + fetchProfile.getLname());

                    /*System.out.println("resp fetch profile .......>>>" + new Gson().toJson(response.body()));*/

                    posted_by.setText(": " + fetchProfile.getProfile_name());
                    gender.setText(": " + fetchProfile.getGender());
                    age.setText(": " + fetchProfile.getAge());
                    date_of_birth.setText(": " + fetchProfile.getDob());
                    email_id.setText(": " + fetchProfile.getEmail());
                    alt_email_id.setText(": " + fetchProfile.getAlt_email());
                    mobile_no.setText(": " + fetchProfile.getMobile_no());
                    whatapp_no.setText(": " + fetchProfile.getWtsapp_no());
                    disability.setText(": " + fetchProfile.getHandicap());
                    height.setText(": " + fetchProfile.getHeight());
                    body_weight.setText(": " + fetchProfile.getWeight());

                    if (fetchProfile.getHandicap_name() != null) {
                        System.out.println("dis name========" + fetchProfile.getHandicap_name());
                        if (fetchProfile.getHandicap().contentEquals("Yes")) {
                            disability_nameLL.setVisibility(View.VISIBLE);
                            disability_name.setText(String.format(": %s", fetchProfile.getHandicap_name()));
                        } else if (fetchProfile.getHandicap().contentEquals("No")) {
                            disability_nameLL.setVisibility(View.GONE);

                        } else {
                            disability_nameLL.setVisibility(View.GONE);
                        }
                    }
                    System.out.println("about name========" + fetchProfile.getAbout_me());

                    about.setText(fetchProfile.getAbout_me());


                    religion.setText(": " + fetchProfile.getReligion_name());
                    mother_tongue.setText(": " + fetchProfile.getMothertounge_name());
                    caste.setText(": " + fetchProfile.getCaste_name());
                    sub_caste.setText(": " + fetchProfile.getSubcaste_name());
                    gothra.setText(": " + fetchProfile.getGotra());
                    sampraday_txt.setText(": " + fetchProfile.getSampraday_name());
                    rashi_txt.setText(": " + fetchProfile.getRashi_name());


                    current_address.setText(": " + fetchProfile.getCurrent_address());
                    current_country.setText(": " + fetchProfile.getCurr_country_name());
                    current_state.setText(": " + fetchProfile.getCurr_state_name());
                    current_district.setText(": " + fetchProfile.getCurr_ditrict_name());
                    current_taluka.setText(": " + fetchProfile.getCurr_taluka_name());
                    current_village.setText(": " + fetchProfile.getCurrent_village());
                    permanent_address.setText(": " + fetchProfile.getPer_address());
                    permanent_country.setText(": " + fetchProfile.getPer_country_name());
                    permanent_state.setText(": " + fetchProfile.getPer_state_name());
                    permanent_district.setText(": " + fetchProfile.getPer_ditrict_name());
                    permanent_taluka.setText(": " + fetchProfile.getPer_taluka_name());
                    permanent_village.setText(": " + fetchProfile.getPer_village());


                    father_name.setText(": " + fetchProfile.getFather_name());
                    father_occupation.setText(": " + fetchProfile.getFather_occ_name());
                    father_property.setText(": " + fetchProfile.getFather_property());
                    property_location.setText(": " + fetchProfile.getProperty_loc());
                    property_type_name.setText(": " + fetchProfile.getProperty_type_name());
                    father_mobile.setText(": " + fetchProfile.getFather_mobile());
                    mother_name.setText(": " + fetchProfile.getMother_name());
                    mother_occupation.setText(": " + fetchProfile.getMother_occ());
                    mother_mobile.setText(": " + fetchProfile.getMother_mobile());
                    family_location.setText(": " + fetchProfile.getFamily_loc());
                    family_class.setText(": " + fetchProfile.getFamily_class());
                    family_type.setText(": " + fetchProfile.getFamily_type());
                    mama_name.setText(": " + fetchProfile.getMama_name());
                    mamekul.setText(": " + fetchProfile.getMamekul());
                    mama_mobile.setText(": " + fetchProfile.getMama_mobile());
                    brother_no.setText(": " + fetchProfile.getBro_count());
                    sister_no.setText(": " + fetchProfile.getSis_count());
                    sibline_name.setText(": " + fetchProfile.getSibling_names());
                    married_sibling.setText(": " + fetchProfile.getMarried_sibling());
                    sibling_occupation.setText(": " + fetchProfile.getSibling_occ_name());


                    manglik.setText(": " + fetchProfile.getManglik());


                    verification_document_name.setText(fetchProfile.getVerification_doc());

                    verification_document_certificate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //requestPermission();
                            showVerificationDocCertificateDialog(fetchProfile.getVerification_doc(), fetchProfile.getVerification_img(), fetchProfile.getUser_id());
                            // startActivity(new Intent(MyProfileActivity.this, CertificateActivity.class).putExtra("isCallFromEducationCertificate", true));
                        }
                    });


                    education_type.setText(": " + fetchProfile.getMain_education_name());
                    education_name.setText(": " + fetchProfile.getEducation_name());
                    other_education.setText(": " + fetchProfile.getOther_education());
                    occupation_type.setText(": " + fetchProfile.getMain_occupation_name());
                    occupation.setText(": " + fetchProfile.getOccupation_name());
                    college_name.setText(": " + fetchProfile.getCollege_name());
                    post_name.setText(": " + fetchProfile.getPost_name());
                    office_address.setText(": " + fetchProfile.getOfc_address());
                    office_location.setText(": " + fetchProfile.getOfc_loc());
                    office_country.setText(": " + fetchProfile.getOfc_country_name());
                    office_state.setText(": " + fetchProfile.getOfc_state_name());
                    office_district.setText(": " + fetchProfile.getOfc_ditrict_name());
                    yr_salary.setText(": " + fetchProfile.getYearly_salary());

                    pref_salary.setText(": " + fetchProfile.getPref_yearsalary());
                    pref_country.setText(": " + fetchProfile.getPref_country_name());
                    pref_state.setText(": " + fetchProfile.getPref_state_name());
                    pref_district.setText(": " + fetchProfile.getPref_dist_name());

//                    Toast.makeText(MyProfileActivity.this, "data " +fetchProfile.getPrefOfcCountryName() + " " + fetchProfile.getPrefOfcStateName() , Toast.LENGTH_SHORT).show();
                      pref_ofc_country.setText(": " + fetchProfile.getNewPrefOfcCountryName());
                    pref_ofc_state.setText(": " + fetchProfile.getNewPrefOfcStateName());
                    pref_ofc_district.setText(": " + fetchProfile.getNewPrefOfcDitrictName());


                    pref_mother_tongue.setText(": " + fetchProfile.getPref_mothertongue_name());


                    birth_time.setText(": " + fetchProfile.getBirth_time());
                    hobbies.setText(": " + fetchProfile.getHobbies());
                    marital_status.setText(": " + fetchProfile.getMarital_status_name());

                    if (fetchProfile.getMarital_status_name() != null) {

                        if (fetchProfile.getMarital_status_name().equals("Devorce") || fetchProfile.getMarital_status_name().equals("Widow/Widower")) {
                            childLL.setVisibility(View.VISIBLE);
                            num_childred.setText(": " + fetchProfile.getNo_of_child());
                            childred_living.setText(": " + fetchProfile.getChild_stay());
                            children_age.setText(": " + fetchProfile.getAge_of_child());

                        } else {
                            childLL.setVisibility(View.GONE);
                        }

                    }
                    color_complex.setText(": " + fetchProfile.getColor_complex_name());
                    blood_grp.setText(": " + fetchProfile.getBloodgroup_name());
                    lifestyle.setText(": " + fetchProfile.getLifestyle_name());
                    dietary.setText(": " + fetchProfile.getDietry_name());


                    marital_Status_p.setText(": " + fetchProfile.getPref_marital_name());
                    color_complexion_p.setText(": " + fetchProfile.getPref_complex_name());
                    height_p.setText(": " + fetchProfile.getPref_height_from());
                    height_to.setText(": " + fetchProfile.getPref_height_to());
                    religion_p.setText(": " + fetchProfile.getPref_religion_name());
                    caste_p.setText(": " + fetchProfile.getPref_caste_name());
                    sub_caste_p.setText(": " + fetchProfile.getPref_subcaste_name());
                    age_from_p.setText(": " + fetchProfile.getPref_agefrom());
                    age_to_p.setText(": " + fetchProfile.getPref_ageto());
                    education_type_p.setText(": " + fetchProfile.getPrefmain_edu_name());
                    education_p.setText(": " + fetchProfile.getPref_edu_name());

                    occupation_type_p.setText(": " + fetchProfile.getPrefmain_occ_name());
                    occupation_p.setText(": " + fetchProfile.getPref_occ_name());

                    lifestyle_p.setText(": " + fetchProfile.getPref_lifestyle_name());
                    dietary_p.setText(": " + fetchProfile.getPref_diet_name());
                    sampraday_p.setText(": " + fetchProfile.getPref_sampraday_name());
                    expectaions_p.setText(": " + fetchProfile.getPref_expect());


                    showVerificationDocCertificateDialog = getIntent().getBooleanExtra("showVerificationDocCertificateDialog",false);
                    if (showVerificationDocCertificateDialog){
                        showVerificationDocCertificateDialog(fetchProfile.getVerification_doc(), fetchProfile.getVerification_img(), fetchProfile.getUser_id());
                    }

                }
            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    /*=============*/
    ActivityResultLauncher<Intent> imgChooser_occupation_certi = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data.getData() != null) {
                            btn_upload.setVisibility(View.VISIBLE);
                            Uri mImageUri = data.getData();
                            imageView_certificate.setImageURI(data.getData());
                            String n = getPathFromURI(mImageUri);
                            occupation_img = UriUtils.copyFileToInternalStorage(mImageUri, "dummy_file", MyProfileActivity.this);

                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(mImageUri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                            encoded_img3 = encodeTobase64(yourSelectedImage);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 0, stream);

                        }


                    }
                }
            });
    /*=============*/

    /*=============*/
    ActivityResultLauncher<Intent> imgChooser_education_certi = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data.getData() != null) {
                            btn_upload.setVisibility(View.VISIBLE);
                            Uri mImageUri = data.getData();
                            imageView_certificate.setImageURI(data.getData());
                            String n = getPathFromURI(mImageUri);
                            edu_img = UriUtils.copyFileToInternalStorage(mImageUri, "dummy_file", MyProfileActivity.this);


                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(mImageUri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                            encoded_img2 = encodeTobase64(yourSelectedImage);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 0, stream);

                        }
                    }
                }
            });
    /*=============*/

    /*=============*/
    ActivityResultLauncher<Intent> imgChooser_verification_doc_certi = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data.getData() != null) {
                            btn_upload.setVisibility(View.VISIBLE);
                            Uri mImageUri = data.getData();
                            imageView_certificate.setImageURI(data.getData());
                            String n = getPathFromURI(mImageUri);
                            verification_doc_img = UriUtils.copyFileToInternalStorage(mImageUri, "dummy_file", MyProfileActivity.this);


                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(mImageUri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                            encoded_img4 = encodeTobase64(yourSelectedImage);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 0, stream);

                        }
                    }
                }
            });
    /*=============*/

    private void requestPermission2() {
        if (Build.VERSION.SDK_INT < 19) {

            System.out.println("11111111111111111111111111111111111111");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser_education_certi.launch(Intent.createChooser(intent, "Select pictures"));
        } else {
            System.out.println("2222222222222222222222222222222222222");
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser_education_certi.launch(Intent.createChooser(intent, "Select pictures"));

        }


    }

    private void requestPermission3() {

        if (Build.VERSION.SDK_INT < 19) {

            System.out.println("11111111111111111111111111111111111111");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser_occupation_certi.launch(Intent.createChooser(intent, "Select pictures"));
        } else {
            System.out.println("2222222222222222222222222222222222222");
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser_occupation_certi.launch(Intent.createChooser(intent, "Select pictures"));

        }


    }


    private void requestPermission4() {

        if (Build.VERSION.SDK_INT < 19) {

            System.out.println("11111111111111111111111111111111111111");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser_verification_doc_certi.launch(Intent.createChooser(intent, "Select pictures"));
        } else {
            System.out.println("2222222222222222222222222222222222222");
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser_verification_doc_certi.launch(Intent.createChooser(intent, "Select pictures"));

        }


    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }


    private String getPathFromURI(Uri uri) {
        String path = uri.getPath(); // uri = any content Uri

        Uri databaseUri;
        String selection;


        if (isDownloadsDocument(uri)) {
            System.out.println("1010101010101010----------");
        } else {
            System.out.println("XXXXXXXXXXXXXXXXX----------");
        }

        if (isDownloadsDocument(uri)) {

            System.out.println("aaaaaaaaaaaaaa");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                System.out.println("bbbbbbbbbbbbbbbbb" + DocumentsContract.getDocumentId(uri));
                id = DocumentsContract.getDocumentId(uri);
            }

            if (id.startsWith("raw:")) {
                return id.replaceFirst("raw:", "");
            }

            System.out.println("cccccccccccccc" + Long.valueOf(id));

//            final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            System.out.println("dddddddddddddddd" + contentUri);


            imagePath = getDataColumn(MyProfileActivity.this, contentUri, null, null);

            System.out.println("imagepath dwnload img=---------------" + imagePath);

        } else if (isExternalStorageDocument(uri)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                docId = DocumentsContract.getDocumentId(uri);
            }
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }

        } else if (isMediaDocument(uri)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                docId = DocumentsContract.getDocumentId(uri);
            }
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            selection = "_id=?";
            final String[] selectionArgs = new String[]{
                    split[1]
            };

            return getDataColumn(MyProfileActivity.this, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(MyProfileActivity.this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else {

            System.out.println("NOT downoad imag----------");

            if (path.contains("/document/image:")) { // files selected from "Documents"
                databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                selection = "_id=?";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    selectionArgs = new String[]{DocumentsContract.getDocumentId(uri).split(":")[1]};
                }

            } else { // files selected from all other sources, especially on Samsung devices
                databaseUri = uri;
                selection = null;
                selectionArgs = null;
            }
            try {
                String[] projection = {
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.ORIENTATION,
                        MediaStore.Images.Media.DATE_TAKEN};
                // some example data you can query
         /*   Cursor cursor = contentResolver.query(
                    databaseUri,
                    projection, selection, selectionArgs, null
            )*/
                Cursor cursor = getContentResolver().query(databaseUri, projection, selection, selectionArgs, null);


                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    imagePath = cursor.getString(columnIndex);


//                 Log.e("path---------", imagePath);
//                imagesEncodedList.add(imagePath);
                }
                cursor.close();
            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return imagePath;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        System.out.println("lllllllllllllllllllllll");

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);

            System.out.println("mmmmmmmmmmmmmmmmmmmmmm");

            if (cursor != null && cursor.moveToFirst()) {
                System.out.println(" 111=============" + cursor.getColumnIndexOrThrow(column));
                System.out.println("nnnnnnnnnnnnnnnnnnnnnn");

                int index = cursor.getColumnIndexOrThrow(column);
                System.out.println("dwnload 111=============" + index);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
package com.WadhuWarProject.WadhuWar.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
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
import com.WadhuWarProject.WadhuWar.adapter.ContactImageAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.UriUtils;
import com.WadhuWarProject.WadhuWar.model.DocumentTypeList;
import com.WadhuWarProject.WadhuWar.model.Image;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;

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

public class InsertStep7Activity extends AppCompatActivity implements
        NetworkStateReceiver.NetworkStateReceiverListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    String[] selectionArgs;
    String docId;
    TextView skip;

    String result;
    List<MultipartBody.Part> list = new ArrayList<>();
    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;
    MultipartBody.Part[] surveyImagesParts;
    ArrayList<Uri> mArrayUriDocumentImg = new ArrayList<Uri>();
    Spinner verification_document_for_spinner;
    MultipartBody.Part education_certificate_file, occupation_certificate_file, verification_doc_certi_file;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private String uriString = "";
    private String imagePath;
    private List<String> imagePathList;
    TextView a1, a2, a3, a4, a5, a6;
    String str;
    LinearLayout upload_verification_doc_img_capture, education_certificate_img_capture, occupation_certificate_img_capture;
    List<String> imagesEncodedListDocument = new ArrayList<>();
    String imageEncoded;
    String encoded_img2, encoded_img3, encoded_img4, encoded_img_doc_ver;
    ContactImageAdapter imageAdapter;
    ArrayList<Image> imageArryDocumentImg = new ArrayList<Image>();
    ArrayList<String> img_array_document_img;
    RelativeLayout submit_btn;
    String occupation_img, edu_img, verification_document_img;
    FrameLayout root, occupation_root, verification_document_root;
    ImageView occupation_img_photo, verification_document_img_photo, img_photo;
    ImageButton occupation_btn_close, verification_document_btn_close, btn_close;

    ArrayList<DocumentTypeList.DocumentTypeListData> documentTypeListData = new ArrayList<>();
    DocumentTypeList documentTypeList;

    List<String> documetTypeList = new ArrayList<String>();

    String documet_type_str;

    ArrayAdapter<String> adapter_document_type;

    ProgressDialog progressBar;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;
    Uri selectedImage;
    File file;
    File file_document_ver;
    MultipartBody.Part[] body;

    String user_id;
    String id;
    Uri contentUri;
    EditText editAboutMe;
    String aboutMeText = "";


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
        setContentView(R.layout.activity_insert_step7);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_insert_step7);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);

        editAboutMe = findViewById(R.id.editAboutMe);
        skip = findViewById(R.id.skip);
        editAboutMe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aboutMeText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        upload_verification_doc_img_capture = (LinearLayout) findViewById(R.id.upload_verification_doc_img_capture);
        submit_btn = (RelativeLayout) findViewById(R.id.submit_btn);
        a1 = (TextView) findViewById(R.id.a1);
        a2 = (TextView) findViewById(R.id.a2);
        a3 = (TextView) findViewById(R.id.a3);
        a4 = (TextView) findViewById(R.id.a4);
        a5 = (TextView) findViewById(R.id.a5);
        a6 = (TextView) findViewById(R.id.a6);
        verification_document_for_spinner = findViewById(R.id.verification_document_for_spinner);
        education_certificate_img_capture = findViewById(R.id.education_certificate_img_capture);
        occupation_certificate_img_capture = findViewById(R.id.occupation_certificate_img_capture);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        root = findViewById(R.id.root);
        occupation_root = findViewById(R.id.occupation_root);
        occupation_img_photo = findViewById(R.id.occupation_img_photo);
        img_photo = findViewById(R.id.img_photo);
        occupation_btn_close = findViewById(R.id.occupation_btn_close);
        btn_close = findViewById(R.id.btn_close);
        verification_document_root = findViewById(R.id.verification_doc_img_root);
        verification_document_img_photo = findViewById(R.id.verification_doc_img_photo);
        verification_document_btn_close = findViewById(R.id.verification_doc_img_btn_close);

        Intent i = getIntent();
        user_id = (i.getStringExtra("user_id"));
        //user_id = "67";


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

        a1.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a1.setTextColor(Color.WHITE);
        a2.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a2.setTextColor(Color.WHITE);
        a3.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a3.setTextColor(Color.WHITE);
        a4.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a4.setTextColor(Color.WHITE);
        a5.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a5.setTextColor(Color.WHITE);
        a6.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink_circle_progress));
        a6.setTextColor(Color.WHITE);


//        a2.setBackgroundDrawable( getResources().getDrawable(R.drawable.pink_circle_progress) );
//        a2.setTextColor(Color.WHITE);
//        a3.setBackgroundDrawable( getResources().getDrawable(R.drawable.pink_circle_progress) );
//        a3.setTextColor(Color.WHITE);
//        a4.setBackgroundDrawable( getResources().getDrawable(R.drawable.pink_circle_progress) );
//        a4.setTextColor(Color.WHITE);
//        a5.setBackgroundDrawable( getResources().getDrawable(R.drawable.pink_circle_progress) );
//        a5.setTextColor(Color.WHITE);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
*/

        upload_verification_doc_img_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!documet_type_str.equals("Select Document")) {
                    requestPermissionDocumentVerification();
                } else {
                    Toast.makeText(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, "Please select verification document", Toast.LENGTH_SHORT).show();
                }

            }
        });

        education_certificate_img_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission2();

            }
        });


        occupation_certificate_img_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission3();

            }
        });


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.setVisibility(View.GONE);
                encoded_img2 = null;
                edu_img = null;
            }
        });


        verification_document_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verification_document_root.setVisibility(View.GONE);
                encoded_img4 = null;
                verification_document_img = null;
            }
        });


        occupation_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation_root.setVisibility(View.GONE);
                encoded_img3 = null;
                occupation_img = null;
            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Create the Intent
                Intent i = new Intent(InsertStep7Activity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (documet_type_str != null && !documet_type_str.contentEquals("") && !documet_type_str.equalsIgnoreCase("Select Document")) {
                    for (int i = 0; i < documentTypeListData.size(); i++) {
                        if (documet_type_str.equals(documentTypeListData.get(i).getName())) {
                            documet_type_str = documentTypeListData.get(i).getName();
                        }
                    }
                } else {
                    documet_type_str = "";
                }
                // submitData(documet_type_str);

                if (documet_type_str.isEmpty() || uriString.isEmpty()) {
                    Toast.makeText(InsertStep7Activity.this, "You are required to upload at least one document.", Toast.LENGTH_SHORT).show();
                } else {
                    submitData(documet_type_str);
                }


            }
        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

        getdocumentTypeList();


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }


    private void requestPermissionDocumentVerification() {
        if (Build.VERSION.SDK_INT < 19) {

            System.out.println("11111111111111111111111111111111111111");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser_verification_doc_certi.launch(Intent.createChooser(intent, "Select pictures"));
        } else {
            System.out.println("2222222222222222222222222222222222222");
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser_verification_doc_certi.launch(Intent.createChooser(intent, "Select pictures"));

        }


    }

    private void requestPermission2() {
        if (Build.VERSION.SDK_INT < 19) {

            System.out.println("11111111111111111111111111111111111111");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser2.launch(Intent.createChooser(intent, "Select pictures"));
        } else {
            System.out.println("2222222222222222222222222222222222222");
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser2.launch(Intent.createChooser(intent, "Select pictures"));

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
            imgChooser3.launch(Intent.createChooser(intent, "Select pictures"));
        } else {
            System.out.println("2222222222222222222222222222222222222");
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);
            imgChooser3.launch(Intent.createChooser(intent, "Select pictures"));

        }


    }


    @Override
    public void networkAvailable() {

//        getProfileCreatedList();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdocumentTypeList();

//                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    public void submitData(String _documet_type_str) {


        System.out.println("user_id=======" + user_id);

        RequestBody documet_type_str = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(_documet_type_str));
        RequestBody _user_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody _aboutme = RequestBody.create(MediaType.parse("text/plain"), aboutMeText);
        RequestBody _profiles = RequestBody.create(MediaType.parse("text/plain"), "");


        if (verification_document_img != null) {
            try {
                File file = new File(String.valueOf(verification_document_img));
                if (file.exists()) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    verification_doc_certi_file = MultipartBody.Part.createFormData("document_img", file.getName(), requestFile);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            verification_document_img = null;
        }

        if (edu_img != null) {
            try {
                File file = new File(String.valueOf(edu_img));
                if (file.exists()) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    education_certificate_file = MultipartBody.Part.createFormData("education_certificate_file", file.getName(), requestFile);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            edu_img = null;
        }

        if (occupation_img != null) {
            try {
                File file = new File(String.valueOf(occupation_img));
                if (file.exists()) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    occupation_certificate_file = MultipartBody.Part.createFormData("occupation_certificate_file", file.getName(), requestFile);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            occupation_img = null;
        }

       /* if(imagesEncodedList!=null) {
            System.out.println("img size=========" +imagesEncodedList.size());
            for(int i=0;i<imagesEncodedList.size();i++){

                System.out.println("img 123=========" +imagesEncodedList.get(i));
            }

            img = new MultipartBody.Part[imagesEncodedList.size()];
            System.out.println("img 000=========" +img);

            for (int index = 0; index < imagesEncodedList.size(); index++) {
                Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + imagesEncodedList.get(index));
                File file = new File(imagesEncodedList.get(index));
                System.out.println("file 000=========" +file);

                RequestBody surveyBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                img[index] = MultipartBody.Part.createFormData("img[]", file.getName(), surveyBody);
            }
        }*/


        /*===========*/


        /*===========*/
        progressBar = ProgressDialog.show(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.insertStep6(_aboutme, _profiles,
                documet_type_str, education_certificate_file,
                occupation_certificate_file, verification_doc_certi_file, null,
                _user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                insertResponse = response.body();

                progressBar.dismiss();

                if (response.isSuccessful()) {

                    /*System.out.println("about resp ========" + new Gson().toJson(insertResponse));*/

                    System.out.println("about resp ========" + insertResponse.getImgnm());
                    System.out.println("about resp ========" + insertResponse.getResMsg());

                    String success = insertResponse.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(InsertStep7Activity.this,AccomodationDetailActivity.class);
                        Intent i = new Intent(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, MainActivity.class);
                        i.putExtra("user_id", user_id);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    } else {
                        Toast.makeText(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                progressBar.dismiss();

                System.out.println("err about ******" + t.toString());

                if (!isNetworkAvailable(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this)) {

                    Toast.makeText(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void getdocumentTypeList() {

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
                        document_type_spinner_data(documentTypeListData);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<DocumentTypeList> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("err profile created for******" + t.toString());

                if (!isNetworkAvailable(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this)) {
//                    Toast.makeText(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void document_type_spinner_data(List<DocumentTypeList.DocumentTypeListData> documentTypeListData) {

        documetTypeList.clear();

        documetTypeList.add("Select Document");

        if (documetTypeList.size() != 0) {
            for (int i = 0; i < documentTypeListData.size(); i++) {

                documetTypeList.add(i + 1, documentTypeListData.get(i).getName());

            }
        }
        adapter_document_type = new ArrayAdapter<String>(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, R.layout.simple_spinner_item, documetTypeList);

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

    /*=============*/
    ActivityResultLauncher<Intent> imgChooser3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data.getData() != null) {
                            uriString = String.valueOf(data.getData());
                            Uri mImageUri = data.getData();
                            occupation_root.setVisibility(View.VISIBLE);
                            occupation_img_photo.setImageURI(data.getData());
                            String n = getPathFromURI(mImageUri);
                            occupation_img = UriUtils.copyFileToInternalStorage(mImageUri, "dummy_file", com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this);

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
    ActivityResultLauncher<Intent> imgChooser2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data.getData() != null) {
                            uriString = String.valueOf(data.getData());
                            Uri mImageUri = data.getData();
                            root.setVisibility(View.VISIBLE);
                            img_photo.setImageURI(data.getData());
                            String n = getPathFromURI(mImageUri);
                            edu_img = UriUtils.copyFileToInternalStorage(mImageUri, "dummy_file", com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this);


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


    /*-----*/

    ActivityResultLauncher<Intent> imgChooser_verification_doc_certi = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data.getData() != null) {
                            Uri mImageUri = data.getData();
                            verification_document_root.setVisibility(View.VISIBLE);
                            verification_document_img_photo.setImageURI(data.getData());
                            String n = getPathFromURI(mImageUri);
                            verification_document_img = UriUtils.copyFileToInternalStorage(mImageUri, "dummy_file", com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this);

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

    /*-----*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                System.out.println("data.getClipData()========" + data.getClipData());
                System.out.println("data.getData()========" + data.getData());

                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();

                    img_array = new ArrayList<>();


                    for (int i = 0; i < 5; i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);

                        String n = getPathFromURI(uri);

                        System.out.println("n===========" + n);

                        imagesEncodedList.add(n);

                        System.out.println("000imagesEncodedList--------" + imagesEncodedList.size());
                        System.out.println("uri 1234--------" + uri);

                        if (imagesEncodedList.size() != 0) {
                            submit_btn.setVisibility(View.VISIBLE);
                        }
                        for (int j = 0; j < imagesEncodedList.size(); j++) {
                            System.out.println("111imagesEncodedList--------" + imagesEncodedList.get(j));

                        }
                        InputStream imageStream = null;
                        try {
                            imageStream = this.getContentResolver().openInputStream(uri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        encoded_img1 = encodeTobase64(yourSelectedImage);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 0, stream);

                        img_array.add(encoded_img1);
                        imageArry.add(new Image(n));
                        adapter11.notifyDataSetChanged();

                    }

                    System.out.println("size img array--------" + img_array.size());


                    Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                } else {
                    if (data.getData() != null) {
                        img_array = new ArrayList<>();

                        Uri mImageUri = data.getData();
                        mArrayUri.add(mImageUri);

                        System.out.println("00 n===========" + mArrayUri);

                        String n = getPathFromURI(mImageUri);

                        System.out.println("11 n===========" + n);
                        imagesEncodedList.add(n);
                        System.out.println("imagesEncodedList 111--------" + imagesEncodedList.size());

                        if (imagesEncodedList.size() != 0) {
                            submit_btn.setVisibility(View.VISIBLE);
                        }
                        InputStream imageStream = null;
                        try {
                            imageStream = this.getContentResolver().openInputStream(mImageUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        encoded_img1 = encodeTobase64(yourSelectedImage);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 0, stream);

                        img_array.add(encoded_img1);
                        imageArry.add(new Image(n));

                        adapter11.notifyDataSetChanged();

                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

        }

        super.onActivityResult(requestCode, resultCode, data);
    }*/


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


            imagePath = getDataColumn(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, contentUri, null, null);

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

            return getDataColumn(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(com.WadhuWarProject.WadhuWar.activity.InsertStep7Activity.this, uri, null, null);
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


    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
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
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InsertStep7Activity.super.onBackPressed();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}
package com.WadhuWarProject.WadhuWar.activity;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.ContactImageAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.Image;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.ProfileCreatedList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

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

public class AddCertificateActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_WRITE_PERMISSION = 786;
    static boolean isNetworkAvailable;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    String docId;

    String[] selectionArgs;
    String id;
    private String imagePath;

    String result;
    List<MultipartBody.Part> list = new ArrayList<>();
    private NetworkStateReceiver networkStateReceiver;
    MultipartBody.Part[] surveyImagesParts;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    Spinner profile_created_for_spinner;
    MultipartBody.Part[] img;

    int t;
    TextView txt_select;
    String str;
    LinearLayout img_capture;
    List<String> imagesEncodedList = new ArrayList<>();
    String imageEncoded;
    String encoded_img1;
    ContactImageAdapter adapter11;
    RecyclerView img_list;
    ArrayList<Image> imageArry = new ArrayList<Image>();
    ArrayList<String> img_array;
    RelativeLayout submit_btn;

    ArrayList<ProfileCreatedList.ProfileCreatedListData> profileCreatedListData = new ArrayList<>();
    ProfileCreatedList profileCreatedList;

    List<String> profileCreatedList_list = new ArrayList<String>();

    String profileCreatedList_str;

    String profileCreatedList_id;

    ArrayAdapter<String> adapter_profilecreatedfor;

    EditText aboutMe;
    String _aboutMe;

    ProgressDialog progressBar;
    InsertResponse insertResponse;
    SwipeRefreshLayout swipeRefreshLayout;
    Uri selectedImage;
    File file;
    MultipartBody.Part[] body;

    String user_id;

    UserData user;
    int img_count;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

           /* Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(i, 100);*/


            if (Build.VERSION.SDK_INT < 19) {

                System.out.println("11111111111111111111111111111111111111");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);

                imgChooser.launch(Intent.createChooser(intent, "Select pictures"));

            } else {
                System.out.println("2222222222222222222222222222222222222");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                imgChooser.launch(Intent.createChooser(intent, "Select pictures"));

//                startActivityForResult(Intent.createChooser(intent, "Select pictures"), 100);

            }

        }
    }
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
        setContentView(R.layout.activity_add_certificate);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_certificate);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        Intent i = getIntent();
        img_count = i.getIntExtra("img_count", 0);

        System.out.println("img count --------" + img_count);
        t = 2 - img_count;


        user = SharedPrefManager.getInstance(AddCertificateActivity.this).getUser();

        txt_select = findViewById(R.id.txt_select);
        img_capture = (LinearLayout) findViewById(R.id.img_capture);
        img_list = (RecyclerView) findViewById(R.id.img_list);
        submit_btn = (RelativeLayout) findViewById(R.id.submit_btn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AddCertificateActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ContextCompat.checkSelfPermission(AddCertificateActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddCertificateActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

        txt_select.setText("You can Add " + t + "  certificate");


        adapter11 = new ContactImageAdapter(this, imageArry);
        img_list.setLayoutManager(new LinearLayoutManager(this));
        img_list.setAdapter(adapter11);


        img_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission();

            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                submitData();

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AddCertificateActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(AddCertificateActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddCertificateActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
/*
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(i, 100);*/


            if (Build.VERSION.SDK_INT < 19) {
                System.out.println("11111111111111111111111111111111111111");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select pictures"), 100);
                imgChooser.launch(Intent.createChooser(intent, "Select pictures"));
            } else {
                System.out.println("2222222222222222222222222222222222222");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent,"Select pictures"), 100);
                imgChooser.launch(Intent.createChooser(intent, "Select pictures"));

            }

        }
    }

    @Override
    public void networkAvailable() {


    }

    public void submitData() {


        RequestBody _user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(user.getUser_id()));



   /*     if(imagesEncodedList!=null) {
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

        if (imageArry != null) {
            System.out.println("img size=========" + imageArry.size());
            for (int i = 0; i < imageArry.size(); i++) {

                System.out.println("imageArry img 123=========" + imageArry.get(i));
            }

            img = new MultipartBody.Part[imageArry.size()];
            System.out.println("img 000=========" + img);

            int size;
            if (imageArry.size() > 2) {
                size = 2;
            } else {
                size = imageArry.size();
            }

            for (int index = 0; index < size; index++) {

                Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + imageArry.get(index));
                File file = new File(imageArry.get(index).getImage());
                System.out.println("file 000=========" + file);

                RequestBody surveyBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                img[index] = MultipartBody.Part.createFormData("img[]", file.getName(), surveyBody);
            }
        }

        /* for (int index = 0; index < image.size(); index++) {
        Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + image.get(index));
        File file2 = new File(image.get(index));
        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file2);
        multipartTypedOutput[index] = MultipartBody.Part.createFormData("imageFiles[]", file2.getPath(), surveyBody);
    }*/


        progressBar = ProgressDialog.show(AddCertificateActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editImage(_user_id, img);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                insertResponse = response.body();

                progressBar.dismiss();

                System.out.println("insertResponse---------" + new Gson().toJson(insertResponse));
                if (response.isSuccessful()) {


                    String success = insertResponse.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(AddCertificateActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddCertificateActivity.this, MyPhotosActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(AddCertificateActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err about ******" + t.toString());

                if (!isNetworkAvailable(AddCertificateActivity.this)) {

                    Toast.makeText(AddCertificateActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AddCertificateActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(AddCertificateActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /*-----*/
    ActivityResultLauncher<Intent> imgChooser = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        System.out.println("data.getClipData()========" + data.getClipData());
                        System.out.println("data.getData()========" + data.getData());

                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();

                            System.out.println("mClipData=========" + mClipData.getItemCount());
                            img_array = new ArrayList<>();

                            int f = 2 - img_count;

                            /*-----*/

                            if (mClipData.getItemCount() >= f) {
                                for (int i = 0; i < f; i++) {

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
                                        imageStream = getContentResolver().openInputStream(uri);
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
                            } else {
                                for (int i = 0; i < mClipData.getItemCount(); i++) {

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
                                        imageStream = getContentResolver().openInputStream(uri);
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

                            /*-----*/


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
                                    imageStream = getContentResolver().openInputStream(mImageUri);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }


                                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                                encoded_img1 = encodeTobase64(yourSelectedImage);

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 0, stream);

                                img_array.add(encoded_img1);
                                imageArry.add(0, new Image(n));

                                adapter11.notifyDataSetChanged();

                            }
                        }


                    }

                }

            });

    /*-----*/


  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                System.out.println("data.getClipData()========" + data.getClipData());
                System.out.println("data.getData()========" + data.getData());

                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();

                    img_array = new ArrayList<>();

                    int f = 5 - img_count;
                    for (int i = 0; i < f; i++) {

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
                        imageArry.add(0, new Image(n));

                        adapter11.notifyDataSetChanged();

                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
//                    .show();
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


            imagePath = getDataColumn(AddCertificateActivity.this, contentUri, null, null);

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

            return getDataColumn(AddCertificateActivity.this, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(AddCertificateActivity.this, uri, null, null);
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

        if (t == 2) {

            System.out.println("back not allow--------" + t);

            /*dialog apply*/
            final Dialog dialog = new Dialog(AddCertificateActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.upload_img_box);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.getWindow().setGravity(Gravity.CENTER);


            dialog.show();


            // Hide after some seconds
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            };


            handler.postDelayed(runnable, 2000);


            /*dialog apply*/


        } else {
            System.out.println("back allow--------" + t);

            super.onBackPressed();
        }

    }


}
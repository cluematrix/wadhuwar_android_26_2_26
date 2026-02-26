package com.WadhuWarProject.WadhuWar.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
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
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.UriUtils;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HideDeleteActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText reason_txt, enter_delete_txt;
    TextView dlt_profile_btn,hideProfileButton,unHideProfileButton;
    static boolean isNetworkAvailable;
    UserData user;
    ProgressDialog progressBar;
    ImageView img_photo;
    ImageButton btn_close;
    FrameLayout root;
    MultipartBody.Part filePart;
    LinearLayout img_capture;

    InsertResponse insertResponse;
    String img, encoded_img;
    String id, docId;
    String[] selectionArgs;
    private String imagePath;


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
        setContentView(R.layout.activity_hide_delete);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hide_delete);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dlt_profile_btn = findViewById(R.id.dlt_profile_btn);
        reason_txt = findViewById(R.id.reason_txt);
        btn_close = findViewById(R.id.btn_close);
        enter_delete_txt = findViewById(R.id.enter_delete_txt);
        img_capture = findViewById(R.id.img_capture);
        root = findViewById(R.id.root);
        img_photo = findViewById(R.id.img_photo);
//        hideProfileButton = findViewById(R.id.hide_profile_btn);
//        unHideProfileButton= findViewById(R.id.unHide_profile_btn);

        user = SharedPrefManager.getInstance(HideDeleteActivity.this).getUser();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Delete Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.setVisibility(View.GONE);
                encoded_img = null;
                img = null;
            }
        });


        img_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadFile();

            }
        });

//        hideProfileButton.setOnClickListener(v -> {
//            setHideProfile(String.valueOf(user.getUser_id()),2);
//        });
//
//
//        unHideProfileButton.setOnClickListener(v -> {
//            setHideProfile(String.valueOf(user.getUser_id()),0);
//        });

        dlt_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _reason = reason_txt.getText().toString().trim();
                String _delete = enter_delete_txt.getText().toString().trim();

                if (checkValidation()) {

                    if (!isNetworkAvailable(HideDeleteActivity.this)) {

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


                        submitData(_reason, String.valueOf(user.getUser_id()));


                    }


                }

            }
        });

        String isHidden = SharedPrefManager.getInstance(HideDeleteActivity.this).getString();

//        if (isHidden.equals("2")){
//            unHideProfileButton.setVisibility(View.VISIBLE);
//            hideProfileButton.setVisibility(View.GONE);
//        }else {
//            unHideProfileButton.setVisibility(View.GONE);
//            hideProfileButton.setVisibility(View.VISIBLE);
//        }

    }

    private void submitData(String reason, String user_id) {
        progressBar = ProgressDialog.show(HideDeleteActivity.this, "", "Please Wait...");

        RequestBody _user_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody _reason = RequestBody.create(MediaType.parse("text/plain"), reason);

        if (img != null) {
            try {
                File file = new File(String.valueOf(img));
                if (file.exists()) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            img = null;
        }


        /*===========*/
        progressBar = ProgressDialog.show(HideDeleteActivity.this, "", "Please Wait...");


        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.deleteProfile(_user_id, _reason, filePart);

        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                insertResponse = response.body();

                if (response.isSuccessful()) {

                    progressBar.dismiss();


                    if (insertResponse.getResid().equals("200")) {
                        SharedPrefManager.getInstance(HideDeleteActivity.this).clear();
                        Intent intent = new Intent(HideDeleteActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(HideDeleteActivity.this, insertResponse.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                System.out.println("err basic info******" + t.toString());
                progressBar.dismiss();

                if (!isNetworkAvailable(HideDeleteActivity.this)) {
                    Toast.makeText(HideDeleteActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(HideDeleteActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

//    private void setHideProfile(String userId,int hide){
//        progressBar = ProgressDialog.show(HideDeleteActivity.this, "", "Please Wait...");
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<ResponseBody> userResponse = apiService.hideProfile(userId, hide);
//
//        userResponse.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()){
//                    progressBar.dismiss();
//                    if (hide == 2){
//                        SharedPrefManager.getInstance(HideDeleteActivity.this).saveString("0");
//                    }else {
//                        SharedPrefManager.getInstance(HideDeleteActivity.this).saveString("2");
//
//                    }
//                    hideProfileButton.setVisibility(hideProfileButton.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//
//                    unHideProfileButton.setVisibility(unHideProfileButton.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//
////                    finish();
//                }else {
//                    progressBar.dismiss();
//                    Toast.makeText(HideDeleteActivity.this,"Unable To hide",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                progressBar.dismiss();
//            }
//        });
//    }

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


    private Boolean checkValidation() {


        if (reason_txt.getText().toString().isEmpty()) {
            reason_txt.setError("Please enter Reason!!");
            reason_txt.requestFocus();
            return false;
        } else if (reason_txt.getText().toString().length() < 5) {
            reason_txt.setError("Reason should be atleast 5 character long");
            reason_txt.requestFocus();
            return false;
        }

        if (enter_delete_txt.getText().toString().isEmpty()) {
            enter_delete_txt.setError("Please enter DELETE word!!");
            enter_delete_txt.requestFocus();
            return false;
        } else if (!enter_delete_txt.getText().toString().equals("DELETE")) {
            enter_delete_txt.setError("Enter word same as DELETE spell (in captital letter)!!");
            enter_delete_txt.requestFocus();
            return false;
        }


        return true;
    }


    /*=============*/
    /*=============*/
    ActivityResultLauncher<Intent> imgChooser3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data.getData() != null) {
                            Uri mImageUri = data.getData();
                            root.setVisibility(View.VISIBLE);
                            img_photo.setImageURI(data.getData());
                            String n = getPathFromURI(mImageUri);
                            img = UriUtils.copyFileToInternalStorage(mImageUri, "dummy_file", HideDeleteActivity.this);

                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(mImageUri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                            encoded_img = encodeTobase64(yourSelectedImage);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 0, stream);

                        }


                    }
                }
            });
    /*=============*/    /*=============*/


    private void uploadFile() {

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


            imagePath = getDataColumn(HideDeleteActivity.this, contentUri, null, null);

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

            return getDataColumn(HideDeleteActivity.this, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(HideDeleteActivity.this, uri, null, null);
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
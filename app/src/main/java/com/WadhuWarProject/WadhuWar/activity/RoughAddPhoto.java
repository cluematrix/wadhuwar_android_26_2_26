//package com.WadhuWarProject.WadhuWar.activity;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.ClipData;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.loader.content.CursorLoader;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.WadhuWarProject.WadhuWar.R;
//import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
//import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
//import com.WadhuWarProject.WadhuWar.adapter.ContactImageAdapter;
//import com.WadhuWarProject.WadhuWar.adapter.MyPhotosAdapter;
//import com.WadhuWarProject.WadhuWar.api.Api;
//import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
//import com.WadhuWarProject.WadhuWar.model.FetchProfile;
//import com.WadhuWarProject.WadhuWar.model.Image;
//import com.WadhuWarProject.WadhuWar.model.InsertResponse;
//import com.WadhuWarProject.WadhuWar.model.ProfileCreatedList;
//import com.WadhuWarProject.WadhuWar.model.SliderListImage;
//import com.WadhuWarProject.WadhuWar.model.UserData;
//import com.google.gson.Gson;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class AddPhotosActivity extends AppCompatActivity implements  NetworkStateReceiver.NetworkStateReceiverListener ,
//        ActivityCompat.OnRequestPermissionsResultCallback {
//
//    String result;
//    List<MultipartBody.Part> list = new ArrayList<>();
//    private NetworkStateReceiver networkStateReceiver;
//    static boolean isNetworkAvailable;
//    MultipartBody.Part[] surveyImagesParts ;
//    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
//    Spinner profile_created_for_spinner;
//    MultipartBody.Part[] img;
//    private static final int REQUEST_WRITE_PERMISSION = 786;
//    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
//
//
//    TextView txt_select;
//    String str;
//    LinearLayout img_capture;
//    List<String> imagesEncodedList;
//    String imageEncoded;
//    String encoded_img1;
//    ContactImageAdapter adapter11;
//    RecyclerView img_list;
//    ContactImageAdapter imageAdapter;
//    ArrayList<Image> imageArry = new ArrayList<Image>();
//    ArrayList<String> img_array;
//    RelativeLayout submit_btn;
//
//    ArrayList<ProfileCreatedList.ProfileCreatedListData> profileCreatedListData =  new ArrayList<>();
//    ProfileCreatedList profileCreatedList;
//
//    List<String> profileCreatedList_list = new ArrayList<String>();
//
//    String profileCreatedList_str;
//
//    String profileCreatedList_id;
//
//    ArrayAdapter<String> adapter_profilecreatedfor;
//
//    EditText aboutMe;
//    String _aboutMe;
//
//    ProgressDialog progressBar;
//    InsertResponse insertResponse;
//    SwipeRefreshLayout swipeRefreshLayout;
//    Uri selectedImage;
//    File file;
//    MultipartBody.Part[] body;
//
//    String user_id;
//
//    UserData user;
//    int img_count;
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            i.setType("image/*");
//            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            startActivityForResult(i, 100);
//
//        }
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_photos);
//
//        Intent i = getIntent();
//        img_count =  i.getIntExtra("img_count",0);
//
//        System.out.println("img count --------" +img_count );
//        int t = 5- img_count;
//
//
//
//
//        user = SharedPrefManager.getInstance(AddPhotosActivity.this).getUser();
//
//        txt_select = findViewById(R.id.txt_select);
//        img_capture = (LinearLayout) findViewById(R.id.img_capture);
//        img_list =  (RecyclerView) findViewById(R.id.img_list);
//        submit_btn =  (RelativeLayout) findViewById(R.id.submit_btn);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AddPhotosActivity.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//
//            if (ContextCompat.checkSelfPermission(AddPhotosActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
//                ActivityCompat.requestPermissions(AddPhotosActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
//            }
//        }
//
//        txt_select .setText("You can Add "+ t + "  Images");
//
//
//
//        adapter11 = new ContactImageAdapter(this, imageArry);
//        img_list.setLayoutManager(new LinearLayoutManager(this));
//        img_list.setAdapter(adapter11);
//
//
//        img_capture.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//                requestPermission();
//
//            }
//        });
//
//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//                submitData();
//
//            }
//        });
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AddPhotosActivity.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//
//            if (ContextCompat.checkSelfPermission(AddPhotosActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
//                ActivityCompat.requestPermissions(AddPhotosActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
//            }
//        }
//
//
//        /*if net off*/
//        networkStateReceiver = new NetworkStateReceiver();
//        networkStateReceiver.addListener(this);
//        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
//        /*end code if net off*/
//
//    }
//
//    private void requestPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
//        } else {
//
//            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            i.setType("image/*");
//            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            startActivityForResult(i, 100);
//
//        }
//
//
//    }
//
//
//
//    @Override
//    public void networkAvailable() {
//
//
//
//
//    }
//
//
//    public  void submitData(){
//
//
//
//
//
//        RequestBody _user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(user.getUser_id()));
//
//
//
//        if(imagesEncodedList!=null) {
//            for(int i=0;i<imagesEncodedList.size();i++){
//
//                System.out.println("img 123=========" +imagesEncodedList.get(i));
//            }
//
//            img = new MultipartBody.Part[imagesEncodedList.size()];
//            System.out.println("img 000=========" +img);
//
//            for (int index = 0; index < imagesEncodedList.size(); index++) {
//                Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + imagesEncodedList.get(index));
//                File file = new File(imagesEncodedList.get(index));
//                System.out.println("file 000=========" +file);
//
//                RequestBody surveyBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                img[index] = MultipartBody.Part.createFormData("img[]", file.getName(), surveyBody);
//            }
//        }
//
//
//
//
//
//
//
//        progressBar = ProgressDialog.show(AddPhotosActivity.this, "", "Please Wait...");
//
//        Api apiService = RetrofitClient.getApiService();
//        Call<InsertResponse> userResponse = apiService.editImage(_user_id,img);
//        userResponse.enqueue(new Callback<InsertResponse>() {
//
//            @Override
//            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
//                insertResponse = response.body();
//
//                progressBar.dismiss();
//
//                if (response.isSuccessful()) {
//
//
//                    String success = insertResponse.getResid();
//
//                    if (success.equals("200")) {
//                        Toast.makeText(AddPhotosActivity.this,insertResponse.getResMsg(),Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(AddPhotosActivity.this,MyPhotosActivity.class);
//                        startActivity(i);
//                        finish();
//
//                    }else {
//                        Toast.makeText(AddPhotosActivity.this,insertResponse.getResMsg(),Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<InsertResponse> call, Throwable t) {
//                progressBar.dismiss();
//
//                System.out.println("err about ******" + t.toString());
//
//                if(!isNetworkAvailable(AddPhotosActivity.this)){
//
//                    Toast.makeText(AddPhotosActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(AddPhotosActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//
//
//
//    }
//
//
//
//
//
//    @Override
//    public void networkUnavailable() {
//        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Toast.makeText(AddPhotosActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();
//
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            // When an Image is picked
//            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
//
//                // Get the Image from data
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                imagesEncodedList = new ArrayList<String>();
//                if(data.getData()!=null){
//                    img_array = new ArrayList<>();
//
//                    Uri mImageUri=data.getData();
//                    mArrayUri.add(mImageUri);
//                    // Get the cursor
//                    Cursor cursor = getContentResolver().query(mImageUri,
//                            filePathColumn, null, null, null);
//                    // Move to first row
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    imageEncoded  = cursor.getString(columnIndex);
//
//                    imagesEncodedList.add(imageEncoded);
//                    System.out.println("imagesEncodedList 111--------" + imagesEncodedList.size());
//
//                    if(imagesEncodedList.size()!=0){
//                        submit_btn.setVisibility(View.VISIBLE);
//                    }
//
//                    System.out.println("imageEncoded111--------" + imageEncoded);
//
//                    System.out.println("mImageUri 111--------" + mImageUri);
//
//                    InputStream imageStream = null;
//                    try {
//                        imageStream = this.getContentResolver().openInputStream(mImageUri);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    String imagePath = getRealPathFromURI(AddPhotosActivity.this, data.getData());
//
//                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(imagePath);
//                    encoded_img1=encodeTobase64(yourSelectedImage);
//
//                    System.out.println("encoded_img1 111--------" + encoded_img1);
//                    System.out.println("imagePath 111--------" + imagePath);
//                    System.out.println("yourSelectedImage 111--------" + yourSelectedImage);
//
//                    img_array.add(encoded_img1);
////                    imageArry.add(new Image(encoded_img1));
//                    imageArry.add(0,new Image(encoded_img1));
//                    adapter11.notifyDataSetChanged();
//                    cursor.close();
//
//                } else {
//                    if (data.getClipData() != null) {
//                        ClipData mClipData = data.getClipData();
//
//                        img_array = new ArrayList<>();
//
//                        int f = 5 - img_count;
//
//                        for (int i = 0; i < f; i++) {
//
//                            ClipData.Item item = mClipData.getItemAt(i);
//                            Uri uri = item.getUri();
//                            mArrayUri.add(uri);
//
//                            String[] proj = {MediaStore.Images.Media.DATA};
//                            CursorLoader loader = new CursorLoader(AddPhotosActivity.this, uri, proj, null, null, null);
//                            Cursor cursor = loader.loadInBackground();
//                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                            cursor.moveToFirst();
//
//
//
//                            result = cursor.getString(column_index);
//                            imagesEncodedList.add(result);
//
//
//                            System.out.println("result--------" + column_index);
//                            System.out.println("imagesEncodedList--------" + imagesEncodedList.size());
//                            System.out.println("uri 1234--------" + uri);
//
//                            if(imagesEncodedList.size()!=0){
//                                submit_btn.setVisibility(View.VISIBLE);
//                            }
//                            for(int j=0;j<imagesEncodedList.size();j++)
//                            {
//                                System.out.println("imagesEncodedList--------" + imagesEncodedList.get(j));
//
//                            }
//                            InputStream imageStream = null;
//                            try {
//                                imageStream = this.getContentResolver().openInputStream(uri);
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
//                            encoded_img1=encodeTobase64(yourSelectedImage);
//
//                            img_array.add(encoded_img1);
//                            imageArry.add(new Image(encoded_img1));
//                            adapter11.notifyDataSetChanged();
//
//
//                            cursor.close();
//
//                        }
//
//                        System.out.println("size img array--------" + img_array.size());
//
//
//                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
//                    }
//                }
//            } else {
//                Toast.makeText(this, "You haven't picked Image",
//                        Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
////            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
////                    .show();
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public static String getRealPathFromURI(Context context, Uri contentURI) {
//        String result = null;
//        String[] projection = {MediaStore.Images.Media.DATA};
//
//        try {
//            Cursor cursor = context.getContentResolver().query(contentURI, projection, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(projection[0]);
//            result = cursor.getString(columnIndex);
//            cursor.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static String encodeTobase64(Bitmap image) {
//        Bitmap immagex=image;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] b = baos.toByteArray();
//        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
//        return imageEncoded;
//    }
//
//
//    public static boolean isNetworkAvailable(Context context) {
//        isNetworkAvailable = false;
//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity == null) {
//            return false;
//        } else {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        isNetworkAvailable = true;
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }//isNetworkAvailable()
//
//
//
//
//
//}
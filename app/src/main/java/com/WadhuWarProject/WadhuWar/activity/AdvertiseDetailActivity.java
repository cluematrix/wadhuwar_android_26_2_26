package com.WadhuWarProject.WadhuWar.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.adapter.BlogImagesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.AdvertiseDetail;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertiseDetailActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    Toolbar toolbar;
    String id;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<AdvertiseDetail.AdvertiseDetailData> advertiseDetailData = new ArrayList<>();
    AdvertiseDetail advertiseDetail;
    RelativeLayout progress_bar;
    TextView title, desc;
    LinearLayout mainLL;
    VideoView videoView;
    MediaController mediaController;



    BlogImagesAdapter imagesAdapter;
    ViewPager viewPager;
    WebView webView; // Use WebView for displaying the video
//    ImageView back_btn, share;

    // Other member variables...

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
        setContentView(R.layout.activity_advertise_detail);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_advertise_detail);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        // Initialization of UI elements
        ImageView back_btn = findViewById(R.id.back_btn);
        ImageView share = findViewById(R.id.share);
        toolbar = findViewById(R.id.toolbar);
        progress_bar = findViewById(R.id.progress_bar);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        mainLL = findViewById(R.id.mainLL);
        viewPager = findViewById(R.id.viewpager);
        webView = findViewById(R.id.webView); // Initialize WebView

        // Configure WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // Back button listener
        back_btn.setOnClickListener(v -> onBackPressed());

        // Intent handling
        Intent i = getIntent();
        if (i.getStringExtra("id") != null) {
            id = i.getStringExtra("id");

        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1 for image, 2 for video
                if (advertiseDetailData.get(0).getType().trim().equals("1")) {
                    if (advertiseDetailData.get(0).getImages().size() != 0) {
                        Picasso.get().load(advertiseDetailData.get(0).getImages().get(0).getImgs()).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                try {
                                    File cachePath = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "shared_images");
                                    if (!cachePath.exists()) {
                                        cachePath.mkdirs();
                                    }

                                    File imageFile = new File(cachePath, "advertise_" + System.currentTimeMillis() + ".jpg");
                                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                    outputStream.flush();
                                    outputStream.close();

                                    Uri contentUri = FileProvider.getUriForFile(
                                            getApplicationContext(),
                                            getPackageName() + ".fileprovider",
                                            imageFile
                                    );

                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("image/*");
                                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() +
                                            "\n\nCheck out the app:\n https://play.google.com/store/apps/details?id=com.WadhuWarProject.WadhuWar&pcampaignid=web_share");
                                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    startActivity(Intent.createChooser(shareIntent, "Share Image"));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) { }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) { }
                        });

                    } else {
                        // No image available
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() +
                                "\n\nCheck out the app:\n https://play.google.com/store/apps/details?id=com.WadhuWarProject.WadhuWar&pcampaignid=web_share");
                        share.setType("text/plain");
                        startActivity(share);
                    }

                } else if(advertiseDetailData.get(0).getType().trim().equals("2")){
                    // For video sharing
                    String s = advertiseDetailData.get(0).getVideolink();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, s + "\n" + desc.getText().toString() +
                            "\n\nCheck out the app:\n https://play.google.com/store/apps/details?id=com.WadhuWarProject.WadhuWar&pcampaignid=web_share");
                    intent.setType("text/plain");
                    startActivity(intent);
                } else {
                    // VIDEO SHARE (MP4)
                    String videoUrl = advertiseDetailData.get(0).getVideo();
                    progress_bar.setVisibility(View.VISIBLE);
                    downloadAndShareVideo(videoUrl);
                }

            }
        });

//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 1 for image, 2 for video
//                if (advertiseDetailData.get(0).getType().trim().equals("1")) {
//                    if (advertiseDetailData.get(0).getImages().size() != 0) {
//                        Picasso.get().load(advertiseDetailData.get(0).getImages().get(0).getImgs()).into(new Target() {
//                            @Override
//                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                                try {
//                                    File cachePath = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "shared_images");
//                                    if (!cachePath.exists()) {
//                                        cachePath.mkdirs();
//                                    }
//
//                                    File imageFile = new File(cachePath, "advertise_" + System.currentTimeMillis() + ".jpg");
//                                    FileOutputStream outputStream = new FileOutputStream(imageFile);
//                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                                    outputStream.flush();
//                                    outputStream.close();
//
//                                    Uri contentUri = FileProvider.getUriForFile(
//                                            getApplicationContext(),
//                                            getPackageName() + ".fileprovider",
//                                            imageFile
//                                    );
//
//                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                                    shareIntent.setType("image/*");
//                                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
//                                    shareIntent.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() +
//                                            "\n\nCheck out the app:\n https://play.google.com/store/apps/details?id=com.Buddhistawadhuwar.Buddhistawadhuwar&pcampaignid=web_share");
//                                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onBitmapFailed(Exception e, Drawable errorDrawable) { }
//
//                            @Override
//                            public void onPrepareLoad(Drawable placeHolderDrawable) { }
//                        });
//
//                    } else {
//                        // No image available
//                        Intent share = new Intent(Intent.ACTION_SEND);
//                        share.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() +
//                                "\n\nCheck out the app:\n https://play.google.com/store/apps/details?id=com.Buddhistawadhuwar.Buddhistawadhuwar&pcampaignid=web_share");
//                        share.setType("text/plain");
//                        startActivity(share);
//                    }
//
//                } else {
//                    // For video sharing
//                    String s = advertiseDetailData.get(0).getVideolink();
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.putExtra(Intent.EXTRA_TEXT, s + "\n" + desc.getText().toString() +
//                            "\n\nCheck out the app:\n https://play.google.com/store/apps/details?id=com.Buddhistawadhuwar.Buddhistawadhuwar&pcampaignid=web_share");
//                    intent.setType("text/plain");
//                    startActivity(intent);
//                }
//            }
//        });


        // Share button listener
//        share.setOnClickListener(view -> {
//            // Share functionality remains the same
//            handleShare(advertiseDetailData);
//        });

        // Fetch advertisement details
        getAdvertiseDetail();

        // Network state receiver
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

//        videoView = findViewById(R.id.videoView);

//        mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);

        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(AdvertiseDetailActivity.this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);


    }

    private void downloadAndShareVideo(String videoUrl) {

        new Thread(() -> {
            try {
                URL url = new URL(videoUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Server returned: " + connection.getResponseCode());
                }

                File videoFile = new File(
                        getCacheDir(),
                        "advertise_video_" + System.currentTimeMillis() + ".mp4"
                );

                InputStream input = connection.getInputStream();
                FileOutputStream output = new FileOutputStream(videoFile);

                byte[] buffer = new byte[8192];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }

                output.flush();
                output.close();
                input.close();

                Uri contentUri = FileProvider.getUriForFile(
                        AdvertiseDetailActivity.this,
                        getPackageName() + ".fileprovider",
                        videoFile
                );

                runOnUiThread(() -> {
                    progress_bar.setVisibility(View.GONE);

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("video/*");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "Share Video"));
                });

            } catch (Exception e) {
                Log.e("VIDEO_SHARE", "Error: ", e);
                runOnUiThread(() -> {
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(this, "Video sharing failed", Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }


    public void getAdvertiseDetail() {
        progress_bar.setVisibility(View.VISIBLE);
        Api apiService = RetrofitClient.getApiService();
        Call<AdvertiseDetail> userResponse = apiService.advertDetails("21", id);
        userResponse.enqueue(new Callback<AdvertiseDetail>() {
            @Override
            public void onResponse(Call<AdvertiseDetail> call, Response<AdvertiseDetail> response) {

                progress_bar.setVisibility(View.GONE);

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(AdvertiseDetailActivity.this,
                            "Error fetching advertisement details",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                advertiseDetail = response.body();

                advertiseDetailData = advertiseDetail.getAdvertiseList();

                for (int i = 0; i < advertiseDetailData.size(); i++) {
                    AdvertiseDetail.AdvertiseDetailData item = advertiseDetailData.get(i);
                }

                if (advertiseDetailData == null || advertiseDetailData.isEmpty()) {
                    Toast.makeText(AdvertiseDetailActivity.this, "No advertisement data found", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Set text
                title.setText(advertiseDetailData.get(0).getName());
                desc.setText(Html.fromHtml(advertiseDetailData.get(0).getDesc()));

                // Hide all views first
                viewPager.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);

                String type = advertiseDetailData.get(0).getType();
                String youtubeUrl = advertiseDetailData.get(0).getVideolink();
                String mp4Url = advertiseDetailData.get(0).getVideo();
//                String mp4Url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";

//                Toast.makeText(AdvertiseDetailActivity.this, "video" + mp4Url, Toast.LENGTH_SHORT).show();

                if ("1".equals(type)) {
                    // ✅ IMAGE
                    viewPager.setVisibility(View.VISIBLE);
                    imagesAdapter = new BlogImagesAdapter(
                            AdvertiseDetailActivity.this,
                            advertiseDetailData.get(0).getImages()
                    );
                    viewPager.setAdapter(imagesAdapter);

                } else if ("2".equals(type)) {
                    // ✅ YOUTUBE VIDEO (WebView)
                    if (youtubeUrl != null && !youtubeUrl.trim().isEmpty()) {
                        webView.setVisibility(View.VISIBLE);
                        webView.loadUrl(youtubeUrl);
                    } else {
                        Toast.makeText(AdvertiseDetailActivity.this, "Video link not available", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // ✅ MP4 VIDEO
                    if (mp4Url != null && !mp4Url.trim().isEmpty()) {

                        videoView.setVisibility(View.VISIBLE);

                        Uri videoUri = Uri.parse(mp4Url);
                        videoView.setVideoURI(videoUri);

                        videoView.setOnPreparedListener(mp -> {
                            mp.setLooping(false);
                            videoView.start();
                        });


                    } else {
                        Toast.makeText(AdvertiseDetailActivity.this, "Video not available", Toast.LENGTH_SHORT).show();
                    }
                }

                mainLL.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<AdvertiseDetail> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(AdvertiseDetailActivity.this, "Network Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleShare(ArrayList<AdvertiseDetail.AdvertiseDetailData> advertiseDetailData) {
        // Similar share functionality here...
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }

    // Implement networkAvailable and networkUnavailable methods similar to your original code
    // Any other methods you need...
}


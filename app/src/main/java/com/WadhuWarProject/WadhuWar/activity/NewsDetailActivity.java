package com.WadhuWarProject.WadhuWar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.WadhuWarProject.WadhuWar.model.NewsDetail;
import com.WadhuWarProject.WadhuWar.model.SliderListImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    Toolbar toolbar;
    String id;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<NewsDetail.NewsDetailData> newsDetailData = new ArrayList<>();
    NewsDetail newsDetail;
    RelativeLayout progress_bar;
    TextView title, desc;
    ImageView img;

    LinearLayout mainLL;

    BlogImagesAdapter imagesAdapter;
    ViewPager viewPager;
    ArrayList<SliderListImage> myList;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;
    static boolean isNetworkAvailable;

    private WebView webView; // Use WebView for playing videos
    String fileUri;


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
        setContentView(R.layout.activity_news_detail);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news_detail);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        View share = findViewById(R.id.share);
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progress_bar = findViewById(R.id.progress_bar);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        mainLL = findViewById(R.id.mainLL);
        viewPager = findViewById(R.id.viewpager);
        ImageView back_btn = findViewById(R.id.back_btn);

        internetOffRL = findViewById(R.id.internetOffRL);
        simpleProgressBar = findViewById(R.id.simpleProgressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        try_again_txt = findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = findViewById(R.id.couldnt_reach_internet_txt);

        webView = findViewById(R.id.webView); // Initialize WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()); // Set WebViewClient to handle page navigation

        back_btn.setOnClickListener(v -> onBackPressed());

        share.setOnClickListener(view -> {
            /*1 for image , 2 for video*/
            if (newsDetailData.get(0).getType().equals("1")) {
                handleImageShare();
            } else {
                handleVideoShare();
            }
        });

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Intent i = getIntent();
        id = i.getStringExtra("id");

        getNewsDetail();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void networkAvailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(() -> getNewsDetail());

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            internetOffRL.setVisibility(View.GONE);
            couldnt_reach_internet_txt.setVisibility(View.GONE);
        }, 3000);
    }

    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(NewsDetailActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        });

        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(v -> {
            try_again_txt.setVisibility(View.GONE);
            simpleProgressBar.setVisibility(View.VISIBLE);
            if (!isNetworkAvailable()) {
                simpleProgressBar.postDelayed(() -> {
                    couldnt_reach_internet_txt.postDelayed(() -> {
                        couldnt_reach_internet_txt.setVisibility(View.VISIBLE);
                        try_again_txt.setVisibility(View.VISIBLE);
                        simpleProgressBar.setVisibility(View.GONE);
                        couldnt_reach_internet_txt.postDelayed(() -> {
                            couldnt_reach_internet_txt.setVisibility(View.GONE);
                        }, 2000);
                    }, 2000);
                }, 2000);
            } else {
                networkUnavailable();
            }
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    public static boolean isNetworkAvailable(Context context) {
        isNetworkAvailable = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        isNetworkAvailable = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void getNewsDetail() {
        if (newsDetailData != null)
            newsDetailData.clear();

        progress_bar.setVisibility(View.VISIBLE);
        Api apiService = RetrofitClient.getApiService();
        Call<NewsDetail> userResponse = apiService.newsDetails("20", id);
        userResponse.enqueue(new Callback<NewsDetail>() {
            @Override
            public void onResponse(Call<NewsDetail> call, Response<NewsDetail> response) {
                swipeRefreshLayout.setRefreshing(false);
                newsDetail = response.body();
                progress_bar.setVisibility(View.GONE);
                mainLL.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && newsDetail != null) {
                    newsDetailData = newsDetail.getNewsList();
                    if (!newsDetailData.isEmpty()) {
                        title.setText(newsDetailData.get(0).getName());
                        desc.setText(Html.fromHtml(newsDetailData.get(0).getDesc()));

                        /*1 for image , 2 for video*/
                        if (newsDetailData.get(0).getType().equals("1")) {
                            webView.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            imagesAdapter = new BlogImagesAdapter(NewsDetailActivity.this, newsDetailData.get(0).getImages());
                            viewPager.setAdapter(imagesAdapter);
                        } else {
                            viewPager.setVisibility(View.GONE);
                            webView.setVisibility(View.VISIBLE);
                            String videoUrl = newsDetailData.get(0).getVideolink();
                            webView.loadUrl(videoUrl); // Load YouTube video URL directly
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsDetail> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NewsDetailActivity.this, "Failed to load news detail.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleImageShare() {
        if (newsDetailData.get(0).getImages().size() != 0) {
            Uri imageUri = Uri.parse(newsDetailData.get(0).getImages().get(0).getImgs());

            Picasso.get().load(imageUri).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try {
                        File cachePath = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "shared_images");
                        if (!cachePath.exists()) {
                            cachePath.mkdirs();
                        }
                        File imageFile = new File(cachePath, "shared_image_" + System.currentTimeMillis() + ".jpg");
                        FileOutputStream stream = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();

                        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(),
                                getPackageName() + ".fileprovider", imageFile);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() +
                                "\n\nCheck out the app:\nhttps://wadhuwar.com/Matrimonial");
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
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() + "\n\nCheck out the app:\n https://wadhuwar.com/Matrimonial");
            share.setType("text/plain");
            startActivity(share);
        }
    }

    private void handleVideoShare() {
        String s = newsDetailData.get(0).getVideolink();
        // Extract video ID from the URL if needed
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, s + "\n" + desc.getText().toString() + "\n\nCheck out the app:\n https://wadhuwar.com/Matrimonial");
        intent.setType("text/plain");
        startActivity(intent);
    }
}
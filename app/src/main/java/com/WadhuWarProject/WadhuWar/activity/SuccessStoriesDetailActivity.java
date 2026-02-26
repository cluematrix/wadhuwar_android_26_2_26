package com.WadhuWarProject.WadhuWar.activity;

import static com.squareup.picasso.Picasso.LoadedFrom;

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
import android.util.Log;
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
import com.WadhuWarProject.WadhuWar.model.BlogDetail;
import com.WadhuWarProject.WadhuWar.model.CommentList;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessStoriesDetailActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    String fileUri;

    RelativeLayout add_commnet_block;
    Toolbar toolbar;
    String id;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<BlogDetail.BlogDetailData> blogDetailData = new ArrayList<>();
    BlogDetail blogDetail;
    RelativeLayout progressBar;
    TextView title, desc, commentCount;
    LinearLayout mainLL;

    BlogImagesAdapter imagesAdapter;
    ViewPager viewPager;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView tryAgainText, couldntReachInternetText;
    static boolean isNetworkAvailable;

    WebView webView; // WebView to display videos
    ImageView backBtn;
    private Uri imageUri;

    ArrayList<CommentList.CommentListData> commentListData;
    CommentList commentList;

    ImageView share;


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
        setContentView(R.layout.activity_success_stories_detail);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_success_stories_detail);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        share = findViewById(R.id.share);
        add_commnet_block = findViewById(R.id.add_commnet_block);
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progressBar = findViewById(R.id.progress_bar);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        mainLL = findViewById(R.id.mainLL);
        viewPager = findViewById(R.id.viewpager);
        backBtn = findViewById(R.id.back_btn);
        commentCount = findViewById(R.id.comment_count);

        internetOffRL = findViewById(R.id.internetOffRL);
        simpleProgressBar = findViewById(R.id.simpleProgressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        tryAgainText = findViewById(R.id.try_again_txt);
        couldntReachInternetText = findViewById(R.id.couldnt_reach_internet_txt);

        webView = findViewById(R.id.webView); // Initialize WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()); // Ensure links open in WebView

        commentListData = new ArrayList<>();

        backBtn.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        if (intent.getStringExtra("id") != null) {
            id = intent.getStringExtra("id");
        }

        add_commnet_block.setOnClickListener(view -> {
            Intent i = new Intent(SuccessStoriesDetailActivity.this, CommentSuccessStoryActivity.class);
            i.putExtra("successstoryid", id);
            startActivity(i);
        });

        share.setOnClickListener(view -> shareContent());

        getSuccessStoriesDetail();
        showCommentData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void networkAvailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            getSuccessStoriesDetail();
            showCommentData();
        });

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            internetOffRL.setVisibility(View.GONE);
            couldntReachInternetText.setVisibility(View.GONE);
        }, 3000);
    }

    public void showCommentData() {
        if (commentListData != null) {
            commentListData.clear();
        }

        progressBar.setVisibility(View.VISIBLE);
        Api apiService = RetrofitClient.getApiService();
        Call<CommentList> userResponse = apiService.commentList(id);
        userResponse.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                commentList = response.body();

                if (response.isSuccessful()) {
                    if (commentList.getResid().equals("200")) {
                        commentCount.setText(commentList.getCommentList().size() + " comments");
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(SuccessStoriesDetailActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        });

        internetOffRL.setVisibility(View.VISIBLE);

        tryAgainText.setOnClickListener(v -> {
            tryAgainText.setVisibility(View.GONE);
            simpleProgressBar.setVisibility(View.VISIBLE);
            if (!isNetworkAvailable()) {
                simpleProgressBar.postDelayed(() ->
                        couldntReachInternetText.postDelayed(() -> {
                            couldntReachInternetText.setVisibility(View.VISIBLE);
                            tryAgainText.setVisibility(View.VISIBLE);
                            simpleProgressBar.setVisibility(View.GONE);
                            couldntReachInternetText.postDelayed(() -> {
                                couldntReachInternetText.setVisibility(View.GONE);
                            }, 2000);
                        }, 2000), 2000);
            } else {
                networkUnavailable();
            }
        });
    }

    public void getSuccessStoriesDetail() {
        if (blogDetailData != null) {
            blogDetailData.clear();
        }

        progressBar.setVisibility(View.VISIBLE);
        Api apiService = RetrofitClient.getApiService();
        Call<BlogDetail> userResponse = apiService.blogDetails("19", id);
        userResponse.enqueue(new Callback<BlogDetail>() {
            @Override
            public void onResponse(Call<BlogDetail> call, Response<BlogDetail> response) {
                swipeRefreshLayout.setRefreshing(false);
                blogDetail = response.body();
                progressBar.setVisibility(View.GONE);
                mainLL.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    blogDetailData = blogDetail.getBlogList();

                    title.setText(blogDetailData.get(0).getName());
                    desc.setText(Html.fromHtml(blogDetailData.get(0).getDesc()));

                    /* 1 for image , 2 for video */
                    if (blogDetailData.get(0).getType().equals("1")) {
                        webView.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                        imagesAdapter = new BlogImagesAdapter(SuccessStoriesDetailActivity.this, blogDetailData.get(0).getImages());
                        viewPager.setAdapter(imagesAdapter);
                    } else {
                        viewPager.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        String videolink = blogDetailData.get(0).getVideolink();
                        webView.loadUrl(videolink); // Load the video URL directly to the WebView
                    }
                }
            }

            @Override
            public void onFailure(Call<BlogDetail> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
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

    private void shareContent() {
        if (blogDetailData.get(0).getType().equals("1")) { // Image post
            if (!blogDetailData.get(0).getImages().isEmpty()) {
                String imageUrl = blogDetailData.get(0).getImages().get(0).getImgs();

                Picasso.get().load(imageUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            // Save image in the correct folder defined in file_paths.xml
                            File mydir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "shared_images");
                            if (!mydir.exists() && !mydir.mkdirs()) {
                                Log.e("DirectoryError", "Failed to create directory!");
                                return;
                            }

                            File imageFile = new File(mydir, System.currentTimeMillis() + ".jpg");

                            FileOutputStream outputStream = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();

                            Uri uri = FileProvider.getUriForFile(
                                    SuccessStoriesDetailActivity.this,
                                    "com.WadhuWarProject.WadhuWar.fileprovider", // Make sure this matches manifest
                                    imageFile
                            );

                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image/*");
                            share.putExtra(Intent.EXTRA_STREAM, uri);
                            share.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() +
                                    "\n\nCheck out the app:\n https://wadhuwar.com/Matrimonial");
                            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(Intent.createChooser(share, "Share Image"));

                        } catch (IOException e) {
                            Log.e("FileSaveError", "Failed to save image: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Log.e("ImageLoadError", "Failed to load image: " + e.getMessage());
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

            } else {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() +
                        "\n\nCheck out the app:\n https://wadhuwar.com/Matrimonial");
                share.setType("text/plain");
                startActivity(Intent.createChooser(share, "Share Text"));
            }

        } else {
            String videoLink = blogDetailData.get(0).getVideolink();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, videoLink + "\n" + desc.getText().toString() +
                    "\n\nCheck out the app:\n https://wadhuwar.com/Matrimonial");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share Video Link"));
        }
    }


//   todo ::  commented by sagar on 31 mar 2025
//        private void shareContent() {
//            if (blogDetailData.get(0).getType().equals("1")) {
//                if (blogDetailData.get(0).getImages().size() != 0) {
//                    imageUri = Uri.parse(blogDetailData.get(0).getImages().get(0).getImgs());
//                    Picasso.get().load(blogDetailData.get(0).getImages().get(0).getImgs()).into(new Target() {
//                        @Override
//                        public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
//                            try {
//                                File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
//                                if (!mydir.exists()) {
//                                    mydir.mkdirs();
//                                }
//
//                                fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
//                                FileOutputStream outputStream = new FileOutputStream(fileUri);
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                                outputStream.flush();
//                                outputStream.close();
//
//                                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
//                                Intent share = new Intent(Intent.ACTION_SEND);
//                                share.setType("image/*");
//                                share.putExtra(Intent.EXTRA_STREAM, uri);
//                                share.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() + "\n\nCheck out the app:\n https://wadhuwar.com/Matrimonial");
//                                startActivity(Intent.createChooser(share, "Share Image"));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//                        }
//                    });
//                } else {
//                    Intent share = new Intent(Intent.ACTION_SEND);
//                    share.putExtra(Intent.EXTRA_TEXT, desc.getText().toString() + "\n\nCheck out the app:\n https://wadhuwar.com/Matrimonial");
//                    share.setType("text/plain");
//                    startActivity(share);
//                }
//            } else {
//                String s = blogDetailData.get(0).getVideolink();
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_SEND);
//                intent.putExtra(Intent.EXTRA_TEXT, s + "\n" + desc.getText().toString() + "\n\nCheck out the app:\n https://wadhuwar.com/Matrimonial");
//                intent.setType("text/plain");
//                startActivity(intent);
//            }
//        }
}
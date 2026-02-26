package com.WadhuWarProject.WadhuWar.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.TransactionAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;

import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.PaymentResponse;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountTransactionActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private ArrayList<PaymentResponse.Payments> paymentsList;
    private ArrayList<PaymentResponse.Plans> plansArrayList;
    private TextView responseStatus;


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
        setContentView(R.layout.activity_account_transaction);

        View root1 = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
//        setContentView(R.layout.activity_account_transaction);

        // Initialize RecyclerView and data list
        paymentsList = new ArrayList<>();
        plansArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        adapter = new TransactionAdapter(this);
        responseStatus = findViewById(R.id.responseStatus);
        responseStatus.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setTitle("Account Transaction");


        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Log for debugging
        Log.d("AccountTransaction", "RecyclerView and Adapter are initialized.");

        // Fetch data from the API
        fetchTransactions();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Perform back action
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void fetchTransactions() {
        Api apiService = RetrofitClient.getApiService();
        int userId = SharedPrefManager.getInstance(this).getUser().getUser_id();
        Log.d("userid", "fetchTransactions: " + userId);
        Call<PaymentResponse> call = apiService.upgradePlanOffline(userId);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Clear the old list and add new items
                    paymentsList.clear();
                    plansArrayList.clear();
                    paymentsList.addAll(response.body().getPayments());
                    plansArrayList.addAll(response.body().getPlans());
                    Log.d("Payments", "Payments List Size: " + paymentsList.size());

                    // Check if the list is populated before updating the adapter
                    if (paymentsList != null && !paymentsList.isEmpty()) {
                        adapter.setList(paymentsList); // Ensure adapter has a valid method to update the list
                        adapter.setPlansArrayList(plansArrayList); // Ensure adapter has a valid method to update the list
                    } else {
                        Log.e("Payments", "No payments data received.");
                        responseStatus.setVisibility(View.VISIBLE);
                        responseStatus.setText("No payments data received.");
                    }
                    if (plansArrayList != null && !plansArrayList.isEmpty()){
                        responseStatus.setVisibility(View.GONE);
                        adapter.setList(paymentsList);
                        adapter.setPlansArrayList(plansArrayList);

                    } else {
                        Log.e("Payments", "No plans data received.");
                        responseStatus.setVisibility(View.VISIBLE);
                        responseStatus.setText("No plans data received.");

                    }

                    Log.d("Payments", "Data fetched successfully");
                } else {
                    if (response.code() == 400) {
                        String errorMessage = null;
                        try {

                            Log.e("Error", "API Response Error: " + errorMessage);
                            errorMessage = response.errorBody().string();
                            responseStatus.setVisibility(View.VISIBLE);
                            responseStatus.setText(errorMessage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);

                        }
                        Log.e("Error", "API Response Error: " + errorMessage);
                    }

                    // Log error details for better debugging
                    Log.d("Payments", "Failed to fetch data. Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                // Log the failure reason
                Log.e("Payments", "API call failed: " + t.getMessage(), t);
                responseStatus.setVisibility(View.VISIBLE);
                responseStatus.setText("API call failed: " + t.getMessage());
            }
        });
    }
}


package com.WadhuWarProject.WadhuWar.activity;

import static com.WadhuWarProject.WadhuWar.activity.ImagesActivity.isNetworkAvailable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.WadhuWarProject.WadhuWar.model.CommonResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.radiobutton.MaterialRadioButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReasonForReportActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Button btn_report, btn_cancel;
    EditText edt_report_reason;
    TextView user_report_name, report_name;
    LinearLayout list_of_reason_layout,list_of_comment_reason_layout;
    RelativeLayout desc_report_reason_layout;
    MaterialRadioButton fake_profile_info;
    MaterialRadioButton multiple_profiles;
    MaterialRadioButton phone_number_is_incorrect;
    MaterialRadioButton photos_are_fake;
    MaterialRadioButton has_send_abusive_emails;
    MaterialRadioButton is_already_married;
    MaterialRadioButton asking_for_money;
    MaterialRadioButton other_misuse_reason;

    MaterialRadioButton it_spam;
    MaterialRadioButton nudity_or_sexual_activity;
    MaterialRadioButton hate_speech_or_symbols;
    MaterialRadioButton violence_or_dangerous_organisations;
    MaterialRadioButton intellectual_property_violation;
    MaterialRadioButton false_information;
    MaterialRadioButton i_just_dont_like_it;
    MaterialRadioButton something_else;

    String detail_report_reason, people_userid, comment_id, f_name = "", m_name = "", l_name = "";
    UserData user;
    boolean isCallFromComment = false;


    @SuppressLint("MissingInflatedId")
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
        setContentView(R.layout.activity_reason_for_report);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reason_for_report);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Reason for Reporting");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //get ids
        btn_report = findViewById(R.id.btn_report);
        btn_cancel = findViewById(R.id.btn_cancel);
        edt_report_reason = findViewById(R.id.edt_report_reason);
        list_of_reason_layout = findViewById(R.id.list_of_reason_layout);
        list_of_comment_reason_layout = findViewById(R.id.list_of_comment_reason_layout);
        desc_report_reason_layout = findViewById(R.id.desc_report_reason_layout);
        user_report_name = findViewById(R.id.user_report_name);
        report_name = findViewById(R.id.report_name);
        fake_profile_info = findViewById(R.id.fake_profile_info);
        multiple_profiles = findViewById(R.id.multiple_profiles);
        phone_number_is_incorrect = findViewById(R.id.phone_number_is_incorrect);
        photos_are_fake = findViewById(R.id.photos_are_fake);
        has_send_abusive_emails = findViewById(R.id.has_send_abusive_emails);
        is_already_married = findViewById(R.id.is_already_married);
        asking_for_money = findViewById(R.id.asking_for_money);
        other_misuse_reason = findViewById(R.id.other_misuse_reason);

        it_spam = findViewById(R.id.it_spam);
        nudity_or_sexual_activity = findViewById(R.id.nudity_or_sexual_activity);
        hate_speech_or_symbols = findViewById(R.id.hate_speech_or_symbols);
        violence_or_dangerous_organisations = findViewById(R.id.violence_or_dangerous_organisations);
        intellectual_property_violation = findViewById(R.id.intellectual_property_violation);
        false_information = findViewById(R.id.false_information);
        i_just_dont_like_it = findViewById(R.id.i_just_dont_like_it);
        something_else = findViewById(R.id.something_else);


        Intent i = getIntent();

        if (i.getStringExtra("userid") != null) {
            people_userid = i.getStringExtra("userid");
        }

        if (i.getStringExtra("Fname") != null) {
            f_name = i.getStringExtra("Fname");
        }

        if (i.getStringExtra("Mname") != null) {
            m_name = i.getStringExtra("Mname");
        }

        if (i.getStringExtra("Lname") != null) {
            l_name = i.getStringExtra("Lname");
        }

        if (i.getStringExtra("CommentID") != null) {
            comment_id = i.getStringExtra("CommentID");
        }

        isCallFromComment = i.getBooleanExtra("isCallFromComment", false);


        user = SharedPrefManager.getInstance(ReasonForReportActivity.this).getUser();


        user_report_name.setText(Html.fromHtml("You are raising a compliant against " + "<font color=\"#45b3e0\">" + f_name + " "
                + m_name + " " + l_name + "</font>" + " with Wadhuwar team"));


        if (isCallFromComment) {
            list_of_reason_layout.setVisibility(View.GONE);
            list_of_comment_reason_layout.setVisibility(View.VISIBLE);

            //set onclick listner
            it_spam.setOnClickListener(this);
            nudity_or_sexual_activity.setOnClickListener(this);
            hate_speech_or_symbols.setOnClickListener(this);
            violence_or_dangerous_organisations.setOnClickListener(this);
            intellectual_property_violation.setOnClickListener(this);
            false_information.setOnClickListener(this);
            i_just_dont_like_it.setOnClickListener(this);
            something_else.setOnClickListener(this);
        } else {
            list_of_reason_layout.setVisibility(View.VISIBLE);
            list_of_comment_reason_layout.setVisibility(View.GONE);

            //set onclick listner
            fake_profile_info.setOnClickListener(this);
            multiple_profiles.setOnClickListener(this);
            phone_number_is_incorrect.setOnClickListener(this);
            photos_are_fake.setOnClickListener(this);
            has_send_abusive_emails.setOnClickListener(this);
            is_already_married.setOnClickListener(this);
            asking_for_money.setOnClickListener(this);
            other_misuse_reason.setOnClickListener(this);
        }

    }

    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.fake_profile_info:
//                showReasonBox("fake_profile_info");
//                break;
//            case R.id.multiple_profiles:
//                showReasonBox("multiple_profiles");
//                break;
//            case R.id.phone_number_is_incorrect:
//                showReasonBox("phone_number_is_incorrect");
//                break;
//            case R.id.photos_are_fake:
//                showReasonBox("photos_are_fake");
//                break;
//            case R.id.has_send_abusive_emails:
//                showReasonBox("has_send_abusive_emails");
//                break;
//            case R.id.is_already_married:
//                showReasonBox("is_already_married");
//                break;
//            case R.id.asking_for_money:
//                showReasonBox("asking_for_money");
//                break;
//            case R.id.other_education:
//                showReasonBox("other_misuse_reason");
//                break;
//
//            case R.id.it_spam:
//                showReasonBox("it_spam");
//                break;
//            case R.id.nudity_or_sexual_activity:
//                showReasonBox("nudity_or_sexual_activity");
//                break;
//            case R.id.hate_speech_or_symbols:
//                showReasonBox("hate_speech_or_symbols");
//                break;
//            case R.id.violence_or_dangerous_organisations:
//                showReasonBox("violence_or_dangerous_organisations");
//                break;
//            case R.id.intellectual_property_violation:
//                showReasonBox("intellectual_property_violation");
//                break;
//            case R.id.false_information:
//                showReasonBox("false_information");
//                break;
//            case R.id.i_just_dont_like_it:
//                showReasonBox("i_just_dont_like_it");
//                break;
//            case R.id.something_else:
//                showReasonBox("something_else");
//                break;
//        }
//    }
    public void onClick(View v) {
        if (v.getId() == R.id.fake_profile_info) {
            showReasonBox("fake_profile_info");
        } else if (v.getId() == R.id.multiple_profiles) {
            showReasonBox("multiple_profiles");
        } else if (v.getId() == R.id.phone_number_is_incorrect) {
            showReasonBox("phone_number_is_incorrect");
        } else if (v.getId() == R.id.photos_are_fake) {
            showReasonBox("photos_are_fake");
        } else if (v.getId() == R.id.has_send_abusive_emails) {
            showReasonBox("has_send_abusive_emails");
        } else if (v.getId() == R.id.is_already_married) {
            showReasonBox("is_already_married");
        } else if (v.getId() == R.id.asking_for_money) {
            showReasonBox("asking_for_money");
        } else if (v.getId() == R.id.other_education) {
            showReasonBox("other_misuse_reason");
        } else if (v.getId() == R.id.it_spam) {
            showReasonBox("it_spam");
        } else if (v.getId() == R.id.nudity_or_sexual_activity) {
            showReasonBox("nudity_or_sexual_activity");
        } else if (v.getId() == R.id.hate_speech_or_symbols) {
            showReasonBox("hate_speech_or_symbols");
        } else if (v.getId() == R.id.violence_or_dangerous_organisations) {
            showReasonBox("violence_or_dangerous_organisations");
        } else if (v.getId() == R.id.intellectual_property_violation) {
            showReasonBox("intellectual_property_violation");
        } else if (v.getId() == R.id.false_information) {
            showReasonBox("false_information");
        } else if (v.getId() == R.id.i_just_dont_like_it) {
            showReasonBox("i_just_dont_like_it");
        } else if (v.getId() == R.id.something_else) {
            showReasonBox("something_else");
        }
    }
    private void showReasonBox(String title_reason) {
        list_of_reason_layout.setVisibility(View.GONE);
        desc_report_reason_layout.setVisibility(View.VISIBLE);

        if (title_reason.equalsIgnoreCase("fake_profile_info")) {
            report_name.setText(fake_profile_info.getText().toString());
        } else if (title_reason.equalsIgnoreCase("multiple_profiles")) {
            report_name.setText(multiple_profiles.getText().toString());
        } else if (title_reason.equalsIgnoreCase("phone_number_is_incorrect")) {
            report_name.setText(phone_number_is_incorrect.getText().toString());
        } else if (title_reason.equalsIgnoreCase("photos_are_fake")) {
            report_name.setText(photos_are_fake.getText().toString());
        } else if (title_reason.equalsIgnoreCase("has_send_abusive_emails")) {
            report_name.setText(has_send_abusive_emails.getText().toString());
        } else if (title_reason.equalsIgnoreCase("is_already_married")) {
            report_name.setText(is_already_married.getText().toString());
        } else if (title_reason.equalsIgnoreCase("asking_for_money")) {
            report_name.setText(asking_for_money.getText().toString());
        } else if (title_reason.equalsIgnoreCase("other_misuse_reason")) {
            report_name.setText(other_misuse_reason.getText().toString());
        }else if (title_reason.equalsIgnoreCase("it_spam")) {
            report_name.setText(it_spam.getText().toString());
        } else if (title_reason.equalsIgnoreCase("nudity_or_sexual_activity")) {
            report_name.setText(nudity_or_sexual_activity.getText().toString());
        } else if (title_reason.equalsIgnoreCase("hate_speech_or_symbols")) {
            report_name.setText(hate_speech_or_symbols.getText().toString());
        } else if (title_reason.equalsIgnoreCase("violence_or_dangerous_organisations")) {
            report_name.setText(violence_or_dangerous_organisations.getText().toString());
        } else if (title_reason.equalsIgnoreCase("intellectual_property_violation")) {
            report_name.setText(intellectual_property_violation.getText().toString());
        } else if (title_reason.equalsIgnoreCase("false_information")) {
            report_name.setText(false_information.getText().toString());
        } else if (title_reason.equalsIgnoreCase("i_just_dont_like_it")) {
            report_name.setText(i_just_dont_like_it.getText().toString());
        } else if (title_reason.equalsIgnoreCase("something_else")) {
            report_name.setText(something_else.getText().toString());
        }

        detail_report_reason = edt_report_reason.getText().toString();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_report_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edt_report_reason.setHintTextColor(Color.GRAY);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edt_report_reason.setHintTextColor(Color.GRAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_report_reason.getText() != null) {
                    if (!edt_report_reason.getText().toString().isEmpty()) {
                        if (isCallFromComment) {
                            report_comment(title_reason, edt_report_reason.getText().toString());
                        } else {
                            report_user(title_reason, edt_report_reason.getText().toString());
                        }
                    } else {
                        edt_report_reason.setHintTextColor(Color.RED);
                        Toast.makeText(ReasonForReportActivity.this, "Please provide more details", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edt_report_reason.setHintTextColor(Color.RED);
                    Toast.makeText(ReasonForReportActivity.this, "Please provide more details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void report_user(String title_reason, String detail_report_reason) {
        Api apiService = RetrofitClient.getApiService();
        Call<CommonResponse> commonResponseCall = apiService.reportAccount(people_userid,
                String.valueOf(user.getUser_id()), title_reason, detail_report_reason);
        commonResponseCall.enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                if (response.isSuccessful()) {
                    if (commonResponse.getResid() == 200) {
                        Toast.makeText(ReasonForReportActivity.this, "Report this user", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ReasonForReportActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);

                    } else {
                        Toast.makeText(ReasonForReportActivity.this, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                System.out.println("err UpdateResponse******" + t.toString());
                if (!isNetworkAvailable(ReasonForReportActivity.this)) {
//                    Toast.makeText(ReasonForReportActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(ReasonForReportActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void report_comment(String title_reason, String detail_report_reason) {
        if (comment_id != null) {
            Api apiService = RetrofitClient.getApiService();
            Call<CommonResponse> commonResponseCall = apiService.reportComment(comment_id,
                    String.valueOf(user.getUser_id()), title_reason, detail_report_reason);
            commonResponseCall.enqueue(new Callback<CommonResponse>() {

                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    CommonResponse commonResponse = response.body();
                    if (response.isSuccessful()) {
                        if (commonResponse.getResid() == 200) {
                            Toast.makeText(ReasonForReportActivity.this, "Report this comment", Toast.LENGTH_SHORT).show();

                            finish();

                        } else {
                            Toast.makeText(ReasonForReportActivity.this, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                    System.out.println("err UpdateResponse******" + t.toString());
                    if (!isNetworkAvailable(ReasonForReportActivity.this)) {
//                        Toast.makeText(ReasonForReportActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                    } else {
//                        Toast.makeText(ReasonForReportActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, "Id not be Null", Toast.LENGTH_SHORT).show();
        }
    }
}
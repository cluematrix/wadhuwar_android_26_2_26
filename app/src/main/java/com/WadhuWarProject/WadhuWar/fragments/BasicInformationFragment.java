package com.WadhuWarProject.WadhuWar.fragments;


import static android.text.method.DialerKeyListener.CHARACTERS;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.AboutMeAndMembershipActivity;
import com.WadhuWarProject.WadhuWar.activity.AccomodationDetailActivity;
import com.WadhuWarProject.WadhuWar.activity.LoginActivity;
import com.WadhuWarProject.WadhuWar.adapter.PhoneNumberAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.PersonalDataInsert1;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;


public class BasicInformationFragment extends Fragment implements  NetworkStateReceiver.NetworkStateReceiverListener{

    private static final int PASSWORD_LENGTH = 12; // Replace with your desired length
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int ACCOUNT_PICKER_REQUEST_CODE = 1000;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int years_form_date;
    TextView back_btn,btn_date,in_date;
    private NetworkStateReceiver networkStateReceiver;
    RelativeLayout next_btn;
    LinearLayout ProgressLL;
    EditText full_name,email_edt,password_edt,mobile_nu,location;
    private RadioButton radioButton;
    private int mYear, mMonth, mDay;
    Spinner gender_spinner;
    String gendor_str;
    List<String> gendor;
    static boolean isNetworkAvailable;
    ScrollView sv_feature;
    ScrollView sv;
    Toolbar toolbar;
    AppBarLayout toolbar_appbar;
    Window window;
    String day ;
    String month;
    String year ;
    String user_id;
    RelativeLayout toolbar1,toolbar2;
    TextView login;
    RelativeLayout submit_btn,submit;
    ProgressDialog progressBar;
    LinearLayout signupfreeLL;
    String _gender;
    EditText first_name,middle_name,last_name,email,alternate_email,mobile,whatapp_mobile,password,confirm_password,age,reference_code;
    int age_num;
    PersonalDataInsert1 personalDataInsert1;
    ImageView back;
    UserData otpUserData;


    public BasicInformationFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }
        setHasOptionsMenu(true);
    }



    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_personal_detail, container, false);
        submit_btn = (RelativeLayout) view.findViewById(R.id.submit_btn);
        ProgressLL = (LinearLayout) view.findViewById(R.id.ProgressLL);
        gender_spinner = (Spinner) view.findViewById(R.id.gender_spinner);

        btn_date = (TextView) view.findViewById(R.id.btn_date);
        in_date = (TextView) view.findViewById(R.id.in_date);
        login = (TextView) view.findViewById(R.id.login);
        first_name =  view.findViewById(R.id.first_name);
        middle_name =  view.findViewById(R.id.middle_name);
        last_name =  view.findViewById(R.id.last_name);
        email =  view.findViewById(R.id.email);
        alternate_email =  view.findViewById(R.id.alternate_email);
        mobile =  view.findViewById(R.id.mobile);
        whatapp_mobile =  view.findViewById(R.id.whatapp_mobile);
        password =  view.findViewById(R.id.password);
        confirm_password =  view.findViewById(R.id.confirm_password);
        age =  view.findViewById(R.id.age);
        signupfreeLL =  view.findViewById(R.id.signupfreeLL);
        reference_code =  view.findViewById(R.id.reference_code);


        //    todo ::     added by sagar on 10 April 2025 -------------------------------------------------
        submit = view.findViewById(R.id.submit);
        back = view.findViewById(R.id.back);
        View registrationLayoutFirst = view.findViewById(R.id.registration_layout_first);
        View registrationLayoutSecond = view.findViewById(R.id.registration_layout_second);
        registrationLayoutFirst.setVisibility(View.VISIBLE);
        registrationLayoutSecond.setVisibility(View.GONE);
        back.setVisibility(View.GONE);

        pickAccount();
        // Password suggestion logic
        password.setOnFocusChangeListener((view2, hasFocus) -> {
            if (hasFocus && password.getText().toString().isEmpty()) {
                String suggestedPassword = generatePassword();
                password.setHint("Suggested: " + suggestedPassword);
            }
        });

        password.setOnClickListener(view1 -> {
            if (password.getText().toString().isEmpty() &&
                    password.getHint() != null &&
                    password.getHint().toString().startsWith("Suggested: ")) {

                String hintText = password.getHint().toString();
                String suggested = hintText.substring(hintText.indexOf("Suggested: ") + "Suggested: ".length());
                password.setText(suggested);
                confirm_password.setText(suggested);
            }
        });


        submit.setOnClickListener(view1 -> {
            if (checkValidationForFormFirst(email, mobile, whatapp_mobile, password, confirm_password)) {
//                    handleViewAndValidation(registrationLayoutFirst, registrationLayoutSecond);
                registrationLayoutFirst.setVisibility(View.GONE);
                registrationLayoutSecond.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);

            }
        });

        back.setOnClickListener(view12 -> {
            registrationLayoutFirst.setVisibility(View.VISIBLE);
            registrationLayoutSecond.setVisibility(View.GONE);
            back.setVisibility(View.GONE);


        });


        /*- todo ----------------------------------- till here  ---------code by sagar --------------*/



        /*------current date--------*/

        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(todaysdate);
        System.out.println("current date==========" + date);

        /*------current date--------*/



        final Calendar c = Calendar.getInstance();
        mYear = c.get(YEAR);
        mMonth = c.get(MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        gendor = new ArrayList<String>();
        gendor.add("Select Gender");
        gendor.add("Male/पुरुष");
        gendor.add("Female/स्त्री");
        gendor.add("Others");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gendor);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(dataAdapter);

        signupfreeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.scrollTo(0, sv.getTop());
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                in_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                years_form_date = getDiffYears( date, in_date.getText().toString());

                                age.setText(String.valueOf(years_form_date));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        in_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                in_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                                years_form_date = getDiffYears( date, in_date.getText().toString());

                                age.setText(String.valueOf(years_form_date));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                gendor_str = gender_spinner.getItemAtPosition(position).toString();

                System.out.println("gendor------" + gendor_str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

        /*toolbae code*/
        toolbar_appbar = (AppBarLayout) view.findViewById(R.id.toolbar_appbar);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar1 = (RelativeLayout) view.findViewById(R.id.toolbar1);
        toolbar2 = (RelativeLayout) view.findViewById(R.id.toolbar2);
        sv=(ScrollView) view.findViewById(R.id.sv_feature);


        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);



        toolbar_appbar.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.INVISIBLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);

        window = getActivity().getWindow();

        sv.smoothScrollTo(0,0);
        sv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollChanged() {

                if(sv.getScrollY() > 3800){

                    toolbar_appbar.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);


                    ((AppCompatActivity)getActivity()).getSupportActionBar().show();

//                    window.setStatusBarColor(Color.parseColor("#EC5252"));
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);


                }
                else{
                    toolbar_appbar.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);

                    ((AppCompatActivity)getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);

                }

            }
        });
        /*end toolbae code*/


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String _first_name= first_name.getText().toString().trim();
                String _middle_name= middle_name.getText().toString().trim();
                String _last_name= last_name.getText().toString().trim();
//                String _gender= gendor_str;
                String _birthdate = in_date.getText().toString();
                String _email= email.getText().toString().trim();
                String _alternate_email= alternate_email.getText().toString().trim();
                String _mobile= mobile.getText().toString().trim();
                String _whatapp_mobile= whatapp_mobile.getText().toString().trim();
                String _age= age.getText().toString().trim();
                String _password= password.getText().toString().trim();
                String _confirm_password= confirm_password.getText().toString().trim();
                String _reference_code = reference_code.getText().toString().trim();





                if(gendor_str.equals("Male/पुरुष")){
                    _gender = "Male";
                }else if(gendor_str.equals("Female/स्त्री")){
                    _gender = "Female";

                }else{
                    _gender = gendor_str;
                }

//                years_form_date = getDiffYears( date, _birthdate);

                System.out.println("111_gender==========" +  _gender) ;
                System.out.println("years_form_date==========" +  years_form_date) ;


//                Intent i = new Intent(getActivity(), AccomodationDetailActivity.class);
//                startActivity(i);orm

                if (checkValidation()) {

                    if (!isNetworkAvailable(getActivity())) {

                        Snackbar mSnackbar = Snackbar.make(view, "Please Check Internet Connection!", Snackbar.LENGTH_LONG);
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


                        System.out.println("_first_name --" + _first_name);
                        System.out.println("_middle_name --" + _middle_name);
                        System.out.println("_last_name --" + _last_name);
                        System.out.println("_gender --" + _gender);
                        System.out.println("_birthdate --" + _birthdate);
                        System.out.println("_email --" + _email);
                        System.out.println("_alternate_email --" + _alternate_email);
                        System.out.println("_mobile --" + _mobile);
                        System.out.println("_whatapp_mobile --" + _whatapp_mobile);
                        System.out.println("_age --" + _age);
                        System.out.println("_password --" + _password);
                        System.out.println("_reference_code --" + _reference_code);


                        submitData(_first_name, _middle_name,_last_name,_gender,_birthdate,_email,_alternate_email,_mobile,_whatapp_mobile,_age,_password,_reference_code);


                    }


                }

            }
        });


        return view;

    }

    //    added by sagar on 10 April 2025
    private void autoFillFormFirstData() {

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_NUMBERS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_PHONE_NUMBERS}, 101);
        } else {
            showPhonePickerDialog();
        }
    }
    // added by sagar on 10 april 2025
    private boolean checkValidationForFormFirst(EditText email, EditText mobile, EditText whatsapp_mobile, EditText password, EditText confirmPassword) {
        // Validate Email
        String emailText = email.getText().toString().trim();
        if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Enter a valid email address");
            email.requestFocus();
            return false;
        }

        // Validate Mobile Number
        String mobileText = mobile.getText().toString().trim();
        if (mobileText.isEmpty() || mobileText.length() != 10) {
            mobile.setError("Enter a valid 10-digit mobile number");
            mobile.requestFocus();
            return false;
        }

        // Validate WhatsApp Number
        String whatsappMobileText = whatsapp_mobile.getText().toString().trim();
        if (whatsappMobileText.isEmpty() || whatsappMobileText.length() != 10) {
            whatsapp_mobile.setError("Enter a valid 10-digit WhatsApp number");
            whatsapp_mobile.requestFocus();
            return false;
        }

        // Validate Password
        String passwordText = password.getText().toString();
        if (passwordText.isEmpty() || passwordText.length() < 8) {
            password.setError("Password must be at least 6 characters long");
            password.requestFocus();
            return false;
        }

        // Confirm Password
        String confirmPasswordText = confirmPassword.getText().toString();
        if (!confirmPasswordText.equals(passwordText)) {
            confirmPassword.setError("Passwords do not match");
            confirmPassword.requestFocus();
            return false;
        }

        return true;
    }
    @SuppressLint("HardwareIds")
    private String getPhoneNumber() {
        TelephonyManager telephonyManager =
                (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_NUMBERS)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        return telephonyManager.getLine1Number();
    }

    // Get phone numbers from contacts and show in dialog
    private void showPhonePickerDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_phone_login, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.contacts);
        Button noneOfTheAboveButton = dialogView.findViewById(R.id.btn_none_of_the_above);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> phoneNumbers = getPhoneNumbers();
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            phoneNumbers = new ArrayList<>();
            phoneNumbers.add("No phone numbers available");
        }


        dialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView)
                .setCancelable(false);
        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        noneOfTheAboveButton.setOnClickListener(v -> {
            noneOfTheAboveButton.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
            dialog.dismiss();
//            showAlternativeLoginDialog();
        });

        for (String contact : phoneNumbers) {
            if (contact.equals("No SIM detected") || contact.equals("No phone numbers available")) {
                noneOfTheAboveButton.setText("Try Another Way");
            }

        }

        PhoneNumberAdapter adapter = new PhoneNumberAdapter(phoneNumbers, selectedNumber -> {
            if (mobile != null) {

                if (selectedNumber.equals("No SIM detected")) {
                    Toast.makeText(getContext(), "No SIM detected", Toast.LENGTH_SHORT).show();
                    mobile.setText("");
                    dialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
                    dialog.dismiss();
                    return null;

                } else if (selectedNumber.equals("No phone numbers available")) {

                    Toast.makeText(getContext(), "No phone numbers available", Toast.LENGTH_SHORT).show();
                    mobile.setText("");
                    dialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
                    dialog.dismiss();
                    return null;
                } else {

                    mobile.setText(removePrefix91(selectedNumber));

//
//                    mobile.setText(selectedNumber.replace("91",""));

                    dialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
                    dialog.dismiss();

                    // Toast.makeText(LoginActivity.this, "Selected: " + selectedNumber, Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        });
        recyclerView.setAdapter(adapter);

        dialog.show();
    }

    // Get phone numbers from contacts and show in dialg
    private List<String> getPhoneNumbers() {
        List<String> numbers = new ArrayList<>();
        String simNumber = getPhoneNumber();
        if (simNumber != null) {
            numbers.add(simNumber);
        } else {
            numbers.add("No SIM detected");
        }
        return numbers;
    }

    public String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    public static String removePrefix91(String number) {
        // Check if the length is 12 and starts with "91"
        if (number.length() == 12 && number.startsWith("91")) {
            // Remove the "91" prefix
            return number.substring(2);
        }
        // Return the original number if conditions are not met
        return number;
    }


    @Override
    public void onResume() {
        super.onResume();


//        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
//        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.GET_ACCOUNTS}, 1);
//        AccountManager manager = (AccountManager) getActivity().getSystemService(Context.ACCOUNT_SERVICE);
//
//        for (Account account : manager.getAccounts()) {
//            if (account.type.equalsIgnoreCase("com.google")) {
//                Log.e(TAG, "Mail: "+account.name);
//                email.setText(account.name);
//            }
//        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACCOUNT_PICKER_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK && data != null) {
                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                if (accountName != null) {
                    email.setText(accountName);
                    autoFillFormFirstData();

                } else {
                    autoFillFormFirstData();

                    Toast.makeText(requireContext(), "No account selected", Toast.LENGTH_SHORT).show();
                }
            } else {
                autoFillFormFirstData();

                Toast.makeText(requireContext(), "Account selection canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void pickAccount() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            intent = AccountManager.newChooseAccountIntent(
                    null, // No pre-selected account
                    null, // No allowed account types
                    new String[]{"com.google"}, // Filter to Google accounts
                    null, // Description text
                    null, // Auth token type
                    null, // Add account required features
                    null  // Add account options bundle
            );
        }

        startActivityForResult(intent, ACCOUNT_PICKER_REQUEST_CODE);
    }

    /*=== todo ======================== till here ========================*/

    public  void submitData(String _first_name,String _middle_name,String _last_name,String _gender,String _birthdate,String _email,String _alternate_email,
                            String _mobile,String _whatapp_mobile,String _age,String _password, String _reference_code){

        progressBar = ProgressDialog.show(getActivity(), "", "Please Wait...");



        Api apiService = RetrofitClient.getApiService();
        Call<PersonalDataInsert1> userResponse = apiService.insertStep1(_first_name, _middle_name,_last_name,_gender,_birthdate,_email,_alternate_email,
                _mobile,_whatapp_mobile,_password,_age,_reference_code);

        userResponse.enqueue(new Callback<PersonalDataInsert1>() {

            @Override
            public void onResponse(Call<PersonalDataInsert1> call, Response<PersonalDataInsert1> response) {
                personalDataInsert1 = response.body();

                if (response.isSuccessful()) {

                    progressBar.dismiss();

//                    System.out.println("basic info resp ========" + new Gson().toJson(personalDataInsert1));
                    String success = personalDataInsert1.getResid();

                    if (success.equals("200")) {
                        // Save user_id to SharedPrefManager
                        SharedPrefManager.getInstance(getActivity())
                                .saveString(String.valueOf(personalDataInsert1.getUser_id()));
                        Toast.makeText(getActivity(),personalDataInsert1.getResMsg(),Toast.LENGTH_SHORT).show();

//                        Intent i = new Intent(getActivity(), AboutMeAndMembershipActivity.class);
//                        i.putExtra("user_id",personalDataInsert1.getUser_id());
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                            if (!TextUtils.isEmpty(token)) {
                                Log.d("device_id", "onResponse: " + token);
                                otpVerification(token, personalDataInsert1.getUser_id());
                            } else {
                                Log.w("FirebaseToken", "Token should not be null...");
                            }
                        });

//                        startActivity(i);

                    }else {
                        Toast.makeText(getActivity(),personalDataInsert1.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<PersonalDataInsert1> call, Throwable t) {

                System.out.println("err basic info******" + t.toString());
                progressBar.dismiss();

                if(!isNetworkAvailable(getActivity())){
                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), "Please Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void otpVerification(String device_id, String user_id) {
        progressBar = ProgressDialog.show(getActivity(), "", "Please wait...");
        Api apiService = RetrofitClient.getApiService();
        Call<UserData> userResponse = apiService.verifyOTP(user_id, device_id);
        userResponse.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                Log.d("device_id", "onResponse: " + device_id);
                progressBar.dismiss();
                otpUserData = response.body();
                if (response.isSuccessful() && otpUserData.getResid().equals("200")) {
                    SharedPrefManager.getInstance(requireContext()).saveUser(otpUserData);
                    Intent i = new Intent(getActivity(), AboutMeAndMembershipActivity.class);
                    i.putExtra("user_id",personalDataInsert1.getUser_id());
                    i.putExtra("gender",_gender);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(requireContext(), "Verification failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.dismiss();
            }
        });
    }


    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }


    private Boolean checkValidation(){


        if (first_name.getText().toString().isEmpty()) {
            first_name.setError("Please enter first name");
            first_name.requestFocus();
            return  false;
        }else if (first_name.getText().toString().length()<2) {
            first_name.setError("Name should be atleast 2 character long");
            first_name.requestFocus();
            return  false;
        }


        if (middle_name.getText().toString().isEmpty()) {
            middle_name.setError("Please enter middle name");
            middle_name.requestFocus();
            return  false;
        }else if (middle_name.getText().toString().length()<2) {
            middle_name.setError("Name should be atleast 2 character long");
            middle_name.requestFocus();
            return  false;
        }


        if (last_name.getText().toString().isEmpty()) {
            last_name.setError("Please enter last name");
            last_name.requestFocus();
            return  false;
        }else if (last_name.getText().toString().length()<2) {
            last_name.setError("Name should be atleast 2 character long");
            last_name.requestFocus();
            return  false;
        }

        View selectedView_gen = gender_spinner.getSelectedView();
        if (selectedView_gen != null && selectedView_gen instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView_gen;
            if (selectedTextView.getText().equals("Select Gender")) {
                selectedTextView.setError("Please Select Gender");
                gender_spinner.requestFocus();
                selectedTextView.setTextColor(Color.RED);
                return  false;
            }

        }

        if (in_date.getText().toString().isEmpty()) {
            in_date.setError("Birth date is required");
            in_date.requestFocus();
            return  false;
        }else if(years_form_date < 18){
            in_date.setError("Minimum age should be 18 !");
            Toast.makeText(getActivity(),"Minimum age should be 18 !",Toast.LENGTH_SHORT).show();

            in_date.requestFocus();

            return  false;

        }

        if (email.getText().toString().isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return  false;
        }else{
            {
                if (email.getText().toString().matches(emailPattern)){

                }
                else
                {
                    email.setError("Email is not valid!");
                    email.requestFocus();
                    return  false;
                }
            }
        }

        if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please enter mobile number");
            mobile.requestFocus();
            return  false;
        } else if (mobile.getText().toString().length()!=10) {
            mobile.setError("Mobile number should be 10 digit");
            mobile.requestFocus();
            return  false;
        }

        if (whatapp_mobile.getText().toString().isEmpty()) {
            whatapp_mobile.setError("Please enter mobile number");
            whatapp_mobile.requestFocus();
            return  false;
        } else if (whatapp_mobile.getText().toString().length()!=10) {
            whatapp_mobile.setError("Mobile number should be 10 digit");
            whatapp_mobile.requestFocus();
            return  false;
        }



        /*if (age.getText().toString().isEmpty()) {
            age.setError("Please enter Age");
            age.requestFocus();
            return  false;
        }else if(age_num<18){
            age.setError("Minimum age should be 18 !");
            age.requestFocus();
            return  false;
        }*/

        if (password.getText().toString().isEmpty()) {
            password.setError("Please enter Password");
            password.requestFocus();
            return  false;
        } else if (password.getText().toString().length()<5) {
            password.setError("Password should be atleast 5 character long");
            password.requestFocus();
            return  false;
        }

        if (confirm_password.getText().toString().isEmpty()) {
            confirm_password.setError("Please enter Confirm Password");
            confirm_password.requestFocus();
            return  false;
        }


        if(!password.getText().toString().contentEquals(confirm_password.getText().toString())){
            confirm_password.setError("Password not match.");
            confirm_password.requestFocus();
            return  false;
        }



        return true;
    }





    public static int getDiffYears(String first, String last) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Calendar a= null;
        Calendar b = null;
        try {

            a = getCalendar(format.parse(first));

            b = getCalendar(format.parse(last));

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        int diff = b.get(YEAR) - a.get(YEAR);
        int diff = a.get(YEAR) - b.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
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




}




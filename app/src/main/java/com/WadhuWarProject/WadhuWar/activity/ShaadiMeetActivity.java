package com.WadhuWarProject.WadhuWar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.WadhuWarProject.WadhuWar.R;

import java.util.Calendar;

public class ShaadiMeetActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView edit1,edit2;



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
        setContentView(R.layout.activity_shaadi_meet);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shaadi_meet);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edit1 =  findViewById(R.id.edit1);
        edit2 =  findViewById(R.id.edit2);
        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Shaadi Meet");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShaadiMeetActivity.this);

                View viewInflated = LayoutInflater.from(ShaadiMeetActivity.this).inflate(R.layout.choose_who_start_meet_dialog_box, null, false);

                builder.setView(viewInflated);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        m_Text = mobile.getText().toString();


                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShaadiMeetActivity.this);

                View viewInflated = LayoutInflater.from(ShaadiMeetActivity.this).inflate(R.layout.choose_availability_dialog_box, null, false);

                final EditText start_time = (EditText) viewInflated.findViewById(R.id.start_time);
                final EditText end_time = (EditText) viewInflated.findViewById(R.id.end_time);



                start_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                         int  mHour = c.get(Calendar.HOUR_OF_DAY);
                         int  mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(ShaadiMeetActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {


                                        String format = "";
                                        if (hourOfDay == 0) {
                                            hourOfDay += 12;
                                            format = "AM";
                                        } else if (hourOfDay == 12) {
                                            format = "PM";
                                        } else if (hourOfDay > 12) {
                                            hourOfDay -= 12;
                                            format = "PM";
                                        } else {
                                            format = "AM";
                                        }

                                        start_time.setText(new StringBuilder().append(hourOfDay).append(" : ").append(minute)
                                                .append(" ").append(format));


                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();

                    }
                });

                end_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int  mHour = c.get(Calendar.HOUR_OF_DAY);
                        int  mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(ShaadiMeetActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {


                                        String format = "";
                                        if (hourOfDay == 0) {
                                            hourOfDay += 12;
                                            format = "AM";
                                        } else if (hourOfDay == 12) {
                                            format = "PM";
                                        } else if (hourOfDay > 12) {
                                            hourOfDay -= 12;
                                            format = "PM";
                                        } else {
                                            format = "AM";
                                        }

                                        end_time.setText(new StringBuilder().append(hourOfDay).append(" : ").append(minute)
                                                .append(" ").append(format));


                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();

                    }
                });


                builder.setView(viewInflated);
                builder.setPositiveButton("SAVE TIME", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        m_Text = mobile.getText().toString();


                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });



    }
}
package com.WadhuWarProject.WadhuWar.extras;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.AccountSettingActivity;
import com.WadhuWarProject.WadhuWar.activity.HideDeleteActivity;

public class DialogUtil {


    public static void showAlertDialog(Context context) {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_for_hide, null);

        // Build the AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.RoundedCornersDialog)
                .setView(alertLayout)
                .setCancelable(true);

        // Create and show the AlertDialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        // Set the click listener for the OK button
        alertLayout.findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(context, AccountSettingActivity.class);
                context.startActivity(intent);
            }
        });
    }
}

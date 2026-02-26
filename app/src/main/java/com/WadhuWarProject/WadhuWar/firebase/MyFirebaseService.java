package com.WadhuWarProject.WadhuWar.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.MainActivity;
import com.WadhuWarProject.WadhuWar.model.Image;
import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;


public class MyFirebaseService extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 100;
    public static final String CHANNEL_ID = "channel_02";

    public Vibrator vibrator;
    int currentapiVersion = Build.VERSION.SDK_INT;

    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    public MediaPlayer mMediaPlayer;
    Uri notification;
    public MediaPlayer mp = null;
    Timer timer = null;
    Ringtone r;

    @Override
    public void onNewToken(final String s) {
        super.onNewToken(s);

        String refreshedToken = s;
        Log.d("Tag_test_firebase", "Refreshed token: " + refreshedToken);


    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = "";
        String body = "";
        String image = "";
        // Handle Notification payload (from Firebase Console or if sent via notification:{} block)
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }
        // Handle Data payload
        Map<String, String> data = remoteMessage.getData();
        if (data != null && !data.isEmpty()) {
            if (data.containsKey("title")) title = data.get("title");
            if (data.containsKey("message")) body = data.get("message");
            if (data.containsKey("image")) image = data.get("image");
        }
        Log.d("FCM", "Title: " + title);
        Log.d("FCM", "Body: " + body);
        Log.d("FCM", "Image: " + image);
        sendNotification(title, body, image);
    }
    private void sendNotification(String title, String body, String imageUrl) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_ONE_SHOT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{500, 500});

        // Optional: Show image using BigPictureStyle
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                URL url = new URL(imageUrl);
                InputStream input = url.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                if (bitmap != null) {
                    builder.setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .setSummaryText(body));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(notificationManager); // Ensure your channel creation method exists
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "General Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for app notifications");
            notificationManager.createNotificationChannel(channel);
        }
    }




    @Override
    public void onDestroy() {

        System.out.println("destroy call---------------");
        super.onDestroy();

    }


}

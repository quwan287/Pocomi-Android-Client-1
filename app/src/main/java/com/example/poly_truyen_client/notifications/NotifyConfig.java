package com.example.poly_truyen_client.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.example.poly_truyen_client.MainActivity;
import com.example.poly_truyen_client.R;

public class NotifyConfig extends Application {
    public static final String CHANEL_ID = "mwarevn_notify";

    void config() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Poly Comic";
            String mota = "Thông báo poly truyện.";
            int do_uu_tien = NotificationManager.IMPORTANCE_DEFAULT;

//            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifi);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel channel = new NotificationChannel(CHANEL_ID, name, do_uu_tien);

            channel.setDescription(mota);
            channel.setSound(uri, audioAttributes);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        config();
    }
}

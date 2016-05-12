package de.explora;

import android.app.Application;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Sven on 22.04.2015.
 */
public class ExploraApplication extends Application {
    private static final String TAG = "Application";
    private MainActivity MainActivity = null;



    public void onCreate() {
        super.onCreate();
        //Example to get preferences
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //String periodStr = prefs.getString("sync_frequency","360")

        //Example how to log to status bar
        // sendNotification();
    }

    private void sendNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Explora")
                        .setContentText("Sending a message.")
                        .setSmallIcon(R.drawable.betrance_logo);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, MainActivity.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    public void setMainActivity(MainActivity activity) {
        this.MainActivity = activity;
    }


}

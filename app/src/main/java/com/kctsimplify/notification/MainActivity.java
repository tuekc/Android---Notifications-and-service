package com.kctsimplify.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
    Button b1;
    Button b2;
    int   notificationID=0001;

    private static NotificationService mService;
    public static NotificationService getService(){
        return mService;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    displayNotification();// Notify("Karaoke","You've received new message");
                // InitNotification();

                mService.initNotifyMedia();
                mService.Play();

            }
        });
        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    displayNotification();// Notify("Karaoke","You've received new message");
               // initNotifyProcess();
                _Play=false;
                initNotifyMedia();
                _Play = true;
            }
        });
    }

    private void InitNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

    int    numMessages = 5;
// Start of a loop that processes data and then notifies the user
/* for (int i=0;i<10;i++) {
    String currentText ="Line : " + i;
    numMessages++;
    mBuilder.setContentText(currentText)

            .setNumber(i);
}*/
        mBuilder.setNumber(++numMessages);


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] events = new String[6];
        events[0] = new String("This is first line....");
        events[1] = new String("This is second line...");
        events[2] = new String("This is third line...");
        events[3] = new String("This is 4th line...");
        events[4] = new String("This is 5th line...");
        events[5] = new String("This is 6th line...");

        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("Big Title Details:");

        // Moves events into the big view
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }

        mBuilder.setStyle(inboxStyle);



// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(notificationID, mBuilder.build());
    }

     NotificationManager  mNotifyManager;
    NotificationCompat.Builder  mBuilder;
    private  void initNotifyProcess()
    {



          mNotifyManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
         mBuilder = new NotificationCompat.Builder(this);
         mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_launcher);
// Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr+=5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            mBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            mNotifyManager.notify(0, mBuilder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(5*1000);
                            } catch (InterruptedException e) {
                                Log.d("Karaoke", "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        mNotifyManager.notify(notificationID, mBuilder.build());
                    }
                }
// Starts the thread by calling the run() method in its Runnable
        ).start();
    }


    public static final String NOTIFICATION_INTENT_PLAY_PAUSE = "com.kctsimplify.notification.INTENT_PLAYPAUSE";
    public static final String NOTIFICATION_INTENT_CANCEL = "com.kctsimplify.notification.INTENT_CANCEL";
    public static final String NOTIFICATION_INTENT_OPEN_PLAYER = "com.kctsimplify.notification.INTENT_OPENPLAYER";

    private int smallImage = R.drawable.ic_launcher;
    private Bitmap artImage;

    private  boolean _Play =false;

    public boolean isPlaying() {

        return _Play;
    }

    private  void initNotifyMedia()
    {



        Intent intentPlayPause = new Intent(NOTIFICATION_INTENT_PLAY_PAUSE);
        Intent intentOpenPlayer = new Intent(NOTIFICATION_INTENT_OPEN_PLAYER);
        Intent intentCancel = new Intent(NOTIFICATION_INTENT_CANCEL);

        /**
         * Pending intents
         */
        PendingIntent playPausePending = PendingIntent.getService(this, 0, intentPlayPause, 0);
        PendingIntent openPending = PendingIntent.getService(this, 0, intentOpenPlayer, 0);
        PendingIntent cancelPending = PendingIntent.getService(this, 0, intentCancel, 0);

        /**
         * Remote view for normal view
         */

        RemoteViews mNotificationTemplate = new RemoteViews(this.getPackageName(), R.layout.notification);
        Notification.Builder notificationBuilder = new Notification.Builder(this);

        /**
         * set small notification texts and image
         */
        if (artImage == null)
            artImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        String strTitle="Notification";
        String strDescription="Chuong trinh phat thanh online";

        mNotificationTemplate.setTextViewText(R.id.notificationTitle, strTitle);
        mNotificationTemplate.setTextViewText(R.id.notification_description, strDescription);
        mNotificationTemplate.setImageViewResource(R.id.notification_radio_state, isPlaying() ? R.drawable.btn_playback_pause : R.drawable.btn_playback_play);
        mNotificationTemplate.setImageViewBitmap(R.id.logo, artImage);
        /**
         * OnClickPending intent for collapsed notification
         */
        mNotificationTemplate.setOnClickPendingIntent(R.id.notification_exit, cancelPending);
        mNotificationTemplate.setOnClickPendingIntent(R.id.notification_radio_state, playPausePending);



        Notification notification = notificationBuilder
                .setSmallIcon(smallImage)
                .setContentIntent(openPending)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContent(mNotificationTemplate)
                .setUsesChronometer(true)
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;





        // mId allows you to update the notification later on.
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationID, notification);



    }
}

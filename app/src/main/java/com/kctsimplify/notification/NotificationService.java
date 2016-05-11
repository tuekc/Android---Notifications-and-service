package com.kctsimplify.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class NotificationService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }
    public final IBinder mLocalBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        public NotificationService getService() {
            return NotificationService.this;
        }
    }

    /**
     * Service called
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */


    public static final String NOTIFICATION_INTENT_PLAY_PAUSE = "com.kctsimplify.notification.INTENT_PLAYPAUSE";
    public static final String NOTIFICATION_INTENT_CANCEL = "com.kctsimplify.notification.INTENT_CANCEL";
    public static final String NOTIFICATION_INTENT_OPEN_PLAYER = "com.kctsimplify.notification.INTENT_OPENPLAYER";

    int   NOTIFICATION_ID=0001;
    private NotificationManager mNotificationManager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();
        log("KCTRadio RadioPlayerService : " + action);
        /**
         * If cancel clicked on notification, then set state to
         * IDLE, stop player and cancel notification
         */
        if (action.equals(NOTIFICATION_INTENT_CANCEL)) {
          //do....
            Toast.makeText(getBaseContext(),"cancel click", Toast.LENGTH_LONG).show();
            if (mNotificationManager != null) {
                mNotificationManager.cancel(NOTIFICATION_ID);
                this.onDestroy();

            }
        }
        /**
         * If play/pause action clicked on notification,
         * Check player state and stop/play streaming.
         */
        else if (action.equals(NOTIFICATION_INTENT_PLAY_PAUSE)) {
            Toast.makeText(getBaseContext(), "Play an Pause click", Toast.LENGTH_LONG).show();
            //// do.....
            if (_Play)
                Pause();
            else
                Play() ;
            initNotifyMedia();
        }
        else if (action.equals(NOTIFICATION_INTENT_OPEN_PLAYER)) {
            Toast.makeText(getBaseContext(), "NOTIFICATION_INTENT_OPEN_PLAYER", Toast.LENGTH_LONG).show();
            //// do.....
            if (_Play)
                Pause();
            else
                Play() ;
            initNotifyMedia();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


    }


    private int smallImage = R.drawable.ic_launcher;
    private Bitmap artImage;
    int   notificationID=0001;
    private  boolean _Play =false;

    public boolean isPlaying() {

        return _Play;
    }

    public  void  Play()
    {
        _Play =true;
        strDescription =" Play Radio";

    }
    public  void Pause() {
        _Play = false;
        strDescription = " Pause Radio";

    }
    String strDescription="Chuong trinh phat thanh online";

    public   void initNotifyMedia()
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
            artImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);

        String strTitle="Notification";

        mNotificationTemplate.setTextViewText(R.id.notificationTitle, strTitle);
        mNotificationTemplate.setTextViewText(R.id.notification_description, strDescription);
        mNotificationTemplate.setImageViewResource(R.id.notification_radio_state, isPlaying() ? R.drawable.btn_playback_pause : R.drawable.btn_playback_play);
        mNotificationTemplate.setImageViewBitmap(R.id.logo, artImage);
        //set the button listeners
      //  setListeners(mNotificationTemplate);



        Notification notification = notificationBuilder
                .setSmallIcon(smallImage)
                .setContentIntent(openPending)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContent(mNotificationTemplate)
                .setUsesChronometer(true)
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;


        /**
         * OnClickPending intent for collapsed notification
         */
        mNotificationTemplate.setOnClickPendingIntent(R.id.notification_exit, cancelPending);
        mNotificationTemplate.setOnClickPendingIntent(R.id.notification_radio_state, playPausePending);



        // mId allows you to update the notification later on.
         mNotificationManager.notify(notificationID, notification);



    }


    private void log(String log) {

            Log.e("Notification", "RadioPlayerService : " + log);
    }
}

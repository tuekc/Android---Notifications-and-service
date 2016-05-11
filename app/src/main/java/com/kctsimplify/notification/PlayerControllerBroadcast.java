package com.kctsimplify.notification;

/**
 * Created by ASUS on 5/11/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by mertsimsek on 04/11/15.
 */
public class PlayerControllerBroadcast extends BroadcastReceiver{

    /**
     * This receiver receives STOP controlling between player services
     * For instances, If MediaPlayerService is running and playing stream
     * and then RadioPlayerService requested play radio stream, Service sends broadcast
     * that stop MediaPlayerService. And If RadioPlayerService is running and
     * playing radio stream, RadioPlayerService will be stopped here when MediaPlayerService
     * requested play media. This is the way we communicate between services to stop
     * each other.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {


        String action = intent.getAction();
        Log.e("Notifition","Action : " + action);

        if(action.equals(MainActivity.NOTIFICATION_INTENT_PLAY_PAUSE))
        {
            Toast.makeText(context,"Play an Pause click", Toast.LENGTH_LONG).show();
        }
        else if(action.equals(MainActivity.NOTIFICATION_INTENT_CANCEL))
        Toast.makeText(context,"cancel click", Toast.LENGTH_LONG).show();
        }
    }
package com.kctsimplify.notification;

/**
 * Created by ASUS on 5/11/2016.
 */
import android.os.Bundle;
import android.app.Activity;

public class NotificationView extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }
}
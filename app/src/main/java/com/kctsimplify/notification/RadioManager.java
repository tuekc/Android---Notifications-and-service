package com.kctsimplify.notification;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 5/12/2016.
 */
public class RadioManager {


    /**
     * RadioPlayerService
     */
    private static RadioPlayerService mService;

    /**
     * Context
     */
    private Context mContext;

    /**
     * Listeners
     */
    private List<RadioListener> mRadioListenerQueue;

    /**
     * Service connected/Disconnected lock
     */
    private boolean isServiceConnected;

    /**
     * Private constructor because of Singleton pattern
     * @param mContext
     */
    private RadioManager(Context mContext) {
        this.mContext = mContext;
        mRadioListenerQueue = new ArrayList<>();
        isServiceConnected = false;
    }
}

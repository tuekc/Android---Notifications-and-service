package com.kctsimplify.notification;

import android.graphics.Bitmap;

/**
 * Created by ASUS on 5/12/2016.
 */
public interface IRadioManager {

    void startRadio(String streamURL);

    void stopRadio();

    boolean isPlaying();

    void registerListener(RadioListener mRadioListener);

    void unregisterListener(RadioListener mRadioListener);

    void setLogging(boolean logging);

    void connect();

    void disconnect();

    void updateNotification(String singerName, String songName, int smallArt, int bigArt);

    void updateNotification(String singerName, String songName, int smallArt, Bitmap bigArt);

}

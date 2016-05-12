package com.kctsimplify.notification;

/**
 * Created by ASUS on 5/12/2016.
 */
public interface RadioListener {

    void onRadioLoading();

    void onRadioConnected();

    void onRadioStarted();

    void onRadioStopped();

    void onMetaDataReceived(String s, String s2);

    void onError();
}
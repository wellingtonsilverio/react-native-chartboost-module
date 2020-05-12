package com.reactlibrary;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chartboost.sdk.Model.CBError;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.chartboost.sdk.Chartboost;

public class ChartboostModuleModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public ChartboostModuleModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ChartboostModule";
    }

    @ReactMethod
    public void initialize(String appId, String appSignature, Callback callback) {
        Chartboost.startWithAppId(getCurrentActivity(), appId, appSignature);

        setupSdkWithCustomSettings();

        callback.invoke("Sucess");
    }

    private void setupSdkWithCustomSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(reactContext.getBaseContext());

        Chartboost.setShouldPrefetchVideoContent(
                sharedPreferences.getBoolean("enableVideoPrefetch", true));
        Chartboost.setShouldRequestInterstitialsInFirstSession(
                sharedPreferences.getBoolean("interstitialInFirstSession", true));
        Chartboost.setAutoCacheAds(
                sharedPreferences.getBoolean("enableAutoCache", true));

    }

    @ReactMethod
    public void rewarded(String location, Callback callback) {
        Chartboost.showRewardedVideo(location);

        callback.invoke("Sucess");
    }
}

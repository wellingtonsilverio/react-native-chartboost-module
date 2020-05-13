package com.reactlibrary;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Model.CBError;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.chartboost.sdk.Chartboost;

import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartboostModuleModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ChartboostModuleModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ChartboostModule";
    }

    @ReactMethod
    public void initialize(String appId, String appSignature) {
        Chartboost.setDelegate(delegate);

        Chartboost.startWithAppId(reactContext, appId, appSignature);

        setupSdkWithCustomSettings();

        addToUILog("initialize");
    }

    private void setupSdkWithCustomSettings() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(reactContext.getBaseContext());

        Chartboost.setShouldPrefetchVideoContent(sharedPreferences.getBoolean("enableVideoPrefetch", true));
        Chartboost.setShouldRequestInterstitialsInFirstSession(
                sharedPreferences.getBoolean("interstitialInFirstSession", true));
        Chartboost.setAutoCacheAds(sharedPreferences.getBoolean("enableAutoCache", true));

    }

    @ReactMethod
    public void isRewardedReadyToDisplay(String location, Callback callback) {
        boolean isReady = Chartboost.hasRewardedVideo(location);

        addToUILog("isReady: "+isReady);

        callback.invoke(isReady);
    }

    @ReactMethod
    public void rewarded(String location, Callback callback) {
        Chartboost.showRewardedVideo(location);

        addToUILog("Show rewarded");

        callback.invoke("Sucess");
    }

    @ReactMethod
    public void rewardedCache(String location, Callback callback) {
        Chartboost.cacheRewardedVideo(location);

        addToUILog("Cache rewarded");

        callback.invoke("Sucess");
    }

    public ChartboostDelegate delegate = new ChartboostDelegate() {

        @Override
        public boolean shouldRequestInterstitial(String location) {
            addToUILog("Should request interstitial at " + location + "?");
            emmitEvent("shouldRequestInterstitial");
            return true;
        }

        @Override
        public boolean shouldDisplayInterstitial(String location) {
            addToUILog("Should display interstitial at " + location + "?");
            emmitEvent("shouldDisplayInterstitial");
            return true;
        }

        @Override
        public void didCacheInterstitial(String location) {
            addToUILog("Interstitial cached at " + location);
            emmitEvent("didCacheInterstitial");
        }

        @Override
        public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error) {
            addToUILog("Interstitial failed to load at " + location + " with error: " + error.name());
            emmitEvent("didFailToLoadInterstitial");
        }

        @Override
        public void willDisplayInterstitial(String location) {
            addToUILog("Will display interstitial at " + location);
            emmitEvent("willDisplayInterstitial");
        }

        @Override
        public void didDismissInterstitial(String location) {
            addToUILog("Interstitial dismissed at " + location);
            emmitEvent("didDismissInterstitial");
        }

        @Override
        public void didCloseInterstitial(String location) {
            addToUILog("Interstitial closed at " + location);
            emmitEvent("didCloseInterstitial");
        }

        @Override
        public void didClickInterstitial(String location) {
            addToUILog("Interstitial clicked at " + location);
            emmitEvent("didClickInterstitial");
        }

        @Override
        public void didDisplayInterstitial(String location) {
            addToUILog("Interstitial displayed at " + location);
            emmitEvent("didDisplayInterstitial");
        }

        @Override
        public void didFailToRecordClick(String uri, CBError.CBClickError error) {
            addToUILog("Failed to record click " + (uri != null ? uri : "null") + ", error: " + error.name());
            emmitEvent("didFailToRecordClick");
        }

        @Override
        public boolean shouldDisplayRewardedVideo(String location) {
            addToUILog("Should display rewarded video at " + location + "?");
            emmitEvent("shouldDisplayRewardedVideo");
            return true;
        }

        @Override
        public void didCacheRewardedVideo(String location) {
            addToUILog("Did cache rewarded video " + location);
            emmitEvent("didCacheRewardedVideo");
        }

        @Override
        public void didFailToLoadRewardedVideo(String location, CBError.CBImpressionError error) {
            addToUILog("Rewarded Video failed to load at " + location + " with error: " + error.name());
            emmitEvent("didFailToLoadRewardedVideo");
        }

        @Override
        public void didDismissRewardedVideo(String location) {
            addToUILog("Rewarded video dismissed at " + location);
            emmitEvent("didDismissRewardedVideo");
        }

        @Override
        public void didCloseRewardedVideo(String location) {
            addToUILog("Rewarded video closed at " + location);
            emmitEvent("didCloseRewardedVideo");
        }

        @Override
        public void didClickRewardedVideo(String location) {
            addToUILog("Rewarded video clicked at " + location);
            emmitEvent("didClickRewardedVideo");
        }

        @Override
        public void didCompleteRewardedVideo(String location, int reward) {
            addToUILog("Rewarded video completed at " + location + "for reward: " + reward);
            emmitEvent("didCompleteRewardedVideo");
        }

        @Override
        public void didDisplayRewardedVideo(String location) {
            addToUILog("Rewarded video displayed at " + location);
            emmitEvent("didDisplayRewardedVideo");
        }

        @Override
        public void willDisplayVideo(String location) {
            addToUILog("Will display video at " + location);
            emmitEvent("willDisplayVideo");
        }

        @Override
        public void didCacheInPlay(String location) {
            addToUILog("In Play loaded at " + location);
            emmitEvent("didCacheInPlay");
        }

        @Override
        public void didFailToLoadInPlay(String location, CBError.CBImpressionError error) {
            addToUILog("In play failed to load at " + location + ", with error: " + error);
            emmitEvent("didFailToLoadInPlay");
        }

        @Override
        public void didInitialize() {
            addToUILog("Chartboost SDK is initialized and ready!");
            emmitEvent("didInitialize");
        }
    };

    public void addToUILog(final String message) {
        WritableMap map = Arguments.createMap();

        map.putString("message", message);

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("log", map);
    }

    public void emmitEvent(final String eventName) {
        WritableMap map = Arguments.createMap();

        map.putString("datetime", formatterDate.format(new Date()));

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, map);
    }
}

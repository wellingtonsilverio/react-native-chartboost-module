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

    public ChartboostDelegate delegate = new ChartboostDelegate() {

//        @Override
//        public boolean shouldRequestInterstitial(String location) {
//            addToUILog("Should request interstitial at " + location + "?");
//            return true;
//        }
//
//        @Override
//        public boolean shouldDisplayInterstitial(String location) {
//            addToUILog("Should display interstitial at " + location + "?");
//            return true;
//        }
//
//        @Override
//        public void didCacheInterstitial(String location) {
//            addToUILog("Interstitial cached at " + location);
//            setHasAdForLocation(location);
//            incrementCounter(cacheCounter);
//        }
//
//        @Override
//        public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error) {
//            addToUILog("Interstitial failed to load at " + location + " with error: " + error.name());
//            setHasAdForLocation(location);
//            incrementCounter(failLoadCounter);
//        }
//
//        @Override
//        public void willDisplayInterstitial(String location) {
//            addToUILog("Will display interstitial at " + location);
//        }
//
//        @Override
//        public void didDismissInterstitial(String location) {
//            addToUILog("Interstitial dismissed at " + location);
//            incrementCounter(dismissCounter);
//        }
//
//        @Override
//        public void didCloseInterstitial(String location) {
//            addToUILog("Interstitial closed at " + location);
//            incrementCounter(closeCounter);
//        }
//
//        @Override
//        public void didClickInterstitial(String location) {
//            addToUILog("Interstitial clicked at " + location );
//            incrementCounter(clickCounter);
//        }
//
//        @Override
//        public void didDisplayInterstitial(String location) {
//            addToUILog("Interstitial displayed at " + location);
//            setHasAdForLocation(location);
//            incrementCounter(displayCounter);
//        }
//
//        @Override
//        public void didFailToRecordClick(String uri, CBError.CBClickError error) {
//            addToUILog("Failed to record click " + (uri != null ? uri : "null") + ", error: " + error.name());
//        }
//
//        @Override
//        public boolean shouldDisplayRewardedVideo(String location) {
//            addToUILog("Should display rewarded video at " + location + "?");
//            return true;
//        }
//
//        @Override
//        public void didCacheRewardedVideo(String location) {
//            addToUILog("Did cache rewarded video " + location);
//            setHasAdForLocation(location);
//            incrementCounter(cacheCounter);
//        }
//
//        @Override
//        public void didFailToLoadRewardedVideo(String location,
//                                               CBError.CBImpressionError error) {
//            addToUILog("Rewarded Video failed to load at " + location + " with error: " + error.name());
//            setHasAdForLocation(location);
//            incrementCounter(failLoadCounter);
//        }
//
//        @Override
//        public void didDismissRewardedVideo(String location) {
//            addToUILog("Rewarded video dismissed at " + location);
//            incrementCounter(dismissCounter);
//        }
//
//        @Override
//        public void didCloseRewardedVideo(String location) {
//            addToUILog("Rewarded video closed at " + location);
//            incrementCounter(closeCounter);
//        }
//
//        @Override
//        public void didClickRewardedVideo(String location) {
//            addToUILog("Rewarded video clicked at " + location);
//            incrementCounter(clickCounter);
//        }
//
//        @Override
//        public void didCompleteRewardedVideo(String location, int reward) {
//            addToUILog("Rewarded video completed at " + location + "for reward: " + reward);
//            incrementCounter(completeCounter);
//            addToCounter(rewardCounter, reward);
//        }
//
//        @Override
//        public void didDisplayRewardedVideo(String location) {
//            addToUILog("Rewarded video displayed at " + location);
//            setHasAdForLocation(location);
//            incrementCounter(displayCounter);
//        }
//
//        @Override
//        public void willDisplayVideo(String location) {
//            addToUILog("Will display video at " + location);
//        }
//
//        @Override
//        public void didCacheInPlay(String location) {
//            addToUILog("In Play loaded at " + location);
//            setHasAdForLocation(location);
//            incrementCounter(cacheCounter);
//        }
//
//        @Override
//        public void didFailToLoadInPlay(String location, CBError.CBImpressionError error) {
//            addToUILog("In play failed to load at " + location + ", with error: " + error);
//            setHasAdForLocation(location);
//            incrementCounter(failLoadCounter);
//        }

        @Override
        public void didInitialize() {
            addToUILog("Chartboost SDK is initialized and ready!");
        }

        public void addToUILog(final String message) {
            System.out.println(message);
        }
    };
}

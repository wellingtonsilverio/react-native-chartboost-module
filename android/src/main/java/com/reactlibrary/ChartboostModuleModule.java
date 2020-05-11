package com.reactlibrary;

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
    public void logApp(String appId, String appSignature, Callback callback) {
        // TODO: Implement some actually useful functionality
        Chartboost.startWithAppId(getApplicationContext(), appId, appSignature);
        callback.invoke("Sucess");
    }
}

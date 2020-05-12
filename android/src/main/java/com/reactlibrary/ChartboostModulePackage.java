package com.reactlibrary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;

import com.chartboost.sdk.Chartboost;

public class ChartboostModulePackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new ChartboostModuleModule(reactContext));
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    public static void ONCREATE(Activity activity, String appId, String appSignature) {
        Chartboost.onCreate(activity);
    }

    public static void ONSTART(Activity activity) {
        Chartboost.onStart(activity);
    }

    public static void ONRESUME(Activity activity) {
        Chartboost.onResume(activity);
    }

    public static void ONPAUSE(Activity activity) {
        Chartboost.onPause(activity);
    }

    public static void ONSTOP(Activity activity) {
        Chartboost.onStop(activity);
    }

    public static void ONDESTROY(Activity activity) {
        Chartboost.onDestroy(activity);
    }

    public static boolean ONBACKPRESSED() {
        return Chartboost.onBackPressed();
    }
}

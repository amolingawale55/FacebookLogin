package com.example.admin.fb;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

public class MyApplication extends MultiDexApplication {

    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;
    public static Typeface sofia_Regular;
    public static Typeface roboto_light;
    @Override
    public void onCreate() {
        //
        /////VIKAS

        ////////////////AMOLLLLLLLLLLLLLLLLLLLLLLLLLLLL

        super.onCreate();
        mInstance = this;
        //Service is below
        //  Intent serviceIntent = new Intent(getApplicationContext(),LogoutService.class);
        //  startService(serviceIntent);
        //Firebase
        mInstance = this;
        //set a custom font for  app
       // sofia_Regular = Typeface.createFromAsset(getAssets(), "fonts/Sofia-Regular.ttf");
        sofia_Regular = Typeface.createFromAsset(getAssets(), "fonts/roboto.light.ttf");
        //set Custom font for All List View
        roboto_light = Typeface.createFromAsset(getAssets(), "fonts/roboto.light.ttf");

//        AnalyticsTrackers.initialize(this);
//        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    //Google Anyalytics
//    public synchronized Tracker getGoogleAnalyticsTracker() {
//        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
//        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
//    }
//
//    public void trackScreenView(String screenName) {
//        Tracker t = getGoogleAnalyticsTracker();
//
//        // Set screen name.
//        t.setScreenName(screenName);
//
//        // Send a screen view.
//        t.send(new HitBuilders.ScreenViewBuilder().build());
//
//        GoogleAnalytics.getInstance(this).dispatchLocalHits();
//    }
//
//    public void trackException(Exception e) {
//        if (e != null) {
//            Tracker t = getGoogleAnalyticsTracker();
//
//            t.send(new HitBuilders.ExceptionBuilder()
//                    .setDescription(
//                            new StandardExceptionParser(this, null)
//                                    .getDescription(Thread.currentThread().getName(), e))
//                    .setFatal(false)
//                    .build()
//            );
//        }
//    }
//
//    public void trackEvent(String category, String action, String label) {
//        Tracker t = getGoogleAnalyticsTracker();
//
//        // Build and send an Event.
//        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
//    }

    // Volley Request
}
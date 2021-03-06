package ir.hotelairport.androidapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.onesignal.OneSignal;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Mohammad on 9/1/2017.
 */

public final class Application extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/IRANSansMobile.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/IRANSansMobile.ttf");
        FontsOverride.setDefaultFont(this, "SANS", "fonts/IRANSansMobile.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/IRANSansMobile.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/IRANSansMobile.ttf");
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale();
    }
    private void setLocale()
    {
        SharedPreferences user_detail=getSharedPreferences(Constants.USER_DETAIL, Context.MODE_PRIVATE);
        String languageToLoad=user_detail.getString(Constants.LANGUAGE_LOCALE,"en");
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

}

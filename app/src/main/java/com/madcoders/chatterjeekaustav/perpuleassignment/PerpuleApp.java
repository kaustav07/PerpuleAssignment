package com.madcoders.chatterjeekaustav.perpuleassignment;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.component.ApplicationComponent;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.component.DaggerApplicationComponent;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.module.ApplicationModule;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Kaustav on 09-03-2018.
 */

public class PerpuleApp extends Application {

    private ApplicationComponent mApplicationComponent;

    @Inject
    CalligraphyConfig mCalligraphyConfig;


    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);

        TypefaceProvider.registerDefaultIconSets();

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}

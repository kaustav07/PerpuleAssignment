package com.madcoders.chatterjeekaustav.perpuleassignment.di.module;

import android.app.Application;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.madcoders.chatterjeekaustav.perpuleassignment.R;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.DataHelper;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.network.AudioManager;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.network.AudioService;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.DataManager;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Kaustav on 08-03-2018.
 */


@Module
public class ApplicationModule {
    private Application mApplication;

    public ApplicationModule(Application application){mApplication = application;}


    @Provides
    @ApplicationContext
    Context provideApplicationContext(){return mApplication;}

    @Provides
    Application provideApplication(){return mApplication;}

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Android-101.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }


}

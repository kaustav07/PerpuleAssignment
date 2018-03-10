package com.madcoders.chatterjeekaustav.perpuleassignment.di.module;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.DataHelper;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.DataManager;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.network.AudioManager;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.network.AudioService;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.ActivityContext;
import com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity.AudioContract;
import com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity.AudioPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kaustav on 08-03-2018.
 */


@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity appCompatActivity){mActivity = appCompatActivity;}

    @Provides
    @ActivityContext
    Context provideActivityContext(){return mActivity;}

    @Provides
    AppCompatActivity provideActivity(){return mActivity;}

    @Provides
    Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(AudioService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    DataHelper provideDataHelper(DataManager dataManager){return dataManager;}


}

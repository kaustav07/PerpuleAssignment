package com.madcoders.chatterjeekaustav.perpuleassignment.di.component;

import android.app.IntentService;

import com.madcoders.chatterjeekaustav.perpuleassignment.PerpuleApp;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.DataHelper;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.network.AudioManager;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Kaustav on 08-03-2018.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(PerpuleApp app);

    void inject(IntentService service);

}

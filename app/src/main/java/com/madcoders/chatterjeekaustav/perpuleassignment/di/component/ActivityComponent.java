package com.madcoders.chatterjeekaustav.perpuleassignment.di.component;

import com.madcoders.chatterjeekaustav.perpuleassignment.di.PerActivity;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.module.ActivityModule;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.module.ApplicationModule;
import com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity.AudioActivity;
import com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity.AudioFragment;

import dagger.Component;

/**
 * Created by Kaustav on 08-03-2018.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

        void inject(AudioFragment fragment);

        void inject(AudioActivity activity);

}

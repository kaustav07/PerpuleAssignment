package com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.madcoders.chatterjeekaustav.perpuleassignment.PerpuleApp;
import com.madcoders.chatterjeekaustav.perpuleassignment.R;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.component.ActivityComponent;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.component.DaggerActivityComponent;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.module.ActivityModule;
import com.madcoders.chatterjeekaustav.perpuleassignment.ui.SingleFragmentActivity;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AudioActivity extends SingleFragmentActivity{


    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((PerpuleApp)getApplication()).getApplicationComponent())
                .build();
        mActivityComponent.inject(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public int getResId() {
        return R.layout.activity_audio;
    }

    @Override
    public Fragment createFragment() {
        return AudioFragment.newInstance();
    }

}

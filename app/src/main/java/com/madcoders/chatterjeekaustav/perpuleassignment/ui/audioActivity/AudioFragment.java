package com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.ybq.android.spinkit.SpinKitView;
import com.hanks.htextview.scale.ScaleTextView;
import com.madcoders.chatterjeekaustav.perpuleassignment.PerpuleApp;
import com.madcoders.chatterjeekaustav.perpuleassignment.R;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.Datum;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.component.ActivityComponent;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.module.ActivityModule;
import com.madcoders.chatterjeekaustav.perpuleassignment.utils.CommonUtils;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioFragment extends Fragment implements AudioContract.View {

    MediaPlayer mPlayer;

    @Inject
    AudioPresenter<AudioContract.View> mAudioPresenter;

    @Inject
    CommonUtils mCommonUtils;

    private ActivityComponent mActivityComponent;
    private AppCompatActivity mActivity;
    private ProgressDialog mProgressDialog;

    @BindView(R.id.continuebtn)
    BootstrapButton mButton;

    @BindView(R.id.audiodesc)
    ScaleTextView mTextView;

    public AudioFragment() {
        // Required empty public constructor
    }

    public static AudioFragment newInstance() {
        AudioFragment fragment = new AudioFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daggerInitialize();
        mediaInitialize();
    }

    private void mediaInitialize() {
        mPlayer = new MediaPlayer();
    }

    private void nextButtonSetup() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioPresenter.onNextClicked();
            }
        });
    }

    private void daggerInitialize() {
        mActivityComponent = ((AudioActivity) getActivity()).getActivityComponent();
        if (mActivityComponent != null)
            mActivityComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_audio, container, false);
        ButterKnife.bind(this, v);
        mAudioPresenter.attchView(this);
        mAudioPresenter.viewPrepared();
        nextButtonSetup();
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        mediaInitialize();
        mAudioPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPlayer.release();
        mAudioPresenter.onStop();
    }

    @Override
    public void loadDataAndPlaySong(Datum datum) {
        if (datum == null)
            return;
        mTextView.animateText(datum.getDesc());
        mButton.setVisibility(View.VISIBLE);
        String filePath = getActivity().getFilesDir() + File.separator + datum.getItemId() + ".mp3";
        File song = new File(filePath);
        if (song.exists()) {
            try {
                mPlayer.reset();
                mPlayer.setDataSource(filePath);
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        snackbar.show();
    }

    @Override
    public void showProgress() {
        hideProgress();
        mProgressDialog = mCommonUtils.showLoadingDialog(this.getContext());
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void showInfo(String message) {
        showSnackBar(message);
    }



}

package com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity;

import com.madcoders.chatterjeekaustav.perpuleassignment.base.BasePresenter;
import com.madcoders.chatterjeekaustav.perpuleassignment.base.BaseView;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.Datum;

/**
 * Created by Kaustav on 09-03-2018.
 */

public interface AudioContract {

    interface Presenter<V extends View> extends BasePresenter<V>{

        void onStart();

        void onStop();

        void onNextClicked();
    }

    interface View extends BaseView{

        void loadDataAndPlaySong(Datum datum);

        void showProgress();

        void hideProgress();

        void finishActivity();

        void showInfo(String message);

    }

}

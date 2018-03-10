package com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity;

import android.content.Context;
import android.content.Intent;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.madcoders.chatterjeekaustav.perpuleassignment.base.BasePresenterImpl;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.DataHelper;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.Datum;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.events.SongDownloadComplete;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.ActivityContext;
import com.madcoders.chatterjeekaustav.perpuleassignment.service.SongDownloadService;
import com.madcoders.chatterjeekaustav.perpuleassignment.utils.CommonUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kaustav on 09-03-2018.
 */

public class AudioPresenter<V extends AudioContract.View> extends BasePresenterImpl<V> implements AudioContract.Presenter<V> {

    private List<Datum> mData = new ArrayList<>();
    private Context mContext;
    private CommonUtils mCommonUtils;
    private int CURRENT_ITEM = 0;
    private Boolean WAITING_FOR_NETWORK = false;


    @Inject
    public AudioPresenter(DataHelper dataHelper, @ActivityContext Context context,CommonUtils commonUtils) {
        super(dataHelper);
        mContext = context;
        mCommonUtils = commonUtils;
    }

    public void startSongFetch() {

        getDataManager().getAudioFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Datum>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Datum datum) {
                        getData().add(datum);
                        if (!ifAudioExist(datum)) {
                            getDataManager().downloadSong(datum);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (getCURRENT_ITEM() == 0 && ifAudioExist(getData().get(getCURRENT_ITEM()))) {
                            getMvpView().hideProgress();
                            playNextSong();
                        }
                        checkIfOfflinePossible();
                    }
                });
    }

    public boolean ifAudioExist(Datum datum) {
       return getDataManager().checkIfAudioExists(datum);
    }

    public void checkIfOfflinePossible() {
        for (Datum datum : getData()) {
            if (!ifAudioExist(datum)) {
                return;
            }
        }
        showOffileEnabledDialog();
    }

    public void showOffileEnabledDialog() {
        getMvpView().showInfo("The app can now work offline!");
    }

    public void listenForInternetConnection() {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) {
                        if (isConnectedToInternet && WAITING_FOR_NETWORK) {
                            WAITING_FOR_NETWORK = false;
                            startSongFetch();
                        }
                    }
                });
    }

    @Override
    public void viewPrepared() {
        getMvpView().showProgress();
        if (!mCommonUtils.checkIfLocalJSONAvailable(mContext) && !mCommonUtils.isNetworkConnected(mContext)) {
            setWAITING_FOR_NETWORK(true);
            listenForInternetConnection();
        } else
            startSongFetch();
    }

    public void playNextSong() {
        if (getCURRENT_ITEM() < getData().size()) {
            getMvpView().loadDataAndPlaySong(getData().get(getCURRENT_ITEM()));
            setCURRENT_ITEM(getCURRENT_ITEM()+1);
        } else {
            getMvpView().finishActivity();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSongDownloadcompleted(SongDownloadComplete songDownloadComplete) {
        if (getData().get(0).getItemId().equals(songDownloadComplete.getItemId())) {
            getMvpView().hideProgress();
            playNextSong();
        }
        for (Datum datum : getData()) {
            if (datum.getItemId().equals(songDownloadComplete.getItemId())) {
                getData().get(getData().indexOf(datum)).setDownloaded(songDownloadComplete.isStatus());
            }
        }
        checkIfOfflinePossible();
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onNextClicked() {
        playNextSong();
    }

    public int getCURRENT_ITEM() {
        return CURRENT_ITEM;
    }

    public AudioPresenter setCURRENT_ITEM(int CURRENT_ITEM) {
        this.CURRENT_ITEM = CURRENT_ITEM;
        return this;
    }

    public Boolean getWAITING_FOR_NETWORK() {
        return WAITING_FOR_NETWORK;
    }

    public AudioPresenter setWAITING_FOR_NETWORK(Boolean WAITING_FOR_NETWORK) {
        this.WAITING_FOR_NETWORK = WAITING_FOR_NETWORK;
        return this;
    }

    public List<Datum> getData() {
        return mData;
    }

    public AudioPresenter setData(List<Datum> data) {
        mData = data;
        return this;
    }
}

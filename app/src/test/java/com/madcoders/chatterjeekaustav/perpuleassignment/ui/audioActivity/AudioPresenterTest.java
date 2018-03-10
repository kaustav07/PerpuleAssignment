package com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity;

import android.content.Context;

import com.madcoders.chatterjeekaustav.perpuleassignment.data.DataHelper;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.Datum;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.events.SongDownloadComplete;
import com.madcoders.chatterjeekaustav.perpuleassignment.utils.CommonUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kaustav on 10-03-2018.
 */
public class AudioPresenterTest {

    AudioPresenter mAudioPresenter;
    AudioPresenter mSpyAudioPresenter;

    @Mock
    AudioFragment mAudioFragment;

    @Mock
    private DataHelper mDataHelper;

    @Mock
    List<Datum> mData;

    @Mock
    SongDownloadComplete mSongDownloadComplete;

    @Mock
    private Context mContext;

    @Mock
    private CommonUtils mCommonUtils;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.reset(mDataHelper);
        Mockito.reset(mAudioFragment);
        Mockito.reset(mCommonUtils);
        Mockito.reset(mContext);
        Mockito.reset(mSongDownloadComplete);
        mAudioPresenter = new AudioPresenter(mDataHelper, mContext, mCommonUtils);
        mAudioPresenter.attchView(mAudioFragment);
        mSpyAudioPresenter = spy(mAudioPresenter);
        when(mCommonUtils.isNetworkConnected(mContext)).thenReturn(true);
        when(mCommonUtils.checkIfLocalJSONAvailable(mContext)).thenReturn(false);
        when(mDataHelper.getAudioFiles()).thenReturn(Observable.just(new Datum(), new Datum(), new Datum()));
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Test
    public void startSongFetchFromNetwork() throws Exception {
        mSpyAudioPresenter.startSongFetch();
        verify(mDataHelper).getAudioFiles();
        verify(mDataHelper,atLeast(3)).checkIfAudioExists(any());
        verify(mDataHelper,atLeast(3)).downloadSong(any());
        verify(mSpyAudioPresenter).checkIfOfflinePossible();
    }

    @Test
    public void checkonNextClicked() throws Exception{
        mSpyAudioPresenter.onNextClicked();
        verify(mSpyAudioPresenter).playNextSong();
    }

    @Test
    public void viewPrepared() throws Exception {
        mSpyAudioPresenter.viewPrepared();
        verify(mAudioFragment).showProgress();
        verify(mSpyAudioPresenter).startSongFetch();
    }

    @Test
    public void viewPreparedWehnNoConnection() throws Exception {
        when(mCommonUtils.isNetworkConnected(mContext)).thenReturn(false);
        when(mCommonUtils.checkIfLocalJSONAvailable(mContext)).thenReturn(false);
        mSpyAudioPresenter.viewPrepared();
        verify(mAudioFragment).showProgress();
        verify(mSpyAudioPresenter).setWAITING_FOR_NETWORK(anyBoolean());
        verify(mSpyAudioPresenter).listenForInternetConnection();
    }

    @Test
    public void startSongFetchwhenLocalavailable() throws Exception {
        when(mSpyAudioPresenter.getCURRENT_ITEM()).thenReturn(0);
        when(mSpyAudioPresenter.ifAudioExist(any())).thenReturn(true);
        when(mDataHelper.checkIfAudioExists(any())).thenReturn(true);
        mSpyAudioPresenter.startSongFetch();
        verify(mDataHelper).getAudioFiles();
        verify(mDataHelper,atLeast(3)).checkIfAudioExists(any());
        verify(mDataHelper,never()).downloadSong(any());
        verify(mAudioFragment).hideProgress();
        verify(mSpyAudioPresenter).playNextSong();
    }

    @Test
    public void playNextSongWhenCurrentislessthanSize() throws Exception {
        when(mSpyAudioPresenter.getCURRENT_ITEM()).thenReturn(0);
        when(mData.size()).thenReturn(3);
        when(mSpyAudioPresenter.getData()).thenReturn(mData);
        mSpyAudioPresenter.playNextSong();
        verify(mAudioFragment).loadDataAndPlaySong(any());
        verify(mSpyAudioPresenter).setCURRENT_ITEM(1);
    }

    @Test
    public void playNextSongWhenCurrentisnotlessthanSize() throws Exception {
        when(mSpyAudioPresenter.getCURRENT_ITEM()).thenReturn(3);
        when(mData.size()).thenReturn(3);
        when(mSpyAudioPresenter.getData()).thenReturn(mData);
        mSpyAudioPresenter.playNextSong();
        verify(mAudioFragment,never()).loadDataAndPlaySong(any());
        verify(mSpyAudioPresenter,never()).setCURRENT_ITEM(1);
    }

    @Test
    public void checkIfOfflinePossiblewhenNotoffilepossible() throws Exception {
        Datum datum = new Datum();
        datum.setItemId("12");
        List<Datum> data = new ArrayList<>();
        data.add(datum);
        data.add(new Datum());
        data.add(new Datum());
        when(mSpyAudioPresenter.getData()).thenReturn(data);
        mSpyAudioPresenter.checkIfOfflinePossible();
        verify(mSpyAudioPresenter,atLeastOnce()).ifAudioExist(any());
        verify(mSpyAudioPresenter,never()).showOffileEnabledDialog();
    }

    @Test
    public void checkIfOfflinePossiblewhenoffilepossible() throws Exception {
        Datum datum = new Datum();
        datum.setItemId("12");
        List<Datum> data = new ArrayList<>();
        data.add(datum);
        data.add(new Datum());
        data.add(new Datum());
        when(mSpyAudioPresenter.getData()).thenReturn(data);
        when(mDataHelper.checkIfAudioExists(any())).thenReturn(true);
        mSpyAudioPresenter.checkIfOfflinePossible();
        verify(mSpyAudioPresenter,times(3)).ifAudioExist(any());
        verify(mSpyAudioPresenter).showOffileEnabledDialog();
    }

    @Test
    public void showOffileEnabledDialog() throws Exception {
        mSpyAudioPresenter.showOffileEnabledDialog();
        verify(mAudioFragment).showInfo(anyString());
    }

    @Test
    public void onSongDownloadcompleted() throws Exception {
        Datum datum = new Datum();
        datum.setItemId("12");
        List<Datum> data = new ArrayList<>();
        data.add(datum);
        when(mSpyAudioPresenter.getData()).thenReturn(data);
        when(mSongDownloadComplete.getItemId()).thenReturn("12");
        mSpyAudioPresenter.onSongDownloadcompleted(mSongDownloadComplete);
        verify(mAudioFragment).hideProgress();
        verify(mSpyAudioPresenter).playNextSong();
        verify(mSpyAudioPresenter).checkIfOfflinePossible();
    }

}
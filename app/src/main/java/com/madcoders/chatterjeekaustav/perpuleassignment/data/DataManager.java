package com.madcoders.chatterjeekaustav.perpuleassignment.data;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.AudioBook;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.Datum;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.network.AudioManager;
import com.madcoders.chatterjeekaustav.perpuleassignment.di.ActivityContext;
import com.madcoders.chatterjeekaustav.perpuleassignment.service.JSONFetcherService;
import com.madcoders.chatterjeekaustav.perpuleassignment.service.SongDownloadService;
import com.madcoders.chatterjeekaustav.perpuleassignment.utils.CommonUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Kaustav on 09-03-2018.
 */

public class DataManager implements DataHelper {

    private AudioManager mAudioManager;
    private Context mContext;
    private CommonUtils mCommonUtils;

    @Inject
    public DataManager(AudioManager audioManager, @ActivityContext Context context,CommonUtils commonUtils) {
        mAudioManager = audioManager;
        mContext = context;
        mCommonUtils = commonUtils;
    }



    @Override
    public Observable<Datum> getAudioFiles() {
        Observable<Datum> datumObservable = null;
        if(mCommonUtils.checkIfLocalJSONAvailable(mContext)){
            Gson gson = new Gson();
            File jsonfile = new File(mContext.getFilesDir() + File.separator + CommonUtils.SAVED_JSON_FILE_NAME);
            try {
                AudioBook audioBook =  gson.fromJson(new FileReader(jsonfile), AudioBook.class);
                datumObservable = Observable.fromIterable(audioBook.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            startJSONDownloadService();
            datumObservable = mAudioManager.getAllSongs()
                    .map(audioBook -> audioBook.getData())
                    .flatMapObservable(data -> Observable.fromIterable(data));
        }

        return datumObservable;
    }

    private void startJSONDownloadService() {
        Intent jsonDownloadService = new Intent(mContext, JSONFetcherService.class);
        jsonDownloadService.putExtra(JSONFetcherService.JSON_URL, CommonUtils.JSON_DOWNLOAD_URL);
        mContext.startService(jsonDownloadService);
    }

    @Override
    public Call<ResponseBody> downloadSongFromURL(String url) {
        return mAudioManager.getSong(url);
    }

    @Override
    public void downloadSong(Datum datum) {
        Intent downloadService = new Intent(mContext, SongDownloadService.class);
        downloadService.putExtra(SongDownloadService.SONG_OBJECT, datum);
        mContext.startService(downloadService);
    }

    @Override
    public boolean checkIfAudioExists(Datum datum) {
        File songfile = new File(mContext.getFilesDir() + File.separator + datum.getItemId() + ".mp3");
        return songfile.exists();
    }
}

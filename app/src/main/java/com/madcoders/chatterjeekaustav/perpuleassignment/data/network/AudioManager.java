package com.madcoders.chatterjeekaustav.perpuleassignment.data.network;

import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.AudioBook;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Url;

/**
 * Created by Kaustav on 09-03-2018.
 */

public class AudioManager{

    private Retrofit mRetrofit;
    private AudioService mAudioService;

    @Inject
    public AudioManager(Retrofit retrofit){
        mRetrofit = retrofit;
        mAudioService = mRetrofit.create(AudioService.class);
    }


    public Single<AudioBook> getAllSongs() {
        return mAudioService.getFiles();
    }

    public Call<ResponseBody> getSong(@Url String url){
        return mAudioService.downloadFileWithDynamicUrlSync(url);
    }
}

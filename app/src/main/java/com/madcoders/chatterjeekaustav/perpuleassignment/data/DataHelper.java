package com.madcoders.chatterjeekaustav.perpuleassignment.data;



import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.Datum;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Url;

/**
 * Created by Kaustav on 09-03-2018.
 */

public interface DataHelper {

    Observable<Datum> getAudioFiles();

    Call<ResponseBody> downloadSongFromURL(@Url String url);

    void downloadSong(Datum datum);

    boolean checkIfAudioExists(Datum datum);

}


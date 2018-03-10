package com.madcoders.chatterjeekaustav.perpuleassignment.data.network;

import com.madcoders.chatterjeekaustav.perpuleassignment.data.Model.AudioBook;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Kaustav on 09-03-2018.
 */

public interface AudioService {

    String BASE_URL = "https://api.myjson.com/";

    @GET("bins/mxcsl")
    Single<AudioBook> getFiles();

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

}

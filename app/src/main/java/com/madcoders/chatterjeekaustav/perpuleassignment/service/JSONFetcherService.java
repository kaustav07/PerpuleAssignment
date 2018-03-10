package com.madcoders.chatterjeekaustav.perpuleassignment.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.events.SongDownloadComplete;
import com.madcoders.chatterjeekaustav.perpuleassignment.data.network.AudioService;
import com.madcoders.chatterjeekaustav.perpuleassignment.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class JSONFetcherService extends IntentService {

    public static final String JSON_URL = "jsonurl";

    String JSONURL;

    private Retrofit mRetrofit;

    private AudioService mAudioService;

    public JSONFetcherService() {
        super("JSONFetcherService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(AudioService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mAudioService = mRetrofit.create(AudioService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       if(intent.hasExtra(JSON_URL)){
           JSONURL = intent.getStringExtra(JSON_URL);
       }

        try {
            Response<ResponseBody> responseBody = mAudioService.downloadFileWithDynamicUrlSync(JSONURL).execute();
            if (responseBody.isSuccessful()) {
                saveOnDisk(responseBody.body());
                //EventBus.getDefault().post(new SongDownloadComplete(mDatum.getItemId(), status));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean saveOnDisk(ResponseBody body) {
        try {
            File songfile = new File(getFilesDir() + File.separator + CommonUtils.SAVED_JSON_FILE_NAME);

            if (!songfile.exists()) {
                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    byte[] fileReader = new byte[4096];

                    long fileSize = body.contentLength();
                    long fileSizeDownloaded = 0;

                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(songfile);

                    while (true) {
                        int read = inputStream.read(fileReader);

                        if (read == -1) {
                            break;
                        }

                        outputStream.write(fileReader, 0, read);

                        fileSizeDownloaded += read;

                        Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }

                    outputStream.flush();

                    return true;
                } catch (IOException e) {
                    return false;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            }
            else
                return true;
        } catch (IOException e) {
            return false;
        }

    }

}

package com.madcoders.chatterjeekaustav.perpuleassignment.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.madcoders.chatterjeekaustav.perpuleassignment.R;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by Kaustav on 10-03-2018.
 */

public class CommonUtils {

    public static String SAVED_JSON_FILE_NAME = "Perpule.json";
    public static String JSON_DOWNLOAD_URL = "https://api.myjson.com/bins/mxcsl";

    @Inject
    public CommonUtils(){

    }

    public ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminateDrawable(new CubeGrid());
        return progressDialog;
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkIfLocalJSONAvailable(Context context){
        File jsonfile = new File(context.getFilesDir() + File.separator + CommonUtils.SAVED_JSON_FILE_NAME);
        return jsonfile.exists();
    }
}

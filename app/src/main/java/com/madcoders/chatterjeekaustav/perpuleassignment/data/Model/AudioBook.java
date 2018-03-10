
package com.madcoders.chatterjeekaustav.perpuleassignment.data.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AudioBook {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public AudioBook withData(List<Datum> data) {
        this.data = data;
        return this;
    }

}

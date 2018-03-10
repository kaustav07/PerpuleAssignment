
package com.madcoders.chatterjeekaustav.perpuleassignment.data.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable
{

    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("downloaded")
    @Expose
    private Boolean downloaded;
    public final static Parcelable.Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
            ;

    protected Datum(Parcel in) {
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.desc = ((String) in.readValue((String.class.getClassLoader())));
        this.audio = ((String) in.readValue((String.class.getClassLoader())));
        this.downloaded = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Datum() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Datum withItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Datum withDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Datum withAudio(String audio) {
        this.audio = audio;
        return this;
    }

    public Boolean getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Boolean downloaded) {
        this.downloaded = downloaded;
    }

    public Datum withDownloaded(Boolean downloaded) {
        this.downloaded = downloaded;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(itemId);
        dest.writeValue(desc);
        dest.writeValue(audio);
        dest.writeValue(downloaded);
    }

    public int describeContents() {
        return 0;
    }

}
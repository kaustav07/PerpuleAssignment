package com.madcoders.chatterjeekaustav.perpuleassignment.data.events;

/**
 * Created by Kaustav on 09-03-2018.
 */

public class SongDownloadComplete {

    private String itemId;
    private boolean status;

    public SongDownloadComplete(String itemId,boolean status){
        this.itemId = itemId;
        this.status = status;
    }

    public String getItemId() {
        return itemId;
    }

    public boolean isStatus() {
        return status;
    }
}

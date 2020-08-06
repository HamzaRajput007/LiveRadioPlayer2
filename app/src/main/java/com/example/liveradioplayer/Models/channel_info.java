package com.example.liveradioplayer.Models;

public class channel_info {

    String chanel_name;
    int imageID;

    public int getPopularImageID() {
        return popularImageID;
    }

    public void setPopularImageID(int popularImageID) {
        this.popularImageID = popularImageID;
    }

    int popularImageID;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public channel_info() {

    }

    public String getChanel_name() {
        return chanel_name;
    }

    public void setChanel_name(String chanel_name) {
        this.chanel_name = chanel_name;
    }
}

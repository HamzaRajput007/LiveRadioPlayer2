package com.example.liveradioplayer.Models;

import androidx.recyclerview.widget.RecyclerView;

public class PopularStationsModel  {

    int image;
    String title ;

    public PopularStationsModel(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public PopularStationsModel(int image) {
        this.image = image;
    }
}

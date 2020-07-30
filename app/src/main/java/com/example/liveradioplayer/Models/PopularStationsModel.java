package com.example.liveradioplayer.Models;

import androidx.recyclerview.widget.RecyclerView;

public class PopularStationsModel  {

    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public PopularStationsModel(int image) {
        this.image = image;
    }
}

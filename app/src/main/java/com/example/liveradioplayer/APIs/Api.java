package com.example.liveradioplayer.APIs;

import com.example.liveradioplayer.Models.ChannelThumbnailModel;
import com.example.liveradioplayer.Models.ChannelsModelClasses.Channel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("posts")
    public Call<List<Channel>> getFeaturedChannels(@Query("search") String searchString );

    @GET("posts/5")
    public  Call<List<ChannelThumbnailModel>> getUsChannelThumbnails();

    @GET("posts/6")
    public  Call<List<ChannelThumbnailModel>> getUkChannelThumbnails();

    @GET("posts/424")
    public  Call<List<ChannelThumbnailModel>> getEgyptChannelThumbnails();

    @GET("posts/636")
    public  Call<List<ChannelThumbnailModel>> getFeaturedChannelThumbnails();

    @GET("posts/4")
    public  Call<List<ChannelThumbnailModel>> getMalaysiaChannelThumbnails();

    @GET("posts/7")
    public  Call<List<ChannelThumbnailModel>> getCanadaChannelThumbnails();



}

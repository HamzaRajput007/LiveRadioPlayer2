package com.example.liveradioplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liveradioplayer.Adapters.HomeScreenRvAdapter;
import com.example.liveradioplayer.Adapters.MoreStationsRvAdapter;
import com.example.liveradioplayer.Adapters.PopularStationsRvAdapter;
import com.example.liveradioplayer.Models.ChannelThumbnailModel;
import com.example.liveradioplayer.Models.MoreStationRvItemModel;
import com.example.liveradioplayer.Models.PopularStationsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationDescription extends AppCompatActivity implements  PopularStationsRvAdapter.PopularStationInterface , MoreStationsRvAdapter.MoreStationInterface {

    TextView channelTitle , channelDescription , radioName ;
    Button btnLive , btnViews , btnBack;
    RecyclerView recyclerViewHome, popularStationsRecyclerView ;
    ImageView playButton;
    ArrayList<MoreStationRvItemModel> arrayList;
    ArrayList<ChannelThumbnailModel> channels;
    MoreStationsRvAdapter rvAdapter;
    Dialog dialog;
    ArrayList<PopularStationsModel> popularStationsModelArrayList;
    PopularStationsRvAdapter popularStationsRvAdapter;
    String channelTitleString , popularChannelString , channelTitleFromHome , streamingLink , descriptionOfChannel , imageURL;
    int category;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_description);
        arrayList = new ArrayList<>();
        popularStationsModelArrayList = new ArrayList<>();
        channelTitle = findViewById(R.id.textViewRadioTitleId);
        channelTitle.setTransitionName("ChannelNameFromHome");
        channelDescription = findViewById(R.id.channelDescriptionTextViewId);
        radioName = findViewById(R.id.textViewRadioName);
        btnLive = findViewById(R.id.btnLiveId);
        btnViews = findViewById(R.id.btnViewsId);
        recyclerViewHome = findViewById(R.id.moreStationsRvId);
        popularStationsRecyclerView = findViewById(R.id.popularStationsRvId);
        btnBack = findViewById(R.id.backBtnDescription);
        dialog = new Dialog("Loading Category");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finishAfterTransition();
            }
        });

        channels = new ArrayList<>();

        Intent intent = getIntent();
        Bundle channelsBundle = new Bundle();
        channelTitleFromHome = intent.getStringExtra("titleFromHome");
        channelTitleString = intent.getStringExtra("ChannelTitleFromMain");
        streamingLink = intent.getStringExtra("streamingLind");
        descriptionOfChannel = intent.getStringExtra("channelDescription");
        imageURL = intent.getStringExtra("imageUrl");
        channelsBundle = intent.getBundleExtra("ChannelsListBundle");
        channels = (ArrayList<ChannelThumbnailModel>) channelsBundle.getSerializable("ChannelsList");
        category = intent.getIntExtra("categoryNumber",-1);

        if (category == -1){
            Toast.makeText(this, "Invalid Category", Toast.LENGTH_SHORT).show();
        }
        else{
           /* switch (category){

                case 4:
                    getMalaysiaChannelThumbnails();
                    break;

                case 5:
                    getUsChannelThumbnails();
                    break;

                case 6 :
                    getUkChannelThumbnails();
                    break;

                case 7 :
                    getCanadaChannelThumbnails();
                    break;

                case 424:
                    getEgyptChannelThumbnails();
                    break;

                case 636:
                    getFeaturedChannelThumbnails();
                    break;

                default:
                    Toast.makeText(this, "No Channels to Load", Toast.LENGTH_SHORT).show();
                    break;

            }*/
        }


//        Setting Channel Title
        if(channelTitleString != null) {
            channelTitle.setText(channelTitleString);
            radioName.setTransitionName(channelTitleString);
        }
        else if (channelTitleString == ""){
            channelTitle.setText(channelTitleString);
            radioName.setText(channelTitleString);
        }
        if(channelTitleFromHome != null) {
            channelTitle.setText(channelTitleFromHome);
            radioName.setText(channelTitleFromHome);
        }
        else if (channelTitleFromHome == ""){
            channelTitle.setText(channelTitleFromHome);
            radioName.setText(channelTitleFromHome);
        }

//        Setting Channel Descriptions

//        Toast.makeText(this, values, Toast.LENGTH_SHORT).show();
        channelDescription.setText(getDescriptionOfChannel(descriptionOfChannel));

        playButton = findViewById(R.id.playBtnId);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPlayerActivity  = new Intent(getApplicationContext(), RadioPlayer.class);
                if(channelTitleString != null || channelTitleFromHome != null) {
                    toPlayerActivity.putExtra("StationName", channelTitleFromHome);
                    toPlayerActivity.putExtra("streamLink" , streamingLink);
                    toPlayerActivity.putExtra("ImageUrl" ,imageURL );
                    startActivity(toPlayerActivity);
                }
                else{
                    channelTitleString = "";
                    Toast.makeText(StationDescription.this, "No Channel To Show Please Select A Valid Channel", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPlayerActivity = new Intent(StationDescription.this , RadioPlayer.class);
                toPlayerActivity.putExtra("StationName", channelTitleFromHome);
                toPlayerActivity.putExtra("streamLink" , streamingLink);
                toPlayerActivity.putExtra("ImageUrl" ,imageURL );
                startActivity(toPlayerActivity);
            }
        });
        /*//More Stations RecyclerView Data Initialization
    *//*    arrayList.add(new MoreStationRvItemModel(R.drawable.am670 , "AM 970 ASPN RADIO"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wmkv , "WMKV 89.3 FM"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wgr550 , "WGR 550 sports radio"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wnjo , "WNJO 90.3"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.kmrbam1430 , "KMRB AM1430"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wsis600 , "WSIS 600"));*//*
//        arrayList.add(new MoreStationRvItemModel(R.drawable.am670 , "This is a very very very long text to be adjusted in the Title"));

        //Popular Stations RecyclerView Data Initialization
       *//* popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wgr550 ,"WGR 550 sports radio") );
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wsis600 ,"WSIS 600"));
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.kmrbam1430 , "KMRB AM1430"));
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wmkv , "WMKV 89.3 FM"));
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.am670 , "AM 970 ASPN RADIO"));*//*

        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wmkv , "WMKV 89.3 FM" ));
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.am670, "AM 970 ASPN RADIO" ));
*/

        //Popular Stations  Adapter Initialization and setting adapter to the Recycler View
        popularStationsRvAdapter = new PopularStationsRvAdapter(popularStationsModelArrayList , this);
        popularStationsRecyclerView.setAdapter(popularStationsRvAdapter );
        popularStationsRecyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        popularStationsRecyclerView.setHasFixedSize(true);


    }
    @Override
    public void onChannelClicked(int position) {
        ChannelThumbnailModel model =  channels.get(position);
        channelTitleString = model.getTitle();
        if(channelTitleString != null) {
            channelTitleFromHome = channelTitleString;
            channelTitle.setText(channelTitleString);
            radioName.setText(channelTitleString);
            radioName.setText(channelTitleString);
        }
        streamingLink = getStreamingLing(channels.get(position).getContent());
        channelDescription.setText(getDescriptionOfChannel(channels.get(position).getContent()));
        imageURL = channels.get(position).getThumbnailUrl().get(0);
    }

    @Override
    public void popularStationClicked(int position) {
        PopularStationsModel popularStationsModel = popularStationsModelArrayList.get(position);
        popularChannelString = popularStationsModel.getTitle();
        if(popularChannelString != null) {
            channelTitle.setText(popularChannelString);
            channelTitleString = popularChannelString;
            channelTitleFromHome = popularChannelString;
        }
        else{
            Toast.makeText(this, "Title is Null", Toast.LENGTH_SHORT).show();
        }

        rvAdapter = new MoreStationsRvAdapter(StationDescription.this , channels, StationDescription.this);
        recyclerViewHome.setAdapter(rvAdapter);
    }
   /* private void getChannels(int catogoryNum , int perPage){
        Call<List<Channel>> call = RetrofitClient.getInstance().getApi().getFeaturedChannels(catogoryNum);
        dialog.startDialog(this);
        call.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call, Response<List<Channel>> response) {
                if (response.isSuccessful()){
                    channelArrayList = (ArrayList<Channel>) response.body();
                    dialog.progressDialog.dismiss();
                    //More Stations  Adapter Initialization and setting adapter to the Recycler View
                    adapter = new MoreStationsRvAdapter(StationDescription.this , channelArrayList , StationDescription.this);
                    moreStationsRecyclerView.setAdapter(adapter);
                    moreStationsRecyclerView.setLayoutManager(new LinearLayoutManager(StationDescription.this, LinearLayoutManager.HORIZONTAL, false));
                    popularStationsRecyclerView.setHasFixedSize(true);
                }
                else{
                    dialog.progressDialog.dismiss();
                    Toast.makeText(StationDescription.this, "Response not successful !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {
                dialog.progressDialog.dismiss();
                Toast.makeText(StationDescription.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public String getStreamingLing(String longText){
        String captured = longText.substring(longText.indexOf("class=\"xradiostream\">") + 21);
        String[] realResut = captured.split("</li><li");
        return realResut[0];
    }

    public String getDescriptionOfChannel(String descriptionOfChannel){
        Spanned parsedText ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            parsedText =  Html.fromHtml(descriptionOfChannel, Html.FROM_HTML_MODE_LEGACY);
        } else {
            parsedText =  Html.fromHtml(descriptionOfChannel);
        }
        String pattern = ".com.";
        String values = "";
        String[] result = parsedText.toString().split(pattern);
        int i = 0;
        for (String s : result) {
            if (i < 2){
                values = values + s;
            }
            else{
                break;
            }
            i++;
        }

        values = values + ".com.";
        return values;
    }

    private void getMalaysiaChannelThumbnails(){
        Call<List<ChannelThumbnailModel>> call = RetrofitClient.getInstance().getApi().getMalaysiaChannelThumbnails();
        dialog.startDialog(this);
        call.enqueue(new Callback<List<ChannelThumbnailModel>>() {
            @Override
            public void onResponse(Call<List<ChannelThumbnailModel>> call, Response<List<ChannelThumbnailModel>> response) {
                if(response.isSuccessful()) {
                    channels = (ArrayList<ChannelThumbnailModel>) response.body();
                    dialog.progressDialog.dismiss();
                    rvAdapter = new MoreStationsRvAdapter(StationDescription.this , channels, StationDescription.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    Toast.makeText(StationDescription.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                Toast.makeText(StationDescription.this, "Thumbnails Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUsChannelThumbnails(){
        Call<List<ChannelThumbnailModel>> call = RetrofitClient.getInstance().getApi().getUsChannelThumbnails();
        dialog.startDialog(this);
        call.enqueue(new Callback<List<ChannelThumbnailModel>>() {
            @Override
            public void onResponse(Call<List<ChannelThumbnailModel>> call, Response<List<ChannelThumbnailModel>> response) {
                if(response.isSuccessful()) {
                    channels = (ArrayList<ChannelThumbnailModel>) response.body();
                    dialog.progressDialog.dismiss();
                    rvAdapter = new MoreStationsRvAdapter(StationDescription.this , channels, StationDescription.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    Toast.makeText(StationDescription.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                Toast.makeText(StationDescription.this, "Thumbnails Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getEgyptChannelThumbnails(){
        Call<List<ChannelThumbnailModel>> call = RetrofitClient.getInstance().getApi().getEgyptChannelThumbnails();
        dialog.startDialog(this);
        call.enqueue(new Callback<List<ChannelThumbnailModel>>() {
            @Override
            public void onResponse(Call<List<ChannelThumbnailModel>> call, Response<List<ChannelThumbnailModel>> response) {
                if(response.isSuccessful()) {
                    channels = (ArrayList<ChannelThumbnailModel>) response.body();
                    dialog.progressDialog.dismiss();
                    rvAdapter = new MoreStationsRvAdapter(StationDescription.this , channels, StationDescription.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    Toast.makeText(StationDescription.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                Toast.makeText(StationDescription.this, "Thumbnails Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getFeaturedChannelThumbnails(){
        Call<List<ChannelThumbnailModel>> call = RetrofitClient.getInstance().getApi().getFeaturedChannelThumbnails();
        dialog.startDialog(this);
        call.enqueue(new Callback<List<ChannelThumbnailModel>>() {
            @Override
            public void onResponse(Call<List<ChannelThumbnailModel>> call, Response<List<ChannelThumbnailModel>> response) {
                if(response.isSuccessful()) {
                    channels = (ArrayList<ChannelThumbnailModel>) response.body();
                    dialog.progressDialog.dismiss();
                    rvAdapter = new MoreStationsRvAdapter(StationDescription.this , channels, StationDescription.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    Toast.makeText(StationDescription.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                Toast.makeText(StationDescription.this, "Thumbnails Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCanadaChannelThumbnails(){
        Call<List<ChannelThumbnailModel>> call = RetrofitClient.getInstance().getApi().getCanadaChannelThumbnails();
        dialog.startDialog(this);
        call.enqueue(new Callback<List<ChannelThumbnailModel>>() {
            @Override
            public void onResponse(Call<List<ChannelThumbnailModel>> call, Response<List<ChannelThumbnailModel>> response) {
                if(response.isSuccessful()) {
                    channels = (ArrayList<ChannelThumbnailModel>) response.body();
                    dialog.progressDialog.dismiss();
                    rvAdapter = new MoreStationsRvAdapter(StationDescription.this , channels, StationDescription.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    Toast.makeText(StationDescription.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                Toast.makeText(StationDescription.this, "Thumbnails Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUkChannelThumbnails(){
        Call<List<ChannelThumbnailModel>> call = RetrofitClient.getInstance().getApi().getUkChannelThumbnails();
        dialog.startDialog(this);
        call.enqueue(new Callback<List<ChannelThumbnailModel>>() {
            @Override
            public void onResponse(Call<List<ChannelThumbnailModel>> call, Response<List<ChannelThumbnailModel>> response) {
                if(response.isSuccessful()) {
                    channels = (ArrayList<ChannelThumbnailModel>) response.body();
                    dialog.progressDialog.dismiss();
                    rvAdapter = new MoreStationsRvAdapter(StationDescription.this , channels, StationDescription.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    Toast.makeText(StationDescription.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                Toast.makeText(StationDescription.this, "Thumbnails Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
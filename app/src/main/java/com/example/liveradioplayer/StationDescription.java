package com.example.liveradioplayer;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liveradioplayer.Adapters.MoreStationsRvAdapter;
import com.example.liveradioplayer.Adapters.PopularStationsRvAdapter;
import com.example.liveradioplayer.Models.MoreStationRvItemModel;
import com.example.liveradioplayer.Models.PopularStationsModel;

import java.util.ArrayList;

public class StationDescription extends AppCompatActivity implements MoreStationsRvAdapter.MoreStationInterface , PopularStationsRvAdapter.PopularStationInterface {

    TextView channelTitle , channelDescription , radioName ;
    Button btnLive , btnViews;
    RecyclerView moreStationsRecyclerView , popularStationsRecyclerView ;
    ImageView playButton;
    ArrayList<MoreStationRvItemModel> arrayList;
    MoreStationsRvAdapter adapter ;
    ArrayList<PopularStationsModel> popularStationsModelArrayList;
    PopularStationsRvAdapter popularStationsRvAdapter;
    String channelTitleString , popularChannelString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_description);
        arrayList = new ArrayList<>();
        popularStationsModelArrayList = new ArrayList<>();
        channelTitle = findViewById(R.id.textViewRadioTitleId);
        channelDescription = findViewById(R.id.channelDescriptionTextViewId);
        radioName = findViewById(R.id.textViewRadioName);
        btnLive = findViewById(R.id.btnLiveId);
        btnViews = findViewById(R.id.btnViewsId);
        moreStationsRecyclerView = findViewById(R.id.moreStationsRvId);
        popularStationsRecyclerView = findViewById(R.id.popularStationsRvId);

        Intent intent = getIntent();
        channelTitleString = intent.getStringExtra("Title");
        popularChannelString = intent.getStringExtra("PopularTitle");
        if(channelTitleString != null) {
            channelTitle.setText(channelTitleString);
        }
        else if(popularChannelString != null ){
            channelTitle.setText(popularChannelString);
        }
        else if(popularChannelString == null && channelTitleString == null ){
            channelTitle.setText("No Channel To Show");
        }


        playButton = findViewById(R.id.playBtnId);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPlayerActivity  = new Intent(getApplicationContext(), RadioPlayer.class);
                if(channelTitleString != null) {
                    toPlayerActivity.putExtra("StationName", channelTitleString);
                    startActivity(toPlayerActivity);
                }
                else{
                    Toast.makeText(StationDescription.this, "No Channel To Show Please Select A Valid Channel", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //More Stations RecyclerView Data Initialization
        arrayList.add(new MoreStationRvItemModel(R.drawable.am670 , "AM 970 ASPN RADIO"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wmkv , "WMKV 89.3 FM"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wgr550 , "WGR 550 sports radio"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wnjo , "WNJO 90.3"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.kmrbam1430 , "KMRB AM1430"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wsis600 , "WSIS 600"));
//        arrayList.add(new MoreStationRvItemModel(R.drawable.am670 , "This is a very very very long text to be adjusted in the Title"));

        //Popular Stations RecyclerView Data Initialization
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wgr550 ,"WGR 550 sports radio") );
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wsis600 ,"WSIS 600"));
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.kmrbam1430 , "KMRB AM1430"));
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wmkv , "WMKV 89.3 FM"));
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.am670 , "AM 970 ASPN RADIO"));

        //Popular Stations  Adapter Initialization and setting adapter to the Recycler View
        popularStationsRvAdapter = new PopularStationsRvAdapter(popularStationsModelArrayList , this);
        popularStationsRecyclerView.setAdapter(popularStationsRvAdapter );
        popularStationsRecyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        popularStationsRecyclerView.setHasFixedSize(true);

        //More Stations  Adapter Initialization and setting adapter to the Recycler View
        adapter = new MoreStationsRvAdapter(arrayList , this);
        moreStationsRecyclerView.setAdapter(adapter);
        moreStationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularStationsRecyclerView.setHasFixedSize(true);
    }
    @Override
    public void onChannelClicked(int position) {
        MoreStationRvItemModel model =  arrayList.get(position);
        Intent loadFromMoreChannels = new Intent(StationDescription.this , StationDescription.class );
        loadFromMoreChannels.putExtra("Title" , model.getTitle());
        startActivity(loadFromMoreChannels);
        finish();
    }

    @Override
    public void popularStationClicked(int position) {
        Toast.makeText(this,  String.valueOf(position) + " Clicked", Toast.LENGTH_SHORT).show();
        PopularStationsModel popularStationsModel = popularStationsModelArrayList.get(position);
        Intent loadFromPopularChannels = new Intent(StationDescription.this , StationDescription.class);
        loadFromPopularChannels.putExtra("PopularTitle" , popularStationsModel.getTitle());
        startActivity(loadFromPopularChannels);
        finish();
    }
}
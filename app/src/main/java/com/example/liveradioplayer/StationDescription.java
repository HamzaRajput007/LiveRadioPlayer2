package com.example.liveradioplayer;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.liveradioplayer.Adapters.MoreStationsRvAdapter;
import com.example.liveradioplayer.Adapters.PopularStationsRvAdapter;
import com.example.liveradioplayer.Models.MoreStationRvItemModel;
import com.example.liveradioplayer.Models.PopularStationsModel;

import java.util.ArrayList;

public class StationDescription extends AppCompatActivity {

    TextView channelTitle , channelDescription , radioName ;
    Button btnLive , btnViews;
    RecyclerView moreStationsRecyclerView , popularStationsRecyclerView ;

    ArrayList<MoreStationRvItemModel> arrayList;
    MoreStationsRvAdapter adapter ;

    ArrayList<PopularStationsModel> popularStationsModelArrayList;
    PopularStationsRvAdapter popularStationsRvAdapter;



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

        arrayList.add(new MoreStationRvItemModel(R.drawable.am670 , "AM 970 ASPN RADIO"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wmkv , "WMKV 89.3 FM"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wgr550 , "WGR 550 sports radio"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wnjo , "WNJO 90.3"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.kmrbam1430 , "KMRB AM1430"));
        arrayList.add(new MoreStationRvItemModel(R.drawable.wsis600 , "WSIS 600"));

        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wgr550) );
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wsis600) );
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.kmrbam1430) );
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.wmkv) );
        popularStationsModelArrayList.add(new PopularStationsModel(R.drawable.am670) );

        popularStationsRvAdapter = new PopularStationsRvAdapter(popularStationsModelArrayList);
        moreStationsRecyclerView.setAdapter(popularStationsRvAdapter);
        popularStationsRecyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));

        adapter = new MoreStationsRvAdapter(arrayList);
        moreStationsRecyclerView.setAdapter(adapter);
        moreStationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
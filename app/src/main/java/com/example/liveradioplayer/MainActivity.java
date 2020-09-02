package com.example.liveradioplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liveradioplayer.Adapters.Adapter;
import com.example.liveradioplayer.Adapters.popular_image_adaptter;
import com.example.liveradioplayer.Models.channel_info;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.MyFavouritesInterface {
    private RecyclerView mRecyclerView,mRecyclerView_Horizontal;
    private Adapter mAdapter;
    private popular_image_adaptter popular_image_adapter_obj;
    Spinner spinner;
    TextView myFavourite,favouriteCount;
    ArrayList<channel_info> arrayList ;
    //    private ImageAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFavourite=findViewById(R.id.myfavouriteTextViewId);
        favouriteCount=findViewById(R.id.favouriteCount);
        //  favouriteCount=findViewById(R.id.favouriteCount);
        spinner=findViewById(R.id.spinner);
        mRecyclerView=findViewById(R.id.recyler_view);
        mRecyclerView_Horizontal=findViewById(R.id.recyler_view_horizontal);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView_Horizontal.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        arrayList = new ArrayList<>();
        arrayList.add(new channel_info("AM 970" , R.drawable.group1 ));
        arrayList.add(new channel_info("MKV 600" , R.drawable.group2 ));
        arrayList.add(new channel_info("WMKV 550" , R.drawable.group3 ));

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView_Horizontal.setLayoutManager(linearLayoutManager);

        final String[] selection_list={"All","fav","Top"};
        ArrayAdapter selectionList = new ArrayAdapter<String>(this, R.layout.spinner_item, selection_list);
//        selectionList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(selectionList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),selection_list[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAdapter = new Adapter(getApplicationContext(),arrayList , this);
        mRecyclerView.setAdapter(mAdapter);
        popular_image_adapter_obj = new popular_image_adaptter(getApplicationContext(),arrayList);
        mRecyclerView_Horizontal.setAdapter(popular_image_adapter_obj);
        myFavourite.setText("My favourite (" + String.valueOf( arrayList.size()) +")");
    }

    @Override
    public void onFavouriteClick(int position) {
        Intent toDescription = new Intent(this , StationDescription.class);
        toDescription.putExtra("ChannelTitleFromMain" , arrayList.get(position).getChanel_name());
        startActivity(toDescription);
    }
}
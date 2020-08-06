package com.example.liveradioplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView,mRecyclerView_Horizontal;
    private Adapter mAdapter;
    private popular_image_adaptter popular_image_adapter_obj;
    Spinner spinner;
    TextView myFavourite,favouriteCount;
    //    private ImageAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFavourite=findViewById(R.id.myfavourite);
        favouriteCount=findViewById(R.id.favouriteCount);
        //  favouriteCount=findViewById(R.id.favouriteCount);
        spinner=findViewById(R.id.spinner);
        mRecyclerView=findViewById(R.id.recyler_view);
        mRecyclerView_Horizontal=findViewById(R.id.recyler_view_horizontal);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView_Horizontal.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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


        String channalarray[]={"A","B","C","D"};
        int channalImages[]={R.drawable.group1,R.drawable.group2,R.drawable.group3,R.drawable.group5};
        int popularImages[]={R.drawable.group1,R.drawable.group2,R.drawable.group3,R.drawable.group5};

        ArrayList<channel_info> info=new ArrayList<channel_info>();
        for(int i = 0 ; i < 4 ;i++)
        {
            channel_info channelInfo=new channel_info();
            channelInfo.setChanel_name(channalarray[i]);
            channelInfo.setImageID(channalImages[i]);

            channelInfo.setPopularImageID(popularImages[i]);

            info.add(channelInfo);
        }

        mAdapter = new Adapter(getApplicationContext(),info);
        mRecyclerView.setAdapter(mAdapter);


        popular_image_adapter_obj = new popular_image_adaptter(getApplicationContext(),info);
        mRecyclerView_Horizontal.setAdapter(popular_image_adapter_obj);



    }
}
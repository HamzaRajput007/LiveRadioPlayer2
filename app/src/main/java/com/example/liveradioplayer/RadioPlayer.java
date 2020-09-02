package com.example.liveradioplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.narayanacharya.waveview.WaveView;

public class RadioPlayer extends AppCompatActivity {

    TextView stationTitle , toolBarTextView;
    ImageView favBtn  , searchBtn , searchBtnToSearch;
    SharedPrefHelper sharedPrefHelper;
    EditText searchBarEditText;
    Boolean flag;
    ImageView stopContnueWave;
    Button backBtn;
    LottieAnimationView radioWaveView;
    WaveView sine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_player);
        sharedPrefHelper = new SharedPrefHelper(this);
        favBtn = findViewById(R.id.btnFavouriteId);
        backBtn = findViewById(R.id.backBtnPlayerId);
        searchBtn = findViewById(R.id.searchBtnIdPlayer);
        toolBarTextView = findViewById(R.id.toolbarTextView);
        searchBarEditText = findViewById(R.id.editTextSearchBarIdPlayer);
        searchBtnToSearch = findViewById(R.id.searchBtnIdPlayer2);

        flag=true;
        stopContnueWave=findViewById(R.id.play);

        sine = (WaveView) findViewById(R.id.waveView);
        sine.setBackgroundColor(getResources().getColor(R.color.playerWaveViewBgColor));
        sine.setWaveColor(getResources().getColor(R.color.colorPrimary));
        sine.setNumberOfWaves(3);
        sine.setFrequency(3.0f);
        sine.setAmplitude(10);
        sine.setPhaseShift(-0.05f);
        sine.setDensity(10.0f);
        /*sine.setPrimaryLineWidth(3.0f);
        sine.setSecondaryLineWidth(1.0f);
        */sine.setWaveXAxisPositionMultiplier(0.5f);

//        radioWaveView=findViewById(R.id.animation_radio_wave);

       /* if(!sine.isPlaying())
        {
            flag=false;
//            radioWaveView.playAnimation();
            sine.pause();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
            }
            else
            {
                Toast.makeText(this, "Your phone's Android Version is lower then LOLLIPOP", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            flag=true;
            sine.play();
//            radioWaveView.cancelAnimation();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.playbtnradioplayer, null));
            }
            else
            {
                Toast.makeText(this, "Your phone's Android Version is lower then LOLLIPOP", Toast.LENGTH_SHORT).show();
            }
        }*/

        stopContnueWave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(!sine.isPlaying())
                {
                    flag=false;
                    sine.play();
                    stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));

                }
                else
                {
                    flag=true;
                    sine.pause();
                    stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.playbtnradioplayer, null));

                }

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBarEditText.setVisibility(View.VISIBLE);
                searchBarEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchBarEditText, InputMethodManager.SHOW_IMPLICIT);

                searchBtnToSearch.setVisibility(View.VISIBLE);

                searchBtn.setVisibility(View.GONE);
                toolBarTextView.setVisibility(View.GONE);
                backBtn.setVisibility(View.GONE);
            }
        });

        searchBtnToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RadioPlayer.this, "You Searched " + searchBarEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        
        searchBarEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Toast.makeText(RadioPlayer.this, "You Searched " + searchBarEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toDescriptionActivity = new Intent(RadioPlayer.this , StationDescription.class);
                startActivity(toDescriptionActivity);
                finish();
            }
        });

        if(sharedPrefHelper.getFavouritesFromSharedPrefs() == false){
            favBtn.setImageDrawable( getResources().getDrawable(R.drawable.path));
            sharedPrefHelper.putToSharedPrefs(true);
        }
        else if (sharedPrefHelper.getFavouritesFromSharedPrefs() == true){
            favBtn.setImageDrawable(getResources().getDrawable(R.drawable.fav));
            sharedPrefHelper.putToSharedPrefs(false);
        }

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPrefHelper.getFavouritesFromSharedPrefs() == false){
                    favBtn.setImageDrawable( getResources().getDrawable(R.drawable.path));
                    sharedPrefHelper.putToSharedPrefs(true);
                }
                else if (sharedPrefHelper.getFavouritesFromSharedPrefs() == true){
                    favBtn.setImageDrawable(getResources().getDrawable(R.drawable.fav));
                    sharedPrefHelper.putToSharedPrefs(false);
                }
            }
        });
        stationTitle = findViewById(R.id.radioStationNameId);
        Intent intent = getIntent();
        String channelName = intent.getStringExtra("StationName");
        if(channelName != null){
            stationTitle.setText(channelName);
        }
        else {
            stationTitle.setText("Default Channel");
        }
    }
}
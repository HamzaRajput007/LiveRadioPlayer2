package com.example.liveradioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RadioPlayer extends AppCompatActivity {

    TextView stationTitle , toolBarTextView;
    ImageView favBtn , backBtn , searchBtn , searchBtnToSearch;
    SharedPrefHelper sharedPrefHelper;
    EditText searchBarEditText;

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
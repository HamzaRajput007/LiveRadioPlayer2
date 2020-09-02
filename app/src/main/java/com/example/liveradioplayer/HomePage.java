package com.example.liveradioplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liveradioplayer.Adapters.HomeScreenRvAdapter;
import com.example.liveradioplayer.Models.channel_info;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements Animation.AnimationListener, HomeScreenRvAdapter.OnItemClickListener {

    RecyclerView recyclerViewHome;
    Button btnUsChannels , btnUkChannels , btnMalasiaChannels , searchBtn;
    EditText searchBar;
    ArrayList<channel_info> usChannels , ukChannels , malasiaChannels;
    HomeScreenRvAdapter rvAdapter;
    // Animation
    Animation animBounce , animUs , animUk;
    MediaPlayer tapSoundPlayer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        getWindow().setExitTransition(new Explode());
        // load the animation
        setContentView(R.layout.activity_home_page);


        btnUsChannels = findViewById(R.id.btnUsChannelsID);
        btnUkChannels = findViewById(R.id.btnUkChannelsId);
        btnMalasiaChannels = findViewById(R.id.btnMalasiaChannelsId);
        searchBtn = findViewById(R.id.homeSearchBtnID);
        searchBar = findViewById(R.id.homeSearchBarID);
        recyclerViewHome = findViewById(R.id.homeScreenRecyclerViewID);


        animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);

        animUk = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        animUs = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               hideKeybaord(view);
            }
        });
        
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHome.setLayoutManager(linearLayoutManager);
        recyclerViewHome.setHasFixedSize(true);

        usChannels = new ArrayList<>();
        usChannels.add(new channel_info("US" , R.drawable.group1 ));
        usChannels.add(new channel_info("US" , R.drawable.group2 ));
        usChannels.add(new channel_info("US" , R.drawable.group3 ));
        usChannels.add(new channel_info("US" , R.drawable.group1 ));
        usChannels.add(new channel_info("US" , R.drawable.group2 ));
        usChannels.add(new channel_info("US" , R.drawable.group3 ));
        usChannels.add(new channel_info("US" , R.drawable.group1 ));
        usChannels.add(new channel_info("US" , R.drawable.group2 ));
        usChannels.add(new channel_info("US" , R.drawable.group3 ));

        btnUsChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvAdapter = new HomeScreenRvAdapter(HomePage.this , usChannels , HomePage.this);
                recyclerViewHome.setAdapter(rvAdapter);
                btnUsChannels.setAnimation(animUs);
                btnUsChannels.startAnimation(animUs);
                startBtnTapSound();
            }
        });

        ukChannels = new ArrayList<>();
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        ukChannels.add(new channel_info("UK" , R.drawable.group1 ));
        btnUkChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvAdapter = new HomeScreenRvAdapter(HomePage.this , ukChannels, HomePage.this);
                recyclerViewHome.setAdapter(rvAdapter);
                btnUkChannels.setAnimation(animUk);
                btnUkChannels.startAnimation(animUk);
                startBtnTapSound();

            }
        });

        malasiaChannels = new ArrayList<>();
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        malasiaChannels.add(new channel_info("MALAYSIA" , R.drawable.group1 ));
        btnMalasiaChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvAdapter = new HomeScreenRvAdapter(HomePage.this , malasiaChannels, HomePage.this);
                recyclerViewHome.setAdapter(rvAdapter);
                btnMalasiaChannels.setAnimation(animBounce);
                btnMalasiaChannels.startAnimation(animBounce);
                startBtnTapSound();

            }
        });

        rvAdapter = new HomeScreenRvAdapter(this , usChannels, HomePage.this);
        recyclerViewHome.setAdapter(rvAdapter);


    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
        searchBar.setCursorVisible(false);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void startBtnTapSound(){
        tapSoundPlayer = new MediaPlayer();
        tapSoundPlayer = MediaPlayer.create(getApplicationContext() , R.raw.btn_tap_sound);
        if(tapSoundPlayer == null) {
            Log.v("TAG", "Create() on MediaPlayer failed.");
        } else {
            tapSoundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mediaplayer) {
                    mediaplayer.stop();
                    mediaplayer.release();
                }
            });
            tapSoundPlayer.start();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "Position = " + String.valueOf(position), Toast.LENGTH_SHORT).show();
        Intent toDescription = new Intent(HomePage.this , StationDescription.class);
        toDescription.putExtra("ChannelTitle" , usChannels.get(position).getChanel_name());
        startActivity(toDescription , ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
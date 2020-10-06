package com.example.liveradioplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liveradioplayer.Adapters.HomeScreenRvAdapter;
import com.example.liveradioplayer.Models.ChannelThumbnailModel;
import com.example.liveradioplayer.Models.ChannelsModelClasses.Channel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity implements Animation.AnimationListener, HomeScreenRvAdapter.OnItemClickListener {

    RecyclerView recyclerViewHome;
    Button btnUsChannels , btnUkChannels , btnMalasiaChannels , searchBtn;
    EditText searchBar;
    ArrayList<Channel> usChannels , ukChannels , malasiaChannels , searchedResultChannels;
    HomeScreenRvAdapter rvAdapter;
    ArrayList<ChannelThumbnailModel> channels;
    TextView canadaTV , egyptTV , featuredTV;
    ArrayList<ChannelThumbnailModel> channelThumbnailModelArrayList;
    int category;
    Dialog dialog;


    // Animation
    Animation animBounce , animUs , animUk , usZoomOut , ukZoomOut , malaysiaZoomOut;
    MediaPlayer tapSoundPlayer;
    TextView channelTitle;
    boolean isZoomInUs = false  , isZoomInUk = false , isZoomInMalaysia = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inside your activity (if you did not enable transitions in your theme)
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
//        getWindow().setExitTransition(new Explode());
        // load the animation
        setContentView(R.layout.activity_home_page);

        //todo We are getting response from the server but Adpter's getItemCount Method is not being called thats why nothing is shown over there [DONE]
        channels  = new ArrayList<>();
        category = 636;
        dialog = new Dialog("Loading...");
        btnUsChannels = findViewById(R.id.btnUsChannelsID);
        btnUkChannels = findViewById(R.id.btnUkChannelsId);
        btnMalasiaChannels = findViewById(R.id.btnMalasiaChannelsId);
        searchBtn = findViewById(R.id.homeSearchBtnID);
        searchBar = findViewById(R.id.homeSearchBarID);
        recyclerViewHome = findViewById(R.id.homeScreenRecyclerViewID);
        channelTitle = findViewById(R.id.textviewHomeScreenItemID);
        canadaTV = findViewById(R.id.textViewCanadaHome);
        egyptTV = findViewById(R.id.textViewEgyptHome);
        featuredTV = findViewById(R.id.textViewFeaturedHome);
        channelThumbnailModelArrayList = new ArrayList<>();
        searchedResultChannels = new ArrayList<>();
        animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);

        animUk = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        animUs = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        usZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);
        ukZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);
        malaysiaZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               hideKeybaord(view);
            }
        });
       /* malaysiaZoomOut = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.zoom_out);
        usZoomOut = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.zoom_out);
        ukZoomOut = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.zoom_out);*/

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHome.setLayoutManager(linearLayoutManager);
        recyclerViewHome.setHasFixedSize(true);

       getFeaturedChannelThumbnails();


        /*LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0 , 0 , 0 , 0 );
        btnMalasiaChannels.setLayoutParams(layoutParams);
        btnUkChannels.setLayoutParams(layoutParams);
        btnUsChannels.setLayoutParams(layoutParams);*/

        usChannels = new ArrayList<>();

        btnUsChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnUsChannels.setAnimation(animUs);
                btnUsChannels.startAnimation(animUs);
                zoomOutTheButton(isZoomInUs , isZoomInUk , isZoomInMalaysia);
                isZoomInUs = true;
                startBtnTapSound();
                featuredTV.setTextColor(getResources().getColor(R.color.lightWhite));
                canadaTV.setTextColor(getResources().getColor(R.color.lightWhite));
                egyptTV.setTextColor(getResources().getColor(R.color.lightWhite));
                category = 5;
                getUsChannelThumbnails();

            }
        });

        ukChannels = new ArrayList<>();
        btnUkChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUkChannels.setAnimation(animUk);
                btnUkChannels.startAnimation(animUk);
                zoomOutTheButton(isZoomInUs , isZoomInUk , isZoomInMalaysia);
                featuredTV.setTextColor(getResources().getColor(R.color.lightWhite));
                canadaTV.setTextColor(getResources().getColor(R.color.lightWhite));
                egyptTV.setTextColor(getResources().getColor(R.color.lightWhite));
                isZoomInUk = true;
                startBtnTapSound();
                category = 6;
                getUkChannelThumbnails();

            }
        });

        malasiaChannels = new ArrayList<>();

        btnMalasiaChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMalasiaChannels.setAnimation(animBounce);
                btnMalasiaChannels.startAnimation(animBounce);
                zoomOutTheButton(isZoomInUs , isZoomInUk, isZoomInMalaysia);
                featuredTV.setTextColor(getResources().getColor(R.color.lightWhite));
                canadaTV.setTextColor(getResources().getColor(R.color.lightWhite));
                egyptTV.setTextColor(getResources().getColor(R.color.lightWhite));
                isZoomInMalaysia = true;
                startBtnTapSound();
                category = 4;
                getMalaysiaChannelThumbnails();

            }
        });
        canadaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canadaTV.setTextColor(getResources().getColor(R.color.white));
                featuredTV.setTextColor(getResources().getColor(R.color.lightWhite));
                egyptTV.setTextColor(getResources().getColor(R.color.lightWhite));
                category = 7 ;
                getCanadaChannelThumbnails();
            }
        });

        featuredTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                featuredTV.setTextColor(getResources().getColor(R.color.white));
                egyptTV.setTextColor(getResources().getColor(R.color.lightWhite));
                canadaTV.setTextColor(getResources().getColor(R.color.lightWhite));
                category = 636;
                getFeaturedChannelThumbnails();

            }
        });

        egyptTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = 424;
                getEgyptChannelThumbnails();
                egyptTV.setTextColor(getResources().getColor(R.color.white));
                featuredTV.setTextColor(getResources().getColor(R.color.lightWhite));
                canadaTV.setTextColor(getResources().getColor(R.color.lightWhite));

            }
        });

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

    //Todo this function is not working good ... zoom out functionality is not working fine
    public void zoomOutTheButton(boolean isZoomInUs2 , boolean isZoomInUk2 , boolean isZoomInMalaysia2 ){
        if (isZoomInMalaysia2){
            isZoomInMalaysia = false;
            btnMalasiaChannels.setAnimation(malaysiaZoomOut);
            btnMalasiaChannels.startAnimation(malaysiaZoomOut);
        } else if (isZoomInUs2){
            isZoomInUs = false;
            btnUkChannels.setAnimation(usZoomOut);
            btnUsChannels.startAnimation(usZoomOut);
        } else if (isZoomInUk2){
            isZoomInUk = false;
            btnUkChannels.setAnimation(ukZoomOut);
            btnUkChannels.startAnimation(ukZoomOut);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClicked(int position) {



        Intent toDescription = new Intent(HomePage.this , StationDescription.class);
        toDescription.putExtra("ChannelTitle" , channels.get(position).getTitle());
        String link = getStreamingLing(channels.get(position).getContent());
        toDescription.putExtra("streamingLind" , link);
        toDescription.putExtra("channelDescription" , channels.get(position).getContent());
        toDescription.putExtra("categoryNumber" , category);
        toDescription.putExtra("imageUrl" , channels.get(position).getThumbnailUrl().get(0));
        Bundle channelsBundle = new Bundle();
        channelsBundle.putSerializable("ChannelsList" , (Serializable) channels);
        toDescription.putExtra("ChannelsListBundle" ,channelsBundle);
        HomeScreenRvAdapter.HomeViewHolder selectedViewHolder = (HomeScreenRvAdapter.HomeViewHolder) recyclerViewHome.findViewHolderForAdapterPosition(position); // this is the textview in the row_item_of_recyclerView at home
        TextView descriptionTitleTv =  selectedViewHolder.getTitleTv();                                                                                            // we have to give this view to the ActivityOptionsCompat optinos.
        String title = descriptionTitleTv.getText().toString();
        if (title != null ) {
            toDescription.putExtra("titleFromHome" , title);
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, descriptionTitleTv, "ChannelNameFromHome"); // this is where we are making the activityOptionsCompat.
            startActivity(toDescription/*, options.toBundle()*/); // Now send the options as a budle to the receiving activity along with the intent in start activity function.

            // NOTE : Transition names of both views should be the same

        }
    }

    public String getStreamingLing(String longText){
        String captured = longText.substring(longText.indexOf("class=\"xradiostream\">") + 21);
        String[] realResut = captured.split("</li><li");
        return realResut[0];
    }

    /*private void getChannels(int catogoryNum , int perPage){
        Call<List<ChannelThumbnailModel>> call = RetrofitClient.getInstance().getApi().getFeaturedChannelThumbnails();
        dialog.startDialog(this);
        call.enqueue(new Callback<List<ChannelThumbnailModel>>() {
            @Override
            public void onResponse(Call<List<ChannelThumbnailModel>> call, Response<List<ChannelThumbnailModel>> response) {
                if (response.isSuccessful()){
                    channels = (ArrayList<ChannelThumbnailModel>) response.body();
                    dialog.progressDialog.dismiss();
                    rvAdapter = new HomeScreenRvAdapter(HomePage.this , channels, HomePage.this);
                    recyclerViewHome.setAdapter(rvAdapter);
                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channels.size()) + " Records", Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Response not successful !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {
                dialog.progressDialog.dismiss();
                Toast.makeText(HomePage.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void getMalaysiaChannelThumbnails(){
        Call<List<ChannelThumbnailModel>> call = RetrofitClient.getInstance().getApi().getMalaysiaChannelThumbnails();
        dialog.startDialog(this);
        call.enqueue(new Callback<List<ChannelThumbnailModel>>() {
            @Override
            public void onResponse(Call<List<ChannelThumbnailModel>> call, Response<List<ChannelThumbnailModel>> response) {
                if(response.isSuccessful()) {
                    channels = (ArrayList<ChannelThumbnailModel>) response.body();
                    dialog.progressDialog.dismiss();
                    rvAdapter = new HomeScreenRvAdapter(HomePage.this , channels, HomePage.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                dialog.progressDialog.dismiss();
                Toast.makeText(HomePage.this, "Failed to Load Channels", Toast.LENGTH_SHORT).show();
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
                    rvAdapter = new HomeScreenRvAdapter(HomePage.this , channels, HomePage.this);
                    recyclerViewHome.setAdapter(rvAdapter);

                }
                else {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                dialog.progressDialog.dismiss();
                Toast.makeText(HomePage.this, "Failed to Load Channels : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Failed US", "onFailure: " + t.getMessage());
                Log.d("Reason", "Cause: " + t.getCause().toString());
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
                    rvAdapter = new HomeScreenRvAdapter(HomePage.this , channels, HomePage.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                dialog.progressDialog.dismiss();
                Toast.makeText(HomePage.this, "Failed to Load Channels", Toast.LENGTH_SHORT).show();
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
                    rvAdapter = new HomeScreenRvAdapter(HomePage.this , channels, HomePage.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                dialog.progressDialog.dismiss();
                Toast.makeText(HomePage.this, "Failed to Load Channels", Toast.LENGTH_SHORT).show();
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
                    rvAdapter = new HomeScreenRvAdapter(HomePage.this , channels, HomePage.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                dialog.progressDialog.dismiss();
                Toast.makeText(HomePage.this, "Failed to Load Channels", Toast.LENGTH_SHORT).show();
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
                    rvAdapter = new HomeScreenRvAdapter(HomePage.this , channels, HomePage.this);
                    recyclerViewHome.setAdapter(rvAdapter);

//                    Toast.makeText(HomePage.this, "Found " + String.valueOf(channelThumbnailModelArrayList.size())  + " Records", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(HomePage.this, "Url = " + channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "onResponse() returned: " +  channelThumbnailModelArrayList.get(0).getThumbnailUrl().get(0).toString() );
                }
                else {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Response Not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChannelThumbnailModel>> call, Throwable t) {
                dialog.progressDialog.dismiss();
                Toast.makeText(HomePage.this, "Failed to Load Channels", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchChannel(String searchString){
        Call<List<Channel>> call = RetrofitClient.getInstance().getApi().getFeaturedChannels(searchString);
        dialog.startDialog(this);
        call.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call, Response<List<Channel>> response) {
                if (response.isSuccessful()) {
                    dialog.progressDialog.dismiss();
                    searchedResultChannels = (ArrayList) response.body();

                }else{
                    dialog.progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Response not Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {

            }
        });
    }




}
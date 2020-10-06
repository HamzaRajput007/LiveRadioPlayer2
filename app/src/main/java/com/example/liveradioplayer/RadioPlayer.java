package com.example.liveradioplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.narayanacharya.waveview.WaveView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import javax.xml.datatype.Duration;

public class RadioPlayer extends AppCompatActivity {

    TextView stationTitle, toolBarTextView;
    //    ImageView favBtn  , searchBtn , searchBtnToSearch;
    SharedPrefHelper sharedPrefHelper;

//    String url = "http://hestia2.cdnstream.com/1301_128"; // your URL here

    //    EditText searchBarEditText;
    Boolean flag;
    ImageView stopContnueWave , channelArtImageView;
    Button backBtn;
    boolean isPlaying = true;
    WaveView sine;

    private boolean playPause;
    private ProgressDialog progressDialog;
    MediaPlayer mediaPlayer;
    private boolean initialStage = true;
    String streamingURL , imageURL;
    boolean isResponded = false;


    // todo there is video who's content can be helpful to play online streaming watch that and implement thant [DONE]
    // todo salman has provided you the api for featured channels implement that api into your application and test it. [DONE]
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_player);

        sharedPrefHelper = new SharedPrefHelper(this);
        backBtn = findViewById(R.id.backBtnPlayerId);
        toolBarTextView = findViewById(R.id.toolbarTextView);
        channelArtImageView = findViewById(R.id.channelArtImageViewID);
        flag = true;
        stopContnueWave = findViewById(R.id.play);
        sine = (WaveView) findViewById(R.id.waveView);
//        sine.setBackgroundColor(getResources().getColor(R.color.playerWaveViewBgColor));
        sine.setBackground(getResources().getDrawable(R.drawable.playerbg));
        sine.setWaveColor(getResources().getColor(R.color.colorPrimary));
        sine.setNumberOfWaves(3);
        sine.setFrequency(3.0f);
        sine.setAmplitude(10);
        sine.setPhaseShift(-0.05f);
        sine.setDensity(15.0f);
        sine.pause();
        /*sine.setPrimaryLineWidth(3.0f);
        sine.setSecondaryLineWidth(1.0f);
        */
        streamingURL = getIntent().getStringExtra("streamLink");
        imageURL = getIntent().getStringExtra("ImageUrl");
        if (imageURL != null){
            Picasso.with(RadioPlayer.this).load(imageURL).placeholder(R.drawable.image_not_found).error(R.drawable.image_not_found).into(channelArtImageView);            
        }else {
            Toast.makeText(this, "Channel Image Not Found", Toast.LENGTH_SHORT).show();
        }
        
        
//  TODO      When you are back you've downloaded a code named "White-Label-shout"  run that code and try if its physible for the player activity in Live Radio Player. [DONE its not Physible]

        sine.setWaveXAxisPositionMultiplier(0.5f);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);
        final Dialog dialog = new Dialog("Preparing to play");
        dialog.startDialog(RadioPlayer.this);
        prepareMediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });


//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        /*try {
            mediaPlayer.setDataSource(url);
        } catch (IllegalArgumentException e1) {
            Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "Message : " + e1.getMessage() + "  || Cause :  " + e1.getCause());
            e1.printStackTrace();
        } catch (SecurityException e1) {
            Log.e("TAG", "Message : " + e1.getMessage() + "  || Cause :  " + e1.getCause());
            Toast.makeText(this, "SecurityException", Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            Toast.makeText(this, "IllegalStateException", Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
            Log.e("TAG", "Message : " + e1.getMessage() + "  || Cause :  " + e1.getCause());
        } catch (IOException e1) {
            Log.e("TAG", "Message : " + e1.getMessage() + "  || Cause :  " + e1.getCause());
            Toast.makeText(this, "SecurityException", Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        }*/

        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressDialog.dismiss();
                isResponded = true;
                int duration = mediaPlayer.getDuration();
                if(duration != 0 || duration != -1){
                    dialog.progressDialog.dismiss();
                    sine.play();
                    stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
                    mediaPlayer.start();
                }
                else {
                    Toast.makeText(RadioPlayer.this, "Channel Unable to Play Yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isResponded) {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(RadioPlayer.this, "Sorry This Channel is Out of Coverage for a little While", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        },10000);

        stopContnueWave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                  /*  progressDialog.setMessage("Loading...");
                    progressDialog.show();*/
                    sine.pause();
                    stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.playbtnradioplayer, null));
                }else {
                    mediaPlayer.start();
                    sine.play();
                    stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
//                    progressDialog.dismiss();
                }
//                prepareMediaPlayer();
            }
        });

       /* mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
            }
        });*/


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 /*               mediaPlayer.stop();
                mediaPlayer.release();*/
                finish();
            }
        });

        stationTitle = findViewById(R.id.radioStationNameId);
        Intent intent = getIntent();
        String channelName = intent.getStringExtra("StationName");
        if (channelName != null) {
            stationTitle.setText(channelName);
        } else {
            stationTitle.setText("Default Channel");
        }

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                switch (i){
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
//                        Toast.makeText(RadioPlayer.this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show();
                        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
                        sine.pause();
                        mediaPlayer.stop();
//                        Toast.makeText(RadioPlayer.this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show();
                        return true;
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
//                        Toast.makeText(RadioPlayer.this, "MEDIA_ERROR                        Toast.makeText(RadioPlayer.this, \"MEDIA_ERROR_SERVER_DIED\", Toast.LENGTH_SHORT).show();\n_SERVER_DIED", Toast.LENGTH_SHORT).show();
                        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
                        sine.pause();
                        mediaPlayer.stop();

                        return true;
                    default:
                        break;
                }
                switch (i1){
                    case MediaPlayer.MEDIA_ERROR_IO:
                        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
                        sine.pause();
                        mediaPlayer.stop();
//                        Toast.makeText(RadioPlayer.this, "MEDIA_ERROR_IO", Toast.LENGTH_SHORT).show();
                        return true;
                    case MediaPlayer.MEDIA_ERROR_MALFORMED:
                        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
                        sine.pause();
                        mediaPlayer.stop();
//                        Toast.makeText(RadioPlayer.this, "MEDIA_ERROR_MALFORMED", Toast.LENGTH_SHORT).show();
                        return true;
                    case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
                        sine.pause();
                        mediaPlayer.stop();
//                        Toast.makeText(RadioPlayer.this, "MEDIA_ERROR_UNSUPPORTED", Toast.LENGTH_SHORT).show();
                        return true;
                    case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
                        sine.pause();
                        mediaPlayer.stop();
//                        Toast.makeText(RadioPlayer.this, "MEDIA_ERROR_TIMED_OUT", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    private void prepareMediaPlayer(){
        try {
                mediaPlayer.setDataSource(streamingURL);
                mediaPlayer.prepareAsync();
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startPlaying() {
        isPlaying = true;
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_bars, null));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stopPlaying() {
        if (mediaPlayer.isPlaying()) {
            isPlaying = false;
            mediaPlayer.stop();
            mediaPlayer.release();
//            initializeMediaPlayer();
        }
        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.playbtnradioplayer, null));
    }

    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(streamingURL);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.i("Buffering", "" + percent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        stopContnueWave.setImageDrawable(getResources().getDrawable(R.drawable.playbtnradioplayer, null));
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepareAsync();
                prepared = true;
            } catch (Exception e) {
//                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }
    }

}
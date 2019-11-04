package com.example.videoandaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener,MediaPlayer.OnCompletionListener {

    private VideoView video;
    private Button startvideo,startmusic,pausemusic;
    private MediaController mediaController;
    private MediaPlayer mediaPlayer;
    private SeekBar seekbar,musicseekbar;
    private AudioManager audioManager;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declare controls
        video=findViewById(R.id.video);
        startvideo=findViewById(R.id.startvideo);
        startmusic=findViewById(R.id.startmusic);
        pausemusic=findViewById(R.id.pausemusic);
        seekbar=findViewById(R.id.seekbar);
        musicseekbar=findViewById(R.id.musicseekbar);


        // Onclick methods
        musicseekbar.setOnSeekBarChangeListener(MainActivity.this);
        startvideo.setOnClickListener(MainActivity.this);
        startmusic.setOnClickListener(MainActivity.this);
        pausemusic.setOnClickListener(MainActivity.this);



        mediaController=new MediaController(MainActivity.this);
        mediaPlayer=MediaPlayer.create(this,R.raw.audion);
        audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer.setOnCompletionListener(this);

        // Max and normal sound
        int maxsound=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int normalsound=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekbar.setMax(maxsound);
        seekbar.setProgress(normalsound);
        seekbarchange();


        musicseekbar.setMax(mediaPlayer.getDuration());
    }


    // Onclick methods

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.startvideo:
                Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
                video.setVideoURI(uri);
                video.setMediaController(mediaController);
                video.start();
                break;

            case R.id.startmusic:
                mediaPlayer.start();
                timer=new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        musicseekbar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                },0,1000);
                break;

            case R.id.pausemusic:
                mediaPlayer.pause();
                timer.cancel();
                break;
        }

        }

        // Sound change seekbar
        public void seekbarchange(){
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    if (fromUser){
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }



    // Music Progress Bar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mediaPlayer.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mediaPlayer.start();
    }



    // MediaPlayer Oncompletion
    @Override
    public void onCompletion(MediaPlayer mp) {
        timer.cancel();
    }
}

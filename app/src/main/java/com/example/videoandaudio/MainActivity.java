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
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView video;
    private Button startvideo,startmusic,pausemusic;
    private MediaController mediaController;
    private MediaPlayer mediaPlayer;
    private SeekBar seekbar;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        video=findViewById(R.id.video);
        startvideo=findViewById(R.id.startvideo);
        startmusic=findViewById(R.id.startmusic);
        pausemusic=findViewById(R.id.pausemusic);
        seekbar=findViewById(R.id.seekbar);

        mediaController=new MediaController(MainActivity.this);
        mediaPlayer=MediaPlayer.create(this,R.raw.audion);
        audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxsound=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int normalsound=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekbar.setMax(maxsound);
        seekbar.setProgress(normalsound);


        startvideo.setOnClickListener(MainActivity.this);
        startmusic.setOnClickListener(MainActivity.this);
        pausemusic.setOnClickListener(MainActivity.this);

        seekbarchange();
    }


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
                break;

            case R.id.pausemusic:
                mediaPlayer.pause();
                break;
        }

        }

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





}

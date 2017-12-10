package leaningmediaplayer.leaningmediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    TextView status_Text, total_Time, current_Time;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    private boolean hadDestroy = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x01:
                    break;
                default:
                    break;
            }
        }
        ;
    };

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
                if (!hadDestroy){
                    mHandler.postDelayed(this, 1000);
                    int currentTime = Math.round(mediaPlayer.getCurrentPosition() / 1000);
                    current_Time.setText(String.format("当前时间为：%02d:%02d",currentTime / 60, currentTime % 60));
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        status_Text = findViewById(R.id.staus_Text);
        total_Time = findViewById(R.id.total_Time);
        current_Time = findViewById(R.id.current_Time);

        mediaPlayer = MediaPlayer.create(this, R.raw.text_music);
        int totalTime = Math.round(mediaPlayer.getDuration() / 1000);
        total_Time.setText(String.format("总时间为：%02d:%02d", totalTime / 60, totalTime % 60));

        findViewById(R.id.mic_Start).setOnClickListener(this);
        findViewById(R.id.mic_Stop).setOnClickListener(this);
        findViewById(R.id.mic_Pause).setOnClickListener(this);
        findViewById(R.id.mic_Restart).setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.mic_Start:
                if (!mediaPlayer.isPlaying()) {
                    status_Text.setText("Start");
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    mHandler.postDelayed(runnable, 1000);
                }
                break;
            case R.id.mic_Pause:
                status_Text.setText("Puase");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.mic_Stop:
                status_Text.setText("Stop");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(this, R.raw.text_music);
                }
                break;

            case R.id.mic_Restart:
                status_Text.setText("Restart");
                mediaPlayer.seekTo(172742);
//                int time = mediaPlayer.getCurrentPosition();
//                status_Text.setText(String.format("%d",time));
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//在创建seekBar.setOnSeekBarChangeListener(this);时系统自动生成函数
        if (b) {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
//在创建seekBar.setOnSeekBarChangeListener(this);时系统自动生成函数
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //在创建seekBar.setOnSeekBarChangeListener(this);时系统自动生成函数
    }
}

package com.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final long MAX_MILLIS_LEFT = 10000;
    SeekBar seekbar;
    CountDownTimer timer;
    TextView timerText;
    Button timerButton;
    boolean isTimerStarted;
    private long millisLeft = MAX_MILLIS_LEFT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.textView);
        timerButton = findViewById(R.id.button);


        this.createSeekBar();
    }

    private void timerStart(){
        timer = new CountDownTimer(millisLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MainActivity.this.millisLeft = millisUntilFinished;
                int secondsUntilFinished = (int) millisUntilFinished / 1000;
                int minutes = (int) secondsUntilFinished / 60;
                int seconds = secondsUntilFinished % 60;
                timerText.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                int test = 100 - (int)((float)MainActivity.this.millisLeft / (float)MAX_MILLIS_LEFT * 100);
                Log.d("progress", String.valueOf(test));
                seekbar.setProgress(test);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void timerStop(){
        this.timer.cancel();
    }

    private void createSeekBar(){
       this.seekbar = findViewById(R.id.seekBar);
       seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });
    }

    public void startTimerAction(View view) {
        if(!isTimerStarted){
            this.timerStart();
            timerButton.setText("STOP");
            isTimerStarted = true;
            seekbar.setEnabled(true);
        }else{
            this.timerStop();
            seekbar.setEnabled(false);
            timerButton.setText("START");
            isTimerStarted = false;
        }
    }
}
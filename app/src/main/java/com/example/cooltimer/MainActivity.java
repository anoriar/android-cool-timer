package com.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int SECONDS_LEFT = 120;
    SeekBar seekbar;
    CountDownTimer timer;
    TextView timerText;
    Button timerButton;
    boolean isTimerStarted;
    private int secondsLeft = SECONDS_LEFT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.textView);
        timerButton = findViewById(R.id.button);


        this.createSeekBar();
    }

    private void timerStart(){
        timer = new CountDownTimer((long) secondsLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MainActivity.this.secondsLeft = (int) millisUntilFinished / 1000;
                int minutes = (int) MainActivity.this.secondsLeft / 60;
                int seconds = MainActivity.this.secondsLeft % 60;
                timerText.setText(String.format("%02d:%02d", minutes, seconds));
                seekbar.setProgress((int)(MainActivity.this.secondsLeft));
            }

            @Override
            public void onFinish() {
                isTimerStarted = false;
                timerButton.setText("START");
            }
        }.start();
    }

    private void timerStop(){
        this.timer.cancel();
    }

    private void createSeekBar(){
       this.seekbar = findViewById(R.id.seekBar);
       seekbar.setMax((int) SECONDS_LEFT);
       seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    secondsLeft = progress;
                    timerStop();
                    timerStart();
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

    public void startTimerAction(View view) {
        if(!isTimerStarted){
            this.timerStart();
            timerButton.setText("STOP");
            isTimerStarted = true;
            seekbar.setEnabled(true);
            if(secondsLeft == 0){
                secondsLeft = SECONDS_LEFT;
                timerStart();
            }
        }else{
            this.timerStop();
            seekbar.setEnabled(false);
            timerButton.setText("START");
            isTimerStarted = false;
        }
    }
}
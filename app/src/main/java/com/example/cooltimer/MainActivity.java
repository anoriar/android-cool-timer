package com.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.textView);
        timerButton = findViewById(R.id.button);


        this.createSeekBar();
    }

    private void timerStart(int seconds) {
        timer = new CountDownTimer((long) seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;
                setTimerText(seconds);
                seekbar.setProgress((seconds));
            }

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                isTimerStarted = false;
                timerButton.setText("START");
                if(sharedPreferences.getBoolean("enable_sound", true)){
                    MediaPlayer bellFinishedSound = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                    bellFinishedSound.start();
                }
            }
        }.start();
    }

    private void timerStop() {
        this.timer.cancel();
    }

    private void createSeekBar() {
        this.seekbar = findViewById(R.id.seekBar);
        seekbar.setMax((int) SECONDS_LEFT);
        seekbar.setProgress((int) SECONDS_LEFT);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    setTimerText(progress);
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

    private void setTimerText(int secondsLeft) {
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft % 60;
        timerText.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public void startTimerAction(View view) {
        if (!isTimerStarted) {
            int seconds = this.seekbar.getProgress();
            timerButton.setText("STOP");
            isTimerStarted = true;
            seekbar.setEnabled(false);
            if (seconds == 0) {
                timerStop();
                timerStart(SECONDS_LEFT);
                seekbar.setProgress(SECONDS_LEFT);
            } else {
                this.timerStart(seconds);
            }
        } else {
            this.timerStop();
            seekbar.setEnabled(true);
            timerButton.setText("START");
            isTimerStarted = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent openSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(openSettingsIntent);
                return true;
            case R.id.action_about:
                Intent openAboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(openAboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
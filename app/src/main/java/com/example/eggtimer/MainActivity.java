package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    Button controllerButton;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes *  60;

        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        timerTextView.setText(MessageFormat.format("{0}:{1}", Integer.toString(minutes), secondString));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        controllerButton = findViewById(R.id.controllerButton);
        timerSeekBar = findViewById(R.id.timerSeekBar);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void controlTimer(View view) {
        if (counterIsActive == false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished/1000);

                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mediaPlayer.start();


                }
            }.start();
        }else {
            resetTimer();

        }

    }

    private void resetTimer() {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        timerSeekBar.setEnabled(true);
        controllerButton.setText("Go!");
        counterIsActive = false;
    }
}

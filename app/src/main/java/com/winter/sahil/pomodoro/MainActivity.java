package com.winter.sahil.pomodoro;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wnafee.vector.MorphButton;

import at.grabner.circleprogress.CircleProgressView;


public class MainActivity extends AppCompatActivity {

    MorphButton morphButton;
    private Timer timer;
    private long startTime = 30000L;
    private long interval = 50L;
    private long millisRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        morphButton = (MorphButton) findViewById(R.id.morphbtn);

        final CircleProgressView circleProgress = (CircleProgressView) findViewById(R.id.circleProgress);

        circleProgress.setMaxValue(startTime);
        circleProgress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                timer.done();
                timer.cancel();
                morphButton.setState(MorphButton.MorphState.START, true);
                int secs = (int) (startTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                circleProgress.setText("" + mins);
                circleProgress.setUnit("" + secs);
                circleProgress.setShowUnit(true);
                circleProgress.setValue(0);
                return true;
            }
        });

        int secs = (int) (startTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        circleProgress.setText("" + mins);
        circleProgress.setUnit("" + secs);


        morphButton.setOnStateChangedListener(new MorphButton.OnStateChangedListener() {
            boolean isrunning = false;

            @Override
            public void onStateChanged(MorphButton.MorphState changedTo, boolean isAnimating) {
                if (!isrunning) {
                    if (millisRemaining == 0) {
                        timer = new Timer(startTime, interval);
                    } else {
                        timer = new Timer(millisRemaining, interval);
                    }
                    timer.start();

                    isrunning = true;
                } else {
                    timer.cancel();
                    isrunning = false;
                }

            }
        });

        timer = new Timer(startTime, interval);
    }


    class Timer extends CountDownTimer {

        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void done() {
            onTick(0);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            millisRemaining = millisUntilFinished;
            CircleProgressView circleProgress = (CircleProgressView) findViewById(R.id.circleProgress);
            circleProgress.setValue(millisUntilFinished);
            int secs = (int) (millisUntilFinished / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            circleProgress.setShowUnit(true);
            circleProgress.setAutoTextSize(false);
            circleProgress.setText(String.format("%d", mins));
            circleProgress.setUnit(String.format("%d", secs));
        }

        @Override
        public void onFinish() {
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            CircleProgressView circleProgress = (CircleProgressView) findViewById(R.id.circleProgress);
            circleProgress.setValue(0);
            circleProgress.setAutoTextSize(false);
            circleProgress.setText("Done");
            circleProgress.setShowUnit(false);
            v.vibrate(500);

        }
    }
}


package com.winter.sahil.pomodoro;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import at.grabner.circleprogress.CircleProgressView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Timer timer;
    private Boolean timer_flag = false;
    private long startTime = 120000L;
    private long interval = 50L;
    private long millisRemaining;
    private Button start, reset;
    private TextView timerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircleProgressView circleProgress = (CircleProgressView) findViewById(R.id.circleProgress);

        circleProgress.setMaxValue(startTime);
        int secs = (int) (startTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        circleProgress.setText("" + mins);
        circleProgress.setUnit("" + secs);


        start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(this);

        reset = (Button) findViewById(R.id.reset_button);

        timerText = (TextView) findViewById(R.id.timer);

        timer = new Timer(startTime, interval);
        timerText.setText("" + startTime);





    }

    @Override
    public void onClick(View v) {
        if (!timer_flag) {
            if (millisRemaining == 0) {
                timer = new Timer(startTime, interval);
            } else {
                timer = new Timer(millisRemaining, interval);
            }
            start.setText("Pause");
            timer.start();
            timer_flag = true;

        } else {
            timer.cancel();
            timer_flag = false;
            start.setText("Resume");
        }
    }

    public void reset(View view) {
        timer.done();
        timer.cancel();
        timer_flag = false;
        start.setText("Start");
        CircleProgressView circleProgressView = (CircleProgressView) findViewById(R.id.circleProgress);
        int secs = (int) (startTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        circleProgressView.setText("" + mins);
        circleProgressView.setUnit("" + secs);
        circleProgressView.setShowUnit(true);
        circleProgressView.setValue(0);

    }

    class Timer extends CountDownTimer {

        long millisInFuture;

        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void done() {
            onTick(0);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            millisRemaining = millisUntilFinished;
            timerText.setText("" + millisUntilFinished);
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
            timerText.setText("Finished");
            CircleProgressView circleProgress = (CircleProgressView) findViewById(R.id.circleProgress);
            circleProgress.setValue(0);
            circleProgress.setAutoTextSize(false);
            circleProgress.setText("Done");
            circleProgress.setShowUnit(false);
            v.vibrate(500);
        }
    }
}


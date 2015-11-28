package com.winter.sahil.pomodoro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import at.grabner.circleprogress.CircleProgressView;


public class MainActivity extends AppCompatActivity {

    PomoTimer pomoTimer;
    //    private Timer timer;
    private Boolean timer_flag = false;
    private long startTime = 120000L;
    private long interval = 50L;
    private long millisRemaining;
    private ImageButton pomodoro, rest, longRest;
    private TextView timerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircleProgressView circleProgress = (CircleProgressView) findViewById(R.id.circleProgress);

        circleProgress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                reset();
                return true;
            }
        });


        pomodoro = (ImageButton) findViewById(R.id.pomodoro);
        rest = (ImageButton) findViewById(R.id.rest);
        longRest = (ImageButton) findViewById(R.id.longRest);

        pomoTimer = new PomoTimer(this, circleProgress, pomodoro, rest, longRest);

//        pomodoro.setOnClickListener(this);

        pomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pomoTimer.pomodoro();
            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pomoTimer.rest();
            }
        });

        longRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pomoTimer.longRest();
            }
        });


        timerText = (TextView) findViewById(R.id.timer);
//        timer = new Timer(startTime, interval);
        timerText.setText("" + startTime);


    }

//    @Override
//    public void onClick(View v) {
//        if (!timer_flag) {
//            if (millisRemaining == 0) {
//                timer = new Timer(startTime, interval);
//            } else {
//                timer = new Timer(millisRemaining, interval);
//            }
//            pomodoro.setImageResource(R.drawable.ic_pause_black_48dp);
//            timer.start();
//            timer_flag = true;
//
//        } else {
//            timer.cancel();
//            timer_flag = false;
//            pomodoro.setImageResource(R.drawable.ic_play_arrow_black_48dp);
//        }
//    }

    private void reset() {
        pomoTimer.done();
        pomoTimer.cancel();
        timer_flag = false;
        pomodoro.setImageResource(R.drawable.ic_play_arrow_black_48dp);
        CircleProgressView circleProgressView = (CircleProgressView) findViewById(R.id.circleProgress);
        int secs = (int) (startTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        circleProgressView.setText("" + mins);
        circleProgressView.setUnit("" + secs);
        circleProgressView.setShowUnit(true);
        circleProgressView.setValue(0);

    }

//    class Timer extends CountDownTimer {
//
//        public Timer(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        public void done() {
//            onTick(0);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            millisRemaining = millisUntilFinished;
//            timerText.setText("" + millisUntilFinished);
//            CircleProgressView circleProgress = (CircleProgressView) findViewById(R.id.circleProgress);
//            circleProgress.setValue(millisUntilFinished);
//            int secs = (int) (millisUntilFinished / 1000);
//            int mins = secs / 60;
//            secs = secs % 60;
//            circleProgress.setShowUnit(true);
//            circleProgress.setAutoTextSize(false);
//            circleProgress.setText(String.format("%d", mins));
//            circleProgress.setUnit(String.format("%d", secs));
//        }
//
//        @Override
//        public void onFinish() {
//            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//            timerText.setText("Finished");
//            CircleProgressView circleProgress = (CircleProgressView) findViewById(R.id.circleProgress);
//            circleProgress.setValue(0);
//            circleProgress.setAutoTextSize(false);
//            circleProgress.setText("Done");
//            circleProgress.setShowUnit(false);
//            v.vibrate(500);
//        }
//    }
}


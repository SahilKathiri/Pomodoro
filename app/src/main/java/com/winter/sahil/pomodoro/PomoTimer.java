package com.winter.sahil.pomodoro;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.widget.ImageButton;

import at.grabner.circleprogress.CircleProgressView;


class PomoTimer {

    Timer timer;
    private CircleProgressView circleProgress;
    private ImageButton pomo, rest, longRest;
    private Context context;
    private boolean timer_flag = false;
    private long startTime, interval = 50L, millisRemaining;

    public PomoTimer(Context context, CircleProgressView circleProgress, ImageButton pomo, ImageButton rest, ImageButton longRest) {
        this.context = context;
        this.circleProgress = circleProgress;
        this.pomo = pomo;
        this.rest = rest;
        this.longRest = longRest;
    }

    public void pomodoro() {
        startTime = 25 * 60 * 1000;

        circleProgress.setMaxValue(startTime);
        circleProgress.setText("" + 25);
        circleProgress.setUnit("" + 0);

        timer = new Timer(startTime, interval);

        if (!timer_flag) {
            if (millisRemaining == 0) {
                timer = new Timer(startTime, interval);
            } else {
                timer = new Timer(millisRemaining, interval);
            }
            pomo.setImageResource(R.drawable.ic_pause_black_48dp);
            timer.start();
            timer_flag = true;

        } else {
            timer.cancel();
            timer_flag = false;
            pomo.setImageResource(R.drawable.ic_play_arrow_black_48dp);
        }
    }

    public void rest() {
        startTime = 5 * 60 * 1000;

        circleProgress.setMaxValue(startTime);
        circleProgress.setText("" + 5);
        circleProgress.setUnit("" + 0);

        timer = new Timer(startTime, interval);

        if (!timer_flag) {
            if (millisRemaining == 0) {
                timer = new Timer(startTime, interval);
            } else {
                timer = new Timer(millisRemaining, interval);
            }
            rest.setImageResource(R.drawable.ic_pause_black_48dp);
            timer.start();
            timer_flag = true;

        } else {
            timer.cancel();
            timer_flag = false;
            rest.setImageResource(R.drawable.ic_play_arrow_black_48dp);
        }
    }

    public void longRest() {
        startTime = 10 * 60 * 1000;

        circleProgress.setMaxValue(startTime);
        circleProgress.setText("" + 10);
        circleProgress.setUnit("" + 0);

        timer = new Timer(startTime, interval);

        if (!timer_flag) {
            if (millisRemaining == 0) {
                timer = new Timer(startTime, interval);
            } else {
                timer = new Timer(millisRemaining, interval);
            }
            longRest.setImageResource(R.drawable.ic_pause_black_48dp);
            timer.start();
            timer_flag = true;

        } else {
            timer.cancel();
            timer_flag = false;
            longRest.setImageResource(R.drawable.ic_play_arrow_black_48dp);
        }
    }

    public void done() {
        timer.done();
    }

    public void cancel() {
        timer.cancel();
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
            int secs = (int) millisUntilFinished / 1000;
            int mins = secs / 60;
            secs = secs % 60;

            circleProgress.setValue(millisUntilFinished);
            circleProgress.setShowUnit(true);
            circleProgress.setText("" + mins);
            circleProgress.setUnit("" + secs);
        }

        @Override
        public void onFinish() {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            circleProgress.setValue(0);
            circleProgress.setShowUnit(false);
            v.vibrate(500);
        }
    }


}

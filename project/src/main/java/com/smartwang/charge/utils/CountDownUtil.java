package com.smartwang.charge.utils;

import android.app.Activity;
import android.os.CountDownTimer;

import com.orhanobut.logger.Logger;

public class CountDownUtil {

    private enum TimerState {
        STOPPED,
        RUNNING
    }

    private OnCountEvent onCountEvent;

    private Long timerLength;
    private CountDownTimer countDownTimer;
    private boolean isFinish =false;

    public CountDownUtil(Long timerLength, OnCountEvent event) {
        this.timerLength = timerLength;
        onCountEvent = event;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timerLength * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
//                timeToGo -= 1;
//                onCountEvent.onTickEvent();
                Logger.d("onTick:" + millisUntilFinished);
                onCountEvent.onTickEvent(millisUntilFinished);
            }

            public void onFinish() {
                if (!isFinish) {
                    isFinish = true;
                    Logger.d("onFinish:");
                    onCountEvent.onTimerFinish();
                }
//                state = TimerState.STOPPED;
//                onCountEvent.onTimerFinish();
            }
        }.start();
    }

//    public void pauseTimer() {
//        if (state == TimerState.RUNNING) {
//            countDownTimer.cancel();
//        }
//    }

    public void finishTimer() {
        if (!isFinish) {
            isFinish = true;
            onCountEvent.onTimerFinish();
        }
    }


    public interface OnCountEvent {
        void onTimerFinish();

        void onTickEvent(long millisUntilFinished);
    }


}

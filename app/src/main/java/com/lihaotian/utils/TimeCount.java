package com.lihaotian.utils;

import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCount extends CountDownTimer{
    private Button btn;
    public TimeCount(long millisInFuture,long countDownInterval,Button btn){
        super(millisInFuture, countDownInterval);
        this.btn = btn; }
    @Override
    public void onFinish() {
        btn.setText("获取验证码");
        btn.setClickable(true); }
    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);
        btn.setText(millisUntilFinished/1000+"秒后\n重新获取"); }
}

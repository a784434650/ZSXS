package com.huida.zsxs.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class SendSmsTimerUtils extends CountDownTimer {
    //private int inFuture;
    //private int downInterval;
    private TextView mTextView;

    public SendSmsTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        //this.inFuture=inFuture;
        //this.downInterval=downInterval;
    }

    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);//设置不可点击
        mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送"); //设置倒计时时间
       //mTextView.setBackgroundResource(downInterval);

        /*SpannableString spannableString = new SpannableString(mTextView.getText().toString());//获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        //设置秒数为红色
        if (millisUntilFinished/1000 > 9) {
            spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        mTextView.setText(spannableString);*/
    }

    @Override
    public void onFinish() {
        mTextView.setText("获取验证码");
        mTextView.setClickable(true);//重新获得点击
        //mTextView.setBackgroundResource(inFuture);
        this.cancel();
    }
}
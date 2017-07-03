package com.huida.zsxs.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.huida.zsxs.R;

/**
 * Created by xiaojiu on 2017/6/22.
 */

public class WelcomActivity extends Activity {
    public static final String IS_OPENMAIN = "is_openmain" ;
    private RelativeLayout rl_welcom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置没有标题的页面
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcom);
        rl_welcom = (RelativeLayout) findViewById(R.id.rl_welcom);
        initAnimation();
    }

    private void enterHome() {
        //显示意图跳转界面（主界面）
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    //初始化动画（由暗到明）
    private void initAnimation() {
        //获取窗体对象
        final Window window = getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.2f, 1f);
        valueAnimator.setDuration(3500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha= (float) animation.getAnimatedValue();
                window.setAttributes(params);
            }
        });
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                enterHome();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}

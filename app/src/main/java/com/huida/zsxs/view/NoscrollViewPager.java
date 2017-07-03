package com.huida.zsxs.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * viewPager没有滑动切换，只能点击下方的radioButton进行切换
 */

public class NoscrollViewPager extends ViewPager {
    public NoscrollViewPager(Context context) {
        super(context);
    }

    public NoscrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}

package com.huida.zsxs.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

public class AnimTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    /*
    * 参数view：viewpager的条目的view对象
    * view1：滑动出去的view  对应的position的值的范围0~-1（当前可见条目  左边）
    * view2：滑动进来的view  对应的position的值的范围1~0 （下一个条目  右边）
    * position：条目的view移动时的坐标
    * */
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(1);//完全不透明
        } else if (position <= 0) { // [-1,0]
            //滑动出去的view（当前可见条目）
            view.setPivotX(pageWidth);
            view.setPivotY(view.getHeight());
        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            //将要滑进来的view
            view.setPivotX(pageWidth);
            view.setPivotY(view.getHeight());
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(1);
            view.setRotation(0);
        }
    }
}
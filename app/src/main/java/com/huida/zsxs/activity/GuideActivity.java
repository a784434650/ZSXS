package com.huida.zsxs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huida.zsxs.R;
import com.huida.zsxs.utils.SpUtil;

import java.util.ArrayList;

import static com.huida.zsxs.activity.WelcomActivity.IS_OPENMAIN;

/**
 * Created by xiaojiu on 2017/6/22.
 */

public class GuideActivity extends Activity {
    private ViewPager vp_guide;
    private ArrayList<ImageView> imageList;
    private ImageButton ib_guide;
    private LinearLayout ll_guide;
    private ImageView iv_red_point;
    private int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean sp = SpUtil.getBoolean(IS_OPENMAIN, GuideActivity.this);
        if(sp){
            Intent intent = new Intent(GuideActivity.this, WelcomActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_guide);
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        ib_guide = (ImageButton) findViewById(R.id.ib_guide);
        ll_guide = (LinearLayout) findViewById(R.id.ll_guide);
        iv_red_point = (ImageView) findViewById(R.id.iv_red_point);
        initData();
        //存储已经跳转的mainactivity的标记
        SpUtil.putBoolean(IS_OPENMAIN, true, GuideActivity.this);
    }

    private void initData() {
        //准备数据
        int[] images = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
        imageList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(images[i]);
            imageList.add(iv);

            //初始化LinearLayout中默认的点
            ImageView iv_point = new ImageView(this);
            iv_point.setBackgroundResource(R.drawable.dot_normal);
            //此处宽高的单位是像素px
            //而布局文件中的宽高单位是dp
            //px=dp*密度比
            //获取手机的密度比
            float density = this.getResources().getDisplayMetrics().density;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (10 * density), (int) (10 * density));
            if (i != 0) {
                //左边距
                params.leftMargin = (int) (15 * density);
            }
            iv_point.setLayoutParams(params);

            //将点添加到LinearLayout里
            ll_guide.addView(iv_point);
        }
        initRedPoint();

        //设置viewpager的动画
        //vp_guide.setPageTransformer(true, new DepthPageTransformer());
        vp_guide.setPageTransformer(true, new AnimTransformer());

        //初始化viewpager数据，通过适配器完成
        vp_guide.setAdapter(new GuideAdapter());
        //设置viewpager的页面变化的监听
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面滚动
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //让红点动起来（位移  不断的给红颜色的点设置左边距）
                //红点移动的距离=两个白点的间距*viewpager的移动比例
                int LeftMargin = (int) (distance * positionOffset + distance * position);
                //获取红点的参数信息
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
                params.leftMargin = LeftMargin;
                iv_red_point.setLayoutParams(params);//重新设置给它，左边距才能起作用
            }

            //当页面被选中时调用
            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    //当前选中的是最后一个条目,button可见
                    ib_guide.setVisibility(View.VISIBLE);
                    //button的点击事件
                    ib_guide.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转到欢迎界面
                            Intent intent = new Intent(GuideActivity.this, WelcomActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                } else {
                    //其他条目，button不可见
                    ib_guide.setVisibility(View.INVISIBLE);
                }

            }

            //页面状态改变时
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //红点的相关初始化
    private void initRedPoint() {
        //measure...layout...draw
        //添加全局layout的监听器
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当全局开始布局的时候回调
            @Override
            public void onGlobalLayout() {
                //获取两个白点的坐标
                distance = ll_guide.getChildAt(1).getLeft() - ll_guide.getChildAt(0).getLeft();
                //不需要实时监测，只需要获取距离
                //移除监听
                iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });//观察者

    }

    class GuideAdapter extends PagerAdapter {
        //条目数量
        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //创建条目方法
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //将条目的view对象添加到容器中
            ImageView iv = imageList.get(position);
            container.addView(iv);
            //将条目的view返回
            return iv;
        }

        //销毁条目方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

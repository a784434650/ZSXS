package com.huida.zsxs.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.huida.zsxs.R;
import com.huida.zsxs.fragment.BaseFragment;
import com.huida.zsxs.fragment.CourseFragment;
import com.huida.zsxs.fragment.HomeFragment;
import com.huida.zsxs.fragment.MyCourseFragment;
import com.huida.zsxs.fragment.PersonalFragment;
import com.huida.zsxs.utils.DensityUtil;
import com.huida.zsxs.utils.SpUtil;
import com.huida.zsxs.view.NoscrollViewPager;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private NoscrollViewPager vp_main;
    private ArrayList<BaseFragment> fragmentList;
    public static String IS_LOGIN = "is_login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        vp_main = (NoscrollViewPager) findViewById(R.id.vp_main);
        RadioButton rb_home = (RadioButton) findViewById(R.id.rb_home);
        RadioButton rb_course = (RadioButton) findViewById(R.id.rb_course);
        RadioButton rb_mycourse = (RadioButton) findViewById(R.id.rb_mycourse);
        RadioButton rb_personal = (RadioButton) findViewById(R.id.rb_personal);

        changeDrawableSize(rb_home);
        changeDrawableSize(rb_course);
        changeDrawableSize(rb_mycourse);
        changeDrawableSize(rb_personal);

        initData();
        initListener();
    }

    private void initListener() {
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vp_main.setCurrentItem(0,false);
                        break;
                    case R.id.rb_course:
                        vp_main.setCurrentItem(1,false);
                        break;
                    case R.id.rb_mycourse:
                        if (SpUtil.getBoolean(IS_LOGIN,MainActivity.this)){
                            vp_main.setCurrentItem(2,false);
                        }else{
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            vp_main.setCurrentItem(0);
                            rg_main.check(R.id.rb_home);
                        }
                        break;
                    case R.id.rb_personal:
                        if (SpUtil.getBoolean(IS_LOGIN,MainActivity.this)){
                            vp_main.setCurrentItem(3,false);
                        }else{
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            vp_main.setCurrentItem(0);
                            rg_main.check(R.id.rb_home);
                        }
                        break;
                }
            }
        });
    }

    private void initData() {

        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CourseFragment());
        fragmentList.add(new MyCourseFragment());
        fragmentList.add(new PersonalFragment());
        vp_main.setAdapter(new VpMyAdapter(getSupportFragmentManager()));
        vp_main.setCurrentItem(0);
        rg_main.check(R.id.rb_home);
    }
    //改变图标大小
    private void changeDrawableSize(RadioButton rb) {
        int dip = DensityUtil.dip2px(this, 34.0f);
        Drawable drawable = rb.getCompoundDrawables()[1];
        drawable.setBounds(0,0,dip,dip);
        rb.setCompoundDrawables(null,drawable,null,null);
    }

    private class VpMyAdapter extends FragmentPagerAdapter {
        public VpMyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
    //返回键，弹出对话框（自定义对话框）
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_back, null);//获取自定义布局
        builder.setView(layout);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        layout.findViewById(R.id.tv_look).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        layout.findViewById(R.id.tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                MainActivity.this.finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtil.putBoolean(IS_LOGIN,false,MainActivity.this);
    }
}

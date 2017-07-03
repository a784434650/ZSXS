package com.huida.zsxs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.huida.zsxs.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AudioActivity extends Activity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_info)
    TextView tvInfo;
    @InjectView(R.id.tv_kclist)
    TextView tvKclist;
    @InjectView(R.id.vp_audio)
    ViewPager vpAudio;
    @InjectView(R.id.activity_audio)
    LinearLayout activityAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.inject(this);
        initData();
        initListener();
    }



    private void initData() {
    }
    private void initListener() {
    }
}

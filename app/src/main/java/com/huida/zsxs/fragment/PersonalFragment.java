package com.huida.zsxs.fragment;

import android.view.View;
import android.widget.TextView;

import com.huida.zsxs.activity.MainActivity;
import com.huida.zsxs.utils.SpUtil;

/**
 * Created by xiaojiu on 2017/6/23.
 */

public class PersonalFragment extends BaseFragment {

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        TextView tv = new TextView(mActivity);
        tv.setText("退出登录");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtil.putBoolean(MainActivity.IS_LOGIN,false,mActivity);
            }
        });
        return tv;
    }
}

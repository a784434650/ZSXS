package com.huida.zsxs.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by xiaojiu on 2017/6/23.
 */

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }
    //初始化监听
    protected abstract void initListener();

    //初始化数据
    protected abstract void initData();

    //初始化view
    public abstract View initView();
    //弹吐司
    public void showToast(String msg){
        Toast.makeText(mActivity,msg,Toast.LENGTH_SHORT).show();
    }
    //打log的方法
    public void LogE(String msg){
        Log.e("jdr",msg);
    }
}

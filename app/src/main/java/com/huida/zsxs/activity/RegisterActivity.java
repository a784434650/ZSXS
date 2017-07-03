package com.huida.zsxs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huida.zsxs.R;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.FormatUtil;
import com.huida.zsxs.utils.MD5Util;
import com.huida.zsxs.utils.SendSmsTimerUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by xiaojiu on 2017/7/1.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends Activity {
    @ViewInject(R.id.bt_register)
    Button bt_register;
    @ViewInject(R.id.bt_register_back)
    Button bt_register_back;
    @ViewInject(R.id.et_register_nickname)
    EditText et_register_nickname;
    @ViewInject(R.id.et_register_shouji)
    EditText et_register_shouji;
    @ViewInject(R.id.et_register_yanzheng)
    EditText et_register_yanzheng;
    @ViewInject(R.id.bt_register_getcode)
    Button bt_register_getcode;
    @ViewInject(R.id.et_register_mima)
    EditText et_register_mima;
    @ViewInject(R.id.cb_register_islook)
    CheckBox cb_register_islook;
    @ViewInject(R.id.et_register_tuijian)
    EditText et_register_tuijian;
    @ViewInject(R.id.tv_register_protocol)
    TextView tv_register_protocol;
    private String getcode = "";
    private SendSmsTimerUtils mCountDownTimerUtils;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        bt_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //初始化
        initListener();
        mCountDownTimerUtils = new SendSmsTimerUtils(bt_register_getcode, 60000, 1000);
    }

    private void initListener() {
        //注册按钮的单击事件
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        //获取验证码的单击事件
        bt_register_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 第二步:控件的点击事件中开始倒计时
                mCountDownTimerUtils.start();
                //发送短信验证码
                sendPwdCodeFromNet();
            }
        });
        //密码可见不可见
        cb_register_islook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    et_register_mima.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                }else{
                    et_register_mima.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                }
                et_register_mima.postInvalidate();//提交设置
                et_register_mima.setSelection(et_register_mima.getText().length());//光标定位到最后
            }
        });
        //中仕学社协议的点击事件
        tv_register_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
                intent.putExtra("title","用户注册协议");
                intent.putExtra("url","file:///android_asset/registerinfo.html");
                startActivity(intent);
            }
        });
    }
    //发送短信验证码
    private void sendPwdCodeFromNet() {
        final String number = et_register_shouji.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!FormatUtil.isPhoneLegal(number)) {
            Toast.makeText(this, "您输入的号码无效", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","SendSms");
        params.addBodyParameter("mobile",number);
        params.addBodyParameter("dotype","reg");
        final int numcode = (int) ((Math.random() * 9 + 1) * 100000);
        params.addBodyParameter("codes",numcode+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.contains("该手机号已被注册")){
                    //提示框
                    showHintDialog();
                    return;
                }
                getcode = number+"-"+numcode;

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void showHintDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.loading_error_dialog);
        window.findViewById(R.id.ib_submit_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText("该手机号已被注册");
        mCountDownTimerUtils.onFinish();
    }

    private void initData() {
        String nickname = et_register_nickname.getText().toString().trim();
        String shouji = et_register_shouji.getText().toString().trim();
        String yanzheng = et_register_yanzheng.getText().toString().trim();
        String tuijian = et_register_tuijian.getText().toString().trim();
        String mima = et_register_mima.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)){
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(shouji)){
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }else if (!TextUtils.isEmpty(shouji)){
            if (!FormatUtil.isPhoneLegal(shouji)){
                Toast.makeText(this, "您输入的号码无效", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!getcode.contains(shouji)){
                Toast.makeText(this, "您输入的号码没有发送验证码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(shouji)){
                Toast.makeText(this, "请输入手机验证码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!getcode.contains(yanzheng)){
                Toast.makeText(this, "手机验证码不对", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TextUtils.isEmpty(mima)&&!FormatUtil.isPwdLegal(mima)){
                Toast.makeText(this, "您输入的密码不符合要求", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","SaveReg");
        params.addBodyParameter("uid",shouji);
        params.addBodyParameter("pwd", MD5Util.getMd5Value(mima));
        params.addBodyParameter("nickname", nickname);
        params.addBodyParameter("TJcode", tuijian);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

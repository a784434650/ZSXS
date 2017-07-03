package com.huida.zsxs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huida.zsxs.R;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.FormatUtil;
import com.huida.zsxs.utils.SendSmsTimerUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by xiaojiu on 2017/6/29.
 */

public class FindPwdActivity extends Activity {
    private EditText et_find_pwd_code;
    private SendSmsTimerUtils mCountDownTimerUtils;
    private Button bt_sendsms_code;
    private EditText et_find_pwd_number;
    private Button bt_find_pwd;
    private String number;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pwd_layout);

        et_find_pwd_code = (EditText) findViewById(R.id.et_find_pwd_code);
        et_find_pwd_number = (EditText) findViewById(R.id.et_find_pwd_number);
        bt_find_pwd = (Button) findViewById(R.id.bt_find_pwd);
        bt_sendsms_code = (Button) findViewById(R.id.bt_sendsms_code);
        findViewById(R.id.bt_find_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 获取验证码
         * 第一步：初始化工具类关联需要实现倒计时功能
         *
         * 第一个参数：控件(需要实现倒计时的控件)
         * 第二个参数：倒计时总时间，以毫秒为单位；
         * 第三个参数：渐变事件，最低1秒，也就是说设置0-1000都是以一秒渐变，设置1000以上改变渐变时间
         * 第四个个参数：点击控件之前的背景
         * 第五个参数：点击控件之后的背景
         */
        mCountDownTimerUtils = new SendSmsTimerUtils(bt_sendsms_code, 60000, 1000);

        bt_sendsms_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = et_find_pwd_number.getText().toString().trim();
                if (TextUtils.isEmpty(number)){
                    Toast.makeText(FindPwdActivity.this, "请输入手机号或邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!FormatUtil.isPhoneLegal(number)){
                    Toast.makeText(FindPwdActivity.this, "请输入合法的手机号码或邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 第二步:控件的点击事件中开始倒计时
                mCountDownTimerUtils.start();
                //发送短信验证码
                sendPwdCodeFromNet();
            }
        });
        bt_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = et_find_pwd_number.getText().toString().trim();
                if (TextUtils.isEmpty(number)){
                    Toast.makeText(FindPwdActivity.this, "请输入手机号或邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!FormatUtil.isPhoneLegal(number)){
                    Toast.makeText(FindPwdActivity.this, "请输入合法的手机号码或邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    public void sendPwdCodeFromNet(){
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","SendSms");
        params.addBodyParameter("mobile",number);
        params.addBodyParameter("dotype","getPwd");
        final int numcode = (int) ((Math.random() * 9 + 1) * 100000);
        params.addBodyParameter("codes",numcode+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               if (result.contains("该账号不存在")){
                   showErrorDialog();
                   return;
               }
                //点击验证跳转到修改密码页面
                bt_find_pwd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = et_find_pwd_code.getText().toString().trim();
                        if (code.equals(numcode+"")) {
                            Toast.makeText(FindPwdActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FindPwdActivity.this, ForgetPwdActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(FindPwdActivity.this, "验证码输入不正确", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void showErrorDialog() {
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
        tv_message.setText("该账号不存在");
        mCountDownTimerUtils.onFinish();

    }
}

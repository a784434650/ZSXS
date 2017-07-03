package com.huida.zsxs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huida.zsxs.R;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.SpUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.huida.zsxs.activity.LoginActivity.ACODE;

/**
 * Created by xiaojiu on 2017/6/29.
 */

public class ForgetPwdActivity extends Activity {

    private EditText et_forget_pwd_new;
    private EditText et_forget_pwd_again;
    private Button bt_pwd_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        et_forget_pwd_new = (EditText) findViewById(R.id.et_forget_pwd_new);
        et_forget_pwd_again = (EditText) findViewById(R.id.et_forget_pwd_again);
        bt_pwd_forget = (Button) findViewById(R.id.bt_pwd_forget);
        Button bt_find_pwd_back = (Button) findViewById(R.id.bt_find_pwd_back);
        bt_find_pwd_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_pwd_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewPwdFromNet();
            }
        });

    }

    private void getNewPwdFromNet() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        String newPwd = et_forget_pwd_new.getText().toString().trim();
        String code = SpUtil.getString(ACODE, ForgetPwdActivity.this);
        params.addBodyParameter("Action","saveNewPwd");
        params.addBodyParameter("acode",code);
        params.addBodyParameter("newPwd",newPwd);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               //api参数错误，无法正常获取

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

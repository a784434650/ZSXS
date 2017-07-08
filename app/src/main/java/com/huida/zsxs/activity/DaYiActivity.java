package com.huida.zsxs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huida.zsxs.R;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.SpUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaojiu on 2017/7/6.
 */

public class DaYiActivity extends Activity {
    @InjectView(R.id.bt_dayi_back)
    Button bt_dayi_back;
    @InjectView(R.id.tv_dayi_submit)
    TextView tv_dayi_submit;
    @InjectView(R.id.et_dayi)
    EditText et_dayi;
    private String id;
    private String acode;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayi);
        ButterKnife.inject(this);
        id = getIntent().getStringExtra("id");

        bt_dayi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_dayi_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayi = et_dayi.getText().toString().trim();
                if (TextUtils.isEmpty(dayi)){
                    Toast.makeText(DaYiActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                //http://api.chinaplat.com/getval_2017?Action=saveMyQuestion&Kc_types=3&kc_id=107907&
                // Acode=33ff6c25a1ea37f191988285d249d523&uid=17190175362&question=%E5%95%A6%E5%95%A6%E5%95%A6
                //保存答疑内容
                RequestParams params = new RequestParams(ConstantUtil.PATH);
                params.addBodyParameter("Action","saveMyQuestion");
                params.addBodyParameter("Kc_types","3");
                params.addBodyParameter("kc_id", id);
                acode = SpUtil.getString(LoginActivity.ACODE, DaYiActivity.this);
                username = SpUtil.getString(LoginActivity.USERNAME, DaYiActivity.this);
                params.addBodyParameter("Acode",acode);
                params.addBodyParameter("uid",username);
                params.addBodyParameter("question",dayi);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DaYiActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        final View layout = inflater.inflate(R.layout.dayi_success_dialog, null);//获取自定义布局
                        builder.setView(layout);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        layout.findViewById(R.id.tv_dayi_myquestions).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到个人中心--我的答疑

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
        });
    }
}

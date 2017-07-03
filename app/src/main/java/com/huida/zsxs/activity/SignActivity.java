package com.huida.zsxs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huida.zsxs.R;
import com.huida.zsxs.bean.GetSignBean;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.DensityUtil;
import com.huida.zsxs.utils.SpUtil;
import com.huida.zsxs.view.ProgressLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaojiu on 2017/7/1.
 */
@ContentView(R.layout.activity_sign)
public class SignActivity extends Activity {
    @ViewInject(R.id.pl_sign)
    ProgressLayout pl_sign;
    @ViewInject(R.id.bt_sign_back)
    Button bt_sign_back;
    @ViewInject(R.id.bt_to_sign)
    Button bt_to_sign;
    @ViewInject(R.id.tv_sign_lianxu)
    TextView tv_sign_lianxu;
    @ViewInject(R.id.tv_sign_jifen)
    TextView tv_sign_jifen;
    @ViewInject(R.id.rl_qiandaojindu)
    RelativeLayout rl_qiandaojindu;
    @ViewInject(R.id.iv_qiandao_one)
    ImageView iv_qiandao_one;
    @ViewInject(R.id.iv_qiandao_two)
    ImageView iv_qiandao_two;
    @ViewInject(R.id.iv_qiandao_three)
    ImageView iv_qiandao_three;
    @ViewInject(R.id.iv_qiandao_four)
    ImageView iv_qiandao_four;
    @ViewInject(R.id.iv_qiandao_five)
    ImageView iv_qiandao_five;
    @ViewInject(R.id.iv_qiandao_six)
    ImageView iv_qiandao_six;
    @ViewInject(R.id.iv_qiandao_seven)
    ImageView iv_qiandao_seven;
    private ImageView[] ivList;
    private boolean is_sign = false;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        pl_sign.showProgress();
        ivList = new ImageView[]{iv_qiandao_one,iv_qiandao_two,iv_qiandao_three,
                iv_qiandao_four,iv_qiandao_five,iv_qiandao_six,iv_qiandao_seven};
        getSign();
        initListener();

    }
    //获取签到状态
    private void getSign() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        String acode = SpUtil.getString(LoginActivity.ACODE, SignActivity.this);
        String uid = SpUtil.getString(LoginActivity.USERNAME, SignActivity.this);
        params.addBodyParameter("Action","getSign");
        params.addBodyParameter("acode",acode);
        params.addBodyParameter("Uid",uid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                pl_sign.showContent();
                Gson gson = new Gson();
                GetSignBean signBean = gson.fromJson(result, GetSignBean.class);
                String jifen = signBean.jifen;
                if (TextUtils.isEmpty(jifen)) {
                    tv_sign_lianxu.setText("请点击右上角签到");
                    tv_sign_jifen.setVisibility(View.GONE);
                    return;
                }
                //获取当前系统的年月日
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
                String label = simpleDateFormat.format(new Date());

                //Toast.makeText(SignActivity.this, "----"+label, Toast.LENGTH_SHORT).show();
                if (signBean.qd_date.contains(label)){
                    is_sign =true;
                    bt_to_sign.setBackgroundResource(R.mipmap.has_sign_back);
                }
                    tv_sign_jifen.setVisibility(View.VISIBLE);
                    String s = "您已经连续签到" + signBean.days + "天";
                    //将部分字体变色
                    SpannableStringBuilder builder = new SpannableStringBuilder(s);
                    int index = (s).indexOf(signBean.days);
                    ForegroundColorSpan span = new ForegroundColorSpan(0xffff2b4b);
                    builder.setSpan(span,index,index+signBean.days.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    tv_sign_lianxu.setText(builder);
                    tv_sign_jifen.setText("积分+"+signBean.jifen+",额外奖励"+signBean.ewai_jifen+"积分");
                //获得签到天数
                int days = Integer.parseInt(signBean.days);
                LinearLayout linearLayout = new LinearLayout(SignActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                //7的倍数回到第一天
                int i = days % 7;
                if (i==0){
                    i=7;
                }
                int width = DensityUtil.dip2px(SignActivity.this, 50 * i);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, rl_qiandaojindu.getLayoutParams().height);
                linearLayout.setLayoutParams(param);
                //将图片填充给原来的布局
                ImageView iv = new ImageView(SignActivity.this);
                iv.setImageResource(R.mipmap.to_sign_progress_quan);
                iv.setLayoutParams(new LinearLayout.LayoutParams(width,rl_qiandaojindu.getLayoutParams().height));
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                TextView tv = new TextView(SignActivity.this);
                tv.setText("  "+days+"天");
                tv.setTextSize(13);
                tv.setTextColor(Color.WHITE);
                linearLayout.addView(iv);
                linearLayout.addView(tv);
                rl_qiandaojindu.addView(linearLayout);
                //将已签到的天数背景换为绿色
                for (int j = 0;j<i;j++){
                    ivList[j].setBackgroundResource(R.mipmap.has_qiandao);
                }
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

    private void initListener() {
        bt_sign_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_to_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_sign){
                    Toast.makeText(SignActivity.this, "您今天已经签到过了", Toast.LENGTH_SHORT).show();
                    return;
                }
                showSignDialog();

            }
        });
    }
    //签到中的对话框
    private void showSignDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.login_dialog_layout);
        TextView tv_content = (TextView) window.findViewById(R.id.tv_login_content);
        tv_content.setText("签到中");
        window.findViewById(R.id.ib_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        //签到
        toSign();
    }

    //签到
    private void toSign() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        String acode = SpUtil.getString(LoginActivity.ACODE, SignActivity.this);
        String uid = SpUtil.getString(LoginActivity.USERNAME, SignActivity.this);
        params.addBodyParameter("Action","toSign");
        params.addBodyParameter("acode",acode);
        params.addBodyParameter("Uid",uid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                alertDialog.dismiss();
                Log.e("jdr",result);
                Toast.makeText(SignActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                getSign();
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

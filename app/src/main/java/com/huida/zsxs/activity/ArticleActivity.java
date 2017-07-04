package com.huida.zsxs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huida.zsxs.R;
import com.huida.zsxs.bean.ArticleDataBean;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.SpUtil;
import com.huida.zsxs.view.ProgressLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaojiu on 2017/7/4.
 */

public class ArticleActivity extends Activity {
    @InjectView(R.id.bt_article_back)
    Button bt_article_back;
    @InjectView(R.id.iv_article_set)
    ImageView iv_article_set;
    @InjectView(R.id.rl_head)
    RelativeLayout rl_head;
    @InjectView(R.id.tv_article_title)
    TextView tv_article_title;
    @InjectView(R.id.tv_article_teacher)
    TextView tv_article_teacher;
    @InjectView(R.id.rl_title_back)
    RelativeLayout rl_title_back;
    @InjectView(R.id.web_article)
    WebView web_article;
    @InjectView(R.id.iv_pl_icon)
    ImageView iv_pl_icon;
    @InjectView(R.id.tv_pl_name)
    TextView tv_pl_name;
    @InjectView(R.id.tv_pl_time)
    TextView tv_pl_time;
    @InjectView(R.id.tv_pl_content)
    TextView tv_pl_content;
    @InjectView(R.id.ll_xianshi_pinlun_one)
    LinearLayout ll_xianshi_pinlun_one;
    @InjectView(R.id.bt_pinglun_more)
    Button bt_pinglun_more;
    @InjectView(R.id.ll_has_pl)
    LinearLayout ll_has_pl;
    @InjectView(R.id.tv_no_pl)
    TextView tv_no_pl;
    @InjectView(R.id.ll_xiangguantuijian)
    LinearLayout ll_xiangguantuijian;
    @InjectView(R.id.ll_main_content_below)
    LinearLayout ll_main_content_below;
    @InjectView(R.id.rl_share)
    RelativeLayout rl_share;
    @InjectView(R.id.ll_dayi)
    LinearLayout ll_dayi;
    @InjectView(R.id.ll_pl)
    LinearLayout llPl;
    @InjectView(R.id.shoucang_iv)
    ImageView shoucang_iv;
    @InjectView(R.id.rl_shoucang)
    RelativeLayout rl_shoucang;
    @InjectView(R.id.rl_main_content)
    RelativeLayout rl_main_content;
    @InjectView(R.id.rl_main)
    RelativeLayout rl_main;
    @InjectView(R.id.pl_article)
    ProgressLayout pl_article;
    @InjectView(R.id.ll_parent_view)
    LinearLayout ll_parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_item);
        ButterKnife.inject(this);
        pl_article.showProgress();
        bt_article_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getDataFromNet();
    }

    private void getDataFromNet() {
        String id = getIntent().getStringExtra("id");
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetCourseInfo");
        params.addBodyParameter("Kc_types","3");
        params.addBodyParameter("kc_id",id);
        if (SpUtil.getBoolean(MainActivity.IS_LOGIN,ArticleActivity.this)){
            String acode = SpUtil.getString(LoginActivity.ACODE, this);
            String username = SpUtil.getString(LoginActivity.USERNAME, this);
            params.addBodyParameter("Acode",acode);
            params.addBodyParameter("uid",username);
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               // Log.e("jdr","----"+result);
                pl_article.showContent();
                Gson gson = new Gson();
                ArticleDataBean articleDataBean = gson.fromJson(result, ArticleDataBean.class);
                if (articleDataBean.shoucang.equals("1")){
                    shoucang_iv.setBackgroundResource(R.mipmap.has_collect);
                }
                bt_article_back.setText(articleDataBean.kc_title);
                tv_article_title.setText(articleDataBean.kc_title);
                tv_article_teacher.setText("作者："+articleDataBean.teacher);
                web_article.setHorizontalScrollBarEnabled(false);//关闭横向滚动
                WebSettings settings = web_article.getSettings();
                settings.setUseWideViewPort(true);//可以任意比例缩放
                settings.setLoadWithOverviewMode(true);//缩放至屏幕大小，适配
                settings.setTextZoom(250);//设置字体比例大小
                settings.setSupportZoom(true);//支持缩放
                web_article.loadDataWithBaseURL(null,articleDataBean.kc_content,"text/html","utf-8",null);//加载代码段

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

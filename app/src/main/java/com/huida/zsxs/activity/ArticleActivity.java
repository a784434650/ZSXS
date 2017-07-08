package com.huida.zsxs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
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
import com.huida.zsxs.bean.GetPLBean;
import com.huida.zsxs.bean.GetSameCourseBean;
import com.huida.zsxs.utils.ConstantUtil;
import com.huida.zsxs.utils.SpUtil;
import com.huida.zsxs.view.ProgressLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaojiu on 2017/7/4.
 */

public class ArticleActivity extends Activity implements View.OnClickListener{
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
    LinearLayout ll_pl;
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
    private String id;
    private List<GetPLBean.PllistBean> pllist;
    private int page_all;
    private int page_now;
    private GetPLBean plBean;
    private ArticleDataBean articleDataBean;

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
        initListener();
        getDataFromNet();

    }
    //获取相关阅读内容
    private void getSameCourseFromNet() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetSameCourse");
        params.addBodyParameter("Kc_types","3");
        params.addBodyParameter("cid",id);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                pl_article.showContent();
                ll_xiangguantuijian.removeAllViews();
                Gson gson = new Gson();
                GetSameCourseBean sameCourseBean = gson.fromJson(result, GetSameCourseBean.class);
                final List<GetSameCourseBean.SameCourseBean> courseList = sameCourseBean.sameCourse;
                for (int i=0;i<courseList.size();i++){
                    View view = View.inflate(ArticleActivity.this, R.layout.recent_article_item, null);
                    TextView tv_recent_title = (TextView) view.findViewById(R.id.tv_recent_title);
                    ImageView article_image = (ImageView) view.findViewById(R.id.article_image);
                    TextView article_item_info_tv = (TextView) view.findViewById(R.id.article_item_info_tv);
                    TextView tv_shangchuan_name = (TextView) view.findViewById(R.id.tv_shangchuan_name);
                    TextView yuedu_tv = (TextView) view.findViewById(R.id.yuedu_tv);
                    //Log.e("jia",courseList.get(i).title);
                    tv_recent_title.setText(courseList.get(i).title);
                    article_item_info_tv.setText(courseList.get(i).info);
                    tv_shangchuan_name.setText(courseList.get(i).teacher);
                    yuedu_tv.setText(courseList.get(i).hot+"");
                    if (TextUtils.isEmpty(courseList.get(i).img)){
                        article_image.setVisibility(View.GONE);
                    }else{
                        x.image().bind(article_image,courseList.get(i).img);
                    }
                    final  int abc = i;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ArticleActivity.this, ArticleActivity.class);
                            intent.putExtra("id",courseList.get(abc).kc_id);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ll_xiangguantuijian.addView(view);
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
        rl_share.setOnClickListener(this);
        ll_dayi.setOnClickListener(this);
        ll_pl.setOnClickListener(this);
        rl_shoucang.setOnClickListener(this);
    }
    //获取文章内容
    private void getDataFromNet() {
        id = getIntent().getStringExtra("id");
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetCourseInfo");
        params.addBodyParameter("Kc_types","3");
        params.addBodyParameter("kc_id", id);
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
                Gson gson = new Gson();
                articleDataBean = gson.fromJson(result, ArticleDataBean.class);
                if (articleDataBean.shoucang.equals("1")){
                    shoucang_iv.setBackgroundResource(R.mipmap.has_collect);
                }
                bt_article_back.setText(articleDataBean.kc_title);
                tv_article_title.setText(articleDataBean.kc_title);
                tv_article_teacher.setText("作者："+ articleDataBean.teacher);
                web_article.setHorizontalScrollBarEnabled(false);//关闭横向滚动
                WebSettings settings = web_article.getSettings();
                settings.setUseWideViewPort(true);//可以任意比例缩放
                settings.setLoadWithOverviewMode(true);//缩放至屏幕大小，适配
                settings.setTextZoom(250);//设置字体比例大小
                settings.setSupportZoom(true);//支持缩放
                web_article.loadDataWithBaseURL(null, articleDataBean.kc_content,"text/html","utf-8",null);//加载代码段

                //获取精彩评论
                getPingLunContent();
                //获取相关阅读内容
                getSameCourseFromNet();
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
    //获取精彩评论内容
    private void getPingLunContent() {
        RequestParams params = new RequestParams(ConstantUtil.PATH);
        params.addBodyParameter("Action","GetCoursePL");
        params.addBodyParameter("cid", id);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                plBean = gson.fromJson(result, GetPLBean.class);
                pllist = plBean.pllist;
                if (pllist.size()==0){
                    tv_no_pl.setVisibility(View.VISIBLE);
                    ll_has_pl.setVisibility(View.GONE);
                    return;
                }
                GetPLBean.PllistBean pllistBean = pllist.get(0);
                tv_pl_name.setText(pllistBean.nickname);
                tv_pl_time.setText(pllistBean.pl_time);
                tv_pl_content.setText(pllistBean.pc_content);
                final ImageOptions build = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).setCircular(true).build();
                x.image().bind(iv_pl_icon,pllistBean.userimg,build);
                bt_pinglun_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转
                        Intent intent = new Intent(ArticleActivity.this, PingLunMoreActivity.class);
                        Bundle bundle = new Bundle();
                       // bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) pllist);
                        //bundle.putSerializable("bean",plBean);
                        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) pllist);
                        intent.putExtra("data",bundle);
                        intent.putExtra("id",id);
                        intent.putExtra("page_now",page_now);
                        intent.putExtra("page_all",page_all);
                        startActivity(intent);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //答疑
            case R.id.ll_dayi:
                if (!SpUtil.getBoolean(MainActivity.IS_LOGIN,ArticleActivity.this)){
                    Intent intent = new Intent(ArticleActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ArticleActivity.this, DaYiActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
                break;
            //评论
            case R.id.ll_pl:
                Intent intent = new Intent(ArticleActivity.this, PingLunMoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) pllist);
                intent.putExtra("data",bundle);
                intent.putExtra("id",id);
                intent.putExtra("page_now",page_now);
                intent.putExtra("page_all",page_all);
                startActivity(intent);
                break;
        }
    }
}

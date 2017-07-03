package com.huida.zsxs.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.huida.zsxs.R;
import com.mingle.widget.LoadingView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 轮播图跳转网页
 * Created by xiaojiu on 2017/6/25.
 */
@ContentView(R.layout.webview_layout)
public class WebViewActivity extends Activity {
    @ViewInject(R.id.lv_loadview)
    LoadingView lv_loadview;
    @ViewInject(R.id.bt_webview_back)
    Button bt_webview_back;
    @ViewInject(R.id.wv_web)
    WebView wv_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        bt_webview_back.setText(title);
        bt_webview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_loadview.setDelay(1000);
        WebSettings settings = wv_web.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_web.loadUrl(url);
        wv_web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                wv_web.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                wv_web.setVisibility(View.VISIBLE);
                lv_loadview.setVisibility(View.GONE);
            }
        });
    }
}

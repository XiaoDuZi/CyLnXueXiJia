package com.cy.cylnxuexijia.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cy.cylnxuexijia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PPTActivity extends AppCompatActivity {

    @BindView(R.id.ppt_webView)
    WebView mPptWebView;

    private String pptUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt);
        ButterKnife.bind(this);
        pptUrl = getIntent().getStringExtra("ppt_url");
        initWebView();
    }

    public static void actionStartPPT(Context context, String pptUrl) {
        Intent intent = new Intent();
        intent.putExtra("ppt_url",pptUrl);
        intent.setClass(context, PPTActivity.class);
        context.startActivity(intent);
    }

    private void initWebView() {
        mPptWebView.requestFocus();
        WebSettings webSettings = mPptWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        mPptWebView.loadUrl(pptUrl);
//        webView.addJavascriptInterface(new JavaScriptObject(PPTActivity.this), "android_ppt");
        mPptWebView.setScrollContainer(false);

        mPptWebView.setWebViewClient(new WebViewClient() {

            //返回值：true 不会显示网页资源，需要等待你的处理，false 就认为系统没有做处理，会显示网页资源
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //当url里面包含webview字段的时候，则跳转到ShowActivity原生页面，否则还是继续显示网页
                //比如：在百度输入框里面输入webview在点击搜索，再点击任何有webview字段的链接，
                //则不继续显示网页，而是跳转到自己定义的原生页面
                if (!url.contains("courseware")) {
                    PPTActivity.this.finish();
                    return true;
                }
                return false;
            }

            //在页面开始加载时候做一些操作，比如展示进度条
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            //在页面加载完成的时候做一些操作,比如隐藏进度条
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPptWebView.destroy();
    }
}

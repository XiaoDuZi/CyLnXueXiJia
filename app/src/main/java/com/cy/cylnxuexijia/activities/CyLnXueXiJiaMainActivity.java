package com.cy.cylnxuexijia.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.comment.CommentInfo;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CyLnXueXiJiaMainActivity extends AppCompatActivity {

    private static final String TAG = "CyLnXueXiJiaMainActivit";

    @BindView(R.id.cy_webview)
    WebView mCyWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cy_ln_xue_xi_jia_main);
        ButterKnife.bind(this);

        mCyWebview.requestFocus();
        WebSettings webSettings = mCyWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        mCyWebview.loadUrl(CommentInfo.WEB_INDEX);
//        mCyWebview.addJavascriptInterface(new JavaScriptObject(MainActivity.this), "android");
        mCyWebview.setScrollContainer(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "onKeyDown: "+mCyWebview.getUrl());
        Log.e(TAG, "onKeyDown: "+keyCode);
        String currentURL = mCyWebview.getUrl();  //获取当前web页面的URL
        String endUrl = null;//最终URL

    if (currentURL.indexOf("backUrl=") != -1) {
            try {
                String subUrl = currentURL.substring(currentURL.indexOf("backUrl=") + "backUrl=".length(), currentURL.length());
                //       解码编码     http://blog.csdn.net/junhuahouse/article/details/23087755
//            String   text1  =   java.net.URLEncoder.encode("中国",   "utf-8"); 编码
                String encodeUrl = java.net.URLDecoder.decode(subUrl, "utf-8");
                endUrl = CommentInfo.WEB_INDEX + encodeUrl;
                Log.e(TAG, "onKeyDown:encodeUrl: " + encodeUrl);
                Log.e(TAG, "onKeyDown:endUrl: " + endUrl);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            endUrl = currentURL;
        }
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (mCyWebview.canGoBack()) {
                mCyWebview.loadUrl(endUrl);
                return true;
            } else {
                mCyWebview.loadUrl(endUrl);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

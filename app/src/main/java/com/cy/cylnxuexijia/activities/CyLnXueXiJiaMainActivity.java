package com.cy.cylnxuexijia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.bean.AuthenticationBean;
import com.cy.cylnxuexijia.bean.ProductIdBean;
import com.cy.cylnxuexijia.bean.UserLauncherBean;
import com.cy.cylnxuexijia.bean.VideoBean;
import com.cy.cylnxuexijia.fragments.SmallVideoFragment;
import com.cy.cylnxuexijia.interfaces.ProductIDAuthenticationService;
import com.cy.cylnxuexijia.tools.CyUtils;
import com.cy.cylnxuexijia.tools.StringUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cy.cylnxuexijia.comment.CommentInfo.PRODUCT_PROGRAM_KEY;
import static com.cy.cylnxuexijia.comment.CommentInfo.SpId;
import static com.cy.cylnxuexijia.comment.CommentInfo.WEB_INDEX;
import static com.cy.cylnxuexijia.comment.GoToOrder.goToOrderActivity;

public class CyLnXueXiJiaMainActivity extends AppCompatActivity {

    private static final String TAG = "CyLnXueXiJiaMainActivit";

    @BindView(R.id.cy_webview)
    WebView mCyWebview;
    @BindView(R.id.fl_cy_small_video)
    FrameLayout mFlCySmallVideo;

    SmallVideoFragment mSmallVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cy_ln_xue_xi_jia_main);
        ButterKnife.bind(this);

        initWebView();

        mSmallVideoFragment = new SmallVideoFragment();

        String insideUrl = getIntent().getStringExtra("url");//内部跳转
        Log.e(TAG, "onNewIntent: "+insideUrl);
        String outUrl = getIntent().getStringExtra("URL");//外部跳转
        if (StringUtils.isStringEmpty(outUrl)){
            if (!StringUtils.isStringEmpty(insideUrl)){
                mCyWebview.loadUrl(insideUrl);
            }
        }else{
            String url = WEB_INDEX + outUrl;
            url = url.replace("#","?");
            mCyWebview.loadUrl(url);
        }

//        initSmallVideoFragment();
    }

    private void initWebView() {
        mCyWebview.requestFocus();
        WebSettings webSettings = mCyWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //不加载缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mCyWebview.setWebViewClient(new WebViewClient());
        mCyWebview.setScrollContainer(false);
        mCyWebview.loadUrl(WEB_INDEX);
//        mCyWebview.addJavascriptInterface(new JSAndroidInteractive(CyLnXueXiJiaMainActivity.this,
//                mFlCySmallVideo), "android");
        mCyWebview.addJavascriptInterface(new JSAndroidInteractive(CyLnXueXiJiaMainActivity.this),
                "android");

        mCyWebview.setScrollContainer(false);
    }

    private void initSmallVideoFragment(String vodID) {
        Log.e(TAG, "initSmallVideoFragment: " + vodID);
        Bundle bundle = new Bundle();
        bundle.putString("VodID", vodID);
        mSmallVideoFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transation = fragmentManager.beginTransaction();
        transation.replace(R.id.fl_cy_small_video, mSmallVideoFragment);
        transation.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "onKeyDown: " + mCyWebview.getUrl());

        String currentURL = mCyWebview.getUrl();  //获取当前web页面的URL
        String endUrl = null;//最终URL

        if (currentURL.indexOf("backUrl=") != -1) {
            try {
                String subUrl = currentURL.substring(currentURL.indexOf("backUrl=") + "backUrl=".length(), currentURL.length());
                String encodeUrl = URLDecoder.decode(subUrl, "utf-8");
                endUrl = WEB_INDEX + encodeUrl;
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            endUrl = WEB_INDEX;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (endUrl.equals(WEB_INDEX)) {
                finish();
                return true;
            }
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

    public static void actionStartCyLnXueXiJiaMainActivity(AppCompatActivity appCompatActivity,
                                                           String url) {
        Intent intent = new Intent(appCompatActivity, CyLnXueXiJiaMainActivity.class);
        intent.putExtra("url", url);
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish();
    }

    @Override
    protected void onDestroy() {
        if (mCyWebview != null) {
            mCyWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mCyWebview.clearHistory();

            ((ViewGroup) mCyWebview.getParent()).removeView(mCyWebview);
            mCyWebview.destroy();
            mCyWebview = null;
        }
        super.onDestroy();
    }

    class JSAndroidInteractive {

        private static final String TAG = "JSAndroidInteractive";

        Context mContxt;

        public JSAndroidInteractive(Context contxt) {
            mContxt = contxt;
        }

        /**
         * 隐藏小窗口播放视频
         */
        @JavascriptInterface
        public void goneSmallVideo() {
            Log.e(TAG, "goneSmallVideo: ");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transation = fragmentManager.beginTransaction();
                    transation.remove(mSmallVideoFragment);
                    transation.commit();
                    mFlCySmallVideo.setVisibility(View.GONE);
                }
            });
        }

        /**
         * 显示小窗口视频播放
         */
        @JavascriptInterface
        public void showSmallVideo(final String vodID) {
            Log.e(TAG, "showSmallVideo: ");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mFlCySmallVideo.setVisibility(View.VISIBLE);
                    mFlCySmallVideo.clearFocus();
                    initSmallVideoFragment(vodID);
                }
            });


        }

        @JavascriptInterface //sdk17版本以上加上注解
        public String getKeyNo() {
            String card_num = UserLauncherBean.getInstance().getUserName();
            Toast.makeText(CyLnXueXiJiaMainActivity.this, "调取了KeyNo:" + card_num,
                    Toast.LENGTH_LONG).show();
            return card_num;
        }

        @JavascriptInterface //sdk17版本以上加上注解
        public void finish() {
            Log.d(TAG, "finish");
            finish();
        }

        @JavascriptInterface //sdk17版本以上加上注解
        public void playVideo(String name) {
            Log.e(TAG, "playVideo: " + name);
            VideoBean authBean = new Gson().fromJson(name, VideoBean.class);
//            PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this,authBean.getVideo_id());
            PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this, authBean);
        }

        @JavascriptInterface //鉴权
        public void authentication(String name) {
            Log.e(TAG, "authentication: " + name);
            ProductIdBean[] authBean = new Gson().fromJson(name, ProductIdBean[].class);
            Log.e(TAG, "authentication: " + authBean[0].product_id);

            long time = System.currentTimeMillis();
            String riddle = CyUtils.MD5(time + PRODUCT_PROGRAM_KEY);
            String temptoken = UserLauncherBean.getUserLauncherBean().getUser32Key();
            final String productId = "240001489";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://59.46.18.48:99/authbilling/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ProductIDAuthenticationService productIDAuthenticationService = retrofit.create(ProductIDAuthenticationService.class);
            Call<AuthenticationBean> authenticationBeanCall = productIDAuthenticationService.getResult(
                    temptoken, productId, SpId, time, riddle);

            authenticationBeanCall.enqueue(new Callback<AuthenticationBean>() {
                @Override
                public void onResponse(Call<AuthenticationBean> call, Response<AuthenticationBean> response) {
                    Log.e(TAG, "onResponse:response " + response.toString());
                    AuthenticationBean authenticationBean = new AuthenticationBean();
                    authenticationBean = response.body();
                    authenticationBean.getResult();
                    String mResult = authenticationBean.getResult();
                    if (mResult.equals("0")) {
                        Toast.makeText(CyLnXueXiJiaMainActivity.this, "用户登陆状态异常", Toast.LENGTH_LONG).show();
                    } else if (mResult.equals("1")) {//1.跳转订购页面
                        goToOrderActivity(CyLnXueXiJiaMainActivity.this, "你好辽宁", "你好吗？", productId);
                    } else if (mResult.equals("2")) {//2.鉴权通过，跳转播放页面
//                        getPlayUrl();
                    }
                }

                @Override
                public void onFailure(Call<AuthenticationBean> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + "访问网络失败！");
                }
            });
        }
    }

}

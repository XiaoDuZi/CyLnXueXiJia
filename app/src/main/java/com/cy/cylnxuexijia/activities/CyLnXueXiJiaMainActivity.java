package com.cy.cylnxuexijia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.bean.AddOrderInfoBean;
import com.cy.cylnxuexijia.bean.AuthenticationBean;
import com.cy.cylnxuexijia.bean.OrderBean;
import com.cy.cylnxuexijia.bean.UserLauncherBean;
import com.cy.cylnxuexijia.bean.VideoBean;
import com.cy.cylnxuexijia.comment.GoToOrder;
import com.cy.cylnxuexijia.fragments.SmallVideoFragment;
import com.cy.cylnxuexijia.interfaces.AddOrderInfoService;
import com.cy.cylnxuexijia.interfaces.ProductIDAuthenticationService;
import com.cy.cylnxuexijia.tools.CyUtils;
import com.cy.cylnxuexijia.tools.StringUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cy.cylnxuexijia.comment.CommentInfo.AUTHENTICATION_URL;
import static com.cy.cylnxuexijia.comment.CommentInfo.ORDER_JUNITOR;
import static com.cy.cylnxuexijia.comment.CommentInfo.PRODUCT_PROGRAM_KEY;
import static com.cy.cylnxuexijia.comment.CommentInfo.SpId;
import static com.cy.cylnxuexijia.comment.CommentInfo.WEB_INDEX;

public class CyLnXueXiJiaMainActivity extends AppCompatActivity {

    private static final String TAG = "CyLnXueXiJiaMainActivit";
    public static CyLnXueXiJiaMainActivity CYXUEXIJIAMAIN_INSTANCE = null;

    @BindView(R.id.cy_webview)
    WebView mCyWebview;
    @BindView(R.id.fl_cy_small_video)
    FrameLayout mFlCySmallVideo;

    SmallVideoFragment mSmallVideoFragment;
    private String mProductIID;
    private VideoBean[] mVideoBeen;
//    private String mIsFree;
//    private String mUseID;
//    private String mContentID;
//    private String mPointID;
//    private String mVideoID;
//    private String mOrderUrl;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                mHandler.sendEmptyMessageDelayed(0, 5000);
                try {
                    Log.e(TAG, "handleMessage: " + mCyWebview.getUrl());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (msg.what == 1) {
                Log.e(TAG, "handleMessage: " + ACTIVITY_SIGN);
                if ("ACTIVITY_MAIN_LIST_ORDER".equals(ACTIVITY_SIGN)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCyWebview.loadUrl("javascript:getListAuthenticationStatus(1)");
                        }
                    });

                } else if ("ACTIVITY_MAIN_ORDER".equals(ACTIVITY_SIGN)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCyWebview.loadUrl("javascript:getAuthenticationStatus(1)");
                        }
                    });
                }
            } else if (msg.what == 2) {
                if ("ACTIVITY_MAIN_LIST_ORDER".equals(ACTIVITY_SIGN)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCyWebview.loadUrl("javascript:getListAuthenticationStatus(2)");
                        }
                    });
                }else if ("ACTIVITY_MAIN_ORDERp".equals(ACTIVITY_SIGN)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCyWebview.loadUrl("javascript:getAuthenticationStatus(2)");
                        }
                    });
                }
            }
            return false;
        }
    });
    private String mBackUrl;
    private String ACTIVITY_SIGN;
    private String mPayBackUrl;
    private VideoBean mPlayVideoBeen;
    private String mProductPrice;
    private String mCard_num;
    private String mSporderNum;
    private String mOrderTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cy_ln_xue_xi_jia_main);
        ButterKnife.bind(this);
        CYXUEXIJIAMAIN_INSTANCE = this;
        initWebView();

        mSmallVideoFragment = new SmallVideoFragment();

        String insideUrl = getIntent().getStringExtra("url");//内部跳转
        ACTIVITY_SIGN = getIntent().getStringExtra("Activity");
        mPlayVideoBeen = (VideoBean) getIntent().getSerializableExtra("videoBean");
        if (ACTIVITY_SIGN == null) {
            ACTIVITY_SIGN = "MainActivity";
        }
        Log.e(TAG, "onNewIntent: " + insideUrl);
        String outUrl = getIntent().getStringExtra("URL");//外部跳转
        if (StringUtils.isStringEmpty(outUrl)) {
            if (!StringUtils.isStringEmpty(insideUrl)) {
                mCyWebview.loadUrl(insideUrl);
            }
        } else {
            String url = WEB_INDEX + outUrl;
            url = url.replace("#", "?");
            mCyWebview.loadUrl(url);
        }
    }

    /**
     * @param requestCode 0
     * @param resultCode  1：订购成功；2：订购失败
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "1：订购成功；2：订购失败；----------" + resultCode);
        switch (requestCode) {
            case 0:
                if (resultCode == 1) {//返回
                    if (ACTIVITY_SIGN.equals("ACTIVITY_PLAY_ORDER")) {
                        mCyWebview.loadUrl(mBackUrl);
                        PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this, mPlayVideoBeen);
                    } else if ("ACTIVITY_MAIN_LIST_ORDER".equals(ACTIVITY_SIGN) || "".equals(ACTIVITY_SIGN)) {
                        mCyWebview.loadUrl(mBackUrl);
                        PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this, mVideoBeen[0]);
                    } else {
                        mCyWebview.loadUrl(mBackUrl);
                    }
                    addLocalOrderResult(0);
                } else if (resultCode == 2) {
                    addLocalOrderResult(1);
                }

                break;
            default:
                break;
        }
    }

    private void addLocalOrderResult(int i) {
        Retrofit retrofit = getRetrofit(WEB_INDEX);
        AddOrderInfoService addOrderInfoService = retrofit.create(AddOrderInfoService.class);
        Call<AddOrderInfoBean> stringCall = addOrderInfoService.addOrderInfo(mCard_num, mSporderNum, "null",
                mProductIID, mProductPrice, mOrderTime, 0, i);
        stringCall.enqueue(new Callback<AddOrderInfoBean>() {
            @Override
            public void onResponse(Call<AddOrderInfoBean> call, Response<AddOrderInfoBean> response) {
                AddOrderInfoBean addOrderInfoBean = response.body();
                Log.e(TAG, "onResponse: " + response);
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<AddOrderInfoBean> call, Throwable t) {
                Log.e(TAG, "onResponse:订购添加失败 ");
            }
        });
    }

    @NonNull
    private Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(0);
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

        String currentURL = mCyWebview.getUrl();  //获取当前web页面的URL
        String endUrl = null;//最终URL

        if (currentURL.indexOf("backUrl=") != -1) {
            try {
                String subUrl = currentURL.substring(currentURL.indexOf("backUrl=") + "backUrl=".length(), currentURL.length());
                if (subUrl.indexOf("index.xml") != -1) {
                    subUrl = subUrl + "&backUrl=index.html";
                }
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
    }

    public static void actionStartCyLnXueXiJiaMainActivity(AppCompatActivity appCompatActivity,
                                                           String ACTIVITY_SIGN, String url, VideoBean videoBean) {
        Intent intent = new Intent(appCompatActivity, CyLnXueXiJiaMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("Activity", ACTIVITY_SIGN);
        bundle.putSerializable("videoBean", videoBean);
        intent.putExtras(bundle);
        appCompatActivity.startActivity(intent);
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
        private String mResult;

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
                    try {
                        transation.commit();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
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
            mCard_num = UserLauncherBean.getInstance().getUserName();
            return mCard_num;
        }

        @JavascriptInterface //sdk17版本以上加上注解
        public void finish() {
            Log.d(TAG, "finish");
            finish();
        }

        @JavascriptInterface //sdk17版本以上加上注解
        public void playVideo(String name) {
            Log.e(TAG, "playVideo: " + name);
            mVideoBeen = new Gson().fromJson(name, VideoBean[].class);
            PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this, mVideoBeen[0]);
        }

        @JavascriptInterface
        public void getLiaoNingPay(String order) {
            Log.e(TAG, "getLiaoNingPay: " + order);
            final OrderBean[] orderBean = new Gson().fromJson(order, OrderBean[].class);
            mProductPrice = orderBean[0].getShow_price();
            mProductIID = orderBean[0].getProduct_id();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    orderEndUrl();
                    mSporderNum = getSporderNum(orderBean[0]);
                    GoToOrder.goToOrderActivity(CyLnXueXiJiaMainActivity.this, orderBean[0].getProduct_name(),
                            orderBean[0].getBuy_tips(), "240001488", mSporderNum);
                }
            });


        }

        @NonNull
        private String getSporderNum(OrderBean orderBean) {
            SimpleDateFormat mYyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddhhmmss");
            mOrderTime = mYyyyMMddhhmmss.format(new Date());
            String sporderNum = mCard_num + mOrderTime + orderBean.getProduct_id();
            Log.d(TAG, "订购号：" + sporderNum);
            return sporderNum;
        }

        /**
         * 区分从哪里的订购，在订购结束后跳转到对应的页面
         */
        private void orderEndUrl() {
            mPayBackUrl = mCyWebview.getUrl();
            Log.e(TAG, "orderEndUrl: " + mPayBackUrl);
            if (mPayBackUrl.indexOf("backUrl=") != -1) {
                try {
                    Log.e(TAG, "orderEndUrl: ------------------");
                    String subUrl = mPayBackUrl.substring(mPayBackUrl.indexOf("backUrl=") + "backUrl=".length(), mPayBackUrl.length());
                    String encodeUrl = URLDecoder.decode(subUrl, "utf-8");
                    if (ACTIVITY_SIGN.equals("PlayActivity")) {
                        ACTIVITY_SIGN = "ACTIVITY_PLAY_ORDER"; //播放视频时，点击下一节，订购
                    } else if (ACTIVITY_SIGN.equals("MainActivity") || ACTIVITY_SIGN.equals("ACTIVITY_MAIN_ORDER")
                            || ACTIVITY_SIGN.equals("ACTIVITY_MAIN_LIST_ORDER") || ACTIVITY_SIGN.equals("ACTIVITY_PLAY_ORDER")) {
                        if (encodeUrl.length() < ORDER_JUNITOR.length()) {
                            ACTIVITY_SIGN = "ACTIVITY_MAIN_ORDER"; //
                        } else {
                            ACTIVITY_SIGN = "ACTIVITY_MAIN_LIST_ORDER"; //视频列表处点击订购
                        }
                    }
                    mBackUrl = WEB_INDEX + encodeUrl;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }


        @JavascriptInterface //鉴权
        public String authentication(String name) {
            Log.e(TAG, "authentication: " + name);
            getLiaoNingAuthentication(name);
//            return mResult;
            return "1";
        }

        private void getLiaoNingAuthentication(String name) {

            mVideoBeen = new Gson().fromJson(name, VideoBean[].class);

            Log.e(TAG, "getLiaoNingAuthentication: " + mVideoBeen[0]);

            if (null==mVideoBeen[0].getVideo_url()) {
                ACTIVITY_SIGN = "ACTIVITY_MAIN_ORDER";
            } else {
                ACTIVITY_SIGN = "ACTIVITY_MAIN_LIST_ORDER";
            }

            long time = System.currentTimeMillis();
            String riddle = CyUtils.MD5(time + PRODUCT_PROGRAM_KEY);
            String temptoken = UserLauncherBean.getUserLauncherBean().getUser32Key();
            final String productId = "240001488";
            mProductIID = mVideoBeen[0].getProduct_id();

            Retrofit retrofit = getRetrofit(AUTHENTICATION_URL);

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
                    mResult = authenticationBean.getResult();
                    if ("1".equals(mResult)) {
                        mHandler.sendEmptyMessage(1);
                    } else if ("2".equals(mResult)) {
                        mHandler.sendEmptyMessage(2);
                    } else {
                        mHandler.sendEmptyMessage(1);
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

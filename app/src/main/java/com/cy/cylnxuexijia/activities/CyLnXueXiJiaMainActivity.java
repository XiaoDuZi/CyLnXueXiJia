package com.cy.cylnxuexijia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.cy.cylnxuexijia.bean.UserLauncherBean;
import com.cy.cylnxuexijia.bean.VideoBean;
import com.cy.cylnxuexijia.comment.GoToOrder;
import com.cy.cylnxuexijia.fragments.SmallVideoFragment;
import com.cy.cylnxuexijia.interfaces.ProductIDAuthenticationService;
import com.cy.cylnxuexijia.tools.AIDLGetUserInfo;
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
            }
            return false;
        }
    });
    private String mBackUrl;
    private String ACTIVITY_SIGN;
    private String mPayBackUrl;
    private VideoBean mPlayVideoBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cy_ln_xue_xi_jia_main);
        ButterKnife.bind(this);
//        new AIDLGetUserInfo(CyLnXueXiJiaMainActivity.this); //AIDL获取用户信息
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

//        initSmallVideoFragment();
    }

    /**
     * @param requestCode 0
     * @param resultCode  1：订购成功；2：订购失败
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "1：订购成功；2：订购失败；----------"+resultCode);
        switch (requestCode) {
            case 0:
                if (resultCode == 1) {//返回
                    if (ACTIVITY_SIGN.equals("ACTIVITY_PLAY_ORDER")) {
                        mCyWebview.loadUrl(mBackUrl);
                        PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this,mPlayVideoBeen);
                    } else if (ACTIVITY_SIGN.equals("ACTIVITY_MAIN_LIST_ORDER")) {
                        mCyWebview.loadUrl(mBackUrl);
                        PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this, mVideoBeen[0]);
                    } else {
                        mCyWebview.loadUrl(mBackUrl);

                    }
                } else if (resultCode == 2) {

                }

                break;
            default:
                break;
        }
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

        String currentURL = mCyWebview.getUrl();  //获取当前web页面的URL
        String endUrl = null;//最终URL

        if (currentURL.indexOf("backUrl=") != -1) {
            try {
                String subUrl = currentURL.substring(currentURL.indexOf("backUrl=") + "backUrl=".length(), currentURL.length());
                if (subUrl.indexOf("index.xml")!=-1){
                    subUrl=subUrl+"&backUrl=index.html";
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
//        intent.putExtra("url", url);
//        intent.putExtra("Activity", ACTIVITY_SIGN);
//        intent.putExtra("videoBean",videoBean);
//        appCompatActivity.startActivity(intent);

    }

//    public static void actionStartCyLnXueXiJiaMainActivity(AppCompatActivity appCompatActivity,
//                                                    String pruductID, String isFree){
//        Intent intent = new Intent(appCompatActivity, CyLnXueXiJiaMainActivity.class);
//        appCompatActivity.startActivity(intent);
//        appCompatActivity.finish();
//    }

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

//    @Override
//    public void getDataFrom_DialogFragment(String productID, String isFree) {
//        mCyWebview.loadUrl(mOrderUrl);
//    }

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
            mVideoBeen = new Gson().fromJson(name, VideoBean[].class);
            PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this, mVideoBeen[0]);
        }

        @JavascriptInterface
        public void getLiaoNingPay(String order) {
            Log.e(TAG, "getLiaoNingPay: " + ACTIVITY_SIGN);
//            Log.e(TAG, "getLiaoNingPay: "+order);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPayBackUrl = mCyWebview.getUrl();
                    if (mPayBackUrl.indexOf("backUrl=") != -1) {
                        try {
                            String subUrl = mPayBackUrl.substring(mPayBackUrl.indexOf("backUrl=") + "backUrl=".length(), mPayBackUrl.length());
                            String encodeUrl = URLDecoder.decode(subUrl, "utf-8");
                            if (ACTIVITY_SIGN.equals("PlayActivity")) {
                                ACTIVITY_SIGN = "ACTIVITY_PLAY_ORDER";
                            } else if (ACTIVITY_SIGN.equals("MainActivity") || ACTIVITY_SIGN.equals("ACTIVITY_MAIN_ORDER")
                                    || ACTIVITY_SIGN.equals("ACTIVITY_MAIN_LIST_ORDER") || ACTIVITY_SIGN.equals("ACTIVITY_PLAY_ORDER")) {
                                if (encodeUrl.length() < ORDER_JUNITOR.length()) {
                                    ACTIVITY_SIGN = "ACTIVITY_MAIN_ORDER";
                                } else {
                                    ACTIVITY_SIGN = "ACTIVITY_MAIN_LIST_ORDER";
                                }
                            }
                            Log.e(TAG, "getLiaoNingPayrun: " + ACTIVITY_SIGN);
                            mBackUrl = WEB_INDEX + encodeUrl;
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
//                    testOrderPlay();
                    GoToOrder.goToOrderActivity(CyLnXueXiJiaMainActivity.this, "学习佳", "学习佳帮助孩子们好好学习"
                            , "240001488");
                }
            });


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

            long time = System.currentTimeMillis();
            String riddle = CyUtils.MD5(time + PRODUCT_PROGRAM_KEY);
            String temptoken = UserLauncherBean.getUserLauncherBean().getUser32Key();
            final String productId = "240001488";
            mProductIID = mVideoBeen[0].getVideo_url();
//            mIsFree = mVideoBeen[0].getIs_free();
//            mUseID = mVideoBeen[0].getUser_id();
//            mContentID = mVideoBeen[0].getContent_id();
//            mPointID = mVideoBeen[0].getPoint_id();
//            mVideoID = mVideoBeen[0].getVideo_id();

//            Log.e(TAG, "authentication: "+ mUseID +":"+ mContentID);

//            initDatas(mUseID,mProductIID, mContentID, mPointID);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AUTHENTICATION_URL)
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
                    mResult = authenticationBean.getResult();
//                    if (mResult.equals("0")) {
//                        Toast.makeText(CyLnXueXiJiaMainActivity.this, "用户登陆状态异常", Toast.LENGTH_LONG).show();
//                    } else if (mResult.equals("1")) {//1.跳转订购页面
//                        if (mIsFree.equals("")&&mPointID.equals("")){
//                            mCyWebview.loadUrl(mOrderUrl);
//                        }else {
//                            DialogFragment dialogFragment = OrderDialogFragment.newInstance(mProductIID, mIsFree);
//                            dialogFragment.show(getFragmentManager(), "orderDialog");
//                        }
//                    } else if (mResult.equals("2")) {//2.鉴权通过，跳转播放页面
//                        if (mIsFree.equals("")&&mPointID.equals("")){
//                            Log.e(TAG, "onResponse: "+mOrderUrl);
//                            mCyWebview.loadUrl(mOrderUrl);
//                        }else {
//                            PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this,mVideoBeen[0]);
//                        }
//                    }
                }

                @Override
                public void onFailure(Call<AuthenticationBean> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + "访问网络失败！");
                }
            });
        }
    }

    private void testOrderPlay() {
        if (ACTIVITY_SIGN.equals("ACTIVITY_PLAY_ORDER")) {
            mCyWebview.loadUrl(mBackUrl);
            Log.e(TAG, "testOrderPlay: "+mPlayVideoBeen.toString());
            PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this,mPlayVideoBeen);
        } else if (ACTIVITY_SIGN.equals("ACTIVITY_MAIN_LIST_ORDER")) {
            mCyWebview.loadUrl(mBackUrl);
            PlayActivity.actionStart(CyLnXueXiJiaMainActivity.this, mVideoBeen[0]);
        } else {
            mCyWebview.loadUrl(mBackUrl);

        }
    }

//    private void initDatas(String useID, String productIID, String contentID, String pointID) {
//
//        Log.e(TAG, "initDatas:========== "+useID+":"+useID+":"+productIID+":"+contentID);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(WEB_INDEX)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PlayDataService playDataService = retrofit.create(PlayDataService.class);
//        Call<PlayDataBean> playDataCall = playDataService.getPlayData(useID,productIID,contentID);
//        playDataCall.enqueue(new Callback<PlayDataBean>() {
//            @Override
//            public void onResponse(Call<PlayDataBean> call, Response<PlayDataBean> response) {
//                Log.e(TAG, "onResponse: "+response.toString());
//                PlayDataBean mPlayDataBean = response.body();
//                if (mPlayDataBean.getData()==null)
//                    return;
//                String gradeName=mPlayDataBean.getData().getGrade_name();
//                String productType=mPlayDataBean.getData().getProduct_type();
//                getPramasUrl(productType,gradeName);
////                mPlayDataBean.getData().getGrade_name();
//                Log.e(TAG, "onResponse: "+mPlayDataBean.getData().getGrade_name());
////                List<TermsBean> listTerms = new ArrayList<TermsBean>();
////                listTerms = mPlayDataBean.getData().getTerms();
////                List<VideosBean> mVideosBeanList = new ArrayList<VideosBean>();
////                List<ParamsBean> mParamsBeanList = new ArrayList<ParamsBean>();
////                for (TermsBean term : listTerms) {
////                    for (PointsBean point : term.getPoints()) {
////                        if (point.getPoint_id().equals(id)) {
////                            Log.e(TAG, "onResponse:------------------------------------------------- ");
////                            mVideosBeanList = point.getVideos();
////                            mParamsBeanList = ConverUtil.jsonToBeanList(point.getParams(), ParamsBean.class);
//////                            initGridViewData();
////                            for (VideosBean videosBean : mVideosBeanList) {
////                                if (videosBean.getVideo_id().equals(productId)) {
////                                    mVideosIndex = mVideosBeanList.indexOf(videosBean);
////                                    mVideoName = mVideosBeanList.get(mVideosIndex).getVideo_name();
////                                }
////                            }
////                        }
////                    }
////                }
//            }
//
//            @Override
//            public void onFailure(Call<PlayDataBean> call, Throwable t) {
//                Log.e(TAG, "onFailure: ");
//            }
//        });
//    }

//    private void getPramasUrl(String productType, String gradeName)  {
//        String buyUrl = StringUtils.format(BUY_URL1,mProductIID,mUseID);
//        String   encodeUrl  = null;
//        if (productType.equals("1")) {
//            if (gradeName.contains("年级")) {//小学
//                mBackUrl =StringUtils.format(ORDER_PRIMARY,mProductIID,mContentID,mPointID);
//            } else if (gradeName.contains("初")) {//初中
//                mBackUrl =StringUtils.format(ORDER_JUNITOR,mProductIID,mContentID,mPointID);
//            } else if (gradeName.contains("高")) {//高中
//                mBackUrl =StringUtils.format(ORDER_SENIOR,mProductIID,mContentID,mPointID);
//            }else if (gradeName.contains("教")){
////                mOrderUrl=StringUtils.format()
//            }
//        }
//        try {
//            encodeUrl = java.net.URLEncoder.encode(mBackUrl,   "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        mOrderUrl=buyUrl+encodeUrl;
//    }

}

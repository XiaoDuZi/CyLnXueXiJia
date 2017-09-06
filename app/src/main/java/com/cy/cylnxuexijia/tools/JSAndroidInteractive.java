package com.cy.cylnxuexijia.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cy.cylnxuexijia.activities.PlayActivity;
import com.cy.cylnxuexijia.bean.UserLauncherBean;
import com.cy.cylnxuexijia.views.CyVideoView;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class JSAndroidInteractive {

    private static final String TAG = "JSAndroidInteractive";

    Context mContxt;
    Activity mActivity;
    private CyVideoView mCySmallVideoView;
    private FrameLayout mFrameLayout;

    public JSAndroidInteractive(Context contxt, CyVideoView cySmallVideo, FrameLayout flCySmallVideo) {
        mContxt = contxt;
        mActivity= (Activity) mContxt;
        mCySmallVideoView=cySmallVideo;
        mFrameLayout=flCySmallVideo;
    }


    /**
     * 隐藏小窗口播放视频
     */
    @JavascriptInterface
    public void goneSmallVideo() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFrameLayout.setVisibility(View.GONE);
                mCySmallVideoView.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 显示小窗口视频播放
     */
    @JavascriptInterface
    public void showSmallVideo(String vodID) {
        Log.e(TAG, "showSmallVideo: ");
//        mPlatform = AppCommonInfo.Platform;
//        getVodId(vodID);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFrameLayout.setVisibility(View.VISIBLE);
                mCySmallVideoView.setVisibility(View.VISIBLE);
//                mLnVideoView.setFocusable(false);
//                mLnVideoView.clearFocus();
//                if (mPlayVodID == null) {
//                    mPlayVodID = mVodid.get(0);
//                    getPlayUrl();
//                } else {
//                    mLnVideoView.start();
//                }
            }
        });

    }

    @JavascriptInterface //sdk17版本以上加上注解
    public String getKeyNo() {
        String card_num = UserLauncherBean.getInstance().getUserName();
        Log.d(TAG, card_num);
        return "获取到了卡号";
    }

//    @JavascriptInterface //sdk17版本以上加上注解
//    public void finish() {
//        Log.d(TAG, "finish");
//        MainActivity.this.finish();
//    }

    @JavascriptInterface //sdk17版本以上加上注解
    public void playVideo(String name) {
        Log.e(TAG, "playVideo: "+name);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(mContxt, "调用fun2:" + name, Toast.LENGTH_SHORT).show();
//                PlayDataBean playDataBean = ConverUtil.jsonToBean(name, PlayDataBean.class);
                Intent intent = new Intent();
                intent.setClass(mActivity,PlayActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("playData", playDataBean);
//                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            }
        });
    }

    @JavascriptInterface //鉴权
    public int authentication(String name){
        Log.e(TAG, "authentication: "+name);
        return 0;
    }

    @JavascriptInterface
    public void getOrderActivity(String name){

    }

}

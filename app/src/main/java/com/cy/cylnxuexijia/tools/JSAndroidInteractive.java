package com.cy.cylnxuexijia.tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.cy.cylnxuexijia.bean.UserLauncherBean;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class JSAndroidInteractive {

    private static final String TAG = "JSAndroidInteractive";

    Context mContxt;
    Activity mActivity;

    public JSAndroidInteractive(Context contxt) {
        mContxt = contxt;
        mActivity= (Activity) mContxt;
    }


    @JavascriptInterface //sdk17版本以上加上注解
    public String getKeyNo() {
        String card_num = UserLauncherBean.getInstance().getUserName();
        Log.d(TAG, card_num);
        return card_num;
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
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, PlayerActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("playData", playDataBean);
//                intent.putExtras(bundle);
//                startActivity(intent);
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

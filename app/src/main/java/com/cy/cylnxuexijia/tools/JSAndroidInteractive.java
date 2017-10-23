package com.cy.cylnxuexijia.tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.activities.PlayActivity;
import com.cy.cylnxuexijia.bean.AuthenticationBean;
import com.cy.cylnxuexijia.bean.ProductIdBean;
import com.cy.cylnxuexijia.bean.UserLauncherBean;
import com.cy.cylnxuexijia.fragments.SmallVideoFragment;
import com.cy.cylnxuexijia.interfaces.ProgramIDAuthenticationService;
import com.cy.cylnxuexijia.views.CyVideoView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cy.cylnxuexijia.comment.CommentInfo.AUTHENTICATION_URL;
import static com.cy.cylnxuexijia.comment.CommentInfo.PRODUCT_PROGRAM_KEY;
import static com.cy.cylnxuexijia.comment.CommentInfo.SpId;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class JSAndroidInteractive {

    private static final String TAG = "JSAndroidInteractive";

    Context mContxt;
    Activity mActivity;
    private CyVideoView mCySmallVideoView;
    private FrameLayout mFrameLayout;
    SmallVideoFragment mSmallVideoFragment;
    public static String mVodId;


    public JSAndroidInteractive(Context contxt) {
        mContxt = contxt;
    }

    public JSAndroidInteractive(Context contxt,FrameLayout flCySmallVideo) {
        mContxt = contxt;
        mActivity= (Activity) mContxt;
        mFrameLayout=flCySmallVideo;
    }

    /**
     * 隐藏小窗口播放视频
     */
    @JavascriptInterface
    public void goneSmallVideo() {
        Log.e(TAG, "goneSmallVideo: ");
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 显示小窗口视频播放
     */
    @JavascriptInterface
    public void showSmallVideo(String vodID) {

        mVodId = vodID;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFrameLayout.setVisibility(View.VISIBLE);
                mFrameLayout.clearFocus();
            }
        });

    }

    @JavascriptInterface //sdk17版本以上加上注解
    public String getKeyNo() {
        String card_num = UserLauncherBean.getInstance().getUserName();
        Toast.makeText(mActivity,"调取了KeyNo:"+card_num,Toast.LENGTH_LONG).show();
        return card_num;
    }

    @JavascriptInterface //sdk17版本以上加上注解
    public void finish() {
        Log.d(TAG, "finish");
        mActivity.finish();
    }

    @JavascriptInterface //sdk17版本以上加上注解
    public void playVideo(String name) {
        Toast.makeText(mActivity,"调取了PlayVideo",Toast.LENGTH_LONG).show();
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
    public void authentication(String name){
        Log.e(TAG, "authentication: "+name);
        Toast.makeText(mActivity,"调取了Authentication",Toast.LENGTH_LONG).show();
        ProductIdBean[] authBean=new Gson().fromJson(name, ProductIdBean[].class);
        Log.e(TAG, "authentication: "+authBean[0].getProduct_id());
        long time = System.currentTimeMillis();
        String riddle = CyUtils.MD5(time +PRODUCT_PROGRAM_KEY);
        String temptoken = UserLauncherBean.getUserLauncherBean().getUser32Key();

//        programId = "240001310";
//        programId = "240001308";
//        programId = "240001312";
//        240001311 240001312
//        programId = "PRO0000000296";
//        programId = "PRO0000000297";
//        programId = productID;
//        240001314  240001313 这两个是新建立的自动续订的产品
//        240001315  240001316 240001317 240001318 240001319 240001320
//        programId = "240001315";
        String programId = "240001512";
//        programId = "PRO581c068d11701289bf0ff5c1";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AUTHENTICATION_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProgramIDAuthenticationService programIDAuthenticationService = retrofit.create(ProgramIDAuthenticationService.class);
        Call<AuthenticationBean> authenticationBeanCall = programIDAuthenticationService.getResult(
                temptoken, programId,SpId, time, riddle);
//        ProductIDAuthenticationService productIDAuthenticationService = retrofit.create(ProductIDAuthenticationService.class);
//        Call<ProductIdBean> authenticationBeanCall = productIDAuthenticationService.getResult(
//                temptoken, programId, AppCommonInfo.SpId, time, riddle);
        authenticationBeanCall.enqueue(new Callback<AuthenticationBean>() {
            @Override
            public void onResponse(Call<AuthenticationBean> call, Response<AuthenticationBean> response) {
                Log.e(TAG, "onResponse:response " + response.toString());
                AuthenticationBean authenticationBean = new AuthenticationBean();
                authenticationBean = response.body();
                authenticationBean.getResult();
//                mResult = authenticationBean.getResult();
//                Log.e(TAG, "onResponse: mResult" + mResult);
//                if (mResult.equals("0")) {
//                    Toast.makeText(PlayListActivity.this, "用户登陆状态异常", Toast.LENGTH_LONG).show();
//                } else if (mResult.equals("1")) {//1.跳转订购页面
//                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayListActivity.this);
//                    builder.setMessage("您尚未订购该产品，请订购！")
//                            .setNegativeButton("再看看", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            })
//                            .setPositiveButton("去订购", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    goToOrderActivity();
//                                }
//                            });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else if (mResult.equals("2")) {//2.鉴权通过，跳转播放页面
//                    getPlayUrl();
//                }
            }

            @Override
            public void onFailure(Call<AuthenticationBean> call, Throwable t) {
                Log.e(TAG, "onFailure: " + "访问网络失败！");
            }
        });
    }

    @JavascriptInterface
    public void getOrderActivity(String name){
        Toast.makeText(mActivity,"调取了OrderActivity",Toast.LENGTH_LONG).show();
    }

}

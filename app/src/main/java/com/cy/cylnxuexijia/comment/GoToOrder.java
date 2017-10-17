package com.cy.cylnxuexijia.comment;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static com.cy.cylnxuexijia.comment.CommentInfo.SpId;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class GoToOrder {

    private static final String TAG = "GoToOrder";

    public GoToOrder() {
    }

    public static void goToOrderActivity(AppCompatActivity appCompatActivity, String videoName,
                                         String desc, String contentID) {
        //第一个参数是Activity所在的package包名，第二个参数是完整的Class类名（包括包路径）
        ComponentName componentName = new ComponentName("com.widgetdo.ottboxforgx",
                "com.widgetdo.ottboxforgx.activity.OrderListOutwardActivity");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.putExtra("videoname", videoName);
        intent.putExtra("desc", desc);
        Log.e(TAG, "goToOrderActivity: " + contentID);
        intent.putExtra("contentid", contentID);
        intent.putExtra("spid",SpId);
        intent.putExtra("contenttype", "2");//视频类型：1：视频；2：游戏
        intent.putExtras(intent);
        appCompatActivity.startActivityForResult(intent, 0);
    }

}

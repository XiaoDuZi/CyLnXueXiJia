package com.cy.cylnxuexijia.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

import com.cy.cylnxuexijia.fragments.SmallVideoFragment;


/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class CyVideoView extends VideoView {

    public CyVideoView(Context context) {
        super(context);
    }

    public CyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

}

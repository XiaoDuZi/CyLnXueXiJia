package com.cy.cylnxuexijia.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.adapters.GridViewAdapter;
import com.cy.cylnxuexijia.bean.GridViewBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity {


    @BindView(R.id.cy_video)
    VideoView mCyVideo;
    @BindView(R.id.lv_play)
    GridView mLvPlay;
    @BindView(R.id.ll_play)
    LinearLayout mLlPlay;
    @BindView(R.id.gv_play)
    GridView mGvPlay;
    @BindView(R.id.ll_play_pause)
    LinearLayout mLlPlayPause;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;

    private String[] listViewData = {"上一节", "下一节", "继续观看", "浏览课件"};
    private int[] gridViewImage = {R.drawable.class_point1, R.drawable.class_point2,
            R.drawable.class_point3, R.drawable.class_point4};
    private List<GridViewBean> mGridViewDataList = new ArrayList<>();
    private GridViewBean gridViedBean = new GridViewBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        initGridViewData();
    }

    private void initGridViewData() {
        for (int i = 0; i < gridViewImage.length; i++) {
            gridViedBean.setImage(gridViewImage[i]);
            gridViedBean.setTitle(listViewData[i]);
            mGridViewDataList.add(gridViedBean);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            mLlPlayPause.setVisibility(View.VISIBLE);
            mCyVideo.setFocusable(false);
            mSeekBar.setFocusable(false);
            mLvPlay.setFocusable(true);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlayActivity.this,
                    android.R.layout.simple_list_item_1, listViewData);
            mLvPlay.setAdapter(adapter);
            mLvPlay.setSelection(3);

            GridViewAdapter gridViewAdapter = new GridViewAdapter(mGridViewDataList, PlayActivity.this);
            mGvPlay.setAdapter(gridViewAdapter);

        }
        return super.onKeyDown(keyCode, event);
    }
}

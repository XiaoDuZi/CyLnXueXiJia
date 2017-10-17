package com.cy.cylnxuexijia.fragments;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.bean.PlayUrlBean;
import com.cy.cylnxuexijia.bean.SmallVideoItemBean;
import com.cy.cylnxuexijia.interfaces.PlayUrlService;
import com.cy.cylnxuexijia.tools.CyUtils;
import com.cy.cylnxuexijia.views.CyVideoView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cy.cylnxuexijia.comment.CommentInfo.BASEURL;
import static com.cy.cylnxuexijia.comment.CommentInfo.PLAY_KEY;
import static com.cy.cylnxuexijia.comment.CommentInfo.SpId;
import static com.cy.cylnxuexijia.comment.CommentInfo.Type;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmallVideoFragment extends Fragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static final String TAG = "SmallVideoFragment";


    @BindView(R.id.cy_small_video)
    CyVideoView mCySmallVideo;
    Unbinder unbinder;

    private List<String> mVodid;
    private String mPlayVodID;
    private String mPlatform;
    private int i = 0;

    public SmallVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_small_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        mCySmallVideo.setFocusable(false);
        mCySmallVideo.clearFocus();

        Bundle bundle =getArguments();
        if (bundle!=null){
            String result = bundle.getString("VodID");
            Log.e(TAG, "onCreateView: "+result);
            getVodId(result);
        }else {
            mCySmallVideo.stopPlayback();
        }

        initVideo();

        return view;
    }

    private void initVideo() {
        Log.e(TAG, "initVideo: ");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    mPlayVodID = "MOV58649e4ad9461c0f2388572a";
                    getPlayUrl();
            }
        });
    }

    private void getPlayUrl() {
        long mTime = System.currentTimeMillis();
        String mRiddle = CyUtils.MD5(mTime+PLAY_KEY);
        if (mPlatform == null) {
            mPlatform = "GD";
        }
        Retrofit retrofit = new Retrofit.Builder()        //使用Retrofit网络框架进行访问网络
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlayUrlService lnPlayUrlService = retrofit.create(PlayUrlService.class);

        Call<PlayUrlBean> call = lnPlayUrlService.getPlayUrlInfo(Type,
                mTime,mRiddle, mPlatform, SpId, mPlayVodID, "", "");
        call.enqueue(new Callback<PlayUrlBean>() {
            @Override
            public void onResponse(Call<PlayUrlBean> call, Response<PlayUrlBean> response) {
                final PlayUrlBean mPlayUrlBean = response.body();
                Log.e(TAG, "onResponse: " + response.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playVideo(mPlayUrlBean);       //播放视频
                    }
                });
            }

            @Override
            public void onFailure(Call<PlayUrlBean> call, Throwable t) {
                Log.e(TAG, "onFailure: 请求视频链接失败！");
            }
        });
    }


    private void playVideo(PlayUrlBean lnPlayUrlBean) {

        try {
            if (lnPlayUrlBean.getPlayUrl() == null)
                return;
            Uri playUrl = Uri.parse(lnPlayUrlBean.getPlayUrl());
            if (playUrl.toString().equals("该内容无播放地址!")
                    ||playUrl.toString().equals("解析地址出错!")) {
                switch (mPlatform) {
                    case "ZX":
                        mPlatform = "GD";
                        getPlayUrl();
                        break;
                    case "GD":
                        mPlatform = "HW";
                        getPlayUrl();
                        //正式地址
                        break;
                    case "HW":
                        mPlatform = "ZX";
                        getPlayUrl();
                        break;
                }
            } else {
                //正式地址
                beginPlayVideo(playUrl);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    private void beginPlayVideo(Uri playUrl) {
        //正式地址
        mCySmallVideo.setVideoPath(playUrl.toString());

        mCySmallVideo.setOnPreparedListener(this);
        mCySmallVideo.requestFocus();
    }

    /**
     * 播放首页小窗口视频获取视频播放ID
     * @param vodID
     */
    private void getVodId(String vodID) {
        List<SmallVideoItemBean> smallVideoItemBeanList = new ArrayList<>();
        Gson gson = new Gson();
        smallVideoItemBeanList = (List<SmallVideoItemBean>) gson.fromJson(vodID,
                new TypeToken<List<SmallVideoItemBean>>() {
                }.getType());
        mVodid = new ArrayList<>();
        for (int i = 0; i < smallVideoItemBeanList.size(); i++) {
            mVodid.add(smallVideoItemBeanList.get(i).getVodId());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mp.isPlaying())
            mp.reset();
        mp.start();
        mp.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        i++;
        if (i >= mVodid.size()) {
            i = 0;
            mPlayVodID = mVodid.get(i);
        } else {
            mPlayVodID = mVodid.get(i);
        }
        getPlayUrl();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach: ");
    }
}

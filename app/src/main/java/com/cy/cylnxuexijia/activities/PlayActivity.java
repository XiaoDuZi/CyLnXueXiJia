package com.cy.cylnxuexijia.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.adapters.GridViewAdapter;
import com.cy.cylnxuexijia.bean.GridViewBean;
import com.cy.cylnxuexijia.bean.ParamsBean;
import com.cy.cylnxuexijia.bean.PlayDataBean;
import com.cy.cylnxuexijia.bean.PlayUrlBean;
import com.cy.cylnxuexijia.bean.PointsBean;
import com.cy.cylnxuexijia.bean.TermsBean;
import com.cy.cylnxuexijia.bean.VideoBean;
import com.cy.cylnxuexijia.bean.VideosBean;
import com.cy.cylnxuexijia.fragments.BuyDialogFragment;
import com.cy.cylnxuexijia.fragments.OrderDialogFragment;
import com.cy.cylnxuexijia.interfaces.PlayDataService;
import com.cy.cylnxuexijia.interfaces.PlayUrlService;
import com.cy.cylnxuexijia.tools.ConverUtil;
import com.cy.cylnxuexijia.tools.CyUtils;
import com.cy.cylnxuexijia.tools.LnUtils;
import com.cy.cylnxuexijia.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cy.cylnxuexijia.comment.CommentInfo.BASEURL;
import static com.cy.cylnxuexijia.comment.CommentInfo.PLAY_KEY;
import static com.cy.cylnxuexijia.comment.CommentInfo.PPT_URL;
import static com.cy.cylnxuexijia.comment.CommentInfo.SpId;
import static com.cy.cylnxuexijia.comment.CommentInfo.Type;
import static com.cy.cylnxuexijia.comment.CommentInfo.WEB_PARAMS_JUNITOR;
import static com.cy.cylnxuexijia.comment.CommentInfo.WEB_PARAMS_PRIMARY;
import static com.cy.cylnxuexijia.comment.CommentInfo.WEB_PARAMS_SENIOR;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, AdapterView.OnItemClickListener {

    private static final String TAG = "PlayActivity";

    @BindView(R.id.cy_video)
    VideoView mCyVideo;
    @BindView(R.id.gv_play_up)
    GridView mGvPlayUp;
    @BindView(R.id.ll_play)
    LinearLayout mLlPlay;
    @BindView(R.id.gv_play_down)
    GridView mGvPlayDwon;
    @BindView(R.id.ll_play_pause)
    LinearLayout mLlPlayPause;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.tv_video_name)
    TextView mTvVideoName;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.iv_video_pause_play)
    ImageView mIvVideoPausePlay;
    @BindView(R.id.tv_video_playing_time)
    TextView mTvVideoPlayingTime;
    @BindView(R.id.tv_video_time)
    TextView mTvVideoTime;
    @BindView(R.id.rl_video_top_bottom)
    RelativeLayout mRlVideoTopBottom;
    @BindView(R.id.videoProgressBar)
    ProgressBar mVideoProgressBar;

    private String[] listViewData = {"上一节", "下一节", "继续观看", "浏览课件"};
    private int[] gridViewImage = {R.drawable.class_point1, R.drawable.class_point2,
            R.drawable.class_point3, R.drawable.class_point4};
    private List<GridViewBean> mGridViewDataList;
    private GridViewBean gridViedBean;
    private boolean mShowPauseWindow = false;
    private String mPlatform;
    private String mVideoID;
    private boolean mIsShowPopWindow = false;
    private long mDuration;
    private long mCurrentTime;

    private static final int ZTE_PLAY_TIME = 0;
    private static final int SHOW_CONTROL = 1;
    private static final int HIDE_CONTROL = 2;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == ZTE_PLAY_TIME) {
                setZTEBoxPlayingTime();
            } else if (msg.what == SHOW_CONTROL) {
                mRlVideoTopBottom.setVisibility(View.VISIBLE);
            } else if (msg.what == HIDE_CONTROL) {
                mRlVideoTopBottom.setVisibility(View.GONE);
            }
            return false;
        }
    });
    private VideoBean mVideoBean;
    private List<VideosBean> mVideosBeanList;
    private List<ParamsBean> mParamsBeanList;
    private int mVideosIndex;
    private String mVideoName;
    private PlayDataBean mPlayDataBean;
    private String mPramasUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mVideoBean = (VideoBean) intent.getSerializableExtra("playData");

//        mVideoID = String.valueOf(videoBean.getVideo_id());
        mVideoID = "MOV58649e4ad9461c0f2388572a";

        getPlayUrl();

        showHideControl();

        initDatas();

    }

    private void initDatas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.76.221.222:90/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlayDataService playDataService = retrofit.create(PlayDataService.class);
        Call<PlayDataBean> playDataCall = playDataService.getPlayData(String.valueOf(mVideoBean.getUser_id()),
                String.valueOf(mVideoBean.getProduct_id()), String.valueOf(mVideoBean.getContent_id()));
        playDataCall.enqueue(new Callback<PlayDataBean>() {
            @Override
            public void onResponse(Call<PlayDataBean> call, Response<PlayDataBean> response) {
                Log.e(TAG, "onResponse: "+response.toString());
                mPlayDataBean = response.body();
                List<TermsBean> listTerms = new ArrayList<TermsBean>();
                listTerms = mPlayDataBean.getData().getTerms();
                mVideosBeanList = new ArrayList<VideosBean>();
                mParamsBeanList = new ArrayList<ParamsBean>();
                for (TermsBean term : listTerms) {
                    for (PointsBean point : term.getPoints()) {
                        if (point.getPoint_id().equals(String.valueOf(mVideoBean.getPoint_id()))) {
                            Log.e(TAG, "onResponse:------------------------------------------------- ");
                            mVideosBeanList = point.getVideos();
                            mParamsBeanList = ConverUtil.jsonToBeanList(point.getParams(), ParamsBean.class);
                            initGridViewData();
                            for (VideosBean videosBean : mVideosBeanList) {
                                if (videosBean.getVideo_id().equals(String.valueOf(mVideoBean.getVideo_id()))) {
                                    mVideosIndex = mVideosBeanList.indexOf(videosBean);
                                    mVideoName = mVideosBeanList.get(mVideosIndex).getVideo_name();
                                    mTvVideoName.setText(mVideoName);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PlayDataBean> call, Throwable t) {
                Log.e(TAG, "onFailure: ");
            }
        });
    }

    private void showHideControl() {
        mHandler.sendEmptyMessage(SHOW_CONTROL);
        mHandler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
    }

    private void setZTEBoxPlayingTime() {
        mHandler.sendEmptyMessageDelayed(ZTE_PLAY_TIME, 1000);
        mCurrentTime = mCyVideo.getCurrentPosition();
        mSeekBar.setProgress((int) ((mCurrentTime * 100) / mDuration));
        mTvVideoPlayingTime.setText(LnUtils.generateTime(mCurrentTime));
    }


    private void playVideo(PlayUrlBean lnPlayUrlBean) {

        try {
            if (lnPlayUrlBean.getPlayUrl() == null)
                return;
            Uri playUrl = Uri.parse(lnPlayUrlBean.getPlayUrl());
            if (playUrl.toString().equals("该内容无播放地址!")
                    || playUrl.toString().equals("解析地址出错!")) {
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

    private void getPlayUrl() {
        long mTime = System.currentTimeMillis();
        String mRiddle = CyUtils.MD5(mTime + PLAY_KEY);
        if (mPlatform == null) {
            mPlatform = "GD";
        }
        Retrofit retrofit = new Retrofit.Builder()        //使用Retrofit网络框架进行访问网络
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlayUrlService lnPlayUrlService = retrofit.create(PlayUrlService.class);

        Call<PlayUrlBean> call = lnPlayUrlService.getPlayUrlInfo(Type,
                mTime, mRiddle, mPlatform, SpId, mVideoID + "", "", "");
        call.enqueue(new Callback<PlayUrlBean>() {
            @Override
            public void onResponse(Call<PlayUrlBean> call, Response<PlayUrlBean> response) {
                final PlayUrlBean mPlayUrlBean = response.body();
                Log.e(TAG, "onResponse: " + response.toString());
                runOnUiThread(new Runnable() {
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

    private void beginPlayVideo(Uri playUrl) {
        //正式地址
        mCyVideo.setVideoPath(playUrl.toString());
        mCyVideo.setOnPreparedListener(this);
        mCyVideo.setOnErrorListener(this);
        mCyVideo.requestFocus();
    }

    private void initGridViewData() {

        mGridViewDataList = new ArrayList<>();
        for (int i = 0; i < gridViewImage.length; i++) {
            gridViedBean = new GridViewBean();
            gridViedBean.setImage(gridViewImage[i]);
            gridViedBean.setTitle(mParamsBeanList.get(i).getPoint_name());
            gridViedBean.setContentId(mParamsBeanList.get(i).getContent_id());
            gridViedBean.setPointId(mParamsBeanList.get(i).getPoint_id());
            gridViedBean.setProductID(mParamsBeanList.get(i).getProduct_id());
            gridViedBean.setTeacherID(mVideoBean.getTeacher_id());
            mGridViewDataList.add(gridViedBean);
        }
        Log.e(TAG, "initGridViewData: " + mGridViewDataList.toString());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            showPopWindow();
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (mLlPlayPause.getVisibility() == View.VISIBLE && mGvPlayDwon.isFocusable()) {
                mLlPlay.setBackgroundResource(R.drawable.video_player_no_border);
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (mLlPlayPause.getVisibility() == View.VISIBLE && mGvPlayUp.isFocusable()) {
                mLlPlay.setBackgroundResource(R.drawable.video_pause_border);
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsShowPopWindow) {
                mIsShowPopWindow = false;
                mLlPlayPause.setVisibility(View.GONE);
                if (Build.MANUFACTURER.equals("ZTE Corporation")) {
                    mHandler.sendEmptyMessage(ZTE_PLAY_TIME);
                }
                mCyVideo.start();
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
//            mCyVideo.pause();
            if (mIsShowPopWindow) {

            } else {
                mCyVideo.pause();
                mRlVideoTopBottom.setVisibility(View.VISIBLE);
                mCyVideo.setFocusable(false);
                mSeekBar.setFocusable(true);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void showPopWindow() {
        mHandler.sendEmptyMessage(SHOW_CONTROL);
        mIsShowPopWindow = true;
        mCyVideo.pause();
        Log.e(TAG, "showPopWindow: ");
        if (Build.MANUFACTURER.equals("ZTE Corporation")) {
            mHandler.removeMessages(ZTE_PLAY_TIME);
        }
        mLlPlayPause.setVisibility(View.VISIBLE);
        mCyVideo.setFocusable(false);
        mSeekBar.setFocusable(false);
        mGvPlayUp.setFocusable(true);

        mIvVideoPausePlay.setImageResource(R.drawable.pv_play);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlayActivity.this,
                R.layout.gridview_up_item, listViewData);
        mGvPlayUp.setAdapter(adapter);
        mGvPlayUp.setSelection(2);

        mGvPlayUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        lastLesson();
                        break;
                    case 1:
                        nextLesson();
                        break;
                    case 2:
                        pausePlay();
                        break;
                    case 3:
                        goPPTActivity();
                        break;
                    default:
                        break;
                }
            }
        });

        GridViewAdapter gridViewAdapter = new GridViewAdapter(mGridViewDataList, PlayActivity.this);
        mGvPlayDwon.setAdapter(gridViewAdapter);

        getPramasUrl();

        mGvPlayDwon.setOnItemClickListener(this);
    }

    private void goPPTActivity() {
        String videoId = mVideosBeanList.get(mVideosIndex).getVideo_id();
        String coursewareCount = mVideosBeanList.get(mVideosIndex).getCourseware_count();
        String pptUrl = StringUtils.format(PPT_URL, videoId, coursewareCount);
        PPTActivity.actionStartPPT(PlayActivity.this, pptUrl);
        mLlPlayPause.setVisibility(View.GONE);
    }

    private void pausePlay() {
        mLlPlayPause.setVisibility(View.GONE);
        mIvVideoPausePlay.setImageResource(R.drawable.pv_pause);
        mCyVideo.start();
        showHideControl();
        if (Build.MANUFACTURER.equals("ZTE Corporation")) {
            mHandler.sendEmptyMessage(ZTE_PLAY_TIME);
        }
    }

    private void nextLesson() {
        mVideosIndex++;
        if (mVideosIndex > mVideosBeanList.size()) {
            Toast.makeText(PlayActivity.this, "已经是最后一节了！", Toast.LENGTH_LONG).show();
            return;
        }
        if (mVideosBeanList.get(mVideosIndex).getIs_free().equals("0")) {
            mVideoName = mVideosBeanList.get(mVideosIndex).getVideo_name();
            mVideoID = mVideosBeanList.get(mVideosIndex).getVideo_id();
        } else if (mVideosBeanList.get(mVideosIndex).getIs_free().equals("1")) {
            Toast.makeText(PlayActivity.this, "订购去", Toast.LENGTH_LONG).show();
            DialogFragment dialogFragment = BuyDialogFragment.newInstance(1);
            dialogFragment.show(getFragmentManager(), "buyDialog");
        }
    }

    private void lastLesson() {
        mVideosIndex--;
        if (mVideosIndex < mVideosBeanList.size()) {
            Toast.makeText(PlayActivity.this, "已经是第一节了", Toast.LENGTH_LONG).show();
            return;
        }
        if (mVideosBeanList.get(mVideosIndex).getIs_free().equals("0")) {
            mVideoName = mVideosBeanList.get(mVideosIndex).getVideo_name();
            mVideoID = mVideosBeanList.get(mVideosIndex).getVideo_id();
        } else if (mVideosBeanList.get(mVideosIndex).getIs_free().equals("1")) {
            Toast.makeText(PlayActivity.this, "订购去", Toast.LENGTH_LONG).show();
            BuyDialogFragment buyDialog=new BuyDialogFragment();
            DialogFragment dialogFragment = BuyDialogFragment.newInstance(1);
            dialogFragment.show(getFragmentManager(), "buyDialog");
        }
    }

    private void getPramasUrl() {
        int type = Integer.valueOf(mPlayDataBean.getData().getProduct_type());
        String gradeName = mPlayDataBean.getData().getGrade_name();
        if (type == 1) {
            if (gradeName.contains("年级")) {//小学
                mPramasUrl = WEB_PARAMS_PRIMARY;
            } else if (gradeName.contains("初")) {//初中
                mPramasUrl = WEB_PARAMS_JUNITOR;
            } else if (gradeName.contains("高")) {//高中
                mPramasUrl = WEB_PARAMS_SENIOR;
            }
        }
    }

    private void goParamsWeb(int position) {
        String url = StringUtils.format(mPramasUrl, mGridViewDataList.get(position).getProductID(),
                mGridViewDataList.get(position).getTeacherID(), mGridViewDataList.get(position).getContentId(),
                mGridViewDataList.get(position).getPointId(), mVideoBean.getUser_id());
        CyLnXueXiJiaMainActivity.actionStartCyLnXueXiJiaMainActivity(PlayActivity.this, url);
    }

    public static void actionStart(Context context, VideoBean videoID) {
        Log.e(TAG, "视频播放数据：" + videoID.toString());
        Intent intent = new Intent();
        intent.setClass(context, PlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("playData", videoID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mp.isPlaying())
            mp.reset();
        mp.start();
        mVideoProgressBar.setVisibility(View.GONE);
        if (Build.MANUFACTURER.equals("ZTE Corporation")) {
            mHandler.sendEmptyMessage(ZTE_PLAY_TIME);
        }
        //获取视频总时间
        mDuration = mp.getDuration();
        mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mTvVideoTime.setText(LnUtils.generateTime(mDuration));//显示视频总时长
        mp.setOnCompletionListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnErrorListener(this);
    }

    SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress > 0) {
                if (fromUser) {

                    mCyVideo.pause();
                    if (progress > 99) {
                        return;
                    }
//                    hidePlayerBottomLayoutHandler.removeMessages(0);
//                    playerBottomLayout.setVisibility(View.VISIBLE);
                    mCurrentTime = mDuration * progress / 100;
                    mTvVideoPlayingTime.setText(LnUtils.generateTime(mCurrentTime));
                    Log.e(TAG, "onProgressChanged: " + mCurrentTime);
                    mCyVideo.seekTo((int) mCurrentTime);
//                    playerBottomLayout.setVisibility(View.VISIBLE);
                } else {
//                    Log.e(TAG, "onProgressChanged: "+mCurrentTime);
                    if (mCyVideo.isPlaying()) {

                    } else {
                        Log.e(TAG, "onProgressChanged: start");
                        mCyVideo.start();
                    }


//                    if (playerBottomLayout.getVisibility() == View.VISIBLE) {
//                        hidePlayerBottomLayoutHandler.removeMessages(0);
//                        hidePlayerBottomLayoutHandler.sendEmptyMessageDelayed(0, 4000);
//                    }
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        if (Build.MANUFACTURER.equals("ZTE Corporation")) {
            mHandler.removeMessages(ZTE_PLAY_TIME);
        }
        showPopWindow();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (!Build.MANUFACTURER.equals("ZTE Corporation")) {
            //当前播放时间获取
            mCurrentTime = mp.getCurrentPosition();
            mSeekBar.setProgress((int) ((mCurrentTime * 100) / mDuration));
            mTvVideoPlayingTime.setText(LnUtils.generateTime(mCurrentTime));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.MANUFACTURER.equals("ZTE Corporation")) {
            mHandler.removeMessages(ZTE_PLAY_TIME);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                goParamsWeb(position);
                break;
            case 1:
                goParamsWeb(position);
                break;
            case 2:
                goParamsWeb(position);
                break;
            case 3:
                goParamsWeb(position);
                break;
            default:
                break;
        }
    }
}

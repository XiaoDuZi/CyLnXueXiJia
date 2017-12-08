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
import android.support.annotation.NonNull;
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
import com.cy.cylnxuexijia.bean.AddHostroyBean;
import com.cy.cylnxuexijia.bean.AuthenticationBean;
import com.cy.cylnxuexijia.bean.GridViewBean;
import com.cy.cylnxuexijia.bean.ParamsBean;
import com.cy.cylnxuexijia.bean.PlayDataBean;
import com.cy.cylnxuexijia.bean.PlayUrlBean;
import com.cy.cylnxuexijia.bean.PlayVideoDataBean;
import com.cy.cylnxuexijia.bean.PointsBean;
import com.cy.cylnxuexijia.bean.SpecialPlayDataBean;
import com.cy.cylnxuexijia.bean.TermsBean;
import com.cy.cylnxuexijia.bean.UserLauncherBean;
import com.cy.cylnxuexijia.bean.VideoBean;
import com.cy.cylnxuexijia.bean.VideosBean;
import com.cy.cylnxuexijia.fragments.BuyDialogFragment;
import com.cy.cylnxuexijia.fragments.PPTFragment;
import com.cy.cylnxuexijia.interfaces.AddPlayHostoryService;
import com.cy.cylnxuexijia.interfaces.PlayDataService;
import com.cy.cylnxuexijia.interfaces.PlayUrlService;
import com.cy.cylnxuexijia.interfaces.PlayVideoDataService;
import com.cy.cylnxuexijia.interfaces.ProductIDAuthenticationService;
import com.cy.cylnxuexijia.interfaces.SpecialPlayDataService;
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

import static com.cy.cylnxuexijia.comment.CommentInfo.AUTHENTICATION_URL;
import static com.cy.cylnxuexijia.comment.CommentInfo.BASEURL;
import static com.cy.cylnxuexijia.comment.CommentInfo.PLAY_KEY;
import static com.cy.cylnxuexijia.comment.CommentInfo.PPT_URL;
import static com.cy.cylnxuexijia.comment.CommentInfo.PRODUCT_PROGRAM_KEY;
import static com.cy.cylnxuexijia.comment.CommentInfo.SpId;
import static com.cy.cylnxuexijia.comment.CommentInfo.Type;
import static com.cy.cylnxuexijia.comment.CommentInfo.WEB_INDEX;
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
    private String mVideo_Url;
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
    private List<VideosBean> mVideosBeanList = new ArrayList<VideosBean>();
    ;
    private List<ParamsBean> mParamsBeanList;
    private int mVideosIndex;
    private String mVideoName;
    private PlayDataBean mPlayDataBean;
    private String mPramasUrl;
    private Call<PlayDataBean> mPlayDataCall;
    private Call<SpecialPlayDataBean> mSpecialPlayDataCall;
    private boolean mShowPPT=false;

    interface PPTBackListener{
        public void pptBack();
    }

    PPTBackListener mPPTBackListener;

    public void setPPTBackListener(PPTBackListener PPTBackListener) {
        mPPTBackListener = PPTBackListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        try {
            mVideoBean = (VideoBean) intent.getSerializableExtra("playData");
        } catch (Exception e) {
            e.printStackTrace();
        }

        getVideo_Url();

        showHideControl();

        initDatas();

    }

    private void getVideo_Url() {
        if (mVideoBean.getUser_id().equals("") || mVideoBean.getProduct_id().equals("")
                || mVideoBean.getContent_id().equals(""))
            return;
        Retrofit retrofit = getRetrofit(WEB_INDEX);
        PlayVideoDataService playVideoDataService = retrofit.create(PlayVideoDataService.class);
        Call<PlayVideoDataBean> playVideoDataBeanCall = playVideoDataService.getPlayVideoData(
                mVideoBean.getPlatformId(), mVideoBean.getUser_id(), mVideoBean.getVideo_id(),
                mVideoBean.getProduct_id(), mVideoBean.getContent_id());
        playVideoDataBeanCall.enqueue(new Callback<PlayVideoDataBean>() {
            @Override
            public void onResponse(Call<PlayVideoDataBean> call, Response<PlayVideoDataBean> response) {
                PlayVideoDataBean playVideoDataBean = new PlayVideoDataBean();
                Log.e(TAG, "onResponse: " + response.toString());
                playVideoDataBean = response.body();

                mVideo_Url = playVideoDataBean.getData().getVideo_url();
                getPlayUrl();
            }

            @Override
            public void onFailure(Call<PlayVideoDataBean> call, Throwable t) {

            }
        });
    }

    private void initDatas() {
        if (mVideoBean.getUser_id().equals("") || mVideoBean.getProduct_id().equals("")
                || mVideoBean.getContent_id().equals(""))
            return;

        Retrofit retrofit = getRetrofit(WEB_INDEX);

        judgeSpecialClass(retrofit);

        Log.e(TAG, "initDatas: " + mVideoBean.toString());
    }

    @NonNull
    private Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 判断专题 还是同步课
     *
     * @param retrofit
     */
    private void judgeSpecialClass(Retrofit retrofit) {
        if (mVideoBean.getContent_id().equals(mVideoBean.getSpecial_id())) {
            SpecialPlayDataService specialPlayDataService = retrofit.create(SpecialPlayDataService.class);
            mSpecialPlayDataCall = specialPlayDataService.getPlayData(mVideoBean.getUser_id(),
                    mVideoBean.getProduct_id());
            mSpecialPlayDataCall.enqueue(new Callback<SpecialPlayDataBean>() {
                @Override
                public void onResponse(Call<SpecialPlayDataBean> call, Response<SpecialPlayDataBean> response) {
                    Log.e(TAG, "onResponse: 推荐位" + response.toString());
                    SpecialPlayDataBean specialPlayDataBean = new SpecialPlayDataBean();
                    specialPlayDataBean = response.body();

                    List<SpecialPlayDataBean.DataBean.GradesBean> gradesBeanList = new ArrayList<SpecialPlayDataBean.DataBean.GradesBean>();
                    gradesBeanList = specialPlayDataBean.getData().getGrades();
                    for (SpecialPlayDataBean.DataBean.GradesBean gradesBean : gradesBeanList) {
                        for (SpecialPlayDataBean.DataBean.GradesBean.SubjectsBean subjectsBean : gradesBean.getSubjects()) {
                            for (SpecialPlayDataBean.DataBean.GradesBean.SubjectsBean.TermsBean termsBean : subjectsBean.getTerms()) {
                                for (SpecialPlayDataBean.DataBean.GradesBean.SubjectsBean.TermsBean.PointsBean pointsBean : termsBean.getPoints()) {
                                    if (pointsBean.getPoint_id().equals(mVideoBean.getPoint_id())) {
                                        mVideosBeanList = pointsBean.getVideos();
                                        Log.e(TAG, "onResponse: "+mVideosBeanList);
                                        for (VideosBean videosBean : mVideosBeanList) {
                                            if (videosBean.getVideo_id().equals(mVideoBean.getVideo_id())) {
                                                mVideosIndex = mVideosBeanList.indexOf(videosBean);
                                                mVideoName = mVideosBeanList.get(mVideosIndex).getVideo_name();
                                                mTvVideoName.setText(mVideoName);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SpecialPlayDataBean> call, Throwable t) {

                }
            });

        } else {
            PlayDataService playDataService = retrofit.create(PlayDataService.class);
            mPlayDataCall = playDataService.getPlayData(mVideoBean.getUser_id(),
                    mVideoBean.getProduct_id(), mVideoBean.getContent_id());
            mPlayDataCall.enqueue(new Callback<PlayDataBean>() {
                @Override
                public void onResponse(Call<PlayDataBean> call, Response<PlayDataBean> response) {
                    Log.e(TAG, "onResponse:普通 " + response.toString());
                    mPlayDataBean = response.body();
                    if (mPlayDataBean.getData() == null)
                        return;
//                if (null==mPlayDataBean.getData().getTerms()||mPlayDataBean.getData().getTerms().equals("null")){
////                    SpecialPlayDataBean specialPlayDataBean=new SpecialPlayDataBean();
////                    specialPlayDataBean=response.body();
//                }

                    Log.e(TAG, "onResponse: " + mPlayDataBean.toString());
                    List<TermsBean> listTerms = new ArrayList<TermsBean>();

                    listTerms = mPlayDataBean.getData().getTerms();
//                    mVideosBeanList = new ArrayList<VideosBean>();
                    mParamsBeanList = new ArrayList<ParamsBean>();
                    for (TermsBean term : listTerms) {
                        for (PointsBean point : term.getPoints()) {
                            if (point.getPoint_id().equals(mVideoBean.getPoint_id())) {
                                Log.e(TAG, "onResponse:------------------------------------------------- ");
                                mVideosBeanList = point.getVideos();
                                Log.e(TAG, "onResponse:mVideosBeanList "+mVideosBeanList.toString());
                                if (point.getParams() == null)
                                    return;
                                mParamsBeanList = ConverUtil.jsonToBeanList(point.getParams(), ParamsBean.class);
                                initGridViewData();
                                for (VideosBean videosBean : mVideosBeanList) {
                                    if (videosBean.getVideo_id().equals(mVideoBean.getVideo_id())) {
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
        Log.e(TAG, "playVideo: " + lnPlayUrlBean.getPlayUrl());
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

        Retrofit retrofit = getRetrofit(BASEURL);
        PlayUrlService lnPlayUrlService = retrofit.create(PlayUrlService.class);

        Log.e(TAG, "getPlayUrl: " + mVideo_Url);
        Call<PlayUrlBean> call = lnPlayUrlService.getPlayUrlInfo(Type,
                mTime, mRiddle, mPlatform, SpId, mVideo_Url + "", "", "");
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
        Log.e(TAG, "beginPlayVideo: ");
        //正式地址
        mCyVideo.setVideoPath(playUrl.toString());
        mCyVideo.setOnPreparedListener(this);
        mCyVideo.setOnErrorListener(this);
        mCyVideo.requestFocus();
    }

    private void initGridViewData() {
        Log.e(TAG, "initGridViewData: " + mParamsBeanList.toString());
        mGridViewDataList = new ArrayList<>();
        int paramsSize;
        if (mParamsBeanList.size() >= gridViewImage.length) {
            paramsSize = gridViewImage.length;
        } else {
            paramsSize = mParamsBeanList.size();
        }
        for (int i = 0; i < paramsSize; i++) {
            Log.e(TAG, "initGridViewData: " + i);
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
            if (!mShowPPT){
                if (mIsShowPopWindow) {
                    mIsShowPopWindow = false;
                    mLlPlayPause.setVisibility(View.GONE);
                    if (Build.MANUFACTURER.equals("ZTE Corporation")) {
                        mHandler.sendEmptyMessage(ZTE_PLAY_TIME);
                    }
                    mCyVideo.start();
                    return true;
                }else {
                    addPlayHostory(mVideosIndex);
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            mCyVideo.pause();
            if (mIsShowPopWindow) {
                showHideControl();
//                mRlVideoTopBottom.setVisibility(View.VISIBLE);
                mSeekBar.setFocusable(true);
            } else {
//                mCyVideo.pause();
                mRlVideoTopBottom.setVisibility(View.VISIBLE);
                mCyVideo.setFocusable(false);
                mSeekBar.setFocusable(true);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
            mCyVideo.start();
        }
        return super.onKeyUp(keyCode, event);
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
                        goPPTFragment();
                        break;
                    default:
                        break;
                }
            }
        });


        if (mGridViewDataList == null)
            return;
        GridViewAdapter gridViewAdapter = new GridViewAdapter(mGridViewDataList, PlayActivity.this);
        mGvPlayDwon.setAdapter(gridViewAdapter);

        getPramasUrl();

        mGvPlayDwon.setOnItemClickListener(this);
    }

    private void goPPTFragment() {
        Log.e(TAG, "goPPTFragment: "+mVideosIndex);
        Log.e(TAG, "goPPTFragment: "+mVideosBeanList.toString());
        mShowPPT=true;
        String videoId;
        String coursewareCount;
        if (mVideosBeanList.size()==0){
            videoId = mVideoBean.getVideo_id();
            coursewareCount = mVideoBean.getCourseware_count();
        }else {
            videoId = mVideosBeanList.get(mVideosIndex).getVideo_id();
            coursewareCount = mVideosBeanList.get(mVideosIndex).getCourseware_count();
        }

        String pptUrl = StringUtils.format(PPT_URL, videoId, coursewareCount);
        DialogFragment dialogFragment=PPTFragment.newInstance(pptUrl);
        dialogFragment.show(getFragmentManager(),"ppt");
//        mLlPlayPause.setVisibility(View.GONE);
    }

    private void pausePlay() {
        mLlPlayPause.setVisibility(View.GONE);
        mSeekBar.setFocusable(true);
        mIvVideoPausePlay.setImageResource(R.drawable.pv_pause);
        mCyVideo.start();
        showHideControl();
        if (Build.MANUFACTURER.equals("ZTE Corporation")) {
            mHandler.sendEmptyMessage(ZTE_PLAY_TIME);
        }
    }

    private void lastLesson() {
        Log.e(TAG, "lastLesson: " + mVideosIndex);
        mVideosIndex--;
        Log.e(TAG, "lastLesson: " + mVideosIndex + ":" + mVideosBeanList.size());
        if (mVideosIndex <= 0) {
            Toast.makeText(PlayActivity.this, "已经是第一节了", Toast.LENGTH_LONG).show();
            mVideosIndex=0;
            return;
        }
        playOrOrderNext("last",mVideosIndex);
        if (mVideosBeanList.get(mVideosIndex).getIs_free().equals("1")) {
            mVideosIndex++;
        }
    }

    /**
     * 点击上一节，下一节时跳转下一节或者订购
     *
     * @param videosIndex
     */
    private void playOrOrderNext(String lastOrNest,int videosIndex) {
        if ("last".equals(lastOrNest)){
            Log.e(TAG, "playOrOrderNext: =========");
            videosIndex++;
            //加入播放历史纪录
            addPlayHostory(videosIndex);
        }else if ("next".equals(lastOrNest)){
            Log.e(TAG, "playOrOrderNext: ========");
            videosIndex--;
            //加入播放历史纪录
            addPlayHostory(videosIndex);
        }

        if (mVideosBeanList.get(mVideosIndex).getIs_free().equals("0")) {
            mVideoName = mVideosBeanList.get(mVideosIndex).getVideo_name();
            mVideo_Url = mVideosBeanList.get(mVideosIndex).getVideo_url();
            mTvVideoName.setText(mVideoName);
            getPlayUrl();
            pausePlay();
        } else if (mVideosBeanList.get(mVideosIndex).getIs_free().equals("1")) {
            Toast.makeText(PlayActivity.this, "订购去", Toast.LENGTH_LONG).show();
            getLiaoNingAuthentication(mVideoBean.getProduct_id());
        }

    }

    private void nextLesson() {
        mVideosIndex++;
        if (mVideosIndex >= mVideosBeanList.size()) {
            Toast.makeText(PlayActivity.this, "已经是最后一节了！", Toast.LENGTH_LONG).show();
            mVideosIndex=mVideosBeanList.size()-1;
            return;
        }
        Log.e(TAG, "nextLesson: ==============");
        playOrOrderNext("next",mVideosIndex);
        if (mVideosBeanList.get(mVideosIndex).getIs_free().equals("1")) {
            mVideosIndex--;
        }
    }

    private void getLiaoNingAuthentication(String product_id) {

        long time = System.currentTimeMillis();
        String riddle = CyUtils.MD5(time + PRODUCT_PROGRAM_KEY);
        String temptoken = UserLauncherBean.getUserLauncherBean().getUser32Key();
        final String productId = "240001488";

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
                String mResult = authenticationBean.getResult();
                if (mResult.equals("0")) {
                    DialogFragment dialogFragment = BuyDialogFragment.newInstance(mVideoBean.getProduct_id(),
                            mVideoBean.getUser_id(), mPlayDataBean.getData().getProduct_type(), mVideoBean.getContent_id(),
                            mVideoBean.getPoint_id(), mPlayDataBean.getData().getGrade_name(), mVideoBean.getVideo_id());
                    dialogFragment.show(getFragmentManager(), "buyDialog");
                } else if (mResult.equals("2")) {
                    mVideoName = mVideosBeanList.get(mVideosIndex).getVideo_name();
                    mVideo_Url = mVideosBeanList.get(mVideosIndex).getVideo_url();
                    mTvVideoName.setText(mVideoName);
                    getPlayUrl();
                }
                Log.e(TAG, "onResponse: " + mResult);
            }

            @Override
            public void onFailure(Call<AuthenticationBean> call, Throwable t) {
                Log.e(TAG, "onFailure: " + "访问网络失败！");
            }
        });
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

        addPlayHostory(mVideosIndex);

        CyLnXueXiJiaMainActivity.CYXUEXIJIAMAIN_INSTANCE.finish();
        String url = StringUtils.format(mPramasUrl, mGridViewDataList.get(position).getProductID(),
                mGridViewDataList.get(position).getTeacherID(), mGridViewDataList.get(position).getContentId(),
                mGridViewDataList.get(position).getPointId(), mVideoBean.getUser_id());
        CyLnXueXiJiaMainActivity.actionStartCyLnXueXiJiaMainActivity(PlayActivity.this, url);
        finish();
    }

    private void addPlayHostory(int videosIndex) {
//   /log/466/4220/78/232/100
        Log.e(TAG, "addPlayHostory: --------------------------------------");
        Log.e(TAG, "addPlayHostory: /n UserID:"+UserLauncherBean.getUserLauncherBean().getUserName()+
                        "productID:"+mVideoBean.getProduct_id()+"ContetnId:"+mVideoBean.getContent_id()+
                        "Video_Url"+mVideosBeanList.get(videosIndex).getVideo_id()+"mCurrentTime:"+mCurrentTime);
        Retrofit retrofit=getRetrofit(WEB_INDEX);
        AddPlayHostoryService addPlayHostoryService=retrofit.create(AddPlayHostoryService.class);
        Call<AddHostroyBean> addHostroyCall=addPlayHostoryService.addHostory(mVideoBean.getUser_id(),
                mVideoBean.getProduct_id(), mVideosBeanList.get(videosIndex).getVideo_id(),mVideoBean.getContent_id(),mCurrentTime);
        addHostroyCall.enqueue(new Callback<AddHostroyBean>() {
            @Override
            public void onResponse(Call<AddHostroyBean> call, Response<AddHostroyBean> response) {
                Log.e(TAG, "onResponse:添加播放历史纪录成功 "+response.toString());
            }

            @Override
            public void onFailure(Call<AddHostroyBean> call, Throwable t) {
                Log.e(TAG, "onFailure: 添加播放记录失败！");
            }
        });
    }

    public static void actionStart(Context context, VideoBean videoBean) {
        Intent intent = new Intent();
        intent.setClass(context, PlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("playData", videoBean);
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
//                    mCyVideo.pause();
                    progress++;
                    if (progress >=100) {
                        return;
                    }
//                    hidePlayerBottomLayoutHandler.removeMessages(0);
//                    playerBottomLayout.setVisibility(View.VISIBLE);
                    mCurrentTime = mDuration * progress / 100;
                    mTvVideoPlayingTime.setText(LnUtils.generateTime(mCurrentTime));
                    mCyVideo.seekTo((int) mCurrentTime);
                    mCyVideo.start();
//                    playerBottomLayout.setVisibility(View.VISIBLE);
                } else {
//                    Log.e(TAG, "onProgressChanged: "+mCurrentTime);
                    if (mCyVideo.isPlaying()) {

                    } else {
                        Log.e(TAG, "onProgressChanged: start");
//                        mCyVideo.start();
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

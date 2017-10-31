package com.cy.cylnxuexijia.fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.activities.CyLnXueXiJiaMainActivity;
import com.cy.cylnxuexijia.bean.VideoBean;
import com.cy.cylnxuexijia.tools.StringUtils;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.cy.cylnxuexijia.comment.CommentInfo.BUY_URL1;
import static com.cy.cylnxuexijia.comment.CommentInfo.ORDER_JUNITOR;
import static com.cy.cylnxuexijia.comment.CommentInfo.ORDER_PRIMARY;
import static com.cy.cylnxuexijia.comment.CommentInfo.ORDER_SENIOR;


/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class BuyDialogFragment extends DialogFragment {

    private static final String TAG = "BuyDialogFragment";

    @BindView(R.id.bt_dialog_order)
    Button mBtDialogOrder;
    @BindView(R.id.bt_dialog_cancel)
    Button mBtDialogCancel;
    Unbinder unbinder;
    private String mProductID;
    private String mUserID;
    private String mContentID;
    private String mPointID;
    private String mProductType;
    private String mGradeName;
    private String mOrderUrl;
    private String mBackUrl;
    private String encodeUrl;
    private String mVideoId;
    private VideoBean mVideoBean;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static BuyDialogFragment newInstance(String product_id, String user_id, String product_type,
                                                String content_id, String point_id, String grade_name, String video_id) {
        BuyDialogFragment f = new BuyDialogFragment();

        Bundle args = new Bundle();
        args.putString("product_id", product_id);
        args.putString("user_id", user_id);
        args.putString("content_id", content_id);
        args.putString("point_id", point_id);
        args.putString("product_type", product_type);
        args.putString("grade_name", grade_name);
        args.putString("video_id", video_id);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductID = getArguments().getString("product_id");
        mUserID = getArguments().getString("user_id");
        mContentID = getArguments().getString("content_id");
        mPointID = getArguments().getString("point_id");
        mProductType = getArguments().getString("product_type");
        mGradeName = getArguments().getString("grade_name");
        mVideoId = getArguments().getString("video_id");

        mVideoBean = new VideoBean();
        mVideoBean.setProduct_id(mProductID);
        mVideoBean.setUser_id(mUserID);
        mVideoBean.setContent_id(mContentID);
        mVideoBean.setPoint_id(mPointID);
        mVideoBean.setVideo_id(mVideoId);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ActionSheetDialogStyle);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        View view = inflater.inflate(R.layout.dialog_go_order, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_dialog_order, R.id.bt_dialog_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_dialog_order:
                String ACTIVITY_SIGN ="PlayActivity";
                CyLnXueXiJiaMainActivity.CYXUEXIJIAMAIN_INSTANCE.finish();
                CyLnXueXiJiaMainActivity.actionStartCyLnXueXiJiaMainActivity((AppCompatActivity) getActivity(),
                        ACTIVITY_SIGN,getPramasUrl(),mVideoBean);
                getActivity().finish();
                dismiss();
                break;
            case R.id.bt_dialog_cancel:
                dismiss();
                break;
        }
    }

    private String getPramasUrl() {
        String buyUrl = StringUtils.format(BUY_URL1, mProductID, mUserID);
        Log.e(TAG, "getPramasUrl: "+mProductType);
        if (mProductType.equals("1")) {
            if (mGradeName.contains("年级")) {//小学
                mBackUrl = StringUtils.format(ORDER_PRIMARY, mProductID, mContentID, mPointID);
                Log.e(TAG, "getPramasUrl: "+mBackUrl );
            } else if (mGradeName.contains("初")) {//初中
                mBackUrl = StringUtils.format(ORDER_JUNITOR, mProductID, mContentID, mPointID);
            } else if (mGradeName.contains("高")) {//高中
                mBackUrl = StringUtils.format(ORDER_SENIOR, mProductID, mContentID, mPointID);
            } else if (mGradeName.contains("教")) {
//                mOrderUrl=StringUtils.format()
            }
        }
        try {
            Log.e(TAG, "getPramasUrl: "+ mBackUrl);
            encodeUrl = java.net.URLEncoder.encode(mBackUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mOrderUrl = buyUrl + encodeUrl;
        return mOrderUrl;
    }

}

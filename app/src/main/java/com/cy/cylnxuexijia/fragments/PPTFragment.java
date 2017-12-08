package com.cy.cylnxuexijia.fragments;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cy.cylnxuexijia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PPTFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PPTFragment extends DialogFragment {

    private static final String TAG = "PPTFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "PPT_Url";

    @BindView(R.id.fg_ppt_webView)
    WebView mFgPptWebView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mPPT_Url;

    public PPTFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pptUrl Parameter 1.
     * @return A new instance of fragment PPTFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PPTFragment newInstance(String pptUrl) {
        PPTFragment fragment = new PPTFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,pptUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");

        if (getArguments() != null) {
            Log.e(TAG, "onCreate: ");
            mPPT_Url = getArguments().getString(ARG_PARAM1);
            Log.e(TAG, "onCreate: "+mPPT_Url);
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ActionSheetDialogStyle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ppt, container, false);
        unbinder = ButterKnife.bind(this, view);
        initWebView();
        return view;
    }

    private void initWebView() {
        mFgPptWebView.requestFocus();
        WebSettings webSettings = mFgPptWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        mFgPptWebView.loadUrl(mPPT_Url);
//        webView.addJavascriptInterface(new JavaScriptObject(PPTActivity.this), "android_ppt");
        mFgPptWebView.setScrollContainer(false);

        mFgPptWebView.setWebViewClient(new WebViewClient() {

            //返回值：true 不会显示网页资源，需要等待你的处理，false 就认为系统没有做处理，会显示网页资源
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //当url里面包含webview字段的时候，则跳转到ShowActivity原生页面，否则还是继续显示网页
                //比如：在百度输入框里面输入webview在点击搜索，再点击任何有webview字段的链接，
                //则不继续显示网页，而是跳转到自己定义的原生页面
                if (!url.contains("courseware")) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }

            //在页面开始加载时候做一些操作，比如展示进度条
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            //在页面加载完成的时候做一些操作,比如隐藏进度条
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}

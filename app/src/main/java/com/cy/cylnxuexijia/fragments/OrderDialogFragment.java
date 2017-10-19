package com.cy.cylnxuexijia.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.cy.cylnxuexijia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class OrderDialogFragment extends DialogFragment{

    private static final String TAG = "OrderDialogFragment";

    @BindView(R.id.bt_dialog_order)
    Button mBtDialogOrder;
    @BindView(R.id.bt_dialog_cancel)
    Button mBtDialogCancel;
    Unbinder unbinder;
    private String mProductID;
    private String mIsFree;

    private OrderDialogFragmentListener mOrderDialogFragmentListener;
    private View mView;

    public interface OrderDialogFragmentListener {
        void getDataFrom_DialogFragment(String productID, String isFree);
    }

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static DialogFragment newInstance(String productIID, String isFree) {
        OrderDialogFragment f = new OrderDialogFragment();

        Bundle args = new Bundle();
        args.putString("productID", productIID);
        args.putString("isFree", isFree);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductID = getArguments().getString("productID");
        mIsFree = getArguments().getString("isFree");
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ActionSheetDialogStyle);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        mView = inflater.inflate(R.layout.dialog_go_order, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @OnClick({R.id.bt_dialog_order, R.id.bt_dialog_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_dialog_order:
                mOrderDialogFragmentListener = (OrderDialogFragmentListener) getActivity();
                mOrderDialogFragmentListener.getDataFrom_DialogFragment("11", "12");
                dismiss();
                break;
            case R.id.bt_dialog_cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        // 通过接口回传数据给activity
        if (mOrderDialogFragmentListener != null) {
            mOrderDialogFragmentListener.getDataFrom_DialogFragment(mProductID, mIsFree);
        }
        super.onDestroyView();
        unbinder.unbind();
    }
}

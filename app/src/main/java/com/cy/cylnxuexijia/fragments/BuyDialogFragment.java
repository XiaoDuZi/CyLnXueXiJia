package com.cy.cylnxuexijia.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.cy.cylnxuexijia.R;


/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class BuyDialogFragment extends DialogFragment {

    int mNum;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static BuyDialogFragment newInstance(int num) {
        BuyDialogFragment f = new BuyDialogFragment();

        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.ActionSheetDialogStyle);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        View view=inflater.inflate(R.layout.dialog_go_order,container,false);

        return view;
    }

}

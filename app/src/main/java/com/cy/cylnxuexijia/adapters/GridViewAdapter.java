package com.cy.cylnxuexijia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cy.cylnxuexijia.R;
import com.cy.cylnxuexijia.bean.GridViewBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class GridViewAdapter extends BaseAdapter {

    List<GridViewBean> mList = new ArrayList<>();
    Context mContext;
    LayoutInflater mLayoutInflater;

    public GridViewAdapter(List<GridViewBean> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView != null) {
            mViewHolder= (ViewHolder) convertView.getTag();
        }else {
            convertView = mLayoutInflater.inflate(R.layout.gridview_item, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }

        mViewHolder.mIvGridViewItem.setImageResource(mList.get(position).getImage());
        mViewHolder.mTvGridViewItem.setText(mList.get(position).getTitle());

        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.iv_grid_view_item)
        ImageView mIvGridViewItem;
        @BindView(R.id.tv_grid_view_item)
        TextView mTvGridViewItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

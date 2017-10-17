package com.cy.cylnxuexijia.bean;

import android.media.Image;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class GridViewBean {

    private int mImage;
    private String mTitle;
    private String mTeacherID;
    private String mProductID;
    private String mContentId;
    private String mPointId;

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getProductID() {
        return mProductID;
    }

    public void setProductID(String productID) {
        mProductID = productID;
    }

    public String getContentId() {
        return mContentId;
    }

    public void setContentId(String contentId) {
        mContentId = contentId;
    }

    public String getPointId() {
        return mPointId;
    }

    public void setPointId(String pointId) {
        mPointId = pointId;
    }

    public String getTeacherID() {
        return mTeacherID;
    }

    public void setTeacherID(String teacherID) {
        mTeacherID = teacherID;
    }

    @Override
    public String toString() {
        return "GridViewBean{" +
                "mImage=" + mImage +
                ", mTitle='" + mTitle + '\'' +
                ", mTeacherID='" + mTeacherID + '\'' +
                ", mProductID='" + mProductID + '\'' +
                ", mContentId='" + mContentId + '\'' +
                ", mPointId='" + mPointId + '\'' +
                '}';
    }
}

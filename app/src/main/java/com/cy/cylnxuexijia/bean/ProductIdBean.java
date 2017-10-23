package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/9/8 0008.
 */

public class ProductIdBean {
    /**
     * product_id : 11
     * is_free : 1
     * user_id : 466
     * content_id : 209
     * point_id : 87
     * video_id : 259
     */

    private String product_id;
    private String is_free;
    private String user_id;
    private String content_id;
    private String point_id;
    private String video_id;

    public static ProductIdBean objectFromData(String str) {

        return new Gson().fromJson(str, ProductIdBean.class);
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getPoint_id() {
        return point_id;
    }

    public void setPoint_id(String point_id) {
        this.point_id = point_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    @Override
    public String toString() {
        return "ProductIdBean{" +
                "product_id='" + product_id + '\'' +
                ", is_free='" + is_free + '\'' +
                ", user_id='" + user_id + '\'' +
                ", content_id='" + content_id + '\'' +
                ", point_id='" + point_id + '\'' +
                ", video_id='" + video_id + '\'' +
                '}';
    }

    //    /**
//     * product_id : 11
//     * uid : 465
//     * is_free : 1
//     */
//
//    public String product_id;
//    private String uid;
//    private String is_free;
//
//    public static ProductIdBean objectFromData(String str) {
//
//        return new Gson().fromJson(str, ProductIdBean.class);
//    }
//
//    public String getProduct_id() {
//        return product_id;
//    }
//
//    public void setProduct_id(String product_id) {
//        this.product_id = product_id;
//    }
//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public String getIs_free() {
//        return is_free;
//    }
//
//    public void setIs_free(String is_free) {
//        this.is_free = is_free;
//    }
//
//    @Override
//    public String toString() {
//        return "ProductIdBean{" +
//                "product_id='" + product_id + '\'' +
//                ", uid='" + uid + '\'' +
//                ", is_free='" + is_free + '\'' +
//                '}';
//    }
}

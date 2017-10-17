package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class ParamsBean {

    /**
     * product_id : 11
     * content_id : 63
     * point_id : 89
     * point_name : 偏旁、部首、笔画、笔顺
     */

    private String product_id;
    private String content_id;
    private String point_id;
    private String point_name;

    public static ParamsBean objectFromData(String str) {

        return new Gson().fromJson(str, ParamsBean.class);
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }
}

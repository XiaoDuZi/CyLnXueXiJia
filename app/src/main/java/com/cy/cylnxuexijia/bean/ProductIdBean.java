package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/9/8 0008.
 */

public class ProductIdBean {

    /**
     * product_id : 11
     * uid : 465
     * is_free : 1
     */

    public String product_id;
    private String uid;
    private String is_free;

    public static ProductIdBean objectFromData(String str) {

        return new Gson().fromJson(str, ProductIdBean.class);
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    @Override
    public String toString() {
        return "ProductIdBean{" +
                "product_id='" + product_id + '\'' +
                ", uid='" + uid + '\'' +
                ", is_free='" + is_free + '\'' +
                '}';
    }
}

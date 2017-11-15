package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class OrderBean {

    /**
     * user_id : 466
     * product_id : 82
     * buy_tips : 123
     * product_name : 一年级
     */

    private int user_id;
    private String product_id;
    private String buy_tips;
    private String product_name;

    public static OrderBean objectFromData(String str) {

        return new Gson().fromJson(str, OrderBean.class);
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBuy_tips() {
        return buy_tips;
    }

    public void setBuy_tips(String buy_tips) {
        this.buy_tips = buy_tips;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "user_id=" + user_id +
                ", product_id='" + product_id + '\'' +
                ", buy_tips='" + buy_tips + '\'' +
                ", product_name='" + product_name + '\'' +
                '}';
    }
}

package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class AddOrderInfoBean {

    /**
     * code : 0
     * message : 保存成功
     */

    private int code;
    private String message;

    public static AddOrderInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, AddOrderInfoBean.class);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AddOrderInfoBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

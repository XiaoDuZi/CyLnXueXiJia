package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class AddHostroyBean {

    /**
     * code : 0
     * message : 保存成功
     */

    private int code;
    private String message;

    public static AddHostroyBean objectFromData(String str) {

        return new Gson().fromJson(str, AddHostroyBean.class);
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
        return "AddHostroyBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

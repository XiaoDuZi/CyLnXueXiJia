package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class SmallVideoBean {

    /**
     * vodId : 4713298
     */

    private String vodId;

    public static SmallVideoBean objectFromData(String str) {

        return new Gson().fromJson(str, SmallVideoBean.class);
    }

    public String getVodId() {
        return vodId;
    }

    public void setVodId(String vodId) {
        this.vodId = vodId;
    }

    @Override
    public String toString() {
        return "SmallVideoBean{" +
                "vodId='" + vodId + '\'' +
                '}';
    }
}

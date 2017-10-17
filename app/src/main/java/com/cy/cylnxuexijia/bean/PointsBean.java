package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class PointsBean {
    /**
     * params : [{"product_id":"11","content_id":"63","point_id":"89","point_name":"偏旁、部首、笔画、笔顺"},{"product_id":"11","content_id":"63","point_id":"91","point_name":"字的音、形、义"},{"product_id":"11","content_id":"63","point_id":"103","point_name":"名著名篇导读"},{"product_id":"11","content_id":"63","point_id":"109","point_name":"情景交际"}]
     * point_id : 87
     * point_name : 拼音
     * videos : [{"is_free":"0","courseware_count":"12","video_id":"257","video_name":"声母、韵母的读音、形及写法"},{"is_free":"1","courseware_count":"5","video_id":"259","video_name":"整体音节的读音、形及写法"},{"is_free":"1","courseware_count":"12","video_id":"265","video_name":"声调和标调、拼音方法及规则"}]
     */

    private String params;
    private String point_id;
    private String point_name;
    private List<VideosBean> videos;

    public static PointsBean objectFromData(String str) {

        return new Gson().fromJson(str, PointsBean.class);
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "PointsBean{" +
                "params='" + params + '\'' +
                ", point_id='" + point_id + '\'' +
                ", point_name='" + point_name + '\'' +
                ", videos=" + videos +
                '}';
    }
}

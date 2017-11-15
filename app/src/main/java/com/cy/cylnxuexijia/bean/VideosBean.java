package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class VideosBean {

    /**
     * is_free : 0
     * courseware_count : 2
     * video_url : MOV5844dd95d9465a1ca894e6f8
     * video_id : 4219
     * video_name : 天花乱坠
     */

    private String is_free;
    private String courseware_count;
    private String video_url;
    private String video_id;
    private String video_name;

    public static VideosBean objectFromData(String str) {

        return new Gson().fromJson(str, VideosBean.class);
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getCourseware_count() {
        return courseware_count;
    }

    public void setCourseware_count(String courseware_count) {
        this.courseware_count = courseware_count;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    @Override
    public String toString() {
        return "VideosBean{" +
                "is_free='" + is_free + '\'' +
                ", courseware_count='" + courseware_count + '\'' +
                ", video_url='" + video_url + '\'' +
                ", video_id='" + video_id + '\'' +
                ", video_name='" + video_name + '\'' +
                '}';
    }
}

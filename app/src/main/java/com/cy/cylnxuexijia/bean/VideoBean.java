package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class VideoBean implements Serializable {

    /**
     * type : 1
     * user_id : 465
     * product_id : 11
     * content_id : 209
     * teacher_id :
     * platformId : 3
     * grade_id :
     * point_id : 87
     * video_id : 257
     * courseware_count : 12
     */

    private int type;
    private int user_id;
    private int product_id;
    private int content_id;
    private String teacher_id;
    private int platformId;
    private String grade_id;
    private int point_id;
    private int video_id;
    private int courseware_count;

    public static VideoBean objectFromData(String str) {

        return new Gson().fromJson(str, VideoBean.class);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public int getPoint_id() {
        return point_id;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getCourseware_count() {
        return courseware_count;
    }

    public void setCourseware_count(int courseware_count) {
        this.courseware_count = courseware_count;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "type=" + type +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", content_id=" + content_id +
                ", teacher_id='" + teacher_id + '\'' +
                ", platformId=" + platformId +
                ", grade_id='" + grade_id + '\'' +
                ", point_id=" + point_id +
                ", video_id=" + video_id +
                ", courseware_count=" + courseware_count +
                '}';
    }
}

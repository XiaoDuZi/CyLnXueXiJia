package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class VideoBean implements Serializable {
    /**
     * is_free : 0
     * user_id : 465
     * product_id : 66
     * content_id : 217
     * teacher_id :
     * platformId : 3
     * grade_id :
     * point_id : 1366
     * video_id : 4190
     * courseware_count : 1
     * video_url : MOV5844dd92d9465a1ca894e6c8
     * video_name :  回家过年
     */

    private String is_free;
    private String user_id;
    private String product_id;
    private String content_id;
    private String teacher_id;
    private String platformId;
    private String grade_id;
    private String point_id;
    private String video_id;
    private String courseware_count;
    private String video_url;
    private String video_name;

    public static VideoBean objectFromData(String str) {

        return new Gson().fromJson(str, VideoBean.class);
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

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
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

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "is_free='" + is_free + '\'' +
                ", user_id='" + user_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", content_id='" + content_id + '\'' +
                ", teacher_id='" + teacher_id + '\'' +
                ", platformId='" + platformId + '\'' +
                ", grade_id='" + grade_id + '\'' +
                ", point_id='" + point_id + '\'' +
                ", video_id='" + video_id + '\'' +
                ", courseware_count='" + courseware_count + '\'' +
                ", video_url='" + video_url + '\'' +
                ", video_name='" + video_name + '\'' +
                '}';
    }


    //    /**
//     * is_free : 1
//     * user_id : 466
//     * product_id : 11
//     * content_id : 209
//     * teacher_id :
//     * platformId : 3
//     * grade_id :
//     * point_id : 87
//     * video_id : 259
//     * courseware_count : 5
//     */

//    private String is_free;
//    private String user_id;
//    private String product_id;
//    private String content_id;
//    private String teacher_id;
//    private String platformId;
//    private String grade_id;
//    private String point_id;
//    private String video_id;
//    private String courseware_count;
//
//    public static VideoBean objectFromData(String str) {
//
//        return new Gson().fromJson(str, VideoBean.class);
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
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
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
//    public String getContent_id() {
//        return content_id;
//    }
//
//    public void setContent_id(String content_id) {
//        this.content_id = content_id;
//    }
//
//    public String getTeacher_id() {
//        return teacher_id;
//    }
//
//    public void setTeacher_id(String teacher_id) {
//        this.teacher_id = teacher_id;
//    }
//
//    public String getPlatformId() {
//        return platformId;
//    }
//
//    public void setPlatformId(String platformId) {
//        this.platformId = platformId;
//    }
//
//    public String getGrade_id() {
//        return grade_id;
//    }
//
//    public void setGrade_id(String grade_id) {
//        this.grade_id = grade_id;
//    }
//
//    public String getPoint_id() {
//        return point_id;
//    }
//
//    public void setPoint_id(String point_id) {
//        this.point_id = point_id;
//    }
//
//    public String getVideo_id() {
//        return video_id;
//    }
//
//    public void setVideo_id(String video_id) {
//        this.video_id = video_id;
//    }
//
//    public String getCourseware_count() {
//        return courseware_count;
//    }
//
//    public void setCourseware_count(String courseware_count) {
//        this.courseware_count = courseware_count;
//    }
//
//    @Override
//    public String toString() {
//        return "VideoBean{" +
//                "is_free='" + is_free + '\'' +
//                ", user_id='" + user_id + '\'' +
//                ", product_id='" + product_id + '\'' +
//                ", content_id='" + content_id + '\'' +
//                ", teacher_id='" + teacher_id + '\'' +
//                ", platformId='" + platformId + '\'' +
//                ", grade_id='" + grade_id + '\'' +
//                ", point_id='" + point_id + '\'' +
//                ", video_id='" + video_id + '\'' +
//                ", courseware_count='" + courseware_count + '\'' +
//                '}';
//    }
}

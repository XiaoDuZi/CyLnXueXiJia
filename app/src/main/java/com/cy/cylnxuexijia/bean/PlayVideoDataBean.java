package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class PlayVideoDataBean {

    /**
     * code : 200
     * data : {"video_status":"1","is_free":"0","courseware_count":"2","video_url":"MOV5861f20cd9461c0f2387f838","teacher_id":"256","xued_id":"90","grade_id":"94","subject_id":"222","term":"1","point_id":"1380","video_id":"4226","video_name":"加法1"}
     */

    private int code;
    private DataBean data;

    public static PlayVideoDataBean objectFromData(String str) {

        return new Gson().fromJson(str, PlayVideoDataBean.class);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * video_status : 1
         * is_free : 0
         * courseware_count : 2
         * video_url : MOV5861f20cd9461c0f2387f838
         * teacher_id : 256
         * xued_id : 90
         * grade_id : 94
         * subject_id : 222
         * term : 1
         * point_id : 1380
         * video_id : 4226
         * video_name : 加法1
         */

        private String video_status;
        private String is_free;
        private String courseware_count;
        private String video_url;
        private String teacher_id;
        private String xued_id;
        private String grade_id;
        private String subject_id;
        private String term;
        private String point_id;
        private String video_id;
        private String video_name;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getVideo_status() {
            return video_status;
        }

        public void setVideo_status(String video_status) {
            this.video_status = video_status;
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

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getXued_id() {
            return xued_id;
        }

        public void setXued_id(String xued_id) {
            this.xued_id = xued_id;
        }

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
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

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }
    }
}

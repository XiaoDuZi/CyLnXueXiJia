package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class SpecialPlayDataBean {

    /**
     * code : 200
     * data : {"product_id":"79","product_type":"3","flag":"false","content_id":"24","is_free":"1","special_id":"24","special_name":"社会","params":"{\"mb_id\":\"3\",\"mb_params\":{\"backgroundURL\":\"https:\\/\\/ss0.bdstatic.com\\/5aV1bjqh_Q23odCf\\/static\\/superman\\/img\\/logo\\/bd_logo1_31bdc765.png\"},\"rkt_type\":\"0\",\"rkt\":[]}","grades":[{"grade_id":"97","grade_name":"高一","subjects":[{"subject_id":"199","subject_name":"高一数学","subject_image":"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png","terms":[{"id":1,"term":"上学期","points":[{"point_id":"1379","point_name":"社会","videos":[{"is_free":"0","courseware_count":"1","video_url":"MOV584523dad9465a1ca8951b59","video_id":"4224","video_name":" 怪兽初现"},{"is_free":"1","courseware_count":"1","video_url":"MOV584523e0d9465a1ca8951b62","video_id":"4225","video_name":"巨龙时代"}]}]}]}]}]}
     */

    private int code;
    private DataBean data;

    public static SpecialPlayDataBean objectFromData(String str) {

        return new Gson().fromJson(str, SpecialPlayDataBean.class);
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
         * product_id : 79
         * product_type : 3
         * flag : false
         * content_id : 24
         * is_free : 1
         * special_id : 24
         * special_name : 社会
         * params : {"mb_id":"3","mb_params":{"backgroundURL":"https:\/\/ss0.bdstatic.com\/5aV1bjqh_Q23odCf\/static\/superman\/img\/logo\/bd_logo1_31bdc765.png"},"rkt_type":"0","rkt":[]}
         * grades : [{"grade_id":"97","grade_name":"高一","subjects":[{"subject_id":"199","subject_name":"高一数学","subject_image":"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png","terms":[{"id":1,"term":"上学期","points":[{"point_id":"1379","point_name":"社会","videos":[{"is_free":"0","courseware_count":"1","video_url":"MOV584523dad9465a1ca8951b59","video_id":"4224","video_name":" 怪兽初现"},{"is_free":"1","courseware_count":"1","video_url":"MOV584523e0d9465a1ca8951b62","video_id":"4225","video_name":"巨龙时代"}]}]}]}]}]
         */

        private String product_id;
        private String product_type;
        private String flag;
        private String content_id;
        private String is_free;
        private String special_id;
        private String special_name;
        private String params;
        private List<GradesBean> grades;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getIs_free() {
            return is_free;
        }

        public void setIs_free(String is_free) {
            this.is_free = is_free;
        }

        public String getSpecial_id() {
            return special_id;
        }

        public void setSpecial_id(String special_id) {
            this.special_id = special_id;
        }

        public String getSpecial_name() {
            return special_name;
        }

        public void setSpecial_name(String special_name) {
            this.special_name = special_name;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public List<GradesBean> getGrades() {
            return grades;
        }

        public void setGrades(List<GradesBean> grades) {
            this.grades = grades;
        }

        public static class GradesBean {
            /**
             * grade_id : 97
             * grade_name : 高一
             * subjects : [{"subject_id":"199","subject_name":"高一数学","subject_image":"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png","terms":[{"id":1,"term":"上学期","points":[{"point_id":"1379","point_name":"社会","videos":[{"is_free":"0","courseware_count":"1","video_url":"MOV584523dad9465a1ca8951b59","video_id":"4224","video_name":" 怪兽初现"},{"is_free":"1","courseware_count":"1","video_url":"MOV584523e0d9465a1ca8951b62","video_id":"4225","video_name":"巨龙时代"}]}]}]}]
             */

            private String grade_id;
            private String grade_name;
            private List<SubjectsBean> subjects;

            public static GradesBean objectFromData(String str) {

                return new Gson().fromJson(str, GradesBean.class);
            }

            public String getGrade_id() {
                return grade_id;
            }

            public void setGrade_id(String grade_id) {
                this.grade_id = grade_id;
            }

            public String getGrade_name() {
                return grade_name;
            }

            public void setGrade_name(String grade_name) {
                this.grade_name = grade_name;
            }

            public List<SubjectsBean> getSubjects() {
                return subjects;
            }

            public void setSubjects(List<SubjectsBean> subjects) {
                this.subjects = subjects;
            }

            public static class SubjectsBean {
                /**
                 * subject_id : 199
                 * subject_name : 高一数学
                 * subject_image : https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png
                 * terms : [{"id":1,"term":"上学期","points":[{"point_id":"1379","point_name":"社会","videos":[{"is_free":"0","courseware_count":"1","video_url":"MOV584523dad9465a1ca8951b59","video_id":"4224","video_name":" 怪兽初现"},{"is_free":"1","courseware_count":"1","video_url":"MOV584523e0d9465a1ca8951b62","video_id":"4225","video_name":"巨龙时代"}]}]}]
                 */

                private String subject_id;
                private String subject_name;
                private String subject_image;
                private List<TermsBean> terms;

                public static SubjectsBean objectFromData(String str) {

                    return new Gson().fromJson(str, SubjectsBean.class);
                }

                public String getSubject_id() {
                    return subject_id;
                }

                public void setSubject_id(String subject_id) {
                    this.subject_id = subject_id;
                }

                public String getSubject_name() {
                    return subject_name;
                }

                public void setSubject_name(String subject_name) {
                    this.subject_name = subject_name;
                }

                public String getSubject_image() {
                    return subject_image;
                }

                public void setSubject_image(String subject_image) {
                    this.subject_image = subject_image;
                }

                public List<TermsBean> getTerms() {
                    return terms;
                }

                public void setTerms(List<TermsBean> terms) {
                    this.terms = terms;
                }

                public static class TermsBean {
                    /**
                     * id : 1
                     * term : 上学期
                     * points : [{"point_id":"1379","point_name":"社会","videos":[{"is_free":"0","courseware_count":"1","video_url":"MOV584523dad9465a1ca8951b59","video_id":"4224","video_name":" 怪兽初现"},{"is_free":"1","courseware_count":"1","video_url":"MOV584523e0d9465a1ca8951b62","video_id":"4225","video_name":"巨龙时代"}]}]
                     */

                    private int id;
                    private String term;
                    private List<PointsBean> points;

                    public static TermsBean objectFromData(String str) {

                        return new Gson().fromJson(str, TermsBean.class);
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public List<PointsBean> getPoints() {
                        return points;
                    }

                    public void setPoints(List<PointsBean> points) {
                        this.points = points;
                    }

                    public static class PointsBean {
                        /**
                         * point_id : 1379
                         * point_name : 社会
                         * videos : [{"is_free":"0","courseware_count":"1","video_url":"MOV584523dad9465a1ca8951b59","video_id":"4224","video_name":" 怪兽初现"},{"is_free":"1","courseware_count":"1","video_url":"MOV584523e0d9465a1ca8951b62","video_id":"4225","video_name":"巨龙时代"}]
                         */

                        private String point_id;
                        private String point_name;
                        private List<VideosBean> videos;

                        public static PointsBean objectFromData(String str) {

                            return new Gson().fromJson(str, PointsBean.class);
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
                    }
                }
            }
        }
    }
}

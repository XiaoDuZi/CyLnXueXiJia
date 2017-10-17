package com.cy.cylnxuexijia.comment;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CommentInfo {

    private static final String TAG = "CommentInfo";


    public static String WEB_INDEX="http://120.76.221.222:90/";

    //产品，内容鉴权加密串，Key值
    public static String PRODUCT_PROGRAM_KEY = "spauth";

    //鉴权URL
    //测试
//    public static String AUTHENTICATION_URL = "http://59.46.18.48:99/authbilling/";
    //现网
    public static String AUTHENTICATION_URL = "http://3a.99tv.com.cn:99/authbilling/";

    //sp ID：
    public static String SpId = "CQGH";


    /**
     * 播放视频请求头部
     * 生产环境接口IP是59.46.18.5
     * 测试环境接口IP是59.46.18.25
     */
    public static String VIDEO_URL = "http://59.46.18.25:99/";

    public static String BASEURL = VIDEO_URL + "spplayurl/";

    //播放接口链接加密串，Key值
    public static String PLAY_KEY = "besto";

    //视频播放类型 1：直播；2：回看；4：点播（默认为点播）
    public static int Type = 4;

    //PPT Url
    public static String PPT_URL=WEB_INDEX+"/courseware.html?video_id=%s&size=%s";

    //小学
    public static final String WEB_PARAMS_PRIMARY = WEB_INDEX + "/primaryterm.html?product_id=%s&teacherId=%s&content_id=%s&point_id=%s&uid=%s";
    //教师
    public static final String WEB_PARAMS_TEACHER = WEB_INDEX + "/knowledge.html?product_id=%s&teacherId=%s&content_id=%s&point_id=%s&uid=%s";
    //初中
    public static final String WEB_PARAMS_JUNITOR = WEB_INDEX + "/juniorterm.html?product_id=%s&teacherId=%s&content_id=%s&point_id=%s&uid=%s";
    //高中
    public static final String WEB_PARAMS_SENIOR = WEB_INDEX + "/seniorterm.html?product_id=%s&teacherId=%s&content_id=%s&point_id=%s&uid=%s";

}

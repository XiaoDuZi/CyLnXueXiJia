package com.cy.cylnxuexijia.interfaces;

import com.cy.cylnxuexijia.bean.PlayVideoDataBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public interface PlayVideoDataService {
    @GET("uflowertv_api/resources/production/get/video/url/{platformId}/{userId}/{video_id}/{product_id}/{content_id}")
    Call<PlayVideoDataBean> getPlayVideoData(@Path("platformId") String platformId,
                                             @Path("userId") String userId,
                                             @Path("video_id") String video_id,
                                             @Path("product_id") String product_id,
                                             @Path("content_id") String content_id);
}

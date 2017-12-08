package com.cy.cylnxuexijia.interfaces;

import com.cy.cylnxuexijia.bean.AddHostroyBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public interface AddPlayHostoryService {
    @GET("uflowertv_api/resources/production/save/play/log/{userId}/{video_id}/{product_id}/{content_id}/{play_duration}")
    Call<AddHostroyBean> addHostory(@Path("userId") String userId,
                                    @Path("video_id") String video_id,
                                    @Path("product_id") String product_id,
                                    @Path("content_id") String content_id,
                                    @Path("play_duration") Long play_duration);
}

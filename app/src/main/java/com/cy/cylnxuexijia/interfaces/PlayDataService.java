package com.cy.cylnxuexijia.interfaces;

import com.cy.cylnxuexijia.bean.PlayDataBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/10/10 0010.
 * http://120.76.221.222:90/uflowertv_api/resources/production/syn/detail/458/11/209
 */

public interface PlayDataService {
    @GET("uflowertv_api/resources/production/syn/detail/{userId}/{productId}/{contentId}")
    Call<PlayDataBean> getPlayData(@Path("userId") String userId,@Path("productId") String productId,
                                   @Path("contentId") String contentId);
}

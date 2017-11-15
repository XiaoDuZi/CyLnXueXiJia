package com.cy.cylnxuexijia.interfaces;

import com.cy.cylnxuexijia.bean.SpecialPlayDataBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/10/10 0010.
 * http://120.76.221.222:90/uflowertv_api/resources/production/special/detail/458/84
 * 专题
 */

public interface SpecialPlayDataService {
    @GET("uflowertv_api/resources/production/special/detail/{userId}/{productId}")
    Call<SpecialPlayDataBean> getPlayData(@Path("userId") String userId, @Path("productId") String productId);
}

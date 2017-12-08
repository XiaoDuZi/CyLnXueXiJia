package com.cy.cylnxuexijia.interfaces;

import com.cy.cylnxuexijia.bean.AddOrderInfoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/11/15 0015.
 * http://ip:port/uflowertv_api/orderResult/saveLocalOrderResult?
 * card={card}&spordernum={spordernum}&ordernum={ordernum}
 * &product_id={product_id}&price={price}&createtime={createtime}
 * &type={type}&isb={isb}
 */

public interface AddOrderInfoService {
    @GET("uflowertv_api/orderResult/saveLocalOrderResult")
    Call<AddOrderInfoBean> addOrderInfo(@Query("card") String card, @Query("spordernum") String sporderNum,
                                        @Query("ordernum") String orderNum, @Query("product_id") String product_id,
                                        @Query("price") String price, @Query("createtime") String createTime,
                                        @Query("type") int type, @Query("isb") int isb);
}

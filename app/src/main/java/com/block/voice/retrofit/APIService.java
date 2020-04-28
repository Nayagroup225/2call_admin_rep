package com.block.voice.retrofit;

import com.block.voice.model.BaseResponse;
import com.block.voice.model.UserInfoRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface APIService {

    @FormUrlEncoded
    @POST("getUsersData")
    Call<UserInfoRes> getUsersData(@Field("identity") String adminIdentity);

    @FormUrlEncoded
    @POST("loginAdmin")
    Call<BaseResponse> adminLogin(@Field("admin_id") String adminId,
                                  @Field("password") String password,
                                  @Field("identity") String identify,
                                  @Field("token") String deviceToken,
                                  @Field("imei") String imei);

}

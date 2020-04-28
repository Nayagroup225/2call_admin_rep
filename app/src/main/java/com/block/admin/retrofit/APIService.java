package com.block.admin.retrofit;

import com.block.admin.model.BaseRes;
import com.block.admin.model.NotificationInfoRes;
import com.block.admin.model.UserListRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface APIService {
    @FormUrlEncoded
    @POST("getDeviceList")
    Call<UserListRes> getList(@Field("identity") String identify);

    @FormUrlEncoded
    @POST("setClientState")
    Call<BaseRes> setState(@Field("device_id") String deviceId,
                           @Field("state") String state,
                           @Field("block_number") String blockNumber,
                           @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("callBlockAdminLogin")
    Call<BaseRes> adminLogin(@Field("user_name") String adminId,
                                  @Field("password") String password,
                                  @Field("identity") String identify,
                             @Field("imei") String imei);

    @FormUrlEncoded
    @POST("getNotification")
    Call<NotificationInfoRes> notificationList(@Field("page") String pgae);

}
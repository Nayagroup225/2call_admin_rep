package com.block.voice.retrofit;

import com.block.voice.model.BaseResponse;
import com.block.voice.model.UserInfoRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {
    private static APIService service;

    public static ApiCall getInstance() {
        if (service == null) {
            service = RestClient.getClient();
        }
        return new ApiCall();
    }

    public void getUsersData(String adminIdentity, IApiCallback<UserInfoRes> iApiCallback) {
        Call<UserInfoRes> call = service.getUsersData(adminIdentity);
        call.enqueue(new Callback<UserInfoRes>() {
            @Override
            public void onResponse(Call<UserInfoRes> call, Response<UserInfoRes> response) {
                iApiCallback.onSuccess("user_data", response);
            }

            @Override
            public void onFailure(Call<UserInfoRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void adminLogin(String deviceId, String phoneNumber, String identity, String deviceToken, String imei, IApiCallback<BaseResponse> iApiCallback) {
        Call<BaseResponse> call = service.adminLogin(deviceId, phoneNumber, identity, deviceToken, imei);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                iApiCallback.onSuccess("check", response);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }
}

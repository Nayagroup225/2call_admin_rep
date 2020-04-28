package com.block.admin.retrofit;

import com.block.admin.model.BaseRes;
import com.block.admin.model.NotificationInfoRes;
import com.block.admin.model.UserListRes;

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

    public void getList(String identity, final IApiCallback<UserListRes> iApiCallback) {
        Call<UserListRes> call = service.getList(identity);
        call.enqueue(new Callback<UserListRes>() {
            @Override
            public void onResponse(Call<UserListRes> call, Response<UserListRes> response) {
                iApiCallback.onSuccess("list", response);
            }

            @Override
            public void onFailure(Call<UserListRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void setState(String deviceId, String state, String blockNumber, String nickname, final IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.setState(deviceId, state, blockNumber, nickname);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("set_state", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void adminLogin(String deviceId, String phoneNumber, String identity, String imei, IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.adminLogin(deviceId, phoneNumber, imei, identity);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("check", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void notificationList(String page, final IApiCallback<NotificationInfoRes> iApiCallback) {
        Call<NotificationInfoRes> call = service.notificationList(page);
        call.enqueue(new Callback<NotificationInfoRes>() {
            @Override
            public void onResponse(Call<NotificationInfoRes> call, Response<NotificationInfoRes> response) {
                iApiCallback.onSuccess("notification_list", response);
            }

            @Override
            public void onFailure(Call<NotificationInfoRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

}
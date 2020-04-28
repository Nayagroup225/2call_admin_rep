package com.block.voice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfoRes {

    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("errorMsg")
    @Expose
    private String errorMsg;
    @SerializedName("data")
    @Expose
    private List<UserInfo> data = null;


    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public List<UserInfo> getData() {
        return data;
    }
}

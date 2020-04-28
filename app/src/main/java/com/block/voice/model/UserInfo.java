package com.block.voice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("call_id")
    @Expose
    private String callId;
    @SerializedName("state")//reward
    @Expose
    private String state;


    public String getCallId() {
        return callId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getState() {
        return state;
    }

}

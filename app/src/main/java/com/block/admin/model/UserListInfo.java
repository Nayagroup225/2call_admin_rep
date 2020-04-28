package com.block.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserListInfo {
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("nick_name")
    @Expose
    private String nickName;

    @SerializedName("longi")
    @Expose
    private String longi;

    @SerializedName("lati")
    @Expose
    private String lati;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("block_number")
    @Expose
    private String blockNumber;

    @SerializedName("last_call")
    @Expose
    private String lastCall;

    public String getDeviceId(){
        return deviceId;
    }

    public String getState() {
        return state;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getLongi() {
        return longi;
    }

    public String getLati(){
        return lati;
    }

    public String getNickName() { return nickName; }

    public String getAddress() {
        return address;
    }

    public String getBlockNumber(){
        return blockNumber;
    }

    public String getLastCall() { return lastCall; }

}

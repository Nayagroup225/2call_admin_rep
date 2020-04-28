package com.block.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NotificationInfoRes extends BaseRes {

    @SerializedName("data")
    @Expose
    private List<NotificationInfo> data = null;


    public List<NotificationInfo> getData() {
        return data;
    }

    public void setData(List<NotificationInfo> data) {
        this.data = data;
    }
}

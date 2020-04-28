package com.block.voice.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.voice.R;
import com.block.voice.model.UserInfo;

public class WalletHolder extends RecyclerView.ViewHolder {
    public WalletHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setData(UserInfo userInfo) {
        ((TextView)itemView.findViewById(R.id.tv_call_id)).setText(userInfo.getCallId());
        ((TextView)itemView.findViewById(R.id.tv_device_id)).setText(userInfo.getDeviceId());
        ((TextView)itemView.findViewById(R.id.tv_phone_number)).setText(userInfo.getPhoneNumber());
        ((TextView)itemView.findViewById(R.id.tv_state)).setText(userInfo.getCallId().equals("1")? "Online" : "Offline");
    }
}

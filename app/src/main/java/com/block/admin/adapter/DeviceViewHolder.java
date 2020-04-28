package com.block.admin.adapter;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.block.admin.AdminActivity;
import com.block.admin.R;
import com.block.admin.model.UserListInfo;

import lib.kingja.switchbutton.SwitchMultiButton;

public class DeviceViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivLike;
    public TextView deviceId, locationDistance, lastCall, phoneNumber;
    EditText blockNumber, nickname;
    SwitchMultiButton switchBlock;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        deviceId=itemView.findViewById(R.id.tv_device_id);
        nickname = itemView.findViewById(R.id.et_nickname);
//        locationDistance = itemView.findViewById(R.id.tv_distance);
        lastCall = itemView.findViewById(R.id.tv_last_call);

        switchBlock = itemView.findViewById(R.id.switch_lock);
        blockNumber = itemView.findViewById(R.id.et_block_number);

    }

    public void setData(UserListInfo data){

        deviceId.setText(data.getDeviceId());
        nickname.setText(data.getNickName());
//        locationDistance.setText(data.getLati()+", "+data.getLongi());
        lastCall.setText(data.getLastCall());
        blockNumber.setText(data.getBlockNumber());
        switchBlock.setOnSwitchListener(null);
        if(data.getState().equals("1")){
            switchBlock.setSelectedTab(0);//lock
        }else{
            switchBlock.setSelectedTab(1);// unlock
        }
    }
}

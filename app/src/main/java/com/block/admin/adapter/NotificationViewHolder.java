package com.block.admin.adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.admin.R;
import com.block.admin.model.NotificationInfo;
import com.block.admin.model.UserListInfo;

import lib.kingja.switchbutton.SwitchMultiButton;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView message, date;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        message=itemView.findViewById(R.id.tv_message);
        date = itemView.findViewById(R.id.tv_date);
    }

    public void setData(NotificationInfo data){
        date.setText(data.getCreatedAt());
        message.setText(data.getMsg());
    }
}

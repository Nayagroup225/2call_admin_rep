package com.block.admin.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.admin.BaseRecycler;
import com.block.admin.R;
import com.block.admin.model.NotificationInfo;
import com.block.admin.model.UserListInfo;
import com.block.admin.preferences.IRecyclerClickListener;

import lib.kingja.switchbutton.SwitchMultiButton;

public class NotificationRecyclerAdapter extends BaseRecycler<NotificationInfo> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_notification, parent, false));
    }

    public NotificationRecyclerAdapter() {
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((NotificationViewHolder)holder).setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

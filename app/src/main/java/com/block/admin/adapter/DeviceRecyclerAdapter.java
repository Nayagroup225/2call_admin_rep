package com.block.admin.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.admin.BaseRecycler;
import com.block.admin.R;
import com.block.admin.model.UserListInfo;
import com.block.admin.preferences.IRecyclerClickListener;

import lib.kingja.switchbutton.SwitchMultiButton;

public class DeviceRecyclerAdapter extends BaseRecycler<UserListInfo> {
    private IRecyclerClickListener listener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_offer, parent, false));
    }

    public DeviceRecyclerAdapter(IRecyclerClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((DeviceViewHolder)holder).setData(list.get(position));
        SwitchMultiButton switchBlock = holder.itemView.findViewById(R.id.switch_lock);
        switchBlock.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {

                listener.onRecyclerClick(holder.getAdapterPosition(), tabText, ((EditText)holder.itemView.findViewById(R.id.et_block_number)).getText(), ((EditText)holder.itemView.findViewById(R.id.et_nickname)).getText() );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

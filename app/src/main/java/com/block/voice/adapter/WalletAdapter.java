package com.block.voice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.voice.R;
import com.block.voice.model.UserInfo;
import com.block.voice.preferences.IRecyclerClickListener;

public class WalletAdapter extends BaseRecycler {

    IRecyclerClickListener mRecyclerListener;

    public WalletAdapter(IRecyclerClickListener listener){
        mRecyclerListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WalletHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_wallet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((WalletHolder) holder).setData((UserInfo) list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerListener.onRecyclerClick(holder.getAdapterPosition(),((UserInfo) list.get(position)).getCallId(),"user_click");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

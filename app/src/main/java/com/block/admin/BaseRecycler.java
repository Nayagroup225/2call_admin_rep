package com.block.admin;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecycler<T> extends RecyclerView.Adapter {
    protected List<T> list = new ArrayList<>();

    public void addItem(T t) {
        list.add(t);
        notifyDataSetChanged();
    }

    public void addAllItem(List<T> t) {
        list.addAll(t);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public List<T> getAllItem() {
        return list;
    }

    public T getItem(int pos) {
        return list.get(pos);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

}

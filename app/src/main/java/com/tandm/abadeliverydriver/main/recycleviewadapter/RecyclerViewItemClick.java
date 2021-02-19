package com.tandm.abadeliverydriver.main.recycleviewadapter;

public interface RecyclerViewItemClick<T> {
    void onClick(T item, int position, int number);
    void onLongClick(T item, int position, int number);
}

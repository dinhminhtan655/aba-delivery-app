package com.tandm.abadeliverydriver.main.recycleviewadapter;

public interface RecyclerViewItemListener<T> {

    void onClick(int position, String strTitle, int iProblem);
    void onLongClick(int position);

}

package com.tandm.abadeliverydriver.main.recycleviewadapter;

public interface RecyclerViewHisDriverListener<T> {

    void onClick(int position, String strATM_SHIPMENT_ID, String timeCompleteted, String timeStart);
    void onLongClick();
}

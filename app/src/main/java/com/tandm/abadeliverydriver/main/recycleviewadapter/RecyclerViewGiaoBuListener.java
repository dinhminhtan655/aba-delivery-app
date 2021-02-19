package com.tandm.abadeliverydriver.main.recycleviewadapter;

public interface RecyclerViewGiaoBuListener {

    void onClick(int position, String store_Code, String store_Name, String khachHang, String address,String ATMSHipmentID, int i);
    void onLongClick(int position);
}

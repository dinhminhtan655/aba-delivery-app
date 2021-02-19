package com.tandm.abadeliverydriver.main.recycleviewadapter;

public interface RecyclerViewItemListener2<T>{

    void onClick(int position, String strStoreCode,String strStoreName, String strStoreAddress,String strKhachHang, String stratM_SHIPMENT_ID, String orderrelease_id, String packaged_Item_XID,int totalCarton,String totalWeight, int key);
    void onLongClick(int position);
}

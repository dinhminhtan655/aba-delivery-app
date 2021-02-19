package com.tandm.abadeliverydriver.main.recycleviewadapter;

public interface RecyclerViewTripListener<T> {

    void onClick(int position, String atm_shipment_id, String trucktype, String deliverydate, String routeno, String driver_gid, int key);
    void onLongClick();
}

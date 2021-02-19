package com.tandm.abadeliverydriver.main.recycleviewadapter;

public interface RecyclerViewUpdateGiaoBuListener {

    void onClick(int position,String strRowID, String strItemCode, String strItemName, String strSoBich, String strActual , String strGiaoBu, int i);
    void onLongClick(int position);
}

package com.tandm.abadeliverydriver.main.home.fragmenthome;

public class Store2 {
    
    public String shipmenT_GID;
    public String stoP_TYPE;
    public String store_Code;
    public String store_Name;
    public String addresS_LINE;
    public String atM_SHIPMENT_ID;
    public String planneD_ARRIVAL;
    public boolean mobileHub;
    public boolean isCompleted;
    public String khachHang;
    public String delivery_Date;
    public int totalCarton;
    public boolean daToi;
    public double latToi;
    public double lngToi;
    public String gioToi;
    public String customerClientPhone;
    public String totalWeight;
    public String orderreleasE_ID;
    public String packaged_Item_XID;
    public int pallet_ID;
    public String address;


    public int getTotalCarton() {
        return totalCarton;
    }

    public void setTotalCarton(int totalCarton) {
        this.totalCarton = totalCarton;
    }

    public String fusionString(){
        return store_Code + " (" + khachHang + ")";
    }
    
}

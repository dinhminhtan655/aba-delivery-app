package com.tandm.abadeliverydriver.main.nhanhang.model;

import com.google.gson.annotations.SerializedName;

public class Item2 {
    @SerializedName("rowId")
    public String rowId;
    @SerializedName("store_Name")
    public String store_Name;
    @SerializedName("xdocK_Doc_Entry")
    public String xdocK_Doc_Entry;
    @SerializedName("route_ID")
    public String route_ID;
    @SerializedName("store_Code")
    public String store_Code;
    @SerializedName("delivery_Date")
    public String delivery_Date;
    @SerializedName("item_Code")
    public String item_Code;
    @SerializedName("item_Name")
    public String item_Name;
    @SerializedName("soBich")
    public String soBich;
    @SerializedName("div_Unit")
    public String div_Unit;
    @SerializedName("actual_Received")
    public String actual_Received;
    @SerializedName("notes")
    public String notes;
    @SerializedName("modified")
    public String modified;
    @SerializedName("modifiedBy")
    public String modifiedBy;
    @SerializedName("item_event_id")
    public String item_event_id;
    @SerializedName("khachHang")
    public String khachHang;

    public String LAT;

    public String LNG;

    public String CHNhanDum;

    public String LyDoNhanDum;

    public String ATM_SHIPMENT_ID;

    public String orderrelease_id;

    public String Packaged_Item_XID;

    public String Thieu;

    public String Thua;

    public String TraVe;


    public Item2(String store_Name, String xdocK_Doc_Entry, String route_ID, String store_Code, String delivery_Date, String item_Code, String item_Name, String soBich, String div_Unit, String actual_Received, String notes, String modified, String modifiedBy, String item_event_id, String khachHang, String LAT, String LNG, String CHNhanDum, String LyDoNhanDum, String ATM_SHIPMENT_ID, String orderrelease_id, String Packaged_Item_XID, String Thieu, String Thua, String TraVe) {
        this.store_Name = store_Name;
        this.xdocK_Doc_Entry = xdocK_Doc_Entry;
        this.route_ID = route_ID;
        this.store_Code = store_Code;
        this.delivery_Date = delivery_Date;
        this.item_Code = item_Code;
        this.item_Name = item_Name;
        this.soBich = soBich;
        this.div_Unit = div_Unit;
        this.actual_Received = actual_Received;
        this.notes = notes;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.item_event_id = item_event_id;
        this.khachHang = khachHang;
        this.LAT = LAT;
        this.LNG = LNG;
        this.CHNhanDum = CHNhanDum;
        this.LyDoNhanDum = LyDoNhanDum;
        this.ATM_SHIPMENT_ID = ATM_SHIPMENT_ID;
        this.orderrelease_id = orderrelease_id;
        this.Packaged_Item_XID = Packaged_Item_XID;
        this.Thieu = Thieu;
        this.Thua = Thua;
        this.TraVe = TraVe;
    }

    public Item2(String rowId, String store_Name, String xdocK_Doc_Entry, String route_ID, String store_Code, String delivery_Date, String item_Code, String item_Name, String soBich, String div_Unit, String actual_Received, String notes, String modified, String modifiedBy, String item_event_id, String khachHang, String LAT, String LNG, String CHNhanDum, String LyDoNhanDum, String ATM_SHIPMENT_ID, String orderrelease_id, String Packaged_Item_XID, String Thieu, String Thua, String TraVe) {
        this.rowId = rowId;
        this.store_Name = store_Name;
        this.xdocK_Doc_Entry = xdocK_Doc_Entry;
        this.route_ID = route_ID;
        this.store_Code = store_Code;
        this.delivery_Date = delivery_Date;
        this.item_Code = item_Code;
        this.item_Name = item_Name;
        this.soBich = soBich;
        this.div_Unit = div_Unit;
        this.actual_Received = actual_Received;
        this.notes = notes;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.item_event_id = item_event_id;
        this.khachHang = khachHang;
        this.LAT = LAT;
        this.LNG = LNG;
        this.CHNhanDum = CHNhanDum;
        this.LyDoNhanDum = LyDoNhanDum;
        this.ATM_SHIPMENT_ID = ATM_SHIPMENT_ID;
        this.orderrelease_id = orderrelease_id;
        this.Packaged_Item_XID = Packaged_Item_XID;
        this.Thieu = Thieu;
        this.Thua = Thua;
        this.TraVe = TraVe;
    }
}

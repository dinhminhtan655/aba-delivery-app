package com.tandm.abadeliverydriver.main.vcm;

public class ABA_TrayBookingHistory {

    private String ID;
    private String ATMShipmentID;
    private String StoreID;
    private String ATM_OrderreleaseID;
    private int TrayDelivering;
    private int TrayReceiving;
    private String TrayName;
    private String DeliveredBy;
    private String DeliveredAt;
    private String LatDelivered;
    private String LngDelivered;
    private String UpdatedBy;
    private String UpdatedAt;
    private String CustomerID;
    private String PackageItemID;

    public ABA_TrayBookingHistory(String ATMShipmentID, String storeID, String ATM_OrderreleaseID, int trayDelivering, int trayReceiving, String trayName, String deliveredBy, String latDelivered, String lngDelivered, String customerID, String packageItemID) {
        this.ATMShipmentID = ATMShipmentID;
        StoreID = storeID;
        this.ATM_OrderreleaseID = ATM_OrderreleaseID;
        TrayDelivering = trayDelivering;
        TrayReceiving = trayReceiving;
        TrayName = trayName;
        DeliveredBy = deliveredBy;
        LatDelivered = latDelivered;
        LngDelivered = lngDelivered;
        CustomerID = customerID;
        PackageItemID = packageItemID;
    }
}

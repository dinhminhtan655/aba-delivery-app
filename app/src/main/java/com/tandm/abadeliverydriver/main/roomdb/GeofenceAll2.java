package com.tandm.abadeliverydriver.main.roomdb;

public class GeofenceAll2 {

    private String ATMOrderReleaseID;

    private String ATMShipmentID;

    private String Lat;

    private String Lng;

    private String ArrivalTime;

    private String LeavedLAT;

    private String LeavedLNG;

    private String LeavedTime;

    public GeofenceAll2(String ATMOrderReleaseID, String ATMShipmentID, String lat, String lng, String arrivalTime, String leavedLAT, String leavedLNG, String leavedTime) {
        this.ATMOrderReleaseID = ATMOrderReleaseID;
        this.ATMShipmentID = ATMShipmentID;
        Lat = lat;
        Lng = lng;
        ArrivalTime = arrivalTime;
        LeavedLAT = leavedLAT;
        LeavedLNG = leavedLNG;
        LeavedTime = leavedTime;
    }
}

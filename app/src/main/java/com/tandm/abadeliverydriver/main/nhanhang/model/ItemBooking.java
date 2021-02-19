package com.tandm.abadeliverydriver.main.nhanhang.model;

public class ItemBooking {
    private String ID;
    private int ActualReceived;
    private double ActualWeightReceived;
    private String Notes;
    private String UpdatedByDeliverer;
    private String LatDelivered;
    private String LngDelivered;
    private String HelpingStore;
    private String HelpingReason;
    private int Incompliance;
    private int Residual;
    private int Returnee;
    private String ATMShipmentID;


    public ItemBooking(String ID, int actualReceived, double actualWeightReceived, String notes, String updatedByDeliverer, String latDelivered, String lngDelivered, String helpingStore, String helpingReason, int incompliance, int residual, int returnee, String ATMShipmentID) {
        this.ID = ID;
        ActualReceived = actualReceived;
        ActualWeightReceived = actualWeightReceived;
        Notes = notes;
        UpdatedByDeliverer = updatedByDeliverer;
        LatDelivered = latDelivered;
        LngDelivered = lngDelivered;
        HelpingStore = helpingStore;
        HelpingReason = helpingReason;
        Incompliance = incompliance;
        Residual = residual;
        Returnee = returnee;
        this.ATMShipmentID = ATMShipmentID;
    }
}

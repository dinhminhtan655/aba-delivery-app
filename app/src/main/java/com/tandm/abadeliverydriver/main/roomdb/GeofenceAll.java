package com.tandm.abadeliverydriver.main.roomdb;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "geofenceall")
public class GeofenceAll {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String atmOrderrelease;

    private String atmShipmentId;

    private String latArrived;

    private String lngArrived;

    private String timeArrived;

    private String latLeft;

    private String lngLeft;

    private String timeLeft;


    public GeofenceAll() {
    }

    public GeofenceAll(String atmOrderrelease, String atmShipmentId, String latArrived, String lngArrived, String timeArrived, String latLeft, String lngLeft, String timeLeft) {
        this.atmOrderrelease = atmOrderrelease;
        this.atmShipmentId = atmShipmentId;
        this.latArrived = latArrived;
        this.lngArrived = lngArrived;
        this.timeArrived = timeArrived;
        this.latLeft = latLeft;
        this.lngLeft = lngLeft;
        this.timeLeft = timeLeft;
    }

    public GeofenceAll(String atmOrderrelease, String atmShipmentId, String latLeft, String lngLeft, String timeLeft) {
        this.atmOrderrelease = atmOrderrelease;
        this.atmShipmentId = atmShipmentId;
        this.latLeft = latLeft;
        this.lngLeft = lngLeft;
        this.timeLeft = timeLeft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAtmOrderrelease() {
        return atmOrderrelease;
    }

    public void setAtmOrderrelease(String atmOrderrelease) {
        this.atmOrderrelease = atmOrderrelease;
    }

    public String getAtmShipmentId() {
        return atmShipmentId;
    }

    public void setAtmShipmentId(String atmShipmentId) {
        this.atmShipmentId = atmShipmentId;
    }

    public String getLatArrived() {
        return latArrived;
    }

    public void setLatArrived(String latArrived) {
        this.latArrived = latArrived;
    }

    public String getLngArrived() {
        return lngArrived;
    }

    public void setLngArrived(String lngArrived) {
        this.lngArrived = lngArrived;
    }

    public String getTimeArrived() {
        return timeArrived;
    }

    public void setTimeArrived(String timeArrived) {
        this.timeArrived = timeArrived;
    }

    public String getLatLeft() {
        return latLeft;
    }

    public void setLatLeft(String latLeft) {
        this.latLeft = latLeft;
    }

    public String getLngLeft() {
        return lngLeft;
    }

    public void setLngLeft(String lngLeft) {
        this.lngLeft = lngLeft;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }
}

package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model;

import java.io.Serializable;

public class GeofencingBoss implements Serializable {
    private String Id;
    private String Lat;
    private String Lng;

    public GeofencingBoss(String id, String lat, String lng) {
        Id = id;
        Lat = lat;
        Lng = lng;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }
}

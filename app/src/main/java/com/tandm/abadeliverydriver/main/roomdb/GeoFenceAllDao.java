package com.tandm.abadeliverydriver.main.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GeoFenceAllDao {

    @Insert
    void insert(GeofenceAll geofenceAll);

    @Query("UPDATE geofenceall SET latLeft=:latLeft, lngLeft=:lngLeft, timeLeft=:timeLeft WHERE atmOrderrelease=:atmOrderrelease and atmShipmentId=:atmShipmentId")
    void update(String atmOrderrelease, String atmShipmentId, String latLeft, String lngLeft, String timeLeft);

    @Query("DELETE FROM geofenceall")
    void deleteGeoFenceAll();

    @Query("SELECT * FROM geofenceall")
    LiveData<List<GeofenceAll>> getGeofenceAll();
}

package com.tandm.abadeliverydriver.main.roomdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GeofenceAllViewModel extends AndroidViewModel {

    private GeofenceAllRepository repository;
    private LiveData<List<GeofenceAll>> allGeofenceAll;


    public GeofenceAllViewModel(@NonNull Application application) {
        super(application);
        repository = new GeofenceAllRepository(application);
        allGeofenceAll = repository.getGeofenceAll();
    }

    public void insert(GeofenceAll geofenceAll) {
        repository.insert(geofenceAll);
    }

    public void update(GeofenceAll geofenceAll) {
        repository.update(geofenceAll);
    }

    public void deleteAllGeofenceAll() {
        repository.deleteAllGeofenceAll();
    }

    public LiveData<List<GeofenceAll>> getAllGeofence(){
        return allGeofenceAll;
    }
}

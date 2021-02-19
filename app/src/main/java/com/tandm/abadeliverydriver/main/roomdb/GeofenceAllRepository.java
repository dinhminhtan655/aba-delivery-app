package com.tandm.abadeliverydriver.main.roomdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GeofenceAllRepository {

    private GeoFenceAllDao geofenceAllDao;

    private LiveData<List<GeofenceAll>> allGeofenceAll;

    public GeofenceAllRepository(Context context) {
        GeofenceAllDatabase database = GeofenceAllDatabase.getInstance(context);
        geofenceAllDao = database.geoFenceAllDao();
        allGeofenceAll = geofenceAllDao.getGeofenceAll();
    }

    public void insert(GeofenceAll geofenceAll) {
        new InsertGeofenceAllTask(geofenceAllDao).execute(geofenceAll);
    }

    public void update(GeofenceAll geofenceAll) {
        new UpdateGeofenceAllTask(geofenceAllDao).execute(geofenceAll);
    }

    public void deleteAllGeofenceAll() {
        new DeleteGeofenceAllTask(geofenceAllDao).execute();
    }

    public LiveData<List<GeofenceAll>> getGeofenceAll() {
        return allGeofenceAll;
    }

    private static class InsertGeofenceAllTask extends AsyncTask<GeofenceAll, Void, Void> {

        private GeoFenceAllDao geoFenceAllDao;

        private InsertGeofenceAllTask(GeoFenceAllDao geoFenceAllDao) {
            this.geoFenceAllDao = geoFenceAllDao;
        }

        @Override
        protected Void doInBackground(GeofenceAll... geofenceAlls) {
            geoFenceAllDao.insert(geofenceAlls[0]);
            return null;
        }
    }

    private static class UpdateGeofenceAllTask extends AsyncTask<GeofenceAll, Void, Void> {
        private GeoFenceAllDao geoFenceAllDao;

        public UpdateGeofenceAllTask(GeoFenceAllDao geoFenceAllDao) {
            this.geoFenceAllDao = geoFenceAllDao;
        }

        @Override
        protected Void doInBackground(GeofenceAll... geofenceAlls) {
            geoFenceAllDao.update(geofenceAlls[0].getAtmOrderrelease(), geofenceAlls[0].getAtmShipmentId(), geofenceAlls[0].getLatLeft(), geofenceAlls[0].getLngLeft(), geofenceAlls[0].getTimeLeft());
            return null;
        }
    }

    private static class DeleteGeofenceAllTask extends AsyncTask<Void, Void, Void> {
        private GeoFenceAllDao geoFenceAllDao;

        public DeleteGeofenceAllTask(GeoFenceAllDao geoFenceAllDao) {
            this.geoFenceAllDao = geoFenceAllDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            geoFenceAllDao.deleteGeoFenceAll();
            return null;
        }
    }

}

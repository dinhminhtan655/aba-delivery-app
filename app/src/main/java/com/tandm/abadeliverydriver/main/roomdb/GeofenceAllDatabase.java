package com.tandm.abadeliverydriver.main.roomdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {GeofenceAll.class}, version = 1, exportSchema = false)
public abstract class GeofenceAllDatabase extends RoomDatabase {

    private static GeofenceAllDatabase instance;

    public abstract GeoFenceAllDao geoFenceAllDao();

    public static synchronized GeofenceAllDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GeofenceAllDatabase.class,
                    "geofenceall")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private GeoFenceAllDao geoFenceAllDao;

        public PopulateDbAsyncTask(GeofenceAllDatabase db) {
            geoFenceAllDao = db.geoFenceAllDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            geoFenceAllDao.insert(new GeofenceAll("ATM_OR20200915-0745","ATM_SH20200915-0151","10.7638862", "106.7009665","2020-09-25 15:51:29:000",null,null,null));
            geoFenceAllDao.insert(new GeofenceAll("ATM_OR20200915-0745","ATM_SH20200915-0151","10.7638862", "106.7009665","2020-09-25 15:51:29:000",null,null,null));
            return null;
        }
    }

}

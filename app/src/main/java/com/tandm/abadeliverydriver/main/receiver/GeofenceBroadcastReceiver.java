package com.tandm.abadeliverydriver.main.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.roomdb.GeofenceAll;
import com.tandm.abadeliverydriver.main.roomdb.GeofenceAllRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";
    private String strToken, strATM_SHIPMENT_ID, strOrderreleaseId, fullName, driverId;
//    private GeofenceAllViewModel geofenceAllViewModel;

    GeofenceAllRepository repository;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        Toast.makeText(context, "Geofence trigged...", Toast.LENGTH_SHORT).show();
        fullName = LoginPrefer.getObject(context).fullName;
        driverId = LoginPrefer.getObject(context).MaNhanVien;
        strToken = "Bearer " + LoginPrefer.getObject(context).access_token;
//        NotificationHelper notificationHelper = new NotificationHelper(context);
        GeofencingClient geofencingClient = LocationServices.getGeofencingClient(context);
        List<String> geoList = new ArrayList<>();

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        repository = new GeofenceAllRepository(context);

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();

        Location location = geofencingEvent.getTriggeringLocation();
        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                for (Geofence geofence : geofenceList) {
                    if (geofence.getRequestId() != null) {
                        String geo[] = geofence.getRequestId().split("/");
                        Log.e(TAG, geofence.getRequestId() + "");
                        strATM_SHIPMENT_ID = geo[0];
                        strOrderreleaseId = geo[1];
                        if (strATM_SHIPMENT_ID.equals("ABA")) {
                            MyRetrofit.initRequest(context).trackingDepot(strToken, fullName, "Den_" + strOrderreleaseId, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), driverId).enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body() == 0) {
                                            Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
//                                            notificationHelper.sendHighPriorityNotification("ABA: Bạn đã đến " + strOrderreleaseId, "", MainActivity.class);
                                        }
                                    } else {
                                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(context, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            repository.insert(new GeofenceAll(strOrderreleaseId.trim(), strATM_SHIPMENT_ID.trim(), String.valueOf(location.getLatitude()),
                                    String.valueOf(location.getLongitude()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())
                                    , null, null, null));
                            MyRetrofit.initRequest(context).UpdateGeoFencingToi(strToken, strATM_SHIPMENT_ID.trim(), strOrderreleaseId.trim(), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())).enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body() == 1) {
                                            Toast.makeText(context, "Đến Thành công", Toast.LENGTH_SHORT).show();
//                                            notificationHelper.sendHighPriorityNotification("ABA: Đã sắp đến điểm giao", "", MainActivity.class);
                                        }
                                    } else {
                                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(context, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
//                                    repository.insert(new GeofenceAll(strOrderreleaseId.trim(), strATM_SHIPMENT_ID.trim(), String.valueOf(location.getLatitude()),
//                                            String.valueOf(location.getLongitude()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())
//                                            , null, null, null));
                                }
                            });
                        }
                    }
                }
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                for (Geofence geofence : geofenceList) {
                    if (geofence.getRequestId() != null) {
                        String geo[] = geofence.getRequestId().split("/");
                        Log.e(TAG, geofence.getRequestId() + "");
                        strATM_SHIPMENT_ID = geo[0];
                        strOrderreleaseId = geo[1];
                        if (strATM_SHIPMENT_ID.equals("ABA")) {
                            MyRetrofit.initRequest(context).trackingDepot(strToken, fullName, "Roi_" + strOrderreleaseId, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), driverId).enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body() == 0) {
                                            Toast.makeText(context, "Rời Thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(context, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {

                            repository.insert(new GeofenceAll(strOrderreleaseId.trim(), strATM_SHIPMENT_ID.trim(), null, null, null
                                    , String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())));
                            MyRetrofit.initRequest(context).UpdateGeoFencingRoi(strToken, strATM_SHIPMENT_ID.trim(), strOrderreleaseId.trim(), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())).enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body() == 1) {
                                            Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(context, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
//                                    repository.update(new GeofenceAll(strOrderreleaseId.trim(), strATM_SHIPMENT_ID.trim(), String.valueOf(location.getLatitude()),
//                                            String.valueOf(location.getLongitude()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime())));
                                }
                            });
                            geoList.add(geofence.getRequestId());
                            geofencingClient.removeGeofences(geoList);
                        }
                    }
                }
                break;
        }
    }
}

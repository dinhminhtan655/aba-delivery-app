package com.tandm.abadeliverydriver.main.service;

import android.location.Location;

public class SendLocationToFragment2 {

    private Location location;

    public SendLocationToFragment2(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}

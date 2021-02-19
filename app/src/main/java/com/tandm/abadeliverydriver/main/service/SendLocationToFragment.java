package com.tandm.abadeliverydriver.main.service;

import android.location.Location;

public class SendLocationToFragment {

    private Location location;

    public SendLocationToFragment(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

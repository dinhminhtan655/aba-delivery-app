package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model;

import com.google.gson.annotations.SerializedName;

public class TimeSlot {

    @SerializedName("TimeSlotID")
    public int timeSlotID;
    @SerializedName("TimeSlot")
    public String timeSlot;
    @SerializedName("SlotStartTime")
    public String slotStartTime;
    @SerializedName("SlotEndTime")
    public String slotEndTime;

    @Override
    public String toString() {
        return "Tên Slot: " + timeSlot + "\n" +
                "Bắt đầu: " + slotStartTime + "\n" +
                "Kết thúc: " + slotEndTime;
    }
}

package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historytraydelivery;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryTrayDeliveryNotesParent {

    @SerializedName("items")
    public List<HistoryTrayDeliveryNotesChild> items;

    public List<HistoryTrayDeliveryNotesChild> getItems() {
        return items;
    }

    public void setItems(List<HistoryTrayDeliveryNotesChild> items) {
        this.items = items;
    }
}

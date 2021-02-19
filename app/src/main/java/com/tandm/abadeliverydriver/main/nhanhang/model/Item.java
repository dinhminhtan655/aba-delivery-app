package com.tandm.abadeliverydriver.main.nhanhang.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName("items")
    public List<ItemChild> items;

    public Item(List<ItemChild> items) {
        this.items = items;
    }

    public List<ItemChild> getItems() {
        return items;
    }

    public void setItems(List<ItemChild> items) {
        this.items = items;
    }
}

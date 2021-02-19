package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model;

public class DateItem extends ListItem {


    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }

    @Override
    public int compareTo(ListItem o) {
        return 0;
    }
}

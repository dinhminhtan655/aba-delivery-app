package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model;

public abstract class ListItem implements Comparable<ListItem> {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_GENERAL = 1;

    abstract public int getType();

}

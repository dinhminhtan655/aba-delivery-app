package com.tandm.abadeliverydriver.main.zalo;

public class TemplateData {
    public String staff_name;
    public String date_time;
    public String deliveryorder_id;
    public String customer_name;
    public String truck_number;

    public TemplateData(String staff_name, String date_time, String deliveryorder_id, String customer_name, String truck_number) {
        this.staff_name = staff_name;
        this.date_time = date_time;
        this.deliveryorder_id = deliveryorder_id;
        this.customer_name = customer_name;
        this.truck_number = truck_number;
    }
}

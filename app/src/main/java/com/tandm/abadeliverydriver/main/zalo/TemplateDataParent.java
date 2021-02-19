package com.tandm.abadeliverydriver.main.zalo;

public class TemplateDataParent {

    public String phone;
    public String template_id;
    public TemplateData520 template_data;
    public String tracking_id;

    public TemplateDataParent(String phone, String template_id, TemplateData520 template_data, String tracking_id) {
        this.phone = phone;
        this.template_id = template_id;
        this.template_data = template_data;
        this.tracking_id = tracking_id;
    }

//    public TemplateDataParent(String phone, String template_id, TemplateData template_data, String tracking_id) {
//        this.phone = phone;
//        this.template_id = template_id;
//        this.template_data2 = template_data;
//        this.tracking_id = tracking_id;
//    }
}

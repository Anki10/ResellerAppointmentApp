package com.anova.reseller_appointment.pojo;

import java.util.ArrayList;

public class VendorDetailsTAB_ResponsePojo {

    private String question_id;
    private String data_id;
    private ArrayList<VendorDetailsTabDataResponsePojo>data;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public ArrayList<VendorDetailsTabDataResponsePojo> getData() {
        return data;
    }

    public void setData(ArrayList<VendorDetailsTabDataResponsePojo> data) {
        this.data = data;
    }


}

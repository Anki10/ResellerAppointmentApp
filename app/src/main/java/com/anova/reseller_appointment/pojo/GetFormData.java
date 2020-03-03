package com.anova.reseller_appointment.pojo;

import java.util.ArrayList;

public class GetFormData {


    private ArrayList<Basic_Information_list_Pojo> physical_location;
    private ArrayList<Basic_Information_list_Pojo> basic_information;
    private ArrayList<Basic_Information_list_Pojo> stores_capabilit;
    private ArrayList<Basic_Information_list_Pojo>transportation;
    private ArrayList<Basic_Information_list_Pojo> safety;

    public ArrayList<Basic_Information_list_Pojo> getPhysical_location() {
        return physical_location;
    }

    public void setPhysical_location(ArrayList<Basic_Information_list_Pojo> physical_location) {
        this.physical_location = physical_location;
    }

    public ArrayList<Basic_Information_list_Pojo> getBasic_information() {
        return basic_information;
    }

    public void setBasic_information(ArrayList<Basic_Information_list_Pojo> basic_information) {
        this.basic_information = basic_information;
    }

    public ArrayList<Basic_Information_list_Pojo> getStores_capabilit() {
        return stores_capabilit;
    }

    public void setStores_capabilit(ArrayList<Basic_Information_list_Pojo> stores_capabilit) {
        this.stores_capabilit = stores_capabilit;
    }

    public ArrayList<Basic_Information_list_Pojo> getTransportation() {
        return transportation;
    }

    public void setTransportation(ArrayList<Basic_Information_list_Pojo> transportation) {
        this.transportation = transportation;
    }

    public ArrayList<Basic_Information_list_Pojo> getSafety() {
        return safety;
    }

    public void setSafety(ArrayList<Basic_Information_list_Pojo> safety) {
        this.safety = safety;
    }

    public ArrayList<Basic_Information_list_Pojo> getTransaction_and_Accounting() {
        return Transaction_and_Accounting;
    }

    public void setTransaction_and_Accounting(ArrayList<Basic_Information_list_Pojo> transaction_and_Accounting) {
        Transaction_and_Accounting = transaction_and_Accounting;
    }

    private ArrayList<Basic_Information_list_Pojo> Transaction_and_Accounting;





}

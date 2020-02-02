package com.anova.reseller_appointment.pojo;

import java.util.ArrayList;

public class Basic_Information_list_Pojo {

    private String id;

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    private ArrayList<String> options;
    private String question;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


}

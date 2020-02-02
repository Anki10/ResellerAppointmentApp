package com.anova.reseller_appointment.pojo;

public class PostVideoRequest {

    private String calling_user_id;

    public String getCalling_user_id() {
        return calling_user_id;
    }

    public void setCalling_user_id(String calling_user_id) {
        this.calling_user_id = calling_user_id;
    }

    public long getChat_time() {
        return chat_time;
    }

    public void setChat_time(long chat_time) {
        this.chat_time = chat_time;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    private long chat_time;
    private String form_id;
}

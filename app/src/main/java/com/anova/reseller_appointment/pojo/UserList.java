package com.anova.reseller_appointment.pojo;

public class UserList {

    private long chat_time;
    private Boolean qc_done;
    private String status;
    private String user_id;
    private String user_name;
    private String api_key;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getHost_id() {
        return host_id;
    }

    public void setHost_id(String host_id) {
        this.host_id = host_id;
    }

    public String getHost_user_name() {
        return host_user_name;
    }

    public void setHost_user_name(String host_user_name) {
        this.host_user_name = host_user_name;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String host_id;
    private String host_user_name;
    private String session_id;
    private String token;


    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    private String form_id;

    public long getChat_time() {
        return chat_time;
    }

    public void setChat_time(long chat_time) {
        this.chat_time = chat_time;
    }

    public Boolean getQc_done() {
        return qc_done;
    }

    public void setQc_done(Boolean qc_done) {
        this.qc_done = qc_done;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


}

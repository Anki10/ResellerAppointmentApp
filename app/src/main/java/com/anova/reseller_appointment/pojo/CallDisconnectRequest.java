package com.anova.reseller_appointment.pojo;

public class CallDisconnectRequest {

    private String session_id;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    private long epoch;
    private String form_id;


}

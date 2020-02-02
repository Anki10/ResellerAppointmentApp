package com.anova.reseller_appointment.pojo;

public class VendorDetailsResponsePojo {

    private Boolean success;
    private Boolean form_status;

    public VendorDetailsDataResponsePojo getData() {
        return data;
    }

    public void setData(VendorDetailsDataResponsePojo data) {
        this.data = data;
    }

    private VendorDetailsDataResponsePojo data;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getForm_status() {
        return form_status;
    }

    public void setForm_status(Boolean form_status) {
        this.form_status = form_status;
    }


}

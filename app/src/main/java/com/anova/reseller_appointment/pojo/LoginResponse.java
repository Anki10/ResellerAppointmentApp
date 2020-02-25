package com.anova.reseller_appointment.pojo;

public class LoginResponse {

    private String message;
    private Boolean success;
    private String token;
    private String user_name;
    private String seller_assessed_as;

    public String getSeller_assessed_as() {
        return seller_assessed_as;
    }

    public void setSeller_assessed_as(String seller_assessed_as) {
        this.seller_assessed_as = seller_assessed_as;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


}

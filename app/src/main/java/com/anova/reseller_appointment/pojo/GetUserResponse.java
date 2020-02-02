package com.anova.reseller_appointment.pojo;

import java.util.ArrayList;

public class GetUserResponse {

    private Boolean success;

    public ArrayList<UserList> getAll_user_data() {
        return all_user_data;
    }

    public void setAll_user_data(ArrayList<UserList> all_user_data) {
        this.all_user_data = all_user_data;
    }

    private ArrayList<UserList>all_user_data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}

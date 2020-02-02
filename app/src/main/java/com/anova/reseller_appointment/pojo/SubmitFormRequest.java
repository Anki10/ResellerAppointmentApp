package com.anova.reseller_appointment.pojo;

public class SubmitFormRequest {

    private String question_id;
    private String file_data;
    private String form_id;
    private Boolean is_submit;
    private String data_id;

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }



    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getFile_data() {
        return file_data;
    }

    public void setFile_data(String file_data) {
        this.file_data = file_data;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public Boolean getIs_submit() {
        return is_submit;
    }

    public void setIs_submit(Boolean is_submit) {
        this.is_submit = is_submit;
    }




}

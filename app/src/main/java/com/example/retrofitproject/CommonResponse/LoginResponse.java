package com.example.retrofitproject.CommonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

 @SerializedName("user_id")
    @Expose
    private  String user_id;

@SerializedName("first_name")
    @Expose
    private  String first_name;


    @SerializedName("last_name")
    @Expose
    private  String last_name;

    @SerializedName("message")
    @Expose
    private  String message;


    @SerializedName("status")
    @Expose
    private  String status;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}

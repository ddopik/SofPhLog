package com.example.softmills.phlog.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class ErrorData {


    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("code")
    @Expose
    public Integer code;
}
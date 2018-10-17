package com.example.softmills.phlog.ui.login.model;

import com.example.softmills.phlog.base.BaseApiResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialLoginResponse     {


    @SerializedName("token")
    @Expose
    public List<String> token = null;

    @SerializedName("msg")
    @Expose
    public Msg msg;
    @SerializedName("state")
    @Expose
    public String state;

    public class Msg {

        @SerializedName("image_profile")
        @Expose
        public List<String> imageProfile = null;

    }
}

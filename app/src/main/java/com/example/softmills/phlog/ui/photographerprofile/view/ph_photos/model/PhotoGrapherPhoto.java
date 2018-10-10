package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class PhotoGrapherPhoto {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("photographer_id")
    @Expose
    public Integer photographerId;
    @SerializedName("caption")
    @Expose
    public String caption;
    @SerializedName("is_for_sale")
    @Expose
    public Integer isForSale;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("can_sold_exclussive")
    @Expose
    public String canSoldExclussive;
    @SerializedName("created_at")
    @Expose
    public Object createdAt;
    @SerializedName("updated_at")
    @Expose
    public Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;



}

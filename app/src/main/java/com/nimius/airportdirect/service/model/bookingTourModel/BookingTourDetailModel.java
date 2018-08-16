package com.nimius.airportdirect.service.model.bookingTourModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nimius.airportdirect.service.model.bookingModel.AgeGroupsModel;

public class BookingTourDetailModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("ageGroups")
    @Expose
    public AgeGroupsModel ageGroups;
    @SerializedName("dropoffAddress")
    @Expose
    public Object dropoffAddress;
    @SerializedName("created")
    @Expose
    public String created;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("paidType")
    @Expose
    public String paidType;
    @SerializedName("customerId")
    @Expose
    public int customerId;
    @SerializedName("customerName")
    @Expose
    public String customerName;
    @SerializedName("customerEmail")
    @Expose
    public String customerEmail;
}

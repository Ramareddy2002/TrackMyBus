package com.example.trackmybus.Responses

import com.example.trackmybus.Responses.Model.Seats
import com.google.gson.annotations.SerializedName

data class GetSeats (
    @SerializedName("error")var error:Boolean?=null,
    @SerializedName("message")var message:String?=null,
    @SerializedName("data")var data:ArrayList<Seats>?= arrayListOf()
)
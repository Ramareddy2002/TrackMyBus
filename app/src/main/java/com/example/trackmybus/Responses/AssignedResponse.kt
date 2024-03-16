package com.example.trackmybus.Responses

import com.example.trackmybus.Responses.Model.Assigned
import com.google.gson.annotations.SerializedName

data class AssignedResponse (
    @SerializedName("error")var error:Boolean?=null,
    @SerializedName("message")var message:String?=null,
    @SerializedName("data")var data:ArrayList<Assigned>?= arrayListOf()
)
package com.example.trackmybus.Responses

import com.example.trackmybus.Responses.Model.Route
import com.google.gson.annotations.SerializedName

data class RouteResponse (
   @SerializedName("error")var error: Boolean,
   @SerializedName("message")var message:String,
   @SerializedName("data")var data:ArrayList<Route>?= arrayListOf()
)
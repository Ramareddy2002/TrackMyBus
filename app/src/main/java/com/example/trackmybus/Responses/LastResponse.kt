package com.example.trackmybus.Responses

import com.example.trackmybus.Responses.Model.Custome
import com.google.gson.annotations.SerializedName

data class LastResponse (

    @SerializedName("error"   ) var error   : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Custome> = arrayListOf()
)
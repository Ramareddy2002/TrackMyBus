package com.example.trackmybus.Responses

import com.example.trackmybus.Responses.Model.History
import com.google.gson.annotations.SerializedName

data class GetHistoryUser (
    @SerializedName("error"   ) var error   : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<History> = arrayListOf()
)
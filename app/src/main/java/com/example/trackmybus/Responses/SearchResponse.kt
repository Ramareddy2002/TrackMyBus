package com.example.trackmybus.Responses

import com.example.trackmybus.Responses.Model.Search
import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("error")var error:Boolean?=null,
    @SerializedName("message")var message:String?=null,
    @SerializedName("data")var data:ArrayList<Search>?= arrayListOf()
)
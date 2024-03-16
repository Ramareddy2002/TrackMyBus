package com.example.trackmybus.User.Core

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackmybus.Responses.Model.Search
import com.example.trackmybus.Responses.ReTrofit
import com.example.trackmybus.Responses.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val mutable = MutableLiveData(ArrayList<Search>())
    fun getdata(search: String) {
        ReTrofit.instance.searchdata(condition = "searchBus", search = search)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    Log.i("asdhfagsdf", "${response.body()}")
                    response.body()?.let {
                        mutable.value = it.data
                    }

                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.i("asdhfagsdf", "${t.message}")
                }
            })
    }

    fun calculate(searches: Location) {
        val locationa = Location("a")
        locationa.longitude = searches.longitude
        locationa.latitude = searches.latitude
        mutable.value?.forEach {
            val locationb = Location("b")
            val k = it.fromlatlon?.split(",")
            if (k?.size == 2) {
                locationb.latitude = k[0].toDouble()
                locationb.longitude = k[1].toDouble()
                val km = locationa.distanceTo(locationb) / 1000

                if (km >0) {
                    it.state = true
                    it.distance=km.toInt()
                }
            }

        }
    }

    fun update() = mutable
}
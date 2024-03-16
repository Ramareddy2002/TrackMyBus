package com.example.trackmybus.User

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackmybus.Adapters.ForSearch
import com.example.trackmybus.Interactions.Searchable
import com.example.trackmybus.Responses.Model.Search
import com.example.trackmybus.User.Core.MainViewModel
import com.example.trackmybus.databinding.ActivitySearchForBusBinding
import com.example.trackmybus.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class SearchForBus : AppCompatActivity() ,Searchable {
    private val bind by lazy {
        ActivitySearchForBusBinding.inflate(layoutInflater)
    }
    private val model by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    var currentLatLng = ""
    lateinit var fused: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        fused = LocationServices.getFusedLocationProviderClient(this)
        fused.lastLocation.addOnSuccessListener {
            if (it != null) {
                currentLatLng = "${it.latitude},${it.longitude}"
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        Geocoder(this).getFromLocation(it.latitude, it.latitude, 1) {

                            model.getdata(it[0].locality)
                        }

                    } else {
                        val city = Geocoder(this).getFromLocation(it.latitude, it.latitude, 1)
                            ?.get(0)?.locality
                        city?.let { it1 ->
                            model.getdata(it1)

                        }

                    }
                } catch (e: Exception) {
                    model.getdata("Hyderabad")
                }




                bind.searchView.setQuery("", true)
                model.update().observe(this) { data ->
                    if (data != null) {
                        model.calculate(it)
                        bind.cycle3.let { the ->
                            the.adapter = ForSearch(this, data, this)
                            the.layoutManager = LinearLayoutManager(this)
                        }

                    }
                }
            }
            bind.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { model.getdata(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { model.getdata(it) }
                    return true
                }
            })
        }.addOnFailureListener {
            showToast(it.message)
        }


    }

    override fun Forintent(search: Search) {
        if (search.distance != null) {
            Intent(this, SeatSelection::class.java).apply {
                putExtra("routedetails", search)
                putExtra("latlon", currentLatLng)
                putExtra("select_date", intent.getStringExtra("select_date"))
                startActivity(this)
            }
        } else {
            showToast("distance is not get in")
        }


    }
}



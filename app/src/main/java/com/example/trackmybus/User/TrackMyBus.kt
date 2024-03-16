package com.example.trackmybus.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackmybus.Adapters.ViewDoc
import com.example.trackmybus.R
import com.example.trackmybus.User.Core.SeatViewModel
import com.example.trackmybus.databinding.ActivityTrackMyBusBinding

class TrackMyBus : AppCompatActivity() {
    private val model by lazy {
        ViewModelProvider(this)[SeatViewModel::class.java]
    }
    private val bind by lazy {
        ActivityTrackMyBusBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        model.startData(search = "c")
        bind.searchView2.setOnQueryTextListener(
            object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { model.startData(search = it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { model.startData(search = it) }
                return true
                }
            })
        model.viewdata().observe(this){
            if(it!=null){
                bind.cycle5.apply {
                    adapter= ViewDoc(this@TrackMyBus,it)
                    layoutManager=LinearLayoutManager(this@TrackMyBus)
                }
            }
        }

    }
}
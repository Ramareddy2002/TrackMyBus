package com.example.trackmybus.User

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmybus.Adapters.HistoryView
import com.example.trackmybus.Comfun
import com.example.trackmybus.R
import com.example.trackmybus.Responses.GetHistoryUser
import com.example.trackmybus.Responses.ReTrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryOfBookings : AppCompatActivity() {
    private val cf by lazy {
        Comfun(this)
    }
    lateinit var cycle: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_of_bookings)
        cycle = findViewById(R.id.cycle4)
        getdata()


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }

        })
    }

    private fun getdata() {
        cf.p.show()
        val id = getSharedPreferences("user", MODE_PRIVATE).getString("id", "")
        if (id != null) {
            ReTrofit.instance.getMyHistroy(condition = "getMyHistory", id = id)
                .enqueue(object : Callback<GetHistoryUser> {
                    override fun onResponse(
                        call: Call<GetHistoryUser>,
                        response: Response<GetHistoryUser>
                    ) {
                        response.body()?.let {
                            cycle.apply {
                                layoutManager = LinearLayoutManager(this@HistoryOfBookings)
                                adapter = HistoryView(this@HistoryOfBookings, it.data)
                            }
                        }
                        cf.p.dismiss()
                    }

                    override fun onFailure(call: Call<GetHistoryUser>, t: Throwable) {
                        cf.p.dismiss()
                    }
                })

        }
    }
}
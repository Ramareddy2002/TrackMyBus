package com.example.trackmybus.Driver.Core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackmybus.Adapters.FinalAdapter
import com.example.trackmybus.Comfun
import com.example.trackmybus.R
import com.example.trackmybus.Responses.LastResponse
import com.example.trackmybus.Responses.ReTrofit
import com.example.trackmybus.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMoreDetails : AppCompatActivity() {

val cf by lazy {
    Comfun(this)
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_days)
    intent.getStringExtra("date")?.let {
        cf.p.show()
        val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")
        ReTrofit.instance.getDayHistory(condition = "getdaysHistory", id = "$id", date = it).enqueue(
            object :Callback<LastResponse>{
                override fun onResponse(
                    call: Call<LastResponse>,
                    response: Response<LastResponse>
                ) {
                    cf.p.dismiss()
                    response.body()?.let {
                        findViewById<RecyclerView>(R.id.cycle6).apply {
                     layoutManager=LinearLayoutManager(this@ViewMoreDetails)
                            adapter=FinalAdapter(this@ViewMoreDetails,it.data)
                        }
                    }
                }

                override fun onFailure(call: Call<LastResponse>, t: Throwable) {
                showToast(t.message)

                    cf.p.dismiss()
                    finish()
                }
            })

    }
    }
}

package com.example.trackmybus.Driver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.HtmlCompat
import coil.load
import com.example.trackmybus.Driver.Core.BroadCast
import com.example.trackmybus.Driver.Core.ViewMoreDetails
import com.example.trackmybus.LoginActivity
import com.example.trackmybus.Responses.AssignedResponse
import com.example.trackmybus.Responses.ReTrofit
import com.example.trackmybus.databinding.ActivityDriverBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class DriverActivity : AppCompatActivity() {
    private val bind by lazy {
        ActivityDriverBinding.inflate(layoutInflater)
    }
    private val dialog by lazy {
        MaterialDatePicker.Builder.datePicker().build()
    }
     lateinit var alarma:AlarmManager

lateinit var pending:PendingIntent


    @SuppressLint("SimpleDateFormat")
    val simple=SimpleDateFormat("dd-MM-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
getSharedPreferences("user", MODE_PRIVATE).apply {
    bind.greetings.text=HtmlCompat.fromHtml("<b>Hi ${getString("name","")} 😊 !!<b>",HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
    getString("id","")?.let{ getdata(it) }
    bind.profile.load(getString("profile",""))
    bind.data.text=HtmlCompat.fromHtml(
        "${getString("Assigned","")}",
        HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
}
        bind.cardView4.setOnClickListener {
            dialog.show(supportFragmentManager,"Choose a date???")
        }

        dialog.addOnPositiveButtonClickListener {
Intent(this,ViewMoreDetails::class.java).apply {
    putExtra("date",simple.format(Date(it)))
    startActivity(this)
}
        }




        bind.logout1.setOnClickListener {
                MaterialAlertDialogBuilder(this).apply{
                    setCancelable(false)
                    setMessage("Do you want to Logout ?? \n Press 'Yes' to Logout or Press 'No' to cancel")
                    setPositiveButton("Yes"){c,_->
                        c.dismiss()
                        getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                        finishAffinity()
                        startActivity(Intent(this@DriverActivity, LoginActivity::class.java))
                    }
                    setNegativeButton("No"){p,_->
                        p.dismiss()
                    }
                    show()
                }


        }





    }

    private fun getdata(id: String) {
    CoroutineScope(IO).launch {
        ReTrofit.instance.getData(condition = "getassigned", id = id).enqueue(object :Callback<AssignedResponse>{
            override fun onResponse(
                call: Call<AssignedResponse>,
                response: Response<AssignedResponse>
            ) {
                response.body()?.let {
                if(it.data?.isNotEmpty() == true) {
                    val view= it.data!![0]

                    val text = "<b>From : </b>${view.fromplace}<br>" +
                                "<b>To  : </b>${view.toplace}<br>" +
                                "<b>Starts at : </b>${view.starttime}<br>" +
                                "<b>Ends : </b>${view.endtime}<br>" +
                                "<b>Per km : </b>${view.perkm} km/s<br>"
                    getSharedPreferences("user", MODE_PRIVATE).apply {
                        val k=getString("Assigned","")
                        if(k!=text){
                            edit().putString("Assigned", text).apply()
                        }
                        bind.data.text=HtmlCompat.fromHtml(
                            text,
                            HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
                    }
                }
                }
            }

            override fun onFailure(call: Call<AssignedResponse>, t: Throwable) {
                Log.i(packageName,"${t.message}")
            }
        })
    }
    }

    override fun onDestroy() {
        super.onDestroy()
    alarma.cancel(pending)
        pending.cancel()
    }

    override fun onStart() {
        super.onStart()
       pending= PendingIntent.getBroadcast(this,0,Intent(this,BroadCast::class.java),
            PendingIntent.FLAG_MUTABLE)
        alarma=getSystemService(ALARM_SERVICE)as AlarmManager
        alarma.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),1000*10,pending)
    }
}
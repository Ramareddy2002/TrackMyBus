package com.example.trackmybus.User

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.text.HtmlCompat
import com.example.trackmybus.LoginActivity
import com.example.trackmybus.R
import com.example.trackmybus.databinding.ActivityUserMain2Binding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date

class UserMainActivity : AppCompatActivity() {
    private val bind by lazy {
        ActivityUserMain2Binding.inflate(layoutInflater)
    }
    private val datapicker by lazy {
        MaterialDatePicker.Builder.datePicker()
    }
    @SuppressLint("SimpleDateFormat")
    val simple=SimpleDateFormat("dd-MM-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        datapicker.setTitleText("Choose a day to Book the Bus ?")
        val picker=datapicker.build()
        val array= arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)
        getSharedPreferences("user", MODE_PRIVATE).apply {

            bind.greeting2.text=HtmlCompat.fromHtml("<b>Hi ${getString("name","")} 😊 !!",
                HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
        }
        bind.search.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this,array[0])!=PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,array[1])!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(array,100)
            }else{
                picker.show(supportFragmentManager,"Material Date-picker")
            }

    }

        bind.history.setOnClickListener {
            startActivity(Intent(this,HistoryOfBookings::class.java))
        }
        bind.track.setOnClickListener {
            startActivity(Intent(this,TrackMyBus::class.java))
        }
        bind.logout2.setOnClickListener {
            MaterialAlertDialogBuilder(this).apply{
                setCancelable(false)
                setMessage("Do you want to Logout ?? \n Press 'Yes' to Logout or Press 'No' to cancel")
                setPositiveButton("Yes"){c,_->
                    c.dismiss()
                    getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                    finishAffinity()
                    startActivity(Intent(this@UserMainActivity, LoginActivity::class.java))
                }
                setNegativeButton("No"){p,_->
                    p.dismiss()
                }
                show()
            }
        }
    picker.addOnPositiveButtonClickListener {
        Intent(this,SearchForBus::class.java).apply {
            putExtra("select_date",simple.format(Date(it)))
            startActivity(this)
        }
    }
    }
}
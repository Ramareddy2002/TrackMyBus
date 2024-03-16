package com.example.trackmybus.Admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackmybus.Adapters.FrontUi
import com.example.trackmybus.LoginActivity
import com.example.trackmybus.R
import com.example.trackmybus.databinding.ActivityAdminMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AdminMainActivity : AppCompatActivity() {
    private val bind by lazy {
        ActivityAdminMainBinding.inflate(layoutInflater)
    }

    private val model by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        model.getdata()
        bind.cycle.let {
        model.myobserver().observe(this){the->
            if(the!=null){
                it.adapter=FrontUi(context = this,array=the)
                it.layoutManager=LinearLayoutManager(this)
            }
        }

        }
    val reallaunch=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode!= RESULT_OK){
model.getdata()
        }
    }
        bind.floating.setOnClickListener {
            reallaunch.launch(Intent(this@AdminMainActivity,AddRoute::class.java))
    }
        bind.logout.setOnClickListener {
MaterialAlertDialogBuilder(this).apply{
                setCancelable(false)
                setMessage("Do you want to Logout ?? \n Press 'Yes' to Logout or Press 'No' to cancel")
                setPositiveButton("Yes"){c,_->
                    c.dismiss()
                    getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                    finishAffinity()
                    startActivity(Intent(this@AdminMainActivity,LoginActivity::class.java))
                }
                setNegativeButton("No"){p,_->
                    p.dismiss()
                }
                show()
            }
        }

    }




}
package com.example.trackmybus.Admin

import android.content.Intent
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.trackmybus.Comfun
import com.example.trackmybus.Responses.CommonResponse
import com.example.trackmybus.Responses.ReTrofit
import com.example.trackmybus.databinding.ActivityAddRouteBinding
import com.example.trackmybus.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class AddRoute : AppCompatActivity() {

    private val bind by lazy {
        ActivityAddRouteBinding.inflate(layoutInflater)
    }
    val mutable= MutableLiveData<String>()

    private val cf by lazy {
        Comfun(this)
    }
    val decimal=DecimalFormat("##.#######")
    lateinit var  geocoder :Geocoder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setContentView(bind.root)
        geocoder=Geocoder(this)
        bind.getlatlon.setOnClickListener {
            getlatlon()
        }
        val textview=bind.fromtolalton
        mutable.observe(this){
            textview.text=it
        }
        bind.add.setOnClickListener {
            val k=textview.text.split(",")
            if(k.size!=4){
                getlatlon()
            }else{
                val from=bind.from.text.toString().trim()
                val to=bind.to.text.toString().trim()
                cf.p.show()
                ReTrofit.instance.addroute(
                    fromplace = from,
                    toplace = to,
                    fromlatlon = "${k[0].trim()},${k[1].trim()}",
                    tolatlon = "${k[2].trim()},${k[3].trim()}",
                    driverid = "not assigned"
                ).enqueue(
                    object :Callback<CommonResponse>{
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                            cf.p.dismiss()
                            response.body()?.apply {
                                showToast(message)
                                if(message=="Added") {
                                    intent?.let {
                                        it.putExtra("result",message)
                                        setResult(RESULT_OK,intent)
                                        finish()
                                    }
                                }

                            }?:run{
                            showToast("Server Error")
                            }
                        }

                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                                cf.p.dismiss()
                            showToast(t.message)
                        }

                    }
                )
            }
        }


    }

    private fun getlatlon() {
        val from=bind.from.text.toString().trim()
        val to=bind.to.text.toString().trim()

        if(from.isEmpty()){
            showToast("Please enter the from Address")
        }else if(to.isEmpty()) {
            showToast("Please enter the To Address")
        }else {

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    var firstString=""
                    var secondString=""
                    geocoder.getFromLocationName(from,1){it->
                        val k= it[0]
                        firstString="${decimal.format(k.latitude)},${decimal.format(k.longitude)}"
                    }
                    geocoder.getFromLocationName(to,1){val l=it[0]
                        secondString="${decimal.format(l.latitude)},${decimal.format(l.longitude)}"
                    }
                    mutable.value="$secondString,\n$firstString"

                }else{
                    val fri=geocoder.getFromLocationName(from,1)?.get(0)
                    val second=geocoder.getFromLocationName(to,1)?.get(0)

                    mutable.value="${decimal.format(fri?.latitude)},${decimal.format(fri?.longitude)},\n${decimal.format(second?.latitude)},${decimal.format(second?.longitude)}"
                }
            }catch (e:Exception){
                mutable.value="Sorry Invalid LatLon"
            }

        }
    }
}
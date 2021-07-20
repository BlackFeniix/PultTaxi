package com.hito.nikolay.pulttaxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val URL_API = "https://dev.pulttaxi.ru/api/"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonContinueLogin = findViewById<Button>(R.id.buttonContinueEnter)
        buttonContinueLogin.setOnClickListener {

            val phoneNumber = findViewById<EditText>(R.id.phone_input).text.toString()
            if (phoneNumber.length == 10) {
                val call =
                    RetrofitService.pultTaxiServiceApi.requestSMSCode(phoneNumber = "7$phoneNumber")
                call.enqueue(object : Callback<SmsCodeClientResponse> {
                    override fun onResponse(
                        call: Call<SmsCodeClientResponse>,
                        response: Response<SmsCodeClientResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == "success") {
                                val intent = Intent(this@MainActivity, EnterSMS::class.java)
                                intent.putExtra("phone_number", "7$phoneNumber")
                                startActivity(intent)

                                Toast.makeText(
                                    this@MainActivity,
                                    response.body()?.status,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            if (response.body()?.status == "failed")
                                Toast.makeText(
                                    this@MainActivity,
                                    "Status: Failed",
                                    Toast.LENGTH_LONG
                                ).show()
                        }
                    }

                    override fun onFailure(call: Call<SmsCodeClientResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}
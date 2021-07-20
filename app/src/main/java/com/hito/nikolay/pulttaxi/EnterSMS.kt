package com.hito.nikolay.pulttaxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.fraggjkee.smsconfirmationview.SmsConfirmationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterSMS : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_sms)

        val phoneNumber = intent.extras!!["phone_number"].toString()

        val textViewWarning = findViewById<TextView>(R.id.textViewSmsWarning)
        textViewWarning.visibility = TextView.INVISIBLE

        val button = findViewById<Button>(R.id.buttonCompleteAuthorization)
        button.setOnClickListener {
            val codeText = findViewById<SmsConfirmationView>(R.id.sms_code_view)

            if (codeText.enteredCode != "" && codeText.enteredCode.length == 4) {
                val call = RetrofitService.pultTaxiServiceApi.getJSONFromSMS(
                    phoneNumber = phoneNumber,
                    codeSMS = codeText.enteredCode
                )

                call.enqueue(object : Callback<AuthenticateClientsToken> {
                    override fun onResponse(
                        call: Call<AuthenticateClientsToken>,
                        response: Response<AuthenticateClientsToken>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()?.token != null) {
                                getUserInformationWithToken(response.body()?.token!!)
                                Toast.makeText(
                                    this@EnterSMS,
                                    response.body()?.token,
                                    Toast.LENGTH_LONG
                                ).show()
                            }


                            if (response.body()?.error == "invalid_credentials") {
                                Toast.makeText(
                                    this@EnterSMS,
                                    "Error: invalid_credentials",
                                    Toast.LENGTH_LONG
                                ).show()

                                textViewWarning.visibility = TextView.VISIBLE
                            }

                        }
                    }

                    override fun onFailure(call: Call<AuthenticateClientsToken>, t: Throwable) {
                        Toast.makeText(this@EnterSMS, t.message, Toast.LENGTH_LONG).show()
                    }

                })
            }
        }
    }


    fun getUserInformationWithToken(token: String) {
        val call = RetrofitService.pultTaxiServiceApi.getUserInformation(token = token)

        call.enqueue(object : Callback<GetInfoClient> {
            override fun onResponse(
                call: Call<GetInfoClient>,
                response: Response<GetInfoClient>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status != "error") {
                        Toast.makeText(
                            this@EnterSMS,
                            response.body()?.status.toString(),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                    if (response.body()?.status is Int)
                        Toast.makeText(
                            this@EnterSMS,
                            response.body()?.status.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                }
            }

            override fun onFailure(call: Call<GetInfoClient>, t: Throwable) {
                Toast.makeText(this@EnterSMS, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
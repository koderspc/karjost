package com.example.karjost

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_authentiaction.*
import org.json.JSONObject

class Authentication : AppCompatActivity(), ApiService.OnUserAuthenticate, View.OnClickListener {

    lateinit var apiService: ApiService
    lateinit var userInf: JSONObject

    val error_code_tv: TextView  by lazy{findViewById<TextView>(R.id.error_code_tv)}
    val code_receiver_btn :TextView by lazy{findViewById<Button>(R.id.btn_code_receiving)}

    lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentiaction)

        if (intent.hasCategory("userInf")) {
            //   userInf = JSONObject(intent.getStringExtra("userInf"))
        }
        apiService = ApiService(this)


        error_code_tv.setOnClickListener(this)
        code_receiver_btn.setOnClickListener(this)

        startTimer()

    }


    private fun startTimer(){
        timer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                timer_tv.visibility = View.INVISIBLE
                error_code_tv.visibility = View.VISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                timer_tv.text = "00:" + millisUntilFinished / 1000
            }

        }.start()


    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.error_code_tv) {
            startTimer()
            error_code_tv.visibility = View.INVISIBLE
            timer_tv.visibility = View.VISIBLE

//            apiService.userAuthenticate(userInf, this)
        }


        if (v?.id == R.id.btn_code_receiving) {
            apiService.userLogin()
            var code = code_et.text.toString()
            if (codeValidation(code)) {
                val preferenceUtils = PreferencesUtils(this)
                preferenceUtils.signUpUser()
                var intent = Intent(this, JobsCategories::class.java)
                startActivity(intent)

            } else {
                error_code_validation.visibility = View.VISIBLE
                var timer = object : CountDownTimer(4000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                    }

                    override fun onFinish() {
                        error_code_validation.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onAuthenticate(result: Boolean) {

        if (result) {

        } else {
            // show error message for 3 sec
        }
    }


    private fun codeValidation(code: String): Boolean {

        if (code == null || TextUtils.isEmpty(code)) {
            return false
        }

        return true
    }
}





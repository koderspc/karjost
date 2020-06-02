package com.example.karjost

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splash.*
import pl.droidsonroids.gif.GifDrawable

class SplashActivity : AppCompatActivity() {



    var  isLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageView.setImageResource(R.drawable.karjost)

        var apiService = ApiService(this)
        apiService.getAppData()

        val preferenceUtils = PreferencesUtils(this)
        isLogin = preferenceUtils.isLogin()




        var intent: Intent
        if(isLogin){
            intent =  Intent(this, MainActivity::class.java)
        } else {
            intent = Intent(this, LoginActivity::class.java)
        }
         Handler().postDelayed(Runnable {

             startActivity(intent)
             finish()
         }, 15500)




    }
}

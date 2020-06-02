package com.example.karjost

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), ApiService.OnUserAuthenticate {


    val MOBILE_NUM_PATTERN_ST: String = "(\\+98|0)?9\\d{9}"
    val MOBILE_NUM_PATTERN_REX = Pattern.compile(MOBILE_NUM_PATTERN_ST)
    val KEY_CITY_NAME = "city_name"
    val KEY_MOBILE_NUMBER = "mobile_number"
    val KEY_USER_TYPE = "user_type_array"

    lateinit var cities_array : Array<String>
    lateinit var user_type_array : Array<String>
    val city_spinner: Spinner by lazy { findViewById<Spinner>(R.id.spinner_city) }
    val user_type_spinner:Spinner by lazy { findViewById<Spinner>(R.id.spinner_user_type) }
    val phone_number_et : EditText by lazy { findViewById<EditText>(R.id.phone_number_et) }
    val code_receive_btn :Button by lazy{findViewById<Button>(R.id.btn_code_receiving) }

    lateinit var city_name_selected:String
    var user_type_selected:String =""





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupSpinner()
        setupPhoneNumber()
        informationReceiver()











    }

    private fun isValidMobile(mobile_number: String): Boolean {
        return MOBILE_NUM_PATTERN_REX.matcher(mobile_number).matches()
    }



    private fun setVisibilityOfErrors(errUserType: Int, errMobileNum: Int) {
        tv_error_user_type.visibility = errUserType
        tv_error_mobile.visibility = errMobileNum


        var timer = object : CountDownTimer(4000, 1000) {
            override fun onFinish() {
                tv_error_user_type.visibility = View.GONE
                tv_error_mobile.visibility = View.GONE

            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }
        timer.start()


    }

    private fun setupSpinner(){


        cities_array = resources.getStringArray(R.array.city_name)
        user_type_array = resources.getStringArray(R.array.user_type)

         city_name_selected= cities_array[0]


        if (city_spinner != null) {
            val adapter = ArrayAdapter(this, R.layout.spinner_selected_item, cities_array)
            adapter.setDropDownViewResource(R.layout.spinner_item)
            city_spinner.adapter = adapter
            city_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    city_name_selected = cities_array[0]
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    city_name_selected = city_spinner.selectedItem.toString()

                }
            }
        }


        if (user_type_spinner != null) {
            val adapter = ArrayAdapter(this, R.layout.spinner_selected_item, user_type_array)
            adapter.setDropDownViewResource(R.layout.spinner_item)
            user_type_spinner.adapter = adapter

            user_type_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    if (user_type_spinner.selectedItem == R.string.user_type) {

                        user_type_selected = ""
                    } else {
                        tv_error_user_type.visibility = View.GONE
                    }
                    user_type_selected = user_type_spinner.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    user_type_selected = ""
                }
            }

        }


    }


    private fun setupPhoneNumber(){
        phone_number_et.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                tv_error_mobile.visibility = View.INVISIBLE
                return true
            }

        })
    }


    private fun informationReceiver(){
        code_receive_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var mobile_number = phone_number_et.text.toString()
                var isValidMobileNum = isValidMobile(mobile_number)
                var isUserTypeSelected =
                    !(TextUtils.isEmpty(user_type_selected) || user_type_selected.equals(user_type_array[0]))
                if (!isUserTypeSelected && isValidMobileNum) {
                    setVisibilityOfErrors(View.VISIBLE, View.GONE)

                } else if (!isValidMobileNum && isUserTypeSelected) {
                    setVisibilityOfErrors(View.GONE, View.VISIBLE)

                } else if (!isValidMobileNum && !isUserTypeSelected) {
                    setVisibilityOfErrors(View.VISIBLE, View.VISIBLE)

                } else {
                    setVisibilityOfErrors(View.GONE, View.GONE)
                    var userInf = JSONObject()
                    userInf.put(KEY_CITY_NAME, city_name_selected)
                    userInf.put(KEY_USER_TYPE, user_type_selected)
                    userInf.put(KEY_MOBILE_NUMBER, mobile_number)

                    var isConnect = checkInternetConnection()
                    if (isConnect) {
                        val apiService = ApiService(this@LoginActivity)
                        apiService.userAuthenticate(userInf, this@LoginActivity)

                        intent = Intent(this@LoginActivity, Authentication::class.java)
                        intent.putExtra("userInf", userInf.toString())
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@LoginActivity, R.string.error_network_connection, Toast.LENGTH_LONG).show()

                    }


                }


            }

        })


    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        return isConnected

    }

    override fun onAuthenticate(result: Boolean) {
        if (result) {
            intent = Intent(this, Authentication::class.java)
            startActivity(intent)
        } else {
            // نمایش خطا
        }
    }

}

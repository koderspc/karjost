package com.example.karjost

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ApiService(var context:Context){

    fun onSignup( jsonObject:JSONObject,  onSighnUpComplete: OnSignupComplete ){
        val request =  JsonObjectRequest(Request.Method.POST,
            "",
            jsonObject,
            Response.Listener<JSONObject> {response ->    },
            Response.ErrorListener { error ->  })

        request.setRetryPolicy(DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) )
                Volley.newRequestQueue(context).add(request)
    }


    interface OnSignupComplete{
        fun onSignup(success: Boolean)
    }

    fun getAppData(){
        val request =  JsonObjectRequest(Request.Method.GET,
            "",
            null,
            Response.Listener<JSONObject> {response ->    },
            Response.ErrorListener { error ->  })

        request.setRetryPolicy(DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) )
        Volley.newRequestQueue(context).add(request)
    }

    fun userAuthenticate(userInf: JSONObject, onUserAuthenticate: OnUserAuthenticate){
        val request =  JsonObjectRequest(Request.Method.POST,
            "",
            userInf,
            Response.Listener<JSONObject> {response ->    },
            Response.ErrorListener { error ->
                onUserAuthenticate.onAuthenticate(false)
            })

        request.setRetryPolicy(DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) )
        Volley.newRequestQueue(context).add(request)
    }


    fun userLogin(){
        val request =  JsonObjectRequest(Request.Method.POST,
            "",
            null,
            Response.Listener<JSONObject> {response ->    },
            Response.ErrorListener { error ->
            })

        request.setRetryPolicy(DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) )
        Volley.newRequestQueue(context).add(request)
    }


    interface OnUserAuthenticate{
        fun onAuthenticate(result:Boolean)
    }

}
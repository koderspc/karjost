package com.example.karjost

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtils (var context: Context){
    val LOGIN_PREFERENCE_NAME = "login"
    val IS_LOGIN = "isLogin"
    val sharedPreferences = context.getSharedPreferences(LOGIN_PREFERENCE_NAME, Context.MODE_PRIVATE)


    fun isLogin():Boolean{
        var isLogin = sharedPreferences.getBoolean(IS_LOGIN, false)
        return isLogin
    }

    fun signUpUser(){
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGIN, true)
        editor.apply()
    }

}
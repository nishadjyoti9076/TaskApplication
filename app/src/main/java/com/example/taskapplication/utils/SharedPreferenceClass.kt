package com.example.taskapplication.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.di_3rddaggerwithmvvmapp.utils.Constant
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.gson.Gson


class SharedPreferenceClass(context: Context) {
    private val USER_PREFS = "NavCom"
    private val appSharedPrefs: SharedPreferences =
        context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE)

    private val prefsEditor: SharedPreferences.Editor = appSharedPrefs.edit()

    fun getValue_int(intKeyValue: String): Int {
        return appSharedPrefs.getInt(intKeyValue, 0)
    }

    fun getString(stringKeyValue: String): String {
        return appSharedPrefs.getString(stringKeyValue, "") ?: ""
    }

    fun getBoolean(stringKeyValue: String): Boolean {
        return appSharedPrefs.getBoolean(stringKeyValue, false)
    }

    fun setInfo(name: String,age : String,mobile : String) {
        prefsEditor.putString(Constant.NAME, name).commit()
        prefsEditor.putString(Constant.AGE, age).commit()
        prefsEditor.putString(Constant.MOBILE_NO, mobile).commit()
    }

    fun getName() : String{
       return appSharedPrefs.getString(Constant.NAME, "") ?: ""
    }
    fun getAge() : String{
        return appSharedPrefs.getString(Constant.AGE, "") ?: ""
        appSharedPrefs.getString(Constant.MOBILE_NO, null)
    }
    fun getMobile() : String{
        return appSharedPrefs.getString(Constant.MOBILE_NO, "") ?: ""
    }

    fun setProfileData(key: String, result: GoogleSignInResult) {
        val gson = Gson()
        val json = gson.toJson(result)
        prefsEditor.putString(key, json).commit()
    }

    fun getProfileData(key: String): GoogleSignInResult? {
        val json= appSharedPrefs.getString(key,null)
        val gson = Gson()
        return gson.fromJson(json, GoogleSignInResult::class.java)
    }

    fun setIsSigned(stringKeyValue: String, _bool: Boolean) {
        prefsEditor.putBoolean(stringKeyValue, _bool).commit()
    }

    fun getIsSigned(stringKeyValue: String) : Boolean {
        return appSharedPrefs.getBoolean(stringKeyValue, false)
    }

    fun setValue_int(intKeyValue: String) {
        prefsEditor.putInt(intKeyValue, 0).commit()
    }

    fun clearData() {
        prefsEditor.clear().commit()
    }

    fun getBoolean(stringKeyValue: String, _stringValue: Boolean) {
        prefsEditor.putBoolean(stringKeyValue, _stringValue).commit()
    }
}


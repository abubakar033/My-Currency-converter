package com.applications.currencyconverter.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferences @Inject constructor(@ApplicationContext context: Context) {

    private var prefs=context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun setSaveValue(key:String, value:String) {
        val editor=prefs.edit()
        editor.putString(key,value)
        editor.apply()
    }
    fun getSaveValue(key: String) : String? {
        return prefs.getString(key,"")
    }

}
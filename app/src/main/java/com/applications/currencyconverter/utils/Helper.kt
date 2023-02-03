package com.applications.currencyconverter.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object {

        fun hideKeyboard(view: View) {
            try {
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {

            }
        }

        fun showSnackBar(
            activity: Activity,
            message: String,
            duration: Int = Snackbar.LENGTH_SHORT
        ) {
            val snackBar =
                Snackbar.make(activity.findViewById(android.R.id.content), message, duration)
            snackBar.show()
        }


        fun showProgressBar(
            activity: Context,
            title: String = "",
            message: String = "",
        ): ProgressDialog {
            val progressDialog = ProgressDialog(activity)
            progressDialog.setTitle(title)
            progressDialog.setMessage(message)
            return progressDialog
        }


        fun <T : Any> handleGenericResponse(
            response: Response<T>,
            mutableResponse: MutableLiveData<NetworkResult<T>>
        ) {
            try {
                if (response.isSuccessful && response.body() != null && response.code() == 200) {
                    mutableResponse.postValue(NetworkResult.Success(response.body()))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    mutableResponse.postValue(NetworkResult.Error(errorObj.getString("message")))
                } else {
                    mutableResponse.postValue(NetworkResult.Error("something went wrong"))
                }
            } catch (e: Exception) {
                mutableResponse.postValue(NetworkResult.Error(e.message))
            }
        }

    }


}
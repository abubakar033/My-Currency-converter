package com.applications.currencyconverter.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.applications.currencyconverter.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
object ExtensionFunction {


    internal fun View.showSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
        val snackbar = Snackbar.make(this, message, 2000)
        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(resources.getColor(R.color.black))
        action?.let { snackbar.it() }
        snackbar.show()
    }

}
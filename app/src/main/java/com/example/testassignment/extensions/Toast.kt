package com.example.testassignment.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.example.testassignment.R
import com.example.testassignment.domain.error.ErrorEntity

@MainThread
fun Fragment.toastSh(msg: String) {
    this.context?.takeIf { isAdded }?.let {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }
}

@MainThread
fun Fragment.toastErrorSh(errorEntity: ErrorEntity?) {
    if (errorEntity?.msg != null) toastSh(errorEntity.msg)
    else toastSh(resources.getString(R.string.error_unknown))
}

@MainThread
fun Fragment.toastThrowableSh(throwable: Throwable?) {
    if (throwable?.message != null) toastSh(throwable.message!!)
    else toastSh(resources.getString(R.string.error_unknown))
}

@MainThread
fun Fragment.toastLng(msg: String) {
    this.context?.takeIf { isAdded }?.let {
        Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show()
    }
}

@MainThread
fun Context.toastSh(msg: String) {
    Toast.makeText(this.applicationContext, msg, Toast.LENGTH_SHORT).show()
}

@MainThread
fun Context.toastLng(msg: String) {
    Toast.makeText(this.applicationContext, msg, Toast.LENGTH_LONG).show()
}
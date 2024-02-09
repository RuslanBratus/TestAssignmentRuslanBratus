package com.example.testassignment.extensions

import android.content.Context
import com.example.testassignment.R

fun convertMoneyToString(amount: Double, context: Context): String {
    return if (amount < 0)
        context.getString(
            R.string.transaction_minus_amount_placeholder,
            amount*-1
        )
    else context.getString(
        R.string.transaction_plus_amount_placeholder,
        amount
    )
}
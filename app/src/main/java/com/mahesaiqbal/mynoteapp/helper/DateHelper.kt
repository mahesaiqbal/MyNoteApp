package com.mahesaiqbal.mynoteapp.helper

import java.text.SimpleDateFormat
import java.util.*

class DateHelper {

    companion object {

        fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
            val date = Date()

            return dateFormat.format(date)
        }
    }
}
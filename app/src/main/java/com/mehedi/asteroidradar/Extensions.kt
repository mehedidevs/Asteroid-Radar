package com.mehedi.asteroidradar

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun String.toDate(): Date {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATABASE_DATE_FORMAT)
        val localDate = LocalDate.parse(this, formatter)
        Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    } else {
        val dateFormat = SimpleDateFormat(Constants.DATABASE_DATE_FORMAT, Locale.US)
        dateFormat.parse(this) as Date
    }

}
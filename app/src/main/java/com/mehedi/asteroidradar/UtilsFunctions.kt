package com.mehedi.asteroidradar

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun today(): String {
    val dateFormat = SimpleDateFormat(Constants.DATABASE_DATE_FORMAT, Locale.US)
    val todayDate = Date()
    return dateFormat.format(todayDate)
}

fun seventhDay(): String {
    val calendar = Calendar.getInstance()
    // Add 7 days to today's date
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val dateAfter7Days = calendar.time
    // Format the date in "yyyy-MM-dd" format
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(dateAfter7Days)

}

package com.example.asteroidradar
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("WeekBasedYear")
private val dateFormatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ENGLISH)

fun String.toDate(): Date {
    return dateFormatter.parse(this)!!
}

fun Date.toFormattedString(): String {
    return dateFormatter.format(this)
}

object DateUtil {

    fun today(): Date {
        return Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }

    fun getNextWeek(
        daysAmount: Int,
        startDate: Date = today()
    ): Date {
        return Calendar.getInstance().apply {
            time = startDate
            add(Calendar.DAY_OF_YEAR, daysAmount)
        }.time
    }
}
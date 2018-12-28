package com.tumoyakov.demonavigation

import java.text.SimpleDateFormat
import java.util.*

fun setDateByPattern(day: Int, month: Int, year: Int): String{
    var monthStr = if (month < 9) "0${month + 1}" else "${month + 1}"
    var dayStr = if (day < 10) "0$day" else "$day"
    return "$dayStr/$monthStr/$year"
}

fun String.convertStrToDate(): Date = SimpleDateFormat("dd.MM.yyyy").parse(this)

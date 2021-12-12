package com.example.composechat.core.ext

import java.text.SimpleDateFormat
import java.util.*

fun Long.dayFromTimestamp(): String {
    val formatter = SimpleDateFormat("EEEE")
    val dateFormat = Date(this)
    return formatter.format(dateFormat)
}

fun Long.timeFromTimestamp(): String {
    val formatter = SimpleDateFormat("HH:mm")
    val dateFormat = Date(this)
    return formatter.format(dateFormat)
}

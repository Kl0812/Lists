package com.example.lists.listUtils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SameDateTimeCheck(createDateTime: LocalDateTime, now: LocalDateTime): String {
    var value: String = ""

    if (createDateTime.hour == now.hour) {
        value += "hour"
    }
    if (createDateTime.toLocalDate() == now.toLocalDate()) {
        value += "day"
    }
    if (createDateTime.year == now.year && createDateTime.month == now.month) {
        value += "month"
    }
    if (createDateTime.year == now.year) {
        value += "year"
    }

    return value

}
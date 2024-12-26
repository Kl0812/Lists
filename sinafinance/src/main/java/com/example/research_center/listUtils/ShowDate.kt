package com.example.research_center.listUtils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.research_center.domain.model.Stock
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDate(stock: Stock) {
    // Declare date format
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // Transform date to LocalDateTime
    val createDateTime = LocalDateTime.parse(stock.adddate, dateTimeFormatter)

    // Get current date and time
    val now = LocalDateTime.now()

    // Calculate time gap
    val duration = Duration.between(createDateTime, now)
    val minutes = duration.toMinutes()

    /*
    * The time check and show function of below code is working
    * However, the json data fetched form sina api doesn't show the time, only date
    * Such as "adddate": "2024-12-26 00:00:00"
    * So only show the date for now. If the time is given
    * Remove below val and Text, and un-comment code below
    * Then the time check function can work normally
    * */
    val dateOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    Text(
        text =  createDateTime.format(dateOnlyFormatter),
        fontSize = 12.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center
    )

    /*// If in same day
    if (createDateTime.toLocalDate() == now.toLocalDate()) {
        // If in one hour
        if (minutes < 60) {
            Text(
                text = "${minutes}分钟前",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            // If not in one hour
        } else {
            val timeOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm")
            Text(
                text = "今天 ${createDateTime.format(timeOnlyFormatter)}",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        // If not in same day but same year
    } else if (createDateTime.year == now.year) {
        val timeAndDateFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm")
        Text(
            text = createDateTime.format(timeAndDateFormatter),
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        // If not in same year
    } else {
        val dateOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        Text(
            text =  createDateTime.format(dateOnlyFormatter),
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }*/
}
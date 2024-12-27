package com.example.research_center.listUtils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTopBar(
    title: String,
    showReturnButton: Boolean = false,
    onReturnClick: () -> Unit = {},
    showDivider: Boolean = true
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
                .padding(8.dp)
        ) {

            // Show return button if needed
            if (showReturnButton) {
                IconButton(
                    onClick = onReturnClick,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .wrapContentHeight()
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Return Button",
                        tint = Color.Gray
                    )
                }
            }

            // Title
            Text(
                text = title,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Divider
        if (showDivider) {
            HorizontalDivider(
                thickness = 0.2.dp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "CustomTopBar Preview",
    showBackground = true
)
@Composable
fun CustomTopBarPreview() {

    CustomTopBar(
        title = "研报中心",
        showReturnButton = true
    )
}

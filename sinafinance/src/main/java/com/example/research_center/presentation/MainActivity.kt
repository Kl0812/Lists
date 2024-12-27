package com.example.research_center.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.research_center.listUtils.SystemBarsColorChanger
import com.example.research_center.presentation.research_center.ResearchCenterScreen
import com.example.research_center.presentation.stock_detail.StockDetailScreen
import com.example.research_center.presentation.ui.theme.ListsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ListsTheme {
                // Change color of system bars
                SystemBarsColorChanger(
                    statusBarColor = Color.White,
                    navigationBarColor = Color.White,
                    isLightIcons = false // Color of system status icon
                )

                val navController = rememberNavController()

                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    // System bar padding
                    .windowInsetsPadding(WindowInsets.systemBars)
                ){ innerPadding ->
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

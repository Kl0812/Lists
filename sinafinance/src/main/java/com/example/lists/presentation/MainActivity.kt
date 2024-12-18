package com.example.lists.presentation

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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lists.presentation.stock_detail.StockDetailScreen
import com.example.lists.presentation.stock_list.StockListScreen
import com.example.lists.presentation.ui.theme.ListsTheme
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
                    isLightIcons = true
                )
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars) // System Bar Padding
                ){ innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.StockListScreen.route,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {
                        composable (
                            route = Screen.StockListScreen.route
                        ) {
                            StockListScreen(navController)
                        }
                        composable (
                            route = Screen.StockDetailScreen.route + "/{wapUrl}",
                            arguments = listOf(navArgument("wapUrl") {
                                type = NavType.StringType
                            }),
                            enterTransition = {
                                slideIntoContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    animationSpec = tween(300, easing = EaseOut),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            }
                        ) { backStackEntry ->
                            val wapUrl = backStackEntry.arguments?.getString("wapUrl") ?: ""
                            StockDetailScreen(navController, url = wapUrl)
                        }
                    }
                }
            }
        }
    }
}

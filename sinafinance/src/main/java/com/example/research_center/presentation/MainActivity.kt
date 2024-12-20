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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.research_center.listUtils.SystemBarsColorChanger
import com.example.research_center.presentation.stock_ranking.stock_detail.StockDetailScreen
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
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    // System Bar Padding, not needed because TopAppBar will handle it
                    // .windowInsetsPadding(WindowInsets.systemBars)
                ){ innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ResearchCenterScreen.route,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {
                        // Research Center Screen
                        composable (
                            route = Screen.ResearchCenterScreen.route
                        ) {
                            ResearchCenterScreen(navController)
                        }

                        // Stock Details Screen
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

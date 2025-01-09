package com.example.research_center.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.research_center.presentation.qs_symbol_list.QsSymbolListScreen
import com.example.research_center.presentation.research_center.ResearchCenterScreen
import com.example.research_center.presentation.report_detail.ReportDetailScreen
import com.example.research_center.presentation.stock.StockScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ResearchCenterScreen.route,
    ) {
        composable(
            route = Screen.ResearchCenterScreen.route,
        ) {
            ResearchCenterScreen(navController)
        }

        composable(
            route = Screen.ReportDetailScreen.route + "/{rptid}",
        ) {
            ReportDetailScreen(navController)
        }

        composable(
            route = Screen.QsSymbolScreen.route + "/{qs_code}",
        ) {
            QsSymbolListScreen(navController)
        }

        composable(
            route = Screen.StockScreen.route + "/{stock_code}",
        ) {
            StockScreen(navController)
        }
    }
}
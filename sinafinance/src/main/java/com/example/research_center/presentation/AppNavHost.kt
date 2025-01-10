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
import com.example.research_center.presentation.report_detail.ReportDetailScreen
import com.example.research_center.presentation.research_center.ResearchCenterScreen
import com.example.research_center.presentation.stock.StockScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ResearchCenterScreen.route,
        // Move forward
        enterTransition = {
            // 新页面从右侧进入 => 向左方向滑入
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300, easing = EaseIn)
            )
        },
        exitTransition = {
            // 旧页面保持不动
            ExitTransition.None
        },
        // Move back
        popEnterTransition = {
            // 新页面保持不动
            EnterTransition.None
        },
        popExitTransition = {
            // 当前页面向右退出 => 向右方向滑出
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300, easing = EaseOut)
            )
        }

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
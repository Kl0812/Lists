package com.example.lists.presentation.stock_detail

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

/*
* Use a Android view to load a web view to
* jump to the given url and show the content
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailScreen(
    navController: NavController,
    url: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("研报中心") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "return"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Show web view
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }

}

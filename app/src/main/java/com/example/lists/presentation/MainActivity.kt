package com.example.lists.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lists.presentation.dog_details.DogDetailScreen
import com.example.lists.presentation.dog_list.DogListScreen
import com.example.lists.presentation.ui.theme.ListsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListsTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination =  Screen.DogListScreen.route
                ) {
                    composable (
                        route = Screen.DogListScreen.route
                    ) {
                        DogListScreen(navController)
                    }
                    composable (
                        route = Screen.DogDetailScreen.route + "/{dogId}"
                    ) {
                        DogDetailScreen()
                    }
                }
            }
        }
    }
}

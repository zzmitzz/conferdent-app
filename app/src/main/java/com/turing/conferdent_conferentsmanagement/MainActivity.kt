package com.turing.conferdent_conferentsmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.turing.conferdent_conferentsmanagement.ui.ConferdentApp
import com.turing.conferdent_conferentsmanagement.ui.ConferdentAppState
import com.turing.conferdent_conferentsmanagement.ui.rememberConferdentAppState
import com.turing.conferdent_conferentsmanagement.ui.theme.ConferdentConferentsManagementTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val appState = rememberConferdentAppState(
                navController = navController,
            )
            ConferdentConferentsManagementTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ConferdentApp(
                        navController = navController,
                        appState = appState,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

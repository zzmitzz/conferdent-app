package com.ptit_booth_chekin.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.rememberNavController
import com.ptit_booth_chekin.project.ui.ConferdentApp
import com.ptit_booth_chekin.project.ui.ConferdentAppState
import com.ptit_booth_chekin.project.ui.rememberConferdentAppState
import com.ptit_booth_chekin.project.ui.theme.ConferdentConferentsManagementTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


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
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()

            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color("#ECECEE".toColorInt())
                        )
                        .padding(top = innerPadding.calculateTopPadding())
                ) {
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
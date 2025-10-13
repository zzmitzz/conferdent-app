package com.turing.conferdent_conferentsmanagement.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay


sealed class SafeNavKey: NavKey {
    data class Home(val id: Int) : SafeNavKey()
    data class Settings(val id: Int) : SafeNavKey()
}

@Composable
fun ConferdentApp(
    modifier: Modifier,
    backStack: NavBackStack<NavKey>
){
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when(key) {
                else -> {
                    error("Unknown route: $key")
                }
            }
        }
    )
}
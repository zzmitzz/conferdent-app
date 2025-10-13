package com.turing.conferdent_conferentsmanagement.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.CoroutineScope



@Composable
fun rememberConferdentAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    backStack: NavBackStack<NavKey>
): ConferdentAppState {
    return remember(
        coroutineScope,
        backStack
    ) {
        ConferdentAppState(
            coroutineScope = coroutineScope,
            backStack = backStack
        )
    }
}

@Stable
class ConferdentAppState(
    val coroutineScope: CoroutineScope,
    val backStack: NavBackStack<NavKey>,
) {

}
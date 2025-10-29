package com.turing.conferdent_conferentsmanagement.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.turing.conferdent_conferentsmanagement.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@Composable
fun rememberConferdentAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController
): ConferdentAppState {
    return remember(
        coroutineScope,
        navController
    ) {
        ConferdentAppState(
            coroutineScope = coroutineScope,
            navController = navController
        )
    }
}

@Stable
class ConferdentAppState(
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {

    private var _isShownBottomNav = MutableStateFlow(false)
    val isShownBottomNav = _isShownBottomNav.asStateFlow()

    private var _currentTopLevelDestination = MutableStateFlow(TopLevelDestination.HOME)
    val currentTopLevelDestination = _currentTopLevelDestination.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        if (currentTopLevelDestination.value != destination) {
            _currentTopLevelDestination.value = destination
            navController.navigate(destination.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    private fun checkIfConnected(): Boolean {
        return true
    }

    fun setVisibleBottomNav(isVisible: Boolean) {
        _isShownBottomNav.value = isVisible
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}
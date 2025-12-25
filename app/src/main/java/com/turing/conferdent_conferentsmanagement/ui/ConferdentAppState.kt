package com.turing.conferdent_conferentsmanagement.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.turing.conferdent_conferentsmanagement.navigation.TopLevelDestination
import com.turing.conferdent_conferentsmanagement.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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
            context = navController.context,
            navController = navController
        )
    }
}

@Stable
class ConferdentAppState(
    val coroutineScope: CoroutineScope,
    val context: Context,
    val navController: NavHostController,
) {

    private var _isShownBottomNav = MutableStateFlow(false)
    val isShownBottomNav = _isShownBottomNav.asStateFlow()

    private var _currentTopLevelDestination : MutableStateFlow<TopLevelDestination> = MutableStateFlow(TopLevelDestination.Auth)
    val currentTopLevelDestination = _currentTopLevelDestination.asStateFlow()

    var networkConnectState = MutableStateFlow(false)


    init {
        startObserveNetworkState()
    }

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        if (currentTopLevelDestination.value != destination) {
            _currentTopLevelDestination.value = destination
            navController.navigate(destination.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = false
            }
        }
    }
    fun startObserveNetworkState(){
        coroutineScope.launch {
            NetworkUtils.networkStateFlow(context).collect{
                networkConnectState.value = it
            }
        }
    }


    fun setVisibleBottomNav(isVisible: Boolean) {
        _isShownBottomNav.value = isVisible
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}
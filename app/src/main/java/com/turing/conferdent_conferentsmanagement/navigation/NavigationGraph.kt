package com.turing.conferdent_conferentsmanagement.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.turing.conferdent_conferentsmanagement.ui.ConferdentAppState
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.login.AuthScreenStateful
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.register.RegisterStateful


@Composable
fun NavigationGraph(
    navController: NavHostController,
    appState: ConferdentAppState
) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        navigation(
            startDestination = Routes.Login.route,
            route = "auth"
        ) {
            composable(
                route = Routes.Login.createRoute(),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(100)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(100)
                    )
                }) {
                appState.setVisibleBottomNav(false)
                AuthScreenStateful(
                    onNavRegister = {
                        navController.navigate(Routes.Register.createRoute()) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                )
            }
            composable(
                route = Routes.Register.createRoute(),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(100)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(100)
                    )
                }) {
                appState.setVisibleBottomNav(false)
                RegisterStateful(
                    onNavLogin = {
                        navController.navigate(Routes.Login.createRoute()) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
        }
        navigation(
            startDestination = Routes.Home.route,
            route = "home"
        ) {
            composable(route = Routes.Home.createRoute()) {
                appState.setVisibleBottomNav(true)
            }
        }
        navigation(
            startDestination = Routes.Home.route,
            route = "home"
        ) {
            composable(route = Routes.Home.createRoute()) {
                appState.setVisibleBottomNav(true)
            }
        }
        navigation(
            startDestination = Routes.Home.route,
            route = "home"
        ) {
            composable(route = Routes.Home.createRoute()) {
                appState.setVisibleBottomNav(true)
            }
        }
        navigation(
            startDestination = Routes.Home.route,
            route = "home"
        ) {
            composable(route = Routes.Home.createRoute()) {
                appState.setVisibleBottomNav(true)
            }
        }

    }
}
package com.turing.conferdent_conferentsmanagement.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.turing.conferdent_conferentsmanagement.ui.ConferdentAppState
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.login.AuthScreenStateful
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.register.RegisterStateful
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_favourite.RegisteredEventScreenStateful
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.ScreenHome
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_search.SearchScreen
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.ScreenSetting

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    appState: ConferdentAppState
) {
    AnimatedNavHost(
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
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
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
                    onNavHome = {
                        navController.navigate(Routes.Home.createRoute()) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(
                route = Routes.Register.createRoute(),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
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
            composable(
                route = Routes.Home.createRoute(),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }) {
                appState.setVisibleBottomNav(true)
                ScreenHome(
                    onNavSearch = {
                        navController.navigate(Routes.Search.createRoute()) {

                        }
                    }

                )
            }
            composable(route = Routes.Search.createRoute()) {
                appState.setVisibleBottomNav(false)
                SearchScreen(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        navigation(
            startDestination = Routes.Setting.route,
            route = "setting"
        ) {
            composable(
                route = Routes.Setting.createRoute(),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }
            ) {
                appState.setVisibleBottomNav(true)
                ScreenSetting()
            }
        }
        navigation(
            startDestination = Routes.Favourite.route,
            route = "favourite"
        ) {
            composable(
                route = Routes.Favourite.createRoute(),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }) {
                appState.setVisibleBottomNav(true)
                RegisteredEventScreenStateful()
            }
        }

    }
}
package com.turing.conferdent_conferentsmanagement.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.turing.conferdent_conferentsmanagement.ui.ConferdentAppState
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.login.AuthScreenStateful
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.register.RegisterStateful
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.ConferenceSession
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.ConferenceSessionVM
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.MainEventScreen
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.MainEventVM
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_category_filter.CategoryFilterScreen
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_checkin.ScreenCheckInQR
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_favourite.RegisteredEventScreenStateful
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form.ScreenFillForm
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.ScreenHome
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_notification.ScreenNotification
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_resource.ScreenResourceEvent
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_search.SearchScreen
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.ScreenSetting
import androidx.core.net.toUri
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.MainEventVMState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    appState: ConferdentAppState
) {

    val context: Context = LocalContext.current

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
                        appState.navigateToTopLevelDestination(TopLevelDestination.HOME)
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
//                enterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right,
//                        animationSpec = tween(500)
//                    )
//                },
//                exitTransition = {
//                    slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        animationSpec = tween(500)
//                    )
//                },
//                popEnterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        animationSpec = tween(500)
//                    )
//                },
//                popExitTransition = {
//                    slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right,
//                        animationSpec = tween(500)
//                    )
//                }
                ) {
                appState.setVisibleBottomNav(true)
                ScreenHome(
                    onNavSearch = {
                        navController.navigate(Routes.Search.createRoute()) {}
                    },
                    onNavEventDetail = {
                        navController.navigate(Routes.EventDetail.createRoute(it)) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    onNavCategoryFilter = { categoryType ->
                        navController.navigate(Routes.CategoryFilter.createRoute(categoryType)) {
                            launchSingleTop = true
                            restoreState = false
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

            composable(
                route = Routes.EventDetail.route,
//                enterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right,
//                        animationSpec = tween(500)
//                    )
//                },
//                exitTransition = {
//                    slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        animationSpec = tween(500)
//                    )
//                },
//                popEnterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        animationSpec = tween(500)
//                    )
//                },
//                popExitTransition = {
//                    slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right,
//                        animationSpec = tween(500)
//                    )
//                },
                arguments = listOf(
                    navArgument(Routes.EVENT_ID) {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                appState.setVisibleBottomNav(false)
                val eventID = entry.arguments?.getString(Routes.EVENT_ID)

                val parentEntry = remember(navController) {
                    navController.getBackStackEntry("home")
                }
                val viewModel: MainEventVM = hiltViewModel(parentEntry)
                MainEventScreen(
                    eventID,
                    navigateBack = {
                        navController.popBackStack()
                    },
                    navigateRegister = {
                        navController.navigate(Routes.EventRegister.createRoute(eventID = eventID!!)) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    viewModel = viewModel,
                    onCheckIn = {
                        navController.navigate(Routes.CheckInQR.createRoute())
                        {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    onMapClick = {
                        try {
                            val url = (viewModel.uiState.value as MainEventVMState.Success).event.maps
                            url?.let {
                                val intent = Intent(Intent.ACTION_VIEW, it.toUri())
                                context.startActivity(intent)
                            }
                        }catch (e: Exception){

                        }
                    },
                    onScheduleClick = {
                        navController.navigate(Routes.EventSession.createRoute(eventID = eventID!!))
                    },
                    onSpeakerClick = { speakerId ->
                        navController.navigate(Routes.SpeakerDetail.createRoute(speakerId, eventID!!)) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    onResourceClick = {
                        navController.navigate(Routes.ResourceScreen.createRoute(eventID!!)) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }

            composable(
                route = Routes.CheckInQR.route,
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
                },
            ) { entry ->
                appState.setVisibleBottomNav(false) // Hide bottom nav for detail/action screens
                val parentEntry = remember(navController) {
                    navController.getBackStackEntry("home")
                }
                val viewModel: MainEventVM = hiltViewModel(parentEntry)
                ScreenCheckInQR(
                    navBack = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel // Pass the shared ViewModel
                )
            }
            composable(
                route = Routes.EventRegister.route,
                arguments = listOf(
                    navArgument(Routes.EVENT_ID) {
                        type = NavType.StringType
                    }
                )
            ) {
                appState.setVisibleBottomNav(false)
                val eventID = it.arguments?.getString(Routes.EVENT_ID)
                ScreenFillForm(
                    navBack = {
                        navController.popBackStack()
                    },
                    navNextScreen = {
                        navController.navigate(Routes.EventDetail.createRoute(eventID)) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    eventID = eventID!!
                )
            }

            composable(
                route = Routes.EventSession.route,
                arguments = listOf(
                    navArgument(Routes.EVENT_ID) {
                        type = NavType.StringType
                    }
                )
            ) {
                appState.setVisibleBottomNav(false)
                val viewModel = hiltViewModel<ConferenceSessionVM>()
                ConferenceSession(
                    onNavBack = {
                        navController.popBackStack()
                    },
                    viewModel
                )
            }

            composable(
                route = Routes.CategoryFilter.route,
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
                },
                arguments = listOf(
                    navArgument(Routes.CATEGORY_TYPE) {
                        type = NavType.StringType
                    }
                )
            ) {
                appState.setVisibleBottomNav(false)
                CategoryFilterScreen(
                    onNavBack = {
                        navController.popBackStack()
                    },
                    onNavEventDetail = { eventId ->
                        navController.navigate(Routes.EventDetail.createRoute(eventId)) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }

            composable(
                route = Routes.SpeakerDetail.route,
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
                },
                arguments = listOf(
                    navArgument(Routes.SPEAKER_ID) {
                        type = NavType.StringType
                    },
                    navArgument(Routes.EVENT_ID) {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                appState.setVisibleBottomNav(false)
                val speakerId = entry.arguments?.getString(Routes.SPEAKER_ID)
                val eventId = entry.arguments?.getString(Routes.EVENT_ID)
                val viewModel: com.turing.conferdent_conferentsmanagement.ui.screen.home.speaker.SpeakerVM = hiltViewModel()
                com.turing.conferdent_conferentsmanagement.ui.screen.home.speaker.ScreenSpeaker(
                    speakerId = speakerId,
                    eventId = eventId,
                    viewModel = viewModel,
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Routes.ResourceScreen.route,
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
                },
                arguments = listOf(
                    navArgument(Routes.EVENT_ID) {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                appState.setVisibleBottomNav(false)
                val eventId = entry.arguments?.getString(Routes.EVENT_ID)
                ScreenResourceEvent(
                    eventId = eventId,
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
                ScreenSetting(
                    resetToLogin = {
                        appState.navigateToTopLevelDestination(TopLevelDestination.Auth)
                    },
                    viewModel = hiltViewModel()
                )
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

        composable(
            route = Routes.Notification.route,
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
            ScreenNotification()
        }


    }
}
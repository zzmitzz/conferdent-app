package com.turing.conferdent_conferentsmanagement.navigation

import androidx.annotation.StringRes
import com.turing.conferdent_conferentsmanagement.R

/**
 * Type for the top level destinations in the application. Contains metadata about the destination
 * that is used in the top app bar and common navigation UI.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is
 * selected.
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is
 * not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 * @param route The route to use when navigating to this destination.
 */
enum class TopLevelDestination(
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    @StringRes val iconText: Int,
    @StringRes val titleTextId: Int,
    val route: String
) {
    HOME(
        selectedIcon = R.drawable.ic_home_selected,
        unSelectedIcon = R.drawable.ic_home,
        iconText = R.string.home,
        titleTextId = R.string.home,
        route = Routes.Home.createRoute()
    ),
    FAVOURITE(
        selectedIcon = R.drawable.ic_fav_selected,
        unSelectedIcon = R.drawable.ic_favourite,
        iconText = R.string.favourite,
        titleTextId = R.string.favourite,
        route = Routes.Favourite.createRoute()
    ),
    NOTIFICATION(
        selectedIcon = R.drawable.ic_noti_selected,
        unSelectedIcon = R.drawable.ic_noti,
        iconText = R.string.notification,
        titleTextId = R.string.notification,
        route = Routes.Notification.createRoute()
    ),
    SETTING(
        selectedIcon = R.drawable.ic_setting_selected,
        unSelectedIcon = R.drawable.ic_setting,
        iconText = R.string.setting,
        titleTextId = R.string.setting,
        route = Routes.Setting.createRoute()
    )

}

sealed class Routes(
    val route: String
) {

    // Authentication
    data object Login : Routes("login") {
        fun createRoute() = "login"
    }

    data object Register : Routes("register") {
        fun createRoute() = "register"
    }

    // HOME
    data object Home : Routes("home"){
        fun createRoute() = "home"
    }

    data object Favourite : Routes("favourite"){
        fun createRoute() = "favourite"
    }
    data object Notification : Routes("notification"){
        fun createRoute() = "notification"
    }
    data object Setting : Routes("setting"){
        fun createRoute() = "setting"
    }
}
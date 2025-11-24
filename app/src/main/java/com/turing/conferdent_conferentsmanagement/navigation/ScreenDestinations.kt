package com.turing.conferdent_conferentsmanagement.navigation

import androidx.annotation.StringRes
import com.turing.conferdent_conferentsmanagement.R
import okhttp3.Route

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
    data object Home : Routes("home_screen") {
        fun createRoute() = "home_screen"
    }

    data object Favourite : Routes("favourite_screen") {
        fun createRoute() = "favourite_screen"
    }

    data object Notification : Routes("notification_screen") {
        fun createRoute() = "notification_screen"
    }

    data object Setting : Routes("setting_screen") {
        fun createRoute() = "setting_screen"
    }

    data object Search : Routes("search_screen") {
        fun createRoute() = "search_screen"
    }

    data object EventDetail : Routes("event_detail_screen/{${EVENT_ID}}") {
        fun createRoute(eventID: String) = "event_detail_screen/$eventID"
    }

    data object EventRegister : Routes("event_register_screen/{${EVENT_ID}}") {
        fun createRoute(eventID: String) = "event_register_screen/$eventID"
    }
    object CheckInQR : Routes("check_in_qr") {
        fun createRoute() = "check_in_qr"
    }

    companion object {
        const val EVENT_ID = "EVENT_ID"
    }
}
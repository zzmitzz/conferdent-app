package com.ptit_booth_chekin.project.navigation

import androidx.annotation.StringRes
import com.ptit_booth_chekin.project.R
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
    Auth(
        selectedIcon = R.drawable.ic_home_selected,
        unSelectedIcon = R.drawable.ic_home,
        iconText = R.string.home,
        titleTextId = R.string.home,
        route = Routes.Login.createRoute()
    ),
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
    CHATBOT(
        selectedIcon = R.drawable.ic_chatbot_selected,
        unSelectedIcon = R.drawable.ic_chatbot,
        iconText = R.string.chatbot,
        titleTextId = R.string.chatbot,
        route = "chat"
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

    data object Chat: Routes("chat_screen") {
        fun createRoute() = "chat_screen"
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
    object CheckInQR : Routes("check_in_qr/{${EVENT_ID}}") {
        fun createRoute(eventID: String) = "check_in_qr/$eventID"
    }

    data object EventSession: Routes("event_session_screen/{${EVENT_ID}}")
    {
        fun createRoute(eventID: String) = "event_session_screen/$eventID"
    }

    data object CategoryFilter : Routes("category_filter_screen/{${CATEGORY_TYPE}}") {
        fun createRoute(categoryType: String) = "category_filter_screen/$categoryType"
    }

    data object SpeakerDetail : Routes("speaker_detail_screen/{${SPEAKER_ID}}/{${EVENT_ID}}") {
        fun createRoute(speakerId: String, eventId: String) = "speaker_detail_screen/$speakerId/$eventId"
    }

    data object ResourceScreen : Routes("resource_screen/{${EVENT_ID}}") {
        fun createRoute(eventId: String) = "resource_screen/$eventId"
    }


    companion object {
        const val EVENT_ID = "EVENT_ID"
        const val CATEGORY_TYPE = "CATEGORY_TYPE"
        const val SPEAKER_ID = "SPEAKER_ID"
    }
}
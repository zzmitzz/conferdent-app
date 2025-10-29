package com.turing.conferdent_conferentsmanagement.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.turing.conferdent_conferentsmanagement.navigation.BottomNavigationBar
import com.turing.conferdent_conferentsmanagement.navigation.NavigationGraph
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.login.AuthScreenStateful
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.register.RegisterStateful
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable


@Preview
@Composable
private fun AppPreview() {

}


@Composable
fun ConferdentApp(
    navController: NavHostController,
    appState: ConferdentAppState,
    modifier: Modifier,
) {
    val isShowBottomNav = appState.isShownBottomNav.collectAsStateWithLifecycle()
    val currDestination = appState.currentTopLevelDestination.collectAsStateWithLifecycle()

    return Box(
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            // Create references for the composable to constrain
            val (nav, bottomBar) = createRefs()

            // NavigationGraph
            Box(
                modifier = Modifier
                    .constrainAs(nav) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            ) {
                NavigationGraph(navController = navController, appState = appState)
            }

            // BottomNavigationBar (conditionally shown)
            if (isShowBottomNav.value) {
                Box(
                    modifier = Modifier
                        .constrainAs(bottomBar) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                ) {
                    BottomNavigationBar(
                        onTopLevelClick = {
                            appState.navigateToTopLevelDestination(it)
                        },
                        currentDestination = currDestination.value
                    )
                }
            }
        }



    }
}
package com.turing.conferdent_conferentsmanagement.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.turing.conferdent_conferentsmanagement.R
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
    val networkState = appState.networkConnectState.collectAsStateWithLifecycle()
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


            if(!networkState.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(
                            alpha = 0.8f
                        ))
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    NetworkErrorDialog()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkErrorDialog(){
    Column(
        modifier = Modifier.padding(
            horizontal = 24.dp
        ).background(
            color = Color.White,
            shape = RoundedCornerShape(16.dp)
        ).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.network_connection),
            modifier = Modifier.size(40.dp),
            contentDescription = null
        )
        Spacer(Modifier.height(12.dp))
        Text("Không có kết nối mạng", color = Color.Black)
    }
}
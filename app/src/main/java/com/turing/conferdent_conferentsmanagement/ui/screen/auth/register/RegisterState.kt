package com.turing.conferdent_conferentsmanagement.ui.screen.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.components.RegisterComponents


@Composable
fun RegisterStateful(
    onNavLogin: () -> Unit = {}
) {
    RegisterStateless(
        onNavLogin = onNavLogin,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun RegisterStateless(
    onNavLogin: () -> Unit = {},
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color("#ECECEE".toColorInt()))
    ) {
        val (bgAuth, authComponent) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(bgAuth) {
                    top.linkTo(parent.top)
                }
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.bg_auth),
                contentDescription = null,

                )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(authComponent) {
                    bottom.linkTo(parent.bottom)
                }
        ) {
            RegisterComponents(
                onNavLogin = onNavLogin
            )
        }
    }
}

@Preview
@Composable
fun AuthScreenPrev() {
    RegisterStateless(
        modifier = Modifier
    )
}


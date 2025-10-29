package com.turing.conferdent_conferentsmanagement.ui.screen.auth.login

import androidx.collection.mutableObjectIntMapOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.core.data.IPersistentStorage
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.AuthenticationVM
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.LoginScreenVMState
import com.turing.conferdent_conferentsmanagement.ui.screen.auth.components.LoginComponents
import javax.inject.Inject


@Composable
fun AuthScreenStateful(
    viewModel: AuthenticationVM = hiltViewModel(),
    onNavHome: () -> Unit,
    onNavRegister: () -> Unit
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(screenState) {
        when (screenState) {
            is LoginScreenVMState.Success -> onNavHome()
            else -> Unit
        }
    }
    LaunchedEffect(Unit) {
        viewModel.checkSavedAccount()
    }

    // 👇 UI rendering remains pure and declarative
    AuthScreenStateless(
        onNavRegister = onNavRegister,
        onLogin = { username, password ->
            viewModel.doLogin(username, password)
        },
        modifier = Modifier.fillMaxSize()
    )

    when (screenState) {
        is LoginScreenVMState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                RoseCurveSpinner()
            }
        }

        is LoginScreenVMState.Error -> {
            // Show error message
        }

        else -> Unit
    }
}


@Composable
fun AuthScreenStateless(
    onLogin: (String, String) -> Unit = { _, _ -> },
    onNavRegister: () -> Unit = {},
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
            LoginComponents(
                onLogin = onLogin,
                onNavRegister = onNavRegister
            )
        }
    }
}

@Preview
@Composable
fun AuthScreenPrev() {
    AuthScreenStateless(
        modifier = Modifier
    )
}


package com.ptit_booth_chekin.project.ui.screen.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.core.ui.RoseCurveSpinner
import com.ptit_booth_chekin.project.ui.screen.auth.AuthenticationVM
import com.ptit_booth_chekin.project.ui.screen.auth.RegisterScreenVMState
import com.ptit_booth_chekin.project.ui.screen.auth.components.RegisterComponents


@Composable
fun RegisterStateful(
    onNavLogin: () -> Unit = {},
    viewModel: AuthenticationVM = hiltViewModel()
) {

    val registerState by viewModel.registerState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(registerState) {
        if (registerState is RegisterScreenVMState.Error) {
            val message = (registerState as RegisterScreenVMState.Error).message
            snackbarHostState.showSnackbar(message)
        }
        if (registerState is RegisterScreenVMState.Success) {
            snackbarHostState.showSnackbar("Register successfully")
            onNavLogin()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        RegisterStateless(
            onNavLogin = onNavLogin,
            modifier = Modifier.fillMaxSize(),
            onRegister = { fullName, email, password, confirmPassword ->
                viewModel.doRegister(fullName, email, password, confirmPassword)
            },
            registerState = if (registerState is RegisterScreenVMState.ErrorInput) (registerState as RegisterScreenVMState.ErrorInput) else null
        )
        when(registerState){
            is RegisterScreenVMState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    RoseCurveSpinner()
                }
            }
            is RegisterScreenVMState.Success -> {

            }
            else -> Unit
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}


@Composable
fun RegisterStateless(
    onNavLogin: () -> Unit = {},
    modifier: Modifier,
    onRegister: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    registerState: RegisterScreenVMState.ErrorInput? = null,
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
                onNavLogin = onNavLogin,
                onClickRegister = { fullName, email, password, confirmPassword ->
                    onRegister(fullName, email, password, confirmPassword)
                },
                onEmailError = registerState?.email,
                onPasswordError = registerState?.password,
                onConfirmPasswordError = registerState?.confirmPassword,
                onUsernameError = registerState?.name,
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


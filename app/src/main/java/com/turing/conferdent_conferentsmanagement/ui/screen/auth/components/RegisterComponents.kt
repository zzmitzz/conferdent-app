package com.turing.conferdent_conferentsmanagement.ui.screen.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans

@Composable
fun RegisterComponents(
    onNavLogin: () -> Unit = {},
    onClickRegister: (
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) -> Unit = { _, _, _, _ -> },
    onUsernameError: String? = null,
    onEmailError: String? = null,
    onPasswordError: String? = null,
    onConfirmPasswordError: String? = null
) {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(
                    topStart = 35.dp,
                    topEnd = 35.dp
                )
            )
            .background(
                color = Color("#ECECEE".toColorInt())
            )
            .padding(top = 38.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.register),
            fontFamily = JosefinSans,
            fontWeight = FontWeight.Bold,
            fontSize = 38.sp
        )


        Box(
            modifier = Modifier
                .padding(
                    start = 45.dp,
                    top = 47.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.full_name),
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
        Column(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                value = fullName,
                onValueChange = {
                    fullName = it
                },
                placeholder = {
                    Text(
                        stringResource(R.string.full_name),
                        color = Color("#B5B4B4".toColorInt())
                    )
                },
                isError = onUsernameError != null,
                singleLine = true,
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                ),

                )
            if (onUsernameError != null) {
                Text(
                    text = onUsernameError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    start = 45.dp,
                    top = 16.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.email),
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
        Column(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                value = email,
                onValueChange = {
                    email = it
                },
                placeholder = {
                    Text(
                        stringResource(R.string.email),
                        color = Color("#B5B4B4".toColorInt())
                    )
                },
                isError = onEmailError != null,
                singleLine = true,
                shape = RoundedCornerShape(100.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                ),
            )
            if (onEmailError != null) {
                Text(
                    text = onEmailError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    start = 45.dp,
                    top = 16.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.password),
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
        Column(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                value = password,
                onValueChange = {
                    password = it
                },
                isError = onPasswordError != null,
                placeholder = {
                    Text(
                        stringResource(R.string.password),
                        color = Color("#B5B4B4".toColorInt())
                    )
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    val icon = if (passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                ),

                )
            if (onPasswordError != null) {
                Text(
                    text = onPasswordError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    start = 45.dp,
                    top = 16.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.password_again),
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
        Column(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                placeholder = {
                    Text(
                        stringResource(R.string.password_again),
                        color = Color("#B5B4B4".toColorInt())
                    )
                },
                isError = onConfirmPasswordError != null,
                visualTransformation = if (confirmPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    val icon = if (confirmPasswordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                ),

                )
            if (onConfirmPasswordError != null) {
                Text(
                    text = onConfirmPasswordError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Box(
            modifier = Modifier.padding(
                vertical = 10.5.dp,
                horizontal = 21.dp
            ),
        ) {
            Button(
                onClick = {
                    onClickRegister(fullName, email, password, confirmPassword)
                },
                modifier = Modifier.wrapContentWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color("#22272F".toColorInt()),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.width(100.dp)
            )
            Spacer(
                modifier = Modifier.width(12.dp)
            )
            Text(
                text = stringResource(R.string.or),
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            Spacer(
                modifier = Modifier.width(12.dp)
            )
            HorizontalDivider(
                modifier = Modifier.width(100.dp)
            )
        }

        Box(
            modifier = Modifier.padding(
                vertical = 10.5.dp,
                horizontal = 21.dp
            ),
        ) {
            Button(
                onClick = {
                    onNavLogin()
                },
                modifier = Modifier.wrapContentWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.login_app),
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(
            modifier = Modifier.height(48.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    RegisterComponents()
}
package com.turing.conferdent_conferentsmanagement.ui.screen.auth.components

import android.text.Layout
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
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans


@Composable
fun LoginComponents(
    onLogin: (String, String) -> Unit = { _, _ -> },
    onNavRegister: () -> Unit = {},
    errorMessage: String? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    
    val errorEmailEmpty = stringResource(R.string.error_email_empty)
    val errorPasswordEmpty = stringResource(R.string.error_password_empty)
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
            text = stringResource(R.string.login),
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
                text = stringResource(R.string.email),
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
        Column {
            Box(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    placeholder = {
                        Text(
                            stringResource(R.string.email),
                            color = Color("#B5B4B4".toColorInt())
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(100.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    isError = emailError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color("#FF6B6B".toColorInt())
                    ),

                    )
            }
            if (emailError != null) {
                Text(
                    text = emailError!!,
                    color = Color("#FF6B6B".toColorInt()),
                    fontSize = 12.sp,
                    fontFamily = JosefinSans,
                    modifier = Modifier.padding(start = 45.dp, top = 4.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    start = 45.dp,
                    top = 30.dp
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
        Column {
            Box(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    placeholder = {
                        Text(
                            stringResource(R.string.password),
                            color = Color("#B5B4B4".toColorInt()),
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(100.dp),
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
                    isError = passwordError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color("#FF6B6B".toColorInt())
                    ),
                )
            }
            if (passwordError != null) {
                Text(
                    text = passwordError!!,
                    color = Color("#FF6B6B".toColorInt()),
                    fontSize = 12.sp,
                    fontFamily = JosefinSans,
                    modifier = Modifier.padding(start = 45.dp, top = 4.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier.height(50.dp)
        )
        
        // Display general error message (e.g., incorrect credentials)
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color("#FF6B6B".toColorInt()),
                fontSize = 14.sp,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
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
                    // Validate fields
                    var hasError = false
                    
                    if (email.isBlank()) {
                        emailError = errorEmailEmpty
                        hasError = true
                    }
                    
                    if (password.isBlank()) {
                        passwordError = errorPasswordEmpty
                        hasError = true
                    }
                    
                    if (!hasError) {
                        onLogin(email, password)
                    }
                },
                modifier = Modifier.wrapContentWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color("#22272F".toColorInt()),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.login),
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
                    onNavRegister()
                },
                modifier = Modifier.wrapContentWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.register_new_account),
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

@Preview(
)
@Composable
fun LoginPrevComponents() {
    LoginComponents()
}
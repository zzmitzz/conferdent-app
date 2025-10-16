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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans

@Composable
fun RegisterComponents(
    onNavLogin: () -> Unit = {}
) {
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
                text = stringResource(R.string.username),
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
        Box(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        stringResource(R.string.username),
                        color = Color("#B5B4B4".toColorInt())
                    )
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
        Box(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        stringResource(R.string.email),
                        color = Color("#B5B4B4".toColorInt())
                    )
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
        Box(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        stringResource(R.string.password),
                        color = Color("#B5B4B4".toColorInt())
                    )
                },
                suffix = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_visibility),
                        contentDescription = "visibility",
                    )
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
        Box(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        stringResource(R.string.password_again),
                        color = Color("#B5B4B4".toColorInt())
                    )
                },
                suffix = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_visibility),
                        contentDescription = "visibility",
                    )
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
                onClick = {},
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
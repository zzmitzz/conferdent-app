package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.LogoutButton
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.ProfileInfoCard
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.SettingsOptionsCard
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans


@Composable
fun ScreenNotification(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFF5F5F5)
            )
            .padding(start = 16.dp, end = 16.dp, top = 50.dp)
    ) {
        Text(
            text = stringResource(R.string.my_notification),
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
            fontSize = 36.sp,
            modifier = Modifier.padding(bottom = 24.dp, start = 8.dp)
        )

    }
}
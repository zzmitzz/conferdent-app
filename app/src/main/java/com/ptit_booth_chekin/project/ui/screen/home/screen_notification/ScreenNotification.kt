package com.ptit_booth_chekin.project.ui.screen.home.screen_notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.core.ui.RoseCurveSpinner
import com.ptit_booth_chekin.project.ui.screen.home.screen_notification.components.NotificationCard
import com.ptit_booth_chekin.project.ui.theme.JosefinSans


@Composable
fun ScreenNotification(
    modifier: Modifier = Modifier,
    notificationScreenVM: ScreenNotificationVM = hiltViewModel()
){
    val uiState by notificationScreenVM.uiState.collectAsStateWithLifecycle()
    
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

        when (uiState) {
            is ScreenNotificationUIState.UISuccess -> {
                val notifications = (uiState as ScreenNotificationUIState.UISuccess).notifications
                if (notifications.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_notifications),
                            fontFamily = JosefinSans,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(notifications) { notification ->
                            NotificationCard(
                                notificationUI = notification,
                                onClick = {
                                    
                                }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(128.dp))
                        }
                    }
                }
            }
            
            is ScreenNotificationUIState.UILoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    RoseCurveSpinner(
                        color = Color.Black
                    )
                }
            }
            
            is ScreenNotificationUIState.UIError -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as ScreenNotificationUIState.UIError).message,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                }
            }
        }
    }
}
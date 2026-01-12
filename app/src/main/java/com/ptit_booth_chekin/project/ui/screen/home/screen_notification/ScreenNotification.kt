package com.ptit_booth_chekin.project.ui.screen.home.screen_notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.core.ui.RoseCurveSpinner
import com.ptit_booth_chekin.project.ui.screen.home.screen_notification.components.NotificationCard
import com.ptit_booth_chekin.project.ui.screen.home.screen_notification.components.NotificationUI
import com.ptit_booth_chekin.project.ui.theme.JosefinSans


@Composable
fun ScreenNotification(
    modifier: Modifier = Modifier,
    notificationScreenVM: ScreenNotificationVM = hiltViewModel()
) {
    val uiState by notificationScreenVM.uiState.collectAsStateWithLifecycle()
    var showDetailNotification by remember { mutableStateOf<NotificationUI?>(null) }
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
                                    showDetailNotification = notification
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
    if(showDetailNotification != null){
        Box(
            modifier = Modifier.fillMaxSize()
                .clickable{
                    showDetailNotification = null
                }
                .background(color = Color.Black.copy(alpha = 0.9f)),
            contentAlignment = Alignment.Center
        ){
            DetailNotification(notificationUI = showDetailNotification!!)
        }
    }
}


@Composable
fun DetailNotification(
    notificationUI: NotificationUI
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!notificationUI.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = notificationUI.imageUrl ,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.logo)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            color = Color("#E8F5E9".toColorInt()),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = notificationUI.title,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = notificationUI.body,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color("#666666".toColorInt()),
                textAlign = TextAlign.Center

            )

        }
    }
}
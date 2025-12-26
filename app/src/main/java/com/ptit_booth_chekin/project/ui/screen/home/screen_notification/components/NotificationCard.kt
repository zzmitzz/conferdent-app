package com.ptit_booth_chekin.project.ui.screen.home.screen_notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ptit_booth_chekin.project.ui.theme.JosefinSans
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.ptit_booth_chekin.project.R
import androidx.compose.foundation.Image
import androidx.core.graphics.toColorInt

@Composable
fun NotificationCard(
    notificationUI: NotificationUI,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start
            ) {
                if (notificationUI.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = notificationUI.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                color = Color("#E8F5E9".toColorInt()),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_star),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = notificationUI.title,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = notificationUI.body,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color("#666666".toColorInt()),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = notificationUI.formattedTime,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color("#999999".toColorInt())
                    )
                }
            }

            if (!notificationUI.isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = Color.Red,
                            shape = CircleShape
                        )
                        .align(Alignment.Top)
                )
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

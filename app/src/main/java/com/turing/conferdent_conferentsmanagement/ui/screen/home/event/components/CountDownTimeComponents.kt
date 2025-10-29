package com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Locale


@Preview
@Composable
private fun CountDownPREV() {
    CountDownTimeComponents(
        modifier = Modifier
            .fillMaxSize(),
        startTimeEvent = LocalDateTime.now().plusSeconds(10), // 3 days ago
        endTimeEvent = LocalDateTime.now().plusSeconds(20),    // 5 days from now
    )
}

@Composable
fun CountDownTimeComponents(
    modifier: Modifier,
    startTimeEvent: LocalDateTime,
    endTimeEvent: LocalDateTime
) {
    var dateTimeNow by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            dateTimeNow = LocalDateTime.now()
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        if (dateTimeNow.isBefore(startTimeEvent)) {
            val diffTime = startTimeEvent.toEpochSecond(
                ZoneOffset.UTC
            ) - dateTimeNow.toEpochSecond(ZoneOffset.UTC)
            val diffDays = diffTime / (24 * 60 * 60)
            val diffHours = (diffTime % (24 * 60 * 60)) / (60 * 60)
            val diffMinutes = (diffTime % (60 * 60)) / 60
            val diffSeconds = diffTime % 60
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ).padding(
                    vertical = 20.dp,
                    horizontal = 20.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Text(
                    text = stringResource(R.string.event_is_coming_after),
                    textAlign = TextAlign.Start,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = String.format(Locale.getDefault(),
                            "%02d", diffDays),
                        textAlign = TextAlign.Center,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(R.string.day),
                        textAlign = TextAlign.Center,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Text(
                    text = ":",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Medium
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = String.format(Locale.getDefault(),
                            "%02d", diffHours),
                        textAlign = TextAlign.Center,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(R.string.hour),
                        textAlign = TextAlign.Center,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Text(
                    text = ":",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Medium
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = String.format(Locale.getDefault(),
                            "%02d", diffMinutes),
                        textAlign = TextAlign.Center,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(R.string.minute),
                        textAlign = TextAlign.Center,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,

                        color = Color.Black
                    )
                }
                Text(
                    text = ":",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Medium
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = String.format(Locale.getDefault(),
                            "%02d", diffSeconds),
                        textAlign = TextAlign.Center,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(R.string.second),
                        textAlign = TextAlign.Center,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,

                        color = Color.Black
                    )
                }
            }
        } else if (dateTimeNow.isAfter(endTimeEvent)) {
            // Event is over
            Box(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(
                        color = Color("#22272F".toColorInt()),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 70.dp, vertical = 28.dp)

                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.event_is_over),
                    textAlign = TextAlign.Center,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color("#F7F6F4".toColorInt())
                )
            }
        } else {
            // Event is happening now
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color("#22272F".toColorInt()),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 70.dp, vertical = 28.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.event_is_happening_now),
                    textAlign = TextAlign.Center,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color("#F7F6F4".toColorInt())
                )
            }
        }
    }
}
package com.ptit_booth_chekin.project.ui.screen.home.conferent_session.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SessionTypeState
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SessionUIWrap
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SpeakerSession
import com.ptit_booth_chekin.project.utils.DateTimeFormatPattern
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun SessionComponents(
    modifier: Modifier,
    session: SessionUIWrap
) {
    val isHappening = session.status == SessionTypeState.HAPPENING
    Box(
        modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = session.startTime.format(
                        DateTimeFormatter.ofPattern(
                            DateTimeFormatPattern.PATERRN_DATE_TIME_SESSION
                        )
                    ),
                    color = Color("#6B6B6B".toColorInt())
                )
                Box(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .background(
                            color = Color("#6B6B6B".toColorInt()),
                            shape = CircleShape
                        )
                        .size(6.dp)
                )
                Text(
                    text = session.endTime.format(
                        DateTimeFormatter.ofPattern(
                            DateTimeFormatPattern.PATERRN_DATE_TIME_SESSION
                        )
                    ),
                    color = Color("#6B6B6B".toColorInt())
                )
            }
            Row(
                modifier = Modifier.background(
                    color = if (isHappening) Color.Black else Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
            ) {
                Box(
                    modifier = Modifier
                        .width(16.dp)
                        .fillMaxHeight()
                        .background(
                            color = Color("#22272F".toColorInt()),
                            shape = RoundedCornerShape(
                                topStart = 12.dp,
                                bottomStart = 12.dp
                            )
                        )
                )
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = session.sessionName,
                        fontSize = 16.sp,
                        color = if (isHappening) Color.White else Color.Black
                    )
                    Spacer(Modifier.height(6.dp))
                    if (isHappening) {
                        Text(
                            text = "Đang diễn ra",
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp
                                ),
                            color = Color.Black,
                            fontSize = 10.sp
                        )
                    }
                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = session.description,
                        color = if (isHappening) Color("#ABABAB".toColorInt()) else Color("#6B6B6B".toColorInt()),
                        fontSize = 14.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                if (isHappening) {
                                    R.drawable.ic_loc_white
                                } else R.drawable.ic_loc_black
                            ), null
                        )
                        Text(
                            text = session.place,
                            color = if (isHappening) Color.White else Color.Black,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                    Row() {
                        session.speaker.forEach {
                            AsyncImage(
                                model = it.avatarLink,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(24.dp)
                                    .clip(CircleShape),   // THIS makes it round
                                placeholder = painterResource(R.drawable.img_loading),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SessionComponents(
        modifier = Modifier,
        session = SessionUIWrap(
            id = 1,
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now().plusHours(2),
            sessionName = "Jetpack Compose Best Practices",
            status = SessionTypeState.UPCOMING, // Assuming SessionTypeState is an enum with values like UPCOMING, ONGOING, FINISHED
            description = "A deep dive into the best practices for building robust and scalable UIs with Jetpack Compose.",
            place = "Room 101, Convention Center",
            speaker = listOf(
                SpeakerSession(
                    id = "1",
                    avatarLink = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/10f13510774061.560eadfde5b61.png",
                ),
                SpeakerSession(
                    id = "1",
                    avatarLink = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/10f13510774061.560eadfde5b61.png",
                )
            ),
            isNotificationOn = true
        )
    )


}
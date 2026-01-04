package com.ptit_booth_chekin.project.ui.screen.home.screen_favourite.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.data.event.EventDetail
import com.ptit_booth_chekin.project.ui.screen.home.screen_home.components.EventCard
import com.ptit_booth_chekin.project.ui.screen.home.screen_home.components.EventCardInformationUI
import com.ptit_booth_chekin.project.ui.theme.JosefinSans
import com.ptit_booth_chekin.project.utils.DateTimeFormatPattern
import com.ptit_booth_chekin.project.utils.parseLocalDateToFormat
import com.ptit_booth_chekin.project.utils.parseTimeFromServer


@Preview()
@Composable
private fun RegristeredPreview() {
    RegisteredEventCardComponents(
        date = "Day dd/MM/yyyy",
        listEvent = listOf(
            EventDetail(
                name = "Event 1",
                location = "Location 1",
                startTime = "2025-11-10T09:00:00.000Z",
                endTime = "2025-11-10T17:00:00.000Z",
                categoryId = "1",
                organizerId = "Organization 1",
                logo = "Logo 1",
            )
        )
    )
}

@Preview
@Composable
private fun HappeningPreview() {
    HappeningEventCard(
        listEvent = listOf(
            EventDetail(
                name = "Event 1",
                location = "Location 1",
                startTime = "2025-11-10T09:00:00.000Z",
                endTime = "2025-11-10T17:00:00.000Z",
                categoryId = "1",
                organizerId = "Organization 1",
                logo = "Logo 1",
            )
        )
    ) {

    }
}

@Composable
fun RegisteredEventCardComponents(
    date: String, // Could be "Day dd/MM/yyyy" or "Is happening now",
    listEvent: List<EventDetail> = emptyList(),
    onEventClick: (String) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Box(
        ) {
            Text(
                text = date,
                color = Color.Black,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Column {
            listEvent.forEachIndexed { index, detail ->
                Box {
                    EventCard(
                        eventCardInformationUI = detail.let { eventDetail ->
                            EventCardInformationUI(
                                id = eventDetail.Id ?: "",
                                title = eventDetail.name ?: "",
                                location = eventDetail.location ?: "",
                                startTime = eventDetail.startTime ?: "",
                                endTime = eventDetail.endTime ?: "",
                                category = eventDetail.categoryId ?: "",
                                organization = "VNTechConf",
                                logo = eventDetail.logo ?: "",
                                thumbnail = eventDetail.thumbnail ?: "",
                            )
                        },
                        onEventClick = onEventClick
                    )
                }
            }
        }
    }
}

@Composable
fun HappeningEventCard(
    listEvent: List<EventDetail> = emptyList(),
    onEventClick: (String) -> Unit = {},
    onCheckIn: (String) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Box(
        ) {
            Text(
                text = "Đang diễn ra",
                color = Color.Black,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            listEvent.forEachIndexed { index, detail ->
                Box {
                    EventDuringCard(
                        eventCardInformationUI = detail.let { eventDetail ->
                            EventCardInformationUI(
                                id = eventDetail.Id ?: "",
                                title = eventDetail.name ?: "",
                                location = eventDetail.location ?: "",
                                startTime = eventDetail.startTime ?: "",
                                endTime = eventDetail.endTime ?: "",
                                category = eventDetail.categoryId ?: "",
                                organization = "VNTechConf",
                                logo = eventDetail.logo ?: "",
                                thumbnail = eventDetail.thumbnail ?: "",
                            )
                        },
                        onCheckIn = onCheckIn,
                        onNavEventDetail = onEventClick
                    )
                }
            }
        }
    }
}

@Composable
fun EventDuringCard(
    eventCardInformationUI: EventCardInformationUI,
    onCheckIn: (String) -> Unit = {},
    onNavEventDetail: (String) -> Unit = {}
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onNavEventDetail(eventCardInformationUI.id)
            }
    ) {
        AsyncImage(
            model = eventCardInformationUI.thumbnail,
            contentDescription = "Hội nghị Công nghệ Số Việt Nam 2025 Stage",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.conference_image),
            error = painterResource(R.drawable.img_loading),
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    Modifier.background(
                        color = Color.Black.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(16.dp)
                    ).padding(
                        vertical = 8.dp,
                        horizontal = 16.dp
                    )
                ){
                    Text(
                        text = eventCardInformationUI.title,
                        fontFamily = JosefinSans,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    eventCardInformationUI.tags.forEachIndexed { index, string ->
                        Text(
                            text = "#${string}" + if (index != eventCardInformationUI.tags.size - 1) ", " else "",
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        Modifier.background(
                            color = Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(16.dp)
                        ).padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        )
                    ){
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.ic_clock_white),
                                contentDescription = null, // Decorative icon
                                modifier = Modifier.size(13.dp),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Đang diễn ra",
                                fontSize = 9.sp,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        Modifier.background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        ).padding(
                            vertical = 8.dp,
                            horizontal = 12.dp
                        ).clickable{
                            onCheckIn(eventCardInformationUI.id)
                        }
                    ){
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.ic_check_in),
                                colorFilter = ColorFilter.tint(Color.Black),
                                contentDescription = null, // Decorative icon
                                modifier = Modifier.size(13.dp),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Checkin",
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

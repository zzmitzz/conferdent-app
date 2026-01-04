package com.ptit_booth_chekin.project.ui.screen.home.screen_home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.ui.theme.JosefinSans
import com.ptit_booth_chekin.project.utils.DateTimeFormatPattern
import com.ptit_booth_chekin.project.utils.parseLocalDateToFormat
import com.ptit_booth_chekin.project.utils.parseTimeFromServer


@Composable
fun NearbyEventCard(
    eventCardInformationUI: EventCardInformationUI,
    onNavEventDetail: (String) -> Unit = {}
) {
    val overlayGradient = Brush.verticalGradient(
        colorStops = arrayOf(
            0f to Color(0xFFECEBF0).copy(alpha = 0.4f),
            0.8f to Color(0xFF22272F)                  // black at bottom
        )
    )
    Box(
        modifier = Modifier
            .size(280.dp)
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
                .background(overlayGradient)
                .padding(
                    horizontal = 22.dp,
                    vertical = 24.dp
                )
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White // Light lavender color
                ) {
                    Text(
                        fontFamily = JosefinSans,
                        text = eventCardInformationUI.category,
                        color = Color.Black, // Indigo color
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = eventCardInformationUI.title,
                    fontFamily = JosefinSans,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row() {
                    eventCardInformationUI.tags.forEachIndexed { index, string ->
                        Text(
                            text = "#${string}" + if (index != eventCardInformationUI.tags.size - 1) ", " else "",
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_clock_white),
                        contentDescription = null, // Decorative icon
                        modifier = Modifier.size(13.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = parseLocalDateToFormat(parseTimeFromServer(eventCardInformationUI.startTime)) + " - " + parseLocalDateToFormat(
                            parseTimeFromServer(
                                eventCardInformationUI.endTime,
                            ),
                            DateTimeFormatPattern.PATTERN_TIME_DATE
                        ),
                        fontSize = 9.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_location_white),
                        contentDescription = null, // Decorative icon
                        modifier = Modifier.size(13.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = eventCardInformationUI.location,
                        fontSize = 9.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NearbyEventPreview() {
    NearbyEventCard(
        eventCardInformationUI = EventCardInformationUI(
            title = "Hội nghị Công nghệ Số Việt Nam 2025",
            category = "Công nghệ",
            organization = "VNTechConf",
            logo = "https://example.com/logo.png",
            startTime = "2025-11-10T09:00:00.000Z",
            endTime = "2025-11-10T17:00:00.000Z",
            location = "Trung tâm hội nghị Quốc Gia, TP. Hà Nội",
            thumbnail = "",
            tags = listOf("HIHIHI", "hihihi")
        )
    )
}
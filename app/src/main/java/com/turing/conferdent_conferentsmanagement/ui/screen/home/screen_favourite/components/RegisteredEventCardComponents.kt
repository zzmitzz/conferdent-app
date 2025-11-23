package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_favourite.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turing.conferdent_conferentsmanagement.data.event.EventDetail
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.EventCard
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.EventCardInformationUI
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans


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

@Composable
fun RegisteredEventCardComponents(
    date: String, // Could be "Day dd/MM/yyyy" or "Is happening now",
    listEvent: List<EventDetail> = emptyList()
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
                            )
                        }
                    )
                }
            }
        }
    }
}
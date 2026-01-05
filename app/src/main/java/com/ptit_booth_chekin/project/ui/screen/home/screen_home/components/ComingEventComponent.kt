package com.ptit_booth_chekin.project.ui.screen.home.screen_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptit_booth_chekin.project.ui.theme.JosefinSans

@Composable
fun ComingEventComponent(
    eventCardInformationUIList: List<EventCardInformationUI>,
    onNavEventDetail: (String) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ){
            Text(
                text = "Hội nghị sắp tới",
                color = Color.Black,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        LazyColumn {
            items(eventCardInformationUIList.size) {
                Box(
                ){
                    EventCard(
                        eventCardInformationUI = eventCardInformationUIList[it]
                    ){ eventID ->
                        onNavEventDetail(eventID)
                    }
                }
            }
        }
    }
}


@Composable
fun ComingNearByEventComponent(
    eventCardInformationUIList: List<EventCardInformationUI>,
    onNavEventDetail: (String) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ){
            Text(
                text = "Hội nghị gần tôi",
                color = Color.Black,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        if(eventCardInformationUIList.isEmpty()){
            Text(
                text = "Không có sự kiện nào xung quanh bạn",
                color = Color.Black,
                fontFamily = JosefinSans,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }else{
            LazyRow() {
                items(eventCardInformationUIList.size) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                    ){
                        NearbyEventCard(
                            eventCardInformationUI = eventCardInformationUIList[it]
                        ){ eventID ->
                            onNavEventDetail(eventID)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

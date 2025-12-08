package com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.components

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans
import com.turing.conferdent_conferentsmanagement.utils.generateWeekFromStart
import com.turing.conferdent_conferentsmanagement.utils.turnNumberIntoWeekName
import java.time.LocalDate
import kotlin.collections.emptyList


data class DateUIWrapper(
    val dayOfWeek: Int,
    val date: LocalDate,
    val sessionAvailable: Boolean,
    val passedDate: Boolean,
    val isSelected: Boolean = false
)

/*
 data: represent the day that have sessions.
 */
@Composable
fun CalenderComponent(
    data: List<LocalDate>,
    currentSelectedDate: LocalDate = LocalDate.now(),
    onDateChosen: (LocalDate) -> Unit = {}
) {

    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    val listData = remember { mutableStateListOf<DateUIWrapper>() }


    LaunchedEffect(currentDate,currentSelectedDate) {
        listData.clear()
        listData.addAll(
            generateWeekFromStart(currentDate).map { it ->
                DateUIWrapper(
                    dayOfWeek = it.dayOfWeek.value,
                    date = it,
                    sessionAvailable = data.contains(it),
                    passedDate = it.isBefore(LocalDate.now()),
                    isSelected = it == currentSelectedDate
                )
            }
        )
    }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_left_rec),
                contentDescription = null,
                modifier = Modifier.clickable{
                    currentDate = currentDate.minusDays(7)
                }
            )
            Text(
                text = "Tháng ${currentDate.monthValue}, ${currentDate.year}",
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(R.drawable.arrow_right_rec),
                contentDescription = null,
                modifier = Modifier.clickable{
                    currentDate = currentDate.plusDays(7)
                }
            )
        }

        Spacer(
            Modifier.height(20.dp)
        )

        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = Color("#F4F4F6".toColorInt()),
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
        ) {
            LazyRow(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(listData.size){
                    val item = listData[it]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable{
                            onDateChosen(item.date)
                        }
                    ) {
                        Text(
                            text = item.dayOfWeek.turnNumberIntoWeekName(),
                            color = Color("#6B6B6B".toColorInt())
                        )
                        Box(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .background(
                                    color = if (item.passedDate) {
                                        Color.LightGray
                                    } else {
                                        if (item.isSelected) {
                                            Color.Black
                                        } else {
                                            Color.White
                                        }
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                ).padding(
                                    vertical = 16.dp,
                                    horizontal = 14.dp
                                )
                        ){
                            Text(
                                text = item.date.dayOfMonth.toString(),
                                color = if (item.passedDate) {
                                    Color("#ABABAB".toColorInt())
                                } else {
                                    if (item.isSelected) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    }
                                }
                            )
                        }
                        if(item.sessionAvailable){
                            Box(
                                modifier = Modifier.size(4.dp).background(
                                    color = Color("#8A38F5".toColorInt()),
                                    shape = CircleShape
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun PreviewComponent() {
    Column(Modifier.fillMaxSize()) {
        CalenderComponent(emptyList())

    }
}
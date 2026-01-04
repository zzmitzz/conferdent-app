package com.ptit_booth_chekin.project.ui.screen.home.screen_favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.core.ui.RoseCurveSpinner
import com.ptit_booth_chekin.project.data.event.EventDetail
import com.ptit_booth_chekin.project.ui.screen.home.screen_favourite.components.HappeningEventCard
import com.ptit_booth_chekin.project.ui.screen.home.screen_favourite.components.RegisteredEventCardComponents
import com.ptit_booth_chekin.project.ui.theme.JosefinSans
import com.ptit_booth_chekin.project.utils.parseTimeFromServer
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@Composable
fun RegisteredEventScreenStateful(
    navigateToEventDetail: (String) -> Unit = {},
    onCheckIn: (String) -> Unit = {},
    viewModel: RegisteredEventVM = hiltViewModel()
) {
    val appState by viewModel.uiState.collectAsStateWithLifecycle()
    RegisteredEventScreen(
        appState = appState,
        onCheckIn = onCheckIn,
        navigateToEventDetail = navigateToEventDetail
    )
}

@Composable
fun RegisteredEventScreen(
    appState: RegisteredEventVMState = RegisteredEventVMState.Success(),
    onCheckIn: (String) -> Unit = {},
    navigateToEventDetail: (String) -> Unit = {}
) {
    val tabTitles = listOf(
        stringResource(R.string.upcoming),
        stringResource(R.string.passed)
    )
//    val tabTitles = listOf("Upcoming", "Passed")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color("#ECECEE".toColorInt())
            )
            .padding(
                horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = stringResource(R.string.registered_event),
            fontSize = 36.sp,
            fontFamily = JosefinSans,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        when (appState) {
            is RegisteredEventVMState.Success -> {
                val data = appState.currentEvent
                if (data == null) {
                    Text(
                        text = stringResource(id = R.string.no_event_is_happening_now),
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                    HappeningEventCard(
                        listEvent = listOf(data),
                        onCheckIn = onCheckIn,
                        onEventClick = navigateToEventDetail
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            val isSelected = index == pagerState.currentPage

                            Column(
                                modifier = Modifier
                                    .width(90.dp)
                                    .padding(end = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .clickable {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(index)
                                            }
                                        },
                                    textAlign = TextAlign.Center,
                                    style = if (isSelected) {
                                        TextStyle(
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    } else {
                                        TextStyle(
                                            color = Color.Gray,
                                            fontWeight = FontWeight.Normal
                                        )
                                    },
                                )
                                if (pagerState.currentPage == index) {
                                    HorizontalDivider(
                                        color = Color.Black,
                                        thickness = 1.dp,
                                        modifier = Modifier
                                            .height(1.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Top
                    ) { page ->
                        when (page) {
                            0 -> {
                                val data = appState.listEvent.filter { eventDetail ->
                                    val now = LocalDateTime.now()
                                    val eventStart = parseTimeFromServer(eventDetail.startTime!!)
                                    eventStart.isAfter(now)
                                }
                                if (data.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.no_event_is_upcoming),
                                        fontFamily = JosefinSans,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 16.sp,
                                        modifier = Modifier.fillMaxSize(),
                                        textAlign = TextAlign.Center
                                    )
                                } else {
                                    Spacer(modifier = Modifier.height(24.dp))
                                    val eventGroupByDate = data.groupBy { eventDetail ->
                                        parseTimeFromServer(eventDetail.startTime!!).toLocalDate()
                                    }
                                    LazyColumn(
                                        state = rememberLazyListState()
                                    ) {
                                        items(count = eventGroupByDate.size){
                                            Column {
                                                RegisteredEventCardComponents(
                                                    date = eventGroupByDate.keys.elementAt(it).toString(),
                                                    listEvent = eventGroupByDate.values.elementAt(it),
                                                    onEventClick = navigateToEventDetail
                                                )
                                                if(it == eventGroupByDate.size - 1){
                                                    Spacer(modifier = Modifier.height(128.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            1 -> {
                                val data = appState.listEvent.filter { eventDetail ->
                                    val now = LocalDateTime.now()
                                    val eventEnd = parseTimeFromServer(eventDetail.endTime!!)
                                    eventEnd.isBefore(now)
                                }
                                if (data.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.no_event_is_registered),
                                        fontFamily = JosefinSans,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 16.sp,
                                        modifier = Modifier.fillMaxSize(),
                                        textAlign = TextAlign.Center
                                    )
                                } else {
                                    Spacer(modifier = Modifier.height(24.dp))
                                    val eventGroupByDate = data.groupBy { eventDetail ->
                                        parseTimeFromServer(eventDetail.startTime!!).toLocalDate()
                                    }
                                    LazyColumn(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        items(count = eventGroupByDate.size){
                                            Column {
                                                RegisteredEventCardComponents(
                                                    date = eventGroupByDate.keys.elementAt(it).toString(),
                                                    listEvent = eventGroupByDate.values.elementAt(it),
                                                    onEventClick = navigateToEventDetail
                                                )
                                                if(it == eventGroupByDate.size-1){
                                                    Spacer(modifier = Modifier.height(128.dp))

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is RegisteredEventVMState.Error -> {

            }

            is RegisteredEventVMState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    RoseCurveSpinner(
                        color = Color.Black
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun RegisPrev() {
    RegisteredEventScreen()
}
package com.ptit_booth_chekin.project.ui.screen.home.conferent_session

import android.se.omapi.Session
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ptit_booth_chekin.project.core.ui.RoseCurveSpinner
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.components.BottomSheetSessionDetail
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.components.CalenderComponent
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.components.SessionComponents
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SessionTypeState
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SessionUIWrap
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SpeakerSession
import com.ptit_booth_chekin.project.ui.theme.JosefinSans
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConferenceSession(
    onNavBack: () -> Unit = {},
    viewModel: ConferenceSessionVM
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.fetchSessionData()
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showSheet by remember { mutableStateOf(false) }
    var sessionDetail by remember { mutableStateOf<SessionUIWrap?>(null) }
    when (state.value) {
        is ConferenceViewState.Success -> {
            ConferenceSessionStateless(
                onNavBack = onNavBack,
                data = (state.value as ConferenceViewState.Success).data,
                rawData = viewModel.sessionData,
                currentSelect = (state.value as ConferenceViewState.Success).currentSelectDate,
                selectDate = {
                    viewModel.updateFilterDate(it)
                },
                viewDetailSession = {
                    sessionDetail = it
                    showSheet = true
                }
            )
        }

        is ConferenceViewState.Error -> {
            Text(text = "Error")
        }

        is ConferenceViewState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                RoseCurveSpinner(color = Color.Black)
            }
        }
    }
    if (showSheet && sessionDetail != null) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color.White,
        ) {
            BottomSheetSessionDetail(sessionDetail!!)
        }
    }
}


@Composable
fun ConferenceSessionStateless(
    onNavBack: () -> Unit = {},
    currentSelect: LocalDate,
    data: List<SessionUIWrap> = emptyList(),
    rawData: List<SessionUIWrap> = emptyList(),
    selectDate: (LocalDate) -> Unit = {},
    viewDetailSession: (SessionUIWrap) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color("#ECECEE".toColorInt()))
            .padding(
                top = 16.dp
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp)
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .clickable {
                    onNavBack()
                }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Quay lại",
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }

        Spacer(
            Modifier.height(24.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CalenderComponent(
                data = rawData.map {
                    it.startTime.toLocalDate()
                },
                currentSelectedDate = currentSelect
            ) { date ->
                selectDate(date)
            }
        }

        Text(
            text = "Các phiên hội nghị trong ngày",
            fontFamily = JosefinSans,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(data.size) { index ->
                SessionComponents(Modifier, data[index], onDetail = {
                    viewDetailSession(data[index])
                })
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun ConferencePreview() {
    ConferenceSessionStateless(
        onNavBack = {},
        LocalDate.now(),
        data = listOf(
            SessionUIWrap(
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
            ),
            SessionUIWrap(
                id = 1,
                startTime = LocalDateTime.now(),
                endTime = LocalDateTime.now().plusHours(2),
                sessionName = "Jetpack Compose Best Practices",
                status = SessionTypeState.HAPPENING, // Assuming SessionTypeState is an enum with values like UPCOMING, ONGOING, FINISHED
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
            ),
            SessionUIWrap(
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
            ),
            SessionUIWrap(
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
            ),
            SessionUIWrap(
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
            ),

            )
    )
}
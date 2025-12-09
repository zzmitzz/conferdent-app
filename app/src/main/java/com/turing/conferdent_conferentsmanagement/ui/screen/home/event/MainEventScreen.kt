package com.turing.conferdent_conferentsmanagement.ui.screen.home.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.data.event.EventDetail
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.models.SpeakerUIModel
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.CountDownTimeComponents
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.HeaderComponents
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.InfoRowMain
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.RegistrationCta
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.RegistrationHoldingCta
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.SpeakerListRow
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.models.OrganizerUIModel
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans
import com.turing.conferdent_conferentsmanagement.utils.parseLocalDateToFormat
import com.turing.conferdent_conferentsmanagement.utils.parseTimeFromServer
import kotlinx.coroutines.delay
import java.time.LocalDateTime


@Composable
fun MainEventScreen(
    eventID: String? = null,
    viewModel: MainEventVM,
    navigateBack: () -> Unit = {},
    navigateRegister: () -> Unit = {},
    onCheckIn: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onScheduleClick: () -> Unit = {},
    onSpeakerClick: (String) -> Unit = {},
    onResourceClick: () -> Unit = {}
) {
    val appState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.fetchEventDetail(eventID)
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (appState) {
            is MainEventVMState.Success -> {
                MainEventDetailScreen(
                    modifier = Modifier.fillMaxSize(),
                    event = ((appState) as MainEventVMState.Success).event,
                    navigateBack = navigateBack,
                    navigateRegister = navigateRegister,
                    speakerList = ((appState) as MainEventVMState.Success).event.speakers.map {
                        SpeakerUIModel(
                            id = it.id ?: 0,
                            name = it.fullName ?: "Not provided",
                            avatar = it.photoUrl ?: "Not provided",
                            workingAt = it.professionalTitle ?: ""
                        )
                    },
                    organizerData = ((appState) as MainEventVMState.Success).event.organizers.let {
                        OrganizerUIModel(
                            name = it!!.name!!,
                            avatar = it.avatar ?: "",
                            description = it.describe!!

                        )

                    },
                    onCheckIn = {
                        onCheckIn()
                    },
                    onMapClick = {
                        onMapClick()
                    },
                    onScheduleClick = {
                        onScheduleClick()
                    },
                    onSpeakerClick = onSpeakerClick,
                    onResourceClick = onResourceClick
                )
            }

            is MainEventVMState.Error -> {
                Text(
                    text = ((appState) as MainEventVMState.Error).message,
                    color = Color.Red
                )
            }

            is MainEventVMState.Loading -> {
                RoseCurveSpinner(
                    color = Color.Black
                )
            }
        }
    }

}


@Composable
private fun MainEventDetailScreen(
    modifier: Modifier,
    event: EventDetail,
    speakerList: List<SpeakerUIModel> = emptyList(),
    organizerData: OrganizerUIModel? = null,
    navigateBack: () -> Unit = {},
    navigateRegister: () -> Unit = {},
    onCheckIn: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onScheduleClick: () -> Unit = {},
    onSpeakerClick: (String) -> Unit = {},
    onResourceClick: () -> Unit = {}
) {


    var secondsLeft by remember { mutableStateOf(LocalDateTime.now()) }
    val eventStart = remember(event.startTime) {
        parseTimeFromServer(event.startTime!!)
    }
    val eventEnd = remember(event.endTime) {
        parseTimeFromServer(event.endTime!!)
    }
    val isEventOnGoing by remember {
        derivedStateOf { secondsLeft in eventStart..eventEnd }
    }


    LaunchedEffect(Unit) {
        while (!isEventOnGoing && secondsLeft.isBefore(eventStart)) {
            delay(1000)
            secondsLeft = LocalDateTime.now()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        EventHeader(
            modifier = Modifier.fillMaxWidth(),
            startTime = eventStart,
            endTime = eventEnd,
            isEventOnGoing = isEventOnGoing,
            eventThumbnail = event.thumbnail
                ?: "https://images.pexels.com/photos/1421903/pexels-photo-1421903.jpeg?cs=srgb&dl=pexels-eberhardgross-1421903.jpg&fm=jpg",
            navigateBack = navigateBack,
            onResourceClick = onResourceClick
        )

        MainEventContent(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            speak = speakerList,
            organizerData = organizerData,
            event = event,
            onSpeakerClick = onSpeakerClick
        )
        if (!isEventOnGoing) {
            RegistrationCta(
                modifier = Modifier.fillMaxWidth(),
                onButtonClick = {
                    navigateRegister()
                },
                isRegistered = event.isRegistered,
                capacity = event.capacity ?: 0,
                isOvered = secondsLeft.isAfter(
                    parseTimeFromServer(
                        event.endTime ?: "2025-11-10T09:00:00.000Z"
                    )
                )
            )
        } else {
            RegistrationHoldingCta(
                modifier = Modifier.fillMaxWidth(),
                onCheckIn = {
                    onCheckIn()
                },
                onMapClick = {
                    onMapClick()
                },
                onScheduleClick = {
                    onScheduleClick()
                },
            )
        }
    }
}


@Preview
@Composable
private fun MainScreenPreview() {
    MainEventDetailScreen(
        Modifier,
        event = EventDetail(
            name = "Hội nghị Công nghệ Số Việt Nam 2025",
            startTime = "2025-11-10T09:00:00.000Z",
            endTime = "2025-11-10T18:00:00.000Z",
            location = "Location",
            description = "Hội nghị Công nghệ Số Việt Nam 2025 là sự kiện thường niên quy tụ các chuyên gia, doanh nghiệp và sinh viên trong lĩnh vực công nghệ thông tin. Hội nghị tập trung vào các chủ đề nổi bật như trí tuệ nhân tạo, chuyển đổi số trong doanh nghiệp, bảo mật mạng, và phát triển ứng dụng di động. Đây là cơ hội để kết nối, trao đổi kiến thức và tìm kiếm cơ hội hợp tác trong ngành công nghệ.",
            categoryId = "Công nghệ",
            logo = "Logo URL",
            lat = 21.02,
            lng = 105.84,
            organizerId = "Organizer ID",
        )
    )
}


@Composable
fun MainEventContent(
    modifier: Modifier,
    event: EventDetail,
    speak: List<SpeakerUIModel> = emptyList(),
    organizerData: OrganizerUIModel? = null,
    onSpeakerClick: (String) -> Unit = {}
) {

    val currentLatLng by remember {
        mutableStateOf(
            LatLng(event.lat!!, event.lng!!)
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLatLng, 12f)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color("#ECECEE".toColorInt()))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .size(
                    height = 6.dp,
                    width = 30.dp
                )
                .align(Alignment.CenterHorizontally)
                .background(
                    color = Color("#A2A1A1".toColorInt())
                )
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = event.name ?: "",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
        )
        Surface(
            shape = CircleShape,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = event.categoryId ?: "",
                color = Color.Black, // Indigo color
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                fontSize = 12.sp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_clock),
                contentDescription = "Time",
                modifier = Modifier.size(16.dp),

                )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = parseLocalDateToFormat(
                    parseTimeFromServer(event.startTime!!)
                ), fontSize = 13.sp, color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_clock),
                contentDescription = "Time",
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = parseLocalDateToFormat(
                    parseTimeFromServer(event.endTime!!)
                ),
                fontSize = 13.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        InfoRowMain(
            icon = R.drawable.ic_location,
            text = event.location!!
        )
        Spacer(modifier = Modifier.height(46.dp))

        Text(
            text = stringResource(R.string.description),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = event.description ?: "",
            fontSize = 13.sp,
            color = Color("#6B6B6B".toColorInt()),
        )
        Spacer(modifier = Modifier.height(48.dp))

        organizerData?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 40.dp, horizontal = 30.dp)
            ) {
                Row() {
                    AsyncImage(
                        model = organizerData.avatar,
                        contentDescription = null,
                        modifier = Modifier.size(42.dp).clip(CircleShape),
                        placeholder = painterResource(R.drawable.img_loading)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = organizerData.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = JosefinSans,
                        color = Color.Black,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = organizerData.description,
                    fontSize = 13.sp,
                    color = Color("#6B6B6B".toColorInt()),
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
        Text(
            text = stringResource(R.string.speaker),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (speak.isNotEmpty()) {
            SpeakerListRow(
                data = speak,
                onSpeakerClick = onSpeakerClick
            )
        }else{
            Text(
                text = "Không có diễn giả trong sự kiện này",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center

            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.maps),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(16.dp))
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = remember {
                    MarkerState(position = currentLatLng)
                },
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}


@Composable
fun EventHeader(
    modifier: Modifier,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    isEventOnGoing: Boolean = true,
    eventThumbnail: String, // should be URL
    onResourceClick: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(300.dp)
            .background(
                color = Color.White,
            ),
    ) {

        AsyncImage(
            model = eventThumbnail,
            contentDescription = null,
            placeholder = painterResource(R.drawable.img_loading),
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderComponents(
                modifier = Modifier,
                isEventOnGoing = isEventOnGoing,
                navigateBack = navigateBack,
                onMoreClick = {},
                onResourceClick = onResourceClick
            )

            CountDownTimeComponents(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 20.dp
                ),
                startTimeEvent = startTime,
                endTimeEvent = endTime
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun EventHeaderPREV() {
    EventHeader(
        modifier = Modifier,
        startTime = LocalDateTime.now().plusSeconds(10), // 3 days ago
        endTime = LocalDateTime.now().plusSeconds(20),
        eventThumbnail = "https://images.pexels.com/photos/1421903/pexels-photo-1421903.jpeg?cs=srgb&dl=pexels-eberhardgross-1421903.jpg&fm=jpg",
        navigateBack = {}
    )
}

@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:width=411dp,height=2000dp"
)
@Composable
private fun MainContentPrev() {
    MainEventContent(
        modifier = Modifier.fillMaxSize(),
        event = EventDetail(
            name = "Hội nghị Công nghệ Số Việt Nam 2025",
            startTime = "2025-11-10T09:00:00.000Z",
            endTime = "2025-11-10T18:00:00.000Z",
            location = "Location",
            description = "Hội nghị Công nghệ Số Việt Nam 2025 là sự kiện thường niên quy tụ các chuyên gia, doanh nghiệp và sinh viên trong lĩnh vực công nghệ thông tin. Hội nghị tập trung vào các chủ đề nổi bật như trí tuệ nhân tạo, chuyển đổi số trong doanh nghiệp, bảo mật mạng, và phát triển ứng dụng di động. Đây là cơ hội để kết nối, trao đổi kiến thức và tìm kiếm cơ hội hợp tác trong ngành công nghệ.",
            categoryId = "Công nghệ",
            logo = "Logo URL",
            lat = 21.02,
            lng = 105.84,
            organizerId = "Organizer ID",
        )
    )
}
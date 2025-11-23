package com.turing.conferdent_conferentsmanagement.ui.screen.home.event

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
import com.turing.conferdent_conferentsmanagement.models.SpeakerUIModel
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.CountDownTimeComponents
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.HeaderComponents
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.InfoRowMain
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.RegistrationCta
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.RegistrationHoldingCta
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.SpeakerListRow
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.InfoRow
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans
import com.turing.conferdent_conferentsmanagement.utils.parseLocalDateToFormat
import com.turing.conferdent_conferentsmanagement.utils.parseTimeFromServer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime


@Composable
fun MainEventScreen(
    eventID: String? = null,
    viewModel: MainEventVM,
    navigateBack: () -> Unit = {},
    navigateRegister: () -> Unit = {}
) {
    val appState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.fetchEventDetail(eventID)
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
                    navigateRegister = navigateRegister
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
    navigateBack: () -> Unit = {},
    navigateRegister: () -> Unit = {}
) {


    var secondsLeft by remember { mutableStateOf(LocalDateTime.now()) }

    val isEventOnGoing = remember {
        derivedStateOf {
            secondsLeft.isAfter(
                parseTimeFromServer(
                    event.startTime ?: "2025-11-10T09:00:00.000Z"
                )
            ) && secondsLeft.isBefore(
                parseTimeFromServer(
                    event.endTime ?: "2025-11-10T09:00:00.000Z"
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            secondsLeft = LocalDateTime.now()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        EventHeader(
            modifier = Modifier.fillMaxWidth(),
            startTime = parseTimeFromServer(event.startTime ?: "2025-11-10T09:00:00.000Z"),
            endTime = parseTimeFromServer(event.endTime ?: "2025-11-10T09:00:00.000Z"),
            eventThumbnail = event.thumbnail
                ?: "https://images.pexels.com/photos/1421903/pexels-photo-1421903.jpeg?cs=srgb&dl=pexels-eberhardgross-1421903.jpg&fm=jpg",
            navigateBack = navigateBack
        )

        MainEventContent(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            event = event
        )
        if (!isEventOnGoing.value) {
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
    event: EventDetail
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
        Spacer(modifier = Modifier.height(8.dp))

        InfoRowMain(icon = R.drawable.ic_org, text = event.organizerId ?: "")

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
        Text(
            text = stringResource(R.string.speaker),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SpeakerListRow(
            data = listOf(
                SpeakerUIModel(
                    name = "Speaker 1",
                    avatar = "https://cdn.dribbble.com/userupload/23788648/file/original-a893a015e4cb1a39778df5de2b242107.jpg?resize=400x0",
                    workingAt = "PTIT",
                ),
                SpeakerUIModel(
                    name = "Speaker 1",
                    avatar = "https://cdn.dribbble.com/userupload/23788648/file/original-a893a015e4cb1a39778df5de2b242107.jpg?resize=400x0",
                    workingAt = "PTIT",
                ),
                SpeakerUIModel(
                    name = "Speaker 1",
                    avatar = "https://cdn.dribbble.com/userupload/23788648/file/original-a893a015e4cb1a39778df5de2b242107.jpg?resize=400x0",
                    workingAt = "PTIT",
                ),
                SpeakerUIModel(
                    name = "Speaker 1",
                    avatar = "https://cdn.dribbble.com/userupload/23788648/file/original-a893a015e4cb1a39778df5de2b242107.jpg?resize=400x0",
                    workingAt = "PTIT",
                )
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 40.dp, horizontal = 30.dp)

        ) {
            Text(
                text = event.organizerId!!,
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
        }
        Spacer(modifier = Modifier.height(48.dp))

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
    eventThumbnail: String, // should be URL
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
                navigateBack = navigateBack,
                onMoreClick = {}
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
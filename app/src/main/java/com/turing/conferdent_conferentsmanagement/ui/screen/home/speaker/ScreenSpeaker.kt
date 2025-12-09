package com.turing.conferdent_conferentsmanagement.ui.screen.home.speaker

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.data.event.EventSpeakers
import com.turing.conferdent_conferentsmanagement.data.event.SpeakerSessionItem
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.components.SessionComponents
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.models.SessionTypeState
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.models.SessionUIWrap
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.models.SpeakerSession
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans
import com.turing.conferdent_conferentsmanagement.utils.parseTimeFromServer
import java.time.LocalDateTime


/**
 * Stateful Composable - Receives ViewModel and navigation callbacks
 */
@Composable
fun ScreenSpeaker(
    speakerId: String? = null,
    eventId: String? = null,
    viewModel: SpeakerVM,
    navigateBack: () -> Unit = {}
) {
    val appState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.fetchSpeakerDetail(speakerId, eventId)
    }
    
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (appState) {
            is SpeakerVMState.Success -> {
                SpeakerDetailScreen(
                    modifier = Modifier.fillMaxSize(),
                    speaker = (appState as SpeakerVMState.Success).speaker,
                    sessions = (appState as SpeakerVMState.Success).sessions,
                    navigateBack = navigateBack
                )
            }
            
            is SpeakerVMState.Error -> {
                Text(
                    text = (appState as SpeakerVMState.Error).message,
                    color = Color.Red
                )
            }
            
            is SpeakerVMState.Loading -> {
                RoseCurveSpinner(
                    color = Color.Black
                )
            }
        }
    }
}


/**
 * Stateless Composable - Receives only data
 */
@Composable
private fun SpeakerDetailScreen(
    modifier: Modifier,
    speaker: EventSpeakers,
    sessions: List<SpeakerSessionItem> = emptyList(),
    navigateBack: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SpeakerHeader(
            modifier = Modifier.fillMaxWidth(),
            speakerPhotoUrl = speaker.photoUrl 
                ?: "https://images.pexels.com/photos/1421903/pexels-photo-1421903.jpeg?cs=srgb&dl=pexels-eberhardgross-1421903.jpg&fm=jpg",
            navigateBack = navigateBack
        )
        
        SpeakerContent(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            speaker = speaker,
            sessions = sessions
        )
    }
}


@Composable
fun SpeakerHeader(
    modifier: Modifier,
    speakerPhotoUrl: String,
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
            model = speakerPhotoUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.img_loading),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = androidx.compose.ui.Modifier
                .padding(16.dp)
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .clickable {
                    navigateBack()
                }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = androidx.compose.ui.Modifier.size(16.dp)
            )
            Spacer(modifier = androidx.compose.ui.Modifier.width(8.dp))
            Text(
                text = "Quay lại",
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun SpeakerContent(
    modifier: Modifier,
    speaker: EventSpeakers,
    sessions: List<SpeakerSessionItem> = emptyList()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color("#ECECEE".toColorInt()))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        // Drag handle
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
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Speaker Name
        Text(
            text = speaker.fullName ?: "Unknown Speaker",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
        )
        
        // Professional Title
        Surface(
            shape = CircleShape,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = speaker.professionalTitle ?: "Speaker",
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                fontSize = 12.sp,
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // Bio Section
        speaker.bio?.let { bio ->
            Text(
                text = bio,
                fontSize = 13.sp,
                color = Color("#6B6B6B".toColorInt()),
            )
            Spacer(modifier = Modifier.height(48.dp))
        }

        // Sessions Section - "Lịch trình bày"
        Text(
            text = "Lịch trình bày",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        if (sessions.isNotEmpty()) {
            sessions.forEach { session ->
                SessionComponents(
                    modifier = Modifier.fillMaxWidth(),
                    session = session.toSessionUIWrap()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            Text(
                text = "Không có lịch trình bày nào",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color("#6B6B6B".toColorInt())
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // LinkedIn Section
        speaker.linkedinUrl?.let { linkedinUrl ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 40.dp, horizontal = 30.dp)
            ) {
                Text(
                    text = "LinkedIn",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = JosefinSans,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = linkedinUrl,
                    fontSize = 13.sp,
                    color = Color("#6B6B6B".toColorInt()),
                )
            }
        }
    }
}


private fun SpeakerSessionItem.toSessionUIWrap(): SessionUIWrap {
    val now = LocalDateTime.now()
    val startTime = parseTimeFromServer(this.startTime ?: "")
    val endTime = parseTimeFromServer(this.endTime ?: "")
    
    val status = when {
        now.isBefore(startTime) -> SessionTypeState.UPCOMING
        now.isAfter(endTime) -> SessionTypeState.CANCELED
        else -> SessionTypeState.HAPPENING
    }
    
    return SessionUIWrap(
        id = this.id ?: 0,
        startTime = startTime,
        endTime = endTime,
        sessionName = this.title ?: "Untitled Session",
        status = status,
        description = this.description ?: "",
        place = this.place ?: "TBA",
        speaker = emptyList(),
        isNotificationOn = false
    )
}


@Preview(showBackground = true)
@Composable
private fun SpeakerHeaderPreview() {
    SpeakerHeader(
        modifier = Modifier,
        speakerPhotoUrl = "https://images.pexels.com/photos/1421903/pexels-photo-1421903.jpeg?cs=srgb&dl=pexels-eberhardgross-1421903.jpg&fm=jpg",
        navigateBack = {}
    )
}


@Preview(
    showBackground = true, 
    showSystemUi = true,
    device = "spec:width=411dp,height=2000dp"
)
@Composable
private fun SpeakerContentPreview() {
    SpeakerContent(
        modifier = Modifier.fillMaxSize(),
        speaker = EventSpeakers(
            id = 1,
            fullName = "Nguyễn Văn A",
            bio = "Với hơn 15 năm kinh nghiệm trong phát triển nền tảng cho doanh nghiệp, ông Nguyễn Văn A sẽ chia sẻ về các xu hướng công nghệ đang định hình trong lại việc nhân tạo tại Việt Nam.",
            professionalTitle = "CEO Công ty ABC Tech",
            email = "nguyen.van.a@example.com",
            phone = "+84 123 456 789",
            photoUrl = "https://images.pexels.com/photos/1421903/pexels-photo-1421903.jpeg",
            linkedinUrl = "https://linkedin.com/in/nguyenvana"
        ),
        sessions = listOf(
            SpeakerSessionItem(
                id = 1,
                title = "Xu hướng AI trong 5 năm tới",
                description = "Tìm hiểu vào việc ứng dụng AI trong các lĩnh",
                startTime = "2025-12-10T09:00:00.000Z",
                endTime = "2025-12-10T10:00:00.000Z",
                place = "Phòng hội thảo A",
                speakerRole = "main_speaker",
                speakingOrder = 1,
                speakingDurationMinutes = 60
            )
        )
    )
}

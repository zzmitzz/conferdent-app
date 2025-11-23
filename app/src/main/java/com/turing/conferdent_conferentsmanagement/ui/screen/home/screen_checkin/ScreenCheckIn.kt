package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_checkin

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.data.event.EventDetail
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.MainEventVM
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.MainEventVMState
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans


@Composable
fun ScreenCheckInQR(
    navBack: () -> Unit,
    viewModel: MainEventVM
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        is MainEventVMState.Success -> {
            val data = (uiState as MainEventVMState.Success).event
            ScreenQRCheckIn(
                onNavBack = navBack,
                event = data
            )
        }

        else -> {
            RoseCurveSpinner(
                color = Color.Black
            )
        }
    }
}


@Composable
fun ScreenQRCheckIn(
    onNavBack: () -> Unit = {},
    event: EventDetail,

    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = event.thumbnail,
            contentDescription = "",
            placeholder = painterResource(id = R.drawable.bg_auth),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
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
            Spacer(Modifier.height(36.dp))
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = Color("#EFEFEF".toColorInt()).copy(alpha = 0.3f),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = event.name ?: "Hội nghị công nghệ số Việt Nam",
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(
                    Modifier.height(20.dp)
                )
                Image(
                    painter = painterResource(R.drawable.qr_bg),
                    contentDescription = ""
                )
                Spacer(
                    Modifier.height(20.dp)
                )
                Spacer(
                    Modifier.height(20.dp)
                )
                Text(
                    text = "Hãy đưa mã QR này cho nhân viên checkin",
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Spacer(
                    Modifier.height(20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), // External padding
                    horizontalArrangement = Arrangement.Center, // Centers the buttons
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Button 1: Tải mã QR
                    PillButton(
                        text = "Tải mã QR",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f) // Optional: Remove .weight(1f) if you don't want equal sizing
                    )

                    // Space between buttons
                    Box(modifier = Modifier.padding(8.dp))

                    // Button 2: Xem form đăng ký
                    PillButton(
                        text = "Xem form đăng ký",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f) // Optional: Remove .weight(1f) if you don't want equal sizing
                    )
                }
            }
        }
    }
}

// Reusable Helper Composable for the specific style
@Composable
fun PillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape, // Makes the button fully rounded (Pill shape)
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White, // White background
            contentColor = Color.Black    // Black text
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp // Optional: Adds slight shadow like the image
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 4.dp) // Increases height slightly,

        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ScreenQRCheckIn(
        event = EventDetail()

    )

}
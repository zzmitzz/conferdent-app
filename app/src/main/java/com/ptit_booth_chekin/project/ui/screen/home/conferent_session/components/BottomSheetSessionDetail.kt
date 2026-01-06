package com.ptit_booth_chekin.project.ui.screen.home.conferent_session.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SessionTypeState
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SessionUIWrap
import com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models.SpeakerSession
import com.ptit_booth_chekin.project.utils.DateTimeFormatPattern
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun BottomSheetSessionDetail(
    data: SessionUIWrap
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Text(
            text = data.sessionName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFFE0E0E0)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_clock),
                contentDescription = null,
                tint = Color(0xFF6B6B6B),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "${data.startTime.format(DateTimeFormatter.ofPattern("HH:mm, 'ngày' dd/MM/yyyy"))} • ${
                    data.endTime.format(
                        DateTimeFormatter.ofPattern("HH:mm, 'ngày' dd/MM/yyyy")
                    )
                }",
                fontSize = 14.sp,
                color = Color(0xFF6B6B6B)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_loc_black),
                contentDescription = null,
                tint = Color(0xFF6B6B6B),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = data.place,
                fontSize = 14.sp,
                color = Color(0xFF6B6B6B)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Mô tả",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = data.description,
            fontSize = 14.sp,
            color = Color(0xFF6B6B6B),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Diễn giả",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow() {
            items(data.speaker.size){index ->
                SpeakerItem(speaker = data.speaker[index], modifier = Modifier.width(120.dp))
                if (index < data.speaker.size - 1) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }


        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SpeakerItem(speaker: SpeakerSession, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = speaker.avatarLink,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.img_loading)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(

            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nguyễn Văn A",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "CEO Công ty\nABC Tech",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = Color(0xFF6B6B6B),
                lineHeight = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomSheetSessionDetail() {
    BottomSheetSessionDetail(
        data = SessionUIWrap(
            id = 1,
            startTime = LocalDateTime.of(2025, 11, 3, 9, 0),
            endTime = LocalDateTime.of(2025, 11, 5, 17, 0),
            sessionName = "Xu hướng AI trong 5 năm tới",
            status = SessionTypeState.UPCOMING,
            description = "Tập trung vào việc ứng dụng AI trong các lĩnh vực giáo dục, y tế và sản xuất. Diễn giả chia sẻ về xu hướng mới trong AI Generative và Machine Learning tại Đông Nam Á.\n\nXu hướng này phản ánh sự dịch chuyển mạnh mẽ từ nghiên cứu học thuật sang triển khai thực tiễn, trong đó AI không chỉ đóng vai trò hỗ trợ mà dần trở thành nền tảng cốt lõi trong đổi mới mô hình đào tạo, chăm sóc sức khỏe thông minh và tối ưu hóa quy trình sản xuất, góp phần nâng cao năng lực cạnh tranh và phát triển bền vững cho khu vực.",
            place = "Phòng A2",
            speaker = listOf(
                SpeakerSession(
                    id = "1",
                    avatarLink = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/10f13510774061.560eadfde5b61.png"
                ),
                SpeakerSession(
                    id = "2",
                    avatarLink = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/10f13510774061.560eadfde5b61.png"
                )
            ),
            isNotificationOn = true
        )
    )
}
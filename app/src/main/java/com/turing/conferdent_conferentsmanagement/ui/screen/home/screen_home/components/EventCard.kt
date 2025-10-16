package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans

// --- Main Composable Function ---

@Composable
fun EventCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.conference_image),
                contentDescription = "Hội nghị Công nghệ Số Việt Nam 2025 Stage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Right Side: Event Details
            Column {
                // Top Row: Title and Notification Icon
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hội nghị Công nghệ Số Việt Nam 2025",
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_noti),
                        contentDescription = "Set Reminder",
                        tint = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFECEBF0) // Light lavender color
                ) {
                    Text(
                        text = "Công nghệ",
                        color = Color.Black, // Indigo color
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 6.sp,
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                InfoRow(icon = R.drawable.ic_org, text = "VNTechConf")


                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_clock),
                        contentDescription = "Time",
                        modifier = Modifier.size(9.dp),

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "09:00, ngày 03/11/2025", fontSize = 9.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_clock),
                        contentDescription = "Time",
                        modifier = Modifier.size(9.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "17:00, ngày 03/11/2025", fontSize = 9.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(5.dp))
                InfoRow(
                    icon = R.drawable.ic_location,
                    text = "Trung tâm hội nghị Quốc Gia, TP. Hà Nội"
                )
            }
        }
    }
}

// --- Helper Composable for Icon + Text rows ---

@Composable
private fun InfoRow(icon: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(icon),
            contentDescription = null, // Decorative icon
            modifier = Modifier.size(9.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 9.sp,
            color = Color.Black
        )
    }
}

// --- Preview Function ---

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventCardPreview() {
    EventCard()
}
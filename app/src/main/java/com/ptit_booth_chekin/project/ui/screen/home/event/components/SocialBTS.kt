package com.ptit_booth_chekin.project.ui.screen.home.event.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.ui.theme.JosefinSans


enum class SocialPlatform(
    val socialName: String,
    @DrawableRes val icon: Int
) {
    FACEBOOK("Facebook", R.drawable.facebook_icon),
    TWITTER("Twitter", R.drawable.twitter),
    YOUTUBE("Youtube", R.drawable.youtube),
    LINKEDIN("Linkedin", R.drawable.linkedin),
    OTHER("OTHER", R.drawable.other)
}


@Composable
fun SocialBTS(
    listSocial: List<Pair<String, SocialPlatform>> = emptyList(),
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            text = "Mạng xã hội",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = JosefinSans
        )
        if (listSocial.isEmpty()) {
            Text(
                text = "Hội nghị không cung cấp thông tin mạng xã hội",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )
        } else {
            listSocial.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onClick(it.first) }),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = it.second.icon),
                        contentDescription = it.second.socialName,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = it.first,
                        color = Color.Black
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
private fun SocialBTSPreview() {
    SocialBTS(
        listOf(
            "Facebook" to SocialPlatform.FACEBOOK,
            "Twitter" to SocialPlatform.TWITTER,
            "Youtube" to SocialPlatform.YOUTUBE,
            "Linkedin" to SocialPlatform.LINKEDIN,
            "Other" to SocialPlatform.OTHER
        ),
        onClick = {}
    )
}
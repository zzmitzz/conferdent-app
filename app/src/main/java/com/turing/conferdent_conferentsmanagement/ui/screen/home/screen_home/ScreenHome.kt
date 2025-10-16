package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.CategoryComponent
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.ComingEventComponent
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.LocationText
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans


@Preview
@Composable
private fun HomePrev() {
    ScreenStateless(
        scrollState = rememberScrollState()
    )
}

@Composable
fun ScreenHome() {
    val scrollState = rememberScrollState()

}

@Composable
fun ScreenStateless(
    scrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .background(
                color = Color("#ECECEE".toColorInt())
            )
            .verticalScroll(scrollState)
    ) {
        Spacer(
            modifier = Modifier.height(72.dp)
        )
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            LocationText()
        }
        Spacer(
            modifier = Modifier.height(33.dp)
        )
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ){
            Text(
                text = "Xin chào \nNgô Tuấn Anh",
                fontSize = 36.sp,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier.height(40.dp)
        )
        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Nhập hội nghị cần tìm kiếm....",
                    color = Color("#B5B4B4".toColorInt()),
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        CategoryComponent()
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier.height(400.dp)
        ){
            ComingEventComponent()
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}
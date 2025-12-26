package com.ptit_booth_chekin.project.ui.screen.home.screen_home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.ui.theme.JosefinSans


@Composable
fun LocationText(
    state: String? = null,
    city: String? = null
) {
    Row(
        modifier = Modifier

            .background(
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_loc),
            contentDescription = ""
        )
        Spacer(
            Modifier.width(8.dp)
        )
        Text(
            text = if(state != null && city != null) "$state, $city" else "Try again later!",
            color = Color.Black,
            fontFamily = JosefinSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
private fun LcoationPrev() {
    LocationText()
}
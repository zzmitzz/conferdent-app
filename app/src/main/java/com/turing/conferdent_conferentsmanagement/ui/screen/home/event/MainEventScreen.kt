package com.turing.conferdent_conferentsmanagement.ui.screen.home.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImage
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans


@Composable
fun MainEventScreen(

) {
}

@Composable
fun EventHeader(
    modifier: Modifier,
    navigateBack: () -> Unit = {}
){
    Box(
        modifier = modifier
            .background(
                color = Color.White,
            ),
        contentAlignment = Alignment.Center
    ){

        AsyncImage(
            model = "",
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Column(
            modifier = modifier
                .background(
                    color = Color.White,
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
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
                            navigateBack()
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

                Image(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).padding(
                        vertical = 20.dp,
                        horizontal = 12.dp
                    ).background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EventHeaderPREV() {
    EventHeader(
        modifier = Modifier,
        navigateBack = {}
    )
}
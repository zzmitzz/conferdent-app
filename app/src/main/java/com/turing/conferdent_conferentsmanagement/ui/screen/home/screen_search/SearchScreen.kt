package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_search

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.EventCard
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_search.components.SearchComponents
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans


@Composable
fun SearchScreen(
    navigateBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().background(
            color = Color("#ECECEE".toColorInt())
        ).padding(
            horizontal = 16.dp
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ).padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ).clickable{
                navigateBack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Quay lại",
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
        SearchComponents()
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn {
            items(10) {
                Box(
                ){
                    EventCard()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SePV() {
    SearchScreen()
}
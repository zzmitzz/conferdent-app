package com.turing.conferdent_conferentsmanagement.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt


@Composable
fun BottomNavigationBar(
    onTopLevelClick: (TopLevelDestination) -> Unit = {},
    currentDestination: TopLevelDestination = TopLevelDestination.HOME,
) {
    Box(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 21.dp
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconState(
                    isChosen = currentDestination == TopLevelDestination.HOME,
                    icon = TopLevelDestination.HOME,
                    onClick = onTopLevelClick
                )
                IconState(
                    isChosen = currentDestination == TopLevelDestination.FAVOURITE,
                    icon = TopLevelDestination.FAVOURITE,
                    onClick = onTopLevelClick
                )
                IconState(
                    isChosen = currentDestination == TopLevelDestination.CHATBOT,
                    icon = TopLevelDestination.CHATBOT,
                    onClick = onTopLevelClick
                )
                IconState(
                    isChosen = currentDestination == TopLevelDestination.NOTIFICATION,
                    icon = TopLevelDestination.NOTIFICATION,
                    onClick = onTopLevelClick
                )
                IconState(
                    isChosen = currentDestination == TopLevelDestination.SETTING,
                    icon = TopLevelDestination.SETTING,
                    onClick = onTopLevelClick
                )
            }
        }
    }
}

@Composable
fun IconState(
    isChosen: Boolean,
    icon: TopLevelDestination,
    onClick: (TopLevelDestination) -> Unit
) {
    return Box(
        modifier = Modifier
            .clickable {
                onClick(icon)
            }
            .background(
                shape = RoundedCornerShape(200.dp),
                color = if (isChosen) Color("#22272F".toColorInt()) else Color.Transparent
            )
            .padding(vertical = 12.dp, horizontal = 21.dp)
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = if (isChosen) painterResource(icon.selectedIcon) else painterResource(icon.unSelectedIcon),
            contentDescription = ""
        )
    }
}

@Preview()
@Composable
private fun BTMPreview() {
    BottomNavigationBar()
}
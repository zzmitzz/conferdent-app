package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_category_filter

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.EventCard
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.EventCardInformationUI
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans

@Composable
fun CategoryFilterScreen(
    onNavBack: () -> Unit = {},
    onNavEventDetail: (String) -> Unit = {},
    viewModel: CategoryFilterVM = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val categoryName = viewModel.getCategoryDisplayName()

    CategoryFilterStateless(
        categoryName = categoryName,
        uiState = uiState,
        onNavBack = onNavBack,
        onNavEventDetail = onNavEventDetail
    )
}

@Composable
fun CategoryFilterStateless(
    categoryName: String,
    uiState: CategoryFilterState,
    onNavBack: () -> Unit = {},
    onNavEventDetail: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color("#ECECEE".toColorInt()))
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_left_rec),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNavBack() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = categoryName,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on state
        when (uiState) {
            is CategoryFilterState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    RoseCurveSpinner(color = Color.Black)
                }
            }

            is CategoryFilterState.Success -> {
                if (uiState.events.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img_loading),
                                contentDescription = "No events",
                                modifier = Modifier.size(120.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Không có sự kiện nào",
                                fontFamily = JosefinSans,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Box(
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            ) {
                                Text(
                                    text = "${uiState.events.size} sự kiện",
                                    fontFamily = JosefinSans,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        items(uiState.events) { event ->
                            EventCard(
                                eventCardInformationUI = event,
                                onEventClick = { eventId ->
                                    onNavEventDetail(eventId)
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }

            is CategoryFilterState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Đã xảy ra lỗi",
                            fontFamily = JosefinSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.message,
                            fontFamily = JosefinSans,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoryFilterPreview() {
    CategoryFilterStateless(
        categoryName = "Công nghệ",
        uiState = CategoryFilterState.Success(
            listOf(
                EventCardInformationUI(
                    id = "1",
                    title = "Hội nghị Công nghệ Số Việt Nam 2025",
                    category = "Công nghệ",
                    organization = "VNTechConf",
                    logo = "https://example.com/logo.png",
                    startTime = "2025-11-10T09:00:00.000Z",
                    endTime = "2025-11-10T17:00:00.000Z",
                    location = "Trung tâm hội nghị Quốc Gia, TP. Hà Nội"
                )
            )
        )
    )
}

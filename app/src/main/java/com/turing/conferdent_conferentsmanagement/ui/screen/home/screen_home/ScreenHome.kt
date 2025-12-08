package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home

import android.Manifest
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.CategoryComponent
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.ComingEventComponent
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.LocationText
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans
import com.turing.conferdent_conferentsmanagement.utils.getAddressFromLatLng
import com.turing.conferdent_conferentsmanagement.utils.getCurrentLocation


@Preview
@Composable
private fun HomePrev() {
    ScreenStateless(
        scrollState = rememberScrollState()
    )
}

@Composable
fun ScreenHome(
    onNavSearch: () -> Unit = {},
    viewModel: ScreenHomeVM = hiltViewModel(),
    onNavEventDetail: (String) -> Unit = {},
    onNavCategoryFilter: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val userState by viewModel.uiState.collectAsStateWithLifecycle()
    val eventState by viewModel.eventState.collectAsStateWithLifecycle()
    var city by remember { mutableStateOf<String?>(null) }
    var province by remember { mutableStateOf<String?>(null) }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                getCurrentLocation(fusedLocationClient) { lat, lon ->
                    val address = getAddressFromLatLng(context, lat, lon)
                    city = address?.subAdminArea
                    province = address?.thoroughfare
                }
            }
        }


    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    ScreenStateless(
        onNavSearch = {
            onNavSearch()
        },
        scrollState = scrollState,
        currentLocation = if (city != null && province != null) (province!! to city!!) else null,
        userName = userState,
        eventState = eventState,
        onNavEventDetail = {
            onNavEventDetail(it)
        },
        onNavCategoryFilter = {
            onNavCategoryFilter(it)
        }
    )
}

@Composable
fun ScreenStateless(
    onNavSearch: () -> Unit = {},
    scrollState: ScrollState,
    currentLocation: Pair<String, String>? = null,
    userName: ScreenHomeViewState = ScreenHomeViewState.Loading,
    eventState: ScreenHomeEvent = ScreenHomeEvent.LoadEvent,
    onNavEventDetail: (String) -> Unit = {},
    onNavCategoryFilter: (String) -> Unit = {}
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
            LocationText(
                currentLocation?.first,
                currentLocation?.second
            )
        }
        Spacer(
            modifier = Modifier.height(33.dp)
        )
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            when (userName) {
                is ScreenHomeViewState.Success -> {
                    Text(
                        text = "Xin chào \n${userName.name}",
                        fontSize = 36.sp,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Bold
                    )
                }

                is ScreenHomeViewState.Error -> {
                    Text(
                        text = "Chào mừng quay lại",
                        fontSize = 36.sp,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Bold
                    )
                }

                is ScreenHomeViewState.Loading -> {

                }
            }
        }
        Spacer(
            modifier = Modifier.height(40.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable {
                    onNavSearch()
                }
        ) {
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
        CategoryComponent(
            onCategoryClick = { categoryId ->
                onNavCategoryFilter(categoryId)
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier.height(400.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (eventState) {
                is ScreenHomeEvent.LoadEventSuccess -> {
                    ComingEventComponent(
                        eventCardInformationUIList = eventState.eventCardInformationUIList
                    ){
                        onNavEventDetail(it)
                    }
                }

                is ScreenHomeEvent.LoadEventError -> {
                    Text(
                        text = eventState.message,
                        color = Color.Red
                    )
                }

                is ScreenHomeEvent.LoadEvent -> {
                    RoseCurveSpinner(
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(128.dp))
    }
}
package com.ptit_booth_chekin.project.ui.screen.home.event.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.ui.screen.home.event.models.SpeakerUIModel
import com.ptit_booth_chekin.project.ui.theme.JosefinSans
import androidx.compose.ui.text.style.TextOverflow


@Preview
@Composable
private fun HeaderPrev() {
    HeaderComponents(Modifier.fillMaxWidth()) { }
}

@Composable
fun HeaderComponents(
    modifier: Modifier,
    isEventOnGoing: Boolean = true,
    navigateBack: () -> Unit = {},
    onResourceClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = androidx.compose.ui.Modifier
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
                modifier = androidx.compose.ui.Modifier.size(16.dp)
            )
            Spacer(modifier = androidx.compose.ui.Modifier.width(8.dp))
            Text(
                text = "Quay lại",
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }

        Row(

        ) {
            if(isEventOnGoing){
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_resource),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onResourceClick()
                            }
                    )
                }
                Spacer(Modifier.width(12.dp))
            }
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onMoreClick()
                        }
                )
            }
        }
    }
}


@Composable
fun InfoRowMain(icon: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(icon),
            contentDescription = null, // Decorative icon
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            color = Color.Black
        )
    }
}

@Composable
fun ProfileCard(
    speakerUIModel: SpeakerUIModel,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile image
        AsyncImage(
            model = speakerUIModel.avatar,
            contentDescription = "Profile photo",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Name
        Text(
            text = speakerUIModel.name,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Role & company
        Text(
            text = speakerUIModel.workingAt,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = Color(0xFF757575) // grey
        )
    }
}

@Composable
fun SpeakerListRow(
    data: List<SpeakerUIModel>,
    onSpeakerClick: (String) -> Unit = {}
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
        ,
    ) {
        data.forEach {
            ProfileCard(
                speakerUIModel = it,
                onClick = { onSpeakerClick(it.id.toString()) }
            )
            Spacer(modifier = Modifier.width(24.dp))
        }
    }
}

@Composable
fun RegistrationCta(
    modifier: Modifier,
    capacity: Int = 300,
    isRegistered: Boolean = false,
    isOvered: Boolean = false,
    onButtonClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White) // Assuming a white background
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween, // Pushes items to the ends
        verticalAlignment = Alignment.CenterVertically // Aligns items vertically
    ) {
        // --- Left Text Block ---
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Giới hạn số lượng:",
                fontSize = 14.sp,
                color = Color.Gray // Softer color for the label
            )
            Text(
                text = "${capacity} người",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        if(!isOvered){
            Button(
                onClick = {
                    onButtonClick()
                },
                shape = RoundedCornerShape(50.dp), // Creates the "pill" shape
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(isRegistered) Color.Red else Color(0xFF303030)  // Dark gray/charcoal color
                ),
                // Padding inside the button
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between icon and text
                ) {
                    Icon(
                        imageVector = if(isRegistered) Icons.Default.Cancel else Icons.Default.SaveAlt, // Replace with your icon
                        contentDescription = "Đăng ký", // For accessibility
                        tint = Color.White,
                        modifier = Modifier.size(20.dp) // Control icon size
                    )
                    Text(
                        text = if(isRegistered) "Hủy đăng ký" else "Đăng ký",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationHoldingNotRegistered(){
    Text(
        text = "Bạn chưa đăng kí hội nghị này, vui lòng liên hệ BTC",
        modifier = Modifier.fillMaxWidth().background(Color.White).padding(vertical = 24.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun RegistrationHoldingCta(
    modifier: Modifier,
    isMapAvail: Boolean = false, // should be boolean
    onCheckIn: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onScheduleClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White) // Assuming a white background
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Use spacedBy for even spacing
        verticalAlignment = Alignment.CenterVertically // Aligns items vertically
    ) {
        Button(
            onClick = {
                onCheckIn()
            },
            modifier = Modifier.weight(1f), // Distribute width equally
            shape = RoundedCornerShape(50.dp), // Creates the "pill" shape
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF303030)  // Dark gray/charcoal color
            ),
            // Padding inside the button
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center, // Center content within button
                modifier = Modifier.fillMaxWidth() // Fill button width
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_check_in),
                    contentDescription = "Check-in", // For accessibility
                    modifier = Modifier.size(18.dp) // Control icon size
                )
                Spacer(modifier = Modifier.width(4.dp)) // Add spacer here
                Text(
                    text = "Check-in",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    maxLines = 1, // Single line
                    overflow = TextOverflow.Ellipsis // Three dots on overflow
                )
            }
        }
        Button(
            onClick = {
                if(isMapAvail){
                    onMapClick()
                }
            },
            modifier = Modifier.weight(1f).alpha(
                if(isMapAvail) 1f else 0.5f
            ), // Distribute width equally
            shape = RoundedCornerShape(50.dp), // Creates the "pill" shape
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF303030)  // Dark gray/charcoal color
            ),
            // Padding inside the button
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center, // Center content within button
                modifier = Modifier.fillMaxWidth() // Fill button width
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_map),
                    contentDescription = "Bản đồ", // For accessibility
                    modifier = Modifier.size(18.dp) // Control icon size
                )
                Spacer(modifier = Modifier.width(4.dp)) // Add spacer here
                Text(
                    text = "Bản đồ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    maxLines = 1, // Single line
                    overflow = TextOverflow.Ellipsis // Three dots on overflow
                )
            }
        }

        Button(
            onClick = {
                onScheduleClick()
            },
            modifier = Modifier.weight(1f), // Distribute width equally
            shape = RoundedCornerShape(50.dp), // Creates the "pill" shape
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF303030)  // Dark gray/charcoal color
            ),
            // Padding inside the button
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center, // Center content within button
                modifier = Modifier.fillMaxWidth() // Fill button width
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = "Lịch trình", // For accessibility
                    modifier = Modifier.size(18.dp) // Control icon size
                )
                Spacer(modifier = Modifier.width(4.dp)) // Add spacer here
                Text(
                    text = "Lịch",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    maxLines = 1, // Single line
                    overflow = TextOverflow.Ellipsis // Three dots on overflow
                )
            }
        }


    }
}


// --- Preview Function ---
@Preview(showBackground = true)
@Composable
fun RegistrationCtaPreview() {
    RegistrationCta(Modifier)
}

@Preview
@Composable
private fun SpeakerPREV() {
    SpeakerListRow(data = listOf(
        SpeakerUIModel(
            name = "Speaker 1",
            avatar = "https://cdn.dribbble.com/userupload/23788648/file/original-a893a015e4cb1a39778df5de2b242107.jpg?resize=400x0",
            workingAt = "PTIT",
        ),
        SpeakerUIModel(
            name = "Speaker 1",
            avatar = "https://cdn.dribbble.com/userupload/23788648/file/original-a893a015e4cb1a39778df5de2b242107.jpg?resize=400x0",
            workingAt = "PTIT",
        ),
        SpeakerUIModel(
            name = "Speaker 1",
            avatar = "https://cdn.dribbble.com/userupload/23788648/file/original-a893a015e4cb1a39778df5de2b242107.jpg?resize=400x0",
            workingAt = "PTIT",
        ),
        SpeakerUIModel(
            name = "Speaker 1",
            avatar = "https://cdn.dribbble.com/userupload/23788648/file/original-a893a015e4cb1a39778df5de2b242107.jpg?resize=400x0",
            workingAt = "PTIT",
        )
    ))
    
}

@Preview
@Composable
private fun InforRowMainOPrev() {
    InfoRowMain(icon = R.drawable.ic_org, text = "Organization")
}

@Preview
@Composable
private fun RegistrationCTAPreview() {
    RegistrationHoldingCta(
        modifier = Modifier.fillMaxWidth()
    )
}
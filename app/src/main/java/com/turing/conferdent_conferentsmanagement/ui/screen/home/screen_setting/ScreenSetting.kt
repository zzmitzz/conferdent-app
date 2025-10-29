package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.components.LogoutConfirmDialog
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans

@Composable
fun ScreenSetting() {
    PersonalSettingsScreen()
}

@Composable
fun PersonalSettingsScreen() {

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFF5F5F5)
            )
            .padding(start = 16.dp, end = 16.dp, top = 50.dp)
    ) {
        Text(
            text = stringResource(R.string.personal_settings),
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
            fontSize = 36.sp,
            modifier = Modifier.padding(bottom = 24.dp, start = 8.dp)
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())

        ) {
            ProfileInfoCard()
            Spacer(modifier = Modifier.height(20.dp))
            SettingsOptionsCard()
            Spacer(modifier = Modifier.height(20.dp))
            LogoutButton()
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    LogoutConfirmDialog(
        showDialog = showDialog,
        onConfirm = {},
        onDismiss = {}
    )
}

@Composable
fun ProfileInfoCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_auth), // Replace with your image
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = "Nguyễn Văn A",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontFamily = JosefinSans,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Info Rows Section
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                InfoRow(icon = R.drawable.ic_email, text = "nguyenvanvan@email.com")
                InfoRow(icon = R.drawable.ic_phone, text = "(+84) 38-4132699")
                InfoRow(icon = R.drawable.ic_calendar_dates, text = "12th Dec 2003")
                InfoRow(icon = R.drawable.ic_bio_user, text = "Điệp lơ tơ mơ ngầu xì dách lem mem.")
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Edit Button
            Button(
                onClick = { /* Handle edit click */ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(16), // Fully rounded
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E2E2E), // Dark gray
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Chỉnh sửa",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 16.sp,
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Helper for the info rows (icon + text)
@Composable
fun InfoRow(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null, // Decorative
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

// --- Block 2: Settings Options Card ---
@Composable
fun SettingsOptionsCard() {
    // State for the checkboxes
    var notificationsEnabled by remember { mutableStateOf(true) }
    var autofillEnabled by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp) // Less horizontal padding for rows
        ) {
            SettingsToggleRow(
                text = "Bật thông báo",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            Divider(
                color = Color(0xFFF0F0F0), // Light divider
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp) // Indent divider
            )
            SettingsToggleRow(
                text = "Tự động điền thông tin có sẵn",
                checked = autofillEnabled,
                onCheckedChange = { autofillEnabled = it }
            )
        }
    }
}

// Helper for the settings rows (text + checkbox)
@Composable
fun SettingsToggleRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 12.dp), // Row padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontFamily = JosefinSans,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f)) // Pushes checkbox to the end
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black, // Dark checked color
                uncheckedColor = Color.LightGray,
                checkmarkColor = Color.White
            )
        )
    }
}

// --- Block 3: Logout Button ---
@Composable
fun LogoutButton() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle logout click */ },
        color = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Logout,
                contentDescription = "Đăng xuất",
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Đăng xuất",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontFamily = JosefinSans,
                color = Color.Red
            )
        }
    }
}


@Preview
@Composable
private fun ScreenSettingPreview() {
    PersonalSettingsScreen()
}
package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.components.LogoutConfirmDialog
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun ScreenSetting(
    resetToLogin: () -> Unit = {},
    viewModel: SettingViewModel
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    when (uiState.value) {
        is SettingVMState.Success -> {
            PersonalSettingsScreen(
                doLogout = {
                    viewModel.logout() {
                        resetToLogin()
                    }
                },
                userProfile = (uiState.value as SettingVMState.Success).userProfile,
                isEditMode = (uiState.value as SettingVMState.Success).isEditMode,
                switchEditMode = { viewModel.switchEditMode() },
                updateUserProfile = { viewModel.updateUserProfile(it,context ) }
            )
        }

        is SettingVMState.Error -> {
            resetToLogin()
        }

        is SettingVMState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                RoseCurveSpinner(
                    color = Color.Black
                )
            }
        }
    }

}

@Composable
fun PersonalSettingsScreen(
    doLogout: () -> Unit = {},
    userProfile: UserProfile? = null,
    isEditMode: Boolean = false,
    switchEditMode: () -> Unit = {},
    updateUserProfile: (UserProfile) -> Unit = {}
) {

    val showDialog = remember { mutableStateOf(false) }

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
            ProfileInfoCard(
                userProfile = userProfile,
                isEditMode = isEditMode,
                switchEditMode = switchEditMode,
                updateUserProfile = {
                    updateUserProfile(it)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            SettingsOptionsCard()
            Spacer(modifier = Modifier.height(20.dp))
            LogoutButton() {
                showDialog.value = true
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    LogoutConfirmDialog(
        showDialog = showDialog.value,
        onConfirm = {
            doLogout()
        },
        onDismiss = {
            showDialog.value = false
        }
    )
}

enum class EditTextType{
    TEXT,
    NUMBER,
    DATE
}

@Composable
fun ProfileInfoCard(
    userProfile: UserProfile? = null,
    isEditMode: Boolean = false,
    switchEditMode: () -> Unit = {},
    updateUserProfile: (UserProfile) -> Unit = {}
) {

    val mailTextChange = remember { mutableStateOf(userProfile?.email ?: "") }
    val phoneTextChange = remember { mutableStateOf(userProfile?.phone ?: "") }
    val dobTextChange = remember { mutableStateOf(userProfile?.dob ?: "") }
    val bioTextChange = remember { mutableStateOf(userProfile?.bio ?: "") }
    val addressChange = remember { mutableStateOf(userProfile?.address ?: "") }

    var selectedURI by remember { mutableStateOf<Uri?>(null) }
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { selectedURI = it }
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
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = userProfile?.avatarURL, // Replace with your image
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                )
                if (isEditMode) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Black.copy(alpha = 0.5f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedURI == null) {
                            Image(
                                painter = painterResource(R.drawable.ic_camera),
                                modifier = Modifier.clickable {
                                    pickMediaLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                                contentDescription = ""
                            )
                        } else {
                            AsyncImage(
                                model = selectedURI,
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = userProfile?.fullName ?: "",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontFamily = JosefinSans,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Info Rows Section
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                InfoRow(
                    icon = R.drawable.ic_email, text = userProfile?.email ?: "", isEditMode,
                    editText = mailTextChange.value,
                    onEditText = {
                        mailTextChange.value = it
                    },
                    textType = EditTextType.TEXT)
                InfoRow(
                    icon = R.drawable.ic_phone, text = userProfile?.phone ?: "", isEditMode,
                    editText = phoneTextChange.value,
                    onEditText = {
                        phoneTextChange.value = it
                    },
                    textType = EditTextType.NUMBER)
                InfoRow(
                    icon = R.drawable.ic_calendar_dates,
                    text = userProfile?.dob ?: "",
                    isEditMode,
                    editText = dobTextChange.value ,
                    onEditText = {
                        dobTextChange.value = it
                    },
                    textType = EditTextType.DATE
                )
                InfoRow(
                    icon = R.drawable.ic_loc, text = userProfile?.address ?: "", isEditMode,
                    editText = addressChange.value,
                    onEditText = { addressChange.value = it },
                    textType = EditTextType.TEXT
                )
                InfoRow(
                    icon = R.drawable.ic_bio_user, text = userProfile?.bio ?: "", isEditMode,
                    editText = bioTextChange.value,
                    onEditText = {
                        bioTextChange.value = it
                    },
                    textType = EditTextType.TEXT)
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Edit Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditMode) {
                    Text(
                        text = "Hủy",
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                mailTextChange.value = userProfile?.email ?: ""
                                phoneTextChange.value = userProfile?.phone ?: ""
                                dobTextChange.value = userProfile?.dob ?: ""
                                bioTextChange.value = userProfile?.bio ?: ""
                                addressChange.value = userProfile?.address ?: ""
                                switchEditMode()
                            }
                    )
                    Spacer(
                        modifier = Modifier.width(48.dp)
                    )
                }

                Button(
                    onClick = {
                        if (isEditMode) {
                            updateUserProfile(
                                UserProfile(
                                    fullName = userProfile?.fullName ?: "",
                                    email = mailTextChange.value ?: "",
                                    phone = phoneTextChange.value ?: "",
                                    dob = dobTextChange.value ?: "",
                                    bio = bioTextChange.value ?: "",
                                    avatarURL = if (selectedURI != null) selectedURI.toString() else null,
                                    address = addressChange.value ?: ""
                                )
                            )
                        }
                        switchEditMode()
                    },
                    shape = RoundedCornerShape(16), // Fully rounded
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E2E2E), // Dark gray
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isEditMode) "Lưu" else "Chỉnh sửa",
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        fontSize = 16.sp,
                        fontFamily = JosefinSans,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: Int,
    text: String?,
    isEditMode: Boolean = false,
    editText: String,
    textType: EditTextType,
    onEditText: (String) -> Unit
) {
    if(textType == EditTextType.DATE){
        val context = LocalContext.current
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var selectedDate by rememberSaveable { mutableStateOf(
            if(!text.isNullOrBlank()){
                dateFormatter.parse(text)
            } else {
                null
            }
        ) }
        val interactionSource = remember { MutableInteractionSource() }
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(year, month, dayOfMonth)
                val newDate = calendar.time
                selectedDate = newDate
                onEditText(dateFormatter.format(newDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null, // Decorative
                modifier = Modifier.size(24.dp)
            )

            Box(
                modifier = Modifier
            ){
                if(isEditMode){
                    TextField(
                        value = if(selectedDate != null) dateFormatter.format(selectedDate!!) else "Chưa cung cấp",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth().clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ){
                            datePickerDialog.show()
                        },
                        singleLine = true,
                        readOnly = true,
                        enabled = false,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = when (textType) {
                                EditTextType.TEXT -> KeyboardType.Text
                                EditTextType.NUMBER -> KeyboardType.Number
                                else -> KeyboardType.Text
                            },
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        )
                    )
                }else{
                    Text(
                        text = if(selectedDate != null) dateFormatter.format(selectedDate!!) else "Chưa cung cấp",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                    )
                }
            }
        }
    }else{
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null, // Decorative
                modifier = Modifier.size(24.dp)
            )
            if (isEditMode) {
                TextField(
                    value = editText,
                    onValueChange = {
                        onEditText(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = when (textType) {
                            EditTextType.TEXT -> KeyboardType.Text
                            EditTextType.NUMBER -> KeyboardType.Number
                            else -> KeyboardType.Text
                        },
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    )
                )
            } else {
                Text(
                    text = text ?: "Chưa cung cấp",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }

}

@Composable
fun SettingsOptionsCard() {
    var notificationsEnabled by remember { mutableStateOf(true) }

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
fun LogoutButton(
    doLogout: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                doLogout()
            },
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
package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_chat

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_chat.components.ChatBoxComponents
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_chat.model.ChatDataUI


@Composable
fun ScreenChatStateless(
    viewModel: ScreenChatVM,
) {

    val listChat = viewModel.listChat.collectAsStateWithLifecycle()
    var messageText by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()

    LaunchedEffect(listChat.value.size) {
        scrollState.animateScrollToItem(listChat.value.size - 1)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White, shape = RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Chat với Conferdent AI",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(vertical = 24.dp)
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState
            ) {
                items(listChat.value.size) { index ->
                    Box(
                        modifier = Modifier.padding(vertical = 6.dp)
                    ){
                        ChatBoxComponents(
                            modifier = Modifier,
                            chatDataUI = listChat.value[index]
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 120.dp,),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(53.dp),
                value = messageText,
                onValueChange = { messageText = it },
                placeholder = {
                    Text(
                        "Nhập tin nhắn...",
                        color = Color("#B5B4B4".toColorInt())
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color("#F5F5F5".toColorInt()),
                    unfocusedContainerColor = Color("#F5F5F5".toColorInt()),
                    disabledContainerColor = Color("#F5F5F5".toColorInt()),
                    errorContainerColor = Color("#F5F5F5".toColorInt()),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(R.drawable.ic_send_message),
                contentDescription = "Send message",
                modifier = Modifier.clickable {
                    if (messageText.isNotBlank()) {
                        viewModel.userSendChat(messageText)
                        messageText = ""
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ScreenChatPrev() {

}
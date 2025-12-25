package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_chat.model.ChatDataUI


@Composable
fun ChatBoxComponents(
    modifier: Modifier,
    chatDataUI: ChatDataUI
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(
                end = if (chatDataUI.fromBot) 90.dp else 24.dp,
                start = if (chatDataUI.fromBot) 24.dp else 90.dp
            ),
        contentAlignment = if(chatDataUI.fromBot) androidx.compose.ui.Alignment.CenterStart else androidx.compose.ui.Alignment.CenterEnd

    ){
        Box(
            modifier = Modifier.background(
                shape = RoundedCornerShape(
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp,
                    topStart = if (!chatDataUI.fromBot) 20.dp else 0.dp,
                    topEnd = if (!chatDataUI.fromBot) 0.dp else 20.dp
                ),
                color = Color(if(chatDataUI.fromBot) "#22272F".toColorInt() else "#FFFFFF".toColorInt())
            ).padding(
                vertical = 10.dp,
                horizontal = 20.dp
            )
        ){
            Text(
                text = buildAnnotatedString {
                    processStyledText(chatDataUI.message)
                },
                color = if(chatDataUI.fromBot) Color.White else Color.Black,
                fontSize = 16.sp
            )
        }
    }
}

/**
 * Helper function to process text with markdown-style formatting:
 * - **text** for bold
 * - *text* for semi-bold (medium weight)
 * - 'text' for italic
 */

enum class StyleType(

){
    BOLD, SEMI_BOLD, ITALIC
}
@Composable
private fun androidx.compose.ui.text.AnnotatedString.Builder.processStyledText(text: String) {
    var currentIndex = 0
    
    // Regex patterns for different styles
    val boldPattern = Regex("""\*\*(.+?)\*\*""")
    val semiBoldPattern = Regex("""\*(.+?)\*""")
    val italicPattern = Regex("""'(.+?)'""")
    
    // Find all matches with their positions
    data class Match(val range: IntRange, val text: String, val type: StyleType)

    val matches = mutableListOf<Match>()
    
    // Find bold matches (**text**)
    boldPattern.findAll(text).forEach { matchResult ->
        matches.add(Match(
            matchResult.range,
            matchResult.groupValues[1],
            StyleType.BOLD
        ))
    }
    
    // Find semi-bold matches (*text*) - but exclude those already matched as bold
    semiBoldPattern.findAll(text).forEach { matchResult ->
        val isPartOfBold = matches.any { 
            it.type == StyleType.BOLD && matchResult.range.first >= it.range.first && matchResult.range.last <= it.range.last 
        }
        if (!isPartOfBold) {
            matches.add(Match(
                matchResult.range,
                matchResult.groupValues[1],
                StyleType.SEMI_BOLD
            ))
        }
    }
    
    // Find italic matches ('text')
    italicPattern.findAll(text).forEach { matchResult ->
        matches.add(Match(
            matchResult.range,
            matchResult.groupValues[1],
            StyleType.ITALIC
        ))
    }
    
    // Sort matches by position
    val sortedMatches = matches.sortedBy { it.range.first }
    
    // Build the annotated string
    sortedMatches.forEach { match ->
        // Append text before the match
        if (currentIndex < match.range.first) {
            append(text.substring(currentIndex, match.range.first))
        }
        
        // Append styled text
        when (match.type) {
            StyleType.BOLD -> {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(match.text)
                }
            }
            StyleType.SEMI_BOLD -> {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                    append(match.text)
                }
            }
            StyleType.ITALIC -> {
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(match.text)
                }
            }
        }
        
        currentIndex = match.range.last + 1
    }
    
    // Append remaining text
    if (currentIndex < text.length) {
        append(text.substring(currentIndex))
    }
}


@Preview(showBackground = true)
@Composable
fun ChatBoxPrev() {
    Column() {
        ChatBoxComponents(
            modifier = Modifier,
            chatDataUI = ChatDataUI(
                id = 0,
                fromBot = true,
                message = "Hello"
            )
        )
        ChatBoxComponents(
            modifier = Modifier,
            chatDataUI = ChatDataUI(
                id = 0,
                fromBot = false,
                message = "Helasnfasdnfiuasf aweefsnfaksdnfads f sfasf  \n fasjfaosjfaowejflo"
            )
        )
    }
}
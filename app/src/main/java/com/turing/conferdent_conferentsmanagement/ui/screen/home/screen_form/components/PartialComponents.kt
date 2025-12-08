package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.turing.conferdent_conferentsmanagement.models.FormFields
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form.data.AnswerType
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form.data.castToAnswerType
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans


@Composable
fun HeaderForm(
    modifier: Modifier,
    formTitle: String,
    formDesc: String,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp
            )
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
                    onBackClick()
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
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        Text(
            text = formTitle,
            fontFamily = JosefinSans,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = formDesc,
            fontFamily = JosefinSans,
            color = Color("#6B6B6B".toColorInt()),
            fontSize = 13.sp
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = "(*) là thông tin bắt buộc cần điền",
            fontFamily = JosefinSans,
            color = Color("#D46565".toColorInt()),
            fontSize = 13.sp
        )
    }
}


@Composable
fun QuestionSection(
    formField: FormFields,
    onAnswerChange: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
    ) {
        Row {
            Text(
                text = formField.fieldLabel ?: "",
                fontSize = 16.sp,
                color = Color.Black
            )
            if (formField.required == true) {
                Text(
                    text = " *",
                    fontSize = 16.sp,
                    color = Color(0xFFD46565)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = formField.fieldDescription ?: "",
            fontSize = 12.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))
        val result = formField.fieldType
        result?.let {
            when (it.castToAnswerType()) {
                AnswerType.TEXT -> {
                    TextInput { result ->
                        onAnswerChange(result)
                    }
                }

                AnswerType.EMAIL -> {
                    EmailInput { result ->
                        onAnswerChange(result)
                    }
                }

                AnswerType.PHONE -> {
                    PhoneNumberInput { result ->
                        onAnswerChange(result)
                    }
                }

                AnswerType.FILE -> {
                    FileInput { uri ->
                        onAnswerChange(uri.toString())
                    }
                }

                AnswerType.RADIO -> {
                    RadioInput(
                        options = formField.fieldOptions
                    ) { result ->
                        onAnswerChange(result)
                    }
                }

                AnswerType.CHECKBOX -> {
                    CheckboxInput(
                        options = formField.fieldOptions
                    ) { result ->
                        onAnswerChange(
                            result.joinToString(
                                separator = ","
                            )
                        )
                    }
                }

                AnswerType.TEXTAREA -> {
                    TextAreaInput { result ->
                        onAnswerChange(result)
                    }
                }

                AnswerType.NUMBER -> {
                    NumberInput { result ->
                        onAnswerChange(result)
                    }
                }

                AnswerType.DATE -> {
                    DateInput { result ->
                        onAnswerChange(result.toString())
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun Preview1() {
    HeaderForm(
        Modifier,
        formTitle = "Form Title",
        formDesc = "Form Description",
        onBackClick = {}
    )
}

@Preview
@Composable
private fun Preview2() {
    QuestionSection(
        FormFields(
            fieldLabel = "Field Label",
            fieldDescription = "Field Description",
            fieldType = AnswerType.TEXT.name,
            required = true,
        )
    )
}

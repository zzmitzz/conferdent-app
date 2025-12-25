package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form

import android.R
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.models.FormFields
import com.turing.conferdent_conferentsmanagement.ui.screen.home.event.components.HeaderComponents
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form.components.HeaderForm
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form.components.QuestionSection
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form.data.AnswerType
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ScreenFillForm(
    navBack: () -> Unit,
    navNextScreen: () -> Unit,
    eventID: String,
    viewModel: ScreenFillFormVM = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getFormField(eventID = eventID)
    }


    var showConfirmExit by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val uiState = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ScreenFFUIEffect.NavigateToNextScreen ->
                    navNextScreen()

                is ScreenFFUIEffect.ShowToast -> {
                    android.widget.Toast.makeText(
                        context,
                        effect.message,
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    BackHandler(enabled = true) {
        showConfirmExit = !showConfirmExit
    }
    ScreenFillFormStateless(
        headerComponent = {
            when (val result = uiState.value) {
                ScreenFFState.Loading -> {
                    HeaderForm(
                        modifier = Modifier,
                        formTitle = "Loading",
                        formDesc = "Loading",
                    ) {
                        showConfirmExit = !showConfirmExit
                    }
                }

                is ScreenFFState.Error -> {
                    Text(
                        text = result.message ?: "Unknown error",
                        color = Color.Black
                    )
                }

                is ScreenFFState.Success -> {
                    HeaderForm(
                        modifier = Modifier,
                        formTitle = result.formData.title
                            ?: "Form không tồn tại, vui lòng thử lại sau",
                        formDesc = result.formData.description
                            ?: "Form không tồn tại, vui lòng thử lại sau",
                    ) {
                        showConfirmExit = !showConfirmExit
                    }
                }

            }

        },
        questionComponent = {
            when (val result = uiState.value) {
                ScreenFFState.Loading -> {
                    RoseCurveSpinner(
                        color = Color.Black
                    )
                }

                is ScreenFFState.Error -> {
                    Text(
                        text = result.message ?: "Unknown error",
                        color = Color.Black
                    )
                }

                is ScreenFFState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                    ) {
                        items(result.formData.fields.size) { index ->
                            QuestionSection(
                                formField = result.formData.fields[index]
                            ) {
                                viewModel.updateAnswer(
                                    questionId = result.formData.fields[index].Id ?: "-1",
                                    answer = it
                                )
                            }
                        }
                    }
                }

            }
        },
        submitResponse = {
            when (val result = uiState.value) {
                is ScreenFFState.Success -> {
                    // Check if all required questions are answered
                    val requiredFields = result.formData.fields
                    val unansweredRequired = requiredFields.filter { field ->
                        val answer = viewModel.answerMap[field.Id]
                        answer.isNullOrBlank()
                    }

                    if (unansweredRequired.isEmpty()) {
                        viewModel.submitForm(eventID)
                    } else {
                        // Show toast or error message for unanswered required questions
                        viewModel.showValidationError("Please answer all required questions")
                    }
                }

                else -> {
                    // Do nothing if not in success state
                }
            }
        },

        )

    if (showConfirmExit) {
        AlertDialog(
            onDismissRequest = {
                showConfirmExit = false
            },
            title = {
                Text(text = "Confirm Exit")
            },
            text = {
                Text(text = "Are you sure you want to exit without saving?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmExit = false
                        navBack()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Exit")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showConfirmExit = false
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ScreenFillFormStateless(
    headerComponent: @Composable () -> Unit = {},
    questionComponent: @Composable () -> Unit = {},
    submitResponse: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        headerComponent()
        Box(modifier = Modifier.weight(1f)) {
            questionComponent()
        }
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .wrapContentSize()
                .background(color = Color.Black, shape = RoundedCornerShape(16.dp))
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                )
                .clickable {
                    submitResponse()
                },
        ) {
            Text(
                text = "Đăng ký",
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ScreenFillFormStateless(
        headerComponent = {
            HeaderForm(
                modifier = Modifier,
                formTitle = "XIN CHAO LMAO LMAO",
                formDesc = "DOIJAOFIJDOISJDFDFJOIEOAONVIDUAUIVBEUFBIEF",
                onBackClick = {

                }
            )
        },
        questionComponent = {
            QuestionSection(
                formField = FormFields(
                    fieldLabel = "Field Label",
                    fieldDescription = "Field Description",
                    fieldType = AnswerType.TEXT.name,
                    required = true,
                )
            )
        }
    )
}

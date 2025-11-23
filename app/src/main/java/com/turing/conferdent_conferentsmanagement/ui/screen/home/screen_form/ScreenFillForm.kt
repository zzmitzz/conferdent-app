package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form

import android.R
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ScreenFFUIEffect.NavigateToNextScreen ->
                    navNextScreen()

                is ScreenFFUIEffect.ShowToast -> {

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

                ScreenFFState.Error -> {
                    Text(
                        text = "Error",
                        color = Color.Black
                    )
                }

                is ScreenFFState.Success -> {
                    HeaderForm(
                        modifier = Modifier,
                        formTitle = result.formData.title ?: "NOT FOUND",
                        formDesc = result.formData.title ?: "NOT FOUND",
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

                ScreenFFState.Error -> {
                    Text(
                        text = "Error",
                        color = Color.Black
                    )
                }

                is ScreenFFState.Success -> {
                    LazyColumn {
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
            viewModel.submitForm(eventID)
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
                TextButton(
                    onClick = {
                        showConfirmExit = false
                        navBack()
                    }
                ) {
                    Text("Exit", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showConfirmExit = false
                    }
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
        questionComponent()
        Box(
            modifier = Modifier
                .background(color = Color.Black, shape = RoundedCornerShape(8.dp))
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

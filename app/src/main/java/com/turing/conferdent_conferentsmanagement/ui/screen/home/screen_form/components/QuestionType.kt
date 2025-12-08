package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form.components

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.turing.conferdent_conferentsmanagement.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    initEmail: String = "",
    label: String = "Email",
    placeholder: String = "example@domain.com",
    onEmailChanged: (email: String) -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf(initEmail) }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = email,
        onValueChange = {
            email = it
            onEmailChanged(it)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
        )
    )
}

@Composable
fun PhoneNumberInput(
    modifier: Modifier = Modifier,
    initPhoneNumber: String = "",
    label: String = "Phone Number",
    placeholder: String = "e.g., 1234567890",
    onPhoneNumberChanged: (phoneNumber: String) -> Unit = {}
) {
    var phoneNumber by rememberSaveable { mutableStateOf(initPhoneNumber) }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = phoneNumber,
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { it.isDigit() }
            if (filteredValue.length <= 15) {
                phoneNumber = filteredValue
                onPhoneNumberChanged(filteredValue)
            }
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
        )
    )
}

@Composable
fun FileInput(
    modifier: Modifier = Modifier,
    label: String = "Upload File",
    onFileSelected: (uri: Uri?) -> Unit = {}
) {
    var selectedFileUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedFileUri = uri
        onFileSelected(uri)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = { launcher.launch("*/*") },
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(label)
        }
        Spacer(Modifier.height(8.dp))
        selectedFileUri?.let { uri ->
            val fileName = getFileNameFromUri(context, uri)
            Text(
                text = "Selected: ${fileName ?: uri.lastPathSegment ?: "Unknown File"}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        } ?: Text(
            "No file selected",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
    }
}

private fun getFileNameFromUri(context: Context, uri: Uri): String? {
    var fileName: String? = null
    val scheme = uri.scheme
    if (scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
    } else if (scheme == "file") {
        fileName = uri.lastPathSegment
    }
    return fileName
}

@Composable
fun RadioInput(
    modifier: Modifier = Modifier,
    options: List<String>,
    initSelectedOption: String? = null,
    label: String? = null,
    onOptionSelected: (String) -> Unit = {}
) {
    var selectedOption by rememberSaveable { mutableStateOf(initSelectedOption) }

    Column(modifier = modifier) {
        label?.let {
            Text(
                it,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Column(modifier = Modifier.selectableGroup()) {
            options.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                selectedOption = text
                                onOptionSelected(text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        colors = RadioButtonDefaults.colors().copy(
                            selectedColor = Color.Black,
                            unselectedColor = Color.Black
                        ),
                        onClick = null
                    )
                    Text(
                        text = text,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CheckboxInput(
    modifier: Modifier = Modifier,
    options: List<String>,
    initSelectedOptions: Set<String> = emptySet(),
    label: String? = null,
    onOptionsSelected: (Set<String>) -> Unit = {}
) {
    var selectedOptions by rememberSaveable { mutableStateOf(initSelectedOptions) }

    Column(modifier = modifier) {
        label?.let {
            Text(
                it,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        options.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable(onClick = {
                        selectedOptions = if (selectedOptions.contains(text)) {
                            selectedOptions - text
                        } else {
                            selectedOptions + text
                        }
                        onOptionsSelected(selectedOptions)
                    })
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedOptions.contains(text),
                    colors = CheckboxDefaults.colors().copy(
                        checkedBorderColor = Color.Black,
                        uncheckedBorderColor = Color.Black,
                        checkedBoxColor = Color.Black,
                    ),
                    onCheckedChange = null
                )
                Text(
                    text = text,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    initText: String = "",
    label: String = "Text Input",
    placeholder: String = "Enter text",
    maxLength: Int = 100,
    onTextChanged: (String) -> Unit = {}
) {
    var text by rememberSaveable { mutableStateOf(initText) }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = { newValue ->
            if (newValue.length <= maxLength) {
                text = newValue
                onTextChanged(newValue)
            }
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
        )
    )
}

@Composable
fun TextAreaInput(
    modifier: Modifier = Modifier,
    initText: String = "",
    label: String = "Text Area",
    placeholder: String = "Enter multiple lines of text",
    minLines: Int = 3,
    maxLines: Int = 5,
    onTextChanged: (String) -> Unit = {}
) {
    var text by rememberSaveable { mutableStateOf(initText) }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = { newValue ->
            text = newValue
            onTextChanged(newValue)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        minLines = minLines,
        maxLines = maxLines,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
        )
    )
}

@Composable
fun NumberInput(
    modifier: Modifier = Modifier,
    initNumber: String = "",
    label: String = "Number Input",
    placeholder: String = "Enter a number",
    onNumberChanged: (String) -> Unit = {}
) {
    var numberText by rememberSaveable { mutableStateOf(initNumber) }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = numberText,
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { it.isDigit() }
            numberText = filteredValue
            onNumberChanged(filteredValue)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
        )
    )
}

@Composable
fun DateInput(
    modifier: Modifier = Modifier,
    initDate: Date? = null,
    label: String = "Select Date",
    onDateSelected: (Date?) -> Unit = {}
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    initDate?.let {
        calendar.time = it
    }

    var selectedDate by rememberSaveable { mutableStateOf(initDate) }
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            val newDate = calendar.time
            selectedDate = newDate
            onDateSelected(newDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() },
        value = selectedDate?.let { dateFormatter.format(it) } ?: "",
        onValueChange = { },
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar_dates),
                contentDescription = "Select Date",
                modifier = Modifier.clickable { datePickerDialog.show() }
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
        )
    )
}

@Preview(showBackground = true, name = "QuestionType Previews")
@Composable
fun QuestionTypePreviews() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        EmailInput(initEmail = "test@example.com")
        Spacer(Modifier.height(16.dp))
        PhoneNumberInput(initPhoneNumber = "1234567890")
        Spacer(Modifier.height(16.dp))
        FileInput(label = "Upload Document")
        Spacer(Modifier.height(16.dp))
        RadioInput(
            label = "Choose an option",
            options = listOf("Option 1", "Option 2", "Option 3"),
            initSelectedOption = "Option 2"
        )
        Spacer(Modifier.height(16.dp))
        CheckboxInput(
            label = "Select multiple options",
            options = listOf("Item A", "Item B", "Item C"),
            initSelectedOptions = setOf("Item A", "Item C")
        )
        Spacer(Modifier.height(16.dp))
        TextInput(initText = "Sample text", label = "Short Text")
        Spacer(Modifier.height(16.dp))
        TextAreaInput(initText = "This is a longer text for the text area.", label = "Long Text")
        Spacer(Modifier.height(16.dp))
        NumberInput(initNumber = "123", label = "Quantity")
        Spacer(Modifier.height(16.dp))
        DateInput(
            label = "Event Date",
            initDate = Calendar.getInstance().apply { set(2024, Calendar.JULY, 20) }.time
        )
    }
}

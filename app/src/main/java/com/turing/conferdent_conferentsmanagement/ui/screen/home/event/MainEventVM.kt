package com.turing.conferdent_conferentsmanagement.ui.screen.home.event

import android.graphics.Bitmap
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.event.EventDetail
import com.turing.conferdent_conferentsmanagement.data.event.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject


sealed class MainEventVMState {
    data class Success(val event: EventDetail) : MainEventVMState()
    data class Error(val message: String) : MainEventVMState()
    object Loading : MainEventVMState()
}


sealed class QRGenerateState {
    data class Success(val qrCode: Bitmap) : QRGenerateState()
    object Loading : QRGenerateState()
    data class Error(val message: String) : QRGenerateState()
}


@HiltViewModel
class MainEventVM @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainEventVMState>(MainEventVMState.Loading)
    val uiState: StateFlow<MainEventVMState> = _uiState

    private var sharedEventID: String? = null
    fun fetchEventDetail(
        eventID: String?
    ){
        sharedEventID = eventID
        viewModelScope.launch(Dispatchers.IO) {
            if (eventID == null) {
                _uiState.value = (MainEventVMState.Error("Event ID is not valid"))
            } else {
                when (val result = eventRepository.getEventDetail(eventID)) {
                    is APIResult.Success -> {
                        _uiState.value = (MainEventVMState.Success(result.data))
                    }

                    is APIResult.Error -> {
                        _uiState.value = (MainEventVMState.Error(result.message))
                    }
                }
            }
        }
    }


    var qrScreenState = MutableStateFlow<QRGenerateState>(QRGenerateState.Loading)

    fun getRegistration(){
        viewModelScope.launch(Dispatchers.IO) {
            qrScreenState.value = QRGenerateState.Loading
            when (val result = eventRepository.getRegistrationForm(sharedEventID!!)) {
                is APIResult.Success -> {
                    generateQRCode(result.data.id!!.toString()).collect {
                        qrScreenState.value = QRGenerateState.Success(it)
                    }
                }

                is APIResult.Error -> {
                    qrScreenState.value = QRGenerateState.Error(result.message)
                }
            }
        }
    }

    fun generateQRCode(input: String): Flow<Bitmap>  = callbackFlow {
        withContext(Dispatchers.IO){
            while(isActive){
                val encodeJson = mapOf<String,String>(
                    "registeredID" to input,
                    "timeStamp" to System.currentTimeMillis().toString()
                )
                val input = Json.encodeToString(encodeJson)
                val qrgEncoder = QRGEncoder(
                    input,
                    null,
                    QRGContents.Type.TEXT,
                    500
                )
                val bitmap = qrgEncoder.getBitmap()
                trySend(bitmap)
                delay(3000)
            }
        }

        awaitClose {

        }
    }
}
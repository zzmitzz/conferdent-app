package com.ptit_booth_chekin.project.ui.screen.home.event

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.data.event.EventDetail
import com.ptit_booth_chekin.project.data.event.EventRepository
import com.ptit_booth_chekin.project.ui.screen.home.screen_setting.UserProfile
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
import com.ptit_booth_chekin.project.util.generateStyledQr
import com.ptit_booth_chekin.project.utils.Constants
import com.ptit_booth_chekin.project.utils.UserAccount
import com.ptit_booth_chekin.project.utils.saveBitmapToMediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    @ApplicationContext val mContext: Context,
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainEventVMState>(MainEventVMState.Loading)
    val uiState: StateFlow<MainEventVMState> = _uiState

    private var sharedEventID: String? = null
    var event: EventDetail? = null
    fun fetchEventDetail(
        eventID: String?
    ) {
        sharedEventID = eventID
        viewModelScope.launch(Dispatchers.IO) {
            if (eventID == null) {
                _uiState.value = (MainEventVMState.Error("Event ID is not valid"))
            } else {
                when (val result = eventRepository.getEventDetail(eventID)) {
                    is APIResult.Success -> {
                        event = result.data
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
    var bitmapGenerateJob: Job? = null
    fun getRegistration(
        eventID: String?
    ) {
        bitmapGenerateJob = viewModelScope.launch(Dispatchers.IO) {
            qrScreenState.value = QRGenerateState.Loading
            when (val result = eventRepository.getRegistrationForm(eventID ?: sharedEventID!!)) {
                is APIResult.Success -> {
                    generateQRCode(result.data.id!!.toString()).flowOn(Dispatchers.Default).collect {
                        qrScreenState.value = QRGenerateState.Success(it)
                    }
                }

                is APIResult.Error -> {
                    qrScreenState.value = QRGenerateState.Error(result.message)
                }
            }
        }
    }

    fun stopBitmapGenerate() {
        if (bitmapGenerateJob?.isActive == true) {
            bitmapGenerateJob?.cancel()
        }
    }

    fun generateQRCode(input: String): Flow<Bitmap> = flow {
        while (currentCoroutineContext().isActive) {
            val encodeJson = mapOf(
                "registeredID" to input,
                "timeStamp" to System.currentTimeMillis().toString()
            )

            val jsonInput = Json.encodeToString(encodeJson)

            emit(
                generateStyledQr(
                    content = jsonInput,
                    size = 700,
                    marginModules = 1,
                    foregroundStartColor = Color.BLACK,
                    foregroundEndColor = Color.BLACK,
                    backgroundTransparent = true,
                    logo = null,
                    logoScale = 0.18f
                )
            )

            delay(3000)
        }
    }

    fun saveBitmap(bitmap: Bitmap?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                bitmap?.let {
                    saveBitmapToMediaStore(mContext, it)
                }
            } catch (e: Exception) {

            }
        }
    }


    fun clearState() {
        _uiState.value = MainEventVMState.Loading
    }
}
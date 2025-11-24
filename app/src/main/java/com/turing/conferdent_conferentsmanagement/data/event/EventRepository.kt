package com.turing.conferdent_conferentsmanagement.data.event

import android.util.Log
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.event.models.FormData
import com.turing.conferdent_conferentsmanagement.data.event.models.RegisteredIDResponse
import com.turing.conferdent_conferentsmanagement.data.event.models.RegistrationResponseSubmit
import com.turing.conferdent_conferentsmanagement.data.event.models.Responses
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventEndpoint: EventEndpoint
) {
    private var eventPageFetch: Int = 1
    private var eventLimitFetch: Int = 10

    suspend fun getEvents(
        firstCall: Boolean = false
    ): APIResult<List<EventDetail>> {
        return try {
            if (firstCall) {
                eventPageFetch = 1
            }
            val result = eventEndpoint.getEvents(
                page = 1,
                limit = eventLimitFetch
            )
            if (result.isSuccessful && result.body() != null) {
                val currentPage = result.body()!!.data.page
                val currentLimit = result.body()!!.data.limit
                eventPageFetch = currentPage + 1
                eventLimitFetch = currentLimit
                APIResult.Success(result.body()!!.data.items)
            } else {
                APIResult.Error(result.message())
            }
        } catch (e: Exception) {
            APIResult.Error(e.message.toString())
        }
    }

    private var eventSearchPageFetch: Int = 1
    private var eventSearchLimitFetch: Int = 10
    suspend fun searchEvent(
        search: String,
        firstCall: Boolean = false
    ): APIResult<List<EventDetail>> {
        return try {
            if (firstCall) {
                eventPageFetch = 1
            }
            val result = eventEndpoint.searchEvent(
                q = search,
                page = eventSearchPageFetch,
                limit = eventSearchLimitFetch
            )
            if (result.isSuccessful && result.body() != null) {
                val currentPage = result.body()!!.data.page
                val currentLimit = result.body()!!.data.limit
                eventPageFetch = currentPage + 1
                eventLimitFetch = currentLimit
                APIResult.Success(result.body()!!.data.items)
            } else {
                APIResult.Error(result.message())
            }
        } catch (e: Exception) {
            APIResult.Error(e.message.toString())
        }
    }


    suspend fun getRegisteredEvent(
    ): APIResult<List<EventDetail>> {
        return try {
            val result = eventEndpoint.getAllRegisteredEvents()
            if (result.isSuccessful && result.body() != null) {
                APIResult.Success(result.body()!!.data)
            } else {
                APIResult.Error(result.message())
            }
        } catch (e: Exception) {
            APIResult.Error(e.message.toString())
        }
    }

    suspend fun getEventDetail(
        eventID: String
    ): APIResult<EventDetail> {
        return try {
            val result = eventEndpoint.getEventDetail(eventID)
            if (result.isSuccessful && result.body() != null) {
                APIResult.Success(result.body()!!.data)
            } else {
                APIResult.Error(result.message())
            }
        } catch (e: Exception) {
            APIResult.Error(e.message.toString())
        }
    }

    suspend fun registerEvent(
        eventID: String
    ): APIResult<FormData> {
        return try {
            val result = eventEndpoint.registerEvent(eventID)
            if (result.isSuccessful && result.body() != null) {
                APIResult.Success(result.body()!!.data)
            } else {
                APIResult.Error(result.message())
            }
        } catch (e: Exception) {
            Log.d("API", e.message.toString())
            APIResult.Error(e.message.toString())
        }
    }


    suspend fun submitResponse(
        eventID: String,
        response: Map<String, String>
    ): APIResult<String> {
        val response = RegistrationResponseSubmit(
            eventId = eventID,
            responses = response.map {
                Responses(
                    formFieldsId = it.key,
                    response = it.value
                )
            }
        )
        return try {
            val result = eventEndpoint.submitResponse(response)
            if (result.isSuccessful && result.body() != null) {
                APIResult.Success("SUCCESS")
            } else {
                APIResult.Error(result.message())
            }
        } catch (e: Exception) {
            APIResult.Error(e.message.toString())
        }
    }


    suspend fun getRegistrationForm(
        eventID: String
    ): APIResult<RegisteredIDResponse> {
        return try {
            val result = eventEndpoint.getRegistrationForm(eventID)
            if (result.isSuccessful && result.body() != null) {
                APIResult.Success(result.body()!!.data)
            } else {
                APIResult.Error(result.message())
            }
        } catch (e: Exception) {
            APIResult.Error(e.message.toString())
        }
    }

}
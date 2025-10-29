package com.turing.conferdent_conferentsmanagement.core.data

import android.content.Context
import android.content.SyncContext
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import okhttp3.Dispatcher
import javax.inject.Inject


val Context.appDataStore: DataStore<Preferences> by preferencesDataStore("conferdent")


interface IPersistentStorage {
    fun saveKey(key: Preferences.Key<String>, value: String)
    suspend fun saveKeySuspend(key: Preferences.Key<String>, value: String?)
    fun readKey(key: Preferences.Key<String>): Flow<String?>
    suspend fun readKeySuspend(key: Preferences.Key<String>): String?

}

class PersistentStorage @Inject constructor(
    @ApplicationContext mContext: Context
) : IPersistentStorage {
    private val dataStore = mContext.appDataStore
    private val corScope =
        CoroutineScope(Dispatchers.IO) + SupervisorJob() + CoroutineExceptionHandler { t, e ->
            Log.e("PersistentStorage", "Exception in corScope", e)
        }

    override fun saveKey(
        key: Preferences.Key<String>,
        value: String
    ) {
        corScope.launch {
            dataStore.edit {
                it[key] = value
            }
        }
    }

    override suspend fun saveKeySuspend(
        key: Preferences.Key<String>,
        value: String?
    ) {
        if(value != null){
            dataStore.edit {
                it[key] = value
            }
        }else{
            dataStore.edit {
                it.remove(key)
            }
        }
    }

    override fun readKey(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map { it[key] }
    }

    override suspend fun readKeySuspend(key: Preferences.Key<String>): String? {
        return dataStore.data.first()[key]
    }
}
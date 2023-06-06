package com.nicolascastilla.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.nicolascastilla.domain.repositories.entities.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private val context: Context,
) {

    private object Keys {
        val USER_DATA = stringPreferencesKey("user_data")
    }
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userData")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    }

    val user: Flow<UserData?> = context.dataStore.data
        .map { preferences ->
            val userJson = preferences[Keys.USER_DATA]
            userJson?.let {
                Gson().fromJson(it, UserData::class.java)
            }
        }

    suspend fun setUser(user: UserData) {
        context.dataStore.edit { preferences ->
            val userJson = Gson().toJson(user)
            preferences[Keys.USER_DATA] = userJson
        }
    }
}
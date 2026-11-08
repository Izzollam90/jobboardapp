package com.jobboard.app.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore("app_settings")

data class AppSettings(
    val companyName: String = "Christian Contracting",
    val appTitle: String = "Job Board",
    val accentHex: String = "#C49A50"
)

data class AccentOption(val label: String, val hex: String)

val ACCENT_OPTIONS = listOf(
    AccentOption("Gold", "#C49A50"),
    AccentOption("Amber", "#E6A23C"),
    AccentOption("Blue", "#5B8DEF"),
    AccentOption("Green", "#4CAF7D"),
    AccentOption("Red", "#E05B5B"),
    AccentOption("Violet", "#9B7EDE")
)

class SettingsRepository(context: Context) {

    private val dataStore = context.settingsDataStore
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val companyKey = stringPreferencesKey("company_name")
    private val titleKey = stringPreferencesKey("app_title")
    private val accentKey = stringPreferencesKey("accent_hex")

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    init {
        scope.launch {
            val prefs = dataStore.data.first()
            _settings.value = AppSettings(
                companyName = prefs[companyKey] ?: AppSettings().companyName,
                appTitle = prefs[titleKey] ?: AppSettings().appTitle,
                accentHex = prefs[accentKey] ?: AppSettings().accentHex
            )
        }
    }

    fun update(companyName: String = _settings.value.companyName,
               appTitle: String = _settings.value.appTitle,
               accentHex: String = _settings.value.accentHex) {
        _settings.value = AppSettings(companyName, appTitle, accentHex)
        scope.launch {
            dataStore.edit { prefs ->
                prefs[companyKey] = companyName
                prefs[titleKey] = appTitle
                prefs[accentKey] = accentHex
            }
        }
    }

    fun reset() {
        val d = AppSettings()
        update(d.companyName, d.appTitle, d.accentHex)
    }
}

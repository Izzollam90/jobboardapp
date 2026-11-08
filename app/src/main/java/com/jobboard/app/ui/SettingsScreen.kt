package com.jobboard.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jobboard.app.settings.ACCENT_OPTIONS
import com.jobboard.app.settings.AppSettings
import com.jobboard.app.theme.parseHexColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    settings: AppSettings,
    onSave: (companyName: String, appTitle: String, accentHex: String) -> Unit,
    onReset: () -> Unit
) {
    var companyName by remember(settings) { mutableStateOf(settings.companyName) }
    var appTitle by remember(settings) { mutableStateOf(settings.appTitle) }
    var accentHex by remember(settings) { mutableStateOf(settings.accentHex) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Eyebrow("Settings")
        Spacer(Modifier.height(4.dp))
        Text(
            "Customize the app",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Branding and accent color apply across the whole app instantly.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = appTitle,
            onValueChange = { appTitle = it },
            label = { Text("App title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("Company name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        Text(
            "Accent color",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ACCENT_OPTIONS.forEach { option ->
                val selected = accentHex.equals(option.hex, ignoreCase = true)
                Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(parseHexColor(option.hex))
                            .then(
                                if (selected) Modifier.border(
                                    3.dp,
                                    MaterialTheme.colorScheme.onSurface,
                                    CircleShape
                                ) else Modifier
                            )
                            .clickable { accentHex = option.hex }
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        option.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selected) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))
        androidx.compose.material3.Button(
            onClick = { onSave(companyName.trim(), appTitle.trim(), accentHex) },
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Save")
        }
        Spacer(Modifier.height(10.dp))
        OutlinedButton(onClick = onReset, modifier = Modifier.fillMaxWidth()) {
            Text("Reset to defaults")
        }
        Spacer(Modifier.height(48.dp))
    }
}

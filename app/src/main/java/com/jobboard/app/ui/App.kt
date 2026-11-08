package com.jobboard.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jobboard.app.data.JobRepository
import com.jobboard.app.settings.AppSettings
import com.jobboard.app.settings.SettingsRepository

private data class Tab(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val TABS = listOf(
    Tab("today", "Today", Icons.Filled.CalendarMonth),
    Tab("board", "Job board", Icons.Filled.Dashboard),
    Tab("mine", "My jobs", Icons.Filled.ListAlt),
    Tab("settings", "Settings", Icons.Filled.Settings)
)

@Composable
fun JobBoardApp(
    settingsRepo: SettingsRepository,
    jobRepo: JobRepository
) {
    val settings by settingsRepo.settings.collectAsStateWithLifecycle()
    val jobs by jobRepo.jobs.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar(settings) },
        bottomBar = { BottomNav(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "today",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("today") {
                TodayScreen(
                    myJobs = jobs.filter { it.claimed },
                    openCount = jobs.count { !it.claimed },
                    onOpen = { id -> navController.navigate("job/$id") }
                )
            }
            composable("board") {
                BoardScreen(
                    repo = jobRepo,
                    board = jobs.filter { !it.claimed }
                        .sortedWith(compareBy({ it.priority.order }, { it.preferredDate ?: "" })),
                    onOpen = { id -> navController.navigate("job/$id") },
                    onClaim = { id ->
                        jobRepo.claim(id)
                        navController.navigate("job/$id")
                    }
                )
            }
            composable("mine") {
                MyJobsScreen(
                    jobs = jobs.filter { it.claimed },
                    onOpen = { id -> navController.navigate("job/$id") }
                )
            }
            composable("settings") {
                SettingsScreen(
                    settings = settings,
                    onSave = { company, title, accent ->
                        settingsRepo.update(
                            companyName = company.ifBlank { settings.companyName },
                            appTitle = title.ifBlank { settings.appTitle },
                            accentHex = accent
                        )
                    },
                    onReset = { settingsRepo.reset() }
                )
            }
            composable("job/{jobId}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("jobId") ?: return@composable
                val job = jobs.find { it.id == id } ?: return@composable
                JobDetailScreen(
                    job = job,
                    onAdvance = { jobRepo.advance(it) },
                    onRelease = { jobRepo.release(it) }
                )
            }
        }
    }
}

@Composable
private fun AppTopBar(settings: AppSettings) {
    androidx.compose.material3.TopAppBar(
        title = {
            androidx.compose.foundation.layout.Column {
                Text(
                    settings.appTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    settings.companyName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.5.sp
                )
            }
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun BottomNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        TABS.forEach { tab ->
            val selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == tab.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) },
                alwaysShowLabel = true
            )
        }
    }
}

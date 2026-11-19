package io.github.mauriciogamedev.ffcompanion

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mauriciogamedev.ffcompanion.data.SensitivityProfile
import io.github.mauriciogamedev.ffcompanion.data.SensitivityProfileStore
import io.github.mauriciogamedev.ffcompanion.ui.FFCompanionTheme
import io.github.mauriciogamedev.ffcompanion.ui.FFPalette
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FFCompanionTheme {
                FFCompanionApp()
            }
        }
    }
}

private data class NavDestination(val title: String, val icon: ImageVector)

@Composable
private fun FFCompanionApp() {
    val context = LocalContext.current
    val store = remember { SensitivityProfileStore(context.applicationContext) }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedProfileName by rememberSaveable { mutableStateOf("Balanced") }
    var profile by remember { mutableStateOf(store.load("Balanced")) }

    val destinations = listOf(
        NavDestination("HOME", Icons.Filled.Home),
        NavDestination("SENSI", Icons.Filled.Tune),
        NavDestination("BOOST", Icons.Filled.Speed),
        NavDestination("INFO", Icons.Filled.Info)
    )

    Scaffold(
        containerColor = FFPalette.Background,
        bottomBar = {
            NavigationBar(containerColor = FFPalette.Surface, tonalElevation = 0.dp) {
                destinations.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.title
                            )
                        },
                        label = {
                            Text(
                                text = destination.title,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = FFPalette.Accent,
                            indicatorColor = FFPalette.AccentSoft,
                            unselectedIconColor = FFPalette.TextSecondary,
                            unselectedTextColor = FFPalette.TextSecondary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FFPalette.Background)
                .padding(innerPadding)
        ) {
            Header()
            HorizontalDivider(color = FFPalette.Border.copy(alpha = 0.55f))
            when (selectedTab) {
                0 -> HomeScreen(
                    onLaunchGame = { launchFreeFire(context) },
                    onNavigate = { selectedTab = it }
                )
                1 -> SensitivityScreen(
                    selectedProfileName = selectedProfileName,
                    profile = profile,
                    onSelectProfile = { name ->
                        selectedProfileName = name
                        profile = store.load(name)
                    },
                    onProfileChange = { profile = it },
                    onSave = {
                        store.save(profile)
                        Toast.makeText(
                            context,
                            "Profile saved on this device",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                2 -> PerformanceScreen(context)
                else -> InfoScreen()
            }
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "FF COMPANION",
                color = FFPalette.TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "PLAYER CONFIGURATION CONSOLE",
                color = FFPalette.TextSecondary,
                fontSize = 9.sp,
                letterSpacing = 1.1.sp
            )
        }
        Surface(
            color = FFPalette.AccentSoft.copy(alpha = 0.55f),
            shape = CircleShape,
            border = BorderStroke(1.dp, FFPalette.Accent.copy(alpha = 0.6f))
        ) {
            Text(
                text = "ANDROID 16",
                modifier = Modifier.padding(horizontal = 11.dp, vertical = 7.dp),
                color = Color(0xFFFFA4B1),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun HomeScreen(onLaunchGame: () -> Unit, onNavigate: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = FFPalette.Surface)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF3A0D19), Color(0xFF171218), FFPalette.Surface)
                        )
                    )
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "PLAYER TOOLKIT",
                    color = Color(0xFFFF9BA9),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Text(
                    "Your setup.\nYour control.",
                    color = Color.White,
                    fontSize = 28.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    "Sensitivity profiles and device settings in one place.",
                    color = FFPalette.TextSecondary,
                    fontSize = 13.sp
                )
                Button(
                    onClick = onLaunchGame,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = FFPalette.Accent),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("OPEN FREE FIRE", fontWeight = FontWeight.Bold)
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            InfoTile(
                title = "SENSITIVITY",
                value = "LOCAL PROFILES",
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(1) }
            )
            InfoTile(
                title = "PERFORMANCE",
                value = "DEVICE TIPS",
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(2) }
            )
        }

        SectionTitle("QUICK ACCESS", "Configure without modifying the game")
        ActionRow(
            title = "Sensitivity lab",
            description = "Tune and save your own values",
            action = "OPEN",
            onClick = { onNavigate(1) }
        )
        ActionRow(
            title = "Device performance",
            description = "Safe Android settings and manual checklist",
            action = "OPEN",
            onClick = { onNavigate(2) }
        )

        Surface(
            color = Color(0xFF201016),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, FFPalette.Accent.copy(alpha = 0.3f))
        ) {
            Text(
                text = "This is a companion app. It does not inject code, automate aiming, change game files, or bypass anti-cheat.",
                modifier = Modifier.padding(14.dp),
                color = Color(0xFFFFC2CA),
                fontSize = 11.sp,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun InfoTile(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = FFPalette.Surface)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, color = FFPalette.TextSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            Text(value, color = FFPalette.TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SensitivityScreen(
    selectedProfileName: String,
    profile: SensitivityProfile,
    onSelectProfile: (String) -> Unit,
    onProfileChange: (SensitivityProfile) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SectionTitle(
            "SENSITIVITY LAB",
            "Suggested values only — adjust in the game yourself"
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (SensitivityProfile.presetNames + "Custom").forEach { name ->
                FilterChip(
                    selected = selectedProfileName == name,
                    onClick = { onSelectProfile(name) },
                    label = { Text(name) }
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = FFPalette.Surface),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Text(
                    text = "$selectedProfileName PROFILE",
                    color = FFPalette.Accent,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                SensitivitySlider("General", profile.general) {
                    onProfileChange(profile.copy(general = it))
                }
                SensitivitySlider("Red Dot", profile.redDot) {
                    onProfileChange(profile.copy(redDot = it))
                }
                SensitivitySlider("2x Scope", profile.scope2x) {
                    onProfileChange(profile.copy(scope2x = it))
                }
                SensitivitySlider("4x Scope", profile.scope4x) {
                    onProfileChange(profile.copy(scope4x = it))
                }
                SensitivitySlider("Sniper / AWM", profile.sniper) {
                    onProfileChange(profile.copy(sniper = it))
                }
                SensitivitySlider("Free Look", profile.freeLook) {
                    onProfileChange(profile.copy(freeLook = it))
                }
                Button(
                    onClick = onSave,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = FFPalette.Accent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("SAVE PROFILE ON DEVICE", fontWeight = FontWeight.Bold)
                }
            }
        }
        Text(
            text = "Values are stored locally as notes. Android does not write them into Free Fire automatically.",
            color = FFPalette.TextSecondary,
            fontSize = 11.sp,
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun SensitivitySlider(label: String, value: Int, onValueChange: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                color = FFPalette.TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value.toString(),
                color = FFPalette.Accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.roundToInt()) },
            valueRange = SensitivityProfile.MIN_VALUE.toFloat()..SensitivityProfile.MAX_VALUE.toFloat()
        )
    }
}

@Composable
private fun PerformanceScreen(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SectionTitle("PERFORMANCE", "Safe, reversible device-side guidance")
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = FFPalette.Surface),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("DEVICE CHECKLIST", color = FFPalette.Accent, fontWeight = FontWeight.Black)
                ChecklistItem("Close apps you are not using before playing.")
                ChecklistItem("Keep at least 10–15% of storage free.")
                ChecklistItem("Avoid playing while the phone is overheating.")
                ChecklistItem("Use a stable network and pause large downloads.")
                ChecklistItem("Test the game's graphics and FPS options one at a time.")
                ChecklistItem("Disable screen recording if you do not need it.")
            }
        }
        SectionTitle(
            "ANDROID SHORTCUTS",
            "These open system settings; nothing is changed automatically"
        )
        ActionRow(
            title = "Display settings",
            description = "Review brightness and display options",
            action = "OPEN",
            onClick = { openSystemSettings(context, Settings.ACTION_DISPLAY_SETTINGS) }
        )
        ActionRow(
            title = "Battery settings",
            description = "Review battery saver and power behavior",
            action = "OPEN",
            onClick = { openSystemSettings(context, Settings.ACTION_BATTERY_SAVER_SETTINGS) }
        )
        ActionRow(
            title = "App information",
            description = "Review this companion app's Android settings",
            action = "OPEN",
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                safeStartActivity(context, intent)
            }
        )
        Text(
            text = "No RAM cleaner, task killer, root command, game-process hook, or background service is used. Android controls app scheduling and memory reclamation.",
            color = FFPalette.TextSecondary,
            fontSize = 11.sp,
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun ChecklistItem(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            modifier = Modifier.padding(top = 4.dp).size(7.dp),
            color = FFPalette.Accent,
            shape = CircleShape
        ) {}
        Text(text, color = FFPalette.TextPrimary, fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SectionTitle(
            "ABOUT & PRIVACY",
            "Transparent behavior, no hidden game modifications"
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = FFPalette.Surface),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("DEVICE", color = FFPalette.Accent, fontWeight = FontWeight.Black)
                DetailRow("Model", "\${Build.MANUFACTURER} \${Build.MODEL}")
                DetailRow("Android", "\${Build.VERSION.RELEASE} (API \${Build.VERSION.SDK_INT})")
                DetailRow("App version", "0.1.0")
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = FFPalette.Surface),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Text("PRIVACY & SAFETY", color = FFPalette.Accent, fontWeight = FontWeight.Black)
                ChecklistItem("Sensitivity profiles stay on this device.")
                ChecklistItem("No account login, passwords, or game credentials are collected.")
                ChecklistItem("No network permission is declared.")
                ChecklistItem("No overlay, accessibility, storage, or root access is requested.")
                ChecklistItem("The game is launched through its normal Android launcher activity.")
            }
        }
        Surface(
            color = FFPalette.SurfaceAlt,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, FFPalette.Border)
        ) {
            Text(
                text = "FF Companion is an independent utility and is not affiliated with or endorsed by Garena. It cannot guarantee account safety or prevent enforcement actions.",
                modifier = Modifier.padding(14.dp),
                color = FFPalette.TextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Text(
            label,
            modifier = Modifier.weight(0.8f),
            color = FFPalette.TextSecondary,
            fontSize = 12.sp
        )
        Text(
            value,
            modifier = Modifier.weight(1.2f),
            color = FFPalette.TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            text = title,
            color = FFPalette.TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.8.sp
        )
        Text(text = subtitle, color = FFPalette.TextSecondary, fontSize = 11.sp)
    }
}

@Composable
private fun ActionRow(
    title: String,
    description: String,
    action: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = FFPalette.Surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(title, color = FFPalette.TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(description, color = FFPalette.TextSecondary, fontSize = 10.sp)
            }
            Text(action, color = FFPalette.Accent, fontSize = 10.sp, fontWeight = FontWeight.Black)
        }
    }
}

private fun launchFreeFire(context: Context) {
    val packageNames = listOf("com.dts.freefireth", "com.dts.freefiremax")
    val launchIntent = packageNames.firstNotNullOfOrNull { packageName ->
        context.packageManager.getLaunchIntentForPackage(packageName)
    }
    if (launchIntent == null) {
        Toast.makeText(
            context,
            "Free Fire or Free Fire MAX was not found.",
            Toast.LENGTH_LONG
        ).show()
        return
    }
    safeStartActivity(context, launchIntent)
}

private fun openSystemSettings(context: Context, action: String) {
    safeStartActivity(context, Intent(action))
}

private fun safeStartActivity(context: Context, intent: Intent) {
    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "This Android settings page is unavailable.",
            Toast.LENGTH_SHORT
        ).show()
    } catch (_: SecurityException) {
        Toast.makeText(context, "Android blocked this action.", Toast.LENGTH_SHORT).show()
    }
}

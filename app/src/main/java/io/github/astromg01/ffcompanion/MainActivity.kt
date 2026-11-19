package io.github.astromg01.ffcompanion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF09090C)
private val CardBg = Color(0xFF17171D)
private val Red = Color(0xFFE5394F)
private val Muted = Color(0xFF9B9BA5)
private val controls = listOf(
    "Geral" to 95f, "Ponto vermelho" to 90f, "Mira 2x" to 85f,
    "Mira 4x" to 80f, "AWM" to 35f, "Olhadinha" to 75f
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(
                primary = Red, background = Bg, surface = CardBg,
                onPrimary = Color.White, onBackground = Color.White, onSurface = Color.White
            )) { Dashboard() }
        }
    }
}

@Composable
private fun Dashboard() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("sensitivity_profiles", Context.MODE_PRIVATE) }
    val values = remember {
        mutableStateMapOf<String, Float>().apply {
            controls.forEach { (name, initial) -> put(name, prefs.getFloat(name, initial)) }
        }
    }
    var profile by remember { mutableStateOf("Personalizado") }
    var status by remember { mutableStateOf("Perfis salvos somente neste aparelho") }

    fun save(message: String) {
        val editor = prefs.edit()
        controls.forEach { (name, _) -> editor.putFloat(name, values[name] ?: 50f) }
        editor.apply()
        status = message
    }
    fun preset(name: String, numbers: List<Float>) {
        controls.forEachIndexed { index, item -> values[item.first] = numbers[index] }
        profile = name
        save("Perfil $name salvo localmente")
    }

    Surface(Modifier.fillMaxSize(), color = Bg) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text("FF COMPANION", fontSize = 26.sp, fontWeight = FontWeight.Black, color = Color.White)
                Text("PAINEL DE CONFIGURAÇÃO", color = Red, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("ANDROID 8–16  •  PERFIS LOCAIS", color = Muted, fontSize = 11.sp)
            }

            Column(
                Modifier.fillMaxWidth().background(CardBg, RoundedCornerShape(18.dp)).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("PERFIL ATUAL", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(profile, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Ajustes de referência para configurar manualmente no jogo.", color = Muted, fontSize = 12.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PresetButton("Equilibrado") { preset("Equilibrado", listOf(95f, 90f, 85f, 80f, 35f, 75f)) }
                    PresetButton("Precisão") { preset("Precisão", listOf(88f, 82f, 78f, 72f, 28f, 65f)) }
                    PresetButton("Rápido") { preset("Rápido", listOf(100f, 98f, 92f, 88f, 48f, 90f)) }
                }
            }

            Column(
                Modifier.fillMaxWidth().background(CardBg, RoundedCornerShape(18.dp)).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("SENSIBILIDADE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                controls.forEach { (name, _) ->
                    val value = values[name] ?: 50f
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(name, color = Color.White, fontSize = 13.sp)
                            Text(value.toInt().toString(), color = Red, fontWeight = FontWeight.Bold)
                        }
                        Slider(value = value, onValueChange = {
                            values[name] = it
                            profile = "Personalizado"
                        }, valueRange = 0f..200f)
                    }
                }
                Button(
                    onClick = { save("Configuração salva neste aparelho") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) { Text("SALVAR CONFIGURAÇÃO", fontWeight = FontWeight.Bold) }
            }

            Column(
                Modifier.fillMaxWidth().background(CardBg, RoundedCornerShape(18.dp)).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("ATALHOS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Button(
                    onClick = {
                        val launchIntent = listOf("com.dts.freefireth", "com.dts.freefiremax")
                            .firstNotNullOfOrNull { context.packageManager.getLaunchIntentForPackage(it) }
                        if (launchIntent != null) context.startActivity(launchIntent)
                        else status = "Free Fire não encontrado neste aparelho."
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) { Text("ABRIR FREE FIRE") }
                Button(
                    onClick = { context.startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34343D))
                ) { Text("CONFIGURAÇÕES DE TELA") }
            }
            Text(status, color = Muted, fontSize = 11.sp)
            Text(
                "O app não altera arquivos ou memória do jogo. Os valores devem ser aplicados manualmente.",
                color = Muted, fontSize = 10.sp
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PresetButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34343D)),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
    ) { Text(label, fontSize = 10.sp, maxLines = 1) }
}

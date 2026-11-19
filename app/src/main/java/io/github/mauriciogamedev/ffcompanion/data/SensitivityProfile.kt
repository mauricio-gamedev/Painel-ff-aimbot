package io.github.mauriciogamedev.ffcompanion.data

data class SensitivityProfile(
    val name: String,
    val general: Int,
    val redDot: Int,
    val scope2x: Int,
    val scope4x: Int,
    val sniper: Int,
    val freeLook: Int
) {
    fun normalized(): SensitivityProfile = copy(
        general = general.coerceIn(MIN_VALUE, MAX_VALUE),
        redDot = redDot.coerceIn(MIN_VALUE, MAX_VALUE),
        scope2x = scope2x.coerceIn(MIN_VALUE, MAX_VALUE),
        scope4x = scope4x.coerceIn(MIN_VALUE, MAX_VALUE),
        sniper = sniper.coerceIn(MIN_VALUE, MAX_VALUE),
        freeLook = freeLook.coerceIn(MIN_VALUE, MAX_VALUE)
    )

    companion object {
        const val MIN_VALUE = 0
        const val MAX_VALUE = 200
        val presetNames = listOf("Balanced", "Rush", "Sniper")

        fun preset(name: String): SensitivityProfile = when (name) {
            "Rush" -> SensitivityProfile("Rush", 190, 180, 170, 150, 70, 150)
            "Sniper" -> SensitivityProfile("Sniper", 130, 120, 140, 160, 100, 90)
            "Custom" -> SensitivityProfile("Custom", 160, 150, 140, 130, 80, 120)
            else -> SensitivityProfile("Balanced", 160, 150, 140, 130, 80, 120)
        }
    }
}

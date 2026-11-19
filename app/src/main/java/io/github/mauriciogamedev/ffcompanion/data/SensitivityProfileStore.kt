package io.github.mauriciogamedev.ffcompanion.data

import android.content.Context
import java.util.Locale

class SensitivityProfileStore(context: Context) {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun load(name: String): SensitivityProfile {
        val defaults = SensitivityProfile.preset(name)
        val prefix = keyPrefix(name)
        return defaults.copy(
            general = preferences.getInt(prefix + "general", defaults.general),
            redDot = preferences.getInt(prefix + "red_dot", defaults.redDot),
            scope2x = preferences.getInt(prefix + "scope_2x", defaults.scope2x),
            scope4x = preferences.getInt(prefix + "scope_4x", defaults.scope4x),
            sniper = preferences.getInt(prefix + "sniper", defaults.sniper),
            freeLook = preferences.getInt(prefix + "free_look", defaults.freeLook)
        ).normalized()
    }

    fun save(profile: SensitivityProfile) {
        val safe = profile.normalized()
        val prefix = keyPrefix(safe.name)
        preferences.edit()
            .putInt(prefix + "general", safe.general)
            .putInt(prefix + "red_dot", safe.redDot)
            .putInt(prefix + "scope_2x", safe.scope2x)
            .putInt(prefix + "scope_4x", safe.scope4x)
            .putInt(prefix + "sniper", safe.sniper)
            .putInt(prefix + "free_look", safe.freeLook)
            .apply()
    }

    private fun keyPrefix(name: String): String =
        "profile_" + name.lowercase(Locale.ROOT).replace(' ', '_') + "_"

    private companion object {
        const val PREFERENCES_NAME = "ff_companion_profiles"
    }
}

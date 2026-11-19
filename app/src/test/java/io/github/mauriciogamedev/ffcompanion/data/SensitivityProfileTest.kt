package io.github.mauriciogamedev.ffcompanion.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SensitivityProfileTest {
    @Test
    fun presetsStayWithinSupportedRange() {
        SensitivityProfile.presetNames.forEach { name ->
            val profile = SensitivityProfile.preset(name)
            listOf(
                profile.general, profile.redDot, profile.scope2x,
                profile.scope4x, profile.sniper, profile.freeLook
            ).forEach { value ->
                assertTrue("$name contains an out-of-range value: $value", value in 0..200)
            }
        }
    }

    @Test
    fun normalizedClampsEverySliderValue() {
        val profile = SensitivityProfile(
            "Custom", -10, 250, 50, 201, 0, 200
        ).normalized()

        assertEquals(0, profile.general)
        assertEquals(200, profile.redDot)
        assertEquals(50, profile.scope2x)
        assertEquals(200, profile.scope4x)
        assertEquals(0, profile.sniper)
        assertEquals(200, profile.freeLook)
    }
}

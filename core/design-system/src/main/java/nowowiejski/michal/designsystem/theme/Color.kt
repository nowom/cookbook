package nowowiejski.michal.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

data class ColorShades(
    val light: Color,
    val primary: Color,
    val dark: Color,
)

@Immutable
object Color {
    val White = Color(0xFFFFFFFF)
    val ExtraLightGray = Color(0xFFEBEBF0)
    val LightGray = Color(0xFFCBCBD6)
    val Gray = Color(0xFF666666)
    val DarkGray = Color(0xFF333333)
    val ExtraDarkGray = Color(0xFF1C1C1F)
    val Black = Color(0xFF09090A)
    val Teal = Color(0xFF008080)
    val Violet = ColorShades(
        light = Color( 0xFFD8BFD8),
        primary = Color(0xFF6A5ACD),
        dark = Color(0xFF6A5ACD),
    )
    val Gold = Color(0xFFFFD700)
    val Blue = ColorShades(
        light = Color(0xFF64B5F6),
        primary = Color(0xFF2196F3),
        dark = Color(0xFF1976D2)
    )

    val Pistachio = ColorShades(
        light = Color(0xFFF0F4EF),
        primary = Color(0xFF93C572),
        dark = Color(0xFF93C572)
    )

    val Green = ColorShades(
        light = Color(0xFF81C784),
        primary = Color(0xFF4CAF50),
        dark = Color(0xFF388E3C)
    )

    val Red = ColorShades(
        light = Color(0xFFEF9A9A),
        primary = Color(0xFFF44336),
        dark = Color(0xFFD32F2F)
    )

    val Purple = ColorShades(
        light = Color(0xFFBA68C8),
        primary = Color(0xFF9C27B0),
        dark = Color(0xFF7B1FA2)
    )

    val Accent = ColorShades(
        light = Color(0xFFB39DDB),
        primary = Color(0xFF673AB7),
        dark = Color(0xFF512DA8)
    )
}
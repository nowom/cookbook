package nowowiejski.michal.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color as GraphicsColor

@Composable
fun CookbookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkAndroidColorScheme
        else -> LightAndroidColorScheme
    }
    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CookbookTypography,
        content = content
    )
}

val LightAndroidColorScheme = lightColorScheme(
    primary = Color.Pistachio.primary,
    onPrimary = Color.White,
    primaryContainer = Color.Pistachio.light,
    onPrimaryContainer = Color.DarkGray,
    secondary = Color.Gold,
    onSecondary = Color.White,
    secondaryContainer = Color.Teal,
    onSecondaryContainer = Color.White,
    tertiary = Color.Violet.primary,
    onTertiary = Color.White,
    tertiaryContainer = Color.Violet.light,
    onTertiaryContainer = Color.DarkGray,
    error = Color.Red.primary,
    onError = Color.White,
    errorContainer = Color.Red.primary,
    onErrorContainer = Color.White,
    background = Color.Pistachio.light,
    onBackground = Color.DarkGray,
    surface = Color.White,
    onSurface = Color.DarkGray,
    surfaceVariant = GraphicsColor(0xFFE0E0E0),
    onSurfaceVariant = GraphicsColor(0xFF666666),
    inverseSurface = Color.DarkGray,
    inverseOnSurface = Color.White,
    outline = Color.Gray
)

val DarkAndroidColorScheme = darkColorScheme(
    primary = Color.Pistachio.primary,
    onPrimary = Color.White,
    primaryContainer = Color.Pistachio.light,
    onPrimaryContainer = Color.DarkGray,
    secondary = Color.Gold,
    onSecondary = Color.White,
    secondaryContainer = Color.Teal,
    onSecondaryContainer = Color.White,
    tertiary = Color.Purple.primary,
    onTertiary = Color.White,
    tertiaryContainer = Color.Purple.primary,
    onTertiaryContainer = Color.White,
    error = Color.Red.primary,
    onError = Color.White,
    errorContainer = Color.Red.primary,
    onErrorContainer = Color.White,
    background = Color.Pistachio.light,
    onBackground = Color.DarkGray,
    surface = Color.White,
    onSurface = Color.DarkGray,
    surfaceVariant = Color.Gray,
    onSurfaceVariant = Color.White,
    inverseSurface = Color.Gray,
    inverseOnSurface = Color.White,
    outline = Color.Gray
)
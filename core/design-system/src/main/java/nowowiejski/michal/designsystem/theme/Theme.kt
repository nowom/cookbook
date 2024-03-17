package nowowiejski.michal.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun CookbookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

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
    primary = Color.Blue.primary,
    onPrimary = Color.White,
    primaryContainer = Color.White,
    onPrimaryContainer = Color.White,
    secondary = Color.Teal,
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
    background = Color.White,
    onBackground = Color.White,
    surface = Color.Gray,
    onSurface = Color.White,
    surfaceVariant = Color.Gray,
    onSurfaceVariant = Color.White,
    inverseSurface = Color.Gray,
    inverseOnSurface = Color.White,
    outline = Color.Gray
)

val DarkAndroidColorScheme = lightColorScheme(
    primary = Color.Blue.primary,
    onPrimary = Color.White,
    primaryContainer = Color.Green.primary,
    onPrimaryContainer = Color.White,
    secondary = Color.Teal,
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
    background = Color.Gray,
    onBackground = Color.White,
    surface = Color.Gray,
    onSurface = Color.White,
    surfaceVariant = Color.Gray,
    onSurfaceVariant = Color.White,
    inverseSurface = Color.Gray,
    inverseOnSurface = Color.White,
    outline = Color.Gray
)
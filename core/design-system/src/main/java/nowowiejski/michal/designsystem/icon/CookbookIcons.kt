package nowowiejski.michal.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import nowowiejski.michal.designsystem.R

data class IconRes(@DrawableRes val resId: Int)
object CookbookIcons {
    val Dashboard = Icons.Rounded.Home
    val Minus
        @Composable
        get() =
        Icon.MINUS.imageVector

}

enum class Icon(private val drawableResId: Int) {
    MINUS(R.drawable.icminus);
    val imageVector: ImageVector
        @Composable
        get() = ImageVector.vectorResource(drawableResId)
}
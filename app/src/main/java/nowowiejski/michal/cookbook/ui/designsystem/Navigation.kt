package nowowiejski.michal.cookbook.ui.designsystem

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy


@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
//    NavigationBar(
//        modifier = modifier,
//        contentColor = NiaNavigationDefaults.navigationContentColor(),
//        tonalElevation = 0.dp,
//        content = content,
//    )
}
 fun NavDestination?.isTopLevelDestinationInHierarchy(destination: BottomNavItem) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false
package nowowiejski.michal.cookbook.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import nowowiejski.michal.cookbook.ui.designsystem.BottomNavItem
import nowowiejski.michal.cookbook.ui.designsystem.Destinations
import nowowiejski.michal.cookbook.ui.designsystem.bottomNavItems
import nowowiejski.michal.cookbook.ui.designsystem.isTopLevelDestinationInHierarchy
import nowowiejski.michal.cookbook.ui.theme.CookbookTheme
import nowowiejski.michal.feature.newRecipeNavigationRoute
import nowowiejski.michal.feature.navigateToRecipeForm
import nowowiejski.michal.feature.recipeFormScreen
import nowowiejski.michal.home.homeScreen
import nowowiejski.michal.home.navigateToHome
import nowowiejski.michal.recipedetails.navigateToRecipeDetails
import nowowiejski.michal.recipedetails.recipeDetailsScreen
import nowowiejski.michal.shopping.navigateToShoppingList
import nowowiejski.michal.shopping.shoppingListScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookbookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        bottomBar = {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            MainBottomMenu(
                                destinations = bottomNavItems,
                                currentDestination = currentDestination,
                            ) { navItem ->
                                navOptions {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                                when (navItem.route) {
                                    Destinations.RECIPE_FORM_ROUTE -> navController.navigateToRecipeForm()
                                    Destinations.SHOPPING_LIST_ROUTE -> navController.navigateToShoppingList()
                                    Destinations.RECIPE_LIST_ROUTE -> navController.navigateToHome()
                                }
                            }
                        },
                    ) { padding ->
                        Row(
                            Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .consumeWindowInsets(padding)
                                .windowInsetsPadding(
                                    WindowInsets.safeDrawing.only(
                                        WindowInsetsSides.Horizontal,
                                    ),
                                ),
                        ) {
                            CookbookNavHost(navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CookbookNavHost(
        navController: NavHostController,
        modifier: Modifier = Modifier,
    ) {
        NavHost(
            navController = navController,
            startDestination = newRecipeNavigationRoute,
            modifier = modifier,
        ) {
            recipeFormScreen()
            shoppingListScreen()
            homeScreen(onRecipeClick = {
                navController.navigateToRecipeDetails()
            }, nestedGraphs = {
//                recipeDetailsScreen()
            })
            recipeDetailsScreen()
        }
    }

    @Composable
    fun MainBottomMenu(
        destinations: List<BottomNavItem>,
        currentDestination: NavDestination?,
        onNavigateToDestination: (BottomNavItem) -> Unit,
    ) {
        NavigationBar(
            tonalElevation = 3.dp,
            contentColor = Color.Red,
            containerColor = Color.White,
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
                        )
                    },
                    label = { Text(destination.name) },
                )
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CookbookTheme {
            Greeting("Android")
        }
    }
}
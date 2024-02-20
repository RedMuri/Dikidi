package com.example.dikidi.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dikidi.R
import com.example.dikidi.ui.navigation.NavigationItem
import com.example.dikidi.ui.navigation.SetupRootNavGraph
import com.example.dikidi.ui.screens.HomeScreen
import com.example.dikidi.ui.theme.OpenSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    Scaffold(
        bottomBar = {
        BottomBar(
            navController = navController, bottomBarState = bottomBarState
        )
    }) { paddingValues ->
        SetupRootNavGraph(
            navController = navController,
            homeScreenContent = {
                HomeScreen(paddingValues)
            },
            catalogScreenContent = {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.navigation_item_catalog))
                }
            },
            salesScreenContent = {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.navigation_item_sales))
                }
            },
            notesScreenContent = {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.navigation_item_notes))
                }
            },
            moreScreenContent = {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.navigation_item_more))
                }
            }
        )
    }
}

@Composable
private fun BottomBar(navController: NavHostController, bottomBarState: MutableState<Boolean>) {
    AnimatedVisibility(visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .systemBarsPadding()
                    .shadow(elevation = 8.dp)
                    .height(70.dp)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Catalog,
                    NavigationItem.Sales,
                    NavigationItem.Notes,
                    NavigationItem.More
                )
                items.forEach { item ->

                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(selected = selected, onClick = {
                        if (!selected) {
                            val route = item.screen.route
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }, icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(
                                id = item.iconResId
                            ), contentDescription = null
                        )
                    }, label = {
                        BoxWithConstraints {
                            Text(
                                modifier = Modifier
                                    .wrapContentWidth(unbounded = true)
                                    .requiredWidth(maxWidth + 24.dp), // 24.dp = the padding * 2
                                text = stringResource(item.titleResId),
                                fontFamily = OpenSans,
                                softWrap = false,
                                letterSpacing = (-0.5).sp,
                                fontSize = 12.sp,
                                color = if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSecondary,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }, colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.tertiary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                        indicatorColor = MaterialTheme.colorScheme.secondary
                    )
                    )
                }
            }
        })
}



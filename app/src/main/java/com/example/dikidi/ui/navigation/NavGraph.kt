package com.example.dikidi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupRootNavGraph(
    navController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    catalogScreenContent: @Composable () -> Unit,
    salesScreenContent: @Composable () -> Unit,
    notesScreenContent: @Composable () -> Unit,
    moreScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            homeScreenContent()
        }
        composable(route = Screen.Catalog.route) {
            catalogScreenContent()
        }
        composable(route = Screen.Sales.route) {
            salesScreenContent()
        }
        composable(route = Screen.Notes.route) {
            notesScreenContent()
        }
        composable(route = Screen.More.route) {
            moreScreenContent()
        }
    }
}
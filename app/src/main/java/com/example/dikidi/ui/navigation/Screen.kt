package com.example.dikidi.ui.navigation

sealed class Screen (val route: String) {
    data object Home: Screen(ROUTE_HOME)
    data object Catalog: Screen(ROUTE_CATALOG)
    data object Sales: Screen(ROUTE_SALES)
    data object Notes: Screen(ROUTE_NOTES)
    data object More: Screen(ROUTE_MORE)

    companion object {
        private const val ROUTE_HOME = "home_screen"
        private const val ROUTE_CATALOG = "catalog_screen"
        private const val ROUTE_SALES = "sales_screen"
        private const val ROUTE_NOTES = "notes_screen"
        private const val ROUTE_MORE = "more_screen"
    }
}
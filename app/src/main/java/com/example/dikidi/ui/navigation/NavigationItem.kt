package com.example.dikidi.ui.navigation

import com.example.dikidi.R

sealed class NavigationItem (
    val screen: Screen,
    val titleResId: Int,
    val iconResId: Int,
) {

    data object Home : NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_home,
        iconResId = R.drawable.ic_home,
    )

    data object Catalog : NavigationItem(
        screen = Screen.Catalog,
        titleResId = R.string.navigation_item_catalog,
        iconResId = R.drawable.ic_stack,
    )

    data object Sales : NavigationItem(
        screen = Screen.Sales,
        titleResId = R.string.navigation_item_sales,
        iconResId = R.drawable.ic_sale,
    )

    data object Notes : NavigationItem(
        screen = Screen.Notes,
        titleResId = R.string.navigation_item_notes,
        iconResId = R.drawable.ic_notes,
    )

    data object More : NavigationItem(
        screen = Screen.More,
        titleResId = R.string.navigation_item_more,
        iconResId = R.drawable.ic_list,
    )

}

package com.example.dikidi.ui.root

import com.example.dikidi.R

sealed class NavigationItem (
    val titleResId: Int,
    val iconResId: Int,
    val config: RootComponent.Config
) {

    data object Home : NavigationItem(
        titleResId = R.string.navigation_item_home,
        iconResId = R.drawable.ic_home,
        config = RootComponent.Config.Home
    )

    data object Catalog : NavigationItem(
        titleResId = R.string.navigation_item_catalog,
        iconResId = R.drawable.ic_stack,
        config = RootComponent.Config.Catalog
    )

    data object Sales : NavigationItem(
        titleResId = R.string.navigation_item_sales,
        iconResId = R.drawable.ic_sale,
        config = RootComponent.Config.Sales,
    )

    data object Notes : NavigationItem(
        titleResId = R.string.navigation_item_notes,
        iconResId = R.drawable.ic_notes,
        config = RootComponent.Config.Notes,
    )

    data object More : NavigationItem(
        titleResId = R.string.navigation_item_more,
        iconResId = R.drawable.ic_list,
        config = RootComponent.Config.More,
    )

}

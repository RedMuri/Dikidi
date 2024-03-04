package com.example.dikidi.ui.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.FaultyDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.Direction
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.isEnter
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.dikidi.R
import com.example.dikidi.ui.home.HomeContent
import com.example.dikidi.ui.theme.DikidiTheme
import com.example.dikidi.ui.theme.OpenSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContent(component: RootComponent) {
    DikidiTheme {
        Children(
            stack = component.stack,
            animation = stackAnimation(fade())
        ) {
            Scaffold(
                bottomBar = {
                    BottomBar(component)
                }
            ) { paddingValues ->
                when (val instance = it.instance) {
                    is RootComponent.Child.Home -> {
                        HomeContent(
                            component = instance.component,
                            paddingValues = paddingValues
                        )
                    }

                    RootComponent.Child.Catalog -> {
                        BoxPlaceholder(
                            paddingValues,
                            stringResource(id = R.string.navigation_item_catalog)
                        )
                    }

                    RootComponent.Child.More -> {
                        BoxPlaceholder(
                            paddingValues,
                            stringResource(id = R.string.navigation_item_more)
                        )
                    }

                    RootComponent.Child.Notes -> {
                        BoxPlaceholder(
                            paddingValues,
                            stringResource(id = R.string.navigation_item_notes)
                        )
                    }

                    RootComponent.Child.Sales -> {
                        BoxPlaceholder(
                            paddingValues,
                            stringResource(id = R.string.navigation_item_sales)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxPlaceholder(paddingValues: PaddingValues, text: String) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}

@Composable
private fun BottomBar(
    component: RootComponent,
) {
    val childStack by component.stack.subscribeAsState()
    val activeComponent = childStack.active.instance

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        modifier = Modifier
            .systemBarsPadding()
            .shadow(elevation = 8.dp)
            .height(70.dp)
    ) {
        NavBarItemWithLabel(
            R.drawable.ic_home,
            R.string.navigation_item_home,
            activeComponent is RootComponent.Child.Home,
            component::onHomeTabClicked
        )
        NavBarItemWithLabel(
            R.drawable.ic_stack,
            R.string.navigation_item_catalog,
            activeComponent is RootComponent.Child.Catalog,
            component::onCatalogTabClicked
        )
        NavBarItemWithLabel(
            R.drawable.ic_sale,
            R.string.navigation_item_sales,
            activeComponent is RootComponent.Child.Sales,
            component::onSalesTabClicked
        )
        NavBarItemWithLabel(
            R.drawable.ic_notes,
            R.string.navigation_item_notes,
            activeComponent is RootComponent.Child.Notes,
            component::onNotesTabClicked
        )
        NavBarItemWithLabel(
            R.drawable.ic_list,
            R.string.navigation_item_more,
            activeComponent is RootComponent.Child.More,
            component::onMoreTabClicked
        )
    }
}

@Composable
private fun RowScope.NavBarItemWithLabel(
    iconResId: Int,
    titleResId: Int,
    selected: Boolean,
    onClick: ()->Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(
                    id = iconResId
                ), contentDescription = null
            )
        },
        label = {
            BoxWithConstraints {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(unbounded = true)
                        .requiredWidth(maxWidth + 24.dp), // 24.dp = the padding * 2
                    text = stringResource(titleResId),
                    fontFamily = OpenSans,
                    softWrap = false,
                    letterSpacing = (-0.5).sp,
                    fontSize = 12.sp,
                    color = if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSecondary,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.tertiary,
            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
            indicatorColor = MaterialTheme.colorScheme.secondary
        )
    )
}





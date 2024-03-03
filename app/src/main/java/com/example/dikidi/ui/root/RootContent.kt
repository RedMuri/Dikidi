package com.example.dikidi.ui.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.value.Value
import com.example.dikidi.R
import com.example.dikidi.ui.home.HomeContent
import com.example.dikidi.ui.theme.DikidiTheme
import com.example.dikidi.ui.theme.OpenSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContent(component: RootComponent) {
    DikidiTheme {
        Children(
            stack = component.stack
        ) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        stack = component.stack,
                        navigation = component.navigation
                    )
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
    stack: Value<ChildStack<*, RootComponent.Child>>,
    navigation: StackNavigation<RootComponent.Config>,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        modifier = Modifier
            .systemBarsPadding()
            .shadow(elevation = 8.dp)
            .height(70.dp)
    ) {
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Catalog,
            NavigationItem.Sales,
            NavigationItem.Notes,
            NavigationItem.More
        )
        items.forEach { item ->

            val selected = stack.value.active.configuration == item.config

            NavigationBarItem(selected = selected, onClick = {
                if (!selected) {
                    navigation.bringToFront(item.config)
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
}



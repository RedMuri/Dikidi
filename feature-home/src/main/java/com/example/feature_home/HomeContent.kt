package com.example.feature_home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.domain.model.Catalog
import com.example.domain.model.Share
import com.example.domain.model.Vip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    component: HomeComponent,
) {
    val state by component.model.collectAsState()

    val scope = rememberCoroutineScope()
    val sharesState = rememberLazyListState()
    val popularState = rememberLazyListState()

    val sharesNestedScrollConnection = remember {
        nestedScrollConnectionObject(scope, sharesState)
    }
    val popularNestedScrollConnection = remember {
        nestedScrollConnectionObject(scope, popularState)
    }

    val mainScrollState: ScrollState = rememberScrollState(0)

    CollapsingToolbar(
        scrollState = mainScrollState, searchQuery = state.searchQuery, onQueryChange = {
            component.changeSearchQuery(it)
        }, locationName = stringResource(R.string.default_location_name)
    ) {
        when (val currentState = state.dataState) {
            is HomeStore.DataState.Content -> {
                ContentBlock(
                    mainScrollState,
                    paddingValues,
                    currentState,
                    sharesState,
                    sharesNestedScrollConnection,
                    popularState,
                    popularNestedScrollConnection
                )
            }

            is HomeStore.DataState.Error -> ErrorBlock {
                component.onClickRefresh()
            }

            HomeStore.DataState.Loading -> LoadingAnimation()
        }
    }
}

private fun nestedScrollConnectionObject(
    scope: CoroutineScope,
    lazyListState: LazyListState,
) = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        scope.launch {
            if (available.x <= 0) { // scrolling forward
                if (available.x >= -40 && lazyListState.firstVisibleItemIndex != lazyListState.layoutInfo.totalItemsCount - 1) lazyListState.animateScrollToItem(
                    lazyListState.firstVisibleItemIndex + 1
                )
            } else if (available.x <= 40) lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
        }
        return Offset.Zero
    }
}

@Composable
private fun ContentBlock(
    mainScrollState: ScrollState,
    paddingValues: PaddingValues,
    currentState: HomeStore.DataState.Content,
    sharesState: LazyListState,
    sharesNestedScrollConnection: NestedScrollConnection,
    popularState: LazyListState,
    popularNestedScrollConnection: NestedScrollConnection,
) {
    Column(
        modifier = Modifier
            .verticalScroll(mainScrollState)
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        Spacer(Modifier.height(headerHeight))
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
            val categories = currentState.data.data.blocks.catalog // for testing
            val vip = currentState.data.data.blocks.vip
            val shares = currentState.data.data.blocks.shares.list
            val popular = currentState.data.data.blocks.catalog // for testing
            val examples = currentState.data.data.blocks.examples
            val new = currentState.data.data.blocks.catalog // for testing

            Categories(categories)
            Vip(vip)
            Shares(sharesState, sharesNestedScrollConnection, shares)
            Popular(popularState, popularNestedScrollConnection, popular)
            Certificates()
            Examples(examples)
            New(new)
        }
    }
}

@Composable
private fun ErrorBlock(
    onButtonClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.fillMaxWidth(0.7f),
                painter = painterResource(id = R.drawable.img_error),
                contentDescription = stringResource(R.string.image_error),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.error),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.server_error),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = onButtonClick
            ) {
                Text(
                    text = stringResource(R.string.repeat),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun New(
    new: List<Catalog>,
) {
    BlockTitle(stringResource(R.string.new_items))

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(new) {
            Row(
                modifier = Modifier
                    .width(250.dp)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.img_error),
                            contentDescription = stringResource(R.string.image_error)
                        )
                    },
                    contentScale = ContentScale.Crop,
                    model = it.image.thumb,
                    contentDescription = stringResource(R.string.company_logo)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp)
                ) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = it.street,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun Examples(examples: String) {
    BlockTitle(stringResource(R.string.service_examples))

    AsyncImage(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        model = examples,
        contentDescription = stringResource(R.string.service_example),
        contentScale = ContentScale.FillWidth
    )

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        text = stringResource(R.string.watch),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
private fun Certificates() {
    BlockTitle(stringResource(R.string.sertificates))

    Image(
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.img_certificates),
        contentDescription = stringResource(R.string.certificates_image)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        text = stringResource(R.string.choose_sertificate),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
private fun Popular(
    popularState: LazyListState,
    popularNestedScrollConnection: NestedScrollConnection,
    popular: List<Catalog>,
) {
    BlockTitle(stringResource(R.string.popular))

    LazyRow(
        state = popularState,
        modifier = Modifier
            .fillMaxWidth()
            .nestedScroll(popularNestedScrollConnection),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(popular) {
            Row(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.img_error),
                            contentDescription = stringResource(id = R.string.image_error)
                        )
                    },
                    contentScale = ContentScale.Crop,
                    model = it.image.thumb,
                    contentDescription = stringResource(id = R.string.company_logo)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = stringResource(R.string.icon_star),
                            tint = Color.Yellow
                        )
                    }
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${it.street} ${it.house}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = it.rating.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun Shares(
    sharesState: LazyListState,
    sharesNestedScrollConnection: NestedScrollConnection,
    shares: List<Share>,
) {
    BlockTitle(stringResource(R.string.sales))

    LazyRow(
        state = sharesState,
        modifier = Modifier
            .fillMaxWidth()
            .nestedScroll(sharesNestedScrollConnection),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(shares) {
            Column(
                modifier = Modifier
                    .fillParentMaxWidth(0.99f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = MaterialTheme.colorScheme.secondary)
            ) {
                Box(
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = it.companyImage,
                        contentDescription = stringResource(R.string.service_image),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.secondary.copy(0.5f))
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            text = String.format("%s%%", it.discountValue),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    MaterialTheme.colorScheme.secondary.copy(
                                        0.7f
                                    )
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            text = buildAnnotatedString {
                                append(stringResource(R.string.before))
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(it.publicTimeStop)
                                }
                            },
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    MaterialTheme.colorScheme.secondary.copy(
                                        0.7f
                                    )
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.ic_eye),
                                contentDescription = stringResource(R.string.icon_eye),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = it.view,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 2,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop,
                            model = it.icon,
                            contentDescription = stringResource(R.string.service_logo)
                        )
                        Column {
                            Text(
                                text = it.companyName,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = it.companyStreet,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondary,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Vip(vip: List<Vip>) {
    BlockTitle(stringResource(R.string.premium))

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        vip.forEach {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                    model = it.image.thumb,
                    contentDescription = stringResource(id = R.string.company_logo)
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 14.dp),
                        text = it.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 2,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 14.dp),
                        text = it.categories.joinToString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    modifier = Modifier
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    text = stringResource(R.string.enroll),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun Categories(categories: List<Catalog>) {
    BlockTitle(stringResource(R.string.categories))
    LazyHorizontalGrid(
        modifier = Modifier.height(220.dp),
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) {
            Box(
                modifier = Modifier
                    .height(108.dp)
                    .width(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    model = it.image.origin,
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.img_error),
                            contentDescription = stringResource(id = R.string.image_error)
                        )
                    },
                    contentDescription = stringResource(R.string.category_image)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(0.3f))
                )
                Text(
                    modifier = Modifier.width(130.dp),
                    textAlign = TextAlign.Center,
                    text = it.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun BlockTitle(title: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun LoadingAnimation(
    iconColor: Color = MaterialTheme.colorScheme.tertiary,
    iconSize: Dp = 36.dp,
    animationDelay: Int = 400,
    initialAlpha: Float = 0.3f,
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        val icons = listOf(remember {
            Animatable(initialValue = initialAlpha)
        } to R.drawable.ic_home, remember {
            Animatable(initialValue = initialAlpha)
        } to R.drawable.ic_stack, remember {
            Animatable(initialValue = initialAlpha)
        } to R.drawable.ic_sale, remember {
            Animatable(initialValue = initialAlpha)
        } to R.drawable.ic_notes, remember {
            Animatable(initialValue = initialAlpha)
        } to R.drawable.ic_list)

        icons.forEachIndexed { index, item ->

            LaunchedEffect(Unit) {

                delay(timeMillis = (animationDelay / icons.size).toLong() * index)

                item.first.animateTo(
                    targetValue = 1f, animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = animationDelay
                        ), repeatMode = RepeatMode.Reverse
                    )
                )
            }
        }

        Row(
            modifier = Modifier
        ) {

            icons.forEachIndexed { index, item ->

                if (index != 0) {
                    Spacer(modifier = Modifier.width(width = 6.dp))
                }

                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = item.second),
                    contentDescription = stringResource(R.string.animatable_icon),
                    tint = iconColor.copy(alpha = item.first.value)
                )

            }
        }
    }
}
package com.example.dikidi.ui.screens

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.dikidi.MainActivity
import com.example.dikidi.R
import com.example.dikidi.domain.model.Catalog
import com.example.dikidi.domain.model.Share
import com.example.dikidi.domain.model.Vip
import com.example.dikidi.ui.intent.HomeIntent
import com.example.dikidi.ui.state.HomeState
import com.example.dikidi.ui.theme.DikidiTheme
import com.example.dikidi.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Preview
@Composable
private fun Preview() {
    DikidiTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {

    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(
        factory = (LocalContext.current as MainActivity).viewModelFactory
    )
    val state = viewModel.homeScreenState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.handleIntent(HomeIntent.LoadData)
    }

    val searchBarValue = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val sharesState = rememberLazyListState()
    val popularState = rememberLazyListState()

    val sharesNestedScrollConnection = remember {
        sharesNestedScrollConnectionObject(scope, sharesState)
    }
    val popularNestedScrollConnection = remember {
        nestedScrollConnectionObject(scope, popularState)
    }

    val mainScrollState: ScrollState = rememberScrollState(0)

    CollapsingToolbar(
        scrollState = mainScrollState,
        searchQuery = searchBarValue,
        locationName = "Ярославль"
    ) {
        when (val currentState = state.value) {
            is HomeState.Content -> {
               Column(Modifier.verticalScroll(mainScrollState)) {
                    val categories = currentState.data.data.blocks.catalog
                    val vip = currentState.data.data.blocks.vip
                    val shares = currentState.data.data.blocks.shares.list
                    val popular = currentState.data.data.blocks.catalog
                    val examples = currentState.data.data.blocks.examples
                    val new = currentState.data.data.blocks.catalog

                    Categories(categories)
                    Vip(vip)
                    Shares(sharesState, sharesNestedScrollConnection, shares)
                    Popular(popularState, popularNestedScrollConnection, popular)
                    Certificates()
                    Examples(examples)
                    New(new)
                }
            }

            is HomeState.Error -> {
                ErrorBlock{
                    viewModel.handleIntent(HomeIntent.LoadData)
                }
            }

            HomeState.Initial -> {}

            HomeState.Loading -> Toast.makeText(
                context,
                "${currentState.javaClass}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
private fun ErrorBlock(
    onButtonClick: ()->Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                modifier = Modifier.fillMaxWidth(0.7f),
                painter = painterResource(id = R.drawable.img_error),
                contentDescription = "Image error",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Ошибка",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Ошибка при соединении с сервером",
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
                onClick = onButtonClick ) {
                Text(
                    text = "Повторить",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

private fun nestedScrollConnectionObject(
    scope: CoroutineScope,
    popularState: LazyListState,
) = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        scope.launch {
            if (available.x <= 0) { // scrolling forward
                if (available.x >= -40 && popularState.firstVisibleItemIndex != popularState.layoutInfo.totalItemsCount - 1)
                    popularState.animateScrollToItem(popularState.firstVisibleItemIndex + 1)
            } else
                if (available.x <= 40)
                    popularState.animateScrollToItem(popularState.firstVisibleItemIndex)
        }
        return Offset.Zero
    }
}

private fun sharesNestedScrollConnectionObject(
    scope: CoroutineScope,
    sharesState: LazyListState,
) = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        scope.launch {
            if (available.x <= 0) { // scrolling forward
                if (available.x >= -40 && sharesState.firstVisibleItemIndex != sharesState.layoutInfo.totalItemsCount - 1)
                    sharesState.animateScrollToItem(sharesState.firstVisibleItemIndex + 1)
            } else
                if (available.x <= 40)
                    sharesState.animateScrollToItem(sharesState.firstVisibleItemIndex)
        }
        return Offset.Zero
    }
}


@Composable
private fun New(
    new: List<Catalog>,
) {
    BlockTitle("Новые")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(new) {
            Row(
                modifier = Modifier
                    .width(250.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.img_error),
                            contentDescription = "Some"
                        )
                    },
                    contentScale = ContentScale.Crop,
                    model = it.image.thumb,
                    contentDescription = "com.example.dikidi.data.model.Image of master"
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp)
                ) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 2,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = it.categories.joinToString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun Examples(examples: String) {
    BlockTitle("Примеры работ")

    AsyncImage(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        model = examples,
        contentDescription = "Example of work",
        contentScale = ContentScale.FillWidth
    )

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        text = "Посмотреть",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
private fun Certificates() {
    BlockTitle("Сертификаты")

    Image(
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.img_certificates),
        contentDescription = "com.example.dikidi.data.model.Image certificates"
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        text = "Выбрать сертификат",
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
    BlockTitle("Популярные")

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
                            contentDescription = "Some"
                        )
                    },
                    contentScale = ContentScale.Crop,
                    model = it.image.thumb,
                    contentDescription = "Image of master"
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
                            contentDescription = "Icon star",
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
    BlockTitle("Акции")

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
                        modifier = Modifier
                            .fillMaxSize(),
                        model = it.companyImage,
                        contentDescription = "Service image",
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
                                append("до: ")
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
                                contentDescription = "Icon eye",
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
                            contentDescription = "Service logo"
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
    BlockTitle("Премиум")

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
                    contentDescription = "Image of master"
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
                    text = "Записаться",
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
    BlockTitle("Категории")
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
                            contentDescription = "Some"
                        )
                    },
                    contentDescription = "Category item"
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

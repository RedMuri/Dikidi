package com.example.dikidi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dikidi.R
import com.example.dikidi.ui.theme.DikidiTheme

@Preview
@Composable
private fun Preview() {
    DikidiTheme {
        MainScreen()
    }
}

data class Category(
    val name: String,
    val imgResId: Int,
)

@Composable
fun MainScreen() {
    val categories = listOf(
        Category("some", R.drawable.img_home_head_bg),
        Category("another", R.drawable.ic_launcher_background),
        Category("first", R.drawable.ic_launcher_background),
        Category("secundant", R.drawable.img_home_head_bg),
        Category("you are", R.drawable.img_home_head_bg),
        Category("gorgeous", R.drawable.img_home_head_bg),
    )

    val searchBarValue = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .verticalScroll(rememberScrollState())
    ) {
        Header(searchBarValue)

        Categories(categories)


    }
}

@Composable
private fun Categories(categories: List<Category>) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = "Категории",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
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
            )
            {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = it.imgResId),
                    contentDescription = "Category item"
                )
                Text(
                    modifier = Modifier.width(130.dp),
                    textAlign = TextAlign.Center,
                    text = it.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun Header(searchBarValue: MutableState<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.img_home_head_bg),
            contentDescription = "Header background"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 25.dp)
        ) {
            Text(
                text = "Онлайн-запись",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Ярославль >",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchForm(query = searchBarValue)
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Icon search",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.SearchForm(
    query: MutableState<String>,
) {
    BasicTextField(
        value = query.value,
        onValueChange = { query.value = it },
        modifier = Modifier
            .weight(1f)
            .height(44.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge
    ) { innerTextField ->
        TextFieldDefaults.TextFieldDecorationBox(
            value = query.value,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            singleLine = true,
            enabled = true,
            shape = RoundedCornerShape(16.dp),
            interactionSource = MutableInteractionSource(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            placeholder = {
                Text(
                    text = "Услуга, специалист или место",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Icon search",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        )
    }
}
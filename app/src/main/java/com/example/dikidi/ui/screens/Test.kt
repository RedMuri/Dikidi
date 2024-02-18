package com.example.dikidi.ui.screens

/*
package com.example.dikidi.ui.screens

import android.annotation.SuppressLint
import android.gesture.Gesture
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.GestureCancellationException
import androidx.compose.foundation.gestures.PressGestureScope
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.isOutOfBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChangeConsumed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAll
import androidx.compose.ui.util.fastAny
import com.example.dikidi.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

private val headerHeight = 190.dp
private val toolbarHeight = 56.dp

suspend fun PointerInputScope.detectTapAndPressUnconsumed(
    onPress: suspend PressGestureScope.(Offset) -> Unit = NoPressGesture,
    onTap: ((Offset) -> Unit)? = null,
) {
    val pressScope = PressGestureScopeImpl(this)
    forEachGesture {
        coroutineScope {
            pressScope.reset()
            awaitPointerEventScope {

                val down = awaitFirstDown(requireUnconsumed = false).also { it.consumeDownChange() }

                if (onPress !== NoPressGesture) {
                    launch { pressScope.onPress(down.position) }
                }

                val up = waitForUpOrCancellationInitial()
                if (up == null) {
                    pressScope.cancel() // tap-up was canceled
                } else {
                    pressScope.release()
                    onTap?.invoke(up.position)
                }
            }
        }
    }
}

suspend fun AwaitPointerEventScope.waitForUpOrCancellationInitial(): PointerInputChange? {
    while (true) {
        val event = awaitPointerEvent(PointerEventPass.Initial)
        if (event.changes.fastAll { it.changedToUp() }) {
            // All pointers are up
            return event.changes[0]
        }

        if (event.changes.fastAny { it.consumed.downChange || it.isOutOfBounds(size) }) {
            return null // Canceled
        }

        // Check for cancel by position consumption. We can look on the Final pass of the
        // existing pointer event because it comes after the Main pass we checked above.
        val consumeCheck = awaitPointerEvent(PointerEventPass.Final)
        if (consumeCheck.changes.fastAny { it.positionChangeConsumed() }) {
            return null
        }
    }
}

@Composable
fun CollapsingToolbar(
    scrollState: ScrollState,
    searchQuery: MutableState<String>,
    modifier: Modifier = Modifier,
    locationName: String,
    content: @Composable () -> Unit,
) {
    val localDensity = LocalDensity.current

    val context = LocalContext.current
    val onClickTop = {
        Toast.makeText(context, "Top", Toast.LENGTH_SHORT).show()
    }
    val onClickBottom = {
        Toast.makeText(context, "Bottom", Toast.LENGTH_SHORT).show()
    }

    val headerHeightPx = with(localDensity) { headerHeight.toPx() }
    val toolbarHeightPx = with(localDensity) { toolbarHeight.toPx() }

    Box(modifier = modifier) {
        */
/*Header(
            query = searchQuery,
            scroll = scrollState,
            headerHeightPx = headerHeightPx,
            modifier = Modifier
                .fillMaxWidth(),
            locationName = locationName
        )*//*

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(60.dp)
                .background(Color.Red)
                .pointerInput(Unit) {
                    detectTapAndPressUnconsumed(onTap = {
                        onClickBottom()
                    })
                }
        )
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .pointerInput(Unit){
                    detectTapGestures()
                }
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
            )
            Column(modifier = modifier.background(MaterialTheme.colorScheme.primary)) {
                content()
            }
        }
        Toolbar(
            scroll = scrollState,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx
        )
    }
}

@Composable
private fun Header(
    query: MutableState<String>,
    scroll: ScrollState,
    headerHeightPx: Float,
    modifier: Modifier = Modifier,
    locationName: String,
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 3f // Parallax effect
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .matchParentSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.img_home_head_bg),
            contentDescription = "com.example.dikidi.ui.screens.Header background"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 24.dp)
        ) {
            Text(
                text = "Онлайн-запись",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "$locationName >",
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
                SearchForm(query = query)
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

@Composable
private fun Body(
    scroll: ScrollState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))
        Column(modifier = modifier.background(MaterialTheme.colorScheme.primary)) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    modifier: Modifier = Modifier,
) {
    val toolbarBottom by remember {
        mutableStateOf(headerHeightPx - toolbarHeightPx * ((headerHeightPx / toolbarHeightPx) / 2))
    }

    val showToolbar by remember {
        derivedStateOf {
            scroll.value >= toolbarBottom
        }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    text = "Главная"
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.SearchForm(
    query: MutableState<String>,
) {
    BasicTextField(
        value = query.value,
        onValueChange = { query.value = it },
        modifier = Modifier
            .weight(1f)
            .height(40.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge
    ) { innerTextField ->
        TextFieldDefaults.TextFieldDecorationBox(value = query.value,
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
                disabledIndicatorColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.secondary
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
            })
    }
}


private val NoPressGesture: suspend PressGestureScope.(Offset) -> Unit = { }

private class PressGestureScopeImpl(
    density: Density,
) : PressGestureScope, Density by density {
    private var isReleased = false
    private var isCanceled = false
    private val mutex = Mutex(locked = false)

    */
/**
     * Called when a gesture has been canceled.
     *//*

    fun cancel() {
        isCanceled = true
        mutex.unlock()
    }

    */
/**
     * Called when all pointers are up.
     *//*

    fun release() {
        isReleased = true
        mutex.unlock()
    }

    */
/**
     * Called when a new gesture has started.
     *//*

    fun reset() {
        mutex.tryLock() // If tryAwaitRelease wasn't called, this will be unlocked.
        isReleased = false
        isCanceled = false
    }

    override suspend fun awaitRelease() {
        if (!tryAwaitRelease()) {
            throw GestureCancellationException("The press gesture was canceled.")
        }
    }

    override suspend fun tryAwaitRelease(): Boolean {
        if (!isReleased && !isCanceled) {
            mutex.lock()
        }
        return isReleased
    }
}*/

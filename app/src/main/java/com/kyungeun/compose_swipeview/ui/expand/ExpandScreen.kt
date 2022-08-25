package com.kyungeun.compose_swipeview.ui.expand

import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kyungeun.compose_swipeview.*
import com.kyungeun.compose_swipeview.R
import com.kyungeun.compose_swipeview.data.Product
import com.kyungeun.compose_swipeview.ui.theme.BlueOnboarding
import com.kyungeun.compose_swipeview.ui.theme.ComposeSwipeViewTheme
import com.kyungeun.compose_swipeview.ui.theme.Gray
import com.kyungeun.compose_swipeview.util.PageIndicator
import com.kyungeun.compose_swipeview.util.Pager
import com.kyungeun.compose_swipeview.util.PagerState
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DecimalFormat

private val InfoContainerMaxHeight = 600.dp
private val InfoContainerMinHeight = 120.dp //title screen height

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandScreen(product: Product, onBackPressed: () -> Unit) {
    BoxWithConstraints {
        val infoSheetState = rememberSwipeableState(SheetState.Open)
        val infoMaxHeightInPixels = with(LocalDensity.current) { InfoContainerMaxHeight.toPx() }
        val infoMinHeightInPixels = with(LocalDensity.current) { InfoContainerMinHeight.toPx() }
        val dragRange = infoMaxHeightInPixels - infoMinHeightInPixels
        val scope = rememberCoroutineScope()
        val pagerState = remember { PagerState() }
        pagerState.maxPage = (product.imageList.size - 1).coerceAtLeast(0)
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .swipeable(
                    state = infoSheetState,
                    anchors = mapOf(
                        0f to SheetState.Closed,
                        -dragRange to SheetState.Open
                    ),
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Vertical
                )
        ) {
            val openFraction = if (infoSheetState.offset.value.isNaN()) {
                0f
            } else {
                -infoSheetState.offset.value / dragRange
            }.coerceIn(0f, 1f)
            val (image, containerInfo, topBar, imageIndicator) = createRefs()
            val offsetY = lerp(
                infoMaxHeightInPixels,
                0f,
                openFraction
            )

            Pager(
                state = pagerState,
                modifier = Modifier
                    .clickable(
                        enabled = infoSheetState.currentValue == SheetState.Open,
                        onClick = {
                            scope.launch {
                                infoSheetState.animateTo(SheetState.Closed)
                            }
                        }
                    )
                    .constrainAs(image) {
                        linkTo(
                            start = parent.start,
                            top = parent.top,
                            end = parent.end,
                            bottom = containerInfo.top,
                            bottomMargin = (-32).dp
                        )
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            ) {
                CoilImage(
                    imageModel = product.imageList[0],
                    // Crop, Fit, Inside, FillHeight, FillWidth, None
                    contentScale = ContentScale.Crop,
                    // shows a placeholder while loading the image.
                    //placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
                    // shows an error ImageBitmap when the request failed.
                    //error = ImageBitmap.imageResource(R.drawable.error)
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            TopBar(
                onBackPressed = onBackPressed,
                modifier = Modifier
                    .height(64.dp)
                    .constrainAs(topBar) {
                        linkTo(start = parent.start, end = parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }
            )

            PageIndicator(
                modifier = Modifier
                    .height(72.dp)
                    .constrainAs(imageIndicator) {
                        bottom.linkTo(image.bottom, margin = 8.dp)
                        linkTo(start = parent.start, end = parent.end)
                        width = Dimension.fillToConstraints
                    },
                count = product.imageList.size, currentPage = pagerState.currentPage
            )

            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = Modifier
                    .constrainAs(containerInfo) {
                        linkTo(start = parent.start, end = parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        val actualHeight = InfoContainerMaxHeight - offsetY.dp
                        height = Dimension.value(
                            actualHeight.coerceAtLeast(
                                InfoContainerMinHeight
                            )
                        )
                    }
            ) {
                InfoContainer(
                    product,
                    Modifier
                        .height(InfoContainerMinHeight)
                        .clickable(
                            enabled = infoSheetState.currentValue == SheetState.Closed,
                            onClick = {
                                scope.launch {
                                    infoSheetState.animateTo(SheetState.Open)
                                }
                            }
                        )
                )
            }
        }
    }
}

@Composable
fun PageIndicator(modifier: Modifier, count: Int, currentPage: Int) {
    Box(modifier, contentAlignment = Alignment.Center) {
        GradientScreen(modifier = Modifier.fillMaxSize(), 0f, 1f)
        PageIndicator(pagesCount = count, currentPageIndex = currentPage, color = Color.White)
    }
}

@Composable
fun TopBar(modifier: Modifier, onBackPressed: () -> Unit) {
    ConstraintLayout(modifier) {
        val (back, share) = createRefs()
        GradientScreen(modifier = Modifier.fillMaxSize(), 1f, 0f)

        IconButton(
            onClick = onBackPressed,
            Modifier.constrainAs(back) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = EMPTY,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun InfoContainer(product: Product, modifier: Modifier) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
    ) {
        val rowsModifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 20.dp, end = 24.dp, bottom = 20.dp)
        TitleRow(title = product.title, subTitle = product.subTitle, category = product.category, modifier)
        Text(
            text = product.info,
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            modifier = rowsModifier
        )
        val myFormatter = DecimalFormat("###,###,###")
        val price: String = myFormatter.format(product.price)
        listOf(
            R.string.label_price to price,
        ).forEach {
            InfoItemRow(modifier = rowsModifier, label = it.first, value = it.second)
        }
        BookButton()
    }
}

@Composable
fun TitleRow(title: String, subTitle: String, category: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp, bottom = 20.dp)
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = subTitle,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
            Button(
                onClick = {},
                modifier = Modifier
                    .wrapContentWidth()
                    .height(25.dp)
                    .padding(top = 4.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Gray),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        HeartAnimation()
    }
}

@Composable
fun HeartAnimation() {
    val interactionSource = MutableInteractionSource()
    val coroutineScope = rememberCoroutineScope()

    var enabled by remember {
        mutableStateOf(false)
    }
    val scale = remember {
        Animatable(1f)
    }

    Box(
        modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Like the product",
            tint = if (enabled) Color.Red else Color.LightGray,
            modifier = Modifier
                .scale(scale = scale.value)
                .padding(end = 24.dp)
                .size(size = 28.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    enabled = !enabled
                    coroutineScope.launch {
                        scale.animateTo(
                            0.8f,
                            animationSpec = tween(100),
                        )
                        scale.animateTo(
                            1f,
                            animationSpec = tween(100),
                        )
                    }
                }
        )
    }
}

@Composable
fun BookButton() {
    Button(
        onClick = {
        },
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
            .height(50.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = BlueOnboarding),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = stringResource(R.string.label_book),
            style = MaterialTheme.typography.button,
            color = Color.White
        )
    }
}

@Composable
fun InfoItemRow(@StringRes label: Int, value: String, modifier: Modifier) {
    Row(modifier) {
        Text(
            text = stringResource(label),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.width(120.dp),
            color = MaterialTheme.colors.onPrimary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W500)
        )
    }
}

@Composable
fun GradientScreen(modifier: Modifier, startYPercentage: Float, endYPercentage: Float) {
    Spacer(
        modifier = modifier.verticalGradientScrim(
            color = Color.Black.copy(alpha = 0.3f),
            startYPercentage = startYPercentage,
            endYPercentage = endYPercentage
        )
    )
}

enum class SheetState { Open, Closed }

@Preview("Expand screen body")
@Composable
fun PreviewExpandScreen() {
    ComposeSwipeViewTheme {
        val expandSample = ProductDataManager.getProduct()
        ExpandScreen(expandSample) {}
    }
}

@Preview("Expand screen body dark")
@Composable
fun PreviewExpandScreenDark() {
    ComposeSwipeViewTheme(darkTheme = true) {
        val puppySample = ProductDataManager.getProduct()
        ExpandScreen(puppySample) {}
    }
}

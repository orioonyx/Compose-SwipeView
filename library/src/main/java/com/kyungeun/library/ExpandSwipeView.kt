package com.kyungeun.library

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kyungeun.library.util.LerpValue
import com.kyungeun.library.util.verticalGradientScrim
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch

public enum class SheetState { Open, Closed }

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
public fun ExpandSwipeView(
    imageList: ArrayList<String>,
    imageScale: ContentScale,
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState(),
    infoSheetState: SwipeableState<Any> = rememberSwipeableState(SheetState.Open),
    infoContainerMaxHeight: Dp,
    infoContainerMinHeight: Dp,
    contentBackgroundColor: Color,
    contents: @Composable () -> Unit
) {
    BoxWithConstraints {
        val infoMaxHeightInPixels = with(LocalDensity.current) { infoContainerMaxHeight.toPx() }
        val infoMinHeightInPixels = with(LocalDensity.current) { infoContainerMinHeight.toPx() }
        val dragRange = infoMaxHeightInPixels - infoMinHeightInPixels
        val scope = rememberCoroutineScope()

        BackHandler(enabled = infoSheetState.currentValue == SheetState.Open) {
            scope.launch {
                infoSheetState.animateTo(SheetState.Closed)
            }
        }

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
            val offsetY = LerpValue(
                infoMaxHeightInPixels,
                0f,
                openFraction
            )
            HorizontalPager(
                count = imageList.size,
                contentPadding = PaddingValues(horizontal = 0.dp),
                state = state,
                modifier = modifier
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
            ) { page ->
                CoilImage(
                    imageModel = imageList[page],
                    // Crop, Fit, Inside, FillHeight, FillWidth, None
                    contentScale = imageScale,
                    // shows a placeholder while loading the image.
                    //placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
                    // shows an error ImageBitmap when the request failed.
                    //error = ImageBitmap.imageResource(R.drawable.error)
                    contentDescription = null,
                    modifier = modifier.fillMaxSize()
                )
            }

            PageIndicator(
                modifier = modifier
                    .height(72.dp)
                    .constrainAs(imageIndicator) {
                        bottom.linkTo(image.bottom, margin = 8.dp)
                        linkTo(start = parent.start, end = parent.end)
                        width = Dimension.fillToConstraints
                    },
                count = imageList.size, currentPage = state.currentPage
            )

            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = modifier
                    .constrainAs(containerInfo) {
                        linkTo(start = parent.start, end = parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        val actualHeight = infoContainerMaxHeight - offsetY.dp
                        height = Dimension.value(
                            actualHeight.coerceAtLeast(
                                infoContainerMinHeight
                            )
                        )
                    }
            ) {
                InfoContainer(
                    modifier = modifier
                        .height(infoContainerMinHeight)
                        .clickable(
                            enabled = infoSheetState.currentValue == SheetState.Closed,
                            onClick = {
                                scope.launch {
                                    infoSheetState.animateTo(SheetState.Open)
                                }
                            }
                        ),
                    color = contentBackgroundColor,
                    contents = contents
                )
            }
        }
    }
}

@Composable
public fun PageIndicator(modifier: Modifier, count: Int, currentPage: Int) {
    Box(modifier, contentAlignment = Alignment.Center) {
        GradientScreen(modifier = Modifier.fillMaxSize(), 0f, 1f)
        com.kyungeun.library.util.PageIndicator(
            pagesCount = count,
            currentPageIndex = currentPage,
            color = Color.White
        )
    }
}

@Composable
public fun InfoContainer(
    modifier: Modifier,
    color: Color,
    contents : @Composable ()-> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(color = color)
    ) {
        contents()
    }
}

@Composable
public fun GradientScreen(modifier: Modifier, startYPercentage: Float, endYPercentage: Float) {
    Spacer(
        modifier = modifier.verticalGradientScrim(
            color = Color.Black.copy(alpha = 0.3f),
            startYPercentage = startYPercentage,
            endYPercentage = endYPercentage
        )
    )
}

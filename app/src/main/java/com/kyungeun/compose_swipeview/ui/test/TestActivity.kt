package com.kyungeun.compose_swipeview.ui.test

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyungeun.compose_swipeview.ui.theme.ComposeSwipeViewTheme
import com.kyungeun.compose_swipeview.util.EMPTY
import com.kyungeun.library.ExpandSwipeView
import com.kyungeun.library.SheetState

/**
 * 'Compose-SwipeView' Library Test
 * implementation 'com.github.shruddms:Compose-SwipeView:Tag'
 */
class TestActivity : ComponentActivity() {

    private lateinit var imageList: ArrayList<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageList = ArrayList<Any>()
        imageList.add("https://images.unsplash.com/photo-1553356084-58ef4a67b2a7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
        imageList.add("https://images.unsplash.com/photo-1604077350837-c7f82f28653f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
        imageList.add("https://images.unsplash.com/photo-1579547944212-c4f4961a8dd8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=339&q=80")

        setContent {
            TestApp()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun TestApp() {
        ComposeSwipeViewTheme {
            ExpandSwipeView(
                imageList = imageList,
                imageScale = ContentScale.Crop,
                modifier = Modifier,
                infoSheetState = rememberSwipeableState(SheetState.Open),
                infoContainerMaxHeight = 600.dp,
                infoContainerMinHeight = 120.dp,
                contentBackgroundColor = MaterialTheme.colors.primary,
                contents = { content() }
            )

            val activity = LocalContext.current as Activity
            TopAppBar(
                modifier = Modifier,
                title = { EMPTY },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = {
                        activity.onBackPressed()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = EMPTY,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            )
        }
    }

    @Composable
    fun content() {
        Text(
            text = "Hello Word!",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(20.dp)
        )
    }
}

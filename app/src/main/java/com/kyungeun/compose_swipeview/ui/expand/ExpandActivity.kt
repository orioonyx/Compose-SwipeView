package com.kyungeun.compose_swipeview.ui.expand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kyungeun.compose_swipeview.data.ProductDataManager
import com.kyungeun.compose_swipeview.ui.theme.ComposeSwipeViewTheme

class ExpandActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExpandApp()
        }
    }

    @Composable
    fun ExpandApp() {
        ComposeSwipeViewTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val productSample = ProductDataManager.getProduct()
                val navController = rememberNavController()
                ExpandScreen(
                    product = productSample,
                    onBackPressed = { navController.navigateUp() }
                )
            }
        }
    }
}


package com.kyungeun.compose_swipeview.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kyungeun.compose_swipeview.R
import com.kyungeun.compose_swipeview.ui.expand.ExpandActivity
import com.kyungeun.compose_swipeview.ui.test.TestActivity
import com.kyungeun.compose_swipeview.ui.theme.ComposeSwipeViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSwipeViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    Column(
                        Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.padding(20.dp))
                        Button(
                            onClick = { context.startActivity(Intent(context, ExpandActivity::class.java))},
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
                            Text(getString(R.string.swipe_expand)).toString()
                        }
                        Spacer(modifier = Modifier.padding(20.dp))
                        Button(
                            onClick = { context.startActivity(Intent(context, TestActivity::class.java))},
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
                            Text(getString(R.string.library_test)).toString()
                        }
                    }
                }
            }
        }
    }
}

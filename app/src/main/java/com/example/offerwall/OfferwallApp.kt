package com.example.offerwall

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.offerwall.ui.home.HomeScreen

/**
 * Screen level composable
 */
@Composable
fun OfferwallApp(
    modifier: Modifier = Modifier
) {
    HomeScreen(modifier = modifier)
}


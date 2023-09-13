package com.example.offerwall.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.offerwall.R

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Red),
            modifier = Modifier.size(dimensionResource(R.dimen.image_size))
        )
        Text(
            text = stringResource(R.string.error_ids),
            style = MaterialTheme.typography.titleLarge
        )
    }
}
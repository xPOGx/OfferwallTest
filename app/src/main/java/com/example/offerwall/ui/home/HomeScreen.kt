package com.example.offerwall.ui.home

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.offerwall.R
import com.example.offerwall.network.Entity
import com.example.offerwall.ui.AppViewModelProvider
import com.example.offerwall.ui.screens.ErrorScreen
import com.example.offerwall.ui.screens.LoadingScreen

/**
 * Entry point for Offerwall App
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.homeUiState.collectAsState()

    HomeBody(
        entity = uiState.entity,
        status = uiState.status,
        action = viewModel::getNextEntity,
        modifier = modifier
    )
}

@Composable
fun HomeBody(
    entity: Entity,
    status: Status,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ContentBody(
            entity = entity,
            status = status
        )
    }
    NavigationButton(
        action = action,
        status = status
    )
}

@Composable
fun NavigationButton(
    action: () -> Unit,
    status: Status
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = action,
                shape = RoundedCornerShape(dimensionResource(R.dimen.small)),
                enabled = status != Status.Loading,
                modifier = Modifier
                    .weight(2f)
                    .padding(bottom = dimensionResource(R.dimen.large))
            ) {
                Text(
                    text = stringResource(R.string.next),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ContentBody(
    entity: Entity,
    status: Status,
    modifier: Modifier = Modifier
) {
    when (status) {
        Status.Loading -> LoadingScreen(modifier = modifier)
        Status.Done -> EntityBody(entity = entity, modifier = modifier)
        Status.Error -> ErrorScreen(modifier = modifier)
    }
}

@Composable
fun EntityBody(
    entity: Entity,
    modifier: Modifier = Modifier
) {

    when (entity.type) {
        "text" -> {
            Card(
                modifier = modifier
            ) {
                Text(
                    text = entity.message,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(dimensionResource(R.dimen.medium))
                )
            }
        }

        "webview" -> {
            WebViewContent(
                url = entity.url,
                modifier = modifier
            )
        }

        "image" -> {
            Card(
                modifier = modifier
            ) {
                ImageContent(
                    url = entity.url,
                    modifier = Modifier.size(dimensionResource(R.dimen.image_size))
                )
            }
        }

        "error" -> {
            Text(
                text = stringResource(R.string.error_entity),
                modifier = Modifier.padding(dimensionResource(R.dimen.medium))
            )
        }

        else -> {}
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContent(
    url: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                }
                loadUrl(url)
            }
        },
        modifier = modifier
    )
}

@Composable
fun ImageContent(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(url)
            .diskCacheKey(url)
            .placeholder(R.drawable.ic_image_search)
            .error(R.drawable.ic_broken_image)
            .build(),
        contentDescription = null,
        modifier = modifier
    )
}

package com.example.amphibians.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.Amphibian

@Composable
fun HomeScreen(
    amphibianUiState: AmphibianUiState,
    retryAction: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    when (amphibianUiState) {
        is AmphibianUiState.Success -> AmphibiansListScreen(
            amphibianUiState.amphibians,
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        )

        is AmphibianUiState.Loading -> LoadingScreen(
            modifier = modifier.fillMaxSize()
        )

        is AmphibianUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Image(
        painterResource(id = R.drawable.loading_img),
        contentDescription = "Loading Image",
        modifier = modifier.size(200.dp)
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.ic_connection_error),
            contentDescription = "Connection Error",
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun AmphibiansListScreen(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        items(amphibians) { amphibian ->
            AmphibianInfoCard(
                amphibian = amphibian,
                modifier = Modifier
                    .fillMaxWidth()
//                    .aspectRatio(dimensionResource(R.dimen.grid_item_aspect_ratio).value)
            )
        }
    }
}

@Composable
fun AmphibianInfoCard(
    amphibian: Amphibian,
    modifier: Modifier = Modifier
) {
    Column() {
        Text(
            text = "${amphibian.name} (${amphibian.type})",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        AmphibianPhotoCard(amphibian.imgSrc)
        Text(
            text = amphibian.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}


@Composable
fun AmphibianPhotoCard(
    amphibianImgSrc: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(amphibianImgSrc)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
package com.example.amphibians.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

// collecting all screens composables

/**val uiState by viewModel.marsUiState.collectAsState()

when (uiState) {
is MarsUiState.Loading -> LoadingScreen()
is MarsUiState.Success -> PhotosGridScreen(photos = (uiState as MarsUiState.Success).photos)
is MarsUiState.Error -> ErrorScreen((uiState as MarsUiState.Error).message)
}*/

@Composable
fun AmphibiansApp(modifier: Modifier = Modifier) {
    Scaffold(

    ){
        val amphibianViewModel: AmphibianViewModel = viewModel(factory = AmphibianViewModel.Factory)
        val amphibianUiState by amphibianViewModel.amphibianUiState.collectAsState()
        HomeScreen(
            amphibianUiState = amphibianUiState,
            retryAction = TODO(),
            contentPadding = it,
            modifier = TODO()
        )
    }
}


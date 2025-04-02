package com.refactorme.demo.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.refactorme.demo.ui.viewmodels.CountriesViewModel
import com.refactorme.demo.ui.entities.ItemCountry
import com.refactorme.demo.ui.theme.RefactorMeTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

data class CountriesScreenState(
    val isLoading: Boolean = false,
    val countries: List<ItemCountry> = emptyList(),
    val error: String? = null
)

sealed class CountriesIntent {
    data object LoadCountries : CountriesIntent()
}

@Composable
fun CountriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CountriesViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    Surface(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> ContentLoading()
            else -> CountriesContent(
                onLoadButtonClick = { viewModel.handleIntent(CountriesIntent.LoadCountries) },
                countries = uiState.countries,
                errorMessage = uiState.error
            )
        }
    }
}

@Composable
fun ContentLoading(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CountriesContent(
    onLoadButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    countries: List<ItemCountry> = emptyList(),
    errorMessage: String? = null
) {
    Box(modifier.fillMaxSize().systemBarsPadding().padding(horizontal = 12.dp)) {
        val listState = rememberLazyListState()
        CountriesList(countries = countries, listState = listState)
        CountriesLoadButton(onLoadButtonClick, Modifier.align(Alignment.BottomEnd), listState)
        LoadingErrorMessage(Modifier.align(Alignment.BottomCenter), errorMessage)
    }
}

@Composable
fun CountriesList(
    countries: List<ItemCountry>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        state = listState
    ) {
        items(countries) { countryItem ->
            Column(Modifier.fillParentMaxWidth()) {
                CountryItem(countryItem, Modifier.fillParentMaxWidth())
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun CountryItem(
    item: ItemCountry,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.capital,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(Modifier.weight(1F))
        Text(
            text = item.flag,
            fontSize = 35.sp
        )
    }
}

@Composable
fun CountriesLoadButton(
    onLoadButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
) {
    AnimatedVisibility(
        visible = listState.isScrollingUp(),
        modifier = modifier,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = 100.dp),
            onClick = { onLoadButtonClick() }
        ) {
            Text("Load")
        }
    }
}

@Composable
fun LoadingErrorMessage(
    modifier: Modifier = Modifier,
    errorMessage: String? = null
) {
    var shouldShow by remember { mutableStateOf(errorMessage != null) }
    if (shouldShow) {
        LaunchedEffect(Unit) {
            delay(3000L)
            shouldShow = false
        }
    }
    AnimatedVisibility(
        visible = shouldShow,
        modifier = modifier,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 18.dp
        ) {
            Text(
                text = errorMessage ?: "",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CountriesContentPreview() {
    RefactorMeTheme {
        CountriesContent(
            onLoadButtonClick = {},
            countries = listOf(
                ItemCountry("South Georgia", "King Edward Point", "🇬🇸"),
                ItemCountry("Switzerland", "Bern", "🇨🇭")
            )
        )
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CountriesContentLoadingPreview() {
    RefactorMeTheme {
        ContentLoading()
    }
}
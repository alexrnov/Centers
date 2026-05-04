package alexrnov.centers.presentation.navigation

import alexrnov.centers.presentation.HomeComponent
import alexrnov.centers.presentation.HomeViewModel
import alexrnov.centers.presentation.R
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable object Home

fun NavGraphBuilder.homeScreen(
	innerPadding: PaddingValues,
	isDarkTheme: Boolean,
	isLandscape: Boolean,
	isTablet: Boolean
) {
	composable<Home> {
		// Достаем общую Activity-вьюмодель
		val activity = LocalActivity.current as? ComponentActivity
		val viewModel: HomeViewModel = koinViewModel(viewModelStoreOwner = activity ?: error("Activity not found"))

		val state by viewModel.uiState.collectAsStateWithLifecycle()
		val selectedTabIndex by viewModel.selectedTabIndex.collectAsStateWithLifecycle()

		if (state.isAllDataLoaded) {
			HomeComponent(
				isLandscape = isLandscape,
				isTablet = isTablet,
				innerPadding = innerPadding,
				isDarkTheme = isDarkTheme,
				sphereState = state.sphereStates,
				onUpdate = { tab, prop, value -> viewModel.updateProperty(tab, prop, value) },
				onSave = { tab, prop, value -> viewModel.saveProperty(tab, prop, value) },
				selectedTabIndex = selectedTabIndex,
				onSelectedTab = { viewModel.selectTab(it) }
			)
		} else {
			// Пока DataStore читает диск (доли секунды), показываем заглушку или лоадер.
			// Это полностью защитит от падений и уберет "прыжки" интерфейса.
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {
				//CircularProgressIndicator() // или просто пустой Box(Modifier.fillMaxSize())
				PulsingLoadingText()
			}
		}
	}
}

@Composable
fun PulsingLoadingText() {
	val infiniteTransition = rememberInfiniteTransition(label = "pulse")
	val alpha by infiniteTransition.animateFloat(
		initialValue = 0.3f,
		targetValue = 1f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
			repeatMode = RepeatMode.Reverse
		),
		label = "alpha"
	)

	Text(
		text = stringResource(R.string.loading),
		modifier = Modifier.graphicsLayer(alpha = alpha),
		style = MaterialTheme.typography.titleLarge,
		color = MaterialTheme.colorScheme.onSurface
	)
}

enum class AppTab(val index: Int, val key: String) {
	RED(0, "red"),
	ORANGE(1, "orange"),
	YELLOW(2, "yellow"),
	GREEN(3, "green"),
	LIGHT_BLUE(4, "light_blue"),
	BLUE(5, "blue"),
	PINK(6, "pink")
}
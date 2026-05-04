package alexrnov.centers.presentation.navigation

import alexrnov.centers.presentation.menu.AboutAppComponent
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable object AboutApp

fun NavGraphBuilder.aboutAppScreen(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean
) {
	composable<AboutApp> { entry ->
		AboutAppComponent(innerPadding, isLandscape, isTablet)
	}
}
package alexrnov.centers.presentation.navigation

import alexrnov.centers.presentation.HomeViewModel
import alexrnov.centers.presentation.menu.InstructionComponent
import alexrnov.centers.presentation.tabpanel.TabPanelViewModel
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable object Instruction

fun NavGraphBuilder.instructionScreen(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean,
	navController: NavController
) {
	composable<Instruction> { entry ->
		val homeEntry = remember(entry) { navController.getBackStackEntry(Home) }
		// получить ViewModel из главного экрана
		//val tabPanelViewModel: TabPanelViewModel = koinViewModel(viewModelStoreOwner = homeEntry)
		//val selectedTabIndex by tabPanelViewModel.selectedTabIndex.collectAsStateWithLifecycle()

		val activity = LocalActivity.current as? ComponentActivity
		val viewModel: HomeViewModel = koinViewModel(viewModelStoreOwner = activity ?: error("Activity not found"))

		val state by viewModel.uiState.collectAsStateWithLifecycle()
		val selectedTabIndex by viewModel.selectedTabIndex.collectAsStateWithLifecycle()


		InstructionComponent(
			isLandscape = isLandscape,
			isTablet = isTablet,
			innerPadding = innerPadding,
			selectedTabIndex = selectedTabIndex,
			onSelectedTab = {
				//tabPanelViewModel.selectTab(it)
				viewModel.selectTab(it)
			}
		)
	}
}
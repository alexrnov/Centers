package alexrnov.centers.presentation.tabpanel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class TabPanelViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
	private val _selectedTabIndex = "SELECTED_TAB_INDEX"

	val selectedTabIndex: StateFlow<Int> = savedStateHandle.getStateFlow(_selectedTabIndex, 0)

	fun selectTab(index: Int) {
		savedStateHandle[_selectedTabIndex] = index
	}
}
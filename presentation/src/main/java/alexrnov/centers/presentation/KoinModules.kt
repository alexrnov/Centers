package alexrnov.centers.presentation

import alexrnov.centers.presentation.menu.settings.SettingsViewModel
import alexrnov.centers.presentation.tabpanel.TabPanelViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.module

val presentationModule = module {
	// Специальная функция Koin для регистрации архитектурных ViewModel
	viewModel {
		SettingsViewModel(
			repository = get(), // Koin сам найдет SettingsRepository из dataModule
			clearAllDataUseCase = get() // Koin сам найдет ClearAllData из domainModule
		)
	}

	//viewModelOf(::TabPanelViewModel)

	//viewModelOf(::HomeViewModel)
	viewModel {
		HomeViewModel(
			repository = get(),
			calculateValueUseCase = get()
		)
	}

	// Koin берет repository из dataModule, а tabIndex прилетит из Compose при вызове parametersOf
	/*
	viewModel { (tabIndex: Int) ->
		PropertiesModel(
			repository = get(),
			calculateValueUseCase = get(),
			tabIndex = tabIndex
		)
	}
	*/
}
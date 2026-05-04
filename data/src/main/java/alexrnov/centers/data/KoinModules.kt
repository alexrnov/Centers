import alexrnov.centers.data.local.PropertiesDataStoreManager
import alexrnov.centers.data.local.SettingsDataStoreManager
import alexrnov.centers.data.repository.PropertiesRepositoryImpl
import alexrnov.centers.data.repository.SettingsRepositoryImpl
import alexrnov.centers.domain.repository.PropertiesRepository
import alexrnov.centers.domain.repository.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
	// single создает синглтон. Передаем androidContext() для DataStoreManager
	single { SettingsDataStoreManager(context = androidContext()) }
	// Связываем интерфейс из domain с реализацией из data
	single<SettingsRepository> { SettingsRepositoryImpl(dataStoreManager = get()) }

	// Создаем менеджер (нужен Context, Koin подставит androidContext())
	single { PropertiesDataStoreManager(get()) }
	// Связываем интерфейс репозитория с его реализацией
	single<PropertiesRepository> { PropertiesRepositoryImpl(get()) }

}
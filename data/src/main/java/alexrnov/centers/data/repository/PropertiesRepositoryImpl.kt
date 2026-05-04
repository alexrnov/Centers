package alexrnov.centers.data.repository

import alexrnov.centers.domain.repository.PropertiesRepository
import alexrnov.centers.data.local.PropertiesDataStoreManager
import alexrnov.centers.domain.AllPropertiesState
import kotlinx.coroutines.flow.Flow

class PropertiesRepositoryImpl(
	private val dataStoreManager: PropertiesDataStoreManager
) : PropertiesRepository {

	override suspend fun saveProperty(tabIndex: Int, propertyIndex: Int, value: Float) {
		dataStoreManager.saveProperty(tabIndex, propertyIndex, value)
	}

	override suspend fun getProperty(tabIndex: Int, propertyIndex: Int): Float {
		return dataStoreManager.getProperty(tabIndex, propertyIndex)
	}

	override suspend fun getPropertiesForTab(tabIndex: Int): List<Float> {
		return dataStoreManager.getPropertiesForTab(tabIndex)
	}

	override fun getAllPropertiesFlow(): Flow<AllPropertiesState> {
		return dataStoreManager.getAllPropertiesFlow()
	}

	override suspend fun clearAllProperties() {
		dataStoreManager.clearAllProperties()
	}
}
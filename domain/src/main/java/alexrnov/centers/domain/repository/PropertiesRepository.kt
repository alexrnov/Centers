package alexrnov.centers.domain.repository

import alexrnov.centers.domain.AllPropertiesState
import kotlinx.coroutines.flow.Flow

interface PropertiesRepository {
	suspend fun saveProperty(tabIndex: Int, propertyIndex: Int, value: Float)
	suspend fun getProperty(tabIndex: Int, propertyIndex: Int): Float
	suspend fun getPropertiesForTab(tabIndex: Int): List<Float>
	fun getAllPropertiesFlow(): Flow<AllPropertiesState>
	suspend fun clearAllProperties()
}
package alexrnov.centers.domain

import alexrnov.centers.domain.usecase.CalculateValueUseCase
import alexrnov.centers.domain.usecase.ClearAllDataUseCase
import org.koin.dsl.module

val domainModule = module {
	// factory создает новый экземпляр Use Case каждый раз, когда он запрашивается
	factory { ClearAllDataUseCase(get()) }

	factory { CalculateValueUseCase() }
}
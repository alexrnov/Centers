package alexrnov.centers

import alexrnov.centers.domain.domainModule
import alexrnov.centers.presentation.presentationModule
import android.app.Application
import dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CentersApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@CentersApplication) // передать applicationContext в Koin
			modules(
				presentationModule,
				domainModule,
				dataModule
			)
		}
	}
}
plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)

	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "alexrnov.centers.presentation"
	compileSdk = 36

	defaultConfig {
		minSdk = 24

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		// Правила, которые уходят "наружу" в модуль :app и применяются к нему
		consumerProguardFiles("consumer-rules.pro")
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
	kotlinOptions {
		jvmTarget = "21"
	}

	buildFeatures {
		// включить функцию генерирации классов для доступа к виджетам в XML-разметке
		viewBinding = true
		// включить функцию Data Binding
		dataBinding = true
		// включить Jetpack Compose
		compose = true
	}

	testOptions {
		unitTests {
			// Включение поддержки Android-ресурсов (для Robolectric)
			isIncludeAndroidResources = true
		}
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)

	implementation(libs.androidx.lifecycle.livedata.ktx)
	implementation(libs.androidx.lifecycle.viewmodel.ktx)
	implementation(libs.androidx.datastore.preferences)
	implementation(libs.androidx.fragment.ktx)

	implementation(platform("io.insert-koin:koin-bom:4.0.3"))
	implementation("io.insert-koin:koin-android")
	implementation("io.insert-koin:koin-androidx-compose")

	implementation(libs.androidx.room.runtime)
	annotationProcessor(libs.androidx.room.compiler)

	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.foundation.layout) // или новее
	implementation(libs.androidx.material3)

	implementation(libs.androidx.adaptive)
	implementation(libs.androidx.window)
	implementation("androidx.compose.material:material-icons-extended:1.7.8")

	testImplementation(libs.junit)
	testImplementation(libs.mockito.core)

	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.espresso.intents)
	androidTestImplementation(libs.androidx.runner)
	androidTestImplementation(libs.androidx.rules)

	androidTestImplementation(libs.androidx.junit.v130) // JUnit Extensions
	androidTestImplementation(libs.androidx.truth) // Truth Extensions (Truth для проверок)

	implementation(libs.androidx.navigation.compose)
	// Сама библиотека для работы с JSON / навигацией
	implementation(libs.kotlinx.serialization.json)


	// Core библиотека
	androidTestImplementation(libs.androidx.core) // для инструментальных тестов
	testImplementation(libs.androidx.core) // для локальных

	testImplementation(libs.junit.jupiter.api)
	testRuntimeOnly(libs.junit.jupiter.engine) // Движок для запуска тестов

	androidTestImplementation(libs.androidx.uiautomator) // Тестирование с UI Automator
	testImplementation(kotlin("test"))
	testImplementation(libs.robolectric)

	implementation(project(":domain"))
}
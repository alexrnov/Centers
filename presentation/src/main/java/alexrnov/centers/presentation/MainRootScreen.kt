package alexrnov.centers.presentation

import alexrnov.centers.presentation.navigation.AboutApp
import alexrnov.centers.presentation.navigation.Home
import alexrnov.centers.presentation.navigation.Instruction
import alexrnov.centers.presentation.navigation.Settings
import alexrnov.centers.presentation.navigation.aboutAppScreen
import alexrnov.centers.presentation.navigation.homeScreen
import alexrnov.centers.presentation.navigation.instructionScreen
import alexrnov.centers.presentation.navigation.settingsScreen
import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRootScreen(isDarkTheme: Boolean, isTablet: Boolean) {
	val navController = rememberNavController()
	val configuration = LocalConfiguration.current
	val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

	var expanded by remember { mutableStateOf(false) }

	// Получаем текущий маршрут для отслеживания открытого экрана
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination

	val isHomeScreen = currentDestination?.hasRoute<Home>() == true
	val isInstructionScreen = currentDestination?.hasRoute<Instruction>() == true
	val isSettingsScreen = currentDestination?.hasRoute<Settings>() == true
	val isAboutAppScreen = currentDestination?.hasRoute<AboutApp>() == true

	val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

	val view = LocalView.current
	// убрать статус-бар в альбомной версии смартфона
	SideEffect {
		val window = (view.context as Activity).window
		val insetsController = WindowCompat.getInsetsController(window, view)

		if (isLandscape && !isTablet // В альбоме на телефоне: полностью скрываем статус-бар и панель навигации
				&& (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM)) {
			insetsController.hide(WindowInsetsCompat.Type.systemBars()) // Позволяет системным барам временно появляться, если пользователь свайпнет от края экрана
			insetsController.systemBarsBehavior =
				WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
		} else {
			// В портрете или на планшете: показываем всё обратно
			insetsController.show(WindowInsetsCompat.Type.systemBars())
			// Возвращаем контрастность иконок для светлой/темной темы
			insetsController.isAppearanceLightStatusBars = !isDarkTheme
		}
	}

	val showAppBar = !isLandscape || isAboutAppScreen || isTablet
	Scaffold(
		topBar = {
			// Показываем шапку только в портрете, либо если это планшет.
			if (showAppBar) {
				TopAppBar(
					navigationIcon = {
						if (!isHomeScreen) {
							IconButton(onClick = { navController.navigateUp() }) {
								Icon(
									painter = painterResource(id = R.drawable.back_arrow_48),
									contentDescription = null
									// tint = Color.Unspecified // Отменяет стандартное перекрашивание
								)
							}
						}
					},
					title = {
						@Composable
						fun AppBarTitle(text: String) {
							Text(
								text = text,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis,
								style = MaterialTheme.typography.titleLarge
							)
						}

						when {
							isInstructionScreen -> AppBarTitle(stringResource(R.string.instruction_title))
							isSettingsScreen -> AppBarTitle(stringResource(R.string.settings))
							isAboutAppScreen -> AppBarTitle(stringResource(R.string.about_app))
							else -> AppBarTitle(stringResource(R.string.appbar_name))
						}
					},
					colors = TopAppBarDefaults.topAppBarColors(
						containerColor = MaterialTheme.colorScheme.primaryContainer,
						titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
					),
					actions = {
						if (isHomeScreen) {
							IconButton(onClick = { expanded = true }) {
								Icon(
									painter = painterResource(id = R.drawable.menu_48),
									contentDescription = null
								)
							}
							DropdownMenu(
								expanded = expanded,
								onDismissRequest = { expanded = false },
								modifier = Modifier.widthIn(min = 200.dp)
							) {
								DropdownMenuItem(
									text = { Text(stringResource(R.string.instruction_title)) },
									onClick = {
										expanded = false
										navController.navigate(Instruction)
									}
								)
								DropdownMenuItem(
									text = { Text(stringResource(R.string.settings)) },
									onClick = {
										expanded = false
										navController.navigate(Settings)
									}
								)
								DropdownMenuItem(
									text = { Text(stringResource(R.string.about_app)) },
									onClick = {
										expanded = false
										navController.navigate(AboutApp)
									}
								)
								HorizontalDivider()
								DropdownMenuItem(
									text = { Text(stringResource(R.string.exit)) },
									onClick = {
										expanded = false
										backPressedDispatcher?.onBackPressed()
										expanded = false
										//(context as? Activity)?.finish()
									}
								)
							}
						}
					}
				)
			}
		},
		// Передаем пустые инсеты в Scaffold, если мы в Landscape на телефоне.
		// Это предотвратит прыжки контента и наложение системных плашек.
		//contentWindowInsets = if (showAppBar) ScaffoldDefaults.contentWindowInsets else WindowInsets(0, 0, 0, 0),
	) { innerPadding ->
		NavHost(navController = navController, startDestination = Home) {
			homeScreen(innerPadding, isDarkTheme, isLandscape, isTablet)
			instructionScreen(innerPadding, isLandscape, isTablet, navController)
			settingsScreen(innerPadding, isLandscape, isTablet)
			aboutAppScreen(innerPadding, isLandscape, isTablet)
		}
	}
}
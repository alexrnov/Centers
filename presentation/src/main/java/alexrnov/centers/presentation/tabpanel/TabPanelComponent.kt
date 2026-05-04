package alexrnov.centers.presentation.tabpanel

import alexrnov.centers.presentation.R
import alexrnov.centers.presentation.SphereState
import alexrnov.centers.presentation.getSphereColors
import alexrnov.centers.presentation.getTabContent
import alexrnov.centers.presentation.getTabsText
import alexrnov.centers.presentation.sphereTextLevel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TabPanelComponent(
	sphereState: List<SphereState>,
	onUpdate: (selectedTabIndex: Int, propertyIndex: Int, value: Float) -> Unit,
	onSave: (selectedTabIndex: Int, propertyIndex: Int, value: Float) -> Unit,
	selectedTabIndex: Int,
	isTablet: Boolean,
	onSelectedTab: (index: Int) -> Unit
) {
	// ОПТИМИЗАЦИЯ 1: Кэшируем тяжелые вызовы, чтобы они не выполнялись при каждом микродвижении
	val tabs = getTabsText()
	val colors = remember { getSphereColors() }

	val boxSize = if (isTablet) 42.dp else 30.dp
	val iconSize = if (isTablet) 32.dp else 24.dp

	Column(Modifier.fillMaxSize()) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f) // занять все доступное пространство
		) {
			PrimaryTabRow(
				selectedTabIndex = selectedTabIndex,
				containerColor = MaterialTheme.colorScheme.secondaryContainer
			) {
				tabs.forEachIndexed { index, title ->
					// ОПТИМИЗАЦИЯ 2: Выносим цвет в remember, чтобы Map.getValue не вызывался на каждый чих
					val tabColor = remember(colors, index) { colors.getValue(index) }

					Tab(
						modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
						selected = selectedTabIndex == index,
						onClick = { onSelectedTab.invoke(index) },
						icon = {
							Box(
								contentAlignment = Alignment.Center,
								modifier = Modifier
									.size(boxSize) // Увеличиваем сам круг для планшета
									.background(tabColor, shape = CircleShape)
							) {
								Icon(
									painter = painterResource(id = R.drawable.explosion_24),
									contentDescription = title,
									tint = Color.White,
									modifier = Modifier.size(iconSize) // Увеличиваем саму иконку
								)
							}
						}
					)
				}
			}

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(
						top = if (!isTablet) 4.dp else 12.dp,
						bottom = if (!isTablet) 4.dp else 12.dp
					)
					.wrapContentHeight(),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = tabs[selectedTabIndex],
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSurface
				)
				Text(
					text = ": ",
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSurface
				)
				// Безопасное чтение стейта
				val calculatedValue = sphereState.getOrNull(selectedTabIndex)?.currentCalculatedValue ?: 0f
				Text(
					text = sphereTextLevel(calculatedValue),
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSurface
				)
			}

			// ОПТИМИЗАЦИЯ 3: Получаем контент вкладок ОДИН раз для текущего индекса
			val currentTabContent = getTabContent(selectedTabIndex)
			val currentSphere = sphereState.getOrNull(selectedTabIndex)

			Column(
				modifier = Modifier
					.fillMaxWidth()
					.weight(1f)
					.padding(vertical = 8.dp),
				verticalArrangement = Arrangement.SpaceBetween
			) {
				// ОПТИМИЗАЦИЯ 4: Передаем в Slider ТОЛЬКО изолированные атомарные данные вместо всего списка
				Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
					Slider(
						propertyTitle = currentTabContent.propertyTitle1,
						propertyValues = currentTabContent.propertyValues1,
						currentSliderValue = currentSphere?.property1 ?: 0f,
						onUpdate = { newValue -> onUpdate(selectedTabIndex, 0, newValue) },
						onSave = { finalValue -> onSave(selectedTabIndex, 0, finalValue) },
						isTablet = isTablet
					)
				}
				Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
					Slider(
						propertyTitle = currentTabContent.propertyTitle2,
						propertyValues = currentTabContent.propertyValues2,
						currentSliderValue = currentSphere?.property2 ?: 0f,
						onUpdate = { newValue -> onUpdate(selectedTabIndex, 1, newValue) },
						onSave = { finalValue -> onSave(selectedTabIndex, 1, finalValue) },
						isTablet = isTablet
					)
				}
				Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
					Slider(
						propertyTitle = currentTabContent.propertyTitle3,
						propertyValues = currentTabContent.propertyValues3,
						currentSliderValue = currentSphere?.property3 ?: 0f,
						onUpdate = { newValue -> onUpdate(selectedTabIndex, 2, newValue) },
						onSave = { finalValue -> onSave(selectedTabIndex, 2, finalValue) },
						isTablet = isTablet
					)
				}
			}
		}
	}
}

@Composable
fun Slider(
	propertyTitle: String,
	propertyValues: List<String>, // Предполагаю, что это List<String>
	currentSliderValue: Float,
	onUpdate: (value: Float) -> Unit,
	onSave: (value: Float) -> Unit,
	isTablet: Boolean
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = if (isTablet) 16.dp else 8.dp),
		verticalArrangement = Arrangement.Center
	) {
		// Оптимизация вычисления текста индекса
		val propertyValue = remember(currentSliderValue, propertyValues) {
			val index = currentSliderValue.toInt()
			if (index in propertyValues.indices) propertyValues[index] else ""
		}

		PropertyText(propertyTitle, propertyValue)

		Column(
			modifier = Modifier.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			var localValue by remember(currentSliderValue) { mutableFloatStateOf(currentSliderValue) }

			Slider(
				value = localValue,
				onValueChange = { newValue ->
					localValue = newValue
					onUpdate(newValue)
				},
				onValueChangeFinished = {
					onSave(localValue)
				},
				valueRange = 0f..(propertyValues.size - 1).toFloat(),
				steps = if (propertyValues.size > 2) propertyValues.size - 2 else 0
			)
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PropertyText(propertyTitle: String, propertyValue: String) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(24.dp), // Защищает от изменения высоты при включении basicMarquee
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = propertyTitle,
			maxLines = 1,
			style = MaterialTheme.typography.bodyMedium.copy(
				fontWeight = FontWeight.Medium // или FontWeight.Medium
			),
			color = MaterialTheme.colorScheme.onSurface
		)
		Text(
			text = ": ",
			style = MaterialTheme.typography.bodyMedium.copy(
				fontWeight = FontWeight.Medium
			),
			color = MaterialTheme.colorScheme.onSurface
		)
		Text(
			text = propertyValue,
			maxLines = 1,
			style = MaterialTheme.typography.bodyMedium,
			modifier = Modifier
				.weight(1f) // Занимает всё оставшееся место вместо fillMaxWidth
				.basicMarquee(), // Добавляем эффект бегущей строки
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)
	}
}
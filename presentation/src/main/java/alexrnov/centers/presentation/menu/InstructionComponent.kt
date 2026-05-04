package alexrnov.centers.presentation.menu

import alexrnov.centers.presentation.R
import alexrnov.centers.presentation.getScreenContent
import alexrnov.centers.presentation.getSphereColors
import alexrnov.centers.presentation.getTabsText
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun InstructionComponent(
	isLandscape: Boolean,
	isTablet: Boolean,
	innerPadding: PaddingValues,
	selectedTabIndex: Int,
	onSelectedTab: (index: Int) -> Unit
) {
	val tabs = getTabsText()
	val colors = getSphereColors()
	val content = getScreenContent(selectedTabIndex)
	val density = LocalDensity.current

	// Условие, при котором скрытие НЕ должно работать
	val disableScrollHide = isTablet && isLandscape

	// Оптимизация 1: Кэшируем список секций
	val infoTitle = stringResource(R.string.info_title)
	val meditationTitle = stringResource(R.string.meditation_title)
	val soundTitle = stringResource(R.string.sound_title)
	val aromaTitle = stringResource(R.string.aroma_title)
	val affirmationsTitle = stringResource(R.string.affirmations_title)
	val mineralsTitle = stringResource(R.string.minerals_title)

	val infoSections = remember(selectedTabIndex, content) {
		listOf(
			SectionData(infoTitle, content.info),
			SectionData(meditationTitle, content.meditation),
			SectionData(soundTitle, content.sound),
			SectionData(aromaTitle, content.aroma),
			SectionData(affirmationsTitle, content.affirmations),
			SectionData(mineralsTitle, content.minerals)
		)
	}

	// --- Переменные для отслеживания скролла и скрытия дисклеймера ---
	var disclaimerHeightPx by remember { mutableFloatStateOf(0f) }
	var disclaimerOffsetHeightPx by remember { mutableFloatStateOf(0f) }

	// Слушатель скролла: считает, насколько сдвинуть дисклеймер
	val nestedScrollConnection = remember(disableScrollHide, disclaimerHeightPx) {
		object : androidx.compose.ui.input.nestedscroll.NestedScrollConnection {
			override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
				// ИСПРАВЛЕНИЕ: Если это альбомный планшет, полностью игнорируем скролл
				if (disableScrollHide) return Offset.Zero

				val delta = available.y
				val newOffset = disclaimerOffsetHeightPx + delta
				disclaimerOffsetHeightPx = newOffset.coerceIn(-disclaimerHeightPx, 0f)
				return Offset.Zero
			}
		}
	}

	// Сбрасываем скрытие при переключении табов ИЛИ при изменении конфигурации планшета
	LaunchedEffect(selectedTabIndex, disableScrollHide) {
		disclaimerOffsetHeightPx = 0f
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(innerPadding)
			.nestedScroll(nestedScrollConnection)
	) {
		Column(modifier = Modifier.fillMaxSize()) {
			val boxSize = if (isTablet) 42.dp else 30.dp
			val iconSize = if (isTablet) 32.dp else 24.dp

			PrimaryTabRow(
				selectedTabIndex = selectedTabIndex,
				containerColor = MaterialTheme.colorScheme.secondaryContainer,
				modifier = Modifier.fillMaxWidth()
			) {
				tabs.forEachIndexed { index, title ->
					Tab(
						modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
						selected = selectedTabIndex == index,
						onClick = { onSelectedTab.invoke(index) },
						icon = {
							Box(
								contentAlignment = Alignment.Center, // Центрируем иконку внутри круга
								modifier = Modifier
									.size(boxSize) // УВЕЛИЧИВАЕМ ЦВЕТНОЙ КРУГ
									.background(colors.getValue(index), shape = CircleShape)
							) {
								Icon(
									painter = painterResource(id = R.drawable.explosion_24),
									contentDescription = title,
									tint = Color.White,
									modifier = Modifier.size(iconSize) // УВЕЛИЧИВАЕМ ИКОНКУ
								)
							}
						}
					)
				}
			}

			val topTitleSpacing = if (isTablet) 12.dp else 6.dp
			val bottomTitleSpacing = if (isTablet) 18.dp else 12.dp

			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					text = tabs[selectedTabIndex],
					modifier = Modifier.padding(
						top = topTitleSpacing,
						bottom = bottomTitleSpacing
					),
					style = MaterialTheme.typography.titleMedium
				)
			}

			// Основной контент (Сетка или Список)
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.weight(1f),
				contentAlignment = Alignment.TopCenter
			) {
				if (!isLandscape) {
					val scrollState = remember(selectedTabIndex) { ScrollState(initial = 0) }
					val maxContentWidth = if (isTablet) 560.dp else 500.dp

					Column(
						modifier = Modifier
							.padding(horizontal = 16.dp)
							.verticalScroll(scrollState)
							.widthIn(max = maxContentWidth)
							.fillMaxWidth(),
						verticalArrangement = Arrangement.spacedBy(20.dp)
					) {
						infoSections.forEach { section ->
							InfoSection(title = section.title, desc = section.desc)
						}
					}
				} else {
					// ИСПРАВЛЕНИЕ: Создаем стейт для управления скроллом сетки
					val gridState = rememberLazyStaggeredGridState()

					// ИСПРАВЛЕНИЕ: При смене вкладки принудительно возвращаем скролл в начало (индекс 0)
					LaunchedEffect(selectedTabIndex) {
						gridState.scrollToItem(0)
					}

					val columnSpacing = if (isTablet) 64.dp else 32.dp
					val borderSpacing = if (isTablet) 32.dp else 16.dp

					LazyVerticalStaggeredGrid(
						columns = StaggeredGridCells.Fixed(2),
						state = gridState, // ИСПРАВЛЕНИЕ: Привязываем стейт к нашей сетке
						modifier = Modifier.fillMaxSize(),
						contentPadding = PaddingValues(
							start = borderSpacing,
							//top = 8.dp,
							end = borderSpacing,
							// ИСПРАВЛЕНИЕ: Если это планшет, нижний отступ стандартный (16dp).
							// Если телефон — высчитываем динамически, так как он накладывается поверх.
							bottom = if (disableScrollHide) 16.dp else with(density) {
								(disclaimerHeightPx + disclaimerOffsetHeightPx).toDp()
							} + 16.dp
						),
						horizontalArrangement = Arrangement.spacedBy(columnSpacing),
						verticalItemSpacing = 20.dp
					) {
						// ДОБАВЛЯЕМ КЛЮЧ: index или section.title (если заголовки уникальные)
						items(
							count = infoSections.size,
							key = { index -> infoSections[index].title } // Позволяет Compose мгновенно переиспользовать ячейки
						) { index ->
							val section = infoSections[index]
							InfoSection(title = section.title, desc = section.desc)
						}
					}
				}
			}

			// В портретном режиме ИЛИ на альбомном ПЛАНШЕТЕ размещаем дисклеймер статично внизу.
			// Теперь он находится внутри Column, сетка сожмется и текст никогда его не перекроет.
			if (!isLandscape || disableScrollHide) {
				Disclaimer(isTablet)
			}
		}

		// В альбомном режиме НА ТЕЛЕФОНАХ накладываем дисклеймер поверх и прячем при скролле.
		// Для планшетов (где disableScrollHide == true) этот блок теперь просто игнорируется.
		if (isLandscape && !disableScrollHide) {
			Box(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.onGloballyPositioned { coordinates ->
						disclaimerHeightPx = coordinates.size.height.toFloat()
					}
					.offset { IntOffset(x = 0, y = -disclaimerOffsetHeightPx.roundToInt()) }
			) {
				Disclaimer(isTablet = false)
			}
		}
	}
}

@Composable
fun InfoSection(title: String, desc: String) {
	Column(
		modifier = Modifier.fillMaxWidth() // Гарантируем, что контейнер секции занимает всю ширину
	) {
		Text(
			modifier = Modifier.padding(bottom = 6.dp),
			text = title,
			style = MaterialTheme.typography.titleMedium.copy(
				platformStyle = PlatformTextStyle(includeFontPadding = false)
			),
			color = MaterialTheme.colorScheme.onSurface
		)
		Text(
			text = desc,
			style = MaterialTheme.typography.bodyMedium.copy(
				hyphens = Hyphens.Auto, // Автопереносы (помогут избежать больших дыр между словами)
				lineBreak = LineBreak(
					strategy = LineBreak.Strategy.HighQuality,
					strictness = LineBreak.Strictness.Strict,
					wordBreak = LineBreak.WordBreak.Default
				),
				platformStyle = PlatformTextStyle(includeFontPadding = false)
			),
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			textAlign = TextAlign.Justify, // Включаем выравнивание по ширине (выравнивание краев)
			modifier = Modifier.fillMaxWidth() // Заставляем сам текстовый элемент растягиваться
		)
	}
}

// ОПТИМИЗАЦИЯ 2: Делаем функцию Disclaimer стабильной (skippable)
@Composable
private fun Disclaimer(isTablet: Boolean) {
	val verticalSpacing = if (isTablet) 16.dp else 8.dp

	// Кэшируем строковые ресурсы внутри самого компонента,
	// чтобы они не запрашивались у операционной системы при каждом кадре рекомпозиции
	val titleText = stringResource(R.string.disclaimer_title)
	val descriptionText = stringResource(R.string.disclaimer_description)

	Box(
		modifier = Modifier.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		Column(
			modifier = Modifier
				.wrapContentHeight()
				.widthIn(min = 240.dp, max = 500.dp)
				.padding(horizontal = 16.dp, vertical = verticalSpacing)
				.clip(RoundedCornerShape(4.dp))
				.background(MaterialTheme.colorScheme.secondaryContainer),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
				text = titleText,
				style = MaterialTheme.typography.titleMedium
			)
			Text(
				modifier = Modifier.padding(bottom = 4.dp),
				text = descriptionText,
				style = MaterialTheme.typography.bodySmall,
				textAlign = TextAlign.Center,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}

// ОПТИМИЗАЦИЯ 1: Добавляем аннотацию @Immutable.
// Это сообщает компилятору Compose, что данные внутри класса никогда не изменятся после создания.
// Теперь Compose сможет ПРЕДОТВРАЩАТЬ (skip) перерисовку InfoSection, если текст внутри остался прежним.
@Immutable
data class SectionData(val title: String, val desc: String)
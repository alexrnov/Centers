package alexrnov.centers.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

data class TabContent(
	val propertyTitle1: String,
	val propertyTitle2: String,
	val propertyTitle3: String,

	val propertyValues1: List<String>,
	val propertyValues2: List<String>,
	val propertyValues3: List<String>
)

@Composable
fun getTabContent(index: Int): TabContent {
	return when (index) {
		0 -> TabContent(
			propertyTitle1 = stringResource(R.string.force_title),
			propertyTitle2 = stringResource(R.string.stability_title),
			propertyTitle3 = stringResource(R.string.finance_title),
			propertyValues1 = listOf(
				stringResource(R.string.force0),
				stringResource(R.string.force1),
				stringResource(R.string.force2),
				stringResource(R.string.force3),
				stringResource(R.string.force4)
			),
			propertyValues2 = listOf(
				stringResource(R.string.stability0),
				stringResource(R.string.stability1),
				stringResource(R.string.stability2),
				stringResource(R.string.stability3),
				stringResource(R.string.stability4)
			),
			propertyValues3 = listOf(
				stringResource(R.string.finance0),
				stringResource(R.string.finance1),
				stringResource(R.string.finance2),
				stringResource(R.string.finance3),
				stringResource(R.string.finance4),
			)
		)
		1 -> TabContent(
			propertyTitle1 = stringResource(R.string.life_energy_title),
			propertyTitle2 = stringResource(R.string.reproduction_title),
			propertyTitle3 = stringResource(R.string.joy_title),
			propertyValues1 = listOf(
				stringResource(R.string.life_energy0),
				stringResource(R.string.life_energy1),
				stringResource(R.string.life_energy2),
				stringResource(R.string.life_energy3),
				stringResource(R.string.life_energy4),
			),
			propertyValues2 = listOf(
				stringResource(R.string.reproduction0),
				stringResource(R.string.reproduction1),
				stringResource(R.string.reproduction2),
				stringResource(R.string.reproduction3),
				stringResource(R.string.reproduction4),
			),
			propertyValues3 = listOf(
				stringResource(R.string.joy0),
				stringResource(R.string.joy1),
				stringResource(R.string.joy2),
				stringResource(R.string.joy3),
				stringResource(R.string.joy4),
			)
		)
		2 -> TabContent(
			propertyTitle1 = stringResource(R.string.confidence_title),
			propertyTitle2 = stringResource(R.string.digestion_title),
			propertyTitle3 = stringResource(R.string.social_title),
			propertyValues1 = listOf(
				stringResource(R.string.confidence0),
				stringResource(R.string.confidence1),
				stringResource(R.string.confidence2),
				stringResource(R.string.confidence3),
				stringResource(R.string.confidence4),
			),
			propertyValues2 = listOf(
				stringResource(R.string.digestion0),
				stringResource(R.string.digestion1),
				stringResource(R.string.digestion2),
				stringResource(R.string.digestion3),
				stringResource(R.string.digestion4),
			),

			propertyValues3 = listOf(
				stringResource(R.string.social0),
				stringResource(R.string.social1),
				stringResource(R.string.social2),
				stringResource(R.string.social3),
				stringResource(R.string.social4),
			)
		)
		3 -> TabContent(
			propertyTitle1 = stringResource(R.string.kindness_title),
			propertyTitle2= stringResource(R.string.heart_title),
			propertyTitle3= stringResource(R.string.nature_title),
			propertyValues1 = listOf(
				stringResource(R.string.kindness0),
				stringResource(R.string.kindness1),
				stringResource(R.string.kindness2),
				stringResource(R.string.kindness3),
				stringResource(R.string.kindness4),
			),
			propertyValues2 = listOf(
				stringResource(R.string.heart0),
				stringResource(R.string.heart1),
				stringResource(R.string.heart2),
				stringResource(R.string.heart3),
				stringResource(R.string.heart4),
			),
			propertyValues3 = listOf(
				stringResource(R.string.nature0),
				stringResource(R.string.nature1),
				stringResource(R.string.nature2),
				stringResource(R.string.nature3),
				stringResource(R.string.nature4),
			)
		)
		4 -> TabContent(
			propertyTitle1 = stringResource(R.string.communication_title),
			propertyTitle2 = stringResource(R.string.honesty_title),
			propertyTitle3 = stringResource(R.string.throat_title),
			propertyValues1 = listOf(
				stringResource(R.string.communication0),
				stringResource(R.string.communication1),
				stringResource(R.string.communication2),
				stringResource(R.string.communication3),
				stringResource(R.string.communication4),
			),
			propertyValues2 = listOf(
				stringResource(R.string.honesty0),
				stringResource(R.string.honesty1),
				stringResource(R.string.honesty2),
				stringResource(R.string.honesty3),
				stringResource(R.string.honesty4),
			),
			propertyValues3 = listOf(
				stringResource(R.string.throat0),
				stringResource(R.string.throat1),
				stringResource(R.string.throat2),
				stringResource(R.string.throat3),
				stringResource(R.string.throat4),
			)
		)
		5 -> TabContent(
			propertyTitle1 = stringResource(R.string.intelligence_title),
			propertyTitle2 = stringResource(R.string.intuition_title),
			propertyTitle3 = stringResource(R.string.eyes_title),
			propertyValues1 = listOf(
				stringResource(R.string.intelligence0),
				stringResource(R.string.intelligence1),
				stringResource(R.string.intelligence2),
				stringResource(R.string.intelligence3),
				stringResource(R.string.intelligence4),
			),
			propertyValues2 = listOf(
				stringResource(R.string.intuition0),
				stringResource(R.string.intuition1),
				stringResource(R.string.intuition2),
				stringResource(R.string.intuition3),
				stringResource(R.string.intuition4),
			),
			propertyValues3 = listOf(
				stringResource(R.string.eyes0),
				stringResource(R.string.eyes1),
				stringResource(R.string.eyes2),
				stringResource(R.string.eyes3),
				stringResource(R.string.eyes4),
			)
		)
		else -> TabContent(
			propertyTitle1 = stringResource(R.string.perception_title),
			propertyTitle2 = stringResource(R.string.sleep_title),
			propertyTitle3 = stringResource(R.string.mental_title),
			propertyValues1 = listOf(
				stringResource(R.string.perception0),
				stringResource(R.string.perception1),
				stringResource(R.string.perception2),
				stringResource(R.string.perception3),
				stringResource(R.string.perception4),
			),
			propertyValues2 = listOf(
				stringResource(R.string.sleep0),
				stringResource(R.string.sleep1),
				stringResource(R.string.sleep2),
				stringResource(R.string.sleep3),
				stringResource(R.string.sleep4),
			),
			propertyValues3 = listOf(
				stringResource(R.string.mental0),
				stringResource(R.string.mental1),
				stringResource(R.string.mental2),
				stringResource(R.string.mental3),
				stringResource(R.string.mental4),
			)
		)
	}
}

@Composable
fun sphereTextLevel(value: Float): String {
	return when {
		value < 3f -> stringResource(R.string.title_value_very_low)
		value < 6f -> stringResource(R.string.title_value_low)
		value < 9f -> stringResource(R.string.title_value_medium)
		value < 12f -> stringResource(R.string.title_value_high)
		else -> stringResource(R.string.title_value_very_high)
	}
}

@Composable
fun getTabsText(): List<String> {
	return listOf(
		stringResource(R.string.title_red_tab),
		stringResource(R.string.title_orange_tab),
		stringResource(R.string.title_yellow_tab),
		stringResource(R.string.title_green_tab),
		stringResource(R.string.title_light_blue_tab),
		stringResource(R.string.title_blue_tab),
		stringResource(R.string.title_pink_tab)
	)
}

// instruction menu
@Composable
fun getScreenContent(index: Int): ScreenContent {
	return when (index) {
		0 -> ScreenContent(
			info = stringResource(R.string.info_red_tab),
			meditation = stringResource(R.string.meditation_red_tab),
			sound = stringResource(R.string.sound_red_tab),
			aroma = stringResource(R.string.aroma_red_tab),
			affirmations = stringResource(R.string.affirmations_red_tab),
			minerals = stringResource(R.string.minerals_red_tab)
		)
		1 -> ScreenContent(
			info = stringResource(R.string.info_orange_tab),
			meditation = stringResource(R.string.meditation_orange_tab),
			sound = stringResource(R.string.sound_orange_tab),
			aroma = stringResource(R.string.aroma_orange_tab),
			affirmations = stringResource(R.string.affirmations_orange_tab),
			minerals = stringResource(R.string.minerals_orange_tab)
		)
		2 -> ScreenContent(
			info = stringResource(R.string.info_yellow_tab),
			meditation = stringResource(R.string.meditation_yellow_tab),
			sound = stringResource(R.string.sound_yellow_tab),
			aroma = stringResource(R.string.aroma_yellow_tab),
			affirmations = stringResource(R.string.affirmations_yellow_tab),
			minerals = stringResource(R.string.minerals_yellow_tab)
		)
		3 -> ScreenContent(
			info = stringResource(R.string.info_green_tab),
			meditation = stringResource(R.string.meditation_green_tab),
			sound = stringResource(R.string.sound_green_tab),
			aroma = stringResource(R.string.aroma_green_tab),
			affirmations = stringResource(R.string.affirmations_green_tab),
			minerals = stringResource(R.string.minerals_green_tab)
		)
		4 -> ScreenContent(
			info = stringResource(R.string.info_light_blue_tab),
			meditation = stringResource(R.string.meditation_light_blue_tab),
			sound = stringResource(R.string.sound_light_blue_tab),
			aroma = stringResource(R.string.aroma_light_blue_tab),
			affirmations = stringResource(R.string.affirmations_light_blue_tab),
			minerals = stringResource(R.string.minerals_light_blue_tab)
		)
		5 -> ScreenContent(
			info = stringResource(R.string.info_blue_tab),
			meditation = stringResource(R.string.meditation_blue_tab),
			sound = stringResource(R.string.sound_blue_tab),
			aroma = stringResource(R.string.aroma_blue_tab),
			affirmations = stringResource(R.string.affirmations_blue_tab),
			minerals = stringResource(R.string.minerals_blue_tab)
		)
		else -> ScreenContent(
			info = stringResource(R.string.info_pink_tab),
			meditation = stringResource(R.string.meditation_pink_tab),
			sound = stringResource(R.string.sound_pink_tab),
			aroma = stringResource(R.string.aroma_pink_tab),
			affirmations = stringResource(R.string.affirmations_pink_tab),
			minerals = stringResource(R.string.minerals_pink_tab)
		)
	}
}

data class ScreenContent(
	val info: String,
	val meditation: String,
	val sound: String,
	val aroma: String,
	val affirmations: String,
	val minerals: String
)
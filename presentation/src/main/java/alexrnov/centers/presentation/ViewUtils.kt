package alexrnov.centers.presentation

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

fun getSphereColors(): Map<Int, Color> {
	return mapOf(
		0 to Color(0xFFFF1717),
		1 to Color(0xFFFF8C2D),
		2 to Color(0xFFFFDB1D),
		3 to Color(0xFF37D423),
		4 to Color(0xFF78EFFF),
		5 to Color(0xFF4C4CFF),
		6 to Color(0xFFF282FF)
	)
}

fun adaptiveTypography(isTab: Boolean): Typography {
	return Typography(
		titleMedium = TextStyle(
			fontSize = if (isTab) 18.sp else 16.sp,
			fontWeight = FontWeight.Medium
		),
		bodyMedium = TextStyle(
			fontSize = if (isTab) 16.sp else 14.sp
		)
	)
}
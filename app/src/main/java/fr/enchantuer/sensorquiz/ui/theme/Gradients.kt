package fr.enchantuer.sensorquiz.ui.theme

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.violetGradientBackground(): Modifier {
    return this.background(
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF7B6EF6), // Violet plus profond
                Color(0xFF8E80FF)  // Violet lavande clair
            )
        )
    )
}
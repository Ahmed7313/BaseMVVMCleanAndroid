package com.daman.edman.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.basemvvmcleanandroid.ui.theme.lightBlue

@Composable
fun SegmentedPills(
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    // Keep track of the currently selected option
    val (selectedOption, setSelectedOption) = remember { mutableStateOf(options.first()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption

            // Choose your colors/styles for selected vs unselected
            val backgroundColor = if (isSelected) lightBlue else Color.White
            val textColor = if (isSelected) Color.Black else Color.Black

            HeaderText(
                text = option,
                color = textColor,
                fontSize = 14,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))     // Pill shape
                    .background(backgroundColor)
                    .clickable {
                        setSelectedOption(option)
                        onOptionSelected(option)
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                ,
                textAlign = TextAlign.Center
            )
        }
    }
}

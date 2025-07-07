package com.daman.edman.screens.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun IconTextView(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    textColor : Color = Color.Black,
    tint : Color = Color.Black
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = tint
        )

        AppSpacer(width = 8.dp)
        HeaderText(text = text, fontSize = 14, color = textColor)
    }
}


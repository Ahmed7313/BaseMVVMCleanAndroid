package com.example.basemvvmcleanandroid.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daman.edman.screens.components.AppSpacer
import com.daman.edman.screens.components.HeaderText
import com.daman.edman.screens.components.NormalText
import com.example.basemvvmcleanandroid.ui.theme.GreenColor
import com.example.basemvvmcleanandroid.R
import com.trend.camelx.ui.theme.large


@Composable
fun ScanItem(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    icon : Int,
    height : Int = 150,
    iconSize : Int = 50,
    textColor: Color = Color.White,
    text: String = "",
    showText: Boolean = true,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = color)
            .height(height.dp)
            .border(color = Color.White, width = 1.dp)
            .clickable{
                onClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(icon),
            contentDescription = "Scan Icon",
            modifier = Modifier
                .fillMaxWidth()
                .height(iconSize.dp),
            tint = Color.White
        )

        if (showText) {
            AppSpacer(height = large)

            NormalText(text = "تحقيق", color = textColor, fontSize = 12)

            HeaderText(
                text = text,
                modifier = Modifier,
                fontSize = 16,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun ScanItemPreview() {
    ScanItem(
        color = GreenColor,
        icon = R.drawable.ticket_icon,
        text = "مسح",
        onClick = {}
    )
}
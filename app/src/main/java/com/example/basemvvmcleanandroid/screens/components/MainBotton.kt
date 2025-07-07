package com.trend.thecontent.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aramex.mypos.Presentation.Components.AutoSizeText

import com.example.basemvvmcleanandroid.ui.theme.BahijSansArabic
import com.example.basemvvmcleanandroid.ui.theme.GreenColor
import com.example.basemvvmcleanandroid.ui.theme.buttonColor
import com.trend.camelx.ui.theme.medium
import com.trend.camelx.ui.theme.spacing


@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    height: Dp = 48 .dp,
    fontSize: TextUnit = 18.sp,
    text: String,
    color: Color = GreenColor,
    textColor : Color = Color.White,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier.height(height).fillMaxWidth(),
        shape = RoundedCornerShape(spacing),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = GreenColor,
            containerColor = color
        ),
        onClick = { onClick() },

        ) {

        AutoSizeText(
            text = text,
            fontSize = fontSize,
            lineHeight = 20.sp,
            fontFamily = BahijSansArabic,
            fontWeight = FontWeight(400),
            color = textColor,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun MainButtonStrock(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = buttonColor,
    height: Dp = 50.dp,
    fontSize: TextUnit = 14.sp,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .height(height),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = color
        ),
        shape = RoundedCornerShape(20),
        onClick = { onClick() },

        ) {

        AutoSizeText(
            text = text,
            fontSize = fontSize,
            lineHeight = 20.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight(400),
            color = color,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewmainButton() {
    MainButton(text = "التالي") {

    }
}

@Preview
@Composable
fun MainButtonStrock() {
    MainButtonStrock(text = "ال asd aتالي") {

    }
}
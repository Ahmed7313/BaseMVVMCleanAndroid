package com.aramex.mypos.Presentation.Components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Center,
    lineHeight: TextUnit = TextUnit.Unspecified,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    BoxWithConstraints(modifier = modifier) {
        var shrunkFontSize = fontSize
        val calculateIntrinsics = @Composable {
            ParagraphIntrinsics(
                text, TextStyle(
                    color = color,
                    fontSize = shrunkFontSize,
                    fontWeight = fontWeight,
                    textAlign = textAlign,
                    lineHeight = lineHeight,
                    fontFamily = fontFamily,
                    textDecoration = textDecoration,
                    fontStyle = fontStyle,
                    letterSpacing = letterSpacing
                ),
                listOf(), // placeholders for spans and placeholders not used
                density = LocalDensity.current,
                fontFamilyResolver = LocalFontFamilyResolver.current
            )
        }

        var intrinsics = calculateIntrinsics()
        with(LocalDensity.current) {
            while (intrinsics.maxIntrinsicWidth > maxWidth.toPx()) {
                shrunkFontSize *= 0.9
                intrinsics = calculateIntrinsics()
            }
        }
        Text(
            text = text,
            modifier = modifier,
            color = color,
            fontSize = shrunkFontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            onTextLayout = onTextLayout,
            style = style
        )
    }
}

package com.daman.edman.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.trend.camelx.ui.theme.large
import com.trend.camelx.ui.theme.spacing

@Composable
fun AppHolder(modifier: Modifier = Modifier,
              color: Color = Color.White,
              noColor : Boolean = false,
              centerAlignment : Boolean = false,
              view : @Composable ()-> Unit) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = if (noColor) Color.Transparent else color)
            .padding(start = spacing, end = spacing, top = large, bottom = large)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = if (centerAlignment)  Alignment.CenterHorizontally else Alignment.Start,
    ) {
        view()
    }
}

@Composable
fun AppHolderBox(modifier: Modifier = Modifier,
              noColor : Boolean = false,
              centerAlignment : Boolean = false,
              view : @Composable ()-> Unit) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(start = spacing, end = spacing, top = large, bottom = large)
            .verticalScroll(rememberScrollState()),
    ) {
        view()
    }
}
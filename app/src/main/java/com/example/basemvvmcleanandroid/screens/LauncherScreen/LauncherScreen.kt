package com.example.basemvvmcleanandroid.screens.LauncherScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.daman.edman.screens.components.AppHolder
import com.daman.edman.screens.components.AppHolderBox
import com.daman.edman.screens.components.AppSpacer
import com.daman.edman.screens.components.BorderView
import com.daman.edman.screens.components.HeaderText
import com.example.basemvvmcleanandroid.R
import com.example.basemvvmcleanandroid.ui.theme.GreenColor
import com.trend.camelx.ui.theme.spacing
import com.trend.thecontent.screens.components.MainButton

@Preview
@Composable
fun LauncherScreen() {


    Box(modifier = Modifier.fillMaxSize()) {

        AppHolderBox(noColor = true, centerAlignment = true) {



                HeaderText(
                    modifier = Modifier.align(Alignment.TopCenter),
                    text = "تطبيق التحقيق",
                    color = Color.White
                )

                AppSpacer(height = spacing)




            Column(modifier = Modifier.align(Alignment.Center)) {
                HeaderText(text = "نوع التحقق", color = GreenColor, modifier = Modifier.align(
                    Alignment.CenterHorizontally))
                AppSpacer(height = spacing)

                BorderView (){
                    var checkChecked by remember { mutableStateOf(false) }
                    var loginChecked by remember { mutableStateOf(false) }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = checkChecked,
                                onCheckedChange = { isChecked ->
                                    checkChecked = isChecked
                                    if (isChecked) loginChecked = false
                                }
                            )
                            Text(text = "check")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = loginChecked,
                                onCheckedChange = { isChecked ->
                                    loginChecked = isChecked
                                    if (isChecked) checkChecked = false
                                }
                            )
                            Text(text = "log in")
                        }
                    }
                }
            }



            MainButton(text = "اختر",
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = spacing) ) { }

        }


        Image(
            painter = painterResource(R.drawable.home_bg_sports),
            contentDescription = "Launcher Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f,
            alignment = androidx.compose.ui.Alignment.Center,
        )
    }

}
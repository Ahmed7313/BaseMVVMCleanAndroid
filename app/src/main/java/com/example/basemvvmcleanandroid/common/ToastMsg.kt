package com.aramex.mypos.Common

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import xyz.hasnat.sweettoast.SweetToast


fun Context.showSuccessMsg(msg: String){
    SweetToast.success(this, msg);

}

fun Context.showErrorMsg(msg: String){
    SweetToast.error(this, msg);
}
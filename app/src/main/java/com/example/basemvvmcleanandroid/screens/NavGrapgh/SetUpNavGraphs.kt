package com.aramex.mypos.Presentation.NavGrapghs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.basemvvmcleanandroid.screens.ScanLogin.ScanLoginScreen
import com.example.basemvvmcleanandroid.screens.LauncherScreen.LauncherScreen
import com.example.basemvvmcleanandroid.screens.NavGrapgh.HomeScreen
import com.example.basemvvmcleanandroid.screens.NavGrapgh.LauncherScreen
import com.example.basemvvmcleanandroid.screens.NavGrapgh.LoginScreen
import com.example.basemvvmcleanandroid.screens.NavGrapgh.ScanLoginScreen
import com.example.basemvvmcleanandroid.screens.NavGrapgh.SettingsScreen
import com.example.basemvvmcleanandroid.screens.Settings.SettingsScreen
import com.example.basemvvmcleanandroid.screens.login.LoginScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SetUpNavGraph (navController : NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = LauncherScreen, modifier = modifier) {


        composable<LauncherScreen> {
         LauncherScreen(navController)
        }

        composable <LoginScreen>{
            LoginScreen(navController)
        }

        composable <ScanLoginScreen>{
            ScanLoginScreen(navController)
        }

        composable<SettingsScreen> {
            SettingsScreen(navController)
        }

        composable<HomeScreen> {
            com.example.basemvvmcleanandroid.screens.Home.HomeScreen(navController)
        }
    }
}

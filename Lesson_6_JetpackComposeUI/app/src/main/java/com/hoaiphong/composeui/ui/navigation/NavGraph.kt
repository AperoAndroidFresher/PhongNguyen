package com.hoaiphong.composeui.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hoaiphong.composeui.ui.screen.login.LoginScreen
import com.hoaiphong.composeui.ui.screen.login.SignUpScreen

object Routes {
    const val LOGIN = "login"
    const val SIGN_UP = "sign_up"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(Routes.SIGN_UP)
                }
            )
        }
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
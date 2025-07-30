package com.hoaiphong.composeui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hoaiphong.composeui.ui.screen.login.LoginScreen
import com.hoaiphong.composeui.ui.screen.login.LoginViewModel
import com.hoaiphong.composeui.ui.screen.signup.SignUpScreen
import com.hoaiphong.composeui.ui.screen.signup.SignUpViewModel
import com.hoaiphong.composeui.ui.screen.splash.SplashScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = "splash"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("splash") {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("login") {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                viewModel = loginViewModel,
                navController = navController,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate("signup")
                }
            )
        }

        composable("signup") {
            val signUpViewModel: SignUpViewModel = viewModel()
            SignUpScreen(
                viewModel = signUpViewModel,
                onSignUpSuccess = { username, password ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("username", username)
                    navController.previousBackStackEntry?.savedStateHandle?.set("password", password)
                    navController.popBackStack()
                }
            )
        }

    }
}
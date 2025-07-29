package com.hoaiphong.composeui.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.hoaiphong.composeui.ui.navigation.Home
import com.hoaiphong.composeui.ui.screen.login.LoginScreen
import com.hoaiphong.composeui.ui.screen.login.SignUpScreen
import com.hoaiphong.composeui.ui.screen.splash.SplashScreen
import com.hoaiphong.composeui.ui.navigation.Home as HomeRoute

@Composable
fun AppNavGraph() {
    val topLevelBackStack = remember { TopLevelBackStack<Any>(Home) }
    val backStack = remember { mutableStateListOf<Route>(Splash) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { route ->
            when (route) {
                is Splash -> NavEntry(route) {
                    SplashScreen(onNavigateToLogin = {
                        backStack.remove(Splash)
                        backStack.add(Login())
                    })
                }

                is Login -> NavEntry(route) {
                    LoginScreen(
                        defaultUsername = route.defaultUsername,
                        defaultPassword = route.defaultPassword,
                        onSignUpClick = { backStack.add(SignUp) },
                        onLoginSuccess = {
                            backStack.clear()
                            backStack.add(HomeRoute)
                        }
                    )
                }

                is SignUp -> NavEntry(route) {
                    SignUpScreen(
                        onBackClick = { backStack.removeLastOrNull() },
                        onSignUpSuccess = { username, password ->
                            backStack.clear()
                            backStack.add(Login(username, password))
                        }
                    )
                }


                is HomeRoute -> NavEntry(route) {
                    TopLevelNavGraph(topLevelBackStack)
                }

                else -> error("Unknown route: $route")
            }
        }
    )
}


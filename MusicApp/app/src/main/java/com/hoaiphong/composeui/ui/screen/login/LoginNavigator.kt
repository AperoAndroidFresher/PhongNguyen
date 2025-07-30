package com.hoaiphong.composeui.ui.screen.login


import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController

fun handleLoginEffect(
    effect: LoginEffect,
    navController: NavHostController,
    context: Context
) {
    when (effect) {
        is LoginEffect.NavigateToHome -> {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true } // clear login khỏi backstack nếu cần
            }
        }
        is LoginEffect.NavigateToSignUp -> {
            navController.navigate("signup")
        }
        is LoginEffect.ShowToast -> {
            Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}
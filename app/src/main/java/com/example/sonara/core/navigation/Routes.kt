package com.example.sonara.core.navigation

sealed class Routes(val route: String) {

    // AUTH
    data object Start : Routes("start")

    data object Login : Routes("login")

    data object SignUp : Routes("sign_up")

    data object RecoverPassword : Routes("recover_password")

    data object RedefinedPassword : Routes("redefined_password")

    // MAIN
    data object Main : Routes("main")

    data object Home : Routes("home")

    data object Search : Routes("search")

    data object Events : Routes("events")

    data object Plans : Routes("plans")
}
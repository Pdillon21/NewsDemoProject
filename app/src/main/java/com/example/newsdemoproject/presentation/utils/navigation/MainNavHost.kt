package com.example.newsdemoproject.presentation.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsdemoproject.presentation.screens.article_detail.DetailScreen
import com.example.newsdemoproject.presentation.screens.main.MainScreen
import com.example.newsdemoproject.presentation.utils.navigation.NavHostConstants.DETAIL_SCREEN_ARGS_ARTICLE_URL
import com.example.newsdemoproject.presentation.utils.navigation.NavHostConstants.DETAIL_SCREEN_ARGS_BASE_COLOR
import com.example.newsdemoproject.presentation.utils.navigation.NavHostConstants.DETAIL_SCREEN_NAV_FULL_KEY
import com.example.newsdemoproject.presentation.utils.navigation.NavHostConstants.MAIN_SCREEN_NAV_KEY

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN_NAV_KEY
    ) {
        composable(MAIN_SCREEN_NAV_KEY) {
            MainScreen()
        }
        composable(
            route = DETAIL_SCREEN_NAV_FULL_KEY,
            arguments = listOf(
                navArgument(DETAIL_SCREEN_ARGS_ARTICLE_URL) { type = NavType.StringType },
                navArgument(DETAIL_SCREEN_ARGS_BASE_COLOR) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val articleURl = navBackStackEntry.arguments?.getString(DETAIL_SCREEN_ARGS_ARTICLE_URL)
            val baseColor =
                (navBackStackEntry.arguments?.getString(DETAIL_SCREEN_ARGS_BASE_COLOR)?.toColorInt()
                    ?: Color.Black) as Color

            requireNotNull(articleURl)
            DetailScreen(averageColor = baseColor, articleApiUrl = articleURl)
        }
    }
}

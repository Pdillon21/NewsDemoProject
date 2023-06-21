package com.example.newsdemoproject.presentation.utils.navigation

object NavHostConstants {

    const val MAIN_SCREEN_NAV_KEY = "main_screen_nav"
    const val DETAIL_SCREEN_ARGS_BASE_COLOR = "detail_screen_base_color"
    const val DETAIL_SCREEN_ARGS_ARTICLE_URL = "details_screen_article_url"
    const val DETAIL_SCREEN_NAV_BASE_KEY = "detail_screen_nav"
    const val DETAIL_SCREEN_NAV_FULL_KEY = DETAIL_SCREEN_NAV_BASE_KEY +
            "/{$DETAIL_SCREEN_ARGS_BASE_COLOR}" +
            "/{$DETAIL_SCREEN_ARGS_ARTICLE_URL}"
}

package com.sercan.bookpedia.core.navigation

sealed interface Route {
    data object Home : Route
    data object Search : Route
    data object Favorites : Route
    data class BookDetails(val bookId: String) : Route
} 
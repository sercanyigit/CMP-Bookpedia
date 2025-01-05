package com.sercan.bookpedia.core.navigation

sealed interface Route {
    data object Home : Route
    data object Search : Route
    data object Favorites : Route
    data class BookDetails(val bookId: String) : Route
    
    companion object {
        fun fromString(route: String): Route {
            return when (route) {
                "home" -> Home
                "search" -> Search
                "favorites" -> Favorites
                else -> Home
            }
        }
        
        fun toString(route: Route): String {
            return when (route) {
                is Home -> "home"
                is Search -> "search"
                is Favorites -> "favorites"
                is BookDetails -> "book_details/${route.bookId}"
            }
        }
    }
} 
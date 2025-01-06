package com.sercan.bookpedia.core.presentation.utils

object Constants {
    object Animation {
        const val DEFAULT_DURATION = 300
        const val HOVER_DURATION = 150
        const val PAGER_DELAY = 3000
        const val SEARCH_DEBOUNCE = 500L
    }
    
    object UI {
        const val DEFAULT_PADDING = 16
        const val SMALL_PADDING = 8
        const val LOADING_SIZE = 250
        const val BOOK_COVER_WIDTH = 120
        const val BOOK_COVER_HEIGHT = 180
        const val INDICATOR_SIZE = 10
        const val INDICATOR_SMALL_SIZE = 8
        const val INDICATOR_SPACING = 2
        const val PAGE_INDICATOR_HEIGHT = 16
        const val SEARCH_BAR_HEIGHT = 56
        const val SEARCH_BAR_CORNER_RADIUS = 12
    }

    object Search {
        const val MIN_QUERY_LENGTH = 2
        const val ALPHA_DISABLED = 0.3f
        const val ALPHA_DESCRIPTION = 0.7f
        const val ALPHA_PLACEHOLDER = 0.6f
        const val ALPHA_ICON = 0.6f
        const val ALPHA_BORDER = 0.12f
    }
} 
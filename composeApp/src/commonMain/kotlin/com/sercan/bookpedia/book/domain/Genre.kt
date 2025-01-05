package com.sercan.bookpedia.book.domain

enum class Genre {
    ART,
    CRIME,
    DRAMA,
    DESIGN,
    ACTION_ADVENTURE,
    HORROR,
    ROMANCE,
    FANTASY,
    FICTION,
    SCI_FI,
    SPACE,
    CHILDREN,
    BIOGRAPHY,
    HISTORY,
    TRUE_CRIME,
    DYSTOPIAN,
    THRILLER,
    COMIC,
    BUSINESS,
    SCIENCE,
    PHOTOGRAPHY,
    FOOD,
    RELIGION,
    POLITICS,
    HEALTH_FITNESS;

    fun toDisplayName(): String {
        return when (this) {
            ACTION_ADVENTURE -> "Action & Adventure"
            TRUE_CRIME -> "True crime"
            HEALTH_FITNESS -> "Health & Fitness"
            SCI_FI -> "Sci-fi"
            else -> name.lowercase().replaceFirstChar { it.uppercase() }
        }
    }
} 
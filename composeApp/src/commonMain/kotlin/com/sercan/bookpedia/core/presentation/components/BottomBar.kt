package com.sercan.bookpedia.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sercan.bookpedia.core.navigation.Route

@Composable
fun BottomBar(
    currentRoute: Route,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            selected = currentRoute is Route.Home,
            onClick = { onNavigate(Route.Home) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute is Route.Search,
            onClick = { onNavigate(Route.Search) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            label = { Text("Search") }
        )
        NavigationBarItem(
            selected = currentRoute is Route.Favorites,
            onClick = { onNavigate(Route.Favorites) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites"
                )
            },
            label = { Text("Favorites") }
        )
    }
} 
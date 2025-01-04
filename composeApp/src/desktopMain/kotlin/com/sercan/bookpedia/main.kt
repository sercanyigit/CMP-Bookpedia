package com.sercan.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sercan.bookpedia.app.App
import com.sercan.bookpedia.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP-Bookpedia",
        ) {
            App()
        }
    }
}
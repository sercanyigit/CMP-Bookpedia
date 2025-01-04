package com.sercan.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.sercan.bookpedia.app.App
import com.sercan.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
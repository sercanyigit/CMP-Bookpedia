# 📚 Bookpedia - CMP Book Discovery App

Bookpedia is a modern, cross-platform book discovery application built with Compose Multiplatform (CMP). It allows users to explore, search their favorite books across Android, iOS, and Desktop platforms.


https://github.com/user-attachments/assets/0dcba1d4-4aab-47dd-8e86-4d7e215b23b2


## ✨ Features

- 🌓 Dynamic theme switching (Light/Dark mode) with persistent settings
- 📱 Cross-platform support (Android, iOS, Desktop)
- 🔍 Book search functionality
- ❤️ Favorite books management
- 📚 Trending books discovery
- 🎯 Book recommendations
- 🖼️ Beautiful and responsive UI with Jetpack Compose
- 💾 Local data persistence
- 🌐 Real-time book data fetching

## 🛠️ Tech Stack

- **Kotlin Multiplatform** - For sharing code across platforms
- **Jetpack Compose** - For building the UI
- **Koin** - For dependency injection
- **Ktor** - For network requests
- **SQLDelight** - For local database
- **DataStore** - For preferences management
- **Coil** - For image loading
- **Kotlin Coroutines & Flow** - For asynchronous operations

## 🏗️ Architecture

The project follows Clean Architecture principles and is organized into the following layers:

- **Presentation** - UI components, ViewModels, and UI state management
- **Domain** - Business logic and use cases
- **Data** - Repositories, data sources, and models
- **Core** - Common utilities and base components

## 🚀 Prerequisites

- Android Studio Arctic Fox or later
- Xcode 13 or later (for iOS)
- JDK 11 or later
- Kotlin 1.9.0 or later


## 📱 Platform-Specific Details

### Android
- Minimum SDK: 24
- Target SDK: 34
- Uses Material3 design components

### iOS
- Minimum iOS version: 14.0
- Uses SwiftUI integration with Compose

### Desktop
- Supports Windows, macOS, and Linux

### Desktop Distribution and Packaging
- Mac      -> ./gradlew packageDmg
- Windows  -> ./gradlew packageMsi
- Linux    -> ./gradlew packageDeb

## 📞 Contact

Sercan Yiğit - [@Linkedin](https://www.linkedin.com/in/sercan-yi%C4%9Fit-993b531b5/)


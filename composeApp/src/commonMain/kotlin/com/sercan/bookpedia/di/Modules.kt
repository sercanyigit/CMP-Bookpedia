package com.sercan.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.sercan.bookpedia.book.data.database.DatabaseFactory
import com.sercan.bookpedia.book.data.database.FavoriteBookDatabase
import com.sercan.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.sercan.bookpedia.book.data.network.RemoteBookDataSource
import com.sercan.bookpedia.book.data.repository.DefaultBookRepository
import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.book.domain.usecase.GetBookDescriptionUseCase
import com.sercan.bookpedia.book.domain.usecase.GetFavoriteBooksUseCase
import com.sercan.bookpedia.book.domain.usecase.GetTrendingBooksUseCase
import com.sercan.bookpedia.book.domain.usecase.IsBookFavoriteUseCase
import com.sercan.bookpedia.book.domain.usecase.SearchBooksUseCase
import com.sercan.bookpedia.book.domain.usecase.ToggleFavoriteUseCase
import com.sercan.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.sercan.bookpedia.book.presentation.book_list.BookListViewModel
import com.sercan.bookpedia.book.presentation.favorites.FavoritesViewModel
import com.sercan.bookpedia.book.presentation.onboarding.OnboardingViewModel
import com.sercan.bookpedia.book.presentation.search.SearchViewModel
import com.sercan.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    // Use Cases
    single { GetTrendingBooksUseCase(get()) }
    single { SearchBooksUseCase(get()) }
    single { GetFavoriteBooksUseCase(get()) }
    single { ToggleFavoriteUseCase(get()) }
    single { GetBookDescriptionUseCase(get()) }
    single { IsBookFavoriteUseCase(get()) }

    // ViewModels
    viewModel { 
        BookListViewModel(
            getTrendingBooksUseCase = get(),
            toggleFavoriteUseCase = get(),
            dataStoreProvider = get()
        )
    }
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::OnboardingViewModel)
}
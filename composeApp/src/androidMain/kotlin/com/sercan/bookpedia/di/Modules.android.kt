package com.sercan.bookpedia.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sercan.bookpedia.book.data.database.DatabaseFactory
import com.sercan.bookpedia.core.data.preferences.DataStoreProvider
import com.sercan.bookpedia.core.presentation.utils.Constants.Preferences.PREFERENCES_APP
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { DatabaseFactory(androidApplication()) }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().preferencesDataStoreFile(PREFERENCES_APP) }
        )
    }
    single { DataStoreProvider(get()) }
}
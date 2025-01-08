package com.sercan.bookpedia.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.sercan.bookpedia.book.data.database.DatabaseFactory
import com.sercan.bookpedia.core.data.preferences.DataStoreProvider
import com.sercan.bookpedia.core.presentation.utils.Constants.Preferences.PREFERENCES_APP
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    single { DatabaseFactory() }
    single<DataStore<Preferences>> {
        val documentsPath = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { (documentsPath + "/$PREFERENCES_APP").toPath() }
        )
    }
    single { DataStoreProvider(get()) }
}
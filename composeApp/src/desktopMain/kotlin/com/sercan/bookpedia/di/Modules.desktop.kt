package com.sercan.bookpedia.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.sercan.bookpedia.book.data.database.DatabaseFactory
import com.sercan.bookpedia.core.data.preferences.DataStoreProvider
import com.sercan.bookpedia.core.presentation.utils.Constants.Preferences.PREFERENCES_APP
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { DatabaseFactory() }
    single<DataStore<Preferences>> {
        val dir = File(System.getProperty("user.home") + "/.bookpedia")
        if (!dir.exists()) dir.mkdirs()
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { (dir.absolutePath + "/$PREFERENCES_APP").toPath() }
        )
    }
    single { DataStoreProvider(get()) }
}
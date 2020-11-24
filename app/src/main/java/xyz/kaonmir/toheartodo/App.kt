package xyz.kaonmir.toheartodo

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.kaonmir.toheartodo.data.BookDataSource
import xyz.kaonmir.toheartodo.data.BookRepository

class App : Application() {
    init {
        startKoin {
            androidLogger()
            modules(module {
                single { BookRepository(BookDataSource()) }

            })
        }

        Log.i("Koanmir", "App start")
    }
}

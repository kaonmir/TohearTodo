package xyz.kaonmir.toheartodo

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.kaonmir.toheartodo.data.BookDataSource
import xyz.kaonmir.toheartodo.data.BookRepository
import xyz.kaonmir.toheartodo.data.ItemDataSource
import xyz.kaonmir.toheartodo.data.ItemRepository

class App : Application() {
    init {
        startKoin {
            androidLogger()
            modules(module {
                single { BookRepository(BookDataSource(this@App)) }
                single { ItemRepository(ItemDataSource(this@App)) }

            })
        }

        Log.i("Koanmir", "App start")
    }
}

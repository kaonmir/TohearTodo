package xyz.kaonmir.toheartodo.view.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.view.intro.login.LoginActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handlerThread = HandlerThread("Sunghun")
        handlerThread.start()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val intent = Intent(this, LoginActivity::class.java).apply{}
            startActivity(intent)

            finish()
        }, 500)
    }
}
package xyz.kaonmir.toheartodo.view.intro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import xyz.kaonmir.toheartodo.R
import kotlin.concurrent.timer

class SplashActivity : AppCompatActivity() {
//    companion object {
//        const val PERMISSION_REQUEST_CODE = 1001
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        // Record audio permission check and request
//        if(checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), PERMISSION_REQUEST_CODE)
//        }

        timer(period = 500) {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java).apply{}
            startActivity(intent)
            finish()
            this.cancel()
        }
    }
}
package xyz.kaonmir.toheartodo.view.hearmode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.koin.android.ext.android.inject
import org.w3c.dom.Text
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.data.ItemRepository
import xyz.kaonmir.toheartodo.data.model.Item
import java.util.*

class HearActivity : AppCompatActivity() {

    private var mark = 0
    private lateinit var dataset: List<Item>
    private lateinit var textItem: TextView

    private val itemRepository: ItemRepository by inject()
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hear)
        supportActionBar?.hide()

        // Set TTS
        textToSpeech = TextToSpeech(this) { status ->
            if(status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale.KOREA)

                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this@HearActivity, "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    textToSpeech.setPitch(0.7f)
                    textToSpeech.setSpeechRate(1.2f)
                }
            }
        }

        // Get dataset
        val bid = intent.getIntExtra("bid", 0)
        dataset = itemRepository.getItemsNotDone(bid)

        textItem = findViewById(R.id.textView_item)
        textItem.text = dataset[0].text

        findViewById<ImageButton>(R.id.button_left).setOnClickListener {
            if(mark > 1) mark--
            refreshText()
            speak()
        }

        findViewById<ImageButton>(R.id.button_right).setOnClickListener {
            if(mark < dataset.size - 1) mark++
            refreshText()
            speak()
        }

        findViewById<Button>(R.id.button_end).setOnClickListener { finish() }
    }

    override fun onStop() {
        super.onStop()
        textToSpeech.let {
            it.stop()
            it.shutdown()
        }
    }

    private fun refreshText() {
        textItem.text = dataset[mark].text
    }

    private fun speak() {
        textToSpeech.speak(dataset[mark].text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
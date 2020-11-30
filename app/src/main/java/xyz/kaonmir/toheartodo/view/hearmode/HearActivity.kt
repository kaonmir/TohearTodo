package xyz.kaonmir.toheartodo.view.hearmode

import android.R.id.message
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.channels.ticker
import org.koin.android.ext.android.inject
import xyz.kaonmir.toheartodo.R
import xyz.kaonmir.toheartodo.data.ItemRepository
import xyz.kaonmir.toheartodo.data.model.Item
import xyz.kaonmir.toheartodo.view.intro.LoginActivity
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask


class HearActivity : AppCompatActivity() {
    companion object {
        const val TAG = "HearActivity"
        const val SPEECH_LIMIT_TIME = 3000L
    }

    private var mark = 0
    private lateinit var dataset: List<Item>
    private lateinit var textItem: TextView

    private val itemRepository: ItemRepository by inject()
    private lateinit var textToSpeech: TextToSpeech // For TTS
    private lateinit var speechRecognizer: SpeechRecognizer // For STT
    private val speechIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    private lateinit var listener: MyRecognitionListener

    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hear)
        supportActionBar?.hide()

        // Set STT
        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

        listener = MyRecognitionListener()

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(listener)
        startListening()

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
        val bid = this.intent.getIntExtra("bid", 0)
        dataset = itemRepository.getItemsNotDone(bid)

        textItem = findViewById(R.id.textView_item)
        textItem.text = dataset[0].text

        findViewById<ImageButton>(R.id.button_left).setOnClickListener { prevItem() }
        findViewById<ImageButton>(R.id.button_right).setOnClickListener { nextItem() }
        findViewById<Button>(R.id.button_end).setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        textToSpeech.let {
            it.stop()
            it.shutdown()
        }
        speechRecognizer.let {
            it.destroy()
            it.cancel()
        }
    }

    private fun startListening() {
        speechRecognizer.cancel()
        speechRecognizer.stopListening()
        speechRecognizer.startListening(speechIntent)

        timer = timer(period = SPEECH_LIMIT_TIME) {
            speechRecognizer.cancel()
            speechRecognizer.stopListening()
            startListening()
            this.cancel()
        }
    }

    private fun refreshText() {
        textItem.text = dataset[mark].text
    }

    private fun speak() {
        textToSpeech.speak(dataset[mark].text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun prevItem() {
        if(mark > 0) mark--
        refreshText()
        speak()
    }

    private fun nextItem() {
        if(mark < dataset.size - 1) mark++
        refreshText()
        speak()
    }

    inner class MyRecognitionListener: RecognitionListener {
        private val NEXT = resources.getString(R.string.next)
        private val PREVIOUS = resources.getString(R.string.previous)
        private val AGAIN = resources.getString(R.string.again)
        private val DONE = resources.getString(R.string.done)
        private val FINISH = resources.getString(R.string.finish)

        override fun onReadyForSpeech(params: Bundle?) {
            Log.i(TAG, "음성인식을 시작합니다.")
        }
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onError(error: Int) {
            val message: String = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트웍 타임아웃"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER가 바쁨"
                SpeechRecognizer.ERROR_SERVER -> "서버가 이상함"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                else -> "알 수 없는 오류임"
            }
            Log.i(TAG, message)
            startListening()
        }

        override fun onResults(results: Bundle?) {

            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (matches != null) {
                val message = matches.joinToString()
                Log.i(TAG, message)

                when (message) {
                    NEXT -> nextItem()
                    PREVIOUS -> prevItem()
                    AGAIN -> speak()
                    DONE -> TODO("remove from this not-done array")
                    FINISH -> this@HearActivity.finish()
                }
                when (message) {
                    FINISH -> {}
                    else -> startListening()
                }
            } else {
                Log.i(TAG, "아무것도 인식되지 않았습니다.")
                startListening()
            }
        }
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }
}
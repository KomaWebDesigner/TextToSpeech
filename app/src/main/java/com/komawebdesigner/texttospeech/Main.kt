package com.komawebdesigner.texttospeech

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.main.*
import java.util.*

class Main : AppCompatActivity(), TextToSpeech.OnInitListener {
    /**
     * Called to signal the completion of the TextToSpeech engine initialization.
     *
     * @param status [TextToSpeech.SUCCESS] or [TextToSpeech.ERROR].
     */


    private var tts: TextToSpeech? = null
    private var btnSpeak: Button? = null
    private var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        btnSpeak = this.btn_Speak
        editText = this.edit_Text

        btnSpeak!!.isEnabled = false
        tts = TextToSpeech(this, this)

        btnSpeak!!.setOnClickListener { speakOut() }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported")
            } else {
                btnSpeak!!.isEnabled = true
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    private fun speakOut() {
        val text = editText!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}

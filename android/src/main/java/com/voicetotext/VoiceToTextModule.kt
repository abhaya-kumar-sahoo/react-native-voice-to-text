package com.voicetotext

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import com.facebook.react.bridge.UiThreadUtil

import androidx.annotation.NonNull
import androidx.core.content.ContextCompat

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

import java.util.ArrayList
import java.util.Locale
import androidx.core.app.ActivityCompat
import android.Manifest


@ReactModule(name = VoiceToTextModule.NAME)
class VoiceToTextModule(reactContext: ReactApplicationContext) :
  NativeVoiceToTextSpec(reactContext) {
    // Store reactContext as a property to use it later
    private val mReactContext: ReactApplicationContext = reactContext
    private var speechRecognizer: SpeechRecognizer? = null
    private var permissionPromise: Promise? = null
    private val REQUEST_RECORD_AUDIO_PERMISSION = 1001
    
  override fun getName(): String {
    return NAME
  }



  @ReactMethod
  override fun startSpeechToText(promise: Promise) {

  val currentActivity = getCurrentActivity()
  if (currentActivity == null) {
      promise.reject("ERROR", "Activity is null")
      return
  }

  if (ContextCompat.checkSelfPermission(mReactContext, Manifest.permission.RECORD_AUDIO)
      != PackageManager.PERMISSION_GRANTED) {
      
      ActivityCompat.requestPermissions(
          currentActivity,
          arrayOf(Manifest.permission.RECORD_AUDIO),
          REQUEST_RECORD_AUDIO_PERMISSION
      )
      permissionPromise = promise // Save promise for later resolution
      // promise.reject("PERMISSION_BLOCKED", "Microphone permission is permanently denied. Please enable it in settings.")

      return
  }



        UiThreadUtil.runOnUiThread {
            if (speechRecognizer != null) {
                speechRecognizer?.destroy()
                speechRecognizer = null
            }

            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(mReactContext)

            val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            }

            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray) {}

                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {
                    val message = when (error) {
                        SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                        SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                        else -> "Unknown error"
                    }
                    promise.reject("ERROR", message)
                    releaseSpeechRecognizer()
                }

                override fun onResults(results: Bundle?) {
                    val result = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (result != null && result.isNotEmpty()) {
                        promise.resolve(result[0])
                    } else {
                        promise.reject("NO_RESULTS", "No speech results")
                    }
                    releaseSpeechRecognizer()
                }

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })

            speechRecognizer?.startListening(speechRecognizerIntent)
        }
    }

    private fun releaseSpeechRecognizer() {
        speechRecognizer?.destroy()
        speechRecognizer = null
    }


  companion object {
    const val NAME = "VoiceToText"
  }
}

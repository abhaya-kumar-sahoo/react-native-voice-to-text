package com.voicetotext;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import java.util.ArrayList;

public class VoiceToTextModule extends VoiceToTextSpec {
  public static final String NAME = "VoiceToText";
  private SpeechRecognizer speechRecognizer;
  private Promise speechPromise;
  VoiceToTextModule(ReactApplicationContext context) {
    super(context);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


    @ReactMethod
    public void startRecognition(String language, Promise promise) {
        this.speechPromise = promise;

        // Ensure that the SpeechRecognizer is created on the main thread
        getReactApplicationContext().runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getReactApplicationContext());

                speechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle params) {}

                    @Override
                    public void onBeginningOfSpeech() {}

                    @Override
                    public void onRmsChanged(float rmsdB) {}

                    @Override
                    public void onBufferReceived(byte[] buffer) {}

                    @Override
                    public void onEndOfSpeech() {}

                    @Override
                    public void onError(int error) {
                        Log.e("SpeechToTextModule", "Speech recognition error: " + error);
                        speechPromise.reject("SpeechToTextError", "Speech recognition error" + error);
                    }

                    @Override
                    public void onResults(Bundle results) {
                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        if (matches != null && !matches.isEmpty()) {
                            WritableMap resultMap = Arguments.createMap();
                            resultMap.putString("text", matches.get(0));
                            speechPromise.resolve(resultMap);
                        } else {
                            speechPromise.reject("SpeechToTextError", "No speech input recognized");
                        }
                    }

                    @Override
                    public void onPartialResults(Bundle partialResults) {}

                    @Override
                    public void onEvent(int eventType, Bundle params) {}
                });

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language); // defines in which you want result like for english - ("en-IN")  
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something");
                speechRecognizer.startListening(intent);
            }
        });
    }

    @ReactMethod
    public void stopRecognition() {
        if (speechRecognizer != null) {
            // Ensure that the SpeechRecognizer is stopped on the main thread
            getReactApplicationContext().runOnUiQueueThread(new Runnable() {
                @Override
                public void run() {
                    speechRecognizer.stopListening();
                    speechRecognizer.destroy();
                }
            });
        }
    }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void multiply(double a, double b, Promise promise) {
    promise.resolve(a * b);
  }
}

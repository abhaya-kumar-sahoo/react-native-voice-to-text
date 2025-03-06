#include <jni.h>
#include "react-native-voice-to-text.h"

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_voicetotext_VoiceToTextModule_nativeMultiply(JNIEnv *env, jclass type, jdouble a, jdouble b) {
    return voicetotext::multiply(a, b);
}

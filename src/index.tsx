import VoiceToText from './NativeVoiceToText';

export const startSpeechToText = () => {
  return new Promise((resolve, reject) => {
    VoiceToText.startSpeechToText()
      .then((result: any) => resolve(result))
      .catch((error: any) => reject(error));
  });
};

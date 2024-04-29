import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-voice-to-text' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const VoiceToTextModule = isTurboModuleEnabled
  ? require('./NativeVoiceToText').default
  : NativeModules.VoiceToText;

const VoiceToText = VoiceToTextModule
  ? VoiceToTextModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function multiply(a: number, b: number): Promise<number> {
  return new Promise((resolve, reject) => {
    if (Platform.OS === 'android') {
      VoiceToText.multiply(a, b)
        .then((result: any) => resolve(result))
        .catch((error: any) => reject(error));
    } else {
      reject({ message: 'IOS is not supported' });
    }
  });
}

export const startRecognition = async (language: string): Promise<any> => {
  return new Promise((resolve, reject) => {
    if (Platform.OS === 'android') {
      VoiceToText.startRecognition(language)
        .then((result: any) => resolve(result))
        .catch((error: any) => reject(error));
    } else {
      reject({ message: 'IOS is not supported' });
    }
  });
};

export const stopRecognition = async (): Promise<any> => {
  return new Promise((resolve, reject) => {
    if (Platform.OS === 'android') {
      VoiceToText.stopRecognition()
        .then((result: any) => resolve(result))
        .catch((error: any) => reject(error));
    } else {
      reject({ message: 'IOS is not supported' });
    }
  });
};

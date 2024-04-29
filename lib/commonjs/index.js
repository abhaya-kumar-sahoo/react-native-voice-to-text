"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.multiply = multiply;
exports.stopRecognition = exports.startRecognition = void 0;
var _reactNative = require("react-native");
const LINKING_ERROR = `The package 'react-native-voice-to-text' doesn't seem to be linked. Make sure: \n\n` + _reactNative.Platform.select({
  ios: "- You have run 'pod install'\n",
  default: ''
}) + '- You rebuilt the app after installing the package\n' + '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;
const VoiceToTextModule = isTurboModuleEnabled ? require('./NativeVoiceToText').default : _reactNative.NativeModules.VoiceToText;
const VoiceToText = VoiceToTextModule ? VoiceToTextModule : new Proxy({}, {
  get() {
    throw new Error(LINKING_ERROR);
  }
});
function multiply(a, b) {
  return new Promise((resolve, reject) => {
    if (_reactNative.Platform.OS === 'android') {
      VoiceToText.multiply(a, b).then(result => resolve(result)).catch(error => reject(error));
    } else {
      reject({
        message: 'IOS is not supported'
      });
    }
  });
}
const startRecognition = async language => {
  return new Promise((resolve, reject) => {
    if (_reactNative.Platform.OS === 'android') {
      VoiceToText.startRecognition(language).then(result => resolve(result)).catch(error => reject(error));
    } else {
      reject({
        message: 'IOS is not supported'
      });
    }
  });
};
exports.startRecognition = startRecognition;
const stopRecognition = async () => {
  return new Promise((resolve, reject) => {
    if (_reactNative.Platform.OS === 'android') {
      VoiceToText.stopRecognition().then(result => resolve(result)).catch(error => reject(error));
    } else {
      reject({
        message: 'IOS is not supported'
      });
    }
  });
};
exports.stopRecognition = stopRecognition;
//# sourceMappingURL=index.js.map
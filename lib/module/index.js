"use strict";

import VoiceToText from "./NativeVoiceToText.js";
export const startSpeechToText = () => {
  return new Promise((resolve, reject) => {
    VoiceToText.startSpeechToText().then(result => resolve(result)).catch(error => reject(error));
  });
};
//# sourceMappingURL=index.js.map
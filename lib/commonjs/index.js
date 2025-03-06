"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.startSpeechToText = void 0;
var _NativeVoiceToText = _interopRequireDefault(require("./NativeVoiceToText.js"));
function _interopRequireDefault(e) { return e && e.__esModule ? e : { default: e }; }
const startSpeechToText = () => {
  return new Promise((resolve, reject) => {
    _NativeVoiceToText.default.startSpeechToText().then(result => resolve(result)).catch(error => reject(error));
  });
};
exports.startSpeechToText = startSpeechToText;
//# sourceMappingURL=index.js.map
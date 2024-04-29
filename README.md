# react-native-voice-to-text

React Native Voice-to-Text is a module that facilitates the conversion of spoken words into text within your React Native mobile applications. With this module, users can dictate text input, enabling hands-free interaction and enhancing accessibility. Integrate voice recognition and transcription capabilities seamlessly into your app to provide a convenient and intuitive user experience.


## Installation

Install the package via npm:

```bash
npm install react-native-voice-to-text
```

Install the package via yarn:

```bash
yarn add react-native-voice-to-text
```

## Android Setup

To ensure proper functionality on Android, you need to add the following permission to your AndroidManifest.xml file:

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

This permission allows the app to record audio, which is essential for voice recognition.


## Usage

Import the functions provided by the package and use them in your React Native components:

```javascript
import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import { startRecognition, stopRecognition } from 'react-native-voice-to-text';


export default function App() {
  const [language, setLanguage] = React.useState('en-IN');

  async function startSpeechToText() {
    console.log('start speach recogination');
    try {
      const result = await startRecognition(language);
      console.log('Speech recognized: ', result);
    } catch (error) {
      console.error(error);
    }
  }
  function stopSpeechToText() {
    stopRecognition();
  }

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.box} onPress={startSpeechToText}>
        <Text style={styles.text}>Start Speech-to-Text</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={stopSpeechToText}>
        <Text style={styles.text}>STOP Speech-to-Text</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'black',
  },
  text: {
    color: 'white',
  },
  box: {
    marginVertical: 20,
  },
});

```

## API

### `startRecognition(language: string): Promise<any>`

Starts voice recognition with the specified language.

- `language`: The language code (e.g., "en-US").

### `stopRecognition(): Promise<any>`

Stops the ongoing voice recognition process.

## Platform Support

- Android: Supported
- iOS: Not supported

## License

This project is licensed under the [MIT License](LICENSE).

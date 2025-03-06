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

Install the package via bun:

```bash
bun add react-native-voice-to-text
```

## Android Setup

To ensure proper functionality on Android, you need to add the following permission to your AndroidManifest.xml file:

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

This permission allows the app to record audio, which is essential for voice recognition.


## Usage

Import the functions provided by the package and use them in your React Native components:

```jsx
import { useState } from 'react';
import { Text, View, StyleSheet, Button } from 'react-native';
import { startSpeechToText } from 'react-native-voice-to-text';

export default function App() {
  const [text, setText] = useState<any>('');
  return (
    <View style={styles.container}>
      <Text style={{ color: 'white', fontWeight: 'bold' }}>Result: {text}</Text>

      <Button
        title="Mic check"
        color={'#ace10d'}
        onPress={async () => {
          try {
            const audioText = await startSpeechToText();
            console.log('audioText:', { audioText });
            setText(audioText);
          } catch (error) {
            console.log({ error });
          }
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#04ff0028',
  },
});


```

## API

### `startSpeechToText(): Promise<any>`

Starts voice recognition with the English language.

## Platform Support

- Android: Supported (React Native version above 0.78.0)

- iOS: Not supported

## License

This project is licensed under the [MIT License](LICENSE).

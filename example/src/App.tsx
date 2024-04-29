import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import { startRecognition, stopRecognition } from 'react-native-voice-to-text';

// const SpeechToText = NativeModules.SpeechToText;

export default function App() {
  const [language, setLanguage] = React.useState('en-IN');

  async function startSpeechToText() {
    console.log('start speach recogination');
    try {
      const result = await startRecognition(language); // <-- pass language here , in which you want the output.
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

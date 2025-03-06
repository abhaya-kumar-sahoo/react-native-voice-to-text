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

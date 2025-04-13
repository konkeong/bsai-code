package ch04.service;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService {

    protected final OpenAiAudioSpeechModel speechModel;

    @Autowired
    public TextToSpeechService(OpenAiAudioSpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    public OpenAiAudioSpeechOptions buildOptions() {
        return OpenAiAudioSpeechOptions.builder()
                .build();
    }

    public byte[] synthesize(@NonNull String text, @NonNull OpenAiAudioSpeechOptions options) {
        SpeechPrompt prompt = new SpeechPrompt(text, options);
        SpeechResponse response = speechModel.call(prompt);
        return response.getResult().getOutput();
    }

    public byte[] synthesize(String text) {
        return synthesize(text, buildOptions());
    }

}

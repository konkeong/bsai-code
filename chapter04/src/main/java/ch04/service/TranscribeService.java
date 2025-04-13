package ch04.service;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TranscribeService {

    protected final OpenAiAudioTranscriptionModel transcriptionModel;

    @Autowired
    public TranscribeService(OpenAiAudioTranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
    }

    public OpenAiAudioTranscriptionOptions buildOptions() {
        return OpenAiAudioTranscriptionOptions.builder()
                .build();
    }

    public AudioTranscriptionResponse transcribe(@NonNull Resource audioResource, @NonNull OpenAiAudioTranscriptionOptions options) {
        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(audioResource, options);
        return transcriptionModel.call(prompt);
    }

    public AudioTranscriptionResponse transcribe(@NonNull Resource audioResource) {
        return transcribe(audioResource, buildOptions());
    }

}

package ch04.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiAudioApi.SpeechRequest.AudioResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ch03.service.UpdateChatService;
import common.Util;

@Service
public class VoiceAssistantService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    protected final UpdateChatService updateChatService;

    protected final TranscribeService transcribeService;

    protected final TextToSpeechService textToSpeechService;

    @Autowired
    public VoiceAssistantService(
            UpdateChatService updateChatService,
            TranscribeService transcribeService,
            TextToSpeechService textToSpeechService
    ) {
        this.updateChatService = updateChatService;
        this.transcribeService = transcribeService;
        this.textToSpeechService = textToSpeechService;
    }

    public byte[] issueCommand(Resource audioResource) {
        AudioTranscriptionResponse response = transcribeService.transcribe(audioResource);

        String output = response.getResult().getOutput();
        log.info("response from audio transcription: {}", output);

        List<Generation> updateResponse = updateChatService.converse(List.of(new UserMessage(output)));
        Util.display("AudioCommand", null, updateResponse);

        return textToSpeechService.synthesize(updateResponse.getFirst().getOutput().getText());
    }

    public byte[] issueCommand(String commandText) {
        log.info("AudioCommand: {}", commandText);
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .model(OpenAiAudioApi.TtsModel.TTS_1.value)
                .voice(OpenAiAudioApi.SpeechRequest.Voice.SHIMMER)
                .speed(0.5f)
                .responseFormat(AudioResponseFormat.MP3)
                .build();
        byte[] responseBytes = textToSpeechService.synthesize(commandText, options);
        return issueCommand(new ByteArrayResource(responseBytes));
    }

}

package ch04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ch04.service.TranscribeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TranscribeTests {

    @Autowired
    protected TranscribeService transcribeService;

    @Test
    public void testTranscribeQuery() {
        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .build();

        // Invalid file format. Supported formats: ['flac', 'm4a', 'mp3', 'mp4', 'mpeg', 'mpga', 'oga', 'ogg', 'wav', 'webm']
        Resource songResource = new ClassPathResource("happy_birthday.mp3");
        AudioTranscriptionResponse response = transcribeService.transcribe(songResource, options);
        String lyric = response.getResult().getOutput();
        System.out.println("response: " + lyric);
        Assertions.assertTrue(lyric.toLowerCase().contains("happy birthday to you"));
    }

}

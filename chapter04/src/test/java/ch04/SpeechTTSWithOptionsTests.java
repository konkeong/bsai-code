package ch04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiAudioApi.SpeechRequest.AudioResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch04.service.TextToSpeechService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SpeechTTSWithOptionsTests {

    @Autowired
    protected TextToSpeechService textToSpeechService;

    @Test
    public void runTTSQuery() throws IOException {
        String lyric = """
                Happy birthday to you
                Happy birthday to you
                Happy birthday, happy birthday
                Happy birthday to you
                """;

        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .model(OpenAiAudioApi.TtsModel.TTS_1.value)
                .voice(OpenAiAudioApi.SpeechRequest.Voice.SHIMMER)
                .speed(0.5f)
                .responseFormat(AudioResponseFormat.MP3)
                .build();

        byte[] responseAsBytes = textToSpeechService.synthesize(lyric, options);
        Assertions.assertNotNull(responseAsBytes);

        Path path = Paths.get("./target/happy_birthday.mp3");
        System.out.println("generated " + path.toAbsolutePath().toString());
        Files.write(path, responseAsBytes);
    }

}

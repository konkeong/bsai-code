package ch04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ch04.service.VoiceAssistantService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VoiceAssistantTests {

    @Autowired
    protected VoiceAssistantService voiceAssistantService;

    // @Test
    public void testVoiceAssistantCommandTurnOnYellowLight() throws IOException {
        Resource commandResource = new ClassPathResource("vacmd_turn_on_yellow_light.mp3");
        byte[] audioCommandBytes = commandResource.getContentAsByteArray();
        byte[] audioResponseBytes = voiceAssistantService.issueCommand(new ByteArrayResource(audioCommandBytes));
        Assertions.assertNotNull(audioResponseBytes);
        Path path = Paths.get("./target/vares_turn_on_yellow_light.mp3");
        Files.write(path, audioResponseBytes);
        System.out.println("generated " + path.toAbsolutePath().toString());
    }

    @Test
    public void testVoiceAssistantCommandTurnOnBlueLight() throws IOException {
        byte[] audioResponseBytes = voiceAssistantService.issueCommand("Turn on blue light.");
        Assertions.assertNotNull(audioResponseBytes);
        Path path = Paths.get("./target/vares_turn_on_blue_light.mp3");
        Files.write(path, audioResponseBytes);
        System.out.println("generated " + path.toAbsolutePath().toString());
    }

}

package ch05;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.image.Image;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.MimeType;

import ch03.service.UpdateChatService;
import ch05.service.ImageGeneratorService;
import ch05.service.ImageRecognitionService;
import common.Util;

@SpringBootTest
public class LightVisualizerTests {

    public static final MimeType IMAGE_WEBP = new MimeType("image", "webp");

    @Autowired
    protected ImageGeneratorService imageGeneratorService;

    @Autowired
    protected ImageRecognitionService imageRecognitionService;

    @Autowired
    protected UpdateChatService updateChatService;

    public String runCommand(String prompt) {
        List<Message> messages = List.of(new UserMessage(prompt));
        List<Generation> updateResponse = updateChatService.converse(messages);
        Util.display(prompt, messages, updateResponse);
        return updateResponse.getFirst().getOutput().getText();
    }

    public Media imageToMedia(Image image) {
        byte[] binaryData = Base64.getDecoder().decode(image.getB64Json());
        return new Media(IMAGE_WEBP, new ByteArrayResource(binaryData));
    }

    public void saveImage(Image image, String outFile) {
        byte[] binaryData = Base64.getDecoder().decode(image.getB64Json());
        try (ByteArrayInputStream bais = new ByteArrayInputStream(binaryData)) {
            BufferedImage bufferedImage = ImageIO.read(bais);
            Assertions.assertNotNull(bufferedImage, "bufferedImage is not null");
            WebpToPngConverter.convertWebpToPng(bufferedImage, outFile);
        } catch (IOException ex) {
            System.out.println("Failed to write file: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }

    @Test
    public void testLightVisualizer() {
        /*
         * We’ve got three lights that are possible in our system and in the following test:
         * 1. Ask the AI to create a prompt for DALL-E to generate an image of any lightbulb which is on.
         * 2. Pass that prompt to the ImageGeneratorService and retrieve the Image.
         * 3. Pass that Image back to the AI and ask it to explain what’s in the picture.
         * 4. Assert that at a minimum the explanation contains the word red since that’s the light that should be turned on.
         * 5. Run a command with OpenAI to turn off the red light, and turn on the green light.
         * 6. Re-run steps 1–3 above again.
         * 7. Assert that at a minimum the explanation contains the word green now
         *        since we turned off the red light and turned on the green in our command.
         */

        String prompt = "Create a single sentence prompt for DALL-E to generate an image of traffic lights, with lights of red, yellow, and green, in which the red light is on.";
        String dallePrompt = runCommand(prompt);
        System.out.println("DALL-E prompt: " + dallePrompt);

        Image firstImage = imageGeneratorService.processPrompt(dallePrompt);
        saveImage(firstImage, "./target/first_image.webp");
        Media firstMedia = imageToMedia(firstImage);

        String firstRecognition = imageRecognitionService.identify("In a single sentence explain what is in this picture?", firstMedia);
        System.out.println(firstRecognition);
        Assertions.assertTrue(firstRecognition.contains("red"));
        Assertions.assertTrue(firstRecognition.contains("green"));

        String updateResponse = runCommand("Turn off red light and turn on green light.");
        System.out.println(updateResponse);

        String secondPrompt = "Create a single sentence prompt for DALL-E to generate an image of " + updateResponse;
        String secondDallePrompt = runCommand(secondPrompt);
        System.out.println("DALL-E prompt: " + secondDallePrompt);

        Image secondImage = imageGeneratorService.processPrompt(secondDallePrompt);
        saveImage(secondImage, "./target/second_image.webp");
        Media secondMedia = imageToMedia(secondImage);

        String secondRecognition = imageRecognitionService.identify("In a single sentence explain what is in this picture?", secondMedia);
        System.out.println(secondRecognition);
    }

}

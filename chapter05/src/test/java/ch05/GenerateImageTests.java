package ch05;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch05.service.ImageGeneratorService;

@SpringBootTest
public class GenerateImageTests {

    @Autowired
    protected ImageGeneratorService imageGeneratorService;

    @Test
    public void testGenerateImage() {
        String prompt = "Can you create a fantasy styling rendering of flying dragon and breathing fire on the meadow field?";
        Image image = imageGeneratorService.processPrompt(prompt);
        Assertions.assertNotNull(image, "image is not null");

        System.out.println(image.getB64Json());

        byte[] binaryData = Base64.getDecoder().decode(image.getB64Json());
        try (ByteArrayInputStream bais = new ByteArrayInputStream(binaryData)) {
            BufferedImage bufferedImage = ImageIO.read(bais);
            Assertions.assertNotNull(bufferedImage, "bufferedImage is not null");
            WebpToPngConverter.convertWebpToPng(bufferedImage, "./target/dragon.png");
        } catch (IOException ex) {
            System.out.println("Failed to write file: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }

}

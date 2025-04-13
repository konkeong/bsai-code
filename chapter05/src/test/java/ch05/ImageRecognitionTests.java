package ch05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;

import ch05.service.ImageRecognitionService;

@SpringBootTest
public class ImageRecognitionTests {

    @Autowired
    protected ImageRecognitionService imageRecognitionService;

    @Test
    public void testImageRecognition() {
        Resource imageResource = new ClassPathResource("dragon.png");
        Media media = new Media(MimeTypeUtils.IMAGE_PNG, imageResource);
        String recognition = imageRecognitionService.identify("In a single sentence explain what is in this picture and identify every item in the picture", media);
        System.out.println(recognition);

        recognition = recognition.toLowerCase();
        Assertions.assertTrue(recognition.contains("dragon"));
        Assertions.assertTrue(recognition.contains("fire"));
        Assertions.assertTrue(recognition.contains("flower"));
    }

}

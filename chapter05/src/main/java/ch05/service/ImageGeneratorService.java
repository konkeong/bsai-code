package ch05.service;

import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageGeneratorService {

    protected final OpenAiImageModel imageModel;

    @Autowired
    public ImageGeneratorService(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public Image processPrompt(String prompt, OpenAiImageOptions options) {
        ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
        ImageResponse response = imageModel.call(imagePrompt);
        return response.getResult().getOutput();
    }

    public Image processPrompt(String prompt) {
        OpenAiImageOptions options = OpenAiImageOptions.builder()
                .width(1024)
                .height(1024)
                .build();
        return processPrompt(prompt, options);
    }

}

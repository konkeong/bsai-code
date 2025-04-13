package ch05.service;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageRecognitionService {

    protected final ChatModel chatModel;

    @Autowired
    public ImageRecognitionService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String identify(String prompt, Media media) {
        Message message = new UserMessage(prompt, media);
        ChatResponse response = chatModel.call(new Prompt(message));
        return response.getResult().getOutput().getText();
    }

}

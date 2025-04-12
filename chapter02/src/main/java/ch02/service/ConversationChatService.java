package ch02.service;

import java.util.Collections;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ConversationChatService extends OptionChatService{

    public ConversationChatService(ChatClient.Builder builder) {
        super(builder);
    }

    public List<Generation> converse(List<Message> messages, OpenAiChatOptions options) {
        Prompt prompt = new Prompt(messages, options);
        ChatClient.ChatClientRequestSpec requestSpec = client.prompt(prompt);
        ChatClient.CallResponseSpec responseSpec = requestSpec.call();
        ChatResponse chatResp = responseSpec.chatResponse();
        if (chatResp == null) {
            return Collections.emptyList();
        }
        return chatResp.getResults();
    }

}

package ch02.service;

import java.util.Objects;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class OptionChatService extends FirstChatService {

    public OptionChatService(ChatClient.Builder builder) {
        super(builder);
    }

    public String query(String query, OpenAiChatOptions options) {
        Objects.requireNonNull(query);
        Objects.requireNonNull(options);

        Prompt prompt = new Prompt(query, options);
        ChatClient.ChatClientRequestSpec requestSpec = client.prompt(prompt);
        requestSpec.options(options);
        ChatClient.CallResponseSpec responseSpec = requestSpec.call();
        return responseSpec.content();
    }

}

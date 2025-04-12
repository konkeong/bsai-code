package ch03.service;

import java.util.Collections;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class RequestChatService {

    protected final ChatClient client;

    @Autowired
    public RequestChatService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    public OpenAiChatOptions buildOptions() {
        return OpenAiChatOptions.builder()
                .toolNames("RequestLightStatusService")
                .build();
    }

    public List<Generation> converse(List<Message> messages) {
        Prompt prompt = new Prompt(messages, buildOptions());
        ChatClient.ChatClientRequestSpec requestSpec = client.prompt(prompt);
        ChatClient.CallResponseSpec responseSpec = requestSpec.call();
        ChatResponse chatResp = responseSpec.chatResponse();
        if (chatResp == null) {
            return Collections.emptyList();
        }
        return chatResp.getResults();
    }

}

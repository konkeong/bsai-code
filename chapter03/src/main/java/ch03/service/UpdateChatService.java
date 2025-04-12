package ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class UpdateChatService extends RequestChatService {

    public UpdateChatService(ChatClient.Builder builder) {
        super(builder);
    }

    @Override
    public OpenAiChatOptions buildOptions() {
        return OpenAiChatOptions.builder()
                .toolNames("RequestLightStatusService")
                .toolNames("ChangeLightStatusService")
                .build();
    }

}

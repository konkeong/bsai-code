package ch03.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateStructuredChatService {

    public record LightWithXYZ (String color, boolean on, Double x, Double y, Double z) { }

    public record LightWithXYZList (List<LightWithXYZ> lights) { }

    protected final ChatClient client;

    @Autowired
    public UpdateStructuredChatService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    public OpenAiChatOptions buildOptions() {
        return OpenAiChatOptions.builder()
                .toolNames("RequestLightStatusService")
                .toolNames("ChangeLightStatusService")
                .build();
    }

    public LightWithXYZList converse(List<Message> messages) {
        List<Message> localMessages = new ArrayList<>(messages);
        localMessages.add(new SystemMessage("Add the CIE 1931 color representation of each light if possible."));
        return client.prompt()
                .messages(localMessages)
                .options(buildOptions())
                .call()
                .entity(LightWithXYZList.class);
    }

}

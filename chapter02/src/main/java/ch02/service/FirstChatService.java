package ch02.service;

import java.util.Objects;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class FirstChatService {

    protected final ChatClient client;

    public FirstChatService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    public String query(String query) {
        Objects.requireNonNull(query);
        // return "The speed of a typical junk carrying tea in November in the South China Sea can vary depending on various factors such as the size of the junk, the wind conditions, and the currents. However, on average, junks can travel at speeds ranging from 5 to 10 knots (5.75 to 11.5 mph) in calm weather conditions.";
        // "The speed of a typical junk carrying tea in the South China Sea can vary based on several factors, including the design of the vessel, wind conditions, and currents. However, traditional Chinese junks, which are often used for cargo transport, typically have a cruising speed of around 4 to 6 knots (approximately 4.6 to 6.9 miles per hour or 7.4 to 11.1 kilometers per hour) under favorable conditions.In November, with clear weather and standard currents, a junk might be able to maintain a speed toward the higher end of that range, especially if the wind is favorable. Therefore, you could expect a typical junk to travel at around 5 to 7 knots under optimal conditions."

        Prompt prompt = new Prompt(query);
        ChatClient.ChatClientRequestSpec requestSpec = client.prompt(prompt);
        ChatClient.CallResponseSpec responseSpec = requestSpec.call();
        return responseSpec.content();
    }

}

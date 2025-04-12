package ch02;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch02.service.ConversationChatService;
import common.Util;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConversationTests {

    @Autowired
    protected ConversationChatService conversationChatService;

    /**
     * Extracts the `AssistantMessage` from the generated LLM output.
     *
     * @param output  the results of the call to the LLM
     *
     * @return  the first `AssistantMessage` in the output
     */
    private AssistantMessage getAssistantMessage(List<Generation> output) {
        return output.getFirst().getOutput();
    }

    /**
     * Wraps the content and dumps it to a logger.
     *
     * @param content  the content to display
     */
    private void display(String content) {
        String[] lines = WordUtils.wrap(content, 72, "\n", true)
                .split("\\n");
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("-----");
    }

    @Test
    @Order(1)
    public void simpleConversation() {
        String query = "What is the slope of y=x*1.2/z if z=2?";
        List<Message> messages = List.of(new UserMessage(query));
        OpenAiChatOptions options = OpenAiChatOptions.builder().build();
        List<Generation> output = conversationChatService.converse(messages, options);
        Util.display(query, messages, output);
        AssistantMessage first = getAssistantMessage(output);
        Assertions.assertTrue(first.getText().contains("0.6"));
    }

    @Test
    @Order(2)
    public void interactiveConversation() {
        String query = "What is the slope of y=x*1.2/z if z=2?";
        OpenAiChatOptions options = OpenAiChatOptions.builder().build();

        // We want to make a mutable list, because we're adding context.
        List<Message> messages = new ArrayList<>();
        messages.add(new UserMessage(query));

        List<Generation> conversation = conversationChatService.converse(messages, options);
        Util.display(query, messages, conversation);
        AssistantMessage first = getAssistantMessage(conversation);
        Assertions.assertTrue(first.getText().contains("0.6"));

        // We want to establish the context of the first answer.
        messages.add(first);

        // Now we want to add some extra context of our own
        messages.add(new UserMessage("And if z=3?"));
        List<Generation> conversation2 = conversationChatService.converse(messages, options);
        Util.display(query, messages, conversation2);
        AssistantMessage second = getAssistantMessage(conversation2);
        Assertions.assertTrue(second.getText().contains("0.4"));
    }

}

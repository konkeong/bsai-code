package ch03;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch03.service.RequestChatService;
import common.Util;

@SpringBootTest
public class RequestLightStatusTests extends BaseLightTests {

    @Autowired
    protected RequestChatService lightQueryService;

    @Test
    public void queryLightStatus() {
        String query = "Can you tell me the status of the lights named 'red' and 'purple'?";
        List<Message> messages = List.of(new UserMessage(query));
        List<Generation> responses = lightQueryService.converse(messages);
        Util.display(query, messages, responses);
        String content = Util.getFirstReply(responses);
        System.out.println("content: " + content);
        Assertions.assertTrue(content.contains("off"), "red is off");
    }

}

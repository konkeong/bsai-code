package ch03;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch03.service.UpdateChatService;
import common.Util;

@SpringBootTest
public class UpdateLightStatusTest extends BaseLightTests {

    @Autowired
    protected UpdateChatService lightConversationService;

    @Test
    public void changeLightStatus() {
        String query = "Turn the red light on. Then show the state of the yellow, red, and purple lights.";
        List<Message> messages = List.of(new UserMessage(query));
        List<Generation> responses = lightConversationService.converse(messages);
        Util.display(query, messages, responses);
        String content = Util.getFirstReply(responses);
        System.out.println("content: " + content);
        assertState("yellow", false);
        assertState("red", true);
    }

}

package ch02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch02.service.FirstChatService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class FirstTests {

    @Autowired
    protected FirstChatService firstChatService;

    @Test
    public void runSimpleQuery() {
        String query = "What is the speed of a typical junk carrying tea in November?\nAssume clear weather and standard currents in the South China Sea.";
        String response = firstChatService.query(query);
        System.out.println("response: " + response);
        Assertions.assertTrue(response.toLowerCase().contains("south china sea"));
    }

}

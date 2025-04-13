package ch02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch02.service.OptionChatService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OptionTests {

    @Autowired
    protected OptionChatService optionChatService;

    @Test
    public void runSimpleQuery() {
        String query = "What is the speed of a typical junk carrying tea in November?\nAssume clear weather and standard currents in the South China Sea.";
        /*
         * response :=
         * "The speed of a typical junk carrying tea in November in the South China Sea can vary depending on various factors such as wind conditions, currents, and the size and design of the junk. However, on average, junks in the South China Sea can sail at speeds ranging from 4 to 8 knots (4.6 to 9.2 miles per hour). It is important to note that this is just an estimate and actual speeds may vary."
         */
        String response = optionChatService.query(
                query,
                OpenAiChatOptions.builder()
                        .model("gpt-3.5-turbo")
                        .temperature(1.0)
                        .build());
        System.out.println("response: " + response);
        Assertions.assertTrue(response.toLowerCase().contains("south china sea"));
    }

}

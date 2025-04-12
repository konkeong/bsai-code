package ch03;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch03.service.UpdateStructuredChatService;

@SpringBootTest
public class UpdateLightStructuredTests extends BaseLightTests {

    @Autowired
    protected UpdateStructuredChatService service;

    public UpdateStructuredChatService.LightWithXYZ find(String color, List<UpdateStructuredChatService.LightWithXYZ> lights) {
        return lights.stream()
                .filter(light -> light.color().equalsIgnoreCase(color))
                .findFirst()
                .orElseThrow();
    }

    @Test
    public void changeLightStatus() {
        String query = "Turn the red light on. Then show the state of the yellow, red, and purple lights.";
        UpdateStructuredChatService.LightWithXYZList response = service.converse(
                List.of(new UserMessage(query)));
        System.out.println(response);
        /*
         * LightWithXYZList[lights=[
         * LightWithXYZ[color=yellow, on=false, x=0.428, y=0.505, z=0.067],
         * LightWithXYZ[color=red, on=true, x=0.64, y=0.33, z=0.03],
         * LightWithXYZ[color=purple, on=false, x=0.3, y=0.15, z=0.55]
         * ]]
         */

        UpdateStructuredChatService.LightWithXYZ red = find("RED", response.lights());
        Assertions.assertTrue(red.on(), "red is on");
        Assertions.assertEquals(0.64, red.x());
        Assertions.assertEquals(0.33, red.y());
        Assertions.assertEquals(0.03, red.z());

        UpdateStructuredChatService.LightWithXYZ purple = find("Purple", response.lights());
        Assertions.assertFalse(purple.on(), "purple is off");
        Assertions.assertEquals(0.3, purple.x());
        Assertions.assertEquals(0.15, purple.y());
        Assertions.assertEquals(0.55, purple.z());
    }

}

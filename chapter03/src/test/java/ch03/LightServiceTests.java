package ch03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch03.service.LightService;

@SpringBootTest
public class LightServiceTests extends BaseLightTests {

    @Autowired
    protected LightService lightService;

    @Test
    public void testLights() {
        // We expect four lights in our configuration.
        Assertions.assertEquals(4, lightService.getLights().size(), "count of lights");
    }

    @Test
    public void findLight() {
        // We need to be able to find a specific light.
        Assertions.assertTrue(lightService.getLight("red").isPresent(), "red light is present");
    }

    @Test
    public void failToFindMissingLight() {
        // We need to be able to make sure a light doesn't exist.
        Assertions.assertFalse(lightService.getLight("purple").isPresent(), "purple light is not present");
    }

    @Test
    public void changeLight() {
        assertState("yellow", false);

        // Set the light on
        lightService.setLight("yellow", true);
        assertState("yellow", true);

        // Turn it off, restoring original state
        lightService.setLight("yellow", false);
        assertState("yellow", false);
    }

}

package ch03;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch03.model.Light;
import ch03.service.LightService;

@SpringBootTest
public abstract class BaseLightTests {

    @Autowired
    protected LightService lightService;

    @BeforeEach
    public void turnAllLightsOff() {
        lightService.getLights()
                .forEach(light -> light.setOn(false));
    }

    public final void assertState(String color, boolean on) {
        Optional<Light> light = lightService.getLight(color);
        Assertions.assertTrue(light.isPresent(), color + " light is present");
        Assertions.assertEquals(on, light.get().getOn(), color + " light state");
    }

    public final Map<String, Boolean> mapToStatus(List<Light> lights) {
        return lights.stream()
                .collect(Collectors.toMap(Light::getColor, Light::getOn));
    }

}

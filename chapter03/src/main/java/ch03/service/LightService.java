package ch03.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ch03.model.Light;

@Service
public class LightService {

    protected ApplicationContext context;

    @Autowired
    public LightService(ApplicationContext context) {
        this.context = context;
    }

    public List<Light> getLights() {
        return context.getBeansOfType(Light.class)
                .values()
                .stream()
                .toList();
    }

    public Optional<Light> getLight(String color) {
        return getLights().stream()
                .filter(light -> light.getColor().equalsIgnoreCase(color))
                .findFirst();
    }

    public Optional<Light> setLight(String color, boolean status) {
        Optional<Light> light = getLight(color);
        light.ifPresent(it -> it.setOn(status));
        return light;
    }

}

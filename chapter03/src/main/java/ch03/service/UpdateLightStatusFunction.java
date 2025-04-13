package ch03.service;

import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import ch03.model.Light;

@Service(value = "ChangeLightStatusService")
@Description(value = "Change a light's state")
public class UpdateLightStatusFunction implements Function<UpdateLightStatusFunction.Request, UpdateLightStatusFunction.Response> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public record Request(String color, boolean on) {
    }

    public record Response(String color, boolean on) {
    }

    protected LightService lightService;

    @Autowired
    public UpdateLightStatusFunction(LightService lightService) {
        this.lightService = lightService;
    }

    @Override
    public Response apply(Request request) {
        log.info("Changing status for light: {}", request);
        Optional<Light> light = lightService.setLight(request.color, request.on);
        return light.map(value -> new Response(request.color, value.getOn()))
                .orElse(null);
    }

}

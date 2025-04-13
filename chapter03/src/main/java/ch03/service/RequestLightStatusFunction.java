package ch03.service;

import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import ch03.model.Light;

@Service(value = "RequestLightStatusService")
@Description(value = "Get light status")
public class RequestLightStatusFunction implements Function<RequestLightStatusFunction.Request, RequestLightStatusFunction.Response> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public record Request(String color) {
    }

    public record Response(String color, boolean on) {
    }

    protected LightService lightService;

    @Autowired
    public RequestLightStatusFunction(LightService lightService) {
        this.lightService = lightService;
    }

    @Override
    public Response apply(Request request) {
        log.info("Requesting status for light: {}", request);
        Optional<Light> light = lightService.getLight(request.color);
        return light.map(value -> new Response(request.color, value.getOn()))
                .orElse(null);
    }

}

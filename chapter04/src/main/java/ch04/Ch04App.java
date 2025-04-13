package ch04;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

import ch03.model.Light;
import ch03.service.LightService;
import ch03.service.RequestLightStatusFunction;
import ch03.service.UpdateChatService;
import ch03.service.UpdateLightStatusFunction;

@SpringBootApplication
public class Ch04App {

    @Bean
    public Light getYellowLight() {
        return new Light("yellow", false);
    }

    @Bean
    public Light getRedLight() {
        return new Light("red", false);
    }

    @Bean
    public Light getGreenLight() {
        return new Light("green", false);
    }

    @Bean
    public Light getBlueLight() {
        return new Light("blue", false);
    }

    @Bean
    public LightService getLightService(ApplicationContext context) {
        return new LightService(context);
    }

    @Bean(name = "RequestLightStatusService")
    @Description(value = "Get light status")
    public RequestLightStatusFunction getRequestLightStatusFunction(LightService lightService) {
        return new RequestLightStatusFunction(lightService);
    }
    @Bean(name = "ChangeLightStatusService")
    @Description(value = "Change a light's state")
    public UpdateLightStatusFunction getChangeLightStatusFunction(LightService lightService) {
        return new UpdateLightStatusFunction(lightService);
    }

    @Bean
    public UpdateChatService getLightUpdateChatService(ChatClient.Builder builder) {
        return new UpdateChatService(builder);
    }

    public static void main(String[] args) {
        SpringApplication.run(Ch04App.class, args);
    }

}

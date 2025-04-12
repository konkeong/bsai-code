package ch03;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch03.model.Light;

@SpringBootApplication
public class Ch03App {

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

}


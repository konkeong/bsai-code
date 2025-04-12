package ch02;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch02.service.JaccardSimilarityCalculator;

@SpringBootTest
public class JaccardTests {

    @Autowired
    protected JaccardSimilarityCalculator calculator;

    public static Stream<Arguments> texts() {
        return Stream.of(
                Arguments.of(
                        "This is come cool text. More is better but this will do.",
                        "This is come cool text. More is better but this will do.",
                        1.0),
                Arguments.of(
                        "Now is the time for all good men to come to the aid of their country.",
                        "The quick brown fox jumps over the lazy dog's tail.",
                        0.0),
                Arguments.of(
                        "This is come cool text. More is better but this will do.",
                        "This is come cool text. More is better but this might do.",
                        0.7));
    }

    @ParameterizedTest
    @MethodSource("texts")
    public void testTexts(String text1, String text2, double expected) {
        double similarity = calculator.calculate(text1, text2, 2);
        Assertions.assertEquals(expected, similarity, 0.01);
    }

}

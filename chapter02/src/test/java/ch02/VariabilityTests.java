package ch02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch02.service.JaccardSimilarityCalculator;
import ch02.service.OptionChatService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VariabilityTests {

    @Autowired
    protected JaccardSimilarityCalculator calculator;

    @Autowired
    protected OptionChatService optionChatService;

    final String query = "Write a story about a salamander learning to fly.";

    private String graph(double c) {
        return StringUtils.rightPad(StringUtils.repeat("*", (int) (c * 20)), 22, " ");
    }

    public static Stream<Arguments> controlParameters() {
        // `temperature` range from 0.0 to 2.0
        // `top_p` range from 0.0 to 1.0
        return Stream.of(
                Arguments.of(true, 0.0, 2.0),
                Arguments.of(false, 0.01, 0.3));
    }

    @ParameterizedTest
    @MethodSource("controlParameters")
    public void testTemperature(boolean useTemperature, double lower, double upper) {
        List<String> results = new ArrayList<>();
        SimpleRegression regression = new SimpleRegression();

        // We're going to reuse the same options instance, but change its parameters.
        OpenAiChatOptions options = OpenAiChatOptions.builder().build();

        double step = (upper - lower) / 7;
        for (double value = lower; value < upper; value += step) {
            if (useTemperature) {
                options.setTemperature(value);
            } else {
                options.setTopP(value);
            }
            String response = optionChatService.query(query, options);

            System.out.println((useTemperature ? "temperature" : "top_p") + "=" + value);
            System.out.println(response);
            System.out.println();

            results.add(response);
        }

        for (int i = 1; i < results.size(); i++) {
            double similarity = calculator.calculate(results.getFirst(), results.get(i), 2);
            System.out.println(i + " :: " + graph(similarity) + " :: " + similarity);
            regression.addData(i, similarity);
        }

        double slope = regression.getSlope();
        /*
         * slope should be negative to indicate less similarity
         * although top_p is less predictable here
         */
        System.out.println((useTemperature ? "temparature" : "top_p") + " :: slope=[" + slope + "]");
        Assertions.assertTrue(slope < (useTemperature ? 0.0 : 0.05));
    }

}

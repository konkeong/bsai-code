package ch02.service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class JaccardSimilarityCalculator {

    public Set<String> preprocessText(String text, int ngramSize) {
        String[] tokens = text.toLowerCase().split("\\W+");
        Set<String> ngrams = new LinkedHashSet<>();
        for (int i = 0; i <= tokens.length - ngramSize; i++) {
            StringBuilder ngram = new StringBuilder();
            for (int j = 0; j < ngramSize; j++) {
                ngram.append(tokens[i + j]).append(" ");
            }
            ngrams.add(ngram.toString().trim());
        }
        return ngrams;
    }

    public double calculate(String text1, String text2, int ngramSize) {
        // Preprocess texts
        Set<String> set1 = new HashSet<>(preprocessText(text1, ngramSize));
        Set<String> set2 = new HashSet<>(preprocessText(text2, ngramSize));

        // Calculate intersection
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        // Calculate union
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        // Calculate Jaccard similarity
        return (double) intersection.size() / union.size();
    }

    public static void main(String[] args) {
        JaccardSimilarityCalculator calculator = new JaccardSimilarityCalculator();
        String ngramText = "n-grams are sequences of tokens in a specific order";
        System.out.println(calculator.preprocessText(ngramText, 2));
        System.out.println(calculator.preprocessText(ngramText, 3));

        String text1 = "The quick brown fox jumps over the lazy dog.";
        String text2 = "The lazy dog jumps over the quick brown fox.";
        double similarity = calculator.calculate(text1, text2, 3);
        System.out.println("Similarity: " + similarity);
    }

}

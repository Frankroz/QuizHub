package com.quizapp.attempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.quiz.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class QuizRandomizer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private QuizRandomizer() {
    }

    public record RandomizedQuiz(
            String questionOrder,
            String optionOrders) {

    }

    public static RandomizedQuiz create(List<Question> questions) {

        List<Question> shuffledQuestions = new ArrayList<>(questions);
        Collections.shuffle(shuffledQuestions);

        String questionOrder = shuffledQuestions.stream()
                .map(q -> q.getId().toString())
                .collect(Collectors.joining(","));

        Map<String, List<String>> optionOrders = new LinkedHashMap<>();

        for (Question question : questions) {
            List<String> options = new ArrayList<>(
                    List.of("A", "B", "C", "D")
            );

            // Each question gets its own independent shuffle.
            Collections.shuffle(options);

            optionOrders.put(
                    question.getId().toString(),
                    options
            );
        }

        try {
            return new RandomizedQuiz(
                    questionOrder,
                    MAPPER.writeValueAsString(optionOrders)
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Could not save quiz randomization.",
                    e
            );
        }
    }

    public static List<Long> readQuestionOrder(String value) {

        if (value == null || value.isBlank()) {
            return List.of();
        }

        List<Long> result = new ArrayList<>();

        for (String id : value.split(",")) {
            result.add(Long.valueOf(id));
        }

        return result;
    }

    public static Map<String, List<String>> readOptionOrders(String value) {

        try {
            return MAPPER.readValue(
                    value,
                    new TypeReference<Map<String, List<String>>>() {
            }
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Could not read quiz randomization.",
                    e
            );
        }
    }
}

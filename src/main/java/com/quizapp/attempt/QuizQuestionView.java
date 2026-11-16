package com.quizapp.attempt;

import java.util.List;

import com.quizapp.quiz.Question;

public record QuizQuestionView(
        Question question,
        List<QuizOptionView> options
        ) {

}

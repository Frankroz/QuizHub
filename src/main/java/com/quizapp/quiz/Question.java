package com.quizapp.quiz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "option_a", nullable = false)
    private String optionA;

    @Column(name = "option_b", nullable = false)
    private String optionB;

    @Column(name = "option_c", nullable = false)
    private String optionC;

    @Column(name = "option_d", nullable = false)
    private String optionD;

    @Column(name = "correct_option", nullable = false, length = 1)
    private String correctOption;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "question_order", nullable = false)
    private int questionOrder;

    @Column(name = "image_path", length = 500)
    private String imagePath;

    protected Question() {
    }

    public Question(
            String text,
            String a,
            String b,
            String c,
            String d,
            String correct,
            String explanation,
            int order) {

        this.questionText = text;
        this.optionA = a;
        this.optionB = b;
        this.optionC = c;
        this.optionD = d;
        this.correctOption = correct;
        this.explanation = explanation;
        this.questionOrder = order;
    }

    public Quiz getQuiz() {
        return this.quiz;
    }

    void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getExplanation() {
        return explanation;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String optionText(String key) {
        return switch (key) {
            case "A" ->
                optionA;
            case "B" ->
                optionB;
            case "C" ->
                optionC;
            case "D" ->
                optionD;
            default ->
                "";
        };
    }

    public void setQuestionText (String value) {
        this.questionText = value;
    }
    public void setOptionA (String value) {
        this.optionA = value;
    }
    public void setOptionB (String value) {
        this.optionB = value;
    }
    public void setOptionC (String value) {
        this.optionC = value;
    }
    public void setOptionD (String value) {
        this.optionD = value;
    }
    public void setCorrectOption (String value) {
        this.correctOption = value;
    }
    public void setExplanation (String value) {
        this.explanation = value;
    }
    public void setQuestionOrder (int value) {
        this.questionOrder = value;
    }
}

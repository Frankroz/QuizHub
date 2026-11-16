package com.quizapp.attempt;

import java.time.LocalDateTime;

import com.quizapp.quiz.Quiz;
import com.quizapp.user.User;

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
@Table(name = "quiz_attempts")
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private int score;

    @Column(name = "total_questions", nullable = false)
    private int totalQuestions;

    @Column(name = "question_order", columnDefinition = "TEXT")
    private String questionOrder;

    @Column(name = "option_orders", columnDefinition = "LONGTEXT")
    private String optionOrders;

    @Column(nullable = false, length = 20)
    private String status = "IN_PROGRESS";

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    protected QuizAttempt() {
    }

    public QuizAttempt(
            User student,
            Quiz quiz,
            String questionOrder,
            String optionOrders) {

        this.student = student;
        this.quiz = quiz;
        this.score = 0;
        this.totalQuestions = quiz.getQuestions().size();
        this.questionOrder = questionOrder;
        this.optionOrders = optionOrders;
        this.status = "IN_PROGRESS";
    }

    public Long getId() {
        return id;
    }

    public User getStudent() {
        return student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public String getQuestionOrder() {
        return questionOrder;
    }

    public String getOptionOrders() {
        return optionOrders;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void complete(int score) {
        if ("SUBMITTED".equals(this.status)) {
            throw new IllegalStateException("This attempt has already been submitted.");
        }

        this.score = score;
        this.status = "SUBMITTED";
        this.submittedAt = LocalDateTime.now();
    }
}

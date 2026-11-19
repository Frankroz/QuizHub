package com.quizapp.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizEditForm {

    private String title;
    private String subject;
    private String description;

    private List<QuestionEditForm> questions = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionEditForm> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEditForm> questions) {
        this.questions = questions;
    }
}

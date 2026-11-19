package com.quizapp.quiz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.quizapp.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100) private String title;
    @Column(nullable = false, length = 50) private String subject;
    @Column(nullable = false, columnDefinition = "TEXT") private String description;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;
    @Column(nullable = false, length = 10) private String status = "DRAFT";
    @Column(name = "created_at", insertable = false, updatable = false) private LocalDateTime createdAt;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("questionOrder ASC") private List<Question> questions = new ArrayList<>();

    protected Quiz() {}
    public Quiz(String title, String subject, String description, User teacher) {
        this.title = title; this.subject = subject; this.description = description; this.teacher = teacher;
    }
    public void addQuestion(Question q) { q.setQuiz(this); questions.add(q); }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public User getTeacher() { return teacher; }
    public String getStatus() { return status; }
    public List<Question> getQuestions() { return questions; }
    public boolean isDeleted() { return "DELETED".equals(this.status); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void publish() { status = "PUBLISHED"; }
    public void unpublish() { status = "DRAFT"; }
    public void setTitle(String title) { this.title = title; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setDescription(String description) { this.description = description; }
    public void delete() { this.status = "DELETED"; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}

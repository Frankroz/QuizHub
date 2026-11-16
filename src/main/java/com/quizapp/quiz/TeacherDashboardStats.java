package com.quizapp.quiz;

public record TeacherDashboardStats(
        long totalQuizzes,
        long publishedQuizzes,
        long draftQuizzes,
        long totalAttempts,
        long uniqueStudents,
        long passedAttempts,
        long failedAttempts,
        double averageScore,
        double passRate
        ) {}

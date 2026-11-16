package com.quizapp.quiz;

public record TeacherQuizStats(
        long attempts,
        long uniqueStudents,
        double averageScore,
        double passRate
        ) {}

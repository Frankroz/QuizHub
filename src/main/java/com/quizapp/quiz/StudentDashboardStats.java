
package com.quizapp.quiz;

public record StudentDashboardStats(
        long quizzesCompleted,
        long totalAttempts,
        long passedAttempts,
        long failedAttempts,
        double averageScore,
        double passRate,
        double bestScore
) {}

package com.quizapp.attempt;

import com.quizapp.quiz.Question;

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
@Table(name = "attempt_answers")
public class AttemptAnswer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "attempt_id", nullable = false) private QuizAttempt attempt;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "question_id", nullable = false) private Question question;
    @Column(name = "selected_option", nullable = false, length = 1) private String selectedOption;
    @Column(name = "is_correct", nullable = false) private boolean correct;
    @Column(name = "question_text_snapshot", columnDefinition = "TEXT") private String questionTextSnapshot;
    @Column(name = "option_a_snapshot", columnDefinition = "TEXT") private String optionASnapshot;
    @Column(name = "option_b_snapshot", columnDefinition = "TEXT") private String optionBSnapshot;
    @Column(name = "option_c_snapshot", columnDefinition = "TEXT") private String optionCSnapshot;
    @Column(name = "option_d_snapshot", columnDefinition = "TEXT") private String optionDSnapshot;
    @Column(name = "correct_option_snapshot", length = 1) private String correctOptionSnapshot;
    @Column(name = "explanation_snapshot", columnDefinition = "TEXT") private String explanationSnapshot;
    @Column(name = "image_path_snapshot", length = 500) private String imagePathSnapshot;
    
    protected AttemptAnswer() {}
    public Long getId() { return id; }
    public QuizAttempt getAttempt() { return attempt; }
    public Question getQuestion() { return question; }
    public String getSelectedOption() { return selectedOption; }
    public boolean isCorrect() { return correct; }
    public AttemptAnswer(QuizAttempt attempt,
                         Question question,
                         String selectedOption,
                         boolean correct,
                         String questionTextSnapshot,
                         String optionASnapshot,
                         String optionBSnapshot,
                         String optionCSnapshot,
                         String optionDSnapshot,
                         String correctOptionSnapshot,
                         String explanationSnapshot,
                         String imagePathSnapshot) {
        this.attempt=attempt;
        this.question=question;
        this.selectedOption=selectedOption;
        this.correct=correct;
        this.questionTextSnapshot=questionTextSnapshot;
        this.optionASnapshot=optionASnapshot;
        this.optionBSnapshot=optionBSnapshot;
        this.optionCSnapshot=optionCSnapshot;
        this.optionDSnapshot=optionDSnapshot;
        this.correctOptionSnapshot=correctOptionSnapshot;
        this.explanationSnapshot=explanationSnapshot;
        this.imagePathSnapshot=imagePathSnapshot;
    }

    public String getQuestionTextSnapshot() { return questionTextSnapshot; }
    public String getOptionASnapshot() { return optionASnapshot; }
    public String getOptionBSnapshot() { return optionBSnapshot; }
    public String getOptionCSnapshot() { return optionCSnapshot; }
    public String getOptionDSnapshot() { return optionDSnapshot; }
    public String getCorrectOptionSnapshot() { return correctOptionSnapshot; }
    public String getExplanationSnapshot() { return explanationSnapshot; }
    public String getImagePathSnapshot() { return imagePathSnapshot; }
    
    public void setQuestionTextSnapshot(String value) { this.questionTextSnapshot = value; }
    public void setOptionASnapshot(String value) { this.optionASnapshot = value; }
    public void setOptionBSnapshot(String value) { this.optionBSnapshot = value; }
    public void setOptionCSnapshot(String value) { this.optionCSnapshot = value; }
    public void setOptionDSnapshot(String value) { this.optionDSnapshot = value; }
    public void setCorrectOptionSnapshot(String value) { this.correctOptionSnapshot = value; }
    public void setExplanationSnapshot(String value) { this.explanationSnapshot = value; }
    public void setImagePathSnapshot(String value) { this.imagePathSnapshot = value; }
}

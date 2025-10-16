package com.rk.online_quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attempted_quiz_questions")
public class AttemptedQuizQuestion {

    @EmbeddedId
    private AttemptedQuizQuestionId id;

    // 🔗 Relations (foreign keys)
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_aqq_user"))
    private LoginUser user;

    @MapsId("attemptedQuizId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempted_quiz_id", nullable = false, foreignKey = @ForeignKey(name = "fk_aqq_attempted_quiz"))
    private AttemptedQuiz attemptedQuiz;

    @MapsId("quizId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false, foreignKey = @ForeignKey(name = "fk_aqq_quiz"))
    private QuizCreation quiz;

    @MapsId("quizQuestionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_question_id", nullable = false, foreignKey = @ForeignKey(name = "fk_aqq_question"))
    private QuizQuestionCreation quizQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempted_option_id", foreignKey = @ForeignKey(name = "fk_aqq_option"))
    private QuizQuestOptionCreation attemptedOption;

    @Column(nullable = false)
    private boolean correct = false;
}

package com.rk.online_quiz.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AttemptedQuizQuestionId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "attempted_quiz_id")
    private Long attemptedQuizId;

    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "quiz_question_id")
    private Long quizQuestionId;

    public AttemptedQuizQuestionId() {
    }

    public AttemptedQuizQuestionId(Long userId, Long attemptedQuizId, Long quizId, Long quizQuestionId) {
        this.userId = userId;
        this.attemptedQuizId = attemptedQuizId;
        this.quizId = quizId;
        this.quizQuestionId = quizQuestionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttemptedQuizQuestionId)) return false;
        AttemptedQuizQuestionId that = (AttemptedQuizQuestionId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(attemptedQuizId, that.attemptedQuizId) &&
                Objects.equals(quizId, that.quizId) &&
                Objects.equals(quizQuestionId, that.quizQuestionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, attemptedQuizId, quizId, quizQuestionId);
    }
}

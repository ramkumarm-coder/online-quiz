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

    @Column(name = "attempted_option_id")
    private Long attemptedOption;

    @Column(nullable = false)
    private boolean correct = false;
}

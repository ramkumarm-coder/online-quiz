package com.rk.online_quiz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "quiz_quest_option_creation")
public class QuizQuestOptionCreation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_option_seq")
    @SequenceGenerator(name = "quiz_option_seq", sequenceName = "quiz_option_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String option;

    @Column(nullable = false)
    private boolean correct = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_question_id", nullable = false, foreignKey = @ForeignKey(name = "fk_option_question"))
    @JsonBackReference
    private QuizQuestionCreation question;
}

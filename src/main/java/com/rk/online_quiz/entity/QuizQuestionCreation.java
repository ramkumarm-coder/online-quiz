package com.rk.online_quiz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "quiz_question_creation")
public class QuizQuestionCreation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_question_seq")
    @SequenceGenerator(name = "quiz_question_seq", sequenceName = "quiz_question_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_creation_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quiz_question_creation_quiz"))
    @JsonBackReference
    private QuizCreation quizCreation;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuizQuestOptionCreation> options = new ArrayList<>();
}

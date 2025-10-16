package com.rk.online_quiz.entity;

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
@Table(name = "quiz_creation")
public class QuizCreation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_creation_seq")
    @SequenceGenerator(name = "quiz_creation_seq", sequenceName = "quiz_creation_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(name = "time_limit_minutes", nullable = false)
    private int timeLimitMinutes;

    @OneToMany(mappedBy = "quizCreation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuizQuestionCreation> questions = new ArrayList<>();
}

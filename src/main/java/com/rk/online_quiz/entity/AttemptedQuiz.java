package com.rk.online_quiz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attempted_quiz")
public class AttemptedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attempted_quiz_seq")
    @SequenceGenerator(name = "attempted_quiz_seq", sequenceName = "attempted_quiz_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "attempted_time",nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime attemptedTime;

    @Column
    private boolean completed = false;

    @Column(name = "completed_time")
    private LocalDateTime completedTime;

    @Column(name = "total_marks")
    private int totalMarks;

    @Column(name = "final_score")
    private int finalScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_attempt_login_user"))
    private LoginUser loginUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_creation_id", nullable = false, foreignKey = @ForeignKey(name = "fk_attempt_quiz_creation"))
    private QuizCreation quizCreation;
}

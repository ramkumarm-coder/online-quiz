package com.rk.online_quiz.dto.admin;

import com.rk.online_quiz.entity.QuizCreation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DashboardResponse {
    List<QuizCreation> quizzes;
}

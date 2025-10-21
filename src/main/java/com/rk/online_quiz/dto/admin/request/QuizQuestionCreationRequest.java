package com.rk.online_quiz.dto.admin.request;

import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.entity.QuizQuestOptionCreation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuizQuestionCreationRequest {

    private Long id;

    @NotBlank(message = "Question is required.")
    @Size(max = 400, message = "Question must be less than 400 characters.")
    private String question;

    @Valid
    @NotEmpty(message = "At least one option is required.")
    @Size(min = 2, message = "A question must have at least two options.")
    private List<QuizQuestionCreationOptionDTO> options;
}

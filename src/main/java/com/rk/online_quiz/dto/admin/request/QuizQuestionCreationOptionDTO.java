package com.rk.online_quiz.dto.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionCreationOptionDTO {
    @NotBlank(message = "Option text is required.")
    @Size(max = 200, message = "Option text must be less than 200 characters.")
    private String option;

    private boolean correct = false;
}

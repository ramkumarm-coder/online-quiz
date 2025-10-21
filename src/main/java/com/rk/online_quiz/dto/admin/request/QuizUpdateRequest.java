package com.rk.online_quiz.dto.admin.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizUpdateRequest {

    @NotNull(message = "ID is required for updating an existing quiz.")
    @Positive(message = "ID must be a positive number.")
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max = 100, message = "Title must be less than 100 characters.")
    private String title;

    @NotBlank(message = "Description is required.")
    @Size(max = 500, message = "Description must be less than 500 characters.")
    private String description;

    @NotNull(message = "Time limit is required.")
    @Min(value = 1, message = "Time limit must be at least 1 minute.")
    @Max(value = 180, message = "Time limit cannot exceed 180 minutes.")
    private Integer timeLimitMinutes;
}

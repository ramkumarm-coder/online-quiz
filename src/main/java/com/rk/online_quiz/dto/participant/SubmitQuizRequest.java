package com.rk.online_quiz.dto.participant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitQuizRequest {
    private long questionId;
    private long answerId;
}

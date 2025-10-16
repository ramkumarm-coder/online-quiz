package com.rk.online_quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Breadcrumb {
    private String label;
    private String url;

    public boolean hasUrl() {
        return url != null && !url.isBlank();
    }
}

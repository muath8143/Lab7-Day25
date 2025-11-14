package com.example.academicsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Assignment {
    @NotEmpty(message = "The id is required")
    private String id;

    @NotEmpty(message = "The course id is required")
    private String courseId;

    @NotEmpty(message = "The title is required")
    private String title;

    @NotEmpty(message = "The description is required")
    private String description;

    @Null(message = "You can't enter the duo date now please leave it empty")
    @Future(message = "The duo date must be in future ")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime duoDate;

    @AssertFalse(message = "The state of assignment must be false")
    private boolean isActive;
}
